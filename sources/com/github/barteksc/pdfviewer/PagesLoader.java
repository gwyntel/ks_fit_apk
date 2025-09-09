package com.github.barteksc.pdfviewer;

import android.graphics.RectF;
import com.github.barteksc.pdfviewer.util.Constants;
import com.github.barteksc.pdfviewer.util.MathUtils;
import com.github.barteksc.pdfviewer.util.Util;
import com.shockwave.pdfium.util.SizeF;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
class PagesLoader {
    private int cacheOrder;
    private float pageRelativePartHeight;
    private float pageRelativePartWidth;
    private float partRenderHeight;
    private float partRenderWidth;
    private PDFView pdfView;
    private final int preloadOffset;
    private final RectF thumbnailRect = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    private float xOffset;
    private float yOffset;

    private class GridSize {

        /* renamed from: a, reason: collision with root package name */
        int f12476a;

        /* renamed from: b, reason: collision with root package name */
        int f12477b;

        private GridSize() {
        }

        public String toString() {
            return "GridSize{rows=" + this.f12476a + ", cols=" + this.f12477b + '}';
        }
    }

    private class Holder {

        /* renamed from: a, reason: collision with root package name */
        int f12479a;

        /* renamed from: b, reason: collision with root package name */
        int f12480b;

        private Holder() {
        }

        public String toString() {
            return "Holder{row=" + this.f12479a + ", col=" + this.f12480b + '}';
        }
    }

    private class RenderRange {

        /* renamed from: a, reason: collision with root package name */
        int f12482a = 0;

        /* renamed from: b, reason: collision with root package name */
        GridSize f12483b;

        /* renamed from: c, reason: collision with root package name */
        Holder f12484c;

        /* renamed from: d, reason: collision with root package name */
        Holder f12485d;

        RenderRange() {
            this.f12483b = new GridSize();
            this.f12484c = new Holder();
            this.f12485d = new Holder();
        }

        public String toString() {
            return "RenderRange{page=" + this.f12482a + ", gridSize=" + this.f12483b + ", leftTop=" + this.f12484c + ", rightBottom=" + this.f12485d + '}';
        }
    }

    PagesLoader(PDFView pDFView) {
        this.pdfView = pDFView;
        this.preloadOffset = Util.getDP(pDFView.getContext(), Constants.PRELOAD_OFFSET);
    }

    private void calculatePartSize(GridSize gridSize) {
        float f2 = 1.0f / gridSize.f12477b;
        this.pageRelativePartWidth = f2;
        float f3 = 1.0f / gridSize.f12476a;
        this.pageRelativePartHeight = f3;
        float f4 = Constants.PART_SIZE;
        this.partRenderWidth = f4 / f2;
        this.partRenderHeight = f4 / f3;
    }

    private void getPageColsRows(GridSize gridSize, int i2) {
        SizeF pageSize = this.pdfView.f12472b.getPageSize(i2);
        float width = 1.0f / pageSize.getWidth();
        float height = (Constants.PART_SIZE * (1.0f / pageSize.getHeight())) / this.pdfView.getZoom();
        float zoom = (Constants.PART_SIZE * width) / this.pdfView.getZoom();
        gridSize.f12476a = MathUtils.ceil(1.0f / height);
        gridSize.f12477b = MathUtils.ceil(1.0f / zoom);
    }

