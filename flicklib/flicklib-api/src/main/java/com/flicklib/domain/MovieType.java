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

/**
 *
 * @author francisdb
 */
public enum MovieType {
    MOVIE("Movie"),
    SHORT_FILM("Short Film"),
    TV_MOVIE("TV Movie"),
    VIDEO_MOVIE("Video Movie"),
    TV_SERIES("TV Series"),
    MINI_SERIES("Mini Series");

    private String name;

    private MovieType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}
