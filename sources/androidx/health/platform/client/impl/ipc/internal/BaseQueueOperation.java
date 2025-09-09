package androidx.health.platform.client.impl.ipc.internal;

import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.google.common.base.Preconditions;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class BaseQueueOperation implements QueueOperation {
    private final ConnectionConfiguration mConnectionConfiguration;

    public BaseQueueOperation(@NonNull ConnectionConfiguration connectionConfiguration) {
        this.mConnectionConfiguration = (ConnectionConfiguration) Preconditions.checkNotNull(connectionConfiguration);
    }

    @Override // androidx.health.platform.client.impl.ipc.internal.QueueOperation
    public void execute(@NonNull IBinder iBinder) throws RemoteException {
    }

    @Override // androidx.health.platform.client.impl.ipc.internal.QueueOperation
    @NonNull
    public ConnectionConfiguration getConnectionConfiguration() {
        return this.mConnectionConfiguration;
    }

    @Override // androidx.health.platform.client.impl.ipc.internal.QueueOperation
    public void setException(@NonNull Throwable th) {
    }

    @Override // androidx.health.platform.client.impl.ipc.internal.QueueOperation
    @NonNull
    public QueueOperation trackExecution(@NonNull ExecutionTracker executionTracker) {
        return this;
    }
}
