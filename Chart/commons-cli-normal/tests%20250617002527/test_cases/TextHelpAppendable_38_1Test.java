package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TextHelpAppendable_38_1Test {

    @Test
    @Timeout(8000)
    public void testMakeColumnQueues() {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(mock(Appendable.class));

        List<String> columnData = Arrays.asList("Column 1", "Column 2", "Column 3");
        List<TextStyle> styles = new ArrayList<>();
        styles.add(new TextStyle.Builder().bold(true).build());
        styles.add(new TextStyle.Builder().italic(true).build());
        styles.add(new TextStyle.Builder().underline(true).build());

        List<Queue<String>> expected = new ArrayList<>();
        Queue<String> queue1 = new LinkedList<>();
        queue1.add("Column 1");
        expected.add(queue1);

        Queue<String> queue2 = new LinkedList<>();
        queue2.add("Column 2");
        expected.add(queue2);

        Queue<String> queue3 = new LinkedList<>();
        queue3.add("Column 3");
        expected.add(queue3);

        List<Queue<String>> actual = textHelpAppendable.makeColumnQueues(columnData, styles);

        assertEquals(expected, actual);
    }

    private static class TextStyleBuilderMock extends TextStyle.Builder {
        public TextStyleBuilderMock bold(boolean bold) {
            return super.bold(bold);
        }

        public TextStyleBuilderMock italic(boolean italic) {
            return super.italic(italic);
        }

        public TextStyleBuilderMock underline(boolean underline) {
            return super.underline(underline);
        }
    }

    private static class TextStyleMock extends TextStyle {
        public static class Builder extends TextStyleBuilderMock {
            @Override
            public TextStyle build() {
                return super.build();
            }
        }
    }

    private static class TextStyleMockStatic {
        public static TextStyleMock.Builder Builder() {
            return new TextStyleMock.Builder();
        }
    }

    private static class TextStyleMockStaticAccessor {
        public static TextStyleMock.Builder accessBuilder() {
            return TextStyleMockStatic.Builder();
        }
    }

    private static class TextStyleMockAccessor {
        public static TextStyle.Builder accessBuilder() {
            return TextStyleMockStaticAccessor.accessBuilder();
        }
    }

    private static class TextStyleMockAccessorField {
        public static final TextStyle.Builder INSTANCE = TextStyleMockAccessor.accessBuilder();
    }

    private static class TextStyleMockAccessorMethod {
        public static TextStyle.Builder getInstance() {
            return TextStyleMockAccessorField.INSTANCE;
        }
    }
}