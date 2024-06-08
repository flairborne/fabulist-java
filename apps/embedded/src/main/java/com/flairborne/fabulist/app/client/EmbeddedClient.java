package com.flairborne.fabulist.app.client;

import com.flairborne.fabulist.runtime.Runtime;
import com.flairborne.fabulist.runtime.context.message.Message;

public class EmbeddedClient {

    private final Runtime runtime;


    public EmbeddedClient(Runtime runtime) {
        this.runtime = runtime;
        runtime.addListener(new ClientMessageListener());
    }

    public void send(Message message) {
        runtime.sendMessage(message);
    }
}
