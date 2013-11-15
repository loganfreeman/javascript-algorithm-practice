package com.flicklib.tools;

import org.junit.Test;
import static org.junit.Assert.*;

public class LevenshteinDistanceTest {

    @Test
    public void testDistance() {
        assertEquals("similar strings", 0, LevenshteinDistance.distance("test1", "test1"));
        assertEquals("file names-0", 1, LevenshteinDistance.distance("film-cd1.avi", "film-cd2.avi"));
        assertEquals("file names-1", 3, LevenshteinDistance.distance("film-cd1.avi", "film-CD2.avi"));
        assertEquals("file names-2", 3, LevenshteinDistance.distance("film-cd1.avi", "film-cd1.srt"));
        assertEquals("file names-3", 2, LevenshteinDistance.distance("film-cd1.avi", "film-1cd.avi"));
    }
}
