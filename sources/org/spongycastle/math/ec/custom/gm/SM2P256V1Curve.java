package org.spongycastle.math.ec.custom.gm;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes5.dex */
public class SM2P256V1Curve extends ECCurve.AbstractFp {
    public static final int SM2P256V1_DEFAULT_COORDS = 2;

    /* renamed from: q, reason: collision with root package name */
    public static final BigInteger f26809q = new BigInteger(1, Hex.decode("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF"));
    public SM2P256V1Point infinity;

    public SM2P256V1Curve() {
        super(f26809q);
        this.infinity = new SM2P256V1Point(this, null, null);
        this.f26793a = fromBigInteger(new BigInteger(1, Hex.decode("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC")));
        this.f26794b = fromBigInteger(new BigInteger(1, Hex.decode("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93")));
        this.order = new BigInteger(1, Hex.decode("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // org.spongycastle.math.ec.ECCurve
    public ECCurve cloneCurve() {
        return new SM2P256V1Curve();
    }

    @Override // org.spongycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean z2) {
        return new SM2P256V1Point(this, eCFieldElement, eCFieldElement2, z2);
    }

    @Override // org.spongycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SM2P256V1FieldElement(bigInteger);
    }

    @Override // org.spongycastle.math.ec.ECCurve
    public int getFieldSize() {
        return f26809q.bitLength();
    }

    @Override // org.spongycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f26809q;
    }

    @Override // org.spongycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int i2) {
        return i2 == 2;
    }

    @Override // org.spongycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr, boolean z2) {
        return new SM2P256V1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr, z2);
    }
}
