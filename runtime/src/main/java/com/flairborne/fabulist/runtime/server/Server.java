package com.flairborne.fabulist.runtime.server;

import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.runtime.Runtime;
import com.flairborne.fabulist.runtime.state.RuntimeState;

import java.util.Queue;

/**
 * A server facilitates communication with its clients using {@link Message messages}.
 */
public interface Server {

    /**
     * Send a message to all clients
     *
     * @param message message to send
     */
    void broadcast(Message message);

    /**
     * Send a message to this server
     *
     * @param message message to send
     */
    void sendMessage(Message message);

    /**
     * @return messages that are sent to this server
     */
    Queue<Message> inboundMessages();

    /**
     * @return runtime in this server
     */
    Runtime runtime();

    /**
     * @return current runtime state
     */
    RuntimeState currentState();

    /**
     * @return previous runtime state
     */
    RuntimeState previousState();
}
