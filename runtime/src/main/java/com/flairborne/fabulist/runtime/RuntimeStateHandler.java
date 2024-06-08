package com.flairborne.fabulist.runtime;

public sealed interface RuntimeStateHandler permits RuntimeActingHandler, RuntimeReadyHandler, RuntimePausedHandler, RuntimeBranchingHandler, RuntimeBlockedHandler, RuntimeFinishedHandler {

    /**
     * @return next {@link RuntimeStateHandler}
     */
    Runtime.State handle(Runtime runtime);

}
