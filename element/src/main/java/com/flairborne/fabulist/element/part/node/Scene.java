package com.flairborne.fabulist.element.part.node;

import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.action.Action;
import com.flairborne.fabulist.element.context.Displayable;

import java.util.Optional;

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

    @Override
    public String displayText() {
        Optional<Action> visibleAction = actions.stream().filter(Action::isVisible).findFirst();

        return visibleAction.map(Displayable::displayText).orElse(null);
    }
}
