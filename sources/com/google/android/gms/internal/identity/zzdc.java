package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final /* synthetic */ class zzdc implements RemoteCall {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ zzdc f13141a = new zzdc();

    private /* synthetic */ zzdc() {
    }

    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final /* synthetic */ void accept(Object obj, Object obj2) throws RemoteException {
        ((zzv) ((zzdz) obj).getService()).zzE(new zzdm((TaskCompletionSource) obj2));
    }
}
