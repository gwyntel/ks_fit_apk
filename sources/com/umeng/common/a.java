package com.umeng.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.pro.ay;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.utils.UMUtils;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static String f22079a = null;

    /* renamed from: b, reason: collision with root package name */
    private static final String f22080b = "umeng+";

    /* renamed from: c, reason: collision with root package name */
    private static final String f22081c = "ek__id";

    /* renamed from: d, reason: collision with root package name */
    private static final String f22082d = "ek_key";

    /* renamed from: e, reason: collision with root package name */
    private static String f22083e = "";

    /* renamed from: f, reason: collision with root package name */
    private static final String f22084f = ay.b().b(ay.f21379n);

    /* renamed from: g, reason: collision with root package name */
    private static String f22085g = "";

    /* renamed from: h, reason: collision with root package name */
    private static a f22086h;

    private a() {
    }

    public static a a() {
        if (f22086h == null) {
            synchronized (a.class) {
                try {
                    if (f22086h == null) {
                        f22086h = new a();
                    }
                } finally {
                }
            }
        }
        return f22086h;
    }

    private String c(String str) {
        String string = "";
        try {
            String strSubstring = str.substring(1, 9);
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < strSubstring.length(); i2++) {
                char cCharAt = strSubstring.charAt(i2);
                if (!Character.isDigit(cCharAt)) {
                    sb.append(cCharAt);
                } else if (Integer.parseInt(Character.toString(cCharAt)) == 0) {
                    sb.append(0);
                } else {
                    sb.append(10 - Integer.parseInt(Character.toString(cCharAt)));
                }
            }
            string = sb.toString();
            return string + new StringBuilder(string).reverse().toString();
        } catch (Throwable unused) {
            return string;
        }
    }

    public String b(String str) {
        String str2;
        String str3;
        try {
            return TextUtils.isEmpty(f22079a) ? str : new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), f22079a.getBytes()));
        } catch (Exception unused) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败!");
            String str4 = null;
            if (TextUtils.isEmpty(f22083e)) {
                return null;
            }
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，换老秘钥重试");
            try {
                String str5 = new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), f22083e.getBytes()));
                try {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，换老秘钥重试成功。");
                    return str5;
                } catch (Exception unused2) {
                    str4 = str5;
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，换老秘钥重试失败。换子进程备份key重试。");
                    try {
                        str3 = new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), f22085g.getBytes()));
                    } catch (Throwable unused3) {
                        str2 = str4;
                    }
                    try {
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，子进程备份key重试成功。");
                        return str3;
                    } catch (Throwable unused4) {
                        str2 = str3;
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，子进程备份key重试失败。");
                        return str2;
                    }
                }
            } catch (Exception unused5) {
            }
        }
    }

    public void a(Context context) {
        try {
            if (TextUtils.isEmpty(f22079a)) {
                String multiProcessSP = UMUtils.getMultiProcessSP(context, f22081c);
                if (!TextUtils.isEmpty(multiProcessSP)) {
                    f22083e = c(multiProcessSP);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>> primaryKey: " + f22083e);
                }
                SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(f22084f, 0);
                if (sharedPreferences != null) {
                    f22085g = sharedPreferences.getString(f22081c, null);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程备份秘钥：主进程key: " + f22085g);
                }
                f22079a = c(UMUtils.genId());
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>> 正式秘钥：key: " + f22079a);
            }
        } catch (Throwable unused) {
        }
    }

    public String a(String str) {
        try {
            return TextUtils.isEmpty(f22079a) ? str : Base64.encodeToString(DataHelper.encrypt(str.getBytes(), f22079a.getBytes()), 0);
        } catch (Exception unused) {
            return null;
        }
    }
}
