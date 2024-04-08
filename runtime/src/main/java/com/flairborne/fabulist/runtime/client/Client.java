package com.flairborne.fabulist.runtime.client;

import com.flairborne.fabulist.element.channel.ReadChannel;
import com.flairborne.fabulist.element.channel.message.ChoicePresentMessage;
import com.flairborne.fabulist.element.channel.message.DialogueMessage;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.runtime.server.Server;

public class Client {

    private final String name;
    private ReadChannel readChannel;
    private Server server;

    public Client(String name) {
        this.name = name;
    }

    public void connect(Server server) {
        this.server = server;
        readChannel = server.connect(name);
    }

    public void send(Message message) {
        server.writeToServer(message);
    }

    public void poll() {
        var message = readChannel.poll();

        if (message == null) {
            return;
        }

        if (message.type().equals("dialogue")) {
            var dialogue = (DialogueMessage) message;
            System.out.printf("[%s]: %s\n", dialogue.character(), dialogue.message());
        }

        if (message.type().equals("choice-present")) {
            var choice = (ChoicePresentMessage) message;

            for (var choiceMessage : choice.choiceMessages()) {
                System.out.printf("- [%s]: %s\n", choiceMessage.id(), choiceMessage.displayText());
            }
        }

        if (message.type().equals("finished")) {
            System.exit(0);
        }
    }

    public ReadChannel readChannel() {
        return readChannel;
    }

    public Message receive() {
        return readChannel.poll();
    }
}
