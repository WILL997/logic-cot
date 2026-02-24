package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextHelpAppendable_24_1Test {

    @Test(timeout = 8000)
    public void testIndexOfWrap_widthLessThanOne() {
        try {
            TextHelpAppendable.indexOfWrap("Sample text to wrap", 0, 0);
            fail("Expected IllegalArgumentException for width less than 1");
        } catch (IllegalArgumentException e) {
            assertEquals("Width must be greater than 0", e.getMessage());
        }
    }

}