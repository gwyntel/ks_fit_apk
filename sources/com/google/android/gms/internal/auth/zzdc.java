package com.google.android.gms.internal.auth;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
public abstract class zzdc {
    public static final /* synthetic */ int zzd = 0;

    @Nullable
    private static volatile zzda zze = null;
    private static volatile boolean zzf = false;

    /* renamed from: a, reason: collision with root package name */
    final zzcz f13010a;

    /* renamed from: b, reason: collision with root package name */
    final String f13011b;
    private final Object zzj;
    private volatile int zzk = -1;
    private volatile Object zzl;
    private static final Object zza = new Object();
    private static final AtomicReference zzg = new AtomicReference();
    private static final zzde zzh = new zzde(zzcu.zza);
    private static final AtomicInteger zzi = new AtomicInteger();

    /* synthetic */ zzdc(zzcz zzczVar, String str, Object obj, boolean z2, zzdb zzdbVar) {
        if (zzczVar.f13005a == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.f13010a = zzczVar;
        this.f13011b = str;
        this.zzj = obj;
    }

    public static void zzc() {
        zzi.incrementAndGet();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0045, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x004a, code lost:
    
        throw r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void zzd(final android.content.Context r3) {
        /*
            com.google.android.gms.internal.auth.zzda r0 = com.google.android.gms.internal.auth.zzdc.zze
            if (r0 != 0) goto L4b
            if (r3 != 0) goto L7
            goto L4b
        L7:
            java.lang.Object r0 = com.google.android.gms.internal.auth.zzdc.zza
            monitor-enter(r0)
            com.google.android.gms.internal.auth.zzda r1 = com.google.android.gms.internal.auth.zzdc.zze     // Catch: java.lang.Throwable -> L45
            if (r1 != 0) goto L47
            monitor-enter(r0)     // Catch: java.lang.Throwable -> L45
            com.google.android.gms.internal.auth.zzda r1 = com.google.android.gms.internal.auth.zzdc.zze     // Catch: java.lang.Throwable -> L21
            android.content.Context r2 = r3.getApplicationContext()     // Catch: java.lang.Throwable -> L21
            if (r2 == 0) goto L18
            r3 = r2
        L18:
            if (r1 == 0) goto L23
            android.content.Context r1 = r1.a()     // Catch: java.lang.Throwable -> L21
            if (r1 == r3) goto L41
            goto L23
        L21:
            r3 = move-exception
            goto L43
        L23:
            com.google.android.gms.internal.auth.zzcg.b()     // Catch: java.lang.Throwable -> L21
            com.google.android.gms.internal.auth.zzdd.b()     // Catch: java.lang.Throwable -> L21
            com.google.android.gms.internal.auth.zzco.c()     // Catch: java.lang.Throwable -> L21
            com.google.android.gms.internal.auth.zzct r1 = new com.google.android.gms.internal.auth.zzct     // Catch: java.lang.Throwable -> L21
            r1.<init>()     // Catch: java.lang.Throwable -> L21
            com.google.android.gms.internal.auth.zzdj r1 = com.google.android.gms.internal.auth.zzdo.zza(r1)     // Catch: java.lang.Throwable -> L21
            com.google.android.gms.internal.auth.zzcd r2 = new com.google.android.gms.internal.auth.zzcd     // Catch: java.lang.Throwable -> L21
            r2.<init>(r3, r1)     // Catch: java.lang.Throwable -> L21
            com.google.android.gms.internal.auth.zzdc.zze = r2     // Catch: java.lang.Throwable -> L21
            java.util.concurrent.atomic.AtomicInteger r3 = com.google.android.gms.internal.auth.zzdc.zzi     // Catch: java.lang.Throwable -> L21
            r3.incrementAndGet()     // Catch: java.lang.Throwable -> L21
        L41:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            goto L47
        L43:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            throw r3     // Catch: java.lang.Throwable -> L45
        L45:
            r3 = move-exception
            goto L49
        L47:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L45
            return
        L49:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L45
            throw r3
        L4b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzdc.zzd(android.content.Context):void");
    }

    abstract Object a(Object obj);

    /* JADX WARN: Removed duplicated region for block: B:14:0x003e A[PHI: r2
      0x003e: PHI (r2v1 com.google.android.gms.internal.auth.zzdh) = (r2v0 com.google.android.gms.internal.auth.zzdh), (r2v5 com.google.android.gms.internal.auth.zzdh) binds: [B:8:0x0016, B:10:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object zzb() {
        /*
            r8 = this;
            java.util.concurrent.atomic.AtomicInteger r0 = com.google.android.gms.internal.auth.zzdc.zzi
            int r0 = r0.get()
            int r1 = r8.zzk
            if (r1 >= r0) goto Lca
            monitor-enter(r8)
            int r1 = r8.zzk     // Catch: java.lang.Throwable -> L3b
            if (r1 >= r0) goto Lc6
            com.google.android.gms.internal.auth.zzda r1 = com.google.android.gms.internal.auth.zzdc.zze     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzdh r2 = com.google.android.gms.internal.auth.zzdh.zzc()     // Catch: java.lang.Throwable -> L3b
            r3 = 0
            if (r1 == 0) goto L3e
            com.google.android.gms.internal.auth.zzdj r2 = r1.b()     // Catch: java.lang.Throwable -> L3b
            java.lang.Object r2 = r2.zza()     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzdh r2 = (com.google.android.gms.internal.auth.zzdh) r2     // Catch: java.lang.Throwable -> L3b
            boolean r4 = r2.zzb()     // Catch: java.lang.Throwable -> L3b
            if (r4 == 0) goto L3e
            java.lang.Object r4 = r2.zza()     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzci r4 = (com.google.android.gms.internal.auth.zzci) r4     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzcz r5 = r8.f13010a     // Catch: java.lang.Throwable -> L3b
            android.net.Uri r6 = r5.f13005a     // Catch: java.lang.Throwable -> L3b
            java.lang.String r5 = r5.f13007c     // Catch: java.lang.Throwable -> L3b
            java.lang.String r7 = r8.f13011b     // Catch: java.lang.Throwable -> L3b
            java.lang.String r4 = r4.zza(r6, r3, r5, r7)     // Catch: java.lang.Throwable -> L3b
            goto L3f
        L3b:
            r0 = move-exception
            goto Lc8
        L3e:
            r4 = r3
        L3f:
            java.lang.String r5 = "Must call PhenotypeFlag.init() first"
            if (r1 == 0) goto Lc0
            com.google.android.gms.internal.auth.zzcz r5 = r8.f13010a     // Catch: java.lang.Throwable -> L3b
            android.net.Uri r5 = r5.f13005a     // Catch: java.lang.Throwable -> L3b
            if (r5 == 0) goto L68
            android.content.Context r6 = r1.a()     // Catch: java.lang.Throwable -> L3b
            boolean r5 = com.google.android.gms.internal.auth.zzcq.zza(r6, r5)     // Catch: java.lang.Throwable -> L3b
            if (r5 == 0) goto L66
            android.content.Context r5 = r1.a()     // Catch: java.lang.Throwable -> L3b
            android.content.ContentResolver r5 = r5.getContentResolver()     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzcz r6 = r8.f13010a     // Catch: java.lang.Throwable -> L3b
            android.net.Uri r6 = r6.f13005a     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzcs r7 = new java.lang.Runnable() { // from class: com.google.android.gms.internal.auth.zzcs
                static {
                    /*
                        com.google.android.gms.internal.auth.zzcs r0 = new com.google.android.gms.internal.auth.zzcs
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.google.android.gms.internal.auth.zzcs) com.google.android.gms.internal.auth.zzcs.zza com.google.android.gms.internal.auth.zzcs
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzcs.<clinit>():void");
                }

                {
                    /*
                        r0 = this;
                        r0.<init>()
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzcs.<init>():void");
                }

                @Override // java.lang.Runnable
                public final void run() {
                    /*
                        r0 = this;
                        com.google.android.gms.internal.auth.zzdc.zzc()
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzcs.run():void");
                }
            }     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzcg r5 = com.google.android.gms.internal.auth.zzcg.zza(r5, r6, r7)     // Catch: java.lang.Throwable -> L3b
            goto L72
        L66:
            r5 = r3
            goto L72
        L68:
            android.content.Context r5 = r1.a()     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzcs r6 = com.google.android.gms.internal.auth.zzcs.zza     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzdd r5 = com.google.android.gms.internal.auth.zzdd.a(r5, r3, r6)     // Catch: java.lang.Throwable -> L3b
        L72:
            if (r5 == 0) goto L81
            java.lang.String r6 = r8.f13011b     // Catch: java.lang.Throwable -> L3b
            java.lang.Object r5 = r5.zzb(r6)     // Catch: java.lang.Throwable -> L3b
            if (r5 == 0) goto L81
            java.lang.Object r5 = r8.a(r5)     // Catch: java.lang.Throwable -> L3b
            goto L82
        L81:
            r5 = r3
        L82:
            if (r5 != 0) goto Lac
            com.google.android.gms.internal.auth.zzcz r5 = r8.f13010a     // Catch: java.lang.Throwable -> L3b
            boolean r5 = r5.f13008d     // Catch: java.lang.Throwable -> L3b
            if (r5 != 0) goto La6
            android.content.Context r1 = r1.a()     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzco r1 = com.google.android.gms.internal.auth.zzco.a(r1)     // Catch: java.lang.Throwable -> L3b
            com.google.android.gms.internal.auth.zzcz r5 = r8.f13010a     // Catch: java.lang.Throwable -> L3b
            boolean r5 = r5.f13008d     // Catch: java.lang.Throwable -> L3b
            if (r5 == 0) goto L9a
            r5 = r3
            goto L9c
        L9a:
            java.lang.String r5 = r8.f13011b     // Catch: java.lang.Throwable -> L3b
        L9c:
            java.lang.String r1 = r1.zzb(r5)     // Catch: java.lang.Throwable -> L3b
            if (r1 == 0) goto La6
            java.lang.Object r3 = r8.a(r1)     // Catch: java.lang.Throwable -> L3b
        La6:
            if (r3 != 0) goto Lab
            java.lang.Object r5 = r8.zzj     // Catch: java.lang.Throwable -> L3b
            goto Lac
        Lab:
            r5 = r3
        Lac:
            boolean r1 = r2.zzb()     // Catch: java.lang.Throwable -> L3b
            if (r1 == 0) goto Lbb
            if (r4 != 0) goto Lb7
            java.lang.Object r5 = r8.zzj     // Catch: java.lang.Throwable -> L3b
            goto Lbb
        Lb7:
            java.lang.Object r5 = r8.a(r4)     // Catch: java.lang.Throwable -> L3b
        Lbb:
            r8.zzl = r5     // Catch: java.lang.Throwable -> L3b
            r8.zzk = r0     // Catch: java.lang.Throwable -> L3b
            goto Lc6
        Lc0:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L3b
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L3b
            throw r0     // Catch: java.lang.Throwable -> L3b
        Lc6:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L3b
            goto Lca
        Lc8:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L3b
            throw r0
        Lca:
            java.lang.Object r0 = r8.zzl
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzdc.zzb():java.lang.Object");
    }
}
