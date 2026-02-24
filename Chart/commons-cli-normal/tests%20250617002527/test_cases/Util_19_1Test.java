package org.apache.commons.cli.help;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Util_19_1Test {

    @Test(timeout = 8000)
    public void testLtrim() throws Exception {
        // Given
        String input = "   Hello, World!";
        String expectedOutput = "Hello, World!";
        
        // When
        String actualOutput = Util.ltrim(input);
        
        // Then
        assertEquals(expectedOutput, actualOutput);
    }
}