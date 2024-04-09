package com.flairborne.fabulist.element.context;

/**
 * Context refers to where a {@link Runtime runtime} is situated in such as a command-line, web application, etc.
 */
public interface Context {

    PropertyMap properties();
}
