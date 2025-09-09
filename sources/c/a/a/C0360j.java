package c.a.a;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1Exception;

/* renamed from: c.a.a.j, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0360j extends FilterInputStream implements N {

    /* renamed from: a, reason: collision with root package name */
    public final int f7949a;

    /* renamed from: b, reason: collision with root package name */
    public final boolean f7950b;

    /* renamed from: c, reason: collision with root package name */
    public final byte[][] f7951c;

    public C0360j(InputStream inputStream) {
        this(inputStream, Ba.a(inputStream));
    }

    public r a(int i2, int i3, int i4) throws IOException {
        boolean z2 = (i2 & 32) != 0;
        ua uaVar = new ua(this, i4);
        if ((i2 & 64) != 0) {
            return new P(z2, i3, uaVar.b());
        }
        if ((i2 & 128) != 0) {
            return new C0372w(uaVar).a(z2, i3);
        }
        if (!z2) {
            return a(i3, uaVar, this.f7951c);
        }
        if (i3 == 4) {
            C0347g c0347gA = a(uaVar);
            int iA = c0347gA.a();
            AbstractC0365o[] abstractC0365oArr = new AbstractC0365o[iA];
            for (int i5 = 0; i5 != iA; i5++) {
                abstractC0365oArr[i5] = (AbstractC0365o) c0347gA.a(i5);
            }
            return new F(abstractC0365oArr);
        }
        if (i3 == 8) {
            return new T(a(uaVar));
        }
        if (i3 == 16) {
            return this.f7950b ? new ya(uaVar.b()) : V.a(a(uaVar));
        }
        if (i3 == 17) {
            return V.b(a(uaVar));
        }
        throw new IOException("unknown tag " + i3 + " encountered");
    }

    public int b() {
        return this.f7949a;
    }

    public int c() {
        return a(this, this.f7949a);
    }

    public r d() {
        int i2 = read();
        if (i2 <= 0) {
            if (i2 != 0) {
                return null;
            }
            throw new IOException("unexpected end-of-contents marker");
        }
        int iB = b(this, i2);
        boolean z2 = (i2 & 32) != 0;
        int iC = c();
        if (iC >= 0) {
            try {
                return a(i2, iB, iC);
            } catch (IllegalArgumentException e2) {
                throw new ASN1Exception("corrupted stream detected", e2);
            }
        }
        if (!z2) {
            throw new IOException("indefinite-length primitive encoding encountered");
        }
        C0372w c0372w = new C0372w(new wa(this, this.f7949a), this.f7949a);
        if ((i2 & 64) != 0) {
            return new C(iB, c0372w).a();
        }
        if ((i2 & 128) != 0) {
            return new M(true, iB, c0372w).a();
        }
        if (iB == 4) {
            return new G(c0372w).a();
        }
        if (iB == 8) {
            return new U(c0372w).a();
        }
        if (iB == 16) {
            return new I(c0372w).a();
        }
        if (iB == 17) {
            return new K(c0372w).a();
        }
        throw new IOException("unknown BER object encountered");
    }

    public C0360j(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }

    public static int b(InputStream inputStream, int i2) throws IOException {
        int i3 = i2 & 31;
        if (i3 != 31) {
            return i3;
        }
        int i4 = inputStream.read();
        if ((i4 & 127) == 0) {
            throw new IOException("corrupted stream - invalid high tag number found");
        }
        int i5 = 0;
        while (i4 >= 0 && (i4 & 128) != 0) {
            i5 = ((i4 & 127) | i5) << 7;
            i4 = inputStream.read();
        }
        if (i4 >= 0) {
            return i5 | (i4 & 127);
        }
        throw new EOFException("EOF found inside tag value.");
    }

    public C0360j(byte[] bArr, boolean z2) {
        this(new ByteArrayInputStream(bArr), bArr.length, z2);
    }

    public C0360j(InputStream inputStream, int i2) {
        this(inputStream, i2, false);
    }

    public C0360j(InputStream inputStream, int i2, boolean z2) {
        super(inputStream);
        this.f7949a = i2;
        this.f7950b = z2;
        this.f7951c = new byte[11][];
    }

    public static char[] b(ua uaVar) throws IOException {
        int i2;
        int iA = uaVar.a() / 2;
        char[] cArr = new char[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            int i4 = uaVar.read();
            if (i4 < 0 || (i2 = uaVar.read()) < 0) {
                break;
            }
            cArr[i3] = (char) ((i4 << 8) | (i2 & 255));
        }
        return cArr;
    }

    public C0347g a() {
        C0347g c0347g = new C0347g();
        while (true) {
            r rVarD = d();
            if (rVarD == null) {
                return c0347g;
            }
            c0347g.a(rVarD);
        }
    }

    public C0347g a(ua uaVar) {
        return new C0360j(uaVar).a();
    }

    public static int a(InputStream inputStream, int i2) throws IOException {
        int i3 = inputStream.read();
        if (i3 < 0) {
            throw new EOFException("EOF found when length expected");
        }
        if (i3 == 128) {
            return -1;
        }
        if (i3 <= 127) {
            return i3;
        }
        int i4 = i3 & 127;
        if (i4 > 4) {
            throw new IOException("DER length more than 4 bytes: " + i4);
        }
        int i5 = 0;
        for (int i6 = 0; i6 < i4; i6++) {
            int i7 = inputStream.read();
            if (i7 < 0) {
                throw new EOFException("EOF found reading length");
            }
            i5 = (i5 << 8) + i7;
        }
        if (i5 < 0) {
            throw new IOException("corrupted stream - negative length found");
        }
        if (i5 < i2) {
            return i5;
        }
        throw new IOException("corrupted stream - out of bounds length found");
    }

    public static byte[] a(ua uaVar, byte[][] bArr) {
        int iA = uaVar.a();
        if (uaVar.a() < bArr.length) {
            byte[] bArr2 = bArr[iA];
            if (bArr2 == null) {
                bArr2 = new byte[iA];
                bArr[iA] = bArr2;
            }
            c.a.d.b.a.a(uaVar, bArr2);
            return bArr2;
        }
        return uaVar.b();
    }

    public static r a(int i2, ua uaVar, byte[][] bArr) throws IOException {
        if (i2 == 10) {
            return C0348h.b(a(uaVar, bArr));
        }
        if (i2 == 12) {
            return new la(uaVar.b());
        }
        if (i2 != 30) {
            switch (i2) {
                case 1:
                    return C0344d.b(a(uaVar, bArr));
                case 2:
                    return new C0361k(uaVar.b(), false);
                case 3:
                    return AbstractC0343c.a(uaVar.a(), uaVar);
                case 4:
                    return new C0342ba(uaVar.b());
                case 5:
                    return Z.f7690a;
                case 6:
                    return C0364n.b(a(uaVar, bArr));
                default:
                    switch (i2) {
                        case 18:
                            return new C0340aa(uaVar.b());
                        case 19:
                            return new ea(uaVar.b());
                        case 20:
                            return new ja(uaVar.b());
                        case 21:
                            return new na(uaVar.b());
                        case 22:
                            return new Y(uaVar.b());
                        case 23:
                            return new A(uaVar.b());
                        case 24:
                            return new C0359i(uaVar.b());
                        case 25:
                            return new X(uaVar.b());
                        case 26:
                            return new oa(uaVar.b());
                        case 27:
                            return new W(uaVar.b());
                        case 28:
                            return new ma(uaVar.b());
                        default:
                            throw new IOException("unknown tag " + i2 + " encountered");
                    }
            }
        }
        return new Q(b(uaVar));
    }
}
