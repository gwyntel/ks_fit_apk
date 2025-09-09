package com.google.zxing.common.reedsolomon;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class ReedSolomonEncoder {
    private final List<GenericGFPoly> cachedGenerators;
    private final GenericGF field;

    public ReedSolomonEncoder(GenericGF genericGF) {
        this.field = genericGF;
        ArrayList arrayList = new ArrayList();
        this.cachedGenerators = arrayList;
        arrayList.add(new GenericGFPoly(genericGF, new int[]{1}));
    }

    private GenericGFPoly buildGenerator(int i2) {
        if (i2 >= this.cachedGenerators.size()) {
            List<GenericGFPoly> list = this.cachedGenerators;
            GenericGFPoly genericGFPolyI = list.get(list.size() - 1);
            for (int size = this.cachedGenerators.size(); size <= i2; size++) {
                GenericGF genericGF = this.field;
                genericGFPolyI = genericGFPolyI.i(new GenericGFPoly(genericGF, new int[]{1, genericGF.c((size - 1) + genericGF.getGeneratorBase())}));
                this.cachedGenerators.add(genericGFPolyI);
            }
        }
        return this.cachedGenerators.get(i2);
    }

    public void encode(int[] iArr, int i2) {
        if (i2 == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int length = iArr.length - i2;
        if (length <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        GenericGFPoly genericGFPolyBuildGenerator = buildGenerator(i2);
        int[] iArr2 = new int[length];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        int[] iArrE = new GenericGFPoly(this.field, iArr2).j(i2, 1).b(genericGFPolyBuildGenerator)[1].e();
        int length2 = i2 - iArrE.length;
        for (int i3 = 0; i3 < length2; i3++) {
            iArr[length + i3] = 0;
        }
        System.arraycopy(iArrE, 0, iArr, length + length2, iArrE.length);
    }
}
