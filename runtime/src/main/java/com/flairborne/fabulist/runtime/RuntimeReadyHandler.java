package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.part.node.Node;

final class RuntimeReadyHandler implements RuntimeStateHandler {

    @Override
    public Runtime.State handle(Runtime runtime) {
        Node currentNode = runtime.currentNode();
        boolean hasActions = !currentNode.actions().isEmpty();

        if (hasActions) {
            return Runtime.State.ACTING;
        }

        if (currentNode.hasLinkages()) {
            return Runtime.State.BRANCHING;
        } else {
            return Runtime.State.FINISHED;
        }
    }
}
