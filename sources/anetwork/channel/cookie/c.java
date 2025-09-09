package anetwork.channel.cookie;

import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.CookieMonitorStat;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import com.huawei.hms.framework.common.ContainerUtils;
import java.net.HttpCookie;

/* loaded from: classes2.dex */
final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f7179a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f7180b;

    c(String str, String str2) {
        this.f7179a = str;
        this.f7180b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (CookieManager.f7171d == null) {
            return;
        }
        try {
            if (TextUtils.isEmpty(CookieManager.f7171d.f7173a) || !HttpCookie.domainMatches(CookieManager.f7171d.f7176d, HttpUrl.parse(this.f7179a).host()) || TextUtils.isEmpty(this.f7180b)) {
                return;
            }
            if (this.f7180b.contains(CookieManager.f7171d.f7173a + ContainerUtils.KEY_VALUE_DELIMITER)) {
                return;
            }
            CookieMonitorStat cookieMonitorStat = new CookieMonitorStat(this.f7179a);
            cookieMonitorStat.cookieName = CookieManager.f7171d.f7173a;
            cookieMonitorStat.cookieText = CookieManager.f7171d.f7174b;
            cookieMonitorStat.setCookie = CookieManager.f7171d.f7175c;
            cookieMonitorStat.missType = 1;
            AppMonitor.getInstance().commitStat(cookieMonitorStat);
        } catch (Exception e2) {
            ALog.e(CookieManager.TAG, "cookieMonitorReport error.", null, e2, new Object[0]);
        }
    }
}
