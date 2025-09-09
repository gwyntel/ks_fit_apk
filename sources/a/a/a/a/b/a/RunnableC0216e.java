package a.a.a.a.b.a;

/* renamed from: a.a.a.a.b.a.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class RunnableC0216e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0218g f1264a;

    public RunnableC0216e(C0218g c0218g) {
        this.f1264a = c0218g;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        if (this.f1264a.f1272f > 0) {
            try {
                Thread.sleep(this.f1264a.f1272f);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        a.a.a.a.b.m.a.a(C0218g.f1267a, "Real Dispatcher");
        this.f1264a.f1270d.c();
    }
}
