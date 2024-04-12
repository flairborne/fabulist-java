package com.flairborne.fabulist.element.part.node;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.action.Action;
import com.flairborne.fabulist.element.character.Quote;
import com.flairborne.fabulist.element.part.linkage.Linkage;

public class Scene extends AbstractNode {

    public static class Builder extends AbstractNode.Builder<Builder> {

        public Builder(String id) {
            super(id);
        }

        public Builder(ElementId id) {
            super(id);
        }

        public Builder addDialogue(Quote quote) {
            return addAction(Action.dialogue(quote));
        }

        public Builder addChoice(String nextId) {
            return addChoice(ElementId.from(nextId));
        }

        public Builder addChoice(ElementId nextId) {
            return addLinkage(Linkage.choice(id, nextId));
        }

        public Builder addPassthrough(String nextId) {
            return addPassthrough(ElementId.from(nextId));
        }

        public Builder addPassthrough(ElementId nextId) {
            return addLinkage(Linkage.passthrough(id, nextId));
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

    @Override
    public String displayText() {
        return actions.peek().displayText();
    }
}
