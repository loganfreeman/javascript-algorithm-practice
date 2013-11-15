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
package com.flicklib.service.movie.porthu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.service.FileSourceLoader;
import com.flicklib.service.movie.AliasingSourceLoader;

/**
 * @author zsombor
 * 
 */
public class PorthuFetcherTest {

    private AliasingSourceLoader loader;
    private PorthuFetcher fetcher;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        loader = new AliasingSourceLoader(new FileSourceLoader("UTF-8"));
        loader.putAlias("http://port.hu/pls/fi/films.film_page?i_where=2&i_film_id=5609&i_city_id=3372&i_county_id=-1", "porthu/film_page.html");
        loader.putAlias("http://port.hu/pls/fi/films.film_page?i_where=2&i_film_id=80364&i_city_id=3372&i_county_id=-1", "porthu/film_page2.html");
        loader.putAlias("http://port.hu/pls/fi/films.film_page?i_where=2&i_film_id=1269&i_city_id=3372&i_county_id=-1", "porthu/film_page3.html");
        loader.putAlias("http://port.hu/pls/ci/cinema.film_creator?i_text=keresztapa&i_film_creator=1&i_city_id=3372", "porthu/filmsearch-response.html");
        loader.putAlias("http://port.hu/pls/ci/cinema.film_creator?i_text=a+kiraly+osszes+embere&i_film_creator=1&i_city_id=3372",
                "porthu/filmsearch-response2.html");
        loader.putAlias("http://port.hu/pls/fi/films.film_page?i_where=2&i_film_id=75033&i_city_id=3372&i_county_id=-1", "porthu/film_page4.html");
        loader.putAlias("http://port.hu/pls/fi/films.film_page?i_where=2&i_film_id=73833&i_city_id=3372&i_county_id=-1", "porthu/film_page5.html");
        loader.putAlias("http://port.hu/pls/fi/VOTE.print_vote_box?::i_area_id=6::i_is_separator=0::i_object_id=73833", "porthu/vote_object.html");
        
