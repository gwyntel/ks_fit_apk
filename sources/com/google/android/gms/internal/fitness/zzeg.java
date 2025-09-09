package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.result.SessionStopResult;
import java.util.Collections;

/* loaded from: classes3.dex */
final class zzeg extends zzbc {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f13092d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzeg(zzep zzepVar, GoogleApiClient googleApiClient, String str, String str2) {
        super(googleApiClient);
        this.f13092d = str2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcd) ((zzbh) anyClient).getService()).zzh(new com.google.android.gms.fitness.request.zzav((String) null, this.f13092d, (zzcm) new zzeo(this, null)));
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new SessionStopResult(status, Collections.emptyList());
    }
}
