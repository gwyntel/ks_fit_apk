package com.meizu.cloud.pushsdk.f.g;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f19680a = "e";

    public static long a(String str) {
        long j2;
        long j3 = 0;
        int i2 = 0;
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt <= 127) {
                j2 = 1;
            } else if (cCharAt <= 2047) {
                j2 = 2;
            } else {
                if (cCharAt >= 55296 && cCharAt <= 57343) {
                    j3 += 4;
                    i2++;
                } else if (cCharAt < 65535) {
                    j2 = 3;
                } else {
                    j3 += 4;
                }
                i2++;
            }
            j3 += j2;
            i2++;
        }
        return j3;
    }

    @TargetApi(19)
    public static Point b(Context context) throws NoSuchMethodException, SecurityException {
        WindowManager windowManager;
        Point point = new Point();
        Display defaultDisplay = null;
        try {
            windowManager = (WindowManager) context.getSystemService("window");
        } catch (Exception unused) {
            String str = f19680a;
            c.b(str, "Display.getSize isn't available on older devices.", new Object[0]);
            if (defaultDisplay != null) {
                point.x = defaultDisplay.getWidth();
                point.y = defaultDisplay.getHeight();
            } else {
                c.b(str, "error get display", new Object[0]);
            }
        }
        if (windowManager == null) {
            return null;
        }
        defaultDisplay = windowManager.getDefaultDisplay();
        Display.class.getMethod("getSize", Point.class);
        defaultDisplay.getSize(point);
        return point;
    }

    public static String c(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return telephonyManager.getSimOperator();
            }
        } catch (Exception e2) {
            c.b(f19680a, "getOperator error " + e2.getMessage(), new Object[0]);
        }
        return null;
    }

    public static boolean d(Context context) {
        try {
            String str = f19680a;
            c.c(str, "Checking tracker internet connectivity.", new Object[0]);
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            boolean z2 = activeNetworkInfo != null && activeNetworkInfo.isConnected();
            c.a(str, "Tracker connection online: %s", Boolean.valueOf(z2));
            return z2;
        } catch (Exception e2) {
            c.b(f19680a, "Security exception checking connection: %s", e2.toString());
            return true;
        }
    }

    private static Object a(Object obj) {
        return obj;
    }

    public static String b() {
        return Long.toString(System.currentTimeMillis());
    }

    public static String a() {
        return UUID.randomUUID().toString();
    }

    public static String a(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return telephonyManager.getNetworkOperatorName();
            }
            return null;
        } catch (Exception e2) {
            c.b(f19680a, "getCarrier: %s", e2.toString());
            return null;
        }
    }

    public static JSONObject a(Map map) {
        return new JSONObject(map);
    }

    public static boolean a(long j2, long j3, long j4) {
        return j2 > j3 - j4;
    }
}
