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
package com.flicklib.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Demo implementation of netflix auth <br>
 * get your key at: http://developer.netflix.com/
 * 
 * @author francisdb
 */
public class NetFlixAuthModule extends AbstractModule {
	
	private String apikey;
	private String sharedsecret;
	
	public NetFlixAuthModule(final String apikey, final String sharedsecret) {
		this.apikey = apikey;
		this.sharedsecret = sharedsecret;
	}

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bindConstant().annotatedWith(Names.named("netflix.key")).to(apikey);
		bindConstant().annotatedWith(Names.named("netflix.secret")).to(sharedsecret);
	}

}
