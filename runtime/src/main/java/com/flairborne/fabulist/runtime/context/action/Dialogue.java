package com.flairborne.fabulist.runtime.context.action;

import com.flairborne.fabulist.runtime.context.message.DialogueMessage;
import com.flairborne.fabulist.runtime.context.message.Message;
import com.flairborne.fabulist.runtime.element.character.Quote;
import com.flairborne.fabulist.runtime.context.Context;

public class Dialogue extends AbstractAction {

    private final Quote quote;

    public static class Builder extends AbstractAction.Builder<Builder> {

        private final Quote quote;

        public Builder(Quote quote) {
            this.quote = quote;
        }

        @Override
        public Action build() {
            return new Dialogue(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }

    private Dialogue(Builder builder) {
        super(builder);
        quote = builder.quote;
    }

    @Override
    public Message act(Context context) {
        return new DialogueMessage(quote.character().name(), quote.text());
    }
}
