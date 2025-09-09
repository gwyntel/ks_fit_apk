package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.kingsmith.miot.KsProperty;
import com.taobao.accs.common.Constants;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public final class Monitor {

    @CheckForNull
    @GuardedBy(KsProperty.Lock)
    private Guard activeGuards;
    private final boolean fair;
    private final ReentrantLock lock;

    public static abstract class Guard {

        /* renamed from: a, reason: collision with root package name */
        final Monitor f14882a;

        /* renamed from: b, reason: collision with root package name */
        final Condition f14883b;

        /* renamed from: c, reason: collision with root package name */
        int f14884c = 0;

        /* renamed from: d, reason: collision with root package name */
        Guard f14885d;

        protected Guard(Monitor monitor) {
            this.f14882a = (Monitor) Preconditions.checkNotNull(monitor, Constants.KEY_MONIROT);
            this.f14883b = monitor.lock.newCondition();
        }

        public abstract boolean isSatisfied();
    }

    public Monitor() {
        this(false);
    }

    @GuardedBy(KsProperty.Lock)
    private void await(Guard guard, boolean z2) throws InterruptedException {
        if (z2) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        do {
            try {
                guard.f14883b.await();
            } finally {
                endWaitingFor(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy(KsProperty.Lock)
    private boolean awaitNanos(Guard guard, long j2, boolean z2) throws InterruptedException {
        boolean z3 = true;
        while (j2 > 0) {
            if (z3) {
                if (z2) {
                    try {
                        signalNextWaiter();
                    } catch (Throwable th) {
                        if (!z3) {
                            endWaitingFor(guard);
                        }
                        throw th;
                    }
                }
                beginWaitingFor(guard);
                z3 = false;
            }
            j2 = guard.f14883b.awaitNanos(j2);
            if (guard.isSatisfied()) {
                if (!z3) {
                    endWaitingFor(guard);
                }
                return true;
            }
        }
        if (!z3) {
            endWaitingFor(guard);
        }
        return false;
    }

    @GuardedBy(KsProperty.Lock)
    private void awaitUninterruptibly(Guard guard, boolean z2) {
        if (z2) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        do {
            try {
                guard.f14883b.awaitUninterruptibly();
            } finally {
                endWaitingFor(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy(KsProperty.Lock)
    private void beginWaitingFor(Guard guard) {
        int i2 = guard.f14884c;
        guard.f14884c = i2 + 1;
        if (i2 == 0) {
            guard.f14885d = this.activeGuards;
            this.activeGuards = guard;
        }
    }

    @GuardedBy(KsProperty.Lock)
    private void endWaitingFor(Guard guard) {
        int i2 = guard.f14884c - 1;
        guard.f14884c = i2;
        if (i2 == 0) {
            Guard guard2 = this.activeGuards;
            Guard guard3 = null;
            while (guard2 != guard) {
                guard3 = guard2;
                guard2 = guard2.f14885d;
            }
            if (guard3 == null) {
                this.activeGuards = guard2.f14885d;
            } else {
                guard3.f14885d = guard2.f14885d;
            }
            guard2.f14885d = null;
        }
    }

    private static long initNanoTime(long j2) {
        if (j2 <= 0) {
            return 0L;
        }
        long jNanoTime = System.nanoTime();
        if (jNanoTime == 0) {
            return 1L;
        }
        return jNanoTime;
    }

    @GuardedBy(KsProperty.Lock)
    private boolean isSatisfied(Guard guard) {
        try {
            return guard.isSatisfied();
        } catch (Throwable th) {
            signalAllWaiters();
            throw th;
        }
    }

    private static long remainingNanos(long j2, long j3) {
        if (j3 <= 0) {
            return 0L;
        }
        return j3 - (System.nanoTime() - j2);
    }

    @GuardedBy(KsProperty.Lock)
    private void signalAllWaiters() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.f14885d) {
            guard.f14883b.signalAll();
        }
    }

    @GuardedBy(KsProperty.Lock)
    private void signalNextWaiter() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.f14885d) {
            if (isSatisfied(guard)) {
                guard.f14883b.signal();
                return;
            }
        }
    }

    private static long toSafeNanos(long j2, TimeUnit timeUnit) {
        return Longs.constrainToRange(timeUnit.toNanos(j2), 0L, 6917529027641081853L);
    }

    public void enter() {
        this.lock.lock();
    }

    public boolean enterIf(Guard guard) {
        if (guard.f14882a != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            boolean zIsSatisfied = guard.isSatisfied();
            if (!zIsSatisfied) {
            }
            return zIsSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.f14882a != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        try {
            boolean zIsSatisfied = guard.isSatisfied();
            if (!zIsSatisfied) {
            }
            return zIsSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.f14882a != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        boolean zIsHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lockInterruptibly();
        try {
            if (guard.isSatisfied()) {
                return;
            }
            await(guard, zIsHeldByCurrentThread);
        } catch (Throwable th) {
            leave();
            throw th;
        }
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.f14882a != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        boolean zIsHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lock();
        try {
            if (guard.isSatisfied()) {
                return;
            }
            awaitUninterruptibly(guard, zIsHeldByCurrentThread);
        } catch (Throwable th) {
            leave();
            throw th;
        }
    }

    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }

    public int getQueueLength() {
        return this.lock.getQueueLength();
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.f14882a != this) {
            throw new IllegalMonitorStateException();
        }
        this.lock.lock();
        try {
            return guard.f14884c;
        } finally {
            this.lock.unlock();
        }
    }

    public boolean hasQueuedThread(Thread thread) {
        return this.lock.hasQueuedThread(thread);
    }

    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public boolean hasWaiters(Guard guard) {
        return getWaitQueueLength(guard) > 0;
    }

    public boolean isFair() {
        return this.fair;
    }

    public boolean isOccupied() {
        return this.lock.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public void leave() {
        ReentrantLock reentrantLock = this.lock;
        try {
            if (reentrantLock.getHoldCount() == 1) {
                signalNextWaiter();
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean tryEnter() {
        return this.lock.tryLock();
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.f14882a != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        if (!reentrantLock.tryLock()) {
            return false;
        }
        try {
            boolean zIsSatisfied = guard.isSatisfied();
            if (!zIsSatisfied) {
            }
            return zIsSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        if (guard.f14882a != this || !this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        if (guard.isSatisfied()) {
            return;
        }
        await(guard, true);
    }

    public void waitForUninterruptibly(Guard guard) {
        if (guard.f14882a != this || !this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        if (guard.isSatisfied()) {
            return;
        }
        awaitUninterruptibly(guard, true);
    }

    public Monitor(boolean z2) {
        this.activeGuards = null;
        this.fair = z2;
        this.lock = new ReentrantLock(z2);
    }

    public boolean enter(long j2, TimeUnit timeUnit) throws Throwable {
        boolean zTryLock;
        long safeNanos = toSafeNanos(j2, timeUnit);
        ReentrantLock reentrantLock = this.lock;
        boolean z2 = true;
        if (!this.fair && reentrantLock.tryLock()) {
            return true;
        }
        boolean zInterrupted = Thread.interrupted();
        try {
            long jNanoTime = System.nanoTime();
            long jRemainingNanos = safeNanos;
            while (true) {
                try {
                    try {
                        zTryLock = reentrantLock.tryLock(jRemainingNanos, TimeUnit.NANOSECONDS);
                        break;
                    } catch (InterruptedException unused) {
                        jRemainingNanos = remainingNanos(jNanoTime, safeNanos);
                        zInterrupted = true;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (z2) {
                        Thread.currentThread().interrupt();
                    }
                    throw th;
                }
            }
            if (zInterrupted) {
                Thread.currentThread().interrupt();
            }
            return zTryLock;
        } catch (Throwable th2) {
            th = th2;
            z2 = zInterrupted;
        }
    }

    public boolean enterInterruptibly(long j2, TimeUnit timeUnit) throws InterruptedException {
        return this.lock.tryLock(j2, timeUnit);
    }

    public boolean waitFor(Guard guard, long j2, TimeUnit timeUnit) throws InterruptedException {
        long safeNanos = toSafeNanos(j2, timeUnit);
        if (guard.f14882a == this && this.lock.isHeldByCurrentThread()) {
            if (guard.isSatisfied()) {
                return true;
            }
            if (!Thread.interrupted()) {
                return awaitNanos(guard, safeNanos, true);
            }
            throw new InterruptedException();
        }
        throw new IllegalMonitorStateException();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean waitForUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r7, long r8, java.util.concurrent.TimeUnit r10) throws java.lang.Throwable {
        /*
            r6 = this;
            long r8 = toSafeNanos(r8, r10)
            com.google.common.util.concurrent.Monitor r10 = r7.f14882a
            if (r10 != r6) goto L53
            java.util.concurrent.locks.ReentrantLock r10 = r6.lock
            boolean r10 = r10.isHeldByCurrentThread()
            if (r10 == 0) goto L53
            boolean r10 = r7.isSatisfied()
            r0 = 1
            if (r10 == 0) goto L18
            return r0
        L18:
            long r1 = initNanoTime(r8)
            boolean r10 = java.lang.Thread.interrupted()
            r3 = r8
            r5 = r0
        L22:
            boolean r7 = r6.awaitNanos(r7, r3, r5)     // Catch: java.lang.Throwable -> L30 java.lang.InterruptedException -> L33
            if (r10 == 0) goto L2f
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            r8.interrupt()
        L2f:
            return r7
        L30:
            r7 = move-exception
            r0 = r10
            goto L49
        L33:
            boolean r10 = r7.isSatisfied()     // Catch: java.lang.Throwable -> L48
            if (r10 == 0) goto L41
            java.lang.Thread r7 = java.lang.Thread.currentThread()
            r7.interrupt()
            return r0
        L41:
            long r3 = remainingNanos(r1, r8)     // Catch: java.lang.Throwable -> L48
            r5 = 0
            r10 = r0
            goto L22
        L48:
            r7 = move-exception
        L49:
            if (r0 == 0) goto L52
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            r8.interrupt()
        L52:
            throw r7
        L53:
            java.lang.IllegalMonitorStateException r7 = new java.lang.IllegalMonitorStateException
            r7.<init>()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.waitForUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public boolean enterIf(Guard guard, long j2, TimeUnit timeUnit) {
        if (guard.f14882a == this) {
            if (!enter(j2, timeUnit)) {
                return false;
            }
            try {
                boolean zIsSatisfied = guard.isSatisfied();
                if (!zIsSatisfied) {
                }
                return zIsSatisfied;
            } finally {
                this.lock.unlock();
            }
        }
        throw new IllegalMonitorStateException();
    }

    public boolean enterIfInterruptibly(Guard guard, long j2, TimeUnit timeUnit) throws InterruptedException {
        if (guard.f14882a == this) {
            ReentrantLock reentrantLock = this.lock;
            if (!reentrantLock.tryLock(j2, timeUnit)) {
                return false;
            }
            try {
                boolean zIsSatisfied = guard.isSatisfied();
                if (!zIsSatisfied) {
                }
                return zIsSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        }
        throw new IllegalMonitorStateException();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean enterWhen(com.google.common.util.concurrent.Monitor.Guard r11, long r12, java.util.concurrent.TimeUnit r14) throws java.lang.InterruptedException {
        /*
            r10 = this;
            long r0 = toSafeNanos(r12, r14)
            com.google.common.util.concurrent.Monitor r2 = r11.f14882a
            if (r2 != r10) goto L62
            java.util.concurrent.locks.ReentrantLock r2 = r10.lock
            boolean r3 = r2.isHeldByCurrentThread()
            boolean r4 = r10.fair
            r5 = 0
            r6 = 0
            if (r4 != 0) goto L29
            boolean r4 = java.lang.Thread.interrupted()
            if (r4 != 0) goto L23
            boolean r4 = r2.tryLock()
            if (r4 == 0) goto L29
            r8 = r6
            goto L34
        L23:
            java.lang.InterruptedException r11 = new java.lang.InterruptedException
            r11.<init>()
            throw r11
        L29:
            long r8 = initNanoTime(r0)
            boolean r12 = r2.tryLock(r12, r14)
            if (r12 != 0) goto L34
            return r5
        L34:
            boolean r12 = r11.isSatisfied()     // Catch: java.lang.Throwable -> L4a
            if (r12 != 0) goto L4c
            int r12 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r12 != 0) goto L3f
            goto L43
        L3f:
            long r0 = remainingNanos(r8, r0)     // Catch: java.lang.Throwable -> L4a
        L43:
            boolean r11 = r10.awaitNanos(r11, r0, r3)     // Catch: java.lang.Throwable -> L4a
            if (r11 == 0) goto L4d
            goto L4c
        L4a:
            r11 = move-exception
            goto L53
        L4c:
            r5 = 1
        L4d:
            if (r5 != 0) goto L52
            r2.unlock()
        L52:
            return r5
        L53:
            if (r3 != 0) goto L5e
            r10.signalNextWaiter()     // Catch: java.lang.Throwable -> L59
            goto L5e
        L59:
            r11 = move-exception
            r2.unlock()
            throw r11
        L5e:
            r2.unlock()
            throw r11
        L62:
            java.lang.IllegalMonitorStateException r11 = new java.lang.IllegalMonitorStateException
            r11.<init>()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enterWhen(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public boolean enterWhenUninterruptibly(Guard guard, long j2, TimeUnit timeUnit) throws Throwable {
        long jInitNanoTime;
        long jRemainingNanos;
        long safeNanos = toSafeNanos(j2, timeUnit);
        if (guard.f14882a == this) {
            ReentrantLock reentrantLock = this.lock;
            boolean zIsHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
            boolean zInterrupted = Thread.interrupted();
            try {
                boolean zAwaitNanos = true;
                if (this.fair || !reentrantLock.tryLock()) {
                    jInitNanoTime = initNanoTime(safeNanos);
                    long jRemainingNanos2 = safeNanos;
                    while (true) {
                        try {
                            try {
                                break;
                            } catch (InterruptedException unused) {
                                jRemainingNanos2 = remainingNanos(jInitNanoTime, safeNanos);
                                zInterrupted = true;
                            }
                        } catch (Throwable th) {
                            th = th;
                            zInterrupted = true;
                            if (zInterrupted) {
                                Thread.currentThread().interrupt();
                            }
                            throw th;
                        }
                    }
                    if (!reentrantLock.tryLock(jRemainingNanos2, TimeUnit.NANOSECONDS)) {
                        if (zInterrupted) {
                            Thread.currentThread().interrupt();
                        }
                        return false;
                    }
                } else {
                    jInitNanoTime = 0;
                }
                while (!guard.isSatisfied()) {
                    try {
                        if (jInitNanoTime == 0) {
                            jInitNanoTime = initNanoTime(safeNanos);
                            jRemainingNanos = safeNanos;
                        } else {
                            jRemainingNanos = remainingNanos(jInitNanoTime, safeNanos);
                        }
                        zAwaitNanos = awaitNanos(guard, jRemainingNanos, zIsHeldByCurrentThread);
                    } catch (InterruptedException unused2) {
                        zIsHeldByCurrentThread = false;
                        zInterrupted = zAwaitNanos;
                    } catch (Throwable th2) {
                        reentrantLock.unlock();
                        throw th2;
                    }
                }
                if (!zAwaitNanos) {
                    reentrantLock.unlock();
                }
                if (zInterrupted) {
                    Thread.currentThread().interrupt();
                }
                return zAwaitNanos;
            } catch (Throwable th3) {
                th = th3;
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }
}
