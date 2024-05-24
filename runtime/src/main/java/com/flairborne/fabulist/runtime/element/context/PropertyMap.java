package com.flairborne.fabulist.runtime.element.context;

import java.util.HashMap;
import java.util.Map;

public class PropertyMap {

    private final Map<String, Object> properties = new HashMap<>();

    public PropertyMap() {
    }

    public int getInt(String key) {
        return (int) properties.get(key);
    }

    public void setInt(String key, int value) {
        properties.put(key, value);
    }

    public void addInt(String key, int value) {
        properties.put(key, getInt(key) + value);
    }

    public void subtractInt(String key, int value) {
        properties.put(key, getInt(key) - value);
    }

    public void setBoolean(String key, boolean value) {
        properties.put(key, value);
    }

    public boolean getBoolean(String key) {
        return (boolean) properties.get(key);
    }
}
