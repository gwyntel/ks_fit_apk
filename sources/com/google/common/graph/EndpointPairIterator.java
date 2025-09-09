package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class EndpointPairIterator<N> extends AbstractIterator<EndpointPair<N>> {

    /* renamed from: a, reason: collision with root package name */
    Object f14527a;

    /* renamed from: b, reason: collision with root package name */
    Iterator f14528b;
    private final BaseGraph<N> graph;
    private final Iterator<N> nodeIterator;

    private static final class Directed<N> extends EndpointPairIterator<N> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.AbstractIterator
        /* renamed from: d, reason: merged with bridge method [inline-methods] */
        public EndpointPair computeNext() {
            while (!this.f14528b.hasNext()) {
                if (!b()) {
                    return (EndpointPair) a();
                }
            }
            Object obj = this.f14527a;
            Objects.requireNonNull(obj);
            return EndpointPair.ordered(obj, this.f14528b.next());
        }

        private Directed(BaseGraph<N> baseGraph) {
            super(baseGraph);
        }
    }

    private static final class Undirected<N> extends EndpointPairIterator<N> {

        @CheckForNull
        private Set<N> visitedNodes;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.AbstractIterator
        /* renamed from: d, reason: merged with bridge method [inline-methods] */
        public EndpointPair computeNext() {
            do {
                Objects.requireNonNull(this.visitedNodes);
                while (this.f14528b.hasNext()) {
                    Object next = this.f14528b.next();
                    if (!this.visitedNodes.contains(next)) {
                        Object obj = this.f14527a;
                        Objects.requireNonNull(obj);
                        return EndpointPair.unordered(obj, next);
                    }
                }
                this.visitedNodes.add(this.f14527a);
            } while (b());
            this.visitedNodes = null;
            return (EndpointPair) a();
        }

        private Undirected(BaseGraph<N> baseGraph) {
            super(baseGraph);
            this.visitedNodes = Sets.newHashSetWithExpectedSize(baseGraph.nodes().size() + 1);
        }
    }

    static EndpointPairIterator c(BaseGraph baseGraph) {
        return baseGraph.isDirected() ? new Directed(baseGraph) : new Undirected(baseGraph);
    }

    final boolean b() {
        Preconditions.checkState(!this.f14528b.hasNext());
        if (!this.nodeIterator.hasNext()) {
            return false;
        }
        N next = this.nodeIterator.next();
        this.f14527a = next;
        this.f14528b = this.graph.successors((BaseGraph<N>) next).iterator();
        return true;
    }

    private EndpointPairIterator(BaseGraph<N> baseGraph) {
        this.f14527a = null;
        this.f14528b = ImmutableSet.of().iterator();
        this.graph = baseGraph;
        this.nodeIterator = baseGraph.nodes().iterator();
    }
}
