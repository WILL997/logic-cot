package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextStyle_5_1Test {

    @Test
    @Timeout(8000)
    public void testBuilder() {
        TextStyle.Builder builder = TextStyle.builder();
        
        // Set specific formatting properties
        builder.alignment(TextStyle.Alignment.CENTER)
               .leftPad(2)
               .indent(4)
               .scalable(true)
               .minWidth(10)
               .maxWidth(20);
        
        TextStyle textStyle = builder.build();
        
        // Test individual properties
        assertEquals(TextStyle.Alignment.CENTER, textStyle.getAlignment());
        assertEquals(2, textStyle.getLeftPad());
        assertEquals(4, textStyle.getIndent());
        assertEquals(true, textStyle.isScalable());
        assertEquals(10, textStyle.getMinWidth());
        assertEquals(20, textStyle.getMaxWidth());
    }
}