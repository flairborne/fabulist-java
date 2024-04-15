package com.flairborne.fabulist.element.channel.message;

public record FinishedMessage() implements Message {

    @Override
    public String type() {
        return "finished";
    }
}
