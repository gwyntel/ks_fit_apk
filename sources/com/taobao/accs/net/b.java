package com.taobao.accs.net;

import com.taobao.accs.data.Message;
import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f20209a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ boolean f20210b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ a f20211c;

    b(a aVar, String str, boolean z2) {
        this.f20211c = aVar;
        this.f20209a = str;
        this.f20210b = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        Message messageA = this.f20211c.f20195e.a(this.f20209a);
        if (messageA != null) {
            this.f20211c.f20195e.a(messageA, -9);
            this.f20211c.a(this.f20209a, this.f20210b, "receive data time out");
            ALog.e(this.f20211c.d(), this.f20209a + "-> receive data time out!", new Object[0]);
        }
    }
}
