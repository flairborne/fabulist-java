package com.flairborne.fabulist.element.action;

import com.flairborne.fabulist.element.channel.message.DialogueMessage;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.character.Quote;
import com.flairborne.fabulist.element.context.Context;

public class Dialogue implements Action {

    private final Quote quote;

    protected Dialogue(Quote quote) {
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
