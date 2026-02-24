package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextStyle_11_1Test {

    @Test
    @Timeout(8000)
    public void testGetMinWidth() throws Exception {
        TextStyle.Builder builder = TextStyle.builder();
        builder.alignment(TextStyle.Alignment.LEFT)
               .leftPad(2)
               .indent(4)
               .scalable(true)
               .minWidth(10)
               .maxWidth(20);
        
        TextStyle textStyle = builder.get();
        
        assertEquals(10, textStyle.getMinWidth());
    }
}