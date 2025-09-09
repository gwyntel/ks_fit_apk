package com.google.android.gms.internal.auth;

import android.util.Log;

/* loaded from: classes3.dex */
final class zzcw extends zzdc {
    zzcw(zzcz zzczVar, String str, Boolean bool, boolean z2) {
        super(zzczVar, str, bool, true, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.auth.zzdc
    final /* synthetic */ Object a(Object obj) {
        if (zzcb.zzc.matcher(obj).matches()) {
            return Boolean.TRUE;
        }
        if (zzcb.zzd.matcher(obj).matches()) {
            return Boolean.FALSE;
        }
        Log.e("PhenotypeFlag", "Invalid boolean value for " + this.f13011b + ": " + ((String) obj));
        return null;
    }
}
