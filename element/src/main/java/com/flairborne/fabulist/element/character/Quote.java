package com.flairborne.fabulist.element.character;

/**
 * Represents what a {@link Character character} is saying.
 */
public class Quote {

    private final Character character;
    private final String text;

    private Quote(Character character, String text) {
        this.character = character;
        this.text = text;
    }
    
    public Character character() {
        return character;
    }

    public String text() {
        return text;
    }

    public static Quote from(Character character, String text) {
        return new Quote(character, text);
    }
}
