package com.huawei.hms.scankit.p;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.huawei.hms.feature.DynamicModuleInitializer;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.framework.network.grs.GrsClient;
import com.huawei.hms.hatool.HmsHiAnalyticsUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.jmdns.impl.constants.DNSConstants;

/* loaded from: classes4.dex */
public class a4 {

    /* renamed from: h, reason: collision with root package name */
    private static volatile a4 f16960h = new a4();

    /* renamed from: i, reason: collision with root package name */
    private static String[] f16961i = {"AD", "AL", "AN", "AT", "AU", "AX", "BA", "BE", "BG", "BQ", "CA", "CH", "CW", "CY", "CZ", "DE", "DK", "EE", "ES", "FI", "FO", "FR", "GB", "GG", "GI", "GL", "GR", "HR", "HU", "IE", "IL", "IM", "IS", "IT", "JE", "LI", "LT", "LU", "LV", "MC", "MD", "ME", "MF", "MK", "MT", "NL", SdkConstant.CLOUDAPI_COMMAND_NOTIFY_RESPONSE, "NZ", "PL", "PM", "PT", SdkConstant.CLOUDAPI_COMMAND_REGISTER_SUCCESS_RESPONSE, "RS", "SE", "SI", "SJ", "SK", "SM", "SX", "TR", "UA", "UM", "US", "VA", "VC", "XK", "YK"};

    /* renamed from: c, reason: collision with root package name */
    private volatile boolean f16964c;

    /* renamed from: d, reason: collision with root package name */
    private volatile long f16965d;

    /* renamed from: a, reason: collision with root package name */
    private Timer f16962a = new Timer();

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f16963b = true;

    /* renamed from: e, reason: collision with root package name */
    private volatile boolean f16966e = false;

    /* renamed from: f, reason: collision with root package name */
    private final Lock f16967f = new ReentrantLock();

    /* renamed from: g, reason: collision with root package name */
    private List<b> f16968g = new ArrayList(5);

    class a extends Thread {
        a(String str) {
            super(str);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            a4.this.d();
        }
    }

    private class b {

        /* renamed from: a, reason: collision with root package name */
        private String f16970a;

        /* renamed from: b, reason: collision with root package name */
        private LinkedHashMap<String, String> f16971b;

        /* synthetic */ b(a4 a4Var, String str, LinkedHashMap linkedHashMap, a aVar) {
            this(str, linkedHashMap);
        }

        private b(String str, LinkedHashMap<String, String> linkedHashMap) {
            this.f16970a = str;
            this.f16971b = linkedHashMap;
        }
    }

    private class c extends TimerTask {
        private c() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            try {
                a4.this.f16963b = true;
                HmsHiAnalyticsUtils.onReport();
            } catch (Exception e2) {
                o4.b("ScanHiAnalytics", e2.getMessage());
            }
        }

        /* synthetic */ c(a4 a4Var, a aVar) {
            this();
        }
    }

    private a4() {
    }

    public static a4 b() {
        return f16960h;
    }

    private void c(String str, LinkedHashMap<String, String> linkedHashMap) {
        if (!this.f16966e) {
            HmsHiAnalyticsUtils.onEvent(0, str, linkedHashMap);
            HmsHiAnalyticsUtils.onEvent(1, str, linkedHashMap);
        }
        if (this.f16963b) {
            this.f16963b = false;
            this.f16962a.schedule(new c(this, null), 3000L);
        }
        o4.d("ScanHiAnalytics", str + " " + linkedHashMap.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        if (!this.f16967f.tryLock() || this.f16964c) {
            return;
        }
        try {
            Context context = DynamicModuleInitializer.getContext();
            if (context == null) {
                return;
            }
            String strA = a(context);
            if (strA != null && !strA.isEmpty()) {
                HmsHiAnalyticsUtils.init(context, false, false, false, strA, context.getPackageName());
                HmsHiAnalyticsUtils.enableLog();
                a();
            }
        } finally {
            this.f16967f.unlock();
        }
    }

    public void b(String str, LinkedHashMap<String, String> linkedHashMap) {
        if (this.f16964c) {
            c(str, linkedHashMap);
        } else {
            a(str, linkedHashMap);
            c();
        }
    }

    private synchronized void a(String str, LinkedHashMap<String, String> linkedHashMap) {
        if (this.f16964c) {
            c(str, linkedHashMap);
        } else {
            if (this.f16968g.size() >= 100) {
                return;
            }
            this.f16968g.add(new b(this, str, linkedHashMap, null));
        }
    }

    private synchronized void a() {
        try {
            this.f16964c = true;
            for (b bVar : this.f16968g) {
                c(bVar.f16970a, bVar.f16971b);
            }
            this.f16968g = null;
        } catch (Throwable th) {
            throw th;
        }
    }

    private void c() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.f16965d > DNSConstants.SERVICE_INFO_TIMEOUT) {
            this.f16965d = jCurrentTimeMillis;
            new a("ScanHiAnalytics").start();
        }
    }

    private String a(Context context) {
        try {
            GrsBaseInfo grsBaseInfo = new GrsBaseInfo();
            String strA = new a1(context, false).a();
            Log.i("ScanHiAnalytics", "getCollectURL:localCountryCode " + strA);
            if (strA != null && !strA.isEmpty() && !"UNKNOWN".equals(strA)) {
                grsBaseInfo.setSerCountry(strA.toUpperCase(Locale.ENGLISH));
            }
            if (Arrays.asList(f16961i).contains(strA)) {
                this.f16966e = true;
            }
            GrsClient grsClient = new GrsClient(context, grsBaseInfo);
            String strSynGetGrsUrl = grsClient.synGetGrsUrl("com.huawei.cloud.mlkithianalytics", "ROOTNEW");
            if (TextUtils.isEmpty(strSynGetGrsUrl)) {
                strSynGetGrsUrl = grsClient.synGetGrsUrl("com.huawei.cloud.mlkithianalytics", "ROOT");
            }
            Log.i("ScanHiAnalytics", "grs get url success: " + strSynGetGrsUrl + "  countryCode = " + grsBaseInfo.getSerCountry());
            return strSynGetGrsUrl;
        } catch (RuntimeException | Exception unused) {
            return null;
        }
    }
}
