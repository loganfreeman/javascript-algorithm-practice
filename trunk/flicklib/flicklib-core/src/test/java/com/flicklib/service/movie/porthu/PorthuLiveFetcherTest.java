package com.flicklib.service.movie.porthu;
/*
 * This file is part of Flicklib.
 * 
 * Copyright (C) Zsombor Gegesy
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
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.movie.AlternateLiveTester;

/**
 * @author zsombor
 * 
 */
// @RunWith(value=Parameterized.class)
public class PorthuLiveFetcherTest extends AlternateLiveTester {
    private PorthuFetcher fetcher;

    public PorthuLiveFetcherTest (boolean internalHttpClient, boolean internalRedirects) {
    	super(internalHttpClient, internalRedirects);
        fetcher = new PorthuFetcher(loader);
    }
    
    @Test
    public void testPerfectTitleMatch() {
        try {
            List<MovieSearchResult> list = fetcher.search("Indiana Jones and the Kingdom of the Crystal Skull");
            Assert.assertEquals("search result size", 1, list.size());
            MovieSearchResult searchResult = list.get(0);
            Assert.assertNotNull("search result not null", searchResult);
            Assert.assertTrue("search result is an extended search result", searchResult instanceof MoviePage);
            
            MoviePage moviePage = (MoviePage) searchResult;
            
            Assert.assertEquals("title", "Indiana Jones és a kristálykoponya királysága", moviePage.getAlternateTitle());
            Assert.assertEquals("alternate title", "Indiana Jones and the Kingdom of the Crystal Skull", moviePage.getTitle());
            Assert.assertEquals("year", Integer.valueOf(2008), moviePage.getYear());
            Assert.assertNotNull("has plot", moviePage.getPlot());

        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testMalenaSearch() {
        try {
            List<MovieSearchResult> list = fetcher.search("maléna");
            Assert.assertEquals("search result size", 1, list.size());
            MovieSearchResult searchResult = list.get(0);
            Assert.assertNotNull("search result not null", searchResult);
            Assert.assertTrue("search result is an extended search result", searchResult instanceof MoviePage);
            
            MoviePage moviePage = (MoviePage) searchResult;
            
            Assert.assertEquals("title", "Maléna", moviePage.getAlternateTitle());
            Assert.assertEquals("alternate title", "Malena", moviePage.getTitle());
            Assert.assertEquals("year", Integer.valueOf(2000), moviePage.getYear());
            Assert.assertNotNull("has plot", moviePage.getPlot());

        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        
    }
    
    
    @Test
    public void testLiveMovieFetch() {
        //73833
        try {
            MoviePage info = fetcher.getMovieInfo("73833");
            Assert.assertEquals("title","Syriana", info.getTitle());
            Assert.assertEquals("title","Sziriana", info.getAlternateTitle());
            Assert.assertEquals("year", Integer.valueOf(2005), info.getYear());
            //Assert.assertEquals("score", Integer.valueOf(74), info.getScore());
            Assert.assertNotNull("score", info.getScore());
            Assert.assertNotNull("votes", info.getVotes());
            Assert.assertTrue("votes=>20", info.getVotes().intValue() >= 20);
            
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        
    }
}
