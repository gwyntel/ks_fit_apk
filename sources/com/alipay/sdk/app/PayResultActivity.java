package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.alipay.sdk.m.s.a;
import com.alipay.sdk.m.u.e;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/* loaded from: classes2.dex */
public final class PayResultActivity extends Activity {

    /* renamed from: b, reason: collision with root package name */
    public static final String f9134b = "{\"isLogin\":\"false\"}";

    /* renamed from: c, reason: collision with root package name */
    public static final HashMap<String, Object> f9135c = new HashMap<>();

    /* renamed from: d, reason: collision with root package name */
    public static final String f9136d = "hk.alipay.wallet";

    /* renamed from: e, reason: collision with root package name */
    public static final String f9137e = "phonecashier.pay.hash";

    /* renamed from: f, reason: collision with root package name */
    public static final String f9138f = "orderSuffix";

    /* renamed from: g, reason: collision with root package name */
    public static final String f9139g = "externalPkgName";

    /* renamed from: h, reason: collision with root package name */
    public static final String f9140h = "phonecashier.pay.result";

    /* renamed from: i, reason: collision with root package name */
    public static final String f9141i = "phonecashier.pay.resultOrderHash";

    /* renamed from: a, reason: collision with root package name */
    public com.alipay.sdk.m.s.a f9142a = null;

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Activity f9143a;

        public a(Activity activity) {
            this.f9143a = activity;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f9143a.finish();
        }
    }

    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        public static volatile String f9144a;

        /* renamed from: b, reason: collision with root package name */
        public static volatile String f9145b;
    }

    public static void a(Activity activity, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        Intent intent = new Intent();
        try {
            intent.setPackage(f9136d);
            intent.setData(Uri.parse("alipayhk://platformapi/startApp?appId=20000125&schemePaySession=" + URLEncoder.encode(str, "UTF-8") + "&orderSuffix=" + URLEncoder.encode(str2, "UTF-8") + "&packageName=" + URLEncoder.encode(str3, "UTF-8") + "&externalPkgName=" + URLEncoder.encode(str3, "UTF-8")));
        } catch (UnsupportedEncodingException e2) {
            e.a(e2);
        }
        if (activity != null) {
            try {
                activity.startActivity(intent);
            } catch (Throwable unused) {
                activity.finish();
            }
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Intent intent = getIntent();
            if (!TextUtils.isEmpty(intent.getStringExtra(f9138f))) {
                b.f9144a = intent.getStringExtra(f9137e);
                String stringExtra = intent.getStringExtra(f9138f);
                String stringExtra2 = intent.getStringExtra(f9139g);
                com.alipay.sdk.m.s.a aVarA = a.C0055a.a(intent);
                this.f9142a = aVarA;
                if (aVarA == null) {
                    finish();
                }
                a(this, b.f9144a, stringExtra, stringExtra2);
                a(this, 300);
                return;
            }
            if (this.f9142a == null) {
                finish();
            }
            String stringExtra3 = intent.getStringExtra(f9140h);
            int intExtra = intent.getIntExtra(f9141i, 0);
            if (intExtra != 0 && TextUtils.equals(b.f9144a, String.valueOf(intExtra))) {
                if (TextUtils.isEmpty(stringExtra3)) {
                    a(b.f9144a);
                } else {
                    a(stringExtra3, b.f9144a);
                }
                b.f9144a = "";
                a(this, 300);
                return;
            }
            com.alipay.sdk.m.k.a.b(this.f9142a, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9360i0, "Expected " + b.f9144a + ", got " + intExtra);
            a(b.f9144a);
            a(this, 300);
        } catch (Throwable unused) {
            finish();
        }
    }

    public static void a(String str) {
        b.f9145b = com.alipay.sdk.m.j.b.a();
        a(f9135c, str);
    }

    public static void a(String str, String str2) {
        b.f9145b = str;
        a(f9135c, str2);
    }

    public static void a(Activity activity, int i2) {
        new Handler().postDelayed(new a(activity), i2);
    }

    public static boolean a(HashMap<String, Object> map, String str) {
        Object obj;
        if (map == null || str == null || (obj = map.get(str)) == null) {
            return false;
        }
        synchronized (obj) {
            obj.notifyAll();
        }
        return true;
    }
}
