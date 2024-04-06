package com.flairborne.fabulist.element.part.linkage;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.context.Interactive;
import com.flairborne.fabulist.element.part.Part;
import com.flairborne.fabulist.element.part.node.Node;

/**
 * An edge that connects between two {@link Node nodes}.
 * These linkages allow a {@link Part part} to progress.
 */
public interface Linkage extends Interactive {

    /**
     * @return previous {@link Node node} that this linkage is connected from
     */
    ElementId previousId();

    /**
     * @return next {@link Node node} that this linkage is connected to
     */
    ElementId nextId();

    /**
     * @param previousId id of the previous node
     * @param nextId     id of the next node
     * @return a new instance of {@link Choice}
     */
    static Linkage choice(ElementId previousId, ElementId nextId) {
        return new Choice(previousId, nextId);
    }

    /**
     * @param previousId id of the previous node
     * @param nextId     id of the next node
     * @return a new instance of {@link Passthrough}
     */
    static Linkage passthrough(ElementId previousId, ElementId nextId) {
        return new Passthrough(previousId, nextId);
    }
}
