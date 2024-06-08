package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.context.message.Message;

final class RuntimePaused implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Message message = runtime.poll();

        if (message == null) {
            return Runtime.PAUSED;
        }

        String type = message.type();

        if (type.equals("next") || type.equals("change-context")) {
            return Runtime.READY;
        } else {
            return Runtime.PAUSED;
        }
    }
}
