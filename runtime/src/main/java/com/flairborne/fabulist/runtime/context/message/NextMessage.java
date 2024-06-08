package com.flairborne.fabulist.runtime.context.message;

public record NextMessage() implements Message {

    @Override
    public String type() {
        return "next";
    }
}
