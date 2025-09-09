package com.tencent.bugly.crashreport;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.p;

/* loaded from: classes4.dex */
public class BuglyLog {
    public static void d(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (p.f21112c) {
            Log.d(str, str2);
        }
        ao.a("D", str, str2);
    }

    public static void e(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (p.f21112c) {
            Log.e(str, str2);
        }
        ao.a(ExifInterface.LONGITUDE_EAST, str, str2);
    }

    public static void i(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (p.f21112c) {
            Log.i(str, str2);
        }
        ao.a("I", str, str2);
    }

    public static void setCache(int i2) {
        ao.a(i2);
    }

    public static void v(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (p.f21112c) {
            Log.v(str, str2);
        }
        ao.a(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, str, str2);
    }

    public static void w(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (p.f21112c) {
            Log.w(str, str2);
        }
        ao.a(ExifInterface.LONGITUDE_WEST, str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (p.f21112c) {
            Log.e(str, str2, th);
        }
        ao.a(ExifInterface.LONGITUDE_EAST, str, th);
    }
}
