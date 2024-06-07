package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.runtime.element.channel.message.Message;

final class RuntimeBlocked implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Message message = runtime.server().messages().poll();

        if (message == null) {
            return Runtime.BLOCKED;
        }

        String type = message.type();

        if (type.equals("choice-select")) {
            return handleChoiceSelect(runtime, message);
        } else {
            return Runtime.BLOCKED;
        }
    }

    private RuntimeState handleChoiceSelect(Runtime runtime, Message message) {
        var id = ElementId.from(((ChoiceSelectMessage) message).id());
        runtime.updateCurrentNode(id);

        return Runtime.READY;
    }
}
