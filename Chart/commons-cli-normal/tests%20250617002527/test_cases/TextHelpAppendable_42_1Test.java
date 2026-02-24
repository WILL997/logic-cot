package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextHelpAppendable_42_1Test {

    @Test
    @Timeout(8000)
    public void testResize() {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(null); // Passing null as Appendable since it's not used in the resize method
        int orig = 10;
        double fraction = 0.5;
        int expected = 5;
        int result = textHelpAppendable.resize(orig, fraction);
        assertEquals(expected, result);
    }
}