package com.flairborne.fabulist.element.channel;

import com.flairborne.fabulist.element.channel.message.Message;

public interface Channel {

    String name();

    /**
     * Send a {@link Message} to this channel
     */
    void send(Message message);

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

    /**
     * @return a new instance of {@link MemoryQueueChannel}
     */
    static Channel memoryQueue(String name) {
        return new MemoryQueueChannel(name);
    }
}
