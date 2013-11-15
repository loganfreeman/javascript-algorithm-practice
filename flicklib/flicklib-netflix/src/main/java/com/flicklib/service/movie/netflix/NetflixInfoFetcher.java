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
package com.flicklib.service.movie.netflix;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.HttpClientPool;
import net.oauth.client.OAuthClient;
import net.oauth.client.OAuthHttpClient;

import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * @author francisdb
 * 
 */
@Singleton
public class NetflixInfoFetcher extends AbstractMovieInfoFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetflixInfoFetcher.class);
    /**
     * http://www.netflix.com
     */
    final static MovieService NETFLIX =  new MovieService("NETFLIX", "Netflix", "http://www.netflix.com");
    
    private final OAuthAccessor accessor;

    @Inject
    public NetflixInfoFetcher(@Named("netflix.key") final String apikey, @Named("netflix.secret") final String sharedsecret) {
        OAuthServiceProvider provider = new OAuthServiceProvider(
        		"http://api.netflix.com/oauth/request_token", 
        		"https://api-user.netflix.com/oauth/login",
                "http://api.netflix.com/oauth/access_token");
        OAuthConsumer consumer = new OAuthConsumer(null, apikey, sharedsecret, provider);
        accessor = new OAuthAccessor(consumer);
    }

    @Override
    public MoviePage getMovieInfo(String id) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<? extends MovieSearchResult> search(String title) throws IOException {
    	List<? extends MovieSearchResult> result = null;
        OAuthClient oAuthClient = new OAuthHttpClient(new NotPoolingHttpClientPool());
        Map<String, String> params = new HashMap<String, String>();
        params.put("term", title);
        params.put("max_results", String.valueOf(20));
        try {
            OAuthMessage message = oAuthClient.invoke(accessor, "GET", "http://api.netflix.com/catalog/titles", params.entrySet());
            // LOGGER.info(message.getBodyAsString());

            SaxResultUnmarshaller saxUms = new SaxResultUnmarshaller();

            XMLReader rdr = XMLReaderFactory.createXMLReader();
            rdr.setContentHandler(saxUms);
            rdr.parse(new InputSource(message.getBodyAsStream()));
            result = saxUms.getResult();
        } catch (OAuthException e) {
            throw new IOException("Problem while requesting data from netflix", e);
        } catch (URISyntaxException e) {
        	throw new IOException("Problem while requesting data from netflix", e);
        } catch (SAXException e) {
        	throw new IOException("Problem while requesting data from netflix", e);
        }
        return result;
    }


    /**
     * TODO Should be removed when switching to new OAuth version (is
     * implemented overthere)
     * 
     * @author francisdb
     * 
     */
    private static final class NotPoolingHttpClientPool implements HttpClientPool {
        @Override
        public HttpClient getHttpClient(URL server) {
            return new HttpClient();
        }
    }

    /**
     * Parses a single netflix xml result
     * 
     * @author francisdb
     * 
     */
    private static class SaxResultUnmarshaller extends DefaultHandler {

        /**
         * Rating in %
         */
        private MoviePage moviePage;
        private String tagToReadContentFrom;

        private final List<MoviePage> result;

        public SaxResultUnmarshaller() {
        	result = new ArrayList<MoviePage>();
        	moviePage = new MoviePage(NETFLIX);
        }
        
        public List<MoviePage> getResult() {
			return result;
		}

        @Override
        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
            LOGGER.debug("element <"+name+" "+toString(attributes)+">");
            if ("catalog_title".equals(name)) {
                moviePage = new MoviePage(NETFLIX);
            }
            if ("id".equals(name)) {
                tagToReadContentFrom = name;
            } else if ("link".equals(name)) {
                String rel = attributes.getValue("rel");
                if ("alternate".equals(rel)) {
                    moviePage.setUrl(attributes.getValue("href"));
                }
            } else if ("average_rating".equals(name)) {
                tagToReadContentFrom = name;
            } else if ("box_art".equals(name)) {
                moviePage.setImgUrl(attributes.getValue("large"));
            } else if ("release_year".equals(name)) {
                tagToReadContentFrom = name;
            } else if ("title".equals(name)) {
                moviePage.setTitle(attributes.getValue("regular"));
            } else if ("category".equals(name)) {
                String scheme = attributes.getValue("scheme");
                if ("http://api.netflix.com/categories/genres".equals(scheme)) {
                    moviePage.addGenre(attributes.getValue("label"));
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (tagToReadContentFrom != null) {
                String txt = new String(ch, start, length);
                if ("average_rating".equals(tagToReadContentFrom)) {
                    moviePage.setScore((int) (Double.valueOf(txt) * 20));
                } else if ("id".equals(tagToReadContentFrom)) {
                    moviePage.setIdForSite(txt);
                } else if ("release_year".equals(tagToReadContentFrom)) {
                    moviePage.setYear(Integer.parseInt(txt));
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String name) throws SAXException {
            if ("catalog_title".equals(name)) {
                result.add(moviePage);
                moviePage = null;
            }
            tagToReadContentFrom = null;
        }
        
        String toString(Attributes attributes) {
            StringBuilder s = new StringBuilder();
            for (int i=0;i<attributes.getLength();i++) {
                s.append(attributes.getLocalName(i)).append("='").append(attributes.getValue(i)).append("' ");
            }
            return s.toString();
        }
    }

    @Override
    public MovieService getService() {
        return NETFLIX;
    }
}
