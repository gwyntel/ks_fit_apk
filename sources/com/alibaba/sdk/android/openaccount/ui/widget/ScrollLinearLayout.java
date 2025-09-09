package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;

/* loaded from: classes2.dex */
public class ScrollLinearLayout extends LinearLayout {
    private Scroller mScroller;

    public ScrollLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScroller = new Scroller(context);
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            if (AliSDKLogger.isDebugEnabled()) {
                AliSDKLogger.d(OpenAccountUIConstants.LOG_TAG, "computeScroll scrollX = " + this.mScroller.getCurrX());
            }
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    public boolean isScrolling() {
        return !this.mScroller.isFinished();
    }

    public void smoothScrollTo(int i2, int i3, int i4, int i5) {
        int i6 = i4 - i2;
        this.mScroller.startScroll(i2, i3, i6, i5 - i3, Math.abs(i6) * 2);
        invalidate();
    }

    public void stopScroll() {
        if (this.mScroller.isFinished()) {
            return;
        }
        this.mScroller.abortAnimation();
    }
}
