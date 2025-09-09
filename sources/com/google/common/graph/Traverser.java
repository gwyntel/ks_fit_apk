package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.DoNotMock;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

@Beta
@ElementTypesAreNonnullByDefault
@DoNotMock("Call forGraph or forTree, passing a lambda or a Graph with the desired edges (built with GraphBuilder)")
/* loaded from: classes3.dex */
public abstract class Traverser<N> {
    private final SuccessorsFunction<N> successorFunction;

    private enum InsertionOrder {
        FRONT { // from class: com.google.common.graph.Traverser.InsertionOrder.1
            @Override // com.google.common.graph.Traverser.InsertionOrder
            <T> void insertInto(Deque<T> deque, T t2) {
                deque.addFirst(t2);
            }
        },
        BACK { // from class: com.google.common.graph.Traverser.InsertionOrder.2
            @Override // com.google.common.graph.Traverser.InsertionOrder
            <T> void insertInto(Deque<T> deque, T t2) {
                deque.addLast(t2);
            }
        };

        abstract <T> void insertInto(Deque<T> deque, T t2);
    }

    private static abstract class Traversal<N> {

        /* renamed from: a, reason: collision with root package name */
        final SuccessorsFunction f14558a;

        Traversal(SuccessorsFunction successorsFunction) {
            this.f14558a = successorsFunction;
        }

        static Traversal b(SuccessorsFunction successorsFunction) {
            final HashSet hashSet = new HashSet();
            return new Traversal<Object>(successorsFunction) { // from class: com.google.common.graph.Traverser.Traversal.1
                @Override // com.google.common.graph.Traverser.Traversal
                Object f(Deque deque) {
                    Iterator it = (Iterator) deque.getFirst();
                    while (it.hasNext()) {
                        Object next = it.next();
                        Objects.requireNonNull(next);
                        if (hashSet.add(next)) {
                            return next;
                        }
                    }
                    deque.removeFirst();
                    return null;
                }
            };
        }

        static Traversal c(SuccessorsFunction successorsFunction) {
            return new Traversal<Object>(successorsFunction) { // from class: com.google.common.graph.Traverser.Traversal.2
                @Override // com.google.common.graph.Traverser.Traversal
                Object f(Deque deque) {
                    Iterator it = (Iterator) deque.getFirst();
                    if (it.hasNext()) {
                        return Preconditions.checkNotNull(it.next());
                    }
                    deque.removeFirst();
                    return null;
                }
            };
        }

