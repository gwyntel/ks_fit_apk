package com.xiaomi.push;

/* loaded from: classes4.dex */
public abstract class kt {
    public int a() {
        return 0;
    }

    public abstract int a(byte[] bArr, int i2, int i3);

    /* renamed from: a */
    public abstract void mo681a(byte[] bArr, int i2, int i3);

    public int b() {
        return -1;
    }

    public void a(int i2) {
    }

    public int b(byte[] bArr, int i2, int i3) throws ku {
        int i4 = 0;
        while (i4 < i3) {
            int iA = a(bArr, i2 + i4, i3 - i4);
            if (iA <= 0) {
                throw new ku("Cannot read. Remote side has closed. Tried to read " + i3 + " bytes, but only got " + i4 + " bytes.");
            }
            i4 += iA;
        }
        return i4;
    }

    /* renamed from: a */
    public byte[] mo682a() {
        return null;
    }
}
