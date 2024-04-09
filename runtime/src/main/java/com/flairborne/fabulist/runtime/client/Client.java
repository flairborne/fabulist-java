package com.flairborne.fabulist.runtime.client;

import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.runtime.server.EmbeddedServer;

public class Client {

    private final String name;
    private EmbeddedServer server;

    public Client(String name) {
        this.name = name;
    }

    public void connect(EmbeddedServer server) {
        this.server = server;
        server.addListener(new ClientMessageListener());
    }

    public void send(Message message) {
        server.sendMessage(message);
    }
}
