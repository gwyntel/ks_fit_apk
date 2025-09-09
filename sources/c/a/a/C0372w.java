package c.a.a;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1Exception;

/* renamed from: c.a.a.w, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0372w {

    /* renamed from: a, reason: collision with root package name */
    public final InputStream f7982a;

    /* renamed from: b, reason: collision with root package name */
    public final int f7983b;

    /* renamed from: c, reason: collision with root package name */
    public final byte[][] f7984c;

    public C0372w(InputStream inputStream) {
        this(inputStream, Ba.a(inputStream));
    }

    public InterfaceC0346f a(int i2) throws ASN1Exception {
        if (i2 == 4) {
            return new G(this);
        }
        if (i2 == 8) {
            return new U(this);
        }
        if (i2 == 16) {
            return new I(this);
        }
        if (i2 == 17) {
            return new K(this);
        }
        throw new ASN1Exception("unknown BER object encountered: 0x" + Integer.toHexString(i2));
    }

    public C0347g b() throws IOException {
        C0347g c0347g = new C0347g();
        while (true) {
            InterfaceC0346f interfaceC0346fA = a();
            if (interfaceC0346fA == null) {
                return c0347g;
            }
            if (interfaceC0346fA instanceof va) {
                c0347g.a(((va) interfaceC0346fA).a());
            } else {
                c0347g.a(interfaceC0346fA.toASN1Primitive());
            }
        }
    }

    public C0372w(InputStream inputStream, int i2) {
        this.f7982a = inputStream;
        this.f7983b = i2;
        this.f7984c = new byte[11][];
    }

    public r a(boolean z2, int i2) throws IOException {
        if (!z2) {
            return new ka(false, i2, new C0342ba(((ua) this.f7982a).b()));
        }
        C0347g c0347gB = b();
        if (this.f7982a instanceof wa) {
            if (c0347gB.a() == 1) {
                return new L(true, i2, c0347gB.a(0));
            }
            return new L(false, i2, D.a(c0347gB));
        }
        if (c0347gB.a() == 1) {
            return new ka(true, i2, c0347gB.a(0));
        }
        return new ka(false, i2, V.a(c0347gB));
    }

    public InterfaceC0346f a() throws IOException {
        int i2 = this.f7982a.read();
        if (i2 == -1) {
            return null;
        }
        a(false);
        int iB = C0360j.b(this.f7982a, i2);
        boolean z2 = (i2 & 32) != 0;
        int iA = C0360j.a(this.f7982a, this.f7983b);
        if (iA < 0) {
            if (z2) {
                C0372w c0372w = new C0372w(new wa(this.f7982a, this.f7983b), this.f7983b);
                if ((i2 & 64) != 0) {
                    return new C(iB, c0372w);
                }
                if ((i2 & 128) != 0) {
                    return new M(true, iB, c0372w);
                }
                return c0372w.a(iB);
            }
            throw new IOException("indefinite-length primitive encoding encountered");
        }
        ua uaVar = new ua(this.f7982a, iA);
        if ((i2 & 64) != 0) {
            return new P(z2, iB, uaVar.b());
        }
        if ((i2 & 128) != 0) {
            return new M(z2, iB, new C0372w(uaVar));
        }
        if (!z2) {
            if (iB != 4) {
                try {
                    return C0360j.a(iB, uaVar, this.f7984c);
                } catch (IllegalArgumentException e2) {
                    throw new ASN1Exception("corrupted stream detected", e2);
                }
            }
            return new ca(uaVar);
        }
        if (iB == 4) {
            return new G(new C0372w(uaVar));
        }
        if (iB == 8) {
            return new U(new C0372w(uaVar));
        }
        if (iB == 16) {
            return new ga(new C0372w(uaVar));
        }
        if (iB == 17) {
            return new ia(new C0372w(uaVar));
        }
        throw new IOException("unknown tag " + iB + " encountered");
    }

    public final void a(boolean z2) {
        InputStream inputStream = this.f7982a;
        if (inputStream instanceof wa) {
            ((wa) inputStream).b(z2);
        }
    }
}
