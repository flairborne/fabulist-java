package com.flairborne.fabulist.runtime.element.part.linkage;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.element.context.Interactive;

/**
 * A {@link Interactive non-interactive} linkage that simply lets the node continue to the next node.
 */
public class Passthrough extends AbstractLinkage {

    public static class Builder extends AbstractLinkage.Builder<Builder> {

        public Builder(ElementId previous, ElementId next) {
            super(previous, next);
        }

        public Builder(String previous, String next) {
            super(previous, next);
        }

        @Override
        public Linkage build() {
            return new Passthrough(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Passthrough(Builder builder) {
        super(builder);
    }

    @Override
    public boolean isInteractive() {
        return false;
    }
}
