package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.LocationSettingsRequest;

/* loaded from: classes3.dex */
final class zzcx extends zzcy {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ LocationSettingsRequest f13140d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcx(zzcz zzczVar, GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest, String str) {
        super(googleApiClient);
        this.f13140d = locationSettingsRequest;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        LocationSettingsRequest locationSettingsRequest = this.f13140d;
        Preconditions.checkArgument(locationSettingsRequest != null, "locationSettingsRequest can't be null");
        ((zzv) ((zzdz) anyClient).getService()).zzD(locationSettingsRequest, new zzdf(this), null);
    }
}
