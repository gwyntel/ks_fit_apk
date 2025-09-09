package com.google.android.gms.internal.measurement;

import android.util.Log;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
final class zzgr extends zzgl<Double> {
    zzgr(zzgt zzgtVar, String str, Double d2, boolean z2) {
        super(zzgtVar, str, d2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzgl
    @Nullable
    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final Double f(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        }
        if (obj instanceof String) {
            try {
                return Double.valueOf(Double.parseDouble((String) obj));
            } catch (NumberFormatException unused) {
            }
        }
        Log.e("PhenotypeFlag", "Invalid double value for " + super.zzb() + ": " + String.valueOf(obj));
        return null;
    }
}
