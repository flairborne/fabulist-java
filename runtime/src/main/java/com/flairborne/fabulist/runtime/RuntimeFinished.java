package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.channel.message.FinishedMessage;

final class RuntimeFinished implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        var message = new FinishedMessage();
        runtime.broadcast(message);

        return Runtime.FINISHED;
    }
}
