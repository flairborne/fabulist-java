package com.flairborne.fabulist.runtime.context.message;

public record FinishedMessage() implements Message {

    @Override
    public String type() {
        return "finished";
    }
}
