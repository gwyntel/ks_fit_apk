package com.github.barteksc.pdfviewer.scroll;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.R;
import com.github.barteksc.pdfviewer.util.Util;

/* loaded from: classes3.dex */
public class DefaultScrollHandle extends RelativeLayout implements ScrollHandle {
    private static final int DEFAULT_TEXT_SIZE = 16;
    private static final int HANDLE_LONG = 65;
    private static final int HANDLE_SHORT = 40;

    /* renamed from: a, reason: collision with root package name */
    protected TextView f12501a;

    /* renamed from: b, reason: collision with root package name */
    protected Context f12502b;
    private float currentPos;
    private Handler handler;
    private Runnable hidePageScrollerRunnable;
    private boolean inverted;
    private PDFView pdfView;
    private float relativeHandlerMiddle;

    public DefaultScrollHandle(Context context) {
        this(context, false);
    }

    private void calculateMiddle() {
        float x2;
        float width;
        int width2;
        if (this.pdfView.isSwipeVertical()) {
            x2 = getY();
            width = getHeight();
            width2 = this.pdfView.getHeight();
        } else {
            x2 = getX();
            width = getWidth();
            width2 = this.pdfView.getWidth();
        }
        this.relativeHandlerMiddle = ((x2 + this.relativeHandlerMiddle) / width2) * width;
    }

    private boolean isPDFViewReady() {
        PDFView pDFView = this.pdfView;
        return (pDFView == null || pDFView.getPageCount() <= 0 || this.pdfView.documentFitsView()) ? false : true;
    }

    private void setPosition(float f2) {
        if (Float.isInfinite(f2) || Float.isNaN(f2)) {
            return;
        }
        float height = this.pdfView.isSwipeVertical() ? this.pdfView.getHeight() : this.pdfView.getWidth();
        float dp = f2 - this.relativeHandlerMiddle;
        if (dp < 0.0f) {
            dp = 0.0f;
        } else if (dp > height - Util.getDP(this.f12502b, 40)) {
            dp = height - Util.getDP(this.f12502b, 40);
        }
        if (this.pdfView.isSwipeVertical()) {
            setY(dp);
        } else {
            setX(dp);
        }
        calculateMiddle();
        invalidate();
    }

    @Override // com.github.barteksc.pdfviewer.scroll.ScrollHandle
    public void destroyLayout() {
        this.pdfView.removeView(this);
    }

    @Override // com.github.barteksc.pdfviewer.scroll.ScrollHandle
    public void hide() {
        setVisibility(4);
    }

