package com.flairborne.fabulist.element.channel;

import com.flairborne.fabulist.element.channel.message.Message;

/**
 * Channel for reading messages
 */
public interface ReadChannel {

    /**
     * @return {@link Message} from channel while removing it
     */
    Message poll();

    /**
     * @return {@link Message} from channel
     */
    Message peek();

    /**
     * @return number of messages in channel
     */
    int size();
}
