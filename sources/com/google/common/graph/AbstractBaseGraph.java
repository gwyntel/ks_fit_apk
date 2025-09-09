package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.util.AbstractSet;
import java.util.Set;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class AbstractBaseGraph<N> implements BaseGraph<N> {

    /* renamed from: com.google.common.graph.AbstractBaseGraph$2, reason: invalid class name */
    class AnonymousClass2 extends IncidentEdgeSet<N> {
        AnonymousClass2(BaseGraph baseGraph, Object obj) {
            super(baseGraph, obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ EndpointPair lambda$iterator$0(Object obj) {
            return EndpointPair.ordered(obj, this.f14532a);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ EndpointPair lambda$iterator$1(Object obj) {
            return EndpointPair.ordered(this.f14532a, obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ EndpointPair lambda$iterator$2(Object obj) {
            return EndpointPair.unordered(this.f14532a, obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public UnmodifiableIterator<EndpointPair<N>> iterator() {
            return this.f14533b.isDirected() ? Iterators.unmodifiableIterator(Iterators.concat(Iterators.transform(this.f14533b.predecessors((BaseGraph) this.f14532a).iterator(), new Function() { // from class: com.google.common.graph.c
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return this.f14572a.lambda$iterator$0(obj);
                }
            }), Iterators.transform(Sets.difference(this.f14533b.successors((BaseGraph) this.f14532a), ImmutableSet.of(this.f14532a)).iterator(), new Function() { // from class: com.google.common.graph.d
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return this.f14573a.lambda$iterator$1(obj);
                }
            }))) : Iterators.unmodifiableIterator(Iterators.transform(this.f14533b.adjacentNodes(this.f14532a).iterator(), new Function() { // from class: com.google.common.graph.e
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return this.f14574a.lambda$iterator$2(obj);
                }
            }));
        }
    }

    AbstractBaseGraph() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$nodeInvalidatableSet$0(Object obj) {
        return Boolean.valueOf(nodes().contains(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$nodeInvalidatableSet$1(Object obj) {
        return String.format("Node %s that was used to generate this set is no longer in the graph.", obj);
    }

    private /* synthetic */ Boolean lambda$nodePairInvalidatableSet$2(Object obj, Object obj2) {
        return Boolean.valueOf(nodes().contains(obj) && nodes().contains(obj2));
    }

    protected long c() {
        long jDegree = 0;
        while (nodes().iterator().hasNext()) {
            jDegree += degree(r0.next());
        }
        Preconditions.checkState((1 & jDegree) == 0);
        return jDegree >>> 1;
    }

    protected final boolean d(EndpointPair endpointPair) {
        return endpointPair.isOrdered() == isDirected();
    }

    @Override // com.google.common.graph.BaseGraph
    public int degree(N n2) {
        if (isDirected()) {
            return IntMath.saturatedAdd(predecessors((AbstractBaseGraph<N>) n2).size(), successors((AbstractBaseGraph<N>) n2).size());
        }
        Set<N> setAdjacentNodes = adjacentNodes(n2);
        return IntMath.saturatedAdd(setAdjacentNodes.size(), (allowsSelfLoops() && setAdjacentNodes.contains(n2)) ? 1 : 0);
    }

    protected final Set e(Set set, final Object obj) {
        return InvalidatableSet.of(set, (Supplier<Boolean>) new Supplier() { // from class: com.google.common.graph.a
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return this.f14569a.lambda$nodeInvalidatableSet$0(obj);
            }
        }, (Supplier<String>) new Supplier() { // from class: com.google.common.graph.b
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return AbstractBaseGraph.lambda$nodeInvalidatableSet$1(obj);
            }
        });
    }

    @Override // com.google.common.graph.BaseGraph
    public Set<EndpointPair<N>> edges() {
        return new AbstractSet<EndpointPair<N>>() { // from class: com.google.common.graph.AbstractBaseGraph.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                return AbstractBaseGraph.this.d(endpointPair) && AbstractBaseGraph.this.nodes().contains(endpointPair.nodeU()) && AbstractBaseGraph.this.successors((AbstractBaseGraph) endpointPair.nodeU()).contains(endpointPair.nodeV());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@CheckForNull Object obj) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return Ints.saturatedCast(AbstractBaseGraph.this.c());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return EndpointPairIterator.c(AbstractBaseGraph.this);
            }
        };
    }

    protected final void f(EndpointPair endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        Preconditions.checkArgument(d(endpointPair), "Mismatch: endpoints' ordering is not compatible with directionality of the graph");
    }

    @Override // com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(N n2, N n3) {
        Preconditions.checkNotNull(n2);
        Preconditions.checkNotNull(n3);
        return nodes().contains(n2) && successors((AbstractBaseGraph<N>) n2).contains(n3);
    }

    @Override // com.google.common.graph.BaseGraph
    public int inDegree(N n2) {
        return isDirected() ? predecessors((AbstractBaseGraph<N>) n2).size() : degree(n2);
    }

    @Override // com.google.common.graph.BaseGraph
    public ElementOrder<N> incidentEdgeOrder() {
        return ElementOrder.unordered();
    }

    @Override // com.google.common.graph.BaseGraph
    public Set<EndpointPair<N>> incidentEdges(N n2) {
        Preconditions.checkNotNull(n2);
        Preconditions.checkArgument(nodes().contains(n2), "Node %s is not an element of this graph.", n2);
        return e(new AnonymousClass2(this, n2), n2);
    }

    @Override // com.google.common.graph.BaseGraph
    public int outDegree(N n2) {
        return isDirected() ? successors((AbstractBaseGraph<N>) n2).size() : degree(n2);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
        return predecessors((AbstractBaseGraph<N>) ((BaseGraph) obj));
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
        return successors((AbstractBaseGraph<N>) ((BaseGraph) obj));
    }

    @Override // com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        if (!d(endpointPair)) {
            return false;
        }
        N nNodeU = endpointPair.nodeU();
        return nodes().contains(nNodeU) && successors((AbstractBaseGraph<N>) nNodeU).contains(endpointPair.nodeV());
    }
}
