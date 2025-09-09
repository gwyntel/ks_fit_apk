package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zabb implements ResultCallback {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ StatusPendingResult f12717a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ boolean f12718b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ GoogleApiClient f12719c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ zabe f12720d;

    zabb(zabe zabeVar, StatusPendingResult statusPendingResult, boolean z2, GoogleApiClient googleApiClient) {
        this.f12720d = zabeVar;
        this.f12717a = statusPendingResult;
        this.f12718b = z2;
        this.f12719c = googleApiClient;
    }

    @Override // com.google.android.gms.common.api.ResultCallback
    public final /* bridge */ /* synthetic */ void onResult(@NonNull Result result) {
        Status status = (Status) result;
        Storage.getInstance(this.f12720d.zan).zac();
        if (status.isSuccess() && this.f12720d.isConnected()) {
            zabe zabeVar = this.f12720d;
            zabeVar.disconnect();
            zabeVar.connect();
        }
        this.f12717a.setResult(status);
        if (this.f12718b) {
            this.f12719c.disconnect();
        }
    }
}
