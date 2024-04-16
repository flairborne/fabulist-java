package com.flairborne.fabulist.element.action;

import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.character.Quote;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.context.Displayable;

import java.util.function.Predicate;

/**
 * Represents an action towards the {@link Context context} of the story.
 * Every action produces a message that details what has happened.
 */
public interface Action extends Displayable {

    /**
     * Perform action.
     *
     * @return {@link Message} message to send that indicates completion of this action
     */
    Message act(Context context);

    /**
     * @return a condition check that must pass so that this action can be executed by the {@link Runtime runtime}
     */
    Predicate<Context> condition();

    /**
     * @return a new instance of {@link Dialogue}
     */
    static Action dialogue(Quote quote, Predicate<Context> condition) {
        return new Dialogue(quote, condition);
    }

    static Action changeContext(ChangeContext.Operation operation, String key, Object value, Predicate<Context> condition) {
        return new ChangeContext(operation, key, value, condition);
    }
}
