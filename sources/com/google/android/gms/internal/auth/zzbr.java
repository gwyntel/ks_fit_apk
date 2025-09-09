package com.google.android.gms.internal.auth;

import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zzbr extends zzbd {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzbs f12992a;

    zzbr(zzbs zzbsVar) {
        this.f12992a = zzbsVar;
    }

    @Override // com.google.android.gms.internal.auth.zzbd, com.google.android.gms.internal.auth.zzbg
    public final void zzc(String str) {
        if (str != null) {
            this.f12992a.setResult((zzbs) new zzbv(str));
        } else {
            this.f12992a.setResult((zzbs) new zzbv(new Status(3006)));
        }
    }
}
