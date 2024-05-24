package com.flairborne.fabulist.runtime.element.channel;

import com.flairborne.fabulist.runtime.element.channel.message.Message;

public interface MessageListener {

    void onReceive(Message message);
}
