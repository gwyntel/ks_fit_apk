package anet.channel;

import anet.channel.SessionRequest;

/* loaded from: classes2.dex */
class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Session f6807a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ SessionRequest.a f6808b;

    i(SessionRequest.a aVar, Session session) {
        this.f6808b = aVar;
        this.f6807a = session;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            SessionRequest.a aVar = this.f6808b;
            SessionRequest.this.a(aVar.f6669c, this.f6807a.getConnType().getType(), anet.channel.util.i.a(SessionRequest.this.f6655a.f6646c), (SessionGetCallback) null, 0L);
        } catch (Exception unused) {
        }
    }
}
