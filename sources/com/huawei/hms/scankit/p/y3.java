package com.huawei.hms.scankit.p;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.mlsdk.common.AgConnectInfo;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class y3 {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18025a = "y3";

    /* renamed from: b, reason: collision with root package name */
    private static volatile Bundle f18026b = null;

    /* renamed from: c, reason: collision with root package name */
    public static String f18027c = "";

    /* renamed from: d, reason: collision with root package name */
    public static HashSet<String> f18028d = new HashSet<>();

    public static Bundle a(Context context) {
        if (context == null) {
            return new Bundle();
        }
        if (f18026b == null) {
            Bundle bundle = new Bundle();
            try {
                String string = AGConnectServicesConfig.fromContext(context).getString(AgConnectInfo.AgConnectKey.APPLICATION_ID);
                if (string == null) {
                    string = context.getPackageName();
                }
                bundle.putString("appid", string);
            } catch (RuntimeException unused) {
                Log.e(f18025a, "getAppInfo: RuntimeException");
            } catch (Exception unused2) {
                Log.e(f18025a, "getAppInfo: Exception");
            }
            f18026b = bundle;
        }
        return f18026b;
    }

    public static String b(Context context) {
        if (f18027c.length() == 0) {
            f18027c = context.getPackageName();
        }
        return f18027c;
    }

    public static HashSet<String> a() {
        if (f18028d.size() == 0) {
            f18028d.add("com.huawei.scanner");
            f18028d.add("com.huawei.hitouch");
        }
        return f18028d;
    }

    public static boolean a(String str, HashSet<String> hashSet) {
        if (str.length() == 0) {
            return true;
        }
        Iterator<String> it = hashSet.iterator();
        while (it.hasNext()) {
            if (str.contains(it.next())) {
                return false;
            }
        }
        return true;
    }
}
