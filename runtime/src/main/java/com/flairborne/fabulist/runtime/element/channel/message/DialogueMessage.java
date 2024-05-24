package com.flairborne.fabulist.runtime.element.channel.message;

public record DialogueMessage(String character, String message) implements Message {

    @Override
    public String type() {
        return "dialogue";
    }
}
