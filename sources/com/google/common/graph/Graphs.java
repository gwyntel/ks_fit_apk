package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.ImmutableGraph;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javax.annotation.CheckForNull;

@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Graphs extends GraphsBridgeMethods {

    private static final class NodeAndRemainingSuccessors<N> {

        /* renamed from: a, reason: collision with root package name */
        final Object f14529a;

        /* renamed from: b, reason: collision with root package name */
        Queue f14530b;

        NodeAndRemainingSuccessors(Object obj) {
            this.f14529a = obj;
        }
    }

    private enum NodeVisitState {
        PENDING,
        COMPLETE
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TransposedGraph<N> extends ForwardingGraph<N> {
        private final Graph<N> graph;

        /* renamed from: com.google.common.graph.Graphs$TransposedGraph$1, reason: invalid class name */
        class AnonymousClass1 extends IncidentEdgeSet<N> {
            AnonymousClass1(BaseGraph baseGraph, Object obj) {
                super(baseGraph, obj);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ EndpointPair lambda$iterator$0(EndpointPair endpointPair) {
                return EndpointPair.a(TransposedGraph.this.g(), endpointPair.nodeV(), endpointPair.nodeU());
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<EndpointPair<N>> iterator() {
                return Iterators.transform(TransposedGraph.this.g().incidentEdges(this.f14532a).iterator(), new Function() { // from class: com.google.common.graph.t
                    @Override // com.google.common.base.Function
                    public final Object apply(Object obj) {
                        return this.f14592a.lambda$iterator$0((EndpointPair) obj);
                    }
                });
            }
        }

        TransposedGraph(Graph graph) {
            this.graph = graph;
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public boolean hasEdgeConnecting(N n2, N n3) {
            return g().hasEdgeConnecting(n3, n2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.graph.ForwardingGraph
        /* renamed from: i, reason: merged with bridge method [inline-methods] */
        public Graph g() {
            return this.graph;
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public int inDegree(N n2) {
            return g().outDegree(n2);
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public Set<EndpointPair<N>> incidentEdges(N n2) {
            return new AnonymousClass1(this, n2);
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public int outDegree(N n2) {
            return g().inDegree(n2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
        public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
            return predecessors((TransposedGraph<N>) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
        public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
            return successors((TransposedGraph<N>) obj);
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
            return g().hasEdgeConnecting(Graphs.e(endpointPair));
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
        public Set<N> predecessors(N n2) {
            return g().successors((Graph) n2);
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
        public Set<N> successors(N n2) {
            return g().predecessors((Graph) n2);
        }
    }

    private static class TransposedNetwork<N, E> extends ForwardingNetwork<N, E> {
        private final Network<N, E> network;

        TransposedNetwork(Network network) {
            this.network = network;
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        @CheckForNull
        public E edgeConnectingOrNull(N n2, N n3) {
            return (E) l().edgeConnectingOrNull(n3, n2);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public Set<E> edgesConnecting(N n2, N n3) {
            return l().edgesConnecting(n3, n2);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public boolean hasEdgeConnecting(N n2, N n3) {
            return l().hasEdgeConnecting(n3, n2);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public int inDegree(N n2) {
            return l().outDegree(n2);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.Network
        public Set<E> inEdges(N n2) {
            return l().outEdges(n2);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.Network
        public EndpointPair<N> incidentNodes(E e2) {
            EndpointPair<N> endpointPairIncidentNodes = l().incidentNodes(e2);
            return EndpointPair.b(this.network, endpointPairIncidentNodes.nodeV(), endpointPairIncidentNodes.nodeU());
        }

        @Override // com.google.common.graph.ForwardingNetwork
        Network l() {
            return this.network;
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public int outDegree(N n2) {
            return l().inDegree(n2);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.Network
        public Set<E> outEdges(N n2) {
            return l().inEdges(n2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
        public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
            return predecessors((TransposedNetwork<N, E>) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
        public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
            return successors((TransposedNetwork<N, E>) obj);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        @CheckForNull
        public E edgeConnectingOrNull(EndpointPair<N> endpointPair) {
            return (E) l().edgeConnectingOrNull(Graphs.e(endpointPair));
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public Set<E> edgesConnecting(EndpointPair<N> endpointPair) {
            return l().edgesConnecting(Graphs.e(endpointPair));
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
            return l().hasEdgeConnecting(Graphs.e(endpointPair));
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
        public Set<N> predecessors(N n2) {
            return l().successors((Network) n2);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
        public Set<N> successors(N n2) {
            return l().predecessors((Network) n2);
        }
    }

    private static class TransposedValueGraph<N, V> extends ForwardingValueGraph<N, V> {
        private final ValueGraph<N, V> graph;

        TransposedValueGraph(ValueGraph valueGraph) {
            this.graph = valueGraph;
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.ValueGraph
        @CheckForNull
        public V edgeValueOrDefault(N n2, N n3, @CheckForNull V v2) {
            return (V) h().edgeValueOrDefault(n3, n2, v2);
        }

        @Override // com.google.common.graph.ForwardingValueGraph
        ValueGraph h() {
            return this.graph;
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public boolean hasEdgeConnecting(N n2, N n3) {
            return h().hasEdgeConnecting(n3, n2);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public int inDegree(N n2) {
            return h().outDegree(n2);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public int outDegree(N n2) {
            return h().inDegree(n2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
        public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
            return predecessors((TransposedValueGraph<N, V>) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
        public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
            return successors((TransposedValueGraph<N, V>) obj);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.ValueGraph
        @CheckForNull
        public V edgeValueOrDefault(EndpointPair<N> endpointPair, @CheckForNull V v2) {
            return (V) h().edgeValueOrDefault(Graphs.e(endpointPair), v2);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
            return h().hasEdgeConnecting(Graphs.e(endpointPair));
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
        public Set<N> predecessors(N n2) {
            return h().successors((ValueGraph) n2);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
        public Set<N> successors(N n2) {
            return h().predecessors((ValueGraph) n2);
        }
    }

    private Graphs() {
    }

    static int a(int i2) {
        Preconditions.checkArgument(i2 >= 0, "Not true that %s is non-negative.", i2);
        return i2;
    }

    static long b(long j2) {
        Preconditions.checkArgument(j2 >= 0, "Not true that %s is non-negative.", j2);
        return j2;
    }

    static int c(int i2) {
        Preconditions.checkArgument(i2 > 0, "Not true that %s is positive.", i2);
        return i2;
    }

    private static boolean canTraverseWithoutReusingEdge(Graph<?> graph, Object obj, @CheckForNull Object obj2) {
        return graph.isDirected() || !Objects.equal(obj2, obj);
    }

    public static <N> MutableGraph<N> copyOf(Graph<N> graph) {
        MutableGraph<N> mutableGraph = (MutableGraph<N>) GraphBuilder.from(graph).expectedNodeCount(graph.nodes().size()).build();
        Iterator<N> it = graph.nodes().iterator();
        while (it.hasNext()) {
            mutableGraph.addNode(it.next());
        }
        for (EndpointPair<N> endpointPair : graph.edges()) {
            mutableGraph.putEdge(endpointPair.nodeU(), endpointPair.nodeV());
        }
        return mutableGraph;
    }

    static long d(long j2) {
        Preconditions.checkArgument(j2 > 0, "Not true that %s is positive.", j2);
        return j2;
    }

    static EndpointPair e(EndpointPair endpointPair) {
        return endpointPair.isOrdered() ? EndpointPair.ordered(endpointPair.target(), endpointPair.source()) : endpointPair;
    }

    public static <N> boolean hasCycle(Graph<N> graph) {
        int size = graph.edges().size();
        if (size == 0) {
            return false;
        }
        if (!graph.isDirected() && size >= graph.nodes().size()) {
            return true;
        }
        HashMap mapNewHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(graph.nodes().size());
        Iterator<N> it = graph.nodes().iterator();
        while (it.hasNext()) {
            if (subgraphHasCycle(graph, mapNewHashMapWithExpectedSize, it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <N> MutableGraph<N> inducedSubgraph(Graph<N> graph, Iterable<? extends N> iterable) {
        StandardMutableGraph standardMutableGraph = iterable instanceof Collection ? (MutableGraph<N>) GraphBuilder.from(graph).expectedNodeCount(((Collection) iterable).size()).build() : (MutableGraph<N>) GraphBuilder.from(graph).build();
        Iterator<? extends N> it = iterable.iterator();
        while (it.hasNext()) {
            standardMutableGraph.addNode(it.next());
        }
        for (N n2 : standardMutableGraph.nodes()) {
            for (N n3 : graph.successors((Graph<N>) n2)) {
                if (standardMutableGraph.nodes().contains(n3)) {
                    standardMutableGraph.putEdge(n2, n3);
                }
            }
        }
        return standardMutableGraph;
    }

    public static <N> ImmutableSet<N> reachableNodes(Graph<N> graph, N n2) {
        Preconditions.checkArgument(graph.nodes().contains(n2), "Node %s is not an element of this graph.", n2);
        return ImmutableSet.copyOf(Traverser.forGraph(graph).breadthFirst((Traverser) n2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <N> boolean subgraphHasCycle(Graph<N> graph, Map<Object, NodeVisitState> map, N n2) {
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.addLast(new NodeAndRemainingSuccessors(n2));
        while (!arrayDeque.isEmpty()) {
            NodeAndRemainingSuccessors nodeAndRemainingSuccessors = (NodeAndRemainingSuccessors) arrayDeque.removeLast();
            NodeAndRemainingSuccessors nodeAndRemainingSuccessors2 = (NodeAndRemainingSuccessors) arrayDeque.peekLast();
            arrayDeque.addLast(nodeAndRemainingSuccessors);
            Object obj = nodeAndRemainingSuccessors.f14529a;
            Object obj2 = nodeAndRemainingSuccessors2 == null ? null : nodeAndRemainingSuccessors2.f14529a;
            if (nodeAndRemainingSuccessors.f14530b == null) {
                NodeVisitState nodeVisitState = map.get(obj);
                if (nodeVisitState == NodeVisitState.COMPLETE) {
                    arrayDeque.removeLast();
                } else {
                    NodeVisitState nodeVisitState2 = NodeVisitState.PENDING;
                    if (nodeVisitState == nodeVisitState2) {
                        return true;
                    }
                    map.put(obj, nodeVisitState2);
                    nodeAndRemainingSuccessors.f14530b = new ArrayDeque(graph.successors((Graph<N>) obj));
                }
            }
            if (!nodeAndRemainingSuccessors.f14530b.isEmpty()) {
                Object objRemove = nodeAndRemainingSuccessors.f14530b.remove();
                if (canTraverseWithoutReusingEdge(graph, objRemove, obj2)) {
                    arrayDeque.addLast(new NodeAndRemainingSuccessors(objRemove));
                }
            }
            arrayDeque.removeLast();
            map.put(obj, NodeVisitState.COMPLETE);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <N> ImmutableGraph<N> transitiveClosure(Graph<N> graph) {
        ImmutableGraph.Builder<N1> builderImmutable = GraphBuilder.from(graph).allowsSelfLoops(true).immutable();
        if (graph.isDirected()) {
            for (N n2 : graph.nodes()) {
                UnmodifiableIterator it = reachableNodes((Graph) graph, (Object) n2).iterator();
                while (it.hasNext()) {
                    builderImmutable.putEdge(n2, it.next());
                }
            }
        } else {
            HashSet hashSet = new HashSet();
            for (N n3 : graph.nodes()) {
                if (!hashSet.contains(n3)) {
                    ImmutableSet immutableSetReachableNodes = reachableNodes((Graph) graph, (Object) n3);
                    hashSet.addAll(immutableSetReachableNodes);
                    int i2 = 1;
                    for (Object obj : immutableSetReachableNodes) {
                        int i3 = i2 + 1;
                        Iterator it2 = Iterables.limit(immutableSetReachableNodes, i2).iterator();
                        while (it2.hasNext()) {
                            builderImmutable.putEdge(obj, it2.next());
                        }
                        i2 = i3;
                    }
                }
            }
        }
        return builderImmutable.build();
    }

    public static <N> Graph<N> transpose(Graph<N> graph) {
        return !graph.isDirected() ? graph : graph instanceof TransposedGraph ? ((TransposedGraph) graph).graph : new TransposedGraph(graph);
    }

    public static <N, V> ValueGraph<N, V> transpose(ValueGraph<N, V> valueGraph) {
        if (!valueGraph.isDirected()) {
            return valueGraph;
        }
        if (valueGraph instanceof TransposedValueGraph) {
            return ((TransposedValueGraph) valueGraph).graph;
        }
        return new TransposedValueGraph(valueGraph);
    }

    public static <N, V> MutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        MutableValueGraph<N, V> mutableValueGraph = (MutableValueGraph<N, V>) ValueGraphBuilder.from(valueGraph).expectedNodeCount(valueGraph.nodes().size()).build();
        Iterator<N> it = valueGraph.nodes().iterator();
        while (it.hasNext()) {
            mutableValueGraph.addNode(it.next());
        }
        for (EndpointPair<N> endpointPair : valueGraph.edges()) {
            N nNodeU = endpointPair.nodeU();
            N nNodeV = endpointPair.nodeV();
            V vEdgeValueOrDefault = valueGraph.edgeValueOrDefault(endpointPair.nodeU(), endpointPair.nodeV(), null);
            java.util.Objects.requireNonNull(vEdgeValueOrDefault);
            mutableValueGraph.putEdgeValue(nNodeU, nNodeV, vEdgeValueOrDefault);
        }
        return mutableValueGraph;
    }

    public static boolean hasCycle(Network<?, ?> network) {
        if (network.isDirected() || !network.allowsParallelEdges() || network.edges().size() <= network.asGraph().edges().size()) {
            return hasCycle(network.asGraph());
        }
        return true;
    }

    public static <N, E> Network<N, E> transpose(Network<N, E> network) {
        if (!network.isDirected()) {
            return network;
        }
        if (network instanceof TransposedNetwork) {
            return ((TransposedNetwork) network).network;
        }
        return new TransposedNetwork(network);
    }

    public static <N, V> MutableValueGraph<N, V> inducedSubgraph(ValueGraph<N, V> valueGraph, Iterable<? extends N> iterable) {
        StandardMutableValueGraph standardMutableValueGraph;
        if (iterable instanceof Collection) {
            standardMutableValueGraph = (MutableValueGraph<N, V>) ValueGraphBuilder.from(valueGraph).expectedNodeCount(((Collection) iterable).size()).build();
        } else {
            standardMutableValueGraph = (MutableValueGraph<N, V>) ValueGraphBuilder.from(valueGraph).build();
        }
        Iterator<? extends N> it = iterable.iterator();
        while (it.hasNext()) {
            standardMutableValueGraph.addNode(it.next());
        }
        for (N n2 : standardMutableValueGraph.nodes()) {
            for (N n3 : valueGraph.successors((ValueGraph<N, V>) n2)) {
                if (standardMutableValueGraph.nodes().contains(n3)) {
                    V vEdgeValueOrDefault = valueGraph.edgeValueOrDefault(n2, n3, null);
                    java.util.Objects.requireNonNull(vEdgeValueOrDefault);
                    standardMutableValueGraph.putEdgeValue(n2, n3, vEdgeValueOrDefault);
                }
            }
        }
        return standardMutableValueGraph;
    }

    public static <N, E> MutableNetwork<N, E> copyOf(Network<N, E> network) {
        MutableNetwork<N, E> mutableNetwork = (MutableNetwork<N, E>) NetworkBuilder.from(network).expectedNodeCount(network.nodes().size()).expectedEdgeCount(network.edges().size()).build();
        Iterator<N> it = network.nodes().iterator();
        while (it.hasNext()) {
            mutableNetwork.addNode(it.next());
        }
        for (E e2 : network.edges()) {
            EndpointPair<N> endpointPairIncidentNodes = network.incidentNodes(e2);
            mutableNetwork.addEdge(endpointPairIncidentNodes.nodeU(), endpointPairIncidentNodes.nodeV(), e2);
        }
        return mutableNetwork;
    }

    public static <N, E> MutableNetwork<N, E> inducedSubgraph(Network<N, E> network, Iterable<? extends N> iterable) {
        StandardMutableNetwork standardMutableNetwork;
        if (iterable instanceof Collection) {
            standardMutableNetwork = (MutableNetwork<N, E>) NetworkBuilder.from(network).expectedNodeCount(((Collection) iterable).size()).build();
        } else {
            standardMutableNetwork = (MutableNetwork<N, E>) NetworkBuilder.from(network).build();
        }
        Iterator<? extends N> it = iterable.iterator();
        while (it.hasNext()) {
            standardMutableNetwork.addNode(it.next());
        }
        for (E e2 : standardMutableNetwork.nodes()) {
            for (E e3 : network.outEdges(e2)) {
                N nAdjacentNode = network.incidentNodes(e3).adjacentNode(e2);
                if (standardMutableNetwork.nodes().contains(nAdjacentNode)) {
                    standardMutableNetwork.addEdge(e2, nAdjacentNode, e3);
                }
            }
        }
        return standardMutableNetwork;
    }
}
