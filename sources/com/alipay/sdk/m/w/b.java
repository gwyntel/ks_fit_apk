package com.alipay.sdk.m.w;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.ConditionVariable;
import android.text.TextUtils;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.sdk.m.w.a;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class b {

    public static class a implements Callable<WifiInfo> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f9837a;

        public a(Context context) {
            this.f9837a = context;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public WifiInfo call() {
            return ((WifiManager) this.f9837a.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        }
    }

    /* renamed from: com.alipay.sdk.m.w.b$b, reason: collision with other inner class name */
    public static class C0058b implements a.InterfaceC0057a<Object, Boolean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alipay.sdk.m.w.a.InterfaceC0057a
        public Boolean a(Object obj) {
            return Boolean.valueOf((obj instanceof String) || obj == null);
        }
    }

    public static class c implements Callable<String> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f9838a;

        public c(Context context) {
            this.f9838a = context;
        }

        @Override // java.util.concurrent.Callable
        public String call() {
            return com.alipay.sdk.m.b.c.a(this.f9838a);
        }
    }

    public static class d implements a.InterfaceC0057a<Object, Boolean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alipay.sdk.m.w.a.InterfaceC0057a
        public Boolean a(Object obj) {
            return Boolean.valueOf((obj instanceof NetworkInfo) || obj == null);
        }
    }

    public static class e implements Callable<NetworkInfo> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f9839a;

        public e(Context context) {
            this.f9839a = context;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public NetworkInfo call() {
            return ((ConnectivityManager) this.f9839a.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        }
    }

    public static class f implements a.InterfaceC0057a<Object, Boolean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alipay.sdk.m.w.a.InterfaceC0057a
        public Boolean a(Object obj) {
            return Boolean.valueOf((obj instanceof String) || obj == null);
        }
    }

    public static class g implements Callable<String> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f9840a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ com.alipay.sdk.m.s.a f9841b;

        public g(Context context, com.alipay.sdk.m.s.a aVar) {
            this.f9840a = context;
            this.f9841b = aVar;
        }

        @Override // java.util.concurrent.Callable
        public String call() {
            try {
                return com.alipay.sdk.m.n0.a.c(this.f9840a);
            } catch (Throwable th) {
                com.alipay.sdk.m.k.a.b(this.f9841b, "third", com.alipay.sdk.m.k.b.f9382u, th.getClass().getName());
                return "";
            }
        }
    }

    public static class h implements a.InterfaceC0057a<Object, Boolean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alipay.sdk.m.w.a.InterfaceC0057a
        public Boolean a(Object obj) {
            return Boolean.valueOf((obj instanceof String) || obj == null);
        }
    }

    public static class i implements Callable<String> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f9842a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f9843b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ Context f9844c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ com.alipay.sdk.m.s.a f9845d;

        public class a implements APSecuritySdk.InitResultListener {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ String[] f9846a;

            /* renamed from: b, reason: collision with root package name */
            public final /* synthetic */ ConditionVariable f9847b;

            public a(String[] strArr, ConditionVariable conditionVariable) {
                this.f9846a = strArr;
                this.f9847b = conditionVariable;
            }

            @Override // com.alipay.apmobilesecuritysdk.face.APSecuritySdk.InitResultListener
            public void onResult(APSecuritySdk.TokenResult tokenResult) {
                if (tokenResult != null) {
                    this.f9846a[0] = tokenResult.apdidToken;
                }
                this.f9847b.open();
            }
        }

        public i(String str, String str2, Context context, com.alipay.sdk.m.s.a aVar) {
            this.f9842a = str;
            this.f9843b = str2;
            this.f9844c = context;
            this.f9845d = aVar;
        }

        @Override // java.util.concurrent.Callable
        public String call() {
            HashMap map = new HashMap();
            map.put("tid", this.f9842a);
            map.put("utdid", this.f9843b);
            String[] strArr = {""};
            try {
                APSecuritySdk aPSecuritySdk = APSecuritySdk.getInstance(this.f9844c);
                ConditionVariable conditionVariable = new ConditionVariable();
                aPSecuritySdk.initToken(0, map, new a(strArr, conditionVariable));
                conditionVariable.block(3000L);
            } catch (Throwable th) {
                com.alipay.sdk.m.u.e.a(th);
                com.alipay.sdk.m.k.a.b(this.f9845d, "third", com.alipay.sdk.m.k.b.f9376r, th.getClass().getName());
            }
            if (TextUtils.isEmpty(strArr[0])) {
                com.alipay.sdk.m.k.a.b(this.f9845d, "third", com.alipay.sdk.m.k.b.f9378s, "missing token");
            }
            return strArr[0];
        }
    }

    public static class j implements a.InterfaceC0057a<Object, Boolean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alipay.sdk.m.w.a.InterfaceC0057a
        public Boolean a(Object obj) {
            return Boolean.valueOf((obj instanceof WifiInfo) || obj == null);
        }
    }

    public static NetworkInfo a(com.alipay.sdk.m.s.a aVar, Context context) {
        Context contextA = com.alipay.sdk.m.w.a.a(context);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        return (NetworkInfo) com.alipay.sdk.m.w.a.a(2, 10L, timeUnit, new d(), new e(contextA), false, 10L, timeUnit, aVar, false);
    }

    public static String b(com.alipay.sdk.m.s.a aVar, Context context) {
        if (!com.alipay.sdk.m.m.a.z().u()) {
            return "";
        }
        return (String) com.alipay.sdk.m.w.a.a(1, 1L, TimeUnit.DAYS, new C0058b(), new c(com.alipay.sdk.m.w.a.a(context)), true, 200L, TimeUnit.MILLISECONDS, aVar, true);
    }

    public static String c(com.alipay.sdk.m.s.a aVar, Context context) {
        return (String) com.alipay.sdk.m.w.a.a(3, 1L, TimeUnit.DAYS, new f(), new g(com.alipay.sdk.m.w.a.a(context), aVar), true, 3L, TimeUnit.SECONDS, aVar, false);
    }

    public static WifiInfo d(com.alipay.sdk.m.s.a aVar, Context context) {
        Context contextA = com.alipay.sdk.m.w.a.a(context);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        return (WifiInfo) com.alipay.sdk.m.w.a.a(5, 10L, timeUnit, new j(), new a(contextA), false, 10L, timeUnit, aVar, false);
    }

    public static String a(com.alipay.sdk.m.s.a aVar, Context context, String str, String str2) {
        Context contextA = com.alipay.sdk.m.w.a.a(context);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        return (String) com.alipay.sdk.m.w.a.a(4, 10L, timeUnit, new h(), new i(str, str2, contextA, aVar), true, 3L, timeUnit, aVar, true);
    }
}
