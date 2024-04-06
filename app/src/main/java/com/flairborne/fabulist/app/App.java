package com.flairborne.fabulist.app;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.element.channel.message.SimpleMessage;
import com.flairborne.fabulist.element.character.Character;
import com.flairborne.fabulist.element.context.BasicContext;
import com.flairborne.fabulist.element.part.Part;
import com.flairborne.fabulist.element.part.node.Scene;
import com.flairborne.fabulist.runtime.client.Client;
import com.flairborne.fabulist.runtime.server.Server;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // Define characters
        var alice = Character.from("alice", "Alice");
        var bob = Character.from("bob", "Bob");

        // Define part
        var part = new Part.Builder(ElementId.random())
                .addNode(new Scene.Builder("alice-hi")
                        .addDialogue(alice.quote("Hey, my name is Alice"))
                        .addDialogue(alice.quote("How you doing?"))
                        .addChoice("bob-fine")
                        .addChoice("bob-rude"))
                .addNode(new Scene.Builder("bob-fine")
                        .addDialogue(bob.quote("Hello, I'm Bob. I'm fine"))
                        .addPassthrough("alice-happy"))
                .addNode(new Scene.Builder("bob-rude")
                        .addDialogue(bob.quote("I don't care about you, Alice!"))
                        .addPassthrough("alice-sad"))
                .addNode(new Scene.Builder("alice-happy")
                        .addDialogue(alice.quote("Nah, you're not fine. You're \"Bob!\""))
                        .addDialogue(bob.quote("Okay..?"))
                        .addDialogue(alice.quote("I'm \"punny\" right?"))
                        .addDialogue(bob.quote("Nah, just stop...")))
                .addNode(new Scene.Builder("alice-sad")
                        .addDialogue(alice.quote("Wow, you're mean"))
                        .addDialogue(alice.quote("Okay, I can't take this anymore. I'm leaving now...")))
                .build();

        var input = new Scanner(System.in);
        var context = new BasicContext();

        // Testing shared memory server and client setup
        var server = new Server(context, part);
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
                client.send(new SimpleMessage("next"));
                client.poll();
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
                var previousState = server.previousState().getClass().getSimpleName();
                var currentState = server.currentState().getClass().getSimpleName();

                var serverChannel = server.writeChannel();
                var clientChannel = client.readChannel();

                System.out.printf("Server [%s -> %s], Message(s): %s\n", previousState, currentState, serverChannel);
                System.out.printf("Client Message(s): %s\n", clientChannel);
            }
        }
    }
}
