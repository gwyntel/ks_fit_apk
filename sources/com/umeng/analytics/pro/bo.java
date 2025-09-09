package com.umeng.analytics.pro;

/* loaded from: classes4.dex */
public class bo {

    /* renamed from: a, reason: collision with root package name */
    private short[] f21522a;

    /* renamed from: b, reason: collision with root package name */
    private int f21523b = -1;

    public bo(int i2) {
        this.f21522a = new short[i2];
    }

    private void d() {
        short[] sArr = this.f21522a;
        short[] sArr2 = new short[sArr.length * 2];
        System.arraycopy(sArr, 0, sArr2, 0, sArr.length);
        this.f21522a = sArr2;
    }

    public short a() {
        short[] sArr = this.f21522a;
        int i2 = this.f21523b;
        this.f21523b = i2 - 1;
        return sArr[i2];
    }

    public short b() {
        return this.f21522a[this.f21523b];
    }

    public void c() {
        this.f21523b = -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ShortStack vector:[");
        for (int i2 = 0; i2 < this.f21522a.length; i2++) {
            if (i2 != 0) {
                sb.append(" ");
            }
            if (i2 == this.f21523b) {
                sb.append(">>");
            }
            sb.append((int) this.f21522a[i2]);
            if (i2 == this.f21523b) {
                sb.append("<<");
            }
        }
        sb.append("]>");
        return sb.toString();
    }

    public void a(short s2) {
        if (this.f21522a.length == this.f21523b + 1) {
            d();
        }
        short[] sArr = this.f21522a;
        int i2 = this.f21523b + 1;
        this.f21523b = i2;
        sArr[i2] = s2;
    }
}
