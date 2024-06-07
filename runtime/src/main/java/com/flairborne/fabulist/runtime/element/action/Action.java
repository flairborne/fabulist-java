package com.flairborne.fabulist.runtime.element.action;

import com.flairborne.fabulist.runtime.element.channel.message.Message;
import com.flairborne.fabulist.runtime.element.context.Context;

import java.util.function.Predicate;

/**
 * Represents an action towards the {@link Context context} of the story.
 * Every action produces a message that details what has happened.
 */
public interface Action {

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
}
