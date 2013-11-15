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
package com.flicklib.service.sub;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.SubtitlesLoader;
import com.flicklib.domain.Subtitle;
import com.flicklib.service.TestUtil;

/**
 *
 * @author francisdb
 */
public class OpenSubtitlesLoaderTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenSubtitlesLoaderTest.class);

    SubtitlesLoader loader = null;
    
    @Before
    public void setup() {
    	loader = new OpenSubtitlesLoader(TestUtil.createLoader());
    }

    /**
     * Test of search method, of class OpenSubtitlesLoader.
     * @throws Exception 
     */
    @Test
    public void testSearch() throws Exception {
        Set<Subtitle> result = loader.search("The Science of Sleep", null);
        assertTrue(result.size() > 0);
        for(Subtitle sub:result){
            LOGGER.info(sub.getFileName());
        }        
    }
    
    @Test
    public void testSearch2() throws Exception {
    	Set<Subtitle> result = loader.search("The.Science.of.Sleep.LIMITED.DVDRip.XViD.-iMBT.avi", null);
        //result = loader.search("The.Science.of.Sleep.LIMITED.DVDRip.XViD", null);
        assertTrue(result.size() > 0);
        for(Subtitle sub:result){
            LOGGER.info(sub.getFileName());
        }
    }
    
    @Test
    public void testSearch3() throws Exception {
    	Set<Subtitle> result = loader.search("X-Men", null);
        assertTrue(result.size() > 0);
        for(Subtitle sub:result){
            LOGGER.info(sub.getFileName());
        }
    }

}