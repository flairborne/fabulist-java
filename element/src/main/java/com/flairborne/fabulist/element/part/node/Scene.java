package com.flairborne.fabulist.element.part.node;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.action.Action;
import com.flairborne.fabulist.element.character.Quote;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.context.Displayable;
import com.flairborne.fabulist.element.part.linkage.Linkage;

import java.util.Optional;
import java.util.function.Predicate;

public class Scene extends AbstractNode {

    public static class Builder extends AbstractNode.Builder<Builder> {

        public Builder(String id) {
            super(id);
        }

        public Builder(ElementId id) {
            super(id);
        }

        public Builder addChangeContext(String key, Object value) {
            return addAction(Action.changeContext(key, value, null));
        }

        public Builder addChangeContextWhen(String key, Object value, Predicate<Context> condition) {
            return addAction(Action.changeContext(key, value, condition));
        }

        public Builder addDialogue(Quote quote) {
            return addAction(Action.dialogue(quote, null));
        }

        public Builder addDialogueWhen(Quote quote, Predicate<Context> condition) {
            return addAction(Action.dialogue(quote, condition));
        }

        public Builder addChoice(String nextId) {
            return addChoice(ElementId.from(nextId), null);
        }

        public Builder addChoice(ElementId nextId, Predicate<Context> condition) {
            return addLinkage(Linkage.choice(id, nextId, condition));
        }

        public Builder addChoiceWhen(String nextId, Predicate<Context> condition) {
            return addChoice(ElementId.from(nextId), condition);
        }

        public Builder addChoiceWhen(ElementId nextId, Predicate<Context> condition) {
            return addChoice(nextId, condition);
        }

        public Builder addPassthrough(String nextId) {
            return addPassthrough(ElementId.from(nextId));
        }

        public Builder addPassthrough(ElementId nextId) {
            return addLinkage(Linkage.passthrough(id, nextId, null));
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
        Optional<Action> visibleAction = actions.stream().filter(Action::isVisible).findFirst();

        return visibleAction.map(Displayable::displayText).orElse(null);
    }
}
