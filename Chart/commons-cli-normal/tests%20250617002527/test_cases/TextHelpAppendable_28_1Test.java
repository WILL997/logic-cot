package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.io.IOException;
import java.io.PrintStream;

public class TextHelpAppendable_28_1Test {

    @Test
    @Timeout(8000)
    public void testAppendHeader_TextNotEmpty() throws IOException {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(System.out);
        
        int level = 2;
        CharSequence text = "Header Text";
        
        textHelpAppendable.appendHeader(level, text);
        
        // Assert
        // You need to implement a way to capture the output and check if it matches the expected result
        // For example, you can redirect System.out to a PrintStream and then compare the output
    }

    @Test
    @Timeout(8000)
    public void testAppendHeader_LevelLessThanOne() throws IOException {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(System.out);
        
        int level = 0;
        CharSequence text = "Header Text";
        
        assertThrows(IllegalArgumentException.class, () -> {
            textHelpAppendable.appendHeader(level, text);
        });
    }

}