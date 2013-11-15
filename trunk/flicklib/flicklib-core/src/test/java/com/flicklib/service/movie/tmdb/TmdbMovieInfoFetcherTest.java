package com.flicklib.service.movie.tmdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.HttpClientSourceLoader;
import com.flicklib.service.SourceLoader;

public class TmdbMovieInfoFetcherTest {
	
	private SourceLoader loader;
    private TmdbMovieInfoFetcher fetcher;
    
    public TmdbMovieInfoFetcherTest() {
    	loader = new HttpClientSourceLoader((int) TimeUnit.SECONDS.toMillis(10));
		String apiKey = "yourkeyhere";
		this.fetcher = new TmdbMovieInfoFetcher(loader, apiKey);
	}

	@Test
	@Ignore
	public void testSearchStringString() throws IOException {
		List<? extends MovieSearchResult> movies = fetcher.search("Pulp Fiction", "1994");
		assertTrue(movies.size() > 0);
		assertEquals("Pulp Fiction", movies.get(0).getTitle());
	}

	@Test
	@Ignore
	public void testGetMovieInfo() throws IOException {
		MoviePage page = fetcher.getMovieInfo("680");
		assertEquals("Pulp Fiction", page.getTitle());
	}

}
