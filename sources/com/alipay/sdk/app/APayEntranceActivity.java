package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.sdk.m.k.b;
import com.alipay.sdk.m.s.a;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class APayEntranceActivity extends Activity {

    /* renamed from: d, reason: collision with root package name */
    public static final String f9100d = "ap_order_info";

    /* renamed from: e, reason: collision with root package name */
    public static final String f9101e = "ap_target_packagename";

    /* renamed from: f, reason: collision with root package name */
    public static final String f9102f = "ap_session";

    /* renamed from: g, reason: collision with root package name */
    public static final String f9103g = "ap_local_info";

    /* renamed from: h, reason: collision with root package name */
    public static final ConcurrentHashMap<String, a> f9104h = new ConcurrentHashMap<>();

    /* renamed from: a, reason: collision with root package name */
    public String f9105a;

    /* renamed from: b, reason: collision with root package name */
    public String f9106b;

    /* renamed from: c, reason: collision with root package name */
    public com.alipay.sdk.m.s.a f9107c;

    public interface a {
        void a(String str);
    }

    @Override // android.app.Activity
    public void finish() {
        String str = this.f9106b;
        com.alipay.sdk.m.k.a.a(this.f9107c, b.f9364l, "BSAFinish", str + "|" + TextUtils.isEmpty(this.f9105a));
        if (TextUtils.isEmpty(this.f9105a)) {
            this.f9105a = com.alipay.sdk.m.j.b.a();
        }
        if (str != null) {
            a aVarRemove = f9104h.remove(str);
            if (aVarRemove != null) {
                aVarRemove.a(this.f9105a);
            } else {
                com.alipay.sdk.m.k.a.b(this.f9107c, "wr", "refNull", "session=" + str);
            }
        }
        try {
            super.finish();
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(this.f9107c, "wr", "APStartFinish", th);
        }
    }

    @Override // android.app.Activity
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        com.alipay.sdk.m.k.a.a(this.f9107c, b.f9364l, "BSAOnAR", this.f9106b + "|" + i2 + "," + i3);
        if (i2 == 1000) {
            if (intent != null) {
                try {
                    this.f9105a = intent.getStringExtra("result");
                } catch (Throwable unused) {
                }
            }
            finish();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                finish();
                return;
            }
            String string = extras.getString(f9100d);
            String string2 = extras.getString(f9101e);
            this.f9106b = extras.getString(f9102f);
            String string3 = extras.getString(f9103g, "{}");
            if (!TextUtils.isEmpty(this.f9106b)) {
                com.alipay.sdk.m.s.a aVarA = a.C0055a.a(this.f9106b);
                this.f9107c = aVarA;
                com.alipay.sdk.m.k.a.a(aVarA, b.f9364l, "BSAEntryCreate", this.f9106b + "|" + SystemClock.elapsedRealtime());
            }
            Intent intent = new Intent();
            intent.putExtra("order_info", string);
            intent.putExtra("localInfo", string3);
            intent.setClassName(string2, "com.alipay.android.app.flybird.ui.window.FlyBirdWindowActivity");
            try {
                startActivityForResult(intent, 1000);
            } catch (Throwable th) {
                com.alipay.sdk.m.k.a.a(this.f9107c, "wr", "APStartEx", th);
                finish();
            }
            if (this.f9107c != null) {
                Context applicationContext = getApplicationContext();
                com.alipay.sdk.m.s.a aVar = this.f9107c;
                com.alipay.sdk.m.k.a.a(applicationContext, aVar, string, aVar.f9727d);
            }
        } catch (Throwable unused) {
            finish();
        }
    }
}
