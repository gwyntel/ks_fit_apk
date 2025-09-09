package com.xiaomi.push;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaomi.push.ak;

/* loaded from: classes4.dex */
class al extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ak f23440a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    al(ak akVar, Looper looper) {
        super(looper);
        this.f23440a = akVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        ak.b bVar = (ak.b) message.obj;
        int i2 = message.what;
        if (i2 == 0) {
            bVar.a();
        } else if (i2 == 1) {
            bVar.mo308c();
        }
        super.handleMessage(message);
    }
}
