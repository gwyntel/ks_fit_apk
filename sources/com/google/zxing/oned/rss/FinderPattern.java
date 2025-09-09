package com.google.zxing.oned.rss;

import com.google.zxing.ResultPoint;

/* loaded from: classes3.dex */
public final class FinderPattern {
    private final ResultPoint[] resultPoints;
    private final int[] startEnd;
    private final int value;

    public FinderPattern(int i2, int[] iArr, int i3, int i4, int i5) {
        this.value = i2;
        this.startEnd = iArr;
        float f2 = i3;
        float f3 = i5;
        this.resultPoints = new ResultPoint[]{new ResultPoint(f2, f3), new ResultPoint(i4, f3)};
    }

    public boolean equals(Object obj) {
        return (obj instanceof FinderPattern) && this.value == ((FinderPattern) obj).value;
    }

    public ResultPoint[] getResultPoints() {
        return this.resultPoints;
    }

    public int[] getStartEnd() {
        return this.startEnd;
    }

    public int getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }
}
