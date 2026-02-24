package org.apache.commons.cli.help;
import java.util.function.Supplier;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TextStyle_12_1Test {

    @Test(timeout = 8000)
    public void testIsScalableTrue() throws Exception {
        TextStyle.Builder builder = createBuilder();
        builder.withScalable(true);
        TextStyle textStyle = createTextStyle(builder);

        boolean result = textStyle.isScalable();

        assertEquals(true, result);
    }

    @Test(timeout = 8000)
    public void testIsScalableFalse() throws Exception {
        TextStyle.Builder builder = createBuilder();
        builder.withScalable(false);
        TextStyle textStyle = createTextStyle(builder);

        boolean result = textStyle.isScalable();

        assertEquals(false, result);
    }

    private TextStyle.Builder createBuilder() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<TextStyle.Builder> constructor = TextStyle.Builder.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private TextStyle createTextStyle(TextStyle.Builder builder) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<TextStyle> constructor = TextStyle.class.getDeclaredConstructor(TextStyle.Builder.class);
        constructor.setAccessible(true);
        return constructor.newInstance(builder);
    }
}