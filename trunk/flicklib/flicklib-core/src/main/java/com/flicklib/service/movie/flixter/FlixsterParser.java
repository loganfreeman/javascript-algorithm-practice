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
package com.flicklib.service.movie.flixter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.movie.AbstractJerichoParser;
import com.flicklib.tools.SimpleXPath;
import com.google.inject.Singleton;
/**
 *
 * @author francisdb
 */
@Singleton
public class FlixsterParser extends AbstractJerichoParser{

    //private static final Logger LOGGER = LoggerFactory.getLogger(FlixsterParser.class);
    

@Override
    public void parse(Source source, MoviePage movieSite) {
        Element movieTitle = new SimpleXPath(source.getElementById("movieTitle")).getTags(HTMLElementName.SPAN).firstElement();
        if (movieTitle != null) {
            parseTitle(movieTitle.getTextExtractor().toString(), movieSite);
        }
        final SimpleXPath details = new SimpleXPath(source.getElementById("details"));
        Element imgPic = details.getAllTagByAttributes("class", "leftSide")
                .getAllTagByAttributes("class", "poster").getTags("img").firstElement();
        if (imgPic != null) {
            movieSite.setImgUrl(imgPic.getAttributeValue("src"));
        }
        SimpleXPath minibox = details.getAllTagByAttributes("class", "minibox");
        String ratingPerc = minibox.getAllTagByAttributes("class", "rating percentage").getValue();
        if (ratingPerc != null) {
            String votes = ratingPerc.replace("%", "");
            movieSite.setScore(Integer.valueOf(votes));
        }
        String ratingCount = minibox.getAllTagByAttributes("class", "ratingCount").getValue();
        if (ratingCount != null) {
            String r = ratingCount.replace("ratings", "").replace(",", "").trim();
            movieSite.setVotes(Integer.parseInt(r));
        }

    }
    
    static void parseTitle(String title,MovieSearchResult mv) {
        Matcher matcher = Pattern.compile("(.*) \\((\\d{4})\\)").matcher(title);
        if (matcher.find()) {
        	String year = matcher.group(2);
        	mv.setYear(Integer.parseInt(year));
        	mv.setTitle(matcher.group(1));
        } else {
        	mv.setTitle(title);
        }
    }

}
