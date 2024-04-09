package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.element.context.BasicContext;
import com.flairborne.fabulist.element.part.Part;
import com.flairborne.fabulist.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RuntimeTest {

    Runtime runtime;

    @BeforeEach
    void setup() {
        var part = new Part.Builder("part").build();
        var context = new BasicContext();
        var server = new EmbeddedServer(context, part);
        runtime = new Runtime(server, context, part);
    }

    @Test
    void it_creates_runtime() {
        assertEquals(Runtime.READY, runtime.currentState());
        assertNull(runtime.previousState());
    }

    @Test
    void it_sets_current_runtime_state() {
        runtime.setCurrentState(Runtime.ACTING);

        assertEquals(Runtime.ACTING, runtime.currentState());
        assertEquals(Runtime.READY, runtime.previousState());
    }
}
