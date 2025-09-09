package com.github.barteksc.pdfviewer.listener;

import android.view.MotionEvent;
import com.github.barteksc.pdfviewer.link.LinkHandler;
import com.github.barteksc.pdfviewer.model.LinkTapEvent;

/* loaded from: classes3.dex */
public class Callbacks {
    private LinkHandler linkHandler;
    private OnDrawListener onDrawAllListener;
    private OnDrawListener onDrawListener;
    private OnErrorListener onErrorListener;
    private OnLoadCompleteListener onLoadCompleteListener;
    private OnLongPressListener onLongPressListener;
    private OnPageChangeListener onPageChangeListener;
    private OnPageErrorListener onPageErrorListener;
    private OnPageScrollListener onPageScrollListener;
    private OnRenderListener onRenderListener;
    private OnTapListener onTapListener;

    public void callLinkHandler(LinkTapEvent linkTapEvent) {
        LinkHandler linkHandler = this.linkHandler;
        if (linkHandler != null) {
            linkHandler.handleLinkEvent(linkTapEvent);
        }
    }

    public void callOnLoadComplete(int i2) {
        OnLoadCompleteListener onLoadCompleteListener = this.onLoadCompleteListener;
        if (onLoadCompleteListener != null) {
            onLoadCompleteListener.loadComplete(i2);
        }
    }

    public void callOnLongPress(MotionEvent motionEvent) {
        OnLongPressListener onLongPressListener = this.onLongPressListener;
        if (onLongPressListener != null) {
            onLongPressListener.onLongPress(motionEvent);
        }
    }

    public void callOnPageChange(int i2, int i3) {
        OnPageChangeListener onPageChangeListener = this.onPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageChanged(i2, i3);
        }
    }

    public boolean callOnPageError(int i2, Throwable th) {
        OnPageErrorListener onPageErrorListener = this.onPageErrorListener;
        if (onPageErrorListener == null) {
            return false;
        }
        onPageErrorListener.onPageError(i2, th);
        return true;
    }

    public void callOnPageScroll(int i2, float f2) {
        OnPageScrollListener onPageScrollListener = this.onPageScrollListener;
        if (onPageScrollListener != null) {
            onPageScrollListener.onPageScrolled(i2, f2);
        }
    }

    public void callOnRender(int i2) {
        OnRenderListener onRenderListener = this.onRenderListener;
        if (onRenderListener != null) {
            onRenderListener.onInitiallyRendered(i2);
        }
    }

    public boolean callOnTap(MotionEvent motionEvent) {
        OnTapListener onTapListener = this.onTapListener;
        return onTapListener != null && onTapListener.onTap(motionEvent);
    }

    public OnDrawListener getOnDraw() {
        return this.onDrawListener;
    }

    public OnDrawListener getOnDrawAll() {
        return this.onDrawAllListener;
    }

    public OnErrorListener getOnError() {
        return this.onErrorListener;
    }

    public void setLinkHandler(LinkHandler linkHandler) {
        this.linkHandler = linkHandler;
    }

    public void setOnDraw(OnDrawListener onDrawListener) {
        this.onDrawListener = onDrawListener;
    }

    public void setOnDrawAll(OnDrawListener onDrawListener) {
        this.onDrawAllListener = onDrawListener;
    }

    public void setOnError(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    public void setOnLoadComplete(OnLoadCompleteListener onLoadCompleteListener) {
        this.onLoadCompleteListener = onLoadCompleteListener;
    }

    public void setOnLongPress(OnLongPressListener onLongPressListener) {
        this.onLongPressListener = onLongPressListener;
    }

    public void setOnPageChange(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public void setOnPageError(OnPageErrorListener onPageErrorListener) {
        this.onPageErrorListener = onPageErrorListener;
    }

    public void setOnPageScroll(OnPageScrollListener onPageScrollListener) {
        this.onPageScrollListener = onPageScrollListener;
    }

    public void setOnRender(OnRenderListener onRenderListener) {
        this.onRenderListener = onRenderListener;
    }

    public void setOnTap(OnTapListener onTapListener) {
        this.onTapListener = onTapListener;
    }
}
