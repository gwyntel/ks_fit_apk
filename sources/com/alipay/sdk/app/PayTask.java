package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import com.alipay.sdk.app.PayResultActivity;
import com.alipay.sdk.m.m.a;
import com.alipay.sdk.m.s.a;
import com.alipay.sdk.m.u.e;
import com.alipay.sdk.m.u.h;
import com.alipay.sdk.m.u.i;
import com.alipay.sdk.m.u.l;
import com.alipay.sdk.m.u.n;
import com.alipay.sdk.util.H5PayResultModel;
import com.facebook.internal.NativeProtocol;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PayTask {

    /* renamed from: h, reason: collision with root package name */
    public static final Object f9146h = h.class;

    /* renamed from: i, reason: collision with root package name */
    public static long f9147i;

    /* renamed from: a, reason: collision with root package name */
    public Activity f9148a;

    /* renamed from: b, reason: collision with root package name */
    public com.alipay.sdk.m.x.a f9149b;

    /* renamed from: c, reason: collision with root package name */
    public final String f9150c = "wappaygw.alipay.com/service/rest.htm";

    /* renamed from: d, reason: collision with root package name */
    public final String f9151d = "mclient.alipay.com/service/rest.htm";

    /* renamed from: e, reason: collision with root package name */
    public final String f9152e = "mclient.alipay.com/home/exterfaceAssign.htm";

    /* renamed from: f, reason: collision with root package name */
    public final String f9153f = "mclient.alipay.com/cashier/mobilepay.htm";

    /* renamed from: g, reason: collision with root package name */
    public Map<String, c> f9154g = new HashMap();

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f9155a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f9156b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ H5PayCallback f9157c;

        public a(String str, boolean z2, H5PayCallback h5PayCallback) {
            this.f9155a = str;
            this.f9156b = z2;
            this.f9157c = h5PayCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            H5PayResultModel h5PayResultModelH5Pay = PayTask.this.h5Pay(new com.alipay.sdk.m.s.a(PayTask.this.f9148a, this.f9155a, "payInterceptorWithUrl"), this.f9155a, this.f9156b);
            e.d(com.alipay.sdk.m.l.a.f9433z, "inc finished: " + h5PayResultModelH5Pay.getResultCode());
            this.f9157c.onPayResult(h5PayResultModelH5Pay);
        }
    }

    public class b implements h.e {
        public b() {
        }

        @Override // com.alipay.sdk.m.u.h.e
        public void a() {
            PayTask.this.dismissLoading();
        }

        @Override // com.alipay.sdk.m.u.h.e
        public void b() {
        }
    }

    public class c {

        /* renamed from: a, reason: collision with root package name */
        public String f9160a;

        /* renamed from: b, reason: collision with root package name */
        public String f9161b;

        /* renamed from: c, reason: collision with root package name */
        public String f9162c;

        /* renamed from: d, reason: collision with root package name */
        public String f9163d;

        public c() {
            this.f9160a = "";
            this.f9161b = "";
            this.f9162c = "";
            this.f9163d = "";
        }

        public String a() {
            return this.f9162c;
        }

        public String b() {
            return this.f9160a;
        }

        public String c() {
            return this.f9161b;
        }

        public String d() {
            return this.f9163d;
        }

        public void a(String str) {
            this.f9162c = str;
        }

        public void b(String str) {
            this.f9160a = str;
        }

        public void c(String str) {
            this.f9161b = str;
        }

        public void d(String str) {
            this.f9163d = str;
        }

        public /* synthetic */ c(PayTask payTask, a aVar) {
            this();
        }
    }

    public PayTask(Activity activity) {
        this.f9148a = activity;
        com.alipay.sdk.m.s.b.d().a(this.f9148a);
        this.f9149b = new com.alipay.sdk.m.x.a(activity, com.alipay.sdk.m.x.a.f9850j);
    }

    public static synchronized boolean fetchSdkConfig(Context context) {
        try {
            com.alipay.sdk.m.s.b.d().a(context);
            long jElapsedRealtime = SystemClock.elapsedRealtime() / 1000;
            if (jElapsedRealtime - f9147i < com.alipay.sdk.m.m.a.z().d()) {
                return false;
            }
            f9147i = jElapsedRealtime;
            com.alipay.sdk.m.m.a.z().a(com.alipay.sdk.m.s.a.f(), context.getApplicationContext(), false, 4);
            return true;
        } catch (Exception e2) {
            e.a(e2);
            return false;
        }
    }

    public void dismissLoading() {
        com.alipay.sdk.m.x.a aVar = this.f9149b;
        if (aVar != null) {
            aVar.a();
            this.f9149b = null;
        }
    }

    public synchronized String fetchOrderInfoFromH5PayUrl(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                String strTrim = str.trim();
                if (strTrim.startsWith("https://wappaygw.alipay.com/service/rest.htm") || strTrim.startsWith("http://wappaygw.alipay.com/service/rest.htm")) {
                    String strTrim2 = strTrim.replaceFirst("(http|https)://wappaygw.alipay.com/service/rest.htm\\?", "").trim();
                    if (!TextUtils.isEmpty(strTrim2)) {
                        return "_input_charset=\"utf-8\"&ordertoken=\"" + n.a("<request_token>", "</request_token>", n.b(strTrim2).get("req_data")) + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + a(this.f9148a) + "\"";
                    }
                }
                if (strTrim.startsWith("https://mclient.alipay.com/service/rest.htm") || strTrim.startsWith("http://mclient.alipay.com/service/rest.htm")) {
                    String strTrim3 = strTrim.replaceFirst("(http|https)://mclient.alipay.com/service/rest.htm\\?", "").trim();
                    if (!TextUtils.isEmpty(strTrim3)) {
                        return "_input_charset=\"utf-8\"&ordertoken=\"" + n.a("<request_token>", "</request_token>", n.b(strTrim3).get("req_data")) + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + a(this.f9148a) + "\"";
                    }
                }
                if ((strTrim.startsWith("https://mclient.alipay.com/home/exterfaceAssign.htm") || strTrim.startsWith("http://mclient.alipay.com/home/exterfaceAssign.htm")) && ((strTrim.contains("alipay.wap.create.direct.pay.by.user") || strTrim.contains("create_forex_trade_wap")) && !TextUtils.isEmpty(strTrim.replaceFirst("(http|https)://mclient.alipay.com/home/exterfaceAssign.htm\\?", "").trim()))) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("url", str);
                    jSONObject.put("bizcontext", a(this.f9148a));
                    return com.alipay.sdk.m.s.a.A + jSONObject.toString();
                }
                a aVar = null;
                if (Pattern.compile("^(http|https)://(maliprod\\.alipay\\.com/w/trade_pay\\.do.?|mali\\.alipay\\.com/w/trade_pay\\.do.?|mclient\\.alipay\\.com/w/trade_pay\\.do.?)").matcher(str).find()) {
                    String strA = n.a("?", "", str);
                    if (!TextUtils.isEmpty(strA)) {
                        Map<String, String> mapB = n.b(strA);
                        StringBuilder sb = new StringBuilder();
                        if (a(false, true, com.alipay.sdk.m.k.b.f9391y0, sb, mapB, com.alipay.sdk.m.k.b.f9391y0, "alipay_trade_no")) {
                            a(true, false, "pay_phase_id", sb, mapB, "payPhaseId", "pay_phase_id", "out_relation_id");
                            sb.append("&biz_sub_type=\"TRADE\"");
                            sb.append("&biz_type=\"trade\"");
                            String str2 = mapB.get(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING);
                            if (TextUtils.isEmpty(str2) && !TextUtils.isEmpty(mapB.get(CmcdConfiguration.KEY_CONTENT_ID))) {
                                str2 = "ali1688";
                            } else if (TextUtils.isEmpty(str2) && (!TextUtils.isEmpty(mapB.get("sid")) || !TextUtils.isEmpty(mapB.get("s_id")))) {
                                str2 = "tb";
                            }
                            sb.append("&app_name=\"" + str2 + "\"");
                            if (!a(true, true, "extern_token", sb, mapB, "extern_token", CmcdConfiguration.KEY_CONTENT_ID, "sid", "s_id")) {
                                return "";
                            }
                            a(true, false, "appenv", sb, mapB, "appenv");
                            sb.append("&pay_channel_id=\"alipay_sdk\"");
                            c cVar = new c(this, aVar);
                            cVar.b(mapB.get("return_url"));
                            cVar.c(mapB.get("show_url"));
                            cVar.a(mapB.get("pay_order_id"));
                            String str3 = sb.toString() + "&bizcontext=\"" + a(this.f9148a) + "\"";
                            this.f9154g.put(str3, cVar);
                            return str3;
                        }
                    }
                }
                if (!strTrim.startsWith("https://mclient.alipay.com/cashier/mobilepay.htm") && !strTrim.startsWith("http://mclient.alipay.com/cashier/mobilepay.htm") && (!EnvUtils.isSandBox() || !strTrim.contains("mobileclientgw.alipaydev.com/cashier/mobilepay.htm"))) {
                    if (com.alipay.sdk.m.m.a.z().h() && Pattern.compile("^https?://(maliprod\\.alipay\\.com|mali\\.alipay\\.com)/batch_payment\\.do\\?").matcher(strTrim).find()) {
                        Uri uri = Uri.parse(strTrim);
                        String queryParameter = uri.getQueryParameter("return_url");
                        String queryParameter2 = uri.getQueryParameter("show_url");
                        String queryParameter3 = uri.getQueryParameter("pay_order_id");
                        String strA2 = a(uri.getQueryParameter("trade_nos"), uri.getQueryParameter("alipay_trade_no"));
                        String strA3 = a(uri.getQueryParameter("payPhaseId"), uri.getQueryParameter("pay_phase_id"), uri.getQueryParameter("out_relation_id"));
                        String strA4 = a(uri.getQueryParameter(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING), !TextUtils.isEmpty(uri.getQueryParameter(CmcdConfiguration.KEY_CONTENT_ID)) ? "ali1688" : "", !TextUtils.isEmpty(uri.getQueryParameter("sid")) ? "tb" : "", !TextUtils.isEmpty(uri.getQueryParameter("s_id")) ? "tb" : "");
                        String strA5 = a(uri.getQueryParameter("extern_token"), uri.getQueryParameter(CmcdConfiguration.KEY_CONTENT_ID), uri.getQueryParameter("sid"), uri.getQueryParameter("s_id"));
                        String strA6 = a(uri.getQueryParameter("appenv"));
                        if (!TextUtils.isEmpty(strA2) && !TextUtils.isEmpty(strA4) && !TextUtils.isEmpty(strA5)) {
                            String str4 = String.format("trade_no=\"%s\"&pay_phase_id=\"%s\"&biz_type=\"trade\"&biz_sub_type=\"TRADE\"&app_name=\"%s\"&extern_token=\"%s\"&appenv=\"%s\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"%s\"", strA2, strA3, strA4, strA5, strA6, a(this.f9148a));
                            c cVar2 = new c(this, aVar);
                            cVar2.b(queryParameter);
                            cVar2.c(queryParameter2);
                            cVar2.a(queryParameter3);
                            cVar2.d(strA2);
                            this.f9154g.put(str4, cVar2);
                            return str4;
                        }
                    }
                }
                String strA7 = a(this.f9148a);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("url", strTrim);
                jSONObject2.put("bizcontext", strA7);
                return String.format("new_external_info==%s", jSONObject2.toString());
            }
        } catch (Throwable th) {
            e.a(th);
        }
        return "";
    }

    public synchronized String fetchTradeToken() {
        return i.a(new com.alipay.sdk.m.s.a(this.f9148a, "", "fetchTradeToken"), this.f9148a.getApplicationContext());
    }

    public String getVersion() {
        return "15.8.10";
    }

    public synchronized H5PayResultModel h5Pay(com.alipay.sdk.m.s.a aVar, String str, boolean z2) {
        H5PayResultModel h5PayResultModel;
        h5PayResultModel = new H5PayResultModel();
        try {
            String[] strArrSplit = a(aVar, str, z2).split(i.f9802b);
            HashMap map = new HashMap();
            for (String str2 : strArrSplit) {
                int iIndexOf = str2.indexOf("={");
                if (iIndexOf >= 0) {
                    String strSubstring = str2.substring(0, iIndexOf);
                    map.put(strSubstring, a(str2, strSubstring));
                }
            }
            if (map.containsKey(l.f9812a)) {
                h5PayResultModel.setResultCode(map.get(l.f9812a));
            }
            h5PayResultModel.setReturnUrl(a(str, map));
            if (TextUtils.isEmpty(h5PayResultModel.getReturnUrl())) {
                com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9363k0, "");
            }
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9365l0, th);
            e.a(th);
        }
        return h5PayResultModel;
    }

    public synchronized String pay(String str, boolean z2) {
        if (com.alipay.sdk.m.u.b.a()) {
            return com.alipay.sdk.m.j.b.b();
        }
        return a(new com.alipay.sdk.m.s.a(this.f9148a, str, "pay"), str, z2);
    }

    public synchronized boolean payInterceptorWithUrl(String str, boolean z2, H5PayCallback h5PayCallback) {
        String strFetchOrderInfoFromH5PayUrl;
        try {
            strFetchOrderInfoFromH5PayUrl = fetchOrderInfoFromH5PayUrl(str);
            if (!TextUtils.isEmpty(strFetchOrderInfoFromH5PayUrl)) {
                e.d(com.alipay.sdk.m.l.a.f9433z, "intercepted: " + strFetchOrderInfoFromH5PayUrl);
                new Thread(new a(strFetchOrderInfoFromH5PayUrl, z2, h5PayCallback)).start();
            }
        } catch (Throwable th) {
            throw th;
        }
        return !TextUtils.isEmpty(strFetchOrderInfoFromH5PayUrl);
    }

    public synchronized Map<String, String> payV2(String str, boolean z2) {
        String strA;
        com.alipay.sdk.m.s.a aVar;
        try {
            if (com.alipay.sdk.m.u.b.a()) {
                strA = com.alipay.sdk.m.j.b.b();
                aVar = null;
            } else {
                com.alipay.sdk.m.s.a aVar2 = new com.alipay.sdk.m.s.a(this.f9148a, str, "payV2");
                strA = a(aVar2, str, z2);
                aVar = aVar2;
            }
        } catch (Throwable th) {
            throw th;
        }
        return l.a(aVar, strA);
    }

    public void showLoading() {
        com.alipay.sdk.m.x.a aVar = this.f9149b;
        if (aVar != null) {
            aVar.d();
        }
    }

    /* JADX WARN: Finally extract failed */
    private synchronized String a(com.alipay.sdk.m.s.a aVar, String str, boolean z2) {
        String strA;
        if (z2) {
            try {
                showLoading();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (str.contains("payment_inst=")) {
            String strSubstring = str.substring(str.indexOf("payment_inst=") + 13);
            int iIndexOf = strSubstring.indexOf(38);
            if (iIndexOf > 0) {
                strSubstring = strSubstring.substring(0, iIndexOf);
            }
            com.alipay.sdk.m.j.a.a(strSubstring.replaceAll("\"", "").toLowerCase(Locale.getDefault()).replaceAll("alipay", ""));
        } else {
            com.alipay.sdk.m.j.a.a("");
        }
        if (str.contains(com.alipay.sdk.m.l.a.f9429v)) {
            com.alipay.sdk.m.l.a.f9430w = true;
        }
        if (com.alipay.sdk.m.l.a.f9430w) {
            if (str.startsWith(com.alipay.sdk.m.l.a.f9431x)) {
                str = str.substring(str.indexOf(com.alipay.sdk.m.l.a.f9431x) + 53);
            } else if (str.startsWith(com.alipay.sdk.m.l.a.f9432y)) {
                str = str.substring(str.indexOf(com.alipay.sdk.m.l.a.f9432y) + 52);
            }
        }
        strA = "";
        try {
            e.d(com.alipay.sdk.m.l.a.f9433z, "pay prepared: " + str);
            strA = a(str, aVar);
            e.d(com.alipay.sdk.m.l.a.f9433z, "pay raw result: " + strA);
            i.a(aVar, this.f9148a.getApplicationContext(), strA);
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.V, "" + SystemClock.elapsedRealtime());
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.W, l.a(strA, l.f9812a) + "|" + l.a(strA, l.f9813b));
        } catch (Throwable th2) {
            try {
                strA = com.alipay.sdk.m.j.b.a();
                e.a(th2);
                com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.V, "" + SystemClock.elapsedRealtime());
                com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.W, l.a(strA, l.f9812a) + "|" + l.a(strA, l.f9813b));
                if (!com.alipay.sdk.m.m.a.z().r()) {
                }
            } catch (Throwable th3) {
                com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.V, "" + SystemClock.elapsedRealtime());
                com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.W, l.a(strA, l.f9812a) + "|" + l.a(strA, l.f9813b));
                if (!com.alipay.sdk.m.m.a.z().r()) {
                    com.alipay.sdk.m.m.a.z().a(aVar, this.f9148a.getApplicationContext(), false, 3);
                }
                dismissLoading();
                com.alipay.sdk.m.k.a.b(this.f9148a.getApplicationContext(), aVar, str, aVar.f9727d);
                throw th3;
            }
        }
        if (!com.alipay.sdk.m.m.a.z().r()) {
            com.alipay.sdk.m.m.a.z().a(aVar, this.f9148a.getApplicationContext(), false, 3);
        }
        dismissLoading();
        com.alipay.sdk.m.k.a.b(this.f9148a.getApplicationContext(), aVar, str, aVar.f9727d);
        e.d(com.alipay.sdk.m.l.a.f9433z, "pay returning: " + strA);
        return strA;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0051 A[Catch: all -> 0x0057, TryCatch #0 {all -> 0x0057, blocks: (B:11:0x001e, B:13:0x0044, B:15:0x0051, B:18:0x0059), top: B:22:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r6) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            java.lang.String r0 = "sc"
            java.lang.String r1 = ""
            android.content.pm.PackageManager r2 = r6.getPackageManager()     // Catch: java.lang.Exception -> L18
            java.lang.String r6 = r6.getPackageName()     // Catch: java.lang.Exception -> L18
            r3 = 0
            android.content.pm.PackageInfo r6 = r2.getPackageInfo(r6, r3)     // Catch: java.lang.Exception -> L18
            java.lang.String r2 = r6.versionName     // Catch: java.lang.Exception -> L18
            java.lang.String r6 = r6.packageName     // Catch: java.lang.Exception -> L16
            goto L1e
        L16:
            r6 = move-exception
            goto L1a
        L18:
            r6 = move-exception
            r2 = r1
        L1a:
            com.alipay.sdk.m.u.e.a(r6)
            r6 = r1
        L1e:
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L57
            r3.<init>()     // Catch: java.lang.Throwable -> L57
            java.lang.String r4 = "appkey"
            java.lang.String r5 = "2014052600006128"
            r3.put(r4, r5)     // Catch: java.lang.Throwable -> L57
            java.lang.String r4 = "ty"
            java.lang.String r5 = "and_lite"
            r3.put(r4, r5)     // Catch: java.lang.Throwable -> L57
            java.lang.String r4 = "sv"
            java.lang.String r5 = "h.a.3.8.10"
            r3.put(r4, r5)     // Catch: java.lang.Throwable -> L57
            java.lang.String r4 = "an"
            r3.put(r4, r6)     // Catch: java.lang.Throwable -> L57
            java.lang.String r6 = "av"
            r3.put(r6, r2)     // Catch: java.lang.Throwable -> L57
            java.lang.String r6 = "sdk_start_time"
            long r4 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L57
            r3.put(r6, r4)     // Catch: java.lang.Throwable -> L57
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L57
            if (r6 != 0) goto L59
            java.lang.String r6 = "h5tonative"
            r3.put(r0, r6)     // Catch: java.lang.Throwable -> L57
            goto L59
        L57:
            r6 = move-exception
            goto L5e
        L59:
            java.lang.String r6 = r3.toString()     // Catch: java.lang.Throwable -> L57
            return r6
        L5e:
            com.alipay.sdk.m.u.e.a(r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.a(android.content.Context):java.lang.String");
    }

    public static final String a(String... strArr) {
        if (strArr == null) {
            return "";
        }
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return "";
    }

    private boolean a(boolean z2, boolean z3, String str, StringBuilder sb, Map<String, String> map, String... strArr) {
        String str2;
        int length = strArr.length;
        int i2 = 0;
        while (true) {
            if (i2 < length) {
                String str3 = strArr[i2];
                if (!TextUtils.isEmpty(map.get(str3))) {
                    str2 = map.get(str3);
                    break;
                }
                i2++;
            } else {
                str2 = "";
                break;
            }
        }
        if (TextUtils.isEmpty(str2)) {
            return !z3;
        }
        if (z2) {
            sb.append("&");
            sb.append(str);
            sb.append("=\"");
            sb.append(str2);
            sb.append("\"");
            return true;
        }
        sb.append(str);
        sb.append("=\"");
        sb.append(str2);
        sb.append("\"");
        return true;
    }

    private String a(String str, Map<String, String> map) throws UnsupportedEncodingException {
        String strA;
        String strD;
        boolean zEquals = "9000".equals(map.get(l.f9812a));
        String str2 = map.get("result");
        c cVarRemove = this.f9154g.remove(str);
        if (cVarRemove == null) {
            strA = "";
        } else {
            strA = cVarRemove.a();
        }
        if (cVarRemove == null) {
            strD = "";
        } else {
            strD = cVarRemove.d();
        }
        a(strA, strD);
        if (map.containsKey("callBackUrl")) {
            return map.get("callBackUrl");
        }
        if (str2.length() > 15) {
            String strA2 = a(n.a("&callBackUrl=\"", "\"", str2), n.a("&call_back_url=\"", "\"", str2), n.a(com.alipay.sdk.m.l.a.f9427t, "\"", str2), URLDecoder.decode(n.a(com.alipay.sdk.m.l.a.f9428u, "&", str2), "utf-8"), URLDecoder.decode(n.a("&callBackUrl=", "&", str2), "utf-8"), n.a("call_back_url=\"", "\"", str2));
            if (!TextUtils.isEmpty(strA2)) {
                return strA2;
            }
        }
        if (cVarRemove != null) {
            String strB = zEquals ? cVarRemove.b() : cVarRemove.c();
            if (!TextUtils.isEmpty(strB)) {
                return strB;
            }
        }
        if (cVarRemove == null) {
            return "";
        }
        return com.alipay.sdk.m.m.a.z().q();
    }

    private String a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf(i.f9804d));
    }

    private h.e a() {
        return new b();
    }

    private String a(String str, com.alipay.sdk.m.s.a aVar) {
        String strA = aVar.a(str);
        if (strA.contains("paymethod=\"expressGateway\"")) {
            return a(aVar, strA);
        }
        List<a.b> listL = com.alipay.sdk.m.m.a.z().l();
        if (!com.alipay.sdk.m.m.a.z().f9518g || listL == null) {
            listL = com.alipay.sdk.m.j.a.f9316d;
        }
        if (n.a(aVar, (Context) this.f9148a, listL, true)) {
            h hVar = new h(this.f9148a, aVar, a());
            e.d(com.alipay.sdk.m.l.a.f9433z, "pay inner started: " + strA);
            String strA2 = hVar.a(strA, false);
            if (!TextUtils.isEmpty(strA2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("resultStatus={");
                com.alipay.sdk.m.j.c cVar = com.alipay.sdk.m.j.c.ACTIVITY_NOT_START_EXIT;
                sb.append(cVar.b());
                sb.append(i.f9804d);
                if (strA2.contains(sb.toString())) {
                    n.a("alipaySdk", com.alipay.sdk.m.l.b.f9450q, this.f9148a, aVar);
                    if (com.alipay.sdk.m.m.a.z().w()) {
                        strA2 = hVar.a(strA, true);
                    } else {
                        strA2 = strA2.replace("resultStatus={" + cVar.b() + i.f9804d, "resultStatus={" + com.alipay.sdk.m.j.c.CANCELED.b() + i.f9804d);
                    }
                }
            }
            e.d(com.alipay.sdk.m.l.a.f9433z, "pay inner raw result: " + strA2);
            hVar.a();
            if (!TextUtils.equals(strA2, h.f9784j) && !TextUtils.equals(strA2, h.f9785k)) {
                if (TextUtils.isEmpty(strA2)) {
                    return com.alipay.sdk.m.j.b.a();
                }
                if (!strA2.contains(PayResultActivity.f9134b)) {
                    return strA2;
                }
                com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9359h0);
                return a(aVar, strA, listL, strA2, this.f9148a);
            }
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9357f0);
            return a(aVar, strA);
        }
        com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9358g0);
        return a(aVar, strA);
    }

    public static String a(com.alipay.sdk.m.s.a aVar, String str, List<a.b> list, String str2, Activity activity) {
        n.c cVarA = n.a(aVar, activity, list);
        if (cVarA == null || cVarA.a(aVar) || cVarA.a() || !TextUtils.equals(cVarA.f9826a.packageName, PayResultActivity.f9136d)) {
            return str2;
        }
        e.b(com.alipay.sdk.m.l.a.f9433z, "PayTask not_login");
        String strValueOf = String.valueOf(str.hashCode());
        Object obj = new Object();
        HashMap<String, Object> map = PayResultActivity.f9135c;
        map.put(strValueOf, obj);
        Intent intent = new Intent(activity, (Class<?>) PayResultActivity.class);
        intent.putExtra(PayResultActivity.f9138f, str);
        intent.putExtra(PayResultActivity.f9139g, activity.getPackageName());
        intent.putExtra(PayResultActivity.f9137e, strValueOf);
        a.C0055a.a(aVar, intent);
        activity.startActivity(intent);
        synchronized (map.get(strValueOf)) {
            try {
                e.b(com.alipay.sdk.m.l.a.f9433z, "PayTask wait");
                map.get(strValueOf).wait();
            } catch (InterruptedException unused) {
                e.b(com.alipay.sdk.m.l.a.f9433z, "PayTask interrupted");
                return com.alipay.sdk.m.j.b.a();
            }
        }
        String str3 = PayResultActivity.b.f9145b;
        e.b(com.alipay.sdk.m.l.a.f9433z, "PayTask ret: " + str3);
        return str3;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00dc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(com.alipay.sdk.m.s.a r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.a(com.alipay.sdk.m.s.a, java.lang.String):java.lang.String");
    }

    private void a(com.alipay.sdk.m.s.a aVar, JSONObject jSONObject) {
        try {
            String strOptString = jSONObject.optString("tid");
            String strOptString2 = jSONObject.optString(com.alipay.sdk.m.t.a.f9742j);
            if (TextUtils.isEmpty(strOptString) || TextUtils.isEmpty(strOptString2)) {
                return;
            }
            com.alipay.sdk.m.t.a.a(com.alipay.sdk.m.s.b.d().b()).a(strOptString, strOptString2);
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.P, th);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x008f, code lost:
    
        r0 = r6.c();
        r11 = com.alipay.sdk.m.j.b.a(java.lang.Integer.valueOf(r0[1]).intValue(), r0[0], com.alipay.sdk.m.u.n.e(r10, r0[2]));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(com.alipay.sdk.m.s.a r10, com.alipay.sdk.m.r.b r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.a(com.alipay.sdk.m.s.a, com.alipay.sdk.m.r.b, java.lang.String):java.lang.String");
    }

    private String a(com.alipay.sdk.m.s.a aVar, com.alipay.sdk.m.r.b bVar) {
        String[] strArrC = bVar.c();
        Intent intent = new Intent(this.f9148a, (Class<?>) H5PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", strArrC[0]);
        if (strArrC.length == 2) {
            bundle.putString("cookie", strArrC[1]);
        }
        intent.putExtras(bundle);
        a.C0055a.a(aVar, intent);
        this.f9148a.startActivity(intent);
        Object obj = f9146h;
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException e2) {
                e.a(e2);
                return com.alipay.sdk.m.j.b.a();
            }
        }
        String strD = com.alipay.sdk.m.j.b.d();
        return TextUtils.isEmpty(strD) ? com.alipay.sdk.m.j.b.a() : strD;
    }
}
