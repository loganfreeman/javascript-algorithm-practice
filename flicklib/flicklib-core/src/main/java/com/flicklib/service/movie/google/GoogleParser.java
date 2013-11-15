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
package com.flicklib.service.movie.google;

import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.domain.MoviePage;
import com.flicklib.service.movie.AbstractJerichoParser;
import com.google.inject.Singleton;

/**
 *
 * @author francisdb
 */
@Singleton
public class GoogleParser extends AbstractJerichoParser{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleParser.class);


    @Override
    public void parse(final Source source, final MoviePage movieSite) {
        List<?> nobrElements = source.getAllElements("nobr");
        for (Iterator<?> i = nobrElements.iterator(); i.hasNext();) {
            Element nobrElement = (Element) i.next();
            //3.9&nbsp;/&nbsp;5
            String content = nobrElement.getTextExtractor().toString();
            if(content.contains(" / ")){
            	LOGGER.trace("  "+content);
            	content = content.replace(" ", "");
	            content = content.replace("&nbsp;", "");
	            if(!content.contains("/")){
	                LOGGER.error("Could not find score on imdb page: "+content);
	            }
	            content = content.substring(0, content.indexOf('/'));
	            Double doubleScore = Double.valueOf(content);
	            doubleScore = doubleScore  * 20.0;
	            Integer intScore = (int) Math.round(doubleScore);
	            movieSite.setScore(intScore);
            }
        }
    }

}
