package com.flicklib.service.cache;

import com.flicklib.module.SourceLoaderSelector;
import com.flicklib.service.Constants;
import com.flicklib.service.SourceLoader;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class EHCacheSelector extends SourceLoaderSelector {

	@Inject
	public EHCacheSelector(@Named(value = Constants.USE_HTTPCOMPONENTS) boolean httpClient,
			@Named(value = Constants.HTTP_TIMEOUT) Integer timeOut) {

		super(httpClient, null, timeOut);
	}

	@Override
	protected SourceLoader wrapInCache(SourceLoader inner) {
		return new HttpEHCache(inner);
	}

}
