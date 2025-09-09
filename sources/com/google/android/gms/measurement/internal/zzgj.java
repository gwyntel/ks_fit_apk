package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.common.wrappers.Wrappers;

/* loaded from: classes3.dex */
public final class zzgj {

    /* renamed from: a, reason: collision with root package name */
    final zzhc f13278a;

    zzgj(zzmq zzmqVar) {
        this.f13278a = zzmqVar.y();
    }

    final Bundle a(String str, com.google.android.gms.internal.measurement.zzby zzbyVar) {
        this.f13278a.zzl().zzt();
        if (zzbyVar == null) {
            this.f13278a.zzj().zzu().zza("Attempting to use Install Referrer Service while it is not initialized");
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("package_name", str);
        try {
            Bundle bundleZza = zzbyVar.zza(bundle);
            if (bundleZza != null) {
                return bundleZza;
            }
            this.f13278a.zzj().zzg().zza("Install Referrer Service returned a null response");
            return null;
        } catch (Exception e2) {
            this.f13278a.zzj().zzg().zza("Exception occurred while retrieving the Install Referrer", e2.getMessage());
            return null;
        }
    }

    final boolean b() {
        try {
            PackageManagerWrapper packageManagerWrapperPackageManager = Wrappers.packageManager(this.f13278a.zza());
            if (packageManagerWrapperPackageManager != null) {
                return packageManagerWrapperPackageManager.getPackageInfo("com.android.vending", 128).versionCode >= 80837300;
            }
            this.f13278a.zzj().zzp().zza("Failed to get PackageManager for Install Referrer Play Store compatibility check");
            return false;
        } catch (Exception e2) {
            this.f13278a.zzj().zzp().zza("Failed to retrieve Play Store version for Install Referrer", e2);
            return false;
        }
    }
}
