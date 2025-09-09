package a.a.a.a.b.i;

/* loaded from: classes.dex */
public class r implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Class f1496a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ u f1497b;

    public r(u uVar, Class cls) {
        this.f1497b = uVar;
        this.f1496a = cls;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f1496a.isInstance(this.f1497b.f1512m)) {
            return;
        }
        a.a.a.a.b.m.a.d(this.f1497b.f1500a, "wait " + this.f1496a.getSimpleName() + " resp timeout");
        u uVar = this.f1497b;
        uVar.onProvisioningFailed(uVar.f1506g, -63, "wait resp timeout, invalid connection may happen");
    }
}
