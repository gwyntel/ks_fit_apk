package org.spongycastle.jce.spec;

import c.a.d.a;
import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.field.FiniteField;
import org.spongycastle.math.field.Polynomial;
import org.spongycastle.math.field.PolynomialExtensionField;

/* loaded from: classes5.dex */
public class ECNamedCurveSpec extends java.security.spec.ECParameterSpec {
    public String name;

    public ECNamedCurveSpec(String str, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        super(convertCurve(eCCurve, null), convertPoint(eCPoint), bigInteger, 1);
        this.name = str;
    }

    public static EllipticCurve convertCurve(ECCurve eCCurve, byte[] bArr) {
        return new EllipticCurve(convertField(eCCurve.getField()), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), bArr);
    }

    public static ECField convertField(FiniteField finiteField) {
        if (ECAlgorithms.isFpField(finiteField)) {
            return new ECFieldFp(finiteField.getCharacteristic());
        }
        Polynomial minimalPolynomial = ((PolynomialExtensionField) finiteField).getMinimalPolynomial();
        int[] exponentsPresent = minimalPolynomial.getExponentsPresent();
        return new ECFieldF2m(minimalPolynomial.getDegree(), a.c(a.a(exponentsPresent, 1, exponentsPresent.length - 1)));
    }

    public static java.security.spec.ECPoint convertPoint(ECPoint eCPoint) {
        ECPoint eCPointNormalize = eCPoint.normalize();
        return new java.security.spec.ECPoint(eCPointNormalize.getAffineXCoord().toBigInteger(), eCPointNormalize.getAffineYCoord().toBigInteger());
    }

    public String getName() {
        return this.name;
    }

    public ECNamedCurveSpec(String str, EllipticCurve ellipticCurve, java.security.spec.ECPoint eCPoint, BigInteger bigInteger) {
        super(ellipticCurve, eCPoint, bigInteger, 1);
        this.name = str;
    }

    public ECNamedCurveSpec(String str, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        super(convertCurve(eCCurve, null), convertPoint(eCPoint), bigInteger, bigInteger2.intValue());
        this.name = str;
    }

    public ECNamedCurveSpec(String str, EllipticCurve ellipticCurve, java.security.spec.ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        super(ellipticCurve, eCPoint, bigInteger, bigInteger2.intValue());
        this.name = str;
    }

    public ECNamedCurveSpec(String str, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        super(convertCurve(eCCurve, bArr), convertPoint(eCPoint), bigInteger, bigInteger2.intValue());
        this.name = str;
    }
}
