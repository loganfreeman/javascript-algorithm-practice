package com.flicklib.service.movie.imdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieType;
import com.flicklib.service.SourceLoader;
import com.flicklib.service.TestUtil;
import com.flicklib.service.cache.LoggingHttpCache;


public class ImdbInfoFetcherTest {
    private SourceLoader    loader;
    private ImdbInfoFetcher fetcher;
	    
	@Before
	public void setUp() throws Exception {
		loader = TestUtil.createLoader();
		if (System.getProperty("flicklib.trace") != null) {
			File tempDir = new File(System.getProperty("java.io.tmpdir"));
			File logDir = new File(tempDir, "flicklib-http-cache");
			logDir.mkdirs();
			loader = new LoggingHttpCache(loader, logDir);
		}
		fetcher = new ImdbInfoFetcher(loader);
	}

		@Test
		public void testSearchString() throws IOException {
			List<? extends MovieSearchResult> res = fetcher.search("Twin Peaks");
			for(MovieSearchResult result:res){
				assertEquals("IMDB", result.getService().getId());
				//System.out.println(result.getTitle()+" / "+result.getOriginalTitle()+" / "+result.getYear()+" / "+result.getType());
			}
			assertEquals("Twin Peaks", res.get(0).getTitle());
			//assertEquals("Twin Peaks", res.get(0).getOriginalTitle());
			assertEquals(Integer.valueOf(1990), res.get(0).getYear());

			assertEquals(MovieType.TV_SERIES, res.get(0).getType());
			assertEquals(MovieType.MOVIE, res.get(1).getType());
			
			
			List<? extends MovieSearchResult> res2 = fetcher.search("mar adentro");
			for(MovieSearchResult result:res2){
				assertEquals("IMDB", result.getService().getId());
				assertNotNull(result.getIdForSite());
				System.out.println(result.getTitle()+" / "+result.getOriginalTitle()+" / "+result.getYear()+" / "+result.getType());
			}
			
		}

		@Test
		public void testGetMovieInfo() throws IOException {
			MoviePage page = fetcher.getMovieInfo("0133093");
			assertEquals("IMDB", page.getService().getId());
			assertEquals("The Matrix", page.getPreferredTitle());
			assertEquals(2, page.getDirectors().size());
			assertTrue(page.getDirectors().contains("Andy Wachowski"));
			assertTrue(page.getDirectors().contains("Lana Wachowski"));
			
			assertTrue(page.getActors().contains("Keanu Reeves"));
			assertTrue(page.getActors().contains("Laurence Fishburne"));
			assertTrue(page.getActors().contains("Carrie-Anne Moss"));
			
			assertNotNull(page.getScore());
			assertNotNull(page.getPlot());
			assertTrue(page.getGenres().contains("Action"));
			assertTrue(page.getGenres().contains("Adventure"));
			assertTrue(page.getGenres().contains("Sci-Fi"));
			assertNotNull(page.getIdForSite());
			
		}
		
		@Test
		public void testIssue107() throws IOException {
			List<MovieSearchResult> search = fetcher.search("santa claus is comin' to town");
			assertNotNull("searchResult ", search);
		}
}
