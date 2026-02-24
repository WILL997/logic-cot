package org.apache.commons.cli.help;
import org.junit.Test;
import org.apache.commons.cli.help.TextHelpAppendable;
import org.apache.commons.cli.help.TextStyle;
import java.util.Queue;
import java.util.LinkedList;

public class TextHelpAppendable_37_1Test {

    @Test(timeout = 8000)
    public void testMakeColumnQueue() {
        // Create a TextHelpAppendable instance
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(null);

        // Create a TextStyle instance
        TextStyle style = TextStyle.DEFAULT;

        // Define columnData for testing
        String columnData = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        // Call the makeColumnQueue method
        Queue<String> result = textHelpAppendable.makeColumnQueue(columnData, style);

        // Create expected queue of formatted strings
        Queue<String> expected = new LinkedList<>();
        expected.add("Lorem ipsum dolor sit amet,");
        expected.add(" consectetur adipiscing elit.");

        // Assert that the result matches the expected value
        assert result.equals(expected);
    }
}