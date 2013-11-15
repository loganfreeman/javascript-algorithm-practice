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
package com.flicklib.service.movie.apple;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.flicklib.service.TestUtil;
import com.google.gson.Gson;

public class AppleTrailerFinderTest {

    /**
     * Test of findTrailerUrl method, of class AppleTrailerFinder.
     */
    @Test
    public void testFindTrailerUrl() {
    	Gson gson = new Gson();
        AppleTrailerFinder instance = new AppleTrailerFinder(TestUtil.createLoader(), gson);
        String url = instance.findTrailerUrl("The descendants", null);
        assertEquals("http://trailers.apple.com/trailers/fox_searchlight/thedescendants/", url);
    }

}