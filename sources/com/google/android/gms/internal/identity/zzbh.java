package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import androidx.annotation.GuardedBy;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzbh implements RemoteCall, zzdr {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzbi f13117a;
    private final zzbg zzb;

    @GuardedBy("this")
    private ListenerHolder zzc;

    @GuardedBy("this")
    private boolean zzd = true;

    zzbh(zzbi zzbiVar, ListenerHolder listenerHolder, zzbg zzbgVar) {
        this.f13117a = zzbiVar;
        this.zzc = listenerHolder;
        this.zzb = zzbgVar;
    }

    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final /* bridge */ /* synthetic */ void accept(Object obj, Object obj2) throws RemoteException {
        ListenerHolder.ListenerKey listenerKey;
        boolean z2;
        zzdz zzdzVar = (zzdz) obj;
        TaskCompletionSource taskCompletionSource = (TaskCompletionSource) obj2;
        synchronized (this) {
            listenerKey = this.zzc.getListenerKey();
            z2 = this.zzd;
            this.zzc.clear();
        }
        if (listenerKey == null) {
            taskCompletionSource.setResult(Boolean.FALSE);
        } else {
            this.zzb.zza(zzdzVar, listenerKey, z2, taskCompletionSource);
        }
    }

    @Override // com.google.android.gms.internal.identity.zzdr
    public final synchronized ListenerHolder zza() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.identity.zzdr
    public final synchronized void zzb(ListenerHolder listenerHolder) {
        ListenerHolder listenerHolder2 = this.zzc;
        if (listenerHolder2 != listenerHolder) {
            listenerHolder2.clear();
            this.zzc = listenerHolder;
        }
    }

    @Override // com.google.android.gms.internal.identity.zzdr
    public final void zzc() {
        ListenerHolder.ListenerKey<?> listenerKey;
        synchronized (this) {
            this.zzd = false;
            listenerKey = this.zzc.getListenerKey();
        }
        if (listenerKey != null) {
            this.f13117a.doUnregisterEventListener(listenerKey, 2441);
        }
    }
}
