package c.a.a;

import java.io.ByteArrayOutputStream;

/* renamed from: c.a.a.m, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract class AbstractC0363m implements InterfaceC0346f, c.a.d.c {
    public static boolean hasEncodedTagValue(Object obj, int i2) {
        return (obj instanceof byte[]) && ((byte[]) obj)[0] == i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof InterfaceC0346f) {
            return toASN1Primitive().equals(((InterfaceC0346f) obj).toASN1Primitive());
        }
        return false;
    }

    public byte[] getEncoded() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new C0367q(byteArrayOutputStream).a(this);
        return byteArrayOutputStream.toByteArray();
    }

    public int hashCode() {
        return toASN1Primitive().hashCode();
    }

    public r toASN1Object() {
        return toASN1Primitive();
    }

    @Override // c.a.a.InterfaceC0346f
    public abstract r toASN1Primitive();

    public byte[] getEncoded(String str) {
        if (str.equals("DER")) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new da(byteArrayOutputStream).a(this);
            return byteArrayOutputStream.toByteArray();
        }
        if (str.equals("DL")) {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            new qa(byteArrayOutputStream2).a(this);
            return byteArrayOutputStream2.toByteArray();
        }
        return getEncoded();
    }
}
