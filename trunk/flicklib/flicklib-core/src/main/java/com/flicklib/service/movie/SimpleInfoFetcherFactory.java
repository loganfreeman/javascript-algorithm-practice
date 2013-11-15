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
package com.flicklib.service.movie;

import com.flicklib.service.SourceLoader;
import com.flicklib.service.movie.cinebel.CinebelFetcher;
import com.flicklib.service.movie.flixter.FlixsterInfoFetcher;
import com.flicklib.service.movie.google.GoogleInfoFetcher;
import com.flicklib.service.movie.imdb.ImdbInfoFetcher;
import com.flicklib.service.movie.movieweb.MovieWebInfoFetcher;
import com.flicklib.service.movie.ofdb.OfdbFetcher;
import com.flicklib.service.movie.porthu.PorthuFetcher;
import com.flicklib.service.movie.tomatoes.TomatoesInfoFetcher;
import com.flicklib.service.movie.xpress.XpressHuFetcher;

/**
 * Lets you use flicklib without using Guice
 *
 */
public class SimpleInfoFetcherFactory extends InfoFetcherFactoryImpl{

    public SimpleInfoFetcherFactory(final SourceLoader sourceLoader) {
        super();
        add(new ImdbInfoFetcher(sourceLoader));
        add(new MovieWebInfoFetcher(sourceLoader));
        add(new TomatoesInfoFetcher(sourceLoader));
        add(new GoogleInfoFetcher(sourceLoader));
        add(new FlixsterInfoFetcher(sourceLoader));
        add(new PorthuFetcher(sourceLoader));
        add(new CinebelFetcher(sourceLoader));
        add(new OfdbFetcher(sourceLoader));
        add(new XpressHuFetcher(sourceLoader));
    }
    
    

}
