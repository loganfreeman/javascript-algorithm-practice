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
package com.flicklib.service.movie.netflix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import com.flicklib.domain.MoviePage;

/**
 * @author francisdb
 *
 */
public class NetflixInfoFetcherTest {

	/**
	 * Test method for {@link com.flicklib.service.movie.netflix.NetflixInfoFetcher#fetch(String)}.
	 * @throws IOException 
	 */
    @Test
    public void testFetch() throws IOException {
    	// Please use your own key, request one at: http://developer.netflix.com
    	String apikey = "sam8gkjk5zc2sspw2m7p9n8u";
    	String sharedsecret = "aZufM9NU62";
    	
        NetflixInfoFetcher fetcher = new NetflixInfoFetcher(apikey, sharedsecret);
        MoviePage site = fetcher.fetch("Pulp Fiction");
        assertNotNull("Score is null", site.getScore());
        assertNotNull("ID is null", site.getIdForSite());
        assertNotNull("ImgUrl is null", site.getImgUrl());
        assertNotNull("Url is null", site.getUrl());
        assertEquals("NETFLIX", site.getService().getId());
        System.out.println(site.getScore());
        System.out.println(site.getUrl());
    }

}
