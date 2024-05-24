package com.flairborne.fabulist.app.server;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.element.action.Dialogue;
import com.flairborne.fabulist.runtime.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.runtime.element.channel.message.NextMessage;
import com.flairborne.fabulist.runtime.element.character.Character;
import com.flairborne.fabulist.runtime.element.context.BasicContext;
import com.flairborne.fabulist.runtime.element.part.Part;
import com.flairborne.fabulist.runtime.element.part.linkage.Choice;
import com.flairborne.fabulist.runtime.element.part.linkage.Passthrough;
import com.flairborne.fabulist.runtime.element.part.node.Scene;
import com.flairborne.fabulist.runtime.Runtime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EmbeddedServerTest {

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
                        .addAction(new Dialogue.Builder(stuart.quote("Hi"))))
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
                        .addAction(new Dialogue.Builder(stuart.quote("Banana")))
                        .addLinkage(new Passthrough.Builder("scene-a", "scene-b")))
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
                        .addAction(new Dialogue.Builder(stuart.quote("What fruit should I eat?")))
                        .addLinkage(new Choice.Builder("scene-a", "scene-b")))
                .addNode(new Scene.Builder("scene-b")
                        .addAction(new Dialogue.Builder(stuart.quote("Banana"))))
                .addNode(new Scene.Builder("scene-c")
                        .addAction(new Dialogue.Builder(stuart.quote("Apple"))))
        );

        runtime.server().sendMessage(new NextMessage());

        assertEquals(Runtime.BLOCKED, runtime.currentState());
        assertEquals(ElementId.from("scene-a"), runtime.currentNode().id());

        runtime.server().sendMessage(new ChoiceSelectMessage("scene-b"));

        assertEquals(Runtime.PAUSED, runtime.currentState());
        assertEquals(ElementId.from("scene-b"), runtime.currentNode().id());
    }
}
