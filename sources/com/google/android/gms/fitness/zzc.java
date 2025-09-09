package com.google.android.gms.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.request.zzab;
import com.google.android.gms.internal.fitness.zzbx;
import com.google.android.gms.internal.fitness.zzcp;
import com.google.android.gms.internal.fitness.zzes;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

/* loaded from: classes3.dex */
final class zzc implements RemoteCall {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ListenerHolder f12958a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ List f12959b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ int f12960c;

    zzc(BleClient bleClient, ListenerHolder listenerHolder, List list, int i2) {
        this.f12958a = listenerHolder;
        this.f12959b = list;
        this.f12960c = i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final /* bridge */ /* synthetic */ void accept(Object obj, Object obj2) throws RemoteException {
        ((zzbx) ((com.google.android.gms.internal.fitness.zzm) obj).getService()).zzf(new StartBleScanRequest(this.f12959b, (zzab) com.google.android.gms.fitness.request.zzc.zza().zzb(this.f12958a), this.f12960c, (zzcp) zzes.zzc((TaskCompletionSource) obj2)));
    }
}
