package com.github.barteksc.pdfviewer;

import android.graphics.PointF;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import com.github.barteksc.pdfviewer.model.LinkTapEvent;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;
import com.github.barteksc.pdfviewer.util.Constants;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.util.SizeF;

/* loaded from: classes3.dex */
class DragPinchManager implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private AnimationManager animationManager;
    private GestureDetector gestureDetector;
    private PDFView pdfView;
    private ScaleGestureDetector scaleGestureDetector;
    private boolean scrolling = false;
    private boolean scaling = false;
    private boolean enabled = false;

    DragPinchManager(PDFView pDFView, AnimationManager animationManager) {
        this.pdfView = pDFView;
        this.animationManager = animationManager;
        this.gestureDetector = new GestureDetector(pDFView.getContext(), this);
        this.scaleGestureDetector = new ScaleGestureDetector(pDFView.getContext(), this);
        pDFView.setOnTouchListener(this);
    }

    private boolean checkDoPageFling(float f2, float f3) {
        float fAbs = Math.abs(f2);
        float fAbs2 = Math.abs(f3);
        if (this.pdfView.isSwipeVertical()) {
            if (fAbs2 <= fAbs) {
                return false;
            }
        } else if (fAbs <= fAbs2) {
            return false;
        }
        return true;
    }

    private boolean checkLinkTapped(float f2, float f3) {
        int secondaryPageOffset;
        int pageOffset;
        PDFView pDFView = this.pdfView;
        PdfFile pdfFile = pDFView.f12472b;
        if (pdfFile == null) {
            return false;
        }
        float f4 = (-pDFView.getCurrentXOffset()) + f2;
        float f5 = (-this.pdfView.getCurrentYOffset()) + f3;
        int pageAtOffset = pdfFile.getPageAtOffset(this.pdfView.isSwipeVertical() ? f5 : f4, this.pdfView.getZoom());
        SizeF scaledPageSize = pdfFile.getScaledPageSize(pageAtOffset, this.pdfView.getZoom());
        if (this.pdfView.isSwipeVertical()) {
            pageOffset = (int) pdfFile.getSecondaryPageOffset(pageAtOffset, this.pdfView.getZoom());
            secondaryPageOffset = (int) pdfFile.getPageOffset(pageAtOffset, this.pdfView.getZoom());
        } else {
            secondaryPageOffset = (int) pdfFile.getSecondaryPageOffset(pageAtOffset, this.pdfView.getZoom());
            pageOffset = (int) pdfFile.getPageOffset(pageAtOffset, this.pdfView.getZoom());
        }
        int i2 = pageOffset;
        int i3 = secondaryPageOffset;
        for (PdfDocument.Link link : pdfFile.getPageLinks(pageAtOffset)) {
            RectF rectFMapRectToDevice = pdfFile.mapRectToDevice(pageAtOffset, i2, i3, (int) scaledPageSize.getWidth(), (int) scaledPageSize.getHeight(), link.getBounds());
            rectFMapRectToDevice.sort();
            if (rectFMapRectToDevice.contains(f4, f5)) {
                this.pdfView.f12474d.callLinkHandler(new LinkTapEvent(f2, f3, f4, f5, rectFMapRectToDevice, link));
                return true;
            }
        }
        return false;
    }

    private void hideHandle() {
        ScrollHandle scrollHandle = this.pdfView.getScrollHandle();
        if (scrollHandle == null || !scrollHandle.shown()) {
            return;
        }
        scrollHandle.hideDelayed();
    }

    private void onBoundedFling(float f2, float f3) {
        float height;
        float f4;
        int currentXOffset = (int) this.pdfView.getCurrentXOffset();
        int currentYOffset = (int) this.pdfView.getCurrentYOffset();
        PDFView pDFView = this.pdfView;
        PdfFile pdfFile = pDFView.f12472b;
        float f5 = -pdfFile.getPageOffset(pDFView.getCurrentPage(), this.pdfView.getZoom());
        float pageLength = f5 - pdfFile.getPageLength(this.pdfView.getCurrentPage(), this.pdfView.getZoom());
        float f6 = 0.0f;
        if (this.pdfView.isSwipeVertical()) {
            f4 = -(this.pdfView.toCurrentScale(pdfFile.getMaxPageWidth()) - this.pdfView.getWidth());
            height = pageLength + this.pdfView.getHeight();
            f6 = f5;
            f5 = 0.0f;
        } else {
            float width = pageLength + this.pdfView.getWidth();
            height = -(this.pdfView.toCurrentScale(pdfFile.getMaxPageHeight()) - this.pdfView.getHeight());
            f4 = width;
        }
        this.animationManager.startFlingAnimation(currentXOffset, currentYOffset, (int) f2, (int) f3, (int) f4, (int) f5, (int) height, (int) f6);
    }

    private void onScrollEnd(MotionEvent motionEvent) {
        this.pdfView.loadPages();
        hideHandle();
        if (this.animationManager.isFlinging()) {
            return;
        }
        this.pdfView.performPageSnap();
    }

    private void startPageFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        float x2;
        float x3;
        if (checkDoPageFling(f2, f3)) {
            int i2 = -1;
            if (!this.pdfView.isSwipeVertical() ? f2 <= 0.0f : f3 <= 0.0f) {
                i2 = 1;
            }
            if (this.pdfView.isSwipeVertical()) {
                x2 = motionEvent2.getY();
                x3 = motionEvent.getY();
            } else {
                x2 = motionEvent2.getX();
                x3 = motionEvent.getX();
            }
            float f4 = x2 - x3;
            int iMax = Math.max(0, Math.min(this.pdfView.getPageCount() - 1, this.pdfView.n(this.pdfView.getCurrentXOffset() - (this.pdfView.getZoom() * f4), this.pdfView.getCurrentYOffset() - (f4 * this.pdfView.getZoom())) + i2));
            this.animationManager.startPageFlingAnimation(-this.pdfView.w(iMax, this.pdfView.o(iMax)));
        }
    }

    void a() {
        this.enabled = false;
    }

    void b() {
        this.gestureDetector.setIsLongpressEnabled(false);
    }

    void c() {
        this.enabled = true;
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (!this.pdfView.p()) {
            return false;
        }
        if (this.pdfView.getZoom() < this.pdfView.getMidZoom()) {
            this.pdfView.zoomWithAnimation(motionEvent.getX(), motionEvent.getY(), this.pdfView.getMidZoom());
            return true;
        }
        if (this.pdfView.getZoom() < this.pdfView.getMaxZoom()) {
            this.pdfView.zoomWithAnimation(motionEvent.getX(), motionEvent.getY(), this.pdfView.getMaxZoom());
            return true;
        }
        this.pdfView.resetZoomWithAnimation();
        return true;
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        this.animationManager.stopFling();
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        float f4;
        float currentScale;
        int height;
        if (!this.pdfView.isSwipeEnabled()) {
            return false;
        }
        if (this.pdfView.isPageFlingEnabled()) {
            if (this.pdfView.pageFillsScreen()) {
                onBoundedFling(f2, f3);
            } else {
                startPageFling(motionEvent, motionEvent2, f2, f3);
            }
            return true;
        }
        int currentXOffset = (int) this.pdfView.getCurrentXOffset();
        int currentYOffset = (int) this.pdfView.getCurrentYOffset();
        PDFView pDFView = this.pdfView;
        PdfFile pdfFile = pDFView.f12472b;
        if (pDFView.isSwipeVertical()) {
            f4 = -(this.pdfView.toCurrentScale(pdfFile.getMaxPageWidth()) - this.pdfView.getWidth());
            currentScale = pdfFile.getDocLen(this.pdfView.getZoom());
            height = this.pdfView.getHeight();
        } else {
            f4 = -(pdfFile.getDocLen(this.pdfView.getZoom()) - this.pdfView.getWidth());
            currentScale = this.pdfView.toCurrentScale(pdfFile.getMaxPageHeight());
            height = this.pdfView.getHeight();
        }
        this.animationManager.startFlingAnimation(currentXOffset, currentYOffset, (int) f2, (int) f3, (int) f4, 0, (int) (-(currentScale - height)), 0);
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
        this.pdfView.f12474d.callOnLongPress(motionEvent);
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        float zoom = this.pdfView.getZoom() * scaleFactor;
        float fMin = Math.min(Constants.Pinch.MINIMUM_ZOOM, this.pdfView.getMinZoom());
        float fMin2 = Math.min(Constants.Pinch.MAXIMUM_ZOOM, this.pdfView.getMaxZoom());
        if (zoom < fMin) {
            scaleFactor = fMin / this.pdfView.getZoom();
        } else if (zoom > fMin2) {
            scaleFactor = fMin2 / this.pdfView.getZoom();
        }
        this.pdfView.zoomCenteredRelativeTo(scaleFactor, new PointF(scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY()));
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        this.scaling = true;
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        this.pdfView.loadPages();
        hideHandle();
        this.scaling = false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        this.scrolling = true;
        if (this.pdfView.isZooming() || this.pdfView.isSwipeEnabled()) {
            this.pdfView.moveRelativeTo(-f2, -f3);
        }
        if (!this.scaling || this.pdfView.doRenderDuringScale()) {
            this.pdfView.s();
        }
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        ScrollHandle scrollHandle;
        boolean zCallOnTap = this.pdfView.f12474d.callOnTap(motionEvent);
        boolean zCheckLinkTapped = checkLinkTapped(motionEvent.getX(), motionEvent.getY());
        if (!zCallOnTap && !zCheckLinkTapped && (scrollHandle = this.pdfView.getScrollHandle()) != null && !this.pdfView.documentFitsView()) {
            if (scrollHandle.shown()) {
                scrollHandle.hide();
            } else {
                scrollHandle.show();
            }
        }
        this.pdfView.performClick();
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.enabled) {
            return false;
        }
        boolean z2 = this.gestureDetector.onTouchEvent(motionEvent) || this.scaleGestureDetector.onTouchEvent(motionEvent);
        if (motionEvent.getAction() == 1 && this.scrolling) {
            this.scrolling = false;
            onScrollEnd(motionEvent);
        }
        return z2;
    }
}
