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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TextHelpAppendable_33_1Test {

    @Test(timeout = 8000)
    public void testGetIndent() throws Exception {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(System.out);
        
        Field textStyleBuilderField = TextHelpAppendable.class.getDeclaredField("textStyleBuilder");
        textStyleBuilderField.setAccessible(true);
        Class<?> textStyleBuilderClass = Class.forName("org.apache.commons.cli.help.TextStyle$Builder");
        Object textStyleBuilderInstance = textStyleBuilderClass.getDeclaredConstructor().newInstance();
        textStyleBuilderField.set(textHelpAppendable, textStyleBuilderInstance);
        
        Method setIndentMethod = textStyleBuilderClass.getDeclaredMethod("setIndent", int.class);
        setIndentMethod.setAccessible(true);
        setIndentMethod.invoke(textStyleBuilderInstance, 5);
        
        Method getIndentMethod = TextHelpAppendable.class.getDeclaredMethod("getIndent");
        getIndentMethod.setAccessible(true);
        int actualIndent = (int) getIndentMethod.invoke(textHelpAppendable);
        
        assertEquals(5, actualIndent);
    }
}