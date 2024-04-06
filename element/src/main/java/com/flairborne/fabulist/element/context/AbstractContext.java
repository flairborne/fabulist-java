package com.flairborne.fabulist.element.context;

import com.flairborne.fabulist.element.channel.Channel;
import com.flairborne.fabulist.element.channel.message.Message;

import java.util.HashMap;
import java.util.Map;

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
