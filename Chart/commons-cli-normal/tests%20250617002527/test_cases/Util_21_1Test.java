package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Util_21_1Test {

    @Test(timeout = 8000)
    public void testRepeatSpace() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Given
        int len = 5;
        String expected = "     ";
        
        // When
        Method repeatSpaceMethod = Util.class.getDeclaredMethod("repeatSpace", int.class);
        repeatSpaceMethod.setAccessible(true);
        String result = (String) repeatSpaceMethod.invoke(null, len);
        
        // Then
        assertEquals(expected, result);
    }
}