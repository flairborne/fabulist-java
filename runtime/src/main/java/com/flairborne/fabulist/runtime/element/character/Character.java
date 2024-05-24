package com.flairborne.fabulist.runtime.element.character;

import com.flairborne.fabulist.runtime.element.AbstractElement;
import com.flairborne.fabulist.runtime.element.Element;
import com.flairborne.fabulist.runtime.element.ElementId;

/**
 * A conversant in a story.
 */
public class Character extends AbstractElement implements Element {

    private final String name;

    private Character(ElementId id, String name) {
        super(id);
        this.name = name;
    }

    public String name() {
        return name;
    }

    /**
     * @param text text of the quote
     * @return a new instance of a quote directly from this character
     */
    public Quote quote(String text) {
        return Quote.from(this, text);
    }

    @Override
    public String toString() {
        return String.format("Character[id=%s]\n", id);
    }

    public static Character from(String id, String name) {
        return from(ElementId.from(id), name);
    }

    public static Character from(ElementId id, String name) {
        return new Character(id, name);
    }
}
