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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author francisdb
 */
public class Movie {

    private String title;
    private String plot;
    private Integer year;
    private List<String> directors;
    private MovieType type;
    /**
     * Runtime in minutes
     */
    private Integer runtime;
    private Set<String> genres;
    private Set<String> languages;
    
    /**
     * Cast list (might be ordered)
     */
    private List<String> cast;
    
    
    /**
     * Constructs a new Movie
     */
    public Movie() {
        this.genres = new HashSet<String>();
        this.languages = new HashSet<String>();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the plot
     */
    public String getPlot() {
        return plot;
    }

    /**
     * @param plot
     *            the plot to set
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }

    /**
     * @return the year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year
     *            the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return the director
     */
    public List<String> getDirectors() {
        return directors;
    }

    /**
     * @param director
     *            the director to set
     */
    public void setDirectors(List<String> directors) {
        this.directors = directors;
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
	 * @return the cast
	 */
	public List<String> getCast() {
		return cast;
	}

	/**
	 * @param cast the cast to set
	 */
	public void setCast(List<String> cast) {
		this.cast = cast;
	}

	public MovieType getType() {
        return type;
    }

    public void setType(MovieType type) {
        this.type = type;
    }

    /**
     * @param genre
     */
    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    /**
     * @param language
     */
    public void addLanguage(String language) {
        this.languages.add(language);
    }
}
