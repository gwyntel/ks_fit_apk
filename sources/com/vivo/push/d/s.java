package com.vivo.push.d;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.heytap.mcssdk.constant.IntentConstant;
import com.vivo.push.model.InsideNotificationItem;
import java.util.HashMap;

/* loaded from: classes4.dex */
final class s implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ InsideNotificationItem f23126a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.q f23127b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ r f23128c;

    s(r rVar, InsideNotificationItem insideNotificationItem, com.vivo.push.b.q qVar) {
        this.f23128c = rVar;
        this.f23126a = insideNotificationItem;
        this.f23127b = qVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws PackageManager.NameNotFoundException {
        int type;
        r rVar = this.f23128c;
        if (((z) rVar).f23137b.onNotificationMessageArrived(((com.vivo.push.l) rVar).f23178a, com.vivo.push.util.q.a(this.f23126a))) {
            com.vivo.push.util.p.b("OnNotificationArrivedTask", "pkg name : " + ((com.vivo.push.l) this.f23128c).f23178a.getPackageName() + " 应用主动拦截通知");
            com.vivo.push.util.p.b(((com.vivo.push.l) this.f23128c).f23178a, "应用主动拦截通知，导致通知无法展示，如需打开请在onNotificationMessageArrived中返回false");
            HashMap map = new HashMap();
            map.put(IntentConstant.MESSAGE_ID, String.valueOf(this.f23127b.f()));
            String strB = com.vivo.push.util.z.b(((com.vivo.push.l) this.f23128c).f23178a, ((com.vivo.push.l) this.f23128c).f23178a.getPackageName());
            if (!TextUtils.isEmpty(strB)) {
                map.put("remoteAppId", strB);
            }
            com.vivo.push.util.e.a(2120L, map);
            return;
        }
        int iB = this.f23128c.b();
        if (iB > 0) {
            com.vivo.push.util.p.b("OnNotificationArrivedTask", "pkg name : " + ((com.vivo.push.l) this.f23128c).f23178a.getPackageName() + " notify channel switch is " + iB);
            com.vivo.push.util.p.b(((com.vivo.push.l) this.f23128c).f23178a, "允许通知开关或者推送通知渠道开关关闭，导致通知无法展示，请到设置页打开应用通知开关 ".concat(String.valueOf(iB)));
            HashMap map2 = new HashMap();
            map2.put(IntentConstant.MESSAGE_ID, String.valueOf(this.f23127b.f()));
            String strB2 = com.vivo.push.util.z.b(((com.vivo.push.l) this.f23128c).f23178a, ((com.vivo.push.l) this.f23128c).f23178a.getPackageName());
            if (!TextUtils.isEmpty(strB2)) {
                map2.put("remoteAppId", strB2);
            }
            com.vivo.push.util.e.a(iB, map2);
            return;
        }
        Context context = ((com.vivo.push.l) this.f23128c).f23178a;
        InsideNotificationItem insideNotificationItem = this.f23126a;
        long jF = this.f23127b.f();
        r rVar2 = this.f23128c;
        com.vivo.push.util.k kVar = new com.vivo.push.util.k(context, insideNotificationItem, jF, ((z) rVar2).f23137b.isAllowNet(((com.vivo.push.l) rVar2).f23178a), new t(this));
        boolean zIsShowBigPicOnMobileNet = this.f23126a.isShowBigPicOnMobileNet();
        String purePicUrl = this.f23126a.getPurePicUrl();
        if (TextUtils.isEmpty(purePicUrl)) {
            purePicUrl = this.f23126a.getCoverUrl();
        }
        if (!TextUtils.isEmpty(purePicUrl)) {
            com.vivo.push.util.p.c("OnNotificationArrivedTask", "showCode=".concat(String.valueOf(zIsShowBigPicOnMobileNet)));
            if (zIsShowBigPicOnMobileNet) {
                com.vivo.push.util.p.a(((com.vivo.push.l) this.f23128c).f23178a, "mobile net show");
            } else {
                com.vivo.push.util.p.a(((com.vivo.push.l) this.f23128c).f23178a, "mobile net unshow");
                NetworkInfo networkInfoA = com.vivo.push.util.r.a(((com.vivo.push.l) this.f23128c).f23178a);
                if (networkInfoA != null && networkInfoA.getState() == NetworkInfo.State.CONNECTED && (type = networkInfoA.getType()) != 1 && type == 0) {
                    this.f23126a.clearCoverUrl();
                    this.f23126a.clearPurePicUrl();
                    purePicUrl = null;
                }
            }
        }
        kVar.execute(this.f23126a.getIconUrl(), purePicUrl);
    }
}
