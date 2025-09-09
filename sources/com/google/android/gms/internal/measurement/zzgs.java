package com.google.android.gms.internal.measurement;

import android.util.Log;

/* loaded from: classes3.dex */
final class zzgs extends zzgl<Boolean> {
    zzgs(zzgt zzgtVar, String str, Boolean bool, boolean z2) {
        super(zzgtVar, str, bool);
    }

    @Override // com.google.android.gms.internal.measurement.zzgl
    final /* synthetic */ Object f(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (zzft.zzb.matcher(str).matches()) {
                return Boolean.TRUE;
            }
            if (zzft.zzc.matcher(str).matches()) {
                return Boolean.FALSE;
            }
        }
        Log.e("PhenotypeFlag", "Invalid boolean value for " + super.zzb() + ": " + String.valueOf(obj));
        return null;
    }
}
