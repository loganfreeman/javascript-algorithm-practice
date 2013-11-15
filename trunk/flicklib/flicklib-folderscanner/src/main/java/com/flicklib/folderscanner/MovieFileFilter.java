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
package com.flicklib.folderscanner;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Checks if a file has a know video extension
 * 
 * @author francisdb
 */
public class MovieFileFilter implements FileFilter {

	public static final Set<String> VIDEO_EXTENSIONS = new HashSet<String>();
	public static final Set<String> VIDEO_EXT_EXTENSIONS = new HashSet<String>();

	static {
		Collections.addAll(VIDEO_EXTENSIONS, "avi", "mpg", "mpeg", "divx",
				"mkv", "xvid", "m4v", "mov", "flv", "iso");
		VIDEO_EXT_EXTENSIONS.addAll(VIDEO_EXTENSIONS);
		Collections.addAll(VIDEO_EXT_EXTENSIONS, "srt", "nfo", "sub", "idx");
		
	}

	private final boolean acceptFolders;

	public MovieFileFilter(boolean acceptFolders) {
		this.acceptFolders = acceptFolders;
	}

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return acceptFolders;
		} else {
			String name = file.getName();
			int lastDotPos = name.lastIndexOf('.');
			if (lastDotPos != -1 && lastDotPos != 0
					&& lastDotPos < name.length() - 1) {
				String ext = name.substring(lastDotPos + 1).toLowerCase();
				if (VIDEO_EXTENSIONS.contains(ext)) {
					return true;
				}
			}

		}
		return false;
	}
}
