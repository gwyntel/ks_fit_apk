package androidx.transition;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* loaded from: classes2.dex */
class ViewOverlayApi14 implements ViewOverlayImpl {

    /* renamed from: a, reason: collision with root package name */
    protected OverlayViewGroup f6349a;

    @Override // androidx.transition.ViewOverlayImpl
    public void add(@NonNull Drawable drawable) {
        this.f6349a.add(drawable);
    }

    @Override // androidx.transition.ViewOverlayImpl
    public void remove(@NonNull Drawable drawable) {
        this.f6349a.remove(drawable);
    }

    static class OverlayViewGroup extends ViewGroup {

        /* renamed from: d, reason: collision with root package name */
        static Method f6350d;

        /* renamed from: a, reason: collision with root package name */
        ViewGroup f6351a;

        /* renamed from: b, reason: collision with root package name */
        View f6352b;

        /* renamed from: c, reason: collision with root package name */
        ArrayList f6353c;
        private boolean mDisposed;

        static {
            try {
                Class cls = Integer.TYPE;
                f6350d = ViewGroup.class.getDeclaredMethod("invalidateChildInParentFast", cls, cls, Rect.class);
            } catch (NoSuchMethodException unused) {
            }
        }

        private void assertNotDisposed() {
            if (this.mDisposed) {
                throw new IllegalStateException("This overlay was disposed already. Please use a new one via ViewGroupUtils.getOverlay()");
            }
        }

        private void disposeIfEmpty() {
            if (getChildCount() == 0) {
                ArrayList arrayList = this.f6353c;
                if (arrayList == null || arrayList.size() == 0) {
                    this.mDisposed = true;
                    this.f6351a.removeView(this);
                }
            }
        }

        private void getOffset(int[] iArr) {
            int[] iArr2 = new int[2];
            int[] iArr3 = new int[2];
            this.f6351a.getLocationOnScreen(iArr2);
            this.f6352b.getLocationOnScreen(iArr3);
            iArr[0] = iArr3[0] - iArr2[0];
            iArr[1] = iArr3[1] - iArr2[1];
        }

        public void add(Drawable drawable) {
            assertNotDisposed();
            if (this.f6353c == null) {
                this.f6353c = new ArrayList();
            }
            if (this.f6353c.contains(drawable)) {
                return;
            }
            this.f6353c.add(drawable);
            invalidate(drawable.getBounds());
            drawable.setCallback(this);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            this.f6351a.getLocationOnScreen(new int[2]);
            this.f6352b.getLocationOnScreen(new int[2]);
            canvas.translate(r0[0] - r1[0], r0[1] - r1[1]);
            canvas.clipRect(new Rect(0, 0, this.f6352b.getWidth(), this.f6352b.getHeight()));
            super.dispatchDraw(canvas);
            ArrayList arrayList = this.f6353c;
            int size = arrayList == null ? 0 : arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((Drawable) this.f6353c.get(i2)).draw(canvas);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public ViewParent invalidateChildInParent(int[] iArr, Rect rect) {
            if (this.f6351a == null) {
                return null;
            }
            rect.offset(iArr[0], iArr[1]);
            if (this.f6351a == null) {
                invalidate(rect);
                return null;
            }
            iArr[0] = 0;
            iArr[1] = 0;
            int[] iArr2 = new int[2];
            getOffset(iArr2);
            rect.offset(iArr2[0], iArr2[1]);
            return super.invalidateChildInParent(iArr, rect);
        }

        @Override // android.view.View, android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(@NonNull Drawable drawable) {
            invalidate(drawable.getBounds());
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        }

        public void remove(Drawable drawable) {
            ArrayList arrayList = this.f6353c;
            if (arrayList != null) {
                arrayList.remove(drawable);
                invalidate(drawable.getBounds());
                drawable.setCallback(null);
                disposeIfEmpty();
            }
        }

        @Override // android.view.View
        protected boolean verifyDrawable(Drawable drawable) {
            ArrayList arrayList;
            return super.verifyDrawable(drawable) || ((arrayList = this.f6353c) != null && arrayList.contains(drawable));
        }

        public void remove(View view) {
            super.removeView(view);
            disposeIfEmpty();
        }

        public void add(View view) {
            assertNotDisposed();
            if (view.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                if (viewGroup != this.f6351a && viewGroup.getParent() != null && ViewCompat.isAttachedToWindow(viewGroup)) {
                    int[] iArr = new int[2];
                    int[] iArr2 = new int[2];
                    viewGroup.getLocationOnScreen(iArr);
                    this.f6351a.getLocationOnScreen(iArr2);
                    ViewCompat.offsetLeftAndRight(view, iArr[0] - iArr2[0]);
                    ViewCompat.offsetTopAndBottom(view, iArr[1] - iArr2[1]);
                }
                viewGroup.removeView(view);
                if (view.getParent() != null) {
                    viewGroup.removeView(view);
                }
            }
            super.addView(view);
        }
    }
}
