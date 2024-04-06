package com.flairborne.fabulist.element.context;

public interface Displayable {

    default String displayText() {
        return null;
    }

    /**
     * @return whether linkage is visible to the user
     */
    default boolean isVisible() {
        return false;
    }
}
