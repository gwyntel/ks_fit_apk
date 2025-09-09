package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.DirectedGraphConnections;
import com.google.common.graph.ElementOrder;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class DirectedGraphConnections<N, V> implements GraphConnections<N, V> {
    private static final Object PRED = new Object();
    private final Map<N, Object> adjacentNodeValues;

    @CheckForNull
    private final List<NodeConnection<N>> orderedNodeConnections;
    private int predecessorCount;
    private int successorCount;

    /* renamed from: com.google.common.graph.DirectedGraphConnections$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f14523a;

        static {
            int[] iArr = new int[ElementOrder.Type.values().length];
            f14523a = iArr;
            try {
                iArr[ElementOrder.Type.UNORDERED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f14523a[ElementOrder.Type.STABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class NodeConnection<N> {

        /* renamed from: a, reason: collision with root package name */
        final Object f14524a;

        static final class Pred<N> extends NodeConnection<N> {
            Pred(Object obj) {
                super(obj);
            }

            public boolean equals(@CheckForNull Object obj) {
                if (obj instanceof Pred) {
                    return this.f14524a.equals(((Pred) obj).f14524a);
                }
                return false;
            }

            public int hashCode() {
                return Pred.class.hashCode() + this.f14524a.hashCode();
            }
        }

        static final class Succ<N> extends NodeConnection<N> {
            Succ(Object obj) {
                super(obj);
            }

            public boolean equals(@CheckForNull Object obj) {
                if (obj instanceof Succ) {
                    return this.f14524a.equals(((Succ) obj).f14524a);
                }
                return false;
            }

            public int hashCode() {
                return Succ.class.hashCode() + this.f14524a.hashCode();
            }
        }

        NodeConnection(Object obj) {
            this.f14524a = Preconditions.checkNotNull(obj);
        }
    }

    private static final class PredAndSucc {
        private final Object successorValue;

        PredAndSucc(Object obj) {
            this.successorValue = obj;
        }
    }

    private DirectedGraphConnections(Map<N, Object> map, @CheckForNull List<NodeConnection<N>> list, int i2, int i3) {
        this.adjacentNodeValues = (Map) Preconditions.checkNotNull(map);
        this.orderedNodeConnections = list;
        this.predecessorCount = Graphs.a(i2);
        this.successorCount = Graphs.a(i3);
        Preconditions.checkState(i2 <= map.size() && i3 <= map.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPredecessor(@CheckForNull Object obj) {
        return obj == PRED || (obj instanceof PredAndSucc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSuccessor(@CheckForNull Object obj) {
        return (obj == PRED || obj == null) ? false : true;
    }

    static DirectedGraphConnections j(ElementOrder elementOrder) {
        ArrayList arrayList;
        int i2 = AnonymousClass5.f14523a[elementOrder.type().ordinal()];
        if (i2 == 1) {
            arrayList = null;
        } else {
            if (i2 != 2) {
                throw new AssertionError(elementOrder.type());
            }
            arrayList = new ArrayList();
        }
        return new DirectedGraphConnections(new HashMap(4, 1.0f), arrayList, 0, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static DirectedGraphConnections k(Object obj, Iterable iterable, Function function) {
        Preconditions.checkNotNull(obj);
        Preconditions.checkNotNull(function);
        HashMap map = new HashMap();
        ImmutableList.Builder builder = ImmutableList.builder();
        Iterator it = iterable.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            EndpointPair endpointPair = (EndpointPair) it.next();
            if (endpointPair.nodeU().equals(obj) && endpointPair.nodeV().equals(obj)) {
                map.put(obj, new PredAndSucc(function.apply(obj)));
                builder.add((ImmutableList.Builder) new NodeConnection.Pred(obj));
                builder.add((ImmutableList.Builder) new NodeConnection.Succ(obj));
                i2++;
            } else if (endpointPair.nodeV().equals(obj)) {
                Object objNodeU = endpointPair.nodeU();
                Object objPut = map.put(objNodeU, PRED);
                if (objPut != null) {
                    map.put(objNodeU, new PredAndSucc(objPut));
                }
                builder.add((ImmutableList.Builder) new NodeConnection.Pred(objNodeU));
                i2++;
            } else {
                Preconditions.checkArgument(endpointPair.nodeU().equals(obj));
                Object objNodeV = endpointPair.nodeV();
                Object objApply = function.apply(objNodeV);
                Object objPut2 = map.put(objNodeV, objApply);
                if (objPut2 != null) {
                    Preconditions.checkArgument(objPut2 == PRED);
                    map.put(objNodeV, new PredAndSucc(objApply));
                }
                builder.add((ImmutableList.Builder) new NodeConnection.Succ(objNodeV));
            }
            i3++;
        }
        return new DirectedGraphConnections(map, builder.build(), i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ EndpointPair lambda$incidentEdgeIterator$0(Object obj, Object obj2) {
        return EndpointPair.ordered(obj2, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ EndpointPair lambda$incidentEdgeIterator$2(Object obj, NodeConnection nodeConnection) {
        return nodeConnection instanceof NodeConnection.Succ ? EndpointPair.ordered(obj, nodeConnection.f14524a) : EndpointPair.ordered(nodeConnection.f14524a, obj);
    }

    @Override // com.google.common.graph.GraphConnections
    public void addPredecessor(N n2, V v2) {
        Map<N, Object> map = this.adjacentNodeValues;
        Object obj = PRED;
        Object objPut = map.put(n2, obj);
        if (objPut != null) {
            if (objPut instanceof PredAndSucc) {
                this.adjacentNodeValues.put(n2, objPut);
                return;
            } else if (objPut == obj) {
                return;
            } else {
                this.adjacentNodeValues.put(n2, new PredAndSucc(objPut));
            }
        }
        int i2 = this.predecessorCount + 1;
        this.predecessorCount = i2;
        Graphs.c(i2);
        List<NodeConnection<N>> list = this.orderedNodeConnections;
        if (list != null) {
            list.add(new NodeConnection.Pred(n2));
        }
    }

    @Override // com.google.common.graph.GraphConnections
    @CheckForNull
    public V addSuccessor(N n2, V v2) {
        Object objPut = this.adjacentNodeValues.put(n2, v2);
        if (objPut == null) {
            objPut = null;
        } else if (objPut instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n2, new PredAndSucc(v2));
            objPut = ((PredAndSucc) objPut).successorValue;
        } else if (objPut == PRED) {
            this.adjacentNodeValues.put(n2, new PredAndSucc(v2));
            objPut = null;
        }
        if (objPut == null) {
            int i2 = this.successorCount + 1;
            this.successorCount = i2;
            Graphs.c(i2);
            List<NodeConnection<N>> list = this.orderedNodeConnections;
            if (list != null) {
                list.add(new NodeConnection.Succ(n2));
            }
        }
        if (objPut == null) {
            return null;
        }
        return (V) objPut;
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> adjacentNodes() {
        return this.orderedNodeConnections == null ? Collections.unmodifiableSet(this.adjacentNodeValues.keySet()) : new AbstractSet<N>() { // from class: com.google.common.graph.DirectedGraphConnections.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                return DirectedGraphConnections.this.adjacentNodeValues.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return DirectedGraphConnections.this.adjacentNodeValues.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = DirectedGraphConnections.this.orderedNodeConnections.iterator();
                final HashSet hashSet = new HashSet();
                return new AbstractIterator<N>(this) { // from class: com.google.common.graph.DirectedGraphConnections.1.1

                    /* renamed from: c, reason: collision with root package name */
                    final /* synthetic */ AnonymousClass1 f14509c;

                    {
                        this.f14509c = this;
                    }

                    @Override // com.google.common.collect.AbstractIterator
                    protected Object computeNext() {
                        while (it.hasNext()) {
                            NodeConnection nodeConnection = (NodeConnection) it.next();
                            if (hashSet.add(nodeConnection.f14524a)) {
                                return nodeConnection.f14524a;
                            }
                        }
                        return a();
                    }
                };
            }
        };
    }

    @Override // com.google.common.graph.GraphConnections
    public Iterator<EndpointPair<N>> incidentEdgeIterator(final N n2) {
        Preconditions.checkNotNull(n2);
        List<NodeConnection<N>> list = this.orderedNodeConnections;
        final Iterator itConcat = list == null ? Iterators.concat(Iterators.transform(predecessors().iterator(), new Function() { // from class: com.google.common.graph.p
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return DirectedGraphConnections.lambda$incidentEdgeIterator$0(n2, obj);
            }
        }), Iterators.transform(successors().iterator(), new Function() { // from class: com.google.common.graph.q
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return EndpointPair.ordered(n2, obj);
            }
        })) : Iterators.transform(list.iterator(), new Function() { // from class: com.google.common.graph.r
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return DirectedGraphConnections.lambda$incidentEdgeIterator$2(n2, (DirectedGraphConnections.NodeConnection) obj);
            }
        });
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        return new AbstractIterator<EndpointPair<N>>(this) { // from class: com.google.common.graph.DirectedGraphConnections.4

            /* renamed from: c, reason: collision with root package name */
            final /* synthetic */ DirectedGraphConnections f14522c;

            {
                this.f14522c = this;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.common.collect.AbstractIterator
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public EndpointPair computeNext() {
                while (itConcat.hasNext()) {
                    EndpointPair endpointPair = (EndpointPair) itConcat.next();
                    if (!endpointPair.nodeU().equals(endpointPair.nodeV()) || !atomicBoolean.getAndSet(true)) {
                        return endpointPair;
                    }
                }
                return (EndpointPair) a();
            }
        };
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> predecessors() {
        return new AbstractSet<N>() { // from class: com.google.common.graph.DirectedGraphConnections.2
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                return DirectedGraphConnections.isPredecessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return DirectedGraphConnections.this.predecessorCount;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<N> iterator() {
                if (DirectedGraphConnections.this.orderedNodeConnections == null) {
                    final Iterator it = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                    return new AbstractIterator<N>(this) { // from class: com.google.common.graph.DirectedGraphConnections.2.1

                        /* renamed from: b, reason: collision with root package name */
                        final /* synthetic */ AnonymousClass2 f14512b;

                        {
                            this.f14512b = this;
                        }

                        @Override // com.google.common.collect.AbstractIterator
                        protected Object computeNext() {
                            while (it.hasNext()) {
                                Map.Entry entry = (Map.Entry) it.next();
                                if (DirectedGraphConnections.isPredecessor(entry.getValue())) {
                                    return entry.getKey();
                                }
                            }
                            return a();
                        }
                    };
                }
                final Iterator it2 = DirectedGraphConnections.this.orderedNodeConnections.iterator();
                return new AbstractIterator<N>(this) { // from class: com.google.common.graph.DirectedGraphConnections.2.2

                    /* renamed from: b, reason: collision with root package name */
                    final /* synthetic */ AnonymousClass2 f14514b;

                    {
                        this.f14514b = this;
                    }

                    @Override // com.google.common.collect.AbstractIterator
                    protected Object computeNext() {
                        while (it2.hasNext()) {
                            NodeConnection nodeConnection = (NodeConnection) it2.next();
                            if (nodeConnection instanceof NodeConnection.Pred) {
                                return nodeConnection.f14524a;
                            }
                        }
                        return a();
                    }
                };
            }
        };
    }

    @Override // com.google.common.graph.GraphConnections
    public void removePredecessor(N n2) {
        Preconditions.checkNotNull(n2);
        Object obj = this.adjacentNodeValues.get(n2);
        if (obj == PRED) {
            this.adjacentNodeValues.remove(n2);
        } else if (!(obj instanceof PredAndSucc)) {
            return;
        } else {
            this.adjacentNodeValues.put(n2, ((PredAndSucc) obj).successorValue);
        }
        int i2 = this.predecessorCount - 1;
        this.predecessorCount = i2;
        Graphs.a(i2);
        List<NodeConnection<N>> list = this.orderedNodeConnections;
        if (list != null) {
            list.remove(new NodeConnection.Pred(n2));
        }
    }

    @Override // com.google.common.graph.GraphConnections
    @CheckForNull
    public V removeSuccessor(Object obj) {
        Object obj2;
        Preconditions.checkNotNull(obj);
        Object obj3 = this.adjacentNodeValues.get(obj);
        if (obj3 == null || obj3 == (obj2 = PRED)) {
            obj3 = null;
        } else if (obj3 instanceof PredAndSucc) {
            this.adjacentNodeValues.put(obj, obj2);
            obj3 = ((PredAndSucc) obj3).successorValue;
        } else {
            this.adjacentNodeValues.remove(obj);
        }
        if (obj3 != null) {
            int i2 = this.successorCount - 1;
            this.successorCount = i2;
            Graphs.a(i2);
            List<NodeConnection<N>> list = this.orderedNodeConnections;
            if (list != null) {
                list.remove(new NodeConnection.Succ(obj));
            }
        }
        if (obj3 == null) {
            return null;
        }
        return (V) obj3;
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> successors() {
        return new AbstractSet<N>() { // from class: com.google.common.graph.DirectedGraphConnections.3
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                return DirectedGraphConnections.isSuccessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return DirectedGraphConnections.this.successorCount;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<N> iterator() {
                if (DirectedGraphConnections.this.orderedNodeConnections == null) {
                    final Iterator it = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                    return new AbstractIterator<N>(this) { // from class: com.google.common.graph.DirectedGraphConnections.3.1

                        /* renamed from: b, reason: collision with root package name */
                        final /* synthetic */ AnonymousClass3 f14517b;

                        {
                            this.f14517b = this;
                        }

                        @Override // com.google.common.collect.AbstractIterator
                        protected Object computeNext() {
                            while (it.hasNext()) {
                                Map.Entry entry = (Map.Entry) it.next();
                                if (DirectedGraphConnections.isSuccessor(entry.getValue())) {
                                    return entry.getKey();
                                }
                            }
                            return a();
                        }
                    };
                }
                final Iterator it2 = DirectedGraphConnections.this.orderedNodeConnections.iterator();
                return new AbstractIterator<N>(this) { // from class: com.google.common.graph.DirectedGraphConnections.3.2

                    /* renamed from: b, reason: collision with root package name */
                    final /* synthetic */ AnonymousClass3 f14519b;

                    {
                        this.f14519b = this;
                    }

                    @Override // com.google.common.collect.AbstractIterator
                    protected Object computeNext() {
                        while (it2.hasNext()) {
                            NodeConnection nodeConnection = (NodeConnection) it2.next();
                            if (nodeConnection instanceof NodeConnection.Succ) {
                                return nodeConnection.f14524a;
                            }
                        }
                        return a();
                    }
                };
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.GraphConnections
    @CheckForNull
    public V value(N n2) {
        Preconditions.checkNotNull(n2);
        V v2 = (V) this.adjacentNodeValues.get(n2);
        if (v2 == PRED) {
            return null;
        }
        return v2 instanceof PredAndSucc ? (V) ((PredAndSucc) v2).successorValue : v2;
    }
}
