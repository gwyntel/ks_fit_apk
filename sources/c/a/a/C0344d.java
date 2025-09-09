package c.a.a;

/* renamed from: c.a.a.d, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0344d extends r {

    /* renamed from: a, reason: collision with root package name */
    public static final byte[] f7765a = {-1};

    /* renamed from: b, reason: collision with root package name */
    public static final byte[] f7766b = {0};

    /* renamed from: c, reason: collision with root package name */
    public static final C0344d f7767c = new C0344d(false);

    /* renamed from: d, reason: collision with root package name */
    public static final C0344d f7768d = new C0344d(true);

    /* renamed from: e, reason: collision with root package name */
    public final byte[] f7769e;

    public C0344d(byte[] bArr) {
        if (bArr.length != 1) {
            throw new IllegalArgumentException("byte value should have 1 byte in it");
        }
        byte b2 = bArr[0];
        if (b2 == 0) {
            this.f7769e = f7766b;
        } else if ((b2 & 255) == 255) {
            this.f7769e = f7765a;
        } else {
            this.f7769e = c.a.d.a.a(bArr);
        }
    }

    public static C0344d b(byte[] bArr) {
        if (bArr.length != 1) {
            throw new IllegalArgumentException("BOOLEAN value should have 1 byte in it");
        }
        byte b2 = bArr[0];
        return b2 == 0 ? f7767c : (b2 & 255) == 255 ? f7768d : new C0344d(bArr);
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(1, this.f7769e);
    }

    @Override // c.a.a.r
    public int c() {
        return 3;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return this.f7769e[0];
    }

    public String toString() {
        return this.f7769e[0] != 0 ? "TRUE" : "FALSE";
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        return (rVar instanceof C0344d) && this.f7769e[0] == ((C0344d) rVar).f7769e[0];
    }

    public C0344d(boolean z2) {
        this.f7769e = z2 ? f7765a : f7766b;
    }
}
