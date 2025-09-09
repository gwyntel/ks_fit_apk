package com.huawei.hms.push;

import android.content.Context;
import android.content.Intent;
import com.huawei.hms.support.log.HMSLog;
import java.net.URISyntaxException;

/* loaded from: classes4.dex */
public class n extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private Context f16718a;

    /* renamed from: b, reason: collision with root package name */
    private m f16719b;

    public n(Context context, m mVar) {
        this.f16718a = context;
        this.f16719b = mVar;
    }

    private static Intent a(Context context, m mVar) throws URISyntaxException {
        if (mVar == null) {
            return null;
        }
        Intent intentB = d.b(context, mVar.d());
        if (mVar.n() == null) {
            if (mVar.a() != null) {
                Intent intent = new Intent(mVar.a());
                if (d.a(context, mVar.d(), intent).booleanValue()) {
                    intentB = intent;
                }
            }
            intentB.setPackage(mVar.d());
            return intentB;
        }
        try {
            Intent uri = Intent.parseUri(mVar.n(), 0);
            uri.setSelector(null);
            HMSLog.d("PushSelfShowLog", "Intent.parseUri(msg.intentUri, 0), action:" + uri.getAction());
            return d.a(context, mVar.d(), uri).booleanValue() ? uri : intentB;
        } catch (Exception e2) {
            HMSLog.w("PushSelfShowLog", "intentUri error," + e2.toString());
            return intentB;
        }
    }

    private boolean b(Context context) {
        if ("cosa".equals(this.f16719b.i())) {
            return a(context);
        }
        return true;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        HMSLog.i("PushSelfShowLog", "enter run()");
        try {
            if (!b(this.f16718a) || b(this.f16718a, this.f16719b)) {
                return;
            }
            l.a(this.f16718a, this.f16719b);
        } catch (Exception e2) {
            HMSLog.e("PushSelfShowLog", e2.toString());
        }
    }

    private boolean b(Context context, m mVar) {
        if (!"cosa".equals(mVar.i()) || a(context, mVar) != null) {
            return false;
        }
        HMSLog.d("PushSelfShowLog", "launchCosaApp,intent == null");
        return true;
    }

    private boolean a(Context context) {
        return d.c(context, this.f16719b.d());
    }
}
