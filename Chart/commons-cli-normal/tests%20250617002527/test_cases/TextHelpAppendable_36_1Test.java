package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextHelpAppendable_36_1Test {

    @Test(timeout = 8000)
    public void testGetTextStyleBuilder() {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(null); // Passing null as Appendable for testing purpose

        TextStyle.Builder textStyleBuilder = textHelpAppendable.getTextStyleBuilder();

        assertNotNull(textStyleBuilder);
    }
}