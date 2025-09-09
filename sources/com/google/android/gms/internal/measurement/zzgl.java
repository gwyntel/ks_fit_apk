package com.google.android.gms.internal.measurement;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
public abstract class zzgl<T> {

    @Nullable
    private static volatile zzgw zzb = null;
    private static volatile boolean zzc = false;
    private final zzgt zzg;
    private final String zzh;
    private final T zzi;
    private volatile int zzj;
    private volatile T zzk;
    private final boolean zzl;
    private static final Object zza = new Object();
    private static final AtomicReference<Collection<zzgl<?>>> zzd = new AtomicReference<>();
    private static zzha zze = new zzha(new zzgz() { // from class: com.google.android.gms.internal.measurement.zzgq
        @Override // com.google.android.gms.internal.measurement.zzgz
        public final boolean zza() {
            return zzgl.g();
        }
    });
    private static final AtomicInteger zzf = new AtomicInteger();

    static /* synthetic */ zzgl a(zzgt zzgtVar, String str, Boolean bool, boolean z2) {
        return new zzgs(zzgtVar, str, bool, true);
    }

    static /* synthetic */ zzgl b(zzgt zzgtVar, String str, Double d2, boolean z2) {
        return new zzgr(zzgtVar, str, d2, true);
    }

    static /* synthetic */ zzgl c(zzgt zzgtVar, String str, Long l2, boolean z2) {
        return new zzgp(zzgtVar, str, l2, true);
    }

    static /* synthetic */ zzgl d(zzgt zzgtVar, String str, String str2, boolean z2) {
        return new zzgu(zzgtVar, str, str2, true);
    }

    static /* synthetic */ boolean g() {
        return true;
    }

    @Nullable
    private final T zzb(zzgw zzgwVar) {
        Object objZza;
        zzgd zzgdVarZza = this.zzg.f13205b != null ? zzgk.zza(zzgwVar.a(), this.zzg.f13205b) ? this.zzg.f13210g ? zzfw.zza(zzgwVar.a().getContentResolver(), zzgm.zza(zzgm.zza(zzgwVar.a(), this.zzg.f13205b.getLastPathSegment())), new Runnable() { // from class: com.google.android.gms.internal.measurement.zzgo
            @Override // java.lang.Runnable
            public final void run() {
                zzgl.zzc();
            }
        }) : zzfw.zza(zzgwVar.a().getContentResolver(), this.zzg.f13205b, new Runnable() { // from class: com.google.android.gms.internal.measurement.zzgo
            @Override // java.lang.Runnable
            public final void run() {
                zzgl.zzc();
            }
        }) : null : zzgy.a(zzgwVar.a(), this.zzg.f13204a, new Runnable() { // from class: com.google.android.gms.internal.measurement.zzgo
            @Override // java.lang.Runnable
            public final void run() {
                zzgl.zzc();
            }
        });
        if (zzgdVarZza == null || (objZza = zzgdVarZza.zza(zzb())) == null) {
            return null;
        }
        return (T) f(objZza);
    }

    public static void zzc() {
        zzf.incrementAndGet();
    }

    abstract Object f(Object obj);

