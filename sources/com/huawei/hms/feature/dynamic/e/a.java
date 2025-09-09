package com.huawei.hms.feature.dynamic.e;

import android.util.Log;
import dalvik.system.PathClassLoader;

/* loaded from: classes4.dex */
public final class a extends PathClassLoader {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16151a = "a";

    public a(String str, ClassLoader classLoader) {
        super(str, classLoader);
    }

    @Override // java.lang.ClassLoader
    public Class<?> loadClass(String str, boolean z2) throws ClassNotFoundException {
        if (!str.startsWith("java.") && !str.startsWith("android.")) {
            try {
                return findClass(str);
            } catch (ClassNotFoundException unused) {
                Log.w(f16151a, "Cannot find The class:" + str);
            }
        }
        return super.loadClass(str, z2);
    }
}
