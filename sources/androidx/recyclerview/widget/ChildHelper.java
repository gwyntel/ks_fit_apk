package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
class ChildHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "ChildrenHelper";

    /* renamed from: a, reason: collision with root package name */
    final Callback f5829a;

    /* renamed from: b, reason: collision with root package name */
    final Bucket f5830b = new Bucket();

    /* renamed from: c, reason: collision with root package name */
    final List f5831c = new ArrayList();

    static class Bucket {

        /* renamed from: a, reason: collision with root package name */
        long f5832a = 0;

        /* renamed from: b, reason: collision with root package name */
        Bucket f5833b;

        Bucket() {
        }

        private void ensureNext() {
            if (this.f5833b == null) {
                this.f5833b = new Bucket();
            }
        }

        void a(int i2) {
            if (i2 < 64) {
                this.f5832a &= ~(1 << i2);
                return;
            }
            Bucket bucket = this.f5833b;
            if (bucket != null) {
                bucket.a(i2 - 64);
            }
        }

        int b(int i2) {
            Bucket bucket = this.f5833b;
            return bucket == null ? i2 >= 64 ? Long.bitCount(this.f5832a) : Long.bitCount(this.f5832a & ((1 << i2) - 1)) : i2 < 64 ? Long.bitCount(this.f5832a & ((1 << i2) - 1)) : bucket.b(i2 - 64) + Long.bitCount(this.f5832a);
        }

        boolean c(int i2) {
            if (i2 < 64) {
                return (this.f5832a & (1 << i2)) != 0;
            }
            ensureNext();
            return this.f5833b.c(i2 - 64);
        }

        void d(int i2, boolean z2) {
            if (i2 >= 64) {
                ensureNext();
                this.f5833b.d(i2 - 64, z2);
                return;
            }
            long j2 = this.f5832a;
            boolean z3 = (Long.MIN_VALUE & j2) != 0;
            long j3 = (1 << i2) - 1;
            this.f5832a = ((j2 & (~j3)) << 1) | (j2 & j3);
            if (z2) {
                g(i2);
            } else {
                a(i2);
            }
            if (z3 || this.f5833b != null) {
                ensureNext();
                this.f5833b.d(0, z3);
            }
        }

        boolean e(int i2) {
            if (i2 >= 64) {
                ensureNext();
                return this.f5833b.e(i2 - 64);
            }
            long j2 = 1 << i2;
            long j3 = this.f5832a;
            boolean z2 = (j3 & j2) != 0;
            long j4 = j3 & (~j2);
            this.f5832a = j4;
            long j5 = j2 - 1;
            this.f5832a = (j4 & j5) | Long.rotateRight((~j5) & j4, 1);
            Bucket bucket = this.f5833b;
            if (bucket != null) {
                if (bucket.c(0)) {
                    g(63);
                }
                this.f5833b.e(0);
            }
            return z2;
        }

        void f() {
            this.f5832a = 0L;
            Bucket bucket = this.f5833b;
            if (bucket != null) {
                bucket.f();
            }
        }

        void g(int i2) {
            if (i2 < 64) {
                this.f5832a |= 1 << i2;
            } else {
                ensureNext();
                this.f5833b.g(i2 - 64);
            }
        }

        public String toString() {
            if (this.f5833b == null) {
                return Long.toBinaryString(this.f5832a);
            }
            return this.f5833b.toString() + "xx" + Long.toBinaryString(this.f5832a);
        }
    }

    interface Callback {
        void addView(View view, int i2);

        void attachViewToParent(View view, int i2, ViewGroup.LayoutParams layoutParams);

        void detachViewFromParent(int i2);

        View getChildAt(int i2);

        int getChildCount();

        RecyclerView.ViewHolder getChildViewHolder(View view);

        int indexOfChild(View view);

        void onEnteredHiddenState(View view);

        void onLeftHiddenState(View view);

        void removeAllViews();

        void removeViewAt(int i2);
    }

    ChildHelper(Callback callback) {
        this.f5829a = callback;
    }

    private int getOffset(int i2) {
        if (i2 < 0) {
            return -1;
        }
        int childCount = this.f5829a.getChildCount();
        int i3 = i2;
        while (i3 < childCount) {
            int iB = i2 - (i3 - this.f5830b.b(i3));
            if (iB == 0) {
                while (this.f5830b.c(i3)) {
                    i3++;
                }
                return i3;
            }
            i3 += iB;
        }
        return -1;
    }

    private void hideViewInternal(View view) {
        this.f5831c.add(view);
        this.f5829a.onEnteredHiddenState(view);
    }

    private boolean unhideViewInternal(View view) {
        if (!this.f5831c.remove(view)) {
            return false;
        }
        this.f5829a.onLeftHiddenState(view);
        return true;
    }

    void a(View view, int i2, boolean z2) {
        int childCount = i2 < 0 ? this.f5829a.getChildCount() : getOffset(i2);
        this.f5830b.d(childCount, z2);
        if (z2) {
            hideViewInternal(view);
        }
        this.f5829a.addView(view, childCount);
    }

    void b(View view, boolean z2) {
        a(view, -1, z2);
    }

    void c(View view, int i2, ViewGroup.LayoutParams layoutParams, boolean z2) {
        int childCount = i2 < 0 ? this.f5829a.getChildCount() : getOffset(i2);
        this.f5830b.d(childCount, z2);
        if (z2) {
            hideViewInternal(view);
        }
        this.f5829a.attachViewToParent(view, childCount, layoutParams);
    }

    void d(int i2) {
        int offset = getOffset(i2);
        this.f5830b.e(offset);
        this.f5829a.detachViewFromParent(offset);
    }

    View e(int i2) {
        int size = this.f5831c.size();
        for (int i3 = 0; i3 < size; i3++) {
            View view = (View) this.f5831c.get(i3);
            RecyclerView.ViewHolder childViewHolder = this.f5829a.getChildViewHolder(view);
            if (childViewHolder.getLayoutPosition() == i2 && !childViewHolder.isInvalid() && !childViewHolder.isRemoved()) {
                return view;
            }
        }
        return null;
    }

    View f(int i2) {
        return this.f5829a.getChildAt(getOffset(i2));
    }

    int g() {
        return this.f5829a.getChildCount() - this.f5831c.size();
    }

    View h(int i2) {
        return this.f5829a.getChildAt(i2);
    }

    int i() {
        return this.f5829a.getChildCount();
    }

    void j(View view) {
        int iIndexOfChild = this.f5829a.indexOfChild(view);
        if (iIndexOfChild >= 0) {
            this.f5830b.g(iIndexOfChild);
            hideViewInternal(view);
        } else {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
    }

    int k(View view) {
        int iIndexOfChild = this.f5829a.indexOfChild(view);
        if (iIndexOfChild == -1 || this.f5830b.c(iIndexOfChild)) {
            return -1;
        }
        return iIndexOfChild - this.f5830b.b(iIndexOfChild);
    }

    boolean l(View view) {
        return this.f5831c.contains(view);
    }

    void m() {
        this.f5830b.f();
        for (int size = this.f5831c.size() - 1; size >= 0; size--) {
            this.f5829a.onLeftHiddenState((View) this.f5831c.get(size));
            this.f5831c.remove(size);
        }
        this.f5829a.removeAllViews();
    }

    void n(View view) {
        int iIndexOfChild = this.f5829a.indexOfChild(view);
        if (iIndexOfChild < 0) {
            return;
        }
        if (this.f5830b.e(iIndexOfChild)) {
            unhideViewInternal(view);
        }
        this.f5829a.removeViewAt(iIndexOfChild);
    }

    void o(int i2) {
        int offset = getOffset(i2);
        View childAt = this.f5829a.getChildAt(offset);
        if (childAt == null) {
            return;
        }
        if (this.f5830b.e(offset)) {
            unhideViewInternal(childAt);
        }
        this.f5829a.removeViewAt(offset);
    }

    boolean p(View view) {
        int iIndexOfChild = this.f5829a.indexOfChild(view);
        if (iIndexOfChild == -1) {
            unhideViewInternal(view);
            return true;
        }
        if (!this.f5830b.c(iIndexOfChild)) {
            return false;
        }
        this.f5830b.e(iIndexOfChild);
        unhideViewInternal(view);
        this.f5829a.removeViewAt(iIndexOfChild);
        return true;
    }

    void q(View view) {
        int iIndexOfChild = this.f5829a.indexOfChild(view);
        if (iIndexOfChild < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
        if (this.f5830b.c(iIndexOfChild)) {
            this.f5830b.a(iIndexOfChild);
            unhideViewInternal(view);
        } else {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
        }
    }

    public String toString() {
        return this.f5830b.toString() + ", hidden list:" + this.f5831c.size();
    }
}