        private Iterator<N> topDown(Iterator<? extends N> it, final InsertionOrder insertionOrder) {
            final ArrayDeque arrayDeque = new ArrayDeque();
            arrayDeque.add(it);
            return new AbstractIterator<N>(this) { // from class: com.google.common.graph.Traverser.Traversal.3

                /* renamed from: c, reason: collision with root package name */
                final /* synthetic */ Traversal f14562c;

                {
                    this.f14562c = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.collect.AbstractIterator
                protected Object computeNext() {
                    do {
                        Object objF = this.f14562c.f(arrayDeque);
                        if (objF != null) {
                            Iterator it2 = this.f14562c.f14558a.successors(objF).iterator();
                            if (it2.hasNext()) {
                                insertionOrder.insertInto(arrayDeque, it2);
                            }
                            return objF;
                        }
                    } while (!arrayDeque.isEmpty());
                    return a();
                }
            };
        }

        final Iterator a(Iterator it) {
            return topDown(it, InsertionOrder.BACK);
        }

        final Iterator d(Iterator it) {
            final ArrayDeque arrayDeque = new ArrayDeque();
            final ArrayDeque arrayDeque2 = new ArrayDeque();
            arrayDeque2.add(it);
            return new AbstractIterator<N>(this) { // from class: com.google.common.graph.Traverser.Traversal.4

                /* renamed from: c, reason: collision with root package name */
                final /* synthetic */ Traversal f14565c;

                {
                    this.f14565c = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.collect.AbstractIterator
                protected Object computeNext() {
                    while (true) {
                        Object objF = this.f14565c.f(arrayDeque2);
                        if (objF == null) {
                            return !arrayDeque.isEmpty() ? arrayDeque.pop() : a();
                        }
                        Iterator it2 = this.f14565c.f14558a.successors(objF).iterator();
                        if (!it2.hasNext()) {
                            return objF;
                        }
                        arrayDeque2.addFirst(it2);
                        arrayDeque.push(objF);
                    }
                }
            };
        }

        final Iterator e(Iterator it) {
            return topDown(it, InsertionOrder.FRONT);
        }

        abstract Object f(Deque deque);
    }

    public static <N> Traverser<N> forGraph(final SuccessorsFunction<N> successorsFunction) {
        return new Traverser<N>(successorsFunction) { // from class: com.google.common.graph.Traverser.1
            @Override // com.google.common.graph.Traverser
            Traversal a() {
                return Traversal.b(successorsFunction);
            }
        };
    }

    public static <N> Traverser<N> forTree(final SuccessorsFunction<N> successorsFunction) {
        if (successorsFunction instanceof BaseGraph) {
            Preconditions.checkArgument(((BaseGraph) successorsFunction).isDirected(), "Undirected graphs can never be trees.");
        }
        if (successorsFunction instanceof Network) {
            Preconditions.checkArgument(((Network) successorsFunction).isDirected(), "Undirected networks can never be trees.");
        }
        return new Traverser<N>(successorsFunction) { // from class: com.google.common.graph.Traverser.2
            @Override // com.google.common.graph.Traverser
            Traversal a() {
                return Traversal.c(successorsFunction);
            }
        };
    }

    private ImmutableSet<N> validate(Iterable<? extends N> iterable) {
        ImmutableSet<N> immutableSetCopyOf = ImmutableSet.copyOf(iterable);
        UnmodifiableIterator<N> it = immutableSetCopyOf.iterator();
        while (it.hasNext()) {
            this.successorFunction.successors(it.next());
        }
        return immutableSetCopyOf;
    }

    abstract Traversal a();

    public final Iterable<N> breadthFirst(N n2) {
        return breadthFirst((Iterable) ImmutableSet.of(n2));
    }

    public final Iterable<N> depthFirstPostOrder(N n2) {
        return depthFirstPostOrder((Iterable) ImmutableSet.of(n2));
    }

    public final Iterable<N> depthFirstPreOrder(N n2) {
        return depthFirstPreOrder((Iterable) ImmutableSet.of(n2));
    }

    private Traverser(SuccessorsFunction<N> successorsFunction) {
        this.successorFunction = (SuccessorsFunction) Preconditions.checkNotNull(successorsFunction);
    }

    public final Iterable<N> breadthFirst(Iterable<? extends N> iterable) {
        final ImmutableSet<N> immutableSetValidate = validate(iterable);
        return new Iterable<N>(this) { // from class: com.google.common.graph.Traverser.3

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ Traverser f14553b;

            {
                this.f14553b = this;
            }

            @Override // java.lang.Iterable
            public Iterator<N> iterator() {
                return this.f14553b.a().a(immutableSetValidate.iterator());
            }
        };
    }

    public final Iterable<N> depthFirstPostOrder(Iterable<? extends N> iterable) {
        final ImmutableSet<N> immutableSetValidate = validate(iterable);
        return new Iterable<N>(this) { // from class: com.google.common.graph.Traverser.5

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ Traverser f14557b;

            {
                this.f14557b = this;
            }

            @Override // java.lang.Iterable
            public Iterator<N> iterator() {
                return this.f14557b.a().d(immutableSetValidate.iterator());
            }
        };
    }

    public final Iterable<N> depthFirstPreOrder(Iterable<? extends N> iterable) {
        final ImmutableSet<N> immutableSetValidate = validate(iterable);
        return new Iterable<N>(this) { // from class: com.google.common.graph.Traverser.4

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ Traverser f14555b;

            {
                this.f14555b = this;
            }

            @Override // java.lang.Iterable
            public Iterator<N> iterator() {
                return this.f14555b.a().e(immutableSetValidate.iterator());
            }
        };
    }
}
