package com.flairborne.fabulist.runtime.context.message;

public record ChoiceSelectMessage(String id) implements Message{

    @Override
    public String type() {
        return "choice-select";
    }
}
