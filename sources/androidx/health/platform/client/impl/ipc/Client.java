package androidx.health.platform.client.impl.ipc;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.health.platform.client.impl.ipc.internal.BaseQueueOperation;
import androidx.health.platform.client.impl.ipc.internal.ConnectionConfiguration;
import androidx.health.platform.client.impl.ipc.internal.ConnectionManager;
import androidx.health.platform.client.impl.ipc.internal.ExecutionTracker;
import androidx.health.platform.client.impl.ipc.internal.QueueOperation;
import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public abstract class Client<S extends IInterface> {
    private static final int UNKNOWN_VERSION = -1;

    /* renamed from: a, reason: collision with root package name */
    protected final ConnectionConfiguration f4348a;

    /* renamed from: b, reason: collision with root package name */
    protected final ConnectionManager f4349b;

    /* renamed from: c, reason: collision with root package name */
    protected volatile int f4350c = -1;
    private final RemoteOperation<S, Integer> mRemoteVersionGetter;
    private final ServiceGetter<S> mServiceGetter;

    /* JADX INFO: Access modifiers changed from: protected */
    public interface ServiceGetter<S> {
        S getService(IBinder iBinder);
    }

    public Client(@NonNull ClientConfiguration clientConfiguration, @NonNull ConnectionManager connectionManager, @NonNull final ServiceGetter<S> serviceGetter, @NonNull final RemoteOperation<S, Integer> remoteOperation) {
        this.f4348a = new ConnectionConfiguration(clientConfiguration.getServicePackageName(), clientConfiguration.getApiClientName(), clientConfiguration.getBindAction(), new QueueOperation() { // from class: androidx.health.platform.client.impl.ipc.Client.1
            @Override // androidx.health.platform.client.impl.ipc.internal.QueueOperation
            public void execute(IBinder iBinder) throws RemoteException {
                Client.this.f4350c = ((Integer) remoteOperation.execute((IInterface) serviceGetter.getService(iBinder))).intValue();
            }

            @Override // androidx.health.platform.client.impl.ipc.internal.QueueOperation
            public ConnectionConfiguration getConnectionConfiguration() {
                return Client.this.f4348a;
            }

            @Override // androidx.health.platform.client.impl.ipc.internal.QueueOperation
            public void setException(Throwable th) {
            }

            @Override // androidx.health.platform.client.impl.ipc.internal.QueueOperation
            public QueueOperation trackExecution(ExecutionTracker executionTracker) {
                return this;
            }
        });
        this.f4349b = connectionManager;
        this.mServiceGetter = serviceGetter;
        this.mRemoteVersionGetter = remoteOperation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$execute$0(RemoteOperation remoteOperation, IInterface iInterface, SettableFuture settableFuture) throws RemoteException {
        settableFuture.set(remoteOperation.execute(iInterface));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getCurrentRemoteVersion$1(Integer num) {
        this.f4350c = num.intValue();
        return Integer.valueOf(this.f4350c);
    }

    QueueOperation c(final RemoteFutureOperation remoteFutureOperation, final SettableFuture settableFuture) {
        return new BaseQueueOperation(this.f4348a) { // from class: androidx.health.platform.client.impl.ipc.Client.3
            @Override // androidx.health.platform.client.impl.ipc.internal.BaseQueueOperation, androidx.health.platform.client.impl.ipc.internal.QueueOperation
            public void execute(@NonNull IBinder iBinder) throws RemoteException {
                remoteFutureOperation.execute(Client.this.i(iBinder), settableFuture);
            }

            @Override // androidx.health.platform.client.impl.ipc.internal.BaseQueueOperation, androidx.health.platform.client.impl.ipc.internal.QueueOperation
            public void setException(@NonNull Throwable th) {
                settableFuture.setException(th);
            }

            @Override // androidx.health.platform.client.impl.ipc.internal.BaseQueueOperation, androidx.health.platform.client.impl.ipc.internal.QueueOperation
            @NonNull
            public QueueOperation trackExecution(@NonNull ExecutionTracker executionTracker) {
                executionTracker.track(settableFuture);
                return this;
            }
        };
    }

    protected ListenableFuture d(RemoteFutureOperation remoteFutureOperation) {
        SettableFuture settableFutureCreate = SettableFuture.create();
        this.f4349b.scheduleForExecution(c(remoteFutureOperation, settableFutureCreate));
        return settableFutureCreate;
    }

    protected ListenableFuture e(final RemoteOperation remoteOperation) {
        return d(new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.ipc.b
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                Client.lambda$execute$0(remoteOperation, (IInterface) obj, settableFuture);
            }
        });
    }

    protected ListenableFuture f(final int i2, final RemoteFutureOperation remoteFutureOperation) {
        final SettableFuture settableFutureCreate = SettableFuture.create();
        Futures.addCallback(h(false), new FutureCallback<Integer>() { // from class: androidx.health.platform.client.impl.ipc.Client.2
            @Override // com.google.common.util.concurrent.FutureCallback
            public void onFailure(@NonNull Throwable th) {
                settableFutureCreate.setException(th);
            }

            @Override // com.google.common.util.concurrent.FutureCallback
            public void onSuccess(Integer num) {
                if (num.intValue() >= i2) {
                    Client client = Client.this;
                    client.f4349b.scheduleForExecution(client.c(remoteFutureOperation, settableFutureCreate));
                } else {
                    Client client2 = Client.this;
                    client2.f4349b.scheduleForExecution(new BaseQueueOperation(client2.f4348a));
                    settableFutureCreate.setException(Client.this.g(num.intValue(), i2));
                }
            }
        }, MoreExecutors.directExecutor());
        return settableFutureCreate;
    }

    protected Exception g(int i2, int i3) {
        return new ApiVersionException(i2, i3);
    }

    protected ListenableFuture h(boolean z2) {
        return (this.f4350c == -1 || z2) ? Futures.transform(e(this.mRemoteVersionGetter), new Function() { // from class: androidx.health.platform.client.impl.ipc.a
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return this.f4361a.lambda$getCurrentRemoteVersion$1((Integer) obj);
            }
        }, MoreExecutors.directExecutor()) : Futures.immediateFuture(Integer.valueOf(this.f4350c));
    }

    IInterface i(IBinder iBinder) {
        return this.mServiceGetter.getService(iBinder);
    }
}
