package com.taobao.accs.utl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import com.taobao.accs.client.AccsConfig;
import com.taobao.accs.common.Constants;

/* loaded from: classes4.dex */
public class v {
    public static final String SP_AGOO_BIND_FILE_NAME = "AGOO_BIND";

    /* renamed from: a, reason: collision with root package name */
    static int f20415a = -1;

    /* renamed from: b, reason: collision with root package name */
    private static final byte[] f20416b = new byte[0];

    /* renamed from: c, reason: collision with root package name */
    private static int f20417c = -1;

    public static boolean a(Context context) {
        if (context == null) {
            return false;
        }
        if (f20417c == -1) {
            f20417c = context.getApplicationInfo().targetSdkVersion;
        }
        return f20417c >= 26 && Build.VERSION.SDK_INT >= 26;
    }

    public static int b(Context context) {
        int i2;
        int i3;
        int i4 = f20415a;
        if (i4 != -1) {
            return i4;
        }
        try {
            try {
            } catch (Throwable th) {
                th = th;
                i2 = context;
            }
        } catch (Throwable th2) {
            th = th2;
            i2 = 0;
        }
        synchronized (f20416b) {
            try {
                i3 = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getInt(Constants.SP_KEY_DEBUG_MODE, 0);
                return i3;
            } catch (Throwable th3) {
                th = th3;
                i2 = 0;
                try {
                    throw th;
                } catch (Throwable th4) {
                    th = th4;
                    ALog.e("Utils", "getMode", th, new Object[0]);
                    i3 = i2;
                    return i3;
                }
            }
        }
    }

    public static void c(Context context) {
        try {
            synchronized (f20416b) {
                SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                editorEdit.clear();
                editorEdit.apply();
            }
        } catch (Throwable th) {
            ALog.e("Utils", "clearAllSharePreferences", th, new Object[0]);
        }
    }

    public static void d(Context context) {
        try {
            UtilityImpl.killService(context);
        } catch (Throwable th) {
            ALog.e("Utils", "killService", th, new Object[0]);
        }
    }

    public static boolean e(Context context) {
        boolean zIsMainProcess;
        try {
            zIsMainProcess = UtilityImpl.isMainProcess(context);
        } catch (Throwable th) {
            ALog.e("Utils", "killservice", th, new Object[0]);
            th.printStackTrace();
            zIsMainProcess = true;
        }
        ALog.i("Utils", "isMainProcess", "result", Boolean.valueOf(zIsMainProcess));
        return zIsMainProcess;
    }

    public static void f(Context context) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("AGOO_BIND", 0).edit();
            editorEdit.clear();
            editorEdit.apply();
        } catch (Exception e2) {
            ALog.e("Utils", "clearAgooBindCache", e2, new Object[0]);
        }
    }

    public static void a(Context context, int i2) {
        try {
            synchronized (f20416b) {
                f20415a = i2;
                SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                editorEdit.putInt(Constants.SP_KEY_DEBUG_MODE, i2);
                editorEdit.apply();
            }
        } catch (Throwable th) {
            ALog.e("Utils", "setMode", th, new Object[0]);
        }
    }

    @Deprecated
    public static void a() {
        try {
            AccsConfig.build();
        } catch (Throwable th) {
            ALog.e("Utils", "initConfig", th, new Object[0]);
            th.printStackTrace();
        }
    }

    public static String a(Context context, String str, String str2) {
        String string = null;
        try {
            synchronized (f20416b) {
                string = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getString(str, null);
            }
            ALog.i("Utils", "getSpValue", "value", string);
            if (TextUtils.isEmpty(string)) {
                ALog.e("Utils", "getSpValue use default!", new Object[0]);
                return str2;
            }
        } catch (Throwable th) {
            ALog.e("Utils", "getSpValue fail", th, new Object[0]);
        }
        return string;
    }

    public static boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }
}
