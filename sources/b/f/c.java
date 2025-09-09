package b.f;

/* loaded from: classes2.dex */
public class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f7466a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f7467b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ byte[] f7468c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ byte[] f7469d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ int f7470e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ e f7471f;

    public c(e eVar, int i2, int i3, byte[] bArr, byte[] bArr2, int i4) {
        this.f7471f = eVar;
        this.f7466a = i2;
        this.f7467b = i3;
        this.f7468c = bArr;
        this.f7469d = bArr2;
        this.f7470e = i4;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f7471f.sendBlockAck(this.f7466a, this.f7467b, this.f7468c, this.f7469d, this.f7470e);
    }
}
