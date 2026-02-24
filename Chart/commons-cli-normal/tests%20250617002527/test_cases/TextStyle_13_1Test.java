package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextStyle_13_1Test {

    @Test
    @Timeout(8000)
    public void testPad() throws Exception {
        TextStyle.Builder builder = TextStyle.builder();
        TextStyle textStyle = builder.alignment(TextStyle.Alignment.LEFT)
                                      .indent(4)
                                      .maxWidth(20)
                                      .build();

        CharSequence text = "Hello";
        boolean addIndent = true;

        CharSequence paddedText = textStyle.pad(addIndent, text);

        assertEquals("    Hello          ", paddedText.toString());
    }
}