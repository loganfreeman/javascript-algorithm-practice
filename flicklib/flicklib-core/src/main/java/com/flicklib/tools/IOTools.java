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
package com.flicklib.tools;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IO related utility class
 * 
 * @author francisdb
 */
public class IOTools {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IOTools.class);

    private IOTools() {
        throw new UnsupportedOperationException("Utility class");
    }

    
    
    /**
     * Reads an inputstream to String
     * @param in
     * @param charsetName the charset name
     * @return the result from reading the stream
     * @throws java.io.IOException
     */
    public static String inputSreamToString(final InputStream in, final String charsetName) throws IOException {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            bos.write(b, 0, n);
        }
        if(charsetName != null){
        	return bos.toString(charsetName);
        }else{
        	return bos.toString();
        }
    }
    
    public static String readerToString(final Reader in) throws IOException {
        StringBuilder out = new StringBuilder();
        char[] b = new char[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
    
    /**
     * Silently closes a Closeable, logs exceptions
     * 
     * @param closeable
     */
    public static void close(final Closeable closeable){
    	if(closeable != null) {
	    	try {
				closeable.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
    	}else{
    		LOGGER.warn("Trying to close null Closeable");
    	}
    }
    
    
    public static void writeToFile(Reader input, File output) throws IOException {
        FileWriter fw = new FileWriter(output);
        char[] b = new char[4096];
        for (int n; (n = input.read(b)) != -1;) {
            fw.write(b, 0, n);
        }
        close(fw);
    }
}
