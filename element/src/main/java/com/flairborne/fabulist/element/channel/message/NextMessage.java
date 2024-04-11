package com.flairborne.fabulist.element.channel.message;

public record NextMessage() implements Message {

    @Override
    public String type() {
        return "next";
    }
}
