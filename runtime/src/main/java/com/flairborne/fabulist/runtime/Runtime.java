package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.element.channel.MessageListener;
import com.flairborne.fabulist.runtime.element.channel.message.Message;
import com.flairborne.fabulist.runtime.element.context.Context;
import com.flairborne.fabulist.runtime.element.part.Part;
import com.flairborne.fabulist.runtime.element.part.node.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * A finite-state machine with {@link RuntimeState} as its state interface.
 */
public class Runtime {

    public static final RuntimeState READY = new RuntimeReady();
    public static final RuntimeState ACTING = new RuntimeActing();
    public static final RuntimeState PAUSED = new RuntimePaused();
    public static final RuntimeState BRANCHING = new RuntimeBranching();
    public static final RuntimeState BLOCKED = new RuntimeBlocked();
    public static final RuntimeState FINISHED = new RuntimeFinished();

    private final Context context;
    private final Part part;
    private final List<MessageListener> messageListeners;
    private final Queue<Message> messages;

    private Node currentNode;
    private RuntimeState previousState;
    private RuntimeState currentState;

    public Runtime(Context context, Part part) {
        this.context = context;
        this.part = part;
        messageListeners = new ArrayList<>();
        messages = new LinkedList<>();
        currentNode = part.root();
        setCurrentState(READY);
    }

    public void broadcast(Message message) {
        for (MessageListener listener : messageListeners) {
            listener.onReceive(message);
        }
    }

    public void addListener(MessageListener messageListener) {
        messageListeners.add(messageListener);
    }

    public void sendMessage(Message message) {
        messages.add(message);
        step();
    }

    public Message poll() {
        return messages.poll();
    }

    public void step() {
        while (!isFinished()) {
            updateState();

            // Step once to allow it to process its interrupted state
            if (isPaused() || isBlocked() || isFinished()) {
                updateState();
                break;
            }
        }
    }

    private void updateState() {
        RuntimeState nextState = currentState.handle(this);
        setCurrentState(nextState);
    }

    public void updateCurrentNode(ElementId id) {
        Optional<Node> next = getNode(id);
        next.ifPresent(node -> currentNode = node);
    }

    public Optional<Node> getNode(ElementId id) {
        return part.getNode(id);
    }

    public Node currentNode() {
        return currentNode;
    }

    public Context context() {
        return context;
    }

    public RuntimeState previousState() {
        return previousState;
    }

    public RuntimeState currentState() {
        return currentState;
    }

    private void setCurrentState(RuntimeState newState) {
        if (currentState == newState) {
            return;
        }

        this.previousState = this.currentState;
        this.currentState = newState;
    }

    public boolean isPaused() {
        return currentState == PAUSED;
    }

    public boolean isBlocked() {
        return currentState == BLOCKED;
    }

    public boolean isFinished() {
        return currentState == FINISHED;
    }
}
