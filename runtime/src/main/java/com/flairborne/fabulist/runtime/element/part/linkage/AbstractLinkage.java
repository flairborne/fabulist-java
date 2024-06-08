package com.flairborne.fabulist.runtime.element.part.linkage;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * This class provides a skeletal implementation of the {@link Linkage} interface.
 */
public abstract class AbstractLinkage implements Linkage {

    protected final ElementId previous;
    protected final ElementId next;
    protected final Predicate<Context> condition;
    protected final Map<String, Object> properties;

    public static abstract class Builder<T extends Builder<T>> {

        protected final ElementId previous;
        protected final ElementId next;
        protected Predicate<Context> condition;
        protected final Map<String, Object> properties;

        protected Builder(ElementId previous, ElementId next) {
            this.previous = previous;
            this.next = next;
            properties = new HashMap<>();
        }

        protected Builder(String previous, String next) {
            this(ElementId.from(previous), ElementId.from(next));
        }

        public T displayText(String displayText) {
            properties.put("text", displayText);
            return self();
        }

        public T when(Predicate<Context> condition) {
            this.condition = condition;
            return self();
        }

        public abstract Linkage build();

        protected abstract T self();
    }

    protected AbstractLinkage(Builder<?> builder) {
        previous = builder.previous;
        next = builder.next;
        condition = builder.condition;
        properties = builder.properties;
    }

    @Override
    public ElementId previousId() {
        return previous;
    }

    @Override
    public ElementId nextId() {
        return next;
    }

    @Override
    public Predicate<Context> condition() {
        return condition;
    }

    @Override
    public Map<String, Object> properties() {
        return properties;
    }
}
