package com.flairborne.fabulist.runtime.client;

import com.flairborne.fabulist.element.channel.MessageListener;
import com.flairborne.fabulist.element.channel.message.ChoicePresentMessage;
import com.flairborne.fabulist.element.channel.message.DialogueMessage;
import com.flairborne.fabulist.element.channel.message.Message;

class ClientMessageListener implements MessageListener {

    @Override
    public void onReceive(Message message) {
        if (message.type().equals("dialogue")) {
            var dialogue = (DialogueMessage) message;
            System.out.printf("[%s]: %s\n", dialogue.character(), dialogue.message());
        }

        if (message.type().equals("choice-present")) {
            var choice = (ChoicePresentMessage) message;

            System.out.println("\nPlease select a choice:");
            for (var choiceMessage : choice.choiceMessages()) {
                System.out.printf("- [%s]: %s\n", choiceMessage.id(), choiceMessage.displayText());
            }
        }

        if (message.type().equals("finished")) {
            System.exit(0);
        }
    }
}
