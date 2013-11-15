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
package com.flicklib.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.flicklib.api.InfoFetcherFactory;
import com.flicklib.api.MovieInfoFetcher;
import com.flicklib.domain.MovieService;
import com.flicklib.module.FlicklibModule;
import com.flicklib.module.NetFlixAuthModule;
import com.flicklib.service.cache.PersistentCacheSourceLoader;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Testing guice configuration.
 * 
 * @author zsombor
 *
 */
public class ModuleLoading {

    @Test
    public void testSourceLoaderDefaults() {
        FlicklibModule f = new FlicklibModule();
        Injector injector = Guice.createInjector(new NetFlixAuthModule("",""), f);
        
        SourceLoader instance = injector.getInstance(SourceLoader.class);
        
        assertNotNull("sourceLoader", instance);
        assertEquals("sourceLoader class is", HttpClientSourceLoader.class, instance.getClass());
    }
    

    @Test
    public void testUseJavaNetUrlLoader() {
        FlicklibModule f = new FlicklibModule().setUseHttpComponents(false);
        Injector injector = Guice.createInjector(new NetFlixAuthModule("",""), f);
        
        SourceLoader instance = injector.getInstance(SourceLoader.class);
        
        assertNotNull("sourceLoader", instance);
        assertEquals("sourceLoader class is", UrlConnectionResolver.class, instance.getClass());
    }
    
    @Test
    public void testUsePersistentCache() {
        FlicklibModule f = new FlicklibModule().setCacheRoot(System.getProperty("java.io.tmpdir")+"/.flicklib.test");
        Injector injector = Guice.createInjector(new NetFlixAuthModule("",""), f);
        
        SourceLoader instance = injector.getInstance(SourceLoader.class);
        
        assertNotNull("sourceLoader", instance);
        assertEquals("sourceLoader class is", PersistentCacheSourceLoader.class, instance.getClass());
    }
    
    @Test
    public void testImdbLoader() {
        FlicklibModule f = new FlicklibModule();
        Injector injector = Guice.createInjector(new NetFlixAuthModule("",""), f);
        
        InfoFetcherFactory instance = injector.getInstance(InfoFetcherFactory.class);
        assertNotNull("InfoFetcherFactory", instance);

        String[] SERVICE_NAMES = new String[] { "IMDB", "PORTHU", "XPRESSHU", "BLIPPR", "CINEBEL" };
        for (String service : SERVICE_NAMES) {
            MovieInfoFetcher infoFetcher = instance.get(MovieService.getById(service));
            assertNotNull("infoFetcher["+service+']', infoFetcher);
        }
    }

}
