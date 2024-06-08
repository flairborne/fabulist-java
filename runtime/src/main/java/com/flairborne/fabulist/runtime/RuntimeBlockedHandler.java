package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.context.message.ChoiceSelectMessage;
import com.flairborne.fabulist.runtime.context.message.Message;

final class RuntimeBlockedHandler implements RuntimeStateHandler {

    @Override
    public Runtime.State handle(Runtime runtime) {
        Message message = runtime.poll();

        if (message == null) {
            return Runtime.State.BLOCKED;
        }

        String type = message.type();

        if (type.equals("choice-select")) {
            return handleChoiceSelect(runtime, message);
        } else {
            return Runtime.State.BLOCKED;
        }
    }

    private Runtime.State handleChoiceSelect(Runtime runtime, Message message) {
        var id = ElementId.from(((ChoiceSelectMessage) message).id());
        runtime.updateCurrentNode(id);

        return Runtime.State.READY;
    }
}
