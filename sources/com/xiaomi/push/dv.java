package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
public class dv {

    /* renamed from: a, reason: collision with root package name */
    private static dk f23611a;

    /* renamed from: a, reason: collision with other field name */
    private static dl f293a;

    public static void a(Context context, hb hbVar) {
        if (m294b(context)) {
            if (f23611a == null) {
                f23611a = new dk(context);
            }
            if (f293a == null) {
                f293a = new dl(context);
            }
            dk dkVar = f23611a;
            hbVar.a(dkVar, dkVar);
            dl dlVar = f293a;
            hbVar.b(dlVar, dlVar);
            a("startStats");
        }
    }

    public static void b(Context context, hb hbVar) {
        dk dkVar = f23611a;
        if (dkVar != null) {
            hbVar.a(dkVar);
            f23611a = null;
        }
        dl dlVar = f293a;
        if (dlVar != null) {
            hbVar.b(dlVar);
            f293a = null;
        }
        a("stopStats");
    }

    public static void c(Context context) {
        a("onPing");
        if (m294b(context)) {
            dy.c(context, System.currentTimeMillis(), m293a(context));
        }
    }

    public static void d(Context context) {
        a("onPong");
        if (m294b(context)) {
            dy.d(context, System.currentTimeMillis(), m293a(context));
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    private static boolean m294b(Context context) {
        return dj.a(context);
    }

    public static void a(Context context) {
        a("onSendMsg");
        if (m294b(context)) {
            dy.a(context, System.currentTimeMillis(), m293a(context));
        }
    }

    public static void b(Context context) {
        a("onReceiveMsg");
        if (m294b(context)) {
            dy.b(context, System.currentTimeMillis(), m293a(context));
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m293a(Context context) {
        return i.m500b(context);
    }

    static void a(String str) {
        dj.a("Push-PowerStats", str);
    }
}
