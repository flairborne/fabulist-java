package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.context.message.Message;

final class RuntimePausedHandler implements RuntimeStateHandler {

    @Override
    public Runtime.State handle(Runtime runtime) {
        Message message = runtime.poll();

        if (message == null) {
            return Runtime.State.PAUSED;
        }

        String type = message.type();

        if (type.equals("next") || type.equals("change-context")) {
            return Runtime.State.READY;
        } else {
            return Runtime.State.PAUSED;
        }
    }
}
