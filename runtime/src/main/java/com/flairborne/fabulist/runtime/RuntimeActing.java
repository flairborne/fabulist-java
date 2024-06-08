package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.action.Action;
import com.flairborne.fabulist.runtime.element.channel.message.Message;
import com.flairborne.fabulist.runtime.context.Context;
import com.flairborne.fabulist.runtime.element.part.node.Node;

import java.util.function.Predicate;

final class RuntimeActing implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Node currentNode = runtime.currentNode();
        Action action = currentNode.actions().poll();

        if (action == null) {
            return Runtime.READY;
        }

        Context context = runtime.context();
        Predicate<Context> condition = action.condition();

        if (condition == null || condition.test(context)) {
            Message message = action.act(context);
            runtime.broadcast(message);
        }

        boolean skipPause = currentNode.actions().isEmpty() && currentNode.isBlocking();

        if (action.isBlocking() && !skipPause) {
            return Runtime.PAUSED;
        } else {
            return Runtime.READY;
        }
    }
}
