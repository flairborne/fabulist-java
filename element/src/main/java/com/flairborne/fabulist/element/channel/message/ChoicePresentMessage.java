package com.flairborne.fabulist.element.channel.message;

import java.util.List;

public record ChoicePresentMessage(List<ChoiceMessage> choiceMessages) implements Message {

    public record ChoiceMessage(String id, String displayText) {
    }

    @Override
    public String type() {
        return "choice-present";
    }
}
