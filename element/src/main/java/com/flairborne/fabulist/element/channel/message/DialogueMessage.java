package com.flairborne.fabulist.element.channel.message;

public record DialogueMessage(String character, String message) implements Message {

    @Override
    public String type() {
        return "dialogue";
    }
}
