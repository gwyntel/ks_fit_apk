package androidx.health.platform.client.impl.ipc.internal;

import androidx.annotation.RestrictTo;
import com.google.common.util.concurrent.SettableFuture;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public interface ExecutionTracker {
    void cancelPendingFutures(Throwable th);

    void track(SettableFuture<?> settableFuture);
}
