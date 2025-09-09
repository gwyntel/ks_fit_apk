package com.vivo.push;

import android.os.Handler;
import android.os.Message;

/* loaded from: classes4.dex */
final class c implements Handler.Callback {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ b f23075a;

    c(b bVar) {
        this.f23075a = bVar;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        if (message == null) {
            com.vivo.push.util.p.a("AidlManager", "handleMessage error : msg is null");
            return false;
        }
        int i2 = message.what;
        if (i2 == 1) {
            com.vivo.push.util.p.a("AidlManager", "In connect, bind core service time out");
            if (this.f23075a.f23024f.get() == 2) {
                this.f23075a.a(1);
            }
        } else if (i2 != 2) {
            com.vivo.push.util.p.b("AidlManager", "unknow msg what [" + message.what + "]");
        } else {
            if (this.f23075a.f23024f.get() == 4) {
                this.f23075a.f();
            }
            this.f23075a.a(1);
        }
        return true;
    }
}
