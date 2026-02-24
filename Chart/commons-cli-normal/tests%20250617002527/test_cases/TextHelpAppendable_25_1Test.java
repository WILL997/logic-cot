package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextHelpAppendable_25_1Test {

    @Test(timeout = 8000)
    public void testSystemOut() {
        TextHelpAppendable textHelpAppendable = TextHelpAppendable.systemOut();
        assertNotNull(textHelpAppendable);
        // Verify that the output of systemOut method is a new instance of TextHelpAppendable
        assertTrue(textHelpAppendable instanceof TextHelpAppendable);
    }
}