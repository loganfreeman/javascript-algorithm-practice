package com.flicklib.service.movie.blippr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.movie.AlternateLiveTester;

public class BlipprInfoFetcherTest extends AlternateLiveTester {

    BlipprInfoFetcher fetcher;

    public BlipprInfoFetcherTest(boolean internalHttpClient, boolean internalRedirects) {
        super(internalHttpClient, internalRedirects);
        fetcher = new BlipprInfoFetcher(loader);
    }

    @Test
    public void testSearchString() throws Exception {
        List<? extends MovieSearchResult> list = fetcher.search("The Matrix");
        assertFalse("expecting results", list.isEmpty());
        assertEquals("The Matrix", list.get(0).getTitle());
    }

    @Test
    public void testGetMovieInfo() throws Exception {
        MoviePage page = fetcher.getMovieInfo("1167");
        assertNotNull("expecting results", page);
        assertEquals("Pulp Fiction", page.getTitle());
        assertEquals(Integer.valueOf(1994), page.getYear());
        assertNotNull(page.getScore());
        assertTrue("no genres", page.getGenres().size() > 0);
        assertTrue("no actors", page.getActors().size() > 0);
        assertTrue("no directors", page.getDirectors().size() > 0);
    }
}
