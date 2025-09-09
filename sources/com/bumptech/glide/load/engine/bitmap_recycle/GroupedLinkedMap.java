package com.bumptech.glide.load.engine.bitmap_recycle;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.bitmap_recycle.Poolable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
class GroupedLinkedMap<K extends Poolable, V> {
    private final LinkedEntry<K, V> head = new LinkedEntry<>();
    private final Map<K, LinkedEntry<K, V>> keyToEntry = new HashMap();

    private static class LinkedEntry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f12340a;

        /* renamed from: b, reason: collision with root package name */
        LinkedEntry f12341b;

        /* renamed from: c, reason: collision with root package name */
        LinkedEntry f12342c;
        private List<V> values;

        LinkedEntry() {
            this(null);
        }

        public void add(V v2) {
            if (this.values == null) {
                this.values = new ArrayList();
            }
            this.values.add(v2);
        }

        @Nullable
        public V removeLast() {
            int size = size();
            if (size > 0) {
                return this.values.remove(size - 1);
            }
            return null;
        }

        public int size() {
            List<V> list = this.values;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        LinkedEntry(Object obj) {
            this.f12342c = this;
            this.f12341b = this;
            this.f12340a = obj;
        }
    }

    GroupedLinkedMap() {
    }

    private void makeHead(LinkedEntry<K, V> linkedEntry) {
        removeEntry(linkedEntry);
        LinkedEntry<K, V> linkedEntry2 = this.head;
        linkedEntry.f12342c = linkedEntry2;
        linkedEntry.f12341b = linkedEntry2.f12341b;
        updateEntry(linkedEntry);
    }

    private void makeTail(LinkedEntry<K, V> linkedEntry) {
        removeEntry(linkedEntry);
        LinkedEntry<K, V> linkedEntry2 = this.head;
        linkedEntry.f12342c = linkedEntry2.f12342c;
        linkedEntry.f12341b = linkedEntry2;
        updateEntry(linkedEntry);
    }

    private static <K, V> void removeEntry(LinkedEntry<K, V> linkedEntry) {
        LinkedEntry linkedEntry2 = linkedEntry.f12342c;
        linkedEntry2.f12341b = linkedEntry.f12341b;
        linkedEntry.f12341b.f12342c = linkedEntry2;
    }

    private static <K, V> void updateEntry(LinkedEntry<K, V> linkedEntry) {
        linkedEntry.f12341b.f12342c = linkedEntry;
        linkedEntry.f12342c.f12341b = linkedEntry;
    }

    @Nullable
    public V get(K k2) {
        LinkedEntry<K, V> linkedEntry = this.keyToEntry.get(k2);
        if (linkedEntry == null) {
            linkedEntry = new LinkedEntry<>(k2);
            this.keyToEntry.put(k2, linkedEntry);
        } else {
            k2.offer();
        }
        makeHead(linkedEntry);
        return linkedEntry.removeLast();
    }

    public void put(K k2, V v2) {
        LinkedEntry<K, V> linkedEntry = this.keyToEntry.get(k2);
        if (linkedEntry == null) {
            linkedEntry = new LinkedEntry<>(k2);
            makeTail(linkedEntry);
            this.keyToEntry.put(k2, linkedEntry);
        } else {
            k2.offer();
        }
        linkedEntry.add(v2);
    }

    @Nullable
    public V removeLast() {
        for (LinkedEntry linkedEntry = this.head.f12342c; !linkedEntry.equals(this.head); linkedEntry = linkedEntry.f12342c) {
            V v2 = (V) linkedEntry.removeLast();
            if (v2 != null) {
                return v2;
            }
            removeEntry(linkedEntry);
            this.keyToEntry.remove(linkedEntry.f12340a);
            ((Poolable) linkedEntry.f12340a).offer();
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GroupedLinkedMap( ");
        LinkedEntry linkedEntry = this.head.f12341b;
        boolean z2 = false;
        while (!linkedEntry.equals(this.head)) {
            sb.append('{');
            sb.append(linkedEntry.f12340a);
            sb.append(':');
            sb.append(linkedEntry.size());
            sb.append("}, ");
            linkedEntry = linkedEntry.f12341b;
            z2 = true;
        }
        if (z2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(" )");
        return sb.toString();
    }
}
