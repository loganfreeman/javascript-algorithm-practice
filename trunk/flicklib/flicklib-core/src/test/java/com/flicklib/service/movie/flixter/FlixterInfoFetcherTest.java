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
package com.flicklib.service.movie.flixter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.service.TestUtil;

public class FlixterInfoFetcherTest {

    /**
     * Test of fetch method, of class FlixsterInfoFetcher.
     * @throws IOException 
     */
    @Test
    public void testFetch() throws IOException {
        FlixsterParser parser = new FlixsterParser();
        FlixsterInfoFetcher fetcher = new FlixsterInfoFetcher(parser, TestUtil.createLoader());
        MoviePage site = fetcher.fetch("The X-Files I Want to Believe");
        assertNotNull(site);
        assertTrue(site.getUrl().contains("http://www.flixster.com/movie/the-x-files-i-want-to-believe-the-x-files-2"));
        assertNotNull("MovieWebStars is null", site.getScore());
        assertNotNull("Votes ", site.getVotes());
        assertNotNull("img url", site.getImgUrl());

        site = fetcher.fetch("pulp fiction");
        assertNotNull("movie info for 'pulp fiction'", site);
        assertNotNull("site.url", site.getUrl());
        assertTrue(site.getUrl().contains("http://www.flixster.com/movie/pulp-fiction"));
        assertEquals("Pulp Fiction", site.getTitle());
        
    }

}