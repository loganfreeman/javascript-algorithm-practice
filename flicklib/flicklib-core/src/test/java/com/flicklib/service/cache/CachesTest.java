package com.flicklib.service.cache;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.flicklib.service.HttpClientSourceLoader;
import com.flicklib.service.SourceLoader;
import com.flicklib.service.UrlConnectionResolver;

/**
 * Tests the output of different cache/resolver configurations
 * @author francisdb
 *
 */
public class CachesTest {

	@Test
	public void test() throws IOException {
		String url = "http://api.blippr.com/v2/titles/652519.json";
		SourceLoader empty = new MemoryCacheSourceLoader(new UrlConnectionResolver((int) TimeUnit.SECONDS.toMillis(5)));
		SourceLoader empty2 = new MemoryCacheSourceLoader(new HttpClientSourceLoader((int) TimeUnit.SECONDS.toMillis(5)));
		//HttpCache4J cache4j = new HttpCache4J();

		String emptyContent = empty.loadSource(url).getContent();
		String emptyContent2 = empty2.loadSource(url).getContent();
		//String cache4jContent = cache4j.get(url).getContent();

		assertEquals("two response should be the same", emptyContent, emptyContent2);
		//assertEquals(emptyContent2, cache4jContent);
	}

}
