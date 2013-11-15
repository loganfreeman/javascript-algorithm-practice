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
package com.flicklib.service.movie.tomatoes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.Parser;
import com.flicklib.domain.MoviePage;
import com.flicklib.service.Source;
import com.google.inject.Singleton;

/**
 *
 * @author francisdb
 */
@Singleton
public class TomatoesParser implements Parser {

	private static final Logger LOGGER = LoggerFactory.getLogger(TomatoesParser.class);

	@Override
	public void parse(Source source, MoviePage movieSite) {
		movieSite.setService(TomatoesInfoFetcher.TOMATOES);
		net.htmlparser.jericho.Source html = source.getJerichoSource();
		
		List<Element> metas = html.getAllElements(HTMLElementName.META);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Element meta : metas) {
			String prop = meta.getAttributeValue("property");
			String value = meta.getAttributeValue("content");
			if ("og:title".equals(prop)) {
				movieSite.setTitle(value);
			} else if ("og:image".equals(prop)) {
				movieSite.setImgUrl(value);
			} else if ("og:description".equals(prop)) {
				//movieSite.setPlot(value);
			} else if ("flixstertomatoes:tomatometer".equals(prop)) {
				movieSite.setScore(Integer.parseInt(value));
			} else if ("flixstertomatoes:release_date".equals(prop)) {
				try {
					Date date = sdf.parse(value);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					movieSite.setYear(cal.get(Calendar.YEAR));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("flixstertomatoes:director".equals(prop)) {
				String celeb = parseCelebrityUrl(value);
				if (celeb != null) {
					movieSite.getDirectors().add(celeb);
				}
			} else if ("flixstertomatoes:cast".equals(prop)) {
				String celeb = parseCelebrityUrl(value);
				if (celeb != null) {
					movieSite.getActors().add(celeb);
				}
			} else if ("og:url".equals(prop)) {
				if (movieSite.getUrl() == null) {
					movieSite.setUrl(value);
				}
			}
		}
		setId(movieSite);
		

		Element allCritics = html.getElementById("all-critics-meter");
		if (allCritics != null) {
			int score = Integer.valueOf(allCritics.getTextExtractor().toString().trim());
			movieSite.setScore(score);
		} else {
			// not used, I think ...

			List<?> divElements = html.getAllElements(HTMLElementName.DIV);
			for (Iterator<?> i = divElements.iterator(); i.hasNext();) {
				Element divElement = (Element) i.next();

				// TODO add id for site

				String id = divElement.getAttributeValue("id");
				if (id != null && "tomatometer_score".equals(id)) {
					String userRating = divElement.getContent().getTextExtractor().toString().trim();
					if (!"".equals(userRating) && !"N/A".equals(userRating)) {
						userRating = userRating.replace("%", "");
						userRating = userRating.trim();
						try {
							int score = Integer.valueOf(userRating);
							movieSite.setScore(score);
						} catch (NumberFormatException ex) {
							LOGGER.error("Could not parse " + userRating + " to Integer", ex);
						}
					}
				}
			}
		}
		Element elementById = html.getElementById("movieSynopsis");
		if (elementById != null) {
			movieSite.setPlot(elementById.getContent().getTextExtractor().toString());
		}

	}

	private void setId(MoviePage movieSite) {
		if (movieSite.getUrl() != null) {
			movieSite.setIdForSite(movieSite.getUrl().replace("http://www.rottentomatoes.com/m/", ""));
		}
	}

	private String parseCelebrityUrl(String value) {
		if (value == null) {
			return null;
		}
		if (value.startsWith("http://www.rottentomatoes.com/celebrity/")) {
			String name = value.substring("http://www.rottentomatoes.com/celebrity/".length());
			if (name.endsWith("/")) {
				name = name.substring(0, name.length() - 1);
			}
			String[] nameParts = name.split("_");
			StringBuilder s = new StringBuilder(name.length());
			for (String n : nameParts) {
				if (s.length()>0) {
					s.append(' ');
				}
				if (n.length() > 0) {
					s.append(Character.toUpperCase(n.charAt(0)));
					if (n.length() > 1) {
						s.append(n.substring(1));
					}
				}
			}
			
			return s.toString();
		}
		return null;
	}
}
