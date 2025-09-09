package androidx.health.platform.client.impl.ipc;

import android.os.RemoteException;
import androidx.annotation.RestrictTo;
import com.google.common.util.concurrent.SettableFuture;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public interface RemoteFutureOperation<S, R> {
    void execute(S s2, SettableFuture<R> settableFuture) throws RemoteException;
}
