package androidx.arch.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class SafeIterableMap<K, V> implements Iterable<Map.Entry<K, V>> {

    /* renamed from: a, reason: collision with root package name */
    Entry f2364a;
    private Entry<K, V> mEnd;
    private final WeakHashMap<SupportRemove<K, V>, Boolean> mIterators = new WeakHashMap<>();
    private int mSize = 0;

    static class AscendingIterator<K, V> extends ListIterator<K, V> {
        AscendingIterator(Entry entry, Entry entry2) {
            super(entry, entry2);
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        Entry a(Entry entry) {
            return entry.f2368d;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        Entry b(Entry entry) {
            return entry.f2367c;
        }
    }

    private static class DescendingIterator<K, V> extends ListIterator<K, V> {
        DescendingIterator(Entry entry, Entry entry2) {
            super(entry, entry2);
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        Entry a(Entry entry) {
            return entry.f2367c;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        Entry b(Entry entry) {
            return entry.f2368d;
        }
    }

    static class Entry<K, V> implements Map.Entry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f2365a;

        /* renamed from: b, reason: collision with root package name */
        final Object f2366b;

        /* renamed from: c, reason: collision with root package name */
        Entry f2367c;

        /* renamed from: d, reason: collision with root package name */
        Entry f2368d;

        Entry(Object obj, Object obj2) {
            this.f2365a = obj;
            this.f2366b = obj2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.f2365a.equals(entry.f2365a) && this.f2366b.equals(entry.f2366b);
        }

        @Override // java.util.Map.Entry
        @NonNull
        public K getKey() {
            return (K) this.f2365a;
        }

        @Override // java.util.Map.Entry
        @NonNull
        public V getValue() {
            return (V) this.f2366b;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.f2365a.hashCode() ^ this.f2366b.hashCode();
        }

        @Override // java.util.Map.Entry
        public V setValue(V v2) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public String toString() {
            return this.f2365a + ContainerUtils.KEY_VALUE_DELIMITER + this.f2366b;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public class IteratorWithAdditions extends SupportRemove<K, V> implements Iterator<Map.Entry<K, V>> {
        private boolean mBeforeStart = true;
        private Entry<K, V> mCurrent;

        IteratorWithAdditions() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.mBeforeStart) {
                return SafeIterableMap.this.f2364a != null;
            }
            Entry<K, V> entry = this.mCurrent;
            return (entry == null || entry.f2367c == null) ? false : true;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.SupportRemove
        void supportRemove(Entry entry) {
            Entry<K, V> entry2 = this.mCurrent;
            if (entry == entry2) {
                Entry<K, V> entry3 = entry2.f2368d;
                this.mCurrent = entry3;
                this.mBeforeStart = entry3 == null;
            }
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            if (this.mBeforeStart) {
                this.mBeforeStart = false;
                this.mCurrent = SafeIterableMap.this.f2364a;
            } else {
                Entry<K, V> entry = this.mCurrent;
                this.mCurrent = entry != null ? entry.f2367c : null;
            }
            return this.mCurrent;
        }
    }

    private static abstract class ListIterator<K, V> extends SupportRemove<K, V> implements Iterator<Map.Entry<K, V>> {

        /* renamed from: a, reason: collision with root package name */
        Entry f2370a;

        /* renamed from: b, reason: collision with root package name */
        Entry f2371b;

        ListIterator(Entry entry, Entry entry2) {
            this.f2370a = entry2;
            this.f2371b = entry;
        }

        private Entry<K, V> nextNode() {
            Entry entry = this.f2371b;
            Entry entry2 = this.f2370a;
            if (entry == entry2 || entry2 == null) {
                return null;
            }
            return b(entry);
        }

        abstract Entry a(Entry entry);

        abstract Entry b(Entry entry);

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f2371b != null;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.SupportRemove
        public void supportRemove(@NonNull Entry<K, V> entry) {
            if (this.f2370a == entry && entry == this.f2371b) {
                this.f2371b = null;
                this.f2370a = null;
            }
            Entry<K, V> entry2 = this.f2370a;
            if (entry2 == entry) {
                this.f2370a = a(entry2);
            }
            if (this.f2371b == entry) {
                this.f2371b = nextNode();
            }
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            Entry entry = this.f2371b;
            this.f2371b = nextNode();
            return entry;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static abstract class SupportRemove<K, V> {
        abstract void supportRemove(Entry entry);
    }

    protected Entry a(Object obj) {
        Entry entry = this.f2364a;
        while (entry != null && !entry.f2365a.equals(obj)) {
            entry = entry.f2367c;
        }
        return entry;
    }

    Entry b(Object obj, Object obj2) {
        Entry<K, V> entry = new Entry<>(obj, obj2);
        this.mSize++;
        Entry<K, V> entry2 = this.mEnd;
        if (entry2 == null) {
            this.f2364a = entry;
            this.mEnd = entry;
            return entry;
        }
        entry2.f2367c = entry;
        entry.f2368d = entry2;
        this.mEnd = entry;
        return entry;
    }

    @NonNull
    public Iterator<Map.Entry<K, V>> descendingIterator() {
        DescendingIterator descendingIterator = new DescendingIterator(this.mEnd, this.f2364a);
        this.mIterators.put(descendingIterator, Boolean.FALSE);
        return descendingIterator;
    }

    @Nullable
    public Map.Entry<K, V> eldest() {
        return this.f2364a;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SafeIterableMap)) {
            return false;
        }
        SafeIterableMap safeIterableMap = (SafeIterableMap) obj;
        if (size() != safeIterableMap.size()) {
            return false;
        }
        Iterator<Map.Entry<K, V>> it = iterator();
        Iterator<Map.Entry<K, V>> it2 = safeIterableMap.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry<K, V> next = it.next();
            Map.Entry<K, V> next2 = it2.next();
            if ((next == null && next2 != null) || (next != null && !next.equals(next2))) {
                return false;
            }
        }
        return (it.hasNext() || it2.hasNext()) ? false : true;
    }

    public int hashCode() {
        Iterator<Map.Entry<K, V>> it = iterator();
        int iHashCode = 0;
        while (it.hasNext()) {
            iHashCode += it.next().hashCode();
        }
        return iHashCode;
    }

    @Override // java.lang.Iterable
    @NonNull
    public Iterator<Map.Entry<K, V>> iterator() {
        AscendingIterator ascendingIterator = new AscendingIterator(this.f2364a, this.mEnd);
        this.mIterators.put(ascendingIterator, Boolean.FALSE);
        return ascendingIterator;
    }

    @NonNull
    public SafeIterableMap<K, V>.IteratorWithAdditions iteratorWithAdditions() {
        SafeIterableMap<K, V>.IteratorWithAdditions iteratorWithAdditions = new IteratorWithAdditions();
        this.mIterators.put(iteratorWithAdditions, Boolean.FALSE);
        return iteratorWithAdditions;
    }

    @Nullable
    public Map.Entry<K, V> newest() {
        return this.mEnd;
    }

    public V putIfAbsent(@NonNull K k2, @NonNull V v2) {
        Entry entryA = a(k2);
        if (entryA != null) {
            return (V) entryA.f2366b;
        }
        b(k2, v2);
        return null;
    }

    public V remove(@NonNull K k2) {
        Entry entryA = a(k2);
        if (entryA == null) {
            return null;
        }
        this.mSize--;
        if (!this.mIterators.isEmpty()) {
            Iterator<SupportRemove<K, V>> it = this.mIterators.keySet().iterator();
            while (it.hasNext()) {
                it.next().supportRemove(entryA);
            }
        }
        Entry<K, V> entry = entryA.f2368d;
        if (entry != null) {
            entry.f2367c = entryA.f2367c;
        } else {
            this.f2364a = entryA.f2367c;
        }
        Entry entry2 = entryA.f2367c;
        if (entry2 != null) {
            entry2.f2368d = entry;
        } else {
            this.mEnd = entry;
        }
        entryA.f2367c = null;
        entryA.f2368d = null;
        return (V) entryA.f2366b;
    }

    public int size() {
        return this.mSize;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
