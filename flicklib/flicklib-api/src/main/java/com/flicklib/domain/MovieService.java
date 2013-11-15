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
package com.flicklib.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author francisdb
 */
public class MovieService {
    
    private final static Logger LOG = LoggerFactory.getLogger(MovieService.class);
    
    private static Map<String, MovieService> services = new LinkedHashMap<String,MovieService>();

    private final String id;
    private final String name;
    private final String url;
    private final String shortName;
    
    public MovieService(String id, String name, String url, String shortName) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.shortName = shortName;
        synchronized(services) {
            if (services.containsKey(id)) {
                throw new RuntimeException("MovieService with ID:"+id+" is already registered:"+services.get(id));
            }
            LOG.info("registering "+this);
            services.put(id, this);
        }
    }
    
    public MovieService(String id, String name, String url) {
        this(id, name, url, name);
    }
    

    public String getId() {
        return id;
    }

    public static MovieService getById(String id) {
        return services.get(id);
    }
    
    public static Collection<MovieService> values() {
        return services.values();
    }
    
    protected void remove() {
        LOG.warn("remove called for "+this+", services registered previously :" + services+" for ");
        services.remove(this.getId());
    }
    
    
    /**
     * Gets the full name for this service
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the main site url without trailing slash
     * @return the url
     */
    public String getUrl(){
        return url;
    }
    
    /**
     * Gets the short name for this service
     * @return the short name
     */
    public String getShortName() {
        return shortName;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj==this;
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return "MovieService["+id+']';
    }
    

    public static MovieService valueOf(String id) {
        MovieService s = services.get(id);
        if (s == null) {
            throw new IllegalArgumentException("Service description for id:"+id+" not found!");
        }
        return s;
    }
    
}
