package com.google.android.gms.internal.measurement;

import android.util.Log;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
final class zzgp extends zzgl<Long> {
    zzgp(zzgt zzgtVar, String str, Long l2, boolean z2) {
        super(zzgtVar, str, l2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzgl
    @Nullable
    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final Long f(Object obj) {
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof String) {
            try {
                return Long.valueOf(Long.parseLong((String) obj));
            } catch (NumberFormatException unused) {
            }
        }
        Log.e("PhenotypeFlag", "Invalid long value for " + super.zzb() + ": " + String.valueOf(obj));
        return null;
    }
}
