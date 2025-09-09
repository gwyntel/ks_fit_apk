package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.ChecksumException;

/* loaded from: classes3.dex */
public final class ErrorCorrection {
    private final ModulusGF field = ModulusGF.PDF417_GF;

    private int[] findErrorLocations(ModulusPoly modulusPoly) throws ChecksumException {
        int iD = modulusPoly.d();
        int[] iArr = new int[iD];
        int i2 = 0;
        for (int i3 = 1; i3 < this.field.e() && i2 < iD; i3++) {
            if (modulusPoly.b(i3) == 0) {
                iArr[i2] = this.field.g(i3);
                i2++;
            }
        }
        if (i2 == iD) {
            return iArr;
        }
        throw ChecksumException.getChecksumInstance();
    }

    private int[] findErrorMagnitudes(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int[] iArr) {
        int iD = modulusPoly2.d();
        int[] iArr2 = new int[iD];
        for (int i2 = 1; i2 <= iD; i2++) {
            iArr2[iD - i2] = this.field.i(i2, modulusPoly2.c(i2));
        }
        ModulusPoly modulusPoly3 = new ModulusPoly(this.field, iArr2);
        int length = iArr.length;
        int[] iArr3 = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            int iG = this.field.g(iArr[i3]);
            iArr3[i3] = this.field.i(this.field.j(0, modulusPoly.b(iG)), this.field.g(modulusPoly3.b(iG)));
        }
        return iArr3;
    }

    private ModulusPoly[] runEuclideanAlgorithm(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int i2) throws ChecksumException {
        if (modulusPoly.d() < modulusPoly2.d()) {
            modulusPoly2 = modulusPoly;
            modulusPoly = modulusPoly2;
        }
        ModulusPoly modulusPolyF = this.field.f();
        ModulusPoly modulusPolyD = this.field.d();
        while (true) {
            ModulusPoly modulusPoly3 = modulusPoly2;
            modulusPoly2 = modulusPoly;
            modulusPoly = modulusPoly3;
            ModulusPoly modulusPoly4 = modulusPolyD;
            ModulusPoly modulusPoly5 = modulusPolyF;
            modulusPolyF = modulusPoly4;
            if (modulusPoly.d() < i2 / 2) {
                int iC = modulusPolyF.c(0);
                if (iC == 0) {
                    throw ChecksumException.getChecksumInstance();
                }
                int iG = this.field.g(iC);
                return new ModulusPoly[]{modulusPolyF.f(iG), modulusPoly.f(iG)};
            }
            if (modulusPoly.e()) {
                throw ChecksumException.getChecksumInstance();
            }
            ModulusPoly modulusPolyF2 = this.field.f();
            int iG2 = this.field.g(modulusPoly.c(modulusPoly.d()));
            while (modulusPoly2.d() >= modulusPoly.d() && !modulusPoly2.e()) {
                int iD = modulusPoly2.d() - modulusPoly.d();
                int i3 = this.field.i(modulusPoly2.c(modulusPoly2.d()), iG2);
                modulusPolyF2 = modulusPolyF2.a(this.field.b(iD, i3));
                modulusPoly2 = modulusPoly2.j(modulusPoly.h(iD, i3));
            }
            modulusPolyD = modulusPolyF2.g(modulusPolyF).j(modulusPoly5).i();
        }
    }

    public int decode(int[] iArr, int i2, int[] iArr2) throws ChecksumException {
        ModulusPoly modulusPoly = new ModulusPoly(this.field, iArr);
        int[] iArr3 = new int[i2];
        boolean z2 = false;
        for (int i3 = i2; i3 > 0; i3--) {
            int iB = modulusPoly.b(this.field.c(i3));
            iArr3[i2 - i3] = iB;
            if (iB != 0) {
                z2 = true;
            }
        }
        if (!z2) {
            return 0;
        }
        ModulusPoly modulusPolyD = this.field.d();
        if (iArr2 != null) {
            for (int i4 : iArr2) {
                int iC = this.field.c((iArr.length - 1) - i4);
                ModulusGF modulusGF = this.field;
                modulusPolyD = modulusPolyD.g(new ModulusPoly(modulusGF, new int[]{modulusGF.j(0, iC), 1}));
            }
        }
        ModulusPoly[] modulusPolyArrRunEuclideanAlgorithm = runEuclideanAlgorithm(this.field.b(i2, 1), new ModulusPoly(this.field, iArr3), i2);
        ModulusPoly modulusPoly2 = modulusPolyArrRunEuclideanAlgorithm[0];
        ModulusPoly modulusPoly3 = modulusPolyArrRunEuclideanAlgorithm[1];
        int[] iArrFindErrorLocations = findErrorLocations(modulusPoly2);
        int[] iArrFindErrorMagnitudes = findErrorMagnitudes(modulusPoly3, modulusPoly2, iArrFindErrorLocations);
        for (int i5 = 0; i5 < iArrFindErrorLocations.length; i5++) {
            int length = (iArr.length - 1) - this.field.h(iArrFindErrorLocations[i5]);
            if (length < 0) {
                throw ChecksumException.getChecksumInstance();
            }
            iArr[length] = this.field.j(iArr[length], iArrFindErrorMagnitudes[i5]);
        }
        return iArrFindErrorLocations.length;
    }
}
