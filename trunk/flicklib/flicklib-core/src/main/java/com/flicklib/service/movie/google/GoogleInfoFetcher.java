/*
 * This file is part of Flicklib.
 *
 * Copyright (C) Francis De Brabandere
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flicklib.service.movie.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.api.Parser;
import com.flicklib.domain.Movie;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;
import com.flicklib.service.SourceLoader;
import com.flicklib.tools.Param;
import com.flicklib.tools.SimpleXPath;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 * @author francisdb
 */
@Singleton
public class GoogleInfoFetcher extends AbstractMovieInfoFetcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleInfoFetcher.class);

	/**
         * http://www.google.com/movies
         */
        private final static MovieService GOOGLE = new MovieService("GOOGLE", "Google movies", "http://www.google.com", "Google");

	private final Parser googleParser;
	private final SourceLoader httpLoader;

	/**
	 * Constructs a new GoogleInfoFetcher
	 * @param googleParser
	 * @param httpLoader
	 */
	@Inject
	public GoogleInfoFetcher(final @Google Parser googleParser, SourceLoader httpLoader) {
		this.googleParser = googleParser;
		this.httpLoader = httpLoader;
	}

	public GoogleInfoFetcher(SourceLoader loader) {
		this(new GoogleParser(), loader);
	}

	@Override
	public MoviePage getMovieInfo(String id) throws IOException {
		if (id.startsWith("http://www.google.com/movies/reviews")) {
			MoviePage site = new MoviePage(GOOGLE);
			site.setUrl(id);
			com.flicklib.service.Source source = httpLoader.loadSource(id);
			googleParser.parse(source, site);
			return site;
		}
		return null;
	}

	@Override
	public List<MovieSearchResult> search(String title) throws IOException {
		String url = GOOGLE.getUrl() + "/movies" + Param.paramString("q", title);

		com.flicklib.service.Source sourceString = httpLoader.loadSource(url);
		Source source = sourceString.getJerichoSource();

		SimpleXPath xp = new SimpleXPath(source.getElementById("movie_results")).getAllTagByAttributes("itemtype", "http://schema.org/Movie");

		List<MovieSearchResult> result = new ArrayList<MovieSearchResult>();

		for (Element e : xp) {
			final SimpleXPath xpath = new SimpleXPath(e);
			String foundTitle = xpath.getAllTagByAttributes("itemprop", "name").getValue();

			
			int on = 0;
			int off = 0;
			for (Element img : xpath.getTags(HTMLElementName.IMG)) {
				String src = img.getAttributeValue("src");
				if ("/images/sy-star-on.gif".equals(src)) {
					on ++;
				} else if ("/images/sy-star-off.gif".equals(src)) {
					off ++;
				}
			}
			MovieSearchResult m;
			if (on + off > 0) {
				double score = ((double) on*100/(on + off));
				LOGGER.info("score for "+foundTitle+" is "+score);
				MoviePage mp = new MoviePage();
				mp.setScore((int) score);
				m = mp;
			} else {
				m = new MovieSearchResult();
			}
			m.setService(GOOGLE);
			m.setTitle(foundTitle);

			Element link = xpath.getAllTagByAttributes("class", "info links").getTags(HTMLElementName.A).firstElement();
			if (link != null) {
				String href = link.getAttributeValue("href");
				m.setUrl(GOOGLE.getUrl() + href);
				Matcher matcher = Pattern.compile("mid=(\\w+)").matcher(href);
				if (matcher.find()) {
					m.setIdForSite(matcher.group(1));
				}
			} else {
				// no critics ... just write something in it
				m.setUrl(url);
				m.setIdForSite(foundTitle);
			}
			String desc = xpath.getAllTagByAttributes("itemprop", "description").getValue();
			if (desc != null) {
				String moreDesc = xpath.getAllTagByAttributes("id", "SynopsisSecond0").getValue();
				if (moreDesc != null) {
					desc = desc + moreDesc;
				}
				m.setDescription(desc);
			}
			result.add(m);
		}
		return result;
	}

	@Deprecated
	public MoviePage fetch(Movie movie, String id) {
		MoviePage site = new MoviePage();
		//site.setMovie(movie);
		site.setService(GOOGLE);
		try {
			String params = Param.paramString("q", movie.getTitle());
			com.flicklib.service.Source httpSource = httpLoader.loadSource("http://www.google.com/movies" + params);
			Source source = httpSource.getJerichoSource();
			//source.setLogWriter(new OutputStreamWriter(System.err)); // send log messages to stderr

			//Element titleElement = (Element)source.getAllElements(HTMLElementName.TITLE).get(0);
			//System.out.println(titleElement.getContent().extractText());

			// <div id="bubble_allCritics" class="percentBubble" style="display:none;">     57%    </div>

			String movieUrl = null;
			List<?> aElements = source.getAllElements(HTMLElementName.A);
			for (Iterator<?> i = aElements.iterator(); i.hasNext() && movieUrl == null;) {
				Element aElement = (Element) i.next();
				String url = aElement.getAttributeValue("href");
				// /movies/reviews?cid=b939f27b219eb36f&fq=Pulp+Fiction&hl=en
				if (url != null && url.startsWith("/movies/reviews?cid=")) {
					movieUrl = "http://www.google.com" + url;
					String movieName = aElement.getContent().getTextExtractor().toString();
					LOGGER.info("taking first result: " + movieName + " -> " + movieUrl);
				}
			}
			if (movieUrl == null) {
				throw new IOException("Movie not found on Google: " + movie.getTitle());
			}
			site.setUrl(movieUrl);
			httpSource = httpLoader.loadSource(movieUrl);
			googleParser.parse(httpSource, site);
		} catch (IOException ex) {
			LOGGER.error("Loading from Google failed", ex);
		}
		return site;
	}
	
	@Override
	public MovieService getService() {
	    return GOOGLE;
	}

}
