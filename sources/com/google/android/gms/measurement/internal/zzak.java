package com.google.android.gms.measurement.internal;

import com.google.android.gms.measurement.internal.zzie;
import java.util.EnumMap;

/* loaded from: classes3.dex */
final class zzak {
    private final EnumMap<zzie.zza, zzaj> zza;

    zzak() {
        this.zza = new EnumMap<>(zzie.zza.class);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("1");
        for (zzie.zza zzaVar : zzie.zza.values()) {
            zzaj zzajVar = this.zza.get(zzaVar);
            if (zzajVar == null) {
                zzajVar = zzaj.UNSET;
            }
            sb.append(zzajVar.zzj);
        }
        return sb.toString();
    }

    public final zzaj zza(zzie.zza zzaVar) {
        zzaj zzajVar = this.zza.get(zzaVar);
        return zzajVar == null ? zzaj.UNSET : zzajVar;
    }

    private zzak(EnumMap<zzie.zza, zzaj> enumMap) {
        EnumMap<zzie.zza, zzaj> enumMap2 = new EnumMap<>(zzie.zza.class);
        this.zza = enumMap2;
        enumMap2.putAll(enumMap);
    }

    public static zzak zza(String str) {
        EnumMap enumMap = new EnumMap(zzie.zza.class);
        if (str.length() >= zzie.zza.values().length) {
            int i2 = 0;
            if (str.charAt(0) == '1') {
                zzie.zza[] zzaVarArrValues = zzie.zza.values();
                int length = zzaVarArrValues.length;
                int i3 = 1;
                while (i2 < length) {
                    enumMap.put((EnumMap) zzaVarArrValues[i2], (zzie.zza) zzaj.zza(str.charAt(i3)));
                    i2++;
                    i3++;
                }
                return new zzak(enumMap);
            }
        }
        return new zzak();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(com.google.android.gms.measurement.internal.zzie.zza r3, int r4) {
        /*
            r2 = this;
            com.google.android.gms.measurement.internal.zzaj r0 = com.google.android.gms.measurement.internal.zzaj.UNSET
            r1 = -20
            if (r4 == r1) goto L17
            r1 = -10
            if (r4 == r1) goto L14
            if (r4 == 0) goto L17
            r1 = 30
            if (r4 == r1) goto L11
            goto L19
        L11:
            com.google.android.gms.measurement.internal.zzaj r0 = com.google.android.gms.measurement.internal.zzaj.INITIALIZATION
            goto L19
        L14:
            com.google.android.gms.measurement.internal.zzaj r0 = com.google.android.gms.measurement.internal.zzaj.MANIFEST
            goto L19
        L17:
            com.google.android.gms.measurement.internal.zzaj r0 = com.google.android.gms.measurement.internal.zzaj.API
        L19:
            java.util.EnumMap<com.google.android.gms.measurement.internal.zzie$zza, com.google.android.gms.measurement.internal.zzaj> r4 = r2.zza
            r4.put(r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzak.zza(com.google.android.gms.measurement.internal.zzie$zza, int):void");
    }

    public final void zza(zzie.zza zzaVar, zzaj zzajVar) {
        this.zza.put((EnumMap<zzie.zza, zzaj>) zzaVar, (zzie.zza) zzajVar);
    }
}
