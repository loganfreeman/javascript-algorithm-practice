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
package com.flicklib.service.movie.ofdb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.api.Parser;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;
import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author francisdb
 *
 */
@Singleton
public class OfdbFetcher extends AbstractMovieInfoFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfdbFetcher.class);
    /**
     * http://www.ofdb.de
     */
    private final static MovieService OFDB =  new MovieService("OFDB", "Online-Filmdatenbank", "http://www.ofdb.de", "OFDb");
    
    private final SourceLoader sourceLoader;
    private final Parser parser;
    
    @Inject
    public OfdbFetcher(final SourceLoader sourceLoader, @Ofdb final Parser parser) {
		this.sourceLoader = sourceLoader;
		this.parser = parser;
	}
    
    public OfdbFetcher(final SourceLoader loader) {
        this(loader, new OfdbParser());
    }
    
	@Override
	public MoviePage getMovieInfo(String idForSite) throws IOException {
		
		String url = generateMovieUrl(idForSite);
		LOGGER.info("getMovieInfo " + idForSite + " from " + url);
		Source source = sourceLoader.loadSource(url);
		if (source != null) {
			MoviePage page = new MoviePage(OFDB);
			page.setIdForSite(idForSite);
			page.setUrl(url);
			parser.parse(source, page);
			return page; 
		}
		LOGGER.warn("no response returned from "+url);
		return null;
		
	}

	@Override
	public List<? extends MovieSearchResult> search(String title) throws IOException {
		List<MovieSearchResult> list = new ArrayList<MovieSearchResult>();
		String url = generateSearchUrl(title);
		Source source = sourceLoader.loadSource(url);
		net.htmlparser.jericho.Source jerichoSource = source.getJerichoSource();
        
    	// find all links
    	//<a href="film/1050,Pulp-Fiction" onmouseover="....">Pulp Fiction<font size="1"> / Pulp Fiction</font> (1994)</a>
        //<a href="film/33740,Pulp-Fiction-The-Facts" onmouseover="...">Pulp Fiction: The Facts [Kurzfilm]<font size="1"> / Pulp Fiction: The Facts</font> (2002)</a>
        List<Element> linkElements = jerichoSource.getAllElements(HTMLElementName.A);
    	for(Element linkElement: linkElements){
	        String href = linkElement.getAttributeValue("href");
	        if (href.startsWith("film/")) {
	        	MovieSearchResult movieSite = new MovieSearchResult();
	        	movieSite.setService(OFDB);
		        String movieTitles = linkElement.getContent().getTextExtractor().toString();
		        String[] titles = movieTitles.split(Pattern.quote("/"));
		        String germanTitle = titles[0].trim();
		        String theTitle = OfdbTools.handleType(germanTitle, movieSite);
		        movieSite.setTitle(theTitle);
	        	if(titles.length > 1){
	        		String originalTitleYear = titles[1].trim();
	        		theTitle = OfdbTools.handleYear(originalTitleYear, movieSite);
	        		movieSite.setOriginalTitle(theTitle);
	        	}
	        	movieSite.setUrl(OFDB.getUrl() + href);
	        	String id = href.substring("film/".length());
	        	movieSite.setIdForSite(id);
	        	list.add(movieSite);
	        }
    	}

		
        return list;
	}
	
	
	
	private String generateMovieUrl(final String id){
		// http://www.ofdb.de/film/1050,Pulp-Fiction
		String encoded;
		try {
			encoded = URLEncoder.encode(id, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("utf-8 encoding not supported" ,e);
		}
		return OFDB.getUrl()+"/film/"+encoded;
	}

	private String generateSearchUrl(final String title) {
		String encoded;
		try {
			encoded = URLEncoder.encode(title, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("utf-8 encoding not supported" ,e);
		}
		String url = OFDB.getUrl()+"/view.php?page=suchergebnis&Kat=Titel&SText="+encoded;
		return url;
	}
	
	@Override
	public MovieService getService() {
	    return OFDB;
	}

}
