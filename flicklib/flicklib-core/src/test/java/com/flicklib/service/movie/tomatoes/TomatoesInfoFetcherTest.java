package com.flicklib.service.movie.tomatoes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.movie.AlternateLiveTester;

public class TomatoesInfoFetcherTest extends AlternateLiveTester {
    TomatoesInfoFetcher fetcher;

    public TomatoesInfoFetcherTest(boolean flag, boolean internalRedirects) {
        super(flag, internalRedirects);
        fetcher = new TomatoesInfoFetcher(loader);
    }
	
	@Test
	public void testGetMovieInfo() throws IOException {
		MoviePage page = fetcher.getMovieInfo("pulp_fiction");
		assertEquals("TOMATOES", page.getService().getId());
		assertTrue("starts with 'Outrageously violent, time-twisting, and in love with language, '", 
				page.getPlot().startsWith("Outrageously violent, time-twisting, and in love with language, "));
		System.out.println("score = "+page.getScore());
	        assertEquals("director count", 1, page.getDirectors().size());
	        assertEquals("director ","Quentin Tarantino", page.getDirectors().iterator().next());
	        assertEquals("Title", "Pulp Fiction", page.getTitle());
		
	}

	@Test
	public void testSearch() throws IOException {
		List<MovieSearchResult> results = fetcher.search("Pulp Fiction");
		assertEquals(5, results.size());
		assertEquals("Pulp Fiction", results.get(0).getTitle());
		assertEquals(Integer.valueOf(1994), results.get(0).getYear());
	}


}
