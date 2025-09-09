package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzjn implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zza;
    private final /* synthetic */ zzin zzb;

    zzjn(zzin zzinVar, com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        this.zzb = zzinVar;
        this.zza = zzcvVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r7 = this;
            com.google.android.gms.measurement.internal.zzin r0 = r7.zzb
            com.google.android.gms.measurement.internal.zzly r0 = r0.zzp()
            boolean r1 = com.google.android.gms.internal.measurement.zzsn.zzb()
            r2 = 0
            if (r1 == 0) goto L68
            com.google.android.gms.measurement.internal.zzaf r1 = r0.zze()
            com.google.android.gms.measurement.internal.zzff<java.lang.Boolean> r3 = com.google.android.gms.measurement.internal.zzbi.zzbx
            boolean r1 = r1.zza(r3)
            if (r1 == 0) goto L68
            com.google.android.gms.measurement.internal.zzge r1 = r0.zzk()
            com.google.android.gms.measurement.internal.zzie r1 = r1.p()
            boolean r1 = r1.zzh()
            if (r1 != 0) goto L36
            com.google.android.gms.measurement.internal.zzfs r0 = r0.zzj()
            com.google.android.gms.measurement.internal.zzfu r0 = r0.zzv()
            java.lang.String r1 = "Analytics storage consent denied; will not get session id"
            r0.zza(r1)
        L34:
            r0 = r2
            goto L76
        L36:
            com.google.android.gms.measurement.internal.zzge r1 = r0.zzk()
            com.google.android.gms.common.util.Clock r3 = r0.zzb()
            long r3 = r3.currentTimeMillis()
            boolean r1 = r1.e(r3)
            if (r1 != 0) goto L34
            com.google.android.gms.measurement.internal.zzge r1 = r0.zzk()
            com.google.android.gms.measurement.internal.zzgf r1 = r1.zzl
            long r3 = r1.zza()
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L59
            goto L34
        L59:
            com.google.android.gms.measurement.internal.zzge r0 = r0.zzk()
            com.google.android.gms.measurement.internal.zzgf r0 = r0.zzl
            long r0 = r0.zza()
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            goto L76
        L68:
            com.google.android.gms.measurement.internal.zzfs r0 = r0.zzj()
            com.google.android.gms.measurement.internal.zzfu r0 = r0.zzv()
            java.lang.String r1 = "getSessionId has been disabled."
            r0.zza(r1)
            goto L34
        L76:
            if (r0 == 0) goto L8a
            com.google.android.gms.measurement.internal.zzin r1 = r7.zzb
            com.google.android.gms.measurement.internal.zzhc r1 = r1.f13286a
            com.google.android.gms.measurement.internal.zzne r1 = r1.zzt()
            com.google.android.gms.internal.measurement.zzcv r2 = r7.zza
            long r3 = r0.longValue()
            r1.zza(r2, r3)
            return
        L8a:
            com.google.android.gms.internal.measurement.zzcv r0 = r7.zza     // Catch: android.os.RemoteException -> L90
            r0.zza(r2)     // Catch: android.os.RemoteException -> L90
            return
        L90:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzin r1 = r7.zzb
            com.google.android.gms.measurement.internal.zzhc r1 = r1.f13286a
            com.google.android.gms.measurement.internal.zzfs r1 = r1.zzj()
            com.google.android.gms.measurement.internal.zzfu r1 = r1.zzg()
            java.lang.String r2 = "getSessionId failed with exception"
            r1.zza(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjn.run():void");
    }
}
