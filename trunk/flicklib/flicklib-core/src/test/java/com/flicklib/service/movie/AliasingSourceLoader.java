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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;

/**
 * Wrapper class for easy url aliasing
 * 
 * @author gzsombor
 *
 */
public class AliasingSourceLoader implements SourceLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliasingSourceLoader.class);

    private final SourceLoader parent;
    private final Map<String, String> mapping;

    public AliasingSourceLoader(SourceLoader parent) {
        super();
        this.mapping = new HashMap<String, String>();
        this.parent = parent;
    }

    public void putAlias(String from, String to) {
        mapping.put(from, to);
    }
    
    
    @Override
    public Source loadSource(String url) throws IOException {
        return this.loadSource(url, true);
    }
    
    @Override
    public Source loadSource(String url, boolean useCache) throws IOException {
        String result = mapping.get(url);
        if (result != null) {
            LOGGER.info("loading " + url + " from " + result);
            url = result;
        } else {
            LOGGER.info("loading " + url);
        }
        return parent.loadSource(url, useCache);
    }
    
    @Override
    public Source post(String url, Map<String, String> parameters, Map<String, String> headers) throws IOException {
        StringBuilder s = new StringBuilder(url);
        for (String key : new TreeSet<String>(parameters.keySet())) {
            s.append("::").append(key).append('=').append(parameters.get(key));
        }
        return loadSource(s.toString());
    }
    
    @Override
    public RestBuilder url(String url) {
    	throw new UnsupportedOperationException("not implemented");
    }
    
}
