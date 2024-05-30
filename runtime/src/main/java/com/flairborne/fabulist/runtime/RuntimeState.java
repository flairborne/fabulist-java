package com.flairborne.fabulist.runtime;

public sealed interface RuntimeState permits RuntimeActing, RuntimeReady, RuntimePaused, RuntimeBranching, RuntimeBlocked, RuntimeFinished {

    /**
     * @return next {@link RuntimeState}
     */
    RuntimeState handle(Runtime runtime);

}
