package com.alipay.sdk.m.s;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.sdk.m.u.e;
import com.alipay.sdk.m.u.n;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.internal.NativeProtocol;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.umeng.analytics.pro.bc;
import com.xiaomi.mipush.sdk.Constants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a {
    public static final String A = "new_external_info==";

    /* renamed from: k, reason: collision with root package name */
    public static final String f9708k = "\"&";

    /* renamed from: l, reason: collision with root package name */
    public static final String f9709l = "&";

    /* renamed from: m, reason: collision with root package name */
    public static final String f9710m = "bizcontext=\"";

    /* renamed from: n, reason: collision with root package name */
    public static final String f9711n = "bizcontext=";

    /* renamed from: o, reason: collision with root package name */
    public static final String f9712o = "\"";

    /* renamed from: p, reason: collision with root package name */
    public static final String f9713p = "appkey";

    /* renamed from: q, reason: collision with root package name */
    public static final String f9714q = "ty";

    /* renamed from: r, reason: collision with root package name */
    public static final String f9715r = "sv";

    /* renamed from: s, reason: collision with root package name */
    public static final String f9716s = "an";

    /* renamed from: t, reason: collision with root package name */
    public static final String f9717t = "setting";

    /* renamed from: u, reason: collision with root package name */
    public static final String f9718u = "av";

    /* renamed from: v, reason: collision with root package name */
    public static final String f9719v = "sdk_start_time";

    /* renamed from: w, reason: collision with root package name */
    public static final String f9720w = "extInfo";

    /* renamed from: x, reason: collision with root package name */
    public static final String f9721x = "ap_link_token";

    /* renamed from: y, reason: collision with root package name */
    public static final String f9722y = "act_info";

    /* renamed from: z, reason: collision with root package name */
    public static final String f9723z = "UTF-8";

    /* renamed from: a, reason: collision with root package name */
    public String f9724a;

    /* renamed from: b, reason: collision with root package name */
    public String f9725b;

    /* renamed from: c, reason: collision with root package name */
    public Context f9726c;

    /* renamed from: d, reason: collision with root package name */
    public final String f9727d;

    /* renamed from: e, reason: collision with root package name */
    public final long f9728e;

    /* renamed from: f, reason: collision with root package name */
    public final int f9729f;

    /* renamed from: g, reason: collision with root package name */
    public final String f9730g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f9731h = false;

    /* renamed from: i, reason: collision with root package name */
    public final ActivityInfo f9732i;

    /* renamed from: j, reason: collision with root package name */
    public final com.alipay.sdk.m.k.b f9733j;

    public a(Context context, String str, String str2) throws PackageManager.NameNotFoundException {
        String str3;
        this.f9724a = "";
        this.f9725b = "";
        this.f9726c = null;
        boolean zIsEmpty = TextUtils.isEmpty(str2);
        this.f9733j = new com.alipay.sdk.m.k.b(context, zIsEmpty);
        String strB = b(str, this.f9725b);
        this.f9727d = strB;
        this.f9728e = SystemClock.elapsedRealtime();
        this.f9729f = n.g();
        ActivityInfo activityInfoA = n.a(context);
        this.f9732i = activityInfoA;
        this.f9730g = str2;
        if (!zIsEmpty) {
            com.alipay.sdk.m.k.a.a(this, com.alipay.sdk.m.k.b.f9364l, "eptyp", str2 + "|" + strB);
            if (activityInfoA != null) {
                str3 = activityInfoA.name + "|" + activityInfoA.launchMode;
            } else {
                str3 = TmpConstant.GROUP_ROLE_UNKNOWN;
            }
            com.alipay.sdk.m.k.a.a(this, com.alipay.sdk.m.k.b.f9364l, "actInfo", str3);
            com.alipay.sdk.m.k.a.a(this, com.alipay.sdk.m.k.b.f9364l, "sys", n.a(this));
            com.alipay.sdk.m.k.a.a(this, com.alipay.sdk.m.k.b.f9364l, "sdkv", "ef70839-clean");
        }
        try {
            this.f9726c = context.getApplicationContext();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.f9724a = packageInfo.versionName;
            this.f9725b = packageInfo.packageName;
        } catch (Exception e2) {
            e.a(e2);
        }
        if (!zIsEmpty) {
            com.alipay.sdk.m.k.a.a(this, com.alipay.sdk.m.k.b.f9364l, bc.aN + n.g());
            com.alipay.sdk.m.k.a.a(this, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.Q, "" + SystemClock.elapsedRealtime());
            com.alipay.sdk.m.k.a.a(context, this, str, this.f9727d);
        }
        if (zIsEmpty || !com.alipay.sdk.m.m.a.z().r()) {
            return;
        }
        com.alipay.sdk.m.m.a.z().a(this, this.f9726c, true, 2);
    }

    private String d(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str.substring(19));
            jSONObject.put("bizcontext", b(jSONObject.optString("bizcontext")));
            return A + jSONObject.toString();
        } catch (Throwable unused) {
            return str;
        }
    }

    private String e(String str) {
        try {
            String strA = a(str, f9708k, f9710m);
            if (TextUtils.isEmpty(strA)) {
                return str + "&" + a(f9710m, "\"");
            }
            if (!strA.endsWith("\"")) {
                strA = strA + "\"";
            }
            int iIndexOf = str.indexOf(strA);
            return str.substring(0, iIndexOf) + b(strA, f9710m, "\"") + str.substring(iIndexOf + strA.length());
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(this, com.alipay.sdk.m.k.b.f9364l, "fmt2", th, str);
            return str;
        }
    }

    public static a f() {
        return null;
    }

    public Context a() {
        return this.f9726c;
    }

    public String b() {
        return this.f9725b;
    }

    public String c() {
        return this.f9724a;
    }

    private String b(String str, String str2, String str3) throws JSONException {
        JSONObject jSONObject;
        String strSubstring = str.substring(str2.length());
        boolean z2 = false;
        String strSubstring2 = strSubstring.substring(0, strSubstring.length() - str3.length());
        if (strSubstring2.length() >= 2 && strSubstring2.startsWith("\"") && strSubstring2.endsWith("\"")) {
            jSONObject = new JSONObject(strSubstring2.substring(1, strSubstring2.length() - 1));
            z2 = true;
        } else {
            jSONObject = new JSONObject(strSubstring2);
        }
        String strA = a(jSONObject);
        if (z2) {
            strA = "\"" + strA + "\"";
        }
        return str2 + strA + str3;
    }

    private String c(String str) {
        try {
            String strA = a(str, "&", f9711n);
            if (TextUtils.isEmpty(strA)) {
                str = str + "&" + a(f9711n, "");
            } else {
                int iIndexOf = str.indexOf(strA);
                str = str.substring(0, iIndexOf) + b(strA, f9711n, "") + str.substring(iIndexOf + strA.length());
            }
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(this, com.alipay.sdk.m.k.b.f9364l, "fmt1", th, str);
        }
        return str;
    }

    private boolean f(String str) {
        return !str.contains(f9708k);
    }

    public String a(String str) {
        return TextUtils.isEmpty(str) ? str : str.startsWith(A) ? d(str) : f(str) ? c(str) : e(str);
    }

    /* renamed from: com.alipay.sdk.m.s.a$a, reason: collision with other inner class name */
    public static final class C0055a {

        /* renamed from: a, reason: collision with root package name */
        public static final HashMap<UUID, a> f9734a = new HashMap<>();

        /* renamed from: b, reason: collision with root package name */
        public static final HashMap<String, a> f9735b = new HashMap<>();

        /* renamed from: c, reason: collision with root package name */
        public static final String f9736c = "i_uuid_b_c";

        public static void a(a aVar, Intent intent) {
            if (aVar == null || intent == null) {
                return;
            }
            UUID uuidRandomUUID = UUID.randomUUID();
            f9734a.put(uuidRandomUUID, aVar);
            intent.putExtra(f9736c, uuidRandomUUID);
        }

        public static a a(Intent intent) {
            if (intent == null) {
                return null;
            }
            Serializable serializableExtra = intent.getSerializableExtra(f9736c);
            if (serializableExtra instanceof UUID) {
                return f9734a.remove((UUID) serializableExtra);
            }
            return null;
        }

        public static void a(a aVar, String str) {
            if (aVar == null || TextUtils.isEmpty(str)) {
                return;
            }
            f9735b.put(str, aVar);
        }

        public static a a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return f9735b.remove(str);
        }
    }

    public boolean d() {
        return this.f9731h;
    }

    private String a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(str2);
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (!TextUtils.isEmpty(strArrSplit[i2]) && strArrSplit[i2].startsWith(str3)) {
                return strArrSplit[i2];
            }
        }
        return null;
    }

    private String b(String str) throws JSONException {
        return a(new JSONObject(str));
    }

    public static String b(String str, String str2) {
        try {
            Locale locale = Locale.getDefault();
            if (str == null) {
                str = "";
            }
            if (str2 == null) {
                str2 = "";
            }
            return String.format("EP%s%s_%s", "1", n.g(String.format(locale, "%s%s%d%s", str, str2, Long.valueOf(System.currentTimeMillis()), UUID.randomUUID().toString())), Long.valueOf(System.currentTimeMillis()));
        } catch (Throwable unused) {
            return Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        }
    }

    private JSONObject e() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(f9721x, this.f9727d);
        } catch (Throwable unused) {
        }
        return jSONObject;
    }

    private String a(String str, String str2) {
        return str + a(new JSONObject()) + str2;
    }

    public String a(JSONObject jSONObject) {
        String str;
        try {
            if (!jSONObject.has("appkey")) {
                jSONObject.put("appkey", com.alipay.sdk.m.l.a.f9413f);
            }
            if (!jSONObject.has(f9714q)) {
                jSONObject.put(f9714q, "and_lite");
            }
            if (!jSONObject.has(f9715r)) {
                jSONObject.put(f9715r, "h.a.3.8.10");
            }
            if (!jSONObject.has(f9716s)) {
                jSONObject.put(f9716s, this.f9725b);
            }
            if (!jSONObject.has("av")) {
                jSONObject.put("av", this.f9724a);
            }
            if (!jSONObject.has(f9719v)) {
                jSONObject.put(f9719v, System.currentTimeMillis());
            }
            if (!jSONObject.has(f9720w)) {
                jSONObject.put(f9720w, e());
            }
            if (!jSONObject.has(f9722y)) {
                if (this.f9732i != null) {
                    str = this.f9732i.name + "|" + this.f9732i.launchMode;
                } else {
                    str = TmpConstant.GROUP_ROLE_UNKNOWN;
                }
                jSONObject.put(f9722y, str);
            }
            return jSONObject.toString();
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(this, com.alipay.sdk.m.k.b.f9364l, "fmt3", th, String.valueOf(jSONObject));
            e.a(th);
            return jSONObject != null ? jSONObject.toString() : "{}";
        }
    }

    public static HashMap<String, String> a(a aVar) {
        HashMap<String, String> map = new HashMap<>();
        if (aVar != null) {
            map.put(HiAnalyticsConstant.BI_KEY_SDK_VER, "15.8.10");
            map.put(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING, aVar.f9725b);
            map.put("token", aVar.f9727d);
            map.put("call_type", aVar.f9730g);
            map.put("ts_api_invoke", String.valueOf(aVar.f9728e));
            com.alipay.sdk.m.u.a.a(aVar, map);
        }
        return map;
    }

    public void a(boolean z2) {
        this.f9731h = z2;
    }
}
