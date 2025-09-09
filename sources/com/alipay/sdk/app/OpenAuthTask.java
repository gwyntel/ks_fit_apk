package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import anetwork.channel.util.RequestConstant;
import com.alipay.sdk.m.m.a;
import com.alipay.sdk.m.s.a;
import com.alipay.sdk.m.u.e;
import com.alipay.sdk.m.u.n;
import com.facebook.share.internal.MessengerShareContentUtility;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class OpenAuthTask {
    public static final int Duplex = 5000;
    public static final int NOT_INSTALLED = 4001;
    public static final int OK = 9000;
    public static final int SYS_ERR = 4000;

    /* renamed from: e, reason: collision with root package name */
    public static final Map<String, Callback> f9122e = new ConcurrentHashMap();

    /* renamed from: f, reason: collision with root package name */
    public static long f9123f = -1;

    /* renamed from: g, reason: collision with root package name */
    public static final int f9124g = 122;

    /* renamed from: b, reason: collision with root package name */
    public final Activity f9126b;

    /* renamed from: c, reason: collision with root package name */
    public Callback f9127c;

    /* renamed from: a, reason: collision with root package name */
    public volatile boolean f9125a = false;

    /* renamed from: d, reason: collision with root package name */
    public final Handler f9128d = new Handler(Looper.getMainLooper());

    public enum BizType {
        Invoice("20000920"),
        AccountAuth("20000067"),
        Deduct("60000157");

        public String appId;

        BizType(String str) {
            this.appId = str;
        }
    }

    public interface Callback {
        void onResult(int i2, String str, Bundle bundle);
    }

    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f9129a;

        static {
            int[] iArr = new int[BizType.values().length];
            f9129a = iArr;
            try {
                iArr[BizType.Deduct.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f9129a[BizType.AccountAuth.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f9129a[BizType.Invoice.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public final class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final int f9130a;

        /* renamed from: b, reason: collision with root package name */
        public final String f9131b;

        /* renamed from: c, reason: collision with root package name */
        public final Bundle f9132c;

        public /* synthetic */ b(OpenAuthTask openAuthTask, int i2, String str, Bundle bundle, a aVar) {
            this(i2, str, bundle);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (OpenAuthTask.this.f9127c != null) {
                OpenAuthTask.this.f9127c.onResult(this.f9130a, this.f9131b, this.f9132c);
            }
        }

        public b(int i2, String str, Bundle bundle) {
            this.f9130a = i2;
            this.f9131b = str;
            this.f9132c = bundle;
        }
    }

    public OpenAuthTask(Activity activity) {
        this.f9126b = activity;
        com.alipay.sdk.m.s.b.d().a(activity);
    }

    public void execute(String str, BizType bizType, Map<String, String> map, Callback callback, boolean z2) {
        com.alipay.sdk.m.s.a aVar = new com.alipay.sdk.m.s.a(this.f9126b, String.valueOf(map), "oa-" + bizType);
        this.f9127c = callback;
        if (a(aVar, str, bizType, map, z2)) {
            com.alipay.sdk.m.k.a.b(this.f9126b, aVar, "", aVar.f9727d);
        }
    }

    private boolean a(com.alipay.sdk.m.s.a aVar, String str, BizType bizType, Map<String, String> map, boolean z2) {
        PackageInfo packageInfo;
        String strA;
        if (this.f9125a) {
            this.f9128d.post(new b(this, SYS_ERR, "该 OpenAuthTask 已在执行", null, null));
            return true;
        }
        this.f9125a = true;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - f9123f <= 3000) {
            this.f9128d.post(new b(this, 5000, "3s 内重复支付", null, null));
            return true;
        }
        f9123f = jElapsedRealtime;
        com.alipay.sdk.m.j.a.a("");
        String strA2 = n.a(32);
        HashMap map2 = new HashMap(map);
        map2.put("mqpPkgName", this.f9126b.getPackageName());
        map2.put("mqpScene", "sdk");
        List<a.b> listL = com.alipay.sdk.m.m.a.z().l();
        if (!com.alipay.sdk.m.m.a.z().f9518g || listL == null) {
            listL = com.alipay.sdk.m.j.a.f9316d;
        }
        n.c cVarA = n.a(aVar, this.f9126b, listL);
        if (cVarA != null && !cVarA.a(aVar) && !cVarA.a() && (packageInfo = cVarA.f9826a) != null) {
            try {
                if (packageInfo.versionCode >= 122) {
                    try {
                        HashMap<String, String> mapA = com.alipay.sdk.m.s.a.a(aVar);
                        mapA.put("ts_scheme", String.valueOf(SystemClock.elapsedRealtime()));
                        map2.put("mqpLoc", new JSONObject(mapA).toString());
                    } catch (Throwable th) {
                        com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "OpenAuthLocEx", th);
                    }
                    String strA3 = a(bizType, map2);
                    f9122e.put(strA2, this.f9127c);
                    try {
                        strA = a(jElapsedRealtime, strA2, bizType, strA3);
                    } catch (JSONException e2) {
                        com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9369n0, e2);
                        strA = null;
                    }
                    if (TextUtils.isEmpty(strA)) {
                        this.f9128d.post(new b(this, SYS_ERR, "参数错误", null, null));
                        return true;
                    }
                    Intent intent = new Intent("android.intent.action.VIEW", new Uri.Builder().scheme("alipays").authority("platformapi").path("startapp").appendQueryParameter("appId", "20001129").appendQueryParameter(MessengerShareContentUtility.ATTACHMENT_PAYLOAD, strA).build());
                    intent.addFlags(268435456);
                    intent.setPackage(cVarA.f9826a.packageName);
                    try {
                        com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.Y, "" + jElapsedRealtime);
                        a.C0055a.a(aVar, strA2);
                        this.f9126b.startActivity(intent);
                        return false;
                    } catch (Throwable th2) {
                        com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "StartWalletEx", th2);
                        return false;
                    }
                }
            } catch (Throwable unused) {
                this.f9128d.post(new b(this, SYS_ERR, "业务参数错误", null, null));
                return true;
            }
        }
        if (!z2) {
            this.f9128d.post(new b(this, 4001, "支付宝未安装或签名错误", null, null));
            return true;
        }
        map2.put("mqpScheme", String.valueOf(str));
        map2.put("mqpNotifyName", strA2);
        map2.put("mqpScene", "landing");
        String strA4 = a(bizType, map2);
        Intent intent2 = new Intent(this.f9126b, (Class<?>) H5OpenAuthActivity.class);
        intent2.putExtra("url", String.format("https://render.alipay.com/p/s/i?scheme=%s", Uri.encode(strA4)));
        a.C0055a.a(aVar, intent2);
        this.f9126b.startActivity(intent2);
        return false;
    }

    private String a(BizType bizType, Map<String, String> map) {
        if (bizType != null) {
            Uri.Builder builderAppendQueryParameter = new Uri.Builder().scheme("alipays").authority("platformapi").path("startapp").appendQueryParameter("appId", bizType.appId);
            if (a.f9129a[bizType.ordinal()] == 1) {
                builderAppendQueryParameter.appendQueryParameter("appClearTop", RequestConstant.FALSE).appendQueryParameter("startMultApp", "YES");
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builderAppendQueryParameter.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            return builderAppendQueryParameter.build().toString();
        }
        throw new RuntimeException("missing bizType");
    }

    private String a(long j2, String str, BizType bizType, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("startTime", String.valueOf(j2));
        jSONObject.put("session", str);
        jSONObject.put("package", this.f9126b.getPackageName());
        if (bizType != null) {
            jSONObject.put("appId", bizType.appId);
        }
        jSONObject.put("sdkVersion", "h.a.3.8.10");
        jSONObject.put("mqpURL", str2);
        return Base64.encodeToString(jSONObject.toString().getBytes(Charset.forName("UTF-8")), 2);
    }

    public static void a(String str, int i2, String str2, Bundle bundle) {
        Callback callbackRemove = f9122e.remove(str);
        if (callbackRemove != null) {
            try {
                callbackRemove.onResult(i2, str2, bundle);
            } catch (Throwable th) {
                e.a(th);
            }
        }
    }
}
