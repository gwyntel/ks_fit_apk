package com.umeng.commonsdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.umeng.commonsdk.debug.UMRTLog;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static HashMap<String, PackageInfo> f22543a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private static Object f22544b = new Object();

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f22545a = new b();

        private a() {
        }
    }

    public static b a() {
        return a.f22545a;
    }

    private b() {
    }

    public PackageInfo a(Context context, String str, int i2) {
        PackageInfo packageInfo;
        synchronized (f22544b) {
            try {
                if (f22543a.containsKey(str)) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> pkg： " + str + ", pkgInfo缓存命中，直接返回");
                    packageInfo = f22543a.get(str);
                } else {
                    try {
                        packageInfo = context.getPackageManager().getPackageInfo(str, i2);
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> pkg： " + str + ", 获取pkgInfo并缓存");
                        f22543a.put(str, packageInfo);
                    } catch (PackageManager.NameNotFoundException unused) {
                        f22543a.put(str, null);
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> pkg: " + str + "，目标包未安装。");
                        packageInfo = null;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return packageInfo;
    }
}
