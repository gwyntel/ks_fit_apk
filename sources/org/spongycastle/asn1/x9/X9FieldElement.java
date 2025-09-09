package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0365o;
import c.a.a.C0342ba;
import c.a.a.r;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;

/* loaded from: classes5.dex */
public class X9FieldElement extends AbstractC0363m {
    public static X9IntegerConverter converter = new X9IntegerConverter();

    /* renamed from: f, reason: collision with root package name */
    public ECFieldElement f26784f;

    public X9FieldElement(ECFieldElement eCFieldElement) {
        this.f26784f = eCFieldElement;
    }

    public ECFieldElement getValue() {
        return this.f26784f;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        return new C0342ba(converter.integerToBytes(this.f26784f.toBigInteger(), converter.getByteLength(this.f26784f)));
    }

    public X9FieldElement(BigInteger bigInteger, AbstractC0365o abstractC0365o) {
        this(new ECFieldElement.Fp(bigInteger, new BigInteger(1, abstractC0365o.g())));
    }

    public X9FieldElement(int i2, int i3, int i4, int i5, AbstractC0365o abstractC0365o) {
        this(new ECFieldElement.F2m(i2, i3, i4, i5, new BigInteger(1, abstractC0365o.g())));
    }
}
