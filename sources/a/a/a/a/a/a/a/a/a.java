package a.a.a.a.a.a.a.a;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f1131a;

    /* renamed from: b, reason: collision with root package name */
    public byte f1132b;

    /* renamed from: c, reason: collision with root package name */
    public byte f1133c;

    /* renamed from: d, reason: collision with root package name */
    public int f1134d;

    /* renamed from: e, reason: collision with root package name */
    public int f1135e;

    /* renamed from: f, reason: collision with root package name */
    public byte[] f1136f;

    public a(byte[] bArr) {
        this.f1131a = bArr;
    }

    public static a a(byte[] bArr) {
        if (bArr == 0) {
            a.a.a.a.b.m.a.b("InexpensiveControlRsp", "data is null");
            return null;
        }
        if (bArr.length < 6) {
            a.a.a.a.b.m.a.b("InexpensiveControlRsp", "data length illegal. " + bArr.length);
            return null;
        }
        a aVar = new a(bArr);
        aVar.f1132b = bArr[1];
        aVar.f1133c = bArr[2];
        int i2 = bArr[3];
        int i3 = (i2 >> 4) & 15;
        aVar.f1134d = i3;
        int i4 = i2 & 15;
        aVar.f1135e = i4;
        if (i4 <= i3) {
            int i5 = bArr[4];
            if (i5 > 0) {
                byte[] bArr2 = new byte[i5];
                aVar.f1136f = bArr2;
                System.arraycopy(bArr, 5, bArr2, 0, i5);
            }
            return aVar;
        }
        a.a.a.a.b.m.a.b("InexpensiveControlRsp", "package number illegal: totalNumber = " + aVar.f1134d + ", curIndex = " + aVar.f1135e);
        return null;
    }

    public byte[] b() {
        return this.f1136f;
    }

    public byte c() {
        return this.f1133c;
    }

    public byte d() {
        return this.f1132b;
    }

    public int e() {
        return this.f1134d;
    }

    public int a() {
        return this.f1135e;
    }
}
