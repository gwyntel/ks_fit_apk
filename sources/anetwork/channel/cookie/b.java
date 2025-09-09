package anetwork.channel.cookie;

import anet.channel.util.ALog;
import java.net.HttpCookie;

/* loaded from: classes2.dex */
final class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f7178a;

    b(String str) {
        this.f7178a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (CookieManager.f7171d == null) {
            return;
        }
        try {
            for (HttpCookie httpCookie : HttpCookie.parse(this.f7178a)) {
                if (httpCookie.getName().equals(CookieManager.f7171d.f7173a)) {
                    CookieManager.f7171d.f7174b = httpCookie.toString();
                    CookieManager.f7171d.f7176d = httpCookie.getDomain();
                    CookieManager.f7171d.f7175c = this.f7178a;
                    CookieManager.f7171d.a();
                    return;
                }
            }
        } catch (Exception e2) {
            ALog.e(CookieManager.TAG, "cookieMonitorSave error.", null, e2, new Object[0]);
        }
    }
}
