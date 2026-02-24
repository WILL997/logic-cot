package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.apache.commons.cli.help.FilterHelpAppendable;

public class FilterHelpAppendable_4_1Test {

    @Test
    @Timeout(8000)
    public void testAppend() throws IOException {
        StringBuilder sb = new StringBuilder();
        FilterHelpAppendable filterHelpAppendable = new FilterHelpAppendable(sb) {
            @Override
            public FilterHelpAppendable append(final char ch) {
                try {
                    output.append(ch);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return this;
            }

            @Override
            public FilterHelpAppendable append(final CharSequence text) {
                return this;
            }

            @Override
            public FilterHelpAppendable appendTitle(CharSequence title) {
                return this;
            }
        };

        filterHelpAppendable.append('A');

        assertEquals("A", sb.toString());
    }
}