package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Size;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzro;
import com.google.android.gms.internal.measurement.zzrp;
import java.lang.reflect.InvocationTargetException;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzaf extends zzia {
    private Boolean zza;
    private zzah zzb;
    private Boolean zzc;

    zzaf(zzhc zzhcVar) {
        super(zzhcVar);
        this.zzb = new zzah() { // from class: com.google.android.gms.measurement.internal.zzai
            @Override // com.google.android.gms.measurement.internal.zzah
            public final String zza(String str, String str2) {
                return null;
            }
        };
    }

    public static long zzh() {
        return zzbi.zzd.zza(null).longValue();
    }

    public static long zzm() {
        return zzbi.zzad.zza(null).longValue();
    }

    @VisibleForTesting
    private final Bundle zzy() {
        try {
            if (zza().getPackageManager() == null) {
                zzj().zzg().zza("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = Wrappers.packageManager(zza()).getApplicationInfo(zza().getPackageName(), 128);
            if (applicationInfo != null) {
                return applicationInfo.metaData;
            }
            zzj().zzg().zza("Failed to load metadata: ApplicationInfo is null");
            return null;
        } catch (PackageManager.NameNotFoundException e2) {
            zzj().zzg().zza("Failed to load metadata: Package name not found", e2);
            return null;
        }
    }

    final int a(String str) {
        return zza(str, zzbi.zzah, 500, 2000);
    }

    final void b(zzah zzahVar) {
        this.zzb = zzahVar;
    }

    final int c(String str) {
        return (zzro.zzb() && zze().zzf(null, zzbi.zzcu)) ? 500 : 100;
    }

    final int d() {
        return (zzrp.zzb() && zze().zzf(null, zzbi.zzcc) && zzq().zza(231100000, true)) ? 35 : 0;
    }

    final int e(String str) {
        return Math.max(c(str), 256);
    }

    final long f(String str) {
        return zzc(str, zzbi.zza);
    }

    final Boolean g(String str) {
        Preconditions.checkNotEmpty(str);
        Bundle bundleZzy = zzy();
        if (bundleZzy == null) {
            zzj().zzg().zza("Failed to load metadata: Metadata bundle is null");
            return null;
        }
        if (bundleZzy.containsKey(str)) {
            return Boolean.valueOf(bundleZzy.getBoolean(str));
        }
        return null;
    }

    final String h(String str) {
        return zzd(str, zzbi.zzal);
    }

    final boolean i() {
        if (this.zza == null) {
            Boolean boolG = g("app_measurement_lite");
            this.zza = boolG;
            if (boolG == null) {
                this.zza = Boolean.FALSE;
            }
        }
        return this.zza.booleanValue() || !this.f13286a.zzag();
    }

    @WorkerThread
    public final double zza(String str, zzff<Double> zzffVar) {
        if (str == null) {
            return zzffVar.zza(null).doubleValue();
        }
        String strZza = this.zzb.zza(str, zzffVar.zza());
        if (TextUtils.isEmpty(strZza)) {
            return zzffVar.zza(null).doubleValue();
        }
        try {
            return zzffVar.zza(Double.valueOf(Double.parseDouble(strZza))).doubleValue();
        } catch (NumberFormatException unused) {
            return zzffVar.zza(null).doubleValue();
        }
    }

    @WorkerThread
    public final int zzb(String str, zzff<Integer> zzffVar) {
        if (str == null) {
            return zzffVar.zza(null).intValue();
        }
        String strZza = this.zzb.zza(str, zzffVar.zza());
        if (TextUtils.isEmpty(strZza)) {
            return zzffVar.zza(null).intValue();
        }
        try {
            return zzffVar.zza(Integer.valueOf(Integer.parseInt(strZza))).intValue();
        } catch (NumberFormatException unused) {
            return zzffVar.zza(null).intValue();
        }
    }

    @WorkerThread
    public final long zzc(String str, zzff<Long> zzffVar) {
        if (str == null) {
            return zzffVar.zza(null).longValue();
        }
        String strZza = this.zzb.zza(str, zzffVar.zza());
        if (TextUtils.isEmpty(strZza)) {
            return zzffVar.zza(null).longValue();
        }
        try {
            return zzffVar.zza(Long.valueOf(Long.parseLong(strZza))).longValue();
        } catch (NumberFormatException unused) {
            return zzffVar.zza(null).longValue();
        }
    }

    public final int zzd(@Size(min = 1) String str) {
        return zza(str, zzbi.zzai, 25, 100);
    }

    @WorkerThread
    public final int zze(@Size(min = 1) String str) {
        return zzb(str, zzbi.zzo);
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    public final int zzg() {
        return zzq().zza(201500000, true) ? 100 : 25;
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

    public final String zzn() {
        return zza("debug.firebase.analytics.app", "");
    }

    public final String zzo() {
        return zza("debug.deferred.deeplink", "");
    }

    public final boolean zzp() {
        Boolean boolG = g("google_analytics_adid_collection_enabled");
        return boolG == null || boolG.booleanValue();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    public final boolean zzu() {
        Boolean boolG = g("google_analytics_automatic_screen_reporting_enabled");
        return boolG == null || boolG.booleanValue();
    }

    public final boolean zzv() {
        Boolean boolG = g("firebase_analytics_collection_deactivated");
        return boolG != null && boolG.booleanValue();
    }

    @EnsuresNonNull({"this.isMainProcess"})
    public final boolean zzx() {
        if (this.zzc == null) {
            synchronized (this) {
                try {
                    if (this.zzc == null) {
                        ApplicationInfo applicationInfo = zza().getApplicationInfo();
                        String myProcessName = ProcessUtils.getMyProcessName();
                        if (applicationInfo != null) {
                            String str = applicationInfo.processName;
                            this.zzc = Boolean.valueOf(str != null && str.equals(myProcessName));
                        }
                        if (this.zzc == null) {
                            this.zzc = Boolean.TRUE;
                            zzj().zzg().zza("My process not in the list of running processes");
                        }
                    }
                } finally {
                }
            }
        }
        return this.zzc.booleanValue();
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

    @WorkerThread
    public final boolean zzf(String str, zzff<Boolean> zzffVar) {
        if (str == null) {
            return zzffVar.zza(null).booleanValue();
        }
        String strZza = this.zzb.zza(str, zzffVar.zza());
        return TextUtils.isEmpty(strZza) ? zzffVar.zza(null).booleanValue() : zzffVar.zza(Boolean.valueOf("1".equals(strZza))).booleanValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x002b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final java.util.List zzi(java.lang.String r4) throws android.content.res.Resources.NotFoundException {
        /*
            r3 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r4)
            android.os.Bundle r0 = r3.zzy()
            r1 = 0
            if (r0 != 0) goto L19
            com.google.android.gms.measurement.internal.zzfs r4 = r3.zzj()
            com.google.android.gms.measurement.internal.zzfu r4 = r4.zzg()
            java.lang.String r0 = "Failed to load metadata: Metadata bundle is null"
            r4.zza(r0)
        L17:
            r4 = r1
            goto L28
        L19:
            boolean r2 = r0.containsKey(r4)
            if (r2 != 0) goto L20
            goto L17
        L20:
            int r4 = r0.getInt(r4)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
        L28:
            if (r4 != 0) goto L2b
            return r1
        L2b:
            android.content.Context r0 = r3.zza()     // Catch: android.content.res.Resources.NotFoundException -> L43
            android.content.res.Resources r0 = r0.getResources()     // Catch: android.content.res.Resources.NotFoundException -> L43
            int r4 = r4.intValue()     // Catch: android.content.res.Resources.NotFoundException -> L43
            java.lang.String[] r4 = r0.getStringArray(r4)     // Catch: android.content.res.Resources.NotFoundException -> L43
            if (r4 != 0) goto L3e
            return r1
        L3e:
            java.util.List r4 = java.util.Arrays.asList(r4)     // Catch: android.content.res.Resources.NotFoundException -> L43
            return r4
        L43:
            r4 = move-exception
            com.google.android.gms.measurement.internal.zzfs r0 = r3.zzj()
            com.google.android.gms.measurement.internal.zzfu r0 = r0.zzg()
            java.lang.String r2 = "Failed to load string array from metadata: resource not found"
            r0.zza(r2, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzaf.zzi(java.lang.String):java.util.List");
    }

    final boolean zzj(String str) {
        return zzf(str, zzbi.zzak);
    }

    public final boolean zzk(String str) {
        return "1".equals(this.zzb.zza(str, "gaia_collection_enabled"));
    }

    public final boolean zzl(String str) {
        return "1".equals(this.zzb.zza(str, "measurement.event_sampling_enabled"));
    }

    @WorkerThread
    public final String zzd(String str, zzff<String> zzffVar) {
        if (str == null) {
            return zzffVar.zza(null);
        }
        return zzffVar.zza(this.zzb.zza(str, zzffVar.zza()));
    }

    public final boolean zze(String str, zzff<Boolean> zzffVar) {
        return zzf(str, zzffVar);
    }

    @WorkerThread
    public final int zza(String str, zzff<Integer> zzffVar, int i2, int i3) {
        return Math.max(Math.min(zzb(str, zzffVar), i3), i2);
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    private final String zza(String str, String str2) {
        try {
            String str3 = (String) Class.forName("android.os.SystemProperties").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(null, str, str2);
            Preconditions.checkNotNull(str3);
            return str3;
        } catch (ClassNotFoundException e2) {
            zzj().zzg().zza("Could not find SystemProperties class", e2);
            return str2;
        } catch (IllegalAccessException e3) {
            zzj().zzg().zza("Could not access SystemProperties.get()", e3);
            return str2;
        } catch (NoSuchMethodException e4) {
            zzj().zzg().zza("Could not find SystemProperties.get() method", e4);
            return str2;
        } catch (InvocationTargetException e5) {
            zzj().zzg().zza("SystemProperties.get() threw an exception", e5);
            return str2;
        }
    }

    public final boolean zza(zzff<Boolean> zzffVar) {
        return zzf(null, zzffVar);
    }
}
