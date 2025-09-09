package b;

import b.K;

/* loaded from: classes2.dex */
public class J implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ K.a.b f7313a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ K.a f7314b;

    public J(K.a aVar, K.a.b bVar) {
        this.f7314b = aVar;
        this.f7313a = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Runnable runnable = this.f7313a.f7347c;
        if (runnable != null) {
            runnable.run();
        }
        this.f7314b.f7343c = null;
        this.f7314b.a(this.f7313a.f7348d);
    }
}
