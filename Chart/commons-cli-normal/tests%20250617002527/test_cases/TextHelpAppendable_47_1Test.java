package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TextHelpAppendable_47_1Test {

    private TextHelpAppendable textHelpAppendable;

    @BeforeEach
    public void setUp() {
        textHelpAppendable = new TextHelpAppendable(mock(Appendable.class));
    }

    @AfterEach
    public void tearDown() {
        textHelpAppendable = null;
    }

    @Test
    @Timeout(8000)
    public void testWriteColumnQueues() throws IOException {
        // Prepare data
        List<Queue<String>> columnQueues = new ArrayList<>();
        Queue<String> queue1 = new LinkedList<>();
        queue1.add("Apple");
        queue1.add("Banana");
        Queue<String> queue2 = new LinkedList<>();
        queue2.add("Carrot");
        queue2.add("Date");
        columnQueues.add(queue1);
        columnQueues.add(queue2);

        List<TextStyle> styles = new ArrayList<>();
        TextStyle.Builder styleBuilder1 = null;
        try {
            styleBuilder1 = (TextStyle.Builder) Class.forName("org.apache.commons.cli.help.TextStyle$Builder").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        styleBuilder1.setMaxWidth(10).setLeftPad(2);
        TextStyle style1 = styleBuilder1.build();

        TextStyle.Builder styleBuilder2 = null;
        try {
            styleBuilder2 = (TextStyle.Builder) Class.forName("org.apache.commons.cli.help.TextStyle$Builder").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        styleBuilder2.setMaxWidth(8).setLeftPad(1);
        TextStyle style2 = styleBuilder2.build();

        styles.add(style1);
        styles.add(style2);

        // Call the method
        try {
            textHelpAppendable.writeColumnQueues(columnQueues, styles);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assert the output
        String expectedOutput = "Apple    Carrot  \nBanana   Date    \n";
        assertEquals(expectedOutput, textHelpAppendable.getOutput().toString());
    }
}