package com.google.android.gms.internal.auth;

import android.os.RemoteException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;

/* loaded from: classes3.dex */
final class zzac extends BaseImplementation.ApiMethodImpl {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ boolean f12978d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzac(zzal zzalVar, Api api, GoogleApiClient googleApiClient, boolean z2) {
        super(api, googleApiClient);
        this.f12978d = z2;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.auth.account.zze) ((zzam) anyClient).getService()).zzf(this.f12978d);
        setResult((zzac) new zzaj(Status.RESULT_SUCCESS));
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final Result createFailedResult(Status status) {
        return new zzaj(status);
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl, com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    @KeepForSdk
    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((zzac) obj);
    }
}
