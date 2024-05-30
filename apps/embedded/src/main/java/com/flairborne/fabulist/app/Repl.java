package com.flairborne.fabulist.app;

import com.flairborne.fabulist.app.client.EmbeddedClient;
import com.flairborne.fabulist.app.server.EmbeddedServer;
import com.flairborne.fabulist.runtime.RuntimeState;
import com.flairborne.fabulist.runtime.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.runtime.element.channel.message.NextMessage;
import com.flairborne.fabulist.runtime.server.Server;

import java.util.Scanner;

class Repl {

    private final Scanner input;

    private final Server server;
    private final EmbeddedClient client;
    private boolean isFinished;

    Repl(EmbeddedClient client, EmbeddedServer server) {
        this.client = client;
        this.server = server;

        input = new Scanner(System.in);

        client.connect(server);

        System.out.println("\nWelcome to the Fabulist REPL! :)\n");
    }

    void update() {
        System.out.print("(%) > ");
        String command = input.nextLine();

        if (command.equalsIgnoreCase("exit")) {
            isFinished = true;
            return;
        }

        if (command.isEmpty() || command.equalsIgnoreCase("next")) {
            client.send(new NextMessage());
        }

        if (command.startsWith("choice")) {
            String[] commandArgs = command.split("\\s+");

            if (commandArgs.length < 2) {
                System.out.println("Insufficient args");
                return;
            }

            client.send(new ChoiceSelectMessage(commandArgs[1]));
        }

        if (command.equalsIgnoreCase("dump")) {
            dump(server, client);
        }
    }

    boolean isFinished() {
        return isFinished;
    }

    private void dump(Server server, EmbeddedClient client) {
        var previousState = previousStateName();
        var currentState = currentStateName();

        System.out.printf("Server [%s -> %s]\n", previousState, currentState);
    }

    private String currentStateName() {
        RuntimeState currentState = server.runtime().currentState();
        return currentState == null ? "None" : currentState.getClass().getSimpleName();
    }

    private String previousStateName() {
        RuntimeState previousState = server.runtime().previousState();
        return previousState == null ? "None" : previousState.getClass().getSimpleName();
    }
}
