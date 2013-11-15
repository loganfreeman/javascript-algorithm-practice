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
package com.flicklib.service.movie.xpress;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.movie.AlternateLiveTester;

public class XpressHuLiveFetcherTest extends AlternateLiveTester {
    private final AbstractMovieInfoFetcher fetcher;

    
    public XpressHuLiveFetcherTest (boolean internalHttpClient, boolean internalRedirects) {
    	super(internalHttpClient, internalRedirects);
        fetcher = new XpressHuFetcher(loader);
    }
    
    @Test
    public void testBreakfastSearch() throws IOException {
        List<? extends MovieSearchResult> searchResult = fetcher.search("Breakfast");
        Assert.assertNotNull("search result", searchResult);
        Assert.assertEquals("5 result", 5, searchResult.size());

        Assert.assertEquals("title 1", "Álom luxuskivitelben", searchResult.get(0).getTitle());


        Assert.assertEquals("title 2", "Nulladik óra", searchResult.get(1).getTitle());
        Assert.assertEquals("orig title 2", "The Breakfast Club", searchResult.get(1).getOriginalTitle());
        Assert.assertEquals("orig year 2", Integer.valueOf(1985), searchResult.get(1).getYear());

        Assert.assertEquals("title 3", "Bajnokok reggelije", searchResult.get(2).getTitle());
        Assert.assertEquals("orig title 3", "Breakfast of Champions", searchResult.get(2).getOriginalTitle());
        Assert.assertEquals("orig year 3", Integer.valueOf(1999), searchResult.get(2).getYear());

        Assert.assertEquals("title 4", "Reggeli a Plútón", searchResult.get(3).getTitle());
        Assert.assertEquals("orig title 4", "Breakfast on Pluto", searchResult.get(3).getOriginalTitle());
        Assert.assertEquals("orig year 4", Integer.valueOf(2005), searchResult.get(3).getYear());
        
        Assert.assertEquals("title 5", "Álom luxuskivitelben (!!! CSAK ANGOLUL !!!)", searchResult.get(4).getTitle());
        Assert.assertEquals("orig title 5", "Breakfast at Tiffany's", searchResult.get(4).getOriginalTitle());
        Assert.assertEquals("orig year 5", Integer.valueOf(1961), searchResult.get(4).getYear());
        
    }
    @Test
    public void testMovieInfoFetching() throws IOException {
        MoviePage movieInfo = fetcher.getMovieInfo("7467");
        Assert.assertNotNull("movie info", movieInfo);

        Assert.assertEquals("id", "7467", movieInfo.getIdForSite());
        
        Assert.assertEquals("url", "http://www.xpress.hu/dvd/film.asp?FILMAZ=7467", movieInfo.getUrl());
        Assert.assertEquals("image url", "http://www.xpress.hu/dvd/cover/kicsi/7467.jpg", movieInfo.getImgUrl());
        Assert.assertEquals("title", "Bajnokok reggelije", movieInfo.getTitle());
        Assert.assertEquals("genre", "[vígjáték]", movieInfo.getGenres().toString());
        Assert.assertTrue("director", movieInfo.getDirectors().contains("Alan Rudolph"));
        Assert.assertEquals("orig title", "Breakfast of Champions", movieInfo.getOriginalTitle());
        Assert.assertEquals("orig year", Integer.valueOf(1999), movieInfo.getYear());
        Assert.assertEquals("score", Integer.valueOf(70), movieInfo.getScore());
        
    }
    

}
