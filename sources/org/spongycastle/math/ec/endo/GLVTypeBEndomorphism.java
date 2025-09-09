package org.spongycastle.math.ec.endo;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPointMap;
import org.spongycastle.math.ec.ScaleXPointMap;

/* loaded from: classes5.dex */
public class GLVTypeBEndomorphism implements GLVEndomorphism {
    public final ECCurve curve;
    public final GLVTypeBParameters parameters;
    public final ECPointMap pointMap;

    public GLVTypeBEndomorphism(ECCurve eCCurve, GLVTypeBParameters gLVTypeBParameters) {
        this.curve = eCCurve;
        this.parameters = gLVTypeBParameters;
        this.pointMap = new ScaleXPointMap(eCCurve.fromBigInteger(gLVTypeBParameters.getBeta()));
    }

    public BigInteger calculateB(BigInteger bigInteger, BigInteger bigInteger2, int i2) {
        boolean z2 = bigInteger2.signum() < 0;
        BigInteger bigIntegerMultiply = bigInteger.multiply(bigInteger2.abs());
        boolean zTestBit = bigIntegerMultiply.testBit(i2 - 1);
        BigInteger bigIntegerShiftRight = bigIntegerMultiply.shiftRight(i2);
        if (zTestBit) {
            bigIntegerShiftRight = bigIntegerShiftRight.add(ECConstants.ONE);
        }
        return z2 ? bigIntegerShiftRight.negate() : bigIntegerShiftRight;
    }

    @Override // org.spongycastle.math.ec.endo.GLVEndomorphism
    public BigInteger[] decomposeScalar(BigInteger bigInteger) {
        int bits = this.parameters.getBits();
        BigInteger bigIntegerCalculateB = calculateB(bigInteger, this.parameters.getG1(), bits);
        BigInteger bigIntegerCalculateB2 = calculateB(bigInteger, this.parameters.getG2(), bits);
        GLVTypeBParameters gLVTypeBParameters = this.parameters;
        return new BigInteger[]{bigInteger.subtract(bigIntegerCalculateB.multiply(gLVTypeBParameters.getV1A()).add(bigIntegerCalculateB2.multiply(gLVTypeBParameters.getV2A()))), bigIntegerCalculateB.multiply(gLVTypeBParameters.getV1B()).add(bigIntegerCalculateB2.multiply(gLVTypeBParameters.getV2B())).negate()};
    }

    @Override // org.spongycastle.math.ec.endo.ECEndomorphism
    public ECPointMap getPointMap() {
        return this.pointMap;
    }

    @Override // org.spongycastle.math.ec.endo.ECEndomorphism
    public boolean hasEfficientPointMap() {
        return true;
    }
}
