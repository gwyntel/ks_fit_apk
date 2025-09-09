package c.a.b.g;

/* loaded from: classes2.dex */
public class b implements a {
    @Override // c.a.b.g.a
    public int a(byte[] bArr, int i2) {
        int length = bArr.length - i2;
        bArr[i2] = Byte.MIN_VALUE;
        while (true) {
            i2++;
            if (i2 >= bArr.length) {
                return length;
            }
            bArr[i2] = 0;
        }
    }
}
