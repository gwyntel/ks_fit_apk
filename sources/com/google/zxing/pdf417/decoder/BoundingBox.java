package com.google.zxing.pdf417.decoder;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes3.dex */
final class BoundingBox {
    private final ResultPoint bottomLeft;
    private final ResultPoint bottomRight;
    private final BitMatrix image;
    private final int maxX;
    private final int maxY;
    private final int minX;
    private final int minY;
    private final ResultPoint topLeft;
    private final ResultPoint topRight;

    BoundingBox(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        boolean z2 = resultPoint == null || resultPoint2 == null;
        boolean z3 = resultPoint3 == null || resultPoint4 == null;
        if (z2 && z3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (z2) {
            resultPoint = new ResultPoint(0.0f, resultPoint3.getY());
            resultPoint2 = new ResultPoint(0.0f, resultPoint4.getY());
        } else if (z3) {
            resultPoint3 = new ResultPoint(bitMatrix.getWidth() - 1, resultPoint.getY());
            resultPoint4 = new ResultPoint(bitMatrix.getWidth() - 1, resultPoint2.getY());
        }
        this.image = bitMatrix;
        this.topLeft = resultPoint;
        this.bottomLeft = resultPoint2;
        this.topRight = resultPoint3;
        this.bottomRight = resultPoint4;
        this.minX = (int) Math.min(resultPoint.getX(), resultPoint2.getX());
        this.maxX = (int) Math.max(resultPoint3.getX(), resultPoint4.getX());
        this.minY = (int) Math.min(resultPoint.getY(), resultPoint3.getY());
        this.maxY = (int) Math.max(resultPoint2.getY(), resultPoint4.getY());
    }

    static BoundingBox j(BoundingBox boundingBox, BoundingBox boundingBox2) {
        return boundingBox == null ? boundingBox2 : boundingBox2 == null ? boundingBox : new BoundingBox(boundingBox.image, boundingBox.topLeft, boundingBox.bottomLeft, boundingBox2.topRight, boundingBox2.bottomRight);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    com.google.zxing.pdf417.decoder.BoundingBox a(int r13, int r14, boolean r15) {
        /*
            r12 = this;
            com.google.zxing.ResultPoint r0 = r12.topLeft
            com.google.zxing.ResultPoint r1 = r12.bottomLeft
            com.google.zxing.ResultPoint r2 = r12.topRight
            com.google.zxing.ResultPoint r3 = r12.bottomRight
            if (r13 <= 0) goto L2a
            if (r15 == 0) goto Le
            r4 = r0
            goto Lf
        Le:
            r4 = r2
        Lf:
            float r5 = r4.getY()
            int r5 = (int) r5
            int r5 = r5 - r13
            if (r5 >= 0) goto L18
            r5 = 0
        L18:
            com.google.zxing.ResultPoint r13 = new com.google.zxing.ResultPoint
            float r4 = r4.getX()
            float r5 = (float) r5
            r13.<init>(r4, r5)
            if (r15 == 0) goto L27
            r8 = r13
        L25:
            r10 = r2
            goto L2c
        L27:
            r10 = r13
            r8 = r0
            goto L2c
        L2a:
            r8 = r0
            goto L25
        L2c:
            if (r14 <= 0) goto L5d
            if (r15 == 0) goto L33
            com.google.zxing.ResultPoint r13 = r12.bottomLeft
            goto L35
        L33:
            com.google.zxing.ResultPoint r13 = r12.bottomRight
        L35:
            float r0 = r13.getY()
            int r0 = (int) r0
            int r0 = r0 + r14
            com.google.zxing.common.BitMatrix r14 = r12.image
            int r14 = r14.getHeight()
            if (r0 < r14) goto L4b
            com.google.zxing.common.BitMatrix r14 = r12.image
            int r14 = r14.getHeight()
            int r0 = r14 + (-1)
        L4b:
            com.google.zxing.ResultPoint r14 = new com.google.zxing.ResultPoint
            float r13 = r13.getX()
            float r0 = (float) r0
            r14.<init>(r13, r0)
            if (r15 == 0) goto L5a
            r9 = r14
        L58:
            r11 = r3
            goto L5f
        L5a:
            r11 = r14
            r9 = r1
            goto L5f
        L5d:
            r9 = r1
            goto L58
        L5f:
            com.google.zxing.pdf417.decoder.BoundingBox r13 = new com.google.zxing.pdf417.decoder.BoundingBox
            com.google.zxing.common.BitMatrix r7 = r12.image
            r6 = r13
            r6.<init>(r7, r8, r9, r10, r11)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.BoundingBox.a(int, int, boolean):com.google.zxing.pdf417.decoder.BoundingBox");
    }

    ResultPoint b() {
        return this.bottomLeft;
    }

    ResultPoint c() {
        return this.bottomRight;
    }

    int d() {
        return this.maxX;
    }

    int e() {
        return this.maxY;
    }

    int f() {
        return this.minX;
    }

    int g() {
        return this.minY;
    }

    ResultPoint h() {
        return this.topLeft;
    }

    ResultPoint i() {
        return this.topRight;
    }

    BoundingBox(BoundingBox boundingBox) {
        this.image = boundingBox.image;
        this.topLeft = boundingBox.h();
        this.bottomLeft = boundingBox.b();
        this.topRight = boundingBox.i();
        this.bottomRight = boundingBox.c();
        this.minX = boundingBox.f();
        this.maxX = boundingBox.d();
        this.minY = boundingBox.g();
        this.maxY = boundingBox.e();
    }
}
