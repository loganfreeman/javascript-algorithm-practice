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
package com.flicklib.service.movie.imdb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.Parser;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.SourceLoader;
import com.flicklib.tools.ElementOnlyTextExtractor;
import com.google.inject.Inject;

/**
 *
 * @author francisdb
 */
public class ImdbSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImdbSearch.class);
    private final SourceLoader sourceLoader;
    private final Parser imdbParser;

    @Inject
    public ImdbSearch(final SourceLoader sourceLoader, final @Imdb Parser imdbParser) {
        this.sourceLoader = sourceLoader;
        this.imdbParser = imdbParser;
    }

    private List<MovieSearchResult> parseResults(com.flicklib.service.Source source) throws IOException{
        Source jerichoSource = source.getJerichoSource();

        List<MovieSearchResult> results = new ArrayList<MovieSearchResult>();
        Element titleElement = (Element) jerichoSource.getAllElements(HTMLElementName.TITLE).get(0);
        String title = titleElement.getContent().getTextExtractor().toString();
        if (title.contains("IMDb") && title.contains("Search")) {
            LOGGER.info("Search results returned");
            List<?> tableElements = jerichoSource.getAllElements(HTMLElementName.TD);
            Element tableElement;
            Iterator<?> j = tableElements.iterator();
            while(j.hasNext()) {
                tableElement = (Element) j.next();
                String tdContents = tableElement.getTextExtractor().toString();
                List<?> linkElements = tableElement.getAllElements(HTMLElementName.A);
                Element linkElement;
                MovieSearchResult movieSite;
                Iterator<?> i = linkElements.iterator();
                Set<String> ids = new HashSet<String>();
                while ((i.hasNext()) && (!tableElement.getTextExtractor().toString().startsWith("Media from")) && (!tableElement.getTextExtractor().toString().startsWith(" ")) && (!tableElement.getTextExtractor().toString().endsWith("Update your search preferences.")) && (!tableElement.getTextExtractor().toString().endsWith("...)"))) {
                    movieSite = new MovieSearchResult();
                    movieSite.setService(ImdbInfoFetcher.IMDB);
                    //movieSite.setMovie(movie);
                    linkElement = (Element) i.next();
                    String href = linkElement.getAttributeValue("href");
                    if (href != null && href.startsWith("/title/tt")) {
                        int questionMarkIndex = href.indexOf('?');
                        if (questionMarkIndex != -1) {
                            href = href.substring(0, questionMarkIndex);
                        }
                        movieSite.setUrl("http://www.imdb.com" + href);
                        movieSite.setIdForSite(href.replaceAll("[a-zA-Z:/.+=?]", "").trim());
                        movieSite.setType(ImdbParserRegex.getType(tdContents, false));
                        title = linkElement.getTextExtractor().toString();
                        title = ImdbParserRegex.cleanTitle(title);
                        movieSite.setTitle(title);
                        ElementOnlyTextExtractor extractor = new ElementOnlyTextExtractor(tableElement.getContent());
                        String titleYear = extractor.toString().trim();
                        if (titleYear.length() > 0 && titleYear.contains(")")) {
                            int start = titleYear.indexOf("(");
                            int end = titleYear.indexOf(")");
                            String year = titleYear.substring(start+1, end);
                            // get rid of the /I in for example "1998/I"
                            int slashIndex = year.indexOf('/');
                            if(slashIndex != -1){
                                year = year.substring(0, slashIndex);
                            }
                            try {
                                movieSite.setYear(Integer.valueOf(year));
                            } catch (NumberFormatException ex) {
                                LOGGER.warn("Could not parse '" + year + "' to integer");
                            }
                        }
                        
//                        List<Element> emElements = tableElement.getAllElements("em");
//                        if (emElements!=null && emElements.size() >= 2) {
//                            // first contains the aka title, second (DVD title)
//                            String alternateTitle = ((Element)tableElement.getAllElements("em").get(0)).getTextExtractor().toString();
//                            movieSite.setAlternateTitle(ImdbParserRegex.cleanTitle(alternateTitle));
//                        }
                        findOriginalTitleFromAkaList(tableElement, movieSite);

                        // only add if not allready in the list
                        if (movieSite.getTitle().length() > 0 && !ids.contains(movieSite.getIdForSite())) {
                            ids.add(movieSite.getIdForSite());
                            results.add(movieSite);
                        }
                    }
                }
            }
            
        }else{
            LOGGER.info("Exact match returned");
            MoviePage result = new MoviePage(ImdbInfoFetcher.IMDB);
            
            // FIXME, there should be a way to know at what url whe ended up (better way to parse imdb id)
            
            //Assume it's a perfect result, therefore get first /title/tt link.
            List<?> linkElements = jerichoSource.getAllElements(HTMLElementName.A);
            Element linkElement;
            Iterator<?> i = linkElements.iterator();
            Set<String> ids = new HashSet<String>();
            while (i.hasNext()) {
                
                linkElement = (Element) i.next();
                String href = linkElement.getAttributeValue("href");
                if (href != null && href.startsWith("/title/tt")) {
                    int questionMarkIndex = href.indexOf('?');
                    if (questionMarkIndex != -1) {
                        href = href.substring(0, questionMarkIndex);
                    }
                    //href has to be split as href will be in from of /title/tt#######/some-other-dir-like-trailers
                    String[] split = href.split("/");
                    href = "/" + split[1] + "/" + split[2];
                    MoviePage movieSite = new MoviePage(ImdbInfoFetcher.IMDB);
                    //movieSite.setMovie(movie);
                    movieSite.setUrl("http://www.imdb.com" + href);
                    movieSite.setIdForSite(href.replaceAll("[a-zA-Z:/.+=?]", "").trim());
                    //set title as the movies title since this is a perfect search result who's HTMLElementName.title will be the movie title.
                    
                    movieSite.setTitle(title);
                    // only add if not allready in the list
                    if (movieSite.getTitle().length() > 0 && !ids.contains(movieSite.getIdForSite())) {
                        //Only add to the set and results list if they are empty, i.e. only if the first result, since perfect result assumed. The rest of the identified links will be the IMDB recommendations for the particular perfect result.
                        if(ids.isEmpty() && results.isEmpty()) {
                            ids.add(movieSite.getIdForSite());
                            result = movieSite;
                        }
                    }
                }
            }

            imdbParser.parse(source, result);
            results.add(result);
        }
        return results;

    }

    private void findOriginalTitleFromAkaList(Element parentElement, MovieSearchResult object) {
        Pattern pattern = Pattern.compile("aka \"(.*)\" - .*");
        List<String> akaNames = new ArrayList<String>();
        for (Element p : parentElement.getAllElements("class", "find-aka", false)) {
            if ("p".equals(p.getName())) {
                String text = p.getTextExtractor().toString();
                if (text.contains("(original title)")) {
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.find()) {
                        LOGGER.info("find the original title : '" + matcher.group(1) + "'");
                        object.setOriginalTitle(matcher.group(1));
                    }
                } else if (text.contains("(imdb display title)")) {
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.find()) {
                        LOGGER.info("imdb display title is : '" + matcher.group(1) + "' ");
                        akaNames.add(matcher.group(1));
                    }
                } else {
                    // String text = new AdvancedTextExtractor(p,
                    // false).addExcludedTagName("em").addAllowedTagName("p").toString();
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.find()) {
                        LOGGER.info("alternate title is : '" + matcher.group(1) + "' ");
                        akaNames.add(matcher.group(1));
                    }
                }
            }
        }
        if (akaNames.size() > 0) {
            // TODO : handle multiple alternate title
            object.setAlternateTitle(akaNames.get(0));
        }
    }

    /**
     * Performs a search on imdb
     * @param search
     * @return the results found as List of MoviePage
     * @throws java.io.IOException
     */
    List<MovieSearchResult> getResults(String search) throws IOException {
        String url = generateImdbTitleSearchUrl(search);
        LOGGER.info(url);
        com.flicklib.service.Source source = sourceLoader.loadSource(url);
        return parseResults(source);
    }

    /**
     * @param title 
     * @return the imdb url
     */
    String generateImdbTitleSearchUrl(String title) {
        String encoded = "";
        try {
            encoded = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error("Could not cencode UTF-8", ex);
        }
        // global search is without s=tt
        return "http://www.imdb.com/find?q="+encoded+";s=tt;site=aka";
    }
}
