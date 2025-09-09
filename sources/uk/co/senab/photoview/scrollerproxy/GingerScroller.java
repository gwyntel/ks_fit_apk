package uk.co.senab.photoview.scrollerproxy;

import android.annotation.TargetApi;
import android.content.Context;
import android.widget.OverScroller;

@TargetApi(9)
/* loaded from: classes5.dex */
public class GingerScroller extends ScrollerProxy {

    /* renamed from: a, reason: collision with root package name */
    protected final OverScroller f26870a;
    private boolean mFirstScroll = false;

    public GingerScroller(Context context) {
        this.f26870a = new OverScroller(context);
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public boolean computeScrollOffset() {
        if (this.mFirstScroll) {
            this.f26870a.computeScrollOffset();
            this.mFirstScroll = false;
        }
        return this.f26870a.computeScrollOffset();
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public void fling(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        this.f26870a.fling(i2, i3, i4, i5, i6, i7, i8, i9, i10, i11);
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public void forceFinished(boolean z2) {
        this.f26870a.forceFinished(z2);
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public int getCurrX() {
        return this.f26870a.getCurrX();
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public int getCurrY() {
        return this.f26870a.getCurrY();
    }

    @Override // uk.co.senab.photoview.scrollerproxy.ScrollerProxy
    public boolean isFinished() {
        return this.f26870a.isFinished();
    }
}
