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
package com.flicklib.api;

import java.io.IOException;
import java.util.List;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;


/**
 * Base interface for classes that fetch movie information from a service
 * 
 * @author fdb
 */
public interface MovieInfoFetcher {
	
    /**
     * Fetches movie info from a service and complements the {@link MoviePage} object
     * 
     * @param title 
     * @return the parsed {@link MoviePage} or NULL if no such movie found
     * @throws IOException 
     */
    MoviePage fetch(String title) throws IOException;
    
    /**
     * Performs a search on the service and returns results. If more then basic info was available during the search
     * the returned list will contain {@link MoviePage}s
     * 
     * @param title
     * @return the result list, never null
     * @throws IOException
     */
    List<? extends MovieSearchResult> search(String title) throws IOException;
    
    /**
     * Performs a search on the service and returns results. If more then basic info was available during the search
     * the returned list will contain {@link MoviePage}s
     * 
     * @param title
     * @param year the year if available (use null for unknown)
     * @return the result list, never null
     * @throws IOException
     */
    List<? extends MovieSearchResult> search(String title, String year) throws IOException;
    
    /**
     * Looks up the detailed movie info for a movieIdForSite
     * 
     * @param idForSite the id the site is using for that movie
     * @return the {@link MoviePage}
     * @throws IOException
     */
    MoviePage getMovieInfo(String idForSite) throws IOException;
    
    /**
     * 
     * @return the service ID which this fetcher returns informations for.
     */
    MovieService getService();
}
