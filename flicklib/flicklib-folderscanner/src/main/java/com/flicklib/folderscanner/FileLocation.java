/*
 * This file is part of Flicklib.
 *
 * Copyright (C) Zsombor Gegesy
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
package com.flicklib.folderscanner;

import java.net.URL;

/**
 * 
 * @author zsombor
 *
 */
public class FileLocation {

    String label;
    URL path;
    
    
    
    public FileLocation(String label, URL path) {
        this.label = label;
        this.path = path;
    }
    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }
    /**
     * @return the path
     */
    public URL getPath() {
        return path;
    }
    
    @Override
    public String toString() {
        return "Location("+label+","+path+")";
    }
}
