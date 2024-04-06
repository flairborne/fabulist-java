package com.flairborne.fabulist.element.part.linkage;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.context.Interactive;

/**
 * A {@link Interactive non-interactive} linkage that simply lets the node continue to the next node.
 */
public class Passthrough extends AbstractLinkage implements Linkage {

    protected Passthrough(ElementId previous, ElementId next) {
        super(previous, next);
    }

    @Override
    public boolean isInteractive() {
        return false;
    }
}
