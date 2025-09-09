package com.yc.utesdk.watchface.close;

/* loaded from: classes4.dex */
public class PreviewScaleInfo {
    private float previewHeight;
    private float previewRound;
    private float previewWidth;

    public PreviewScaleInfo() {
    }

    public float getPreviewHeight() {
        return this.previewHeight;
    }

    public float getPreviewRound() {
        return this.previewRound;
    }

    public float getPreviewWidth() {
        return this.previewWidth;
    }

    public void setPreviewHeight(float f2) {
        this.previewHeight = f2;
    }

    public void setPreviewRound(float f2) {
        this.previewRound = f2;
    }

    public void setPreviewWidth(float f2) {
        this.previewWidth = f2;
    }

    public PreviewScaleInfo(float f2, float f3, float f4) {
        setPreviewWidth(f2);
        setPreviewHeight(f3);
        setPreviewRound(f4);
    }
}
