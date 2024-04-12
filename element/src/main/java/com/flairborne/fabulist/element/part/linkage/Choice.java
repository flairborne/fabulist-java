package com.flairborne.fabulist.element.part.linkage;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.context.Interactive;

/**
 * An {@link Interactive interactive} linkage that is prompted to the user.
 */
public class Choice extends AbstractLinkage {

    protected Choice(ElementId previous, ElementId next) {
        super(previous, next);
    }
}
