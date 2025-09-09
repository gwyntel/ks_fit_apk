package com.xiaomi.push.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/* loaded from: classes4.dex */
class cq extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24586a;

    cq(XMPushService xMPushService) {
        this.f24586a = xMPushService;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message != null) {
            try {
                int i2 = message.what;
                if (i2 == 17) {
                    Object obj = message.obj;
                    if (obj != null) {
                        this.f24586a.onStart((Intent) obj, 1);
                    }
                } else if (i2 == 18) {
                    Message messageObtain = Message.obtain((Handler) null, 0);
                    messageObtain.what = 18;
                    Bundle bundle = new Bundle();
                    bundle.putString("xmsf_region", a.a(this.f24586a.getApplicationContext()).a());
                    messageObtain.setData(bundle);
                    message.replyTo.send(messageObtain);
                }
            } catch (Throwable unused) {
            }
        }
    }
}
