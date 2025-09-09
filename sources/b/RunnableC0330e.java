package b;

/* renamed from: b.e, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0330e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ byte[] f7416a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ RunnableC0331f f7417b;

    public RunnableC0330e(RunnableC0331f runnableC0331f, byte[] bArr) {
        this.f7417b = runnableC0331f;
        this.f7416a = bArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f7417b.f7464c.f7499f.sendPdu(this.f7417b.f7463b, this.f7416a);
    }
}
