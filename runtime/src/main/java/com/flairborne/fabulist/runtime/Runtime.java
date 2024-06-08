package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.element.channel.MessageListener;
import com.flairborne.fabulist.runtime.element.channel.message.Message;
import com.flairborne.fabulist.runtime.context.Context;
import com.flairborne.fabulist.runtime.element.part.Part;
import com.flairborne.fabulist.runtime.element.part.node.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * A finite-state machine that processes the progression of a {@link Part part}.
 * It produces {@link Message messages} depending on the {@link RuntimeState state} it is currently in.
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

    /**
     * Trigger the {@link RuntimeState state} transitions to update and move forward.
     * An additional state update is triggered during interruption so the runtime is
     * given a chance to be unblocked and transition to another state.
     */
    private void step() {
        while (!isFinished()) {
            updateState();

            if (isInterrupted()) {
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

    private boolean isInterrupted() {
        return isPaused() || isBlocked() || isFinished();
    }

    private boolean isPaused() {
        return currentState == PAUSED;
    }

    private boolean isBlocked() {
        return currentState == BLOCKED;
    }

    private boolean isFinished() {
        return currentState == FINISHED;
    }
}
