package com.flairborne.fabulist.app.client;

import com.flairborne.fabulist.app.server.EmbeddedServer;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.runtime.server.Server;

public class Client {

    private final String name;
    private Server server;

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
