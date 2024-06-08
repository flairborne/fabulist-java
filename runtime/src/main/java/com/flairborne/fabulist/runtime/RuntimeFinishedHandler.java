package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.context.message.FinishedMessage;

final class RuntimeFinishedHandler implements RuntimeStateHandler {

    @Override
    public Runtime.State handle(Runtime runtime) {
        var message = new FinishedMessage();
        runtime.broadcast(message);

        return Runtime.State.FINISHED;
    }
}
