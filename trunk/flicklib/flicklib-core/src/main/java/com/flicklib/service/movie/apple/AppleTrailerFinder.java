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
package com.flicklib.service.movie.apple;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.TrailerFinder;
import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;
import com.flicklib.tools.Param;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AppleTrailerFinder implements TrailerFinder {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppleTrailerFinder.class);

	private final SourceLoader loader;
	private final Gson gson;

	@Inject
	public AppleTrailerFinder(final SourceLoader loader, final Gson gson) {
		this.loader = loader;
		this.gson = gson;
	}

	@Override
	public String findTrailerUrl(String title, String localId) {
		try {
			GoogleResponse response = getJSON(title);
			List<GoogleSearchResult> results = response.responseData.results;
			for (GoogleSearchResult result:results) {
				if (result.unescapedUrl.startsWith("http://trailers.apple.com")) {
					return result.unescapedUrl;
				}
			}
		} catch (IOException e) {
			LOGGER.info("error :" + e.getMessage(), e);
		} catch (JsonSyntaxException e) {
			LOGGER.info("error :" + e.getMessage(), e);
		}
		
		return null;
	}

	private GoogleResponse getJSON(String title) throws IOException {
		//String url = "http://trailers.apple.com/trailers/home/scripts/quickfind.php?"+Param.paramString("q", title);
		String url = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q="+Param.encode(title+ " site:apple.com");
		Source src = loader.loadSource(url, true);
		return gson.fromJson(src.getContent(), GoogleResponse.class);
	}

}
