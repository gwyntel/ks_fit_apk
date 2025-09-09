package com.google.android.gms.common.wrappers;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Process;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@KeepForSdk
/* loaded from: classes3.dex */
public class PackageManagerWrapper {

    /* renamed from: a, reason: collision with root package name */
    protected final Context f12896a;

    public PackageManagerWrapper(@NonNull Context context) {
        this.f12896a = context;
    }

    @KeepForSdk
    public int checkCallingOrSelfPermission(@NonNull String str) {
        return this.f12896a.checkCallingOrSelfPermission(str);
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public int checkPermission(@NonNull String str, @NonNull String str2) {
        return this.f12896a.getPackageManager().checkPermission(str, str2);
    }

    @NonNull
    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public ApplicationInfo getApplicationInfo(@NonNull String str, int i2) throws PackageManager.NameNotFoundException {
        return this.f12896a.getPackageManager().getApplicationInfo(str, i2);
    }

    @NonNull
    @KeepForSdk
    public CharSequence getApplicationLabel(@NonNull String str) throws PackageManager.NameNotFoundException {
        Context context = this.f12896a;
        return context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(str, 0));
    }

    @NonNull
    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public Pair<CharSequence, Drawable> getApplicationLabelAndIcon(@NonNull String str) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = this.f12896a.getPackageManager().getApplicationInfo(str, 0);
        return Pair.create(this.f12896a.getPackageManager().getApplicationLabel(applicationInfo), this.f12896a.getPackageManager().getApplicationIcon(applicationInfo));
    }

    @NonNull
    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public PackageInfo getPackageInfo(@NonNull String str, int i2) throws PackageManager.NameNotFoundException {
        return this.f12896a.getPackageManager().getPackageInfo(str, i2);
    }

    @KeepForSdk
    public boolean isCallerInstantApp() {
        String nameForUid;
        if (Binder.getCallingUid() == Process.myUid()) {
            return InstantApps.isInstantApp(this.f12896a);
        }
        if (!PlatformVersion.isAtLeastO() || (nameForUid = this.f12896a.getPackageManager().getNameForUid(Binder.getCallingUid())) == null) {
            return false;
        }
        return this.f12896a.getPackageManager().isInstantApp(nameForUid);
    }

    @TargetApi(19)
    public final boolean zza(int i2, @NonNull String str) {
        if (PlatformVersion.isAtLeastKitKat()) {
            try {
                AppOpsManager appOpsManager = (AppOpsManager) this.f12896a.getSystemService("appops");
                if (appOpsManager == null) {
                    throw new NullPointerException("context.getSystemService(Context.APP_OPS_SERVICE) is null");
                }
                appOpsManager.checkPackage(i2, str);
                return true;
            } catch (SecurityException unused) {
                return false;
            }
        }
        String[] packagesForUid = this.f12896a.getPackageManager().getPackagesForUid(i2);
        if (str != null && packagesForUid != null) {
            for (String str2 : packagesForUid) {
                if (str.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
