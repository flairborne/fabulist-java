package com.flairborne.fabulist.element;

/**
 * This class provides a skeletal implementation of the {@link Element} interface.
 * Whereas, an {@link ElementId} is assumed to be provided via the element's constructor.
 */
public abstract class AbstractElement implements Element {

    protected final ElementId id;

    protected AbstractElement(ElementId id) {
        this.id = id;
    }

    @Override
    public ElementId id() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        return ((AbstractElement) obj).id.equals(id);
    }

    @Override
    public String toString() {
        return String.format("Element[id=%s]", id);
    }
}
