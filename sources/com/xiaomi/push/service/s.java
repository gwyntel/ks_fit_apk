package com.xiaomi.push.service;

import com.xiaomi.push.im;
import com.xiaomi.push.ir;
import java.util.List;

/* loaded from: classes4.dex */
public class s implements im {

    /* renamed from: a, reason: collision with root package name */
    private final XMPushService f24620a;

    public s(XMPushService xMPushService) {
        this.f24620a = xMPushService;
    }

    @Override // com.xiaomi.push.im
    public void a(List<ir> list, String str, String str2) {
        this.f24620a.a(new t(this, 4, str, list, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str) {
        if ("com.xiaomi.xmsf".equals(str)) {
            return "1000271";
        }
        return this.f24620a.getSharedPreferences("pref_registered_pkg_names", 0).getString(str, null);
    }
}
