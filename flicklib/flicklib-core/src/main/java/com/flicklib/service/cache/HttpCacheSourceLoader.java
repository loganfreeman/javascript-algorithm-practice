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
package com.flicklib.service.cache;

import java.io.IOException;
import java.util.Map;

import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;

/**
 * Source loader which consult with a cache if it's possible before forwarding the request to the internal source loader.
 * 
 * @author zsombor
 *
 */
public abstract class HttpCacheSourceLoader implements SourceLoader {
	private final SourceLoader resolver;
	public HttpCacheSourceLoader(final SourceLoader resolver) {
		this.resolver = resolver;
	}

	@Override
	public Source loadSource(String url) throws IOException {
		return loadSource(url, true);
	}

	@Override
	public Source loadSource(String url, boolean useCache) throws IOException {
		Source source = null;
		if (useCache) {
			source = getFromCache(url);
		}
		if (source == null) {
			source = resolver.loadSource(url, useCache);
			put(url, source);
		}
		return source;
	}

	protected abstract Source getFromCache(String url);

	protected abstract void put(String url, Source source);

	@Override
	public Source post(String url, Map<String, String> parameters, Map<String, String> headers) throws IOException {
		return resolver.post(url, parameters, headers);
	}

}
