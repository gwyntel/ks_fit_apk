package com.google.common.graph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class DirectedNetworkConnections<N, E> extends AbstractDirectedNetworkConnections<N, E> {
    DirectedNetworkConnections(Map map, Map map2, int i2) {
        super(map, map2, i2);
    }

    static DirectedNetworkConnections b() {
        return new DirectedNetworkConnections(HashBiMap.create(2), HashBiMap.create(2), 0);
    }

    static DirectedNetworkConnections c(Map map, Map map2, int i2) {
        return new DirectedNetworkConnections(ImmutableBiMap.copyOf(map), ImmutableBiMap.copyOf(map2), i2);
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> edgesConnecting(N n2) {
        return new EdgesConnecting(((BiMap) this.f14492b).inverse(), n2);
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> predecessors() {
        return Collections.unmodifiableSet(((BiMap) this.f14491a).values());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> successors() {
        return Collections.unmodifiableSet(((BiMap) this.f14492b).values());
    }
}
