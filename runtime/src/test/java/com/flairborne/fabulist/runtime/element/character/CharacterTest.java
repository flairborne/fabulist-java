package com.flairborne.fabulist.runtime.element.character;

import com.flairborne.fabulist.runtime.element.ElementId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterTest {

    @Test
    void it_creates_character_from_id() {
        var bob = Character.from(ElementId.from("bob"), "Bob");

        assertEquals(ElementId.from("bob"), bob.id());
        assertEquals("Bob", bob.name());
    }

    @Test
    void it_creates_character_from_id_string() {
        var alice = Character.from("alice", "Alice");

        assertEquals(ElementId.from("alice"), alice.id());
        assertEquals("Alice", alice.name());
    }

    @Test
    void it_creates_quote_from_character() {
        var kara = Character.from("kara", "Kara");
        var quote = kara.quote("Hello");

        assertEquals(ElementId.from("kara"), quote.character().id());
    }
}
