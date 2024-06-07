package com.flairborne.fabulist.runtime.element.context;

/**
 * An element is blocking when it requires a stimulus (like a choice made by a player) in order to proceed.
 */
public interface Blocking {

    default boolean isBlocking() {
        return true;
    }
}
