package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.j2objc.annotations.Weak;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public class CycleDetectingLockFactory {

    /* renamed from: a, reason: collision with root package name */
    final Policy f14853a;
    private static final ConcurrentMap<Class<? extends Enum<?>>, Map<? extends Enum<?>, LockGraphNode>> lockGraphNodesPerType = new MapMaker().weakKeys().makeMap();
    private static final LazyLogger logger = new LazyLogger(CycleDetectingLockFactory.class);
    private static final ThreadLocal<ArrayList<LockGraphNode>> acquiredLocks = new ThreadLocal<ArrayList<LockGraphNode>>() { // from class: com.google.common.util.concurrent.CycleDetectingLockFactory.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public ArrayList initialValue() {
            return Lists.newArrayListWithCapacity(3);
        }
    };

    private interface CycleDetectingLock {
        LockGraphNode getLockGraphNode();

        boolean isAcquiredByCurrentThread();
    }

    final class CycleDetectingReentrantLock extends ReentrantLock implements CycleDetectingLock {
        private final LockGraphNode lockGraphNode;

        @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.CycleDetectingLock
        public LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }

        @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.CycleDetectingLock
        public boolean isAcquiredByCurrentThread() {
            return isHeldByCurrentThread();
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        private CycleDetectingReentrantLock(LockGraphNode lockGraphNode, boolean z2) {
            super(z2);
            this.lockGraphNode = (LockGraphNode) Preconditions.checkNotNull(lockGraphNode);
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public boolean tryLock(long j2, TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                return super.tryLock(j2, timeUnit);
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }
    }

    final class CycleDetectingReentrantReadWriteLock extends ReentrantReadWriteLock implements CycleDetectingLock {
        private final LockGraphNode lockGraphNode;
        private final CycleDetectingReentrantReadLock readLock;
        private final CycleDetectingReentrantWriteLock writeLock;

        @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.CycleDetectingLock
        public LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }

        @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.CycleDetectingLock
        public boolean isAcquiredByCurrentThread() {
            return isWriteLockedByCurrentThread() || getReadHoldCount() > 0;
        }

        private CycleDetectingReentrantReadWriteLock(LockGraphNode lockGraphNode, boolean z2) {
            super(z2);
            this.readLock = CycleDetectingLockFactory.this.new CycleDetectingReentrantReadLock(this);
            this.writeLock = CycleDetectingLockFactory.this.new CycleDetectingReentrantWriteLock(this);
            this.lockGraphNode = (LockGraphNode) Preconditions.checkNotNull(lockGraphNode);
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock, java.util.concurrent.locks.ReadWriteLock
        public ReentrantReadWriteLock.ReadLock readLock() {
            return this.readLock;
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock, java.util.concurrent.locks.ReadWriteLock
        public ReentrantReadWriteLock.WriteLock writeLock() {
            return this.writeLock;
        }
    }

    private static class ExampleStackTrace extends IllegalStateException {
        static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
        static final ImmutableSet<String> EXCLUDED_CLASS_NAMES = ImmutableSet.of(CycleDetectingLockFactory.class.getName(), ExampleStackTrace.class.getName(), LockGraphNode.class.getName());

        ExampleStackTrace(LockGraphNode lockGraphNode, LockGraphNode lockGraphNode2) {
            super(lockGraphNode.c() + " -> " + lockGraphNode2.c());
            StackTraceElement[] stackTrace = getStackTrace();
            int length = stackTrace.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (WithExplicitOrdering.class.getName().equals(stackTrace[i2].getClassName())) {
                    setStackTrace(EMPTY_STACK_TRACE);
                    return;
                } else {
                    if (!EXCLUDED_CLASS_NAMES.contains(stackTrace[i2].getClassName())) {
                        setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i2, length));
                        return;
                    }
                }
            }
        }
    }

    private static class LockGraphNode {

        /* renamed from: a, reason: collision with root package name */
        final Map f14854a = new MapMaker().weakKeys().makeMap();

        /* renamed from: b, reason: collision with root package name */
        final Map f14855b = new MapMaker().weakKeys().makeMap();

        /* renamed from: c, reason: collision with root package name */
        final String f14856c;

        LockGraphNode(String str) {
            this.f14856c = (String) Preconditions.checkNotNull(str);
        }

        @CheckForNull
        private ExampleStackTrace findPathTo(LockGraphNode lockGraphNode, Set<LockGraphNode> set) {
            if (!set.add(this)) {
                return null;
            }
            ExampleStackTrace exampleStackTrace = (ExampleStackTrace) this.f14854a.get(lockGraphNode);
            if (exampleStackTrace != null) {
                return exampleStackTrace;
            }
            for (Map.Entry entry : this.f14854a.entrySet()) {
                LockGraphNode lockGraphNode2 = (LockGraphNode) entry.getKey();
                ExampleStackTrace exampleStackTraceFindPathTo = lockGraphNode2.findPathTo(lockGraphNode, set);
                if (exampleStackTraceFindPathTo != null) {
                    ExampleStackTrace exampleStackTrace2 = new ExampleStackTrace(lockGraphNode2, this);
                    exampleStackTrace2.setStackTrace(((ExampleStackTrace) entry.getValue()).getStackTrace());
                    exampleStackTrace2.initCause(exampleStackTraceFindPathTo);
                    return exampleStackTrace2;
                }
            }
            return null;
        }

        void a(Policy policy, LockGraphNode lockGraphNode) {
            Preconditions.checkState(this != lockGraphNode, "Attempted to acquire multiple locks with the same rank %s", lockGraphNode.c());
            if (this.f14854a.containsKey(lockGraphNode)) {
                return;
            }
            PotentialDeadlockException potentialDeadlockException = (PotentialDeadlockException) this.f14855b.get(lockGraphNode);
            if (potentialDeadlockException != null) {
                policy.handlePotentialDeadlock(new PotentialDeadlockException(lockGraphNode, this, potentialDeadlockException.getConflictingStackTrace()));
                return;
            }
            ExampleStackTrace exampleStackTraceFindPathTo = lockGraphNode.findPathTo(this, Sets.newIdentityHashSet());
            if (exampleStackTraceFindPathTo == null) {
                this.f14854a.put(lockGraphNode, new ExampleStackTrace(lockGraphNode, this));
                return;
            }
            PotentialDeadlockException potentialDeadlockException2 = new PotentialDeadlockException(lockGraphNode, this, exampleStackTraceFindPathTo);
            this.f14855b.put(lockGraphNode, potentialDeadlockException2);
            policy.handlePotentialDeadlock(potentialDeadlockException2);
        }

        void b(Policy policy, List list) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                a(policy, (LockGraphNode) it.next());
            }
        }

        String c() {
            return this.f14856c;
        }
    }

    public enum Policies implements Policy {
        THROW { // from class: com.google.common.util.concurrent.CycleDetectingLockFactory.Policies.1
            @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.Policy
            public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
                throw potentialDeadlockException;
            }
        },
        WARN { // from class: com.google.common.util.concurrent.CycleDetectingLockFactory.Policies.2
            @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.Policy
            public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
                CycleDetectingLockFactory.logger.a().log(Level.SEVERE, "Detected potential deadlock", (Throwable) potentialDeadlockException);
            }
        },
        DISABLED { // from class: com.google.common.util.concurrent.CycleDetectingLockFactory.Policies.3
            @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.Policy
            public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
            }
        }
    }

    public interface Policy {
        void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException);
    }

    public static final class PotentialDeadlockException extends ExampleStackTrace {
        private final ExampleStackTrace conflictingStackTrace;

        public ExampleStackTrace getConflictingStackTrace() {
            return this.conflictingStackTrace;
        }

        @Override // java.lang.Throwable
        public String getMessage() {
            String message = super.getMessage();
            Objects.requireNonNull(message);
            StringBuilder sb = new StringBuilder(message);
            for (Throwable cause = this.conflictingStackTrace; cause != null; cause = cause.getCause()) {
                sb.append(", ");
                sb.append(cause.getMessage());
            }
            return sb.toString();
        }

        private PotentialDeadlockException(LockGraphNode lockGraphNode, LockGraphNode lockGraphNode2, ExampleStackTrace exampleStackTrace) {
            super(lockGraphNode, lockGraphNode2);
            this.conflictingStackTrace = exampleStackTrace;
            initCause(exampleStackTrace);
        }
    }

    public static final class WithExplicitOrdering<E extends Enum<E>> extends CycleDetectingLockFactory {
        private final Map<E, LockGraphNode> lockGraphNodes;

        WithExplicitOrdering(Policy policy, Map map) {
            super(policy);
            this.lockGraphNodes = map;
        }

        public ReentrantLock newReentrantLock(E e2) {
            return newReentrantLock((WithExplicitOrdering<E>) e2, false);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E e2) {
            return newReentrantReadWriteLock((WithExplicitOrdering<E>) e2, false);
        }

        public ReentrantLock newReentrantLock(E e2, boolean z2) {
            if (this.f14853a == Policies.DISABLED) {
                return new ReentrantLock(z2);
            }
            LockGraphNode lockGraphNode = this.lockGraphNodes.get(e2);
            Objects.requireNonNull(lockGraphNode);
            return new CycleDetectingReentrantLock(lockGraphNode, z2);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E e2, boolean z2) {
            if (this.f14853a == Policies.DISABLED) {
                return new ReentrantReadWriteLock(z2);
            }
            LockGraphNode lockGraphNode = this.lockGraphNodes.get(e2);
            Objects.requireNonNull(lockGraphNode);
            return new CycleDetectingReentrantReadWriteLock(lockGraphNode, z2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aboutToAcquire(CycleDetectingLock cycleDetectingLock) {
        if (cycleDetectingLock.isAcquiredByCurrentThread()) {
            return;
        }
        ArrayList<LockGraphNode> arrayList = acquiredLocks.get();
        Objects.requireNonNull(arrayList);
        LockGraphNode lockGraphNode = cycleDetectingLock.getLockGraphNode();
        lockGraphNode.b(this.f14853a, arrayList);
        arrayList.add(lockGraphNode);
    }

    static Map d(Class cls) {
        EnumMap enumMapNewEnumMap = Maps.newEnumMap(cls);
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        int length = enumArr.length;
        ArrayList arrayListNewArrayListWithCapacity = Lists.newArrayListWithCapacity(length);
        int i2 = 0;
        for (Enum r6 : enumArr) {
            LockGraphNode lockGraphNode = new LockGraphNode(getLockName(r6));
            arrayListNewArrayListWithCapacity.add(lockGraphNode);
            enumMapNewEnumMap.put((EnumMap) r6, (Enum) lockGraphNode);
        }
        for (int i3 = 1; i3 < length; i3++) {
            ((LockGraphNode) arrayListNewArrayListWithCapacity.get(i3)).b(Policies.THROW, arrayListNewArrayListWithCapacity.subList(0, i3));
        }
        while (i2 < length - 1) {
            i2++;
            ((LockGraphNode) arrayListNewArrayListWithCapacity.get(i2)).b(Policies.DISABLED, arrayListNewArrayListWithCapacity.subList(i2, length));
        }
        return Collections.unmodifiableMap(enumMapNewEnumMap);
    }

    private static String getLockName(Enum<?> r2) {
        return r2.getDeclaringClass().getSimpleName() + "." + r2.name();
    }

    private static <E extends Enum<E>> Map<? extends E, LockGraphNode> getOrCreateNodes(Class<E> cls) {
        ConcurrentMap<Class<? extends Enum<?>>, Map<? extends Enum<?>, LockGraphNode>> concurrentMap = lockGraphNodesPerType;
        Map<? extends E, LockGraphNode> map = (Map) concurrentMap.get(cls);
        if (map != null) {
            return map;
        }
        Map<? extends Enum<?>, LockGraphNode> mapD = d(cls);
        return (Map) MoreObjects.firstNonNull(concurrentMap.putIfAbsent(cls, mapD), mapD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void lockStateChanged(CycleDetectingLock cycleDetectingLock) {
        if (cycleDetectingLock.isAcquiredByCurrentThread()) {
            return;
        }
        ArrayList<LockGraphNode> arrayList = acquiredLocks.get();
        Objects.requireNonNull(arrayList);
        LockGraphNode lockGraphNode = cycleDetectingLock.getLockGraphNode();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size) == lockGraphNode) {
                arrayList.remove(size);
                return;
            }
        }
    }

    public static CycleDetectingLockFactory newInstance(Policy policy) {
        return new CycleDetectingLockFactory(policy);
    }

    public static <E extends Enum<E>> WithExplicitOrdering<E> newInstanceWithExplicitOrdering(Class<E> cls, Policy policy) {
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(policy);
        return new WithExplicitOrdering<>(policy, getOrCreateNodes(cls));
    }

    public ReentrantLock newReentrantLock(String str) {
        return newReentrantLock(str, false);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String str) {
        return newReentrantReadWriteLock(str, false);
    }

    private CycleDetectingLockFactory(Policy policy) {
        this.f14853a = (Policy) Preconditions.checkNotNull(policy);
    }

    public ReentrantLock newReentrantLock(String str, boolean z2) {
        return this.f14853a == Policies.DISABLED ? new ReentrantLock(z2) : new CycleDetectingReentrantLock(new LockGraphNode(str), z2);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String str, boolean z2) {
        return this.f14853a == Policies.DISABLED ? new ReentrantReadWriteLock(z2) : new CycleDetectingReentrantReadWriteLock(new LockGraphNode(str), z2);
    }

    private class CycleDetectingReentrantReadLock extends ReentrantReadWriteLock.ReadLock {

        @Weak
        final CycleDetectingReentrantReadWriteLock readWriteLock;

        CycleDetectingReentrantReadLock(CycleDetectingReentrantReadWriteLock cycleDetectingReentrantReadWriteLock) {
            super(cycleDetectingReentrantReadWriteLock);
            this.readWriteLock = cycleDetectingReentrantReadWriteLock;
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public boolean tryLock(long j2, TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                return super.tryLock(j2, timeUnit);
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }
    }

    private class CycleDetectingReentrantWriteLock extends ReentrantReadWriteLock.WriteLock {

        @Weak
        final CycleDetectingReentrantReadWriteLock readWriteLock;

        CycleDetectingReentrantWriteLock(CycleDetectingReentrantReadWriteLock cycleDetectingReentrantReadWriteLock) {
            super(cycleDetectingReentrantReadWriteLock);
            this.readWriteLock = cycleDetectingReentrantReadWriteLock;
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public boolean tryLock(long j2, TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                return super.tryLock(j2, timeUnit);
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }
    }
}
