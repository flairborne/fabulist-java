package com.flairborne.fabulist.runtime.context;

/**
 * An element is blocking when it requires a stimulus (like a choice made by a player) in order to
 * proceed the {@link Runtime runtime}.
 */
public interface Blocking {

    /**
     * @return whether this element is capable of blocking the {@link Runtime runtime}
     */
    default boolean isBlocking() {
        return true;
    }
}
