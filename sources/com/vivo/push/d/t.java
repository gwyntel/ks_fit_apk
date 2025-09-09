package com.vivo.push.d;

import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.google.firebase.messaging.Constants;
import com.heytap.mcssdk.constant.IntentConstant;
import com.vivo.push.d.r;
import java.util.HashMap;

/* loaded from: classes4.dex */
final class t implements r.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ s f23129a;

    t(s sVar) {
        this.f23129a = sVar;
    }

    @Override // com.vivo.push.d.r.a
    public final void a() throws PackageManager.NameNotFoundException {
        long jL = com.vivo.push.e.a().l();
        if (jL < 1400 && jL != 1340) {
            com.vivo.push.util.p.b("OnNotificationArrivedTask", "引擎版本太低，不支持正向展示功能，pushEngineSDKVersion：".concat(String.valueOf(jL)));
            return;
        }
        HashMap map = new HashMap();
        map.put("srt", "1");
        map.put(Constants.MessagePayloadKeys.MSGID_SERVER, String.valueOf(this.f23129a.f23127b.f()));
        String strB = com.vivo.push.util.z.b(((com.vivo.push.l) this.f23129a.f23128c).f23178a, ((com.vivo.push.l) this.f23129a.f23128c).f23178a.getPackageName());
        if (!TextUtils.isEmpty(strB)) {
            map.put("app_id", strB);
        }
        map.put("type", "1");
        map.put("dtp", "1");
        com.vivo.push.util.e.a(6L, map);
    }

    @Override // com.vivo.push.d.r.a
    public final void b() throws PackageManager.NameNotFoundException {
        HashMap map = new HashMap();
        map.put(IntentConstant.MESSAGE_ID, String.valueOf(this.f23129a.f23127b.f()));
        String strB = com.vivo.push.util.z.b(((com.vivo.push.l) this.f23129a.f23128c).f23178a, ((com.vivo.push.l) this.f23129a.f23128c).f23178a.getPackageName());
        if (!TextUtils.isEmpty(strB)) {
            map.put("remoteAppId", strB);
        }
        com.vivo.push.util.e.a(2122L, map);
    }
}
