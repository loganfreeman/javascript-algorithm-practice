/*
 * This file is part of Movie Browser.
 * 
 * Copyright (C) Francis De Brabandere
 * 
 * Movie Browser is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Movie Browser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.flicklib.folderscanner;

/**
 * 
 * @author zsombor
 *
 */
public enum ReleaseType {
    ONE_CD("One CD"), TWO_CD("Two CD"), DVD ("DVD");
            
    String label;

    private ReleaseType(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
}
