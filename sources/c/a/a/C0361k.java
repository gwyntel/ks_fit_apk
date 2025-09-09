package c.a.a;

import java.math.BigInteger;

/* renamed from: c.a.a.k, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0361k extends r {

    /* renamed from: a, reason: collision with root package name */
    public final byte[] f7957a;

    public C0361k(long j2) {
        this.f7957a = BigInteger.valueOf(j2).toByteArray();
    }

    public static boolean b(byte[] bArr) {
        if (bArr.length > 1) {
            byte b2 = bArr[0];
            if (b2 == 0 && (bArr[1] & 128) == 0) {
                return true;
            }
            if (b2 == -1 && (bArr[1] & 128) != 0) {
                return true;
            }
        }
        return false;
    }

    public static C0361k getInstance(Object obj) {
        if (obj == null || (obj instanceof C0361k)) {
            return (C0361k) obj;
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
        try {
            return (C0361k) r.a((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException("encoding error in getInstance: " + e2.toString());
        }
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(2, this.f7957a);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7957a.length) + 1 + this.f7957a.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public BigInteger g() {
        return new BigInteger(1, this.f7957a);
    }

    public BigInteger getValue() {
        return new BigInteger(this.f7957a);
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.f7957a;
            if (i2 == bArr.length) {
                return i3;
            }
            i3 ^= (bArr[i2] & 255) << (i2 % 4);
            i2++;
        }
    }

    public String toString() {
        return getValue().toString();
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof C0361k) {
            return c.a.d.a.a(this.f7957a, ((C0361k) rVar).f7957a);
        }
        return false;
    }

    public C0361k(BigInteger bigInteger) {
        this.f7957a = bigInteger.toByteArray();
    }

    public C0361k(byte[] bArr, boolean z2) {
        if (!c.a.d.j.b("org.spongycastle.asn1.allow_unsafe_integer") && b(bArr)) {
            throw new IllegalArgumentException("malformed integer");
        }
        this.f7957a = z2 ? c.a.d.a.a(bArr) : bArr;
    }
}
