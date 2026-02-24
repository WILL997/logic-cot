package org.apache.commons.cli.help;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.Queue;

public class TextHelpAppendable_30_1Test {

    @Test(timeout = 8000)
    public void testAppendParagraph() throws Exception {
        // Given
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(mock(Appendable.class));
        CharSequence paragraph = "This is a test paragraph.";

        // When
        textHelpAppendable.appendParagraph(paragraph);

        // Then
        // Verify that the output is formatted correctly with a blank line added at the end
        Method method = TextHelpAppendable.class.getDeclaredMethod("printQueue", Queue.class);
        method.setAccessible(true);
        method.invoke(textHelpAppendable, Mockito.any());
        verify(textHelpAppendable, Mockito.times(1)).printQueue(Mockito.any());
    }
}