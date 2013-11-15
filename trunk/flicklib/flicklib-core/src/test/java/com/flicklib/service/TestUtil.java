package com.flicklib.service;

import java.util.concurrent.TimeUnit;

import com.flicklib.service.cache.MemoryCacheSourceLoader;
import com.flicklib.service.cache.PersistentCacheSourceLoader;

public abstract class TestUtil {

	public static SourceLoader wrapCache(SourceLoader realLoader) {
		String rootPath = System.getProperty("java.io.tmpdir", "target")+"/.cache/";
		return new MemoryCacheSourceLoader(new PersistentCacheSourceLoader(realLoader, rootPath, "yyyy-MM-dd"));
		
	}
	
	public static SourceLoader createLoader() {
		return wrapCache(new UrlConnectionResolver((int) TimeUnit.SECONDS.toMillis(10)));
	}
}
