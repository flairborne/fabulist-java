package com.flairborne.fabulist.runtime.element.part;

import com.flairborne.fabulist.runtime.element.AbstractElement;
import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.element.part.linkage.Choice;
import com.flairborne.fabulist.runtime.element.part.linkage.Linkage;
import com.flairborne.fabulist.runtime.element.part.node.AbstractNode;
import com.flairborne.fabulist.runtime.element.part.node.Node;
import com.flairborne.fabulist.runtime.element.part.node.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A part is a collection of {@link Node nodes} that are interconnected by {@link Linkage linkages}.
 * This structure allows the representation of all possible {@link Scene outcomes} of a story.
 * Each outcome is driven by different {@link Choice choices} (and other linkage types) interactions made by a player.
 */
public class Part extends AbstractElement {

    // TODO: Use a map for O(1) access. We query the element ID as the key anyway
    private final List<Node> nodes;

    private final Node root;

    public static class Builder {

        private final ElementId id;
        private final List<Node> nodes;

        public Builder(String id) {
            this(ElementId.from(id));
        }

        public Builder(ElementId id) {
            this.id = id;
            nodes = new ArrayList<>();
        }

        /**
         * A convenience method for adding a node via an {@link AbstractNode.Builder} class.
         */
        public Builder addNode(AbstractNode.Builder<?> builder) {
            return addNode(builder.build());
        }

        public Builder addNode(Node node) {
            nodes.add(node);
            return this;
        }

        public Part build() {
            return new Part(this);
        }
    }

    private Part(Builder builder) {
        super(builder.id);

        if (builder.nodes.isEmpty()) {
            throw new IllegalStateException("Part does not have any nodes");
        }

        nodes = builder.nodes;
        root = nodes.get(0);
    }

    /**
     * @return the first {@link Node node} in the story
     */
    public Node root() {
        return root;
    }

    public Optional<Node> getNode(String id) {
        return getNode(ElementId.from(id));
    }

    public Optional<Node> getNode(ElementId id) {
        return nodes.stream().filter(node -> node.id().equals(id)).findFirst();
    }

    /**
     * @return number of nodes in the story
     */
    public int size() {
        return nodes.size();
    }
}
