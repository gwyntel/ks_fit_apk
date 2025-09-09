package com.aliyun.alink.linksdk.tools;

import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.aliyun.alink.linksdk.tools.log.ILogDispatcher;
import com.aliyun.alink.linksdk.tools.log.TLogHelper;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class ALog {
    public static final byte LEVEL_DEBUG = 1;
    public static final byte LEVEL_ERROR = 4;
    public static final byte LEVEL_INFO = 2;
    public static final byte LEVEL_WARNING = 3;

    /* renamed from: a, reason: collision with root package name */
    private static byte f11444a = 3;

    /* renamed from: b, reason: collision with root package name */
    private static byte f11445b = 4;

    /* renamed from: c, reason: collision with root package name */
    private static final int f11446c = 8;

    /* renamed from: d, reason: collision with root package name */
    private static ILogDispatcher f11447d;

    private static void a(int i2, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ILogDispatcher iLogDispatcher = f11447d;
        if (iLogDispatcher != null) {
            iLogDispatcher.log(i2, str, str2);
        }
        if (f11444a + 2 > i2) {
            return;
        }
        TLogHelper.printToTLog(i2, str, str2);
        if (6 < i2) {
            i2 = 6;
        }
        if (3 > i2) {
            i2 = 3;
        }
        if (str == null) {
            print(i2, str, str2);
        } else {
            print(i2, str, str2);
        }
    }

    public static void d(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(3, str, str2);
    }

    public static void e(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(6, str, str2);
    }

    public static byte getLevel() {
        return f11444a;
    }

    public static byte getUploadLevel() {
        return f11445b;
    }

    public static void i(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(4, str, str2);
    }

    public static void llog(byte b2, String str, String str2) {
        if (f11444a + 2 <= b2 && str2 != null) {
            int length = str2.length();
            int i2 = 0;
            while (i2 < length - 900) {
                int i3 = i2 + TypedValues.Custom.TYPE_INT;
                a(b2, str, str2.substring(i2, i3));
                i2 = i3;
            }
            a(b2, str, str2.substring(i2));
        }
    }

    public static void logBA(byte b2, String str, byte[] bArr) {
        ILogDispatcher iLogDispatcher;
        if (f11444a + 2 <= b2 && (iLogDispatcher = f11447d) != null) {
            iLogDispatcher.log(b2, str, bArr);
        }
    }

    public static String makeLogTag(String str) {
        int length = str.length();
        int i2 = f11446c;
        if (length > 36 - i2) {
            return "linksdk_" + str.substring(0, 35 - i2);
        }
        return "linksdk_" + str;
    }

    public static void print(int i2, String str, String str2) {
        if (str2 == null || "".equals(str2)) {
            return;
        }
        Log.println(i2, str, str2);
    }

    public static void setLevel(byte b2) {
        f11444a = b2;
    }

    public static void setLogDispatcher(ILogDispatcher iLogDispatcher) {
        f11447d = iLogDispatcher;
    }

    public static void setUploadLevel(byte b2) {
        Log.d("ALog", "setUploadLevel uploadLevel:" + ((int) b2));
        if (b2 < 2) {
            b2 = 2;
        }
        f11445b = b2;
    }

    public static void w(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(5, str, str2);
    }

    public static void e(String str, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        if (str2 == null) {
            str2 = "";
        }
        sb.append(str2);
        sb.append(" ERROR: ");
        sb.append(str3);
        a(6, str, sb.toString());
    }

    public static void e(String str, String str2, Exception exc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (exc != null) {
            StringBuilder sb = new StringBuilder();
            if (str2 == null) {
                str2 = "";
            }
            sb.append(str2);
            sb.append(" EXCEPTION: ");
            sb.append(exc.getMessage());
            a(6, str, sb.toString());
            exc.printStackTrace();
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        if (str2 == null) {
            str2 = "";
        }
        sb2.append(str2);
        sb2.append(" EXCEPTION: unknown");
        a(6, str, sb2.toString());
    }

    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }
}
