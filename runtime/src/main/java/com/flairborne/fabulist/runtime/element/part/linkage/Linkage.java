package com.flairborne.fabulist.runtime.element.part.linkage;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.context.Context;
import com.flairborne.fabulist.runtime.context.Blocking;
import com.flairborne.fabulist.runtime.element.part.Part;
import com.flairborne.fabulist.runtime.element.part.node.Node;

import java.util.Map;
import java.util.function.Predicate;

/**
 * An edge that connects between two {@link Node nodes}.
 * These linkages allow a {@link Part part} to progress.
 */
public interface Linkage extends Blocking {

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

    Map<String, Object> properties();
}
