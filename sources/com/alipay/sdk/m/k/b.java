package com.alipay.sdk.m.k;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import com.alipay.sdk.m.k.a;
import com.alipay.sdk.m.u.c;
import com.alipay.sdk.m.u.e;
import com.alipay.sdk.m.u.n;
import com.huawei.hms.framework.common.ContainerUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class b {
    public static final String A = "SSLDenied";
    public static final String A0 = "app_id";
    public static final String B = "H5PayDataAnalysisError";
    public static final String C = "H5AuthDataAnalysisError";
    public static final String D = "PublicKeyUnmatch";
    public static final String E = "ClientBindFailed";
    public static final String F = "TriDesEncryptError";
    public static final String G = "TriDesDecryptError";
    public static final String H = "ClientBindException";
    public static final String I = "SaveTradeTokenError";
    public static final String J = "ClientBindServiceFailed";
    public static final String K = "TryStartServiceEx";
    public static final String L = "BindWaitTimeoutEx";
    public static final String M = "CheckClientExistEx";
    public static final String N = "CheckClientSignEx";
    public static final String O = "GetInstalledAppEx";
    public static final String P = "ParserTidClientKeyEx";
    public static final String Q = "PgApiInvoke";
    public static final String R = "PgBindStarting";
    public static final String S = "PgBinded";
    public static final String T = "PgBindEnd";
    public static final String U = "PgBindPay";
    public static final String V = "PgReturn";
    public static final String W = "PgReturnV";
    public static final String X = "PgWltVer";
    public static final String Y = "PgOpenStarting";
    public static final String Z = "ErrIntentEx";

    /* renamed from: a0, reason: collision with root package name */
    public static final String f9352a0 = "ErrActNull";

    /* renamed from: b0, reason: collision with root package name */
    public static final String f9353b0 = "ErrActNull";

    /* renamed from: c0, reason: collision with root package name */
    public static final String f9354c0 = "GetInstalledAppEx";

    /* renamed from: d0, reason: collision with root package name */
    public static final String f9355d0 = "StartLaunchAppTransEx";

    /* renamed from: e0, reason: collision with root package name */
    public static final String f9356e0 = "CheckLaunchAppExistEx";

    /* renamed from: f0, reason: collision with root package name */
    public static final String f9357f0 = "LogBindCalledH5";

    /* renamed from: g0, reason: collision with root package name */
    public static final String f9358g0 = "LogCalledH5";

    /* renamed from: h0, reason: collision with root package name */
    public static final String f9359h0 = "LogHkLoginByIntent";

    /* renamed from: i0, reason: collision with root package name */
    public static final String f9360i0 = "SchemePayWrongHashEx";

    /* renamed from: j0, reason: collision with root package name */
    public static final String f9361j0 = "LogAppFetchConfigTimeout";

    /* renamed from: k, reason: collision with root package name */
    public static final String f9362k = "net";

    /* renamed from: k0, reason: collision with root package name */
    public static final String f9363k0 = "H5CbUrlEmpty";

    /* renamed from: l, reason: collision with root package name */
    public static final String f9364l = "biz";

    /* renamed from: l0, reason: collision with root package name */
    public static final String f9365l0 = "H5CbEx";

    /* renamed from: m, reason: collision with root package name */
    public static final String f9366m = "cp";

    /* renamed from: m0, reason: collision with root package name */
    public static final String f9367m0 = "StartActivityEx";

    /* renamed from: n, reason: collision with root package name */
    public static final String f9368n = "auth";

    /* renamed from: n0, reason: collision with root package name */
    public static final String f9369n0 = "JSONEx";

    /* renamed from: o, reason: collision with root package name */
    public static final String f9370o = "third";

    /* renamed from: o0, reason: collision with root package name */
    public static final String f9371o0 = "ParseBundleSerializableError";

    /* renamed from: p, reason: collision with root package name */
    public static final String f9372p = "wlt";

    /* renamed from: p0, reason: collision with root package name */
    public static final String f9373p0 = "ParseSchemeQueryError";

    /* renamed from: q, reason: collision with root package name */
    public static final String f9374q = "FormatResultEx";

    /* renamed from: q0, reason: collision with root package name */
    public static final String f9375q0 = "TbChk";

    /* renamed from: r, reason: collision with root package name */
    public static final String f9376r = "GetApdidEx";

    /* renamed from: r0, reason: collision with root package name */
    public static final String f9377r0 = "TbStart";

    /* renamed from: s, reason: collision with root package name */
    public static final String f9378s = "GetApdidNull";

    /* renamed from: s0, reason: collision with root package name */
    public static final String f9379s0 = "TbCancel";

    /* renamed from: t, reason: collision with root package name */
    public static final String f9380t = "GetApdidTimeout";

    /* renamed from: t0, reason: collision with root package name */
    public static final String f9381t0 = "TbUnknown";

    /* renamed from: u, reason: collision with root package name */
    public static final String f9382u = "GetUtdidEx";

    /* renamed from: u0, reason: collision with root package name */
    public static final String f9383u0 = "TbOk";

    /* renamed from: v, reason: collision with root package name */
    public static final String f9384v = "GetPackageInfoEx";

    /* renamed from: v0, reason: collision with root package name */
    public static final String f9385v0 = "TbActFail";

    /* renamed from: w, reason: collision with root package name */
    public static final String f9386w = "NotIncludeSignatures";

    /* renamed from: w0, reason: collision with root package name */
    public static final String f9387w0 = "partner";

    /* renamed from: x, reason: collision with root package name */
    public static final String f9388x = "GetPublicKeyFromSignEx";

    /* renamed from: x0, reason: collision with root package name */
    public static final String f9389x0 = "out_trade_no";

    /* renamed from: y, reason: collision with root package name */
    public static final String f9390y = "webError";

    /* renamed from: y0, reason: collision with root package name */
    public static final String f9391y0 = "trade_no";

    /* renamed from: z, reason: collision with root package name */
    public static final String f9392z = "SSLError";
    public static final String z0 = "biz_content";

    /* renamed from: a, reason: collision with root package name */
    public String f9393a;

    /* renamed from: b, reason: collision with root package name */
    public String f9394b;

    /* renamed from: c, reason: collision with root package name */
    public String f9395c;

    /* renamed from: d, reason: collision with root package name */
    public String f9396d;

    /* renamed from: e, reason: collision with root package name */
    public String f9397e;

    /* renamed from: f, reason: collision with root package name */
    public String f9398f;

    /* renamed from: g, reason: collision with root package name */
    public String f9399g;

    /* renamed from: h, reason: collision with root package name */
    public String f9400h = "";

    /* renamed from: i, reason: collision with root package name */
    public String f9401i = "";

    /* renamed from: j, reason: collision with root package name */
    public String f9402j;

    public b(Context context, boolean z2) {
        context = context != null ? context.getApplicationContext() : context;
        this.f9393a = b();
        this.f9395c = a(context);
        this.f9396d = a(z2 ? 0L : a.e.a(context));
        this.f9397e = a();
        this.f9398f = b(context);
        this.f9399g = Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        this.f9402j = Constants.ACCEPT_TIME_SEPARATOR_SERVER;
    }

    private synchronized void c(String str, String str2, String str3) {
        try {
            e.d(com.alipay.sdk.m.l.a.f9433z, String.format("event %s %s %s", str, str2, str3));
            String str4 = "";
            if (!TextUtils.isEmpty(this.f9400h)) {
                str4 = "^";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str4);
            sb.append(String.format("%s,%s,%s,-,-,-,-,-,-,-,-,-,-,%s", TextUtils.isEmpty(str) ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : c(str), c(str2), c(str3), c(c())));
            this.f9400h += sb.toString();
        } catch (Throwable th) {
            throw th;
        }
    }

    private boolean d() {
        return TextUtils.isEmpty(this.f9401i);
    }

    public static String e() {
        try {
            return UUID.randomUUID().toString();
        } catch (Throwable unused) {
            return "12345678uuid";
        }
    }

    public void a(String str, String str2, Throwable th) {
        d(str, str2, a(th));
    }

    public void b(String str, String str2, String str3) {
        d(str, str2, str3);
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String b() {
        return String.format("%s,%s", e(), new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
    }

    private synchronized void d(String str, String str2, String str3) {
        try {
            e.c(com.alipay.sdk.m.l.a.f9433z, String.format("err %s %s %s", str, str2, str3));
            String str4 = "";
            if (!TextUtils.isEmpty(this.f9401i)) {
                str4 = "^";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str4);
            sb.append(String.format("%s,%s,%s,%s", str, str2, TextUtils.isEmpty(str3) ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : c(str3), c(c())));
            this.f9401i += sb.toString();
        } catch (Throwable th) {
            throw th;
        }
    }

    public void a(String str, String str2, Throwable th, String str3) {
        d(str, str2, str3 + ": " + a(th));
    }

    public static String b(String str) {
        String string;
        String strReplace;
        if (str == null) {
            str = "";
        }
        String[] strArrSplit = str.split("&");
        String strReplace2 = null;
        if (strArrSplit != null) {
            string = null;
            strReplace = null;
            for (String str2 : strArrSplit) {
                String[] strArrSplit2 = str2.split(ContainerUtils.KEY_VALUE_DELIMITER);
                if (strArrSplit2 != null && strArrSplit2.length == 2) {
                    if (strArrSplit2[0].equalsIgnoreCase(f9387w0)) {
                        strReplace2 = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(f9389x0)) {
                        string = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(f9391y0)) {
                        strReplace = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(z0)) {
                        try {
                            JSONObject jSONObject = new JSONObject(n.e(com.alipay.sdk.m.s.a.f(), strArrSplit2[1]));
                            if (TextUtils.isEmpty(string)) {
                                string = jSONObject.getString(f9389x0);
                            }
                        } catch (Throwable unused) {
                        }
                    } else if (strArrSplit2[0].equalsIgnoreCase("app_id") && TextUtils.isEmpty(strReplace2)) {
                        strReplace2 = strArrSplit2[1];
                    }
                }
            }
        } else {
            string = null;
            strReplace = null;
        }
        return String.format("%s,%s,-,%s,-,-,-", c(strReplace), c(string), c(strReplace2));
    }

    public void a(String str, String str2, String str3) {
        c("", str, str2 + "|" + str3);
    }

    public void a(String str, String str2) {
        c("", str, str2);
    }

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(th.getClass().getName());
            stringBuffer.append(":");
            stringBuffer.append(th.getMessage());
            stringBuffer.append(" 》 ");
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null) {
                int i2 = 0;
                for (StackTraceElement stackTraceElement : stackTrace) {
                    stringBuffer.append(stackTraceElement.toString());
                    stringBuffer.append(" 》 ");
                    i2++;
                    if (i2 > 5) {
                        break;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return stringBuffer.toString();
    }

    public static String d(String str) {
        return TextUtils.isEmpty(str) ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : str;
    }

    public static String c() {
        return new SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault()).format(new Date());
    }

    public static String c(String str) {
        return TextUtils.isEmpty(str) ? "" : str.replace("[", "【").replace("]", "】").replace("(", "（").replace(")", "）").replace(",", "，").replace("^", Constants.WAVE_SEPARATOR).replace(MqttTopic.MULTI_LEVEL_WILDCARD, "＃");
    }

    public String a(String str) {
        String strB = b(str);
        this.f9394b = strB;
        return String.format("[(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s)]", this.f9393a, strB, this.f9395c, this.f9396d, this.f9397e, this.f9398f, this.f9399g, d(this.f9400h), d(this.f9401i), this.f9402j);
    }

    public static String a(Context context) {
        String packageName;
        String str = Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        if (context != null) {
            try {
                Context applicationContext = context.getApplicationContext();
                packageName = applicationContext.getPackageName();
                try {
                    PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(packageName, 64);
                    str = packageInfo.versionName + "|" + a(packageInfo);
                } catch (Throwable unused) {
                }
            } catch (Throwable unused2) {
            }
        } else {
            packageName = Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        }
        return String.format("%s,%s,-,-,-", c(packageName), c(str));
    }

    public static String a(PackageInfo packageInfo) {
        Signature[] signatureArr;
        String strA;
        if (packageInfo == null || (signatureArr = packageInfo.signatures) == null || signatureArr.length == 0) {
            return "0";
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(packageInfo.signatures.length);
            for (Signature signature : packageInfo.signatures) {
                try {
                    strA = n.a((com.alipay.sdk.m.s.a) null, signature.toByteArray());
                } catch (Throwable unused) {
                }
                String strSubstring = TextUtils.isEmpty(strA) ? "?" : n.g(strA).substring(0, 8);
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(strSubstring);
            }
            return sb.toString();
        } catch (Throwable unused2) {
            return "?";
        }
    }

    public static String b(Context context) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,-", c(c.c(context)), "android", c(Build.VERSION.RELEASE), c(Build.MODEL), Constants.ACCEPT_TIME_SEPARATOR_SERVER, "0", c(c.d(context).b()), "gw", c(com.alipay.sdk.m.w.b.b(null, context)));
    }

    public static String a(long j2) {
        return String.format("android,3,%s,%s,com.alipay.mcpay,5.0,-,%s,-", c("15.8.10"), c("h.a.3.8.10"), Constants.WAVE_SEPARATOR + j2);
    }

    public static String a() {
        return String.format("%s,%s,-,-,-", c(com.alipay.sdk.m.t.a.a(com.alipay.sdk.m.s.b.d().b()).d()), c(com.alipay.sdk.m.s.b.d().c()));
    }
}
