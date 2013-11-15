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
package com.flicklib.service.movie.cinebel;

import java.util.Collection;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import com.flicklib.api.Parser;
import com.flicklib.domain.MoviePage;
import com.flicklib.tools.AdvancedTextExtractor;
import com.flicklib.tools.SimpleXPath;

/**
 * @author francisdb
 *
 */
public class CinebelParser implements Parser {

	/* (non-Javadoc)
	 * @see com.flicklib.api.Parser#parse(com.flicklib.service.Source, com.flicklib.domain.MoviePage)
	 */
	@Override
	public void parse(com.flicklib.service.Source source, MoviePage page) {

		Source document = source.getJerichoSource();

		parseTitle(page, document);

		parseImageUrl(page, document);
		parseRating(page, document);
		parseSynopsis(page, document);
		parseYear(page, document);
		parseMisc(page, document);

	}

	private void parseMisc(MoviePage page, Source document) {
		for (Element e : new SimpleXPath(document.getAllElements("class", "movieInfosGroup", true)).children().filterTagName(HTMLElementName.DIV)) {
			Element strong = e.getFirstElement(HTMLElementName.STRONG);
			if (strong != null) {
				String strongValue = strong.getTextExtractor().toString();
				if ("Regie".equals(strongValue)) {
					fillSet(page.getDirectors(), new SimpleXPath(e).getTags(HTMLElementName.LI));
				}
				if ("Cast".equals(strongValue)) {
					fillSet(page.getActors(), new SimpleXPath(e).getTags(HTMLElementName.LI));
				}
				if ("Genre".equals(strongValue)) {
					fillSet(page.getGenres(), new SimpleXPath(e).getTags(HTMLElementName.LI));
				}
			}
		}
	}

	private void fillSet(Collection<String> values, SimpleXPath tags) {
		for (Element e : tags) {
			String value = e.getTextExtractor().toString().trim();
			values.add(value);
		}
	}

	private void parseYear(MoviePage page, Source document) {
		List<Element> elements = document.getAllElements("class", "productionDate", true);
		if (elements.size()> 0) {
			String value = excludeStrong(elements);
			page.setYear(Integer.parseInt(value.replace(':', ' ').trim()));
		}
	}

	private void parseTitle(MoviePage page, Source document) {
		Element movieDetails = document.getElementById("movieDetails");
		Element title = new SimpleXPath(movieDetails).children().filterTagName(HTMLElementName.H1).children().unique();
		page.setTitle(title.getTextExtractor().toString());
	}

	private void parseImageUrl(MoviePage page, Source document) {
		Element fullPosterLink = document.getElementById("fullPosterLink");
		if (fullPosterLink != null) {
			page.setImgUrl(fullPosterLink.getAttributeValue("href"));
		}
	}

	private void parseRating(MoviePage page, Source document) {
		Element rating = document.getElementById("userRating");
		if (rating != null) {
			Element average = new SimpleXPath(rating).getAllTagByAttributes("class", "average").unique();
			String averageText = average.getTextExtractor().toString();
			double score = Double.parseDouble(averageText);
			page.setScore((int) (score * 10));
		}
	}

	private void parseSynopsis(MoviePage page, Source document) {
		List<Element> synopsis = document.getAllElements("class", "synopsis", true);
		if (synopsis.size() > 0) {

			String syn = excludeStrong(synopsis);
			page.setDescription(syn);
			page.setPlot(syn);
		}
	}

	private String excludeStrong(List<Element> synopsis) {
		return new AdvancedTextExtractor(synopsis.get(0), false).addExcludedTagName(HTMLElementName.STRONG).toString();
	}

}
