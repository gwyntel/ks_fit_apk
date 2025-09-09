package com.taobao.accs.net;

import com.taobao.accs.data.Message;
import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class p implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f20248a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ boolean f20249b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ k f20250c;

    p(k kVar, String str, boolean z2) {
        this.f20250c = kVar;
        this.f20248a = str;
        this.f20249b = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        Message messageA = this.f20250c.f20195e.a(this.f20248a);
        if (messageA != null) {
            this.f20250c.f20195e.a(messageA, -9);
            this.f20250c.a(this.f20248a, this.f20249b, "receive data time out");
            ALog.e(this.f20250c.d(), this.f20248a + "-> receive data time out!", new Object[0]);
        }
    }
}
