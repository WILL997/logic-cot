package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.cli.help.TextStyle;
import org.apache.commons.cli.help.TextStyle.Alignment;

public class TextStyle_14_1Test {

    @Test
    @Timeout(8000)
    public void testToString() {
        TextStyle textStyle = TextStyle.builder()
                .alignment(TextStyle.Alignment.CENTER)
                .leftPad(2)
                .indent(4)
                .scalable(true)
                .minWidth(10)
                .maxWidth(20)
                .build();

        String expected = "TextStyle{CENTER, l:2, i:4, scalable, min:10, max:20}";
        String actual = textStyle.toString();

        assertEquals(expected, actual);
    }
}