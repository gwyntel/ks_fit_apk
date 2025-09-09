package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class AbstractNetwork<N, E> implements Network<N, E> {

    /* renamed from: com.google.common.graph.AbstractNetwork$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractGraph<N> {

        /* renamed from: com.google.common.graph.AbstractNetwork$1$1, reason: invalid class name and collision with other inner class name */
        class C01251 extends AbstractSet<EndpointPair<N>> {
            C01251() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ EndpointPair lambda$iterator$0(Object obj) {
                return AbstractNetwork.this.incidentNodes(obj);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                return AnonymousClass1.this.d(endpointPair) && AnonymousClass1.this.nodes().contains(endpointPair.nodeU()) && AnonymousClass1.this.successors((AnonymousClass1) endpointPair.nodeU()).contains(endpointPair.nodeV());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<EndpointPair<N>> iterator() {
                return Iterators.transform(AbstractNetwork.this.edges().iterator(), new Function() { // from class: com.google.common.graph.m
                    @Override // com.google.common.base.Function
                    public final Object apply(Object obj) {
                        return this.f14587a.lambda$iterator$0(obj);
                    }
                });
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractNetwork.this.edges().size();
            }
        }

        AnonymousClass1() {
        }

        @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
        public Set<N> adjacentNodes(N n2) {
            return AbstractNetwork.this.adjacentNodes(n2);
        }

        @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
        public boolean allowsSelfLoops() {
            return AbstractNetwork.this.allowsSelfLoops();
        }

        @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public Set<EndpointPair<N>> edges() {
            return AbstractNetwork.this.allowsParallelEdges() ? super.edges() : new C01251();
        }

        @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public ElementOrder<N> incidentEdgeOrder() {
            return ElementOrder.unordered();
        }

        @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
        public boolean isDirected() {
            return AbstractNetwork.this.isDirected();
        }

        @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
        public ElementOrder<N> nodeOrder() {
            return AbstractNetwork.this.nodeOrder();
        }

        @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
        public Set<N> nodes() {
            return AbstractNetwork.this.nodes();
        }

        @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
        public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
            return predecessors((AnonymousClass1) obj);
        }

        @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
        public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
            return successors((AnonymousClass1) obj);
        }

        @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
        public Set<N> predecessors(N n2) {
            return AbstractNetwork.this.predecessors((AbstractNetwork) n2);
        }

        @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
        public Set<N> successors(N n2) {
            return AbstractNetwork.this.successors((AbstractNetwork) n2);
        }
    }

    private Predicate<E> connectedPredicate(final N n2, final N n3) {
        return new Predicate<E>(this) { // from class: com.google.common.graph.AbstractNetwork.2

            /* renamed from: c, reason: collision with root package name */
            final /* synthetic */ AbstractNetwork f14503c;

            {
                this.f14503c = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.base.Predicate
            public boolean apply(E e2) {
                return this.f14503c.incidentNodes(e2).adjacentNode(n2).equals(n3);
            }
        };
    }

    private static <N, E> Map<E, EndpointPair<N>> edgeIncidentNodesMap(final Network<N, E> network) {
        return Maps.asMap(network.edges(), new Function() { // from class: com.google.common.graph.l
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return network.incidentNodes(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$edgeInvalidatableSet$0(Object obj) {
        return Boolean.valueOf(edges().contains(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$edgeInvalidatableSet$1(Object obj) {
        return String.format("Edge %s that was used to generate this set is no longer in the graph.", obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$nodeInvalidatableSet$2(Object obj) {
        return Boolean.valueOf(nodes().contains(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$nodeInvalidatableSet$3(Object obj) {
        return String.format("Node %s that was used to generate this set is no longer in the graph.", obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$nodePairInvalidatableSet$4(Object obj, Object obj2) {
        return Boolean.valueOf(nodes().contains(obj) && nodes().contains(obj2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$nodePairInvalidatableSet$5(Object obj, Object obj2) {
        return String.format("Node %s or node %s that were used to generate this set are no longer in the graph.", obj, obj2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> adjacentEdges(E e2) {
        EndpointPair<N> endpointPairIncidentNodes = incidentNodes(e2);
        return g(Sets.difference(Sets.union(incidentEdges(endpointPairIncidentNodes.nodeU()), incidentEdges(endpointPairIncidentNodes.nodeV())), ImmutableSet.of((Object) e2)), e2);
    }

    @Override // com.google.common.graph.Network
    public Graph<N> asGraph() {
        return new AnonymousClass1();
    }

    @Override // com.google.common.graph.Network
    public int degree(N n2) {
        return isDirected() ? IntMath.saturatedAdd(inEdges(n2).size(), outEdges(n2).size()) : IntMath.saturatedAdd(incidentEdges(n2).size(), edgesConnecting(n2, n2).size());
    }

    @Override // com.google.common.graph.Network
    @CheckForNull
    public E edgeConnectingOrNull(N n2, N n3) {
        Set<E> setEdgesConnecting = edgesConnecting(n2, n3);
        int size = setEdgesConnecting.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return setEdgesConnecting.iterator().next();
        }
        throw new IllegalArgumentException(String.format("Cannot call edgeConnecting() when parallel edges exist between %s and %s. Consider calling edgesConnecting() instead.", n2, n3));
    }

    @Override // com.google.common.graph.Network
    public Set<E> edgesConnecting(N n2, N n3) {
        Set<E> setOutEdges = outEdges(n2);
        Set<E> setInEdges = inEdges(n3);
        return j(setOutEdges.size() <= setInEdges.size() ? Collections.unmodifiableSet(Sets.filter(setOutEdges, connectedPredicate(n2, n3))) : Collections.unmodifiableSet(Sets.filter(setInEdges, connectedPredicate(n3, n2))), n2, n3);
    }

    @Override // com.google.common.graph.Network
    public final boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Network)) {
            return false;
        }
        Network network = (Network) obj;
        return isDirected() == network.isDirected() && nodes().equals(network.nodes()) && edgeIncidentNodesMap(this).equals(edgeIncidentNodesMap(network));
    }

    protected final Set g(Set set, final Object obj) {
        return InvalidatableSet.of(set, (Supplier<Boolean>) new Supplier() { // from class: com.google.common.graph.j
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return this.f14583a.lambda$edgeInvalidatableSet$0(obj);
            }
        }, (Supplier<String>) new Supplier() { // from class: com.google.common.graph.k
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return AbstractNetwork.lambda$edgeInvalidatableSet$1(obj);
            }
        });
    }

    protected final boolean h(EndpointPair endpointPair) {
        return endpointPair.isOrdered() == isDirected();
    }

    @Override // com.google.common.graph.Network
    public boolean hasEdgeConnecting(N n2, N n3) {
        Preconditions.checkNotNull(n2);
        Preconditions.checkNotNull(n3);
        return nodes().contains(n2) && successors((AbstractNetwork<N, E>) n2).contains(n3);
    }

    @Override // com.google.common.graph.Network
    public final int hashCode() {
        return edgeIncidentNodesMap(this).hashCode();
    }

    protected final Set i(Set set, final Object obj) {
        return InvalidatableSet.of(set, (Supplier<Boolean>) new Supplier() { // from class: com.google.common.graph.f
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return this.f14575a.lambda$nodeInvalidatableSet$2(obj);
            }
        }, (Supplier<String>) new Supplier() { // from class: com.google.common.graph.g
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return AbstractNetwork.lambda$nodeInvalidatableSet$3(obj);
            }
        });
    }

    @Override // com.google.common.graph.Network
    public int inDegree(N n2) {
        return isDirected() ? inEdges(n2).size() : degree(n2);
    }

    protected final Set j(Set set, final Object obj, final Object obj2) {
        return InvalidatableSet.of(set, (Supplier<Boolean>) new Supplier() { // from class: com.google.common.graph.h
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return this.f14578a.lambda$nodePairInvalidatableSet$4(obj, obj2);
            }
        }, (Supplier<String>) new Supplier() { // from class: com.google.common.graph.i
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return AbstractNetwork.lambda$nodePairInvalidatableSet$5(obj, obj2);
            }
        });
    }

    protected final void k(EndpointPair endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        Preconditions.checkArgument(h(endpointPair), "Mismatch: endpoints' ordering is not compatible with directionality of the graph");
    }

    @Override // com.google.common.graph.Network
    public int outDegree(N n2) {
        return isDirected() ? outEdges(n2).size() : degree(n2);
    }

    @Override // com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
        return predecessors((AbstractNetwork<N, E>) ((Network) obj));
    }

    @Override // com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
        return successors((AbstractNetwork<N, E>) ((Network) obj));
    }

    public String toString() {
        return "isDirected: " + isDirected() + ", allowsParallelEdges: " + allowsParallelEdges() + ", allowsSelfLoops: " + allowsSelfLoops() + ", nodes: " + nodes() + ", edges: " + edgeIncidentNodesMap(this);
    }

    @Override // com.google.common.graph.Network
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        if (h(endpointPair)) {
            return hasEdgeConnecting(endpointPair.nodeU(), endpointPair.nodeV());
        }
        return false;
    }

    @Override // com.google.common.graph.Network
    @CheckForNull
    public E edgeConnectingOrNull(EndpointPair<N> endpointPair) {
        k(endpointPair);
        return edgeConnectingOrNull(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override // com.google.common.graph.Network
    public Set<E> edgesConnecting(EndpointPair<N> endpointPair) {
        k(endpointPair);
        return edgesConnecting(endpointPair.nodeU(), endpointPair.nodeV());
    }
}
