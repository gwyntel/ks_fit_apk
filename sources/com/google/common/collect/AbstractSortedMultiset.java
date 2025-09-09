package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultisets;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E> {

    @GwtTransient
    final Comparator<? super E> comparator;

    @CheckForNull
    @LazyInit
    private transient SortedMultiset<E> descendingMultiset;

    AbstractSortedMultiset() {
        this(Ordering.natural());
    }

    @Override // com.google.common.collect.SortedMultiset, com.google.common.collect.SortedIterable
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    SortedMultiset<E> createDescendingMultiset() {
        return new DescendingMultiset<E>() { // from class: com.google.common.collect.AbstractSortedMultiset.1DescendingMultisetImpl
            @Override // com.google.common.collect.DescendingMultiset
            Iterator b() {
                return AbstractSortedMultiset.this.descendingEntryIterator();
            }

            @Override // com.google.common.collect.DescendingMultiset
            SortedMultiset c() {
                return AbstractSortedMultiset.this;
            }

            @Override // com.google.common.collect.DescendingMultiset, com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<E> iterator() {
                return AbstractSortedMultiset.this.descendingIterator();
            }
        };
    }

    abstract Iterator descendingEntryIterator();

    Iterator<E> descendingIterator() {
        return Multisets.f(descendingMultiset());
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> descendingMultiset() {
        SortedMultiset<E> sortedMultiset = this.descendingMultiset;
        if (sortedMultiset != null) {
            return sortedMultiset;
        }
        SortedMultiset<E> sortedMultisetCreateDescendingMultiset = createDescendingMultiset();
        this.descendingMultiset = sortedMultisetCreateDescendingMultiset;
        return sortedMultisetCreateDescendingMultiset;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    public Multiset.Entry<E> firstEntry() {
        Iterator itEntryIterator = entryIterator();
        if (itEntryIterator.hasNext()) {
            return (Multiset.Entry) itEntryIterator.next();
        }
        return null;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    public Multiset.Entry<E> lastEntry() {
        Iterator itDescendingEntryIterator = descendingEntryIterator();
        if (itDescendingEntryIterator.hasNext()) {
            return (Multiset.Entry) itDescendingEntryIterator.next();
        }
        return null;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    public Multiset.Entry<E> pollFirstEntry() {
        Iterator itEntryIterator = entryIterator();
        if (!itEntryIterator.hasNext()) {
            return null;
        }
        Multiset.Entry entry = (Multiset.Entry) itEntryIterator.next();
        Multiset.Entry<E> entryImmutableEntry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        itEntryIterator.remove();
        return entryImmutableEntry;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    public Multiset.Entry<E> pollLastEntry() {
        Iterator itDescendingEntryIterator = descendingEntryIterator();
        if (!itDescendingEntryIterator.hasNext()) {
            return null;
        }
        Multiset.Entry entry = (Multiset.Entry) itDescendingEntryIterator.next();
        Multiset.Entry<E> entryImmutableEntry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        itDescendingEntryIterator.remove();
        return entryImmutableEntry;
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> subMultiset(@ParametricNullness E e2, BoundType boundType, @ParametricNullness E e3, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return tailMultiset(e2, boundType).headMultiset(e3, boundType2);
    }

    AbstractSortedMultiset(Comparator comparator) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.AbstractMultiset
    public NavigableSet<E> createElementSet() {
        return new SortedMultisets.NavigableElementSet(this);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public NavigableSet<E> elementSet() {
        return (NavigableSet) super.elementSet();
    }
}
