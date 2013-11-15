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
package com.flicklib.service.movie.xpress;

import static com.flicklib.tools.StringUtils.isElementAttributeValue;
import static com.flicklib.tools.StringUtils.isElementAttributeValueContains;
import static com.flicklib.tools.StringUtils.unbracket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.StartTag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;
import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;
import com.google.inject.Inject;

public class XpressHuFetcher extends AbstractMovieInfoFetcher {
    private final static MovieService XPRESSHU = new MovieService("XPRESSHU" ,"Xpress.hu", "http://www.xpress.hu");
    

    private static final Pattern ORIG_TITLE_WITH_YEAR_PATTERN = Pattern.compile("(.*) - ([0-9]+)");

    private static final Pattern URL_PATTERN = Pattern.compile(".*FILMAZ=([0-9]+)");

    private static final URL SEARCH_URL;
    private static final URL MOVIE_URL;

    static {
        try {
            SEARCH_URL = new URL("http://www.xpress.hu/dvd/keres.asp");
            MOVIE_URL = new URL("http://www.xpress.hu/dvd/film.asp");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL exception : " + e.getMessage(), e);
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(XpressHuFetcher.class);

    private final SourceLoader sourceLoader;

    @Inject
    public XpressHuFetcher(final SourceLoader sourceLoader) {
        this.sourceLoader = sourceLoader;
    }

    @Override
    public MoviePage getMovieInfo(final String idForSite) throws IOException {
        final String url = "http://www.xpress.hu/dvd/film.asp?FILMAZ=" + idForSite;
        final Source source = this.sourceLoader.loadSource(url);
        final net.htmlparser.jericho.Source jerichoSource = source.getJerichoSource();
        final MoviePage result = new MoviePage();
        result.setIdForSite(idForSite);
        result.setUrl(url);
        result.setService(XPRESSHU);

        final List<Element> trLines = jerichoSource.getAllElements("tr");
        // search for <tr valign='top' align='center'> with 2 child
        for (Element tr : trLines) {
            if (isElementAttributeValue(tr, "valign", "top") && isElementAttributeValue(tr, "align", "center")) {
                List<Element> tdList = tr.getChildElements();
                if (tdList.size() == 2) {
                    if (parseCenterPanel(result, tdList)) {
                        // navigate to the right most column, up one table
                        Element outerTr = getParentElement(tr, "tr");
                        if (outerTr != null && outerTr.getChildElements().size()==2) {
                            parseLeftMostColumn(result, (Element) outerTr.getChildElements().get(1));
                        } else {
                            LOGGER.error("unable locate rating for "+url);
                        }
                        break;
                    }
                }
            }
        }
        if (result.getImgUrl() == null) {
            LOGGER.error("Image URL not found for " + url);
        }
        return result;
    }

    private void parseLeftMostColumn(MoviePage result, Element element) {
        List<Element> bolds = element.getAllElements("b");
        for (Element boldElement : bolds) {
            List<Element> boldChild = boldElement.getChildElements();
            LOGGER.debug("CHILD:" + boldChild);
            if (boldChild.size() == 1 && "img".equals(boldChild.get(0).getName())) {
                // <img width="110" height="11" src="kepek/m2film.gif"/>
                Element img = boldChild.get(0);
                if (isElementAttributeValue(img, "src", "kepek/m2film.gif")) {
                    result.setScore(locateRating(img));
                    return;
                }
            }
        }
    }

	private boolean parseCenterPanel(final MoviePage result, List<Element> tdList) {
        Element firstTd = tdList.get(0);
        Element secondTd = tdList.get(1);
        if (isElementAttributeValue(firstTd, "rowspan", "2") && isElementAttributeValue(secondTd, "rowspan", "2")
                && isElementAttributeValue(firstTd, "valign", "top") && isElementAttributeValue(firstTd, "width", "194")
                && isElementAttributeValue(secondTd, "width", "1056")) {
            List<Element> imgList = firstTd.getAllElements("img");
            for (Element image : imgList) {
                if (isElementAttributeValueContains(image, "src", "cover")) {
                    result.setImgUrl(resolveRelativeUrl(MOVIE_URL, image.getAttributeValue("src")));
                }
            }
            List<Element> bolds = secondTd.getAllElements("b");
            if (bolds.size() > 0) {
                extractTitle(result, bolds);
                int found = 0;
                for (int i = 1; i < bolds.size() && found < 4; i++) {
                    Element boldElement = bolds.get(i);
                    String text = boldElement.getTextExtractor().toString();
                    LOGGER.debug("text found:" + text);
                    if ("Műfaj:".equalsIgnoreCase(text) || "Mûfaj:".equalsIgnoreCase(text)) {
                        String value = getLabelValue(boldElement);
                        if (value != null) {
                            result.setGenres(Collections.singleton(value));
                            found++;
                        }
                    } else if ("Rendezte:".equalsIgnoreCase(text)) {
                        String value = getLabelValue(boldElement);
                        if (value != null) {
                            result.getDirectors().add(value);
                            found++;
                        }
                    } else if ("Szereplők:".equalsIgnoreCase(text) || "Szereplõk:".equalsIgnoreCase(text)) {
                        String value = getLabelValue(boldElement);
                        if (value != null) {
                            LOGGER.info("Actors:" + value);
                            found++;
                        }
                    } else if ("Tartalom:".equalsIgnoreCase(text)) {
                        Element trx = getParentElement(boldElement, "tr");
                        Element plot = getSibling(trx, 1);
                        String plotValue = plot.getTextExtractor().toString();
                        LOGGER.info("Plot:" + plotValue);
                        result.setPlot(plotValue);
                        found++;
                    }

                }
            }
            return true;
        }
        return false;
    }

    /**
     * Locate the rating from a &gt;img width="110" height="11" src="kepek/m2film.gif"/&lt; element.
     * 
     * @param img
     * @return
     */
    private Integer locateRating(Element img) {
        Element tr = getParentElement(img, "tr");
        Element nextRow = getSibling(tr, 1);
        String nextRowValue = nextRow.getTextExtractor().toString();
        LOGGER.debug("nextRow : "+nextRowValue);
        if (nextRowValue.endsWith("%")) {
            String subSequence = nextRowValue.substring(0, nextRowValue.length()-1);
            return Integer.valueOf(subSequence);
        } else {
            return null;
        }
        
    }

    private void extractTitle(final MoviePage result, List<Element> bolds) {
        String title = bolds.get(0).getTextExtractor().toString();
        LOGGER.info("title is :" + title);
        result.setTitle(title);
        Element parentTd = getParentElement(bolds.get(0), "td");
        List<Element> childs = parentTd.getChildElements();
        LOGGER.debug("child size:" + childs.size());
        if (childs.size() >= 3) {
            String text = childs.get(2).getTextExtractor().toString();
            Pattern p = Pattern.compile("(.+) \\((.+)- ([0-9]+)\\)");
            Matcher matcher = p.matcher(text);
            if (matcher.matches()) {
                LOGGER.debug("group : " + matcher.group());
                LOGGER.debug("group 0 : " + matcher.group(0));
                LOGGER.debug("group 1 : " + matcher.group(1));
                LOGGER.debug("group 2 : " + matcher.group(2));
                LOGGER.debug("group 3 : " + matcher.group(3));
                result.setOriginalTitle(matcher.group(1));
                result.setYear(new Integer(matcher.group(3)));
            }
            // result.setOriginalTitle(text);
        }
    }

    private static String getLabelValue(Element boldElement) {
        Element parentTr = getParentElement(boldElement, "tr");
        if (parentTr != null) {
            List<Element> tds = parentTr.getChildElements();
            if (tds.size() == 2) {
                String value = tds.get(1).getTextExtractor().toString();
                if (value.endsWith("</")) {
                    value = value.substring(0, value.length() - 2);
                }
                return value;
            }
        }
        return null;
    }

    private static Element getSibling(Element element, int position) {
        List<Element> elements = element.getParentElement().getChildElements();
        int current = elements.indexOf(element);
        return elements.get(current + position);
    }

    @Override
    public List<? extends MovieSearchResult> search(String title) throws IOException {
        Source searchResponse = executeSearch(title);
        LOGGER.info("search for '" + title + "' returned " + searchResponse);
        //System.err.println(searchResponse.getContent());

        net.htmlparser.jericho.Source jerichoSource = searchResponse.getJerichoSource();
        List<StartTag> forms = jerichoSource.getAllStartTags("form");

        List<MovieSearchResult> result = new ArrayList<MovieSearchResult>();
        for (StartTag form : forms) {
            if (isElementAttributeValueContains(form, "action", "rendel.asp")) {
                List<Element> trLines = form.getElement().getAllElements("tr");
                for (Element tr : trLines) {
                    if (isElementAttributeValue(tr, "valign", "top")) {
                        List<Element> childs = tr.getChildElements();
                        LOGGER.debug("child count :" + childs.size());
                        if (childs.size() == 4) {
                            MovieSearchResult msr = parseRow(childs);
                            result.add(msr);
                        }
                    }
                }

            }
        }
        return result;
    }

    /**
     * parse the search result row
     * 
     * @param childs
     * @return
     */
    private MovieSearchResult parseRow(List<Element> childs) {
        MovieSearchResult msr = new MovieSearchResult();
        msr.setService(XPRESSHU);
        
        {
            Element imageCell = childs.get(1);
            List<Element> imageTags = imageCell.getAllElements("img");
            if (imageTags.size() > 0) {
                String imageUrl = imageTags.get(0).getAttributeValue("src");
                LOGGER.info("image url : " + imageUrl);
            }
        }
        {
            Element descCell = childs.get(2);
            List<Element> aTags = descCell.getAllElements("a");
            if (aTags.size() > 0) {
                Element alink = aTags.get(0);
                String link = alink.getAttributeValue("href");
                Matcher matcher = URL_PATTERN.matcher(link);
                if (matcher.find()) {
                    String id = matcher.group(1);
                    String relUrl = matcher.group();
                    LOGGER.info("ID:" + id + " relative url:" + relUrl);
                    msr.setIdForSite(id);
                    String url = resolveRelativeUrl(SEARCH_URL, relUrl);
                    msr.setUrl(url);
                }
                List<Element> fontElements = alink.getAllElements("font");
                if (fontElements.size() == 1) {
                    msr.setTitle(fontElements.get(0).getTextExtractor().toString());
                    LOGGER.info("set title :" + msr.getTitle());
                }

                List<Element> siblings = alink.getParentElement().getChildElements();
                int pos = siblings.indexOf(alink);
                LOGGER.debug("pos :" + pos);
                if (pos >= 0) {
                    Element originalTitle = siblings.get(pos + 2);
                    String originalTitleText = unbracket(originalTitle.getTextExtractor().toString());
                    Matcher titleMatcher = ORIG_TITLE_WITH_YEAR_PATTERN.matcher(originalTitleText);
                    if (titleMatcher.matches()) {
                        String origTitle = titleMatcher.group(1);
                        String year = titleMatcher.group(2);
                        LOGGER.info("set original title :" + origTitle + ", year:" + year);
                        msr.setOriginalTitle(origTitle);
                        msr.setYear(Integer.parseInt(year));
                    }
                }
            }
        }
        return msr;
    }

    private static Element getParentElement(Element element, String parentName) {
        while (true) {
            element = element.getParentElement();
            if (element == null || element.getName().equalsIgnoreCase(parentName)) {
                return element;
            }
        }
    }

    private static String resolveRelativeUrl(URL baseUrl, String relUrl) {
        try {
            return new URL(baseUrl, relUrl).toString();
        } catch (MalformedURLException e) {
            LOGGER.error("Problematic url:" + relUrl, e);
            return relUrl;
        }
    }

    /**
     * post a request to do the search.
     * 
     * @param title
     * @return
     * @throws IOException
     */
    private Source executeSearch(String title) throws IOException {
        Source loadSource = sourceLoader.loadSource("http://www.xpress.hu/", false);
        net.htmlparser.jericho.Source jerichoSource = loadSource.getJerichoSource();
        List<StartTag> forms = jerichoSource.getAllStartTags("form");
        Map<String, String> params = new HashMap<String, String>();
        params.put("GOMB", "1");
        params.put("Go2", "Go");
        params.put("Go2.x", "0");
        params.put("Go2.y", "0");
        params.put("KERES", title);
        for (StartTag form : forms) {
            String action = form.getAttributeValue("action");
            if (action != null && action.indexOf("keres.asp") != -1) {
                parseHiddenFields(params, form);
                break;
            }
        }
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Referer", "http://www.xpress.hu/");
        Source searchResponse = sourceLoader.post("http://www.xpress.hu/dvd/keres.asp", params, headers);
        return searchResponse;
    }

    private static void parseHiddenFields(Map<String, String> params, StartTag form) {
        List<Element> hiddenElements = form.getElement().getAllElements("type", "hidden", true);
        for (Element hidden : hiddenElements) {
            String name = hidden.getAttributeValue("name");
            String value = hidden.getAttributeValue("value");
            if (name != null && value != null && name.length() > 0) {
                params.put(name, value);
            }
        }
    }
    
    @Override
    public MovieService getService() {
        return XPRESSHU;
    }
}
