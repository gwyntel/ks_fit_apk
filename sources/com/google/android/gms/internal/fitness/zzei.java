package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.SessionReadResult;
import java.util.ArrayList;

/* loaded from: classes3.dex */
final class zzei extends zzbc {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ SessionReadRequest f13094d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzei(zzep zzepVar, GoogleApiClient googleApiClient, SessionReadRequest sessionReadRequest) {
        super(googleApiClient);
        this.f13094d = sessionReadRequest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcd) ((zzbh) anyClient).getService()).zze(new SessionReadRequest(this.f13094d, new zzem(this, null)));
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new SessionReadResult(new ArrayList(), new ArrayList(), status);
    }
}
