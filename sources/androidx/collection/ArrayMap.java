package androidx.collection;

import androidx.annotation.NonNull;
import com.huawei.hms.framework.common.ContainerUtils;
import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes.dex */
public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {

    /* renamed from: h, reason: collision with root package name */
    EntrySet f2485h;

    /* renamed from: i, reason: collision with root package name */
    KeySet f2486i;

    /* renamed from: j, reason: collision with root package name */
    ValueCollection f2487j;

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new MapIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return ArrayMap.this.f2515c;
        }
    }

    final class KeyIterator extends IndexBasedArrayIterator<K> {
        KeyIterator() {
            super(ArrayMap.this.f2515c);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected Object a(int i2) {
            return ArrayMap.this.keyAt(i2);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected void b(int i2) {
            ArrayMap.this.removeAt(i2);
        }
    }

    final class MapIterator implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        int f2491a;

        /* renamed from: b, reason: collision with root package name */
        int f2492b = -1;

        /* renamed from: c, reason: collision with root package name */
        boolean f2493c;

        MapIterator() {
            this.f2491a = ArrayMap.this.f2515c - 1;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!this.f2493c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return ContainerHelpers.equal(entry.getKey(), ArrayMap.this.keyAt(this.f2492b)) && ContainerHelpers.equal(entry.getValue(), ArrayMap.this.valueAt(this.f2492b));
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.f2493c) {
                return ArrayMap.this.keyAt(this.f2492b);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.f2493c) {
                return ArrayMap.this.valueAt(this.f2492b);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f2492b < this.f2491a;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            if (!this.f2493c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            K kKeyAt = ArrayMap.this.keyAt(this.f2492b);
            V vValueAt = ArrayMap.this.valueAt(this.f2492b);
            return (kKeyAt == null ? 0 : kKeyAt.hashCode()) ^ (vValueAt != null ? vValueAt.hashCode() : 0);
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.f2493c) {
                throw new IllegalStateException();
            }
            ArrayMap.this.removeAt(this.f2492b);
            this.f2492b--;
            this.f2491a--;
            this.f2493c = false;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v2) {
            if (this.f2493c) {
                return ArrayMap.this.setValueAt(this.f2492b, v2);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public String toString() {
            return getKey() + ContainerUtils.KEY_VALUE_DELIMITER + getValue();
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.f2492b++;
            this.f2493c = true;
            return this;
        }
    }

    final class ValueIterator extends IndexBasedArrayIterator<V> {
        ValueIterator() {
            super(ArrayMap.this.f2515c);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected Object a(int i2) {
            return ArrayMap.this.valueAt(i2);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected void b(int i2) {
            ArrayMap.this.removeAt(i2);
        }
    }

    public ArrayMap() {
    }

    static boolean d(Set set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    Object[] e(Object[] objArr, int i2) {
        int i3 = this.f2515c;
        if (objArr.length < i3) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i3);
        }
        for (int i4 = 0; i4 < i3; i4++) {
            objArr[i4] = this.f2514b[(i4 << 1) + i2];
        }
        if (objArr.length > i3) {
            objArr[i3] = null;
        }
        return objArr;
    }

    @Override // java.util.Map
    @NonNull
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet = this.f2485h;
        if (entrySet != null) {
            return entrySet;
        }
        EntrySet entrySet2 = new EntrySet();
        this.f2485h = entrySet2;
        return entrySet2;
    }

    @Override // java.util.Map
    @NonNull
    public Set<K> keySet() {
        KeySet keySet = this.f2486i;
        if (keySet != null) {
            return keySet;
        }
        KeySet keySet2 = new KeySet();
        this.f2486i = keySet2;
        return keySet2;
    }

    @Override // java.util.Map
    public void putAll(@NonNull Map<? extends K, ? extends V> map) {
        ensureCapacity(this.f2515c + map.size());
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(@NonNull Collection<?> collection) {
        int i2 = this.f2515c;
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
        return i2 != this.f2515c;
    }

    public boolean retainAll(@NonNull Collection<?> collection) {
        int i2 = this.f2515c;
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            if (!collection.contains(keyAt(i3))) {
                removeAt(i3);
            }
        }
        return i2 != this.f2515c;
    }

    @Override // java.util.Map
    @NonNull
    public Collection<V> values() {
        ValueCollection valueCollection = this.f2487j;
        if (valueCollection != null) {
            return valueCollection;
        }
        ValueCollection valueCollection2 = new ValueCollection();
        this.f2487j = valueCollection2;
        return valueCollection2;
    }

    public ArrayMap(int i2) {
        super(i2);
    }

    final class KeySet implements Set<K> {
        KeySet() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(K k2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            ArrayMap.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return ArrayMap.this.containsKey(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return ArrayMap.this.containsAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return ArrayMap.d(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int i2 = ArrayMap.this.f2515c - 1; i2 >= 0; i2--) {
                K kKeyAt = ArrayMap.this.keyAt(i2);
                iHashCode += kKeyAt == null ? 0 : kKeyAt.hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            int iIndexOfKey = ArrayMap.this.indexOfKey(obj);
            if (iIndexOfKey < 0) {
                return false;
            }
            ArrayMap.this.removeAt(iIndexOfKey);
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            return ArrayMap.this.removeAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return ArrayMap.this.retainAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return ArrayMap.this.f2515c;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            int i2 = ArrayMap.this.f2515c;
            Object[] objArr = new Object[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                objArr[i3] = ArrayMap.this.keyAt(i3);
            }
            return objArr;
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) ArrayMap.this.e(tArr, 0);
        }
    }

    final class ValueCollection implements Collection<V> {
        ValueCollection() {
        }

        @Override // java.util.Collection
        public boolean add(V v2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public void clear() {
            ArrayMap.this.clear();
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return ArrayMap.this.c(obj) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            int iC = ArrayMap.this.c(obj);
            if (iC < 0) {
                return false;
            }
            ArrayMap.this.removeAt(iC);
            return true;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            int i2 = ArrayMap.this.f2515c;
            int i3 = 0;
            boolean z2 = false;
            while (i3 < i2) {
                if (collection.contains(ArrayMap.this.valueAt(i3))) {
                    ArrayMap.this.removeAt(i3);
                    i3--;
                    i2--;
                    z2 = true;
                }
                i3++;
            }
            return z2;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            int i2 = ArrayMap.this.f2515c;
            int i3 = 0;
            boolean z2 = false;
            while (i3 < i2) {
                if (!collection.contains(ArrayMap.this.valueAt(i3))) {
                    ArrayMap.this.removeAt(i3);
                    i3--;
                    i2--;
                    z2 = true;
                }
                i3++;
            }
            return z2;
        }

        @Override // java.util.Collection
        public int size() {
            return ArrayMap.this.f2515c;
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            int i2 = ArrayMap.this.f2515c;
            Object[] objArr = new Object[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                objArr[i3] = ArrayMap.this.valueAt(i3);
            }
            return objArr;
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) ArrayMap.this.e(tArr, 1);
        }
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap);
    }
}
