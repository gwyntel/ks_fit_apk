package com.huawei.hms.push;

import android.content.Context;
import android.content.Intent;
import com.huawei.hms.support.log.HMSLog;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.net.URISyntaxException;

/* loaded from: classes4.dex */
public class s {

    /* renamed from: c, reason: collision with root package name */
    private static final String[] f16727c = {"url", PushConstants.EXTRA_APPLICATION_PENDING_INTENT, "cosa", "rp"};

    /* renamed from: a, reason: collision with root package name */
    private Context f16728a;

    /* renamed from: b, reason: collision with root package name */
    private m f16729b;

    public s(Context context, m mVar) {
        this.f16728a = context;
        this.f16729b = mVar;
    }

    public static boolean a(String str) {
        for (String str2 : f16727c) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void b() throws URISyntaxException {
        Intent uri;
        HMSLog.i("PushSelfShowLog", "run into launchCosaApp");
        try {
            HMSLog.i("PushSelfShowLog", "enter launchExistApp cosa, appPackageName =" + this.f16729b.d() + ",and msg.intentUri is " + this.f16729b.n());
            Intent intentB = d.b(this.f16728a, this.f16729b.d());
            boolean zBooleanValue = false;
            if (this.f16729b.n() != null) {
                try {
                    uri = Intent.parseUri(this.f16729b.n(), 0);
                    uri.setSelector(null);
                    HMSLog.i("PushSelfShowLog", "Intent.parseUri(msg.intentUri, 0), action:" + uri.getAction());
                    zBooleanValue = d.a(this.f16728a, this.f16729b.d(), uri).booleanValue();
                    if (zBooleanValue) {
                        intentB = uri;
                    }
                } catch (Exception e2) {
                    HMSLog.w("PushSelfShowLog", "intentUri error." + e2.toString());
                }
            } else if (this.f16729b.a() != null) {
                uri = new Intent(this.f16729b.a());
                if (d.a(this.f16728a, this.f16729b.d(), uri).booleanValue()) {
                    intentB = uri;
                }
            }
            if (intentB == null) {
                HMSLog.i("PushSelfShowLog", "launchCosaApp,intent == null");
                return;
            }
            intentB.setPackage(this.f16729b.d());
            if (zBooleanValue) {
                intentB.addFlags(268435456);
            } else {
                intentB.setFlags(805437440);
            }
            this.f16728a.startActivity(intentB);
        } catch (Exception e3) {
            HMSLog.e("PushSelfShowLog", "launch Cosa App exception." + e3.toString());
        }
    }

    public void c() throws URISyntaxException {
        m mVar;
        HMSLog.d("PushSelfShowLog", "enter launchNotify()");
        if (this.f16728a == null || (mVar = this.f16729b) == null) {
            HMSLog.d("PushSelfShowLog", "launchNotify  context or msg is null");
            return;
        }
        if (PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(mVar.i())) {
            a();
            return;
        }
        if ("cosa".equals(this.f16729b.i())) {
            b();
            return;
        }
        if ("rp".equals(this.f16729b.i())) {
            HMSLog.w("PushSelfShowLog", this.f16729b.i() + " not support rich message.");
            return;
        }
        if ("url".equals(this.f16729b.i())) {
            HMSLog.w("PushSelfShowLog", this.f16729b.i() + " not support URL.");
            return;
        }
        HMSLog.d("PushSelfShowLog", this.f16729b.i() + " is not exist in hShowType");
    }

    private void a() {
        try {
            HMSLog.i("PushSelfShowLog", "enter launchApp, appPackageName =" + this.f16729b.d());
            if (d.c(this.f16728a, this.f16729b.d())) {
                b();
            }
        } catch (Exception e2) {
            HMSLog.e("PushSelfShowLog", "launchApp error:" + e2.toString());
        }
    }
}
