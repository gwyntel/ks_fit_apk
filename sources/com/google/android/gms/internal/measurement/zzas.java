package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* loaded from: classes3.dex */
public final class zzas implements zzaq, Iterable<zzaq> {
    private final String zza;

    public zzas(String str) {
        if (str == null) {
            throw new IllegalArgumentException("StringValue cannot be null.");
        }
        this.zza = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzas) {
            return this.zza.equals(((zzas) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    @Override // java.lang.Iterable
    public final Iterator<zzaq> iterator() {
        return new zzau(this);
    }

    public final String toString() {
        return "\"" + this.zza + "\"";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0317  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x03c8  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x045e  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x04a9  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x04bd  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x04d3  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x059b  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x05b1  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x05ea  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0630  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0644  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x064d  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00cd A[PHI: r6 r7 r14 r15
      0x00cd: PHI (r6v31 java.lang.String) = 
      (r6v4 java.lang.String)
      (r6v5 java.lang.String)
      (r6v6 java.lang.String)
      (r6v7 java.lang.String)
      (r6v32 java.lang.String)
     binds: [B:111:0x019e, B:107:0x018c, B:103:0x017a, B:99:0x0168, B:43:0x00c5] A[DONT_GENERATE, DONT_INLINE]
      0x00cd: PHI (r7v13 java.lang.String) = 
      (r7v1 java.lang.String)
      (r7v2 java.lang.String)
      (r7v3 java.lang.String)
      (r7v4 java.lang.String)
      (r7v14 java.lang.String)
     binds: [B:111:0x019e, B:107:0x018c, B:103:0x017a, B:99:0x0168, B:43:0x00c5] A[DONT_GENERATE, DONT_INLINE]
      0x00cd: PHI (r14v10 java.lang.String) = 
      (r14v1 java.lang.String)
      (r14v2 java.lang.String)
      (r14v3 java.lang.String)
      (r14v4 java.lang.String)
      (r14v11 java.lang.String)
     binds: [B:111:0x019e, B:107:0x018c, B:103:0x017a, B:99:0x0168, B:43:0x00c5] A[DONT_GENERATE, DONT_INLINE]
      0x00cd: PHI (r15v7 java.lang.String) = 
      (r15v1 java.lang.String)
      (r15v2 java.lang.String)
      (r15v3 java.lang.String)
      (r15v4 java.lang.String)
      (r15v8 java.lang.String)
     binds: [B:111:0x019e, B:107:0x018c, B:103:0x017a, B:99:0x0168, B:43:0x00c5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x015c  */
    @Override // com.google.android.gms.internal.measurement.zzaq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.measurement.zzaq zza(java.lang.String r22, com.google.android.gms.internal.measurement.zzh r23, java.util.List<com.google.android.gms.internal.measurement.zzaq> r24) {
        /*
            Method dump skipped, instructions count: 1790
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzas.zza(java.lang.String, com.google.android.gms.internal.measurement.zzh, java.util.List):com.google.android.gms.internal.measurement.zzaq");
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final zzaq zzc() {
        return new zzas(this.zza);
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final Boolean zzd() {
        return Boolean.valueOf(!this.zza.isEmpty());
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final Double zze() {
        if (this.zza.isEmpty()) {
            return Double.valueOf(0.0d);
        }
        try {
            return Double.valueOf(this.zza);
        } catch (NumberFormatException unused) {
            return Double.valueOf(Double.NaN);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final String zzf() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final Iterator<zzaq> zzh() {
        return new zzav(this);
    }
}
