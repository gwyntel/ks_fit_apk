package io.flutter.plugins.googlesignin;

import androidx.annotation.NonNull;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class BackgroundTaskRunner {
    private final ThreadPoolExecutor executor;

    public interface Callback<T> {
        void run(@NonNull Future<T> future);
    }

    public BackgroundTaskRunner(int i2) {
        this.executor = new ThreadPoolExecutor(i2, i2, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runInBackground$1(SettableFuture settableFuture, Callable callable) {
        if (settableFuture.isCancelled()) {
            return;
        }
        try {
            settableFuture.set(callable.call());
        } catch (Throwable th) {
            settableFuture.setException(th);
        }
    }

    public <T> void runInBackground(@NonNull Callable<T> callable, @NonNull final Callback<T> callback) {
        final ListenableFuture<T> listenableFutureRunInBackground = runInBackground(callable);
        listenableFutureRunInBackground.addListener(new Runnable() { // from class: io.flutter.plugins.googlesignin.a
            @Override // java.lang.Runnable
            public final void run() {
                callback.run(listenableFutureRunInBackground);
            }
        }, Executors.uiThreadExecutor());
    }

    @NonNull
    public <T> ListenableFuture<T> runInBackground(@NonNull final Callable<T> callable) {
        final SettableFuture settableFutureCreate = SettableFuture.create();
        this.executor.execute(new Runnable() { // from class: io.flutter.plugins.googlesignin.b
            @Override // java.lang.Runnable
            public final void run() {
                BackgroundTaskRunner.lambda$runInBackground$1(settableFutureCreate, callable);
            }
        });
        return settableFutureCreate;
    }
}
