package com.flairborne.fabulist.app;

import com.flairborne.fabulist.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.element.channel.message.NextMessage;
import com.flairborne.fabulist.runtime.client.Client;
import com.flairborne.fabulist.runtime.server.EmbeddedServer;
import com.flairborne.fabulist.runtime.server.Server;

import java.util.Scanner;

class Repl {

    private final Scanner input;

    private final Server server;
    private final Client client;
    private boolean isFinished;

    Repl(Client client, EmbeddedServer server) {
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

    private void dump(Server server, Client client) {
        var previousState = previousStateName();
        var currentState = currentStateName();

        System.out.printf("Server [%s -> %s]\n", previousState, currentState);
    }

    private String currentStateName() {
        return server.currentState() == null ? "None" : server.currentState().getClass().getSimpleName();
    }

    private String previousStateName() {
        return server.previousState() == null ? "None" : server.previousState().getClass().getSimpleName();
    }
}
