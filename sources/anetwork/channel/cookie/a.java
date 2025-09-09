package anetwork.channel.cookie;

import android.text.TextUtils;
import anet.channel.util.ALog;
import anetwork.channel.cookie.CookieManager;

/* loaded from: classes2.dex */
final class a implements Runnable {
    a() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (TextUtils.isEmpty(CookieManager.f())) {
                return;
            }
            CookieManager.a unused = CookieManager.f7171d = new CookieManager.a(CookieManager.f());
        } catch (Exception e2) {
            ALog.e(CookieManager.TAG, "", null, e2, new Object[0]);
        }
    }
}
