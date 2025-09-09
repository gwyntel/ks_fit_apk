package com.github.barteksc.pdfviewer;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.SparseBooleanArray;
import com.github.barteksc.pdfviewer.exception.PageRenderingException;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.github.barteksc.pdfviewer.util.PageSizeCalculator;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.shockwave.pdfium.util.Size;
import com.shockwave.pdfium.util.SizeF;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
class PdfFile {
    private static final Object lock = new Object();
    private boolean autoSpacing;
    private final boolean fitEachPage;
    private boolean isVertical;
    private int[] originalUserPages;
    private final FitPolicy pageFitPolicy;
    private PdfDocument pdfDocument;
    private PdfiumCore pdfiumCore;
    private int spacingPx;
    private int pagesCount = 0;
    private List<Size> originalPageSizes = new ArrayList();
    private List<SizeF> pageSizes = new ArrayList();
    private SparseBooleanArray openedPages = new SparseBooleanArray();
    private Size originalMaxWidthPageSize = new Size(0, 0);
    private Size originalMaxHeightPageSize = new Size(0, 0);
    private SizeF maxHeightPageSize = new SizeF(0.0f, 0.0f);
    private SizeF maxWidthPageSize = new SizeF(0.0f, 0.0f);
    private List<Float> pageOffsets = new ArrayList();
    private List<Float> pageSpacing = new ArrayList();
    private float documentLength = 0.0f;

    PdfFile(PdfiumCore pdfiumCore, PdfDocument pdfDocument, FitPolicy fitPolicy, Size size, int[] iArr, boolean z2, int i2, boolean z3, boolean z4) {
        this.pdfiumCore = pdfiumCore;
        this.pdfDocument = pdfDocument;
        this.pageFitPolicy = fitPolicy;
        this.originalUserPages = iArr;
        this.isVertical = z2;
        this.spacingPx = i2;
        this.autoSpacing = z3;
        this.fitEachPage = z4;
        setup(size);
    }

    private void prepareAutoSpacing(Size size) {
        float width;
        float width2;
        this.pageSpacing.clear();
        for (int i2 = 0; i2 < getPagesCount(); i2++) {
            SizeF sizeF = this.pageSizes.get(i2);
            if (this.isVertical) {
                width = size.getHeight();
                width2 = sizeF.getHeight();
            } else {
                width = size.getWidth();
                width2 = sizeF.getWidth();
            }
            float fMax = Math.max(0.0f, width - width2);
            if (i2 < getPagesCount() - 1) {
                fMax += this.spacingPx;
            }
            this.pageSpacing.add(Float.valueOf(fMax));
        }
    }

    private void prepareDocLen() {
        float fFloatValue;
        float height = 0.0f;
        for (int i2 = 0; i2 < getPagesCount(); i2++) {
            SizeF sizeF = this.pageSizes.get(i2);
            height += this.isVertical ? sizeF.getHeight() : sizeF.getWidth();
            if (this.autoSpacing) {
                fFloatValue = this.pageSpacing.get(i2).floatValue();
            } else if (i2 < getPagesCount() - 1) {
                fFloatValue = this.spacingPx;
            }
            height += fFloatValue;
        }
        this.documentLength = height;
    }

    private void preparePagesOffset() {
        float fFloatValue;
        this.pageOffsets.clear();
        float fFloatValue2 = 0.0f;
        for (int i2 = 0; i2 < getPagesCount(); i2++) {
            SizeF sizeF = this.pageSizes.get(i2);
            float height = this.isVertical ? sizeF.getHeight() : sizeF.getWidth();
            if (this.autoSpacing) {
                fFloatValue2 += this.pageSpacing.get(i2).floatValue() / 2.0f;
                if (i2 == 0) {
                    fFloatValue2 -= this.spacingPx / 2.0f;
                } else if (i2 == getPagesCount() - 1) {
                    fFloatValue2 += this.spacingPx / 2.0f;
                }
                this.pageOffsets.add(Float.valueOf(fFloatValue2));
                fFloatValue = this.pageSpacing.get(i2).floatValue() / 2.0f;
            } else {
                this.pageOffsets.add(Float.valueOf(fFloatValue2));
                fFloatValue = this.spacingPx;
            }
            fFloatValue2 += height + fFloatValue;
        }
    }

    private void setup(Size size) {
        int[] iArr = this.originalUserPages;
        if (iArr != null) {
            this.pagesCount = iArr.length;
        } else {
            this.pagesCount = this.pdfiumCore.getPageCount(this.pdfDocument);
        }
        for (int i2 = 0; i2 < this.pagesCount; i2++) {
            Size pageSize = this.pdfiumCore.getPageSize(this.pdfDocument, documentPage(i2));
            if (pageSize.getWidth() > this.originalMaxWidthPageSize.getWidth()) {
                this.originalMaxWidthPageSize = pageSize;
            }
            if (pageSize.getHeight() > this.originalMaxHeightPageSize.getHeight()) {
                this.originalMaxHeightPageSize = pageSize;
            }
            this.originalPageSizes.add(pageSize);
        }
        recalculatePageSizes(size);
    }

    public int determineValidPageNumberFrom(int i2) {
        int pagesCount;
        if (i2 <= 0) {
            return 0;
        }
        int[] iArr = this.originalUserPages;
        if (iArr != null) {
            if (i2 >= iArr.length) {
                pagesCount = iArr.length;
                return pagesCount - 1;
            }
            return i2;
        }
        if (i2 >= getPagesCount()) {
            pagesCount = getPagesCount();
            return pagesCount - 1;
        }
        return i2;
    }

