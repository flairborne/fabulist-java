package com.flairborne.fabulist.runtime.server;

import com.flairborne.fabulist.element.channel.Channel;
import com.flairborne.fabulist.element.channel.ReadChannel;
import com.flairborne.fabulist.element.channel.WriteChannel;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.part.Part;
import com.flairborne.fabulist.runtime.Runtime;
import com.flairborne.fabulist.runtime.state.RuntimeState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Server {

    private final ReadChannel readChannel;
    private final WriteChannel writeChannel;
    private final List<WriteChannel> clientWriteChannels;

    private final Runtime runtime;

    public Server(Context context, Part part) {
        runtime = new Runtime(this, context, part);

        var channel = Channel.memoryQueue("server");
        readChannel = (ReadChannel) channel;
        writeChannel = (WriteChannel) channel;

        clientWriteChannels = new ArrayList<>();
    }

    public void broadcast(Message message) {
        for (WriteChannel channel : clientWriteChannels) {
            channel.send(message);
        }
    }

    public Optional<Message> peek() {
        return Optional.ofNullable(readChannel.peek());
    }

    public Optional<Message> poll() {
        return Optional.ofNullable(readChannel.poll());
    }

    public ReadChannel connect(String name) {
        var channel = Channel.memoryQueue(name);

        clientWriteChannels.add((WriteChannel) channel);

        return (ReadChannel) channel;
    }

    public void writeToServer(Message message) {
        writeChannel.send(message);

        // TODO: Handle this somewhere much better
        if (message.type().equals("next")) {
            if (runtime.isBlocked()) {
                runtime.step();
            }

            fastForwardState();

            readChannel.poll();
        }
    }

    private void fastForwardState() {
        while (!runtime.isBlocked() && !runtime.isFinished()) {
            runtime.step();
        }
    }

    public WriteChannel writeChannel() {
        return writeChannel;
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

    public boolean isFinished() {
        return runtime.isFinished();
    }
}
