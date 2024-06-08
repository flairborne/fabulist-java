package com.flairborne.fabulist.app;

import com.flairborne.fabulist.app.client.EmbeddedClient;
import com.flairborne.fabulist.runtime.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.runtime.element.channel.message.NextMessage;

import java.util.Scanner;

class Repl {

    private final Scanner input;

    private final EmbeddedClient client;
    private boolean isFinished;

    Repl(EmbeddedClient client) {
        this.client = client;

        input = new Scanner(System.in);

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
    }

    boolean isFinished() {
        return isFinished;
    }
}
