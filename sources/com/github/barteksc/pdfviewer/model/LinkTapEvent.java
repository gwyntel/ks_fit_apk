package com.github.barteksc.pdfviewer.model;

import android.graphics.RectF;
import com.shockwave.pdfium.PdfDocument;

/* loaded from: classes3.dex */
public class LinkTapEvent {
    private float documentX;
    private float documentY;
    private PdfDocument.Link link;
    private RectF mappedLinkRect;
    private float originalX;
    private float originalY;

    public LinkTapEvent(float f2, float f3, float f4, float f5, RectF rectF, PdfDocument.Link link) {
        this.originalX = f2;
        this.originalY = f3;
        this.documentX = f4;
        this.documentY = f5;
        this.mappedLinkRect = rectF;
        this.link = link;
    }

    public float getDocumentX() {
        return this.documentX;
    }

    public float getDocumentY() {
        return this.documentY;
    }

    public PdfDocument.Link getLink() {
        return this.link;
    }

    public RectF getMappedLinkRect() {
        return this.mappedLinkRect;
    }

    public float getOriginalX() {
        return this.originalX;
    }

    public float getOriginalY() {
        return this.originalY;
    }
}
