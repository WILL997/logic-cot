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
import java.io.ByteArrayOutputStream;

public class TextHelpAppendable_46_1Test {

    @Test
    @Timeout(8000)
    public void testSetMaxWidth() {
        // Given
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(new ByteArrayOutputStream());
        int maxWidth = 80;

        // When
        textHelpAppendable.setMaxWidth(maxWidth);

        // Then
        assertEquals(maxWidth, textHelpAppendable.getMaxWidth());
    }
}