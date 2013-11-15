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
package com.flicklib.service.sub;

import com.flicklib.api.SubtitlesLoader;
import com.flicklib.domain.Subtitle;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author francisdb
 */
public class SubtitleSourceLoader implements SubtitlesLoader {

    @Override
    public Set<Subtitle> search(String localFileName, String imdbId) throws IOException {
        Set<Subtitle> subs = new HashSet<Subtitle>();
        subs.add(makeSubtitlesourceEntry(localFileName, imdbId));
        return subs;
    }

    /**
     * Generates a dummy entry that links to the site
     * @param localFileName 
     * @param imdbId 
     * @return the Subtitle entry
     * @throws java.io.IOException
     */
    public Subtitle makeSubtitlesourceEntry(String localFileName, String imdbId) throws IOException {
        Subtitle sub = new Subtitle();
        sub.setFileName(localFileName);
        String url = "http://www.subtitlesource.org/title/tt" + imdbId;
        sub.setFileUrl(url);
        sub.setSubSource("http://www.subtitlesource.org");
        sub.setLanguage("Various");
        sub.setNoCd("N/A");
        sub.setType("N/A");
        return sub;
    }
}
