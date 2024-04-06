package com.flairborne.fabulist.element.channel;

import com.flairborne.fabulist.element.channel.message.Message;

/**
 * Channel for sending messages
 */
public interface WriteChannel {

    /**
     * Send a {@link Message} to this channel
     */
    void send(Message message);
}
