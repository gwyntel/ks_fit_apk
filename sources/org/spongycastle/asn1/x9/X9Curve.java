package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0365o;
import c.a.a.AbstractC0368s;
import c.a.a.C0347g;
import c.a.a.C0361k;
import c.a.a.C0364n;
import c.a.a.S;
import c.a.a.fa;
import c.a.a.r;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;

/* loaded from: classes5.dex */
public class X9Curve extends AbstractC0363m implements X9ObjectIdentifiers {
    public ECCurve curve;
    public C0364n fieldIdentifier;
    public byte[] seed;

    public X9Curve(ECCurve eCCurve) {
        this.fieldIdentifier = null;
        this.curve = eCCurve;
        this.seed = null;
        setFieldIdentifier();
    }

    private void setFieldIdentifier() {
        if (ECAlgorithms.isFpCurve(this.curve)) {
            this.fieldIdentifier = X9ObjectIdentifiers.prime_field;
        } else {
            if (!ECAlgorithms.isF2mCurve(this.curve)) {
                throw new IllegalArgumentException("This type of ECCurve is not implemented");
            }
            this.fieldIdentifier = X9ObjectIdentifiers.characteristic_two_field;
        }
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        if (this.fieldIdentifier.equals(X9ObjectIdentifiers.prime_field) || this.fieldIdentifier.equals(X9ObjectIdentifiers.characteristic_two_field)) {
            c0347g.a(new X9FieldElement(this.curve.getA()).toASN1Primitive());
            c0347g.a(new X9FieldElement(this.curve.getB()).toASN1Primitive());
        }
        byte[] bArr = this.seed;
        if (bArr != null) {
            c0347g.a(new S(bArr));
        }
        return new fa(c0347g);
    }

    public X9Curve(ECCurve eCCurve, byte[] bArr) {
        this.fieldIdentifier = null;
        this.curve = eCCurve;
        this.seed = bArr;
        setFieldIdentifier();
    }

    public X9Curve(X9FieldID x9FieldID, AbstractC0368s abstractC0368s) {
        int iIntValue;
        int iIntValue2;
        int i2;
        this.fieldIdentifier = null;
        C0364n identifier = x9FieldID.getIdentifier();
        this.fieldIdentifier = identifier;
        if (identifier.equals(X9ObjectIdentifiers.prime_field)) {
            BigInteger value = ((C0361k) x9FieldID.getParameters()).getValue();
            this.curve = new ECCurve.Fp(value, new X9FieldElement(value, (AbstractC0365o) abstractC0368s.a(0)).getValue().toBigInteger(), new X9FieldElement(value, (AbstractC0365o) abstractC0368s.a(1)).getValue().toBigInteger());
        } else if (this.fieldIdentifier.equals(X9ObjectIdentifiers.characteristic_two_field)) {
            AbstractC0368s abstractC0368s2 = AbstractC0368s.getInstance(x9FieldID.getParameters());
            int iIntValue3 = ((C0361k) abstractC0368s2.a(0)).getValue().intValue();
            C0364n c0364n = (C0364n) abstractC0368s2.a(1);
            if (c0364n.equals(X9ObjectIdentifiers.tpBasis)) {
                iIntValue2 = C0361k.getInstance(abstractC0368s2.a(2)).getValue().intValue();
                i2 = 0;
                iIntValue = 0;
            } else if (c0364n.equals(X9ObjectIdentifiers.ppBasis)) {
                AbstractC0368s abstractC0368s3 = AbstractC0368s.getInstance(abstractC0368s2.a(2));
                int iIntValue4 = C0361k.getInstance(abstractC0368s3.a(0)).getValue().intValue();
                int iIntValue5 = C0361k.getInstance(abstractC0368s3.a(1)).getValue().intValue();
                iIntValue = C0361k.getInstance(abstractC0368s3.a(2)).getValue().intValue();
                iIntValue2 = iIntValue4;
                i2 = iIntValue5;
            } else {
                throw new IllegalArgumentException("This type of EC basis is not implemented");
            }
            int i3 = iIntValue2;
            int i4 = i2;
            int i5 = iIntValue;
            this.curve = new ECCurve.F2m(iIntValue3, i3, i4, i5, new X9FieldElement(iIntValue3, i3, i4, i5, (AbstractC0365o) abstractC0368s.a(0)).getValue().toBigInteger(), new X9FieldElement(iIntValue3, i3, i4, i5, (AbstractC0365o) abstractC0368s.a(1)).getValue().toBigInteger());
        } else {
            throw new IllegalArgumentException("This type of ECCurve is not implemented");
        }
        if (abstractC0368s.h() == 3) {
            this.seed = ((S) abstractC0368s.a(2)).g();
        }
    }
}
