package com.google.android.datatransport.runtime;

import com.google.android.datatransport.runtime.logging.Logging;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
class SafeLoggingExecutor implements Executor {
    private final Executor delegate;

    static class SafeLoggingRunnable implements Runnable {
        private final Runnable delegate;

        SafeLoggingRunnable(Runnable runnable) {
            this.delegate = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.delegate.run();
            } catch (Exception e2) {
                Logging.e("Executor", "Background execution failure.", e2);
            }
        }
    }

    SafeLoggingExecutor(Executor executor) {
        this.delegate = executor;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        this.delegate.execute(new SafeLoggingRunnable(runnable));
    }
}
