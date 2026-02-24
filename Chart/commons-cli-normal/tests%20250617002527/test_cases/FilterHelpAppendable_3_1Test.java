package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.cli.help.FilterHelpAppendable;
import java.io.IOException;

public class FilterHelpAppendable_3_1Test {

    @Test
    @Timeout(8000)
    public void testAppend() {
        // Given
        StringBuilder sb = new StringBuilder();
        FilterHelpAppendable filterHelpAppendable = new FilterHelpAppendableTestImpl(sb);

        // When
        try {
            filterHelpAppendable.append("Hello, World!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then
        assertEquals("Hello, World!", sb.toString());
    }

    private static class FilterHelpAppendableTestImpl extends FilterHelpAppendable {
        public FilterHelpAppendableTestImpl(Appendable output) {
            super(output);
        }

        @Override
        public FilterHelpAppendable append(CharSequence text) {
            try {
                output.append(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        @Override
        public FilterHelpAppendable append(char ch) {
            try {
                output.append(ch);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        @Override
        public FilterHelpAppendable append(CharSequence csq, int start, int end) {
            try {
                output.append(csq, start, end);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        @Override
        public FilterHelpAppendable appendTitle(CharSequence title) {
            return this;
        }
    }
}