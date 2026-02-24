package org.apache.commons.cli.help;
import org.apache.commons.cli.help.TextHelpAppendable;
import org.apache.commons.cli.help.TextStyle;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TextHelpAppendable_45_1Test {

    @Test(timeout = 8000)
    public void testSetLeftPad() {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(System.out);
        int leftPad = 5;

        textHelpAppendable.setLeftPad(leftPad);

        assertEquals(leftPad, textHelpAppendable.getLeftPad());
    }
}