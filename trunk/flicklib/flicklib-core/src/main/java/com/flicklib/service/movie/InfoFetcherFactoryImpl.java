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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.InfoFetcherFactory;
import com.flicklib.api.MovieInfoFetcher;
import com.flicklib.domain.MovieService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * 
 * @author francisdb
 */
@Singleton
public class InfoFetcherFactoryImpl implements InfoFetcherFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfoFetcherFactoryImpl.class);

    private final Map<MovieService, MovieInfoFetcher> services = new HashMap<MovieService, MovieInfoFetcher>();

    /**
     * Constructs a new InfoFetcherFactoryImpl
     * 
     */
    @Inject
    public InfoFetcherFactoryImpl(Set<MovieInfoFetcher> fetchers) {
        for (MovieInfoFetcher f : fetchers) {
            add(f);
        }
    }
    
    protected InfoFetcherFactoryImpl() {
    }

    protected void add(MovieInfoFetcher f) {
        services.put(f.getService(), f);
    }

    @Override
    public MovieInfoFetcher get(MovieService service) {
        MovieInfoFetcher fetcher = services.get(service);
        if (fetcher == null) {
            LOGGER.warn("No fetcher defined for service " + service);
            throw new IllegalArgumentException("Unknown service: " + service);
        }
        return fetcher;
    }

}
