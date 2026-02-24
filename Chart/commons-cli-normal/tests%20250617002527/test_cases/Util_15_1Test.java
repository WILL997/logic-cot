package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Util_15_1Test {

    @Test(timeout = 8000)
    public void testDefaultValue_whenInputStringIsEmpty_thenReturnDefaultValue() throws Exception {
        // Given
        String input = "";
        String defaultValue = "default";

        // When
        String result = Util.class.getDeclaredMethod("defaultValue", CharSequence.class, CharSequence.class)
                .invoke(null, input, defaultValue).toString();

        // Then
        assertEquals(defaultValue, result);
    }
}