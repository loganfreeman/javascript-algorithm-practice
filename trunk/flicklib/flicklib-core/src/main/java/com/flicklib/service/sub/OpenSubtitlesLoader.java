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
package com.flicklib.service.sub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.SubtitlesLoader;
import com.flicklib.domain.Subtitle;
import com.flicklib.service.SourceLoader;
import com.flicklib.tools.ElementOnlyTextExtractor;
import com.flicklib.tools.Param;
import com.google.inject.Inject;

/**
 * 
 * http://www.opensubtitles.org
 * @author francisdb
 */
public class OpenSubtitlesLoader implements SubtitlesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenSubtitlesLoader.class);
    private static final String SITE = "http://www.opensubtitles.org";
    private final SourceLoader sourceLoader;

    /**
     * Constructs a new OpenSubtitlesLoader
     * @param sourceLoader
     */
    @Inject
    public OpenSubtitlesLoader(final SourceLoader sourceLoader) {
        this.sourceLoader = sourceLoader;
    }

    @Override
    public Set<Subtitle> search(String localFileName, String imdbId) throws IOException {
        String url = searchUrl(localFileName);
        int carryOn = 1;
        com.flicklib.service.Source source = sourceLoader.loadSource(url);
        Source jerichoSource = source.getJerichoSource();

        Set<Subtitle> results = new HashSet<Subtitle>();

        Element titleElement = (Element) jerichoSource.getAllElements(HTMLElementName.TITLE).get(0);
        String title = titleElement.getContent().getTextExtractor().toString();
        if (title.contains("(results)")) {
            //first check if the results page contains no results. 
            List<?> divElements = jerichoSource.getAllElements(HTMLElementName.DIV);
            Iterator<?> j = divElements.iterator();
            while(j.hasNext() && carryOn==1) {
                Element divElement  = (Element) j.next();
                if(divElement.getTextExtractor().toString().contains("No results found")) {
                    carryOn = 0;
                }
                else {
                    carryOn = 1;
                }
            }
            
            //if the results page does contain results then load first link
            if(carryOn!=0) {
                String subsUrl = null;
                List<?> aElements = jerichoSource.getAllElements(HTMLElementName.A);
                for (int i = 0; i < aElements.size() && subsUrl == null; i++) {
                    Element aElement = (Element) aElements.get(i);
                    if ("bnone".equals(aElement.getAttributeValue("class"))) {
                        subsUrl = SITE + aElement.getAttributeValue("href");
                    }
                }

                source = load(subsUrl);
                results = loadSubtitlesPage(source.getJerichoSource());
                
                //Get links for other pages.
                List<String> pages = new ArrayList<String>();
                pages.addAll(getPageLinks(jerichoSource));
                Iterator<?> k = pages.iterator();
                while(k.hasNext()) {
                    String link = (String) k.next();
                    source = load(SITE + link);
                    results.addAll(loadSubtitlesPage(source.getJerichoSource()));
                }
            }
            
        } else {
            // direct hit
            results = loadSubtitlesPage(jerichoSource);
            
            //Get links for other pages.
            Set<String> pages = new HashSet<String>();
            pages.addAll(getPageLinks(jerichoSource));
            for(String link:pages){
                source = load(SITE + link);
                results.addAll(loadSubtitlesPage(source.getJerichoSource()));
            }
        }
        
        return results;
    }

private com.flicklib.service.Source load(String link) throws IOException {
	com.flicklib.service.Source source = sourceLoader.loadSource(link);
	if (source == null) {
		throw new IOException("loading " + SITE + link + " is failed!");
	}
	return source;
}
    
    /**
     * This method retrieves the links for all pages other than the first page. 
     * @param URL
     * @return
     */
    private Set<String> getPageLinks(Source source) {
        Set<String> links = new HashSet<String>();
        List<?> linksElements = source.getAllElements(HTMLElementName.A);
        Iterator<?> i;
        i = linksElements.iterator();
        
        while (i.hasNext()) {
            Element linkElement = (Element) i.next();
            String href = linkElement.getAttributeValue("href");
            if(!href.isEmpty() && href.contains("/offset-")) {
                LOGGER.trace(linkElement.getTextExtractor().toString());
                links.add(href);
            }
        }
        return links;
    }

    private Set<Subtitle> loadSubtitlesPage(Source jerichoSource) {
        Set<Subtitle> results = new HashSet<Subtitle>();

        Element tableElement = (Element) jerichoSource.getAllElements("id", "search_results", false).get(0);

        Subtitle sub;
        for (Element trElement : tableElement.getAllElements(HTMLElementName.TR)) {
            String style = trElement.getAttributeValue("style");
            if (!"display:none".equals(style)) {

                sub = new Subtitle();
                sub.setSubSource(SITE);

                List<?> tdElements = trElement.getAllElements(HTMLElementName.TD);
                if (tdElements.size() >= 4) {


                    // TITLE/URL
                    Element titleTd = (Element) tdElements.get(0);
                    Element firstLink = (Element) titleTd.getFirstElement(HTMLElementName.A);
                    if (firstLink == null) {
                	    // TODO : investigate
                	    continue;
                    }
                    String fileName = firstLink.getContent().getTextExtractor().toString();

                    ElementOnlyTextExtractor extractor = new ElementOnlyTextExtractor(titleTd.getContent());
                    String extra = extractor.toString();
                    if(extra.trim().length() > 0){
                        fileName = extractor.toString()+" "+fileName;
                    }
                    sub.setFileName(fileName);


                    // LANG
                    Element flagTd = (Element) tdElements.get(1);
                    List<?> divElements = flagTd.getAllElements(HTMLElementName.DIV);
                    Element divElement;
                    Iterator<?> divs = divElements.iterator();
                    while (divs.hasNext()) {
                        divElement = (Element) divs.next();
                        //LOGGER.info(divElement.toString());
                        String cls = divElement.getAttributeValue("class");
                        if (cls != null && cls.startsWith("flag")) {
                            sub.setLanguage(cls.substring(5));
                        }
                    }

                    // CD
                    Element cdTd = (Element) tdElements.get(2);
                    sub.setNoCd(cdTd.getContent().getTextExtractor().toString());

                    // TYPE & URL
                    Element typeTd = (Element) tdElements.get(4);
                    Element span = (Element) typeTd.getAllElements("class", "p", false).get(0);
                    Element link = (Element) typeTd.getAllElements("a").get(0);
                    sub.setType(span.getContent().getTextExtractor().toString());
                    sub.setFileUrl(SITE + link.getAttributeValue("href"));

                    results.add(sub);
                }

            }
        }
        
        return results;
    }

    private String searchUrl(String title) {
        String encoded = Param.encode("\""+title+"\"");
        return SITE + "/en/search2/sublanguageid-all/moviename-" + encoded;
    }

    
}
