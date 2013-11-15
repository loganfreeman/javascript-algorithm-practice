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
package com.flicklib.service.movie.cinebel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.api.Parser;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;
import com.flicklib.domain.MovieType;
import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;
import com.flicklib.tools.SimpleXPath;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author francisdb
 *
 */
@Singleton
public class CinebelFetcher extends AbstractMovieInfoFetcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(CinebelFetcher.class);

	private final static MovieService CINEBEL = new MovieService("CINEBEL", "Cinebel", "http://www.cinebel.be");

	/**
	 * TODO add support for en and fr
	 * TODO add support for fetching info curently available in the movies (find count link and go to page)
	 */
	private static final String LANG = "nl";

	private static final String PREFIX = "/" + LANG + "/film/";

	private final SourceLoader sourceLoader;
	private final Parser parser;

	@Inject
	public CinebelFetcher(final SourceLoader sourceLoader, @Cinebel final Parser parser) {
		this.sourceLoader = sourceLoader;
		this.parser = parser;
	}

	public CinebelFetcher(final SourceLoader loader) {
		this(loader, new CinebelParser());
	}

	@Override
	public MoviePage getMovieInfo(String idForSite) throws IOException {
		MoviePage page = new MoviePage(CINEBEL);
		// No other movie types for this site?
		page.setType(MovieType.MOVIE);
		String url = generateMovieUrl(idForSite);
		page.setUrl(url);
		Source source = sourceLoader.loadSource(url);

		parser.parse(source, page);

		return page;
	}

	@Override
	public List<? extends MovieSearchResult> search(String title) throws IOException {
		LOGGER.trace("search for " + title);
		List<MovieSearchResult> list = new ArrayList<MovieSearchResult>();
		String url = generateSearchUrl(title, 30);
		Source source = sourceLoader.loadSource(url);
		net.htmlparser.jericho.Source document = source.getJerichoSource();

		SimpleXPath xp = new SimpleXPath(document.getAllElements("class", "searchResultsBox", true)).getAllTagByAttributes("class",
				"subSection snippet movieSnippet");
		for (Element e : xp) {
			// movie found
			MovieSearchResult rs = new MovieSearchResult();
			rs.setService(CINEBEL);
			for (Element name : new SimpleXPath(e).getAllTagByAttributes("class", "snippetTitle")) {
				String href = name.getAttributeValue("href");
				if (href.startsWith(PREFIX)) {
					rs.setTitle(name.getTextExtractor().toString());
					rs.setUrl(CINEBEL.getUrl() + href);
					href = href.substring(PREFIX.length());
					int end = href.indexOf('/');
					if (end >= 0) {
						href = href.substring(0, end);
						rs.setIdForSite(href);
					}
				}
			}
			list.add(rs);
		}

		return list;
	}

	private String generateMovieUrl(final String id) {
		//http://www.cinebel.be/nl/film/102-.htm
		return CINEBEL.getUrl() + PREFIX + id + "/";
	}

	private String generateSearchUrl(final String title, final int maxResults) {
		String encoded;
		try {
			encoded = URLEncoder.encode(title, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("utf-8 encoding not supported", e);
		}
		//String url = MovieService.CINEBEL.getUrl()+"/portal/faces/public/exo/search?portal:componentId=SearchContentPortlet&portal:type=render&portal:isSecure=false&lng="+LANG+"&query="+encoded+"&itemsPerPage="+maxResults+"&fuzzy=true&fieldToSearch=movieTitle&category=movie&movieWithSchedules=false";
		String url = CINEBEL.getUrl() + "/nl/zoek?query=" + encoded + "&x=13&y=13";
		return url;
	}
	
	@Override
	public MovieService getService() {
	    return CINEBEL;
	}

}
