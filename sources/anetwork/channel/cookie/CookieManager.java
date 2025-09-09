package anetwork.channel.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.http.NetworkSdkSetting;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CookieManager {
    public static final String TAG = "anet.CookieManager";

    /* renamed from: a, reason: collision with root package name */
    private static volatile boolean f7168a = false;

    /* renamed from: b, reason: collision with root package name */
    private static android.webkit.CookieManager f7169b = null;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f7170c = true;

    /* renamed from: d, reason: collision with root package name */
    private static a f7171d;

    /* renamed from: e, reason: collision with root package name */
    private static SharedPreferences f7172e;

    /* JADX INFO: Access modifiers changed from: private */
    static class a {

        /* renamed from: a, reason: collision with root package name */
        String f7173a;

        /* renamed from: b, reason: collision with root package name */
        String f7174b;

        /* renamed from: c, reason: collision with root package name */
        String f7175c;

        /* renamed from: d, reason: collision with root package name */
        String f7176d;

        /* renamed from: e, reason: collision with root package name */
        long f7177e;

        a(String str) {
            this.f7173a = str;
            String string = CookieManager.f7172e.getString("networksdk_cookie_monitor", null);
            if (TextUtils.isEmpty(string)) {
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(string);
                if (!TextUtils.isEmpty(this.f7173a) && this.f7173a.equals(jSONObject.getString("cookieName"))) {
                    this.f7177e = jSONObject.getLong("time");
                    if (System.currentTimeMillis() - this.f7177e < 86400000) {
                        this.f7174b = jSONObject.getString("cookieText");
                        this.f7175c = jSONObject.getString("setCookie");
                        this.f7176d = jSONObject.getString("domain");
                    } else {
                        this.f7177e = 0L;
                        CookieManager.f7172e.edit().remove("networksdk_cookie_monitor").apply();
                    }
                }
            } catch (JSONException e2) {
                ALog.e(CookieManager.TAG, "cookie json parse error.", null, e2, new Object[0]);
            }
        }

        void a() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("cookieName", this.f7173a);
                jSONObject.put("cookieText", this.f7174b);
                jSONObject.put("setCookie", this.f7175c);
                long jCurrentTimeMillis = System.currentTimeMillis();
                this.f7177e = jCurrentTimeMillis;
                jSONObject.put("time", jCurrentTimeMillis);
                jSONObject.put("domain", this.f7176d);
                CookieManager.f7172e.edit().putString("networksdk_cookie_monitor", jSONObject.toString()).apply();
            } catch (Exception e2) {
                ALog.e(CookieManager.TAG, "cookie json save error.", null, e2, new Object[0]);
            }
        }
    }

    private static boolean d() {
        if (!f7168a && NetworkSdkSetting.getContext() != null) {
            setup(NetworkSdkSetting.getContext());
        }
        return f7168a;
    }

    private static void e() {
        ThreadPoolExecutorFactory.submitCookieMonitor(new anetwork.channel.cookie.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String f() {
        SharedPreferences sharedPreferences = f7172e;
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString("networksdk_target_cookie_name", null);
    }

    public static synchronized String getCookie(String str) {
        String cookie = null;
        if (!NetworkConfigCenter.isCookieEnable()) {
            return null;
        }
        if (!d() || !f7170c) {
            return null;
        }
        try {
            cookie = f7169b.getCookie(str);
        } catch (Throwable th) {
            ALog.e(TAG, "get cookie failed. url=" + str, null, th, new Object[0]);
        }
        a(str, cookie);
        return cookie;
    }

    public static synchronized void setCookie(String str, String str2) {
        if (NetworkConfigCenter.isCookieEnable()) {
            if (d() && f7170c) {
                try {
                    f7169b.setCookie(str, str2);
                    f7169b.flush();
                } catch (Throwable th) {
                    ALog.e(TAG, "set cookie failed.", null, th, "url", str, "cookies", str2);
                }
            }
        }
    }

    public static void setTargetMonitorCookieName(String str) {
        SharedPreferences sharedPreferences;
        if (str == null || (sharedPreferences = f7172e) == null) {
            return;
        }
        sharedPreferences.edit().putString("networksdk_target_cookie_name", str).apply();
    }

    public static synchronized void setup(Context context) {
        if (NetworkConfigCenter.isCookieEnable()) {
            if (f7168a) {
                return;
            }
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
                f7169b = cookieManager;
                cookieManager.setAcceptCookie(true);
                f7172e = PreferenceManager.getDefaultSharedPreferences(context);
                e();
                ALog.e(TAG, "CookieManager setup.", null, "cost", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
            } catch (Throwable th) {
                f7170c = false;
                ALog.e(TAG, "Cookie Manager setup failed!!!", null, th, new Object[0]);
            }
            f7168a = true;
        }
    }

    private static void a(String str) {
        ThreadPoolExecutorFactory.submitCookieMonitor(new b(str));
    }

    private static void a(String str, String str2) {
        ThreadPoolExecutorFactory.submitCookieMonitor(new c(str, str2));
    }

    public static void setCookie(String str, Map<String, List<String>> map) {
        if (!NetworkConfigCenter.isCookieEnable() || str == null || map == null) {
            return;
        }
        try {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key != null && (key.equalsIgnoreCase("Set-Cookie") || key.equalsIgnoreCase("Set-Cookie2"))) {
                    for (String str2 : entry.getValue()) {
                        setCookie(str, str2);
                        a(str2);
                    }
                }
            }
        } catch (Exception e2) {
            ALog.e(TAG, "set cookie failed", null, e2, "url", str, "\nheaders", map);
        }
    }
}
