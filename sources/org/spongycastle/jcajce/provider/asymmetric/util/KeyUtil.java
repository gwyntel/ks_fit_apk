package org.spongycastle.jcajce.provider.asymmetric.util;

import c.a.a.InterfaceC0346f;
import c.a.a.j.a;
import c.a.a.j.b;

/* loaded from: classes5.dex */
public class KeyUtil {
    public static byte[] getEncodedPrivateKeyInfo(a aVar, InterfaceC0346f interfaceC0346f) {
        try {
            return getEncodedPrivateKeyInfo(new c.a.a.f.a(aVar, interfaceC0346f.toASN1Primitive()));
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(a aVar, InterfaceC0346f interfaceC0346f) {
        try {
            return getEncodedSubjectPublicKeyInfo(new b(aVar, interfaceC0346f));
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(a aVar, byte[] bArr) {
        try {
            return getEncodedSubjectPublicKeyInfo(new b(aVar, bArr));
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getEncodedPrivateKeyInfo(c.a.a.f.a aVar) {
        try {
            return aVar.getEncoded("DER");
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(b bVar) {
        try {
            return bVar.getEncoded("DER");
        } catch (Exception unused) {
            return null;
        }
    }
}
