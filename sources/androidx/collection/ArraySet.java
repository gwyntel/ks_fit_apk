package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public final class ArraySet<E> implements Collection<E>, Set<E> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArraySet";

    @Nullable
    private static Object[] sBaseCache;
    private static int sBaseCacheSize;

    @Nullable
    private static Object[] sTwiceBaseCache;
    private static int sTwiceBaseCacheSize;

    /* renamed from: a, reason: collision with root package name */
    Object[] f2497a;

    /* renamed from: b, reason: collision with root package name */
    int f2498b;
    private int[] mHashes;
    private static final Object sBaseCacheLock = new Object();
    private static final Object sTwiceBaseCacheLock = new Object();

    private class ElementIterator extends IndexBasedArrayIterator<E> {
        ElementIterator() {
            super(ArraySet.this.f2498b);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected Object a(int i2) {
            return ArraySet.this.valueAt(i2);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected void b(int i2) {
            ArraySet.this.removeAt(i2);
        }
    }

    public ArraySet() {
        this(0);
    }

    private void allocArrays(int i2) {
        if (i2 == 8) {
            synchronized (sTwiceBaseCacheLock) {
                try {
                    Object[] objArr = sTwiceBaseCache;
                    if (objArr != null) {
                        try {
                            this.f2497a = objArr;
                            sTwiceBaseCache = (Object[]) objArr[0];
                            int[] iArr = (int[]) objArr[1];
                            this.mHashes = iArr;
                            if (iArr != null) {
                                objArr[1] = null;
                                objArr[0] = null;
                                sTwiceBaseCacheSize--;
                                return;
                            }
                        } catch (ClassCastException unused) {
                        }
                        System.out.println("ArraySet Found corrupt ArraySet cache: [0]=" + objArr[0] + " [1]=" + objArr[1]);
                        sTwiceBaseCache = null;
                        sTwiceBaseCacheSize = 0;
                    }
                } finally {
                }
            }
        } else if (i2 == 4) {
            synchronized (sBaseCacheLock) {
                try {
                    Object[] objArr2 = sBaseCache;
                    if (objArr2 != null) {
                        try {
                            this.f2497a = objArr2;
                            sBaseCache = (Object[]) objArr2[0];
                            int[] iArr2 = (int[]) objArr2[1];
                            this.mHashes = iArr2;
                            if (iArr2 != null) {
                                objArr2[1] = null;
                                objArr2[0] = null;
                                sBaseCacheSize--;
                                return;
                            }
                        } catch (ClassCastException unused2) {
                        }
                        System.out.println("ArraySet Found corrupt ArraySet cache: [0]=" + objArr2[0] + " [1]=" + objArr2[1]);
                        sBaseCache = null;
                        sBaseCacheSize = 0;
                    }
                } finally {
                }
            }
        }
        this.mHashes = new int[i2];
        this.f2497a = new Object[i2];
    }

    private int binarySearch(int i2) {
        try {
            return ContainerHelpers.a(this.mHashes, this.f2498b, i2);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i2) {
        if (iArr.length == 8) {
            synchronized (sTwiceBaseCacheLock) {
                try {
                    if (sTwiceBaseCacheSize < 10) {
                        objArr[0] = sTwiceBaseCache;
                        objArr[1] = iArr;
                        for (int i3 = i2 - 1; i3 >= 2; i3--) {
                            objArr[i3] = null;
                        }
                        sTwiceBaseCache = objArr;
                        sTwiceBaseCacheSize++;
                    }
                } finally {
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (sBaseCacheLock) {
                try {
                    if (sBaseCacheSize < 10) {
                        objArr[0] = sBaseCache;
                        objArr[1] = iArr;
                        for (int i4 = i2 - 1; i4 >= 2; i4--) {
                            objArr[i4] = null;
                        }
                        sBaseCache = objArr;
                        sBaseCacheSize++;
                    }
                } finally {
                }
            }
        }
    }

    private int indexOf(Object obj, int i2) {
        int i3 = this.f2498b;
        if (i3 == 0) {
            return -1;
        }
        int iBinarySearch = binarySearch(i2);
        if (iBinarySearch < 0 || obj.equals(this.f2497a[iBinarySearch])) {
            return iBinarySearch;
        }
        int i4 = iBinarySearch + 1;
        while (i4 < i3 && this.mHashes[i4] == i2) {
            if (obj.equals(this.f2497a[i4])) {
                return i4;
            }
            i4++;
        }
        for (int i5 = iBinarySearch - 1; i5 >= 0 && this.mHashes[i5] == i2; i5--) {
            if (obj.equals(this.f2497a[i5])) {
                return i5;
            }
        }
        return ~i4;
    }

    private int indexOfNull() {
        int i2 = this.f2498b;
        if (i2 == 0) {
            return -1;
        }
        int iBinarySearch = binarySearch(0);
        if (iBinarySearch < 0 || this.f2497a[iBinarySearch] == null) {
            return iBinarySearch;
        }
        int i3 = iBinarySearch + 1;
        while (i3 < i2 && this.mHashes[i3] == 0) {
            if (this.f2497a[i3] == null) {
                return i3;
            }
            i3++;
        }
        for (int i4 = iBinarySearch - 1; i4 >= 0 && this.mHashes[i4] == 0; i4--) {
            if (this.f2497a[i4] == null) {
                return i4;
            }
        }
        return ~i3;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(@Nullable E e2) {
        int i2;
        int iIndexOf;
        int i3 = this.f2498b;
        if (e2 == null) {
            iIndexOf = indexOfNull();
            i2 = 0;
        } else {
            int iHashCode = e2.hashCode();
            i2 = iHashCode;
            iIndexOf = indexOf(e2, iHashCode);
        }
        if (iIndexOf >= 0) {
            return false;
        }
        int i4 = ~iIndexOf;
        int[] iArr = this.mHashes;
        if (i3 >= iArr.length) {
            int i5 = 8;
            if (i3 >= 8) {
                i5 = (i3 >> 1) + i3;
            } else if (i3 < 4) {
                i5 = 4;
            }
            Object[] objArr = this.f2497a;
            allocArrays(i5);
            if (i3 != this.f2498b) {
                throw new ConcurrentModificationException();
            }
            int[] iArr2 = this.mHashes;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr, 0, this.f2497a, 0, objArr.length);
            }
            freeArrays(iArr, objArr, i3);
        }
        if (i4 < i3) {
            int[] iArr3 = this.mHashes;
            int i6 = i4 + 1;
            int i7 = i3 - i4;
            System.arraycopy(iArr3, i4, iArr3, i6, i7);
            Object[] objArr2 = this.f2497a;
            System.arraycopy(objArr2, i4, objArr2, i6, i7);
        }
        int i8 = this.f2498b;
        if (i3 == i8) {
            int[] iArr4 = this.mHashes;
            if (i4 < iArr4.length) {
                iArr4[i4] = i2;
                this.f2497a[i4] = e2;
                this.f2498b = i8 + 1;
                return true;
            }
        }
        throw new ConcurrentModificationException();
    }

    public void addAll(@NonNull ArraySet<? extends E> arraySet) {
        int i2 = arraySet.f2498b;
        ensureCapacity(this.f2498b + i2);
        if (this.f2498b != 0) {
            for (int i3 = 0; i3 < i2; i3++) {
                add(arraySet.valueAt(i3));
            }
        } else if (i2 > 0) {
            System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, i2);
            System.arraycopy(arraySet.f2497a, 0, this.f2497a, 0, i2);
            if (this.f2498b != 0) {
                throw new ConcurrentModificationException();
            }
            this.f2498b = i2;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public void clear() {
        int i2 = this.f2498b;
        if (i2 != 0) {
            int[] iArr = this.mHashes;
            Object[] objArr = this.f2497a;
            this.mHashes = ContainerHelpers.f2500a;
            this.f2497a = ContainerHelpers.f2502c;
            this.f2498b = 0;
            freeArrays(iArr, objArr, i2);
        }
        if (this.f2498b != 0) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean contains(@Nullable Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public void ensureCapacity(int i2) {
        int i3 = this.f2498b;
        int[] iArr = this.mHashes;
        if (iArr.length < i2) {
            Object[] objArr = this.f2497a;
            allocArrays(i2);
            int i4 = this.f2498b;
            if (i4 > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, i4);
                System.arraycopy(objArr, 0, this.f2497a, 0, this.f2498b);
            }
            freeArrays(iArr, objArr, this.f2498b);
        }
        if (this.f2498b != i3) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            if (size() != set.size()) {
                return false;
            }
            for (int i2 = 0; i2 < this.f2498b; i2++) {
                try {
                    if (!set.contains(valueAt(i2))) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int[] iArr = this.mHashes;
        int i2 = this.f2498b;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += iArr[i4];
        }
        return i3;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.f2498b <= 0;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    @NonNull
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    @Override // java.util.Collection, java.util.Set
    public boolean remove(@Nullable Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return false;
        }
        removeAt(iIndexOf);
        return true;
    }

    public boolean removeAll(@NonNull ArraySet<? extends E> arraySet) {
        int i2 = arraySet.f2498b;
        int i3 = this.f2498b;
        for (int i4 = 0; i4 < i2; i4++) {
            remove(arraySet.valueAt(i4));
        }
        return i3 != this.f2498b;
    }

    public E removeAt(int i2) {
        int i3 = this.f2498b;
        Object[] objArr = this.f2497a;
        E e2 = (E) objArr[i2];
        if (i3 <= 1) {
            clear();
        } else {
            int i4 = i3 - 1;
            int[] iArr = this.mHashes;
            if (iArr.length <= 8 || i3 >= iArr.length / 3) {
                if (i2 < i4) {
                    int i5 = i2 + 1;
                    int i6 = i4 - i2;
                    System.arraycopy(iArr, i5, iArr, i2, i6);
                    Object[] objArr2 = this.f2497a;
                    System.arraycopy(objArr2, i5, objArr2, i2, i6);
                }
                this.f2497a[i4] = null;
            } else {
                allocArrays(i3 > 8 ? i3 + (i3 >> 1) : 8);
                if (i2 > 0) {
                    System.arraycopy(iArr, 0, this.mHashes, 0, i2);
                    System.arraycopy(objArr, 0, this.f2497a, 0, i2);
                }
                if (i2 < i4) {
                    int i7 = i2 + 1;
                    int i8 = i4 - i2;
                    System.arraycopy(iArr, i7, this.mHashes, i2, i8);
                    System.arraycopy(objArr, i7, this.f2497a, i2, i8);
                }
            }
            if (i3 != this.f2498b) {
                throw new ConcurrentModificationException();
            }
            this.f2498b = i4;
        }
        return e2;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(@NonNull Collection<?> collection) {
        boolean z2 = false;
        for (int i2 = this.f2498b - 1; i2 >= 0; i2--) {
            if (!collection.contains(this.f2497a[i2])) {
                removeAt(i2);
                z2 = true;
            }
        }
        return z2;
    }

    @Override // java.util.Collection, java.util.Set
    public int size() {
        return this.f2498b;
    }

    @Override // java.util.Collection, java.util.Set
    @NonNull
    public Object[] toArray() {
        int i2 = this.f2498b;
        Object[] objArr = new Object[i2];
        System.arraycopy(this.f2497a, 0, objArr, 0, i2);
        return objArr;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f2498b * 14);
        sb.append('{');
        for (int i2 = 0; i2 < this.f2498b; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            E eValueAt = valueAt(i2);
            if (eValueAt != this) {
                sb.append(eValueAt);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public E valueAt(int i2) {
        return (E) this.f2497a[i2];
    }

    public ArraySet(int i2) {
        if (i2 == 0) {
            this.mHashes = ContainerHelpers.f2500a;
            this.f2497a = ContainerHelpers.f2502c;
        } else {
            allocArrays(i2);
        }
        this.f2498b = 0;
    }

    @Override // java.util.Collection, java.util.Set
    @NonNull
    public <T> T[] toArray(@NonNull T[] tArr) {
        if (tArr.length < this.f2498b) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.f2498b));
        }
        System.arraycopy(this.f2497a, 0, tArr, 0, this.f2498b);
        int length = tArr.length;
        int i2 = this.f2498b;
        if (length > i2) {
            tArr[i2] = null;
        }
        return tArr;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(@Nullable ArraySet<E> arraySet) {
        this();
        if (arraySet != 0) {
            addAll((ArraySet) arraySet);
        }
    }

    public int indexOf(@Nullable Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj, obj.hashCode());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(@Nullable Collection<E> collection) {
        this();
        if (collection != 0) {
            addAll(collection);
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean addAll(@NonNull Collection<? extends E> collection) {
        ensureCapacity(this.f2498b + collection.size());
        Iterator<? extends E> it = collection.iterator();
        boolean zAdd = false;
        while (it.hasNext()) {
            zAdd |= add(it.next());
        }
        return zAdd;
    }

    public ArraySet(@Nullable E[] eArr) {
        this();
        if (eArr != null) {
            for (E e2 : eArr) {
                add(e2);
            }
        }
    }
}
