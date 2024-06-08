package com.flairborne.fabulist.runtime.context;

import java.util.function.Predicate;

/**
 * Context refers to where a {@link Runtime runtime} is situated in such as a command-line, web application, etc.
 */
public interface Context {

    PropertyMap properties();

    static Predicate<Context> isPropertyTrue(String property) {
        return context -> context.properties().getBoolean(property);
    }
}
