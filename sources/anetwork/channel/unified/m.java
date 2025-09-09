package anetwork.channel.unified;

import anetwork.channel.unified.k.a;

/* loaded from: classes2.dex */
class m implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ k f7298a;

    m(k kVar) {
        this.f7298a = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        k kVar = this.f7298a;
        kVar.new a(0, kVar.f7292a.f7286a.a(), this.f7298a.f7292a.f7287b).proceed(this.f7298a.f7292a.f7286a.a(), this.f7298a.f7292a.f7287b);
    }
}
