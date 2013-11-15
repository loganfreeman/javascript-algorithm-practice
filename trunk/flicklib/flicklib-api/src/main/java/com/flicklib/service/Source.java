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

import java.io.Serializable;

public class Source implements Serializable{
	
	public static final long serialVersionUID = 1L;

    private final String url;
    private final String content;
    private final String contentType;
    private final String requestUrl;
    
    private transient net.htmlparser.jericho.Source jerichoSource;
    
    /**
     * Creates a new Source
     * @param url
     * @param content
     * @param contentType 
     */
    public Source(final String url, final String content, final String contentType, final String requestUrl) {
    	this.url = url;
        this.content = content;
        this.contentType = contentType;
        this.requestUrl = requestUrl;
	}
    
    /**
     * Html source constructor
     * @param url
     * @param content
     */
    public Source(final String url, final String content, final String reqUrl) {
        this.url = url;
        this.content = content;
        this.contentType = "text/html";
        this.requestUrl = reqUrl;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	public String getRequestUrl() {
            return requestUrl;
        }
    
	
    public net.htmlparser.jericho.Source getJerichoSource() {
        if (jerichoSource == null) {
            jerichoSource = new net.htmlparser.jericho.Source(content);
            jerichoSource.fullSequentialParse();
        }
        return jerichoSource;
    }
	
}
