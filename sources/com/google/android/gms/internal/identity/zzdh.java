package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdh extends LocationCallback {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13147a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzdz f13148b;

    zzdh(zzdz zzdzVar, TaskCompletionSource taskCompletionSource) {
        this.f13147a = taskCompletionSource;
        this.f13148b = zzdzVar;
    }

    @Override // com.google.android.gms.location.LocationCallback
    public final void onLocationResult(LocationResult locationResult) {
        this.f13147a.trySetResult(locationResult.getLastLocation());
        try {
            this.f13148b.zzw(ListenerHolders.createListenerKey(this, "GetCurrentLocation"), false, new TaskCompletionSource());
        } catch (RemoteException unused) {
        }
    }
}
