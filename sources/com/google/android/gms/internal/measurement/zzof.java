package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzof extends zzoc {
    zzof(int i2) {
        super(i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzoc
    public final void zzd() {
        if (!zze()) {
            for (int i2 = 0; i2 < zza(); i2++) {
                Map.Entry entryZzb = zzb(i2);
                if (((zzlo) entryZzb.getKey()).zze()) {
                    entryZzb.setValue(Collections.unmodifiableList((List) entryZzb.getValue()));
                }
            }
            for (Map.Entry entry : zzb()) {
                if (((zzlo) entry.getKey()).zze()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzd();
    }
}
