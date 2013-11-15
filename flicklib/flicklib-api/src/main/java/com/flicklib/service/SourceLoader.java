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
package com.flicklib.service;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author francisdb
 */
public interface SourceLoader {

    /**
     * Loads a http request and parses it to a jericho source
     * @param url
     * @return the page source and the final URL (after the redirects followed).
     * @throws java.io.IOException
     */
    Source loadSource(String url) throws IOException;

    
    /**
     * Loads a http request and parses it to a jericho source.
     * @param url
     * @param useCache if it's true it is allowed to cache the response.
     * @return the page source and the final URL (after the redirects followed).
     * @throws java.io.IOException
     */
    Source loadSource(String url, boolean useCache) throws IOException;
    
    /**
     * Post a http request and parses it to a jericho source
     * 
     * @param url
     * @param parameters
     * @param headers
     * @return the page source
     * @throws IOException
     */
    Source post(String url, Map<String,String> parameters, Map<String,String> headers) throws IOException;
    
    RestBuilder url(String url);
    
    interface RestBuilder{

		RestBuilder setHeader(String string, String string2);
		RestResult get() throws IOException;
    	
    }
    
    interface RestResult{
    	// TODO implement
		//JsonElement getJson();
		
		String getString() throws IOException;
    }
}
