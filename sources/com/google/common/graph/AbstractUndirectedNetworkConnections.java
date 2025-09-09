package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class AbstractUndirectedNetworkConnections<N, E> implements NetworkConnections<N, E> {

    /* renamed from: a, reason: collision with root package name */
    final Map f14504a;

    AbstractUndirectedNetworkConnections(Map map) {
        this.f14504a = (Map) Preconditions.checkNotNull(map);
    }

    @Override // com.google.common.graph.NetworkConnections
    public void addInEdge(E e2, N n2, boolean z2) {
        if (z2) {
            return;
        }
        addOutEdge(e2, n2);
    }

    @Override // com.google.common.graph.NetworkConnections
    public void addOutEdge(E e2, N n2) {
        Preconditions.checkState(this.f14504a.put(e2, n2) == null);
    }

    @Override // com.google.common.graph.NetworkConnections
    public N adjacentNode(E e2) {
        N n2 = (N) this.f14504a.get(e2);
        Objects.requireNonNull(n2);
        return n2;
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> inEdges() {
        return incidentEdges();
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> incidentEdges() {
        return Collections.unmodifiableSet(this.f14504a.keySet());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> outEdges() {
        return incidentEdges();
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> predecessors() {
        return adjacentNodes();
    }

    @Override // com.google.common.graph.NetworkConnections
    @CheckForNull
    public N removeInEdge(E e2, boolean z2) {
        if (z2) {
            return null;
        }
        return removeOutEdge(e2);
    }

    @Override // com.google.common.graph.NetworkConnections
    public N removeOutEdge(E e2) {
        N n2 = (N) this.f14504a.remove(e2);
        Objects.requireNonNull(n2);
        return n2;
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> successors() {
        return adjacentNodes();
    }
}
