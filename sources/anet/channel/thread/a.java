package anet.channel.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
class a extends ThreadPoolExecutor {
    public a(int i2, int i3, long j2, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory) {
        super(i2, i3, j2, timeUnit, blockingQueue, threadFactory);
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t2) {
        return new C0013a(runnable, t2);
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new C0013a(callable);
    }

    /* renamed from: anet.channel.thread.a$a, reason: collision with other inner class name */
    class C0013a<V> extends FutureTask<V> implements Comparable<C0013a<V>> {

        /* renamed from: b, reason: collision with root package name */
        private Object f7074b;

        public C0013a(Callable<V> callable) {
            super(callable);
            this.f7074b = callable;
        }

        @Override // java.lang.Comparable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compareTo(C0013a<V> c0013a) {
            if (this == c0013a) {
                return 0;
            }
            if (c0013a == null) {
                return -1;
            }
            Object obj = this.f7074b;
            if (obj != null && c0013a.f7074b != null && obj.getClass().equals(c0013a.f7074b.getClass())) {
                Object obj2 = this.f7074b;
                if (obj2 instanceof Comparable) {
                    return ((Comparable) obj2).compareTo(c0013a.f7074b);
                }
            }
            return 0;
        }

        public C0013a(Runnable runnable, V v2) {
            super(runnable, v2);
            this.f7074b = runnable;
        }
    }
}
