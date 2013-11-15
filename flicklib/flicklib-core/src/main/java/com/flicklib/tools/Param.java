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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author francisdb
 */
public class Param {

    private static final Logger LOGGER = LoggerFactory.getLogger(Param.class);

    private Param() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a map of string - string key-value pairs encoded by UrlEncoder
     * The number of parameters hould be even!
     * @param paramValue
     * @return the Map
     */
    public static final Map<String, String> map(final String... paramValue) {
        if (paramValue.length % 2 != 0) {
            throw new IllegalArgumentException("parameter count should be even but is " + paramValue.length);
        }
        Map<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < paramValue.length / 2; i++) {
            params.put(encode(paramValue[i]), encode(paramValue[i + 1]));
        }
        return params;
    }

    /**
     * Transforms paramValue pairs to a parameter string
     * @param paramValue
     * @return the parameter string
     */
    public static final String paramString(final String... paramValue) {
        return paramString(map(paramValue));
    }

    /**
     * Transforms parameter map to a parameter string
     * @param params
     * @return the parameter string
     */
    public static final String paramString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (first) {
                result.append('?');
                first = false;
            } else {
                result.append('&');
            }
            result.append(param.getKey());
            result.append('=');
            result.append(param.getValue());
        }

        return result.toString();
    }

    /**
     * UrlEncode a string
     * @param str 
     * @return the imdb url
     */
    public static String encode(String str) {
        String encoded = "";
        try {
            encoded = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error("Could not cencode UTF-8", ex);
        }
        return encoded;
    }
}
