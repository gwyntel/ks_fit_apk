package com.github.barteksc.pdfviewer.scroll;

import com.github.barteksc.pdfviewer.PDFView;

/* loaded from: classes3.dex */
public interface ScrollHandle {
    void destroyLayout();

    void hide();

    void hideDelayed();

    void setPageNum(int i2);

    void setScroll(float f2);

    void setupLayout(PDFView pDFView);

    void show();

    boolean shown();
}
