package org.apache.commons.cli.help;

import org.junit.Test;
import org.apache.commons.cli.help.TextHelpAppendable;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TextHelpAppendable_39_1Test {

    @Test(timeout = 8000)
    public void testPrintQueue() throws IOException {
        // Create a mock Appendable
        Appendable output = mock(Appendable.class);
        
        // Create a TextHelpAppendable instance with the mock Appendable
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(output);
        
        // Create a Queue of strings
        Queue<String> queue = new LinkedList<>();
        queue.add("Hello");
        queue.add("World");
        
        // Call the private method printQueue using reflection
        try {
            Method method = TextHelpAppendable.class.getDeclaredMethod("printQueue", Queue.class);
            method.setAccessible(true);
            method.invoke(textHelpAppendable, queue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Verify that the appendFormat method was called with the correct arguments
        verify(output).append("Hello\n");
        verify(output).append("World\n");
    }
}