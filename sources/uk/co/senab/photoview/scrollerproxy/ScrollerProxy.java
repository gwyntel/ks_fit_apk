package uk.co.senab.photoview.scrollerproxy;

import android.content.Context;

/* loaded from: classes5.dex */
public abstract class ScrollerProxy {
    public static ScrollerProxy getScroller(Context context) {
        return new IcsScroller(context);
    }

    public abstract boolean computeScrollOffset();

    public abstract void fling(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

    public abstract void forceFinished(boolean z2);

    public abstract int getCurrX();

    public abstract int getCurrY();

    public abstract boolean isFinished();
}
