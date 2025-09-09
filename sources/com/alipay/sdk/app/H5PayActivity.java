package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.alipay.sdk.m.j.b;
import com.alipay.sdk.m.j.d;
import com.alipay.sdk.m.s.a;
import com.alipay.sdk.m.u.e;
import com.alipay.sdk.m.u.n;
import com.alipay.sdk.m.x.c;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class H5PayActivity extends Activity {

    /* renamed from: a, reason: collision with root package name */
    public c f9114a;

    /* renamed from: b, reason: collision with root package name */
    public String f9115b;

    /* renamed from: c, reason: collision with root package name */
    public String f9116c;

    /* renamed from: d, reason: collision with root package name */
    public String f9117d;

    /* renamed from: e, reason: collision with root package name */
    public String f9118e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f9119f;

    /* renamed from: g, reason: collision with root package name */
    public String f9120g;

    /* renamed from: h, reason: collision with root package name */
    public WeakReference<a> f9121h;

    private void b() {
        try {
            super.requestWindowFeature(1);
            getWindow().addFlags(8192);
        } catch (Throwable th) {
            e.a(th);
        }
    }

    public void a() {
        Object obj = PayTask.f9146h;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception unused) {
            }
        }
    }

    @Override // android.app.Activity
    public void finish() {
        a();
        super.finish();
    }

    @Override // android.app.Activity
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i2 == 1010) {
            d.a((a) n.a(this.f9121h), i2, i3, intent);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        c cVar = this.f9114a;
        if (cVar == null) {
            finish();
            return;
        }
        if (cVar.a()) {
            cVar.b();
            return;
        }
        if (!cVar.b()) {
            super.onBackPressed();
        }
        b.a(b.a());
        finish();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        b();
        super.onCreate(bundle);
        try {
            a aVarA = a.C0055a.a(getIntent());
            if (aVarA == null) {
                finish();
                return;
            }
            this.f9121h = new WeakReference<>(aVarA);
            if (com.alipay.sdk.m.m.a.z().v()) {
                setRequestedOrientation(3);
            } else {
                setRequestedOrientation(1);
            }
            try {
                Bundle extras = getIntent().getExtras();
                String string = extras.getString("url", null);
                this.f9115b = string;
                if (!n.f(string)) {
                    finish();
                    return;
                }
                this.f9117d = extras.getString("cookie", null);
                this.f9116c = extras.getString("method", null);
                this.f9118e = extras.getString("title", null);
                this.f9120g = extras.getString("version", c.f9865c);
                this.f9119f = extras.getBoolean("backisexit", false);
                try {
                    com.alipay.sdk.m.x.d dVar = new com.alipay.sdk.m.x.d(this, aVarA, this.f9120g);
                    setContentView(dVar);
                    dVar.a(this.f9118e, this.f9116c, this.f9119f);
                    dVar.a(this.f9115b, this.f9117d);
                    dVar.a(this.f9115b);
                    this.f9114a = dVar;
                } catch (Throwable th) {
                    com.alipay.sdk.m.k.a.a(aVarA, com.alipay.sdk.m.k.b.f9364l, "GetInstalledAppEx", th);
                    finish();
                }
            } catch (Exception unused) {
                finish();
            }
        } catch (Exception unused2) {
            finish();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        c cVar = this.f9114a;
        if (cVar != null) {
            cVar.c();
        }
    }

    @Override // android.app.Activity
    public void setRequestedOrientation(int i2) {
        try {
            super.setRequestedOrientation(i2);
        } catch (Throwable th) {
            try {
                com.alipay.sdk.m.k.a.a((a) n.a(this.f9121h), com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.B, th);
            } catch (Throwable unused) {
            }
        }
    }
}
