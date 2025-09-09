package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.Weak;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {

    /* renamed from: w, reason: collision with root package name */
    static final Logger f13725w = Logger.getLogger(LocalCache.class.getName());

    /* renamed from: x, reason: collision with root package name */
    static final ValueReference f13726x = new ValueReference<Object, Object>() { // from class: com.google.common.cache.LocalCache.1
        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<Object, Object> copyFor(ReferenceQueue<Object> referenceQueue, @CheckForNull Object obj, ReferenceEntry<Object, Object> referenceEntry) {
            return this;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        @CheckForNull
        public Object get() {
            return null;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        @CheckForNull
        public ReferenceEntry<Object, Object> getEntry() {
            return null;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 0;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(Object obj) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        @CheckForNull
        public Object waitForValue() {
            return null;
        }
    };

    /* renamed from: y, reason: collision with root package name */
    static final Queue f13727y = new AbstractQueue<Object>() { // from class: com.google.common.cache.LocalCache.2
        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Object> iterator() {
            return ImmutableSet.of().iterator();
        }

        @Override // java.util.Queue
        public boolean offer(Object obj) {
            return true;
        }

        @Override // java.util.Queue
        @CheckForNull
        public Object peek() {
            return null;
        }

        @Override // java.util.Queue
        @CheckForNull
        public Object poll() {
            return null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return 0;
        }
    };

    /* renamed from: a, reason: collision with root package name */
    final int f13728a;

    /* renamed from: b, reason: collision with root package name */
    final int f13729b;

    /* renamed from: c, reason: collision with root package name */
    final Segment[] f13730c;

    /* renamed from: d, reason: collision with root package name */
    final int f13731d;

    /* renamed from: e, reason: collision with root package name */
    final Equivalence f13732e;

    /* renamed from: f, reason: collision with root package name */
    final Equivalence f13733f;

    /* renamed from: g, reason: collision with root package name */
    final Strength f13734g;

    /* renamed from: h, reason: collision with root package name */
    final Strength f13735h;

    /* renamed from: i, reason: collision with root package name */
    final long f13736i;

    /* renamed from: j, reason: collision with root package name */
    final Weigher f13737j;

    /* renamed from: k, reason: collision with root package name */
    final long f13738k;

    /* renamed from: l, reason: collision with root package name */
    final long f13739l;

    /* renamed from: m, reason: collision with root package name */
    final long f13740m;

    /* renamed from: n, reason: collision with root package name */
    final Queue f13741n;

    /* renamed from: o, reason: collision with root package name */
    final RemovalListener f13742o;

    /* renamed from: p, reason: collision with root package name */
    final Ticker f13743p;

    /* renamed from: q, reason: collision with root package name */
    final EntryFactory f13744q;

    /* renamed from: r, reason: collision with root package name */
    final AbstractCache.StatsCounter f13745r;

    /* renamed from: s, reason: collision with root package name */
    final CacheLoader f13746s;

    /* renamed from: t, reason: collision with root package name */
    Set f13747t;

    /* renamed from: u, reason: collision with root package name */
    Collection f13748u;

    /* renamed from: v, reason: collision with root package name */
    Set f13749v;

    abstract class AbstractCacheSet<T> extends AbstractSet<T> {
        AbstractCacheSet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            LocalCache.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return LocalCache.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return LocalCache.this.size();
        }
    }

    static abstract class AbstractReferenceEntry<K, V> implements ReferenceEntry<K, V> {
        AbstractReferenceEntry() {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public int getHash() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public K getKey() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            throw new UnsupportedOperationException();
        }
    }

    static final class AccessQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {

        /* renamed from: a, reason: collision with root package name */
        final ReferenceEntry f13751a = new AbstractReferenceEntry<K, V>() { // from class: com.google.common.cache.LocalCache.AccessQueue.1

            /* renamed from: a, reason: collision with root package name */
            ReferenceEntry f13752a = this;

            /* renamed from: b, reason: collision with root package name */
            ReferenceEntry f13753b = this;

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public long getAccessTime() {
                return Long.MAX_VALUE;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getNextInAccessQueue() {
                return this.f13752a;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getPreviousInAccessQueue() {
                return this.f13753b;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setAccessTime(long j2) {
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.f13752a = referenceEntry;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.f13753b = referenceEntry;
            }
        };

        AccessQueue() {
        }

        @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            ReferenceEntry<K, V> nextInAccessQueue = this.f13751a.getNextInAccessQueue();
            while (true) {
                ReferenceEntry<K, V> referenceEntry = this.f13751a;
                if (nextInAccessQueue == referenceEntry) {
                    referenceEntry.setNextInAccessQueue(referenceEntry);
                    ReferenceEntry<K, V> referenceEntry2 = this.f13751a;
                    referenceEntry2.setPreviousInAccessQueue(referenceEntry2);
                    return;
                } else {
                    ReferenceEntry<K, V> nextInAccessQueue2 = nextInAccessQueue.getNextInAccessQueue();
                    LocalCache.u(nextInAccessQueue);
                    nextInAccessQueue = nextInAccessQueue2;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).getNextInAccessQueue() != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.f13751a.getNextInAccessQueue() == this.f13751a;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) { // from class: com.google.common.cache.LocalCache.AccessQueue.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.google.common.collect.AbstractSequentialIterator
                /* renamed from: b, reason: merged with bridge method [inline-methods] */
                public ReferenceEntry a(ReferenceEntry referenceEntry) {
                    ReferenceEntry<K, V> nextInAccessQueue = referenceEntry.getNextInAccessQueue();
                    if (nextInAccessQueue == AccessQueue.this.f13751a) {
                        return null;
                    }
                    return nextInAccessQueue;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        @CanIgnoreReturnValue
        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry<K, V> previousInAccessQueue = referenceEntry.getPreviousInAccessQueue();
            ReferenceEntry<K, V> nextInAccessQueue = referenceEntry.getNextInAccessQueue();
            LocalCache.a(previousInAccessQueue, nextInAccessQueue);
            LocalCache.u(referenceEntry);
            return nextInAccessQueue != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            int i2 = 0;
            for (ReferenceEntry<K, V> nextInAccessQueue = this.f13751a.getNextInAccessQueue(); nextInAccessQueue != this.f13751a; nextInAccessQueue = nextInAccessQueue.getNextInAccessQueue()) {
                i2++;
            }
            return i2;
        }

        @Override // java.util.Queue
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.a(referenceEntry.getPreviousInAccessQueue(), referenceEntry.getNextInAccessQueue());
            LocalCache.a(this.f13751a.getPreviousInAccessQueue(), referenceEntry);
            LocalCache.a(referenceEntry, this.f13751a);
            return true;
        }

        @Override // java.util.Queue
        @CheckForNull
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> nextInAccessQueue = this.f13751a.getNextInAccessQueue();
            if (nextInAccessQueue == this.f13751a) {
                return null;
            }
            return nextInAccessQueue;
        }

        @Override // java.util.Queue
        @CheckForNull
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> nextInAccessQueue = this.f13751a.getNextInAccessQueue();
            if (nextInAccessQueue == this.f13751a) {
                return null;
            }
            remove(nextInAccessQueue);
            return nextInAccessQueue;
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 com.google.common.cache.LocalCache$EntryFactory, still in use, count: 1, list:
      (r0v0 com.google.common.cache.LocalCache$EntryFactory) from 0x0058: FILLED_NEW_ARRAY 
      (r0v0 com.google.common.cache.LocalCache$EntryFactory)
      (r1v1 com.google.common.cache.LocalCache$EntryFactory)
      (r3v1 com.google.common.cache.LocalCache$EntryFactory)
      (r5v1 com.google.common.cache.LocalCache$EntryFactory)
      (r7v1 com.google.common.cache.LocalCache$EntryFactory)
      (r9v1 com.google.common.cache.LocalCache$EntryFactory)
      (r11v1 com.google.common.cache.LocalCache$EntryFactory)
      (r13v1 com.google.common.cache.LocalCache$EntryFactory)
     A[WRAPPED] (LINE:89) elemType: com.google.common.cache.LocalCache$EntryFactory
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
    	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    static abstract class EntryFactory {
        STRONG { // from class: com.google.common.cache.LocalCache.EntryFactory.1
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new StrongEntry(k2, i2, referenceEntry);
            }
        },
        STRONG_ACCESS { // from class: com.google.common.cache.LocalCache.EntryFactory.2
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessEntry(k2, i2, referenceEntry);
            }
        },
        STRONG_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.3
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new StrongWriteEntry(k2, i2, referenceEntry);
            }
        },
        STRONG_ACCESS_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.4
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessWriteEntry(k2, i2, referenceEntry);
            }
        },
        WEAK { // from class: com.google.common.cache.LocalCache.EntryFactory.5
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new WeakEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        },
        WEAK_ACCESS { // from class: com.google.common.cache.LocalCache.EntryFactory.6
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        },
        WEAK_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.7
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new WeakWriteEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        },
        WEAK_ACCESS_WRITE { // from class: com.google.common.cache.LocalCache.EntryFactory.8
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        };

        static final int ACCESS_MASK = 1;
        static final int WEAK_MASK = 4;
        static final int WRITE_MASK = 2;
        static final EntryFactory[] factories = {new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.1
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new StrongEntry(k2, i2, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.2
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessEntry(k2, i2, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.3
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new StrongWriteEntry(k2, i2, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.4
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessWriteEntry(k2, i2, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.5
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new WeakEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.6
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.7
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new WeakWriteEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        }, new EntryFactory() { // from class: com.google.common.cache.LocalCache.EntryFactory.8
            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2, k2);
                copyAccessEntry(referenceEntry, referenceEntryCopyEntry);
                copyWriteEntry(referenceEntry, referenceEntryCopyEntry);
                return referenceEntryCopyEntry;
            }

            @Override // com.google.common.cache.LocalCache.EntryFactory
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, k2, i2, referenceEntry);
            }
        }};

        static {
        }

        private EntryFactory() {
        }

        static EntryFactory getFactory(Strength strength, boolean z2, boolean z3) {
            return factories[(strength == Strength.WEAK ? 4 : 0) | (z2 ? 1 : 0) | (z3 ? 2 : 0)];
        }

        public static EntryFactory valueOf(String str) {
            return (EntryFactory) Enum.valueOf(EntryFactory.class, str);
        }

        public static EntryFactory[] values() {
            return (EntryFactory[]) $VALUES.clone();
        }

        <K, V> void copyAccessEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setAccessTime(referenceEntry.getAccessTime());
            LocalCache.a(referenceEntry.getPreviousInAccessQueue(), referenceEntry2);
            LocalCache.a(referenceEntry2, referenceEntry.getNextInAccessQueue());
            LocalCache.u(referenceEntry);
        }

        <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k2) {
            return newEntry(segment, k2, referenceEntry.getHash(), referenceEntry2);
        }

        <K, V> void copyWriteEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setWriteTime(referenceEntry.getWriteTime());
            LocalCache.b(referenceEntry.getPreviousInWriteQueue(), referenceEntry2);
            LocalCache.b(referenceEntry2, referenceEntry.getNextInWriteQueue());
            LocalCache.v(referenceEntry);
        }

        abstract <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry);
    }

    final class EntryIterator extends LocalCache<K, V>.HashIterator<Map.Entry<K, V>> {
        EntryIterator() {
            super();
        }

        @Override // com.google.common.cache.LocalCache.HashIterator, java.util.Iterator
        public Map.Entry<K, V> next() {
            return c();
        }
    }

    final class EntrySet extends LocalCache<K, V>.AbstractCacheSet<Map.Entry<K, V>> {
        EntrySet() {
            super();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            Map.Entry entry;
            Object key;
            Object obj2;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (obj2 = LocalCache.this.get(key)) != null && LocalCache.this.f13733f.equivalent(entry.getValue(), obj2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            Map.Entry entry;
            Object key;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && LocalCache.this.remove(key, entry.getValue());
        }
    }

    abstract class HashIterator<T> implements Iterator<T> {

        /* renamed from: a, reason: collision with root package name */
        int f13758a;

        /* renamed from: b, reason: collision with root package name */
        int f13759b = -1;

        /* renamed from: c, reason: collision with root package name */
        Segment f13760c;

        /* renamed from: d, reason: collision with root package name */
        AtomicReferenceArray f13761d;

        /* renamed from: e, reason: collision with root package name */
        ReferenceEntry f13762e;

        /* renamed from: f, reason: collision with root package name */
        WriteThroughEntry f13763f;

        /* renamed from: g, reason: collision with root package name */
        WriteThroughEntry f13764g;

        HashIterator() {
            this.f13758a = LocalCache.this.f13730c.length - 1;
            a();
        }

        final void a() {
            this.f13763f = null;
            if (d() || e()) {
                return;
            }
            while (true) {
                int i2 = this.f13758a;
                if (i2 < 0) {
                    return;
                }
                Segment[] segmentArr = LocalCache.this.f13730c;
                this.f13758a = i2 - 1;
                Segment segment = segmentArr[i2];
                this.f13760c = segment;
                if (segment.count != 0) {
                    this.f13761d = this.f13760c.table;
                    this.f13759b = r0.length() - 1;
                    if (e()) {
                        return;
                    }
                }
            }
        }

        boolean b(ReferenceEntry referenceEntry) {
            try {
                long j2 = LocalCache.this.f13743p.read();
                Object key = referenceEntry.getKey();
                Object objL = LocalCache.this.l(referenceEntry, j2);
                if (objL == null) {
                    this.f13760c.postReadCleanup();
                    return false;
                }
                this.f13763f = new WriteThroughEntry(key, objL);
                this.f13760c.postReadCleanup();
                return true;
            } catch (Throwable th) {
                this.f13760c.postReadCleanup();
                throw th;
            }
        }

        WriteThroughEntry c() {
            WriteThroughEntry writeThroughEntry = this.f13763f;
            if (writeThroughEntry == null) {
                throw new NoSuchElementException();
            }
            this.f13764g = writeThroughEntry;
            a();
            return this.f13764g;
        }

        boolean d() {
            ReferenceEntry referenceEntry = this.f13762e;
            if (referenceEntry == null) {
                return false;
            }
            while (true) {
                this.f13762e = referenceEntry.getNext();
                ReferenceEntry referenceEntry2 = this.f13762e;
                if (referenceEntry2 == null) {
                    return false;
                }
                if (b(referenceEntry2)) {
                    return true;
                }
                referenceEntry = this.f13762e;
            }
        }

        boolean e() {
            while (true) {
                int i2 = this.f13759b;
                if (i2 < 0) {
                    return false;
                }
                AtomicReferenceArray atomicReferenceArray = this.f13761d;
                this.f13759b = i2 - 1;
                ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(i2);
                this.f13762e = referenceEntry;
                if (referenceEntry != null && (b(referenceEntry) || d())) {
                    return true;
                }
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f13763f != null;
        }

        @Override // java.util.Iterator
        public abstract T next();

        @Override // java.util.Iterator
        public void remove() {
            Preconditions.checkState(this.f13764g != null);
            LocalCache.this.remove(this.f13764g.getKey());
            this.f13764g = null;
        }
    }

    final class KeyIterator extends LocalCache<K, V>.HashIterator<K> {
        KeyIterator() {
            super();
        }

        @Override // com.google.common.cache.LocalCache.HashIterator, java.util.Iterator
        public K next() {
            return (K) c().getKey();
        }
    }

    final class KeySet extends LocalCache<K, V>.AbstractCacheSet<K> {
        KeySet() {
            super();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return LocalCache.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return LocalCache.this.remove(obj) != null;
        }
    }

    static final class LoadingSerializationProxy<K, V> extends ManualSerializationProxy<K, V> implements LoadingCache<K, V>, Serializable {
        private static final long serialVersionUID = 1;

        @CheckForNull
        transient LoadingCache<K, V> autoDelegate;

        LoadingSerializationProxy(LocalCache<K, V> localCache) {
            super(localCache);
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.autoDelegate = (LoadingCache<K, V>) recreateCacheBuilder().build(this.loader);
        }

        private Object readResolve() {
            return this.autoDelegate;
        }

        @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
        public V apply(K k2) {
            return this.autoDelegate.apply(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public V get(K k2) throws ExecutionException {
            return this.autoDelegate.get(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.autoDelegate.getAll(iterable);
        }

        @Override // com.google.common.cache.LoadingCache
        public V getUnchecked(K k2) {
            return this.autoDelegate.getUnchecked(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public void refresh(K k2) {
            this.autoDelegate.refresh(k2);
        }
    }

    static class LoadingValueReference<K, V> implements ValueReference<K, V> {

        /* renamed from: a, reason: collision with root package name */
        volatile ValueReference f13768a;

        /* renamed from: b, reason: collision with root package name */
        final SettableFuture f13769b;

        /* renamed from: c, reason: collision with root package name */
        final Stopwatch f13770c;

        public LoadingValueReference() {
            this(LocalCache.G());
        }

        private ListenableFuture<V> fullyFailedFuture(Throwable th) {
            return Futures.immediateFailedFuture(th);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ Object lambda$loadFuture$0(Object obj) {
            set(obj);
            return obj;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @CheckForNull V v2, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public long elapsedNanos() {
            return this.f13770c.elapsed(TimeUnit.NANOSECONDS);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V get() {
            return (V) this.f13768a.get();
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public ValueReference<K, V> getOldValue() {
            return this.f13768a;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.f13768a.getWeight();
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return this.f13768a.isActive();
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ListenableFuture<V> loadFuture(K k2, CacheLoader<? super K, V> cacheLoader) {
            try {
                this.f13770c.start();
                Object obj = this.f13768a.get();
                if (obj == null) {
                    V vLoad = cacheLoader.load(k2);
                    return set(vLoad) ? this.f13769b : Futures.immediateFuture(vLoad);
                }
                ListenableFuture listenableFutureReload = cacheLoader.reload(k2, obj);
                return listenableFutureReload == null ? Futures.immediateFuture(null) : Futures.transform(listenableFutureReload, new Function() { // from class: com.google.common.cache.b
                    @Override // com.google.common.base.Function
                    public final Object apply(Object obj2) {
                        return this.f13824a.lambda$loadFuture$0(obj2);
                    }
                }, MoreExecutors.directExecutor());
            } catch (Throwable th) {
                ListenableFuture<V> listenableFutureFullyFailedFuture = setException(th) ? this.f13769b : fullyFailedFuture(th);
                if (th instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return listenableFutureFullyFailedFuture;
            }
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(@CheckForNull V v2) {
            if (v2 != null) {
                set(v2);
            } else {
                this.f13768a = LocalCache.G();
            }
        }

        @CanIgnoreReturnValue
        public boolean set(@CheckForNull V v2) {
            return this.f13769b.set(v2);
        }

        @CanIgnoreReturnValue
        public boolean setException(Throwable th) {
            return this.f13769b.setException(th);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() throws ExecutionException {
            return (V) Uninterruptibles.getUninterruptibly(this.f13769b);
        }

        public LoadingValueReference(ValueReference<K, V> valueReference) {
            this.f13769b = SettableFuture.create();
            this.f13770c = Stopwatch.createUnstarted();
            this.f13768a = valueReference;
        }
    }

    static class LocalLoadingCache<K, V> extends LocalManualCache<K, V> implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1;

        LocalLoadingCache(CacheBuilder<? super K, ? super V> cacheBuilder, CacheLoader<? super K, V> cacheLoader) {
            super();
        }

        private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
            throw new InvalidObjectException("Use LoadingSerializationProxy");
        }

        @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
        public final V apply(K k2) {
            return getUnchecked(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public V get(K k2) throws ExecutionException {
            return (V) this.localCache.m(k2);
        }

        @Override // com.google.common.cache.LoadingCache
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.localCache.j(iterable);
        }

        @Override // com.google.common.cache.LoadingCache
        @CanIgnoreReturnValue
        public V getUnchecked(K k2) {
            try {
                return get(k2);
            } catch (ExecutionException e2) {
                throw new UncheckedExecutionException(e2.getCause());
            }
        }

        @Override // com.google.common.cache.LoadingCache
        public void refresh(K k2) {
            this.localCache.C(k2);
        }

        @Override // com.google.common.cache.LocalCache.LocalManualCache
        Object writeReplace() {
            return new LoadingSerializationProxy(this.localCache);
        }
    }

    static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final LocalCache<K, V> localCache;

        private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
            throw new InvalidObjectException("Use ManualSerializationProxy");
        }

        @Override // com.google.common.cache.Cache
        public ConcurrentMap<K, V> asMap() {
            return this.localCache;
        }

        @Override // com.google.common.cache.Cache
        public void cleanUp() {
            this.localCache.cleanUp();
        }

        @Override // com.google.common.cache.Cache
        public V get(K k2, final Callable<? extends V> callable) throws ExecutionException {
            Preconditions.checkNotNull(callable);
            return (V) this.localCache.i(k2, new CacheLoader<Object, V>(this) { // from class: com.google.common.cache.LocalCache.LocalManualCache.1

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ LocalManualCache f13772b;

                {
                    this.f13772b = this;
                }

                @Override // com.google.common.cache.CacheLoader
                public V load(Object obj) throws Exception {
                    return (V) callable.call();
                }
            });
        }

        @Override // com.google.common.cache.Cache
        public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
            return this.localCache.k(iterable);
        }

        @Override // com.google.common.cache.Cache
        @CheckForNull
        public V getIfPresent(Object obj) {
            return this.localCache.getIfPresent(obj);
        }

        @Override // com.google.common.cache.Cache
        public void invalidate(Object obj) {
            Preconditions.checkNotNull(obj);
            this.localCache.remove(obj);
        }

        @Override // com.google.common.cache.Cache
        public void invalidateAll(Iterable<?> iterable) {
            this.localCache.o(iterable);
        }

        @Override // com.google.common.cache.Cache
        public void put(K k2, V v2) {
            this.localCache.put(k2, v2);
        }

        @Override // com.google.common.cache.Cache
        public void putAll(Map<? extends K, ? extends V> map) {
            this.localCache.putAll(map);
        }

        @Override // com.google.common.cache.Cache
        public long size() {
            return this.localCache.r();
        }

        @Override // com.google.common.cache.Cache
        public CacheStats stats() {
            AbstractCache.SimpleStatsCounter simpleStatsCounter = new AbstractCache.SimpleStatsCounter();
            simpleStatsCounter.incrementBy(this.localCache.f13745r);
            for (Segment segment : this.localCache.f13730c) {
                simpleStatsCounter.incrementBy(segment.statsCounter);
            }
            return simpleStatsCounter.snapshot();
        }

        Object writeReplace() {
            return new ManualSerializationProxy(this.localCache);
        }

        LocalManualCache(CacheBuilder<? super K, ? super V> cacheBuilder) {
            this(new LocalCache(cacheBuilder, null));
        }

        @Override // com.google.common.cache.Cache
        public void invalidateAll() {
            this.localCache.clear();
        }

        private LocalManualCache(LocalCache<K, V> localCache) {
            this.localCache = localCache;
        }
    }

    static class ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        final int concurrencyLevel;

        @CheckForNull
        transient Cache<K, V> delegate;
        final long expireAfterAccessNanos;
        final long expireAfterWriteNanos;
        final Equivalence<Object> keyEquivalence;
        final Strength keyStrength;
        final CacheLoader<? super K, V> loader;
        final long maxWeight;
        final RemovalListener<? super K, ? super V> removalListener;

        @CheckForNull
        final Ticker ticker;
        final Equivalence<Object> valueEquivalence;
        final Strength valueStrength;
        final Weigher<K, V> weigher;

        ManualSerializationProxy(LocalCache<K, V> localCache) {
            this(localCache.f13734g, localCache.f13735h, localCache.f13732e, localCache.f13733f, localCache.f13739l, localCache.f13738k, localCache.f13736i, localCache.f13737j, localCache.f13731d, localCache.f13742o, localCache.f13743p, localCache.f13746s);
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.delegate = (Cache<K, V>) recreateCacheBuilder().build();
        }

        private Object readResolve() {
            return this.delegate;
        }

        CacheBuilder<K, V> recreateCacheBuilder() {
            CacheBuilder<K, V> cacheBuilder = (CacheBuilder<K, V>) CacheBuilder.newBuilder().q(this.keyStrength).r(this.valueStrength).o(this.keyEquivalence).s(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
            cacheBuilder.f13692a = false;
            long j2 = this.expireAfterWriteNanos;
            if (j2 > 0) {
                cacheBuilder.expireAfterWrite(j2, TimeUnit.NANOSECONDS);
            }
            long j3 = this.expireAfterAccessNanos;
            if (j3 > 0) {
                cacheBuilder.expireAfterAccess(j3, TimeUnit.NANOSECONDS);
            }
            Weigher weigher = this.weigher;
            if (weigher != CacheBuilder.OneWeigher.INSTANCE) {
                cacheBuilder.weigher(weigher);
                long j4 = this.maxWeight;
                if (j4 != -1) {
                    cacheBuilder.maximumWeight(j4);
                }
            } else {
                long j5 = this.maxWeight;
                if (j5 != -1) {
                    cacheBuilder.maximumSize(j5);
                }
            }
            Ticker ticker = this.ticker;
            if (ticker != null) {
                cacheBuilder.ticker(ticker);
            }
            return cacheBuilder;
        }

        private ManualSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, long j2, long j3, long j4, Weigher<K, V> weigher, int i2, RemovalListener<? super K, ? super V> removalListener, Ticker ticker, CacheLoader<? super K, V> cacheLoader) {
            this.keyStrength = strength;
            this.valueStrength = strength2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.expireAfterWriteNanos = j2;
            this.expireAfterAccessNanos = j3;
            this.maxWeight = j4;
            this.weigher = weigher;
            this.concurrencyLevel = i2;
            this.removalListener = removalListener;
            this.ticker = (ticker == Ticker.systemTicker() || ticker == CacheBuilder.f13691t) ? null : ticker;
            this.loader = cacheLoader;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        public Cache<K, V> delegate() {
            return this.delegate;
        }
    }

    private enum NullEntry implements ReferenceEntry<Object, Object> {
        INSTANCE;

        @Override // com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return 0L;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public int getHash() {
            return 0;
        }

        @Override // com.google.common.cache.ReferenceEntry
        @CheckForNull
        public Object getKey() {
            return null;
        }

        @Override // com.google.common.cache.ReferenceEntry
        @CheckForNull
        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getNextInAccessQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getNextInWriteQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
            return this;
        }

        @Override // com.google.common.cache.ReferenceEntry
        @CheckForNull
        public ValueReference<Object, Object> getValueReference() {
            return null;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return 0L;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<Object, Object> valueReference) {
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
        }
    }

    static class SoftValueReference<K, V> extends SoftReference<V> implements ValueReference<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final ReferenceEntry f13773a;

        SoftValueReference(ReferenceQueue referenceQueue, Object obj, ReferenceEntry referenceEntry) {
            super(obj, referenceQueue);
            this.f13773a = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return new SoftValueReference(referenceQueue, v2, referenceEntry);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return this.f13773a;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 1;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return true;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(V v2) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() {
            return get();
        }
    }

    enum Strength {
        STRONG { // from class: com.google.common.cache.LocalCache.Strength.1
            @Override // com.google.common.cache.LocalCache.Strength
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }

            @Override // com.google.common.cache.LocalCache.Strength
            <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v2, int i2) {
                return i2 == 1 ? new StrongValueReference(v2) : new WeightedStrongValueReference(v2, i2);
            }
        },
        SOFT { // from class: com.google.common.cache.LocalCache.Strength.2
            @Override // com.google.common.cache.LocalCache.Strength
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }

            @Override // com.google.common.cache.LocalCache.Strength
            <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v2, int i2) {
                return i2 == 1 ? new SoftValueReference(segment.valueReferenceQueue, v2, referenceEntry) : new WeightedSoftValueReference(segment.valueReferenceQueue, v2, referenceEntry, i2);
            }
        },
        WEAK { // from class: com.google.common.cache.LocalCache.Strength.3
            @Override // com.google.common.cache.LocalCache.Strength
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }

            @Override // com.google.common.cache.LocalCache.Strength
            <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v2, int i2) {
                return i2 == 1 ? new WeakValueReference(segment.valueReferenceQueue, v2, referenceEntry) : new WeightedWeakValueReference(segment.valueReferenceQueue, v2, referenceEntry, i2);
            }
        };

        abstract Equivalence<Object> defaultEquivalence();

        abstract <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v2, int i2);
    }

    static final class StrongAccessEntry<K, V> extends StrongEntry<K, V> {

        /* renamed from: e, reason: collision with root package name */
        volatile long f13774e;

        /* renamed from: f, reason: collision with root package name */
        ReferenceEntry f13775f;

        /* renamed from: g, reason: collision with root package name */
        ReferenceEntry f13776g;

        StrongAccessEntry(Object obj, int i2, ReferenceEntry referenceEntry) {
            super(obj, i2, referenceEntry);
            this.f13774e = Long.MAX_VALUE;
            this.f13775f = LocalCache.t();
            this.f13776g = LocalCache.t();
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.f13774e;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.f13775f;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.f13776g;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            this.f13774e = j2;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13775f = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13776g = referenceEntry;
        }
    }

    static final class StrongAccessWriteEntry<K, V> extends StrongEntry<K, V> {

        /* renamed from: e, reason: collision with root package name */
        volatile long f13777e;

        /* renamed from: f, reason: collision with root package name */
        ReferenceEntry f13778f;

        /* renamed from: g, reason: collision with root package name */
        ReferenceEntry f13779g;

        /* renamed from: h, reason: collision with root package name */
        volatile long f13780h;

        /* renamed from: i, reason: collision with root package name */
        ReferenceEntry f13781i;

        /* renamed from: j, reason: collision with root package name */
        ReferenceEntry f13782j;

        StrongAccessWriteEntry(Object obj, int i2, ReferenceEntry referenceEntry) {
            super(obj, i2, referenceEntry);
            this.f13777e = Long.MAX_VALUE;
            this.f13778f = LocalCache.t();
            this.f13779g = LocalCache.t();
            this.f13780h = Long.MAX_VALUE;
            this.f13781i = LocalCache.t();
            this.f13782j = LocalCache.t();
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.f13777e;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.f13778f;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.f13781i;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.f13779g;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.f13782j;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.f13780h;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            this.f13777e = j2;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13778f = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13781i = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13779g = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13782j = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            this.f13780h = j2;
        }
    }

    static class StrongEntry<K, V> extends AbstractReferenceEntry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f13783a;

        /* renamed from: b, reason: collision with root package name */
        final int f13784b;

        /* renamed from: c, reason: collision with root package name */
        final ReferenceEntry f13785c;

        /* renamed from: d, reason: collision with root package name */
        volatile ValueReference f13786d = LocalCache.G();

        StrongEntry(Object obj, int i2, ReferenceEntry referenceEntry) {
            this.f13783a = obj;
            this.f13784b = i2;
            this.f13785c = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public int getHash() {
            return this.f13784b;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public K getKey() {
            return (K) this.f13783a;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNext() {
            return this.f13785c;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ValueReference<K, V> getValueReference() {
            return this.f13786d;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.f13786d = valueReference;
        }
    }

    static class StrongValueReference<K, V> implements ValueReference<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f13787a;

        StrongValueReference(Object obj) {
            this.f13787a = obj;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V get() {
            return (V) this.f13787a;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 1;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return true;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(V v2) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() {
            return get();
        }
    }

    static final class StrongWriteEntry<K, V> extends StrongEntry<K, V> {

        /* renamed from: e, reason: collision with root package name */
        volatile long f13788e;

        /* renamed from: f, reason: collision with root package name */
        ReferenceEntry f13789f;

        /* renamed from: g, reason: collision with root package name */
        ReferenceEntry f13790g;

        StrongWriteEntry(Object obj, int i2, ReferenceEntry referenceEntry) {
            super(obj, i2, referenceEntry);
            this.f13788e = Long.MAX_VALUE;
            this.f13789f = LocalCache.t();
            this.f13790g = LocalCache.t();
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.f13789f;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.f13790g;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.f13788e;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13789f = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13790g = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            this.f13788e = j2;
        }
    }

    final class ValueIterator extends LocalCache<K, V>.HashIterator<V> {
        ValueIterator() {
            super();
        }

        @Override // com.google.common.cache.LocalCache.HashIterator, java.util.Iterator
        public V next() {
            return (V) c().getValue();
        }
    }

    interface ValueReference<K, V> {
        ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @CheckForNull V v2, ReferenceEntry<K, V> referenceEntry);

        @CheckForNull
        V get();

        @CheckForNull
        ReferenceEntry<K, V> getEntry();

        int getWeight();

        boolean isActive();

        boolean isLoading();

        void notifyNewValue(@CheckForNull V v2);

        V waitForValue() throws ExecutionException;
    }

    final class Values extends AbstractCollection<V> {
        Values() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            LocalCache.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return LocalCache.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return LocalCache.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return LocalCache.this.size();
        }
    }

    static final class WeakAccessEntry<K, V> extends WeakEntry<K, V> {

        /* renamed from: d, reason: collision with root package name */
        volatile long f13793d;

        /* renamed from: e, reason: collision with root package name */
        ReferenceEntry f13794e;

        /* renamed from: f, reason: collision with root package name */
        ReferenceEntry f13795f;

        WeakAccessEntry(ReferenceQueue referenceQueue, Object obj, int i2, ReferenceEntry referenceEntry) {
            super(referenceQueue, obj, i2, referenceEntry);
            this.f13793d = Long.MAX_VALUE;
            this.f13794e = LocalCache.t();
            this.f13795f = LocalCache.t();
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.f13793d;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.f13794e;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.f13795f;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            this.f13793d = j2;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13794e = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13795f = referenceEntry;
        }
    }

    static final class WeakAccessWriteEntry<K, V> extends WeakEntry<K, V> {

        /* renamed from: d, reason: collision with root package name */
        volatile long f13796d;

        /* renamed from: e, reason: collision with root package name */
        ReferenceEntry f13797e;

        /* renamed from: f, reason: collision with root package name */
        ReferenceEntry f13798f;

        /* renamed from: g, reason: collision with root package name */
        volatile long f13799g;

        /* renamed from: h, reason: collision with root package name */
        ReferenceEntry f13800h;

        /* renamed from: i, reason: collision with root package name */
        ReferenceEntry f13801i;

        WeakAccessWriteEntry(ReferenceQueue referenceQueue, Object obj, int i2, ReferenceEntry referenceEntry) {
            super(referenceQueue, obj, i2, referenceEntry);
            this.f13796d = Long.MAX_VALUE;
            this.f13797e = LocalCache.t();
            this.f13798f = LocalCache.t();
            this.f13799g = Long.MAX_VALUE;
            this.f13800h = LocalCache.t();
            this.f13801i = LocalCache.t();
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getAccessTime() {
            return this.f13796d;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.f13797e;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.f13800h;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.f13798f;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.f13801i;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.f13799g;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setAccessTime(long j2) {
            this.f13796d = j2;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13797e = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13800h = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13798f = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13801i = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            this.f13799g = j2;
        }
    }

    static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final int f13802a;

        /* renamed from: b, reason: collision with root package name */
        final ReferenceEntry f13803b;

        /* renamed from: c, reason: collision with root package name */
        volatile ValueReference f13804c;

        WeakEntry(ReferenceQueue referenceQueue, Object obj, int i2, ReferenceEntry referenceEntry) {
            super(obj, referenceQueue);
            this.f13804c = LocalCache.G();
            this.f13802a = i2;
            this.f13803b = referenceEntry;
        }

        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public int getHash() {
            return this.f13802a;
        }

        @Override // com.google.common.cache.ReferenceEntry
        public K getKey() {
            return get();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNext() {
            return this.f13803b;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public ValueReference<K, V> getValueReference() {
            return this.f13804c;
        }

        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        public void setAccessTime(long j2) {
            throw new UnsupportedOperationException();
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.ReferenceEntry
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.f13804c = valueReference;
        }

        public void setWriteTime(long j2) {
            throw new UnsupportedOperationException();
        }
    }

    static class WeakValueReference<K, V> extends WeakReference<V> implements ValueReference<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final ReferenceEntry f13805a;

        WeakValueReference(ReferenceQueue referenceQueue, Object obj, ReferenceEntry referenceEntry) {
            super(obj, referenceQueue);
            this.f13805a = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return new WeakValueReference(referenceQueue, v2, referenceEntry);
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public ReferenceEntry<K, V> getEntry() {
            return this.f13805a;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return 1;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isActive() {
            return true;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public void notifyNewValue(V v2) {
        }

        @Override // com.google.common.cache.LocalCache.ValueReference
        public V waitForValue() {
            return get();
        }
    }

    static final class WeakWriteEntry<K, V> extends WeakEntry<K, V> {

        /* renamed from: d, reason: collision with root package name */
        volatile long f13806d;

        /* renamed from: e, reason: collision with root package name */
        ReferenceEntry f13807e;

        /* renamed from: f, reason: collision with root package name */
        ReferenceEntry f13808f;

        WeakWriteEntry(ReferenceQueue referenceQueue, Object obj, int i2, ReferenceEntry referenceEntry) {
            super(referenceQueue, obj, i2, referenceEntry);
            this.f13806d = Long.MAX_VALUE;
            this.f13807e = LocalCache.t();
            this.f13808f = LocalCache.t();
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.f13807e;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.f13808f;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public long getWriteTime() {
            return this.f13806d;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13807e = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.f13808f = referenceEntry;
        }

        @Override // com.google.common.cache.LocalCache.WeakEntry, com.google.common.cache.ReferenceEntry
        public void setWriteTime(long j2) {
            this.f13806d = j2;
        }
    }

    static final class WeightedSoftValueReference<K, V> extends SoftValueReference<K, V> {

        /* renamed from: b, reason: collision with root package name */
        final int f13809b;

        WeightedSoftValueReference(ReferenceQueue referenceQueue, Object obj, ReferenceEntry referenceEntry, int i2) {
            super(referenceQueue, obj, referenceEntry);
            this.f13809b = i2;
        }

        @Override // com.google.common.cache.LocalCache.SoftValueReference, com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedSoftValueReference(referenceQueue, v2, referenceEntry, this.f13809b);
        }

        @Override // com.google.common.cache.LocalCache.SoftValueReference, com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.f13809b;
        }
    }

    static final class WeightedStrongValueReference<K, V> extends StrongValueReference<K, V> {

        /* renamed from: b, reason: collision with root package name */
        final int f13810b;

        WeightedStrongValueReference(Object obj, int i2) {
            super(obj);
            this.f13810b = i2;
        }

        @Override // com.google.common.cache.LocalCache.StrongValueReference, com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.f13810b;
        }
    }

    static final class WeightedWeakValueReference<K, V> extends WeakValueReference<K, V> {

        /* renamed from: b, reason: collision with root package name */
        final int f13811b;

        WeightedWeakValueReference(ReferenceQueue referenceQueue, Object obj, ReferenceEntry referenceEntry, int i2) {
            super(referenceQueue, obj, referenceEntry);
            this.f13811b = i2;
        }

        @Override // com.google.common.cache.LocalCache.WeakValueReference, com.google.common.cache.LocalCache.ValueReference
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v2, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedWeakValueReference(referenceQueue, v2, referenceEntry, this.f13811b);
        }

        @Override // com.google.common.cache.LocalCache.WeakValueReference, com.google.common.cache.LocalCache.ValueReference
        public int getWeight() {
            return this.f13811b;
        }
    }

    static final class WriteQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {

        /* renamed from: a, reason: collision with root package name */
        final ReferenceEntry f13812a = new AbstractReferenceEntry<K, V>() { // from class: com.google.common.cache.LocalCache.WriteQueue.1

            /* renamed from: a, reason: collision with root package name */
            ReferenceEntry f13813a = this;

            /* renamed from: b, reason: collision with root package name */
            ReferenceEntry f13814b = this;

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getNextInWriteQueue() {
                return this.f13813a;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public ReferenceEntry<K, V> getPreviousInWriteQueue() {
                return this.f13814b;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public long getWriteTime() {
                return Long.MAX_VALUE;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.f13813a = referenceEntry;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.f13814b = referenceEntry;
            }

            @Override // com.google.common.cache.LocalCache.AbstractReferenceEntry, com.google.common.cache.ReferenceEntry
            public void setWriteTime(long j2) {
            }
        };

        WriteQueue() {
        }

        @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            ReferenceEntry<K, V> nextInWriteQueue = this.f13812a.getNextInWriteQueue();
            while (true) {
                ReferenceEntry<K, V> referenceEntry = this.f13812a;
                if (nextInWriteQueue == referenceEntry) {
                    referenceEntry.setNextInWriteQueue(referenceEntry);
                    ReferenceEntry<K, V> referenceEntry2 = this.f13812a;
                    referenceEntry2.setPreviousInWriteQueue(referenceEntry2);
                    return;
                } else {
                    ReferenceEntry<K, V> nextInWriteQueue2 = nextInWriteQueue.getNextInWriteQueue();
                    LocalCache.v(nextInWriteQueue);
                    nextInWriteQueue = nextInWriteQueue2;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).getNextInWriteQueue() != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.f13812a.getNextInWriteQueue() == this.f13812a;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) { // from class: com.google.common.cache.LocalCache.WriteQueue.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.google.common.collect.AbstractSequentialIterator
                /* renamed from: b, reason: merged with bridge method [inline-methods] */
                public ReferenceEntry a(ReferenceEntry referenceEntry) {
                    ReferenceEntry<K, V> nextInWriteQueue = referenceEntry.getNextInWriteQueue();
                    if (nextInWriteQueue == WriteQueue.this.f13812a) {
                        return null;
                    }
                    return nextInWriteQueue;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        @CanIgnoreReturnValue
        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry<K, V> previousInWriteQueue = referenceEntry.getPreviousInWriteQueue();
            ReferenceEntry<K, V> nextInWriteQueue = referenceEntry.getNextInWriteQueue();
            LocalCache.b(previousInWriteQueue, nextInWriteQueue);
            LocalCache.v(referenceEntry);
            return nextInWriteQueue != NullEntry.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            int i2 = 0;
            for (ReferenceEntry<K, V> nextInWriteQueue = this.f13812a.getNextInWriteQueue(); nextInWriteQueue != this.f13812a; nextInWriteQueue = nextInWriteQueue.getNextInWriteQueue()) {
                i2++;
            }
            return i2;
        }

        @Override // java.util.Queue
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.b(referenceEntry.getPreviousInWriteQueue(), referenceEntry.getNextInWriteQueue());
            LocalCache.b(this.f13812a.getPreviousInWriteQueue(), referenceEntry);
            LocalCache.b(referenceEntry, this.f13812a);
            return true;
        }

        @Override // java.util.Queue
        @CheckForNull
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> nextInWriteQueue = this.f13812a.getNextInWriteQueue();
            if (nextInWriteQueue == this.f13812a) {
                return null;
            }
            return nextInWriteQueue;
        }

        @Override // java.util.Queue
        @CheckForNull
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> nextInWriteQueue = this.f13812a.getNextInWriteQueue();
            if (nextInWriteQueue == this.f13812a) {
                return null;
            }
            remove(nextInWriteQueue);
            return nextInWriteQueue;
        }
    }

    final class WriteThroughEntry implements Map.Entry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f13817a;

        /* renamed from: b, reason: collision with root package name */
        Object f13818b;

        WriteThroughEntry(Object obj, Object obj2) {
            this.f13817a = obj;
            this.f13818b = obj2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return this.f13817a.equals(entry.getKey()) && this.f13818b.equals(entry.getValue());
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return (K) this.f13817a;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return (V) this.f13818b;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.f13817a.hashCode() ^ this.f13818b.hashCode();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Map.Entry
        public V setValue(V v2) {
            V v3 = (V) LocalCache.this.put(this.f13817a, v2);
            this.f13818b = v2;
            return v3;
        }

        public String toString() {
            return getKey() + ContainerUtils.KEY_VALUE_DELIMITER + getValue();
        }
    }

    LocalCache(CacheBuilder cacheBuilder, CacheLoader cacheLoader) {
        this.f13731d = Math.min(cacheBuilder.a(), 65536);
        Strength strengthF = cacheBuilder.f();
        this.f13734g = strengthF;
        this.f13735h = cacheBuilder.m();
        this.f13732e = cacheBuilder.e();
        this.f13733f = cacheBuilder.l();
        long jG = cacheBuilder.g();
        this.f13736i = jG;
        this.f13737j = cacheBuilder.n();
        this.f13738k = cacheBuilder.b();
        this.f13739l = cacheBuilder.c();
        this.f13740m = cacheBuilder.h();
        RemovalListener removalListenerI = cacheBuilder.i();
        this.f13742o = removalListenerI;
        this.f13741n = removalListenerI == CacheBuilder.NullListener.INSTANCE ? e() : new ConcurrentLinkedQueue();
        this.f13743p = cacheBuilder.k(A());
        this.f13744q = EntryFactory.getFactory(strengthF, H(), L());
        this.f13745r = (AbstractCache.StatsCounter) cacheBuilder.j().get();
        this.f13746s = cacheLoader;
        int iMin = Math.min(cacheBuilder.d(), 1073741824);
        if (f() && !d()) {
            iMin = (int) Math.min(iMin, jG);
        }
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        int i5 = 1;
        while (i5 < this.f13731d && (!f() || i5 * 20 <= this.f13736i)) {
            i4++;
            i5 <<= 1;
        }
        this.f13729b = 32 - i4;
        this.f13728a = i5 - 1;
        this.f13730c = s(i5);
        int i6 = iMin / i5;
        while (i3 < (i6 * i5 < iMin ? i6 + 1 : i6)) {
            i3 <<= 1;
        }
        if (f()) {
            long j2 = this.f13736i;
            long j3 = i5;
            long j4 = (j2 / j3) + 1;
            long j5 = j2 % j3;
            while (true) {
                Segment[] segmentArr = this.f13730c;
                if (i2 >= segmentArr.length) {
                    return;
                }
                if (i2 == j5) {
                    j4--;
                }
                segmentArr[i2] = c(i3, j4, (AbstractCache.StatsCounter) cacheBuilder.j().get());
                i2++;
            }
        } else {
            while (true) {
                Segment[] segmentArr2 = this.f13730c;
                if (i2 >= segmentArr2.length) {
                    return;
                }
                segmentArr2[i2] = c(i3, -1L, (AbstractCache.StatsCounter) cacheBuilder.j().get());
                i2++;
            }
        }
    }

    static int E(int i2) {
        int i3 = i2 + ((i2 << 15) ^ (-12931));
        int i4 = i3 ^ (i3 >>> 10);
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >>> 6);
        int i7 = i6 + (i6 << 2) + (i6 << 14);
        return i7 ^ (i7 >>> 16);
    }

    static ValueReference G() {
        return f13726x;
    }

    static void a(ReferenceEntry referenceEntry, ReferenceEntry referenceEntry2) {
        referenceEntry.setNextInAccessQueue(referenceEntry2);
        referenceEntry2.setPreviousInAccessQueue(referenceEntry);
    }

    static void b(ReferenceEntry referenceEntry, ReferenceEntry referenceEntry2) {
        referenceEntry.setNextInWriteQueue(referenceEntry2);
        referenceEntry2.setPreviousInWriteQueue(referenceEntry);
    }

    static Queue e() {
        return f13727y;
    }

    static ReferenceEntry t() {
        return NullEntry.INSTANCE;
    }

    static void u(ReferenceEntry referenceEntry) {
        ReferenceEntry<K, V> referenceEntryT = t();
        referenceEntry.setNextInAccessQueue(referenceEntryT);
        referenceEntry.setPreviousInAccessQueue(referenceEntryT);
    }

    static void v(ReferenceEntry referenceEntry) {
        ReferenceEntry<K, V> referenceEntryT = t();
        referenceEntry.setNextInWriteQueue(referenceEntryT);
        referenceEntry.setPreviousInWriteQueue(referenceEntryT);
    }

    boolean A() {
        return B() || z();
    }

    boolean B() {
        return h() || D();
    }

    void C(Object obj) {
        int iN = n(Preconditions.checkNotNull(obj));
        F(iN).refresh(obj, iN, this.f13746s, false);
    }

    boolean D() {
        return this.f13740m > 0;
    }

    Segment F(int i2) {
        return this.f13730c[(i2 >>> this.f13729b) & this.f13728a];
    }

    boolean H() {
        return I() || z();
    }

    boolean I() {
        return g() || f();
    }

    boolean J() {
        return this.f13734g != Strength.STRONG;
    }

    boolean K() {
        return this.f13735h != Strength.STRONG;
    }

    boolean L() {
        return M() || B();
    }

    boolean M() {
        return h();
    }

    Segment c(int i2, long j2, AbstractCache.StatsCounter statsCounter) {
        return new Segment(this, i2, j2, statsCounter);
    }

    public void cleanUp() {
        for (Segment segment : this.f13730c) {
            segment.cleanUp();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        for (Segment segment : this.f13730c) {
            segment.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@CheckForNull Object obj) {
        if (obj == null) {
            return false;
        }
        int iN = n(obj);
        return F(iN).containsKey(obj, iN);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [int] */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1, types: [int] */
    /* JADX WARN: Type inference failed for: r15v3 */
    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@CheckForNull Object obj) {
        boolean z2 = false;
        if (obj == null) {
            return false;
        }
        long j2 = this.f13743p.read();
        Segment[] segmentArr = this.f13730c;
        long j3 = -1;
        int i2 = 0;
        while (i2 < 3) {
            int length = segmentArr.length;
            long j4 = 0;
            for (?? r12 = z2; r12 < length; r12++) {
                Segment segment = segmentArr[r12];
                int i3 = segment.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segment.table;
                for (?? r15 = z2; r15 < atomicReferenceArray.length(); r15++) {
                    ReferenceEntry<K, V> next = atomicReferenceArray.get(r15);
                    while (next != null) {
                        Segment[] segmentArr2 = segmentArr;
                        Object liveValue = segment.getLiveValue(next, j2);
                        long j5 = j2;
                        if (liveValue != null && this.f13733f.equivalent(obj, liveValue)) {
                            return true;
                        }
                        next = next.getNext();
                        segmentArr = segmentArr2;
                        j2 = j5;
                    }
                }
                j4 += segment.modCount;
                j2 = j2;
                z2 = false;
            }
            long j6 = j2;
            Segment[] segmentArr3 = segmentArr;
            if (j4 == j3) {
                return false;
            }
            i2++;
            j3 = j4;
            segmentArr = segmentArr3;
            j2 = j6;
            z2 = false;
        }
        return z2;
    }

    boolean d() {
        return this.f13737j != CacheBuilder.OneWeigher.INSTANCE;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @GwtIncompatible
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.f13749v;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.f13749v = entrySet;
        return entrySet;
    }

    boolean f() {
        return this.f13736i >= 0;
    }

    boolean g() {
        return this.f13738k > 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        if (obj == null) {
            return null;
        }
        int iN = n(obj);
        return (V) F(iN).get(obj, iN);
    }

    @CheckForNull
    public V getIfPresent(Object obj) {
        int iN = n(Preconditions.checkNotNull(obj));
        V v2 = (V) F(iN).get(obj, iN);
        if (v2 == null) {
            this.f13745r.recordMisses(1);
        } else {
            this.f13745r.recordHits(1);
        }
        return v2;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CheckForNull
    public V getOrDefault(@CheckForNull Object obj, @CheckForNull V v2) {
        V v3 = get(obj);
        return v3 != null ? v3 : v2;
    }

    boolean h() {
        return this.f13739l > 0;
    }

    Object i(Object obj, CacheLoader cacheLoader) {
        int iN = n(Preconditions.checkNotNull(obj));
        return F(iN).get(obj, iN, cacheLoader);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        Segment[] segmentArr = this.f13730c;
        long j2 = 0;
        for (Segment segment : segmentArr) {
            if (segment.count != 0) {
                return false;
            }
            j2 += r8.modCount;
        }
        if (j2 == 0) {
            return true;
        }
        for (Segment segment2 : segmentArr) {
            if (segment2.count != 0) {
                return false;
            }
            j2 -= r9.modCount;
        }
        return j2 == 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    ImmutableMap j(Iterable iterable) {
        LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
        LinkedHashSet linkedHashSetNewLinkedHashSet = Sets.newLinkedHashSet();
        int i2 = 0;
        int i3 = 0;
        for (Object obj : iterable) {
            V v2 = get(obj);
            if (!linkedHashMapNewLinkedHashMap.containsKey(obj)) {
                linkedHashMapNewLinkedHashMap.put(obj, v2);
                if (v2 == null) {
                    i3++;
                    linkedHashSetNewLinkedHashSet.add(obj);
                } else {
                    i2++;
                }
            }
        }
        try {
            if (!linkedHashSetNewLinkedHashSet.isEmpty()) {
                try {
                    Map mapQ = q(Collections.unmodifiableSet(linkedHashSetNewLinkedHashSet), this.f13746s);
                    for (Object obj2 : linkedHashSetNewLinkedHashSet) {
                        Object obj3 = mapQ.get(obj2);
                        if (obj3 == null) {
                            throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + obj2);
                        }
                        linkedHashMapNewLinkedHashMap.put(obj2, obj3);
                    }
                } catch (CacheLoader.UnsupportedLoadingOperationException unused) {
                    for (Object obj4 : linkedHashSetNewLinkedHashSet) {
                        i3--;
                        linkedHashMapNewLinkedHashMap.put(obj4, i(obj4, this.f13746s));
                    }
                }
            }
            ImmutableMap immutableMapCopyOf = ImmutableMap.copyOf((Map) linkedHashMapNewLinkedHashMap);
            this.f13745r.recordHits(i2);
            this.f13745r.recordMisses(i3);
            return immutableMapCopyOf;
        } catch (Throwable th) {
            this.f13745r.recordHits(i2);
            this.f13745r.recordMisses(i3);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    ImmutableMap k(Iterable iterable) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        int i2 = 0;
        int i3 = 0;
        for (Object obj : iterable) {
            V v2 = get(obj);
            if (v2 == null) {
                i3++;
            } else {
                builder.put(obj, v2);
                i2++;
            }
        }
        this.f13745r.recordHits(i2);
        this.f13745r.recordMisses(i3);
        return builder.buildKeepingLast();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.f13747t;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.f13747t = keySet;
        return keySet;
    }

    Object l(ReferenceEntry referenceEntry, long j2) {
        V v2;
        if (referenceEntry.getKey() == null || (v2 = referenceEntry.getValueReference().get()) == null || p(referenceEntry, j2)) {
            return null;
        }
        return v2;
    }

    Object m(Object obj) {
        return i(obj, this.f13746s);
    }

    int n(Object obj) {
        return E(this.f13732e.hash(obj));
    }

    void o(Iterable iterable) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    boolean p(ReferenceEntry referenceEntry, long j2) {
        Preconditions.checkNotNull(referenceEntry);
        if (!g() || j2 - referenceEntry.getAccessTime() < this.f13738k) {
            return h() && j2 - referenceEntry.getWriteTime() >= this.f13739l;
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @CheckForNull
    public V put(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int iN = n(k2);
        return (V) F(iN).put(k2, iN, v2, false);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CheckForNull
    public V putIfAbsent(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int iN = n(k2);
        return (V) F(iN).put(k2, iN, v2, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.util.Map q(java.util.Set r7, com.google.common.cache.CacheLoader r8) throws java.lang.Throwable {
        /*
            r6 = this;
            com.google.common.base.Preconditions.checkNotNull(r8)
            com.google.common.base.Preconditions.checkNotNull(r7)
            com.google.common.base.Stopwatch r0 = com.google.common.base.Stopwatch.createStarted()
            r1 = 1
            r2 = 0
            java.util.Map r7 = r8.loadAll(r7)     // Catch: java.lang.Throwable -> L8e java.lang.Error -> L91 java.lang.Exception -> L98 java.lang.RuntimeException -> L9f java.lang.InterruptedException -> La6 com.google.common.cache.CacheLoader.UnsupportedLoadingOperationException -> Lb4
            if (r7 == 0) goto L6c
            r0.stop()
            java.util.Set r3 = r7.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L1d:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L3c
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r5 = r4.getKey()
            java.lang.Object r4 = r4.getValue()
            if (r5 == 0) goto L3a
            if (r4 != 0) goto L36
            goto L3a
        L36:
            r6.put(r5, r4)
            goto L1d
        L3a:
            r2 = r1
            goto L1d
        L3c:
            if (r2 != 0) goto L4a
            com.google.common.cache.AbstractCache$StatsCounter r8 = r6.f13745r
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r0 = r0.elapsed(r1)
            r8.recordLoadSuccess(r0)
            return r7
        L4a:
            com.google.common.cache.AbstractCache$StatsCounter r7 = r6.f13745r
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r0 = r0.elapsed(r1)
            r7.recordLoadException(r0)
            com.google.common.cache.CacheLoader$InvalidCacheLoadException r7 = new com.google.common.cache.CacheLoader$InvalidCacheLoadException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r8)
            java.lang.String r8 = " returned null keys or values from loadAll"
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            r7.<init>(r8)
            throw r7
        L6c:
            com.google.common.cache.AbstractCache$StatsCounter r7 = r6.f13745r
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r0 = r0.elapsed(r1)
            r7.recordLoadException(r0)
            com.google.common.cache.CacheLoader$InvalidCacheLoadException r7 = new com.google.common.cache.CacheLoader$InvalidCacheLoadException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r8)
            java.lang.String r8 = " returned null map from loadAll"
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            r7.<init>(r8)
            throw r7
        L8e:
            r7 = move-exception
            r1 = r2
            goto Lb7
        L91:
            r7 = move-exception
            com.google.common.util.concurrent.ExecutionError r8 = new com.google.common.util.concurrent.ExecutionError     // Catch: java.lang.Throwable -> L8e
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L8e
            throw r8     // Catch: java.lang.Throwable -> L8e
        L98:
            r7 = move-exception
            java.util.concurrent.ExecutionException r8 = new java.util.concurrent.ExecutionException     // Catch: java.lang.Throwable -> L8e
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L8e
            throw r8     // Catch: java.lang.Throwable -> L8e
        L9f:
            r7 = move-exception
            com.google.common.util.concurrent.UncheckedExecutionException r8 = new com.google.common.util.concurrent.UncheckedExecutionException     // Catch: java.lang.Throwable -> L8e
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L8e
            throw r8     // Catch: java.lang.Throwable -> L8e
        La6:
            r7 = move-exception
            java.lang.Thread r8 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> L8e
            r8.interrupt()     // Catch: java.lang.Throwable -> L8e
            java.util.concurrent.ExecutionException r8 = new java.util.concurrent.ExecutionException     // Catch: java.lang.Throwable -> L8e
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L8e
            throw r8     // Catch: java.lang.Throwable -> L8e
        Lb4:
            r7 = move-exception
            throw r7     // Catch: java.lang.Throwable -> Lb6
        Lb6:
            r7 = move-exception
        Lb7:
            if (r1 != 0) goto Lc4
            com.google.common.cache.AbstractCache$StatsCounter r8 = r6.f13745r
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r0 = r0.elapsed(r1)
            r8.recordLoadException(r0)
        Lc4:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.q(java.util.Set, com.google.common.cache.CacheLoader):java.util.Map");
    }

    long r() {
        long jMax = 0;
        for (int i2 = 0; i2 < this.f13730c.length; i2++) {
            jMax += Math.max(0, r0[i2].count);
        }
        return jMax;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @CheckForNull
    public V remove(@CheckForNull Object obj) {
        if (obj == null) {
            return null;
        }
        int iN = n(obj);
        return (V) F(iN).remove(obj, iN);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public boolean replace(K k2, @CheckForNull V v2, V v3) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v3);
        if (v2 == null) {
            return false;
        }
        int iN = n(k2);
        return F(iN).replace(k2, iN, v2, v3);
    }

    final Segment[] s(int i2) {
        return new Segment[i2];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return Ints.saturatedCast(r());
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.f13748u;
        if (collection != null) {
            return collection;
        }
        Values values = new Values();
        this.f13748u = values;
        return values;
    }

    void w() {
        while (true) {
            RemovalNotification<K, V> removalNotification = (RemovalNotification) this.f13741n.poll();
            if (removalNotification == null) {
                return;
            }
            try {
                this.f13742o.onRemoval(removalNotification);
            } catch (Throwable th) {
                f13725w.log(Level.WARNING, "Exception thrown by removal listener", th);
            }
        }
    }

    void x(ReferenceEntry referenceEntry) {
        int hash = referenceEntry.getHash();
        F(hash).reclaimKey(referenceEntry, hash);
    }

    void y(ValueReference valueReference) {
        ReferenceEntry<K, V> entry = valueReference.getEntry();
        int hash = entry.getHash();
        F(hash).reclaimValue(entry.getKey(), hash, valueReference);
    }

    boolean z() {
        return g();
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public boolean remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        int iN = n(obj);
        return F(iN).remove(obj, iN, obj2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    @CheckForNull
    public V replace(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int iN = n(k2);
        return (V) F(iN).replace(k2, iN, v2);
    }

    static class Segment<K, V> extends ReentrantLock {

        @GuardedBy("this")
        final Queue<ReferenceEntry<K, V>> accessQueue;
        volatile int count;

        @CheckForNull
        final ReferenceQueue<K> keyReferenceQueue;

        @Weak
        final LocalCache<K, V> map;
        final long maxSegmentWeight;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final AbstractCache.StatsCounter statsCounter;

        @CheckForNull
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        int threshold;

        @GuardedBy("this")
        long totalWeight;

        @CheckForNull
        final ReferenceQueue<V> valueReferenceQueue;

        @GuardedBy("this")
        final Queue<ReferenceEntry<K, V>> writeQueue;

        Segment(LocalCache<K, V> localCache, int i2, long j2, AbstractCache.StatsCounter statsCounter) {
            this.map = localCache;
            this.maxSegmentWeight = j2;
            this.statsCounter = (AbstractCache.StatsCounter) Preconditions.checkNotNull(statsCounter);
            initTable(newEntryArray(i2));
            this.keyReferenceQueue = localCache.J() ? new ReferenceQueue<>() : null;
            this.valueReferenceQueue = localCache.K() ? new ReferenceQueue<>() : null;
            this.recencyQueue = localCache.I() ? new ConcurrentLinkedQueue<>() : LocalCache.e();
            this.writeQueue = localCache.M() ? new WriteQueue<>() : LocalCache.e();
            this.accessQueue = localCache.I() ? new AccessQueue<>() : LocalCache.e();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$loadAsync$0(Object obj, int i2, LoadingValueReference loadingValueReference, ListenableFuture listenableFuture) {
            try {
                getAndRecordStats(obj, i2, loadingValueReference, listenableFuture);
            } catch (Throwable th) {
                LocalCache.f13725w.log(Level.WARNING, "Exception thrown during refresh", th);
                loadingValueReference.setException(th);
            }
        }

        void cleanUp() {
            runLockedCleanup(this.map.f13743p.read());
            runUnlockedCleanup();
        }

        void clear() {
            if (this.count != 0) {
                lock();
                try {
                    preWriteCleanup(this.map.f13743p.read());
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                    for (int i2 = 0; i2 < atomicReferenceArray.length(); i2++) {
                        for (ReferenceEntry<K, V> next = atomicReferenceArray.get(i2); next != null; next = next.getNext()) {
                            if (next.getValueReference().isActive()) {
                                K key = next.getKey();
                                V v2 = next.getValueReference().get();
                                enqueueNotification(key, next.getHash(), v2, next.getValueReference().getWeight(), (key == null || v2 == null) ? RemovalCause.COLLECTED : RemovalCause.EXPLICIT);
                            }
                        }
                    }
                    for (int i3 = 0; i3 < atomicReferenceArray.length(); i3++) {
                        atomicReferenceArray.set(i3, null);
                    }
                    clearReferenceQueues();
                    this.writeQueue.clear();
                    this.accessQueue.clear();
                    this.readCount.set(0);
                    this.modCount++;
                    this.count = 0;
                    unlock();
                    postWriteCleanup();
                } catch (Throwable th) {
                    unlock();
                    postWriteCleanup();
                    throw th;
                }
            }
        }

        void clearKeyReferenceQueue() {
            while (this.keyReferenceQueue.poll() != null) {
            }
        }

        void clearReferenceQueues() {
            if (this.map.J()) {
                clearKeyReferenceQueue();
            }
            if (this.map.K()) {
                clearValueReferenceQueue();
            }
        }

        void clearValueReferenceQueue() {
            while (this.valueReferenceQueue.poll() != null) {
            }
        }

        boolean containsKey(Object obj, int i2) {
            try {
                if (this.count == 0) {
                    return false;
                }
                ReferenceEntry<K, V> liveEntry = getLiveEntry(obj, i2, this.map.f13743p.read());
                if (liveEntry == null) {
                    return false;
                }
                return liveEntry.getValueReference().get() != null;
            } finally {
                postReadCleanup();
            }
        }

        @VisibleForTesting
        boolean containsValue(Object obj) {
            try {
                if (this.count != 0) {
                    long j2 = this.map.f13743p.read();
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                    int length = atomicReferenceArray.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        for (ReferenceEntry<K, V> next = atomicReferenceArray.get(i2); next != null; next = next.getNext()) {
                            V liveValue = getLiveValue(next, j2);
                            if (liveValue != null && this.map.f13733f.equivalent(obj, liveValue)) {
                                postReadCleanup();
                                return true;
                            }
                        }
                    }
                }
                return false;
            } finally {
                postReadCleanup();
            }
        }

        @CheckForNull
        @GuardedBy("this")
        ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            K key = referenceEntry.getKey();
            if (key == null) {
                return null;
            }
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            V v2 = valueReference.get();
            if (v2 == null && valueReference.isActive()) {
                return null;
            }
            ReferenceEntry<K, V> referenceEntryCopyEntry = this.map.f13744q.copyEntry(this, referenceEntry, referenceEntry2, key);
            referenceEntryCopyEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, v2, referenceEntryCopyEntry));
            return referenceEntryCopyEntry;
        }

        @GuardedBy("this")
        void drainKeyReferenceQueue() {
            int i2 = 0;
            do {
                Reference<? extends K> referencePoll = this.keyReferenceQueue.poll();
                if (referencePoll == null) {
                    return;
                }
                this.map.x((ReferenceEntry) referencePoll);
                i2++;
            } while (i2 != 16);
        }

        @GuardedBy("this")
        void drainRecencyQueue() {
            while (true) {
                ReferenceEntry<K, V> referenceEntryPoll = this.recencyQueue.poll();
                if (referenceEntryPoll == null) {
                    return;
                }
                if (this.accessQueue.contains(referenceEntryPoll)) {
                    this.accessQueue.add(referenceEntryPoll);
                }
            }
        }

        @GuardedBy("this")
        void drainReferenceQueues() {
            if (this.map.J()) {
                drainKeyReferenceQueue();
            }
            if (this.map.K()) {
                drainValueReferenceQueue();
            }
        }

        @GuardedBy("this")
        void drainValueReferenceQueue() {
            int i2 = 0;
            do {
                Reference<? extends V> referencePoll = this.valueReferenceQueue.poll();
                if (referencePoll == null) {
                    return;
                }
                this.map.y((ValueReference) referencePoll);
                i2++;
            } while (i2 != 16);
        }

        @GuardedBy("this")
        void enqueueNotification(@CheckForNull K k2, int i2, @CheckForNull V v2, int i3, RemovalCause removalCause) {
            this.totalWeight -= i3;
            if (removalCause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.f13741n != LocalCache.f13727y) {
                this.map.f13741n.offer(RemovalNotification.create(k2, v2, removalCause));
            }
        }

        @GuardedBy("this")
        void evictEntries(ReferenceEntry<K, V> referenceEntry) {
            if (this.map.f()) {
                drainRecencyQueue();
                if (referenceEntry.getValueReference().getWeight() > this.maxSegmentWeight && !removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.SIZE)) {
                    throw new AssertionError();
                }
                while (this.totalWeight > this.maxSegmentWeight) {
                    ReferenceEntry<K, V> nextEvictable = getNextEvictable();
                    if (!removeEntry(nextEvictable, nextEvictable.getHash(), RemovalCause.SIZE)) {
                        throw new AssertionError();
                    }
                }
            }
        }

        @GuardedBy("this")
        void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int length = atomicReferenceArray.length();
            if (length >= 1073741824) {
                return;
            }
            int i2 = this.count;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArrayNewEntryArray = newEntryArray(length << 1);
            this.threshold = (atomicReferenceArrayNewEntryArray.length() * 3) / 4;
            int length2 = atomicReferenceArrayNewEntryArray.length() - 1;
            for (int i3 = 0; i3 < length; i3++) {
                ReferenceEntry<K, V> next = atomicReferenceArray.get(i3);
                if (next != null) {
                    ReferenceEntry<K, V> next2 = next.getNext();
                    int hash = next.getHash() & length2;
                    if (next2 == null) {
                        atomicReferenceArrayNewEntryArray.set(hash, next);
                    } else {
                        ReferenceEntry<K, V> referenceEntry = next;
                        while (next2 != null) {
                            int hash2 = next2.getHash() & length2;
                            if (hash2 != hash) {
                                referenceEntry = next2;
                                hash = hash2;
                            }
                            next2 = next2.getNext();
                        }
                        atomicReferenceArrayNewEntryArray.set(hash, referenceEntry);
                        while (next != referenceEntry) {
                            int hash3 = next.getHash() & length2;
                            ReferenceEntry<K, V> referenceEntryCopyEntry = copyEntry(next, atomicReferenceArrayNewEntryArray.get(hash3));
                            if (referenceEntryCopyEntry != null) {
                                atomicReferenceArrayNewEntryArray.set(hash3, referenceEntryCopyEntry);
                            } else {
                                removeCollectedEntry(next);
                                i2--;
                            }
                            next = next.getNext();
                        }
                    }
                }
            }
            this.table = atomicReferenceArrayNewEntryArray;
            this.count = i2;
        }

        @GuardedBy("this")
        void expireEntries(long j2) {
            ReferenceEntry<K, V> referenceEntryPeek;
            ReferenceEntry<K, V> referenceEntryPeek2;
            drainRecencyQueue();
            do {
                referenceEntryPeek = this.writeQueue.peek();
                if (referenceEntryPeek == null || !this.map.p(referenceEntryPeek, j2)) {
                    do {
                        referenceEntryPeek2 = this.accessQueue.peek();
                        if (referenceEntryPeek2 == null || !this.map.p(referenceEntryPeek2, j2)) {
                            return;
                        }
                    } while (removeEntry(referenceEntryPeek2, referenceEntryPeek2.getHash(), RemovalCause.EXPIRED));
                    throw new AssertionError();
                }
            } while (removeEntry(referenceEntryPeek, referenceEntryPeek.getHash(), RemovalCause.EXPIRED));
            throw new AssertionError();
        }

        @CanIgnoreReturnValue
        V get(K k2, int i2, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            ReferenceEntry<K, V> entry;
            Preconditions.checkNotNull(k2);
            Preconditions.checkNotNull(cacheLoader);
            try {
                try {
                    if (this.count != 0 && (entry = getEntry(k2, i2)) != null) {
                        long j2 = this.map.f13743p.read();
                        V liveValue = getLiveValue(entry, j2);
                        if (liveValue != null) {
                            recordRead(entry, j2);
                            this.statsCounter.recordHits(1);
                            return scheduleRefresh(entry, k2, i2, liveValue, j2, cacheLoader);
                        }
                        ValueReference<K, V> valueReference = entry.getValueReference();
                        if (valueReference.isLoading()) {
                            return waitForLoadingValue(entry, k2, valueReference);
                        }
                    }
                    return lockedGetOrLoad(k2, i2, cacheLoader);
                } catch (ExecutionException e2) {
                    Throwable cause = e2.getCause();
                    if (cause instanceof Error) {
                        throw new ExecutionError((Error) cause);
                    }
                    if (cause instanceof RuntimeException) {
                        throw new UncheckedExecutionException(cause);
                    }
                    throw e2;
                }
            } finally {
                postReadCleanup();
            }
        }

        @CanIgnoreReturnValue
        V getAndRecordStats(K k2, int i2, LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> listenableFuture) throws Throwable {
            V v2;
            try {
                v2 = (V) Uninterruptibles.getUninterruptibly(listenableFuture);
                try {
                    if (v2 != null) {
                        this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
                        storeLoadedValue(k2, i2, loadingValueReference, v2);
                        return v2;
                    }
                    throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + k2 + ".");
                } catch (Throwable th) {
                    th = th;
                    if (v2 == null) {
                        this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                        removeLoadingValue(k2, i2, loadingValueReference);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                v2 = null;
            }
        }

        @CheckForNull
        ReferenceEntry<K, V> getEntry(Object obj, int i2) {
            for (ReferenceEntry<K, V> first = getFirst(i2); first != null; first = first.getNext()) {
                if (first.getHash() == i2) {
                    K key = first.getKey();
                    if (key == null) {
                        tryDrainReferenceQueues();
                    } else if (this.map.f13732e.equivalent(obj, key)) {
                        return first;
                    }
                }
            }
            return null;
        }

        ReferenceEntry<K, V> getFirst(int i2) {
            return this.table.get(i2 & (r0.length() - 1));
        }

        @CheckForNull
        ReferenceEntry<K, V> getLiveEntry(Object obj, int i2, long j2) {
            ReferenceEntry<K, V> entry = getEntry(obj, i2);
            if (entry == null) {
                return null;
            }
            if (!this.map.p(entry, j2)) {
                return entry;
            }
            tryExpireEntries(j2);
            return null;
        }

        V getLiveValue(ReferenceEntry<K, V> referenceEntry, long j2) {
            if (referenceEntry.getKey() == null) {
                tryDrainReferenceQueues();
                return null;
            }
            V v2 = referenceEntry.getValueReference().get();
            if (v2 == null) {
                tryDrainReferenceQueues();
                return null;
            }
            if (!this.map.p(referenceEntry, j2)) {
                return v2;
            }
            tryExpireEntries(j2);
            return null;
        }

        @GuardedBy("this")
        ReferenceEntry<K, V> getNextEvictable() {
            for (ReferenceEntry<K, V> referenceEntry : this.accessQueue) {
                if (referenceEntry.getValueReference().getWeight() > 0) {
                    return referenceEntry;
                }
            }
            throw new AssertionError();
        }

        void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray) {
            this.threshold = (atomicReferenceArray.length() * 3) / 4;
            if (!this.map.d()) {
                int i2 = this.threshold;
                if (i2 == this.maxSegmentWeight) {
                    this.threshold = i2 + 1;
                }
            }
            this.table = atomicReferenceArray;
        }

        /* JADX WARN: Finally extract failed */
        @CheckForNull
        LoadingValueReference<K, V> insertLoadingValueReference(K k2, int i2, boolean z2) {
            lock();
            try {
                long j2 = this.map.f13743p.read();
                preWriteCleanup(j2);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry<K, V> referenceEntry = (ReferenceEntry) atomicReferenceArray.get(length);
                for (ReferenceEntry next = referenceEntry; next != null; next = next.getNext()) {
                    Object key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.f13732e.equivalent(k2, key)) {
                        ValueReference<K, V> valueReference = next.getValueReference();
                        if (!valueReference.isLoading() && (!z2 || j2 - next.getWriteTime() >= this.map.f13740m)) {
                            this.modCount++;
                            LoadingValueReference<K, V> loadingValueReference = new LoadingValueReference<>(valueReference);
                            next.setValueReference(loadingValueReference);
                            unlock();
                            postWriteCleanup();
                            return loadingValueReference;
                        }
                        unlock();
                        postWriteCleanup();
                        return null;
                    }
                }
                this.modCount++;
                LoadingValueReference<K, V> loadingValueReference2 = new LoadingValueReference<>();
                ReferenceEntry<K, V> referenceEntryNewEntry = newEntry(k2, i2, referenceEntry);
                referenceEntryNewEntry.setValueReference(loadingValueReference2);
                atomicReferenceArray.set(length, referenceEntryNewEntry);
                unlock();
                postWriteCleanup();
                return loadingValueReference2;
            } catch (Throwable th) {
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        ListenableFuture<V> loadAsync(final K k2, final int i2, final LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) {
            final ListenableFuture<V> listenableFutureLoadFuture = loadingValueReference.loadFuture(k2, cacheLoader);
            listenableFutureLoadFuture.addListener(new Runnable() { // from class: com.google.common.cache.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13825a.lambda$loadAsync$0(k2, i2, loadingValueReference, listenableFutureLoadFuture);
                }
            }, MoreExecutors.directExecutor());
            return listenableFutureLoadFuture;
        }

        V loadSync(K k2, int i2, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            return getAndRecordStats(k2, i2, loadingValueReference, loadingValueReference.loadFuture(k2, cacheLoader));
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x00a3, code lost:
        
            if (r1 == false) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x00a5, code lost:
        
            r15 = new com.google.common.cache.LocalCache.LoadingValueReference<>();
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x00aa, code lost:
        
            if (r14 != null) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x00ac, code lost:
        
            r14 = newEntry(r18, r19, r13);
            r14.setValueReference(r15);
            r11.set(r12, r14);
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x00b7, code lost:
        
            r14.setValueReference(r15);
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x00ba, code lost:
        
            unlock();
            postWriteCleanup();
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x00c0, code lost:
        
            if (r1 == false) goto L48;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x00c2, code lost:
        
            monitor-enter(r14);
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00c5, code lost:
        
            r0 = loadSync(r18, r19, r15, r20);
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00c9, code lost:
        
            monitor-exit(r14);
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00cf, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00d3, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x00d4, code lost:
        
            r17.statsCounter.recordMisses(1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00d9, code lost:
        
            throw r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00de, code lost:
        
            return waitForLoadingValue(r14, r18, r2);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        V lockedGetOrLoad(K r18, int r19, com.google.common.cache.CacheLoader<? super K, V> r20) throws java.util.concurrent.ExecutionException {
            /*
                Method dump skipped, instructions count: 230
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.lockedGetOrLoad(java.lang.Object, int, com.google.common.cache.CacheLoader):java.lang.Object");
        }

        /* JADX WARN: Multi-variable type inference failed */
        @GuardedBy("this")
        ReferenceEntry<K, V> newEntry(K k2, int i2, @CheckForNull ReferenceEntry<K, V> referenceEntry) {
            return this.map.f13744q.newEntry(this, Preconditions.checkNotNull(k2), i2, referenceEntry);
        }

        AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int i2) {
            return new AtomicReferenceArray<>(i2);
        }

        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                cleanUp();
            }
        }

        void postWriteCleanup() {
            runUnlockedCleanup();
        }

        @GuardedBy("this")
        void preWriteCleanup(long j2) {
            runLockedCleanup(j2);
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x008d, code lost:
        
            unlock();
            postWriteCleanup();
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0093, code lost:
        
            return null;
         */
        @com.google.errorprone.annotations.CanIgnoreReturnValue
        @javax.annotation.CheckForNull
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        V put(K r15, int r16, V r17, boolean r18) {
            /*
                Method dump skipped, instructions count: 238
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.put(java.lang.Object, int, java.lang.Object, boolean):java.lang.Object");
        }

        @CanIgnoreReturnValue
        boolean reclaimKey(ReferenceEntry<K, V> referenceEntry, int i2) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry<K, V> referenceEntry2 = atomicReferenceArray.get(length);
                for (ReferenceEntry<K, V> next = referenceEntry2; next != null; next = next.getNext()) {
                    if (next == referenceEntry) {
                        this.modCount++;
                        ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = removeValueFromChain(referenceEntry2, next, next.getKey(), i2, next.getValueReference().get(), next.getValueReference(), RemovalCause.COLLECTED);
                        int i3 = this.count - 1;
                        atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                        this.count = i3;
                        return true;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        @CanIgnoreReturnValue
        boolean reclaimValue(K k2, int i2, ValueReference<K, V> valueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                for (ReferenceEntry<K, V> next = referenceEntry; next != null; next = next.getNext()) {
                    K key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.f13732e.equivalent(k2, key)) {
                        if (next.getValueReference() != valueReference) {
                            unlock();
                            if (!isHeldByCurrentThread()) {
                                postWriteCleanup();
                            }
                            return false;
                        }
                        this.modCount++;
                        ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = removeValueFromChain(referenceEntry, next, key, i2, valueReference.get(), valueReference, RemovalCause.COLLECTED);
                        int i3 = this.count - 1;
                        atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                        this.count = i3;
                        return true;
                    }
                }
                unlock();
                if (!isHeldByCurrentThread()) {
                    postWriteCleanup();
                }
                return false;
            } finally {
                unlock();
                if (!isHeldByCurrentThread()) {
                    postWriteCleanup();
                }
            }
        }

        @GuardedBy("this")
        void recordLockedRead(ReferenceEntry<K, V> referenceEntry, long j2) {
            if (this.map.z()) {
                referenceEntry.setAccessTime(j2);
            }
            this.accessQueue.add(referenceEntry);
        }

        void recordRead(ReferenceEntry<K, V> referenceEntry, long j2) {
            if (this.map.z()) {
                referenceEntry.setAccessTime(j2);
            }
            this.recencyQueue.add(referenceEntry);
        }

        @GuardedBy("this")
        void recordWrite(ReferenceEntry<K, V> referenceEntry, int i2, long j2) {
            drainRecencyQueue();
            this.totalWeight += i2;
            if (this.map.z()) {
                referenceEntry.setAccessTime(j2);
            }
            if (this.map.B()) {
                referenceEntry.setWriteTime(j2);
            }
            this.accessQueue.add(referenceEntry);
            this.writeQueue.add(referenceEntry);
        }

        @CanIgnoreReturnValue
        @CheckForNull
        V refresh(K k2, int i2, CacheLoader<? super K, V> cacheLoader, boolean z2) {
            LoadingValueReference<K, V> loadingValueReferenceInsertLoadingValueReference = insertLoadingValueReference(k2, i2, z2);
            if (loadingValueReferenceInsertLoadingValueReference == null) {
                return null;
            }
            ListenableFuture<V> listenableFutureLoadAsync = loadAsync(k2, i2, loadingValueReferenceInsertLoadingValueReference, cacheLoader);
            if (listenableFutureLoadAsync.isDone()) {
                try {
                    return (V) Uninterruptibles.getUninterruptibly(listenableFutureLoadAsync);
                } catch (Throwable unused) {
                }
            }
            return null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0038, code lost:
        
            r9 = r5.getValueReference();
            r12 = r9.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0040, code lost:
        
            if (r12 == null) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x0042, code lost:
        
            r2 = com.google.common.cache.RemovalCause.EXPLICIT;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0044, code lost:
        
            r10 = r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x004c, code lost:
        
            if (r9.isActive() == false) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x004e, code lost:
        
            r2 = com.google.common.cache.RemovalCause.COLLECTED;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:
        
            r11.modCount++;
            r13 = removeValueFromChain(r4, r5, r6, r13, r12, r9, r10);
            r2 = r11.count - 1;
            r0.set(r1, r13);
            r11.count = r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x0067, code lost:
        
            unlock();
            postWriteCleanup();
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x006d, code lost:
        
            return r12;
         */
        @javax.annotation.CheckForNull
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        V remove(java.lang.Object r12, int r13) {
            /*
                r11 = this;
                r11.lock()
                com.google.common.cache.LocalCache<K, V> r0 = r11.map     // Catch: java.lang.Throwable -> L46
                com.google.common.base.Ticker r0 = r0.f13743p     // Catch: java.lang.Throwable -> L46
                long r0 = r0.read()     // Catch: java.lang.Throwable -> L46
                r11.preWriteCleanup(r0)     // Catch: java.lang.Throwable -> L46
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r0 = r11.table     // Catch: java.lang.Throwable -> L46
                int r1 = r0.length()     // Catch: java.lang.Throwable -> L46
                int r1 = r1 + (-1)
                r1 = r1 & r13
                java.lang.Object r2 = r0.get(r1)     // Catch: java.lang.Throwable -> L46
                r4 = r2
                com.google.common.cache.ReferenceEntry r4 = (com.google.common.cache.ReferenceEntry) r4     // Catch: java.lang.Throwable -> L46
                r5 = r4
            L1f:
                r2 = 0
                if (r5 == 0) goto L6e
                java.lang.Object r6 = r5.getKey()     // Catch: java.lang.Throwable -> L46
                int r3 = r5.getHash()     // Catch: java.lang.Throwable -> L46
                if (r3 != r13) goto L75
                if (r6 == 0) goto L75
                com.google.common.cache.LocalCache<K, V> r3 = r11.map     // Catch: java.lang.Throwable -> L46
                com.google.common.base.Equivalence r3 = r3.f13732e     // Catch: java.lang.Throwable -> L46
                boolean r3 = r3.equivalent(r12, r6)     // Catch: java.lang.Throwable -> L46
                if (r3 == 0) goto L75
                com.google.common.cache.LocalCache$ValueReference r9 = r5.getValueReference()     // Catch: java.lang.Throwable -> L46
                java.lang.Object r12 = r9.get()     // Catch: java.lang.Throwable -> L46
                if (r12 == 0) goto L48
                com.google.common.cache.RemovalCause r2 = com.google.common.cache.RemovalCause.EXPLICIT     // Catch: java.lang.Throwable -> L46
            L44:
                r10 = r2
                goto L51
            L46:
                r12 = move-exception
                goto L7a
            L48:
                boolean r3 = r9.isActive()     // Catch: java.lang.Throwable -> L46
                if (r3 == 0) goto L6e
                com.google.common.cache.RemovalCause r2 = com.google.common.cache.RemovalCause.COLLECTED     // Catch: java.lang.Throwable -> L46
                goto L44
            L51:
                int r2 = r11.modCount     // Catch: java.lang.Throwable -> L46
                int r2 = r2 + 1
                r11.modCount = r2     // Catch: java.lang.Throwable -> L46
                r3 = r11
                r7 = r13
                r8 = r12
                com.google.common.cache.ReferenceEntry r13 = r3.removeValueFromChain(r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L46
                int r2 = r11.count     // Catch: java.lang.Throwable -> L46
                int r2 = r2 + (-1)
                r0.set(r1, r13)     // Catch: java.lang.Throwable -> L46
                r11.count = r2     // Catch: java.lang.Throwable -> L46
                r11.unlock()
                r11.postWriteCleanup()
                return r12
            L6e:
                r11.unlock()
                r11.postWriteCleanup()
                return r2
            L75:
                com.google.common.cache.ReferenceEntry r5 = r5.getNext()     // Catch: java.lang.Throwable -> L46
                goto L1f
            L7a:
                r11.unlock()
                r11.postWriteCleanup()
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.remove(java.lang.Object, int):java.lang.Object");
        }

        @GuardedBy("this")
        void removeCollectedEntry(ReferenceEntry<K, V> referenceEntry) {
            enqueueNotification(referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry.getValueReference().get(), referenceEntry.getValueReference().getWeight(), RemovalCause.COLLECTED);
            this.writeQueue.remove(referenceEntry);
            this.accessQueue.remove(referenceEntry);
        }

        @VisibleForTesting
        @CanIgnoreReturnValue
        @GuardedBy("this")
        boolean removeEntry(ReferenceEntry<K, V> referenceEntry, int i2, RemovalCause removalCause) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int length = (atomicReferenceArray.length() - 1) & i2;
            ReferenceEntry<K, V> referenceEntry2 = atomicReferenceArray.get(length);
            for (ReferenceEntry<K, V> next = referenceEntry2; next != null; next = next.getNext()) {
                if (next == referenceEntry) {
                    this.modCount++;
                    ReferenceEntry<K, V> referenceEntryRemoveValueFromChain = removeValueFromChain(referenceEntry2, next, next.getKey(), i2, next.getValueReference().get(), next.getValueReference(), removalCause);
                    int i3 = this.count - 1;
                    atomicReferenceArray.set(length, referenceEntryRemoveValueFromChain);
                    this.count = i3;
                    return true;
                }
            }
            return false;
        }

        @CheckForNull
        @GuardedBy("this")
        ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            int i2 = this.count;
            ReferenceEntry<K, V> next = referenceEntry2.getNext();
            while (referenceEntry != referenceEntry2) {
                ReferenceEntry<K, V> referenceEntryCopyEntry = copyEntry(referenceEntry, next);
                if (referenceEntryCopyEntry != null) {
                    next = referenceEntryCopyEntry;
                } else {
                    removeCollectedEntry(referenceEntry);
                    i2--;
                }
                referenceEntry = referenceEntry.getNext();
            }
            this.count = i2;
            return next;
        }

        @CanIgnoreReturnValue
        boolean removeLoadingValue(K k2, int i2, LoadingValueReference<K, V> loadingValueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry<K, V> next = referenceEntry;
                while (true) {
                    if (next == null) {
                        break;
                    }
                    K key = next.getKey();
                    if (next.getHash() != i2 || key == null || !this.map.f13732e.equivalent(k2, key)) {
                        next = next.getNext();
                    } else if (next.getValueReference() == loadingValueReference) {
                        if (loadingValueReference.isActive()) {
                            next.setValueReference(loadingValueReference.getOldValue());
                        } else {
                            atomicReferenceArray.set(length, removeEntryFromChain(referenceEntry, next));
                        }
                        unlock();
                        postWriteCleanup();
                        return true;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } catch (Throwable th) {
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        @CheckForNull
        @GuardedBy("this")
        ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, @CheckForNull K k2, int i2, V v2, ValueReference<K, V> valueReference, RemovalCause removalCause) {
            enqueueNotification(k2, i2, v2, valueReference.getWeight(), removalCause);
            this.writeQueue.remove(referenceEntry2);
            this.accessQueue.remove(referenceEntry2);
            if (!valueReference.isLoading()) {
                return removeEntryFromChain(referenceEntry, referenceEntry2);
            }
            valueReference.notifyNewValue(null);
            return referenceEntry;
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0072, code lost:
        
            return false;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        boolean replace(K r18, int r19, V r20, V r21) {
            /*
                r17 = this;
                r9 = r17
                r0 = r19
                r17.lock()
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> L6a
                com.google.common.base.Ticker r1 = r1.f13743p     // Catch: java.lang.Throwable -> L6a
                long r7 = r1.read()     // Catch: java.lang.Throwable -> L6a
                r9.preWriteCleanup(r7)     // Catch: java.lang.Throwable -> L6a
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r10 = r9.table     // Catch: java.lang.Throwable -> L6a
                int r1 = r10.length()     // Catch: java.lang.Throwable -> L6a
                r11 = 1
                int r1 = r1 - r11
                r12 = r0 & r1
                java.lang.Object r1 = r10.get(r12)     // Catch: java.lang.Throwable -> L6a
                r2 = r1
                com.google.common.cache.ReferenceEntry r2 = (com.google.common.cache.ReferenceEntry) r2     // Catch: java.lang.Throwable -> L6a
                r13 = r2
            L24:
                r14 = 0
                if (r13 == 0) goto L6c
                java.lang.Object r4 = r13.getKey()     // Catch: java.lang.Throwable -> L6a
                int r1 = r13.getHash()     // Catch: java.lang.Throwable -> L6a
                if (r1 != r0) goto Lb1
                if (r4 == 0) goto Lb1
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> L6a
                com.google.common.base.Equivalence r1 = r1.f13732e     // Catch: java.lang.Throwable -> L6a
                r15 = r18
                boolean r1 = r1.equivalent(r15, r4)     // Catch: java.lang.Throwable -> L6a
                if (r1 == 0) goto Lae
                com.google.common.cache.LocalCache$ValueReference r16 = r13.getValueReference()     // Catch: java.lang.Throwable -> L6a
                java.lang.Object r6 = r16.get()     // Catch: java.lang.Throwable -> L6a
                if (r6 != 0) goto L73
                boolean r1 = r16.isActive()     // Catch: java.lang.Throwable -> L6a
                if (r1 == 0) goto L6c
                int r1 = r9.modCount     // Catch: java.lang.Throwable -> L6a
                int r1 = r1 + r11
                r9.modCount = r1     // Catch: java.lang.Throwable -> L6a
                com.google.common.cache.RemovalCause r8 = com.google.common.cache.RemovalCause.COLLECTED     // Catch: java.lang.Throwable -> L6a
                r1 = r17
                r3 = r13
                r5 = r19
                r7 = r16
                com.google.common.cache.ReferenceEntry r0 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L6a
                int r1 = r9.count     // Catch: java.lang.Throwable -> L6a
                int r1 = r1 - r11
                r10.set(r12, r0)     // Catch: java.lang.Throwable -> L6a
                r9.count = r1     // Catch: java.lang.Throwable -> L6a
                goto L6c
            L6a:
                r0 = move-exception
                goto Lba
            L6c:
                r17.unlock()
                r17.postWriteCleanup()
                return r14
            L73:
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> L6a
                com.google.common.base.Equivalence r1 = r1.f13733f     // Catch: java.lang.Throwable -> L6a
                r3 = r20
                boolean r1 = r1.equivalent(r3, r6)     // Catch: java.lang.Throwable -> L6a
                if (r1 == 0) goto Laa
                int r1 = r9.modCount     // Catch: java.lang.Throwable -> L6a
                int r1 = r1 + r11
                r9.modCount = r1     // Catch: java.lang.Throwable -> L6a
                int r5 = r16.getWeight()     // Catch: java.lang.Throwable -> L6a
                com.google.common.cache.RemovalCause r10 = com.google.common.cache.RemovalCause.REPLACED     // Catch: java.lang.Throwable -> L6a
                r1 = r17
                r2 = r18
                r3 = r19
                r4 = r6
                r6 = r10
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L6a
                r1 = r17
                r2 = r13
                r3 = r18
                r4 = r21
                r5 = r7
                r1.setValue(r2, r3, r4, r5)     // Catch: java.lang.Throwable -> L6a
                r9.evictEntries(r13)     // Catch: java.lang.Throwable -> L6a
                r17.unlock()
                r17.postWriteCleanup()
                return r11
            Laa:
                r9.recordLockedRead(r13, r7)     // Catch: java.lang.Throwable -> L6a
                goto L6c
            Lae:
                r3 = r20
                goto Lb4
            Lb1:
                r15 = r18
                goto Lae
            Lb4:
                com.google.common.cache.ReferenceEntry r13 = r13.getNext()     // Catch: java.lang.Throwable -> L6a
                goto L24
            Lba:
                r17.unlock()
                r17.postWriteCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.replace(java.lang.Object, int, java.lang.Object, java.lang.Object):boolean");
        }

        void runLockedCleanup(long j2) {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                    expireEntries(j2);
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }

        void runUnlockedCleanup() {
            if (isHeldByCurrentThread()) {
                return;
            }
            this.map.w();
        }

        V scheduleRefresh(ReferenceEntry<K, V> referenceEntry, K k2, int i2, V v2, long j2, CacheLoader<? super K, V> cacheLoader) {
            V vRefresh;
            return (!this.map.D() || j2 - referenceEntry.getWriteTime() <= this.map.f13740m || referenceEntry.getValueReference().isLoading() || (vRefresh = refresh(k2, i2, cacheLoader, true)) == null) ? v2 : vRefresh;
        }

        @GuardedBy("this")
        void setValue(ReferenceEntry<K, V> referenceEntry, K k2, V v2, long j2) {
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            int iWeigh = this.map.f13737j.weigh(k2, v2);
            Preconditions.checkState(iWeigh >= 0, "Weights must be non-negative");
            referenceEntry.setValueReference(this.map.f13735h.referenceValue(this, referenceEntry, v2, iWeigh));
            recordWrite(referenceEntry, iWeigh, j2);
            valueReference.notifyNewValue(v2);
        }

        @CanIgnoreReturnValue
        boolean storeLoadedValue(K k2, int i2, LoadingValueReference<K, V> loadingValueReference, V v2) {
            lock();
            try {
                long j2 = this.map.f13743p.read();
                preWriteCleanup(j2);
                int i3 = this.count + 1;
                if (i3 > this.threshold) {
                    expand();
                    i3 = this.count + 1;
                }
                int i4 = i3;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = i2 & (atomicReferenceArray.length() - 1);
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry<K, V> next = referenceEntry;
                while (true) {
                    if (next == null) {
                        this.modCount++;
                        ReferenceEntry<K, V> referenceEntryNewEntry = newEntry(k2, i2, referenceEntry);
                        setValue(referenceEntryNewEntry, k2, v2, j2);
                        atomicReferenceArray.set(length, referenceEntryNewEntry);
                        this.count = i4;
                        evictEntries(referenceEntryNewEntry);
                        break;
                    }
                    K key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.f13732e.equivalent(k2, key)) {
                        ValueReference<K, V> valueReference = next.getValueReference();
                        V v3 = valueReference.get();
                        if (loadingValueReference != valueReference && (v3 != null || valueReference == LocalCache.f13726x)) {
                            enqueueNotification(k2, i2, v2, 0, RemovalCause.REPLACED);
                            unlock();
                            postWriteCleanup();
                            return false;
                        }
                        this.modCount++;
                        if (loadingValueReference.isActive()) {
                            enqueueNotification(k2, i2, v3, loadingValueReference.getWeight(), v3 == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED);
                            i4--;
                        }
                        setValue(next, k2, v2, j2);
                        this.count = i4;
                        evictEntries(next);
                    } else {
                        next = next.getNext();
                    }
                }
                unlock();
                postWriteCleanup();
                return true;
            } catch (Throwable th) {
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        void tryDrainReferenceQueues() {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                } finally {
                    unlock();
                }
            }
        }

        void tryExpireEntries(long j2) {
            if (tryLock()) {
                try {
                    expireEntries(j2);
                } finally {
                    unlock();
                }
            }
        }

        V waitForLoadingValue(ReferenceEntry<K, V> referenceEntry, K k2, ValueReference<K, V> valueReference) throws ExecutionException {
            if (!valueReference.isLoading()) {
                throw new AssertionError();
            }
            Preconditions.checkState(!Thread.holdsLock(referenceEntry), "Recursive load of: %s", k2);
            try {
                V vWaitForValue = valueReference.waitForValue();
                if (vWaitForValue != null) {
                    recordRead(referenceEntry, this.map.f13743p.read());
                    return vWaitForValue;
                }
                throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + k2 + ".");
            } finally {
                this.statsCounter.recordMisses(1);
            }
        }

        @CheckForNull
        V get(Object obj, int i2) {
            try {
                if (this.count != 0) {
                    long j2 = this.map.f13743p.read();
                    ReferenceEntry<K, V> liveEntry = getLiveEntry(obj, i2, j2);
                    if (liveEntry == null) {
                        return null;
                    }
                    V v2 = liveEntry.getValueReference().get();
                    if (v2 != null) {
                        recordRead(liveEntry, j2);
                        return scheduleRefresh(liveEntry, liveEntry.getKey(), i2, v2, j2, this.map.f13746s);
                    }
                    tryDrainReferenceQueues();
                }
                return null;
            } finally {
                postReadCleanup();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0038, code lost:
        
            r10 = r6.getValueReference();
            r9 = r10.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0048, code lost:
        
            if (r12.map.f13733f.equivalent(r15, r9) == false) goto L16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x004a, code lost:
        
            r13 = com.google.common.cache.RemovalCause.EXPLICIT;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x004f, code lost:
        
            if (r9 != null) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0055, code lost:
        
            if (r10.isActive() == false) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0057, code lost:
        
            r13 = com.google.common.cache.RemovalCause.COLLECTED;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0059, code lost:
        
            r12.modCount++;
            r14 = removeValueFromChain(r5, r6, r7, r14, r9, r10, r13);
            r15 = r12.count - 1;
            r0.set(r1, r14);
            r12.count = r15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x006f, code lost:
        
            if (r13 != com.google.common.cache.RemovalCause.EXPLICIT) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0072, code lost:
        
            r2 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0073, code lost:
        
            unlock();
            postWriteCleanup();
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0079, code lost:
        
            return r2;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        boolean remove(java.lang.Object r13, int r14, java.lang.Object r15) {
            /*
                r12 = this;
                r12.lock()
                com.google.common.cache.LocalCache<K, V> r0 = r12.map     // Catch: java.lang.Throwable -> L4d
                com.google.common.base.Ticker r0 = r0.f13743p     // Catch: java.lang.Throwable -> L4d
                long r0 = r0.read()     // Catch: java.lang.Throwable -> L4d
                r12.preWriteCleanup(r0)     // Catch: java.lang.Throwable -> L4d
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r0 = r12.table     // Catch: java.lang.Throwable -> L4d
                int r1 = r0.length()     // Catch: java.lang.Throwable -> L4d
                r2 = 1
                int r1 = r1 - r2
                r1 = r1 & r14
                java.lang.Object r3 = r0.get(r1)     // Catch: java.lang.Throwable -> L4d
                r5 = r3
                com.google.common.cache.ReferenceEntry r5 = (com.google.common.cache.ReferenceEntry) r5     // Catch: java.lang.Throwable -> L4d
                r6 = r5
            L1f:
                r3 = 0
                if (r6 == 0) goto L7a
                java.lang.Object r7 = r6.getKey()     // Catch: java.lang.Throwable -> L4d
                int r4 = r6.getHash()     // Catch: java.lang.Throwable -> L4d
                if (r4 != r14) goto L81
                if (r7 == 0) goto L81
                com.google.common.cache.LocalCache<K, V> r4 = r12.map     // Catch: java.lang.Throwable -> L4d
                com.google.common.base.Equivalence r4 = r4.f13732e     // Catch: java.lang.Throwable -> L4d
                boolean r4 = r4.equivalent(r13, r7)     // Catch: java.lang.Throwable -> L4d
                if (r4 == 0) goto L81
                com.google.common.cache.LocalCache$ValueReference r10 = r6.getValueReference()     // Catch: java.lang.Throwable -> L4d
                java.lang.Object r9 = r10.get()     // Catch: java.lang.Throwable -> L4d
                com.google.common.cache.LocalCache<K, V> r13 = r12.map     // Catch: java.lang.Throwable -> L4d
                com.google.common.base.Equivalence r13 = r13.f13733f     // Catch: java.lang.Throwable -> L4d
                boolean r13 = r13.equivalent(r15, r9)     // Catch: java.lang.Throwable -> L4d
                if (r13 == 0) goto L4f
                com.google.common.cache.RemovalCause r13 = com.google.common.cache.RemovalCause.EXPLICIT     // Catch: java.lang.Throwable -> L4d
                goto L59
            L4d:
                r13 = move-exception
                goto L86
            L4f:
                if (r9 != 0) goto L7a
                boolean r13 = r10.isActive()     // Catch: java.lang.Throwable -> L4d
                if (r13 == 0) goto L7a
                com.google.common.cache.RemovalCause r13 = com.google.common.cache.RemovalCause.COLLECTED     // Catch: java.lang.Throwable -> L4d
            L59:
                int r15 = r12.modCount     // Catch: java.lang.Throwable -> L4d
                int r15 = r15 + r2
                r12.modCount = r15     // Catch: java.lang.Throwable -> L4d
                r4 = r12
                r8 = r14
                r11 = r13
                com.google.common.cache.ReferenceEntry r14 = r4.removeValueFromChain(r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L4d
                int r15 = r12.count     // Catch: java.lang.Throwable -> L4d
                int r15 = r15 - r2
                r0.set(r1, r14)     // Catch: java.lang.Throwable -> L4d
                r12.count = r15     // Catch: java.lang.Throwable -> L4d
                com.google.common.cache.RemovalCause r14 = com.google.common.cache.RemovalCause.EXPLICIT     // Catch: java.lang.Throwable -> L4d
                if (r13 != r14) goto L72
                goto L73
            L72:
                r2 = r3
            L73:
                r12.unlock()
                r12.postWriteCleanup()
                return r2
            L7a:
                r12.unlock()
                r12.postWriteCleanup()
                return r3
            L81:
                com.google.common.cache.ReferenceEntry r6 = r6.getNext()     // Catch: java.lang.Throwable -> L4d
                goto L1f
            L86:
                r12.unlock()
                r12.postWriteCleanup()
                throw r13
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.remove(java.lang.Object, int, java.lang.Object):boolean");
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0075, code lost:
        
            return null;
         */
        @javax.annotation.CheckForNull
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        V replace(K r18, int r19, V r20) {
            /*
                r17 = this;
                r9 = r17
                r0 = r19
                r17.lock()
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> L6d
                com.google.common.base.Ticker r1 = r1.f13743p     // Catch: java.lang.Throwable -> L6d
                long r7 = r1.read()     // Catch: java.lang.Throwable -> L6d
                r9.preWriteCleanup(r7)     // Catch: java.lang.Throwable -> L6d
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r10 = r9.table     // Catch: java.lang.Throwable -> L6d
                int r1 = r10.length()     // Catch: java.lang.Throwable -> L6d
                int r1 = r1 + (-1)
                r11 = r0 & r1
                java.lang.Object r1 = r10.get(r11)     // Catch: java.lang.Throwable -> L6d
                r2 = r1
                com.google.common.cache.ReferenceEntry r2 = (com.google.common.cache.ReferenceEntry) r2     // Catch: java.lang.Throwable -> L6d
                r12 = r2
            L24:
                r13 = 0
                if (r12 == 0) goto L6f
                java.lang.Object r4 = r12.getKey()     // Catch: java.lang.Throwable -> L6d
                int r1 = r12.getHash()     // Catch: java.lang.Throwable -> L6d
                if (r1 != r0) goto La2
                if (r4 == 0) goto La2
                com.google.common.cache.LocalCache<K, V> r1 = r9.map     // Catch: java.lang.Throwable -> L6d
                com.google.common.base.Equivalence r1 = r1.f13732e     // Catch: java.lang.Throwable -> L6d
                r14 = r18
                boolean r1 = r1.equivalent(r14, r4)     // Catch: java.lang.Throwable -> L6d
                if (r1 == 0) goto La4
                com.google.common.cache.LocalCache$ValueReference r15 = r12.getValueReference()     // Catch: java.lang.Throwable -> L6d
                java.lang.Object r16 = r15.get()     // Catch: java.lang.Throwable -> L6d
                if (r16 != 0) goto L76
                boolean r1 = r15.isActive()     // Catch: java.lang.Throwable -> L6d
                if (r1 == 0) goto L6f
                int r1 = r9.modCount     // Catch: java.lang.Throwable -> L6d
                int r1 = r1 + 1
                r9.modCount = r1     // Catch: java.lang.Throwable -> L6d
                com.google.common.cache.RemovalCause r8 = com.google.common.cache.RemovalCause.COLLECTED     // Catch: java.lang.Throwable -> L6d
                r1 = r17
                r3 = r12
                r5 = r19
                r6 = r16
                r7 = r15
                com.google.common.cache.ReferenceEntry r0 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L6d
                int r1 = r9.count     // Catch: java.lang.Throwable -> L6d
                int r1 = r1 + (-1)
                r10.set(r11, r0)     // Catch: java.lang.Throwable -> L6d
                r9.count = r1     // Catch: java.lang.Throwable -> L6d
                goto L6f
            L6d:
                r0 = move-exception
                goto Laa
            L6f:
                r17.unlock()
                r17.postWriteCleanup()
                return r13
            L76:
                int r1 = r9.modCount     // Catch: java.lang.Throwable -> L6d
                int r1 = r1 + 1
                r9.modCount = r1     // Catch: java.lang.Throwable -> L6d
                int r5 = r15.getWeight()     // Catch: java.lang.Throwable -> L6d
                com.google.common.cache.RemovalCause r6 = com.google.common.cache.RemovalCause.REPLACED     // Catch: java.lang.Throwable -> L6d
                r1 = r17
                r2 = r18
                r3 = r19
                r4 = r16
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L6d
                r1 = r17
                r2 = r12
                r3 = r18
                r4 = r20
                r5 = r7
                r1.setValue(r2, r3, r4, r5)     // Catch: java.lang.Throwable -> L6d
                r9.evictEntries(r12)     // Catch: java.lang.Throwable -> L6d
                r17.unlock()
                r17.postWriteCleanup()
                return r16
            La2:
                r14 = r18
            La4:
                com.google.common.cache.ReferenceEntry r12 = r12.getNext()     // Catch: java.lang.Throwable -> L6d
                goto L24
            Laa:
                r17.unlock()
                r17.postWriteCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.replace(java.lang.Object, int, java.lang.Object):java.lang.Object");
        }
    }
}
