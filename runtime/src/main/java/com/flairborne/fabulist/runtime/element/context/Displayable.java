package com.flairborne.fabulist.runtime.element.context;

public interface Displayable {

    /**
     * @return text to display
     */
    String displayText();

    /**
     * @return whether element is visible to the user
     */
    default boolean isVisible() {
        return true;
    }
}
