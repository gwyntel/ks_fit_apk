package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.Enum;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true)
@J2ktIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class EnumMultiset<E extends Enum<E>> extends AbstractMultiset<E> implements Serializable {

    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient int[] counts;
    private transient int distinctElements;
    private transient E[] enumConstants;
    private transient long size;
    private transient Class<E> type;

    abstract class Itr<T> implements Iterator<T> {

        /* renamed from: a, reason: collision with root package name */
        int f13955a = 0;

        /* renamed from: b, reason: collision with root package name */
        int f13956b = -1;

        Itr() {
        }

        abstract Object a(int i2);

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (this.f13955a < EnumMultiset.this.enumConstants.length) {
                int[] iArr = EnumMultiset.this.counts;
                int i2 = this.f13955a;
                if (iArr[i2] > 0) {
                    return true;
                }
                this.f13955a = i2 + 1;
            }
            return false;
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T t2 = (T) a(this.f13955a);
            int i2 = this.f13955a;
            this.f13956b = i2;
            this.f13955a = i2 + 1;
            return t2;
        }

        @Override // java.util.Iterator
        public void remove() {
            CollectPreconditions.e(this.f13956b >= 0);
            if (EnumMultiset.this.counts[this.f13956b] > 0) {
                EnumMultiset.access$210(EnumMultiset.this);
                EnumMultiset.access$322(EnumMultiset.this, r0.counts[this.f13956b]);
                EnumMultiset.this.counts[this.f13956b] = 0;
            }
            this.f13956b = -1;
        }
    }

    private EnumMultiset(Class<E> cls) {
        this.type = cls;
        Preconditions.checkArgument(cls.isEnum());
        E[] enumConstants = cls.getEnumConstants();
        this.enumConstants = enumConstants;
        this.counts = new int[enumConstants.length];
    }

    static /* synthetic */ int access$210(EnumMultiset enumMultiset) {
        int i2 = enumMultiset.distinctElements;
        enumMultiset.distinctElements = i2 - 1;
        return i2;
    }

    static /* synthetic */ long access$322(EnumMultiset enumMultiset, long j2) {
        long j3 = enumMultiset.size - j2;
        enumMultiset.size = j3;
        return j3;
    }

    private void checkIsE(Object obj) {
        Preconditions.checkNotNull(obj);
        if (isActuallyE(obj)) {
            return;
        }
        throw new ClassCastException("Expected an " + this.type + " but got " + obj);
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Class<E> cls) {
        return new EnumMultiset<>(cls);
    }

    private boolean isActuallyE(@CheckForNull Object obj) {
        if (!(obj instanceof Enum)) {
            return false;
        }
        Enum r5 = (Enum) obj;
        int iOrdinal = r5.ordinal();
        E[] eArr = this.enumConstants;
        return iOrdinal < eArr.length && eArr[iOrdinal] == r5;
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        Object object = objectInputStream.readObject();
        Objects.requireNonNull(object);
        Class<E> cls = (Class) object;
        this.type = cls;
        E[] enumConstants = cls.getEnumConstants();
        this.enumConstants = enumConstants;
        this.counts = new int[enumConstants.length];
        Serialization.f(this, objectInputStream);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.type);
        Serialization.k(this, objectOutputStream);
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        Arrays.fill(this.counts, 0);
        this.size = 0L;
        this.distinctElements = 0;
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ boolean contains(@CheckForNull Object obj) {
        return super.contains(obj);
    }

    @Override // com.google.common.collect.Multiset
    public int count(@CheckForNull Object obj) {
        if (obj == null || !isActuallyE(obj)) {
            return 0;
        }
        return this.counts[((Enum) obj).ordinal()];
    }

    @Override // com.google.common.collect.AbstractMultiset
    int distinctElements() {
        return this.distinctElements;
    }

    @Override // com.google.common.collect.AbstractMultiset
    Iterator<E> elementIterator() {
        return new EnumMultiset<E>.Itr<E>() { // from class: com.google.common.collect.EnumMultiset.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.EnumMultiset.Itr
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public Enum a(int i2) {
                return EnumMultiset.this.enumConstants[i2];
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ Set elementSet() {
        return super.elementSet();
    }

    @Override // com.google.common.collect.AbstractMultiset
    Iterator<Multiset.Entry<E>> entryIterator() {
        return new EnumMultiset<E>.Itr<Multiset.Entry<E>>() { // from class: com.google.common.collect.EnumMultiset.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.EnumMultiset.Itr
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public Multiset.Entry a(final int i2) {
                return new Multisets.AbstractEntry<E>(this) { // from class: com.google.common.collect.EnumMultiset.2.1

                    /* renamed from: b, reason: collision with root package name */
                    final /* synthetic */ AnonymousClass2 f13954b;

                    {
                        this.f13954b = this;
                    }

                    @Override // com.google.common.collect.Multiset.Entry
                    public int getCount() {
                        return EnumMultiset.this.counts[i2];
                    }

                    @Override // com.google.common.collect.Multiset.Entry
                    public E getElement() {
                        return (E) EnumMultiset.this.enumConstants[i2];
                    }
                };
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    public Iterator<E> iterator() {
        return Multisets.f(this);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int remove(@CheckForNull Object obj, int i2) {
        if (obj == null || !isActuallyE(obj)) {
            return 0;
        }
        Enum r1 = (Enum) obj;
        CollectPreconditions.b(i2, "occurrences");
        if (i2 == 0) {
            return count(obj);
        }
        int iOrdinal = r1.ordinal();
        int[] iArr = this.counts;
        int i3 = iArr[iOrdinal];
        if (i3 == 0) {
            return 0;
        }
        if (i3 <= i2) {
            iArr[iOrdinal] = 0;
            this.distinctElements--;
            this.size -= i3;
        } else {
            iArr[iOrdinal] = i3 - i2;
            this.size -= i2;
        }
        return i3;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        return Ints.saturatedCast(this.size);
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable) {
        Iterator<E> it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "EnumMultiset constructor passed empty Iterable");
        EnumMultiset<E> enumMultiset = new EnumMultiset<>(it.next().getDeclaringClass());
        Iterables.addAll(enumMultiset, iterable);
        return enumMultiset;
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int add(E e2, int i2) {
        checkIsE(e2);
        CollectPreconditions.b(i2, "occurrences");
        if (i2 == 0) {
            return count(e2);
        }
        int iOrdinal = e2.ordinal();
        int i3 = this.counts[iOrdinal];
        long j2 = i2;
        long j3 = i3 + j2;
        Preconditions.checkArgument(j3 <= 2147483647L, "too many occurrences: %s", j3);
        this.counts[iOrdinal] = (int) j3;
        if (i3 == 0) {
            this.distinctElements++;
        }
        this.size += j2;
        return i3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean setCount(@ParametricNullness Object obj, int i2, int i3) {
        return super.setCount(obj, i2, i3);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int setCount(E e2, int i2) {
        checkIsE(e2);
        CollectPreconditions.b(i2, "count");
        int iOrdinal = e2.ordinal();
        int[] iArr = this.counts;
        int i3 = iArr[iOrdinal];
        iArr[iOrdinal] = i2;
        this.size += i2 - i3;
        if (i3 == 0 && i2 > 0) {
            this.distinctElements++;
        } else if (i3 > 0 && i2 == 0) {
            this.distinctElements--;
        }
        return i3;
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable, Class<E> cls) {
        EnumMultiset<E> enumMultisetCreate = create(cls);
        Iterables.addAll(enumMultisetCreate, iterable);
        return enumMultisetCreate;
    }
}
