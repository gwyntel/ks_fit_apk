package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
public class bz extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    private Context f23522a;

    public bz(Context context) {
        this.f23522a = context;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return "100887";
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (a()) {
                com.xiaomi.clientreport.manager.a.a(this.f23522a).c();
                com.xiaomi.channel.commonutils.logger.b.c(this.f23522a.getPackageName() + " perf begin upload");
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("fail to send perf data. " + e2);
        }
    }

    private boolean a() {
        return com.xiaomi.clientreport.manager.a.a(this.f23522a).m101a().isPerfUploadSwitchOpen();
    }
}
