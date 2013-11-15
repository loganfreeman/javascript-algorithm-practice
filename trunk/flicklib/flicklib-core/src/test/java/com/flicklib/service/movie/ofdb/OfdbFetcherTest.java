package com.flicklib.service.movie.ofdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieType;
import com.flicklib.service.SourceLoader;
import com.flicklib.service.TestUtil;

public class OfdbFetcherTest {
	private final static Logger LOG = LoggerFactory.getLogger(OfdbFetcherTest.class);
	
    private SourceLoader loader;
    private OfdbFetcher fetcher;

    
    @Before
    public void setUp() throws Exception {
        loader = TestUtil.createLoader();
        fetcher = new OfdbFetcher(loader, new OfdbParser());
    }
    


	@Test
	public void testSearchString() throws IOException {
		List<? extends MovieSearchResult> res = fetcher.search("Twin Peaks");
		for(MovieSearchResult result:res){
			assertEquals("OFDB", result.getService().getId());
			LOG.info("result: " + result.getTitle() + " / " + result.getOriginalTitle() + " / " + result.getYear() + " / " + result.getType());
		}
		assertEquals("Geheimnis von Twin Peaks, Das", res.get(0).getTitle());
		assertEquals("Twin Peaks", res.get(0).getOriginalTitle());
		assertEquals(Integer.valueOf(1990), res.get(0).getYear());
		assertEquals(MovieType.MOVIE, res.get(0).getType());
		
		assertEquals(MovieType.TV_SERIES, res.get(1).getType());
		
		List<? extends MovieSearchResult> res2 = fetcher.search("mar adentro");
		for(MovieSearchResult result:res2){
			assertEquals("OFDB", result.getService().getId());
			assertNotNull(result.getIdForSite());
			LOG.info("result 2:" + result.getTitle() + " / " + result.getOriginalTitle() + " / " + result.getYear() + " / " + result.getType());
		}
		
	}
	

	@Test
	public void testGetMovieInfo() throws IOException {
		MoviePage page = fetcher.getMovieInfo("1050,Pulp-Fiction");
		assertEquals("OFDB", page.getService().getId());
		assertEquals("Pulp Fiction", page.getTitle());
		assertNotNull(page.getScore());
		assertNotNull(page.getPlot());
		assertNotNull(page.getDescription());

		page = fetcher.getMovieInfo("3635,Dune---Der-Wüstenplanet");
                assertEquals("OFDB", page.getService().getId());
		assertEquals(MovieType.MINI_SERIES, page.getType());
		assertEquals("Dune - Der Wüstenplanet", page.getAlternateTitle());
		assertEquals("Dune", page.getTitle());
		assertEquals("Dune", page.getOriginalTitle());
		
		assertEquals(1, page.getDirectors().size());
		assertTrue(page.getDirectors().contains("John Harrison"));
		
		assertTrue(page.getActors().contains("William Hurt"));
		assertTrue(page.getActors().contains("Alec Newman"));
		assertTrue(page.getActors().contains("Saskia Reeves"));
		
		assertEquals("http://img.ofdb.de/film/3/3635.jpg", page.getImgUrl());
		assertEquals(Integer.valueOf(2000), page.getYear());
		assertTrue(page.getGenres().contains("Abenteuer"));
		assertTrue(page.getGenres().contains("Mystery"));
		assertNotNull(page.getScore());
		assertNotNull(page.getPlot());
		assertNotNull(page.getDescription());
		assertNotNull(page.getIdForSite());
		
	}

}
