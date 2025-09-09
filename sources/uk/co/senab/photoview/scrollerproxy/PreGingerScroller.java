package uk.co.senab.photoview.scrollerproxy;

import android.content.Context;
import android.widget.Scroller;

/* loaded from: classes5.dex */
public class PreGingerScroller extends ScrollerProxy {
    private final Scroller mScroller;

    public PreGingerScroller(Context context) {
        this.mScroller = new Scroller(context);
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public void fling(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        this.mScroller.fling(i2, i3, i4, i5, i6, i7, i8, i9);
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public void forceFinished(boolean z2) {
        this.mScroller.forceFinished(z2);
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public int getCurrX() {
        return this.mScroller.getCurrX();
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public int getCurrY() {
        return this.mScroller.getCurrY();
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public boolean isFinished() {
        return this.mScroller.isFinished();
    }
}
