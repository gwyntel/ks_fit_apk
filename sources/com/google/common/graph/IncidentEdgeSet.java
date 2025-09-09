package com.google.common.graph;

import java.util.AbstractSet;
import java.util.Set;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class IncidentEdgeSet<N> extends AbstractSet<EndpointPair<N>> {

    /* renamed from: a, reason: collision with root package name */
    final Object f14532a;

    /* renamed from: b, reason: collision with root package name */
    final BaseGraph f14533b;

    IncidentEdgeSet(BaseGraph baseGraph, Object obj) {
        this.f14533b = baseGraph;
        this.f14532a = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@CheckForNull Object obj) {
        if (!(obj instanceof EndpointPair)) {
            return false;
        }
        EndpointPair endpointPair = (EndpointPair) obj;
        if (this.f14533b.isDirected()) {
            if (!endpointPair.isOrdered()) {
                return false;
            }
            Object objSource = endpointPair.source();
            Object objTarget = endpointPair.target();
            return (this.f14532a.equals(objSource) && this.f14533b.successors((BaseGraph) this.f14532a).contains(objTarget)) || (this.f14532a.equals(objTarget) && this.f14533b.predecessors((BaseGraph) this.f14532a).contains(objSource));
        }
        if (endpointPair.isOrdered()) {
            return false;
        }
        Set setAdjacentNodes = this.f14533b.adjacentNodes(this.f14532a);
        Object objNodeU = endpointPair.nodeU();
        Object objNodeV = endpointPair.nodeV();
        return (this.f14532a.equals(objNodeV) && setAdjacentNodes.contains(objNodeU)) || (this.f14532a.equals(objNodeU) && setAdjacentNodes.contains(objNodeV));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(@CheckForNull Object obj) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.f14533b.isDirected() ? (this.f14533b.inDegree(this.f14532a) + this.f14533b.outDegree(this.f14532a)) - (this.f14533b.successors((BaseGraph) this.f14532a).contains(this.f14532a) ? 1 : 0) : this.f14533b.adjacentNodes(this.f14532a).size();
    }
}
