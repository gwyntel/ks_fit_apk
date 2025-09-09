package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes3.dex */
public final class WhiteRectangleDetector {
    private static final int CORR = 1;
    private static final int INIT_SIZE = 10;
    private final int downInit;
    private final int height;
    private final BitMatrix image;
    private final int leftInit;
    private final int rightInit;
    private final int upInit;
    private final int width;

    public WhiteRectangleDetector(BitMatrix bitMatrix) throws NotFoundException {
        this(bitMatrix, 10, bitMatrix.getWidth() / 2, bitMatrix.getHeight() / 2);
    }

    private ResultPoint[] centerEdges(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        float x2 = resultPoint.getX();
        float y2 = resultPoint.getY();
        float x3 = resultPoint2.getX();
        float y3 = resultPoint2.getY();
        float x4 = resultPoint3.getX();
        float y4 = resultPoint3.getY();
        float x5 = resultPoint4.getX();
        float y5 = resultPoint4.getY();
        return x2 < ((float) this.width) / 2.0f ? new ResultPoint[]{new ResultPoint(x5 - 1.0f, y5 + 1.0f), new ResultPoint(x3 + 1.0f, y3 + 1.0f), new ResultPoint(x4 - 1.0f, y4 - 1.0f), new ResultPoint(x2 + 1.0f, y2 - 1.0f)} : new ResultPoint[]{new ResultPoint(x5 + 1.0f, y5 + 1.0f), new ResultPoint(x3 + 1.0f, y3 - 1.0f), new ResultPoint(x4 - 1.0f, y4 + 1.0f), new ResultPoint(x2 - 1.0f, y2 - 1.0f)};
    }

    private boolean containsBlackPoint(int i2, int i3, int i4, boolean z2) {
        if (z2) {
            while (i2 <= i3) {
                if (this.image.get(i2, i4)) {
                    return true;
                }
                i2++;
            }
            return false;
        }
        while (i2 <= i3) {
            if (this.image.get(i4, i2)) {
                return true;
            }
            i2++;
        }
        return false;
    }

    private ResultPoint getBlackPointOnSegment(float f2, float f3, float f4, float f5) {
        int iRound = MathUtils.round(MathUtils.distance(f2, f3, f4, f5));
        float f6 = iRound;
        float f7 = (f4 - f2) / f6;
        float f8 = (f5 - f3) / f6;
        for (int i2 = 0; i2 < iRound; i2++) {
            float f9 = i2;
            int iRound2 = MathUtils.round((f9 * f7) + f2);
            int iRound3 = MathUtils.round((f9 * f8) + f3);
            if (this.image.get(iRound2, iRound3)) {
                return new ResultPoint(iRound2, iRound3);
            }
        }
        return null;
    }

    public ResultPoint[] detect() throws NotFoundException {
        int i2 = this.leftInit;
        int i3 = this.rightInit;
        int i4 = this.upInit;
        int i5 = this.downInit;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        boolean z8 = true;
        while (z8) {
            boolean z9 = false;
            boolean zContainsBlackPoint = true;
            while (true) {
                if ((!zContainsBlackPoint && z3) || i3 >= this.width) {
                    break;
                }
                zContainsBlackPoint = containsBlackPoint(i4, i5, i3, false);
                if (zContainsBlackPoint) {
                    i3++;
                    z3 = true;
                    z9 = true;
                } else if (!z3) {
                    i3++;
                }
            }
            if (i3 < this.width) {
                boolean zContainsBlackPoint2 = true;
                while (true) {
                    if ((!zContainsBlackPoint2 && z4) || i5 >= this.height) {
                        break;
                    }
                    zContainsBlackPoint2 = containsBlackPoint(i2, i3, i5, true);
                    if (zContainsBlackPoint2) {
                        i5++;
                        z4 = true;
                        z9 = true;
                    } else if (!z4) {
                        i5++;
                    }
                }
                if (i5 < this.height) {
                    boolean zContainsBlackPoint3 = true;
                    while (true) {
                        if ((!zContainsBlackPoint3 && z5) || i2 < 0) {
                            break;
                        }
                        zContainsBlackPoint3 = containsBlackPoint(i4, i5, i2, false);
                        if (zContainsBlackPoint3) {
                            i2--;
                            z5 = true;
                            z9 = true;
                        } else if (!z5) {
                            i2--;
                        }
                    }
                    if (i2 >= 0) {
                        z8 = z9;
                        boolean zContainsBlackPoint4 = true;
                        while (true) {
                            if ((!zContainsBlackPoint4 && z7) || i4 < 0) {
                                break;
                            }
                            zContainsBlackPoint4 = containsBlackPoint(i2, i3, i4, true);
                            if (zContainsBlackPoint4) {
                                i4--;
                                z8 = true;
                                z7 = true;
                            } else if (!z7) {
                                i4--;
                            }
                        }
                        if (i4 >= 0) {
                            if (z8) {
                                z6 = true;
                            }
                        }
                    }
                }
            }
            z2 = true;
            break;
        }
        if (z2 || !z6) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i6 = i3 - i2;
        ResultPoint blackPointOnSegment = null;
        ResultPoint blackPointOnSegment2 = null;
        for (int i7 = 1; blackPointOnSegment2 == null && i7 < i6; i7++) {
            blackPointOnSegment2 = getBlackPointOnSegment(i2, i5 - i7, i2 + i7, i5);
        }
        if (blackPointOnSegment2 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint blackPointOnSegment3 = null;
        for (int i8 = 1; blackPointOnSegment3 == null && i8 < i6; i8++) {
            blackPointOnSegment3 = getBlackPointOnSegment(i2, i4 + i8, i2 + i8, i4);
        }
        if (blackPointOnSegment3 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint blackPointOnSegment4 = null;
        for (int i9 = 1; blackPointOnSegment4 == null && i9 < i6; i9++) {
            blackPointOnSegment4 = getBlackPointOnSegment(i3, i4 + i9, i3 - i9, i4);
        }
        if (blackPointOnSegment4 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        for (int i10 = 1; blackPointOnSegment == null && i10 < i6; i10++) {
            blackPointOnSegment = getBlackPointOnSegment(i3, i5 - i10, i3 - i10, i5);
        }
        if (blackPointOnSegment != null) {
            return centerEdges(blackPointOnSegment, blackPointOnSegment2, blackPointOnSegment4, blackPointOnSegment3);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public WhiteRectangleDetector(BitMatrix bitMatrix, int i2, int i3, int i4) throws NotFoundException {
        this.image = bitMatrix;
        int height = bitMatrix.getHeight();
        this.height = height;
        int width = bitMatrix.getWidth();
        this.width = width;
        int i5 = i2 / 2;
        int i6 = i3 - i5;
        this.leftInit = i6;
        int i7 = i3 + i5;
        this.rightInit = i7;
        int i8 = i4 - i5;
        this.upInit = i8;
        int i9 = i4 + i5;
        this.downInit = i9;
        if (i8 < 0 || i6 < 0 || i9 >= height || i7 >= width) {
            throw NotFoundException.getNotFoundInstance();
        }
    }
}
