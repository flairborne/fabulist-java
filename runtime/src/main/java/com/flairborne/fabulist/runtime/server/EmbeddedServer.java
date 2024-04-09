package com.flairborne.fabulist.runtime.server;

import com.flairborne.fabulist.element.channel.MessageListener;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.part.Part;
import com.flairborne.fabulist.runtime.Runtime;
import com.flairborne.fabulist.runtime.state.RuntimeState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EmbeddedServer implements Server {

    private final Runtime runtime;
    private final Queue<Message> inboundMessages;
    private final MessageListener serverMessageListener;
    private final List<MessageListener> clientMessageListeners;

    public EmbeddedServer(Context context, Part part) {
        runtime = new Runtime(this, context, part);
        inboundMessages = new LinkedList<>();
        serverMessageListener = new ServerMessageListener(runtime);
        clientMessageListeners = new ArrayList<>();
    }

    @Override
    public void broadcast(Message message) {
        for (MessageListener listener : clientMessageListeners) {
            listener.onReceive(message);
        }
    }

    public void addListener(MessageListener messageListener) {
        clientMessageListeners.add(messageListener);
    }

    @Override
    public void sendMessage(Message message) {
        inboundMessages.add(message);
        serverMessageListener.onReceive(message);
    }

    @Override
    public Queue<Message> inboundMessages() {
        return inboundMessages;
    }

    public void update() {
        runtime.step();
    }

    public RuntimeState currentState() {
        return runtime.currentState();
    }

    public RuntimeState previousState() {
        return runtime.previousState();
    }

    public String previousStateName() {
        var state = runtime.previousState();

        return state == null ? "None" : state.getClass().getSimpleName();
    }

    public boolean isFinished() {
        return runtime.isFinished();
    }
}
