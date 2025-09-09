package com.umeng.commonsdk.vchannel;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static String f22583a = "https://pslog.umeng.com";

    /* renamed from: b, reason: collision with root package name */
    public static String f22584b = "https://pslog.umeng.com/";

    /* renamed from: c, reason: collision with root package name */
    public static String f22585c = "explog";

    /* renamed from: d, reason: collision with root package name */
    public static final String f22586d = "analytics";

    /* renamed from: e, reason: collision with root package name */
    public static final String f22587e = "ekv";

    /* renamed from: f, reason: collision with root package name */
    public static final String f22588f = "id";

    /* renamed from: g, reason: collision with root package name */
    public static final String f22589g = "ts";

    /* renamed from: h, reason: collision with root package name */
    public static final String f22590h = "ds";

    /* renamed from: i, reason: collision with root package name */
    public static final String f22591i = "pn";

    /* renamed from: j, reason: collision with root package name */
    public static String f22592j = "";

    static {
        String str = "SUB" + System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(String.format("%0" + (32 - str.length()) + "d", 0));
        f22592j = sb.toString();
    }
}
