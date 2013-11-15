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

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.StartTag;

public final class StringUtils {

    private StringUtils() {}

    /**
     * remove brackets from the start and end of the string.
     * 
     * @param text
     * @return
     */
    public static String unbracket(String text) {
        text = text.trim();
        if (text.startsWith("(")) {
            text = text.substring(1);
        }
        if (text.endsWith(")")) {
            text = text.substring(0, text.length() - 1);
        }
        return text.trim();
    }
    
    
    public static boolean isElementAttributeValue(Element element, String name, String value) {
        return value.equals(element.getAttributeValue(name));
    }

    public static boolean isElementAttributeValueContains(Element element, String name, String value) {
        String attrValue = element.getAttributeValue(name);
        if (attrValue!=null) {
            return attrValue.contains(value);
        }
        return false;
    }

    public static boolean isElementAttributeValue(StartTag element, String name, String value) {
        return value.equals(element.getAttributeValue(name));
    }

    public static boolean isElementAttributeValueContains(StartTag element, String name, String value) {
        String attrValue = element.getAttributeValue(name);
        if (attrValue!=null) {
            return attrValue.contains(value);
        }
        return false;
    }
    

}
