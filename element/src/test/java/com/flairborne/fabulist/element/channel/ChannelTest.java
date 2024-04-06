package com.flairborne.fabulist.element.channel;

import com.flairborne.fabulist.element.channel.message.Message;
import com.flairborne.fabulist.element.channel.message.SimpleMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChannelTest {

    @Test
    void it_creates_channel() {
        var channel = Channel.memoryQueue("default");

        assertEquals("default", channel.name());
        assertEquals(0, channel.size());
    }

    @Test
    void it_sends_message_in_channel() {
        var channel = Channel.memoryQueue("default");
        channel.send(new SimpleMessage("test"));

        assertEquals(1, channel.size());
    }

    @Test
    void it_receives_message_from_channel() {
        var channel = Channel.memoryQueue("default");
        channel.send(new SimpleMessage("test"));

        Message received = channel.poll();

        assertEquals(0, channel.size());
        assertEquals("test", received.type());
    }
}
