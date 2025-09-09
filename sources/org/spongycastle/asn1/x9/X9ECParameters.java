package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0365o;
import c.a.a.AbstractC0368s;
import c.a.a.C0347g;
import c.a.a.C0361k;
import c.a.a.InterfaceC0346f;
import c.a.a.fa;
import c.a.a.r;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.field.PolynomialExtensionField;

/* loaded from: classes5.dex */
public class X9ECParameters extends AbstractC0363m implements X9ObjectIdentifiers {
    public static final BigInteger ONE = BigInteger.valueOf(1);
    public ECCurve curve;
    public X9FieldID fieldID;

    /* renamed from: g, reason: collision with root package name */
    public X9ECPoint f26779g;

    /* renamed from: h, reason: collision with root package name */
    public BigInteger f26780h;

    /* renamed from: n, reason: collision with root package name */
    public BigInteger f26781n;
    public byte[] seed;

    public X9ECParameters(AbstractC0368s abstractC0368s) {
        if (!(abstractC0368s.a(0) instanceof C0361k) || !((C0361k) abstractC0368s.a(0)).getValue().equals(ONE)) {
            throw new IllegalArgumentException("bad version in X9ECParameters");
        }
        X9Curve x9Curve = new X9Curve(X9FieldID.getInstance(abstractC0368s.a(1)), AbstractC0368s.getInstance(abstractC0368s.a(2)));
        this.curve = x9Curve.getCurve();
        InterfaceC0346f interfaceC0346fA = abstractC0368s.a(3);
        if (interfaceC0346fA instanceof X9ECPoint) {
            this.f26779g = (X9ECPoint) interfaceC0346fA;
        } else {
            this.f26779g = new X9ECPoint(this.curve, (AbstractC0365o) interfaceC0346fA);
        }
        this.f26781n = ((C0361k) abstractC0368s.a(4)).getValue();
        this.seed = x9Curve.getSeed();
        if (abstractC0368s.h() == 6) {
            this.f26780h = ((C0361k) abstractC0368s.a(5)).getValue();
        }
    }

    public static X9ECParameters getInstance(Object obj) {
        if (obj instanceof X9ECParameters) {
            return (X9ECParameters) obj;
        }
        if (obj != null) {
            return new X9ECParameters(AbstractC0368s.getInstance(obj));
        }
        return null;
    }

    public X9ECPoint getBaseEntry() {
        return this.f26779g;
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public X9Curve getCurveEntry() {
        return new X9Curve(this.curve, this.seed);
    }

    public X9FieldID getFieldIDEntry() {
        return this.fieldID;
    }

    public ECPoint getG() {
        return this.f26779g.getPoint();
    }

    public BigInteger getH() {
        return this.f26780h;
    }

    public BigInteger getN() {
        return this.f26781n;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(new C0361k(ONE));
        c0347g.a(this.fieldID);
        c0347g.a(new X9Curve(this.curve, this.seed));
        c0347g.a(this.f26779g);
        c0347g.a(new C0361k(this.f26781n));
        BigInteger bigInteger = this.f26780h;
        if (bigInteger != null) {
            c0347g.a(new C0361k(bigInteger));
        }
        return new fa(c0347g);
    }

    public X9ECParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this(eCCurve, eCPoint, bigInteger, (BigInteger) null, (byte[]) null);
    }

    public X9ECParameters(ECCurve eCCurve, X9ECPoint x9ECPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCCurve, x9ECPoint, bigInteger, bigInteger2, (byte[]) null);
    }

    public X9ECParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCCurve, eCPoint, bigInteger, bigInteger2, (byte[]) null);
    }

    public X9ECParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this(eCCurve, new X9ECPoint(eCPoint), bigInteger, bigInteger2, bArr);
    }

    public X9ECParameters(ECCurve eCCurve, X9ECPoint x9ECPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.curve = eCCurve;
        this.f26779g = x9ECPoint;
        this.f26781n = bigInteger;
        this.f26780h = bigInteger2;
        this.seed = bArr;
        if (ECAlgorithms.isFpCurve(eCCurve)) {
            this.fieldID = new X9FieldID(eCCurve.getField().getCharacteristic());
            return;
        }
        if (ECAlgorithms.isF2mCurve(eCCurve)) {
            int[] exponentsPresent = ((PolynomialExtensionField) eCCurve.getField()).getMinimalPolynomial().getExponentsPresent();
            if (exponentsPresent.length == 3) {
                this.fieldID = new X9FieldID(exponentsPresent[2], exponentsPresent[1]);
                return;
            } else {
                if (exponentsPresent.length == 5) {
                    this.fieldID = new X9FieldID(exponentsPresent[4], exponentsPresent[1], exponentsPresent[2], exponentsPresent[3]);
                    return;
                }
                throw new IllegalArgumentException("Only trinomial and pentomial curves are supported");
            }
        }
        throw new IllegalArgumentException("'curve' is of an unsupported type");
    }
}
