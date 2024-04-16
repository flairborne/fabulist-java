package com.flairborne.fabulist.element.action;

import com.flairborne.fabulist.element.channel.message.ChangeContextMessage;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.context.Context;

import java.util.function.Predicate;

public class ChangeContext extends AbstractAction {

    private final Operation operation;
    private final String key;
    private final Object value;

    @FunctionalInterface
    private interface ContextConsumer {
        void apply(Context context, String key, Object value);
    }

    public enum Operation {
        // Setter operations
        SET_INT((c, k, v) -> c.properties().setInt(k, (int) v)),
        SET_BOOLEAN((c, k, v) -> c.properties().setBoolean(k, (boolean) v)),

        // Numerical operations
        ADD((c, k, v) -> c.properties().addInt(k, (int) v)),
        SUBTRACT((c, k, v) -> c.properties().subtractInt(k, (int) v));

        private final ContextConsumer consumer;

        Operation(ContextConsumer consumer) {
            this.consumer = consumer;
        }
    }

    protected ChangeContext(Operation operation, String key, Object value, Predicate<Context> condition) {
        super(condition);
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    @Override
    public Message act(Context context) {
        operation.consumer.apply(context, key, value);

        return new ChangeContextMessage(key);
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
