package com.taobao.accs.utl;

import android.content.Context;
import android.content.SharedPreferences;
import com.kingsmith.miot.KsProperty;
import com.taobao.accs.common.Constants;

/* loaded from: classes4.dex */
public class t {

    /* renamed from: a, reason: collision with root package name */
    private static volatile Long f20412a;

    /* renamed from: b, reason: collision with root package name */
    private static volatile Integer f20413b;

    public static boolean a() {
        return true;
    }

    public static boolean b() {
        return true;
    }

    public static boolean c() {
        return true;
    }

    public static long d() {
        if (f20413b == null) {
            f20413b = 10000;
            ALog.d("OrangeAdapter", "getConnectTimeout", "result", f20413b);
        }
        return f20413b.intValue();
    }

    public static boolean e() {
        return true;
    }

    static long a(Context context) {
        if (f20412a == null) {
            try {
                f20412a = Long.valueOf(context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getLong(Constants.SP_KEY_LAST_LAUNCH_TIME, 0L));
            } catch (Throwable th) {
                ALog.e("OrangeAdapter", "getLastActiveTime", th, new Object[0]);
            }
            ALog.d("OrangeAdapter", "getLastActiveTime", "result", f20412a);
        }
        return f20412a.longValue();
    }

    static void a(Context context, long j2) {
        try {
            f20412a = Long.valueOf(j2);
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            editorEdit.putLong(Constants.SP_KEY_LAST_LAUNCH_TIME, j2);
            editorEdit.apply();
        } catch (Throwable th) {
            ALog.e("OrangeAdapter", "saveLastActiveTime fail:", th, "lastLaunchTime", Long.valueOf(j2));
        }
    }

    public static void a(Context context, String str, int i2) {
        try {
        } catch (Exception e2) {
            ALog.e("OrangeAdapter", "saveConfigToSP fail:", e2, KsProperty.Key, str, "value", Integer.valueOf(i2));
        }
        if (context == null) {
            ALog.e("OrangeAdapter", "saveTLogOffToSP context null", new Object[0]);
            return;
        }
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
        editorEdit.putInt(str, i2);
        editorEdit.apply();
        ALog.i("OrangeAdapter", "saveConfigToSP", KsProperty.Key, str, "value", Integer.valueOf(i2));
    }
}
