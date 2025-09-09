package androidx.health.platform.client.impl.ipc.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.RestrictTo;
import androidx.health.platform.client.impl.logger.Logger;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ServiceConnection implements android.content.ServiceConnection {
    private static final int MAX_RETRIES = 10;
    private static final String TAG = "ServiceConnection";

    /* renamed from: a, reason: collision with root package name */
    IBinder f4363a;

    /* renamed from: b, reason: collision with root package name */
    volatile boolean f4364b;
    private final Callback mCallback;
    private final ConnectionConfiguration mConnectionConfiguration;
    private final Context mContext;
    private final ExecutionTracker mExecutionTracker;
    private int mServiceConnectionRetry;
    private final Queue<QueueOperation> mOperationQueue = new ConcurrentLinkedQueue();
    private final Map<ListenerKey, QueueOperation> mRegisteredListeners = new HashMap();
    private final IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: androidx.health.platform.client.impl.ipc.internal.b
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            this.f4367a.lambda$new$0();
        }
    };

    public interface Callback {
        boolean isBindToSelfEnabled();

        void onConnected(ServiceConnection serviceConnection);

        void onDisconnected(ServiceConnection serviceConnection, long j2);
    }

    ServiceConnection(Context context, ConnectionConfiguration connectionConfiguration, ExecutionTracker executionTracker, Callback callback) {
        this.mContext = (Context) Preconditions.checkNotNull(context);
        this.mConnectionConfiguration = (ConnectionConfiguration) Preconditions.checkNotNull(connectionConfiguration);
        this.mExecutionTracker = (ExecutionTracker) Preconditions.checkNotNull(executionTracker);
        this.mCallback = (Callback) Preconditions.checkNotNull(callback);
    }

    private void cancelAllOperationsInQueue(Throwable th) {
        Iterator it = new ArrayList(this.mOperationQueue).iterator();
        while (it.hasNext()) {
            QueueOperation queueOperation = (QueueOperation) it.next();
            if (this.mOperationQueue.remove(queueOperation)) {
                queueOperation.setException(th);
            }
        }
    }

    private void cleanOnDeath(IBinder iBinder) throws RemoteException {
        try {
            iBinder.linkToDeath(this.mDeathRecipient, 0);
        } catch (RemoteException e2) {
            Logger.warning(TAG, "Cannot link to death, binder already died. Cleaning operations.", e2);
            handleRetriableDisconnection(e2);
        }
    }

    private String getBindPackageName() {
        return this.mCallback.isBindToSelfEnabled() ? this.mContext.getPackageName() : this.mConnectionConfiguration.d();
    }

    private static int getRetryDelayMs(int i2) {
        return 200 << i2;
    }

    private void handleNonRetriableDisconnection(Throwable th) {
        this.mServiceConnectionRetry = 10;
        handleRetriableDisconnection(th);
    }

    private synchronized void handleRetriableDisconnection(Throwable th) {
        if (isConnected()) {
            Logger.warning(TAG, "Connection is already re-established. No need to reconnect again");
            return;
        }
        b(th);
        if (this.mServiceConnectionRetry < 10) {
            Logger.warning(TAG, "WCS SDK Client '" + this.mConnectionConfiguration.b() + "' disconnected, retrying connection. Retry attempt: " + this.mServiceConnectionRetry, th);
            this.mCallback.onDisconnected(this, (long) getRetryDelayMs(this.mServiceConnectionRetry));
        } else {
            Logger.error(TAG, "Connection disconnected and maximum number of retries reached.", th);
        }
    }

    private boolean isConnected() {
        IBinder iBinder = this.f4363a;
        return iBinder != null && iBinder.isBinderAlive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        Logger.warning(TAG, "Binder died for client:" + this.mConnectionConfiguration.b());
        handleRetriableDisconnection(new RemoteException("Binder died"));
    }

    private void tryClearConnection() {
        if (this.f4364b) {
            try {
                this.mContext.unbindService(this);
            } catch (IllegalArgumentException e2) {
                Logger.error(TAG, "Failed to unbind the service. Ignoring and continuing", e2);
            }
            this.f4364b = false;
        }
        IBinder iBinder = this.f4363a;
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this.mDeathRecipient, 0);
            } catch (NoSuchElementException e3) {
                Logger.error(TAG, "mDeathRecipient not linked", e3);
            }
            this.f4363a = null;
        }
        Logger.debug(TAG, "unbindService called");
    }

    void b(Throwable th) {
        tryClearConnection();
        this.mExecutionTracker.cancelPendingFutures(th);
        cancelAllOperationsInQueue(th);
    }

    boolean c() {
        if (!this.mOperationQueue.isEmpty() || !this.mRegisteredListeners.isEmpty()) {
            return false;
        }
        tryClearConnection();
        return true;
    }

    public void connect() {
        if (this.f4364b) {
            return;
        }
        try {
            this.f4364b = this.mContext.bindService(new Intent().setPackage(getBindPackageName()).setAction(this.mConnectionConfiguration.a()), this, 129);
            if (this.f4364b) {
                return;
            }
            Logger.error(TAG, "Connection to service is not available for package '" + this.mConnectionConfiguration.d() + "' and action '" + this.mConnectionConfiguration.a() + "'.");
            handleNonRetriableDisconnection(new IllegalStateException("Service not available"));
        } catch (SecurityException e2) {
            Logger.warning(TAG, "Failed to bind connection '" + this.mConnectionConfiguration.c() + "', no permission or service not found.", e2);
            this.f4364b = false;
            this.f4363a = null;
            throw e2;
        }
    }

    void d(QueueOperation queueOperation) {
        if (isConnected()) {
            e(queueOperation);
        } else {
            this.mOperationQueue.add(queueOperation);
            connect();
        }
    }

    void e(QueueOperation queueOperation) {
        try {
            queueOperation.trackExecution(this.mExecutionTracker);
            queueOperation.execute((IBinder) Preconditions.checkNotNull(this.f4363a));
        } catch (DeadObjectException e2) {
            handleRetriableDisconnection(e2);
        } catch (RemoteException e3) {
            e = e3;
            queueOperation.setException(e);
        } catch (RuntimeException e4) {
            e = e4;
            queueOperation.setException(e);
        }
    }

    void f() {
        Iterator it = new ArrayList(this.mOperationQueue).iterator();
        while (it.hasNext()) {
            QueueOperation queueOperation = (QueueOperation) it.next();
            if (this.mOperationQueue.remove(queueOperation)) {
                e(queueOperation);
            }
        }
    }

    void g() {
        if (this.mRegisteredListeners.isEmpty()) {
            Logger.debug(TAG, "No listeners registered, service " + this.mConnectionConfiguration.b() + " is not automatically reconnected.");
            return;
        }
        this.mServiceConnectionRetry++;
        Logger.debug(TAG, "Listeners for service " + this.mConnectionConfiguration.b() + " are registered, reconnecting.");
        connect();
    }

    void h() {
        for (Map.Entry<ListenerKey, QueueOperation> entry : this.mRegisteredListeners.entrySet()) {
            Logger.debug(TAG, "Re-registering listener: " + entry.getKey());
            e(entry.getValue());
        }
    }

    void i() {
        this.mOperationQueue.add(this.mConnectionConfiguration.e());
    }

    void j(ListenerKey listenerKey, QueueOperation queueOperation) {
        this.mRegisteredListeners.put(listenerKey, queueOperation);
        if (isConnected()) {
            d(queueOperation);
        } else {
            connect();
        }
    }

    void k(ListenerKey listenerKey, QueueOperation queueOperation) {
        this.mRegisteredListeners.remove(listenerKey);
        d(queueOperation);
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(ComponentName componentName) {
        Logger.error(TAG, "Binding died for client '" + this.mConnectionConfiguration.b() + "'.");
        handleRetriableDisconnection(new RemoteException("Binding died"));
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName componentName) {
        Logger.error(TAG, "Cannot bind client '" + this.mConnectionConfiguration.b() + "', binder is null");
        handleRetriableDisconnection(new IllegalStateException("Null binding"));
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
        Logger.debug(TAG, "onServiceConnected(), componentName = " + componentName);
        if (iBinder == null) {
            Logger.error(TAG, "Service connected but binder is null.");
            return;
        }
        this.mServiceConnectionRetry = 0;
        cleanOnDeath(iBinder);
        this.f4363a = iBinder;
        this.mCallback.onConnected(this);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Logger.debug(TAG, "onServiceDisconnected(), componentName = " + componentName);
    }
}
