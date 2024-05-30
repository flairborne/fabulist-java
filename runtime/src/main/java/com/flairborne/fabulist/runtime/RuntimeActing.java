package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.action.Action;
import com.flairborne.fabulist.runtime.element.channel.message.Message;
import com.flairborne.fabulist.runtime.element.context.Context;
import com.flairborne.fabulist.runtime.element.part.node.Node;

import java.util.function.Predicate;

final class RuntimeActing implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Node current = runtime.currentNode();
        Action action = current.actions().poll();

        if (action == null) {
            return Runtime.READY;
        }

        Context context = runtime.context();
        Predicate<Context> condition = action.condition();

        if (condition == null || condition.test(context)) {
            Message message = action.act(context);
            runtime.server().broadcast(message);
        }

        boolean skipPause = current.actions().isEmpty() && current.isInteractive();

        if (action.isVisible() && !skipPause) {
            return Runtime.PAUSED;
        } else {
            return Runtime.READY;
        }
    }
}