    /* JADX WARN: Removed duplicated region for block: B:34:0x007c A[Catch: all -> 0x004e, TryCatch #0 {all -> 0x004e, blocks: (B:8:0x001c, B:10:0x0020, B:12:0x0029, B:14:0x0039, B:20:0x0055, B:22:0x0060, B:35:0x007e, B:38:0x0086, B:39:0x0089, B:40:0x008d, B:25:0x0067, B:34:0x007c, B:28:0x006e, B:31:0x0075, B:41:0x0091), top: B:47:0x001c }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final T zza() {
        /*
            r8 = this;
            boolean r0 = r8.zzl
            if (r0 != 0) goto L11
            com.google.android.gms.internal.measurement.zzha r0 = com.google.android.gms.internal.measurement.zzgl.zze
            java.lang.String r1 = r8.zzh
            boolean r0 = r0.zza(r1)
            java.lang.String r1 = "Attempt to access PhenotypeFlag not via codegen. All new PhenotypeFlags must be accessed through codegen APIs. If you believe you are seeing this error by mistake, you can add your flag to the exemption list located at //java/com/google/android/libraries/phenotype/client/lockdown/flags.textproto. Send the addition CL to ph-reviews@. See go/phenotype-android-codegen for information about generated code. See go/ph-lockdown for more information about this error."
            com.google.android.gms.internal.measurement.zzhn.zzb(r0, r1)
        L11:
            java.util.concurrent.atomic.AtomicInteger r0 = com.google.android.gms.internal.measurement.zzgl.zzf
            int r0 = r0.get()
            int r1 = r8.zzj
            if (r1 >= r0) goto L95
            monitor-enter(r8)
            int r1 = r8.zzj     // Catch: java.lang.Throwable -> L4e
            if (r1 >= r0) goto L91
            com.google.android.gms.internal.measurement.zzgw r1 = com.google.android.gms.internal.measurement.zzgl.zzb     // Catch: java.lang.Throwable -> L4e
            com.google.android.gms.internal.measurement.zzho r2 = com.google.android.gms.internal.measurement.zzho.zzc()     // Catch: java.lang.Throwable -> L4e
            r3 = 0
            if (r1 == 0) goto L50
            com.google.android.gms.internal.measurement.zzhs r2 = r1.b()     // Catch: java.lang.Throwable -> L4e
            java.lang.Object r2 = r2.zza()     // Catch: java.lang.Throwable -> L4e
            com.google.android.gms.internal.measurement.zzho r2 = (com.google.android.gms.internal.measurement.zzho) r2     // Catch: java.lang.Throwable -> L4e
            boolean r4 = r2.zzb()     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L50
            java.lang.Object r3 = r2.zza()     // Catch: java.lang.Throwable -> L4e
            com.google.android.gms.internal.measurement.zzgj r3 = (com.google.android.gms.internal.measurement.zzgj) r3     // Catch: java.lang.Throwable -> L4e
            com.google.android.gms.internal.measurement.zzgt r4 = r8.zzg     // Catch: java.lang.Throwable -> L4e
            android.net.Uri r5 = r4.f13205b     // Catch: java.lang.Throwable -> L4e
            java.lang.String r6 = r4.f13204a     // Catch: java.lang.Throwable -> L4e
            java.lang.String r4 = r4.f13207d     // Catch: java.lang.Throwable -> L4e
            java.lang.String r7 = r8.zzh     // Catch: java.lang.Throwable -> L4e
            java.lang.String r3 = r3.zza(r5, r6, r4, r7)     // Catch: java.lang.Throwable -> L4e
            goto L50
        L4e:
            r0 = move-exception
            goto L93
        L50:
            if (r1 == 0) goto L54
            r4 = 1
            goto L55
        L54:
            r4 = 0
        L55:
            java.lang.String r5 = "Must call PhenotypeFlagInitializer.maybeInit() first"
            com.google.android.gms.internal.measurement.zzhn.zzb(r4, r5)     // Catch: java.lang.Throwable -> L4e
            com.google.android.gms.internal.measurement.zzgt r4 = r8.zzg     // Catch: java.lang.Throwable -> L4e
            boolean r4 = r4.f13209f     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L6e
            java.lang.Object r4 = r8.zza(r1)     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L67
            goto L7e
        L67:
            java.lang.Object r4 = r8.zzb(r1)     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L7c
            goto L7e
        L6e:
            java.lang.Object r4 = r8.zzb(r1)     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L75
            goto L7e
        L75:
            java.lang.Object r4 = r8.zza(r1)     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L7c
            goto L7e
        L7c:
            T r4 = r8.zzi     // Catch: java.lang.Throwable -> L4e
        L7e:
            boolean r1 = r2.zzb()     // Catch: java.lang.Throwable -> L4e
            if (r1 == 0) goto L8d
            if (r3 != 0) goto L89
            T r4 = r8.zzi     // Catch: java.lang.Throwable -> L4e
            goto L8d
        L89:
            java.lang.Object r4 = r8.f(r3)     // Catch: java.lang.Throwable -> L4e
        L8d:
            r8.zzk = r4     // Catch: java.lang.Throwable -> L4e
            r8.zzj = r0     // Catch: java.lang.Throwable -> L4e
        L91:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L4e
            goto L95
        L93:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L4e
            throw r0
        L95:
            T r0 = r8.zzk
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzgl.zza():java.lang.Object");
    }

    private zzgl(zzgt zzgtVar, String str, T t2, boolean z2) {
        this.zzj = -1;
        String str2 = zzgtVar.f13204a;
        if (str2 == null && zzgtVar.f13205b == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        if (str2 != null && zzgtVar.f13205b != null) {
            throw new IllegalArgumentException("Must pass one of SharedPreferences file name or ContentProvider URI");
        }
        this.zzg = zzgtVar;
        this.zzh = str;
        this.zzi = t2;
        this.zzl = z2;
    }

    public final String zzb() {
        return zza(this.zzg.f13207d);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0046, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x004b, code lost:
    
        throw r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void zzb(final android.content.Context r3) {
        /*
            com.google.android.gms.internal.measurement.zzgw r0 = com.google.android.gms.internal.measurement.zzgl.zzb
            if (r0 != 0) goto L4c
            if (r3 != 0) goto L7
            goto L4c
        L7:
            java.lang.Object r0 = com.google.android.gms.internal.measurement.zzgl.zza
            monitor-enter(r0)
            com.google.android.gms.internal.measurement.zzgw r1 = com.google.android.gms.internal.measurement.zzgl.zzb     // Catch: java.lang.Throwable -> L46
            if (r1 != 0) goto L48
            monitor-enter(r0)     // Catch: java.lang.Throwable -> L46
            com.google.android.gms.internal.measurement.zzgw r1 = com.google.android.gms.internal.measurement.zzgl.zzb     // Catch: java.lang.Throwable -> L22
            android.content.Context r2 = r3.getApplicationContext()     // Catch: java.lang.Throwable -> L22
            if (r2 != 0) goto L18
            goto L19
        L18:
            r3 = r2
        L19:
            if (r1 == 0) goto L24
            android.content.Context r1 = r1.a()     // Catch: java.lang.Throwable -> L22
            if (r1 == r3) goto L42
            goto L24
        L22:
            r3 = move-exception
            goto L44
        L24:
            com.google.android.gms.internal.measurement.zzfw.b()     // Catch: java.lang.Throwable -> L22
            com.google.android.gms.internal.measurement.zzgy.b()     // Catch: java.lang.Throwable -> L22
            com.google.android.gms.internal.measurement.zzge.b()     // Catch: java.lang.Throwable -> L22
            com.google.android.gms.internal.measurement.zzgn r1 = new com.google.android.gms.internal.measurement.zzgn     // Catch: java.lang.Throwable -> L22
            r1.<init>()     // Catch: java.lang.Throwable -> L22
            com.google.android.gms.internal.measurement.zzhs r1 = com.google.android.gms.internal.measurement.zzhr.zza(r1)     // Catch: java.lang.Throwable -> L22
            com.google.android.gms.internal.measurement.zzfx r2 = new com.google.android.gms.internal.measurement.zzfx     // Catch: java.lang.Throwable -> L22
            r2.<init>(r3, r1)     // Catch: java.lang.Throwable -> L22
            com.google.android.gms.internal.measurement.zzgl.zzb = r2     // Catch: java.lang.Throwable -> L22
            java.util.concurrent.atomic.AtomicInteger r3 = com.google.android.gms.internal.measurement.zzgl.zzf     // Catch: java.lang.Throwable -> L22
            r3.incrementAndGet()     // Catch: java.lang.Throwable -> L22
        L42:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L22
            goto L48
        L44:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L22
            throw r3     // Catch: java.lang.Throwable -> L46
        L46:
            r3 = move-exception
            goto L4a
        L48:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L46
            return
        L4a:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L46
            throw r3
        L4c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzgl.zzb(android.content.Context):void");
    }

    @Nullable
    private final T zza(zzgw zzgwVar) {
        zzhg zzhgVar;
        zzgt zzgtVar = this.zzg;
        if (!zzgtVar.f13208e && ((zzhgVar = zzgtVar.f13211h) == null || ((Boolean) zzhgVar.zza(zzgwVar.a())).booleanValue())) {
            zzge zzgeVarA = zzge.a(zzgwVar.a());
            zzgt zzgtVar2 = this.zzg;
            Object objZza = zzgeVarA.zza(zzgtVar2.f13208e ? null : zza(zzgtVar2.f13206c));
            if (objZza != null) {
                return (T) f(objZza);
            }
        }
        return null;
    }

    private final String zza(String str) {
        if (str != null && str.isEmpty()) {
            return this.zzh;
        }
        return str + this.zzh;
    }
}
