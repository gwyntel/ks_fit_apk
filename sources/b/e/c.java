package b.e;

import b.e.e;

/* loaded from: classes2.dex */
public class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ e.a f7430a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f7431b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f7432c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ byte[] f7433d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ byte[] f7434e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ int f7435f;

    /* renamed from: g, reason: collision with root package name */
    public final /* synthetic */ e f7436g;

    public c(e eVar, e.a aVar, int i2, int i3, byte[] bArr, byte[] bArr2, int i4) {
        this.f7436g = eVar;
        this.f7430a = aVar;
        this.f7431b = i2;
        this.f7432c = i3;
        this.f7433d = bArr;
        this.f7434e = bArr2;
        this.f7435f = i4;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f7436g.c(this.f7430a, this.f7431b, this.f7432c, this.f7433d, this.f7434e, this.f7435f);
    }
}
