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
package com.flicklib.folderscanner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author francisdb
 */
public class MovieNameExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieNameExtractor.class);

    /*
     * more specefic items first!
     */
    private static final String TO_REMOVE[] = {
        ".5.1",
        ".ws.dvdrip",
        ".fs.dvdrip",
        ".fs.internal",
        ".se.internal",
        ".extended.dvdrip",
        ".real.repack",
        ".real.proper",
        ".real.retail",
        ".limited.dvdrip",
        ".limited.color.fixed",
        ".read.nfo",
        ".dvdrip",
        ".samplefix",
        ".dvdivx4",
        ".dvdivx5",
        ".dvdivx",
        ".dvdr",
        ".divx",
        ".dual",
        ".r5.xvid",
        ".xvid",
        ".limited",
        ".internal",
        ".special.edition",
        ".proper",
        ".dc",
        ".ac3",
        ".unrated",
        ".uncut",
        ".stv",
        ".dutch", // keep this one?
        ".hundub",
        ".hund",
        ".hun",
        ".rerip",
        ".nfofix",
        ".full.subpack",
        ".subpack",
        ".subfix",
        ".syncfix",
        ".ts",
        ".cd1",
        ".cd2",
        ".screener",
        ".dvdr",
        ".dvd",
        ".pal",
        ".1080p",
        ".direcors.cut",
        ".extended.cut",
        ".repack",
        ".disc",
        ".retail"//".ws"        
    };
    
    static class LanguageSuggestion {
        Pattern pattern;
        Locale language;

        public LanguageSuggestion(String pattern, Locale language) {
            this.pattern = Pattern.compile(pattern);
            this.language = language;
        }

        public Locale match(String name) {
            if (pattern.matcher(name.toLowerCase()).find()) {
                return language;
            }
            return null;
        }

    }

    final static Locale HUNGARIAN = new Locale("hu_HU", "hu", "HU");
    final static Locale DUTCH = new Locale("nl_NL", "nl", "NL");

    private static final LanguageSuggestion[] SUGGESTIONS = new LanguageSuggestion[] { new LanguageSuggestion("\\.hungarian", HUNGARIAN),
            new LanguageSuggestion("\\.hun\\.", HUNGARIAN), new LanguageSuggestion("\\.dutch", DUTCH), new LanguageSuggestion("\\.en\\.", Locale.ENGLISH) };

    public MovieNameExtractor() {
    }
    
    public String removeCrap(FileObject file) throws FileSystemException {
        return removeCrap(file.getName().getBaseName(), file.getType().hasChildren());
    }

    public String removeCrap(String fileName, boolean directory) {
        String movieName = fileName.toLowerCase().replace('_', '.');
        if (!directory) {
            movieName = clearMovieExtension(movieName);
        }
        // getYear(movieName);
        boolean release = false;
        for (String bad : TO_REMOVE) {
            if (movieName.contains(bad)) {
                // these strings should not be available in non-release movies
                release = true;
                movieName = movieName.replaceAll(bad, "");
            }
            bad = bad.replace('.', ' ');
            if (movieName.contains(bad)) {
                // these strings should not be available in non-release movies
                release = true;
                movieName = movieName.replaceAll(bad, "");
            }
        }

        if (release) {
            int dashPos = movieName.lastIndexOf('-');
            if (dashPos != -1) {
                movieName = movieName.substring(0, movieName.lastIndexOf('-'));
            }

            // this actualy yields better results in imdb
            // TODO make this optional or depending on the main service
            Calendar calendar = new GregorianCalendar();
            int thisYear = calendar.get(Calendar.YEAR);
            // TODO recup the movie year!
            for (int i = 1800; i < thisYear; i++) {
                movieName = movieName.replaceAll(Integer.toString(i), "");
            }
        }

        // clean up dashes for normal movies
        movieName = movieName.replace('-', '.');

        movieName = movieName.replaceAll("\\.", " ");
        movieName = movieName.trim();
        LOGGER.trace(movieName);
        return movieName;
    }

    public String clearMovieExtension(String name) {
        int lastDotPos = name.lastIndexOf('.');
        if (lastDotPos != -1 && lastDotPos != 0 && lastDotPos < name.length() - 1) {
            String ext = name.substring(lastDotPos + 1).toLowerCase();
            if (MovieFileFilter.VIDEO_EXT_EXTENSIONS.contains(ext)) {
                return name.substring(0, lastDotPos);
            }
        }
        return name;
    }
    
    public Locale getLanguageSuggestion(FileObject file) {
        return getLanguageSuggestion(file.getName().getBaseName());
    }

    public Locale getLanguageSuggestion(String filename) {
        for (LanguageSuggestion s : SUGGESTIONS) {
            Locale l = s.match(filename);
            if (l != null) {
                return l;
            }
        }
        return Locale.ENGLISH;
    }

}
