package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Util_22_1Test {

    @Test(timeout = 8000)
    public void testRtrim() throws Exception {
        String input = "  Hello World   ";
        String expectedOutput = "  Hello World";
        
        String actualOutput = Util.rtrim(input);
        
        assertEquals(expectedOutput, actualOutput);
    }
}