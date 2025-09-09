package com.github.barteksc.pdfviewer.util;

import com.shockwave.pdfium.util.Size;
import com.shockwave.pdfium.util.SizeF;

/* loaded from: classes3.dex */
public class PageSizeCalculator {
    private boolean fitEachPage;
    private FitPolicy fitPolicy;
    private float heightRatio;
    private SizeF optimalMaxHeightPageSize;
    private SizeF optimalMaxWidthPageSize;
    private final Size originalMaxHeightPageSize;
    private final Size originalMaxWidthPageSize;
    private final Size viewSize;
    private float widthRatio;

    /* renamed from: com.github.barteksc.pdfviewer.util.PageSizeCalculator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f12504a;

        static {
            int[] iArr = new int[FitPolicy.values().length];
            f12504a = iArr;
            try {
                iArr[FitPolicy.HEIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f12504a[FitPolicy.BOTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public PageSizeCalculator(FitPolicy fitPolicy, Size size, Size size2, Size size3, boolean z2) {
        this.fitPolicy = fitPolicy;
        this.originalMaxWidthPageSize = size;
        this.originalMaxHeightPageSize = size2;
        this.viewSize = size3;
        this.fitEachPage = z2;
        calculateMaxPages();
    }

    private void calculateMaxPages() {
        int i2 = AnonymousClass1.f12504a[this.fitPolicy.ordinal()];
        if (i2 == 1) {
            SizeF sizeFFitHeight = fitHeight(this.originalMaxHeightPageSize, this.viewSize.getHeight());
            this.optimalMaxHeightPageSize = sizeFFitHeight;
            this.heightRatio = sizeFFitHeight.getHeight() / this.originalMaxHeightPageSize.getHeight();
            this.optimalMaxWidthPageSize = fitHeight(this.originalMaxWidthPageSize, r0.getHeight() * this.heightRatio);
            return;
        }
        if (i2 != 2) {
            SizeF sizeFFitWidth = fitWidth(this.originalMaxWidthPageSize, this.viewSize.getWidth());
            this.optimalMaxWidthPageSize = sizeFFitWidth;
            this.widthRatio = sizeFFitWidth.getWidth() / this.originalMaxWidthPageSize.getWidth();
            this.optimalMaxHeightPageSize = fitWidth(this.originalMaxHeightPageSize, r0.getWidth() * this.widthRatio);
            return;
        }
        float width = fitBoth(this.originalMaxWidthPageSize, this.viewSize.getWidth(), this.viewSize.getHeight()).getWidth() / this.originalMaxWidthPageSize.getWidth();
        SizeF sizeFFitBoth = fitBoth(this.originalMaxHeightPageSize, r1.getWidth() * width, this.viewSize.getHeight());
        this.optimalMaxHeightPageSize = sizeFFitBoth;
        this.heightRatio = sizeFFitBoth.getHeight() / this.originalMaxHeightPageSize.getHeight();
        SizeF sizeFFitBoth2 = fitBoth(this.originalMaxWidthPageSize, this.viewSize.getWidth(), this.originalMaxWidthPageSize.getHeight() * this.heightRatio);
        this.optimalMaxWidthPageSize = sizeFFitBoth2;
        this.widthRatio = sizeFFitBoth2.getWidth() / this.originalMaxWidthPageSize.getWidth();
    }

    private SizeF fitBoth(Size size, float f2, float f3) {
        float width = size.getWidth() / size.getHeight();
        float fFloor = (float) Math.floor(f2 / width);
        if (fFloor > f3) {
            f2 = (float) Math.floor(width * f3);
        } else {
            f3 = fFloor;
        }
        return new SizeF(f2, f3);
    }

    private SizeF fitHeight(Size size, float f2) {
        return new SizeF((float) Math.floor(f2 / (size.getHeight() / size.getWidth())), f2);
    }

    private SizeF fitWidth(Size size, float f2) {
        return new SizeF(f2, (float) Math.floor(f2 / (size.getWidth() / size.getHeight())));
    }

    public SizeF calculate(Size size) {
        if (size.getWidth() <= 0 || size.getHeight() <= 0) {
            return new SizeF(0.0f, 0.0f);
        }
        float width = this.fitEachPage ? this.viewSize.getWidth() : size.getWidth() * this.widthRatio;
        float height = this.fitEachPage ? this.viewSize.getHeight() : size.getHeight() * this.heightRatio;
        int i2 = AnonymousClass1.f12504a[this.fitPolicy.ordinal()];
        return i2 != 1 ? i2 != 2 ? fitWidth(size, width) : fitBoth(size, width, height) : fitHeight(size, height);
    }

    public SizeF getOptimalMaxHeightPageSize() {
        return this.optimalMaxHeightPageSize;
    }

    public SizeF getOptimalMaxWidthPageSize() {
        return this.optimalMaxWidthPageSize;
    }
}
