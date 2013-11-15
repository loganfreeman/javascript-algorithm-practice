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

import com.flicklib.domain.MovieType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copy of jmoviedb functionality!
 * @author francisdb
 */
class ImdbParserRegex {

    private String html;

    /**
     * The default constructor
     * @param html - a HTML document
     */
    ImdbParserRegex(String html) {
        this.html = html;
    }

    /**
     * Returns an array of the movie's genres, if the open document is a movie page.
     * @return an array of genres, or an empty array if none were found.
     */
    List<String> getGenres() {

        /*
         * Examples:
         * <a href="/Sections/Genres/Crime/">Crime</a>
         * <a href="/Sections/Genres/Film-Noir/">Film-Noir</a>
         * <a href="/Sections/Genres/Thriller/">Thriller</a>
         */
        Pattern patternGenre = Pattern.compile("<a href=\"/Sections/Genres/[^/]+/\">([^<]+)</a>");
        Matcher matcherGenre = patternGenre.matcher(html);
        List<String> temp = new ArrayList<String>();
        while (matcherGenre.find()) {
            temp.add(matcherGenre.group(1));
        }
        return temp;
    }

    MovieType getType() {
        MovieType type = null;
        Pattern patternType = Pattern.compile("<h1>(.+)</h1>");
        Matcher matcherType = patternType.matcher(html);
        if (matcherType.find()) {
            String match = matcherType.group(1);
            type = getType(match, true);
        }
        return type;
    }

    /**
     * TODO cleanup put in other class?
     * @param header
     * @return
     */
    static MovieType getType(final String header, final boolean html) {
        MovieType type = MovieType.MOVIE;
        if (header.contains("(TV)")) {
            type = MovieType.TV_MOVIE;
        } else if (header.contains("(V)")) {
            type = MovieType.VIDEO_MOVIE;
        } else if (header.contains("TV mini-series")) {
            type = MovieType.MINI_SERIES;
        } else if (header.contains("TVseries")) {
            type = MovieType.TV_SERIES;
        } else if (html && header.startsWith("&#34;")) {
            type = MovieType.TV_SERIES;
        } else if (!html && header.startsWith("\"")) {
            type = MovieType.TV_SERIES;
        }
        return type;
    }
    
    /**
     * Remove quote at beginning and end of title for TV-series
     * @param title
     * @return
     */
    static String cleanTitle(String title){
        if (title.startsWith("\"") && title.endsWith("\"")) {
            title = title.substring(1, title.length() - 1);
        }
        return title;
    }

    /**
     * Returns the movie's production year, if the open document is a movie page.
     * @return a year
     */
    int getYear() {
        Pattern patternYear = Pattern.compile("<a href=\"/Sections/Years/\\d{4}\">(\\d{4})</a>");
        Matcher matcherYear = patternYear.matcher(html);
        if (matcherYear.find()) {
            return Integer.parseInt(matcherYear.group(1));
        }
        return 0;
    }
}
