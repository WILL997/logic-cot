package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.cli.help.TableDefinition;
import org.apache.commons.cli.help.TextHelpAppendable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextHelpAppendable_31_1Test {

    @Test
    @Timeout(8000)
    public void testAppendTable() throws IOException {
        // Create a mock TableDefinition
        TableDefinition tableDefinition = Mockito.mock(TableDefinition.class);

        // Set up the necessary data for the TableDefinition
        List<String> headers = Arrays.asList("Header1", "Header2", "Header3");
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("Row1Col1", "Row1Col2", "Row1Col3"));
        rows.add(Arrays.asList("Row2Col1", "Row2Col2", "Row2Col3"));

        Mockito.when(tableDefinition.caption()).thenReturn("Table Caption");
        Mockito.when(tableDefinition.columnTextStyles()).thenReturn(new ArrayList<>());
        Mockito.when(tableDefinition.headers()).thenReturn(headers);
        Mockito.when(tableDefinition.rows()).thenReturn(rows);

        // Create a TextHelpAppendable instance
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(System.out);

        // Call the appendTable method
        textHelpAppendable.appendTable(tableDefinition);

        // Assert the output (assuming the output is stored in a StringBuilder in TextHelpAppendable)
        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append("Table Caption").append(System.lineSeparator());
        expectedOutput.append("Header1  Header2  Header3").append(System.lineSeparator());
        expectedOutput.append("Row1Col1 Row1Col2 Row1Col3").append(System.lineSeparator());
        expectedOutput.append("Row2Col1 Row2Col2 Row2Col3").append(System.lineSeparator());

        String actualOutput = textHelpAppendable.getOutput().toString();

        assertEquals(expectedOutput.toString(), actualOutput);
    }
}