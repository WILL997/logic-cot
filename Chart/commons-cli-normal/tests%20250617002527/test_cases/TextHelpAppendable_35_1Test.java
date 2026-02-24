package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TextHelpAppendable_35_1Test {

    @Test(timeout = 8000)
    public void testGetMaxWidth() {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(null); // Pass a dummy Appendable

        int maxWidth = textHelpAppendable.getMaxWidth();

        assertNotNull(maxWidth);
        assertEquals(74, maxWidth);
    }
}