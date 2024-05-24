package com.flairborne.fabulist.runtime.element.channel.message;

public record ChangeContextMessage(String key) implements Message {

    @Override
    public String type() {
        return "change-context";
    }
}
