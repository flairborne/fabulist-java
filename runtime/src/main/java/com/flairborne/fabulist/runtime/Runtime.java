package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.context.message.MessageListener;
import com.flairborne.fabulist.runtime.context.message.Message;
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
 * It produces {@link Message messages} depending on the {@link State state} it is currently in.
 */
public class Runtime {

    public enum State {
        READY(new RuntimeReadyHandler()),
        ACTING(new RuntimeActingHandler()),
        PAUSED(new RuntimePausedHandler()),
        BRANCHING(new RuntimeBranchingHandler()),
        BLOCKED(new RuntimeBlockedHandler()),
        FINISHED(new RuntimeFinishedHandler());

        private final RuntimeStateHandler handler;

        State(RuntimeStateHandler handler) {
            this.handler = handler;
        }
    }

    private final Context context;
    private final Part part;
    private final List<MessageListener> messageListeners;
    private final Queue<Message> messages;

    private Node currentNode;
    private State previousState;
    private State currentState;

    public Runtime(Context context, Part part) {
        this.context = context;
        this.part = part;
        messageListeners = new ArrayList<>();
        messages = new LinkedList<>();
        currentNode = part.root();
        setCurrentState(State.READY);
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
     * Trigger the {@link RuntimeStateHandler state} transitions to update and move forward.
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
        Runtime.State nextState = currentState.handler.handle(this);
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

    public State previousState() {
        return previousState;
    }

    public State currentState() {
        return currentState;
    }

    private void setCurrentState(State newState) {
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
        return currentState == State.PAUSED;
    }

    private boolean isBlocked() {
        return currentState == State.BLOCKED;
    }

    private boolean isFinished() {
        return currentState == State.FINISHED;
    }
}
