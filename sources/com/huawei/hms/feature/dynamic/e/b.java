package com.huawei.hms.feature.dynamic.e;

import android.util.Log;
import dalvik.system.DexClassLoader;

/* loaded from: classes4.dex */
public final class b extends DexClassLoader {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16152a = "b";

    public b(String str, String str2, String str3, ClassLoader classLoader) {
        super(str, str2, str3, classLoader);
    }

    @Override // java.lang.ClassLoader
    public Class<?> loadClass(String str, boolean z2) throws ClassNotFoundException {
        if (!str.startsWith("java.") && !str.startsWith("android.")) {
            try {
                return findClass(str);
            } catch (ClassNotFoundException unused) {
                Log.w(f16152a, "Cannot find The class:" + str);
            }
        }
        return super.loadClass(str, z2);
    }
}
