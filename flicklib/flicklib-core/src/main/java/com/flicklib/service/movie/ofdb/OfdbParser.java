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
package com.flicklib.service.movie.ofdb;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.Parser;
import com.flicklib.domain.MoviePage;
import com.flicklib.service.Source;

public class OfdbParser implements Parser{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OfdbParser.class);


	@Override
	public void parse(Source source, MoviePage page) {

		net.htmlparser.jericho.Source jerichoSource = source.getJerichoSource();
        
        // <span class="movieMainTitle">The Matrix</span>
        List<Element> h2Elements = jerichoSource.getAllElements(HTMLElementName.H2);
        for(Element h2Element: h2Elements){
        	// TODO get all titles
        	String germanTitle = h2Element.getContent().getTextExtractor().toString();
        	germanTitle = OfdbTools.handleType(germanTitle, page);
        	page.setAlternateTitle(germanTitle);
        }
        
//        <tr valign="top">
//        <td nowrap><font face="Arial,Helvetica,sans-serif" size="2" class="Normal">Originaltitel:</font></td>
//        <td>&nbsp;&nbsp;</td>
//        <td width="99%"><font face="Arial,Helvetica,sans-serif" size="2" class="Daten"><b>Dune</b></font></td>
//        </tr>
        
        List<Element> fontElements = jerichoSource.getAllElements(HTMLElementName.FONT);
        Iterator<Element> fontIterator = fontElements.iterator();

        Element fontElement;
        while(fontIterator.hasNext()){
        	fontElement = fontIterator.next();
        	String txt = fontElement.getContent().getTextExtractor().toString();
        	if("Originaltitel:".equals(txt)){
        		fontElement = fontIterator.next();
                String title = fontElement.getContent().getTextExtractor().toString().trim();
        		page.setTitle(title);
                page.setOriginalTitle(title);
        	}else if("Erscheinungsjahr:".equals(txt)){
        		fontElement = fontIterator.next();
        		try{
    				page.setYear(Integer.parseInt(fontElement.getContent().getTextExtractor().toString()));
    			}catch(NumberFormatException ex){
    				LOGGER.warn("Could not parse year: "+ex.getMessage());
    			}
        	}else if("Regie:".equals(txt)){
        		Element tr = jerichoSource.getNextElement(fontElement.getEnd(), HTMLElementName.TR);
        		Element a = jerichoSource.getNextElement(fontElement.getEnd(), HTMLElementName.A);
        		while(a != null && tr != null && a.getEnd() < tr.getStartTag().getBegin()){
        			page.getDirectors().add(a.getContent().getTextExtractor().toString());
        			a = jerichoSource.getNextElement(a.getEnd(), HTMLElementName.A);
        		}
        	}else if("Darsteller:".equals(txt)){
        		Element tr = jerichoSource.getNextElement(fontElement.getEnd(), HTMLElementName.TR);
        		Element a = jerichoSource.getNextElement(fontElement.getEnd(), HTMLElementName.A);
        		while(a != null && tr != null && a.getEnd() < tr.getStartTag().getBegin()){
        			page.getActors().add(a.getContent().getTextExtractor().toString());
        			a = jerichoSource.getNextElement(a.getEnd(), HTMLElementName.A);
        		}
        	}
        }
        
        
        //<img src="http://img.ofdb.de/film/3/3635.jpg" alt="Dune - Der Wüstenplanet" border="0" width="120" height="168"><br><br>
        List<Element> imgElements = jerichoSource.getAllElements(HTMLElementName.IMG);
        Iterator<Element> imgIterator = imgElements.iterator();
        Element imgElement;
        String imgUrl = null;
        while(imgIterator.hasNext() && imgUrl == null){
        	imgElement = imgIterator.next();
        	String src = imgElement.getAttributeValue("src");
        	if(src != null && src.startsWith("http://img.ofdb.de/film/")){
        		imgUrl = src;
        	}
        }
        page.setImgUrl(imgUrl);


        //<a href="view.php?page=genre&Genre=Fantasy">Fantasy</a><br><a href="view.php?page=genre&Genre=Krieg">Krieg</a><br>....
        List<Element> aElements = jerichoSource.getAllElements(HTMLElementName.A);
        Iterator<Element> aIterator = aElements.iterator();
        Element aElement;
        Set<String> genres = new HashSet<String>();
        while(aIterator.hasNext()){
        	aElement = aIterator.next();
        	String href = aElement.getAttributeValue("href");
        	if(href.startsWith("view.php?page=genre&Genre=")){
        		href = href.replace("view.php?page=genre&Genre=", "");
        		try {
					href = URLDecoder.decode(href, "UTF-8");
					genres.add(href);
				} catch (UnsupportedEncodingException e) {
					LOGGER.error(e.getMessage(), e);
				}
        	}
        }
        page.setGenres(genres);
        
        
        // score
        int noteIndex = source.getContent().indexOf("Note: ");
        int nextBr = source.getContent().indexOf("<br>", noteIndex);
        String score = source.getContent().substring(noteIndex, nextBr);
        //System.out.println(score);
        String actual = score.substring("Note: ".length(), score.indexOf("&nbsp;"));
        double dScore = Double.valueOf(actual);
        page.setScore((int)Math.round(dScore * 10));
        
        
        // Inhalt:</b> Vincent Vega und Jules Winnfield holen für ihren Boss Marsellus Wallace eine schwarze Aktentasche aus einer Wohnung ab. Drei Jungs, die ihnen dabei im... <a href="plot/1050,
        // TODO fetch the extra info page and get the text from there
        int inhaltIndex = source.getContent().indexOf("Inhalt:</b> ");
        int nextAHref = source.getContent().indexOf("<a href", inhaltIndex);
        String inhalt = source.getContent().substring(inhaltIndex + "Inhalt:</b> ".length(), nextAHref);
        page.setPlot(inhalt);
        page.setDescription(inhalt);
        // TODO get genres, length, alternative titles
	}

}
