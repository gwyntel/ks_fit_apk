package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

/* loaded from: classes3.dex */
public final class FinderPattern extends ResultPoint {
    private final int count;
    private final float estimatedModuleSize;

    FinderPattern(float f2, float f3, float f4) {
        this(f2, f3, f4, 1);
    }

    boolean a(float f2, float f3, float f4) {
        if (Math.abs(f3 - getY()) > f2 || Math.abs(f4 - getX()) > f2) {
            return false;
        }
        float fAbs = Math.abs(f2 - this.estimatedModuleSize);
        return fAbs <= 1.0f || fAbs <= this.estimatedModuleSize;
    }

    FinderPattern b(float f2, float f3, float f4) {
        int i2 = this.count;
        int i3 = i2 + 1;
        float x2 = (i2 * getX()) + f3;
        float f5 = i3;
        return new FinderPattern(x2 / f5, ((this.count * getY()) + f2) / f5, ((this.count * this.estimatedModuleSize) + f4) / f5, i3);
    }

    int c() {
        return this.count;
    }

    public float getEstimatedModuleSize() {
        return this.estimatedModuleSize;
    }

    private FinderPattern(float f2, float f3, float f4, int i2) {
        super(f2, f3);
        this.estimatedModuleSize = f4;
        this.count = i2;
    }
}
