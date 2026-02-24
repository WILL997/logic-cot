package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TextStyle_9_1Test {

    @Test
    @Timeout(8000)
    public void testGetLeftPad() throws Exception {
        TextStyle.Builder builder = TextStyle.builder();
        try {
            Method leftPadMethod = TextStyle.Builder.class.getDeclaredMethod("leftPad", int.class);
            leftPadMethod.setAccessible(true);
            leftPadMethod.invoke(builder, 4);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        TextStyle textStyle = builder.get();

        assertEquals(4, textStyle.getLeftPad());
    }
}