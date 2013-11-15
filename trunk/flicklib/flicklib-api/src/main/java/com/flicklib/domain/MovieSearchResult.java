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


public class MovieSearchResult {

    private String url;
    private String idForSite;
    private String title;
    private String alternateTitle;
    private String originalTitle;
    private Integer year;
    private String description;

    private MovieService service;
    private MovieType type;

    public MovieSearchResult() {
    }

    public MovieSearchResult(MovieService service) {
        this.service = service;
    }

    /**
     * @return the idForSite
     */
    public String getIdForSite() {
        return idForSite;
    }

    /**
     * @param idForSite
     *            the idForSite to set
     */
    public void setIdForSite(String idForSite) {
        this.idForSite = idForSite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * The title in english.
     * 
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
     * A translated title, based on the service localization.
     * 
     * @return the alternateTitle
     */
    public String getAlternateTitle() {
        return alternateTitle;
    }

    /**
     * @param alternateTitle
     *            the alternateTitle to set
     */
    public void setAlternateTitle(String alternateTitle) {
        this.alternateTitle = alternateTitle;
    }
    
    /**
     * The original title, it can be in any language.
     * 
     * @return the originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * @param originalTitle the originalTitle to set
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public MovieService getService() {
        return service;
    }

    public void setService(MovieService service) {
        this.service = service;
    }

    public void setType(MovieType type) {
        this.type = type;
    }

    public MovieType getType() {
        return type;
    }
    
    /**
     * 
     * @return the original title if specified, or the title if not.
     */
    public String getPreferredTitle() {
        return originalTitle != null && !originalTitle.trim().isEmpty() ? originalTitle : title;
    }
    
    @Override
    public String toString() {
        return "MovieSearchResult["+title+'/'+alternateTitle+'/'+idForSite+'/'+url+']';
    }

}
