package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.apache.commons.cli.help.TextHelpAppendable;

public class TextHelpAppendable_34_1Test {

    @Test(timeout = 8000)
    public void testGetLeftPad() {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(null); // Pass null as Appendable since it's not used in this test

        int leftPad = textHelpAppendable.getLeftPad();

        assertEquals(1, leftPad);
    }
}