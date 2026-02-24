package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Util_20_1Test {

    @Test(timeout = 8000)
    public void testRepeat() throws Exception {
        // Given
        int len = 5;
        char fillChar = '*';
        String expected = "*****";

        // When
        String result = Util.repeat(len, fillChar);

        // Then
        assertEquals(expected, result);
    }
}