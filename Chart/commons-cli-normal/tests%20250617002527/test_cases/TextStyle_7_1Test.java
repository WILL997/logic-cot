package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.function.Supplier;

import org.apache.commons.cli.help.TextStyle;
import org.apache.commons.cli.help.TextStyle.Alignment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextStyle_7_1Test {

    @Test
    @Timeout(8000)
    public void testGetAlignment() throws Exception {
        // Given
        TextStyle.Alignment expectedAlignment = TextStyle.Alignment.LEFT;
        TextStyle textStyle = TextStyle.builder()
                .withAlignment(expectedAlignment)
                .build();

        // When
        TextStyle.Alignment actualAlignment = textStyle.getAlignment();

        // Then
        assertEquals(expectedAlignment, actualAlignment);
    }
}