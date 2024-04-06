package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.channel.message.ChoiceSelectMessage;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.runtime.Runtime;

import java.util.Optional;

public class RuntimeBlocked implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Optional<Message> potentialMessage = runtime.server().peek();

        if (potentialMessage.isEmpty()) {
            return Runtime.BLOCKED;
        }

        Message message = potentialMessage.get();
        String type = message.type();

        if (type.equals("next")) {
            return handleNext(runtime);
        } else if (type.equals("choice-select")) {
            return handleChoiceSelect(runtime, message);
        } else {
            return Runtime.BLOCKED;
        }
    }

    private RuntimeState handleNext(Runtime runtime) {
        // Disallow progression of a story from a "next" message since a choice is need to be made
        // TODO: Fix this fragile check, it bypasses when previous state and current state becomes BLOCKED
        if (runtime.previousState() == Runtime.BRANCHING) {
            return Runtime.BLOCKED;
        }

        return Runtime.READY;
    }

    private RuntimeState handleChoiceSelect(Runtime runtime, Message message) {
        var id = ElementId.from(((ChoiceSelectMessage) message).id());
        runtime.updateCurrentNode(id);

        return Runtime.READY;
    }
}
