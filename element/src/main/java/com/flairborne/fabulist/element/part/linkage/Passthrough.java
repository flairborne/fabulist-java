package com.flairborne.fabulist.element.part.linkage;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.context.Interactive;

import java.util.function.Predicate;

/**
 * A {@link Interactive non-interactive} linkage that simply lets the node continue to the next node.
 */
public class Passthrough extends AbstractLinkage {

    protected Passthrough(ElementId previous, ElementId next, Predicate<Context> condition) {
        super(previous, next, condition);
    }

    @Override
    public boolean isInteractive() {
        return false;
    }
}
