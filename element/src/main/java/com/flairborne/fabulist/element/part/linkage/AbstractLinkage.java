package com.flairborne.fabulist.element.part.linkage;

import com.flairborne.fabulist.element.ElementId;

/**
 * This class provides a skeletal implementation of the {@link Linkage} interface.
 */
public abstract class AbstractLinkage implements Linkage {

    protected final ElementId previous;
    protected final ElementId next;

    protected AbstractLinkage(ElementId previous, ElementId next) {
        this.previous = previous;
        this.next = next;
    }

    @Override
    public ElementId previousId() {
        return previous;
    }

    @Override
    public ElementId nextId() {
        return next;
    }
}
