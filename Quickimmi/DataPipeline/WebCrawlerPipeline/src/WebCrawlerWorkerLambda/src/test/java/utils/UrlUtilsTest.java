package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlUtilsTest {

    @Test
    void name() {

    }

    @Test
    void testRemoveTailSlash() {
        Assertions.assertEquals("http://www.test.edu",
                UrlUtils.removeTailSlash("http://www.test.edu/"));
    }
}