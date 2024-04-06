package com.flairborne.fabulist.element.context;

import com.flairborne.fabulist.element.channel.Channel;
import com.flairborne.fabulist.element.channel.message.Message;

/**
 * Context refers to where a {@link Runtime runtime} is situated in such as a command-line, web application, etc.
 */
public interface Context {

    PropertyMap properties();
}
