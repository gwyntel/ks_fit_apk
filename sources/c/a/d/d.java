package c.a.d;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static char[] f8120a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: b, reason: collision with root package name */
    public final byte[] f8121b;

    public d(byte[] bArr) {
        this.f8121b = a(bArr);
    }

    public static byte[] a(byte[] bArr) {
        c.a.b.b.c cVar = new c.a.b.b.c(160);
        cVar.a(bArr, 0, bArr.length);
        byte[] bArr2 = new byte[cVar.e()];
        cVar.b(bArr2, 0);
        return bArr2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof d) {
            return a.a(((d) obj).f8121b, this.f8121b);
        }
        return false;
    }

    public int hashCode() {
        return a.b(this.f8121b);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 != this.f8121b.length; i2++) {
            if (i2 > 0) {
                stringBuffer.append(":");
            }
            stringBuffer.append(f8120a[(this.f8121b[i2] >>> 4) & 15]);
            stringBuffer.append(f8120a[this.f8121b[i2] & 15]);
        }
        return stringBuffer.toString();
    }
}
