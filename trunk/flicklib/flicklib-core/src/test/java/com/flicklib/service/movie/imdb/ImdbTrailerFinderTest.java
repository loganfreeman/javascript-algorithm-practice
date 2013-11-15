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
package com.flicklib.service.movie.imdb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.flicklib.domain.MoviePage;

public class ImdbTrailerFinderTest {

    /**
     * Test of findTrailerUrl method, of class ImdbTrailerFinder.
     */
    @Test
    public void testFindTrailerUrl() {
        ImdbTrailerFinder instance = new ImdbTrailerFinder();
        MoviePage site = new MoviePage();
        site.setIdForSite("123");
        String url = instance.findTrailerUrl(site.getTitle(), site.getIdForSite());
        assertEquals("http://www.imdb.com/title/tt123/trailers", url);
    }

}