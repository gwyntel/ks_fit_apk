package androidx.health.platform.client.impl.ipc.internal;

import androidx.annotation.RestrictTo;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class DefaultExecutionTracker implements ExecutionTracker {
    private final Set<SettableFuture<?>> mFuturesInProgress = new HashSet();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$track$0(SettableFuture settableFuture) {
        synchronized (this.mFuturesInProgress) {
            this.mFuturesInProgress.remove(settableFuture);
        }
    }

    @Override // androidx.health.platform.client.impl.ipc.internal.ExecutionTracker
    public void cancelPendingFutures(Throwable th) {
        HashSet hashSet;
        synchronized (this.mFuturesInProgress) {
            hashSet = new HashSet(this.mFuturesInProgress);
            this.mFuturesInProgress.clear();
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            ((SettableFuture) it.next()).setException(th);
        }
    }

    @Override // androidx.health.platform.client.impl.ipc.internal.ExecutionTracker
    public void track(final SettableFuture<?> settableFuture) {
        synchronized (this.mFuturesInProgress) {
            this.mFuturesInProgress.add(settableFuture);
            settableFuture.addListener(new Runnable() { // from class: androidx.health.platform.client.impl.ipc.internal.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f4365a.lambda$track$0(settableFuture);
                }
            }, MoreExecutors.directExecutor());
        }
    }
}
