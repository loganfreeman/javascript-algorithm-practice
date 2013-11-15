package com.flicklib.service.movie.omdb;

import com.flicklib.api.MovieInfoFetcher;
import com.google.inject.AbstractModule;

public class OmdbModule extends AbstractModule {

	@Override
	protected void configure() {
		 bind(MovieInfoFetcher.class).annotatedWith(Omdb.class).to(OmdbFetcher.class);
	}

}
