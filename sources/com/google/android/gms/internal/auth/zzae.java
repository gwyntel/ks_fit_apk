package com.google.android.gms.internal.auth;

import android.os.RemoteException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;

/* loaded from: classes3.dex */
final class zzae extends BaseImplementation.ApiMethodImpl {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f12980d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzae(zzal zzalVar, Api api, GoogleApiClient googleApiClient, String str) {
        super(api, googleApiClient);
        this.f12980d = str;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.auth.account.zze) ((zzam) anyClient).getService()).zzd(new zzad(this), this.f12980d);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new zzai(status, null);
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl, com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    @KeepForSdk
    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((zzae) obj);
    }
}
