package com.github.barteksc.pdfviewer.model;

import android.graphics.Bitmap;
import android.graphics.RectF;

/* loaded from: classes3.dex */
public class PagePart {
    private int cacheOrder;
    private int page;
    private RectF pageRelativeBounds;
    private Bitmap renderedBitmap;
    private boolean thumbnail;

    public PagePart(int i2, Bitmap bitmap, RectF rectF, boolean z2, int i3) {
        this.page = i2;
        this.renderedBitmap = bitmap;
        this.pageRelativeBounds = rectF;
        this.thumbnail = z2;
        this.cacheOrder = i3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PagePart)) {
            return false;
        }
        PagePart pagePart = (PagePart) obj;
        return pagePart.getPage() == this.page && pagePart.getPageRelativeBounds().left == this.pageRelativeBounds.left && pagePart.getPageRelativeBounds().right == this.pageRelativeBounds.right && pagePart.getPageRelativeBounds().top == this.pageRelativeBounds.top && pagePart.getPageRelativeBounds().bottom == this.pageRelativeBounds.bottom;
    }

    public int getCacheOrder() {
        return this.cacheOrder;
    }

    public int getPage() {
        return this.page;
    }

    public RectF getPageRelativeBounds() {
        return this.pageRelativeBounds;
    }

    public Bitmap getRenderedBitmap() {
        return this.renderedBitmap;
    }

    public boolean isThumbnail() {
        return this.thumbnail;
    }

    public void setCacheOrder(int i2) {
        this.cacheOrder = i2;
    }
}
