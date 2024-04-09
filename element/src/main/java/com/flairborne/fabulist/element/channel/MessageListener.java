package com.flairborne.fabulist.element.channel;

import com.flairborne.fabulist.element.channel.message.Message;

public interface MessageListener {

    void onReceive(Message message);
}
