package org.apache.commons.cli.help;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextHelpAppendable_26_1Test {

    @Test
    @Timeout(8000)
    public void testAppendHeader() throws IOException {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(Mockito.mock(Appendable.class));
        int level = 1;
        String text = "Header text";

        textHelpAppendable.appendHeader(level, text);
    }

    @Test
    @Timeout(8000)
    public void testAppendList() throws IOException {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(Mockito.mock(Appendable.class));
        boolean ordered = false;
        Collection<CharSequence> list = Arrays.asList("Item 1", "Item 2", "Item 3");

        textHelpAppendable.appendList(ordered, list);
    }

    @Test
    @Timeout(8000)
    public void testAppendParagraph() throws IOException {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(Mockito.mock(Appendable.class));
        String paragraph = "This is a paragraph of text.";

        textHelpAppendable.appendParagraph(paragraph);
    }

    @Test
    @Timeout(8000)
    public void testAppendTable() throws IOException {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(Mockito.mock(Appendable.class));
        TableDefinition table = Mockito.mock(TableDefinition.class);

        textHelpAppendable.appendTable(table);
    }

    @Test
    @Timeout(8000)
    public void testAppendTitle() throws IOException {
        TextHelpAppendable textHelpAppendable = new TextHelpAppendable(Mockito.mock(Appendable.class));
        String title = "Page Title";

        textHelpAppendable.appendTitle(title);
    }
}