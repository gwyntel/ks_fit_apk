package com.aliyun.alink.linksdk.connectsdk.tools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class WeakHandler {

    /* renamed from: a, reason: collision with root package name */
    @VisibleForTesting
    final ChainedRef f11077a;
    private final Handler.Callback mCallback;
    private final ExecHandler mExec;
    private Lock mLock;

    static class WeakRunnable implements Runnable {
        private final WeakReference<Runnable> mDelegate;
        private final WeakReference<ChainedRef> mReference;

        WeakRunnable(WeakReference<Runnable> weakReference, WeakReference<ChainedRef> weakReference2) {
            this.mDelegate = weakReference;
            this.mReference = weakReference2;
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable = this.mDelegate.get();
            ChainedRef chainedRef = this.mReference.get();
            if (chainedRef != null) {
                chainedRef.remove();
            }
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public WeakHandler() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.f11077a = new ChainedRef(reentrantLock, null);
        this.mCallback = null;
        this.mExec = new ExecHandler();
    }

    private WeakRunnable wrapRunnable(@NonNull Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("Runnable can't be null");
        }
        ChainedRef chainedRef = new ChainedRef(this.mLock, runnable);
        this.f11077a.insertAfter(chainedRef);
        return chainedRef.f11081d;
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
        WeakRunnable weakRunnableRemove = this.f11077a.remove(runnable);
        if (weakRunnableRemove != null) {
            this.mExec.removeCallbacks(weakRunnableRemove);
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

    static class ExecHandler extends Handler {
        private final WeakReference<Handler.Callback> mCallback;

        ExecHandler() {
            this.mCallback = null;
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            Handler.Callback callback;
            WeakReference<Handler.Callback> weakReference = this.mCallback;
            if (weakReference == null || (callback = weakReference.get()) == null) {
                return;
            }
            callback.handleMessage(message);
        }

        ExecHandler(WeakReference<Handler.Callback> weakReference) {
            this.mCallback = weakReference;
        }

        ExecHandler(Looper looper) {
            super(looper);
            this.mCallback = null;
        }

        ExecHandler(Looper looper, WeakReference<Handler.Callback> weakReference) {
            super(looper);
            this.mCallback = weakReference;
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
        WeakRunnable weakRunnableRemove = this.f11077a.remove(runnable);
        if (weakRunnableRemove != null) {
            this.mExec.removeCallbacks(weakRunnableRemove, obj);
        }
    }

    public WeakHandler(@Nullable Handler.Callback callback) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.f11077a = new ChainedRef(reentrantLock, null);
        this.mCallback = callback;
        this.mExec = new ExecHandler((WeakReference<Handler.Callback>) new WeakReference(callback));
    }

    static class ChainedRef {

        /* renamed from: a, reason: collision with root package name */
        @Nullable
        ChainedRef f11078a;

        /* renamed from: b, reason: collision with root package name */
        @Nullable
        ChainedRef f11079b;

        /* renamed from: c, reason: collision with root package name */
        @NonNull
        final Runnable f11080c;

        /* renamed from: d, reason: collision with root package name */
        @NonNull
        final WeakRunnable f11081d;

        /* renamed from: e, reason: collision with root package name */
        @NonNull
        Lock f11082e;

        public ChainedRef(@NonNull Lock lock, @NonNull Runnable runnable) {
            this.f11080c = runnable;
            this.f11082e = lock;
            this.f11081d = new WeakRunnable(new WeakReference(runnable), new WeakReference(this));
        }

        public void insertAfter(@NonNull ChainedRef chainedRef) {
            this.f11082e.lock();
            try {
                ChainedRef chainedRef2 = this.f11078a;
                if (chainedRef2 != null) {
                    chainedRef2.f11079b = chainedRef;
                }
                chainedRef.f11078a = chainedRef2;
                this.f11078a = chainedRef;
                chainedRef.f11079b = this;
                this.f11082e.unlock();
            } catch (Throwable th) {
                this.f11082e.unlock();
                throw th;
            }
        }

        public WeakRunnable remove() {
            this.f11082e.lock();
            try {
                ChainedRef chainedRef = this.f11079b;
                if (chainedRef != null) {
                    chainedRef.f11078a = this.f11078a;
                }
                ChainedRef chainedRef2 = this.f11078a;
                if (chainedRef2 != null) {
                    chainedRef2.f11079b = chainedRef;
                }
                this.f11079b = null;
                this.f11078a = null;
                this.f11082e.unlock();
                return this.f11081d;
            } catch (Throwable th) {
                this.f11082e.unlock();
                throw th;
            }
        }

        @Nullable
        public WeakRunnable remove(Runnable runnable) {
            this.f11082e.lock();
            try {
                for (ChainedRef chainedRef = this.f11078a; chainedRef != null; chainedRef = chainedRef.f11078a) {
                    if (chainedRef.f11080c == runnable) {
                        return chainedRef.remove();
                    }
                }
                this.f11082e.unlock();
                return null;
            } finally {
                this.f11082e.unlock();
            }
        }
    }

    public WeakHandler(@NonNull Looper looper) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.f11077a = new ChainedRef(reentrantLock, null);
        this.mCallback = null;
        this.mExec = new ExecHandler(looper);
    }

    public WeakHandler(@NonNull Looper looper, @NonNull Handler.Callback callback) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.f11077a = new ChainedRef(reentrantLock, null);
        this.mCallback = callback;
        this.mExec = new ExecHandler(looper, new WeakReference(callback));
    }
}
