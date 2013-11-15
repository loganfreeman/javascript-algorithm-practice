/*
 * This file is part of Flicklib.
 *
 * Copyright (C) Zsombor Gegesy.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class FileGroup {

    private final static long MB = 1024*1024;
    
    String title;

    Locale audioLanguage;

    Set<FileLocation> locations;
    
    List<FileMeta> files;

    ReleaseType type;
    
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

    
    public void setType(ReleaseType type) {
        this.type = type;
    }
    
    public ReleaseType getType() {
        return type;
    }

    /**
     * @return the audioLanguage
     */
    public Locale getAudioLanguage() {
        return audioLanguage;
    }

    /**
     * @param audioLanguage
     *            the audioLanguage to set
     */
    public void setAudioLanguage(Locale audioLanguage) {
        this.audioLanguage = audioLanguage;
    }

    public Set<FileLocation> getLocations() {
        if (locations == null) {
            locations = new HashSet<FileLocation>();
        }
        return locations;
    }
    
    public List<FileMeta> getFiles() {
        if (files == null) {
            files = new ArrayList<FileMeta>();
        }
        return files;
    }
    
    public FileMeta getFileByName(String name) {
        for (FileMeta f : getFiles()) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "FileGroup[" + title + "," + audioLanguage + ", locations:" + locations + ']';
    }
    
    /**
     * 
     */
    public void guessReleaseType() {
        if (getType()==null) {
            long size = getSize();
            if (size>500*MB && size<=900*MB) {
                setType(ReleaseType.ONE_CD);
            } else {
                if (size>900*MB && size<=1500*MB) {
                    setType(ReleaseType.TWO_CD);
                } else {
                    if (size>1500*MB && size<=4500*MB) {
                        setType(ReleaseType.DVD);
                    }
                }
            }
        }
    }
    
    public long getSize() {
        long size = 0;
        for (FileMeta sm : getFiles()) {
            size += sm.getSize();
        }
        return size;
    }

    
    
    public boolean isValid() {
        if (getLocations().isEmpty()) {
            return false;
        }
        for (FileMeta f : getFiles()) {
            if (f.getType().equals(MovieFileType.VIDEO_CONTENT)) {
                return true;
            }
        }
        
        return true;
        
    }

}
