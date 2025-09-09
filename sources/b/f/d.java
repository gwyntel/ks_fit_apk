package b.f;

/* loaded from: classes2.dex */
public class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f7472a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f7473b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ byte[] f7474c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ byte[] f7475d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ int f7476e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ e f7477f;

    public d(e eVar, int i2, int i3, byte[] bArr, byte[] bArr2, int i4) {
        this.f7477f = eVar;
        this.f7472a = i2;
        this.f7473b = i3;
        this.f7474c = bArr;
        this.f7475d = bArr2;
        this.f7476e = i4;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f7477f.sendBlockAck(this.f7472a, this.f7473b, this.f7474c, this.f7475d, this.f7476e);
    }
}
