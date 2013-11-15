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
package com.flicklib.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 
 * @author francisdb
 */
public class MoviePage extends MovieSearchResult {

    /**
     * Score from 0 - 100
     */
    private Integer score;

    private Integer votes;

    private String imgUrl;

    private String plot;

    /**
     * Runtime in minutes
     */
    private Integer runtime;
    
    private Set<String> genres;
    private Set<String> languages;
    private List<String> directors;
    private List<String> actors;

    public MoviePage() {
    	this(null);
    }

    public MoviePage(MovieService service) {
        super(service);
        this.directors = new ArrayList<String>();
        this.languages = new HashSet<String>();
        this.genres = new HashSet<String>();
        this.actors = new ArrayList<String>();
    }

    /**
     * Gets the score (0-100) 
     * @return the score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Sets the score (0-100) 
     * @param score
     */
    public void setScore(Integer score) {
    	if(score != null && (score < 0 || score > 100)){
    		throw new IllegalArgumentException("Score must be null or a value in the range 0-100");
    	}
        this.score = score;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    /**
     * @return the runtime
     */
    public Integer getRuntime() {
        return runtime;
    }

    /**
     * @param runtime
     *            the runtime to set
     */
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    /**
     * @return the genres
     */
    public Set<String> getGenres() {
        return genres;
    }

    /**
     * @param genres
     *            the genres to set
     */
    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    /**
     * @return the languages
     */
    public Set<String> getLanguages() {
        return languages;
    }

    /**
     * @param languages
     *            the languages to set
     */
    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    /**
     * @return the directors
     */
    public List<String> getDirectors() {
        return directors;
    }

    /**
     * @param directors
     *            the directors to set
     */
    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }
    
    public List<String> getActors() {
		return actors;
	}
    
    public void setActors(List<String> actors) {
		this.actors = actors;
	}

    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public void addLanguage(String language) {
        this.languages.add(language);
    }
    
    @Override
    public String toString() {
    	return getClass().getSimpleName()+" / "+getService()+" / "+getUrl();
    }

}
