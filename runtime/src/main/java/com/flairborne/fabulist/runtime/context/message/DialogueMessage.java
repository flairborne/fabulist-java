package com.flairborne.fabulist.runtime.context.message;

public record DialogueMessage(String character, String message) implements Message {

    @Override
    public String type() {
        return "dialogue";
    }
}
