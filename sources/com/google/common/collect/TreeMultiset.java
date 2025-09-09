package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class TreeMultiset<E> extends AbstractSortedMultiset<E> implements Serializable {

    @J2ktIncompatible
    @GwtIncompatible
    private static final long serialVersionUID = 1;
    private final transient AvlNode<E> header;
    private final transient GeneralRange<E> range;
    private final transient Reference<AvlNode<E>> rootReference;

    /* renamed from: com.google.common.collect.TreeMultiset$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f14375a;

        static {
            int[] iArr = new int[BoundType.values().length];
            f14375a = iArr;
            try {
                iArr[BoundType.OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f14375a[BoundType.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private enum Aggregate {
        SIZE { // from class: com.google.common.collect.TreeMultiset.Aggregate.1
            @Override // com.google.common.collect.TreeMultiset.Aggregate
            int nodeAggregate(AvlNode<?> avlNode) {
                return ((AvlNode) avlNode).elemCount;
            }

            @Override // com.google.common.collect.TreeMultiset.Aggregate
            long treeAggregate(@CheckForNull AvlNode<?> avlNode) {
                if (avlNode == null) {
                    return 0L;
                }
                return ((AvlNode) avlNode).totalCount;
            }
        },
        DISTINCT { // from class: com.google.common.collect.TreeMultiset.Aggregate.2
            @Override // com.google.common.collect.TreeMultiset.Aggregate
            int nodeAggregate(AvlNode<?> avlNode) {
                return 1;
            }

            @Override // com.google.common.collect.TreeMultiset.Aggregate
            long treeAggregate(@CheckForNull AvlNode<?> avlNode) {
                if (avlNode == null) {
                    return 0L;
                }
                return ((AvlNode) avlNode).distinctElements;
            }
        };

        abstract int nodeAggregate(AvlNode<?> avlNode);

        abstract long treeAggregate(@CheckForNull AvlNode<?> avlNode);
    }

    private static final class Reference<T> {

        @CheckForNull
        private T value;

        private Reference() {
        }

        void a() {
            this.value = null;
        }

        public void checkAndSet(@CheckForNull T t2, @CheckForNull T t3) {
            if (this.value != t2) {
                throw new ConcurrentModificationException();
            }
            this.value = t3;
        }

        @CheckForNull
        public T get() {
            return this.value;
        }
    }

    TreeMultiset(Reference<AvlNode<E>> reference, GeneralRange<E> generalRange, AvlNode<E> avlNode) {
        super(generalRange.comparator());
        this.rootReference = reference;
        this.range = generalRange;
        this.header = avlNode;
    }

    private long aggregateAboveRange(Aggregate aggregate, @CheckForNull AvlNode<E> avlNode) {
        long jTreeAggregate;
        long jAggregateAboveRange;
        if (avlNode == null) {
            return 0L;
        }
        int iCompare = comparator().compare(NullnessCasts.a(this.range.getUpperEndpoint()), avlNode.r());
        if (iCompare > 0) {
            return aggregateAboveRange(aggregate, ((AvlNode) avlNode).right);
        }
        if (iCompare == 0) {
            int i2 = AnonymousClass4.f14375a[this.range.getUpperBoundType().ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    return aggregate.treeAggregate(((AvlNode) avlNode).right);
                }
                throw new AssertionError();
            }
            jTreeAggregate = aggregate.nodeAggregate(avlNode);
            jAggregateAboveRange = aggregate.treeAggregate(((AvlNode) avlNode).right);
        } else {
            jTreeAggregate = aggregate.treeAggregate(((AvlNode) avlNode).right) + aggregate.nodeAggregate(avlNode);
            jAggregateAboveRange = aggregateAboveRange(aggregate, ((AvlNode) avlNode).left);
        }
        return jTreeAggregate + jAggregateAboveRange;
    }

    private long aggregateBelowRange(Aggregate aggregate, @CheckForNull AvlNode<E> avlNode) {
        long jTreeAggregate;
        long jAggregateBelowRange;
        if (avlNode == null) {
            return 0L;
        }
        int iCompare = comparator().compare(NullnessCasts.a(this.range.getLowerEndpoint()), avlNode.r());
        if (iCompare < 0) {
            return aggregateBelowRange(aggregate, ((AvlNode) avlNode).left);
        }
        if (iCompare == 0) {
            int i2 = AnonymousClass4.f14375a[this.range.getLowerBoundType().ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    return aggregate.treeAggregate(((AvlNode) avlNode).left);
                }
                throw new AssertionError();
            }
            jTreeAggregate = aggregate.nodeAggregate(avlNode);
            jAggregateBelowRange = aggregate.treeAggregate(((AvlNode) avlNode).left);
        } else {
            jTreeAggregate = aggregate.treeAggregate(((AvlNode) avlNode).left) + aggregate.nodeAggregate(avlNode);
            jAggregateBelowRange = aggregateBelowRange(aggregate, ((AvlNode) avlNode).right);
        }
        return jTreeAggregate + jAggregateBelowRange;
    }

    private long aggregateForEntries(Aggregate aggregate) {
        AvlNode<E> avlNode = this.rootReference.get();
        long jTreeAggregate = aggregate.treeAggregate(avlNode);
        if (this.range.hasLowerBound()) {
            jTreeAggregate -= aggregateBelowRange(aggregate, avlNode);
        }
        return this.range.hasUpperBound() ? jTreeAggregate - aggregateAboveRange(aggregate, avlNode) : jTreeAggregate;
    }

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset<>(Ordering.natural());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CheckForNull
    public AvlNode<E> firstNode() {
        AvlNode<E> avlNodeSucc;
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode == null) {
            return null;
        }
        if (this.range.hasLowerBound()) {
            Object objA = NullnessCasts.a(this.range.getLowerEndpoint());
            avlNodeSucc = avlNode.ceiling(comparator(), objA);
            if (avlNodeSucc == null) {
                return null;
            }
            if (this.range.getLowerBoundType() == BoundType.OPEN && comparator().compare(objA, avlNodeSucc.r()) == 0) {
                avlNodeSucc = avlNodeSucc.succ();
            }
        } else {
            avlNodeSucc = this.header.succ();
        }
        if (avlNodeSucc == this.header || !this.range.contains(avlNodeSucc.r())) {
            return null;
        }
        return avlNodeSucc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CheckForNull
    public AvlNode<E> lastNode() {
        AvlNode<E> avlNodePred;
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode == null) {
            return null;
        }
        if (this.range.hasUpperBound()) {
            Object objA = NullnessCasts.a(this.range.getUpperEndpoint());
            avlNodePred = avlNode.floor(comparator(), objA);
            if (avlNodePred == null) {
                return null;
            }
            if (this.range.getUpperBoundType() == BoundType.OPEN && comparator().compare(objA, avlNodePred.r()) == 0) {
                avlNodePred = avlNodePred.pred();
            }
        } else {
            avlNodePred = this.header.pred();
        }
        if (avlNodePred == this.header || !this.range.contains(avlNodePred.r())) {
            return null;
        }
        return avlNodePred;
    }

    @J2ktIncompatible
    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        Object object = objectInputStream.readObject();
        Objects.requireNonNull(object);
        Comparator comparator = (Comparator) object;
        Serialization.a(AbstractSortedMultiset.class, "comparator").b(this, comparator);
        Serialization.a(TreeMultiset.class, "range").b(this, GeneralRange.all(comparator));
        Serialization.a(TreeMultiset.class, "rootReference").b(this, new Reference());
        AvlNode avlNode = new AvlNode();
        Serialization.a(TreeMultiset.class, "header").b(this, avlNode);
        successor(avlNode, avlNode);
        Serialization.f(this, objectInputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> void successor(AvlNode<T> avlNode, AvlNode<T> avlNode2) {
        ((AvlNode) avlNode).succ = avlNode2;
        ((AvlNode) avlNode2).pred = avlNode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Multiset.Entry<E> wrapEntry(final AvlNode<E> avlNode) {
        return new Multisets.AbstractEntry<E>(this) { // from class: com.google.common.collect.TreeMultiset.1

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ TreeMultiset f14368b;

            {
                this.f14368b = this;
            }

            @Override // com.google.common.collect.Multiset.Entry
            public int getCount() {
                int iQ = avlNode.q();
                return iQ == 0 ? this.f14368b.count(getElement()) : iQ;
            }

            @Override // com.google.common.collect.Multiset.Entry
            @ParametricNullness
            public E getElement() {
                return (E) avlNode.r();
            }
        };
    }

    @J2ktIncompatible
    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(elementSet().comparator());
        Serialization.k(this, objectOutputStream);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int add(@ParametricNullness E e2, int i2) {
        CollectPreconditions.b(i2, "occurrences");
        if (i2 == 0) {
            return count(e2);
        }
        Preconditions.checkArgument(this.range.contains(e2));
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode != null) {
            int[] iArr = new int[1];
            this.rootReference.checkAndSet(avlNode, avlNode.o(comparator(), e2, i2, iArr));
            return iArr[0];
        }
        comparator().compare(e2, e2);
        AvlNode<E> avlNode2 = new AvlNode<>(e2, i2);
        AvlNode<E> avlNode3 = this.header;
        successor(avlNode3, avlNode2, avlNode3);
        this.rootReference.checkAndSet(avlNode, avlNode2);
        return 0;
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        if (this.range.hasLowerBound() || this.range.hasUpperBound()) {
            Iterators.b(entryIterator());
            return;
        }
        AvlNode<E> avlNodeSucc = this.header.succ();
        while (true) {
            AvlNode<E> avlNode = this.header;
            if (avlNodeSucc == avlNode) {
                successor(avlNode, avlNode);
                this.rootReference.a();
                return;
            }
            AvlNode<E> avlNodeSucc2 = avlNodeSucc.succ();
            ((AvlNode) avlNodeSucc).elemCount = 0;
            ((AvlNode) avlNodeSucc).left = null;
            ((AvlNode) avlNodeSucc).right = null;
            ((AvlNode) avlNodeSucc).pred = null;
            ((AvlNode) avlNodeSucc).succ = null;
            avlNodeSucc = avlNodeSucc2;
        }
    }

    @Override // com.google.common.collect.AbstractSortedMultiset, com.google.common.collect.SortedMultiset, com.google.common.collect.SortedIterable
    public /* bridge */ /* synthetic */ Comparator comparator() {
        return super.comparator();
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ boolean contains(@CheckForNull Object obj) {
        return super.contains(obj);
    }

    @Override // com.google.common.collect.Multiset
    public int count(@CheckForNull Object obj) {
        try {
            AvlNode<E> avlNode = this.rootReference.get();
            if (this.range.contains(obj) && avlNode != null) {
                return avlNode.p(comparator(), obj);
            }
        } catch (ClassCastException | NullPointerException unused) {
        }
        return 0;
    }

    @Override // com.google.common.collect.AbstractSortedMultiset
    Iterator<Multiset.Entry<E>> descendingEntryIterator() {
        return new Iterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.TreeMultiset.3

            /* renamed from: a, reason: collision with root package name */
            AvlNode f14372a;

            /* renamed from: b, reason: collision with root package name */
            Multiset.Entry f14373b = null;

            {
                this.f14372a = TreeMultiset.this.lastNode();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.f14372a == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooLow(this.f14372a.r())) {
                    return true;
                }
                this.f14372a = null;
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            public void remove() {
                Preconditions.checkState(this.f14373b != null, "no calls to next() since the last call to remove()");
                TreeMultiset.this.setCount(this.f14373b.getElement(), 0);
                this.f14373b = null;
            }

            @Override // java.util.Iterator
            public Multiset.Entry<E> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Objects.requireNonNull(this.f14372a);
                Multiset.Entry<E> entryWrapEntry = TreeMultiset.this.wrapEntry(this.f14372a);
                this.f14373b = entryWrapEntry;
                if (this.f14372a.pred() == TreeMultiset.this.header) {
                    this.f14372a = null;
                } else {
                    this.f14372a = this.f14372a.pred();
                }
                return entryWrapEntry;
            }
        };
    }

    @Override // com.google.common.collect.AbstractSortedMultiset, com.google.common.collect.SortedMultiset
    public /* bridge */ /* synthetic */ SortedMultiset descendingMultiset() {
        return super.descendingMultiset();
    }

    @Override // com.google.common.collect.AbstractMultiset
    int distinctElements() {
        return Ints.saturatedCast(aggregateForEntries(Aggregate.DISTINCT));
    }

    @Override // com.google.common.collect.AbstractMultiset
    Iterator<E> elementIterator() {
        return Multisets.c(entryIterator());
    }

    @Override // com.google.common.collect.AbstractSortedMultiset, com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ NavigableSet elementSet() {
        return super.elementSet();
    }

    @Override // com.google.common.collect.AbstractMultiset
    Iterator<Multiset.Entry<E>> entryIterator() {
        return new Iterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.TreeMultiset.2

            /* renamed from: a, reason: collision with root package name */
            AvlNode f14369a;

            /* renamed from: b, reason: collision with root package name */
            Multiset.Entry f14370b;

            {
                this.f14369a = TreeMultiset.this.firstNode();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.f14369a == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooHigh(this.f14369a.r())) {
                    return true;
                }
                this.f14369a = null;
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            public void remove() {
                Preconditions.checkState(this.f14370b != null, "no calls to next() since the last call to remove()");
                TreeMultiset.this.setCount(this.f14370b.getElement(), 0);
                this.f14370b = null;
            }

            @Override // java.util.Iterator
            public Multiset.Entry<E> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                TreeMultiset treeMultiset = TreeMultiset.this;
                AvlNode avlNode = this.f14369a;
                Objects.requireNonNull(avlNode);
                Multiset.Entry<E> entryWrapEntry = treeMultiset.wrapEntry(avlNode);
                this.f14370b = entryWrapEntry;
                if (this.f14369a.succ() == TreeMultiset.this.header) {
                    this.f14369a = null;
                } else {
                    this.f14369a = this.f14369a.succ();
                }
                return entryWrapEntry;
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    @Override // com.google.common.collect.AbstractSortedMultiset, com.google.common.collect.SortedMultiset
    @CheckForNull
    public /* bridge */ /* synthetic */ Multiset.Entry firstEntry() {
        return super.firstEntry();
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> headMultiset(@ParametricNullness E e2, BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.upTo(comparator(), e2, boundType)), this.header);
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    public Iterator<E> iterator() {
        return Multisets.f(this);
    }

    @Override // com.google.common.collect.AbstractSortedMultiset, com.google.common.collect.SortedMultiset
    @CheckForNull
    public /* bridge */ /* synthetic */ Multiset.Entry lastEntry() {
        return super.lastEntry();
    }

    @Override // com.google.common.collect.AbstractSortedMultiset, com.google.common.collect.SortedMultiset
    @CheckForNull
    public /* bridge */ /* synthetic */ Multiset.Entry pollFirstEntry() {
        return super.pollFirstEntry();
    }

    @Override // com.google.common.collect.AbstractSortedMultiset, com.google.common.collect.SortedMultiset
    @CheckForNull
    public /* bridge */ /* synthetic */ Multiset.Entry pollLastEntry() {
        return super.pollLastEntry();
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int remove(@CheckForNull Object obj, int i2) {
        CollectPreconditions.b(i2, "occurrences");
        if (i2 == 0) {
            return count(obj);
        }
        AvlNode<E> avlNode = this.rootReference.get();
        int[] iArr = new int[1];
        try {
            if (this.range.contains(obj) && avlNode != null) {
                this.rootReference.checkAndSet(avlNode, avlNode.s(comparator(), obj, i2, iArr));
                return iArr[0];
            }
        } catch (ClassCastException | NullPointerException unused) {
        }
        return 0;
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int setCount(@ParametricNullness E e2, int i2) {
        CollectPreconditions.b(i2, "count");
        if (!this.range.contains(e2)) {
            Preconditions.checkArgument(i2 == 0);
            return 0;
        }
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode == null) {
            if (i2 > 0) {
                add(e2, i2);
            }
            return 0;
        }
        int[] iArr = new int[1];
        this.rootReference.checkAndSet(avlNode, avlNode.u(comparator(), e2, i2, iArr));
        return iArr[0];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        return Ints.saturatedCast(aggregateForEntries(Aggregate.SIZE));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractSortedMultiset, com.google.common.collect.SortedMultiset
    public /* bridge */ /* synthetic */ SortedMultiset subMultiset(@ParametricNullness Object obj, BoundType boundType, @ParametricNullness Object obj2, BoundType boundType2) {
        return super.subMultiset(obj, boundType, obj2, boundType2);
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> tailMultiset(@ParametricNullness E e2, BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.downTo(comparator(), e2, boundType)), this.header);
    }

    public static <E> TreeMultiset<E> create(@CheckForNull Comparator<? super E> comparator) {
        return comparator == null ? new TreeMultiset<>(Ordering.natural()) : new TreeMultiset<>(comparator);
    }

    static int distinctElements(@CheckForNull AvlNode<?> avlNode) {
        if (avlNode == null) {
            return 0;
        }
        return ((AvlNode) avlNode).distinctElements;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> void successor(AvlNode<T> avlNode, AvlNode<T> avlNode2, AvlNode<T> avlNode3) {
        successor(avlNode, avlNode2);
        successor(avlNode2, avlNode3);
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> iterable) {
        TreeMultiset<E> treeMultisetCreate = create();
        Iterables.addAll(treeMultisetCreate, iterable);
        return treeMultisetCreate;
    }

    TreeMultiset(Comparator<? super E> comparator) {
        super(comparator);
        this.range = GeneralRange.all(comparator);
        AvlNode<E> avlNode = new AvlNode<>();
        this.header = avlNode;
        successor(avlNode, avlNode);
        this.rootReference = new Reference<>();
    }

    private static final class AvlNode<E> {
        private int distinctElements;

        @CheckForNull
        private final E elem;
        private int elemCount;
        private int height;

        @CheckForNull
        private AvlNode<E> left;

        @CheckForNull
        private AvlNode<E> pred;

        @CheckForNull
        private AvlNode<E> right;

        @CheckForNull
        private AvlNode<E> succ;
        private long totalCount;

        /* JADX WARN: Multi-variable type inference failed */
        AvlNode(Object obj, int i2) {
            Preconditions.checkArgument(i2 > 0);
            this.elem = obj;
            this.elemCount = i2;
            this.totalCount = i2;
            this.distinctElements = 1;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        private AvlNode<E> addLeftChild(@ParametricNullness E e2, int i2) {
            this.left = new AvlNode<>(e2, i2);
            TreeMultiset.successor(pred(), this.left, this);
            this.height = Math.max(2, this.height);
            this.distinctElements++;
            this.totalCount += i2;
            return this;
        }

        private AvlNode<E> addRightChild(@ParametricNullness E e2, int i2) {
            AvlNode<E> avlNode = new AvlNode<>(e2, i2);
            this.right = avlNode;
            TreeMultiset.successor(this, avlNode, succ());
            this.height = Math.max(2, this.height);
            this.distinctElements++;
            this.totalCount += i2;
            return this;
        }

        private int balanceFactor() {
            return height(this.left) - height(this.right);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        @CheckForNull
        public AvlNode<E> ceiling(Comparator<? super E> comparator, @ParametricNullness E e2) {
            int iCompare = comparator.compare(e2, (Object) r());
            if (iCompare < 0) {
                AvlNode<E> avlNode = this.left;
                return avlNode == null ? this : (AvlNode) MoreObjects.firstNonNull(avlNode.ceiling(comparator, e2), this);
            }
            if (iCompare == 0) {
                return this;
            }
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                return null;
            }
            return avlNode2.ceiling(comparator, e2);
        }

        @CheckForNull
        private AvlNode<E> deleteMe() {
            int i2 = this.elemCount;
            this.elemCount = 0;
            TreeMultiset.successor(pred(), succ());
            AvlNode<E> avlNode = this.left;
            if (avlNode == null) {
                return this.right;
            }
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                return avlNode;
            }
            if (avlNode.height >= avlNode2.height) {
                AvlNode<E> avlNodePred = pred();
                avlNodePred.left = this.left.removeMax(avlNodePred);
                avlNodePred.right = this.right;
                avlNodePred.distinctElements = this.distinctElements - 1;
                avlNodePred.totalCount = this.totalCount - i2;
                return avlNodePred.rebalance();
            }
            AvlNode<E> avlNodeSucc = succ();
            avlNodeSucc.right = this.right.removeMin(avlNodeSucc);
            avlNodeSucc.left = this.left;
            avlNodeSucc.distinctElements = this.distinctElements - 1;
            avlNodeSucc.totalCount = this.totalCount - i2;
            return avlNodeSucc.rebalance();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        @CheckForNull
        public AvlNode<E> floor(Comparator<? super E> comparator, @ParametricNullness E e2) {
            int iCompare = comparator.compare(e2, (Object) r());
            if (iCompare > 0) {
                AvlNode<E> avlNode = this.right;
                return avlNode == null ? this : (AvlNode) MoreObjects.firstNonNull(avlNode.floor(comparator, e2), this);
            }
            if (iCompare == 0) {
                return this;
            }
            AvlNode<E> avlNode2 = this.left;
            if (avlNode2 == null) {
                return null;
            }
            return avlNode2.floor(comparator, e2);
        }

        private static int height(@CheckForNull AvlNode<?> avlNode) {
            if (avlNode == null) {
                return 0;
            }
            return ((AvlNode) avlNode).height;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public AvlNode<E> pred() {
            AvlNode<E> avlNode = this.pred;
            Objects.requireNonNull(avlNode);
            return avlNode;
        }

        private AvlNode<E> rebalance() {
            int iBalanceFactor = balanceFactor();
            if (iBalanceFactor == -2) {
                Objects.requireNonNull(this.right);
                if (this.right.balanceFactor() > 0) {
                    this.right = this.right.rotateRight();
                }
                return rotateLeft();
            }
            if (iBalanceFactor != 2) {
                recomputeHeight();
                return this;
            }
            Objects.requireNonNull(this.left);
            if (this.left.balanceFactor() < 0) {
                this.left = this.left.rotateLeft();
            }
            return rotateRight();
        }

        private void recompute() {
            recomputeMultiset();
            recomputeHeight();
        }

        private void recomputeHeight() {
            this.height = Math.max(height(this.left), height(this.right)) + 1;
        }

        private void recomputeMultiset() {
            this.distinctElements = TreeMultiset.distinctElements(this.left) + 1 + TreeMultiset.distinctElements(this.right);
            this.totalCount = this.elemCount + totalCount(this.left) + totalCount(this.right);
        }

        @CheckForNull
        private AvlNode<E> removeMax(AvlNode<E> avlNode) {
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                return this.left;
            }
            this.right = avlNode2.removeMax(avlNode);
            this.distinctElements--;
            this.totalCount -= avlNode.elemCount;
            return rebalance();
        }

        @CheckForNull
        private AvlNode<E> removeMin(AvlNode<E> avlNode) {
            AvlNode<E> avlNode2 = this.left;
            if (avlNode2 == null) {
                return this.right;
            }
            this.left = avlNode2.removeMin(avlNode);
            this.distinctElements--;
            this.totalCount -= avlNode.elemCount;
            return rebalance();
        }

        private AvlNode<E> rotateLeft() {
            Preconditions.checkState(this.right != null);
            AvlNode<E> avlNode = this.right;
            this.right = avlNode.left;
            avlNode.left = this;
            avlNode.totalCount = this.totalCount;
            avlNode.distinctElements = this.distinctElements;
            recompute();
            avlNode.recomputeHeight();
            return avlNode;
        }

        private AvlNode<E> rotateRight() {
            Preconditions.checkState(this.left != null);
            AvlNode<E> avlNode = this.left;
            this.left = avlNode.right;
            avlNode.right = this;
            avlNode.totalCount = this.totalCount;
            avlNode.distinctElements = this.distinctElements;
            recompute();
            avlNode.recomputeHeight();
            return avlNode;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public AvlNode<E> succ() {
            AvlNode<E> avlNode = this.succ;
            Objects.requireNonNull(avlNode);
            return avlNode;
        }

        private static long totalCount(@CheckForNull AvlNode<?> avlNode) {
            if (avlNode == null) {
                return 0L;
            }
            return ((AvlNode) avlNode).totalCount;
        }

        /* JADX WARN: Multi-variable type inference failed */
        AvlNode o(Comparator comparator, Object obj, int i2, int[] iArr) {
            int iCompare = comparator.compare(obj, r());
            if (iCompare < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return addLeftChild(obj, i2);
                }
                int i3 = avlNode.height;
                AvlNode<E> avlNodeO = avlNode.o(comparator, obj, i2, iArr);
                this.left = avlNodeO;
                if (iArr[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += i2;
                return avlNodeO.height == i3 ? this : rebalance();
            }
            if (iCompare <= 0) {
                int i4 = this.elemCount;
                iArr[0] = i4;
                long j2 = i2;
                Preconditions.checkArgument(((long) i4) + j2 <= 2147483647L);
                this.elemCount += i2;
                this.totalCount += j2;
                return this;
            }
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                iArr[0] = 0;
                return addRightChild(obj, i2);
            }
            int i5 = avlNode2.height;
            AvlNode<E> avlNodeO2 = avlNode2.o(comparator, obj, i2, iArr);
            this.right = avlNodeO2;
            if (iArr[0] == 0) {
                this.distinctElements++;
            }
            this.totalCount += i2;
            return avlNodeO2.height == i5 ? this : rebalance();
        }

        int p(Comparator comparator, Object obj) {
            int iCompare = comparator.compare(obj, r());
            if (iCompare < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    return 0;
                }
                return avlNode.p(comparator, obj);
            }
            if (iCompare <= 0) {
                return this.elemCount;
            }
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                return 0;
            }
            return avlNode2.p(comparator, obj);
        }

        int q() {
            return this.elemCount;
        }

        Object r() {
            return NullnessCasts.a(this.elem);
        }

        AvlNode s(Comparator comparator, Object obj, int i2, int[] iArr) {
            int iCompare = comparator.compare(obj, r());
            if (iCompare < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return this;
                }
                this.left = avlNode.s(comparator, obj, i2, iArr);
                int i3 = iArr[0];
                if (i3 > 0) {
                    if (i2 >= i3) {
                        this.distinctElements--;
                        this.totalCount -= i3;
                    } else {
                        this.totalCount -= i2;
                    }
                }
                return i3 == 0 ? this : rebalance();
            }
            if (iCompare <= 0) {
                int i4 = this.elemCount;
                iArr[0] = i4;
                if (i2 >= i4) {
                    return deleteMe();
                }
                this.elemCount = i4 - i2;
                this.totalCount -= i2;
                return this;
            }
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                iArr[0] = 0;
                return this;
            }
            this.right = avlNode2.s(comparator, obj, i2, iArr);
            int i5 = iArr[0];
            if (i5 > 0) {
                if (i2 >= i5) {
                    this.distinctElements--;
                    this.totalCount -= i5;
                } else {
                    this.totalCount -= i2;
                }
            }
            return rebalance();
        }

        /* JADX WARN: Multi-variable type inference failed */
        AvlNode t(Comparator comparator, Object obj, int i2, int i3, int[] iArr) {
            int iCompare = comparator.compare(obj, r());
            if (iCompare < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return (i2 != 0 || i3 <= 0) ? this : addLeftChild(obj, i3);
                }
                this.left = avlNode.t(comparator, obj, i2, i3, iArr);
                int i4 = iArr[0];
                if (i4 == i2) {
                    if (i3 == 0 && i4 != 0) {
                        this.distinctElements--;
                    } else if (i3 > 0 && i4 == 0) {
                        this.distinctElements++;
                    }
                    this.totalCount += i3 - i4;
                }
                return rebalance();
            }
            if (iCompare <= 0) {
                int i5 = this.elemCount;
                iArr[0] = i5;
                if (i2 == i5) {
                    if (i3 == 0) {
                        return deleteMe();
                    }
                    this.totalCount += i3 - i5;
                    this.elemCount = i3;
                }
                return this;
            }
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                iArr[0] = 0;
                return (i2 != 0 || i3 <= 0) ? this : addRightChild(obj, i3);
            }
            this.right = avlNode2.t(comparator, obj, i2, i3, iArr);
            int i6 = iArr[0];
            if (i6 == i2) {
                if (i3 == 0 && i6 != 0) {
                    this.distinctElements--;
                } else if (i3 > 0 && i6 == 0) {
                    this.distinctElements++;
                }
                this.totalCount += i3 - i6;
            }
            return rebalance();
        }

        public String toString() {
            return Multisets.immutableEntry(r(), q()).toString();
        }

        /* JADX WARN: Multi-variable type inference failed */
        AvlNode u(Comparator comparator, Object obj, int i2, int[] iArr) {
            int iCompare = comparator.compare(obj, r());
            if (iCompare < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return i2 > 0 ? addLeftChild(obj, i2) : this;
                }
                this.left = avlNode.u(comparator, obj, i2, iArr);
                if (i2 == 0 && iArr[0] != 0) {
                    this.distinctElements--;
                } else if (i2 > 0 && iArr[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += i2 - iArr[0];
                return rebalance();
            }
            if (iCompare <= 0) {
                iArr[0] = this.elemCount;
                if (i2 == 0) {
                    return deleteMe();
                }
                this.totalCount += i2 - r3;
                this.elemCount = i2;
                return this;
            }
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                iArr[0] = 0;
                return i2 > 0 ? addRightChild(obj, i2) : this;
            }
            this.right = avlNode2.u(comparator, obj, i2, iArr);
            if (i2 == 0 && iArr[0] != 0) {
                this.distinctElements--;
            } else if (i2 > 0 && iArr[0] == 0) {
                this.distinctElements++;
            }
            this.totalCount += i2 - iArr[0];
            return rebalance();
        }

        AvlNode() {
            this.elem = null;
            this.elemCount = 1;
        }
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public boolean setCount(@ParametricNullness E e2, int i2, int i3) {
        CollectPreconditions.b(i3, "newCount");
        CollectPreconditions.b(i2, "oldCount");
        Preconditions.checkArgument(this.range.contains(e2));
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode != null) {
            int[] iArr = new int[1];
            this.rootReference.checkAndSet(avlNode, avlNode.t(comparator(), e2, i2, i3, iArr));
            return iArr[0] == i2;
        }
        if (i2 != 0) {
            return false;
        }
        if (i3 > 0) {
            add(e2, i3);
        }
        return true;
    }
}
