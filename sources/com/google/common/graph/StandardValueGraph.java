package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class StandardValueGraph<N, V> extends AbstractValueGraph<N, V> {

    /* renamed from: a, reason: collision with root package name */
    final MapIteratorCache f14546a;
    private final boolean allowsSelfLoops;

    /* renamed from: b, reason: collision with root package name */
    long f14547b;
    private final boolean isDirected;
    private final ElementOrder<N> nodeOrder;

    StandardValueGraph(AbstractGraphBuilder abstractGraphBuilder) {
        this(abstractGraphBuilder, abstractGraphBuilder.f14496c.b(((Integer) abstractGraphBuilder.f14498e.or((Optional) 10)).intValue()), 0L);
    }

    private final GraphConnections<N, V> checkedConnections(N n2) {
        GraphConnections<N, V> graphConnections = (GraphConnections) this.f14546a.e(n2);
        if (graphConnections != null) {
            return graphConnections;
        }
        Preconditions.checkNotNull(n2);
        throw new IllegalArgumentException("Node " + n2 + " is not an element of this graph.");
    }

    @CheckForNull
    private final V edgeValueOrDefaultInternal(N n2, N n3, @CheckForNull V v2) {
        GraphConnections graphConnections = (GraphConnections) this.f14546a.e(n2);
        V v3 = graphConnections == null ? null : (V) graphConnections.value(n3);
        return v3 == null ? v2 : v3;
    }

    private final boolean hasEdgeConnectingInternal(N n2, N n3) {
        GraphConnections graphConnections = (GraphConnections) this.f14546a.e(n2);
        return graphConnections != null && graphConnections.successors().contains(n3);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public Set<N> adjacentNodes(N n2) {
        return e(checkedConnections(n2).adjacentNodes(), n2);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    @Override // com.google.common.graph.AbstractBaseGraph
    protected long c() {
        return this.f14547b;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckForNull
    public V edgeValueOrDefault(N n2, N n3, @CheckForNull V v2) {
        return (V) edgeValueOrDefaultInternal(Preconditions.checkNotNull(n2), Preconditions.checkNotNull(n3), v2);
    }

    final boolean h(Object obj) {
        return this.f14546a.d(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(N n2, N n3) {
        return hasEdgeConnectingInternal(Preconditions.checkNotNull(n2), Preconditions.checkNotNull(n3));
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public Set<EndpointPair<N>> incidentEdges(N n2) {
        final GraphConnections<N, V> graphConnectionsCheckedConnections = checkedConnections(n2);
        return e(new IncidentEdgeSet<N>(this, this, n2) { // from class: com.google.common.graph.StandardValueGraph.1

            /* renamed from: d, reason: collision with root package name */
            final /* synthetic */ StandardValueGraph f14549d;

            {
                this.f14549d = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<EndpointPair<N>> iterator() {
                return graphConnectionsCheckedConnections.incidentEdgeIterator(this.f14532a);
            }
        }, n2);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public Set<N> nodes() {
        return this.f14546a.j();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
        return predecessors((StandardValueGraph<N, V>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
        return successors((StandardValueGraph<N, V>) obj);
    }

    @CheckForNull
    public V edgeValueOrDefault(EndpointPair<N> endpointPair, @CheckForNull V v2) {
        f(endpointPair);
        return edgeValueOrDefaultInternal(endpointPair.nodeU(), endpointPair.nodeV(), v2);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        return d(endpointPair) && hasEdgeConnectingInternal(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N n2) {
        return e(checkedConnections(n2).predecessors(), n2);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N n2) {
        return e(checkedConnections(n2).successors(), n2);
    }

    StandardValueGraph(AbstractGraphBuilder abstractGraphBuilder, Map map, long j2) {
        MapIteratorCache mapIteratorCache;
        this.isDirected = abstractGraphBuilder.f14494a;
        this.allowsSelfLoops = abstractGraphBuilder.f14495b;
        this.nodeOrder = abstractGraphBuilder.f14496c.a();
        if (map instanceof TreeMap) {
            mapIteratorCache = new MapRetrievalCache(map);
        } else {
            mapIteratorCache = new MapIteratorCache(map);
        }
        this.f14546a = mapIteratorCache;
        this.f14547b = Graphs.b(j2);
    }
}
