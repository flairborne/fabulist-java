package com.flairborne.fabulist.element.action;

import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.character.Quote;
import com.flairborne.fabulist.element.context.Context;
import com.flairborne.fabulist.element.context.Displayable;

import java.util.function.Predicate;

/**
 * Represents an action towards the {@link Context context} of the story.
 */
public interface Action extends Displayable {

    Message act(Context context);

    /**
     * @return a new instance of {@link Dialogue}
     */
    static Action dialogue(Quote quote) {
        return new Dialogue(quote);
    }
}
