package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.BleDevice;

/* loaded from: classes3.dex */
final class zzct extends zzk {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ BleDevice f13066d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzct(zzcy zzcyVar, GoogleApiClient googleApiClient, BleDevice bleDevice) {
        super(googleApiClient);
        this.f13066d = bleDevice;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzbx) ((zzm) anyClient).getService()).zzd(new com.google.android.gms.fitness.request.zzf(this.f13066d.getAddress(), this.f13066d, (zzcp) new zzes(this)));
    }
}
