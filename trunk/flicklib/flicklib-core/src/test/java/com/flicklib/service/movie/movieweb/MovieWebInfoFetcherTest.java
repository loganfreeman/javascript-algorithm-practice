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
package com.flicklib.service.movie.movieweb;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.flicklib.domain.MoviePage;
import com.flicklib.service.TestUtil;

/**
 *
 * @author francisdb
 */
public class MovieWebInfoFetcherTest {


    /**
     * Test of load method, of class MovieWebInfoFetcher.
     * @throws IOException 
     */
    @Test
    public void testFetch() throws IOException {
        MovieWebParser parser = new MovieWebParser();
        MovieWebInfoFetcher fetcher = new MovieWebInfoFetcher(parser, TestUtil.createLoader());
        MoviePage site = fetcher.fetch("Pulp Fiction");
        Assert.assertNotNull("site ",site);
        Assert.assertEquals("title", "Pulp Fiction Blu-ray", site.getTitle());
        Assert.assertNotNull("score", site.getScore());
        Assert.assertEquals("imgUrl", "http://c181311.r11.cf0.rackcdn.com/DVHtxZN5RWSAMI_1_m.jpg", site.getImgUrl());
    }

}