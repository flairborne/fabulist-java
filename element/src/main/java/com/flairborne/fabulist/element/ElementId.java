package com.flairborne.fabulist.element;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * An immutable ID of a story {@link Element element}.
 */
public final class ElementId {

    private static final Map<String, ElementId> ids = new HashMap<>();

    private final String data;

    private ElementId(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        return ((ElementId) obj).data.equals(data);
    }

    @Override
    public String toString() {
        return data;
    }

    /**
     * @return an element ID with a random {@link UUID} as its data
     */
    public static ElementId random() {
        return new ElementId(UUID.randomUUID().toString());
    }

    public static ElementId from(String data) {
        if (!ids.containsKey(data)) {
            ids.put(data, new ElementId(data));
        }

        return ids.get(data);
    }
}
