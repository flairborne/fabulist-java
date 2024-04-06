package com.flairborne.fabulist.element.channel;

import com.flairborne.fabulist.element.channel.message.Message;

import java.util.LinkedList;
import java.util.Queue;

public class MemoryQueueChannel implements Channel, ReadChannel, WriteChannel {

    private final String name;
    private final Queue<Message> messages;

    protected MemoryQueueChannel(String name) {
        this.name = name;
        messages = new LinkedList<>();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void send(Message message) {
        messages.add(message);
    }

    @Override
    public Message poll() {
        return messages.poll();
    }

    @Override
    public Message peek() {
        return messages.peek();
    }

    @Override
    public String toString() {
        return messages.toString();
    }

    @Override
    public int size() {
        return messages.size();
    }
}
