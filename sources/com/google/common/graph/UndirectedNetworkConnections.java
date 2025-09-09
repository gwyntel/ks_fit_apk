package com.google.common.graph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class UndirectedNetworkConnections<N, E> extends AbstractUndirectedNetworkConnections<N, E> {
    UndirectedNetworkConnections(Map map) {
        super(map);
    }

    static UndirectedNetworkConnections a() {
        return new UndirectedNetworkConnections(HashBiMap.create(2));
    }

    static UndirectedNetworkConnections b(Map map) {
        return new UndirectedNetworkConnections(ImmutableBiMap.copyOf(map));
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(((BiMap) this.f14504a).values());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> edgesConnecting(N n2) {
        return new EdgesConnecting(((BiMap) this.f14504a).inverse(), n2);
    }
}
