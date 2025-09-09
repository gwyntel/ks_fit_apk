package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
public class by extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    private Context f23521a;

    public by(Context context) {
        this.f23521a = context;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a, reason: collision with other method in class */
    public String mo224a() {
        return "100886";
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (a()) {
                com.xiaomi.channel.commonutils.logger.b.c(this.f23521a.getPackageName() + " begin upload event");
                com.xiaomi.clientreport.manager.a.a(this.f23521a).m103b();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    private boolean a() {
        return com.xiaomi.clientreport.manager.a.a(this.f23521a).m101a().isEventUploadSwitchOpen();
    }
}
