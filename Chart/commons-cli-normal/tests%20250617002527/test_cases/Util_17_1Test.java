package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.*;

public class Util_17_1Test {

    @Test(timeout = 8000)
    public void testIsEmpty_NullInput() throws Exception {
        // Given
        CharSequence str = null;

        // When
        boolean result = Util.isEmpty(str);

        // Then
        assertTrue(result);
    }

    @Test(timeout = 8000)
    public void testIsEmpty_EmptyStringInput() throws Exception {
        // Given
        CharSequence str = "";

        // When
        boolean result = Util.isEmpty(str);

        // Then
        assertTrue(result);
    }

    @Test(timeout = 8000)
    public void testIsEmpty_NonEmptyStringInput() throws Exception {
        // Given
        CharSequence str = "Hello";

        // When
        boolean result = Util.isEmpty(str);

        // Then
        assertFalse(result);
    }
}