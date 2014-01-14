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
package com.flicklib.service.movie.tomatoes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.service.FileSourceLoader;
import com.flicklib.service.Source;

/**
 *
 * @author francisdb
 */
public class TomatoesParserTest {

    /**
     * Test of parse method, of class TomatoesParser.
     * @throws Exception 
     */
    @Test
    public void testOfflineParse() throws Exception {
        Source source = new FileSourceLoader().loadSource("tomatoes/Pulp Fiction Movie Reviews, Pictures - Rotten Tomatoes.html");
        MoviePage site = new MoviePage();
        TomatoesParser instance = new TomatoesParser();
        instance.parse(source, site);
        assertEquals("Score", Integer.valueOf(95), site.getScore());
        assertEquals("director count", 1, site.getDirectors().size());
        assertEquals("director ","Quentin Tarantino", site.getDirectors().iterator().next());
        assertEquals("Title", "Pulp Fiction", site.getTitle());
    }
    
    @Test
    public void testOfflineParse2() throws Exception {
        Source source = new FileSourceLoader().loadSource("tomatoes/Waist Deep Movie Reviews, Pictures - Rotten Tomatoes.html");
        MoviePage site = new MoviePage();
        TomatoesParser instance = new TomatoesParser();
        instance.parse(source, site);
        assertEquals("Score", Integer.valueOf(27), site.getScore());
        assertEquals("Year", Integer.valueOf(2006), site.getYear());
        assertEquals("Title", "Waist Deep", site.getTitle());
    }

}