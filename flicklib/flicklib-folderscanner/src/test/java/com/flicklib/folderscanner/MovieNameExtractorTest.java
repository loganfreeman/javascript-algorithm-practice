package com.flicklib.folderscanner;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author francisdb
 */
public class MovieNameExtractorTest {

    @Test
    public void test(){
        MovieNameExtractor extractor = new MovieNameExtractor();
        String name = extractor.removeCrap("movie.name.1905.DVDRiP.XViD-JUnit", false);
        assertEquals("movie name", name);
    }

}