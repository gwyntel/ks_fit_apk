package com.google.zxing.common.reedsolomon;

/* loaded from: classes3.dex */
public final class ReedSolomonDecoder {
    private final GenericGF field;

    public ReedSolomonDecoder(GenericGF genericGF) {
        this.field = genericGF;
    }

    private int[] findErrorLocations(GenericGFPoly genericGFPoly) throws ReedSolomonException {
        int iF = genericGFPoly.f();
        if (iF == 1) {
            return new int[]{genericGFPoly.d(1)};
        }
        int[] iArr = new int[iF];
        int i2 = 0;
        for (int i3 = 1; i3 < this.field.getSize() && i2 < iF; i3++) {
            if (genericGFPoly.c(i3) == 0) {
                iArr[i2] = this.field.f(i3);
                i2++;
            }
        }
        if (i2 == iF) {
            return iArr;
        }
        throw new ReedSolomonException("Error locator degree does not match number of roots");
    }

    private int[] findErrorMagnitudes(GenericGFPoly genericGFPoly, int[] iArr) {
        int length = iArr.length;
        int[] iArr2 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            int iF = this.field.f(iArr[i2]);
            int iH = 1;
            for (int i3 = 0; i3 < length; i3++) {
                if (i2 != i3) {
                    int iH2 = this.field.h(iArr[i3], iF);
                    iH = this.field.h(iH, (iH2 & 1) == 0 ? iH2 | 1 : iH2 & (-2));
                }
            }
            iArr2[i2] = this.field.h(genericGFPoly.c(iF), this.field.f(iH));
            if (this.field.getGeneratorBase() != 0) {
                iArr2[i2] = this.field.h(iArr2[i2], iF);
            }
        }
        return iArr2;
    }

    private GenericGFPoly[] runEuclideanAlgorithm(GenericGFPoly genericGFPoly, GenericGFPoly genericGFPoly2, int i2) throws ReedSolomonException {
        if (genericGFPoly.f() < genericGFPoly2.f()) {
            genericGFPoly2 = genericGFPoly;
            genericGFPoly = genericGFPoly2;
        }
        GenericGFPoly genericGFPolyE = this.field.e();
        GenericGFPoly genericGFPolyD = this.field.d();
        do {
            GenericGFPoly genericGFPoly3 = genericGFPoly2;
            genericGFPoly2 = genericGFPoly;
            genericGFPoly = genericGFPoly3;
            GenericGFPoly genericGFPoly4 = genericGFPolyD;
            GenericGFPoly genericGFPoly5 = genericGFPolyE;
            genericGFPolyE = genericGFPoly4;
            if (genericGFPoly.f() < i2 / 2) {
                int iD = genericGFPolyE.d(0);
                if (iD == 0) {
                    throw new ReedSolomonException("sigmaTilde(0) was zero");
                }
                int iF = this.field.f(iD);
                return new GenericGFPoly[]{genericGFPolyE.h(iF), genericGFPoly.h(iF)};
            }
            if (genericGFPoly.g()) {
                throw new ReedSolomonException("r_{i-1} was zero");
            }
            GenericGFPoly genericGFPolyE2 = this.field.e();
            int iF2 = this.field.f(genericGFPoly.d(genericGFPoly.f()));
            while (genericGFPoly2.f() >= genericGFPoly.f() && !genericGFPoly2.g()) {
                int iF3 = genericGFPoly2.f() - genericGFPoly.f();
                int iH = this.field.h(genericGFPoly2.d(genericGFPoly2.f()), iF2);
                genericGFPolyE2 = genericGFPolyE2.a(this.field.b(iF3, iH));
                genericGFPoly2 = genericGFPoly2.a(genericGFPoly.j(iF3, iH));
            }
            genericGFPolyD = genericGFPolyE2.i(genericGFPolyE).a(genericGFPoly5);
        } while (genericGFPoly2.f() < genericGFPoly.f());
        throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
    }

    public void decode(int[] iArr, int i2) throws ReedSolomonException {
        GenericGFPoly genericGFPoly = new GenericGFPoly(this.field, iArr);
        int[] iArr2 = new int[i2];
        boolean z2 = true;
        for (int i3 = 0; i3 < i2; i3++) {
            GenericGF genericGF = this.field;
            int iC = genericGFPoly.c(genericGF.c(genericGF.getGeneratorBase() + i3));
            iArr2[(i2 - 1) - i3] = iC;
            if (iC != 0) {
                z2 = false;
            }
        }
        if (z2) {
            return;
        }
        GenericGFPoly[] genericGFPolyArrRunEuclideanAlgorithm = runEuclideanAlgorithm(this.field.b(i2, 1), new GenericGFPoly(this.field, iArr2), i2);
        GenericGFPoly genericGFPoly2 = genericGFPolyArrRunEuclideanAlgorithm[0];
        GenericGFPoly genericGFPoly3 = genericGFPolyArrRunEuclideanAlgorithm[1];
        int[] iArrFindErrorLocations = findErrorLocations(genericGFPoly2);
        int[] iArrFindErrorMagnitudes = findErrorMagnitudes(genericGFPoly3, iArrFindErrorLocations);
        for (int i4 = 0; i4 < iArrFindErrorLocations.length; i4++) {
            int length = (iArr.length - 1) - this.field.g(iArrFindErrorLocations[i4]);
            if (length < 0) {
                throw new ReedSolomonException("Bad error location");
            }
            iArr[length] = GenericGF.a(iArr[length], iArrFindErrorMagnitudes[i4]);
        }
    }
}
