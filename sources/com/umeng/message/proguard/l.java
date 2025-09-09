package com.umeng.message.proguard;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/* loaded from: classes4.dex */
final class l {

    /* renamed from: a, reason: collision with root package name */
    final String f22859a;

    /* renamed from: b, reason: collision with root package name */
    final String f22860b;

    /* renamed from: c, reason: collision with root package name */
    final String f22861c;

    /* renamed from: d, reason: collision with root package name */
    final long f22862d;

    /* renamed from: e, reason: collision with root package name */
    final long f22863e;

    /* renamed from: f, reason: collision with root package name */
    final int f22864f;

    public l(Object obj, Object obj2) {
        int i2;
        String strValueOf;
        PackageManager packageManager = (PackageManager) obj;
        PackageInfo packageInfo = (PackageInfo) obj2;
        this.f22859a = packageInfo.packageName;
        this.f22861c = packageInfo.versionName;
        this.f22862d = packageInfo.firstInstallTime;
        this.f22863e = packageInfo.lastUpdateTime;
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        if (applicationInfo == null) {
            i2 = -1;
        } else {
            int i3 = applicationInfo.flags;
            i2 = ((i3 & 1) == 0 && (i3 & 128) == 0) ? 1 : 0;
        }
        this.f22864f = i2;
        try {
            strValueOf = String.valueOf(packageManager.getApplicationLabel(applicationInfo));
        } catch (Throwable unused) {
            strValueOf = "";
        }
        this.f22860b = strValueOf;
    }
}
