package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;

public class FilterHelpAppendable_1_1Test {

    @Test
    @Timeout(8000)
    public void testFilterHelpAppendable() throws IOException {
        // Create a mock Appendable object
        Appendable mockAppendable = mock(Appendable.class);

        // Create a FilterHelpAppendable object for testing
        FilterHelpAppendable filterHelpAppendable = new FilterHelpAppendable(mockAppendable) {
            @Override
            public FilterHelpAppendable append(char ch) {
                return this;
            }

            @Override
            public FilterHelpAppendable append(CharSequence text) {
                return this;
            }

            @Override
            public FilterHelpAppendable append(CharSequence csq, int start, int end) {
                return this;
            }

            @Override
            public FilterHelpAppendable appendTitle(CharSequence title) {
                return this;
            }
        };

        // Test the append(char ch) method
        FilterHelpAppendable result1 = filterHelpAppendable.append('a');
        assertEquals(filterHelpAppendable, result1);

        // Test the append(CharSequence text) method
        FilterHelpAppendable result2 = filterHelpAppendable.append("Sample text");
        assertEquals(filterHelpAppendable, result2);

        // Test the append(CharSequence csq, int start, int end) method
        FilterHelpAppendable result3 = filterHelpAppendable.append("Sample text", 0, 5);
        assertEquals(filterHelpAppendable, result3);
    }
}