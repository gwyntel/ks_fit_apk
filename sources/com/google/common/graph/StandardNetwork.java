package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class StandardNetwork<N, E> extends AbstractNetwork<N, E> {

    /* renamed from: a, reason: collision with root package name */
    final MapIteratorCache f14544a;
    private final boolean allowsParallelEdges;
    private final boolean allowsSelfLoops;

    /* renamed from: b, reason: collision with root package name */
    final MapIteratorCache f14545b;
    private final ElementOrder<E> edgeOrder;
    private final boolean isDirected;
    private final ElementOrder<N> nodeOrder;

    StandardNetwork(NetworkBuilder networkBuilder) {
        this(networkBuilder, networkBuilder.f14496c.b(((Integer) networkBuilder.f14498e.or((Optional) 10)).intValue()), networkBuilder.f14542g.b(((Integer) networkBuilder.f14543h.or((Optional) 20)).intValue()));
    }

    @Override // com.google.common.graph.Network
    public Set<N> adjacentNodes(N n2) {
        return i(l(n2).adjacentNodes(), n2);
    }

    @Override // com.google.common.graph.Network
    public boolean allowsParallelEdges() {
        return this.allowsParallelEdges;
    }

    @Override // com.google.common.graph.Network
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<E> edgeOrder() {
        return this.edgeOrder;
    }

    @Override // com.google.common.graph.Network
    public Set<E> edges() {
        return this.f14545b.j();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> edgesConnecting(N n2, N n3) {
        NetworkConnections networkConnectionsL = l(n2);
        if (!this.allowsSelfLoops && n2 == n3) {
            return ImmutableSet.of();
        }
        Preconditions.checkArgument(o(n3), "Node %s is not an element of this graph.", n3);
        return j(networkConnectionsL.edgesConnecting(n3), n2, n3);
    }

    @Override // com.google.common.graph.Network
    public Set<E> inEdges(N n2) {
        return i(l(n2).inEdges(), n2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> incidentEdges(N n2) {
        return i(l(n2).incidentEdges(), n2);
    }

    @Override // com.google.common.graph.Network
    public EndpointPair<N> incidentNodes(E e2) {
        Object objM = m(e2);
        NetworkConnections networkConnections = (NetworkConnections) this.f14544a.e(objM);
        Objects.requireNonNull(networkConnections);
        return EndpointPair.b(this, objM, networkConnections.adjacentNode(e2));
    }

    @Override // com.google.common.graph.Network
    public boolean isDirected() {
        return this.isDirected;
    }

    final NetworkConnections l(Object obj) {
        NetworkConnections networkConnections = (NetworkConnections) this.f14544a.e(obj);
        if (networkConnections != null) {
            return networkConnections;
        }
        Preconditions.checkNotNull(obj);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", obj));
    }

    final Object m(Object obj) {
        Object objE = this.f14545b.e(obj);
        if (objE != null) {
            return objE;
        }
        Preconditions.checkNotNull(obj);
        throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", obj));
    }

    final boolean n(Object obj) {
        return this.f14545b.d(obj);
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override // com.google.common.graph.Network
    public Set<N> nodes() {
        return this.f14544a.j();
    }

    final boolean o(Object obj) {
        return this.f14544a.d(obj);
    }

    @Override // com.google.common.graph.Network
    public Set<E> outEdges(N n2) {
        return i(l(n2).outEdges(), n2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
        return predecessors((StandardNetwork<N, E>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
        return successors((StandardNetwork<N, E>) obj);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N n2) {
        return i(l(n2).predecessors(), n2);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N n2) {
        return i(l(n2).successors(), n2);
    }

    StandardNetwork(NetworkBuilder networkBuilder, Map map, Map map2) {
        MapIteratorCache mapIteratorCache;
        this.isDirected = networkBuilder.f14494a;
        this.allowsParallelEdges = networkBuilder.f14541f;
        this.allowsSelfLoops = networkBuilder.f14495b;
        this.nodeOrder = networkBuilder.f14496c.a();
        this.edgeOrder = networkBuilder.f14542g.a();
        if (map instanceof TreeMap) {
            mapIteratorCache = new MapRetrievalCache(map);
        } else {
            mapIteratorCache = new MapIteratorCache(map);
        }
        this.f14544a = mapIteratorCache;
        this.f14545b = new MapIteratorCache(map2);
    }
}
