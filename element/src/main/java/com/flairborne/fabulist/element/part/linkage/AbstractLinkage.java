package com.flairborne.fabulist.element.part.linkage;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.context.Context;

import java.util.function.Predicate;

/**
 * This class provides a skeletal implementation of the {@link Linkage} interface.
 */
public abstract class AbstractLinkage implements Linkage {

    protected final ElementId previous;
    protected final ElementId next;
    protected final Predicate<Context> condition;
    protected final String displayText;

    public static abstract class Builder<T extends Builder<T>> {

        protected final ElementId previous;
        protected final ElementId next;
        protected Predicate<Context> condition;
        protected String displayText;

        protected Builder(ElementId previous, ElementId next) {
            this.previous = previous;
            this.next = next;
        }

        protected Builder(String previous, String next) {
            this(ElementId.from(previous), ElementId.from(next));
        }

        public T displayText(String displayText) {
            this.displayText = displayText;
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
        displayText = builder.displayText;
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
    public String displayText() {
        return displayText;
    }
}
