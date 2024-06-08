package com.flairborne.fabulist.runtime.context;

/**
 * This class provides a skeletal implementation of the {@link Context} interface.
 */
public abstract class AbstractContext implements Context {

    private final PropertyMap properties;

    public AbstractContext() {
        properties = new PropertyMap();
    }

    @Override
    public PropertyMap properties() {
        return properties;
    }
}
