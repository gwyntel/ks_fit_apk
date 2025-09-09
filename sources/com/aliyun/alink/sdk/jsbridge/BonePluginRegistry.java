package com.aliyun.alink.sdk.jsbridge;

import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class BonePluginRegistry {

    /* renamed from: a, reason: collision with root package name */
    static Map<String, Class<? extends IBonePlugin>> f11507a = new HashMap(10);

    public static IBonePlugin findAPlugin(String str) {
        if (f11507a.containsKey(str)) {
            Class<? extends IBonePlugin> cls = f11507a.get(str);
            try {
                return cls.newInstance();
            } catch (Exception e2) {
                Log.e("APluginRegistry", "can not create instance for class:" + cls);
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static void register(String str, Class<? extends IBonePlugin> cls) {
        if (TextUtils.isEmpty(str) || cls == null) {
            return;
        }
        f11507a.containsKey(str);
        f11507a.put(str, cls);
    }

    public static void unregister(String str) {
        if (!TextUtils.isEmpty(str) && f11507a.containsKey(str)) {
            f11507a.remove(str);
        }
    }
}
