package com.aliyun.iot.aep.sdk.threadpool;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public class WeakHandler {
    private final Handler.Callback mCallback;
    private final b mExec;
    private Lock mLock;

    @VisibleForTesting
    final a mRunnables;

    static class c implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private final WeakReference<Runnable> f12049a;

        /* renamed from: b, reason: collision with root package name */
        private final WeakReference<a> f12050b;

        c(WeakReference<Runnable> weakReference, WeakReference<a> weakReference2) {
            this.f12049a = weakReference;
            this.f12050b = weakReference2;
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable = this.f12049a.get();
            a aVar = this.f12050b.get();
            if (aVar != null) {
                aVar.a();
            }
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public WeakHandler() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mRunnables = new a(reentrantLock, null);
        this.mCallback = null;
        this.mExec = new b();
    }

    private c wrapRunnable(@NonNull Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("Runnable can't be null");
        }
        a aVar = new a(this.mLock, runnable);
        this.mRunnables.a(aVar);
        return aVar.f12046d;
    }

    public final Looper getLooper() {
        return this.mExec.getLooper();
    }

    public final boolean hasMessages(int i2) {
        return this.mExec.hasMessages(i2);
    }

    public final boolean post(@NonNull Runnable runnable) {
        return this.mExec.post(wrapRunnable(runnable));
    }

    public final boolean postAtFrontOfQueue(Runnable runnable) {
        return this.mExec.postAtFrontOfQueue(wrapRunnable(runnable));
    }

    public final boolean postAtTime(@NonNull Runnable runnable, long j2) {
        return this.mExec.postAtTime(wrapRunnable(runnable), j2);
    }

    public final boolean postDelayed(Runnable runnable, long j2) {
        return this.mExec.postDelayed(wrapRunnable(runnable), j2);
    }

    public final void removeCallbacks(Runnable runnable) {
        c cVarA = this.mRunnables.a(runnable);
        if (cVarA != null) {
            this.mExec.removeCallbacks(cVarA);
        }
    }

    public final void removeCallbacksAndMessages(Object obj) {
        this.mExec.removeCallbacksAndMessages(obj);
    }

    public final void removeMessages(int i2) {
        this.mExec.removeMessages(i2);
    }

    public final boolean sendEmptyMessage(int i2) {
        return this.mExec.sendEmptyMessage(i2);
    }

    public final boolean sendEmptyMessageAtTime(int i2, long j2) {
        return this.mExec.sendEmptyMessageAtTime(i2, j2);
    }

    public final boolean sendEmptyMessageDelayed(int i2, long j2) {
        return this.mExec.sendEmptyMessageDelayed(i2, j2);
    }

    public final boolean sendMessage(Message message) {
        return this.mExec.sendMessage(message);
    }

    public final boolean sendMessageAtFrontOfQueue(Message message) {
        return this.mExec.sendMessageAtFrontOfQueue(message);
    }

    public boolean sendMessageAtTime(Message message, long j2) {
        return this.mExec.sendMessageAtTime(message, j2);
    }

    public final boolean sendMessageDelayed(Message message, long j2) {
        return this.mExec.sendMessageDelayed(message, j2);
    }

    static class b extends Handler {

        /* renamed from: a, reason: collision with root package name */
        private final WeakReference<Handler.Callback> f12048a;

        b() {
            this.f12048a = null;
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            Handler.Callback callback;
            WeakReference<Handler.Callback> weakReference = this.f12048a;
            if (weakReference == null || (callback = weakReference.get()) == null) {
                return;
            }
            callback.handleMessage(message);
        }

        b(WeakReference<Handler.Callback> weakReference) {
            this.f12048a = weakReference;
        }

        b(Looper looper) {
            super(looper);
            this.f12048a = null;
        }

        b(Looper looper, WeakReference<Handler.Callback> weakReference) {
            super(looper);
            this.f12048a = weakReference;
        }
    }

    public final boolean hasMessages(int i2, Object obj) {
        return this.mExec.hasMessages(i2, obj);
    }

    public final boolean postAtTime(Runnable runnable, Object obj, long j2) {
        return this.mExec.postAtTime(wrapRunnable(runnable), obj, j2);
    }

    public final void removeMessages(int i2, Object obj) {
        this.mExec.removeMessages(i2, obj);
    }

    public final void removeCallbacks(Runnable runnable, Object obj) {
        c cVarA = this.mRunnables.a(runnable);
        if (cVarA != null) {
            this.mExec.removeCallbacks(cVarA, obj);
        }
    }

    public WeakHandler(@Nullable Handler.Callback callback) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mRunnables = new a(reentrantLock, null);
        this.mCallback = callback;
        this.mExec = new b((WeakReference<Handler.Callback>) new WeakReference(callback));
    }

    static class a {

        /* renamed from: a, reason: collision with root package name */
        @Nullable
        a f12043a;

        /* renamed from: b, reason: collision with root package name */
        @Nullable
        a f12044b;

        /* renamed from: c, reason: collision with root package name */
        @NonNull
        final Runnable f12045c;

        /* renamed from: d, reason: collision with root package name */
        @NonNull
        final c f12046d;

        /* renamed from: e, reason: collision with root package name */
        @NonNull
        Lock f12047e;

        public a(@NonNull Lock lock, @NonNull Runnable runnable) {
            this.f12045c = runnable;
            this.f12047e = lock;
            this.f12046d = new c(new WeakReference(runnable), new WeakReference(this));
        }

        public c a() {
            this.f12047e.lock();
            try {
                a aVar = this.f12044b;
                if (aVar != null) {
                    aVar.f12043a = this.f12043a;
                }
                a aVar2 = this.f12043a;
                if (aVar2 != null) {
                    aVar2.f12044b = aVar;
                }
                this.f12044b = null;
                this.f12043a = null;
                this.f12047e.unlock();
                return this.f12046d;
            } catch (Throwable th) {
                this.f12047e.unlock();
                throw th;
            }
        }

        public void a(@NonNull a aVar) {
            this.f12047e.lock();
            try {
                a aVar2 = this.f12043a;
                if (aVar2 != null) {
                    aVar2.f12044b = aVar;
                }
                aVar.f12043a = aVar2;
                this.f12043a = aVar;
                aVar.f12044b = this;
                this.f12047e.unlock();
            } catch (Throwable th) {
                this.f12047e.unlock();
                throw th;
            }
        }

        @Nullable
        public c a(Runnable runnable) {
            this.f12047e.lock();
            try {
                for (a aVar = this.f12043a; aVar != null; aVar = aVar.f12043a) {
                    if (aVar.f12045c == runnable) {
                        return aVar.a();
                    }
                }
                this.f12047e.unlock();
                return null;
            } finally {
                this.f12047e.unlock();
            }
        }
    }

    public WeakHandler(@NonNull Looper looper) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mRunnables = new a(reentrantLock, null);
        this.mCallback = null;
        this.mExec = new b(looper);
    }

    public WeakHandler(@NonNull Looper looper, @NonNull Handler.Callback callback) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mRunnables = new a(reentrantLock, null);
        this.mCallback = callback;
        this.mExec = new b(looper, new WeakReference(callback));
    }
}
