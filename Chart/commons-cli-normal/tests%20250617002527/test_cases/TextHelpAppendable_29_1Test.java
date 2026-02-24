package org.apache.commons.cli.help;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TextHelpAppendable_29_1Test {

    @Test(timeout = 8000)
    public void testAppendList_whenListNotNullAndNotEmpty_ordered() throws IOException {
        // Given
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(mock(Appendable.class));
        List<CharSequence> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");

        // When
        textHelpAppendable.appendList(true, list);

        // Then
        // Verify that the output contains the formatted ordered list
        verify(textHelpAppendable.getOutput()).append(" 1. Item 1");
        verify(textHelpAppendable.getOutput()).append(" 2. Item 2");
    }

    @Test(timeout = 8000)
    public void testAppendList_whenListNotNullAndNotEmpty_unordered() throws IOException {
        // Given
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(mock(Appendable.class));
        List<CharSequence> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");

        // When
        textHelpAppendable.appendList(false, list);

        // Then
        // Verify that the output contains the formatted unordered list
        verify(textHelpAppendable.getOutput()).append(" * Item 1");
        verify(textHelpAppendable.getOutput()).append(" * Item 2");
    }

    private static class TextHelpAppendable extends org.apache.commons.cli.help.TextHelpAppendable {
        public TextHelpAppendable(Appendable output) {
            super(output);
        }

        public Appendable getOutput() {
            return output;
        }
    }
}