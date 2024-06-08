package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.context.action.Action;
import com.flairborne.fabulist.runtime.context.message.Message;
import com.flairborne.fabulist.runtime.context.Context;
import com.flairborne.fabulist.runtime.element.part.node.Node;

import java.util.function.Predicate;

final class RuntimeActingHandler implements RuntimeStateHandler {

    @Override
    public Runtime.State handle(Runtime runtime) {
        Node currentNode = runtime.currentNode();
        Action action = currentNode.actions().poll();

        if (action == null) {
            return Runtime.State.READY;
        }

        Context context = runtime.context();
        Predicate<Context> condition = action.condition();

        if (condition == null || condition.test(context)) {
            Message message = action.act(context);
            runtime.broadcast(message);
        }

        boolean skipPause = currentNode.actions().isEmpty() && currentNode.isBlocking();

        if (action.isBlocking() && !skipPause) {
            return Runtime.State.PAUSED;
        } else {
            return Runtime.State.READY;
        }
    }
}
