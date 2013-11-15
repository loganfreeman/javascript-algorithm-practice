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

import java.io.IOException;

import org.junit.Test;

import com.flicklib.service.SourceLoader;

public class EmptyHttpCacheTest {
	
	

	@Test
	public void testPutGet() throws IOException {
		MockResolver resolver = new MockResolver();
		SourceLoader cache = new MemoryCacheSourceLoader(resolver);
		
		assertEquals("mock", cache.loadSource("test").getContent());
		assertEquals(1, resolver.getCallCount());
		
		// second call should hit the resolver again
		assertEquals("mock", cache.loadSource("test/2").getContent());
		assertEquals(2, resolver.getCallCount());

		// second call should hit the resolver again
		assertEquals("mock", cache.loadSource("test").getContent());
		assertEquals(2, resolver.getCallCount());
	}

}
