package com.google.android.gms.internal.auth;

import android.util.Log;

/* loaded from: classes3.dex */
final class zzcx extends zzdc {
    zzcx(zzcz zzczVar, String str, Double d2, boolean z2) {
        super(zzczVar, str, d2, true, null);
    }

    @Override // com.google.android.gms.internal.auth.zzdc
    final /* synthetic */ Object a(Object obj) {
        try {
            return Double.valueOf(Double.parseDouble((String) obj));
        } catch (NumberFormatException unused) {
            Log.e("PhenotypeFlag", "Invalid double value for " + this.f13011b + ": " + ((String) obj));
            return null;
        }
    }
}
