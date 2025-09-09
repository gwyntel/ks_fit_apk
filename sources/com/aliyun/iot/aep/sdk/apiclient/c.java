package com.aliyun.iot.aep.sdk.apiclient;

import android.util.Log;

/* loaded from: classes3.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static byte f11618a = 1;

    /* renamed from: b, reason: collision with root package name */
    public static d f11619b;

    public static void a(String str, String str2) {
        d dVar = f11619b;
        if (dVar != null) {
            dVar.a(str, str2);
        } else {
            a((byte) 1, str, str2);
        }
    }

    public static void b(String str, String str2) {
        d dVar = f11619b;
        if (dVar != null) {
            dVar.b(str, str2);
        } else {
            a((byte) 2, str, str2);
        }
    }

    public static void a(byte b2, String str, String str2) {
        if (f11618a > b2) {
            return;
        }
        if (b2 == 1) {
            Log.d(str, str2);
            return;
        }
        if (b2 == 2) {
            Log.i(str, str2);
        } else if (b2 == 3) {
            Log.w(str, str2);
        } else {
            if (b2 != 4) {
                return;
            }
            Log.e(str, str2);
        }
    }
}
