package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
class LayoutState {

    /* renamed from: b, reason: collision with root package name */
    int f5956b;

    /* renamed from: c, reason: collision with root package name */
    int f5957c;

    /* renamed from: d, reason: collision with root package name */
    int f5958d;

    /* renamed from: e, reason: collision with root package name */
    int f5959e;

    /* renamed from: h, reason: collision with root package name */
    boolean f5962h;

    /* renamed from: i, reason: collision with root package name */
    boolean f5963i;

    /* renamed from: a, reason: collision with root package name */
    boolean f5955a = true;

    /* renamed from: f, reason: collision with root package name */
    int f5960f = 0;

    /* renamed from: g, reason: collision with root package name */
    int f5961g = 0;

    LayoutState() {
    }

    boolean a(RecyclerView.State state) {
        int i2 = this.f5957c;
        return i2 >= 0 && i2 < state.getItemCount();
    }

    View b(RecyclerView.Recycler recycler) {
        View viewForPosition = recycler.getViewForPosition(this.f5957c);
        this.f5957c += this.f5958d;
        return viewForPosition;
    }

    public String toString() {
        return "LayoutState{mAvailable=" + this.f5956b + ", mCurrentPosition=" + this.f5957c + ", mItemDirection=" + this.f5958d + ", mLayoutDirection=" + this.f5959e + ", mStartLine=" + this.f5960f + ", mEndLine=" + this.f5961g + '}';
    }
}
