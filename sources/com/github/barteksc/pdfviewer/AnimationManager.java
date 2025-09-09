package com.github.barteksc.pdfviewer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;

/* loaded from: classes3.dex */
class AnimationManager {
    private ValueAnimator animation;
    private boolean flinging = false;
    private boolean pageFlinging = false;
    private PDFView pdfView;
    private OverScroller scroller;

    class XAnimation extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        XAnimation() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            AnimationManager.this.pdfView.loadPages();
            AnimationManager.this.pageFlinging = false;
            AnimationManager.this.hideHandle();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            AnimationManager.this.pdfView.loadPages();
            AnimationManager.this.pageFlinging = false;
            AnimationManager.this.hideHandle();
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            AnimationManager.this.pdfView.moveTo(((Float) valueAnimator.getAnimatedValue()).floatValue(), AnimationManager.this.pdfView.getCurrentYOffset());
            AnimationManager.this.pdfView.s();
        }
    }

    class YAnimation extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        YAnimation() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            AnimationManager.this.pdfView.loadPages();
            AnimationManager.this.pageFlinging = false;
            AnimationManager.this.hideHandle();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            AnimationManager.this.pdfView.loadPages();
            AnimationManager.this.pageFlinging = false;
            AnimationManager.this.hideHandle();
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            AnimationManager.this.pdfView.moveTo(AnimationManager.this.pdfView.getCurrentXOffset(), ((Float) valueAnimator.getAnimatedValue()).floatValue());
            AnimationManager.this.pdfView.s();
        }
    }

    class ZoomAnimation implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
        private final float centerX;
        private final float centerY;

        public ZoomAnimation(float f2, float f3) {
            this.centerX = f2;
            this.centerY = f3;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            AnimationManager.this.pdfView.loadPages();
            AnimationManager.this.hideHandle();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            AnimationManager.this.pdfView.loadPages();
            AnimationManager.this.pdfView.performPageSnap();
            AnimationManager.this.hideHandle();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            AnimationManager.this.pdfView.zoomCenteredTo(((Float) valueAnimator.getAnimatedValue()).floatValue(), new PointF(this.centerX, this.centerY));
        }
    }

    public AnimationManager(PDFView pDFView) {
        this.pdfView = pDFView;
        this.scroller = new OverScroller(pDFView.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideHandle() {
        if (this.pdfView.getScrollHandle() != null) {
            this.pdfView.getScrollHandle().hideDelayed();
        }
    }

    void d() {
        if (this.scroller.computeScrollOffset()) {
            this.pdfView.moveTo(this.scroller.getCurrX(), this.scroller.getCurrY());
            this.pdfView.s();
        } else if (this.flinging) {
            this.flinging = false;
            this.pdfView.loadPages();
            hideHandle();
            this.pdfView.performPageSnap();
        }
    }

    public boolean isFlinging() {
        return this.flinging || this.pageFlinging;
    }

    public void startFlingAnimation(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        stopAll();
        this.flinging = true;
        this.scroller.fling(i2, i3, i4, i5, i6, i7, i8, i9);
    }

    public void startPageFlingAnimation(float f2) {
        if (this.pdfView.isSwipeVertical()) {
            startYAnimation(this.pdfView.getCurrentYOffset(), f2);
        } else {
            startXAnimation(this.pdfView.getCurrentXOffset(), f2);
        }
        this.pageFlinging = true;
    }

    public void startXAnimation(float f2, float f3) {
        stopAll();
        this.animation = ValueAnimator.ofFloat(f2, f3);
        XAnimation xAnimation = new XAnimation();
        this.animation.setInterpolator(new DecelerateInterpolator());
        this.animation.addUpdateListener(xAnimation);
        this.animation.addListener(xAnimation);
        this.animation.setDuration(400L);
        this.animation.start();
    }

    public void startYAnimation(float f2, float f3) {
        stopAll();
        this.animation = ValueAnimator.ofFloat(f2, f3);
        YAnimation yAnimation = new YAnimation();
        this.animation.setInterpolator(new DecelerateInterpolator());
        this.animation.addUpdateListener(yAnimation);
        this.animation.addListener(yAnimation);
        this.animation.setDuration(400L);
        this.animation.start();
    }

    public void startZoomAnimation(float f2, float f3, float f4, float f5) {
        stopAll();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f4, f5);
        this.animation = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        ZoomAnimation zoomAnimation = new ZoomAnimation(f2, f3);
        this.animation.addUpdateListener(zoomAnimation);
        this.animation.addListener(zoomAnimation);
        this.animation.setDuration(400L);
        this.animation.start();
    }

    public void stopAll() {
        ValueAnimator valueAnimator = this.animation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.animation = null;
        }
        stopFling();
    }

    public void stopFling() {
        this.flinging = false;
        this.scroller.forceFinished(true);
    }
}
