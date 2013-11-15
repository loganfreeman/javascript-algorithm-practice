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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.Parser;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieType;
import com.flicklib.tools.AdvancedTextExtractor;
import com.flicklib.tools.ElementOnlyTextExtractor;
import com.flicklib.tools.SimpleXPath;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 * @author francisdb
 */
@Singleton
public class ImdbParser implements Parser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImdbParser.class);
    private final Map<String, MovieType> movieTypeMap = new HashMap<String, MovieType>();
    {
        movieTypeMap.put("video", MovieType.VIDEO_MOVIE);
    }

    @Inject
    public ImdbParser() {
    }

    @Override
    public final void parse(com.flicklib.service.Source htmlSource, MoviePage movieSite) {
        parse(htmlSource.getContent(), htmlSource.getJerichoSource(), movieSite);
    }

    private void parse(final String html, Source source, MoviePage movie) {

        ImdbParserRegex regexParser = new ImdbParserRegex(html);

        movie.setType(regexParser.getType());
        Element titleHeader = (Element) source.getAllElements(HTMLElementName.H1).get(0);
        String title = new ElementOnlyTextExtractor(titleHeader.getContent()).toString();
        title = ImdbParserRegex.cleanTitle(title);
        movie.setTitle(title);

        List<Element> extraTitle = titleHeader.getAllElements("class", "title-extra", false);
        if (!extraTitle.isEmpty()) {
            AdvancedTextExtractor at = new AdvancedTextExtractor(extraTitle.get(0), false).addExcludedTagName("i");
            movie.setOriginalTitle(at.toString());
        }

        List<?> yearLinks = titleHeader.getAllElements(HTMLElementName.A);
        if (yearLinks.size() > 0) {
            Element yearLink = (Element) yearLinks.get(0);
            String year = yearLink.getContent().getTextExtractor().toString();
            setYear(movie, year);
        } else {
            List<Element> spans = titleHeader.getAllElements(HTMLElementName.SPAN);
            Pattern p = Pattern.compile("\\((\\D*)(\\d+)\\)");
            for (Element span : spans) {
                String txt = span.getTextExtractor().toString();
                Matcher m = p.matcher(txt);
                if (m.matches()) {
                    // (Video XYZ)
                    String type = m.group(1).trim().toLowerCase();
                    MovieType mType = movieTypeMap.get(type);
                    String year = m.group(2);
                    LOGGER.info("found element with year :" + txt + " -> " + year + " type:" + type + ", " + mType);
                    if (mType != null) {
                        movie.setType(mType);
                    }
                    setYear(movie, year);
                }
            }
        }

        List<?> linkElements = source.getAllElements(HTMLElementName.A);
        for (Iterator<?> i = linkElements.iterator(); i.hasNext();) {
            Element linkElement = (Element) i.next();
            if ("poster".equals(linkElement.getAttributeValue("name"))) {
                // A element can contain other tags so need to extract the text from it:
                List<?> imgs = linkElement.getContent().getAllElements(HTMLElementName.IMG);
                Element img = (Element) imgs.get(0);
                String imgUrl = img.getAttributeValue("src");
                movie.setImgUrl(imgUrl);
            }
            String href = linkElement.getAttributeValue("href");
            final String linkContent = linkElement.getContent().getTextExtractor().toString();
            if (href != null && (href.contains("/Sections/Genres/") || (href.contains("/genre/")))) {
                String genre = linkContent;
                // TODO find a better way to parse these out, make sure it are only the movie genres
                if (!genre.toLowerCase().contains("imdb")) {
                    movie.addGenre(linkContent);
                }
            }
            if (href != null && href.contains("/Sections/Languages/")) {
                movie.addLanguage(linkContent);
            }
            
            //<a href="/name/nm0000206/" onclick="(new Image()).src='/rg/castlist/position-1/images/b.gif?link=/name/nm0000206/';">Keanu Reeves</a>
            String onclick = linkElement.getAttributeValue("onclick");
            if (onclick != null) {
                if(onclick.contains("castlist")){
                	 movie.getActors().add(linkContent);
                }
            //<a href="/name/nm0905154/" onclick="(new Image()).src='/rg/directorlist/position-2/images/b.gif?link=name/nm0905154/';">Larry Wachowski</a><br/> 
                if (onclick.contains("directorlist")) {
                    movie.getDirectors().add(linkContent);
                }
            }
            String itemprop = linkElement.getAttributeValue("itemprop");
            if (itemprop != null) {
                if ("director".equals(itemprop)) {
                    movie.getDirectors().add(linkContent);
                }
                if ("actors".equals(itemprop)) {
                    movie.getActors().add(linkContent);
                }
            }
        }

        linkElements = source.getAllElements(HTMLElementName.B);
        for (Iterator<?> i = linkElements.iterator(); i.hasNext();) {
            Element bElement = (Element) i.next();
            if (bElement.getContent().getTextExtractor().toString().contains("User Rating:")) {
                Element next = source.getNextElement(bElement.getEndTag().getEnd());
                String rating = next.getContent().getTextExtractor().toString();
                // skip (awaiting 5 votes)
                if (!rating.contains("awaiting")) {
                    parseRatingString(movie, rating);
                    next = source.getNextElement(next.getEndTag().getEnd());
                    parseVotes(movie, next);
                }
            }
        }
        
        if (movie.getScore() == null) {
        	List<Element> elements = source.getAllElements("class","starbar-meta", false);
        	for (Element e : elements) {
        		List<Element> boldElements = e.getAllElements(HTMLElementName.B);
        		if (boldElements.size() > 0) {
        			String rating = boldElements.get(0).getTextExtractor().toString();
        			if (!rating.contains("awaiting")) {
                        parseRatingString(movie, rating);
        			}
        		}
        		List<Element> aElements = e.getAllElements(HTMLElementName.A);
        		if (aElements.size() > 0) {
        			String votes= aElements.get(0).getTextExtractor().toString();
        			if (votes.contains("votes")) {
        				parseVotes(movie, aElements.get(0));
        			}
        		}
        		
        	}
        }
        if (movie.getScore() == null) {
            for (Element element : source.getAllElements("class", "star-box-details", false)) {
                for (Element span : element.getAllElements("span")) {
                    String itemprop = span.getAttributeValue("itemprop");
                    if ("ratingValue".equals(itemprop)) {
                        String value = span.getTextExtractor().toString();
                        parseRatingString(movie, value);
                    }
                    if ("ratingCount".equals(itemprop)) {
                        String value = span.getTextExtractor().toString().replace(",", "").trim();
                        if (value.length() > 0) {
                            movie.setVotes(Integer.parseInt(value));
                        }
                    }
                }
            }
        }
        

        linkElements = source.getAllElements(HTMLElementName.H5);
        String hText;
        for (Iterator<?> i = linkElements.iterator(); i.hasNext();) {
            Element hElement = (Element) i.next();
            hText = hElement.getContent().getTextExtractor().toString();
            int end = hElement.getEnd();
            if (hText.contains("Plot Outline")) {
                movie.setPlot(source.subSequence(end, source.getNextStartTag(end).getBegin()).toString().trim());
            } else if (hText.contains("Plot:")) {
            	Element divElement = source.getNextElement(end);
            	end = divElement.getStartTag().getEnd();
                movie.setPlot(source.subSequence(end, source.getNextStartTag(end).getBegin()).toString().trim());
            } else if (hText.contains("Runtime")) {

            	Element divElement = source.getNextElement(end);
                String runtime = divElement.getTextExtractor().toString();

//                EndTag next = source.getNextEndTag(end);
//                //System.out.println(next);
//                StartTag nextStartTag = source.getNextStartTag(end);
//                String runtime;
//                if (nextStartTag.getBegin() < next.getBegin()) {
//                    // There is an extra div tag : <div class="info-content">
//                    runtime = source.subSequence(nextStartTag.getEnd(), next.getBegin()).toString().trim();
//                } else {
//                    runtime = source.subSequence(end, next.getBegin()).toString().trim();
//                }

                movie.setRuntime(parseRuntime(runtime));
            } else if (hText.contains("User Rating")) {
                Element aElement = source.getNextElement(end);
                List<Element> boldOnes = aElement.getAllElements(HTMLElementName.B);
                if (boldOnes.size()>0) {
                    Element element = boldOnes.get(0);
                    String rating = element.getTextExtractor().toString();
                    if (!rating.contains("awaiting")) {
                        parseRatingString(movie, rating);
                        Element next = source.getNextElement(element.getEndTag().getEnd());
                        parseVotes(movie, next);
                    }
                }
            } /*else if (hText.contains("Genre")) {
                
            }*/
            		
        }
        if (movie.getPlot() == null) {
            List<Element> descriptions = source.getAllElements("itemprop", "description", false);
            for (Element desc : descriptions) {
                String txt = desc.getTextExtractor().toString().trim();
                if (txt.length() > 0) {
                    movie.setPlot(txt);
                }
            }
            
        }
        if (movie.getTitle() == null) {
            //System.out.println(source.toString());
            movie.setPlot("Not found");
        }
        if (movie.getImgUrl() == null) {
            for (Element e : new SimpleXPath(source.getElementById("img_primary")).getAllTagByAttributes("itemprop", "image")) {
                final String src = e.getAttributeValue("src");
                if (src != null) {
                    LOGGER.info("found image : " + src);
                    movie.setImgUrl(src);
                }
            }
        }        
    }

    private void setYear(MoviePage movie, String year) {
        try {
            movie.setYear(Integer.valueOf(year));
        } catch (NumberFormatException ex) {
            LOGGER.error("Could not parse year '" + year + "' to integer", ex);
        }
    }

    private void parseVotes(MoviePage movieSite, Element element) {
        String votes = element.getContent().getTextExtractor().toString();

        votes = votes.replaceAll("\\(", "");
        votes = votes.replaceAll("votes(\\))*", "");
        votes = votes.replaceAll(",", "");
        votes = votes.trim();
        try {
            movieSite.setVotes(Integer.valueOf(votes));
        } catch (NumberFormatException ex) {
            LOGGER.error("Could not parse the votes '" + votes + "' to Integer", ex);
        }
    }

    private void parseRatingString(MoviePage movieSite, String rating) {
        // to percentage
        rating = rating.replace("/10", "");
        try {
            int theScore = Math.round(Float.valueOf(rating).floatValue() * 10);
            movieSite.setScore(theScore);
        } catch (NumberFormatException ex) {
            LOGGER.error("Could not parse rating '" + rating + "' to Float", ex);
        }
    }

    private Integer parseRuntime(String runtimeString) {
        String runtime = runtimeString.substring(0, runtimeString.indexOf("min")).trim();
        int colonIndex = runtime.indexOf(":");
        if (colonIndex != -1) {
            runtime = runtime.substring(colonIndex + 1);
        }

        return Integer.valueOf(runtime);
    }
}
