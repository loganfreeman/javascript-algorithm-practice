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

import net.htmlparser.jericho.Source;

import com.flicklib.api.Parser;
import com.flicklib.domain.MoviePage;

/**
 *
 * @author francisdb
 */
public abstract class AbstractJerichoParser implements Parser{

    @Override
    public final void parse(com.flicklib.service.Source htmlSource, MoviePage movieSite) {
        Source source = htmlSource.getJerichoSource();
        //source.setLogWriter(new OutputStreamWriter(System.err)); // send log messages to stderr
        parse(source, movieSite);
    }


/**
     * Parses jericho source to MovieSite
 * @param source
 * @param movieSite
     */
    public abstract void parse(Source source, MoviePage movieSite);
}
