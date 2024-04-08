package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.part.Part;
import com.flairborne.fabulist.element.part.node.Node;
import com.flairborne.fabulist.runtime.server.Server;
import com.flairborne.fabulist.runtime.state.RuntimeActing;
import com.flairborne.fabulist.runtime.state.RuntimeBlocked;
import com.flairborne.fabulist.runtime.state.RuntimeBranching;
import com.flairborne.fabulist.runtime.state.RuntimeFinished;
import com.flairborne.fabulist.runtime.state.RuntimePaused;
import com.flairborne.fabulist.runtime.state.RuntimeReady;
import com.flairborne.fabulist.runtime.state.RuntimeState;

import java.util.Optional;

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

    private final Server server;
    private final Context context;
    private final Part part;

    private Node currentNode;
    private RuntimeState previousState;
    private RuntimeState currentState;

    public Runtime(Server server, Context context, Part part) {
        this.server = server;
        this.context = context;
        this.part = part;
        currentNode = part.root();
        setCurrentState(READY);
    }

    public void step() {
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

    public Server server() {
        return server;
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

    public void setCurrentState(RuntimeState newState) {
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
