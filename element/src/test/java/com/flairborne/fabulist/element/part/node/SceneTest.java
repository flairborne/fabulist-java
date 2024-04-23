package com.flairborne.fabulist.element.part.node;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.part.linkage.Choice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SceneTest {

    @Test
    void it_creates_scene() {
        var scene = new Scene.Builder("scene-a").build();

        assertEquals(scene.id(), ElementId.from("scene-a"));
    }

    @Test
    void it_creates_interactive_scene() {
        var scene = new Scene.Builder("scene-a")
                .addLinkage(new Choice.Builder("scene-a", "scene-b"))
                .build();

        assertTrue(scene.isInteractive());
    }
}
