package org.apache.commons.cli.help;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class TextHelpAppendable_32_1Test {

    @Test(timeout = 8000)
    public void testAppendTitle() throws Exception {
        // Setup
        TextHelpAppendable textHelpAppendable = spy(new TextHelpAppendable(mock(Appendable.class)));
        CharSequence title = "Sample Title";

        // Invoke method
        textHelpAppendable.appendTitle(title);

        // Verify
        verify(textHelpAppendable, times(1)).appendTitle(title);
    }
}