package com.google.android.gms.auth.api.accounttransfer;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.auth.zzan;

/* loaded from: classes3.dex */
final class zzm extends zzan {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzn f12631a;

    zzm(zzn zznVar) {
        this.f12631a = zznVar;
    }

    @Override // com.google.android.gms.internal.auth.zzan, com.google.android.gms.internal.auth.zzat
    public final void zzd(Status status) {
        this.f12631a.f12630a.setException(new AccountTransferException(status));
    }

    @Override // com.google.android.gms.internal.auth.zzan, com.google.android.gms.internal.auth.zzat
    public final void zze() {
        this.f12631a.f12630a.setResult(null);
    }
}
