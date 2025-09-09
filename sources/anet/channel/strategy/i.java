package anet.channel.strategy;

/* loaded from: classes2.dex */
class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ g f7015a;

    i(g gVar) {
        this.f7015a = gVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f7015a.a()) {
            return;
        }
        this.f7015a.f7010b.c();
    }
}
