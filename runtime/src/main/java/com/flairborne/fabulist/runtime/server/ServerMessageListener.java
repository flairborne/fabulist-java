package com.flairborne.fabulist.runtime.server;

import com.flairborne.fabulist.element.channel.MessageListener;
import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.runtime.Runtime;

public class ServerMessageListener implements MessageListener {

    private final Runtime runtime;

    public ServerMessageListener(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public void onReceive(Message message) {
        handleNext();
    }

    private void handleNext() {
        while (!runtime.isFinished()) {
            runtime.step();

            // Step once to allow it to process its interrupted state
            if (runtime.isPaused() || runtime.isBlocked() || runtime.isFinished()) {
                runtime.step();
                break;
            }
        }
    }
}
