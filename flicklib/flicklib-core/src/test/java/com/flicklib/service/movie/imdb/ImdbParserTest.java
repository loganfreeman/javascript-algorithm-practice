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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.service.FileSourceLoader;
import com.flicklib.service.Source;

/**
 *
 * @author francisdb
 */
public class ImdbParserTest {

    /**
     * Test of parse method, of class ImdbParser.
     * 
     * 
     * @throws Exception 
     */
    @Test
    public void testParse() throws Exception{
        Source source = new FileSourceLoader().loadSource("imdb/Pulp Fiction (1994).html");
        MoviePage site = new MoviePage();
        ImdbParser instance = new ImdbParser();
        instance.parse(source, site);
        //assertEquals(Integer.valueOf(89), site.getIdForSite());
        assertEquals("Pulp Fiction", site.getTitle());
        assertEquals(1, site.getDirectors().size());
        assertTrue(site.getDirectors().contains("Quentin Tarantino"));
        assertEquals(Integer.valueOf(1994), site.getYear());
        assertEquals("http://ia.media-imdb.com/images/M/MV5BMjE0ODk2NjczOV5BMl5BanBnXkFtZTYwNDQ0NDg4._V1._SX98_SY140_.jpg", site.getImgUrl());
        assertEquals("The lives of two mob hit men, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", site.getPlot());
        assertEquals(Integer.valueOf(154), site.getRuntime());
        assertEquals(377356, site.getVotes().intValue());
        // TODO test other fields
        
        source = new FileSourceLoader().loadSource("imdb/A Couple of White Chicks at the Hairdresser (2007).html");
        site = new MoviePage();
        instance.parse(source, site);
        //assertEquals(Integer.valueOf(89), site.getIdForSite());
        assertEquals("A Couple of White Chicks at the Hairdresser", site.getTitle());
        assertTrue(site.getDirectors().contains("Roxanne Messina Captor"));
        assertEquals(Integer.valueOf(2007), site.getYear());
        assertEquals(null, site.getImgUrl());
        assertEquals("\"A COUPLE OF WHITE CHICKS AT THE HAIRDRESSER\" is a Dramady about two very different women who develop...", site.getPlot());
        assertEquals(null, site.getRuntime());
        assertEquals(Integer.valueOf(5), site.getVotes());
    }

    
    @Test
    public void testIssue107() throws IOException {
        Source source = new FileSourceLoader().loadSource("imdb/Matrix.html");
        MoviePage page = new MoviePage();
        ImdbParser instance = new ImdbParser();
        instance.parse(source, page);

        assertEquals("The Matrix", page.getTitle());
        assertEquals(2, page.getDirectors().size());
        assertTrue(page.getDirectors().contains("Andy Wachowski"));
        assertTrue(page.getDirectors().contains("Larry Wachowski"));
        
        assertTrue(page.getActors().contains("Keanu Reeves"));
        assertTrue(page.getActors().contains("Laurence Fishburne"));
        assertTrue(page.getActors().contains("Carrie-Anne Moss"));          
        
        assertNotNull(page.getScore());
        assertNotNull(page.getPlot());
        assertTrue(page.getGenres().contains("Action"));
        assertTrue(page.getGenres().contains("Adventure"));
        assertTrue(page.getGenres().contains("Sci-Fi"));
        
    }
    
    @Test
    public void testCoverLoading() throws IOException {
        Source source = new FileSourceLoader().loadSource("imdb/kingspeech.html");
        MoviePage page = new MoviePage();
        ImdbParser instance = new ImdbParser();
        instance.parse(source, page);
        
        assertEquals("A király beszéde", page.getTitle());
        assertEquals("The King's Speech", page.getOriginalTitle());
        assertEquals("The King's Speech", page.getPreferredTitle());
        assertEquals(1, page.getDirectors().size());
        assertTrue(page.getDirectors().contains("Tom Hooper"));

        assertNotNull("cover found", page.getImgUrl());
        assertEquals("cover image found", "http://ia.media-imdb.com/images/M/MV5BMzU5MjEwMTg2Nl5BMl5BanBnXkFtZTcwNzM3MTYxNA@@._V1._SY317_CR1,0,214,317_.jpg", page.getImgUrl());
        
    }
}