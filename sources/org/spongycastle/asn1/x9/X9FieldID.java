package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0368s;
import c.a.a.C0347g;
import c.a.a.C0361k;
import c.a.a.C0364n;
import c.a.a.fa;
import c.a.a.r;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class X9FieldID extends AbstractC0363m implements X9ObjectIdentifiers {
    public C0364n id;
    public r parameters;

    public X9FieldID(BigInteger bigInteger) {
        this.id = X9ObjectIdentifiers.prime_field;
        this.parameters = new C0361k(bigInteger);
    }

    public static X9FieldID getInstance(Object obj) {
        if (obj instanceof X9FieldID) {
            return (X9FieldID) obj;
        }
        if (obj != null) {
            return new X9FieldID(AbstractC0368s.getInstance(obj));
        }
        return null;
    }

    public C0364n getIdentifier() {
        return this.id;
    }

    public r getParameters() {
        return this.parameters;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(this.id);
        c0347g.a(this.parameters);
        return new fa(c0347g);
    }

    public X9FieldID(int i2, int i3) {
        this(i2, i3, 0, 0);
    }

    public X9FieldID(int i2, int i3, int i4, int i5) {
        this.id = X9ObjectIdentifiers.characteristic_two_field;
        C0347g c0347g = new C0347g();
        c0347g.a(new C0361k(i2));
        if (i4 == 0) {
            if (i5 == 0) {
                c0347g.a(X9ObjectIdentifiers.tpBasis);
                c0347g.a(new C0361k(i3));
            } else {
                throw new IllegalArgumentException("inconsistent k values");
            }
        } else if (i4 > i3 && i5 > i4) {
            c0347g.a(X9ObjectIdentifiers.ppBasis);
            C0347g c0347g2 = new C0347g();
            c0347g2.a(new C0361k(i3));
            c0347g2.a(new C0361k(i4));
            c0347g2.a(new C0361k(i5));
            c0347g.a(new fa(c0347g2));
        } else {
            throw new IllegalArgumentException("inconsistent k values");
        }
        this.parameters = new fa(c0347g);
    }

    public X9FieldID(AbstractC0368s abstractC0368s) {
        this.id = C0364n.getInstance(abstractC0368s.a(0));
        this.parameters = abstractC0368s.a(1).toASN1Primitive();
    }
}
