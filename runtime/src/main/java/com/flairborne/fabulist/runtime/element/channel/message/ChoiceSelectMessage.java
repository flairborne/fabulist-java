package com.flairborne.fabulist.runtime.element.channel.message;

public record ChoiceSelectMessage(String id) implements Message{

    @Override
    public String type() {
        return "choice-select";
    }
}
