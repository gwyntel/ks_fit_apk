package top.zibin.luban.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.zibin.luban.io.PoolAble;

/* loaded from: classes5.dex */
class GroupedLinkedMap<K extends PoolAble, V> {
    private final LinkedEntry<K, V> head = new LinkedEntry<>();
    private final Map<K, LinkedEntry<K, V>> keyToEntry = new HashMap();

    private static class LinkedEntry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f26853a;

        /* renamed from: b, reason: collision with root package name */
        LinkedEntry f26854b;

        /* renamed from: c, reason: collision with root package name */
        LinkedEntry f26855c;
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
            this.f26855c = this;
            this.f26854b = this;
            this.f26853a = obj;
        }
    }

    GroupedLinkedMap() {
    }

    private void makeHead(LinkedEntry<K, V> linkedEntry) {
        removeEntry(linkedEntry);
        LinkedEntry<K, V> linkedEntry2 = this.head;
        linkedEntry.f26855c = linkedEntry2;
        linkedEntry.f26854b = linkedEntry2.f26854b;
        updateEntry(linkedEntry);
    }

    private void makeTail(LinkedEntry<K, V> linkedEntry) {
        removeEntry(linkedEntry);
        LinkedEntry<K, V> linkedEntry2 = this.head;
        linkedEntry.f26855c = linkedEntry2.f26855c;
        linkedEntry.f26854b = linkedEntry2;
        updateEntry(linkedEntry);
    }

    private static <K, V> void removeEntry(LinkedEntry<K, V> linkedEntry) {
        LinkedEntry linkedEntry2 = linkedEntry.f26855c;
        linkedEntry2.f26854b = linkedEntry.f26854b;
        linkedEntry.f26854b.f26855c = linkedEntry2;
    }

    private static <K, V> void updateEntry(LinkedEntry<K, V> linkedEntry) {
        linkedEntry.f26854b.f26855c = linkedEntry;
        linkedEntry.f26855c.f26854b = linkedEntry;
    }

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

    public V removeLast() {
        for (LinkedEntry linkedEntry = this.head.f26855c; !linkedEntry.equals(this.head); linkedEntry = linkedEntry.f26855c) {
            V v2 = (V) linkedEntry.removeLast();
            if (v2 != null) {
                return v2;
            }
            removeEntry(linkedEntry);
            this.keyToEntry.remove(linkedEntry.f26853a);
            ((PoolAble) linkedEntry.f26853a).offer();
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GroupedLinkedMap( ");
        LinkedEntry linkedEntry = this.head.f26854b;
        boolean z2 = false;
        while (!linkedEntry.equals(this.head)) {
            sb.append('{');
            sb.append(linkedEntry.f26853a);
            sb.append(':');
            sb.append(linkedEntry.size());
            sb.append("}, ");
            linkedEntry = linkedEntry.f26854b;
            z2 = true;
        }
        if (z2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(" )");
        return sb.toString();
    }
}
