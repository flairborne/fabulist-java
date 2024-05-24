package com.flairborne.fabulist.runtime.element.channel.message;

public record FinishedMessage() implements Message {

    @Override
    public String type() {
        return "finished";
    }
}
