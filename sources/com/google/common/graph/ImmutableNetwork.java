package com.google.common.graph;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.util.Map;
import java.util.Set;

@Immutable(containerOf = {"N", ExifInterface.LONGITUDE_EAST})
@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class ImmutableNetwork<N, E> extends StandardNetwork<N, E> {

    public static class Builder<N, E> {
        private final MutableNetwork<N, E> mutableNetwork;

        Builder(NetworkBuilder networkBuilder) {
            this.mutableNetwork = networkBuilder.build();
        }

        @CanIgnoreReturnValue
        public Builder<N, E> addEdge(N n2, N n3, E e2) {
            this.mutableNetwork.addEdge(n2, n3, e2);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<N, E> addNode(N n2) {
            this.mutableNetwork.addNode(n2);
            return this;
        }

        public ImmutableNetwork<N, E> build() {
            return ImmutableNetwork.copyOf(this.mutableNetwork);
        }

        @CanIgnoreReturnValue
        public Builder<N, E> addEdge(EndpointPair<N> endpointPair, E e2) {
            this.mutableNetwork.addEdge(endpointPair, e2);
            return this;
        }
    }

    private ImmutableNetwork(Network<N, E> network) {
        super(NetworkBuilder.from(network), getNodeConnections(network), getEdgeToReferenceNode(network));
    }

    private static <N, E> Function<E, N> adjacentNodeFn(final Network<N, E> network, final N n2) {
        return new Function() { // from class: com.google.common.graph.v
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return ImmutableNetwork.lambda$adjacentNodeFn$2(network, n2, obj);
            }
        };
    }

    private static <N, E> NetworkConnections<N, E> connectionsOf(Network<N, E> network, N n2) {
        if (!network.isDirected()) {
            Map mapAsMap = Maps.asMap(network.incidentEdges(n2), adjacentNodeFn(network, n2));
            return network.allowsParallelEdges() ? UndirectedMultiNetworkConnections.c(mapAsMap) : UndirectedNetworkConnections.b(mapAsMap);
        }
        Map mapAsMap2 = Maps.asMap(network.inEdges(n2), sourceNodeFn(network));
        Map mapAsMap3 = Maps.asMap(network.outEdges(n2), targetNodeFn(network));
        int size = network.edgesConnecting(n2, n2).size();
        return network.allowsParallelEdges() ? DirectedMultiNetworkConnections.d(mapAsMap2, mapAsMap3, size) : DirectedNetworkConnections.c(mapAsMap2, mapAsMap3, size);
    }

    public static <N, E> ImmutableNetwork<N, E> copyOf(Network<N, E> network) {
        return network instanceof ImmutableNetwork ? (ImmutableNetwork) network : new ImmutableNetwork<>(network);
    }

    private static <N, E> Map<E, N> getEdgeToReferenceNode(Network<N, E> network) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (E e2 : network.edges()) {
            builder.put(e2, network.incidentNodes(e2).nodeU());
        }
        return builder.buildOrThrow();
    }

    private static <N, E> Map<N, NetworkConnections<N, E>> getNodeConnections(Network<N, E> network) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (N n2 : network.nodes()) {
            builder.put(n2, connectionsOf(network, n2));
        }
        return builder.buildOrThrow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Object lambda$adjacentNodeFn$2(Network network, Object obj, Object obj2) {
        return network.incidentNodes(obj2).adjacentNode(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$sourceNodeFn$0(Network network, Object obj) {
        return network.incidentNodes(obj).source();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$targetNodeFn$1(Network network, Object obj) {
        return network.incidentNodes(obj).target();
    }

    private static <N, E> Function<E, N> sourceNodeFn(final Network<N, E> network) {
        return new Function() { // from class: com.google.common.graph.u
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return ImmutableNetwork.lambda$sourceNodeFn$0(network, obj);
            }
        };
    }

    private static <N, E> Function<E, N> targetNodeFn(final Network<N, E> network) {
        return new Function() { // from class: com.google.common.graph.w
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return ImmutableNetwork.lambda$targetNodeFn$1(network, obj);
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set adjacentNodes(Object obj) {
        return super.adjacentNodes(obj);
    }

    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ boolean allowsParallelEdges() {
        return super.allowsParallelEdges();
    }

    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ boolean allowsSelfLoops() {
        return super.allowsSelfLoops();
    }

    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ ElementOrder edgeOrder() {
        return super.edgeOrder();
    }

    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set edges() {
        return super.edges();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set edgesConnecting(Object obj, Object obj2) {
        return super.edgesConnecting(obj, obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set inEdges(Object obj) {
        return super.inEdges(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set incidentEdges(Object obj) {
        return super.incidentEdges(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ EndpointPair incidentNodes(Object obj) {
        return super.incidentNodes(obj);
    }

    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ boolean isDirected() {
        return super.isDirected();
    }

    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ ElementOrder nodeOrder() {
        return super.nodeOrder();
    }

    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set nodes() {
        return super.nodes();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set outEdges(Object obj) {
        return super.outEdges(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Set predecessors(Object obj) {
        return super.predecessors((ImmutableNetwork<N, E>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.StandardNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Set successors(Object obj) {
        return super.successors((ImmutableNetwork<N, E>) obj);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public ImmutableGraph<N> asGraph() {
        return new ImmutableGraph<>(super.asGraph());
    }

    @Deprecated
    public static <N, E> ImmutableNetwork<N, E> copyOf(ImmutableNetwork<N, E> immutableNetwork) {
        return (ImmutableNetwork) Preconditions.checkNotNull(immutableNetwork);
    }
}
