package com.google.android.gms.internal.auth;

import android.util.Log;

/* loaded from: classes3.dex */
final class zzcv extends zzdc {
    zzcv(zzcz zzczVar, String str, Long l2, boolean z2) {
        super(zzczVar, str, l2, true, null);
    }

    @Override // com.google.android.gms.internal.auth.zzdc
    final /* synthetic */ Object a(Object obj) {
        try {
            return Long.valueOf(Long.parseLong((String) obj));
        } catch (NumberFormatException unused) {
            Log.e("PhenotypeFlag", "Invalid long value for " + this.f13011b + ": " + ((String) obj));
            return null;
        }
    }
}
