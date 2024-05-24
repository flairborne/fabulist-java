package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.runtime.element.channel.message.FinishedMessage;
import com.flairborne.fabulist.runtime.Runtime;

public final class RuntimeFinished implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        var message = new FinishedMessage();
        runtime.server().broadcast(message);

        return Runtime.FINISHED;
    }
}
