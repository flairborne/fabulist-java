package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.runtime.Runtime;

import java.util.Optional;

public class RuntimePaused implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Optional<Message> potentialMessage = runtime.server().peek();

        if (potentialMessage.isEmpty()) {
            return Runtime.PAUSED;
        }

        Message message = potentialMessage.get();
        String type = message.type();

        if (type.equals("next")) {
            return Runtime.READY;
        } else {
            return Runtime.PAUSED;
        }
    }
}
