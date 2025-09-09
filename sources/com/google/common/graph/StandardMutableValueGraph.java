package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Objects;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class StandardMutableValueGraph<N, V> extends StandardValueGraph<N, V> implements MutableValueGraph<N, V> {
    private final ElementOrder<N> incidentEdgeOrder;

    StandardMutableValueGraph(AbstractGraphBuilder abstractGraphBuilder) {
        super(abstractGraphBuilder);
        this.incidentEdgeOrder = abstractGraphBuilder.f14497d.a();
    }

    @CanIgnoreReturnValue
    private GraphConnections<N, V> addNodeInternal(N n2) {
        GraphConnections<N, V> graphConnectionsNewConnections = newConnections();
        Preconditions.checkState(this.f14546a.h(n2, graphConnectionsNewConnections) == null);
        return graphConnectionsNewConnections;
    }

    private GraphConnections<N, V> newConnections() {
        return isDirected() ? DirectedGraphConnections.j(this.incidentEdgeOrder) : UndirectedGraphConnections.b(this.incidentEdgeOrder);
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public boolean addNode(N n2) {
        Preconditions.checkNotNull(n2, "node");
        if (h(n2)) {
            return false;
        }
        addNodeInternal(n2);
        return true;
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public ElementOrder<N> incidentEdgeOrder() {
        return this.incidentEdgeOrder;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    @CheckForNull
    public V putEdgeValue(N n2, N n3, V v2) {
        Preconditions.checkNotNull(n2, "nodeU");
        Preconditions.checkNotNull(n3, "nodeV");
        Preconditions.checkNotNull(v2, "value");
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!n2.equals(n3), "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n2);
        }
        GraphConnections<N, V> graphConnectionsAddNodeInternal = (GraphConnections) this.f14546a.e(n2);
        if (graphConnectionsAddNodeInternal == null) {
            graphConnectionsAddNodeInternal = addNodeInternal(n2);
        }
        V vAddSuccessor = graphConnectionsAddNodeInternal.addSuccessor(n3, v2);
        GraphConnections<N, V> graphConnectionsAddNodeInternal2 = (GraphConnections) this.f14546a.e(n3);
        if (graphConnectionsAddNodeInternal2 == null) {
            graphConnectionsAddNodeInternal2 = addNodeInternal(n3);
        }
        graphConnectionsAddNodeInternal2.addPredecessor(n2, v2);
        if (vAddSuccessor == null) {
            long j2 = this.f14547b + 1;
            this.f14547b = j2;
            Graphs.d(j2);
        }
        return vAddSuccessor;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    @CheckForNull
    public V removeEdge(N n2, N n3) {
        Preconditions.checkNotNull(n2, "nodeU");
        Preconditions.checkNotNull(n3, "nodeV");
        GraphConnections graphConnections = (GraphConnections) this.f14546a.e(n2);
        GraphConnections graphConnections2 = (GraphConnections) this.f14546a.e(n3);
        if (graphConnections == null || graphConnections2 == null) {
            return null;
        }
        V v2 = (V) graphConnections.removeSuccessor(n3);
        if (v2 != null) {
            graphConnections2.removePredecessor(n2);
            long j2 = this.f14547b - 1;
            this.f14547b = j2;
            Graphs.b(j2);
        }
        return v2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public boolean removeNode(N n2) {
        Preconditions.checkNotNull(n2, "node");
        GraphConnections graphConnections = (GraphConnections) this.f14546a.e(n2);
        if (graphConnections == 0) {
            return false;
        }
        if (allowsSelfLoops() && graphConnections.removeSuccessor(n2) != null) {
            graphConnections.removePredecessor(n2);
            this.f14547b--;
        }
        UnmodifiableIterator it = ImmutableList.copyOf((Collection) graphConnections.successors()).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            GraphConnections graphConnections2 = (GraphConnections) this.f14546a.g(next);
            Objects.requireNonNull(graphConnections2);
            graphConnections2.removePredecessor(n2);
            Objects.requireNonNull(graphConnections.removeSuccessor(next));
            this.f14547b--;
        }
        if (isDirected()) {
            UnmodifiableIterator it2 = ImmutableList.copyOf((Collection) graphConnections.predecessors()).iterator();
            while (it2.hasNext()) {
                Object next2 = it2.next();
                GraphConnections graphConnections3 = (GraphConnections) this.f14546a.g(next2);
                Objects.requireNonNull(graphConnections3);
                Preconditions.checkState(graphConnections3.removeSuccessor(n2) != null);
                graphConnections.removePredecessor(next2);
                this.f14547b--;
            }
        }
        this.f14546a.i(n2);
        Graphs.b(this.f14547b);
        return true;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    @CheckForNull
    public V removeEdge(EndpointPair<N> endpointPair) {
        f(endpointPair);
        return removeEdge(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    @CheckForNull
    public V putEdgeValue(EndpointPair<N> endpointPair, V v2) {
        f(endpointPair);
        return putEdgeValue(endpointPair.nodeU(), endpointPair.nodeV(), v2);
    }
}
