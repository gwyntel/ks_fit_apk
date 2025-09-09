package com.github.barteksc.pdfviewer.exception;

/* loaded from: classes3.dex */
public class PageRenderingException extends Exception {
    private final int page;

    public PageRenderingException(int i2, Throwable th) {
        super(th);
        this.page = i2;
    }

    public int getPage() {
        return this.page;
    }
}
