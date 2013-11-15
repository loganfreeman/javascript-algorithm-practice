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
package com.flicklib.service.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;

public class PersistentCacheSourceLoader extends HttpCacheSourceLoader {

	final static Logger LOG = LoggerFactory.getLogger(PersistentCacheSourceLoader.class);
	
	File dir;

    public PersistentCacheSourceLoader(SourceLoader resolver, String rootPath) {
        this(resolver, rootPath, null);
    }

    public PersistentCacheSourceLoader(SourceLoader resolver, String rootPath, String datePattern) {
        super(resolver);
        File file = new File(rootPath);
        File flicklib = new File(file, "flicklib");
        if (datePattern != null) {
            SimpleDateFormat df = new SimpleDateFormat(datePattern);
            String path = df.format(new Date());
            dir = new File(flicklib, path);
            LOG.info("rootPath : " + rootPath + ", datePattern:" + datePattern + " -> " + dir.getAbsolutePath());
        } else {
            dir = new File(flicklib,"httpcache");
        }
        if (dir.mkdirs()) {
            LOG.info("path created [" + dir + "]");
        }
    }

	@Override
	protected Source getFromCache(String url) {
		File file = new File(dir, createSafeName(url));
		if (file.exists() && file.isFile() && file.canRead()) {
			ObjectInputStream o;
			try {
				LOG.info("loading from " + file.getAbsolutePath() + " for " + url);
				o = new ObjectInputStream(new FileInputStream(file));
				Source src = (Source) o.readObject();
				o.close();
				
				if (url.equals(src.getRequestUrl())) {
					return src;
				} else {
					LOG.warn("url mismatch:" + url + " not equals to " + src.getRequestUrl());
				}
			} catch (FileNotFoundException e) {
				LOG.warn("error opening "+file.getAbsolutePath()+" : "+e.getMessage(), e);
			} catch (IOException e) {
				LOG.warn("error opening "+file.getAbsolutePath()+" : "+e.getMessage(), e);
			} catch (ClassNotFoundException e) {
				LOG.warn("error opening "+file.getAbsolutePath()+" : "+e.getMessage(), e);
			}
		
		}
		return null;
	}

	private String createSafeName(String url) {
		return url.replace(':', '_').replace('/', '_').replace('\\', '_').replace('?', '_').replace('=', '_').replace('&','_').replaceAll("_+", "_");
	}

	@Override
	protected void put(String url, Source source) {
		File file = new File(dir, createSafeName(url));
		try {
			LOG.info("storing into " + file.getAbsolutePath() + " for " + url);
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file));
			o.writeObject(source);
			o.close();
		} catch (FileNotFoundException e) {
			LOG.warn("error writing "+file.getAbsolutePath()+" : "+e.getMessage(), e);
		} catch (IOException e) {
			LOG.warn("error writing "+file.getAbsolutePath()+" : "+e.getMessage(), e);
		}
	}
	
    @Override
    public RestBuilder url(String url) {
    	throw new UnsupportedOperationException("not implemented");
    }

}
