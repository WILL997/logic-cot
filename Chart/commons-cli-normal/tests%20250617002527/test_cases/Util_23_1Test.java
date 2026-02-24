package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Util_23_1Test {

    @Test
    @Timeout(8000)
    public void testDefaultValue() throws Exception {
        String result = Util.defaultValue("Hello", "Default");
        assertEquals("Hello", result);
    }

    @Test
    @Timeout(8000)
    public void testIndexOfNonWhitespace() throws Exception {
        int result = Util.indexOfNonWhitespace("  Hello", 0);
        assertEquals(2, result);
    }

    @Test
    @Timeout(8000)
    public void testIsEmpty() throws Exception {
        boolean result = Util.isEmpty("");
        assertTrue(result);
    }

    @Test
    @Timeout(8000)
    public void testIsWhitespace() throws Exception {
        boolean result = Util.isWhitespace(' ');
        assertTrue(result);
    }

    @Test
    @Timeout(8000)
    public void testLtrim() throws Exception {
        String result = Util.ltrim("   Hello");
        assertEquals("Hello", result);
    }

    @Test
    @Timeout(8000)
    public void testRepeat() throws Exception {
        String result = Util.repeat(3, 'x');
        assertEquals("xxx", result);
    }

    @Test
    @Timeout(8000)
    public void testRepeatSpace() throws Exception {
        String result = Util.repeatSpace(2);
        assertEquals("  ", result);
    }

    @Test
    @Timeout(8000)
    public void testRtrim() throws Exception {
        String result = Util.rtrim("Hello   ");
        assertEquals("Hello", result);
    }
}