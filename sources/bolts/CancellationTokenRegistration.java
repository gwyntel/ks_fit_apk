package bolts;

import java.io.Closeable;

/* loaded from: classes2.dex */
public class CancellationTokenRegistration implements Closeable {
    private Runnable action;
    private boolean closed;
    private final Object lock = new Object();
    private CancellationTokenSource tokenSource;

    CancellationTokenRegistration(CancellationTokenSource cancellationTokenSource, Runnable runnable) {
        this.tokenSource = cancellationTokenSource;
        this.action = runnable;
    }

    private void throwIfClosed() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.lock) {
            try {
                if (this.closed) {
                    return;
                }
                this.closed = true;
                this.tokenSource.f(this);
                this.tokenSource = null;
                this.action = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void e() {
        synchronized (this.lock) {
            throwIfClosed();
            this.action.run();
            close();
        }
    }
}
