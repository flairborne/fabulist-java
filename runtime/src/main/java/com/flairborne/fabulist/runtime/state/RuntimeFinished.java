package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.element.channel.message.SimpleMessage;
import com.flairborne.fabulist.runtime.Runtime;

public class RuntimeFinished implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        var message = new SimpleMessage("finished");
        runtime.server().broadcast(message);

        return Runtime.FINISHED;
    }
}
