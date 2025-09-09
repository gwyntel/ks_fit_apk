package com.umeng.analytics.pro;

/* loaded from: classes4.dex */
public abstract class dd {
    public abstract int a(byte[] bArr, int i2, int i3) throws de;

    public void a(int i2) {
    }

    public abstract boolean a();

    public abstract void b() throws de;

    public void b(byte[] bArr) throws de {
        b(bArr, 0, bArr.length);
    }

    public abstract void b(byte[] bArr, int i2, int i3) throws de;

    public abstract void c();

    public void d() throws de {
    }

    public byte[] f() {
        return null;
    }

    public int g() {
        return 0;
    }

    public int h() {
        return -1;
    }

    public boolean i() {
        return a();
    }

    public int d(byte[] bArr, int i2, int i3) throws de {
        int i4 = 0;
        while (i4 < i3) {
            int iA = a(bArr, i2 + i4, i3 - i4);
            if (iA <= 0) {
                throw new de("Cannot read. Remote side has closed. Tried to read " + i3 + " bytes, but only got " + i4 + " bytes. (This is often indicative of an internal error on the server side. Please check your server logs.)");
            }
            i4 += iA;
        }
        return i4;
    }
}
