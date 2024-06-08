package com.flairborne.fabulist.runtime.element.part.linkage;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.context.Blocking;

/**
 * An {@link Blocking interactive} linkage that is prompted to the user.
 */
public class Choice extends AbstractLinkage {

    public static class Builder extends AbstractLinkage.Builder<Builder> {

        public Builder(ElementId previous, ElementId next) {
            super(previous, next);
        }

        public Builder(String previous, String next) {
            super(previous, next);
        }

        @Override
        public Linkage build() {
            return new Choice(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Choice(Builder builder) {
        super(builder);
    }
}
