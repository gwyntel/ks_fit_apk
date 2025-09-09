package androidx.loader.content;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import androidx.annotation.NonNull;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
abstract class ModernAsyncTask<Result> {
    private static final String LOG_TAG = "AsyncTask";
    private static Handler sHandler;
    private volatile Status mStatus = Status.PENDING;

    /* renamed from: a, reason: collision with root package name */
    final AtomicBoolean f4606a = new AtomicBoolean();

    /* renamed from: b, reason: collision with root package name */
    final AtomicBoolean f4607b = new AtomicBoolean();
    private final FutureTask<Result> mFuture = new FutureTask<Result>(new Callable<Result>() { // from class: androidx.loader.content.ModernAsyncTask.1
        @Override // java.util.concurrent.Callable
        public Result call() {
            ModernAsyncTask.this.f4607b.set(true);
            Result result = null;
            try {
                Process.setThreadPriority(10);
                result = (Result) ModernAsyncTask.this.a();
                Binder.flushPendingCommands();
                return result;
            } finally {
            }
        }
    }) { // from class: androidx.loader.content.ModernAsyncTask.2
        @Override // java.util.concurrent.FutureTask
        protected void done() {
            try {
                ModernAsyncTask.this.f(get());
            } catch (InterruptedException e2) {
                Log.w(ModernAsyncTask.LOG_TAG, e2);
            } catch (CancellationException unused) {
                ModernAsyncTask.this.f(null);
            } catch (ExecutionException e3) {
                throw new RuntimeException("An error occurred while executing doInBackground()", e3.getCause());
            } catch (Throwable th) {
                throw new RuntimeException("An error occurred while executing doInBackground()", th);
            }
        }
    };

    /* renamed from: androidx.loader.content.ModernAsyncTask$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4612a;

        static {
            int[] iArr = new int[Status.values().length];
            f4612a = iArr;
            try {
                iArr[Status.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4612a[Status.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    ModernAsyncTask() {
    }

    private static Handler getHandler() {
        Handler handler;
        synchronized (ModernAsyncTask.class) {
            try {
                if (sHandler == null) {
                    sHandler = new Handler(Looper.getMainLooper());
                }
                handler = sHandler;
            } catch (Throwable th) {
                throw th;
            }
        }
        return handler;
    }

    protected abstract Object a();

    void b(Object obj) {
        if (isCancelled()) {
            c(obj);
        } else {
            d(obj);
        }
        this.mStatus = Status.FINISHED;
    }

    protected void c(Object obj) {
    }

    public final boolean cancel(boolean z2) {
        this.f4606a.set(true);
        return this.mFuture.cancel(z2);
    }

    protected void d(Object obj) {
    }

    void e(final Object obj) {
        getHandler().post(new Runnable() { // from class: androidx.loader.content.ModernAsyncTask.3
            @Override // java.lang.Runnable
            public void run() {
                ModernAsyncTask.this.b(obj);
            }
        });
    }

    public final void executeOnExecutor(@NonNull Executor executor) {
        if (this.mStatus == Status.PENDING) {
            this.mStatus = Status.RUNNING;
            executor.execute(this.mFuture);
            return;
        }
        int i2 = AnonymousClass4.f4612a[this.mStatus.ordinal()];
        if (i2 == 1) {
            throw new IllegalStateException("Cannot execute task: the task is already running.");
        }
        if (i2 == 2) {
            throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
        }
        throw new IllegalStateException("We should never reach this state");
    }

    void f(Object obj) {
        if (this.f4607b.get()) {
            return;
        }
        e(obj);
    }

    public final boolean isCancelled() {
        return this.f4606a.get();
    }
}
