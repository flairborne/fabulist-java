package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.runtime.element.channel.message.Message;
import com.flairborne.fabulist.runtime.Runtime;

public final class RuntimePaused implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Message message = runtime.server().inboundMessages().poll();

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
