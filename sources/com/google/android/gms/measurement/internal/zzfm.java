package com.google.android.gms.measurement.internal;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzql;
import com.google.android.gms.internal.measurement.zzsg;
import com.google.android.gms.internal.measurement.zzss;
import com.google.android.gms.internal.measurement.zzte;
import com.google.android.gms.measurement.internal.zzie;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzfm extends zze {
    private String zza;
    private String zzb;
    private int zzc;
    private String zzd;
    private String zze;
    private long zzf;
    private long zzg;
    private List<String> zzh;
    private String zzi;
    private int zzj;
    private String zzk;
    private String zzl;
    private String zzm;
    private long zzn;
    private String zzo;

    zzfm(zzhc zzhcVar, long j2) {
        super(zzhcVar);
        this.zzn = 0L;
        this.zzo = null;
        this.zzg = j2;
    }

    @VisibleForTesting
    @WorkerThread
    private final String zzah() throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (zzte.zzb() && zze().zza(zzbi.zzbk)) {
            zzj().zzp().zza("Disabled IID for tests.");
            return null;
        }
        try {
            Class<?> clsLoadClass = zza().getClassLoader().loadClass("com.google.firebase.analytics.FirebaseAnalytics");
            if (clsLoadClass == null) {
                return null;
            }
            try {
                Object objInvoke = clsLoadClass.getDeclaredMethod("getInstance", Context.class).invoke(null, zza());
                if (objInvoke == null) {
                    return null;
                }
                try {
                    return (String) clsLoadClass.getDeclaredMethod("getFirebaseInstanceId", null).invoke(objInvoke, null);
                } catch (Exception unused) {
                    zzj().zzv().zza("Failed to retrieve Firebase Instance Id");
                    return null;
                }
            } catch (Exception unused2) {
                zzj().zzw().zza("Failed to obtain Firebase Analytics instance");
                return null;
            }
        } catch (ClassNotFoundException unused3) {
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(20:0|2|(1:4)(22:86|6|(1:10)(2:11|(1:13))|84|14|(4:16|(1:18)(1:20)|88|21)|26|(1:31)(1:30)|32|33|(1:46)|47|(1:49)|90|50|(1:52)(1:53)|54|(1:56)|(3:60|(1:62)(1:63)|64)|67|(1:78)(2:69|(1:71)(4:72|(3:75|(1:93)(1:94)|73)|92|78))|(2:80|81)(2:82|83))|5|26|(2:28|31)(0)|32|33|(0)|47|(0)|90|50|(0)(0)|54|(0)|(0)|67|(0)(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01ac, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01cd, code lost:
    
        zzj().zzg().zza("Fetching Google App Id failed with exception. appId", com.google.android.gms.measurement.internal.zzfs.d(r0), r3);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0194 A[Catch: IllegalStateException -> 0x01ac, TryCatch #3 {IllegalStateException -> 0x01ac, blocks: (B:50:0x016f, B:54:0x018c, B:56:0x0194, B:60:0x01b0, B:62:0x01c4, B:64:0x01c9, B:63:0x01c7), top: B:90:0x016f }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01b0 A[Catch: IllegalStateException -> 0x01ac, TryCatch #3 {IllegalStateException -> 0x01ac, blocks: (B:50:0x016f, B:54:0x018c, B:56:0x0194, B:60:0x01b0, B:62:0x01c4, B:64:0x01c9, B:63:0x01c7), top: B:90:0x016f }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x022d  */
    @Override // com.google.android.gms.measurement.internal.zze
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected final void a() throws android.content.res.Resources.NotFoundException, android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 582
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfm.a():void");
    }

    final zzo b(String str) {
        String strZzf;
        int iZza;
        long j2;
        long j3;
        String str2;
        int iG;
        zzt();
        zzie zzieVarP = zzk().p();
        if (zzql.zzb() && zze().zza(zzbi.zzcl)) {
            strZzf = zzk().o().zzf();
            iZza = zzieVarP.zza();
        } else {
            strZzf = "";
            iZza = 100;
        }
        String str3 = strZzf;
        int i2 = iZza;
        String strF = f();
        String strZzae = zzae();
        zzu();
        String str4 = this.zzb;
        long jD = d();
        zzu();
        Preconditions.checkNotNull(this.zzd);
        String str5 = this.zzd;
        zzu();
        zzt();
        if (this.zzf == 0) {
            this.zzf = this.f13286a.zzt().e(zza(), zza().getPackageName());
        }
        long j4 = this.zzf;
        boolean zZzac = this.f13286a.zzac();
        boolean z2 = !zzk().zzm;
        zzt();
        String strZzah = !this.f13286a.zzac() ? null : zzah();
        zzhc zzhcVar = this.f13286a;
        long jZza = zzhcVar.zzn().zzc.zza();
        long jMin = jZza == 0 ? zzhcVar.f13285a : Math.min(zzhcVar.f13285a, jZza);
        int iC = c();
        boolean zZzp = zze().zzp();
        zzge zzgeVarZzk = zzk();
        zzgeVarZzk.zzt();
        boolean z3 = zzgeVarZzk.l().getBoolean("deferred_analytics_collection", false);
        String strE = e();
        Boolean boolValueOf = zze().g("google_analytics_default_allow_ad_personalization_signals") == null ? null : Boolean.valueOf(!r4.booleanValue());
        long j5 = this.zzg;
        List<String> list = this.zzh;
        String strZze = zzieVarP.zze();
        if (this.zzi == null) {
            this.zzi = zzq().Q();
        }
        String str6 = this.zzi;
        if (zzss.zzb() && zze().zza(zzbi.zzbs)) {
            zzt();
            j3 = 0;
            if (this.zzn != 0) {
                j2 = j5;
                long jCurrentTimeMillis = zzb().currentTimeMillis() - this.zzn;
                if (this.zzm != null && jCurrentTimeMillis > 86400000 && this.zzo == null) {
                    h();
                }
            } else {
                j2 = j5;
            }
            if (this.zzm == null) {
                h();
            }
            str2 = this.zzm;
        } else {
            j2 = j5;
            j3 = 0;
            str2 = null;
        }
        Boolean boolG = zze().g("google_analytics_sgtm_upload_enabled");
        boolean zBooleanValue = boolG == null ? false : boolG.booleanValue();
        long jH = zzq().H(f());
        if (zzsg.zzb() && zze().zza(zzbi.zzcg)) {
            zzq();
            iG = zzne.G();
        } else {
            iG = 0;
        }
        return new zzo(strF, strZzae, str4, jD, str5, 81010L, j4, str, zZzac, z2, strZzah, 0L, jMin, iC, zZzp, z3, strE, boolValueOf, j2, list, (String) null, strZze, str6, str2, zBooleanValue, jH, i2, str3, iG, (zzsg.zzb() && zze().zza(zzbi.zzcg)) ? zzq().zzh() : j3);
    }

    final int c() {
        zzu();
        return this.zzj;
    }

    final int d() {
        zzu();
        return this.zzc;
    }

    final String e() {
        zzu();
        return this.zzl;
    }

    final String f() {
        zzu();
        Preconditions.checkNotNull(this.zza);
        return this.zza;
    }

    final List g() {
        return this.zzh;
    }

    final void h() {
        String str;
        zzt();
        if (zzk().p().zza(zzie.zza.ANALYTICS_STORAGE)) {
            byte[] bArr = new byte[16];
            zzq().S().nextBytes(bArr);
            str = String.format(Locale.US, "%032x", new BigInteger(1, bArr));
        } else {
            zzj().zzc().zza("Analytics Storage consent is not granted");
            str = null;
        }
        zzj().zzc().zza(String.format("Resetting session stitching token to %s", str == null ? TmpConstant.GROUP_ROLE_UNKNOWN : "not null"));
        this.zzm = str;
        this.zzn = zzb().currentTimeMillis();
    }

    final boolean i(String str) {
        String str2 = this.zzo;
        boolean z2 = (str2 == null || str2.equals(str)) ? false : true;
        this.zzo = str;
        return z2;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    final String zzae() {
        zzt();
        zzu();
        Preconditions.checkNotNull(this.zzk);
        return this.zzk;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzb zzc() {
        return super.zzc();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzae zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzaf zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfm zzg() {
        return super.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfl zzh() {
        return super.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzfn zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzfs zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzge zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzgz zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzin zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzki zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzkq zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzly zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    @Override // com.google.android.gms.measurement.internal.zze
    protected final boolean zzz() {
        return true;
    }
}
