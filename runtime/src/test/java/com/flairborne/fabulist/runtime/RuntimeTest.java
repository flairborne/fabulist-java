package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.element.channel.message.NextMessage;
import com.flairborne.fabulist.element.character.Character;
import com.flairborne.fabulist.element.context.BasicContext;
import com.flairborne.fabulist.element.part.Part;
import com.flairborne.fabulist.element.part.node.Scene;
import com.flairborne.fabulist.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RuntimeTest {

    private Runtime createRuntime(Part.Builder builder) {
        var part = builder.build();
        var context = new BasicContext();
        var server = new EmbeddedServer(context, part);

        return server.runtime();
    }

    @Test
    void it_creates_runtime() {
        var runtime = createRuntime(new Part.Builder("part")
                .addNode(new Scene.Builder("intro"))
        );

        assertEquals(ElementId.from("intro"), runtime.currentNode().id());
        assertEquals(Runtime.READY, runtime.currentState());
        assertNull(runtime.previousState());
    }

    @Test
    void it_steps_with_dialogue() {
        var stuart = Character.from("stuart", "Stuart");
        var runtime = createRuntime(new Part.Builder("part")
                .addNode(new Scene.Builder("intro")
                        .addDialogue(stuart.quote("Hi")))
        );

        runtime.server().sendMessage(new NextMessage());
        assertEquals(Runtime.READY, runtime.currentState());

        runtime.server().sendMessage(new NextMessage());
        assertEquals(Runtime.FINISHED, runtime.currentState());
    }

    @Test
    void it_steps_with_passthrough() {
        var stuart = Character.from("stuart", "Stuart");
        var runtime = createRuntime(new Part.Builder("part")
                .addNode(new Scene.Builder("scene-a")
                        .addDialogue(stuart.quote("Banana"))
                        .addPassthrough("scene-b"))
                .addNode(new Scene.Builder("scene-b"))
        );


        runtime.server().sendMessage(new NextMessage());

        assertEquals(Runtime.READY, runtime.currentState());
        assertEquals(ElementId.from("scene-a"), runtime.currentNode().id());

        runtime.server().sendMessage(new NextMessage());

        assertEquals(Runtime.FINISHED, runtime.currentState());
        assertEquals(ElementId.from("scene-b"), runtime.currentNode().id());
    }

    @Test
    void it_steps_with_choices() {
        var stuart = Character.from("stuart", "Stuart");
        var runtime = createRuntime(new Part.Builder("part")
                .addNode(new Scene.Builder("scene-a")
                        .addDialogue(stuart.quote("What fruit should I eat?"))
                        .addChoice("scene-b"))
                .addNode(new Scene.Builder("scene-b")
                        .addDialogue(stuart.quote("Banana")))
                .addNode(new Scene.Builder("scene-c")
                        .addDialogue(stuart.quote("Apple")))
        );

        runtime.server().sendMessage(new NextMessage());

        assertEquals(Runtime.BLOCKED, runtime.currentState());
        assertEquals(ElementId.from("scene-a"), runtime.currentNode().id());

        runtime.server().sendMessage(new ChoiceSelectMessage("scene-b"));

        assertEquals(Runtime.PAUSED, runtime.currentState());
        assertEquals(ElementId.from("scene-b"), runtime.currentNode().id());
    }
}
