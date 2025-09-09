package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.MainThread;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzri;
import com.google.android.gms.internal.measurement.zzsy;
import com.google.firebase.messaging.Constants;
import com.huawei.hms.support.api.entity.core.CommonCode;
import java.lang.reflect.InvocationTargetException;

@MainThread
@VisibleForTesting
/* loaded from: classes3.dex */
final class zzjy implements Application.ActivityLifecycleCallbacks {
    private final /* synthetic */ zzin zza;

    zzjy(zzin zzinVar) {
        this.zza = zzinVar;
    }

    static /* synthetic */ void a(zzjy zzjyVar, boolean z2, Uri uri, String str, String str2) throws IllegalAccessException, InvocationTargetException {
        Bundle bundleG;
        Uri uri2;
        boolean z3;
        zzjyVar.zza.zzt();
        try {
            zzne zzneVarZzq = zzjyVar.zza.zzq();
            boolean z4 = zzsy.zzb() && zzjyVar.zza.zze().zza(zzbi.zzby);
            boolean z5 = zzri.zzb() && zzjyVar.zza.zze().zza(zzbi.zzcs);
            if (TextUtils.isEmpty(str2)) {
                bundleG = null;
            } else if (str2.contains("gclid") || ((z5 && str2.contains("gbraid")) || str2.contains("utm_campaign") || str2.contains("utm_source") || str2.contains("utm_medium") || str2.contains("utm_id") || str2.contains("dclid") || str2.contains("srsltid") || (z4 && str2.contains("sfmc_id")))) {
                bundleG = zzneVarZzq.g(Uri.parse("https://google.com/search?" + str2), z4, z5);
                if (bundleG != null) {
                    bundleG.putString("_cis", "referrer");
                }
            } else {
                zzneVarZzq.zzj().zzc().zza("Activity created with data 'referrer' without required params");
                bundleG = null;
            }
            if (z2) {
                zzne zzneVarZzq2 = zzjyVar.zza.zzq();
                boolean z6 = zzsy.zzb() && zzjyVar.zza.zze().zza(zzbi.zzby);
                if (zzri.zzb() && zzjyVar.zza.zze().zza(zzbi.zzcs)) {
                    uri2 = uri;
                    z3 = true;
                } else {
                    uri2 = uri;
                    z3 = false;
                }
                Bundle bundleG2 = zzneVarZzq2.g(uri2, z6, z3);
                if (bundleG2 != null) {
                    bundleG2.putString("_cis", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK);
                    if (!bundleG2.containsKey("gclid") && bundleG != null && bundleG.containsKey("gclid")) {
                        bundleG2.putString("_cer", String.format("gclid=%s", bundleG.getString("gclid")));
                    }
                    zzjyVar.zza.r(str, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundleG2);
                    zzjyVar.zza.f13298c.b(str, bundleG2);
                }
            }
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            zzjyVar.zza.zzj().zzc().zza("Activity created with referrer", str2);
            if (zzjyVar.zza.zze().zza(zzbi.zzbi)) {
                if (bundleG != null) {
                    zzjyVar.zza.r(str, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundleG);
                    zzjyVar.zza.f13298c.b(str, bundleG);
                } else {
                    zzjyVar.zza.zzj().zzc().zza("Referrer does not contain valid parameters", str2);
                }
                zzjyVar.zza.zza("auto", "_ldl", (Object) null, true);
                return;
            }
            if (!str2.contains("gclid") || (!str2.contains("utm_campaign") && !str2.contains("utm_source") && !str2.contains("utm_medium") && !str2.contains("utm_term") && !str2.contains("utm_content"))) {
                zzjyVar.zza.zzj().zzc().zza("Activity created with data 'referrer' without required params");
            } else {
                if (TextUtils.isEmpty(str2)) {
                    return;
                }
                zzjyVar.zza.zza("auto", "_ldl", (Object) str2, true);
            }
        } catch (RuntimeException e2) {
            zzjyVar.zza.zzj().zzg().zza("Throwable caught in handleReferrerForOnActivityCreated", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004a  */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onActivityCreated(android.app.Activity r9, android.os.Bundle r10) {
        /*
            r8 = this;
            com.google.android.gms.measurement.internal.zzin r0 = r8.zza     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            com.google.android.gms.measurement.internal.zzfs r0 = r0.zzj()     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            com.google.android.gms.measurement.internal.zzfu r0 = r0.zzp()     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            java.lang.String r1 = "onActivityCreated"
            r0.zza(r1)     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            android.content.Intent r0 = r9.getIntent()     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            if (r0 != 0) goto L1f
            com.google.android.gms.measurement.internal.zzin r0 = r8.zza
            com.google.android.gms.measurement.internal.zzki r0 = r0.zzn()
            r0.zza(r9, r10)
            return
        L1f:
            android.net.Uri r1 = r0.getData()     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            if (r1 == 0) goto L33
            boolean r2 = r1.isHierarchical()     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            if (r2 == 0) goto L33
        L2b:
            r5 = r1
            goto L4c
        L2d:
            r0 = move-exception
            goto Lb1
        L30:
            r0 = move-exception
            goto L98
        L33:
            android.os.Bundle r1 = r0.getExtras()     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            if (r1 == 0) goto L4a
            java.lang.String r2 = "com.android.vending.referral_url"
            java.lang.String r1 = r1.getString(r2)     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            if (r2 != 0) goto L4a
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            goto L2b
        L4a:
            r1 = 0
            goto L2b
        L4c:
            if (r5 == 0) goto L8e
            boolean r1 = r5.isHierarchical()     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            if (r1 != 0) goto L55
            goto L8e
        L55:
            com.google.android.gms.measurement.internal.zzin r1 = r8.zza     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            r1.zzq()     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            boolean r0 = com.google.android.gms.measurement.internal.zzne.q(r0)     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            if (r0 == 0) goto L64
            java.lang.String r0 = "gs"
        L62:
            r6 = r0
            goto L67
        L64:
            java.lang.String r0 = "auto"
            goto L62
        L67:
            java.lang.String r0 = "referrer"
            java.lang.String r7 = r5.getQueryParameter(r0)     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            if (r10 != 0) goto L72
            r0 = 1
        L70:
            r4 = r0
            goto L74
        L72:
            r0 = 0
            goto L70
        L74:
            com.google.android.gms.measurement.internal.zzin r0 = r8.zza     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            com.google.android.gms.measurement.internal.zzgz r0 = r0.zzl()     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            com.google.android.gms.measurement.internal.zzjx r1 = new com.google.android.gms.measurement.internal.zzjx     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            r2 = r1
            r3 = r8
            r2.<init>(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            r0.zzb(r1)     // Catch: java.lang.Throwable -> L2d java.lang.RuntimeException -> L30
            com.google.android.gms.measurement.internal.zzin r0 = r8.zza
            com.google.android.gms.measurement.internal.zzki r0 = r0.zzn()
            r0.zza(r9, r10)
            return
        L8e:
            com.google.android.gms.measurement.internal.zzin r0 = r8.zza
            com.google.android.gms.measurement.internal.zzki r0 = r0.zzn()
            r0.zza(r9, r10)
            return
        L98:
            com.google.android.gms.measurement.internal.zzin r1 = r8.zza     // Catch: java.lang.Throwable -> L2d
            com.google.android.gms.measurement.internal.zzfs r1 = r1.zzj()     // Catch: java.lang.Throwable -> L2d
            com.google.android.gms.measurement.internal.zzfu r1 = r1.zzg()     // Catch: java.lang.Throwable -> L2d
            java.lang.String r2 = "Throwable caught in onActivityCreated"
            r1.zza(r2, r0)     // Catch: java.lang.Throwable -> L2d
            com.google.android.gms.measurement.internal.zzin r0 = r8.zza
            com.google.android.gms.measurement.internal.zzki r0 = r0.zzn()
            r0.zza(r9, r10)
            return
        Lb1:
            com.google.android.gms.measurement.internal.zzin r1 = r8.zza
            com.google.android.gms.measurement.internal.zzki r1 = r1.zzn()
            r1.zza(r9, r10)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjy.onActivityCreated(android.app.Activity, android.os.Bundle):void");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        this.zza.zzn().zza(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityPaused(Activity activity) throws IllegalStateException {
        this.zza.zzn().zzb(activity);
        zzly zzlyVarZzp = this.zza.zzp();
        zzlyVarZzp.zzl().zzb(new zzma(zzlyVarZzp, zzlyVarZzp.zzb().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityResumed(Activity activity) throws IllegalStateException {
        zzly zzlyVarZzp = this.zza.zzp();
        zzlyVarZzp.zzl().zzb(new zzlx(zzlyVarZzp, zzlyVarZzp.zzb().elapsedRealtime()));
        this.zza.zzn().zzc(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zza.zzn().zzb(activity, bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }
}
