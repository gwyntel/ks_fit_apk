package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class AbstractDirectedNetworkConnections<N, E> implements NetworkConnections<N, E> {

    /* renamed from: a, reason: collision with root package name */
    final Map f14491a;

    /* renamed from: b, reason: collision with root package name */
    final Map f14492b;
    private int selfLoopCount;

    AbstractDirectedNetworkConnections(Map map, Map map2, int i2) {
        this.f14491a = (Map) Preconditions.checkNotNull(map);
        this.f14492b = (Map) Preconditions.checkNotNull(map2);
        this.selfLoopCount = Graphs.a(i2);
        Preconditions.checkState(i2 <= map.size() && i2 <= map2.size());
    }

    @Override // com.google.common.graph.NetworkConnections
    public void addInEdge(E e2, N n2, boolean z2) {
        Preconditions.checkNotNull(e2);
        Preconditions.checkNotNull(n2);
        if (z2) {
            int i2 = this.selfLoopCount + 1;
            this.selfLoopCount = i2;
            Graphs.c(i2);
        }
        Preconditions.checkState(this.f14491a.put(e2, n2) == null);
    }

    @Override // com.google.common.graph.NetworkConnections
    public void addOutEdge(E e2, N n2) {
        Preconditions.checkNotNull(e2);
        Preconditions.checkNotNull(n2);
        Preconditions.checkState(this.f14492b.put(e2, n2) == null);
    }

    @Override // com.google.common.graph.NetworkConnections
    public N adjacentNode(E e2) {
        N n2 = (N) this.f14492b.get(e2);
        Objects.requireNonNull(n2);
        return n2;
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> adjacentNodes() {
        return Sets.union(predecessors(), successors());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> inEdges() {
        return Collections.unmodifiableSet(this.f14491a.keySet());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> incidentEdges() {
        return new AbstractSet<E>() { // from class: com.google.common.graph.AbstractDirectedNetworkConnections.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                return AbstractDirectedNetworkConnections.this.f14491a.containsKey(obj) || AbstractDirectedNetworkConnections.this.f14492b.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return IntMath.saturatedAdd(AbstractDirectedNetworkConnections.this.f14491a.size(), AbstractDirectedNetworkConnections.this.f14492b.size() - AbstractDirectedNetworkConnections.this.selfLoopCount);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<E> iterator() {
                return Iterators.unmodifiableIterator((AbstractDirectedNetworkConnections.this.selfLoopCount == 0 ? Iterables.concat(AbstractDirectedNetworkConnections.this.f14491a.keySet(), AbstractDirectedNetworkConnections.this.f14492b.keySet()) : Sets.union(AbstractDirectedNetworkConnections.this.f14491a.keySet(), AbstractDirectedNetworkConnections.this.f14492b.keySet())).iterator());
            }
        };
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> outEdges() {
        return Collections.unmodifiableSet(this.f14492b.keySet());
    }

    @Override // com.google.common.graph.NetworkConnections
    public N removeInEdge(E e2, boolean z2) {
        if (z2) {
            int i2 = this.selfLoopCount - 1;
            this.selfLoopCount = i2;
            Graphs.a(i2);
        }
        N n2 = (N) this.f14491a.remove(e2);
        Objects.requireNonNull(n2);
        return n2;
    }

    @Override // com.google.common.graph.NetworkConnections
    public N removeOutEdge(E e2) {
        N n2 = (N) this.f14492b.remove(e2);
        Objects.requireNonNull(n2);
        return n2;
    }
}
