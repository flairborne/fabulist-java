package com.flairborne.fabulist.app;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.action.ChangeContext;
import com.flairborne.fabulist.element.action.Dialogue;
import com.flairborne.fabulist.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.element.channel.message.NextMessage;
import com.flairborne.fabulist.element.character.Character;
import com.flairborne.fabulist.element.context.BasicContext;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.part.Part;
import com.flairborne.fabulist.element.part.linkage.Choice;
import com.flairborne.fabulist.element.part.node.Scene;
import com.flairborne.fabulist.runtime.client.Client;
import com.flairborne.fabulist.runtime.server.EmbeddedServer;

import java.util.Scanner;

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

        var input = new Scanner(System.in);
        var context = new BasicContext();

        // Testing shared memory server and client setup
        var server = new EmbeddedServer(context, part);
        var client = new Client("player");

        client.connect(server);

        System.out.println("\nWelcome to the Fabulist REPL! :)\n");

        while (true) {
            System.out.print("(%) > ");
            String command = input.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                break;
            }

            if (command.isEmpty() || command.equalsIgnoreCase("next")) {
                client.send(new NextMessage());
            }

            if (command.startsWith("choice")) {
                String[] commandArgs = command.split("\\s+");

                if (commandArgs.length < 2) {
                    System.out.println("Insufficient args");
                    continue;
                }

                client.send(new ChoiceSelectMessage(commandArgs[1]));
            }

            if (command.equalsIgnoreCase("dump")) {
                dump(server, client);
            }
        }
    }

    private static void dump(EmbeddedServer embeddedServer, Client client) {
        var previousState = embeddedServer.previousStateName();
        var currentState = embeddedServer.currentState().getClass().getSimpleName();

        System.out.printf("Server [%s -> %s]\n", previousState, currentState);
    }
}
