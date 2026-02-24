package org.apache.commons.cli.help;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;

public class TextHelpAppendable_43_1Test {

    @Test(timeout = 8000)
    public void testResize() {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(null); // Passing null as Appendable since it's not used in this test

        TextStyle.Builder textStyleBuilder = null;
        try {
            Constructor<TextStyle.Builder> constructor = TextStyle.Builder.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            textStyleBuilder = constructor.newInstance(); // Changed to use reflection to access private constructor
        } catch (Exception e) {
            e.printStackTrace();
        }

        textStyleBuilder.setIndent(3);
        textStyleBuilder.setMaxWidth(100);
        textStyleBuilder.setMinWidth(50);

        TextStyle.Builder resultBuilder = textHelpAppendable.resize(textStyleBuilder, 0.5);

        assertEquals(75, resultBuilder.getMaxWidth());
        assertEquals(3, resultBuilder.getIndent());
    }
}