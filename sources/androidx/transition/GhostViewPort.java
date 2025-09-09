package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import java.lang.reflect.InvocationTargetException;

@SuppressLint({"ViewConstructor"})
/* loaded from: classes2.dex */
class GhostViewPort extends ViewGroup implements GhostView {

    /* renamed from: a, reason: collision with root package name */
    ViewGroup f6297a;

    /* renamed from: b, reason: collision with root package name */
    View f6298b;

    /* renamed from: c, reason: collision with root package name */
    final View f6299c;

    /* renamed from: d, reason: collision with root package name */
    int f6300d;

    @Nullable
    private Matrix mMatrix;
    private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;

    GhostViewPort(View view) {
        super(view.getContext());
        this.mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: androidx.transition.GhostViewPort.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                View view2;
                ViewCompat.postInvalidateOnAnimation(GhostViewPort.this);
                GhostViewPort ghostViewPort = GhostViewPort.this;
                ViewGroup viewGroup = ghostViewPort.f6297a;
                if (viewGroup == null || (view2 = ghostViewPort.f6298b) == null) {
                    return true;
                }
                viewGroup.endViewTransition(view2);
                ViewCompat.postInvalidateOnAnimation(GhostViewPort.this.f6297a);
                GhostViewPort ghostViewPort2 = GhostViewPort.this;
                ghostViewPort2.f6297a = null;
                ghostViewPort2.f6298b = null;
                return true;
            }
        };
        this.f6299c = view;
        setWillNotDraw(false);
        setClipChildren(false);
        setLayerType(2, null);
    }

    static GhostViewPort a(View view, ViewGroup viewGroup, Matrix matrix) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int i2;
        GhostViewHolder ghostViewHolder;
        if (!(view.getParent() instanceof ViewGroup)) {
            throw new IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
        }
        GhostViewHolder ghostViewHolderB = GhostViewHolder.b(viewGroup);
        GhostViewPort ghostViewPortD = d(view);
        if (ghostViewPortD == null || (ghostViewHolder = (GhostViewHolder) ghostViewPortD.getParent()) == ghostViewHolderB) {
            i2 = 0;
        } else {
            i2 = ghostViewPortD.f6300d;
            ghostViewHolder.removeView(ghostViewPortD);
            ghostViewPortD = null;
        }
        if (ghostViewPortD == null) {
            if (matrix == null) {
                matrix = new Matrix();
                b(view, viewGroup, matrix);
            }
            ghostViewPortD = new GhostViewPort(view);
            ghostViewPortD.g(matrix);
            if (ghostViewHolderB == null) {
                ghostViewHolderB = new GhostViewHolder(viewGroup);
            } else {
                ghostViewHolderB.c();
            }
            c(viewGroup, ghostViewHolderB);
            c(viewGroup, ghostViewPortD);
            ghostViewHolderB.a(ghostViewPortD);
            ghostViewPortD.f6300d = i2;
        } else if (matrix != null) {
            ghostViewPortD.g(matrix);
        }
        ghostViewPortD.f6300d++;
        return ghostViewPortD;
    }

    static void b(View view, ViewGroup viewGroup, Matrix matrix) {
        ViewGroup viewGroup2 = (ViewGroup) view.getParent();
        matrix.reset();
        ViewUtils.j(viewGroup2, matrix);
        matrix.preTranslate(-viewGroup2.getScrollX(), -viewGroup2.getScrollY());
        ViewUtils.k(viewGroup, matrix);
    }

    static void c(View view, View view2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ViewUtils.g(view2, view2.getLeft(), view2.getTop(), view2.getLeft() + view.getWidth(), view2.getTop() + view.getHeight());
    }

    static GhostViewPort d(View view) {
        return (GhostViewPort) view.getTag(R.id.ghost_view);
    }

    static void e(View view) {
        GhostViewPort ghostViewPortD = d(view);
        if (ghostViewPortD != null) {
            int i2 = ghostViewPortD.f6300d - 1;
            ghostViewPortD.f6300d = i2;
            if (i2 <= 0) {
                ((GhostViewHolder) ghostViewPortD.getParent()).removeView(ghostViewPortD);
            }
        }
    }

    static void f(View view, GhostViewPort ghostViewPort) {
        view.setTag(R.id.ghost_view, ghostViewPort);
    }

    void g(Matrix matrix) {
        this.mMatrix = matrix;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        f(this.f6299c, this);
        this.f6299c.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        ViewUtils.i(this.f6299c, 4);
        if (this.f6299c.getParent() != null) {
            ((View) this.f6299c.getParent()).invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        this.f6299c.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        ViewUtils.i(this.f6299c, 0);
        f(this.f6299c, null);
        if (this.f6299c.getParent() != null) {
            ((View) this.f6299c.getParent()).invalidate();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        CanvasUtils.a(canvas, true);
        canvas.setMatrix(this.mMatrix);
        ViewUtils.i(this.f6299c, 0);
        this.f6299c.invalidate();
        ViewUtils.i(this.f6299c, 4);
        drawChild(canvas, this.f6299c, getDrawingTime());
        CanvasUtils.a(canvas, false);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
    }

    @Override // androidx.transition.GhostView
    public void reserveEndViewTransition(ViewGroup viewGroup, View view) {
        this.f6297a = viewGroup;
        this.f6298b = view;
    }

    @Override // android.view.View, androidx.transition.GhostView
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        if (d(this.f6299c) == this) {
            ViewUtils.i(this.f6299c, i2 == 0 ? 4 : 0);
        }
    }
}
