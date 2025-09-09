package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0365o;
import c.a.a.AbstractC0368s;
import c.a.a.C0347g;
import c.a.a.C0364n;
import c.a.a.fa;
import c.a.a.r;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class KeySpecificInfo extends AbstractC0363m {
    public C0364n algorithm;
    public AbstractC0365o counter;

    public KeySpecificInfo(C0364n c0364n, AbstractC0365o abstractC0365o) {
        this.algorithm = c0364n;
        this.counter = abstractC0365o;
    }

    public static KeySpecificInfo getInstance(Object obj) {
        if (obj instanceof KeySpecificInfo) {
            return (KeySpecificInfo) obj;
        }
        if (obj != null) {
            return new KeySpecificInfo(AbstractC0368s.getInstance(obj));
        }
        return null;
    }

    public C0364n getAlgorithm() {
        return this.algorithm;
    }

    public AbstractC0365o getCounter() {
        return this.counter;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(this.algorithm);
        c0347g.a(this.counter);
        return new fa(c0347g);
    }

    public KeySpecificInfo(AbstractC0368s abstractC0368s) {
        Enumeration enumerationG = abstractC0368s.g();
        this.algorithm = (C0364n) enumerationG.nextElement();
        this.counter = (AbstractC0365o) enumerationG.nextElement();
    }
}
