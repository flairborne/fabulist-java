package com.flairborne.fabulist.runtime.context.message;

import java.util.List;

public record ChoicePresentMessage(List<ChoiceMessage> choiceMessages) implements Message {

    public record ChoiceMessage(String id, String displayText) {
    }

    @Override
    public String type() {
        return "choice-present";
    }
}
