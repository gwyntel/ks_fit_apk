package b.e;

import b.e.e;

/* loaded from: classes2.dex */
public class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ e.a f7423a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f7424b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f7425c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ byte[] f7426d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ byte[] f7427e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ int f7428f;

    /* renamed from: g, reason: collision with root package name */
    public final /* synthetic */ e f7429g;

    public b(e eVar, e.a aVar, int i2, int i3, byte[] bArr, byte[] bArr2, int i4) {
        this.f7429g = eVar;
        this.f7423a = aVar;
        this.f7424b = i2;
        this.f7425c = i3;
        this.f7426d = bArr;
        this.f7427e = bArr2;
        this.f7428f = i4;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f7429g.c(this.f7423a, this.f7424b, this.f7425c, this.f7426d, this.f7427e, this.f7428f);
    }
}
