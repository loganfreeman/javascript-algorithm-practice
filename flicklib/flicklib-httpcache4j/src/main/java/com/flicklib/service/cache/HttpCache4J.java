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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.codehaus.httpcache4j.HTTPMethod;
import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.MIMEType;
import org.codehaus.httpcache4j.Parameter;
import org.codehaus.httpcache4j.cache.HTTPCache;
import org.codehaus.httpcache4j.cache.MemoryCacheStorage;
import org.codehaus.httpcache4j.client.HTTPClientResponseResolver;
import org.codehaus.httpcache4j.payload.ByteArrayPayload;
import org.codehaus.httpcache4j.payload.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;
import com.flicklib.tools.IOTools;

public class HttpCache4J implements SourceLoader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpCache4J.class);
	
	private final HTTPCache cache;
	
	public HttpCache4J() {
		this.cache = new HTTPCache(new MemoryCacheStorage(), new HTTPClientResponseResolver(new HttpClient()));
	}

	/** {@inheritDoc} */
	@Override
	public Source loadSource(final String url, boolean useCache) {
		HTTPRequest request = new HTTPRequest(URI.create(url));
		HTTPResponse response = cache.execute(request, !useCache);
		String content = payloadToString(response);
		String respUrl = response.getHeaders().getFirstHeaderValue("Content-Location");
		//System.err.println(response.getHeaders().toString());
		final String theUrl = respUrl != null ? respUrl : url;
		Source source = new Source(theUrl, content, response.getPayload().getMimeType().toString(), url);
		return source;
	}
	
	/** {@inheritDoc} */
	@Override
	public Source post(String url, Map<String, String> parameters, Map<String, String> headers) {
		HTTPRequest request = new HTTPRequest(URI.create(url), HTTPMethod.POST);
		for(Entry<String, String> header:headers.entrySet()) {
			request.addHeader(header.getKey(), header.getValue());
		}
		
//		TODO Enable when new version of httpcache4j is released
//		
//		List<FormParameter> params = new ArrayList<FormParameter>();
//		for(Entry<String,String> param:parameters.entrySet()){
//			params.add(new FormParameter(param.getKey(), param.getValue()));
//		}
//		FormDataPayload payload = new FormDataPayload(params);
//		request = request.payload(payload);
		
		///////////////// TODO replace this part with the code above /////////////////////
		StringBuilder builder = new StringBuilder();
		// TODO fix when this is fixed: http://jira.codehaus.org/browse/HTCJ-51
		// or see in httpclient if we can reuse code
		for(Entry<String,String> param:parameters.entrySet()){
			if(builder.length() > 0){
				builder.append("&");
			}
			String encoding = "UTF-8";
			try {
				builder.append(URLEncoder.encode(param.getKey(), encoding)).append("=").append(URLEncoder.encode(param.getValue(), encoding));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(builder.toString().getBytes());
		
		MIMEType mimeType = new MIMEType("application/x-www-form-urlencoded");
		
		Payload payload = null;
		try {
			payload = new ByteArrayPayload(bis, mimeType);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		request = request.payload(payload);
		//////////////////////////////////////////////////////////////////////////
		
		HTTPResponse response = cache.execute(request, false);
		String content = payloadToString(response);
		
		final String respUrl = response.getHeaders().getFirstHeaderValue("Content-Location");
		final String theUrl = (respUrl != null) ? respUrl : url;
		Source source = new Source(theUrl, content, response.getPayload().getMimeType().toString(), url);
		return source;
	}
	

	@Override
	public Source loadSource(String url) throws IOException {
		return loadSource(url, true);
	}
	
	private String payloadToString(HTTPResponse response){
		String content = null;
		InputStream is = null;
		try{
			is = response.getPayload().getInputStream();
			 //httpMethod.addRequestHeader("Content-Type","text/html; charset=UTF-8");
			String contentType = response.getHeaders().getFirstHeaderValue("Content-Type");
			MIMEType mimeType = new MIMEType(contentType);
			// default as described in: http://hc.apache.org/httpclient-3.x/charencodings.html
			String encoding = "ISO-8859-1";
			if(contentType.contains("charset")){
				for(Parameter parameter:mimeType.getParameters()){
					if("charset".equals(parameter.getName())){
						encoding = parameter.getValue();
					}
				}
			}
			content = IOTools.inputSreamToString(is, encoding);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}finally{
			IOTools.close(is);
		}
		return content;
	}
	
    @Override
    public RestBuilder url(String url) {
    	throw new UnsupportedOperationException("not implemented");
    }

}
