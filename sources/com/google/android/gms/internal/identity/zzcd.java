package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final /* synthetic */ class zzcd implements zzbg {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ zzcd f13129a = new zzcd();

    private /* synthetic */ zzcd() {
    }

    @Override // com.google.android.gms.internal.identity.zzbg
    public final /* synthetic */ void zza(zzdz zzdzVar, ListenerHolder.ListenerKey listenerKey, boolean z2, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzdzVar.zzv(listenerKey, z2, taskCompletionSource);
    }
}
