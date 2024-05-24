package com.flairborne.fabulist.app;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.element.action.ChangeContext;
import com.flairborne.fabulist.runtime.element.action.Dialogue;
import com.flairborne.fabulist.runtime.element.character.Character;
import com.flairborne.fabulist.runtime.element.context.BasicContext;
import com.flairborne.fabulist.runtime.element.context.Context;
import com.flairborne.fabulist.runtime.element.part.Part;
import com.flairborne.fabulist.runtime.element.part.linkage.Choice;
import com.flairborne.fabulist.runtime.element.part.node.Scene;
import com.flairborne.fabulist.app.client.EmbeddedClient;
import com.flairborne.fabulist.app.server.EmbeddedServer;

public class App {

    public static void main(String[] args) {
        // Define characters
        var alice = Character.from("alice", "Alice");
        var bob = Character.from("bob", "Bob");

        // Define part
        var part = new Part.Builder(ElementId.random())
                .addNode(new Scene.Builder("alice-hi")
                        .withActions(
                                new ChangeContext.Builder(ChangeContext.Operation.SET_BOOLEAN, "is-fine", true),
                                new Dialogue.Builder(alice.quote("Hey, my name is Alice")),
                                new Dialogue.Builder(alice.quote("How you doing?"))
                        )
                        .withLinkages(
                                new Choice.Builder("alice-hi", "alice-happy")
                                        .displayText("Hello, I'm Bob. I'm fine"),
                                new Choice.Builder("alice-hi", "alice-sad")
                                        .displayText("I don't care about you, Alice!")
                        )
                )
                .addNode(new Scene.Builder("alice-happy")
                        .withActions(
                                new Dialogue.Builder(alice.quote("Nah, you're not fine. You're \"Bob!\"")),
                                new Dialogue.Builder(bob.quote("Okay..?")),
                                new Dialogue.Builder(alice.quote("I'm \"punny\" right?")),
                                new Dialogue.Builder(bob.quote("Nah, just stop..."))
                        )
                )
                .addNode(new Scene.Builder("alice-sad")
                        .withActions(
                                new ChangeContext.Builder(ChangeContext.Operation.SET_BOOLEAN, "leaving", true),
                                new Dialogue.Builder(alice.quote("Wow, you're mean")),
                                new Dialogue.Builder(alice.quote("Okay, I can't take this anymore. I'm leaving now..."))
                                        .when(Context.isPropertyTrue("leaving"))
                        )
                )
                .build();
        
        var context = new BasicContext();

        // Testing shared memory server and client setup
        var server = new EmbeddedServer(context, part);
        var client = new EmbeddedClient("player");

        var repl = new Repl(client, server);

        while (!repl.isFinished()) {
            repl.update();
        }
    }
}
