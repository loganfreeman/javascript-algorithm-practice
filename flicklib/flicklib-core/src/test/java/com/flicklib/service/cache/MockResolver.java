/**
 * 
 */
package com.flicklib.service.cache;

import java.io.IOException;
import java.util.Map;

import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;

final class MockResolver implements SourceLoader {
	
	private int count = 0;
	
	@Override
	public Source loadSource(String url) throws IOException {
		count++;
		Source source = new Source(url, "mock", url);
		return source;
	}
	
	@Override
	public Source loadSource(String url, boolean useCache) throws IOException {
		return loadSource(url);
	}

	@Override
	public Source post(String url, Map<String, String> parameters, Map<String, String> headers) throws IOException {
		count++;
		Source source = new Source(url, "mock", url);
		return source;
	}
	
	public int getCallCount() {
		return count;
	}
	
    @Override
    public RestBuilder url(String url) {
    	throw new UnsupportedOperationException("not implemented");
    }
}