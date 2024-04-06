package com.flairborne.fabulist.element.context;

import java.util.HashMap;
import java.util.Map;

public class PropertyMap {

    private final Map<String, Object> properties = new HashMap<>();

    public PropertyMap() {
    }

    private void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    private Object getProperty(String key) {
        return properties.get(key);
    }

    public void setInteger(String key, int value) {
        setProperty(key, value);
    }

    public void setBoolean(String key, boolean value) {
        setProperty(key, value);
    }

    public boolean getBoolean(String key) {
        return (Boolean) properties.get(key);
    }
}
