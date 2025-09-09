package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

/* loaded from: classes4.dex */
class w implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23425a;

    w(Context context) {
        this.f23425a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            PackageInfo packageInfo = this.f23425a.getPackageManager().getPackageInfo(this.f23425a.getPackageName(), 4612);
            v.c(this.f23425a);
            v.d(this.f23425a, packageInfo);
            v.c(this.f23425a, packageInfo);
        } catch (Throwable th) {
            Log.e("ManifestChecker", "", th);
        }
    }
}
