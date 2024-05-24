package com.flairborne.fabulist.runtime.element.part.node;

import com.flairborne.fabulist.runtime.element.ElementId;

public class Scene extends AbstractNode {

    public static class Builder extends AbstractNode.Builder<Builder> {

        public Builder(ElementId id) {
            super(id);
        }

        public Builder(String id) {
            this(ElementId.from(id));
        }

        @Override
        public Scene build() {
            return new Scene(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Scene(Builder builder) {
        super(builder);
    }
}
