package c.a.a;

import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class r extends AbstractC0363m {
    public static r a(byte[] bArr) throws IOException {
        C0360j c0360j = new C0360j(bArr);
        try {
            r rVarD = c0360j.d();
            if (c0360j.available() == 0) {
                return rVarD;
            }
            throw new IOException("Extra data detected in stream");
        } catch (ClassCastException unused) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    public abstract void a(C0367q c0367q);

    public abstract boolean a(r rVar);

    public abstract int c();

    public abstract boolean d();

    public r e() {
        return this;
    }

    @Override // c.a.a.AbstractC0363m
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof InterfaceC0346f) && a(((InterfaceC0346f) obj).toASN1Primitive());
    }

    public r f() {
        return this;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        return this;
    }
}
