package com.aliyun.alink.linksdk.channel.mobile.a;

import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.alink.linksdk.tools.log.HLoggerFactory;
import com.aliyun.alink.linksdk.tools.log.ILogger;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static ILogger f10994a = new HLoggerFactory().getInstance("LK-mc-");

    private static String a(String str) {
        return str;
    }

    public static void b(String str, String str2) {
        f10994a.i(str, a(str2));
    }

    public static void c(String str, String str2) {
        f10994a.w(str, a(str2));
    }

    public static void d(String str, String str2) {
        f10994a.e(str, a(str2));
    }

    public static void a(String str, String str2) {
        f10994a.d(str, a(str2));
    }

    public static void a(int i2) {
        ALog.setLevel((byte) i2);
    }
}
