package com.google.android.gms.internal.common;

import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes3.dex */
final class zzt extends zzw {

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ zzu f13040f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzt(zzu zzuVar, zzx zzxVar, CharSequence charSequence) {
        super(zzxVar, charSequence);
        this.f13040f = zzuVar;
    }

    @Override // com.google.android.gms.internal.common.zzw
    final int c(int i2) {
        return i2 + 1;
    }

    @Override // com.google.android.gms.internal.common.zzw
    final int d(int i2) {
        CharSequence charSequence = this.f13044a;
        int length = charSequence.length();
        zzs.zzb(i2, length, FirebaseAnalytics.Param.INDEX);
        while (i2 < length) {
            zzu zzuVar = this.f13040f;
            if (zzuVar.f13041a.zza(charSequence.charAt(i2))) {
                return i2;
            }
            i2++;
        }
        return -1;
    }
}
