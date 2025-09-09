package c.a.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* renamed from: c.a.a.n, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0364n extends r {

    /* renamed from: a, reason: collision with root package name */
    public static final ConcurrentMap<a, C0364n> f7962a = new ConcurrentHashMap();

    /* renamed from: b, reason: collision with root package name */
    public final String f7963b;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f7964c;

    /* renamed from: c.a.a.n$a */
    private static class a {

        /* renamed from: a, reason: collision with root package name */
        public final int f7965a;

        /* renamed from: b, reason: collision with root package name */
        public final byte[] f7966b;

        public a(byte[] bArr) {
            this.f7965a = c.a.d.a.b(bArr);
            this.f7966b = bArr;
        }

        public boolean equals(Object obj) {
            if (obj instanceof a) {
                return c.a.d.a.a(this.f7966b, ((a) obj).f7966b);
            }
            return false;
        }

        public int hashCode() {
            return this.f7965a;
        }
    }

    public C0364n(byte[] bArr) {
        int i2;
        StringBuffer stringBuffer = new StringBuffer();
        boolean z2 = true;
        BigInteger bigIntegerShiftLeft = null;
        int i3 = 0;
        long j2 = 0;
        while (i3 != bArr.length) {
            byte b2 = bArr[i3];
            if (j2 <= 72057594037927808L) {
                i2 = i3;
                long j3 = j2 + (b2 & Byte.MAX_VALUE);
                if ((b2 & 128) == 0) {
                    if (z2) {
                        if (j3 < 40) {
                            stringBuffer.append('0');
                        } else if (j3 < 80) {
                            stringBuffer.append('1');
                            j3 -= 40;
                        } else {
                            stringBuffer.append('2');
                            j3 -= 80;
                        }
                        z2 = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(j3);
                    j2 = 0;
                } else {
                    j2 = j3 << 7;
                }
            } else {
                i2 = i3;
                BigInteger bigIntegerOr = (bigIntegerShiftLeft == null ? BigInteger.valueOf(j2) : bigIntegerShiftLeft).or(BigInteger.valueOf(b2 & Byte.MAX_VALUE));
                if ((b2 & 128) == 0) {
                    if (z2) {
                        stringBuffer.append('2');
                        bigIntegerOr = bigIntegerOr.subtract(BigInteger.valueOf(80L));
                        z2 = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(bigIntegerOr);
                    bigIntegerShiftLeft = null;
                    j2 = 0;
                } else {
                    bigIntegerShiftLeft = bigIntegerOr.shiftLeft(7);
                }
            }
            i3 = i2 + 1;
        }
        this.f7963b = stringBuffer.toString();
        this.f7964c = c.a.d.a.a(bArr);
    }

    public static boolean b(String str) {
        char cCharAt;
        if (str.length() < 3 || str.charAt(1) != '.' || (cCharAt = str.charAt(0)) < '0' || cCharAt > '2') {
            return false;
        }
        return a(str, 2);
    }

    public static C0364n getInstance(Object obj) {
        if (obj == null || (obj instanceof C0364n)) {
            return (C0364n) obj;
        }
        if (obj instanceof InterfaceC0346f) {
            InterfaceC0346f interfaceC0346f = (InterfaceC0346f) obj;
            if (interfaceC0346f.toASN1Primitive() instanceof C0364n) {
                return (C0364n) interfaceC0346f.toASN1Primitive();
            }
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
        try {
            return (C0364n) r.a((byte[]) obj);
        } catch (IOException e2) {
            throw new IllegalArgumentException("failed to construct object identifier from byte[]: " + e2.getMessage());
        }
    }

    public C0364n a(String str) {
        return new C0364n(this, str);
    }

    @Override // c.a.a.r
    public int c() {
        int length = g().length;
        return Ba.a(length) + 1 + length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public final synchronized byte[] g() {
        try {
            if (this.f7964c == null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                a(byteArrayOutputStream);
                this.f7964c = byteArrayOutputStream.toByteArray();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f7964c;
    }

    public String h() {
        return this.f7963b;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return this.f7963b.hashCode();
    }

    public String toString() {
        return h();
    }

    public final void a(ByteArrayOutputStream byteArrayOutputStream, long j2) {
        byte[] bArr = new byte[9];
        int i2 = 8;
        bArr[8] = (byte) (((int) j2) & 127);
        while (j2 >= 128) {
            j2 >>= 7;
            i2--;
            bArr[i2] = (byte) ((((int) j2) & 127) | 128);
        }
        byteArrayOutputStream.write(bArr, i2, 9 - i2);
    }

    public static C0364n b(byte[] bArr) {
        C0364n c0364n = f7962a.get(new a(bArr));
        return c0364n == null ? new C0364n(bArr) : c0364n;
    }

    public final void a(ByteArrayOutputStream byteArrayOutputStream, BigInteger bigInteger) {
        int iBitLength = (bigInteger.bitLength() + 6) / 7;
        if (iBitLength == 0) {
            byteArrayOutputStream.write(0);
            return;
        }
        byte[] bArr = new byte[iBitLength];
        int i2 = iBitLength - 1;
        for (int i3 = i2; i3 >= 0; i3--) {
            bArr[i3] = (byte) ((bigInteger.intValue() & 127) | 128);
            bigInteger = bigInteger.shiftRight(7);
        }
        bArr[i2] = (byte) (bArr[i2] & Byte.MAX_VALUE);
        byteArrayOutputStream.write(bArr, 0, iBitLength);
    }

    public final void a(ByteArrayOutputStream byteArrayOutputStream) {
        Aa aa = new Aa(this.f7963b);
        int i2 = Integer.parseInt(aa.b()) * 40;
        String strB = aa.b();
        if (strB.length() <= 18) {
            a(byteArrayOutputStream, i2 + Long.parseLong(strB));
        } else {
            a(byteArrayOutputStream, new BigInteger(strB).add(BigInteger.valueOf(i2)));
        }
        while (aa.a()) {
            String strB2 = aa.b();
            if (strB2.length() <= 18) {
                a(byteArrayOutputStream, Long.parseLong(strB2));
            } else {
                a(byteArrayOutputStream, new BigInteger(strB2));
            }
        }
    }

    public C0364n(String str) {
        if (str != null) {
            if (b(str)) {
                this.f7963b = str;
                return;
            }
            throw new IllegalArgumentException("string " + str + " not an OID");
        }
        throw new IllegalArgumentException("'identifier' cannot be null");
    }

    public C0364n(C0364n c0364n, String str) {
        if (a(str, 0)) {
            this.f7963b = c0364n.h() + "." + str;
            return;
        }
        throw new IllegalArgumentException("string " + str + " not a valid OID branch");
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        byte[] bArrG = g();
        c0367q.a(6);
        c0367q.b(bArrG.length);
        c0367q.a(bArrG);
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar == this) {
            return true;
        }
        if (rVar instanceof C0364n) {
            return this.f7963b.equals(((C0364n) rVar).f7963b);
        }
        return false;
    }

    public static boolean a(String str, int i2) {
        boolean z2;
        char cCharAt;
        int length = str.length();
        do {
            z2 = false;
            while (true) {
                length--;
                if (length < i2) {
                    return z2;
                }
                cCharAt = str.charAt(length);
                if ('0' > cCharAt || cCharAt > '9') {
                    break;
                }
                z2 = true;
            }
            if (cCharAt != '.') {
                break;
            }
        } while (z2);
        return false;
    }
}
