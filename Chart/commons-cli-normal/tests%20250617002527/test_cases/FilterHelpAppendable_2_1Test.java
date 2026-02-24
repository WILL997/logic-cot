package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.apache.commons.cli.help.FilterHelpAppendable;

public class FilterHelpAppendable_2_1Test {

    @Test
    @Timeout(8000)
    public void testAppend_char() throws IOException {
        // Setup
        StringBuilder sb = new StringBuilder();
        FilterHelpAppendable filterHelpAppendable = new FilterHelpAppendableImpl(sb);

        // Invoke method
        FilterHelpAppendable result = filterHelpAppendable.append('A');

        // Verify output
        assertEquals(filterHelpAppendable, result);
        assertEquals("A", sb.toString());
    }

    private static class FilterHelpAppendableImpl extends FilterHelpAppendable {
        public FilterHelpAppendableImpl(Appendable output) {
            super(output);
        }

        @Override
        public FilterHelpAppendable append(CharSequence text) {
            return null; // Not needed for this test
        }

        @Override
        public FilterHelpAppendable append(CharSequence csq, int start, int end) {
            return null; // Not needed for this test
        }

        @Override
        public FilterHelpAppendable appendTitle(CharSequence title) {
            return null; // Not needed for this test
        }
    }
}