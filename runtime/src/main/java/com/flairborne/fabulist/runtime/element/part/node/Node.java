package com.flairborne.fabulist.runtime.element.part.node;

import com.flairborne.fabulist.runtime.element.Element;
import com.flairborne.fabulist.runtime.element.action.Action;
import com.flairborne.fabulist.runtime.element.context.Interactive;
import com.flairborne.fabulist.runtime.element.part.linkage.Linkage;

import java.util.List;
import java.util.Queue;

/**
 * A node holds a collection of {@link Action actions} and {@link Linkage linkages} that are necessary to progress the story.
 * Actions are triggered when a node is entered and exited.
 * Linkages are presented to allow the story to traverse to other connected nodes.
 */
public interface Node extends Element, Interactive {

    /**
     * @return {@link Action actions} performed by this node during enter or exit
     */
    Queue<Action> actions();

    /**
     * @return {@link Linkage linkages} connected to this node
     */
    List<Linkage> linkages();

    /**
     * @return whether node has linkages connected to it
     */
    boolean hasLinkages();
}
