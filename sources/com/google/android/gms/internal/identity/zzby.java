package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.location.LastLocationRequest;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final /* synthetic */ class zzby implements RemoteCall {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ zzby f13124a = new zzby();

    private /* synthetic */ zzby() {
    }

    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final /* synthetic */ void accept(Object obj, Object obj2) throws RemoteException {
        ((zzdz) obj).zzq(new LastLocationRequest.Builder().build(), (TaskCompletionSource) obj2);
    }
}