    @Override // com.github.barteksc.pdfviewer.scroll.ScrollHandle
    public void hideDelayed() {
        this.handler.postDelayed(this.hidePageScrollerRunnable, 1000L);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.isPDFViewReady()
            if (r0 != 0) goto Lb
            boolean r5 = super.onTouchEvent(r5)
            return r5
        Lb:
            int r0 = r5.getAction()
            r1 = 1
            if (r0 == 0) goto L2e
            if (r0 == r1) goto L25
            r2 = 2
            if (r0 == r2) goto L59
            r2 = 3
            if (r0 == r2) goto L25
            r2 = 5
            if (r0 == r2) goto L2e
            r2 = 6
            if (r0 == r2) goto L25
            boolean r5 = super.onTouchEvent(r5)
            return r5
        L25:
            r4.hideDelayed()
            com.github.barteksc.pdfviewer.PDFView r5 = r4.pdfView
            r5.performPageSnap()
            return r1
        L2e:
            com.github.barteksc.pdfviewer.PDFView r0 = r4.pdfView
            r0.stopFling()
            android.os.Handler r0 = r4.handler
            java.lang.Runnable r2 = r4.hidePageScrollerRunnable
            r0.removeCallbacks(r2)
            com.github.barteksc.pdfviewer.PDFView r0 = r4.pdfView
            boolean r0 = r0.isSwipeVertical()
            if (r0 == 0) goto L4e
            float r0 = r5.getRawY()
            float r2 = r4.getY()
            float r0 = r0 - r2
            r4.currentPos = r0
            goto L59
        L4e:
            float r0 = r5.getRawX()
            float r2 = r4.getX()
            float r0 = r0 - r2
            r4.currentPos = r0
        L59:
            com.github.barteksc.pdfviewer.PDFView r0 = r4.pdfView
            boolean r0 = r0.isSwipeVertical()
            r2 = 0
            if (r0 == 0) goto L7d
            float r5 = r5.getRawY()
            float r0 = r4.currentPos
            float r5 = r5 - r0
            float r0 = r4.relativeHandlerMiddle
            float r5 = r5 + r0
            r4.setPosition(r5)
            com.github.barteksc.pdfviewer.PDFView r5 = r4.pdfView
            float r0 = r4.relativeHandlerMiddle
            int r3 = r4.getHeight()
            float r3 = (float) r3
            float r0 = r0 / r3
            r5.setPositionOffset(r0, r2)
            goto L97
        L7d:
            float r5 = r5.getRawX()
            float r0 = r4.currentPos
            float r5 = r5 - r0
            float r0 = r4.relativeHandlerMiddle
            float r5 = r5 + r0
            r4.setPosition(r5)
            com.github.barteksc.pdfviewer.PDFView r5 = r4.pdfView
            float r0 = r4.relativeHandlerMiddle
            int r3 = r4.getWidth()
            float r3 = (float) r3
            float r0 = r0 / r3
            r5.setPositionOffset(r0, r2)
        L97:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // com.github.barteksc.pdfviewer.scroll.ScrollHandle
    public void setPageNum(int i2) {
        String strValueOf = String.valueOf(i2);
        if (this.f12501a.getText().equals(strValueOf)) {
            return;
        }
        this.f12501a.setText(strValueOf);
    }

    @Override // com.github.barteksc.pdfviewer.scroll.ScrollHandle
    public void setScroll(float f2) {
        if (shown()) {
            this.handler.removeCallbacks(this.hidePageScrollerRunnable);
        } else {
            show();
        }
        PDFView pDFView = this.pdfView;
        if (pDFView != null) {
            setPosition((pDFView.isSwipeVertical() ? this.pdfView.getHeight() : this.pdfView.getWidth()) * f2);
        }
    }

    public void setTextColor(int i2) {
        this.f12501a.setTextColor(i2);
    }

    public void setTextSize(int i2) {
        this.f12501a.setTextSize(1, i2);
    }

    @Override // com.github.barteksc.pdfviewer.scroll.ScrollHandle
    public void setupLayout(PDFView pDFView) {
        Drawable drawable;
        int i2;
        int i3 = 40;
        int i4 = 65;
        if (pDFView.isSwipeVertical()) {
            if (this.inverted) {
                drawable = ContextCompat.getDrawable(this.f12502b, R.drawable.default_scroll_handle_left);
                i2 = 9;
            } else {
                drawable = ContextCompat.getDrawable(this.f12502b, R.drawable.default_scroll_handle_right);
                i2 = 11;
            }
            i4 = 40;
            i3 = 65;
        } else if (this.inverted) {
            drawable = ContextCompat.getDrawable(this.f12502b, R.drawable.default_scroll_handle_top);
            i2 = 10;
        } else {
            drawable = ContextCompat.getDrawable(this.f12502b, R.drawable.default_scroll_handle_bottom);
            i2 = 12;
        }
        setBackground(drawable);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Util.getDP(this.f12502b, i3), Util.getDP(this.f12502b, i4));
        layoutParams.setMargins(0, 0, 0, 0);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(13, -1);
        addView(this.f12501a, layoutParams2);
        layoutParams.addRule(i2);
        pDFView.addView(this, layoutParams);
        this.pdfView = pDFView;
    }

    @Override // com.github.barteksc.pdfviewer.scroll.ScrollHandle
    public void show() {
        setVisibility(0);
    }

    @Override // com.github.barteksc.pdfviewer.scroll.ScrollHandle
    public boolean shown() {
        return getVisibility() == 0;
    }

    public DefaultScrollHandle(Context context, boolean z2) {
        super(context);
        this.relativeHandlerMiddle = 0.0f;
        this.handler = new Handler();
        this.hidePageScrollerRunnable = new Runnable() { // from class: com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle.1
            @Override // java.lang.Runnable
            public void run() {
                DefaultScrollHandle.this.hide();
            }
        };
        this.f12502b = context;
        this.inverted = z2;
        this.f12501a = new TextView(context);
        setVisibility(4);
        setTextColor(ViewCompat.MEASURED_STATE_MASK);
        setTextSize(16);
    }
}
