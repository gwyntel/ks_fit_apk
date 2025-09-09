package com.google.common.graph;

import com.google.common.graph.GraphConstants;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class StandardMutableGraph<N> extends ForwardingGraph<N> implements MutableGraph<N> {
    private final MutableValueGraph<N, GraphConstants.Presence> backingValueGraph;

    StandardMutableGraph(AbstractGraphBuilder abstractGraphBuilder) {
        this.backingValueGraph = new StandardMutableValueGraph(abstractGraphBuilder);
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean addNode(N n2) {
        return this.backingValueGraph.addNode(n2);
    }

    @Override // com.google.common.graph.ForwardingGraph
    BaseGraph g() {
        return this.backingValueGraph;
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean putEdge(N n2, N n3) {
        return this.backingValueGraph.putEdgeValue(n2, n3, GraphConstants.Presence.EDGE_EXISTS) == null;
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean removeEdge(N n2, N n3) {
        return this.backingValueGraph.removeEdge(n2, n3) != null;
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean removeNode(N n2) {
        return this.backingValueGraph.removeNode(n2);
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean putEdge(EndpointPair<N> endpointPair) {
        f(endpointPair);
        return putEdge(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean removeEdge(EndpointPair<N> endpointPair) {
        f(endpointPair);
        return removeEdge(endpointPair.nodeU(), endpointPair.nodeV());
    }
}
