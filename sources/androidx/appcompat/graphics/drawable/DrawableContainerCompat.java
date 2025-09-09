package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;

/* loaded from: classes.dex */
public class DrawableContainerCompat extends Drawable implements Drawable.Callback {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_DITHER = true;
    private static final String TAG = "DrawableContainerCompat";
    private Runnable mAnimationRunnable;
    private BlockInvalidateCallback mBlockInvalidateCallback;
    private Drawable mCurrDrawable;
    private DrawableContainerState mDrawableContainerState;
    private long mEnterAnimationEnd;
    private long mExitAnimationEnd;
    private boolean mHasAlpha;
    private Rect mHotspotBounds;
    private Drawable mLastDrawable;
    private boolean mMutated;
    private int mAlpha = 255;
    private int mCurIndex = -1;

    @RequiresApi(21)
    private static class Api21Impl {
        private Api21Impl() {
        }

        public static boolean canApplyTheme(Drawable.ConstantState constantState) {
            return constantState.canApplyTheme();
        }

        public static void getOutline(Drawable drawable, Outline outline) {
            drawable.getOutline(outline);
        }

        public static Resources getResources(Resources.Theme theme) {
            return theme.getResources();
        }
    }

    static class BlockInvalidateCallback implements Drawable.Callback {
        private Drawable.Callback mCallback;

        BlockInvalidateCallback() {
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(@NonNull Drawable drawable) {
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j2) {
            Drawable.Callback callback = this.mCallback;
            if (callback != null) {
                callback.scheduleDrawable(drawable, runnable, j2);
            }
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
            Drawable.Callback callback = this.mCallback;
            if (callback != null) {
                callback.unscheduleDrawable(drawable, runnable);
            }
        }

        public Drawable.Callback unwrap() {
            Drawable.Callback callback = this.mCallback;
            this.mCallback = null;
            return callback;
        }

        public BlockInvalidateCallback wrap(Drawable.Callback callback) {
            this.mCallback = callback;
            return this;
        }
    }

    static abstract class DrawableContainerState extends Drawable.ConstantState {
        int A;
        int B;
        boolean C;
        ColorFilter D;
        boolean E;
        ColorStateList F;
        PorterDuff.Mode G;
        boolean H;
        boolean I;

        /* renamed from: a, reason: collision with root package name */
        final DrawableContainerCompat f2080a;

        /* renamed from: b, reason: collision with root package name */
        Resources f2081b;

        /* renamed from: c, reason: collision with root package name */
        int f2082c;

        /* renamed from: d, reason: collision with root package name */
        int f2083d;

        /* renamed from: e, reason: collision with root package name */
        int f2084e;

        /* renamed from: f, reason: collision with root package name */
        SparseArray f2085f;

        /* renamed from: g, reason: collision with root package name */
        Drawable[] f2086g;

        /* renamed from: h, reason: collision with root package name */
        int f2087h;

        /* renamed from: i, reason: collision with root package name */
        boolean f2088i;

        /* renamed from: j, reason: collision with root package name */
        boolean f2089j;

        /* renamed from: k, reason: collision with root package name */
        Rect f2090k;

        /* renamed from: l, reason: collision with root package name */
        boolean f2091l;

        /* renamed from: m, reason: collision with root package name */
        boolean f2092m;

        /* renamed from: n, reason: collision with root package name */
        int f2093n;

        /* renamed from: o, reason: collision with root package name */
        int f2094o;

        /* renamed from: p, reason: collision with root package name */
        int f2095p;

        /* renamed from: q, reason: collision with root package name */
        int f2096q;

        /* renamed from: r, reason: collision with root package name */
        boolean f2097r;

        /* renamed from: s, reason: collision with root package name */
        int f2098s;

        /* renamed from: t, reason: collision with root package name */
        boolean f2099t;

        /* renamed from: u, reason: collision with root package name */
        boolean f2100u;

        /* renamed from: v, reason: collision with root package name */
        boolean f2101v;

        /* renamed from: w, reason: collision with root package name */
        boolean f2102w;

        /* renamed from: x, reason: collision with root package name */
        boolean f2103x;

        /* renamed from: y, reason: collision with root package name */
        boolean f2104y;

        /* renamed from: z, reason: collision with root package name */
        int f2105z;

