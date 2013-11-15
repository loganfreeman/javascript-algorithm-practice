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
package com.flicklib.module;

import com.flicklib.service.Constants;
import com.flicklib.service.HttpClientSourceLoader;
import com.flicklib.service.SourceLoader;
import com.flicklib.service.UrlConnectionResolver;
import com.flicklib.service.cache.PersistentCacheSourceLoader;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * This class provides SourceLoader for the guice.
 * @author zsombor
 *
 */
public class SourceLoaderSelector implements Provider<SourceLoader> {

    boolean httpClient = true;
    
    String cacheRoot = null;
    
    Integer timeout = null;
    
    public SourceLoaderSelector(String cacheRoot) {
        this.cacheRoot = cacheRoot;
    }
    
    @Inject
    public SourceLoaderSelector(
            @Named(value = Constants.USE_HTTPCOMPONENTS) boolean httpClient, 
            @Named(value = Constants.CACHE_ROOT) String cacheRoot, 
            @Named(value = Constants.HTTP_TIMEOUT) Integer timeOut) {
        super();
        this.httpClient = httpClient;
        this.cacheRoot = cacheRoot;
        this.timeout = timeOut;
    }

    public SourceLoaderSelector(boolean httpClient) {
        this(httpClient, null, null);
    }



    @Override
    public SourceLoader get() {
        SourceLoader inner = getInnerSourceLoader();
        return wrapInCache(inner);
    }
    
    protected SourceLoader wrapInCache(SourceLoader inner) {
        if (cacheRoot != null && cacheRoot.trim().length() > 0) {
            return new PersistentCacheSourceLoader(inner, cacheRoot);
        }
        return inner;
    }

    protected SourceLoader getInnerSourceLoader() {
        return httpClient ? new HttpClientSourceLoader(timeout) : new UrlConnectionResolver(timeout);
    }
}
