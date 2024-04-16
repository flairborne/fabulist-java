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

    protected AbstractLinkage(ElementId previous, ElementId next, Predicate<Context> condition) {
        this.previous = previous;
        this.next = next;
        this.condition = condition;
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
}
