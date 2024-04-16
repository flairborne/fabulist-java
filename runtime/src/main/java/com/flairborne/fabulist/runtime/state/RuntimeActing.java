package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.element.action.Action;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.part.node.Node;
import com.flairborne.fabulist.runtime.Runtime;

import java.util.function.Predicate;

public class RuntimeActing implements RuntimeState {

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
