package com.flairborne.fabulist.runtime.element.action;

import com.flairborne.fabulist.runtime.element.channel.message.Message;
import com.flairborne.fabulist.runtime.context.Context;

import java.util.function.Predicate;

/**
 * This class provides a skeletal implementation of the {@link Action} interface.
 */
public abstract class AbstractAction implements Action {

    private final Predicate<Context> condition;

    public static abstract class Builder<T extends Builder<T>> {
        private Predicate<Context> condition;

        public T when(Predicate<Context> condition) {
            this.condition = condition;
            return self();
        }

        public abstract Action build();

        public abstract T self();
    }

    protected AbstractAction(Builder<?> builder) {
        condition = builder.condition;
    }

    @Override
    public abstract Message act(Context context);

    @Override
    public Predicate<Context> condition() {
        return condition;
    }
}
