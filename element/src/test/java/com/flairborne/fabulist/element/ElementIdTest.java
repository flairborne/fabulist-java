package com.flairborne.fabulist.element;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElementIdTest {

    @Test
    void it_creates_element_id() {
        ElementId id = ElementId.from("identity");

        assertEquals("identity", id.toString());
    }

    @Test
    void it_compares_equal_element_ids() {
        ElementId a = ElementId.from("a");
        ElementId b = ElementId.from("a");

        assertEquals(a, b);
    }

    @Test
    void it_compares_unequal_element_ids() {
        ElementId a = ElementId.from("a");
        ElementId b = ElementId.from("b");

        assertNotEquals(a, b);
    }

    @Test
    void it_maintains_singleton_element_ids() {
        ElementId a = ElementId.from("a");
        ElementId b = ElementId.from("a");

        assertSame(a, b);
    }
}
