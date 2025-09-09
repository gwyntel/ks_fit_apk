package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final /* synthetic */ class zzcc implements RemoteCall {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ zzcc f13128a = new zzcc();

    private /* synthetic */ zzcc() {
    }

    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final /* synthetic */ void accept(Object obj, Object obj2) throws RemoteException {
        ((zzdz) obj).zzz((TaskCompletionSource) obj2);
    }
}
