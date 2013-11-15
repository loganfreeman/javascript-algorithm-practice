/*
 * This file is part of Flicklib.
 *
 * Copyright (C) Francis De Brabandere
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
package com.flicklib.service.movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.flicklib.api.InfoFetcherFactory;
import com.flicklib.api.MovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;

/**
 * @author francisdb
 * 
 */
public class InfoFetcherFactoryImplTest {

    private InfoFetcherFactory factory;

    @Before
    public void setupFactory() {
        Set<MovieInfoFetcher> f = new HashSet<MovieInfoFetcher>();
        for (int i = 0; i < 3; i++) {
            MovieInfoFetcher fetcher = new MockInfoFetcher("mock_" + i);
            f.add(fetcher);
        }
        factory = new InfoFetcherFactoryImpl(f);
    }

    @Test
    public void testAllServices() {
        MovieInfoFetcher fetcher;
        for (MovieService service : MovieService.values()) {
            if (service instanceof MockMovieService) {
                fetcher = factory.get(service);
                Assert.assertNotNull("fetcher for " + service + " is null", fetcher);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        factory.get(null);
    }

    @After
    public void cleanup() {
        for (MovieService m : new ArrayList<MovieService>(MovieService.values())) {
            if (m instanceof MockMovieService) {
                ((MockMovieService) m).remove();
            }
        }
    }

    private static class MockMovieService extends MovieService {

        public MockMovieService(String id, String name, String url, String shortName) {
            super(id, name, url, shortName);
        }

        @Override
        public void remove() {
            // TODO Auto-generated method stub
            super.remove();
        }

    }

    private class MockInfoFetcher implements MovieInfoFetcher {

        MovieService dummy;

        MockInfoFetcher(String id) {
            this.dummy = new MockMovieService(id, "Name=" + id, "url=" + id, "shortName:" + id);
        }

        @Override
        public MoviePage fetch(String title) throws IOException {
            return null;
        }

        @Override
        public MoviePage getMovieInfo(String idForSite) throws IOException {
            return null;
        }

        @Override
        public List<? extends MovieSearchResult> search(String title) throws IOException {
            return null;
        }

        @Override
        public List<? extends MovieSearchResult> search(String title, String year) throws IOException {
            return null;
        }

        @Override
        public MovieService getService() {
            return dummy;
        }
    }

}
