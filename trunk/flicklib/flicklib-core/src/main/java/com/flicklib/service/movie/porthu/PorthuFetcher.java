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
package com.flicklib.service.movie.porthu;

import static com.flicklib.tools.StringUtils.unbracket;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;
import com.flicklib.service.SourceLoader;
import com.flicklib.tools.ElementOnlyTextExtractor;
import com.flicklib.tools.LevenshteinDistance;
import com.flicklib.tools.SimpleXPath;
import com.flicklib.tools.StringUtils;
import com.google.inject.Inject;


public class PorthuFetcher extends AbstractMovieInfoFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PorthuFetcher.class);
    /**
     * http://www.port.hu
     */
    private final static MovieService PORTHU = new MovieService("PORTHU", "Port.hu", "http://www.port.hu");

    private static final String FILM_INFO_URL = "/pls/fi/films.film_page";
    private static final String TEST_CITY_ID = "3372";

    private static final Pattern FILM_ID_PATTERN = Pattern.compile("i_film_id=(\\d*)");

    private static final Pattern YEAR_END_PATTERN = Pattern.compile("(\\d+)\\z");

    private static final Pattern RUNTIME_PATTERN = Pattern.compile("(\\d+) perc");

    /**
     * match (x.y)/10 where x and y are numbers.
     */
    private static final Pattern SCORE_PATTERN = Pattern.compile("(\\d+([\\.,]\\d)?)/10");

    private final SourceLoader sourceLoader;

    @Inject
    public PorthuFetcher(final SourceLoader sourceLoader) {
        this.sourceLoader = sourceLoader;
    }

    @Override
    public MoviePage getMovieInfo(String id) throws IOException {

        String url = generateUrlForFilmID(id);
        Source source = sourceLoader.loadSource(url).getJerichoSource();

        MoviePage mp = parseMovieInfoPage(source, id);
        mp.setUrl(url);
        return mp;
    }

    private MoviePage parseMovieInfoPage(Source source, String id) throws IOException {
        MoviePage mp = new MoviePage(PORTHU);
        mp.setIdForSite(id);
        {
            List<Element> titleElements = source.getAllElements("class", "blackbigtitle", false);
            if (titleElements.size() == 0) {
                throw new RuntimeException("no <span class='blackbigtitle'> found!");
            }
            Element titleElement = titleElements.get(0);

            setEnglishAndOriginalTitle(mp, getOriginalAndEnglishTitle(titleElement), titleElement.getContent().getTextExtractor().toString());
        }
        {
            SimpleXPath xp = new SimpleXPath(source.getAllElements("td")).filter("width", "98%").children().filter("class", "txt").filterTagName("span");
            if (xp.size()>0) {
                String content = xp.get(0).getContent().getTextExtractor().toString();
                initDescriptionAndYear(content, mp);
            }
            
            List<Element> spanElements = source.getAllElements("span");
            for (int i = 0; i < spanElements.size(); i++) {
                Element span = spanElements.get(i);
                if ("btxt".equals(span.getAttributeValue("class"))) {
                    String content = span.getTextExtractor().toString();
                    if (content.trim().equals("rendező:")) {
                        // the next span contains the name of the director
                        Element nextSpan = spanElements.get(i + 1);
                        LOGGER.debug("director is : " + nextSpan);
                        mp.getDirectors().add(nextSpan.getTextExtractor().toString());
                    }
                    // forgatókönyvíró:
                    // zeneszerző:
                    if (content.startsWith("A film értékelése:")) {
                        mp.setScore(getScore(content));
                        // the next span contains the vote count
                        Element nextSpan = spanElements.get(i + 1);
                        LOGGER.debug("vote count : " + nextSpan);
                        mp.setVotes(getVotes(nextSpan.getTextExtractor().toString()));
                    }
                }
            }
            
            if (mp.getScore()==null) {
                // we have to fetch with ajax
                parseAjaxVoteObjectResponse(mp, id);
            }
        }

        mp.setPlot(getPlot(source));
        return mp;
    }

    private String getPlot(Source source) {
        SimpleXPath xp = new SimpleXPath(source.getAllElements("table"));
        
        xp = xp.filter("id", null).filter("width","100%").filter("cellpadding", "0").filter("cellspacing", "0");
        xp = xp.children().filterTagName("tr");
        xp = xp.children().filterTagName("td").filter("align","left").filter("valign","top");
        xp = xp.children().filterTagName("span").filter("class","txt");
        if (xp.size()>0) {
            Element element = xp.get(0);
            return element.getContent().getTextExtractor().toString();
        }
/*        System.out.println("XPATH:"+xp.size()+" content:"+xp);
        List<Element> tables = source.getAllElements("table");
        // find a table which doesn't have 'id', it's width='100%'
        // cellpadding="0" cellspacing="0' and it's parent a td
        for (Element table : tables) {
            // 
            if (table.getAttributeValue("id") == null && "100%".equals(table.getAttributeValue("width")) && "0".equals(table.getAttributeValue("cellpadding"))
                    && "0".equals(table.getAttributeValue("cellspacing"))) {
                if ("td".equals(table.getParentElement().getName())) {
                    List<Element> txtElements = table.getAllElements("class", "txt", true);
                    if (txtElements.size() > 0) {
                        return txtElements.get(0).getContent().getTextExtractor().toString();
                    }
                }
            }
        }*/
        return null;
    }

    private String getOriginalAndEnglishTitle(Element titleElement) {
        // // strange, this doesn't works, the span content is always
        // List<Element> alternateTitleSpan =
        // titleElement.getParentElement().findAllElements("class", "txt",
        // false);
        // if (alternateTitleSpan.size()==0) {
        // throw new
        // RuntimeException("no <span class='txt'> found next to the title!");
        // }
        // mp.setAlternateTitle(alternateTitleSpan.get(0).getContent().getTextExtractor().toString());*/
        
        List<Element> allElements = titleElement.getParentElement().getAllElements("class", "txt", false);
        if (allElements.size()>0) {
            String txt = allElements.get(0).getTextExtractor().toString();
            return StringUtils.unbracket(txt);
        }
        
        String fullTag = titleElement.getParentElement().toString();
        // pattern for matching: <span class="txt">(<SOMETHING>)
        Pattern p = Pattern.compile("<span class=\"txt\">\\((.*)\\)");
        Matcher matcher = p.matcher(fullTag);
        if (matcher.find()) {
            if (matcher.groupCount() > 0) {
                LOGGER.debug("match:" + matcher.group() + " -> " + matcher.group(1));
                return matcher.group(1);
            }
        }
        return null;
    }

    @Override
	public List<MovieSearchResult> search(String title) throws IOException {
        List<MovieSearchResult> result = new ArrayList<MovieSearchResult>();
        String url = generateUrlForTitleSearch(title);

        com.flicklib.service.Source flicklibSource = sourceLoader.loadSource(url);
        Source jerichoSource = flicklibSource.getJerichoSource();

        if (isMoviePageUrl(flicklibSource.getUrl())) {
            String id = collectIdFromUrl(flicklibSource.getUrl());
            MoviePage mp = parseMovieInfoPage(jerichoSource, id);
            mp.setUrl(flicklibSource.getUrl());
            result.add(mp);
            return result;
        }
        MovieSearchResultComparator comparator = new MovieSearchResultComparator();
        TreeSet<MovieSearchResult> orderedSet = new TreeSet<MovieSearchResult>(comparator);

        List<Element> spans = jerichoSource.getAllElements("span");
        for (int i = 0; i < spans.size(); i++) {
            Element span = spans.get(i);

            if ("btxt".equals(span.getAttributeValue("class"))) {
                List<Element> childs = span.getContent().getAllElements();
                if (childs.size() > 0) {
                    Element link = childs.get(0);
                    LOGGER.trace("link : " + link);
                    if ("a".equals(link.getName())) {
                        String href = link.getAttributeValue("href");
                        if (href.indexOf(FILM_INFO_URL)!=-1) {
                            LOGGER.info("film url :" + href);
                            String movieTitle = link.getContent().getTextExtractor().toString();
                            MovieSearchResult msr = new MovieSearchResult(PORTHU);
                            msr.setUrl(new java.net.URL(new java.net.URL("http://port.hu"),href).toString());
                            msr.setIdForSite(collectIdFromUrl(href));

                            String basetitle = unbracket(new ElementOnlyTextExtractor(span.getContent()).toString());
                            setEnglishAndOriginalTitle(msr, basetitle, movieTitle);

                            int distance = LevenshteinDistance.distance(movieTitle, title);
                            int distance2 = LevenshteinDistance.distance(msr.getAlternateTitle(), title);
                            LOGGER
                                    .info("found [distance:" + distance + ", alternate distance:" + distance2 + "]:" + movieTitle + '/'
                                            + msr.getAlternateTitle());
                            comparator.set(msr, Math.min(distance, distance2));
                            // next span has style 'txt' and contains the
                            // description

                            if (i + 1 < spans.size()) {
                                Element descSpan = spans.get(i + 1);
                                if ("txt".equals(descSpan.getAttributeValue("class"))) {
                                    String description = descSpan.getContent().getTextExtractor().toString();
                                    initDescriptionAndYear(description, msr);
                                }
                            }

                            orderedSet.add(msr);
                        }
                    }
                }
            }
        }
        result.addAll(orderedSet);
        return result;
    }

    private void setEnglishAndOriginalTitle(MovieSearchResult msr, String basetitle, String alternateTitle) {
        if (basetitle != null) {
            int perPos = basetitle.indexOf('/');
            if (perPos != -1) {
                // original title/english title
                msr.setOriginalTitle(basetitle.substring(0, perPos));
                msr.setTitle(basetitle.substring(perPos + 1));
            } else {
                msr.setTitle(basetitle);
            }
            msr.setAlternateTitle(alternateTitle);
        } else {
            msr.setTitle(unbracket(alternateTitle));
        }
    }

    private static class MovieSearchResultComparator implements Comparator<MovieSearchResult>, Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private final Map<MovieSearchResult, Integer> scoreMap;

        public MovieSearchResultComparator() {
            this.scoreMap = new HashMap<MovieSearchResult, Integer>();
        }

        public void set(MovieSearchResult movie, Integer score) {
            this.scoreMap.put(movie, score);
        }

        @Override
        public int compare(MovieSearchResult o1, MovieSearchResult o2) {
            Integer d1 = scoreMap.get(o1);
            Integer d2 = scoreMap.get(o2);
            int value = safeCompare(d1, d2);
            if (value != 0) {
                return value;
            }
            // both null, or both not-null, but both has the same score.
            value = o1.getTitle().compareTo(o2.getTitle());
            if (value != 0) {
                return value;
            }
            // they have the same title ...
            // sort according to the creation year, newer first.
            value = -safeCompare(o1.getYear(), o2.getYear());
            return value;
        }

        private int safeCompare(Integer d1, Integer d2) {
            if (d1 != null) {
                if (d2 != null) {
                    return d1.compareTo(d2);
                } else {
                    return -1;
                }
            } else {
                if (d2 != null) {
                    return 1;
                }
            }
            return 0;
        }

    }

    /**
     * initialize the description and year field based on a generic, plain
     * description: 'színes magyarul beszélő amerikai gengszterfilm, 171 perc,
     * 1972'
     * 
     * @param description
     * @param msr
     */
    private void initDescriptionAndYear(String description, MovieSearchResult msr) {
        description = unbracket(description);

        
        
        if (msr instanceof MoviePage) {
            MoviePage m = (MoviePage) msr;
            m.setRuntime(getRuntime(description));

            Set<String> skipWordList = new HashSet<String>();
            skipWordList.add("színes");
            skipWordList.add("magyarul");
            skipWordList.add("beszélő");
            skipWordList.add("perc");
            
            // concatenate 'animációs film ' to 'animációsfilm' 
            String[] strings = description.replaceAll(" film[ ,]", "film ").split("[ \\-,]");
            for (String word : strings) {
                if (word.length()>0 && !skipWordList.contains(word)) {
                    try {
                        Integer.parseInt(word);
                        // it's a number, skip
                    } catch (NumberFormatException e) {
                        LOGGER.debug("found genre:"+word);
                        m.addGenre(word);
                    }
                }
            }
        }
        msr.setYear(getYear(description));
        msr.setDescription(description);
    }


    /**
     * search for port.hu movie id in the url.
     * 
     * @param url
     * @return
     */
    private String collectIdFromUrl(String url) {
        Matcher matcher = FILM_ID_PATTERN.matcher(url);
        if (matcher.find()) {
            if (matcher.groupCount() == 1) {
                return matcher.group(1);
            }
        }
        return null;
    }

    private Integer getYear(String description) {
        Matcher matcher = YEAR_END_PATTERN.matcher(description);
        if (matcher.find()) {
            if (matcher.groupCount() == 1) {
                return Integer.parseInt(matcher.group(1));
            }
        }
        return null;
    }

    private Integer getRuntime(String description) {
        Matcher matcher = RUNTIME_PATTERN.matcher(description);
        if (matcher.find()) {
            if (matcher.groupCount() == 1) {
                return Integer.parseInt(matcher.group(1));
            }
        }
        return null;
    }

    private Integer getScore(String scoreText) {
        Matcher matcher = SCORE_PATTERN.matcher(scoreText);
        if (matcher.find()) {
            if (matcher.groupCount() == 2) {
                float score = Float.parseFloat(matcher.group(1).replace(',', '.'));
                return Integer.valueOf((int) (score * 10));
            }
        }
        return null;
    }

    /**
     * get vote count from a string like: "(32 szavazat)" -> 32
     * 
     * @param string
     * @return
     */
    private Integer getVotes(String string) {
        String txt = unbracket(string);
        String[] array = txt.split(" ");
        if (array.length == 2 && "szavazat".equals(array[1])) {
            return Integer.parseInt(array[0]);
        }
        return null;
    }

    private void parseAjaxVoteObjectResponse(MoviePage mp, String id) throws IOException {
        Source voteObject = fetchVoteObject(id);
        List<Element> spanElements = voteObject.getAllElements("span");
        for (Element span : spanElements) {
            String content = span.getTextExtractor().toString();
            String classAttr = span.getAttributeValue("class");
            if ("btxt".equals(classAttr)) {
                mp.setScore(getScore(content));
            }
            if ("rtxt".equals(classAttr)) {
                mp.setVotes(getVotes(content));
            }
        }

    }

    private Source fetchVoteObject(String id) throws IOException {
        Map<String,String> params = new LinkedHashMap<String,String>();
        //i_object_id=73833&i_area_id=6&i_is_separator=0
        params.put("i_object_id", id);
        params.put("i_area_id","6");
        params.put("i_is_separator", "0");
        params.put("i_reload_container","id=\"vote_box\"");
        
        Map<String,String> headers = new LinkedHashMap<String,String>();
        headers.put("X-Requested-With", "XMLHttpRequest");
        //headers.put("X-Prototype-Version", "1.6.0.2");
        headers.put("Referer", generateUrlForFilmID(id));
        
        // http://port.hu/indiana_jones_es_a_kristalykoponya_kiralysaga_indiana_jones_and_the_kingdom_of_the_crystal_skull/pls/fi/vote.print_vote_box?i_object_id=73047&i_area_id=6&i_reload_container=id%3D%22vote_box%22&i_is_separator=0
        com.flicklib.service.Source content = sourceLoader.post("http://port.hu/pls/fi/vote.print_vote_box?", params, headers);
        return content.getJerichoSource();
    }


    protected String generateUrlForTitleSearch(String title) {
        try {
            return "http://port.hu/pls/ci/cinema.film_creator?i_text=" + URLEncoder.encode(title, "ISO-8859-2") + "&i_film_creator=1&i_city_id=" + TEST_CITY_ID;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding problem with UTF-8? " + e.getMessage(), e);
        }
    }

    protected String generateUrlForFilmID(String id) {
        return "http://port.hu" + FILM_INFO_URL + "?i_where=2&i_film_id=" + id + "&i_city_id=" + TEST_CITY_ID + "&i_county_id=-1";
    }

    protected boolean isMoviePageUrl(String url) {
    	// FIXME is this first cause correct?
        return FILM_INFO_URL.startsWith(url) || (url.startsWith("http://port.hu") && url.indexOf(FILM_INFO_URL)!=-1);
    }
    
    @Override
    public MovieService getService() {
        return PORTHU;
    }
}
