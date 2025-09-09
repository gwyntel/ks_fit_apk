package com.taobao.accs.antibrush;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.alipay.sdk.m.u.i;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.client.GlobalConfig;
import com.taobao.accs.utl.ALog;
import java.util.StringTokenizer;

/* loaded from: classes4.dex */
public class b {
    public static final String KEY_SEC = "sec";
    public static final String TAG = "CookieMgr";

    /* renamed from: a, reason: collision with root package name */
    public static CookieManager f20055a = null;

    /* renamed from: b, reason: collision with root package name */
    private static volatile boolean f20056b = false;

    public static synchronized void a(Context context) {
        try {
        } finally {
        }
        if (!GlobalConfig.enableCookie) {
            ALog.i(TAG, "disable cookie", new Object[0]);
        } else {
            if (f20056b) {
                return;
            }
            CookieManager cookieManager = CookieManager.getInstance();
            f20055a = cookieManager;
            cookieManager.setAcceptCookie(true);
            f20056b = true;
        }
    }

    public static String b(String str) {
        String strC = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, i.f9802b);
        do {
            try {
                String strNextToken = stringTokenizer.nextToken();
                int iIndexOf = strNextToken.indexOf(61);
                if (iIndexOf == -1) {
                    throw new IllegalArgumentException("Invalid cookie name-value pair");
                }
                String strTrim = strNextToken.substring(0, iIndexOf).trim();
                String strTrim2 = strNextToken.substring(iIndexOf + 1).trim();
                if (KEY_SEC.equals(strTrim)) {
                    strC = c(strTrim2);
                }
            } catch (Throwable th) {
                ALog.e(TAG, "parse", th, new Object[0]);
            }
        } while (stringTokenizer.hasMoreTokens());
        return strC;
    }

    private static String c(String str) {
        return (str == null || str.length() <= 2 || str.charAt(0) != '\"' || str.charAt(str.length() - 1) != '\"') ? (str == null || str.length() <= 2 || str.charAt(0) != '\'' || str.charAt(str.length() - 1) != '\'') ? str : str.substring(1, str.length() - 1) : str.substring(1, str.length() - 1);
    }

    private static boolean a() {
        Context context;
        if (!f20056b && (context = GlobalClientInfo.f20065a) != null) {
            a(context);
        }
        return f20056b;
    }

    public static synchronized String a(String str) {
        String strB = null;
        if (!a()) {
            ALog.e(TAG, "cookieMgr not setup", new Object[0]);
            return null;
        }
        try {
            strB = b(f20055a.getCookie(str));
        } catch (Throwable th) {
            ALog.e(TAG, "get cookie failed. url=" + str, th, new Object[0]);
        }
        return strB;
    }
}
