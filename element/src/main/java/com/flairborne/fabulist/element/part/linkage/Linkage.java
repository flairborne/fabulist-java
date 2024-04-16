package com.flairborne.fabulist.element.part.linkage;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.context.Interactive;
import com.flairborne.fabulist.element.part.Part;
import com.flairborne.fabulist.element.part.node.Node;

import java.util.function.Predicate;

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
     * @return a condition check that must pass so that this linkage can be presented
     * for traversal of other {@link Node nodes} by the {@link Runtime runtime}
     */
    Predicate<Context> condition();

    /**
     * @param previousId id of the previous node
     * @param nextId     id of the next node
     * @return a new instance of {@link Choice}
     */
    static Linkage choice(ElementId previousId, ElementId nextId, Predicate<Context> condition) {
        return new Choice(previousId, nextId, condition);
    }

    /**
     * @param previousId id of the previous node
     * @param nextId     id of the next node
     * @return a new instance of {@link Passthrough}
     */
    static Linkage passthrough(ElementId previousId, ElementId nextId, Predicate<Context> condition) {
        return new Passthrough(previousId, nextId, condition);
    }
}
