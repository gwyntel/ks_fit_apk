package com.meizu.cloud.pushsdk.e.b;

import com.meizu.cloud.pushinternal.DebugLogger;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f19276a = false;

    /* renamed from: b, reason: collision with root package name */
    private static String f19277b = "AndroidNetworking";

    public static void a() {
        f19276a = true;
    }

    public static void b(String str) {
        if (f19276a) {
            DebugLogger.i(f19277b, str);
        }
    }

    public static void a(String str) {
        if (f19276a) {
            DebugLogger.d(f19277b, str);
        }
    }
}
