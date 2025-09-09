package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.GuardedBy;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzki extends zze {

    /* renamed from: b, reason: collision with root package name */
    protected zzkf f13300b;
    private volatile zzkf zzb;
    private volatile zzkf zzc;
    private final Map<Activity, zzkf> zzd;

    @GuardedBy("activityLock")
    private Activity zze;

    @GuardedBy("activityLock")
    private volatile boolean zzf;
    private volatile zzkf zzg;
    private zzkf zzh;

    @GuardedBy("activityLock")
    private boolean zzi;
    private final Object zzj;

    public zzki(zzhc zzhcVar) {
        super(zzhcVar);
        this.zzj = new Object();
        this.zzd = new ConcurrentHashMap();
    }

    static /* synthetic */ void c(zzki zzkiVar, Bundle bundle, zzkf zzkfVar, zzkf zzkfVar2, long j2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (bundle != null) {
            bundle.remove(FirebaseAnalytics.Param.SCREEN_NAME);
            bundle.remove(FirebaseAnalytics.Param.SCREEN_CLASS);
        }
        zzkiVar.zza(zzkfVar, zzkfVar2, j2, true, zzkiVar.zzq().h(null, FirebaseAnalytics.Event.SCREEN_VIEW, bundle, null, false));
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    public final zzkf zzaa() {
        return this.zzb;
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
        return false;
    }

    @MainThread
    private final zzkf zzd(@NonNull Activity activity) {
        Preconditions.checkNotNull(activity);
        zzkf zzkfVar = this.zzd.get(activity);
        if (zzkfVar == null) {
            zzkf zzkfVar2 = new zzkf(null, zza(activity.getClass(), "Activity"), zzq().zzm());
            this.zzd.put(activity, zzkfVar2);
            zzkfVar = zzkfVar2;
        }
        return this.zzg != null ? this.zzg : zzkfVar;
    }

    @WorkerThread
    public final zzkf zza(boolean z2) {
        zzu();
        zzt();
        if (!z2) {
            return this.f13300b;
        }
        zzkf zzkfVar = this.f13300b;
        return zzkfVar != null ? zzkfVar : this.zzh;
    }

    @MainThread
    public final void zzb(Activity activity) throws IllegalStateException {
        synchronized (this.zzj) {
            this.zzi = false;
            this.zzf = true;
        }
        long jElapsedRealtime = zzb().elapsedRealtime();
        if (!zze().zzu()) {
            this.zzb = null;
            zzl().zzb(new zzkl(this, jElapsedRealtime));
        } else {
            zzkf zzkfVarZzd = zzd(activity);
            this.zzc = this.zzb;
            this.zzb = null;
            zzl().zzb(new zzko(this, zzkfVarZzd, jElapsedRealtime));
        }
    }

    @MainThread
    public final void zzc(Activity activity) throws IllegalStateException {
        synchronized (this.zzj) {
            this.zzi = true;
            if (activity != this.zze) {
                synchronized (this.zzj) {
                    this.zze = activity;
                    this.zzf = false;
                }
                if (zze().zzu()) {
                    this.zzg = null;
                    zzl().zzb(new zzkn(this));
                }
            }
        }
        if (!zze().zzu()) {
            this.zzb = this.zzg;
            zzl().zzb(new zzkm(this));
        } else {
            zza(activity, zzd(activity), false);
            zzb zzbVarZzc = zzc();
            zzbVarZzc.zzl().zzb(new zzc(zzbVarZzc, zzbVarZzc.zzb().elapsedRealtime()));
        }
    }

    @VisibleForTesting
    private final String zza(Class<?> cls, String str) {
        String str2;
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            return str;
        }
        String[] strArrSplit = canonicalName.split("\\.");
        if (strArrSplit.length > 0) {
            str2 = strArrSplit[strArrSplit.length - 1];
        } else {
            str2 = "";
        }
        return str2.length() > zze().c(null) ? str2.substring(0, zze().c(null)) : str2;
    }

    @MainThread
    private final void zza(Activity activity, zzkf zzkfVar, boolean z2) throws IllegalStateException {
        zzkf zzkfVar2;
        zzkf zzkfVar3 = this.zzb == null ? this.zzc : this.zzb;
        if (zzkfVar.zzb == null) {
            zzkfVar2 = new zzkf(zzkfVar.zza, activity != null ? zza(activity.getClass(), "Activity") : null, zzkfVar.zzc, zzkfVar.zze, zzkfVar.zzf);
        } else {
            zzkfVar2 = zzkfVar;
        }
        this.zzc = this.zzb;
        this.zzb = zzkfVar2;
        zzl().zzb(new zzkj(this, zzkfVar2, zzkfVar3, zzb().elapsedRealtime(), z2));
    }

    @MainThread
    public final void zzb(Activity activity, Bundle bundle) {
        zzkf zzkfVar;
        if (!zze().zzu() || bundle == null || (zzkfVar = this.zzd.get(activity)) == null) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putLong("id", zzkfVar.zzc);
        bundle2.putString("name", zzkfVar.zza);
        bundle2.putString("referrer_name", zzkfVar.zzb);
        bundle.putBundle("com.google.app_measurement.screen_service", bundle2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00af  */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(com.google.android.gms.measurement.internal.zzkf r16, com.google.android.gms.measurement.internal.zzkf r17, long r18, boolean r20, android.os.Bundle r21) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r15 = this;
            r0 = r15
            r1 = r16
            r2 = r17
            r3 = r18
            r5 = r21
            r15.zzt()
            r6 = 0
            r7 = 1
            if (r2 == 0) goto L2f
            long r8 = r2.zzc
            long r10 = r1.zzc
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 != 0) goto L2f
            java.lang.String r8 = r2.zzb
            java.lang.String r9 = r1.zzb
            boolean r8 = com.google.android.gms.measurement.internal.zzkh.zza(r8, r9)
            if (r8 == 0) goto L2f
            java.lang.String r8 = r2.zza
            java.lang.String r9 = r1.zza
            boolean r8 = com.google.android.gms.measurement.internal.zzkh.zza(r8, r9)
            if (r8 != 0) goto L2d
            goto L2f
        L2d:
            r8 = r6
            goto L30
        L2f:
            r8 = r7
        L30:
            if (r20 == 0) goto L37
            com.google.android.gms.measurement.internal.zzkf r9 = r0.f13300b
            if (r9 == 0) goto L37
            r6 = r7
        L37:
            if (r8 == 0) goto Lba
            android.os.Bundle r8 = new android.os.Bundle
            if (r5 == 0) goto L42
            r8.<init>(r5)
        L40:
            r14 = r8
            goto L46
        L42:
            r8.<init>()
            goto L40
        L46:
            com.google.android.gms.measurement.internal.zzne.zza(r1, r14, r7)
            if (r2 == 0) goto L64
            java.lang.String r5 = r2.zza
            if (r5 == 0) goto L54
            java.lang.String r8 = "_pn"
            r14.putString(r8, r5)
        L54:
            java.lang.String r5 = r2.zzb
            if (r5 == 0) goto L5d
            java.lang.String r8 = "_pc"
            r14.putString(r8, r5)
        L5d:
            java.lang.String r5 = "_pi"
            long r8 = r2.zzc
            r14.putLong(r5, r8)
        L64:
            r8 = 0
            if (r6 == 0) goto L7d
            com.google.android.gms.measurement.internal.zzly r2 = r15.zzp()
            com.google.android.gms.measurement.internal.zzme r2 = r2.f13306c
            long r10 = r2.a(r3)
            int r2 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r2 <= 0) goto L7d
            com.google.android.gms.measurement.internal.zzne r2 = r15.zzq()
            r2.zza(r14, r10)
        L7d:
            com.google.android.gms.measurement.internal.zzaf r2 = r15.zze()
            boolean r2 = r2.zzu()
            if (r2 != 0) goto L8e
            java.lang.String r2 = "_mst"
            r10 = 1
            r14.putLong(r2, r10)
        L8e:
            boolean r2 = r1.zze
            if (r2 == 0) goto L96
            java.lang.String r2 = "app"
        L94:
            r10 = r2
            goto L99
        L96:
            java.lang.String r2 = "auto"
            goto L94
        L99:
            com.google.android.gms.common.util.Clock r2 = r15.zzb()
            long r11 = r2.currentTimeMillis()
            boolean r2 = r1.zze
            r20 = r11
            if (r2 == 0) goto Laf
            long r11 = r1.zzf
            int r2 = (r11 > r8 ? 1 : (r11 == r8 ? 0 : -1))
            if (r2 == 0) goto Laf
            r12 = r11
            goto Lb1
        Laf:
            r12 = r20
        Lb1:
            com.google.android.gms.measurement.internal.zzin r9 = r15.zzm()
            java.lang.String r11 = "_vs"
            r9.l(r10, r11, r12, r14)
        Lba:
            if (r6 == 0) goto Lc1
            com.google.android.gms.measurement.internal.zzkf r2 = r0.f13300b
            r15.zza(r2, r7, r3)
        Lc1:
            r0.f13300b = r1
            boolean r2 = r1.zze
            if (r2 == 0) goto Lc9
            r0.zzh = r1
        Lc9:
            com.google.android.gms.measurement.internal.zzkq r2 = r15.zzo()
            r2.i(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzki.zza(com.google.android.gms.measurement.internal.zzkf, com.google.android.gms.measurement.internal.zzkf, long, boolean, android.os.Bundle):void");
    }

    @MainThread
    public final void zza(Activity activity, Bundle bundle) {
        Bundle bundle2;
        if (!zze().zzu() || bundle == null || (bundle2 = bundle.getBundle("com.google.app_measurement.screen_service")) == null) {
            return;
        }
        this.zzd.put(activity, new zzkf(bundle2.getString("name"), bundle2.getString("referrer_name"), bundle2.getLong("id")));
    }

    @MainThread
    public final void zza(Activity activity) {
        synchronized (this.zzj) {
            try {
                if (activity == this.zze) {
                    this.zze = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (zze().zzu()) {
            this.zzd.remove(activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zza(zzkf zzkfVar, boolean z2, long j2) {
        zzc().zza(zzb().elapsedRealtime());
        if (!zzp().zza(zzkfVar != null && zzkfVar.f13299a, z2, j2) || zzkfVar == null) {
            return;
        }
        zzkfVar.f13299a = false;
    }

    @Deprecated
    public final void zza(@NonNull Activity activity, @Size(max = 36, min = 1) String str, @Size(max = 36, min = 1) String str2) throws IllegalStateException {
        if (!zze().zzu()) {
            zzj().zzv().zza("setCurrentScreen cannot be called while screen reporting is disabled.");
            return;
        }
        zzkf zzkfVar = this.zzb;
        if (zzkfVar == null) {
            zzj().zzv().zza("setCurrentScreen cannot be called while no activity active");
            return;
        }
        if (this.zzd.get(activity) == null) {
            zzj().zzv().zza("setCurrentScreen must be called with an activity in the activity lifecycle");
            return;
        }
        if (str2 == null) {
            str2 = zza(activity.getClass(), "Activity");
        }
        boolean zZza = zzkh.zza(zzkfVar.zzb, str2);
        boolean zZza2 = zzkh.zza(zzkfVar.zza, str);
        if (zZza && zZza2) {
            zzj().zzv().zza("setCurrentScreen cannot be called with the same class and name");
            return;
        }
        if (str != null && (str.length() <= 0 || str.length() > zze().c(null))) {
            zzj().zzv().zza("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            return;
        }
        if (str2 != null && (str2.length() <= 0 || str2.length() > zze().c(null))) {
            zzj().zzv().zza("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            return;
        }
        zzj().zzp().zza("Setting current screen to name, class", str == null ? TmpConstant.GROUP_ROLE_UNKNOWN : str, str2);
        zzkf zzkfVar2 = new zzkf(str, str2, zzq().zzm());
        this.zzd.put(activity, zzkfVar2);
        zza(activity, zzkfVar2, true);
    }

    public final void zza(Bundle bundle, long j2) {
        String str;
        synchronized (this.zzj) {
            try {
                if (!this.zzi) {
                    zzj().zzv().zza("Cannot log screen view event when the app is in the background.");
                    return;
                }
                String strZza = null;
                if (bundle != null) {
                    String string = bundle.getString(FirebaseAnalytics.Param.SCREEN_NAME);
                    if (string != null && (string.length() <= 0 || string.length() > zze().c(null))) {
                        zzj().zzv().zza("Invalid screen name length for screen view. Length", Integer.valueOf(string.length()));
                        return;
                    }
                    String string2 = bundle.getString(FirebaseAnalytics.Param.SCREEN_CLASS);
                    if (string2 != null && (string2.length() <= 0 || string2.length() > zze().c(null))) {
                        zzj().zzv().zza("Invalid screen class length for screen view. Length", Integer.valueOf(string2.length()));
                        return;
                    } else {
                        strZza = string2;
                        str = string;
                    }
                } else {
                    str = null;
                }
                if (strZza == null) {
                    Activity activity = this.zze;
                    if (activity != null) {
                        strZza = zza(activity.getClass(), "Activity");
                    } else {
                        strZza = "Activity";
                    }
                }
                String str2 = strZza;
                zzkf zzkfVar = this.zzb;
                if (this.zzf && zzkfVar != null) {
                    this.zzf = false;
                    boolean zZza = zzkh.zza(zzkfVar.zzb, str2);
                    boolean zZza2 = zzkh.zza(zzkfVar.zza, str);
                    if (zZza && zZza2) {
                        zzj().zzv().zza("Ignoring call to log screen view event with duplicate parameters.");
                        return;
                    }
                }
                zzj().zzp().zza("Logging screen view with name, class", str == null ? TmpConstant.GROUP_ROLE_UNKNOWN : str, str2 == null ? TmpConstant.GROUP_ROLE_UNKNOWN : str2);
                zzkf zzkfVar2 = this.zzb == null ? this.zzc : this.zzb;
                zzkf zzkfVar3 = new zzkf(str, str2, zzq().zzm(), true, j2);
                this.zzb = zzkfVar3;
                this.zzc = zzkfVar2;
                this.zzg = zzkfVar3;
                zzl().zzb(new zzkk(this, bundle, zzkfVar3, zzkfVar2, zzb().elapsedRealtime()));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
