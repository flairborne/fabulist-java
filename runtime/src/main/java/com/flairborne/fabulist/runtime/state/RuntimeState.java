package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.runtime.Runtime;

public interface RuntimeState {

    /**
     * @return next {@link RuntimeState}
     */
    RuntimeState handle(Runtime runtime);

}
