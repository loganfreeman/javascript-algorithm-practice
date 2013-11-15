package com.flicklib.service.movie.xpress;


import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.FileSourceLoader;
import com.flicklib.service.movie.AliasingSourceLoader;

public class XpressHuFetcherTest {
    private AliasingSourceLoader loader;
    private AbstractMovieInfoFetcher fetcher;

    @Before
    public void setUp() throws Exception {
        loader = new AliasingSourceLoader(new FileSourceLoader("UTF-8"));
        loader.putAlias("http://www.xpress.hu/", "xpresshu/main.html");
        loader.putAlias("http://www.xpress.hu/dvd/keres.asp" +
                "::GOMB=1::Go2=Go::Go2.x=0::Go2.y=0::KERES=Breakfast::VID=066-193882322-6678351010467122", 
                "xpresshu/search-result.html");
        loader.putAlias("http://www.xpress.hu/dvd/film.asp?FILMAZ=7467", "xpresshu/film_7467.html");

        
        fetcher = new XpressHuFetcher(loader);
    }

    
    @Test
    public void testBreakfastSearch() throws IOException {
        List<? extends MovieSearchResult> searchResult = fetcher.search("Breakfast");
        Assert.assertNotNull("search result", searchResult);
        Assert.assertEquals("3 result", 3, searchResult.size());
        Assert.assertEquals("title 1", "Nulladik óra", searchResult.get(0).getTitle());
        Assert.assertEquals("title 2", "Bajnokok reggelije", searchResult.get(1).getTitle());
        Assert.assertEquals("title 3", "Reggeli a Plútón", searchResult.get(2).getTitle());

        Assert.assertEquals("orig title 1", "The Breakfast Club", searchResult.get(0).getOriginalTitle());
        Assert.assertEquals("orig title 2", "Breakfast of Champions", searchResult.get(1).getOriginalTitle());
        Assert.assertEquals("orig title 3", "Breakfast on Pluto", searchResult.get(2).getOriginalTitle());
        
        Assert.assertEquals("orig year 1", Integer.valueOf(1985), searchResult.get(0).getYear());
        Assert.assertEquals("orig year 2", Integer.valueOf(1999), searchResult.get(1).getYear());
        Assert.assertEquals("orig year 3", Integer.valueOf(2005), searchResult.get(2).getYear());
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
        Assert.assertEquals("score", Integer.valueOf(73), movieInfo.getScore());
    }

    
}
