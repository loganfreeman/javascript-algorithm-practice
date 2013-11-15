package com.flicklib.service.movie.imdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.movie.AlternateLiveTester;

public class ImdbApiFetcherTest extends AlternateLiveTester {

	ImdbApiFetcher fetcher;

    public ImdbApiFetcherTest(boolean internalHttpClient, boolean internalRedirects) {
        super(internalHttpClient, internalRedirects);
        fetcher = new ImdbApiFetcher(loader);
    }
	
	@Test
	public void testSearchString() throws IOException {
		List<? extends MovieSearchResult> list = fetcher.search("Pulp Fiction");
		assertFalse("expecting results", list.isEmpty());
		assertEquals("Pulp Fiction", list.get(0).getTitle());
	}

	@Test
	public void testGetMovieInfo() throws IOException {
		MoviePage moviePage = fetcher.getMovieInfo("tt0110912");
		assertEquals("Pulp Fiction", moviePage.getTitle());
	}

}
