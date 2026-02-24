package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextStyle_10_1Test {

    @Test
    @Timeout(8000)
    public void testGetMaxWidth() throws Exception {
        TextStyle.Builder builder = TextStyle.builder();
        builder.alignment(TextStyle.Alignment.LEFT);
        builder.leftPad(2);
        builder.indent(4);
        builder.scalable(true);
        builder.minWidth(0);
        builder.maxWidth(100);

        TextStyle textStyle = builder.get();

        assertEquals(100, textStyle.getMaxWidth());
    }
}