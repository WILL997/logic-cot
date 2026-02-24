package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextStyle_6_1Test {

    @Test
    @Timeout(8000)
    public void testTextStyleBuilder() {
        TextStyle text = TextStyle.builder()
                .alignment(TextStyle.Alignment.CENTER)
                .leftPad(4)
                .indent(2)
                .scalable(true)
                .minWidth(10)
                .maxWidth(20)
                .build();

        assertEquals(TextStyle.Alignment.CENTER, text.getAlignment());
        assertEquals(4, text.getLeftPad());
        assertEquals(2, text.getIndent());
        assertEquals(true, text.isScalable());
        assertEquals(10, text.getMinWidth());
        assertEquals(20, text.getMaxWidth());
    }

    @Test
    @Timeout(8000)
    public void testTextStyleToString() {
        TextStyle text = TextStyle.builder()
                .alignment(TextStyle.Alignment.LEFT)
                .leftPad(2)
                .indent(1)
                .scalable(false)
                .minWidth(5)
                .maxWidth(15)
                .build();

        String expected = "TextStyle{alignment=LEFT, leftPad=2, indent=1, scalable=false, minWidth=5, maxWidth=15}";
        assertEquals(expected, text.toString());
    }
}