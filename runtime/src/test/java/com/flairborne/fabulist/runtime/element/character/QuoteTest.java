package com.flairborne.fabulist.runtime.element.character;

import com.flairborne.fabulist.runtime.element.ElementId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuoteTest {

    @Test
    void it_creates_quote() {
        var william = Character.from("william", "William");
        var quote = Quote.from(william, "To be or not to be");

        assertEquals(ElementId.from("william"), quote.character().id());
        assertEquals("To be or not to be", quote.text());
    }
}
