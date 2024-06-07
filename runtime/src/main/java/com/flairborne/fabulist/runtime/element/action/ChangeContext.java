package com.flairborne.fabulist.runtime.element.action;

import com.flairborne.fabulist.runtime.element.channel.message.ChangeContextMessage;
import com.flairborne.fabulist.runtime.element.channel.message.Message;
import com.flairborne.fabulist.runtime.element.context.Context;

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

    public static class Builder extends AbstractAction.Builder<Builder> {
        private final Operation operation;
        private final String key;
        private final Object value;

        public Builder(Operation operation, String key, Object value) {
            this.operation = operation;
            this.key = key;
            this.value = value;
        }

        @Override
        public Action build() {
            return new ChangeContext(this);
        }

        @Override
        public Builder self() {
            return null;
        }
    }

    private ChangeContext(Builder builder) {
        super(builder);
        key = builder.key;
        value = builder.value;
        operation = builder.operation;
    }

    @Override
    public Message act(Context context) {
        operation.consumer.apply(context, key, value);

        return new ChangeContextMessage(key);
    }
}