    private List<RenderRange> getRenderRangeList(float f2, float f3, float f4, float f5) {
        float pageOffset;
        float width;
        float f6;
        float height;
        float f7;
        float f8;
        float f9;
        int i2;
        boolean z2;
        float width2;
        float height2;
        float f10 = -MathUtils.max(f2, 0.0f);
        float f11 = -MathUtils.max(f3, 0.0f);
        float f12 = -MathUtils.max(f4, 0.0f);
        float f13 = -MathUtils.max(f5, 0.0f);
        float f14 = this.pdfView.isSwipeVertical() ? f11 : f10;
        float f15 = this.pdfView.isSwipeVertical() ? f13 : f12;
        PDFView pDFView = this.pdfView;
        int pageAtOffset = pDFView.f12472b.getPageAtOffset(f14, pDFView.getZoom());
        PDFView pDFView2 = this.pdfView;
        int pageAtOffset2 = pDFView2.f12472b.getPageAtOffset(f15, pDFView2.getZoom());
        int i3 = 1;
        int i4 = (pageAtOffset2 - pageAtOffset) + 1;
        LinkedList linkedList = new LinkedList();
        int i5 = pageAtOffset;
        while (i5 <= pageAtOffset2) {
            RenderRange renderRange = new RenderRange();
            renderRange.f12482a = i5;
            if (i5 != pageAtOffset) {
                if (i5 == pageAtOffset2) {
                    PDFView pDFView3 = this.pdfView;
                    pageOffset = pDFView3.f12472b.getPageOffset(i5, pDFView3.getZoom());
                    if (this.pdfView.isSwipeVertical()) {
                        f7 = pageOffset;
                        pageOffset = f10;
                    } else {
                        f7 = f11;
                    }
                    height = f13;
                    f6 = f7;
                } else {
                    PDFView pDFView4 = this.pdfView;
                    pageOffset = pDFView4.f12472b.getPageOffset(i5, pDFView4.getZoom());
                    PDFView pDFView5 = this.pdfView;
                    SizeF scaledPageSize = pDFView5.f12472b.getScaledPageSize(i5, pDFView5.getZoom());
                    if (this.pdfView.isSwipeVertical()) {
                        f6 = pageOffset;
                        height = scaledPageSize.getHeight() + pageOffset;
                        pageOffset = f10;
                    } else {
                        width = scaledPageSize.getWidth() + pageOffset;
                        f6 = f11;
                        height = f13;
                    }
                }
                width = f12;
            } else if (i4 == i3) {
                pageOffset = f10;
                f6 = f11;
                width = f12;
                height = f13;
            } else {
                PDFView pDFView6 = this.pdfView;
                float pageOffset2 = pDFView6.f12472b.getPageOffset(i5, pDFView6.getZoom());
                PDFView pDFView7 = this.pdfView;
                SizeF scaledPageSize2 = pDFView7.f12472b.getScaledPageSize(i5, pDFView7.getZoom());
                if (this.pdfView.isSwipeVertical()) {
                    height2 = pageOffset2 + scaledPageSize2.getHeight();
                    width2 = f12;
                } else {
                    width2 = pageOffset2 + scaledPageSize2.getWidth();
                    height2 = f13;
                }
                f6 = f11;
                height = height2;
                width = width2;
                pageOffset = f10;
            }
            getPageColsRows(renderRange.f12483b, renderRange.f12482a);
            PDFView pDFView8 = this.pdfView;
            float f16 = f10;
            SizeF scaledPageSize3 = pDFView8.f12472b.getScaledPageSize(renderRange.f12482a, pDFView8.getZoom());
            float height3 = scaledPageSize3.getHeight() / renderRange.f12483b.f12476a;
            float width3 = scaledPageSize3.getWidth() / renderRange.f12483b.f12477b;
            PDFView pDFView9 = this.pdfView;
            float f17 = f11;
            float secondaryPageOffset = pDFView9.f12472b.getSecondaryPageOffset(i5, pDFView9.getZoom());
            if (this.pdfView.isSwipeVertical()) {
                Holder holder = renderRange.f12484c;
                f8 = f12;
                PDFView pDFView10 = this.pdfView;
                f9 = f13;
                i2 = pageAtOffset;
                holder.f12479a = MathUtils.floor(Math.abs(f6 - pDFView10.f12472b.getPageOffset(renderRange.f12482a, pDFView10.getZoom())) / height3);
                renderRange.f12484c.f12480b = MathUtils.floor(MathUtils.min(pageOffset - secondaryPageOffset, 0.0f) / width3);
                Holder holder2 = renderRange.f12485d;
                PDFView pDFView11 = this.pdfView;
                holder2.f12479a = MathUtils.ceil(Math.abs(height - pDFView11.f12472b.getPageOffset(renderRange.f12482a, pDFView11.getZoom())) / height3);
                renderRange.f12485d.f12480b = MathUtils.floor(MathUtils.min(width - secondaryPageOffset, 0.0f) / width3);
                z2 = false;
            } else {
                f8 = f12;
                f9 = f13;
                i2 = pageAtOffset;
                Holder holder3 = renderRange.f12484c;
                PDFView pDFView12 = this.pdfView;
                holder3.f12480b = MathUtils.floor(Math.abs(pageOffset - pDFView12.f12472b.getPageOffset(renderRange.f12482a, pDFView12.getZoom())) / width3);
                renderRange.f12484c.f12479a = MathUtils.floor(MathUtils.min(f6 - secondaryPageOffset, 0.0f) / height3);
                Holder holder4 = renderRange.f12485d;
                PDFView pDFView13 = this.pdfView;
                holder4.f12480b = MathUtils.floor(Math.abs(width - pDFView13.f12472b.getPageOffset(renderRange.f12482a, pDFView13.getZoom())) / width3);
                z2 = false;
                renderRange.f12485d.f12479a = MathUtils.floor(MathUtils.min(height - secondaryPageOffset, 0.0f) / height3);
            }
            linkedList.add(renderRange);
            i5++;
            f11 = f17;
            f13 = f9;
            f10 = f16;
            pageAtOffset = i2;
            i3 = 1;
            f12 = f8;
        }
        return linkedList;
    }

