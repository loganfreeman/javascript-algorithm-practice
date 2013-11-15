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
package com.flicklib.service.cache;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HttpEHCache extends HttpCacheSourceLoader {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpEHCache.class);

	private final CacheManager manager;
	private final Cache cache;

	@Inject
	public HttpEHCache(
			final SourceLoader resolver) {
		super(resolver);
		URL url = getClass().getResource("/ehcache-flicklib.xml");
		manager = new CacheManager(url);
		manager.addCache("httpCache");
		cache = manager.getCache("httpCache");
		LOGGER.debug("Started cache, " + cache.getSize() + " pages cached.");

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				LOGGER.debug(cache.getStatistics().toString());
				try {
					manager.shutdown();
				} catch (Exception e) {
					LOGGER.info("error during shutdown:" + e.getMessage(), e);
				}
				LOGGER.debug("shut down cache.");
			}
		});
	}

	@Override
	protected Source getFromCache(String url) {
		Element element = cache.get(url);
		return (Source) (element != null ? element.getObjectValue() : null);
	}
	

	@Override
	protected void put(String url, Source page) {
		LOGGER.debug("Caching result for " + url + " (" + (page != null ? page.getContentType() : "null")
				+ ")");
		cache.put(new Element(url, page));
	}
	
    @Override
    public RestBuilder url(String url) {
    	throw new UnsupportedOperationException("not implemented");
    }

}
