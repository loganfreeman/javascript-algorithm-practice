package com.flicklib.service.cache;
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
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;


public class HttpEHCacheTest {
	
	@Before 
	public void setup() throws IOException{
		getCacheDir().mkdirs();
	}
	@After
	public void cleanup() throws IOException{
		FileUtils.deleteDirectory(getCacheDir());
	}

	protected File getCacheDir() {
		String userHome = System.getProperty("java.io.tmpdir");
		File browserHome = new File(userHome, ".moviebrowser.test");
		return browserHome;
	}

	@Test
	public void testHttpCache() throws IOException {
		String testValue = UUID.randomUUID().toString();
		SourceLoader mockResolver = Mockito.mock(SourceLoader.class);
		Mockito.when(mockResolver.loadSource("test", true)).thenReturn(new Source("test", testValue, "test"));
		
		SourceLoader cache = new HttpEHCache(mockResolver);
		{
			Source source = cache.loadSource("test");
			assertEquals(testValue, source.getContent());
		}
		{
			Source source = cache.loadSource("test");
			assertEquals(testValue, source.getContent());
		}
		// second call should not hit the resolver so we should have only one #get call
		Mockito.verify(mockResolver).loadSource("test", true);
		
	}

}
