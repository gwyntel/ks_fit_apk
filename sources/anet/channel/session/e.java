package anet.channel.session;

import anet.channel.RequestCb;
import anet.channel.request.Request;

/* loaded from: classes2.dex */
class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Request f6913a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ d f6914b;

    e(d dVar, Request request) {
        this.f6914b = dVar;
        this.f6913a = request;
    }

    @Override // java.lang.Runnable
    public void run() {
        int i2 = b.a(this.f6913a, (RequestCb) null).f6906a;
        if (i2 > 0) {
            this.f6914b.notifyStatus(4, new anet.channel.entity.b(1));
        } else {
            this.f6914b.handleCallbacks(256, new anet.channel.entity.b(256, i2, "Http connect fail"));
        }
    }
}
