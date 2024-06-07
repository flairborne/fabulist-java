package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.part.node.Node;

final class RuntimeReady implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Node currentNode = runtime.currentNode();
        boolean hasActions = !currentNode.actions().isEmpty();

        if (hasActions) {
            return Runtime.ACTING;
        }

        if (currentNode.hasLinkages()) {
            return Runtime.BRANCHING;
        } else {
            return Runtime.FINISHED;
        }
    }
}
