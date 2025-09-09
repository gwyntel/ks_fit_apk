package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class ImageWatchFaceRk {
    public static final int IMAGE_TYPE_BITMAP = 2;
    public static final int IMAGE_TYPE_COMPRESSED = 0;
    public static final int IMAGE_TYPE_PNG = 3;
    public static final int IMAGE_TYPE_RGB565 = 1;
    public static final int POSITION_BOTTOM_CENTER = 2;
    public static final int POSITION_MIDDLE_CENTER = 1;
    public static final int POSITION_TOP_CENTER = 0;
    private int hadSetCount;
    private int imageType;
    private int maxCount;
    private int maxSize;
    private boolean needPreview;
    private int picIndex;
    private int positionIndex;
    private int previewHeight;
    private int previewWidth;
    private int styleIndex;
    private boolean supportFontColor;

    public int getHadSetCount() {
        return this.hadSetCount;
    }

    public int getImageType() {
        return this.imageType;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public boolean getNeedPreview() {
        return this.needPreview;
    }

    public int getPicIndex() {
        return this.picIndex;
    }

    public int getPositionIndex() {
        return this.positionIndex;
    }

    public int getPreviewHeight() {
        return this.previewHeight;
    }

    public int getPreviewWidth() {
        return this.previewWidth;
    }

    public int getStyleIndex() {
        return this.styleIndex;
    }

    public boolean getSupportFontColor() {
        return this.supportFontColor;
    }

    public void setHadSetCount(int i2) {
        this.hadSetCount = i2;
    }

    public void setImageType(int i2) {
        this.imageType = i2;
    }

    public void setMaxCount(int i2) {
        this.maxCount = i2;
    }

    public void setMaxSize(int i2) {
        this.maxSize = i2;
    }

    public void setNeedPreview(boolean z2) {
        this.needPreview = z2;
    }

    public void setPicIndex(int i2) {
        this.picIndex = i2;
    }

    public void setPositionIndex(int i2) {
        this.positionIndex = i2;
    }

    public void setPreviewHeight(int i2) {
        this.previewHeight = i2;
    }

    public void setPreviewWidth(int i2) {
        this.previewWidth = i2;
    }

    public void setStyleIndex(int i2) {
        this.styleIndex = i2;
    }

    public void setSupportFontColor(boolean z2) {
        this.supportFontColor = z2;
    }
}
