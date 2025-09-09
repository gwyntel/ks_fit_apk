package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zznp<T> implements zzob<T> {
    private final zznj zza;
    private final zzow<?, ?> zzb;
    private final boolean zzc;
    private final zzll<?> zzd;

    private zznp(zzow<?, ?> zzowVar, zzll<?> zzllVar, zznj zznjVar) {
        this.zzb = zzowVar;
        this.zzc = zzllVar.h(zznjVar);
        this.zzd = zzllVar;
        this.zza = zznjVar;
    }

    static zznp a(zzow zzowVar, zzll zzllVar, zznj zznjVar) {
        return new zznp(zzowVar, zzllVar, zznjVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final int zza(T t2) {
        zzow<?, ?> zzowVar = this.zzb;
        int iK = zzowVar.k(zzowVar.q(t2));
        return this.zzc ? iK + this.zzd.b(t2).zza() : iK;
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final int zzb(T t2) {
        int iHashCode = this.zzb.q(t2).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.b(t2).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final void zzc(T t2) {
        this.zzb.s(t2);
        this.zzd.j(t2);
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final boolean zzd(T t2) {
        return this.zzd.b(t2).zzg();
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final boolean zzb(T t2, T t3) {
        if (!this.zzb.q(t2).equals(this.zzb.q(t3))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.b(t2).equals(this.zzd.b(t3));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final T zza() {
        zznj zznjVar = this.zza;
        if (zznjVar instanceof zzlw) {
            return (T) ((zzlw) zznjVar).n();
        }
        return (T) zznjVar.zzcd().zzac();
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final void zza(T t2, T t3) {
        zzod.j(this.zzb, t2, t3);
        if (this.zzc) {
            zzod.h(this.zzd, t2, t3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0088 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[LOOP:0: B:46:0x000c->B:54:?, LOOP_END, SYNTHETIC] */
    @Override // com.google.android.gms.internal.measurement.zzob
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r11, com.google.android.gms.internal.measurement.zzny r12, com.google.android.gms.internal.measurement.zzlj r13) throws java.io.IOException {
        /*
            r10 = this;
            com.google.android.gms.internal.measurement.zzow<?, ?> r0 = r10.zzb
            com.google.android.gms.internal.measurement.zzll<?> r1 = r10.zzd
            java.lang.Object r2 = r0.o(r11)
            com.google.android.gms.internal.measurement.zzlm r3 = r1.i(r11)
        Lc:
            int r4 = r12.zzc()     // Catch: java.lang.Throwable -> L34
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 != r5) goto L19
            r0.n(r11, r2)
            return
        L19:
            int r4 = r12.zzd()     // Catch: java.lang.Throwable -> L34
            r6 = 11
            if (r4 == r6) goto L40
            r5 = r4 & 7
            r6 = 2
            if (r5 != r6) goto L3b
            com.google.android.gms.internal.measurement.zznj r5 = r10.zza     // Catch: java.lang.Throwable -> L34
            int r4 = r4 >>> 3
            java.lang.Object r4 = r1.c(r13, r5, r4)     // Catch: java.lang.Throwable -> L34
            if (r4 == 0) goto L36
            r1.f(r12, r4, r13, r3)     // Catch: java.lang.Throwable -> L34
            goto L85
        L34:
            r12 = move-exception
            goto L91
        L36:
            boolean r4 = r0.j(r2, r12)     // Catch: java.lang.Throwable -> L34
            goto L86
        L3b:
            boolean r4 = r12.zzt()     // Catch: java.lang.Throwable -> L34
            goto L86
        L40:
            r4 = 0
            r6 = 0
            r7 = r6
            r6 = r4
        L44:
            int r8 = r12.zzc()     // Catch: java.lang.Throwable -> L34
            if (r8 == r5) goto L72
            int r8 = r12.zzd()     // Catch: java.lang.Throwable -> L34
            r9 = 16
            if (r8 != r9) goto L5d
            int r7 = r12.zzj()     // Catch: java.lang.Throwable -> L34
            com.google.android.gms.internal.measurement.zznj r4 = r10.zza     // Catch: java.lang.Throwable -> L34
            java.lang.Object r4 = r1.c(r13, r4, r7)     // Catch: java.lang.Throwable -> L34
            goto L44
        L5d:
            r9 = 26
            if (r8 != r9) goto L6c
            if (r4 == 0) goto L67
            r1.f(r12, r4, r13, r3)     // Catch: java.lang.Throwable -> L34
            goto L44
        L67:
            com.google.android.gms.internal.measurement.zzkm r6 = r12.zzp()     // Catch: java.lang.Throwable -> L34
            goto L44
        L6c:
            boolean r8 = r12.zzt()     // Catch: java.lang.Throwable -> L34
            if (r8 != 0) goto L44
        L72:
            int r5 = r12.zzd()     // Catch: java.lang.Throwable -> L34
            r8 = 12
            if (r5 != r8) goto L8c
            if (r6 == 0) goto L85
            if (r4 == 0) goto L82
            r1.e(r6, r4, r13, r3)     // Catch: java.lang.Throwable -> L34
            goto L85
        L82:
            r0.f(r2, r7, r6)     // Catch: java.lang.Throwable -> L34
        L85:
            r4 = 1
        L86:
            if (r4 != 0) goto Lc
            r0.n(r11, r2)
            return
        L8c:
            com.google.android.gms.internal.measurement.zzme r12 = com.google.android.gms.internal.measurement.zzme.zzb()     // Catch: java.lang.Throwable -> L34
            throw r12     // Catch: java.lang.Throwable -> L34
        L91:
            r0.n(r11, r2)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznp.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzny, com.google.android.gms.internal.measurement.zzlj):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0099 A[EDGE_INSN: B:56:0x0099->B:34:0x0099 BREAK  A[LOOP:1: B:18:0x0053->B:61:0x0053], SYNTHETIC] */
    @Override // com.google.android.gms.internal.measurement.zzob
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r10, byte[] r11, int r12, int r13, com.google.android.gms.internal.measurement.zzkh r14) throws java.io.IOException {
        /*
            r9 = this;
            r0 = r10
            com.google.android.gms.internal.measurement.zzlw r0 = (com.google.android.gms.internal.measurement.zzlw) r0
            com.google.android.gms.internal.measurement.zzoz r1 = r0.zzb
            com.google.android.gms.internal.measurement.zzoz r2 = com.google.android.gms.internal.measurement.zzoz.zzc()
            if (r1 != r2) goto L11
            com.google.android.gms.internal.measurement.zzoz r1 = com.google.android.gms.internal.measurement.zzoz.f()
            r0.zzb = r1
        L11:
            com.google.android.gms.internal.measurement.zzlw$zzb r10 = (com.google.android.gms.internal.measurement.zzlw.zzb) r10
            r10.u()
            r10 = 0
            r0 = r10
        L18:
            if (r12 >= r13) goto La4
            int r4 = com.google.android.gms.internal.measurement.zzki.p(r11, r12, r14)
            int r2 = r14.zza
            r12 = 11
            r3 = 2
            if (r2 == r12) goto L51
            r12 = r2 & 7
            if (r12 != r3) goto L4c
            com.google.android.gms.internal.measurement.zzll<?> r12 = r9.zzd
            com.google.android.gms.internal.measurement.zzlj r0 = r14.zzd
            com.google.android.gms.internal.measurement.zznj r3 = r9.zza
            int r5 = r2 >>> 3
            java.lang.Object r12 = r12.c(r0, r3, r5)
            r0 = r12
            com.google.android.gms.internal.measurement.zzlw$zzd r0 = (com.google.android.gms.internal.measurement.zzlw.zzd) r0
            if (r0 != 0) goto L43
            r3 = r11
            r5 = r13
            r6 = r1
            r7 = r14
            int r12 = com.google.android.gms.internal.measurement.zzki.d(r2, r3, r4, r5, r6, r7)
            goto L18
        L43:
            com.google.android.gms.internal.measurement.zznx.zza()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L4c:
            int r12 = com.google.android.gms.internal.measurement.zzki.b(r2, r11, r4, r13, r14)
            goto L18
        L51:
            r12 = 0
            r2 = r10
        L53:
            if (r4 >= r13) goto L99
            int r4 = com.google.android.gms.internal.measurement.zzki.p(r11, r4, r14)
            int r5 = r14.zza
            int r6 = r5 >>> 3
            r7 = r5 & 7
            if (r6 == r3) goto L7b
            r8 = 3
            if (r6 == r8) goto L65
            goto L90
        L65:
            if (r0 != 0) goto L72
            if (r7 != r3) goto L90
            int r4 = com.google.android.gms.internal.measurement.zzki.k(r11, r4, r14)
            java.lang.Object r2 = r14.zzc
            com.google.android.gms.internal.measurement.zzkm r2 = (com.google.android.gms.internal.measurement.zzkm) r2
            goto L53
        L72:
            com.google.android.gms.internal.measurement.zznx.zza()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L7b:
            if (r7 != 0) goto L90
            int r4 = com.google.android.gms.internal.measurement.zzki.p(r11, r4, r14)
            int r12 = r14.zza
            com.google.android.gms.internal.measurement.zzll<?> r0 = r9.zzd
            com.google.android.gms.internal.measurement.zzlj r5 = r14.zzd
            com.google.android.gms.internal.measurement.zznj r6 = r9.zza
            java.lang.Object r0 = r0.c(r5, r6, r12)
            com.google.android.gms.internal.measurement.zzlw$zzd r0 = (com.google.android.gms.internal.measurement.zzlw.zzd) r0
            goto L53
        L90:
            r6 = 12
            if (r5 == r6) goto L99
            int r4 = com.google.android.gms.internal.measurement.zzki.b(r5, r11, r4, r13, r14)
            goto L53
        L99:
            if (r2 == 0) goto La1
            int r12 = r12 << 3
            r12 = r12 | r3
            r1.c(r12, r2)
        La1:
            r12 = r4
            goto L18
        La4:
            if (r12 != r13) goto La7
            return
        La7:
            com.google.android.gms.internal.measurement.zzme r10 = com.google.android.gms.internal.measurement.zzme.zzg()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznp.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.measurement.zzkh):void");
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final void zza(T t2, zzpw zzpwVar) throws IOException {
        Iterator itZzd = this.zzd.b(t2).zzd();
        while (itZzd.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzd.next();
            zzlo zzloVar = (zzlo) entry.getKey();
            if (zzloVar.zzc() == zzpt.MESSAGE && !zzloVar.zze() && !zzloVar.zzd()) {
                if (entry instanceof zzmi) {
                    zzpwVar.zza(zzloVar.zza(), (Object) ((zzmi) entry).zza().zzc());
                } else {
                    zzpwVar.zza(zzloVar.zza(), entry.getValue());
                }
            } else {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
        }
        zzow<?, ?> zzowVar = this.zzb;
        zzowVar.h(zzowVar.q(t2), zzpwVar);
    }
}
