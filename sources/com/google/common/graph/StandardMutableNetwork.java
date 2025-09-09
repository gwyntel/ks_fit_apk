package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Objects;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class StandardMutableNetwork<N, E> extends StandardNetwork<N, E> implements MutableNetwork<N, E> {
    StandardMutableNetwork(NetworkBuilder networkBuilder) {
        super(networkBuilder);
    }

    @CanIgnoreReturnValue
    private NetworkConnections<N, E> addNodeInternal(N n2) {
        NetworkConnections<N, E> networkConnectionsNewConnections = newConnections();
        Preconditions.checkState(this.f14544a.h(n2, networkConnectionsNewConnections) == null);
        return networkConnectionsNewConnections;
    }

    private NetworkConnections<N, E> newConnections() {
        return isDirected() ? allowsParallelEdges() ? DirectedMultiNetworkConnections.c() : DirectedNetworkConnections.b() : allowsParallelEdges() ? UndirectedMultiNetworkConnections.b() : UndirectedNetworkConnections.a();
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addEdge(N n2, N n3, E e2) {
        Preconditions.checkNotNull(n2, "nodeU");
        Preconditions.checkNotNull(n3, "nodeV");
        Preconditions.checkNotNull(e2, "edge");
        if (n(e2)) {
            EndpointPair<N> endpointPairIncidentNodes = incidentNodes(e2);
            EndpointPair endpointPairB = EndpointPair.b(this, n2, n3);
            Preconditions.checkArgument(endpointPairIncidentNodes.equals(endpointPairB), "Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.", e2, endpointPairIncidentNodes, endpointPairB);
            return false;
        }
        NetworkConnections<N, E> networkConnectionsAddNodeInternal = (NetworkConnections) this.f14544a.e(n2);
        if (!allowsParallelEdges()) {
            Preconditions.checkArgument(networkConnectionsAddNodeInternal == null || !networkConnectionsAddNodeInternal.successors().contains(n3), "Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.", n2, n3);
        }
        boolean zEquals = n2.equals(n3);
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!zEquals, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n2);
        }
        if (networkConnectionsAddNodeInternal == null) {
            networkConnectionsAddNodeInternal = addNodeInternal(n2);
        }
        networkConnectionsAddNodeInternal.addOutEdge(e2, n3);
        NetworkConnections<N, E> networkConnectionsAddNodeInternal2 = (NetworkConnections) this.f14544a.e(n3);
        if (networkConnectionsAddNodeInternal2 == null) {
            networkConnectionsAddNodeInternal2 = addNodeInternal(n3);
        }
        networkConnectionsAddNodeInternal2.addInEdge(e2, n2, zEquals);
        this.f14545b.h(e2, n2);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addNode(N n2) {
        Preconditions.checkNotNull(n2, "node");
        if (o(n2)) {
            return false;
        }
        addNodeInternal(n2);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean removeEdge(E e2) {
        Preconditions.checkNotNull(e2, "edge");
        Object objE = this.f14545b.e(e2);
        boolean z2 = false;
        if (objE == null) {
            return false;
        }
        NetworkConnections networkConnections = (NetworkConnections) this.f14544a.e(objE);
        Objects.requireNonNull(networkConnections);
        NetworkConnections networkConnections2 = networkConnections;
        Object objAdjacentNode = networkConnections2.adjacentNode(e2);
        NetworkConnections networkConnections3 = (NetworkConnections) this.f14544a.e(objAdjacentNode);
        Objects.requireNonNull(networkConnections3);
        NetworkConnections networkConnections4 = networkConnections3;
        networkConnections2.removeOutEdge(e2);
        if (allowsSelfLoops() && objE.equals(objAdjacentNode)) {
            z2 = true;
        }
        networkConnections4.removeInEdge(e2, z2);
        this.f14545b.i(e2);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean removeNode(N n2) {
        Preconditions.checkNotNull(n2, "node");
        NetworkConnections networkConnections = (NetworkConnections) this.f14544a.e(n2);
        if (networkConnections == null) {
            return false;
        }
        UnmodifiableIterator<E> it = ImmutableList.copyOf((Collection) networkConnections.incidentEdges()).iterator();
        while (it.hasNext()) {
            removeEdge(it.next());
        }
        this.f14544a.i(n2);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addEdge(EndpointPair<N> endpointPair, E e2) {
        k(endpointPair);
        return addEdge(endpointPair.nodeU(), endpointPair.nodeV(), e2);
    }
}
