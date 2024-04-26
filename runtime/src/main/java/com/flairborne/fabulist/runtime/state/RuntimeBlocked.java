package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.runtime.Runtime;

public final class RuntimeBlocked implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Message message = runtime.server().inboundMessages().poll();

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
