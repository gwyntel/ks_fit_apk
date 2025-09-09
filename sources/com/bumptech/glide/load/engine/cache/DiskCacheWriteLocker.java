package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.util.Preconditions;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
final class DiskCacheWriteLocker {
    private final Map<String, WriteLock> locks = new HashMap();
    private final WriteLockPool writeLockPool = new WriteLockPool();

    private static class WriteLock {

        /* renamed from: a, reason: collision with root package name */
        final Lock f12347a = new ReentrantLock();

        /* renamed from: b, reason: collision with root package name */
        int f12348b;

        WriteLock() {
        }
    }

    private static class WriteLockPool {
        private static final int MAX_POOL_SIZE = 10;
        private final Queue<WriteLock> pool = new ArrayDeque();

        WriteLockPool() {
        }

        WriteLock a() {
            WriteLock writeLockPoll;
            synchronized (this.pool) {
                writeLockPoll = this.pool.poll();
            }
            return writeLockPoll == null ? new WriteLock() : writeLockPoll;
        }

        void b(WriteLock writeLock) {
            synchronized (this.pool) {
                try {
                    if (this.pool.size() < 10) {
                        this.pool.offer(writeLock);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    DiskCacheWriteLocker() {
    }

    void a(String str) {
        WriteLock writeLockA;
        synchronized (this) {
            try {
                writeLockA = this.locks.get(str);
                if (writeLockA == null) {
                    writeLockA = this.writeLockPool.a();
                    this.locks.put(str, writeLockA);
                }
                writeLockA.f12348b++;
            } catch (Throwable th) {
                throw th;
            }
        }
        writeLockA.f12347a.lock();
    }

    void b(String str) {
        WriteLock writeLock;
        synchronized (this) {
            try {
                writeLock = (WriteLock) Preconditions.checkNotNull(this.locks.get(str));
                int i2 = writeLock.f12348b;
                if (i2 < 1) {
                    throw new IllegalStateException("Cannot release a lock that is not held, safeKey: " + str + ", interestedThreads: " + writeLock.f12348b);
                }
                int i3 = i2 - 1;
                writeLock.f12348b = i3;
                if (i3 == 0) {
                    WriteLock writeLockRemove = this.locks.remove(str);
                    if (!writeLockRemove.equals(writeLock)) {
                        throw new IllegalStateException("Removed the wrong lock, expected to remove: " + writeLock + ", but actually removed: " + writeLockRemove + ", safeKey: " + str);
                    }
                    this.writeLockPool.b(writeLockRemove);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        writeLock.f12347a.unlock();
    }
}
