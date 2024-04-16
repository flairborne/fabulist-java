package com.flairborne.fabulist.element.action;

import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.context.Context;

import java.util.function.Predicate;

/**
 * This class provides a skeletal implementation of the {@link Action} interface.
 */
public abstract class AbstractAction implements Action {

    private final Predicate<Context> condition;

    protected AbstractAction(Predicate<Context> condition) {
        this.condition = condition;
    }

    @Override
    public abstract Message act(Context context);

    @Override
    public Predicate<Context> condition() {
        return condition;
    }
}