        DrawableContainerState(DrawableContainerState drawableContainerState, DrawableContainerCompat drawableContainerCompat, Resources resources) {
            this.f2088i = false;
            this.f2091l = false;
            this.f2103x = true;
            this.A = 0;
            this.B = 0;
            this.f2080a = drawableContainerCompat;
            this.f2081b = resources != null ? resources : drawableContainerState != null ? drawableContainerState.f2081b : null;
            int iD = DrawableContainerCompat.d(resources, drawableContainerState != null ? drawableContainerState.f2082c : 0);
            this.f2082c = iD;
            if (drawableContainerState == null) {
                this.f2086g = new Drawable[10];
                this.f2087h = 0;
                return;
            }
            this.f2083d = drawableContainerState.f2083d;
            this.f2084e = drawableContainerState.f2084e;
            this.f2101v = true;
            this.f2102w = true;
            this.f2088i = drawableContainerState.f2088i;
            this.f2091l = drawableContainerState.f2091l;
            this.f2103x = drawableContainerState.f2103x;
            this.f2104y = drawableContainerState.f2104y;
            this.f2105z = drawableContainerState.f2105z;
            this.A = drawableContainerState.A;
            this.B = drawableContainerState.B;
            this.C = drawableContainerState.C;
            this.D = drawableContainerState.D;
            this.E = drawableContainerState.E;
            this.F = drawableContainerState.F;
            this.G = drawableContainerState.G;
            this.H = drawableContainerState.H;
            this.I = drawableContainerState.I;
            if (drawableContainerState.f2082c == iD) {
                if (drawableContainerState.f2089j) {
                    this.f2090k = drawableContainerState.f2090k != null ? new Rect(drawableContainerState.f2090k) : null;
                    this.f2089j = true;
                }
                if (drawableContainerState.f2092m) {
                    this.f2093n = drawableContainerState.f2093n;
                    this.f2094o = drawableContainerState.f2094o;
                    this.f2095p = drawableContainerState.f2095p;
                    this.f2096q = drawableContainerState.f2096q;
                    this.f2092m = true;
                }
            }
            if (drawableContainerState.f2097r) {
                this.f2098s = drawableContainerState.f2098s;
                this.f2097r = true;
            }
            if (drawableContainerState.f2099t) {
                this.f2100u = drawableContainerState.f2100u;
                this.f2099t = true;
            }
            Drawable[] drawableArr = drawableContainerState.f2086g;
            this.f2086g = new Drawable[drawableArr.length];
            this.f2087h = drawableContainerState.f2087h;
            SparseArray sparseArray = drawableContainerState.f2085f;
            if (sparseArray != null) {
                this.f2085f = sparseArray.clone();
            } else {
                this.f2085f = new SparseArray(this.f2087h);
            }
            int i2 = this.f2087h;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                if (drawable != null) {
                    Drawable.ConstantState constantState = drawable.getConstantState();
                    if (constantState != null) {
                        this.f2085f.put(i3, constantState);
                    } else {
                        this.f2086g[i3] = drawableArr[i3];
                    }
                }
            }
        }

        private void createAllFutures() {
            SparseArray sparseArray = this.f2085f;
            if (sparseArray != null) {
                int size = sparseArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.f2086g[this.f2085f.keyAt(i2)] = prepareDrawable(((Drawable.ConstantState) this.f2085f.valueAt(i2)).newDrawable(this.f2081b));
                }
                this.f2085f = null;
            }
        }

        private Drawable prepareDrawable(Drawable drawable) {
            DrawableCompat.setLayoutDirection(drawable, this.f2105z);
            Drawable drawableMutate = drawable.mutate();
            drawableMutate.setCallback(this.f2080a);
            return drawableMutate;
        }

        final void a(Resources.Theme theme) {
            if (theme != null) {
                createAllFutures();
                int i2 = this.f2087h;
                Drawable[] drawableArr = this.f2086g;
                for (int i3 = 0; i3 < i2; i3++) {
                    Drawable drawable = drawableArr[i3];
                    if (drawable != null && DrawableCompat.canApplyTheme(drawable)) {
                        DrawableCompat.applyTheme(drawableArr[i3], theme);
                        this.f2084e |= drawableArr[i3].getChangingConfigurations();
                    }
                }
                g(Api21Impl.getResources(theme));
            }
        }

