package com.flairborne.fabulist.element.part;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.part.node.Node;
import com.flairborne.fabulist.element.part.node.Scene;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartTest {

    @Test
    void it_creates_part() {
        Part part = new Part.Builder("intro").build();

        assertNull(part.root());
        assertEquals(0, part.size());
    }

    @Test
    void it_creates_part_with_node() {
        Part part = new Part.Builder("intro")
                .addNode(new Scene.Builder("first"))
                .build();

        assertEquals(ElementId.from("first"), part.root().id());
        assertEquals(1, part.size());
    }

    @Test
    void it_creates_part_with_nodes() {
        Part part = new Part.Builder("intro")
                .addNode(new Scene.Builder("first"))
                .addNode(new Scene.Builder("second"))
                .addNode(new Scene.Builder("third"))
                .build();

        assertEquals(ElementId.from("first"), part.root().id());
        assertEquals(3, part.size());
    }

    @Test
    void it_retrieves_node_from_part() {
        Part part = new Part.Builder("intro")
                .addNode(new Scene.Builder("morning"))
                .build();

        Optional<Node> node = part.getNode("morning");

        assertTrue(node.isPresent());
        assertEquals(ElementId.from("morning"), node.get().id());
    }
}
