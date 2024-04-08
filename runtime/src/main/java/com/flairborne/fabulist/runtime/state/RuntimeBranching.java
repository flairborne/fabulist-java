package com.flairborne.fabulist.runtime.state;

import com.flairborne.fabulist.element.channel.message.ChoicePresentMessage;
import com.flairborne.fabulist.element.part.linkage.Linkage;
import com.flairborne.fabulist.element.part.node.Node;
import com.flairborne.fabulist.runtime.Runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RuntimeBranching implements RuntimeState {

    @Override
    public RuntimeState handle(Runtime runtime) {
        Node current = runtime.currentNode();

        /*
         * TODO: Add different passthrough strategies?
         *  Let's say a Node has only one linkage. During a step, should the Node automatically traverse that linkage
         *  since there's no other linkage anyway?
         */
        boolean canPassthrough = !current.isInteractive() && current.linkages().size() == 1;

        if (canPassthrough) {
            return handlePassthrough(runtime);
        }

        return handleInteractiveLinkages(runtime);
    }

    private RuntimeState handlePassthrough(Runtime runtime) {
        Linkage firstLinkage = runtime.currentNode().linkages().get(0);

        runtime.updateCurrentNode(firstLinkage.nextId());

        return Runtime.PAUSED;
    }

    private RuntimeState handleInteractiveLinkages(Runtime runtime) {
        List<ChoicePresentMessage.ChoiceMessage> choices = new ArrayList<>();

        for (Linkage linkage : runtime.currentNode().linkages()) {
            if (!linkage.isInteractive()) {
                continue;
            }

            Optional<Node> next = runtime.getNode(linkage.nextId());

            next.ifPresent(node -> {
                choices.add(new ChoicePresentMessage.ChoiceMessage(node.id().toString(), node.displayText()));
            });
        }

        runtime.server().broadcast(new ChoicePresentMessage(choices));

        return Runtime.BLOCKED;
    }
}
