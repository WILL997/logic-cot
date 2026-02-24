package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.cli.help.TextHelpAppendable;
import org.apache.commons.cli.help.TextStyle;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextHelpAppendable_41_1Test {

    @Test
    @Timeout(8000)
    public void testPrintWrapped() throws IOException {
        // Given
        String text = "This is a sample text to be wrapped.";
        TextStyle.Builder styleBuilder = TextStyle.builder();
        TextStyle style = styleBuilder.build();

        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(new ByteArrayOutputStream());

        // When
        textHelpAppendable.printWrapped(text, style);

        // Then
        // Since the method prints to an Appendable, we will capture the output and check it
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        textHelpAppendable.getOutput().writeTo(outputStream);
        String printedText = outputStream.toString();

        // Verify the printed text matches the expected wrapped format
        assertEquals("This is a sample text to be\nwrapped.\n", printedText);
    }
}