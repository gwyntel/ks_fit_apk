package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zbg extends zbl {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ Context f12652d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ GoogleSignInOptions f12653e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zbg(GoogleApiClient googleApiClient, Context context, GoogleSignInOptions googleSignInOptions) {
        super(googleApiClient);
        this.f12652d = context;
        this.f12653e = googleSignInOptions;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zbs) ((zbe) anyClient).getService()).zbe(new zbf(this), this.f12653e);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new GoogleSignInResult(null, status);
    }
}
