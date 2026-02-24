package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.*;

public class Util_18_1Test {

    @Test(timeout = 8000)
    public void testIsWhitespace_space() throws Exception {
        char c = ' ';
        boolean result = Util.isWhitespace(c);
        assertTrue(result);
    }

    @Test(timeout = 8000)
    public void testIsWhitespace_tab() throws Exception {
        char c = '\t';
        boolean result = Util.isWhitespace(c);
        assertTrue(result);
    }

    @Test(timeout = 8000)
    public void testIsWhitespace_newLine() throws Exception {
        char c = '\n';
        boolean result = Util.isWhitespace(c);
        assertTrue(result);
    }

    @Test(timeout = 8000)
    public void testIsWhitespace_carriageReturn() throws Exception {
        char c = '\r';
        boolean result = Util.isWhitespace(c);
        assertTrue(result);
    }

    @Test(timeout = 8000)
    public void testIsWhitespace_paragraphSeparator() throws Exception {
        char c = Character.PARAGRAPH_SEPARATOR;
        boolean result = Util.isWhitespace(c);
        assertTrue(result);
    }
}