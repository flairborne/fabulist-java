package com.flairborne.fabulist.element.channel.message;

public record SimpleMessage (String type) implements Message {

    @Override
    public String type() {
        return type;
    }
}
