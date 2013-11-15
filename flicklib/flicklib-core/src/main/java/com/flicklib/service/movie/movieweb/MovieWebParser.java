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
package com.flicklib.service.movie.movieweb;

import java.util.regex.Matcher;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.domain.MoviePage;
import com.flicklib.service.movie.AbstractJerichoParser;
import com.flicklib.tools.SimpleXPath;
import com.google.inject.Singleton;

/**
 *
 * @author francisdb
 */
@Singleton
public class MovieWebParser extends AbstractJerichoParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieWebParser.class);

	@Override
	public void parse(Source source, MoviePage movieSite) {

		Element rater = new SimpleXPath(source.getElementById("module_rater")).getAllTagByAttributes("class", "score").children()
				.unique();
		String xp = rater.getTextExtractor().toString();
		Matcher matcher = java.util.regex.Pattern.compile("\\d+[.]\\d*").matcher(xp);
		if (matcher.find()) {
			String txt = matcher.group();
			try {
				movieSite.setScore((int) (Double.parseDouble(txt) * 20));
			} catch (NumberFormatException ex) {
				LOGGER.error("Could not parse " + txt + " to Double", ex);
			}
		}
		Element title = new SimpleXPath(source.getElementById("colC")).getTags(HTMLElementName.H1).getTags(HTMLElementName.A).unique();
		movieSite.setTitle(title.getTextExtractor().toString());
		SimpleXPath img = new SimpleXPath(source.getElementById("colL")).getAllTagByAttributes("class", "moduleTB").getTags(HTMLElementName.A).getTags(HTMLElementName.IMG);
		if (img.size()>0) {
			Element imgTag = img.unique();
			movieSite.setImgUrl(imgTag.getAttributeValue("src"));
		}
	}

}
