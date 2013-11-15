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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;

public abstract class AbstractMovieInfoFetcher implements MovieInfoFetcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMovieInfoFetcher.class);

	public AbstractMovieInfoFetcher() {
	}

	@Override
	public List<? extends MovieSearchResult> search(String title, String year) throws IOException {
		return search(title);
	}
	
	@Override
	public MoviePage fetch(String title) throws IOException {
		MoviePage item = null;
		List<? extends MovieSearchResult> search = this.search(title);
		if (search.size() > 0) {
			MovieSearchResult firstResult = search.get(0);
			if (firstResult instanceof MoviePage) {
				item = (MoviePage) firstResult;
			}else{
				item = getMovieInfo(firstResult.getIdForSite());
			}
		}else{
			LOGGER.warn("No movie found for title: '"+title+"' using "+this.getClass().getSimpleName());
		}
		return item;
	}

}
