package com.flairborne.fabulist.runtime;

import com.flairborne.fabulist.runtime.context.message.ChoicePresentMessage;
import com.flairborne.fabulist.runtime.context.Context;
import com.flairborne.fabulist.runtime.element.part.linkage.Linkage;
import com.flairborne.fabulist.runtime.element.part.node.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

final class RuntimeBranchingHandler implements RuntimeStateHandler {

    @Override
    public Runtime.State handle(Runtime runtime) {
        Node currentNode = runtime.currentNode();

        /*
         * TODO: Add different passthrough strategies?
         *  Let's say a Node has only one linkage. During a step, should the Node automatically traverse that linkage
         *  since there's no other linkage anyway?
         */
        boolean canPassthrough = !currentNode.isBlocking() && currentNode.linkages().size() == 1;

        if (canPassthrough) {
            return handlePassthrough(runtime);
        }

        return handleInteractiveLinkages(runtime);
    }

    private Runtime.State handlePassthrough(Runtime runtime) {
        Linkage firstLinkage = runtime.currentNode().linkages().get(0);

        runtime.updateCurrentNode(firstLinkage.nextId());

        return Runtime.State.READY;
    }

    private Runtime.State handleInteractiveLinkages(Runtime runtime) {
        List<ChoicePresentMessage.ChoiceMessage> choices = new ArrayList<>();

        for (Linkage linkage : runtime.currentNode().linkages()) {
            if (!linkage.isBlocking()) {
                continue;
            }

            Predicate<Context> condition = linkage.condition();

            if (condition != null && !condition.test(runtime.context())) {
                continue;
            }

            var id = linkage.nextId().toString();
            var text = (String) linkage.properties().get("text");
            choices.add(new ChoicePresentMessage.ChoiceMessage(id, text));
        }

        runtime.broadcast(new ChoicePresentMessage(choices));

        return Runtime.State.BLOCKED;
    }
}