    private boolean loadCell(int i2, int i3, int i4, float f2, float f3) {
        float f4 = i4 * f2;
        float f5 = i3 * f3;
        float f6 = this.partRenderWidth;
        float f7 = this.partRenderHeight;
        float f8 = f4 + f2 > 1.0f ? 1.0f - f4 : f2;
        float f9 = f5 + f3 > 1.0f ? 1.0f - f5 : f3;
        float f10 = f6 * f8;
        float f11 = f7 * f9;
        RectF rectF = new RectF(f4, f5, f8 + f4, f9 + f5);
        if (f10 <= 0.0f || f11 <= 0.0f) {
            return false;
        }
        if (!this.pdfView.f12471a.upPartIfContained(i2, rectF, this.cacheOrder)) {
            PDFView pDFView = this.pdfView;
            pDFView.f12473c.b(i2, f10, f11, rectF, false, this.cacheOrder, pDFView.isBestQuality(), this.pdfView.isAnnotationRendering());
        }
        this.cacheOrder++;
        return true;
    }

    private int loadPage(int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8 = 0;
        while (i3 <= i4) {
            for (int i9 = i5; i9 <= i6; i9++) {
                if (loadCell(i2, i3, i9, this.pageRelativePartWidth, this.pageRelativePartHeight)) {
                    i8++;
                }
                if (i8 >= i7) {
                    return i8;
                }
            }
            i3++;
        }
        return i8;
    }

    private void loadThumbnail(int i2) {
        SizeF pageSize = this.pdfView.f12472b.getPageSize(i2);
        float width = pageSize.getWidth() * Constants.THUMBNAIL_RATIO;
        float height = pageSize.getHeight() * Constants.THUMBNAIL_RATIO;
        if (this.pdfView.f12471a.containsThumbnail(i2, this.thumbnailRect)) {
            return;
        }
        PDFView pDFView = this.pdfView;
        pDFView.f12473c.b(i2, width, height, this.thumbnailRect, true, 0, pDFView.isBestQuality(), this.pdfView.isAnnotationRendering());
    }

    private void loadVisible() {
        float f2 = this.preloadOffset;
        float f3 = this.xOffset;
        float f4 = this.yOffset;
        List<RenderRange> renderRangeList = getRenderRangeList((-f3) + f2, (-f4) + f2, ((-f3) - this.pdfView.getWidth()) - f2, ((-f4) - this.pdfView.getHeight()) - f2);
        Iterator<RenderRange> it = renderRangeList.iterator();
        while (it.hasNext()) {
            loadThumbnail(it.next().f12482a);
        }
        int iLoadPage = 0;
        for (RenderRange renderRange : renderRangeList) {
            calculatePartSize(renderRange.f12483b);
            int i2 = renderRange.f12482a;
            Holder holder = renderRange.f12484c;
            int i3 = holder.f12479a;
            Holder holder2 = renderRange.f12485d;
            iLoadPage += loadPage(i2, i3, holder2.f12479a, holder.f12480b, holder2.f12480b, Constants.Cache.CACHE_SIZE - iLoadPage);
            if (iLoadPage >= Constants.Cache.CACHE_SIZE) {
                return;
            }
        }
    }

    void a() {
        this.cacheOrder = 1;
        this.xOffset = -MathUtils.max(this.pdfView.getCurrentXOffset(), 0.0f);
        this.yOffset = -MathUtils.max(this.pdfView.getCurrentYOffset(), 0.0f);
        loadVisible();
    }
}