    public void dispose() {
        PdfDocument pdfDocument;
        PdfiumCore pdfiumCore = this.pdfiumCore;
        if (pdfiumCore != null && (pdfDocument = this.pdfDocument) != null) {
            pdfiumCore.closeDocument(pdfDocument);
        }
        this.pdfDocument = null;
        this.originalUserPages = null;
    }

    public int documentPage(int i2) {
        int i3;
        int[] iArr = this.originalUserPages;
        if (iArr == null) {
            i3 = i2;
        } else {
            if (i2 < 0 || i2 >= iArr.length) {
                return -1;
            }
            i3 = iArr[i2];
        }
        if (i3 < 0 || i2 >= getPagesCount()) {
            return -1;
        }
        return i3;
    }

    public List<PdfDocument.Bookmark> getBookmarks() {
        PdfDocument pdfDocument = this.pdfDocument;
        return pdfDocument == null ? new ArrayList() : this.pdfiumCore.getTableOfContents(pdfDocument);
    }

    public float getDocLen(float f2) {
        return this.documentLength * f2;
    }

    public float getMaxPageHeight() {
        return getMaxPageSize().getHeight();
    }

    public SizeF getMaxPageSize() {
        return this.isVertical ? this.maxWidthPageSize : this.maxHeightPageSize;
    }

    public float getMaxPageWidth() {
        return getMaxPageSize().getWidth();
    }

    public PdfDocument.Meta getMetaData() {
        PdfDocument pdfDocument = this.pdfDocument;
        if (pdfDocument == null) {
            return null;
        }
        return this.pdfiumCore.getDocumentMeta(pdfDocument);
    }

    public int getPageAtOffset(float f2, float f3) {
        int i2 = 0;
        for (int i3 = 0; i3 < getPagesCount() && (this.pageOffsets.get(i3).floatValue() * f3) - (getPageSpacing(i3, f3) / 2.0f) < f2; i3++) {
            i2++;
        }
        int i4 = i2 - 1;
        if (i4 >= 0) {
            return i4;
        }
        return 0;
    }

    public float getPageLength(int i2, float f2) {
        SizeF pageSize = getPageSize(i2);
        return (this.isVertical ? pageSize.getHeight() : pageSize.getWidth()) * f2;
    }

    public List<PdfDocument.Link> getPageLinks(int i2) {
        return this.pdfiumCore.getPageLinks(this.pdfDocument, documentPage(i2));
    }

    public float getPageOffset(int i2, float f2) {
        if (documentPage(i2) < 0) {
            return 0.0f;
        }
        return this.pageOffsets.get(i2).floatValue() * f2;
    }

    public SizeF getPageSize(int i2) {
        return documentPage(i2) < 0 ? new SizeF(0.0f, 0.0f) : this.pageSizes.get(i2);
    }

    public float getPageSpacing(int i2, float f2) {
        return (this.autoSpacing ? this.pageSpacing.get(i2).floatValue() : this.spacingPx) * f2;
    }

    public int getPagesCount() {
        return this.pagesCount;
    }

    public SizeF getScaledPageSize(int i2, float f2) {
        SizeF pageSize = getPageSize(i2);
        return new SizeF(pageSize.getWidth() * f2, pageSize.getHeight() * f2);
    }

    public float getSecondaryPageOffset(int i2, float f2) {
        float maxPageHeight;
        float height;
        SizeF pageSize = getPageSize(i2);
        if (this.isVertical) {
            maxPageHeight = getMaxPageWidth();
            height = pageSize.getWidth();
        } else {
            maxPageHeight = getMaxPageHeight();
            height = pageSize.getHeight();
        }
        return (f2 * (maxPageHeight - height)) / 2.0f;
    }

    public RectF mapRectToDevice(int i2, int i3, int i4, int i5, int i6, RectF rectF) {
        return this.pdfiumCore.mapRectToDevice(this.pdfDocument, documentPage(i2), i3, i4, i5, i6, 0, rectF);
    }

    public boolean openPage(int i2) throws PageRenderingException {
        int iDocumentPage = documentPage(i2);
        if (iDocumentPage < 0) {
            return false;
        }
        synchronized (lock) {
            try {
                if (this.openedPages.indexOfKey(iDocumentPage) >= 0) {
                    return false;
                }
                try {
                    this.pdfiumCore.openPage(this.pdfDocument, iDocumentPage);
                    this.openedPages.put(iDocumentPage, true);
                    return true;
                } catch (Exception e2) {
                    this.openedPages.put(iDocumentPage, false);
                    throw new PageRenderingException(i2, e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean pageHasError(int i2) {
        return !this.openedPages.get(documentPage(i2), false);
    }

    public void recalculatePageSizes(Size size) {
        this.pageSizes.clear();
        PageSizeCalculator pageSizeCalculator = new PageSizeCalculator(this.pageFitPolicy, this.originalMaxWidthPageSize, this.originalMaxHeightPageSize, size, this.fitEachPage);
        this.maxWidthPageSize = pageSizeCalculator.getOptimalMaxWidthPageSize();
        this.maxHeightPageSize = pageSizeCalculator.getOptimalMaxHeightPageSize();
        Iterator<Size> it = this.originalPageSizes.iterator();
        while (it.hasNext()) {
            this.pageSizes.add(pageSizeCalculator.calculate(it.next()));
        }
        if (this.autoSpacing) {
            prepareAutoSpacing(size);
        }
        prepareDocLen();
        preparePagesOffset();
    }

    public void renderPageBitmap(Bitmap bitmap, int i2, Rect rect, boolean z2) throws Throwable {
        this.pdfiumCore.renderPageBitmap(this.pdfDocument, bitmap, documentPage(i2), rect.left, rect.top, rect.width(), rect.height(), z2);
    }
}
