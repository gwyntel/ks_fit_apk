package com.huawei.hms.feature.dynamic.f;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.huawei.hms.common.util.Logger;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16159a = "HMSPkgManager";

    /* renamed from: b, reason: collision with root package name */
    public static final List<String> f16160b = new a();

    /* renamed from: c, reason: collision with root package name */
    public static final int f16161c = 8;

    /* renamed from: d, reason: collision with root package name */
    public static final int f16162d = 4;

    /* renamed from: e, reason: collision with root package name */
    public static final int f16163e = 5;

    public static class a extends ArrayList<String> {
        public a() {
            add("com.huawei.hwid");
            add("com.huawei.hwid.tv");
        }
    }

    public static class b implements PrivilegedAction {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Field f16164a;

        public b(Field field) {
            this.f16164a = field;
        }

        @Override // java.security.PrivilegedAction
        public Object run() throws SecurityException {
            this.f16164a.setAccessible(true);
            return null;
        }
    }

    public static boolean a(Context context) {
        if (context == null) {
            Logger.e(f16159a, "The given context is null.");
            return false;
        }
        for (String str : f16160b) {
            try {
            } catch (PackageManager.NameNotFoundException unused) {
                Logger.w(f16159a, "Query for HMS Core package name:" + str + " failed.");
            }
            if (context.getPackageManager().getPackageInfo(str, 0) != null) {
                Logger.i(f16159a, "The HMS Core is installed, pkgName:" + str);
                return true;
            }
            continue;
        }
        return false;
    }

    public static boolean b(Context context, String str) throws IllegalAccessException, NoSuchFieldException, PackageManager.NameNotFoundException, IllegalArgumentException {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        if (context == null || TextUtils.isEmpty(str)) {
            Logger.e(f16159a, "context is null or pkgName is null.");
            return false;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (PackageManager.NameNotFoundException e2) {
            Logger.e(f16159a, "get PrivAppFlag err for " + str + ":" + e2.getMessage());
            packageInfo = null;
        }
        if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) {
            Logger.i(f16159a, "Get pkg application null:" + str);
            return false;
        }
        try {
            Field field = applicationInfo.getClass().getField("privateFlags");
            AccessController.doPrivileged(new b(field));
            Object obj = field.get(applicationInfo);
            if (!(obj instanceof Integer)) {
                Logger.i(f16159a, "Get privFlag instance error.");
                return false;
            }
            int iIntValue = ((Integer) obj).intValue();
            Logger.d(f16159a, "privFlag of " + str + " is:" + iIntValue);
            boolean z2 = (iIntValue & 8) != 0;
            Logger.i(f16159a, "pkgName:" + str + ", isPrivApp:" + z2);
            return z2;
        } catch (IllegalAccessException e3) {
            e = e3;
            Logger.e(f16159a, "get Priv App Flag err for " + str + ":" + e.getMessage());
            return false;
        } catch (NoSuchFieldException e4) {
            e = e4;
            Logger.e(f16159a, "get Priv App Flag err for " + str + ":" + e.getMessage());
            return false;
        }
    }

    public static boolean c(Context context, String str) throws PackageManager.NameNotFoundException {
        if (context == null || TextUtils.isEmpty(str)) {
            Logger.w(f16159a, "context is null or pkgName is null.");
            return false;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
            boolean z2 = (packageInfo == null || (packageInfo.applicationInfo.flags & 1) == 0) ? false : true;
            Logger.i(f16159a, "isHMSSystemApp:" + z2);
            if (z2) {
                Logger.i(f16159a, "The HMS package:" + str + " is SystemApp");
                return true;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Logger.e(f16159a, "getSystemApp flag error for " + str + ":" + e2.getMessage());
        }
        return false;
    }

    public static boolean a(Context context, String str) throws IOException {
        if (context == null || TextUtils.isEmpty(str)) {
            Logger.e(f16159a, "context is null or filePath is null.");
            return false;
        }
        try {
        } catch (IOException unused) {
            Logger.e(f16159a, "checkPathValidity IOException");
        }
        if (!new File(str).exists()) {
            Logger.w(f16159a, "the file does not exist.");
            return false;
        }
        String canonicalPath = new File(str).getCanonicalPath();
        if (canonicalPath.startsWith("/system/app/HFF")) {
            Logger.i(f16159a, "HFF file path, need not to verify.");
            return true;
        }
        if (canonicalPath.startsWith("/data/data/")) {
            String[] strArrSplit = canonicalPath.split("/");
            if (strArrSplit.length >= 4) {
                return b(context, strArrSplit[3]);
            }
        } else if (canonicalPath.startsWith("/data/user_de/") || canonicalPath.startsWith("/data/user/")) {
            String[] strArrSplit2 = canonicalPath.split("/");
            if (strArrSplit2.length >= 5) {
                return b(context, strArrSplit2[4]);
            }
        } else {
            Logger.w(f16159a, "illegal path.");
        }
        return false;
    }
}
