package org.apache.commons.cli.help;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Util_16_1Test {

    @Test(timeout = 8000)
    public void testIndexOfNonWhitespace() throws Exception {
        CharSequence text = "   Hello World";
        int startPos = 3;
        
        // Test when the start position is 3
        int result1 = Util.indexOfNonWhitespace(text, startPos);
        assertEquals(3, result1);
        
        // Test when the start position is 0
        int result2 = Util.indexOfNonWhitespace(text, 0);
        assertEquals(3, result2);
        
        // Test when the text is empty
        CharSequence emptyText = "";
        int result3 = Util.indexOfNonWhitespace(emptyText, 0);
        assertEquals(-1, result3);
    }
}