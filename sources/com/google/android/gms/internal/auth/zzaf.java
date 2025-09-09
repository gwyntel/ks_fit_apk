package com.google.android.gms.internal.auth;

import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zzaf extends zzah {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzag f12981a;

    zzaf(zzag zzagVar) {
        this.f12981a = zzagVar;
    }

    @Override // com.google.android.gms.internal.auth.zzah, com.google.android.gms.auth.account.zzb
    public final void zzc(boolean z2) {
        this.f12981a.setResult((zzag) new zzak(z2 ? Status.RESULT_SUCCESS : zzal.zza));
    }
}
