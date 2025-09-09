package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class CompactLinkedHashSet<E> extends CompactHashSet<E> {
    private static final int ENDPOINT = -2;
    private transient int firstEntry;
    private transient int lastEntry;

    @CheckForNull
    private transient int[] predecessor;

    @CheckForNull
    private transient int[] successor;

    CompactLinkedHashSet() {
    }

    public static <E> CompactLinkedHashSet<E> create() {
        return new CompactLinkedHashSet<>();
    }

    public static <E> CompactLinkedHashSet<E> createWithExpectedSize(int i2) {
        return new CompactLinkedHashSet<>(i2);
    }

    private int getPredecessor(int i2) {
        return requirePredecessors()[i2] - 1;
    }

    private int[] requirePredecessors() {
        int[] iArr = this.predecessor;
        Objects.requireNonNull(iArr);
        return iArr;
    }

    private int[] requireSuccessors() {
        int[] iArr = this.successor;
        Objects.requireNonNull(iArr);
        return iArr;
    }

    private void setPredecessor(int i2, int i3) {
        requirePredecessors()[i2] = i3 + 1;
    }

    private void setSucceeds(int i2, int i3) {
        if (i2 == -2) {
            this.firstEntry = i3;
        } else {
            setSuccessor(i2, i3);
        }
        if (i3 == -2) {
            this.lastEntry = i2;
        } else {
            setPredecessor(i3, i2);
        }
    }

    private void setSuccessor(int i2, int i3) {
        requireSuccessors()[i2] = i3 + 1;
    }

    @Override // com.google.common.collect.CompactHashSet
    int adjustAfterRemove(int i2, int i3) {
        return i2 >= size() ? i3 : i2;
    }

    @Override // com.google.common.collect.CompactHashSet
    int allocArrays() {
        int iAllocArrays = super.allocArrays();
        this.predecessor = new int[iAllocArrays];
        this.successor = new int[iAllocArrays];
        return iAllocArrays;
    }

    @Override // com.google.common.collect.CompactHashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        if (needsAllocArrays()) {
            return;
        }
        this.firstEntry = -2;
        this.lastEntry = -2;
        int[] iArr = this.predecessor;
        if (iArr != null && this.successor != null) {
            Arrays.fill(iArr, 0, size(), 0);
            Arrays.fill(this.successor, 0, size(), 0);
        }
        super.clear();
    }

    @Override // com.google.common.collect.CompactHashSet
    @CanIgnoreReturnValue
    Set<E> convertToHashFloodingResistantImplementation() {
        Set<E> setConvertToHashFloodingResistantImplementation = super.convertToHashFloodingResistantImplementation();
        this.predecessor = null;
        this.successor = null;
        return setConvertToHashFloodingResistantImplementation;
    }

    @Override // com.google.common.collect.CompactHashSet
    int firstEntryIndex() {
        return this.firstEntry;
    }

    @Override // com.google.common.collect.CompactHashSet
    int getSuccessor(int i2) {
        return requireSuccessors()[i2] - 1;
    }

    @Override // com.google.common.collect.CompactHashSet
    void init(int i2) {
        super.init(i2);
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    @Override // com.google.common.collect.CompactHashSet
    void insertEntry(int i2, @ParametricNullness E e2, int i3, int i4) {
        super.insertEntry(i2, e2, i3, i4);
        setSucceeds(this.lastEntry, i2);
        setSucceeds(i2, -2);
    }

    @Override // com.google.common.collect.CompactHashSet
    void moveLastEntry(int i2, int i3) {
        int size = size() - 1;
        super.moveLastEntry(i2, i3);
        setSucceeds(getPredecessor(i2), getSuccessor(i2));
        if (i2 < size) {
            setSucceeds(getPredecessor(size), i2);
            setSucceeds(i2, getSuccessor(size));
        }
        requirePredecessors()[size] = 0;
        requireSuccessors()[size] = 0;
    }

    @Override // com.google.common.collect.CompactHashSet
    void resizeEntries(int i2) {
        super.resizeEntries(i2);
        this.predecessor = Arrays.copyOf(requirePredecessors(), i2);
        this.successor = Arrays.copyOf(requireSuccessors(), i2);
    }

    @Override // com.google.common.collect.CompactHashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public Object[] toArray() {
        return ObjectArrays.d(this);
    }

    CompactLinkedHashSet(int i2) {
        super(i2);
    }

    public static <E> CompactLinkedHashSet<E> create(Collection<? extends E> collection) {
        CompactLinkedHashSet<E> compactLinkedHashSetCreateWithExpectedSize = createWithExpectedSize(collection.size());
        compactLinkedHashSetCreateWithExpectedSize.addAll(collection);
        return compactLinkedHashSetCreateWithExpectedSize;
    }

    @Override // com.google.common.collect.CompactHashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        return (T[]) ObjectArrays.e(this, tArr);
    }

    @SafeVarargs
    public static <E> CompactLinkedHashSet<E> create(E... eArr) {
        CompactLinkedHashSet<E> compactLinkedHashSetCreateWithExpectedSize = createWithExpectedSize(eArr.length);
        Collections.addAll(compactLinkedHashSetCreateWithExpectedSize, eArr);
        return compactLinkedHashSetCreateWithExpectedSize;
    }
}
