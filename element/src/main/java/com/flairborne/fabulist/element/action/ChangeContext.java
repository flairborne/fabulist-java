package com.flairborne.fabulist.element.action;

import com.flairborne.fabulist.element.channel.message.ChangeContextMessage;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.context.Context;

import java.util.function.Predicate;

public class ChangeContext extends AbstractAction {

    private final String key;
    private final Object value;

    protected ChangeContext(String key, Object value, Predicate<Context> condition) {
        super(condition);
        this.key = key;
        this.value = value;
    }

    @Override
    public Message act(Context context) {
        context.properties().setProperty(key, value);
        return new ChangeContextMessage(key);
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
