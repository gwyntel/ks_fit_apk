package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anet.channel.util.ALog;
import anetwork.channel.Response;
import anetwork.channel.aidl.ParcelableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public class a implements Future<Response> {

    /* renamed from: a, reason: collision with root package name */
    private ParcelableFuture f7124a;

    /* renamed from: b, reason: collision with root package name */
    private Response f7125b;

    public a(ParcelableFuture parcelableFuture) {
        this.f7124a = parcelableFuture;
    }

    @Override // java.util.concurrent.Future
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Response get() throws ExecutionException, InterruptedException {
        Response response = this.f7125b;
        if (response != null) {
            return response;
        }
        ParcelableFuture parcelableFuture = this.f7124a;
        if (parcelableFuture != null) {
            try {
                return parcelableFuture.get(20000L);
            } catch (RemoteException e2) {
                ALog.w("anet.FutureResponse", "[get]", null, e2, new Object[0]);
            }
        }
        return null;
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z2) {
        ParcelableFuture parcelableFuture = this.f7124a;
        if (parcelableFuture == null) {
            return false;
        }
        try {
            return parcelableFuture.cancel(z2);
        } catch (RemoteException e2) {
            ALog.w("anet.FutureResponse", "[cancel]", null, e2, new Object[0]);
            return false;
        }
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        try {
            return this.f7124a.isCancelled();
        } catch (RemoteException e2) {
            ALog.w("anet.FutureResponse", "[isCancelled]", null, e2, new Object[0]);
            return false;
        }
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        try {
            return this.f7124a.isDone();
        } catch (RemoteException e2) {
            ALog.w("anet.FutureResponse", "[isDone]", null, e2, new Object[0]);
            return true;
        }
    }

    @Override // java.util.concurrent.Future
    public /* synthetic */ Response get(long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        return a(j2);
    }

    public a(Response response) {
        this.f7125b = response;
    }

    public Response a(long j2) throws ExecutionException, InterruptedException, TimeoutException {
        Response response = this.f7125b;
        if (response != null) {
            return response;
        }
        ParcelableFuture parcelableFuture = this.f7124a;
        if (parcelableFuture != null) {
            try {
                return parcelableFuture.get(j2);
            } catch (RemoteException e2) {
                ALog.w("anet.FutureResponse", "[get(long timeout, TimeUnit unit)]", null, e2, new Object[0]);
            }
        }
        return null;
    }
}
