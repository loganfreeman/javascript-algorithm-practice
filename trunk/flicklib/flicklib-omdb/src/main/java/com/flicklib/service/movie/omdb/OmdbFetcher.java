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
package com.flicklib.service.movie.omdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.Client;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Preference;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.api.MovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;

/**
 *
 * @author francisdb
 */
public class OmdbFetcher extends AbstractMovieInfoFetcher {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieInfoFetcher.class);

    

    
    @Override
    public MoviePage getMovieInfo(String id) throws IOException {
        //MoviePage site = new MoviePage(MovieService.MOVIEWEB);
        throw new RuntimeException("Not implemented");
    }
    
    @Override
    public List<MovieSearchResult> search(String title) throws IOException {

        //site.setMovie(movie);
        // Outputting the content of a Web page
        // Prepare the request
        Request request = new Request(Method.GET, "http://www.omdb-beta.org/search/movies?query=test");
        Preference<MediaType> preference2 = new Preference<MediaType>(MediaType.APPLICATION_XML); // MediaType.TEXT_XML
        List<Preference<MediaType>> types = new ArrayList<Preference<MediaType>>();
        types.add(preference2);
        //types.add(preference3);
        request.getClientInfo().setAcceptedMediaTypes(types);
        request.setReferrerRef("http://www.mysite.org");

        Client client = new Client(Protocol.HTTP);
        try {
            Response response = client.handle(request);
            LOGGER.info(response.getAllowedMethods().toString());
            LOGGER.info(response.getStatus().toString());
            Representation entity = response.getEntity();
            entity.write(System.out);
            // todo set score....
        } catch (IOException ex) {
            LOGGER.error("Could not load rest",ex);
        }
        return new ArrayList<MovieSearchResult>();
    }


}
