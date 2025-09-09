package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class CompactHashSet<E> extends AbstractSet<E> implements Serializable {

    @VisibleForTesting
    static final double HASH_FLOODING_FPP = 0.001d;
    private static final int MAX_HASH_BUCKET_LENGTH = 9;

    @VisibleForTesting
    @CheckForNull
    transient Object[] elements;

    @CheckForNull
    private transient int[] entries;
    private transient int metadata;
    private transient int size;

    @CheckForNull
    private transient Object table;

    CompactHashSet() {
        init(3);
    }

    public static <E> CompactHashSet<E> create() {
        return new CompactHashSet<>();
    }

    private Set<E> createHashFloodingResistantDelegate(int i2) {
        return new LinkedHashSet(i2, 1.0f);
    }

    public static <E> CompactHashSet<E> createWithExpectedSize(int i2) {
        return new CompactHashSet<>(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public E element(int i2) {
        return (E) requireElements()[i2];
    }

    private int entry(int i2) {
        return requireEntries()[i2];
    }

    private int hashTableMask() {
        return (1 << (this.metadata & 31)) - 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @J2ktIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i2 = objectInputStream.readInt();
        if (i2 < 0) {
            throw new InvalidObjectException("Invalid size: " + i2);
        }
        init(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            add(objectInputStream.readObject());
        }
    }

    private Object[] requireElements() {
        Object[] objArr = this.elements;
        Objects.requireNonNull(objArr);
        return objArr;
    }

    private int[] requireEntries() {
        int[] iArr = this.entries;
        Objects.requireNonNull(iArr);
        return iArr;
    }

    private Object requireTable() {
        Object obj = this.table;
        Objects.requireNonNull(obj);
        return obj;
    }

    private void resizeMeMaybe(int i2) {
        int iMin;
        int length = requireEntries().length;
        if (i2 <= length || (iMin = Math.min(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, (Math.max(1, length >>> 1) + length) | 1)) == length) {
            return;
        }
        resizeEntries(iMin);
    }

    @CanIgnoreReturnValue
    private int resizeTable(int i2, int i3, int i4, int i5) {
        Object objA = CompactHashing.a(i3);
        int i6 = i3 - 1;
        if (i5 != 0) {
            CompactHashing.i(objA, i4 & i6, i5 + 1);
        }
        Object objRequireTable = requireTable();
        int[] iArrRequireEntries = requireEntries();
        for (int i7 = 0; i7 <= i2; i7++) {
            int iH = CompactHashing.h(objRequireTable, i7);
            while (iH != 0) {
                int i8 = iH - 1;
                int i9 = iArrRequireEntries[i8];
                int iB = CompactHashing.b(i9, i2) | i7;
                int i10 = iB & i6;
                int iH2 = CompactHashing.h(objA, i10);
                CompactHashing.i(objA, i10, iH);
                iArrRequireEntries[i8] = CompactHashing.d(iB, iH2, i6);
                iH = CompactHashing.c(i9, i2);
            }
        }
        this.table = objA;
        setHashTableMask(i6);
        return i6;
    }

    private void setElement(int i2, E e2) {
        requireElements()[i2] = e2;
    }

    private void setEntry(int i2, int i3) {
        requireEntries()[i2] = i3;
    }

    private void setHashTableMask(int i2) {
        this.metadata = CompactHashing.d(this.metadata, 32 - Integer.numberOfLeadingZeros(i2), 31);
    }

    @J2ktIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            objectOutputStream.writeObject(it.next());
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    @CanIgnoreReturnValue
    public boolean add(@ParametricNullness E e2) {
        if (needsAllocArrays()) {
            allocArrays();
        }
        Set<E> setDelegateOrNull = delegateOrNull();
        if (setDelegateOrNull != null) {
            return setDelegateOrNull.add(e2);
        }
        int[] iArrRequireEntries = requireEntries();
        Object[] objArrRequireElements = requireElements();
        int i2 = this.size;
        int i3 = i2 + 1;
        int iD = Hashing.d(e2);
        int iHashTableMask = hashTableMask();
        int i4 = iD & iHashTableMask;
        int iH = CompactHashing.h(requireTable(), i4);
        if (iH != 0) {
            int iB = CompactHashing.b(iD, iHashTableMask);
            int i5 = 0;
            while (true) {
                int i6 = iH - 1;
                int i7 = iArrRequireEntries[i6];
                if (CompactHashing.b(i7, iHashTableMask) == iB && com.google.common.base.Objects.equal(e2, objArrRequireElements[i6])) {
                    return false;
                }
                int iC = CompactHashing.c(i7, iHashTableMask);
                i5++;
                if (iC != 0) {
                    iH = iC;
                } else {
                    if (i5 >= 9) {
                        return convertToHashFloodingResistantImplementation().add(e2);
                    }
                    if (i3 > iHashTableMask) {
                        iHashTableMask = resizeTable(iHashTableMask, CompactHashing.e(iHashTableMask), iD, i2);
                    } else {
                        iArrRequireEntries[i6] = CompactHashing.d(i7, i3, iHashTableMask);
                    }
                }
            }
        } else if (i3 > iHashTableMask) {
            iHashTableMask = resizeTable(iHashTableMask, CompactHashing.e(iHashTableMask), iD, i2);
        } else {
            CompactHashing.i(requireTable(), i4, i3);
        }
        resizeMeMaybe(i3);
        insertEntry(i2, e2, iD, iHashTableMask);
        this.size = i3;
        incrementModCount();
        return true;
    }

    int adjustAfterRemove(int i2, int i3) {
        return i2 - 1;
    }

    @CanIgnoreReturnValue
    int allocArrays() {
        Preconditions.checkState(needsAllocArrays(), "Arrays already allocated");
        int i2 = this.metadata;
        int iJ = CompactHashing.j(i2);
        this.table = CompactHashing.a(iJ);
        setHashTableMask(iJ - 1);
        this.entries = new int[i2];
        this.elements = new Object[i2];
        return i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        if (needsAllocArrays()) {
            return;
        }
        incrementModCount();
        Set<E> setDelegateOrNull = delegateOrNull();
        if (setDelegateOrNull != null) {
            this.metadata = Ints.constrainToRange(size(), 3, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
            setDelegateOrNull.clear();
            this.table = null;
            this.size = 0;
            return;
        }
        Arrays.fill(requireElements(), 0, this.size, (Object) null);
        CompactHashing.g(requireTable());
        Arrays.fill(requireEntries(), 0, this.size, 0);
        this.size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@CheckForNull Object obj) {
        if (needsAllocArrays()) {
            return false;
        }
        Set<E> setDelegateOrNull = delegateOrNull();
        if (setDelegateOrNull != null) {
            return setDelegateOrNull.contains(obj);
        }
        int iD = Hashing.d(obj);
        int iHashTableMask = hashTableMask();
        int iH = CompactHashing.h(requireTable(), iD & iHashTableMask);
        if (iH == 0) {
            return false;
        }
        int iB = CompactHashing.b(iD, iHashTableMask);
        do {
            int i2 = iH - 1;
            int iEntry = entry(i2);
            if (CompactHashing.b(iEntry, iHashTableMask) == iB && com.google.common.base.Objects.equal(obj, element(i2))) {
                return true;
            }
            iH = CompactHashing.c(iEntry, iHashTableMask);
        } while (iH != 0);
        return false;
    }

    @CanIgnoreReturnValue
    Set<E> convertToHashFloodingResistantImplementation() {
        Set<E> setCreateHashFloodingResistantDelegate = createHashFloodingResistantDelegate(hashTableMask() + 1);
        int iFirstEntryIndex = firstEntryIndex();
        while (iFirstEntryIndex >= 0) {
            setCreateHashFloodingResistantDelegate.add(element(iFirstEntryIndex));
            iFirstEntryIndex = getSuccessor(iFirstEntryIndex);
        }
        this.table = setCreateHashFloodingResistantDelegate;
        this.entries = null;
        this.elements = null;
        incrementModCount();
        return setCreateHashFloodingResistantDelegate;
    }

    @VisibleForTesting
    @CheckForNull
    Set<E> delegateOrNull() {
        Object obj = this.table;
        if (obj instanceof Set) {
            return (Set) obj;
        }
        return null;
    }

    int firstEntryIndex() {
        return isEmpty() ? -1 : 0;
    }

    int getSuccessor(int i2) {
        int i3 = i2 + 1;
        if (i3 < this.size) {
            return i3;
        }
        return -1;
    }

    void incrementModCount() {
        this.metadata += 32;
    }

    void init(int i2) {
        Preconditions.checkArgument(i2 >= 0, "Expected size must be >= 0");
        this.metadata = Ints.constrainToRange(i2, 1, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
    }

    void insertEntry(int i2, @ParametricNullness E e2, int i3, int i4) {
        setEntry(i2, CompactHashing.d(i3, 0, i4));
        setElement(i2, e2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return size() == 0;
    }

    @VisibleForTesting
    boolean isUsingHashFloodingResistance() {
        return delegateOrNull() != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        Set<E> setDelegateOrNull = delegateOrNull();
        return setDelegateOrNull != null ? setDelegateOrNull.iterator() : new Iterator<E>() { // from class: com.google.common.collect.CompactHashSet.1

            /* renamed from: a, reason: collision with root package name */
            int f13936a;

            /* renamed from: b, reason: collision with root package name */
            int f13937b;

            /* renamed from: c, reason: collision with root package name */
            int f13938c = -1;

            {
                this.f13936a = CompactHashSet.this.metadata;
                this.f13937b = CompactHashSet.this.firstEntryIndex();
            }

            private void checkForConcurrentModification() {
                if (CompactHashSet.this.metadata != this.f13936a) {
                    throw new ConcurrentModificationException();
                }
            }

            void a() {
                this.f13936a += 32;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.f13937b >= 0;
            }

            @Override // java.util.Iterator
            @ParametricNullness
            public E next() {
                checkForConcurrentModification();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int i2 = this.f13937b;
                this.f13938c = i2;
                E e2 = (E) CompactHashSet.this.element(i2);
                this.f13937b = CompactHashSet.this.getSuccessor(this.f13937b);
                return e2;
            }

            @Override // java.util.Iterator
            public void remove() {
                checkForConcurrentModification();
                CollectPreconditions.e(this.f13938c >= 0);
                a();
                CompactHashSet compactHashSet = CompactHashSet.this;
                compactHashSet.remove(compactHashSet.element(this.f13938c));
                this.f13937b = CompactHashSet.this.adjustAfterRemove(this.f13937b, this.f13938c);
                this.f13938c = -1;
            }
        };
    }

    void moveLastEntry(int i2, int i3) {
        Object objRequireTable = requireTable();
        int[] iArrRequireEntries = requireEntries();
        Object[] objArrRequireElements = requireElements();
        int size = size();
        int i4 = size - 1;
        if (i2 >= i4) {
            objArrRequireElements[i2] = null;
            iArrRequireEntries[i2] = 0;
            return;
        }
        Object obj = objArrRequireElements[i4];
        objArrRequireElements[i2] = obj;
        objArrRequireElements[i4] = null;
        iArrRequireEntries[i2] = iArrRequireEntries[i4];
        iArrRequireEntries[i4] = 0;
        int iD = Hashing.d(obj) & i3;
        int iH = CompactHashing.h(objRequireTable, iD);
        if (iH == size) {
            CompactHashing.i(objRequireTable, iD, i2 + 1);
            return;
        }
        while (true) {
            int i5 = iH - 1;
            int i6 = iArrRequireEntries[i5];
            int iC = CompactHashing.c(i6, i3);
            if (iC == size) {
                iArrRequireEntries[i5] = CompactHashing.d(i6, i2 + 1, i3);
                return;
            }
            iH = iC;
        }
    }

    boolean needsAllocArrays() {
        return this.table == null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    @CanIgnoreReturnValue
    public boolean remove(@CheckForNull Object obj) {
        if (needsAllocArrays()) {
            return false;
        }
        Set<E> setDelegateOrNull = delegateOrNull();
        if (setDelegateOrNull != null) {
            return setDelegateOrNull.remove(obj);
        }
        int iHashTableMask = hashTableMask();
        int iF = CompactHashing.f(obj, null, iHashTableMask, requireTable(), requireEntries(), requireElements(), null);
        if (iF == -1) {
            return false;
        }
        moveLastEntry(iF, iHashTableMask);
        this.size--;
        incrementModCount();
        return true;
    }

    void resizeEntries(int i2) {
        this.entries = Arrays.copyOf(requireEntries(), i2);
        this.elements = Arrays.copyOf(requireElements(), i2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        Set<E> setDelegateOrNull = delegateOrNull();
        return setDelegateOrNull != null ? setDelegateOrNull.size() : this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public Object[] toArray() {
        if (needsAllocArrays()) {
            return new Object[0];
        }
        Set<E> setDelegateOrNull = delegateOrNull();
        return setDelegateOrNull != null ? setDelegateOrNull.toArray() : Arrays.copyOf(requireElements(), this.size);
    }

    public void trimToSize() {
        if (needsAllocArrays()) {
            return;
        }
        Set<E> setDelegateOrNull = delegateOrNull();
        if (setDelegateOrNull != null) {
            Set<E> setCreateHashFloodingResistantDelegate = createHashFloodingResistantDelegate(size());
            setCreateHashFloodingResistantDelegate.addAll(setDelegateOrNull);
            this.table = setCreateHashFloodingResistantDelegate;
            return;
        }
        int i2 = this.size;
        if (i2 < requireEntries().length) {
            resizeEntries(i2);
        }
        int iJ = CompactHashing.j(i2);
        int iHashTableMask = hashTableMask();
        if (iJ < iHashTableMask) {
            resizeTable(iHashTableMask, iJ, 0, 0);
        }
    }

    public static <E> CompactHashSet<E> create(Collection<? extends E> collection) {
        CompactHashSet<E> compactHashSetCreateWithExpectedSize = createWithExpectedSize(collection.size());
        compactHashSetCreateWithExpectedSize.addAll(collection);
        return compactHashSetCreateWithExpectedSize;
    }

    CompactHashSet(int i2) {
        init(i2);
    }

    @SafeVarargs
    public static <E> CompactHashSet<E> create(E... eArr) {
        CompactHashSet<E> compactHashSetCreateWithExpectedSize = createWithExpectedSize(eArr.length);
        Collections.addAll(compactHashSetCreateWithExpectedSize, eArr);
        return compactHashSetCreateWithExpectedSize;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    @CanIgnoreReturnValue
    public <T> T[] toArray(T[] tArr) {
        if (needsAllocArrays()) {
            if (tArr.length > 0) {
                tArr[0] = null;
            }
            return tArr;
        }
        Set<E> setDelegateOrNull = delegateOrNull();
        if (setDelegateOrNull != null) {
            return (T[]) setDelegateOrNull.toArray(tArr);
        }
        return (T[]) ObjectArrays.f(requireElements(), 0, this.size, tArr);
    }
}
