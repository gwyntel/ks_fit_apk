package com.xiaomi.push;

import com.xiaomi.push.kf;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class kp extends kf {

    /* renamed from: b, reason: collision with root package name */
    private static int f24388b = 10000;

    /* renamed from: c, reason: collision with root package name */
    private static int f24389c = 10000;

    /* renamed from: d, reason: collision with root package name */
    private static int f24390d = 10000;

    /* renamed from: e, reason: collision with root package name */
    private static int f24391e = 10485760;

    /* renamed from: f, reason: collision with root package name */
    private static int f24392f = 104857600;

    public static class a extends kf.a {
        public a() {
            super(false, true);
        }

        @Override // com.xiaomi.push.kf.a, com.xiaomi.push.kl
        public kj a(kt ktVar) {
            kp kpVar = new kp(ktVar, ((kf.a) this).f920a, this.f24378b);
            int i2 = ((kf.a) this).f24377a;
            if (i2 != 0) {
                kpVar.b(i2);
            }
            return kpVar;
        }

        public a(boolean z2, boolean z3, int i2) {
            super(z2, z3, i2);
        }
    }

    public kp(kt ktVar, boolean z2, boolean z3) {
        super(ktVar, z2, z3);
    }

    @Override // com.xiaomi.push.kf, com.xiaomi.push.kj
    /* renamed from: a */
    public ki mo672a() throws kd {
        byte bA = a();
        byte bA2 = a();
        int iMo668a = mo668a();
        if (iMo668a <= f24388b) {
            return new ki(bA, bA2, iMo668a);
        }
        throw new kk(3, "Thrift map size " + iMo668a + " out of range!");
    }

    @Override // com.xiaomi.push.kf, com.xiaomi.push.kj
    /* renamed from: a */
    public kh mo671a() throws kd {
        byte bA = a();
        int iMo668a = mo668a();
        if (iMo668a <= f24389c) {
            return new kh(bA, iMo668a);
        }
        throw new kk(3, "Thrift list size " + iMo668a + " out of range!");
    }

    @Override // com.xiaomi.push.kf, com.xiaomi.push.kj
    /* renamed from: a */
    public kn mo673a() throws kd {
        byte bA = a();
        int iMo668a = mo668a();
        if (iMo668a <= f24390d) {
            return new kn(bA, iMo668a);
        }
        throw new kk(3, "Thrift set size " + iMo668a + " out of range!");
    }

    @Override // com.xiaomi.push.kf, com.xiaomi.push.kj
    /* renamed from: a */
    public String mo675a() throws kd {
        int iMo668a = mo668a();
        if (iMo668a <= f24391e) {
            if (((kj) this).f24383a.b() >= iMo668a) {
                try {
                    String str = new String(((kj) this).f24383a.mo682a(), ((kj) this).f24383a.a(), iMo668a, "UTF-8");
                    ((kj) this).f24383a.a(iMo668a);
                    return str;
                } catch (UnsupportedEncodingException unused) {
                    throw new kd("JVM DOES NOT SUPPORT UTF-8");
                }
            }
            return a(iMo668a);
        }
        throw new kk(3, "Thrift string size " + iMo668a + " out of range!");
    }

    @Override // com.xiaomi.push.kf, com.xiaomi.push.kj
    /* renamed from: a */
    public ByteBuffer mo676a() throws kd {
        int iMo668a = mo668a();
        if (iMo668a <= f24392f) {
            c(iMo668a);
            if (((kj) this).f24383a.b() >= iMo668a) {
                ByteBuffer byteBufferWrap = ByteBuffer.wrap(((kj) this).f24383a.mo682a(), ((kj) this).f24383a.a(), iMo668a);
                ((kj) this).f24383a.a(iMo668a);
                return byteBufferWrap;
            }
            byte[] bArr = new byte[iMo668a];
            ((kj) this).f24383a.b(bArr, 0, iMo668a);
            return ByteBuffer.wrap(bArr);
        }
        throw new kk(3, "Thrift binary size " + iMo668a + " out of range!");
    }
}
