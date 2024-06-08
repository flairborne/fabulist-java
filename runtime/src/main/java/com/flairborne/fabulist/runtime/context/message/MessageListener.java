package com.flairborne.fabulist.runtime.context.message;

import com.flairborne.fabulist.runtime.context.message.Message;

public interface MessageListener {

    void onReceive(Message message);
}
