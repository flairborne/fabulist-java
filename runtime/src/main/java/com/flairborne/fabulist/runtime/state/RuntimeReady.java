package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.runtime.Runtime;

public final class RuntimeReady implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        var current = runtime.currentNode();
        boolean hasActions = !current.actions().isEmpty();

        if (hasActions) {
            return Runtime.ACTING;
        }

        if (current.hasLinkages()) {
            return Runtime.BRANCHING;
        } else {
            return Runtime.FINISHED;
        }
    }
}
