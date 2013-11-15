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
package com.flicklib.service.movie.movieweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.flicklib.tools.Param;
import com.flicklib.tools.SimpleXPath;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 * @author francisdb
 */
@Singleton
public class MovieWebInfoFetcher extends AbstractMovieInfoFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieWebInfoFetcher.class);
    /**
     * http://www.movieweb.com
     */
    private final static MovieService MOVIEWEB = new MovieService("MOVIEWEB", "MovieWeb", "http://www.movieweb.com", "MWeb");
    
    private final Parser parser;
    private final SourceLoader sourceLoader;

    /**
     * Creates a new MovieWebInfoFetcher
     * @param movieWebInfoParser
     * @param httpLoader 
     */
    @Inject
    public MovieWebInfoFetcher(final @MovieWeb Parser movieWebInfoParser, final SourceLoader httpLoader) {
        this.parser = movieWebInfoParser;
        this.sourceLoader = httpLoader;
    }
    
    public MovieWebInfoFetcher(SourceLoader loader) {
        this(new MovieWebParser(), loader);
    }

    @Override
	public MoviePage getMovieInfo(String id) throws IOException {
		MoviePage site = null;
		if (id.startsWith(MOVIEWEB.getUrl())) {
			com.flicklib.service.Source source = sourceLoader.loadSource(id);
			site = new MoviePage(MOVIEWEB);
			site.setIdForSite(id);
			site.setUrl(id);
			parser.parse(source, site);
		}else{
			throw new IOException("Trying to get movie info for MovieWeb but the supplied id is not a movieweb id!");			
		}
		return site;
	}
    
    @Override
    public List<MovieSearchResult> search(String title) throws IOException {
        List<MovieSearchResult> result = new ArrayList<MovieSearchResult>();
        String urlToLoad = createMovieWebSearchUrl(title);

        com.flicklib.service.Source source = sourceLoader.loadSource(urlToLoad);
        Source jerichoSource = source.getJerichoSource();

        String movieUrl = null;
        SimpleXPath searchResult = new SimpleXPath(jerichoSource.getElementById("search_results"));

        /*List<Element> imgList = searchResult.getAllTagByAttributes("class", "img").filterTagName(HTMLElementName.DIV).getTags(HTMLElementName.A).toList();
        Map<String, String> imgMap = new HashMap<String, String>();
        for (Element e : imgList) {
        	String dvdPath = e.getAttributeValue("href");
        	Element imgElement = e.getFirstElement(HTMLElementName.IMG);
        	if (imgElement != null) {
        		String src = imgElement.getAttributeValue("src");
        		if (src != null) {
        			imgMap.put(dvdPath, src);
        		}
        	}
        }*/
        
        for (Element aElement : searchResult.getTags(HTMLElementName.H2).getTags(HTMLElementName.A)) {
            String url = aElement.getAttributeValue("href");
            if (url != null && (url.startsWith("/movie/") || url.startsWith("/dvd/"))) {
                String movieName = aElement.getContent().getTextExtractor().toString();
                if (movieName != null && movieName.trim().length() != 0) {
                    // String imgUrl = imgMap.get(url);
                    movieUrl = "http://www.movieweb.com" + url;

                    MovieSearchResult m = new MovieSearchResult();
                    m.setIdForSite(movieUrl);
                    m.setTitle(movieName);
                    m.setService(MOVIEWEB);
                    m.setUrl(movieUrl);
                    //m.setImgUrl(imgUrl);
                    result.add(m);
                    
                    LOGGER.debug("found title: " + movieName + " -> " + movieUrl);
                }
            }
        }

        return result;
    }
    
    
    @Deprecated
    public MoviePage fetch(Movie movie, String id) {
        MoviePage site = new MoviePage();
        //site.setMovie(movie);
        site.setService(MOVIEWEB);
        String urlToLoad = createMovieWebSearchUrl(movie.getTitle());
        try {
            com.flicklib.service.Source source = sourceLoader.loadSource(urlToLoad);
            Source jerichoSource = source.getJerichoSource();
            //source.setLogWriter(new OutputStreamWriter(System.err)); // send log messages to stderr

            //Element titleElement = (Element)source.getAllElements(HTMLElementName.TITLE).get(0);
            //System.out.println(titleElement.getContent().extractText());

            // <div id="bubble_allCritics" class="percentBubble" style="display:none;">     57%    </div>

            String movieUrl = null;
            List<?> aElements = jerichoSource.getAllElements(HTMLElementName.A);
            for (Iterator<?> i = aElements.iterator(); i.hasNext();) {
                Element aElement = (Element) i.next();
                String url = aElement.getAttributeValue("href");
                if (url != null && url.endsWith("summary.php")) {
                    String movieName = aElement.getContent().getTextExtractor().toString();
                    if (movieUrl == null && movieName != null && movieName.trim().length() != 0) {

                        movieUrl = "http://www.movieweb.com" + url;
                        LOGGER.info("taking first result: " + movieName + " -> " + movieUrl);
                    }
                }
            }
            if (movieUrl == null) {
                LOGGER.warn("Movie not found on MovieWeb: "+movie.getTitle());
            }else{
                site.setUrl(movieUrl);
                source = sourceLoader.loadSource(movieUrl);
                parser.parse(source, site);
            }
        } catch (IOException ex) {
            LOGGER.error("Loading from MovieWeb failed: "+urlToLoad, ex);
        }
        return site;
    }

    private String createMovieWebSearchUrl(String title) {
        return "http://www.movieweb.com/search" + Param.paramString("search", title);
    }
    
    @Override
    public MovieService getService() {
        return MOVIEWEB;
    }

}
