package com.flairborne.fabulist.element.action;

import com.flairborne.fabulist.element.channel.message.DialogueMessage;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.character.Quote;
import com.flairborne.fabulist.element.context.Context;

import java.util.function.Predicate;

public class Dialogue extends AbstractAction {

    private final Quote quote;

    protected Dialogue(Quote quote, Predicate<Context> condition) {
        super(condition);
        this.quote = quote;
    }

    @Override
    public Message act(Context context) {
        return new DialogueMessage(quote.character().name(), quote.text());
    }

    @Override
    public String displayText() {
        return quote.text();
    }
}
