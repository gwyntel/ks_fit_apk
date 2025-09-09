package com.ta.a.b;

import android.content.Context;
import java.io.File;

/* loaded from: classes4.dex */
public class e {
    public static void a(String str) {
        try {
            com.ta.a.c.f.e();
            com.ta.a.c.a.b(b(), str);
        } catch (Throwable unused) {
        }
    }

    private static String b() {
        String str = a(com.ta.a.a.a().getContext()) + File.separator + "4635b664f789000d";
        com.ta.a.c.f.b("", str);
        return str;
    }

    public static String c() {
        return a(com.ta.a.a.a().getContext()) + File.separator + "9983c160aa044115";
    }

    public static String d() {
        try {
            return com.ta.a.c.a.c(b());
        } catch (Exception unused) {
            return null;
        }
    }

    private static String a(Context context) {
        String str = context.getFilesDir().getAbsolutePath() + File.separator + ".7934039a7252be16";
        com.ta.a.c.f.b("", "UtdidAppRoot dir:" + str);
        com.ta.a.c.a.b(str);
        return str;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m75a(Context context) {
        try {
            return !context.getFileStreamPath("3c9b584e65e6c983").exists();
        } catch (Exception unused) {
            return true;
        }
    }
}
