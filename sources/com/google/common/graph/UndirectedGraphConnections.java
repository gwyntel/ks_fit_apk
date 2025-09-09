package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.graph.ElementOrder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class UndirectedGraphConnections<N, V> implements GraphConnections<N, V> {
    private final Map<N, V> adjacentNodeValues;

    /* renamed from: com.google.common.graph.UndirectedGraphConnections$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f14566a;

        static {
            int[] iArr = new int[ElementOrder.Type.values().length];
            f14566a = iArr;
            try {
                iArr[ElementOrder.Type.UNORDERED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f14566a[ElementOrder.Type.STABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private UndirectedGraphConnections(Map<N, V> map) {
        this.adjacentNodeValues = (Map) Preconditions.checkNotNull(map);
    }

    static UndirectedGraphConnections b(ElementOrder elementOrder) {
        int i2 = AnonymousClass1.f14566a[elementOrder.type().ordinal()];
        if (i2 == 1) {
            return new UndirectedGraphConnections(new HashMap(2, 1.0f));
        }
        if (i2 == 2) {
            return new UndirectedGraphConnections(new LinkedHashMap(2, 1.0f));
        }
        throw new AssertionError(elementOrder.type());
    }

    static UndirectedGraphConnections c(Map map) {
        return new UndirectedGraphConnections(ImmutableMap.copyOf(map));
    }

    @Override // com.google.common.graph.GraphConnections
    public void addPredecessor(N n2, V v2) {
        addSuccessor(n2, v2);
    }

    @Override // com.google.common.graph.GraphConnections
    @CheckForNull
    public V addSuccessor(N n2, V v2) {
        return this.adjacentNodeValues.put(n2, v2);
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    @Override // com.google.common.graph.GraphConnections
    public Iterator<EndpointPair<N>> incidentEdgeIterator(final N n2) {
        return Iterators.transform(this.adjacentNodeValues.keySet().iterator(), new Function() { // from class: com.google.common.graph.z
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return EndpointPair.unordered(n2, obj);
            }
        });
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> predecessors() {
        return adjacentNodes();
    }

    @Override // com.google.common.graph.GraphConnections
    public void removePredecessor(N n2) {
        removeSuccessor(n2);
    }

    @Override // com.google.common.graph.GraphConnections
    @CheckForNull
    public V removeSuccessor(N n2) {
        return this.adjacentNodeValues.remove(n2);
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> successors() {
        return adjacentNodes();
    }

    @Override // com.google.common.graph.GraphConnections
    @CheckForNull
    public V value(N n2) {
        return this.adjacentNodeValues.get(n2);
    }
}
