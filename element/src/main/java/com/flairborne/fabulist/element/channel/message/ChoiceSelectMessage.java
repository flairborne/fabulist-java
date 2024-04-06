package com.flairborne.fabulist.element.channel.message;

public record ChoiceSelectMessage(String id) implements Message{

    @Override
    public String type() {
        return "choice-select";
    }
}
