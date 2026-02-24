package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayOutputStream;

public class TextHelpAppendable_44_1Test {

    @Test
    @Timeout(8000)
    public void testSetIndent() {
        // Given
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(new StringBuilder());
        int expectedIndent = 5;

        // When
        textHelpAppendable.setIndent(expectedIndent);
        int actualIndent = textHelpAppendable.getIndent();

        // Then
        assertEquals(expectedIndent, actualIndent);
    }
}