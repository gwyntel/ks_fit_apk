package androidx.health.platform.client.impl.ipc.internal;

import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public interface QueueOperation {
    void execute(IBinder iBinder) throws RemoteException;

    ConnectionConfiguration getConnectionConfiguration();

    void setException(Throwable th);

    QueueOperation trackExecution(ExecutionTracker executionTracker);
}
