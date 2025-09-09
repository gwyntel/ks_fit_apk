package androidx.recyclerview.widget;

import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
class ViewBoundsCheck {

    /* renamed from: a, reason: collision with root package name */
    final Callback f6155a;

    /* renamed from: b, reason: collision with root package name */
    BoundFlags f6156b = new BoundFlags();

    static class BoundFlags {

        /* renamed from: a, reason: collision with root package name */
        int f6157a = 0;

        /* renamed from: b, reason: collision with root package name */
        int f6158b;

        /* renamed from: c, reason: collision with root package name */
        int f6159c;

        /* renamed from: d, reason: collision with root package name */
        int f6160d;

        /* renamed from: e, reason: collision with root package name */
        int f6161e;

        BoundFlags() {
        }

        void a(int i2) {
            this.f6157a = i2 | this.f6157a;
        }

        boolean b() {
            int i2 = this.f6157a;
            if ((i2 & 7) != 0 && (i2 & c(this.f6160d, this.f6158b)) == 0) {
                return false;
            }
            int i3 = this.f6157a;
            if ((i3 & 112) != 0 && (i3 & (c(this.f6160d, this.f6159c) << 4)) == 0) {
                return false;
            }
            int i4 = this.f6157a;
            if ((i4 & 1792) != 0 && (i4 & (c(this.f6161e, this.f6158b) << 8)) == 0) {
                return false;
            }
            int i5 = this.f6157a;
            return (i5 & 28672) == 0 || (i5 & (c(this.f6161e, this.f6159c) << 12)) != 0;
        }

        int c(int i2, int i3) {
            if (i2 > i3) {
                return 1;
            }
            return i2 == i3 ? 2 : 4;
        }

        void d() {
            this.f6157a = 0;
        }

        void e(int i2, int i3, int i4, int i5) {
            this.f6158b = i2;
            this.f6159c = i3;
            this.f6160d = i4;
            this.f6161e = i5;
        }
    }

    interface Callback {
        View getChildAt(int i2);

        int getChildEnd(View view);

        int getChildStart(View view);

        int getParentEnd();

        int getParentStart();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewBounds {
    }

    ViewBoundsCheck(Callback callback) {
        this.f6155a = callback;
    }

    View a(int i2, int i3, int i4, int i5) {
        int parentStart = this.f6155a.getParentStart();
        int parentEnd = this.f6155a.getParentEnd();
        int i6 = i3 > i2 ? 1 : -1;
        View view = null;
        while (i2 != i3) {
            View childAt = this.f6155a.getChildAt(i2);
            this.f6156b.e(parentStart, parentEnd, this.f6155a.getChildStart(childAt), this.f6155a.getChildEnd(childAt));
            if (i4 != 0) {
                this.f6156b.d();
                this.f6156b.a(i4);
                if (this.f6156b.b()) {
                    return childAt;
                }
            }
            if (i5 != 0) {
                this.f6156b.d();
                this.f6156b.a(i5);
                if (this.f6156b.b()) {
                    view = childAt;
                }
            }
            i2 += i6;
        }
        return view;
    }

    boolean b(View view, int i2) {
        this.f6156b.e(this.f6155a.getParentStart(), this.f6155a.getParentEnd(), this.f6155a.getChildStart(view), this.f6155a.getChildEnd(view));
        if (i2 == 0) {
            return false;
        }
        this.f6156b.d();
        this.f6156b.a(i2);
        return this.f6156b.b();
    }
}