        loader.putAlias("http://port.hu/pls/fi/films.film_page?i_where=2&i_film_id=73834&i_city_id=3372&i_county_id=-1", "porthu/film_page6.html");
        //loader.putAlias("http://port.hu/pls/fi/vote.print_vote_box?i_object_id=73834&i_area_id=6&i_reload_container=id%3D%22vote_box%22&i_is_separator=0", "porthu/vote_object2.html");
        loader.putAlias("http://port.hu/pls/fi/vote.print_vote_box?::i_area_id=6::i_is_separator=0::i_object_id=73834::i_reload_container=id=\"vote_box\"", "porthu/vote_object2.html");
        fetcher = new PorthuFetcher(loader);
    }

    @Test
    public void testSearch() {
        try {
            List<MovieSearchResult> result = fetcher.search("keresztapa");

            assertNotNull("result not null", result);
            assertEquals("has enough results", 11, result.size());

            check(result.get(0), "A Keresztapa", "5609", "The Godfather", "színes magyarul beszélő amerikai gengszterfilm, 171 perc, 1972", 1972);
            check(result.get(10), "A blues - Keresztapák és fiaik", "67762", "The Blues - Godfather's And Sons", "amerikai filmsorozat, 96 perc, 2003", 2003);

            result = fetcher.search("a kiraly osszes embere");
            assertNotNull("result not null", result);
            assertEquals("has enough results", 3, result.size());
            check(result.get(0), "A király összes embere", "83459", "All the King's Men",
                    "színes, fekete-fehér magyarul beszélő német-amerikai filmdráma, 128 perc, 2006", 2006);
            check(result.get(1), "A király összes embere", "39441", "All the King's Men", "fekete-fehér feliratos amerikai filmdráma, 105 perc, 1949", 1949);
            check(result.get(2), "All the King's Men", "40444", "", "színes amerikai-angol háborús filmdráma, 110 perc, 1999", 1999);

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    void check(MovieSearchResult m, String alternateTitle, String id, String title, String description, int year) {
        assertEquals("title", title, m.getTitle());
        assertEquals("id", id, m.getIdForSite());
        assertEquals("alternate title", alternateTitle, m.getAlternateTitle());
        assertEquals("description", description, m.getDescription());
        assertEquals("year", Integer.valueOf(year), m.getYear());
    }

    @Test
    @Ignore
    public void testGetMovieInfo() {
        try {
            MoviePage moviePage = fetcher.getMovieInfo("5609");
            assertNotNull("movie page", moviePage);
            assertEquals("service type", "PORTHU", moviePage.getService().getId());
            assertEquals("title", "A Keresztapa", moviePage.getAlternateTitle());
            assertEquals("year", Integer.valueOf(1972), moviePage.getYear());
            assertNotNull("has plot", moviePage.getPlot());
            assertTrue("plot", moviePage.getPlot().startsWith("A gengszterfilmek legnagyobbika, világhírű"));
            assertTrue("director", moviePage.getDirectors().contains("Francis Ford Coppola"));
            assertEquals("score", Integer.valueOf(94), moviePage.getScore());
            assertEquals("votes", Integer.valueOf(80), moviePage.getVotes());
            assertGenres(moviePage.getGenres(), "amerikai", "gengszterfilm");
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void testGetMovieInfo2() {
        try {
            MoviePage moviePage = fetcher.getMovieInfo("80364");
            assertNotNull("movie page", moviePage);
            assertEquals("service type", "PORTHU", moviePage.getService().getId());
            assertEquals("title", "Parfüm: Egy gyilkos története", moviePage.getAlternateTitle());
            assertEquals("alternate title", "Perfume: The Story of a Murderer", moviePage.getTitle());
            assertEquals("year", Integer.valueOf(2006), moviePage.getYear());
            assertNotNull("has plot", moviePage.getPlot());
            assertTrue("plot", moviePage.getPlot().startsWith("1766-ban, a franciaországi Grasse városban a parfűmkészítő"));
            assertTrue("director", moviePage.getDirectors().contains("Tom Tykwer"));
            assertEquals("score", Integer.valueOf(80), moviePage.getScore());
            assertEquals("votes", Integer.valueOf(114), moviePage.getVotes());
            assertGenres(moviePage.getGenres(), "német", "francia", "spanyol", "filmdráma");

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void testGetMovieInfo3() {
        try {
            MoviePage moviePage = fetcher.getMovieInfo("1269");
            assertNotNull("movie page", moviePage);
            assertEquals("service type", "PORTHU", moviePage.getService().getId());
            assertEquals("title", "Star Trek 8. - Kapcsolatfelvétel", moviePage.getAlternateTitle());
            assertEquals("alternate title", "Star Trek: First Contact", moviePage.getTitle());
            assertEquals("year", Integer.valueOf(1996), moviePage.getYear());
            assertNotNull("has plot", moviePage.getPlot());
            assertTrue("plot", moviePage.getPlot().startsWith("A Star Trek filmek legújabb és magasan legjobb darabja"));
            assertTrue("director", moviePage.getDirectors().contains("Jonathan Frakes"));
            assertEquals("score", Integer.valueOf(100), moviePage.getScore());
            assertEquals("votes", Integer.valueOf(4), moviePage.getVotes());
            assertGenres(moviePage.getGenres(), "amerikai", "akciófilm");

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void testGetMovieInfo4() {
        try {
            MoviePage moviePage = fetcher.getMovieInfo("75033");
            assertNotNull("movie page", moviePage);
            assertEquals("service type", "PORTHU", moviePage.getService().getId());
            assertEquals("title", "Terkel in Trouble", moviePage.getTitle());
            assertEquals("alternate title", "Terhelt Terkel", moviePage.getAlternateTitle());
            assertEquals("original title", "Terkel i knibe", moviePage.getOriginalTitle());
            assertEquals("year", Integer.valueOf(2004), moviePage.getYear());
            assertNotNull("has plot", moviePage.getPlot());
            assertTrue("plot", moviePage.getPlot().startsWith("Végy egy göndör hajú, pattanásos tizenhét éves tinédzsert"));
            assertTrue("director", moviePage.getDirectors().contains("Kresten Vestbjerg Andersen"));
            assertEquals("score", Integer.valueOf(78), moviePage.getScore());
            assertEquals("votes", Integer.valueOf(9), moviePage.getVotes());
            assertEquals("runtime", Integer.valueOf(78), moviePage.getRuntime());
            assertGenres(moviePage.getGenres(), "dán", "animációsfilm");

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void testGetMovieInfo5() {
        try {
            MoviePage moviePage = fetcher.getMovieInfo("73833");
            Assert.assertEquals("title", "Syriana", moviePage.getTitle());
            Assert.assertEquals("title", "Sziriana", moviePage.getAlternateTitle());
            Assert.assertEquals("year", Integer.valueOf(2005), moviePage.getYear());
            Assert.assertEquals("runtime", Integer.valueOf(126), moviePage.getRuntime());
            Assert.assertNotNull("scores", moviePage.getScore());
            Assert.assertNotNull("votes", moviePage.getVotes());
            assertGenres(moviePage.getGenres(), "amerikai", "filmdráma");

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testMovieFetch_2011_oct_17() {
        //73833
        try {
            MoviePage info = fetcher.getMovieInfo("73834");
            Assert.assertEquals("title","Syriana", info.getTitle());
            Assert.assertEquals("title","Sziriana", info.getAlternateTitle());
            Assert.assertEquals("year", Integer.valueOf(2005), info.getYear());
            //Assert.assertEquals("score", Integer.valueOf(74), info.getScore());
            Assert.assertNotNull("score", info.getScore());
            Assert.assertNotNull("votes", info.getVotes());
            Assert.assertTrue("votes=>20", info.getVotes().intValue() >= 20);
            Assert.assertNotNull("plot", info.getPlot());
            Assert.assertTrue("plot", info.getPlot().startsWith("21 év a CIA szolgálatában kiélezi az érzékeket. Bob Barnes ügynök"));
            
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        
    }
    

    private void assertGenres(Set<String> genres, String... expected) {
        for (String g : expected) {
            assertTrue("genres contains " + g, genres.contains(g));
        }

    }

}
