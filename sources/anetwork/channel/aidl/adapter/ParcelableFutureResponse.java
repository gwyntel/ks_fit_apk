package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anet.channel.util.ALog;
import anetwork.channel.Response;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class ParcelableFutureResponse extends ParcelableFuture.Stub {
    private static final String TAG = "anet.ParcelableFutureResponse";
    Future<Response> future;
    NetworkResponse response;

    public ParcelableFutureResponse(Future<Response> future) {
        this.future = future;
    }

    @Override // anetwork.channel.aidl.ParcelableFuture
    public boolean cancel(boolean z2) throws RemoteException {
        Future<Response> future = this.future;
        if (future == null) {
            return true;
        }
        return future.cancel(z2);
    }

    @Override // anetwork.channel.aidl.ParcelableFuture
    public NetworkResponse get(long j2) throws RemoteException {
        Future<Response> future = this.future;
        if (future == null) {
            NetworkResponse networkResponse = this.response;
            return networkResponse != null ? networkResponse : new NetworkResponse(-201);
        }
        try {
            return (NetworkResponse) future.get(j2, TimeUnit.MILLISECONDS);
        } catch (Exception e2) {
            if ("NO SUPPORT".equalsIgnoreCase(e2.getMessage())) {
                ALog.e(TAG, "[get]有listener将不支持future.get()方法，如有需要请listener传入null", null, e2, new Object[0]);
            }
            return new NetworkResponse(-201);
        }
    }

    @Override // anetwork.channel.aidl.ParcelableFuture
    public boolean isCancelled() throws RemoteException {
        Future<Response> future = this.future;
        if (future == null) {
            return true;
        }
        return future.isCancelled();
    }

    @Override // anetwork.channel.aidl.ParcelableFuture
    public boolean isDone() throws RemoteException {
        Future<Response> future = this.future;
        if (future == null) {
            return true;
        }
        return future.isDone();
    }
}
