package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true, serializable = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public class LinkedListMultimap<K, V> extends AbstractMultimap<K, V> implements ListMultimap<K, V>, Serializable {

    @J2ktIncompatible
    @GwtIncompatible
    private static final long serialVersionUID = 0;

    @CheckForNull
    private transient Node<K, V> head;
    private transient Map<K, KeyList<K, V>> keyToKeyList;
    private transient int modCount;
    private transient int size;

    @CheckForNull
    private transient Node<K, V> tail;

    private class DistinctKeyIterator implements Iterator<K> {

        /* renamed from: a, reason: collision with root package name */
        final Set f14100a;

        /* renamed from: b, reason: collision with root package name */
        Node f14101b;

        /* renamed from: c, reason: collision with root package name */
        Node f14102c;

        /* renamed from: d, reason: collision with root package name */
        int f14103d;

        private DistinctKeyIterator() {
            this.f14100a = Sets.newHashSetWithExpectedSize(LinkedListMultimap.this.keySet().size());
            this.f14101b = LinkedListMultimap.this.head;
            this.f14103d = LinkedListMultimap.this.modCount;
        }

        private void checkForConcurrentModification() {
            if (LinkedListMultimap.this.modCount != this.f14103d) {
                throw new ConcurrentModificationException();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            checkForConcurrentModification();
            return this.f14101b != null;
        }

        @Override // java.util.Iterator
        @ParametricNullness
        public K next() {
            Node node;
            checkForConcurrentModification();
            Node node2 = this.f14101b;
            if (node2 == null) {
                throw new NoSuchElementException();
            }
            this.f14102c = node2;
            this.f14100a.add(node2.f14108a);
            do {
                node = this.f14101b.f14110c;
                this.f14101b = node;
                if (node == null) {
                    break;
                }
            } while (!this.f14100a.add(node.f14108a));
            return (K) this.f14102c.f14108a;
        }

        @Override // java.util.Iterator
        public void remove() {
            checkForConcurrentModification();
            Preconditions.checkState(this.f14102c != null, "no calls to next() since the last call to remove()");
            LinkedListMultimap.this.removeAllNodes(this.f14102c.f14108a);
            this.f14102c = null;
            this.f14103d = LinkedListMultimap.this.modCount;
        }
    }

    private static class KeyList<K, V> {

        /* renamed from: a, reason: collision with root package name */
        Node f14105a;

        /* renamed from: b, reason: collision with root package name */
        Node f14106b;

        /* renamed from: c, reason: collision with root package name */
        int f14107c;

        KeyList(Node node) {
            this.f14105a = node;
            this.f14106b = node;
            node.f14113f = null;
            node.f14112e = null;
            this.f14107c = 1;
        }
    }

    static final class Node<K, V> extends AbstractMapEntry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f14108a;

        /* renamed from: b, reason: collision with root package name */
        Object f14109b;

        /* renamed from: c, reason: collision with root package name */
        Node f14110c;

        /* renamed from: d, reason: collision with root package name */
        Node f14111d;

        /* renamed from: e, reason: collision with root package name */
        Node f14112e;

        /* renamed from: f, reason: collision with root package name */
        Node f14113f;

        Node(Object obj, Object obj2) {
            this.f14108a = obj;
            this.f14109b = obj2;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K getKey() {
            return (K) this.f14108a;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V getValue() {
            return (V) this.f14109b;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V setValue(@ParametricNullness V v2) {
            V v3 = (V) this.f14109b;
            this.f14109b = v2;
            return v3;
        }
    }

    private class NodeIterator implements ListIterator<Map.Entry<K, V>> {

        /* renamed from: a, reason: collision with root package name */
        int f14114a;

        /* renamed from: b, reason: collision with root package name */
        Node f14115b;

        /* renamed from: c, reason: collision with root package name */
        Node f14116c;

        /* renamed from: d, reason: collision with root package name */
        Node f14117d;

        /* renamed from: e, reason: collision with root package name */
        int f14118e;

        NodeIterator(int i2) {
            this.f14118e = LinkedListMultimap.this.modCount;
            int size = LinkedListMultimap.this.size();
            Preconditions.checkPositionIndex(i2, size);
            if (i2 < size / 2) {
                this.f14115b = LinkedListMultimap.this.head;
                while (true) {
                    int i3 = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    }
                    next();
                    i2 = i3;
                }
            } else {
                this.f14117d = LinkedListMultimap.this.tail;
                this.f14114a = size;
                while (true) {
                    int i4 = i2 + 1;
                    if (i2 >= size) {
                        break;
                    }
                    previous();
                    i2 = i4;
                }
            }
            this.f14116c = null;
        }

        private void checkForConcurrentModification() {
            if (LinkedListMultimap.this.modCount != this.f14118e) {
                throw new ConcurrentModificationException();
            }
        }

        void a(Object obj) {
            Preconditions.checkState(this.f14116c != null);
            this.f14116c.f14109b = obj;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            checkForConcurrentModification();
            return this.f14115b != null;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            checkForConcurrentModification();
            return this.f14117d != null;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.f14114a;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.f14114a - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            checkForConcurrentModification();
            Preconditions.checkState(this.f14116c != null, "no calls to next() since the last call to remove()");
            Node node = this.f14116c;
            if (node != this.f14115b) {
                this.f14117d = node.f14111d;
                this.f14114a--;
            } else {
                this.f14115b = node.f14110c;
            }
            LinkedListMultimap.this.removeNode(node);
            this.f14116c = null;
            this.f14118e = LinkedListMultimap.this.modCount;
        }

        @Override // java.util.ListIterator
        public void add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        @CanIgnoreReturnValue
        public Node<K, V> next() {
            checkForConcurrentModification();
            Node<K, V> node = this.f14115b;
            if (node == null) {
                throw new NoSuchElementException();
            }
            this.f14116c = node;
            this.f14117d = node;
            this.f14115b = node.f14110c;
            this.f14114a++;
            return node;
        }

        @Override // java.util.ListIterator
        @CanIgnoreReturnValue
        public Node<K, V> previous() {
            checkForConcurrentModification();
            Node<K, V> node = this.f14117d;
            if (node == null) {
                throw new NoSuchElementException();
            }
            this.f14116c = node;
            this.f14115b = node;
            this.f14117d = node.f14111d;
            this.f14114a--;
            return node;
        }

        @Override // java.util.ListIterator
        public void set(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }
    }

    LinkedListMultimap() {
        this(12);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CanIgnoreReturnValue
    public Node<K, V> addNode(@ParametricNullness K k2, @ParametricNullness V v2, @CheckForNull Node<K, V> node) {
        Node<K, V> node2 = new Node<>(k2, v2);
        if (this.head == null) {
            this.tail = node2;
            this.head = node2;
            this.keyToKeyList.put(k2, new KeyList<>(node2));
            this.modCount++;
        } else if (node == null) {
            Node<K, V> node3 = this.tail;
            Objects.requireNonNull(node3);
            node3.f14110c = node2;
            node2.f14111d = this.tail;
            this.tail = node2;
            KeyList<K, V> keyList = this.keyToKeyList.get(k2);
            if (keyList == null) {
                this.keyToKeyList.put(k2, new KeyList<>(node2));
                this.modCount++;
            } else {
                keyList.f14107c++;
                Node node4 = keyList.f14106b;
                node4.f14112e = node2;
                node2.f14113f = node4;
                keyList.f14106b = node2;
            }
        } else {
            KeyList<K, V> keyList2 = this.keyToKeyList.get(k2);
            Objects.requireNonNull(keyList2);
            keyList2.f14107c++;
            node2.f14111d = node.f14111d;
            node2.f14113f = node.f14113f;
            node2.f14110c = node;
            node2.f14112e = node;
            Node node5 = node.f14113f;
            if (node5 == null) {
                keyList2.f14105a = node2;
            } else {
                node5.f14112e = node2;
            }
            Node node6 = node.f14111d;
            if (node6 == null) {
                this.head = node2;
            } else {
                node6.f14110c = node2;
            }
            node.f14111d = node2;
            node.f14113f = node2;
        }
        this.size++;
        return node2;
    }

    public static <K, V> LinkedListMultimap<K, V> create() {
        return new LinkedListMultimap<>();
    }

    private List<V> getCopy(@ParametricNullness K k2) {
        return Collections.unmodifiableList(Lists.newArrayList(new ValueForKeyIterator(k2)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @J2ktIncompatible
    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.keyToKeyList = CompactLinkedHashMap.create();
        int i2 = objectInputStream.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAllNodes(@ParametricNullness K k2) {
        Iterators.b(new ValueForKeyIterator(k2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeNode(Node<K, V> node) {
        Node<K, V> node2 = node.f14111d;
        if (node2 != null) {
            node2.f14110c = node.f14110c;
        } else {
            this.head = node.f14110c;
        }
        Node node3 = node.f14110c;
        if (node3 != null) {
            node3.f14111d = node2;
        } else {
            this.tail = node2;
        }
        if (node.f14113f == null && node.f14112e == null) {
            KeyList<K, V> keyListRemove = this.keyToKeyList.remove(node.f14108a);
            Objects.requireNonNull(keyListRemove);
            keyListRemove.f14107c = 0;
            this.modCount++;
        } else {
            KeyList<K, V> keyList = this.keyToKeyList.get(node.f14108a);
            Objects.requireNonNull(keyList);
            keyList.f14107c--;
            Node node4 = node.f14113f;
            if (node4 == null) {
                Node node5 = node.f14112e;
                Objects.requireNonNull(node5);
                keyList.f14105a = node5;
            } else {
                node4.f14112e = node.f14112e;
            }
            Node node6 = node.f14112e;
            if (node6 == null) {
                Node node7 = node.f14113f;
                Objects.requireNonNull(node7);
                keyList.f14106b = node7;
            } else {
                node6.f14113f = node.f14113f;
            }
        }
        this.size--;
    }

    @J2ktIncompatible
    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        for (Map.Entry<K, V> entry : entries()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        this.head = null;
        this.tail = null;
        this.keyToKeyList.clear();
        this.size = 0;
        this.modCount++;
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean containsEntry(@CheckForNull Object obj, @CheckForNull Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@CheckForNull Object obj) {
        return this.keyToKeyList.containsKey(obj);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public boolean containsValue(@CheckForNull Object obj) {
        return values().contains(obj);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Map<K, Collection<V>> createAsMap() {
        return new Multimaps.AsMap(this);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Set<K> createKeySet() {
        return new Sets.ImprovedAbstractSet<K>() { // from class: com.google.common.collect.LinkedListMultimap.1KeySetImpl
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                return LinkedListMultimap.this.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<K> iterator() {
                return new DistinctKeyIterator();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@CheckForNull Object obj) {
                return !LinkedListMultimap.this.removeAll(obj).isEmpty();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return LinkedListMultimap.this.keyToKeyList.size();
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultimap
    Multiset<K> createKeys() {
        return new Multimaps.Keys(this);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ boolean equals(@CheckForNull Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ Collection get(@ParametricNullness Object obj) {
        return get((LinkedListMultimap<K, V>) obj);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean put(@ParametricNullness K k2, @ParametricNullness V v2) {
        addNode(k2, v2, null);
        return true;
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
        return super.remove(obj, obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Collection replaceValues(@ParametricNullness Object obj, Iterable iterable) {
        return replaceValues((LinkedListMultimap<K, V>) obj, iterable);
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        return this.size;
    }

    @Override // com.google.common.collect.AbstractMultimap
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    private LinkedListMultimap(int i2) {
        this.keyToKeyList = Platform.d(i2);
    }

    public static <K, V> LinkedListMultimap<K, V> create(int i2) {
        return new LinkedListMultimap<>(i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.AbstractMultimap
    public List<Map.Entry<K, V>> createEntries() {
        return new AbstractSequentialList<Map.Entry<K, V>>() { // from class: com.google.common.collect.LinkedListMultimap.1EntriesImpl
            @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
            public ListIterator<Map.Entry<K, V>> listIterator(int i2) {
                return new NodeIterator(i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return LinkedListMultimap.this.size;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.AbstractMultimap
    public List<V> createValues() {
        return new AbstractSequentialList<V>() { // from class: com.google.common.collect.LinkedListMultimap.1ValuesImpl
            @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
            public ListIterator<V> listIterator(int i2) {
                final NodeIterator nodeIterator = new NodeIterator(i2);
                return new TransformedListIterator<Map.Entry<K, V>, V>(this, nodeIterator) { // from class: com.google.common.collect.LinkedListMultimap.1ValuesImpl.1

                    /* renamed from: c, reason: collision with root package name */
                    final /* synthetic */ C1ValuesImpl f14099c;

                    {
                        this.f14099c = this;
                    }

                    /* JADX INFO: Access modifiers changed from: package-private */
                    @Override // com.google.common.collect.TransformedIterator
                    /* renamed from: b, reason: merged with bridge method [inline-methods] */
                    public Object a(Map.Entry entry) {
                        return entry.getValue();
                    }

                    @Override // com.google.common.collect.TransformedListIterator, java.util.ListIterator
                    public void set(@ParametricNullness V v2) {
                        nodeIterator.a(v2);
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return LinkedListMultimap.this.size;
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public List<Map.Entry<K, V>> entries() {
        return (List) super.entries();
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public List<V> get(@ParametricNullness final K k2) {
        return new AbstractSequentialList<V>(this) { // from class: com.google.common.collect.LinkedListMultimap.1

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ LinkedListMultimap f14094b;

            {
                this.f14094b = this;
            }

            @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
            public ListIterator<V> listIterator(int i2) {
                return new ValueForKeyIterator(k2, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                KeyList keyList = (KeyList) this.f14094b.keyToKeyList.get(k2);
                if (keyList == null) {
                    return 0;
                }
                return keyList.f14107c;
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean putAll(@ParametricNullness Object obj, Iterable iterable) {
        return super.putAll(obj, iterable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public List<V> removeAll(@CheckForNull Object obj) {
        List<V> copy = getCopy(obj);
        removeAllNodes(obj);
        return copy;
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public List<V> replaceValues(@ParametricNullness K k2, Iterable<? extends V> iterable) {
        List<V> copy = getCopy(k2);
        ValueForKeyIterator valueForKeyIterator = new ValueForKeyIterator(k2);
        Iterator<? extends V> it = iterable.iterator();
        while (valueForKeyIterator.hasNext() && it.hasNext()) {
            valueForKeyIterator.next();
            valueForKeyIterator.set(it.next());
        }
        while (valueForKeyIterator.hasNext()) {
            valueForKeyIterator.next();
            valueForKeyIterator.remove();
        }
        while (it.hasNext()) {
            valueForKeyIterator.add(it.next());
        }
        return copy;
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public List<V> values() {
        return (List) super.values();
    }

    public static <K, V> LinkedListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new LinkedListMultimap<>(multimap);
    }

    private class ValueForKeyIterator implements ListIterator<V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f14120a;

        /* renamed from: b, reason: collision with root package name */
        int f14121b;

        /* renamed from: c, reason: collision with root package name */
        Node f14122c;

        /* renamed from: d, reason: collision with root package name */
        Node f14123d;

        /* renamed from: e, reason: collision with root package name */
        Node f14124e;

        ValueForKeyIterator(Object obj) {
            this.f14120a = obj;
            KeyList keyList = (KeyList) LinkedListMultimap.this.keyToKeyList.get(obj);
            this.f14122c = keyList == null ? null : keyList.f14105a;
        }

        @Override // java.util.ListIterator
        public void add(@ParametricNullness V v2) {
            this.f14124e = LinkedListMultimap.this.addNode(this.f14120a, v2, this.f14122c);
            this.f14121b++;
            this.f14123d = null;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.f14122c != null;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.f14124e != null;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        @ParametricNullness
        @CanIgnoreReturnValue
        public V next() {
            Node node = this.f14122c;
            if (node == null) {
                throw new NoSuchElementException();
            }
            this.f14123d = node;
            this.f14124e = node;
            this.f14122c = node.f14112e;
            this.f14121b++;
            return (V) node.f14109b;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.f14121b;
        }

        @Override // java.util.ListIterator
        @ParametricNullness
        @CanIgnoreReturnValue
        public V previous() {
            Node node = this.f14124e;
            if (node == null) {
                throw new NoSuchElementException();
            }
            this.f14123d = node;
            this.f14122c = node;
            this.f14124e = node.f14113f;
            this.f14121b--;
            return (V) node.f14109b;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.f14121b - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            Preconditions.checkState(this.f14123d != null, "no calls to next() since the last call to remove()");
            Node node = this.f14123d;
            if (node != this.f14122c) {
                this.f14124e = node.f14113f;
                this.f14121b--;
            } else {
                this.f14122c = node.f14112e;
            }
            LinkedListMultimap.this.removeNode(node);
            this.f14123d = null;
        }

        @Override // java.util.ListIterator
        public void set(@ParametricNullness V v2) {
            Preconditions.checkState(this.f14123d != null);
            this.f14123d.f14109b = v2;
        }

        public ValueForKeyIterator(@ParametricNullness K k2, int i2) {
            KeyList keyList = (KeyList) LinkedListMultimap.this.keyToKeyList.get(k2);
            int i3 = keyList == null ? 0 : keyList.f14107c;
            Preconditions.checkPositionIndex(i2, i3);
            if (i2 >= i3 / 2) {
                this.f14124e = keyList == null ? null : keyList.f14106b;
                this.f14121b = i3;
                while (true) {
                    int i4 = i2 + 1;
                    if (i2 >= i3) {
                        break;
                    }
                    previous();
                    i2 = i4;
                }
            } else {
                this.f14122c = keyList == null ? null : keyList.f14105a;
                while (true) {
                    int i5 = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    }
                    next();
                    i2 = i5;
                }
            }
            this.f14120a = k2;
            this.f14123d = null;
        }
    }

    private LinkedListMultimap(Multimap<? extends K, ? extends V> multimap) {
        this(multimap.keySet().size());
        putAll(multimap);
    }
}
