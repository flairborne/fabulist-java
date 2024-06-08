package com.flairborne.fabulist.runtime.context.message;

public record ChangeContextMessage(String key) implements Message {

    @Override
    public String type() {
        return "change-context";
    }
}
