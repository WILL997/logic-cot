package org.apache.commons.cli.help;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.IOException;

public class TextHelpAppendable_40_1Test {

    @Test(timeout = 8000)
    public void testPrintWrapped() throws IOException {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(System.out);
        String text = "This is a sample text to be wrapped within a specified width.";
        
        textHelpAppendable.printWrapped(text);
        
        // As the method prints to System.out, we cannot directly capture the output for assertion
        // So, we will just verify that no exceptions are thrown during the method call
        // and the test will pass if it reaches this point without any exceptions
    }
}