        public final int addChild(Drawable drawable) {
            int i2 = this.f2087h;
            if (i2 >= this.f2086g.length) {
                growArray(i2, i2 + 10);
            }
            drawable.mutate();
            drawable.setVisible(false, true);
            drawable.setCallback(this.f2080a);
            this.f2086g[i2] = drawable;
            this.f2087h++;
            this.f2084e = drawable.getChangingConfigurations() | this.f2084e;
            d();
            this.f2090k = null;
            this.f2089j = false;
            this.f2092m = false;
            this.f2101v = false;
            return i2;
        }

        protected void b() {
            this.f2092m = true;
            createAllFutures();
            int i2 = this.f2087h;
            Drawable[] drawableArr = this.f2086g;
            this.f2094o = -1;
            this.f2093n = -1;
            this.f2096q = 0;
            this.f2095p = 0;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                int intrinsicWidth = drawable.getIntrinsicWidth();
                if (intrinsicWidth > this.f2093n) {
                    this.f2093n = intrinsicWidth;
                }
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicHeight > this.f2094o) {
                    this.f2094o = intrinsicHeight;
                }
                int minimumWidth = drawable.getMinimumWidth();
                if (minimumWidth > this.f2095p) {
                    this.f2095p = minimumWidth;
                }
                int minimumHeight = drawable.getMinimumHeight();
                if (minimumHeight > this.f2096q) {
                    this.f2096q = minimumHeight;
                }
            }
        }

        final int c() {
            return this.f2086g.length;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @RequiresApi(21)
        public boolean canApplyTheme() {
            int i2 = this.f2087h;
            Drawable[] drawableArr = this.f2086g;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                if (drawable == null) {
                    Drawable.ConstantState constantState = (Drawable.ConstantState) this.f2085f.get(i3);
                    if (constantState != null && Api21Impl.canApplyTheme(constantState)) {
                        return true;
                    }
                } else if (DrawableCompat.canApplyTheme(drawable)) {
                    return true;
                }
            }
            return false;
        }

        public boolean canConstantState() {
            if (this.f2101v) {
                return this.f2102w;
            }
            createAllFutures();
            this.f2101v = true;
            int i2 = this.f2087h;
            Drawable[] drawableArr = this.f2086g;
            for (int i3 = 0; i3 < i2; i3++) {
                if (drawableArr[i3].getConstantState() == null) {
                    this.f2102w = false;
                    return false;
                }
            }
            this.f2102w = true;
            return true;
        }

        void d() {
            this.f2097r = false;
            this.f2099t = false;
        }

        void e() {
            int i2 = this.f2087h;
            Drawable[] drawableArr = this.f2086g;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                if (drawable != null) {
                    drawable.mutate();
                }
            }
            this.f2104y = true;
        }

        final boolean f(int i2, int i3) {
            int i4 = this.f2087h;
            Drawable[] drawableArr = this.f2086g;
            boolean z2 = false;
            for (int i5 = 0; i5 < i4; i5++) {
                Drawable drawable = drawableArr[i5];
                if (drawable != null) {
                    boolean layoutDirection = DrawableCompat.setLayoutDirection(drawable, i2);
                    if (i5 == i3) {
                        z2 = layoutDirection;
                    }
                }
            }
            this.f2105z = i2;
            return z2;
        }

        final void g(Resources resources) {
            if (resources != null) {
                this.f2081b = resources;
                int iD = DrawableContainerCompat.d(resources, this.f2082c);
                int i2 = this.f2082c;
                this.f2082c = iD;
                if (i2 != iD) {
                    this.f2092m = false;
                    this.f2089j = false;
                }
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f2083d | this.f2084e;
        }

        public final Drawable getChild(int i2) {
            int iIndexOfKey;
            Drawable drawable = this.f2086g[i2];
            if (drawable != null) {
                return drawable;
            }
            SparseArray sparseArray = this.f2085f;
            if (sparseArray == null || (iIndexOfKey = sparseArray.indexOfKey(i2)) < 0) {
                return null;
            }
            Drawable drawablePrepareDrawable = prepareDrawable(((Drawable.ConstantState) this.f2085f.valueAt(iIndexOfKey)).newDrawable(this.f2081b));
            this.f2086g[i2] = drawablePrepareDrawable;
            this.f2085f.removeAt(iIndexOfKey);
            if (this.f2085f.size() == 0) {
                this.f2085f = null;
            }
            return drawablePrepareDrawable;
        }

        public final int getChildCount() {
            return this.f2087h;
        }

        public final int getConstantHeight() {
            if (!this.f2092m) {
                b();
            }
            return this.f2094o;
        }

        public final int getConstantMinimumHeight() {
            if (!this.f2092m) {
                b();
            }
            return this.f2096q;
        }

        public final int getConstantMinimumWidth() {
            if (!this.f2092m) {
                b();
            }
            return this.f2095p;
        }

        public final Rect getConstantPadding() {
            Rect rect = null;
            if (this.f2088i) {
                return null;
            }
            Rect rect2 = this.f2090k;
            if (rect2 != null || this.f2089j) {
                return rect2;
            }
            createAllFutures();
            Rect rect3 = new Rect();
            int i2 = this.f2087h;
            Drawable[] drawableArr = this.f2086g;
            for (int i3 = 0; i3 < i2; i3++) {
                if (drawableArr[i3].getPadding(rect3)) {
                    if (rect == null) {
                        rect = new Rect(0, 0, 0, 0);
                    }
                    int i4 = rect3.left;
                    if (i4 > rect.left) {
                        rect.left = i4;
                    }
                    int i5 = rect3.top;
                    if (i5 > rect.top) {
                        rect.top = i5;
                    }
                    int i6 = rect3.right;
                    if (i6 > rect.right) {
                        rect.right = i6;
                    }
                    int i7 = rect3.bottom;
                    if (i7 > rect.bottom) {
                        rect.bottom = i7;
                    }
                }
            }
            this.f2089j = true;
            this.f2090k = rect;
            return rect;
        }

        public final int getConstantWidth() {
            if (!this.f2092m) {
                b();
            }
            return this.f2093n;
        }

        public final int getEnterFadeDuration() {
            return this.A;
        }

        public final int getExitFadeDuration() {
            return this.B;
        }

        public final int getOpacity() {
            if (this.f2097r) {
                return this.f2098s;
            }
            createAllFutures();
            int i2 = this.f2087h;
            Drawable[] drawableArr = this.f2086g;
            int opacity = i2 > 0 ? drawableArr[0].getOpacity() : -2;
            for (int i3 = 1; i3 < i2; i3++) {
                opacity = Drawable.resolveOpacity(opacity, drawableArr[i3].getOpacity());
            }
            this.f2098s = opacity;
            this.f2097r = true;
            return opacity;
        }

        public void growArray(int i2, int i3) {
            Drawable[] drawableArr = new Drawable[i3];
            Drawable[] drawableArr2 = this.f2086g;
            if (drawableArr2 != null) {
                System.arraycopy(drawableArr2, 0, drawableArr, 0, i2);
            }
            this.f2086g = drawableArr;
        }

        public final boolean isConstantSize() {
            return this.f2091l;
        }

        public final boolean isStateful() {
            if (this.f2099t) {
                return this.f2100u;
            }
            createAllFutures();
            int i2 = this.f2087h;
            Drawable[] drawableArr = this.f2086g;
            boolean z2 = false;
            int i3 = 0;
            while (true) {
                if (i3 >= i2) {
                    break;
                }
                if (drawableArr[i3].isStateful()) {
                    z2 = true;
                    break;
                }
                i3++;
            }
            this.f2100u = z2;
            this.f2099t = true;
            return z2;
        }

        public final void setConstantSize(boolean z2) {
            this.f2091l = z2;
        }

        public final void setEnterFadeDuration(int i2) {
            this.A = i2;
        }

        public final void setExitFadeDuration(int i2) {
            this.B = i2;
        }

        public final void setVariablePadding(boolean z2) {
            this.f2088i = z2;
        }
    }

    static int d(Resources resources, int i2) {
        if (resources != null) {
            i2 = resources.getDisplayMetrics().densityDpi;
        }
        if (i2 == 0) {
            return 160;
        }
        return i2;
    }

    private void initializeDrawableForDisplay(Drawable drawable) {
        if (this.mBlockInvalidateCallback == null) {
            this.mBlockInvalidateCallback = new BlockInvalidateCallback();
        }
        drawable.setCallback(this.mBlockInvalidateCallback.wrap(drawable.getCallback()));
        try {
            if (this.mDrawableContainerState.A <= 0 && this.mHasAlpha) {
                drawable.setAlpha(this.mAlpha);
            }
            DrawableContainerState drawableContainerState = this.mDrawableContainerState;
            if (drawableContainerState.E) {
                drawable.setColorFilter(drawableContainerState.D);
            } else {
                if (drawableContainerState.H) {
                    DrawableCompat.setTintList(drawable, drawableContainerState.F);
                }
                DrawableContainerState drawableContainerState2 = this.mDrawableContainerState;
                if (drawableContainerState2.I) {
                    DrawableCompat.setTintMode(drawable, drawableContainerState2.G);
                }
            }
            drawable.setVisible(isVisible(), true);
            drawable.setDither(this.mDrawableContainerState.f2103x);
            drawable.setState(getState());
            drawable.setLevel(getLevel());
            drawable.setBounds(getBounds());
            DrawableCompat.setLayoutDirection(drawable, DrawableCompat.getLayoutDirection(this));
            DrawableCompat.setAutoMirrored(drawable, this.mDrawableContainerState.C);
            Rect rect = this.mHotspotBounds;
            if (rect != null) {
                DrawableCompat.setHotspotBounds(drawable, rect.left, rect.top, rect.right, rect.bottom);
            }
            drawable.setCallback(this.mBlockInvalidateCallback.unwrap());
        } catch (Throwable th) {
            drawable.setCallback(this.mBlockInvalidateCallback.unwrap());
            throw th;
        }
    }

    private boolean needsMirroring() {
        return isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0066 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void a(boolean r14) {
        /*
            r13 = this;
            r0 = 1
            r13.mHasAlpha = r0
            long r1 = android.os.SystemClock.uptimeMillis()
            android.graphics.drawable.Drawable r3 = r13.mCurrDrawable
            r4 = 255(0xff, double:1.26E-321)
            r6 = 0
            r8 = 0
            if (r3 == 0) goto L36
            long r9 = r13.mEnterAnimationEnd
            int r11 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r11 == 0) goto L38
            int r11 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r11 > 0) goto L22
            int r9 = r13.mAlpha
            r3.setAlpha(r9)
            r13.mEnterAnimationEnd = r6
            goto L38
        L22:
            long r9 = r9 - r1
            long r9 = r9 * r4
            int r9 = (int) r9
            androidx.appcompat.graphics.drawable.DrawableContainerCompat$DrawableContainerState r10 = r13.mDrawableContainerState
            int r10 = r10.A
            int r9 = r9 / r10
            int r9 = 255 - r9
            int r10 = r13.mAlpha
            int r9 = r9 * r10
            int r9 = r9 / 255
            r3.setAlpha(r9)
            r3 = r0
            goto L39
        L36:
            r13.mEnterAnimationEnd = r6
        L38:
            r3 = r8
        L39:
            android.graphics.drawable.Drawable r9 = r13.mLastDrawable
            if (r9 == 0) goto L61
            long r10 = r13.mExitAnimationEnd
            int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r12 == 0) goto L63
            int r12 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r12 > 0) goto L50
            r9.setVisible(r8, r8)
            r0 = 0
            r13.mLastDrawable = r0
            r13.mExitAnimationEnd = r6
            goto L63
        L50:
            long r10 = r10 - r1
            long r10 = r10 * r4
            int r3 = (int) r10
            androidx.appcompat.graphics.drawable.DrawableContainerCompat$DrawableContainerState r4 = r13.mDrawableContainerState
            int r4 = r4.B
            int r3 = r3 / r4
            int r4 = r13.mAlpha
            int r3 = r3 * r4
            int r3 = r3 / 255
            r9.setAlpha(r3)
            goto L64
        L61:
            r13.mExitAnimationEnd = r6
        L63:
            r0 = r3
        L64:
            if (r14 == 0) goto L70
            if (r0 == 0) goto L70
            java.lang.Runnable r14 = r13.mAnimationRunnable
            r3 = 16
            long r1 = r1 + r3
            r13.scheduleSelf(r14, r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.graphics.drawable.DrawableContainerCompat.a(boolean):void");
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void applyTheme(@NonNull Resources.Theme theme) {
        this.mDrawableContainerState.a(theme);
    }

    DrawableContainerState b() {
        return this.mDrawableContainerState;
    }

    int c() {
        return this.mCurIndex;
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public boolean canApplyTheme() {
        return this.mDrawableContainerState.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean e(int r10) {
        /*
            r9 = this;
            int r0 = r9.mCurIndex
            r1 = 0
            if (r10 != r0) goto L6
            return r1
        L6:
            long r2 = android.os.SystemClock.uptimeMillis()
            androidx.appcompat.graphics.drawable.DrawableContainerCompat$DrawableContainerState r0 = r9.mDrawableContainerState
            int r0 = r0.B
            r4 = 0
            r5 = 0
            if (r0 <= 0) goto L2e
            android.graphics.drawable.Drawable r0 = r9.mLastDrawable
            if (r0 == 0) goto L1a
            r0.setVisible(r1, r1)
        L1a:
            android.graphics.drawable.Drawable r0 = r9.mCurrDrawable
            if (r0 == 0) goto L29
            r9.mLastDrawable = r0
            androidx.appcompat.graphics.drawable.DrawableContainerCompat$DrawableContainerState r0 = r9.mDrawableContainerState
            int r0 = r0.B
            long r0 = (long) r0
            long r0 = r0 + r2
            r9.mExitAnimationEnd = r0
            goto L35
        L29:
            r9.mLastDrawable = r4
            r9.mExitAnimationEnd = r5
            goto L35
        L2e:
            android.graphics.drawable.Drawable r0 = r9.mCurrDrawable
            if (r0 == 0) goto L35
            r0.setVisible(r1, r1)
        L35:
            if (r10 < 0) goto L55
            androidx.appcompat.graphics.drawable.DrawableContainerCompat$DrawableContainerState r0 = r9.mDrawableContainerState
            int r1 = r0.f2087h
            if (r10 >= r1) goto L55
            android.graphics.drawable.Drawable r0 = r0.getChild(r10)
            r9.mCurrDrawable = r0
            r9.mCurIndex = r10
            if (r0 == 0) goto L5a
            androidx.appcompat.graphics.drawable.DrawableContainerCompat$DrawableContainerState r10 = r9.mDrawableContainerState
            int r10 = r10.A
            if (r10 <= 0) goto L51
            long r7 = (long) r10
            long r2 = r2 + r7
            r9.mEnterAnimationEnd = r2
        L51:
            r9.initializeDrawableForDisplay(r0)
            goto L5a
        L55:
            r9.mCurrDrawable = r4
            r10 = -1
            r9.mCurIndex = r10
        L5a:
            long r0 = r9.mEnterAnimationEnd
            int r10 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            r0 = 1
            if (r10 != 0) goto L67
            long r1 = r9.mExitAnimationEnd
            int r10 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r10 == 0) goto L79
        L67:
            java.lang.Runnable r10 = r9.mAnimationRunnable
            if (r10 != 0) goto L73
            androidx.appcompat.graphics.drawable.DrawableContainerCompat$1 r10 = new androidx.appcompat.graphics.drawable.DrawableContainerCompat$1
            r10.<init>()
            r9.mAnimationRunnable = r10
            goto L76
        L73:
            r9.unscheduleSelf(r10)
        L76:
            r9.a(r0)
        L79:
            r9.invalidateSelf()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.graphics.drawable.DrawableContainerCompat.e(int):boolean");
    }

    void f(DrawableContainerState drawableContainerState) {
        this.mDrawableContainerState = drawableContainerState;
        int i2 = this.mCurIndex;
        if (i2 >= 0) {
            Drawable child = drawableContainerState.getChild(i2);
            this.mCurrDrawable = child;
            if (child != null) {
                initializeDrawableForDisplay(child);
            }
        }
        this.mLastDrawable = null;
    }

    final void g(Resources resources) {
        this.mDrawableContainerState.g(resources);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mDrawableContainerState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (!this.mDrawableContainerState.canConstantState()) {
            return null;
        }
        this.mDrawableContainerState.f2083d = getChangingConfigurations();
        return this.mDrawableContainerState;
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    public Drawable getCurrent() {
        return this.mCurrDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    public void getHotspotBounds(@NonNull Rect rect) {
        Rect rect2 = this.mHotspotBounds;
        if (rect2 != null) {
            rect.set(rect2);
        } else {
            super.getHotspotBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantHeight();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantWidth();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumHeight();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getMinimumHeight();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumWidth();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getMinimumWidth();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.mCurrDrawable;
        if (drawable == null || !drawable.isVisible()) {
            return -2;
        }
        return this.mDrawableContainerState.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void getOutline(@NonNull Outline outline) {
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            Api21Impl.getOutline(drawable, outline);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(@NonNull Rect rect) {
        boolean padding;
        Rect constantPadding = this.mDrawableContainerState.getConstantPadding();
        if (constantPadding != null) {
            rect.set(constantPadding);
            padding = (constantPadding.right | ((constantPadding.left | constantPadding.top) | constantPadding.bottom)) != 0;
        } else {
            Drawable drawable = this.mCurrDrawable;
            padding = drawable != null ? drawable.getPadding(rect) : super.getPadding(rect);
        }
        if (needsMirroring()) {
            int i2 = rect.left;
            rect.left = rect.right;
            rect.right = i2;
        }
        return padding;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(@NonNull Drawable drawable) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState != null) {
            drawableContainerState.d();
        }
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return;
        }
        getCallback().invalidateDrawable(this);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mDrawableContainerState.C;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mDrawableContainerState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        boolean z2;
        Drawable drawable = this.mLastDrawable;
        boolean z3 = true;
        if (drawable != null) {
            drawable.jumpToCurrentState();
            this.mLastDrawable = null;
            z2 = true;
        } else {
            z2 = false;
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
            if (this.mHasAlpha) {
                this.mCurrDrawable.setAlpha(this.mAlpha);
            }
        }
        if (this.mExitAnimationEnd != 0) {
            this.mExitAnimationEnd = 0L;
            z2 = true;
        }
        if (this.mEnterAnimationEnd != 0) {
            this.mEnterAnimationEnd = 0L;
        } else {
            z3 = z2;
        }
        if (z3) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            DrawableContainerState drawableContainerStateB = b();
            drawableContainerStateB.e();
            f(drawableContainerStateB);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i2) {
        return this.mDrawableContainerState.f(i2, c());
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i2) {
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            return drawable.setLevel(i2);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.setLevel(i2);
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.setState(iArr);
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j2) {
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return;
        }
        getCallback().scheduleDrawable(this, runnable, j2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        if (this.mHasAlpha && this.mAlpha == i2) {
            return;
        }
        this.mHasAlpha = true;
        this.mAlpha = i2;
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            if (this.mEnterAnimationEnd == 0) {
                drawable.setAlpha(i2);
            } else {
                a(false);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z2) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState.C != z2) {
            drawableContainerState.C = z2;
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                DrawableCompat.setAutoMirrored(drawable, z2);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        drawableContainerState.E = true;
        if (drawableContainerState.D != colorFilter) {
            drawableContainerState.D = colorFilter;
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z2) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState.f2103x != z2) {
            drawableContainerState.f2103x = z2;
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                drawable.setDither(z2);
            }
        }
    }

    public void setEnterFadeDuration(int i2) {
        this.mDrawableContainerState.A = i2;
    }

    public void setExitFadeDuration(int i2) {
        this.mDrawableContainerState.B = i2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f2, float f3) {
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspot(drawable, f2, f3);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i2, int i3, int i4, int i5) {
        Rect rect = this.mHotspotBounds;
        if (rect == null) {
            this.mHotspotBounds = new Rect(i2, i3, i4, i5);
        } else {
            rect.set(i2, i3, i4, i5);
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspotBounds(drawable, i2, i3, i4, i5);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTint(@ColorInt int i2) {
        setTintList(ColorStateList.valueOf(i2));
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        drawableContainerState.H = true;
        if (drawableContainerState.F != colorStateList) {
            drawableContainerState.F = colorStateList;
            DrawableCompat.setTintList(this.mCurrDrawable, colorStateList);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        drawableContainerState.I = true;
        if (drawableContainerState.G != mode) {
            drawableContainerState.G = mode;
            DrawableCompat.setTintMode(this.mCurrDrawable, mode);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z2, boolean z3) {
        boolean visible = super.setVisible(z2, z3);
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            drawable.setVisible(z2, z3);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.setVisible(z2, z3);
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return;
        }
        getCallback().unscheduleDrawable(this, runnable);
    }
}
