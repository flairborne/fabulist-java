package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.runtime.Runtime;

public class RuntimeActing implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        var current = runtime.currentNode();
        var action = current.actions().poll();

        if (action != null) {
            Message message = action.act(runtime.context());
            runtime.server().broadcast(message);
        }

        return Runtime.PAUSED;
    }
}
