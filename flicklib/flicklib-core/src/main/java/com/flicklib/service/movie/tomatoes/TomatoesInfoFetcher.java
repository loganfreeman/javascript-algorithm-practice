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
package com.flicklib.service.movie.tomatoes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
import com.flicklib.tools.StringUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 * @author fdb
 */
@Singleton
public class TomatoesInfoFetcher extends AbstractMovieInfoFetcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(TomatoesInfoFetcher.class);
        /**
         * http://www.rottentomatoes.com
         */
        final static MovieService TOMATOES = new MovieService("TOMATOES", "Rotten Tomatoes", "http://www.rottentomatoes.com", "Tomatoes");
        
    
	private final SourceLoader sourceLoader;
	private final Parser tomatoesParser = new TomatoesParser();

	@Inject
	public TomatoesInfoFetcher(final SourceLoader sourceLoader) {
		this.sourceLoader = sourceLoader;
	}

	@Override
	public MoviePage getMovieInfo(String id) throws IOException {
		MoviePage site = new MoviePage(TOMATOES);
		String url = generateTomatoesUrl(id);
		site.setUrl(url);
		com.flicklib.service.Source source = sourceLoader.loadSource(site.getUrl());
		tomatoesParser.parse(source, site);
		return site;
	}

	@Override
	public List<MovieSearchResult> search(String title) throws IOException {
		// use the imdb fetcher, to load IMDB id-s.


		com.flicklib.service.Source source = sourceLoader.loadSource(createTomatoesSearchUrl(title));
		Source jerichoSource = source.getJerichoSource();
		//source.setLogWriter(new OutputStreamWriter(System.err)); // send log messages to stderr

		return parseSearchResults(jerichoSource);
	}

	private List<MovieSearchResult> parseSearchResults(Source jerichoSource) {
		List<MovieSearchResult> result = new ArrayList<MovieSearchResult>();
		Element ul = jerichoSource.getElementById("movie_results_ul");
		if (ul != null) {
			for (Element li : ul.getAllElements(HTMLElementName.LI)) {
				MoviePage m = new MoviePage();
				m.setService(TOMATOES);
				
				Element tmeter = li.getFirstElement("class", "tmeter", true);
				if (tmeter != null) {
					String tmeterTxt = tmeter.getTextExtractor().toString().replace('%', ' ').replace('—',' ').replaceAll("&mdash;", "").trim();
					if (tmeterTxt.length() > 0) {
						int score = Integer.parseInt(tmeterTxt);
						m.setScore(score);
					}
				}
				Element mediaBlock = li.getFirstElement("class", "media_block_content", true);
				if (mediaBlock != null) {
					Element a = mediaBlock.getFirstElement(HTMLElementName.A);
					if (a != null) {
						m.setTitle(a.getTextExtractor().toString());
						String path = a.getAttributeValue("href");
						m.setIdForSite(path.replaceAll("/m/", ""));
						m.setUrl("http://www.rottentomatoes.com"+path);
					}
					Element mYear = mediaBlock.getFirstElement("class", "movie_year", true);
					if (mYear != null) {
						String yearStr = StringUtils.unbracket(mYear.getTextExtractor().toString());
						if (yearStr.length() > 0) {
							int year = Integer.parseInt(yearStr);
							m.setYear(year);
						}
					}
					
				}
				result.add(m);
			}
			
/*			List<?> trElements = ul.getAllElements(HTMLElementName.TR);
			for (Iterator<?> j = trElements.iterator(); j.hasNext();) {
				Element trElement = (Element) j.next();

				MovieSearchResult m = new MovieSearchResult();

				List<?> aElements = trElement.getAllElements(HTMLElementName.A);
				for (Iterator<?> k = aElements.iterator(); k.hasNext();) {
					Element aElement = (Element) k.next();
					String url = aElement.getAttributeValue("href");
					if (url != null && url.startsWith("/m/")) {
						String movieName = aElement.getContent().getTextExtractor().toString();
						if (movieName != null && movieName.trim().length() != 0) {
							String movieUrl = MovieService.TOMATOES.getUrl() + url;
							m.setUrl(movieUrl);
							m.setIdForSite(url.replace("/m/", ""));
							m.setTitle(movieName);
							m.setService(MovieService.TOMATOES);
							result.add(m);
							LOGGER.trace("taking result: " + movieName + " -> " + movieUrl);
						}
					}
				}

				List<?> strongElements = trElement.getAllElements(HTMLElementName.STRONG);
				for (Iterator<?> k = strongElements.iterator(); k.hasNext();) {
					Element strongElement = (Element) k.next();
					String year = strongElement.getContent().getTextExtractor().toString();
					if (year.trim().length() > 0) {

						// TODO handle these
						//1:44:55.249 [pool-2-thread-3] ERROR c.f.s.m.tomatoes.TomatoesInfoFetcher - For input string: "2002/2004"
						//11:44:55.249 [pool-2-thread-3] ERROR c.f.s.m.tomatoes.TomatoesInfoFetcher - For input string: "2002-2007"
						//11:44:55.249 [pool-2-thread-3] ERROR c.f.s.m.tomatoes.TomatoesInfoFetcher - For input string: "2002/2006"
						//11:44:55.250 [pool-2-thread-3] ERROR c.f.s.m.tomatoes.TomatoesInfoFetcher - For input string: "2002/2003"

						try {
							m.setYear(Integer.valueOf(year));
						} catch (NumberFormatException ex) {
							LOGGER.warn(ex.getMessage());
						}
					}
				}
			}*/
		}
		return result;
	}

	/**
	 * @deprecated not part of the interface
	 * @param movie
	 * @param imdbId
	 * @return the MoviePage
	 */
	@Deprecated
	public MoviePage fetch(Movie movie, String imdbId) {
		MoviePage site = new MoviePage();
		//site.setMovie(movie);
		site.setService(TOMATOES);
		if (imdbId == null || "".equals(imdbId)) {
			LOGGER.error("IMDB id missing", new IOException("No imdb id available, not implemented"));
		} else {
			try {
				String url = generateTomatoesUrlForImdb(imdbId);
				site.setUrl(url);
				com.flicklib.service.Source source = sourceLoader.loadSource(site.getUrl());
				tomatoesParser.parse(source, site);
			} catch (IOException ex) {
				LOGGER.error("Loading from rotten tomatoes failed", ex);
			}
		}
		return site;
	}

	private String createTomatoesSearchUrl(String title) {
		String encoded = "";
		try {
			encoded = URLEncoder.encode(title, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			LOGGER.error("Could not cencode UTF-8", ex);
		}
		return TOMATOES.getUrl() + "/search/?search=" + encoded;
	}

	/**
	 *
	 * @param movie 
	 * @return the tomatoes url
	 */
	private String generateTomatoesUrlForImdb(String imdbId) {
		return TOMATOES.getUrl() + "/alias?type=imdbid&s=" + imdbId;
	}

	/**
	*
	* @param movie 
	* @return the tomatoes url
	*/
	private String generateTomatoesUrl(String id) {
		return TOMATOES.getUrl() + "/m/" + id;
	}
	
	@Override
	public MovieService getService() {
	    return TOMATOES;
	}
}
