package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextStyle_8_1Test {

    @Test
    @Timeout(8000)
    public void testGetIndent() {
        TextStyle textStyle = TextStyle.builder()
                .alignment(TextStyle.Alignment.LEFT)
                .leftPad(4)
                .indent(2)
                .scalable(true)
                .minWidth(10)
                .maxWidth(20)
                .build();

        assertEquals(2, textStyle.getIndent());
    }
}