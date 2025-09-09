package com.xiaomi.account.auth;

import android.accounts.OperationCanceledException;
import android.os.Looper;
import com.xiaomi.account.openauth.XMAuthericationException;
import com.xiaomi.account.openauth.XiaomiOAuthFuture;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class XiaomiOAuthFutureImpl<V> extends FutureTask<V> implements XiaomiOAuthFuture<V> {
    private static final long DEFAULT_TIMEOUT_MINUTE = 10;

    public XiaomiOAuthFutureImpl(Callable<V> callable) {
        super(callable);
    }

    private void ensureNotOnMainThread() {
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null && looperMyLooper == Looper.getMainLooper()) {
            throw new IllegalStateException("calling this from your main thread can lead to deadlock");
        }
    }

    private V internalGetResult(Long l2, TimeUnit timeUnit) throws OperationCanceledException, IOException, XMAuthericationException {
        if (!isDone()) {
            ensureNotOnMainThread();
        }
        try {
            try {
                try {
                    return l2 == null ? get() : get(l2.longValue(), timeUnit);
                } catch (CancellationException unused) {
                    throw new OperationCanceledException();
                } catch (ExecutionException e2) {
                    Throwable cause = e2.getCause();
                    if (cause instanceof IOException) {
                        throw ((IOException) cause);
                    }
                    if (cause instanceof RuntimeException) {
                        throw ((RuntimeException) cause);
                    }
                    if (cause instanceof Error) {
                        throw ((Error) cause);
                    }
                    if (cause instanceof XMAuthericationException) {
                        throw ((XMAuthericationException) cause);
                    }
                    if (cause instanceof OperationCanceledException) {
                        throw ((OperationCanceledException) cause);
                    }
                    throw new XMAuthericationException(cause);
                }
            } catch (InterruptedException | TimeoutException unused2) {
                cancel(true);
                throw new OperationCanceledException();
            }
        } finally {
            cancel(true);
        }
    }

    @Override // com.xiaomi.account.openauth.XiaomiOAuthFuture
    public V getResult() throws OperationCanceledException, IOException, XMAuthericationException {
        return internalGetResult(Long.valueOf(DEFAULT_TIMEOUT_MINUTE), TimeUnit.MINUTES);
    }

    @Override // com.xiaomi.account.openauth.XiaomiOAuthFuture
    public V getResult(long j2, TimeUnit timeUnit) throws OperationCanceledException, IOException, XMAuthericationException {
        return internalGetResult(Long.valueOf(j2), timeUnit);
    }
}
