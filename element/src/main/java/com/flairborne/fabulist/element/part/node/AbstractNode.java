package com.flairborne.fabulist.element.part.node;

import com.flairborne.fabulist.element.AbstractElement;
import com.flairborne.fabulist.element.ElementId;
import com.flairborne.fabulist.element.action.Action;
import com.flairborne.fabulist.element.context.Interactive;
import com.flairborne.fabulist.element.part.linkage.Linkage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class provides a skeletal implementation of the {@link Node} interface.
 */
public abstract class AbstractNode extends AbstractElement implements Node {

    // TODO: Queue of actions might disable backtracking or undo functionality
    protected final Queue<Action> actions;
    protected final List<Linkage> linkages;

    // Builder is a generic type with a recursive type parameter
    public abstract static class Builder<T extends Builder<T>> {

        protected final ElementId id;
        protected final Queue<Action> actions;
        protected final List<Linkage> linkages;

        public Builder(String id) {
            this(ElementId.from(id));
        }

        public Builder(ElementId id) {
            this.id = id;
            actions = new LinkedList<>();
            linkages = new LinkedList<>();
        }

        public T addAction(Action action) {
            actions.add(action);
            return self();
        }

        public T addLinkage(Linkage linkage) {
            linkages.add(linkage);
            return self();
        }

        public abstract Node build();

        // A simulated self-type idiom
        protected abstract T self();
    }

    protected AbstractNode(Builder<?> builder) {
        super(builder.id);
        actions = builder.actions;
        linkages = Collections.unmodifiableList(builder.linkages);
    }

    @Override
    public Queue<Action> actions() {
        return actions;
    }

    @Override
    public List<Linkage> linkages() {
        return linkages;
    }

    @Override
    public boolean hasLinkages() {
        return !linkages.isEmpty();
    }

    /**
     * @return whether node is interactive which means at least one of its linkages is interactive
     */
    @Override
    public boolean isInteractive() {
        return linkages.stream().anyMatch(Interactive::isInteractive);
    }
}
