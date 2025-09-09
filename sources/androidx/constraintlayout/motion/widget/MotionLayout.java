package androidx.constraintlayout.motion.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Flow;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.core.widgets.Placeholder;
import androidx.constraintlayout.core.widgets.VirtualLayout;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.StateSet;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class MotionLayout extends ConstraintLayout implements NestedScrollingParent3 {
    private static final boolean DEBUG = false;
    public static final int DEBUG_SHOW_NONE = 0;
    public static final int DEBUG_SHOW_PATH = 2;
    public static final int DEBUG_SHOW_PROGRESS = 1;
    private static final float EPSILON = 1.0E-5f;
    public static boolean IS_IN_EDIT_MODE = false;
    public static final int TOUCH_UP_COMPLETE = 0;
    public static final int TOUCH_UP_COMPLETE_TO_END = 2;
    public static final int TOUCH_UP_COMPLETE_TO_START = 1;
    public static final int TOUCH_UP_DECELERATE = 4;
    public static final int TOUCH_UP_DECELERATE_AND_COMPLETE = 5;
    public static final int TOUCH_UP_NEVER_TO_END = 7;
    public static final int TOUCH_UP_NEVER_TO_START = 6;
    public static final int TOUCH_UP_STOP = 3;
    public static final int VELOCITY_LAYOUT = 1;
    public static final int VELOCITY_POST_LAYOUT = 0;
    public static final int VELOCITY_STATIC_LAYOUT = 3;
    public static final int VELOCITY_STATIC_POST_LAYOUT = 2;
    int A;
    boolean B;
    float C;
    float D;
    long E;
    float F;
    boolean G;
    protected boolean H;
    int I;
    int J;
    int K;
    int L;
    int M;
    int N;
    float O;
    int P;
    int Q;
    HashMap R;
    Rect S;
    TransitionState T;
    Model U;
    ArrayList V;

    /* renamed from: j, reason: collision with root package name */
    MotionScene f3042j;

    /* renamed from: k, reason: collision with root package name */
    Interpolator f3043k;

    /* renamed from: l, reason: collision with root package name */
    Interpolator f3044l;
    private float lastPos;
    private float lastY;

    /* renamed from: m, reason: collision with root package name */
    float f3045m;
    private long mAnimationStartTime;
    private int mBeginState;
    private RectF mBoundsCheck;
    private DecelerateInterpolator mDecelerateLogic;
    private ArrayList<MotionHelper> mDecoratorsHelpers;
    private boolean mDelayedApply;
    private DesignTool mDesignTool;
    private int mEndState;
    private int mFrames;
    private boolean mInLayout;
    private boolean mInRotation;
    private boolean mInteractionEnabled;
    private Matrix mInverseMatrix;
    private boolean mKeepAnimating;
    private KeyCache mKeyCache;
    private long mLastDrawTime;
    private float mLastFps;
    private int mLastHeightMeasureSpec;
    private int mLastWidthMeasureSpec;
    private float mListenerPosition;
    private int mListenerState;
    private boolean mNeedsFireTransitionCompleted;
    private Runnable mOnComplete;
    private ArrayList<MotionHelper> mOnHideHelpers;
    private ArrayList<MotionHelper> mOnShowHelpers;
    private int mPreRotateHeight;
    private int mPreRotateWidth;
    private int mPreviouseRotation;
    private View mRegionView;
    private int[] mScheduledTransitionTo;
    private StateCache mStateCache;
    private StopLogic mStopLogic;
    private boolean mTemporalInterpolator;
    private float mTransitionDuration;
    private boolean mTransitionInstantly;
    private long mTransitionLastTime;
    private TransitionListener mTransitionListener;
    private CopyOnWriteArrayList<TransitionListener> mTransitionListeners;

    /* renamed from: n, reason: collision with root package name */
    int f3046n;

    /* renamed from: o, reason: collision with root package name */
    HashMap f3047o;

    /* renamed from: p, reason: collision with root package name */
    float f3048p;

    /* renamed from: q, reason: collision with root package name */
    float f3049q;

    /* renamed from: r, reason: collision with root package name */
    float f3050r;

    /* renamed from: s, reason: collision with root package name */
    boolean f3051s;

    /* renamed from: t, reason: collision with root package name */
    boolean f3052t;

    /* renamed from: u, reason: collision with root package name */
    int f3053u;

    /* renamed from: v, reason: collision with root package name */
    DevModeDraw f3054v;

    /* renamed from: w, reason: collision with root package name */
    boolean f3055w;

    /* renamed from: x, reason: collision with root package name */
    int f3056x;

    /* renamed from: y, reason: collision with root package name */
    int f3057y;

    /* renamed from: z, reason: collision with root package name */
    int f3058z;

    /* renamed from: androidx.constraintlayout.motion.widget.MotionLayout$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f3063a;

        static {
            int[] iArr = new int[TransitionState.values().length];
            f3063a = iArr;
            try {
                iArr[TransitionState.UNDEFINED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f3063a[TransitionState.SETUP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f3063a[TransitionState.MOVING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f3063a[TransitionState.FINISHED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    class DecelerateInterpolator extends MotionInterpolator {

        /* renamed from: a, reason: collision with root package name */
        float f3064a = 0.0f;

        /* renamed from: b, reason: collision with root package name */
        float f3065b = 0.0f;

        /* renamed from: c, reason: collision with root package name */
        float f3066c;

        DecelerateInterpolator() {
        }

        public void config(float f2, float f3, float f4) {
            this.f3064a = f2;
            this.f3065b = f3;
            this.f3066c = f4;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionInterpolator, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float f3;
            float f4;
            float f5 = this.f3064a;
            if (f5 > 0.0f) {
                float f6 = this.f3066c;
                if (f5 / f6 < f2) {
                    f2 = f5 / f6;
                }
                MotionLayout.this.f3045m = f5 - (f6 * f2);
                f3 = (f5 * f2) - (((f6 * f2) * f2) / 2.0f);
                f4 = this.f3065b;
            } else {
                float f7 = this.f3066c;
                if ((-f5) / f7 < f2) {
                    f2 = (-f5) / f7;
                }
                MotionLayout.this.f3045m = (f7 * f2) + f5;
                f3 = (f5 * f2) + (((f7 * f2) * f2) / 2.0f);
                f4 = this.f3065b;
            }
            return f3 + f4;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionInterpolator
        public float getVelocity() {
            return MotionLayout.this.f3045m;
        }
    }

    private class DevModeDraw {
        private static final int DEBUG_PATH_TICKS_PER_MS = 16;

        /* renamed from: a, reason: collision with root package name */
        float[] f3068a;

        /* renamed from: b, reason: collision with root package name */
        int[] f3069b;

        /* renamed from: c, reason: collision with root package name */
        float[] f3070c;

        /* renamed from: d, reason: collision with root package name */
        Path f3071d;

        /* renamed from: e, reason: collision with root package name */
        Paint f3072e;

        /* renamed from: f, reason: collision with root package name */
        Paint f3073f;

        /* renamed from: g, reason: collision with root package name */
        Paint f3074g;

        /* renamed from: h, reason: collision with root package name */
        Paint f3075h;

        /* renamed from: i, reason: collision with root package name */
        Paint f3076i;
        private float[] mRectangle;

        /* renamed from: o, reason: collision with root package name */
        DashPathEffect f3082o;

        /* renamed from: p, reason: collision with root package name */
        int f3083p;

        /* renamed from: s, reason: collision with root package name */
        int f3086s;

        /* renamed from: j, reason: collision with root package name */
        final int f3077j = -21965;

        /* renamed from: k, reason: collision with root package name */
        final int f3078k = -2067046;

        /* renamed from: l, reason: collision with root package name */
        final int f3079l = -13391360;

        /* renamed from: m, reason: collision with root package name */
        final int f3080m = 1996488704;

        /* renamed from: n, reason: collision with root package name */
        final int f3081n = 10;

        /* renamed from: q, reason: collision with root package name */
        Rect f3084q = new Rect();

        /* renamed from: r, reason: collision with root package name */
        boolean f3085r = false;

        public DevModeDraw() {
            this.f3086s = 1;
            Paint paint = new Paint();
            this.f3072e = paint;
            paint.setAntiAlias(true);
            this.f3072e.setColor(-21965);
            this.f3072e.setStrokeWidth(2.0f);
            Paint paint2 = this.f3072e;
            Paint.Style style = Paint.Style.STROKE;
            paint2.setStyle(style);
            Paint paint3 = new Paint();
            this.f3073f = paint3;
            paint3.setAntiAlias(true);
            this.f3073f.setColor(-2067046);
            this.f3073f.setStrokeWidth(2.0f);
            this.f3073f.setStyle(style);
            Paint paint4 = new Paint();
            this.f3074g = paint4;
            paint4.setAntiAlias(true);
            this.f3074g.setColor(-13391360);
            this.f3074g.setStrokeWidth(2.0f);
            this.f3074g.setStyle(style);
            Paint paint5 = new Paint();
            this.f3075h = paint5;
            paint5.setAntiAlias(true);
            this.f3075h.setColor(-13391360);
            this.f3075h.setTextSize(MotionLayout.this.getContext().getResources().getDisplayMetrics().density * 12.0f);
            this.mRectangle = new float[8];
            Paint paint6 = new Paint();
            this.f3076i = paint6;
            paint6.setAntiAlias(true);
            DashPathEffect dashPathEffect = new DashPathEffect(new float[]{4.0f, 8.0f}, 0.0f);
            this.f3082o = dashPathEffect;
            this.f3074g.setPathEffect(dashPathEffect);
            this.f3070c = new float[100];
            this.f3069b = new int[50];
            if (this.f3085r) {
                this.f3072e.setStrokeWidth(8.0f);
                this.f3076i.setStrokeWidth(8.0f);
                this.f3073f.setStrokeWidth(8.0f);
                this.f3086s = 4;
            }
        }

        private void drawBasicPath(Canvas canvas) {
            canvas.drawLines(this.f3068a, this.f3072e);
        }

        private void drawPathAsConfigured(Canvas canvas) {
            boolean z2 = false;
            boolean z3 = false;
            for (int i2 = 0; i2 < this.f3083p; i2++) {
                int i3 = this.f3069b[i2];
                if (i3 == 1) {
                    z2 = true;
                }
                if (i3 == 0) {
                    z3 = true;
                }
            }
            if (z2) {
                drawPathRelative(canvas);
            }
            if (z3) {
                drawPathCartesian(canvas);
            }
        }

        private void drawPathCartesian(Canvas canvas) {
            float[] fArr = this.f3068a;
            float f2 = fArr[0];
            float f3 = fArr[1];
            float f4 = fArr[fArr.length - 2];
            float f5 = fArr[fArr.length - 1];
            canvas.drawLine(Math.min(f2, f4), Math.max(f3, f5), Math.max(f2, f4), Math.max(f3, f5), this.f3074g);
            canvas.drawLine(Math.min(f2, f4), Math.min(f3, f5), Math.min(f2, f4), Math.max(f3, f5), this.f3074g);
        }

        private void drawPathCartesianTicks(Canvas canvas, float f2, float f3) {
            float[] fArr = this.f3068a;
            float f4 = fArr[0];
            float f5 = fArr[1];
            float f6 = fArr[fArr.length - 2];
            float f7 = fArr[fArr.length - 1];
            float fMin = Math.min(f4, f6);
            float fMax = Math.max(f5, f7);
            float fMin2 = f2 - Math.min(f4, f6);
            float fMax2 = Math.max(f5, f7) - f3;
            String str = "" + (((int) (((fMin2 * 100.0f) / Math.abs(f6 - f4)) + 0.5d)) / 100.0f);
            a(str, this.f3075h);
            canvas.drawText(str, ((fMin2 / 2.0f) - (this.f3084q.width() / 2)) + fMin, f3 - 20.0f, this.f3075h);
            canvas.drawLine(f2, f3, Math.min(f4, f6), f3, this.f3074g);
            String str2 = "" + (((int) (((fMax2 * 100.0f) / Math.abs(f7 - f5)) + 0.5d)) / 100.0f);
            a(str2, this.f3075h);
            canvas.drawText(str2, f2 + 5.0f, fMax - ((fMax2 / 2.0f) - (this.f3084q.height() / 2)), this.f3075h);
            canvas.drawLine(f2, f3, f2, Math.max(f5, f7), this.f3074g);
        }

        private void drawPathRelative(Canvas canvas) {
            float[] fArr = this.f3068a;
            canvas.drawLine(fArr[0], fArr[1], fArr[fArr.length - 2], fArr[fArr.length - 1], this.f3074g);
        }

        private void drawPathRelativeTicks(Canvas canvas, float f2, float f3) {
            float[] fArr = this.f3068a;
            float f4 = fArr[0];
            float f5 = fArr[1];
            float f6 = fArr[fArr.length - 2];
            float f7 = fArr[fArr.length - 1];
            float fHypot = (float) Math.hypot(f4 - f6, f5 - f7);
            float f8 = f6 - f4;
            float f9 = f7 - f5;
            float f10 = (((f2 - f4) * f8) + ((f3 - f5) * f9)) / (fHypot * fHypot);
            float f11 = f4 + (f8 * f10);
            float f12 = f5 + (f10 * f9);
            Path path = new Path();
            path.moveTo(f2, f3);
            path.lineTo(f11, f12);
            float fHypot2 = (float) Math.hypot(f11 - f2, f12 - f3);
            String str = "" + (((int) ((fHypot2 * 100.0f) / fHypot)) / 100.0f);
            a(str, this.f3075h);
            canvas.drawTextOnPath(str, path, (fHypot2 / 2.0f) - (this.f3084q.width() / 2), -20.0f, this.f3075h);
            canvas.drawLine(f2, f3, f11, f12, this.f3074g);
        }

        private void drawPathScreenTicks(Canvas canvas, float f2, float f3, int i2, int i3) {
            String str = "" + (((int) ((((f2 - (i2 / 2)) * 100.0f) / (MotionLayout.this.getWidth() - i2)) + 0.5d)) / 100.0f);
            a(str, this.f3075h);
            canvas.drawText(str, ((f2 / 2.0f) - (this.f3084q.width() / 2)) + 0.0f, f3 - 20.0f, this.f3075h);
            canvas.drawLine(f2, f3, Math.min(0.0f, 1.0f), f3, this.f3074g);
            String str2 = "" + (((int) ((((f3 - (i3 / 2)) * 100.0f) / (MotionLayout.this.getHeight() - i3)) + 0.5d)) / 100.0f);
            a(str2, this.f3075h);
            canvas.drawText(str2, f2 + 5.0f, 0.0f - ((f3 / 2.0f) - (this.f3084q.height() / 2)), this.f3075h);
            canvas.drawLine(f2, f3, f2, Math.max(0.0f, 1.0f), this.f3074g);
        }

        private void drawRectangle(Canvas canvas, MotionController motionController) {
            this.f3071d.reset();
            for (int i2 = 0; i2 <= 50; i2++) {
                motionController.d(i2 / 50, this.mRectangle, 0);
                Path path = this.f3071d;
                float[] fArr = this.mRectangle;
                path.moveTo(fArr[0], fArr[1]);
                Path path2 = this.f3071d;
                float[] fArr2 = this.mRectangle;
                path2.lineTo(fArr2[2], fArr2[3]);
                Path path3 = this.f3071d;
                float[] fArr3 = this.mRectangle;
                path3.lineTo(fArr3[4], fArr3[5]);
                Path path4 = this.f3071d;
                float[] fArr4 = this.mRectangle;
                path4.lineTo(fArr4[6], fArr4[7]);
                this.f3071d.close();
            }
            this.f3072e.setColor(1140850688);
            canvas.translate(2.0f, 2.0f);
            canvas.drawPath(this.f3071d, this.f3072e);
            canvas.translate(-2.0f, -2.0f);
            this.f3072e.setColor(SupportMenu.CATEGORY_MASK);
            canvas.drawPath(this.f3071d, this.f3072e);
        }

        private void drawTicks(Canvas canvas, int i2, int i3, MotionController motionController) {
            int width;
            int height;
            float f2;
            float f3;
            View view = motionController.f3031b;
            if (view != null) {
                width = view.getWidth();
                height = motionController.f3031b.getHeight();
            } else {
                width = 0;
                height = 0;
            }
            for (int i4 = 1; i4 < i3 - 1; i4++) {
                if (i2 != 4 || this.f3069b[i4 - 1] != 0) {
                    float[] fArr = this.f3070c;
                    int i5 = i4 * 2;
                    float f4 = fArr[i5];
                    float f5 = fArr[i5 + 1];
                    this.f3071d.reset();
                    this.f3071d.moveTo(f4, f5 + 10.0f);
                    this.f3071d.lineTo(f4 + 10.0f, f5);
                    this.f3071d.lineTo(f4, f5 - 10.0f);
                    this.f3071d.lineTo(f4 - 10.0f, f5);
                    this.f3071d.close();
                    int i6 = i4 - 1;
                    motionController.i(i6);
                    if (i2 == 4) {
                        int i7 = this.f3069b[i6];
                        if (i7 == 1) {
                            drawPathRelativeTicks(canvas, f4 - 0.0f, f5 - 0.0f);
                        } else if (i7 == 0) {
                            drawPathCartesianTicks(canvas, f4 - 0.0f, f5 - 0.0f);
                        } else {
                            if (i7 == 2) {
                                f2 = f5;
                                f3 = f4;
                                drawPathScreenTicks(canvas, f4 - 0.0f, f5 - 0.0f, width, height);
                            }
                            canvas.drawPath(this.f3071d, this.f3076i);
                        }
                        f2 = f5;
                        f3 = f4;
                        canvas.drawPath(this.f3071d, this.f3076i);
                    } else {
                        f2 = f5;
                        f3 = f4;
                    }
                    if (i2 == 2) {
                        drawPathRelativeTicks(canvas, f3 - 0.0f, f2 - 0.0f);
                    }
                    if (i2 == 3) {
                        drawPathCartesianTicks(canvas, f3 - 0.0f, f2 - 0.0f);
                    }
                    if (i2 == 6) {
                        drawPathScreenTicks(canvas, f3 - 0.0f, f2 - 0.0f, width, height);
                    }
                    canvas.drawPath(this.f3071d, this.f3076i);
                }
            }
            float[] fArr2 = this.f3068a;
            if (fArr2.length > 1) {
                canvas.drawCircle(fArr2[0], fArr2[1], 8.0f, this.f3073f);
                float[] fArr3 = this.f3068a;
                canvas.drawCircle(fArr3[fArr3.length - 2], fArr3[fArr3.length - 1], 8.0f, this.f3073f);
            }
        }

        private void drawTranslation(Canvas canvas, float f2, float f3, float f4, float f5) {
            canvas.drawRect(f2, f3, f4, f5, this.f3074g);
            canvas.drawLine(f2, f3, f4, f5, this.f3074g);
        }

        void a(String str, Paint paint) {
            paint.getTextBounds(str, 0, str.length(), this.f3084q);
        }

        public void draw(Canvas canvas, HashMap<View, MotionController> map, int i2, int i3) {
            if (map == null || map.size() == 0) {
                return;
            }
            canvas.save();
            if (!MotionLayout.this.isInEditMode() && (i3 & 1) == 2) {
                String str = MotionLayout.this.getContext().getResources().getResourceName(MotionLayout.this.mEndState) + ":" + MotionLayout.this.getProgress();
                canvas.drawText(str, 10.0f, MotionLayout.this.getHeight() - 30, this.f3075h);
                canvas.drawText(str, 11.0f, MotionLayout.this.getHeight() - 29, this.f3072e);
            }
            for (MotionController motionController : map.values()) {
                int drawPath = motionController.getDrawPath();
                if (i3 > 0 && drawPath == 0) {
                    drawPath = 1;
                }
                if (drawPath != 0) {
                    this.f3083p = motionController.b(this.f3070c, this.f3069b);
                    if (drawPath >= 1) {
                        int i4 = i2 / 16;
                        float[] fArr = this.f3068a;
                        if (fArr == null || fArr.length != i4 * 2) {
                            this.f3068a = new float[i4 * 2];
                            this.f3071d = new Path();
                        }
                        int i5 = this.f3086s;
                        canvas.translate(i5, i5);
                        this.f3072e.setColor(1996488704);
                        this.f3076i.setColor(1996488704);
                        this.f3073f.setColor(1996488704);
                        this.f3074g.setColor(1996488704);
                        motionController.c(this.f3068a, i4);
                        drawAll(canvas, drawPath, this.f3083p, motionController);
                        this.f3072e.setColor(-21965);
                        this.f3073f.setColor(-2067046);
                        this.f3076i.setColor(-2067046);
                        this.f3074g.setColor(-13391360);
                        int i6 = this.f3086s;
                        canvas.translate(-i6, -i6);
                        drawAll(canvas, drawPath, this.f3083p, motionController);
                        if (drawPath == 5) {
                            drawRectangle(canvas, motionController);
                        }
                    }
                }
            }
            canvas.restore();
        }

        public void drawAll(Canvas canvas, int i2, int i3, MotionController motionController) {
            if (i2 == 4) {
                drawPathAsConfigured(canvas);
            }
            if (i2 == 2) {
                drawPathRelative(canvas);
            }
            if (i2 == 3) {
                drawPathCartesian(canvas);
            }
            drawBasicPath(canvas);
            drawTicks(canvas, i2, i3, motionController);
        }
    }

    class Model {

        /* renamed from: a, reason: collision with root package name */
        ConstraintWidgetContainer f3088a = new ConstraintWidgetContainer();

        /* renamed from: b, reason: collision with root package name */
        ConstraintWidgetContainer f3089b = new ConstraintWidgetContainer();

        /* renamed from: c, reason: collision with root package name */
        ConstraintSet f3090c = null;

        /* renamed from: d, reason: collision with root package name */
        ConstraintSet f3091d = null;

        /* renamed from: e, reason: collision with root package name */
        int f3092e;

        /* renamed from: f, reason: collision with root package name */
        int f3093f;

        Model() {
        }

        private void computeStartEndSize(int i2, int i3) {
            int optimizationLevel = MotionLayout.this.getOptimizationLevel();
            MotionLayout motionLayout = MotionLayout.this;
            if (motionLayout.f3046n == motionLayout.getStartState()) {
                MotionLayout motionLayout2 = MotionLayout.this;
                ConstraintWidgetContainer constraintWidgetContainer = this.f3089b;
                ConstraintSet constraintSet = this.f3091d;
                motionLayout2.h(constraintWidgetContainer, optimizationLevel, (constraintSet == null || constraintSet.mRotate == 0) ? i2 : i3, (constraintSet == null || constraintSet.mRotate == 0) ? i3 : i2);
                ConstraintSet constraintSet2 = this.f3090c;
                if (constraintSet2 != null) {
                    MotionLayout motionLayout3 = MotionLayout.this;
                    ConstraintWidgetContainer constraintWidgetContainer2 = this.f3088a;
                    int i4 = constraintSet2.mRotate;
                    int i5 = i4 == 0 ? i2 : i3;
                    if (i4 == 0) {
                        i2 = i3;
                    }
                    motionLayout3.h(constraintWidgetContainer2, optimizationLevel, i5, i2);
                    return;
                }
                return;
            }
            ConstraintSet constraintSet3 = this.f3090c;
            if (constraintSet3 != null) {
                MotionLayout motionLayout4 = MotionLayout.this;
                ConstraintWidgetContainer constraintWidgetContainer3 = this.f3088a;
                int i6 = constraintSet3.mRotate;
                motionLayout4.h(constraintWidgetContainer3, optimizationLevel, i6 == 0 ? i2 : i3, i6 == 0 ? i3 : i2);
            }
            MotionLayout motionLayout5 = MotionLayout.this;
            ConstraintWidgetContainer constraintWidgetContainer4 = this.f3089b;
            ConstraintSet constraintSet4 = this.f3091d;
            int i7 = (constraintSet4 == null || constraintSet4.mRotate == 0) ? i2 : i3;
            if (constraintSet4 == null || constraintSet4.mRotate == 0) {
                i2 = i3;
            }
            motionLayout5.h(constraintWidgetContainer4, optimizationLevel, i7, i2);
        }

        @SuppressLint({"LogConditional"})
        private void debugLayout(String str, ConstraintWidgetContainer constraintWidgetContainer) {
            String str2 = str + " " + Debug.getName((View) constraintWidgetContainer.getCompanionWidget());
            Log.v("MotionLayout", str2 + "  ========= " + constraintWidgetContainer);
            int size = constraintWidgetContainer.getChildren().size();
            for (int i2 = 0; i2 < size; i2++) {
                String str3 = str2 + "[" + i2 + "] ";
                ConstraintWidget constraintWidget = constraintWidgetContainer.getChildren().get(i2);
                StringBuilder sb = new StringBuilder();
                sb.append("");
                ConstraintAnchor constraintAnchor = constraintWidget.mTop.mTarget;
                String str4 = OpenAccountUIConstants.UNDER_LINE;
                sb.append(constraintAnchor != null ? ExifInterface.GPS_DIRECTION_TRUE : OpenAccountUIConstants.UNDER_LINE);
                String string = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(string);
                sb2.append(constraintWidget.mBottom.mTarget != null ? "B" : OpenAccountUIConstants.UNDER_LINE);
                String string2 = sb2.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(string2);
                sb3.append(constraintWidget.mLeft.mTarget != null ? "L" : OpenAccountUIConstants.UNDER_LINE);
                String string3 = sb3.toString();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(string3);
                if (constraintWidget.mRight.mTarget != null) {
                    str4 = "R";
                }
                sb4.append(str4);
                String string4 = sb4.toString();
                View view = (View) constraintWidget.getCompanionWidget();
                String name = Debug.getName(view);
                if (view instanceof TextView) {
                    name = name + "(" + ((Object) ((TextView) view).getText()) + ")";
                }
                Log.v("MotionLayout", str3 + "  " + name + " " + constraintWidget + " " + string4);
            }
            Log.v("MotionLayout", str2 + " done. ");
        }

        @SuppressLint({"LogConditional"})
        private void debugLayoutParam(String str, ConstraintLayout.LayoutParams layoutParams) {
            StringBuilder sb = new StringBuilder();
            sb.append(" ");
            sb.append(layoutParams.startToStart != -1 ? "SS" : "__");
            String string = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(string);
            sb2.append(layoutParams.startToEnd != -1 ? "|SE" : "|__");
            String string2 = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(string2);
            sb3.append(layoutParams.endToStart != -1 ? "|ES" : "|__");
            String string3 = sb3.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(string3);
            sb4.append(layoutParams.endToEnd != -1 ? "|EE" : "|__");
            String string4 = sb4.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(string4);
            sb5.append(layoutParams.leftToLeft != -1 ? "|LL" : "|__");
            String string5 = sb5.toString();
            StringBuilder sb6 = new StringBuilder();
            sb6.append(string5);
            sb6.append(layoutParams.leftToRight != -1 ? "|LR" : "|__");
            String string6 = sb6.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(string6);
            sb7.append(layoutParams.rightToLeft != -1 ? "|RL" : "|__");
            String string7 = sb7.toString();
            StringBuilder sb8 = new StringBuilder();
            sb8.append(string7);
            sb8.append(layoutParams.rightToRight != -1 ? "|RR" : "|__");
            String string8 = sb8.toString();
            StringBuilder sb9 = new StringBuilder();
            sb9.append(string8);
            sb9.append(layoutParams.topToTop != -1 ? "|TT" : "|__");
            String string9 = sb9.toString();
            StringBuilder sb10 = new StringBuilder();
            sb10.append(string9);
            sb10.append(layoutParams.topToBottom != -1 ? "|TB" : "|__");
            String string10 = sb10.toString();
            StringBuilder sb11 = new StringBuilder();
            sb11.append(string10);
            sb11.append(layoutParams.bottomToTop != -1 ? "|BT" : "|__");
            String string11 = sb11.toString();
            StringBuilder sb12 = new StringBuilder();
            sb12.append(string11);
            sb12.append(layoutParams.bottomToBottom != -1 ? "|BB" : "|__");
            Log.v("MotionLayout", str + sb12.toString());
        }

        @SuppressLint({"LogConditional"})
        private void debugWidget(String str, ConstraintWidget constraintWidget) {
            String string;
            String string2;
            String string3;
            StringBuilder sb = new StringBuilder();
            sb.append(" ");
            String string4 = "__";
            if (constraintWidget.mTop.mTarget != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(ExifInterface.GPS_DIRECTION_TRUE);
                sb2.append(constraintWidget.mTop.mTarget.mType == ConstraintAnchor.Type.TOP ? ExifInterface.GPS_DIRECTION_TRUE : "B");
                string = sb2.toString();
            } else {
                string = "__";
            }
            sb.append(string);
            String string5 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(string5);
            if (constraintWidget.mBottom.mTarget != null) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("B");
                sb4.append(constraintWidget.mBottom.mTarget.mType == ConstraintAnchor.Type.TOP ? ExifInterface.GPS_DIRECTION_TRUE : "B");
                string2 = sb4.toString();
            } else {
                string2 = "__";
            }
            sb3.append(string2);
            String string6 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(string6);
            if (constraintWidget.mLeft.mTarget != null) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append("L");
                sb6.append(constraintWidget.mLeft.mTarget.mType == ConstraintAnchor.Type.LEFT ? "L" : "R");
                string3 = sb6.toString();
            } else {
                string3 = "__";
            }
            sb5.append(string3);
            String string7 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(string7);
            if (constraintWidget.mRight.mTarget != null) {
                StringBuilder sb8 = new StringBuilder();
                sb8.append("R");
                sb8.append(constraintWidget.mRight.mTarget.mType == ConstraintAnchor.Type.LEFT ? "L" : "R");
                string4 = sb8.toString();
            }
            sb7.append(string4);
            Log.v("MotionLayout", str + sb7.toString() + " ---  " + constraintWidget);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void setupConstraintWidget(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet) {
            SparseArray<ConstraintWidget> sparseArray = new SparseArray<>();
            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
            sparseArray.clear();
            sparseArray.put(0, constraintWidgetContainer);
            sparseArray.put(MotionLayout.this.getId(), constraintWidgetContainer);
            if (constraintSet != null && constraintSet.mRotate != 0) {
                MotionLayout motionLayout = MotionLayout.this;
                motionLayout.h(this.f3089b, motionLayout.getOptimizationLevel(), View.MeasureSpec.makeMeasureSpec(MotionLayout.this.getHeight(), 1073741824), View.MeasureSpec.makeMeasureSpec(MotionLayout.this.getWidth(), 1073741824));
            }
            Iterator<ConstraintWidget> it = constraintWidgetContainer.getChildren().iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                next.setAnimated(true);
                sparseArray.put(((View) next.getCompanionWidget()).getId(), next);
            }
            Iterator<ConstraintWidget> it2 = constraintWidgetContainer.getChildren().iterator();
            while (it2.hasNext()) {
                ConstraintWidget next2 = it2.next();
                View view = (View) next2.getCompanionWidget();
                constraintSet.applyToLayoutParams(view.getId(), layoutParams);
                next2.setWidth(constraintSet.getWidth(view.getId()));
                next2.setHeight(constraintSet.getHeight(view.getId()));
                if (view instanceof ConstraintHelper) {
                    constraintSet.applyToHelper((ConstraintHelper) view, next2, layoutParams, sparseArray);
                    if (view instanceof Barrier) {
                        ((Barrier) view).validateParams();
                    }
                }
                layoutParams.resolveLayoutDirection(MotionLayout.this.getLayoutDirection());
                MotionLayout.this.c(false, view, next2, layoutParams, sparseArray);
                if (constraintSet.getVisibilityMode(view.getId()) == 1) {
                    next2.setVisibility(view.getVisibility());
                } else {
                    next2.setVisibility(constraintSet.getVisibility(view.getId()));
                }
            }
            Iterator<ConstraintWidget> it3 = constraintWidgetContainer.getChildren().iterator();
            while (it3.hasNext()) {
                ConstraintWidget next3 = it3.next();
                if (next3 instanceof VirtualLayout) {
                    ConstraintHelper constraintHelper = (ConstraintHelper) next3.getCompanionWidget();
                    Helper helper = (Helper) next3;
                    constraintHelper.updatePreLayout(constraintWidgetContainer, helper, sparseArray);
                    ((VirtualLayout) helper).captureWidgets();
                }
            }
        }

        void a(ConstraintWidgetContainer constraintWidgetContainer, ConstraintWidgetContainer constraintWidgetContainer2) {
            ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
            HashMap<ConstraintWidget, ConstraintWidget> map = new HashMap<>();
            map.put(constraintWidgetContainer, constraintWidgetContainer2);
            constraintWidgetContainer2.getChildren().clear();
            constraintWidgetContainer2.copy(constraintWidgetContainer, map);
            Iterator<ConstraintWidget> it = children.iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                ConstraintWidget barrier = next instanceof androidx.constraintlayout.core.widgets.Barrier ? new androidx.constraintlayout.core.widgets.Barrier() : next instanceof Guideline ? new Guideline() : next instanceof Flow ? new Flow() : next instanceof Placeholder ? new Placeholder() : next instanceof Helper ? new HelperWidget() : new ConstraintWidget();
                constraintWidgetContainer2.add(barrier);
                map.put(next, barrier);
            }
            Iterator<ConstraintWidget> it2 = children.iterator();
            while (it2.hasNext()) {
                ConstraintWidget next2 = it2.next();
                map.get(next2).copy(next2, map);
            }
        }

        ConstraintWidget b(ConstraintWidgetContainer constraintWidgetContainer, View view) {
            if (constraintWidgetContainer.getCompanionWidget() == view) {
                return constraintWidgetContainer;
            }
            ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
            int size = children.size();
            for (int i2 = 0; i2 < size; i2++) {
                ConstraintWidget constraintWidget = children.get(i2);
                if (constraintWidget.getCompanionWidget() == view) {
                    return constraintWidget;
                }
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x00e8  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x013c A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void build() {
            /*
                Method dump skipped, instructions count: 359
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.Model.build():void");
        }

        void c(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet, ConstraintSet constraintSet2) {
            this.f3090c = constraintSet;
            this.f3091d = constraintSet2;
            this.f3088a = new ConstraintWidgetContainer();
            this.f3089b = new ConstraintWidgetContainer();
            this.f3088a.setMeasurer(((ConstraintLayout) MotionLayout.this).f3227b.getMeasurer());
            this.f3089b.setMeasurer(((ConstraintLayout) MotionLayout.this).f3227b.getMeasurer());
            this.f3088a.removeAllChildren();
            this.f3089b.removeAllChildren();
            a(((ConstraintLayout) MotionLayout.this).f3227b, this.f3088a);
            a(((ConstraintLayout) MotionLayout.this).f3227b, this.f3089b);
            if (MotionLayout.this.f3049q > 0.5d) {
                if (constraintSet != null) {
                    setupConstraintWidget(this.f3088a, constraintSet);
                }
                setupConstraintWidget(this.f3089b, constraintSet2);
            } else {
                setupConstraintWidget(this.f3089b, constraintSet2);
                if (constraintSet != null) {
                    setupConstraintWidget(this.f3088a, constraintSet);
                }
            }
            this.f3088a.setRtl(MotionLayout.this.e());
            this.f3088a.updateHierarchy();
            this.f3089b.setRtl(MotionLayout.this.e());
            this.f3089b.updateHierarchy();
            ViewGroup.LayoutParams layoutParams = MotionLayout.this.getLayoutParams();
            if (layoutParams != null) {
                if (layoutParams.width == -2) {
                    ConstraintWidgetContainer constraintWidgetContainer2 = this.f3088a;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    constraintWidgetContainer2.setHorizontalDimensionBehaviour(dimensionBehaviour);
                    this.f3089b.setHorizontalDimensionBehaviour(dimensionBehaviour);
                }
                if (layoutParams.height == -2) {
                    ConstraintWidgetContainer constraintWidgetContainer3 = this.f3088a;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    constraintWidgetContainer3.setVerticalDimensionBehaviour(dimensionBehaviour2);
                    this.f3089b.setVerticalDimensionBehaviour(dimensionBehaviour2);
                }
            }
        }

        public boolean isNotConfiguredWith(int i2, int i3) {
            return (i2 == this.f3092e && i3 == this.f3093f) ? false : true;
        }

        public void measure(int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i2);
            int mode2 = View.MeasureSpec.getMode(i3);
            MotionLayout motionLayout = MotionLayout.this;
            motionLayout.M = mode;
            motionLayout.N = mode2;
            motionLayout.getOptimizationLevel();
            computeStartEndSize(i2, i3);
            if (!(MotionLayout.this.getParent() instanceof MotionLayout) || mode != 1073741824 || mode2 != 1073741824) {
                computeStartEndSize(i2, i3);
                MotionLayout.this.I = this.f3088a.getWidth();
                MotionLayout.this.J = this.f3088a.getHeight();
                MotionLayout.this.K = this.f3089b.getWidth();
                MotionLayout.this.L = this.f3089b.getHeight();
                MotionLayout motionLayout2 = MotionLayout.this;
                motionLayout2.H = (motionLayout2.I == motionLayout2.K && motionLayout2.J == motionLayout2.L) ? false : true;
            }
            MotionLayout motionLayout3 = MotionLayout.this;
            int i4 = motionLayout3.I;
            int i5 = motionLayout3.J;
            int i6 = motionLayout3.M;
            if (i6 == Integer.MIN_VALUE || i6 == 0) {
                i4 = (int) (i4 + (motionLayout3.O * (motionLayout3.K - i4)));
            }
            int i7 = i4;
            int i8 = motionLayout3.N;
            if (i8 == Integer.MIN_VALUE || i8 == 0) {
                i5 = (int) (i5 + (motionLayout3.O * (motionLayout3.L - i5)));
            }
            MotionLayout.this.g(i2, i3, i7, i5, this.f3088a.isWidthMeasuredTooSmall() || this.f3089b.isWidthMeasuredTooSmall(), this.f3088a.isHeightMeasuredTooSmall() || this.f3089b.isHeightMeasuredTooSmall());
        }

        public void reEvaluateState() {
            measure(MotionLayout.this.mLastWidthMeasureSpec, MotionLayout.this.mLastHeightMeasureSpec);
            MotionLayout.this.setupMotionViews();
        }

        public void setMeasuredId(int i2, int i3) {
            this.f3092e = i2;
            this.f3093f = i3;
        }
    }

    protected interface MotionTracker {
        void addMovement(MotionEvent motionEvent);

        void clear();

        void computeCurrentVelocity(int i2);

        void computeCurrentVelocity(int i2, float f2);

        float getXVelocity();

        float getXVelocity(int i2);

        float getYVelocity();

        float getYVelocity(int i2);

        void recycle();
    }

    class StateCache {

        /* renamed from: a, reason: collision with root package name */
        float f3097a = Float.NaN;

        /* renamed from: b, reason: collision with root package name */
        float f3098b = Float.NaN;

        /* renamed from: c, reason: collision with root package name */
        int f3099c = -1;

        /* renamed from: d, reason: collision with root package name */
        int f3100d = -1;

        /* renamed from: e, reason: collision with root package name */
        final String f3101e = "motion.progress";

        /* renamed from: f, reason: collision with root package name */
        final String f3102f = "motion.velocity";

        /* renamed from: g, reason: collision with root package name */
        final String f3103g = "motion.StartState";

        /* renamed from: h, reason: collision with root package name */
        final String f3104h = "motion.EndState";

        StateCache() {
        }

        void a() {
            int i2 = this.f3099c;
            if (i2 != -1 || this.f3100d != -1) {
                if (i2 == -1) {
                    MotionLayout.this.transitionToState(this.f3100d);
                } else {
                    int i3 = this.f3100d;
                    if (i3 == -1) {
                        MotionLayout.this.setState(i2, -1, -1);
                    } else {
                        MotionLayout.this.setTransition(i2, i3);
                    }
                }
                MotionLayout.this.setState(TransitionState.SETUP);
            }
            if (Float.isNaN(this.f3098b)) {
                if (Float.isNaN(this.f3097a)) {
                    return;
                }
                MotionLayout.this.setProgress(this.f3097a);
            } else {
                MotionLayout.this.setProgress(this.f3097a, this.f3098b);
                this.f3097a = Float.NaN;
                this.f3098b = Float.NaN;
                this.f3099c = -1;
                this.f3100d = -1;
            }
        }

        public Bundle getTransitionState() {
            Bundle bundle = new Bundle();
            bundle.putFloat("motion.progress", this.f3097a);
            bundle.putFloat("motion.velocity", this.f3098b);
            bundle.putInt("motion.StartState", this.f3099c);
            bundle.putInt("motion.EndState", this.f3100d);
            return bundle;
        }

        public void recordState() {
            this.f3100d = MotionLayout.this.mEndState;
            this.f3099c = MotionLayout.this.mBeginState;
            this.f3098b = MotionLayout.this.getVelocity();
            this.f3097a = MotionLayout.this.getProgress();
        }

        public void setEndState(int i2) {
            this.f3100d = i2;
        }

        public void setProgress(float f2) {
            this.f3097a = f2;
        }

        public void setStartState(int i2) {
            this.f3099c = i2;
        }

        public void setTransitionState(Bundle bundle) {
            this.f3097a = bundle.getFloat("motion.progress");
            this.f3098b = bundle.getFloat("motion.velocity");
            this.f3099c = bundle.getInt("motion.StartState");
            this.f3100d = bundle.getInt("motion.EndState");
        }

        public void setVelocity(float f2) {
            this.f3098b = f2;
        }
    }

    public interface TransitionListener {
        void onTransitionChange(MotionLayout motionLayout, int i2, int i3, float f2);

        void onTransitionCompleted(MotionLayout motionLayout, int i2);

        void onTransitionStarted(MotionLayout motionLayout, int i2, int i3);

        void onTransitionTrigger(MotionLayout motionLayout, int i2, boolean z2, float f2);
    }

    enum TransitionState {
        UNDEFINED,
        SETUP,
        MOVING,
        FINISHED
    }

    public MotionLayout(@NonNull Context context) {
        super(context);
        this.f3044l = null;
        this.f3045m = 0.0f;
        this.mBeginState = -1;
        this.f3046n = -1;
        this.mEndState = -1;
        this.mLastWidthMeasureSpec = 0;
        this.mLastHeightMeasureSpec = 0;
        this.mInteractionEnabled = true;
        this.f3047o = new HashMap();
        this.mAnimationStartTime = 0L;
        this.mTransitionDuration = 1.0f;
        this.f3048p = 0.0f;
        this.f3049q = 0.0f;
        this.f3050r = 0.0f;
        this.f3051s = false;
        this.f3052t = false;
        this.f3053u = 0;
        this.mTemporalInterpolator = false;
        this.mStopLogic = new StopLogic();
        this.mDecelerateLogic = new DecelerateInterpolator();
        this.f3055w = true;
        this.B = false;
        this.mKeepAnimating = false;
        this.mOnShowHelpers = null;
        this.mOnHideHelpers = null;
        this.mDecoratorsHelpers = null;
        this.mTransitionListeners = null;
        this.mFrames = 0;
        this.mLastDrawTime = -1L;
        this.mLastFps = 0.0f;
        this.mListenerState = 0;
        this.mListenerPosition = 0.0f;
        this.G = false;
        this.H = false;
        this.mKeyCache = new KeyCache();
        this.mInLayout = false;
        this.mOnComplete = null;
        this.mScheduledTransitionTo = null;
        this.P = 0;
        this.mInRotation = false;
        this.Q = 0;
        this.R = new HashMap();
        this.S = new Rect();
        this.mDelayedApply = false;
        this.T = TransitionState.UNDEFINED;
        this.U = new Model();
        this.mNeedsFireTransitionCompleted = false;
        this.mBoundsCheck = new RectF();
        this.mRegionView = null;
        this.mInverseMatrix = null;
        this.V = new ArrayList();
        init(null);
    }

    private boolean callTransformedTouchEvent(View view, MotionEvent motionEvent, float f2, float f3) {
        Matrix matrix = view.getMatrix();
        if (matrix.isIdentity()) {
            motionEvent.offsetLocation(f2, f3);
            boolean zOnTouchEvent = view.onTouchEvent(motionEvent);
            motionEvent.offsetLocation(-f2, -f3);
            return zOnTouchEvent;
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.offsetLocation(f2, f3);
        if (this.mInverseMatrix == null) {
            this.mInverseMatrix = new Matrix();
        }
        matrix.invert(this.mInverseMatrix);
        motionEventObtain.transform(this.mInverseMatrix);
        boolean zOnTouchEvent2 = view.onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
        return zOnTouchEvent2;
    }

    private void checkStructure() {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            Log.e("MotionLayout", "CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
            return;
        }
        int iU = motionScene.u();
        MotionScene motionScene2 = this.f3042j;
        checkStructure(iU, motionScene2.h(motionScene2.u()));
        SparseIntArray sparseIntArray = new SparseIntArray();
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        Iterator<MotionScene.Transition> it = this.f3042j.getDefinedTransitions().iterator();
        while (it.hasNext()) {
            MotionScene.Transition next = it.next();
            if (next == this.f3042j.f3127b) {
                Log.v("MotionLayout", "CHECK: CURRENT");
            }
            checkStructure(next);
            int startConstraintSetId = next.getStartConstraintSetId();
            int endConstraintSetId = next.getEndConstraintSetId();
            String name = Debug.getName(getContext(), startConstraintSetId);
            String name2 = Debug.getName(getContext(), endConstraintSetId);
            if (sparseIntArray.get(startConstraintSetId) == endConstraintSetId) {
                Log.e("MotionLayout", "CHECK: two transitions with the same start and end " + name + "->" + name2);
            }
            if (sparseIntArray2.get(endConstraintSetId) == startConstraintSetId) {
                Log.e("MotionLayout", "CHECK: you can't have reverse transitions" + name + "->" + name2);
            }
            sparseIntArray.put(startConstraintSetId, endConstraintSetId);
            sparseIntArray2.put(endConstraintSetId, startConstraintSetId);
            if (this.f3042j.h(startConstraintSetId) == null) {
                Log.e("MotionLayout", " no such constraintSetStart " + name);
            }
            if (this.f3042j.h(endConstraintSetId) == null) {
                Log.e("MotionLayout", " no such constraintSetEnd " + name);
            }
        }
    }

    private void computeCurrentPositions() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            MotionController motionController = (MotionController) this.f3047o.get(childAt);
            if (motionController != null) {
                motionController.s(childAt);
            }
        }
    }

    @SuppressLint({"LogConditional"})
    private void debugPos() {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            Log.v("MotionLayout", " " + Debug.getLocation() + " " + Debug.getName(this) + " " + Debug.getName(getContext(), this.f3046n) + " " + Debug.getName(childAt) + childAt.getLeft() + " " + childAt.getTop());
        }
    }

    private void evaluateLayout() {
        boolean z2;
        float fSignum = Math.signum(this.f3050r - this.f3049q);
        long nanoTime = getNanoTime();
        Interpolator interpolator = this.f3043k;
        float interpolation = this.f3049q + (!(interpolator instanceof StopLogic) ? (((nanoTime - this.mTransitionLastTime) * fSignum) * 1.0E-9f) / this.mTransitionDuration : 0.0f);
        if (this.mTransitionInstantly) {
            interpolation = this.f3050r;
        }
        if ((fSignum <= 0.0f || interpolation < this.f3050r) && (fSignum > 0.0f || interpolation > this.f3050r)) {
            z2 = false;
        } else {
            interpolation = this.f3050r;
            z2 = true;
        }
        if (interpolator != null && !z2) {
            interpolation = this.mTemporalInterpolator ? interpolator.getInterpolation((nanoTime - this.mAnimationStartTime) * 1.0E-9f) : interpolator.getInterpolation(interpolation);
        }
        if ((fSignum > 0.0f && interpolation >= this.f3050r) || (fSignum <= 0.0f && interpolation <= this.f3050r)) {
            interpolation = this.f3050r;
        }
        this.O = interpolation;
        int childCount = getChildCount();
        long nanoTime2 = getNanoTime();
        Interpolator interpolator2 = this.f3044l;
        if (interpolator2 != null) {
            interpolation = interpolator2.getInterpolation(interpolation);
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            MotionController motionController = (MotionController) this.f3047o.get(childAt);
            if (motionController != null) {
                motionController.n(childAt, interpolation, nanoTime2, this.mKeyCache);
            }
        }
        if (this.H) {
            requestLayout();
        }
    }

    private void fireTransitionChange() {
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
        if ((this.mTransitionListener == null && ((copyOnWriteArrayList = this.mTransitionListeners) == null || copyOnWriteArrayList.isEmpty())) || this.mListenerPosition == this.f3048p) {
            return;
        }
        if (this.mListenerState != -1) {
            TransitionListener transitionListener = this.mTransitionListener;
            if (transitionListener != null) {
                transitionListener.onTransitionStarted(this, this.mBeginState, this.mEndState);
            }
            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList2 = this.mTransitionListeners;
            if (copyOnWriteArrayList2 != null) {
                Iterator<TransitionListener> it = copyOnWriteArrayList2.iterator();
                while (it.hasNext()) {
                    it.next().onTransitionStarted(this, this.mBeginState, this.mEndState);
                }
            }
            this.G = true;
        }
        this.mListenerState = -1;
        float f2 = this.f3048p;
        this.mListenerPosition = f2;
        TransitionListener transitionListener2 = this.mTransitionListener;
        if (transitionListener2 != null) {
            transitionListener2.onTransitionChange(this, this.mBeginState, this.mEndState, f2);
        }
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList3 = this.mTransitionListeners;
        if (copyOnWriteArrayList3 != null) {
            Iterator<TransitionListener> it2 = copyOnWriteArrayList3.iterator();
            while (it2.hasNext()) {
                it2.next().onTransitionChange(this, this.mBeginState, this.mEndState, this.f3048p);
            }
        }
        this.G = true;
    }

    private void fireTransitionStarted(MotionLayout motionLayout, int i2, int i3) {
        TransitionListener transitionListener = this.mTransitionListener;
        if (transitionListener != null) {
            transitionListener.onTransitionStarted(this, i2, i3);
        }
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList = this.mTransitionListeners;
        if (copyOnWriteArrayList != null) {
            Iterator<TransitionListener> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                it.next().onTransitionStarted(motionLayout, i2, i3);
            }
        }
    }

    private boolean handlesTouchEvent(float f2, float f3, View view, MotionEvent motionEvent) {
        boolean z2;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                if (handlesTouchEvent((r3.getLeft() + f2) - view.getScrollX(), (r3.getTop() + f3) - view.getScrollY(), viewGroup.getChildAt(childCount), motionEvent)) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        } else {
            z2 = false;
        }
        if (!z2) {
            this.mBoundsCheck.set(f2, f3, (view.getRight() + f2) - view.getLeft(), (view.getBottom() + f3) - view.getTop());
            if ((motionEvent.getAction() != 0 || this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY())) && callTransformedTouchEvent(view, motionEvent, -f2, -f3)) {
                return true;
            }
        }
        return z2;
    }

    private void init(AttributeSet attributeSet) {
        MotionScene motionScene;
        IS_IN_EDIT_MODE = isInEditMode();
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.MotionLayout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            boolean z2 = true;
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.MotionLayout_layoutDescription) {
                    this.f3042j = new MotionScene(getContext(), this, typedArrayObtainStyledAttributes.getResourceId(index, -1));
                } else if (index == R.styleable.MotionLayout_currentState) {
                    this.f3046n = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                } else if (index == R.styleable.MotionLayout_motionProgress) {
                    this.f3050r = typedArrayObtainStyledAttributes.getFloat(index, 0.0f);
                    this.f3051s = true;
                } else if (index == R.styleable.MotionLayout_applyMotionScene) {
                    z2 = typedArrayObtainStyledAttributes.getBoolean(index, z2);
                } else if (index == R.styleable.MotionLayout_showPaths) {
                    if (this.f3053u == 0) {
                        this.f3053u = typedArrayObtainStyledAttributes.getBoolean(index, false) ? 2 : 0;
                    }
                } else if (index == R.styleable.MotionLayout_motionDebug) {
                    this.f3053u = typedArrayObtainStyledAttributes.getInt(index, 0);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
            if (this.f3042j == null) {
                Log.e("MotionLayout", "WARNING NO app:layoutDescription tag");
            }
            if (!z2) {
                this.f3042j = null;
            }
        }
        if (this.f3053u != 0) {
            checkStructure();
        }
        if (this.f3046n != -1 || (motionScene = this.f3042j) == null) {
            return;
        }
        this.f3046n = motionScene.u();
        this.mBeginState = this.f3042j.u();
        this.mEndState = this.f3042j.j();
    }

    private void processTransitionCompleted() {
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
        if (this.mTransitionListener == null && ((copyOnWriteArrayList = this.mTransitionListeners) == null || copyOnWriteArrayList.isEmpty())) {
            return;
        }
        this.G = false;
        Iterator it = this.V.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            TransitionListener transitionListener = this.mTransitionListener;
            if (transitionListener != null) {
                transitionListener.onTransitionCompleted(this, num.intValue());
            }
            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList2 = this.mTransitionListeners;
            if (copyOnWriteArrayList2 != null) {
                Iterator<TransitionListener> it2 = copyOnWriteArrayList2.iterator();
                while (it2.hasNext()) {
                    it2.next().onTransitionCompleted(this, num.intValue());
                }
            }
        }
        this.V.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupMotionViews() {
        int childCount = getChildCount();
        this.U.build();
        this.f3051s = true;
        SparseArray sparseArray = new SparseArray();
        int i2 = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            sparseArray.put(childAt.getId(), (MotionController) this.f3047o.get(childAt));
        }
        int width = getWidth();
        int height = getHeight();
        int iGatPathMotionArc = this.f3042j.gatPathMotionArc();
        if (iGatPathMotionArc != -1) {
            for (int i4 = 0; i4 < childCount; i4++) {
                MotionController motionController = (MotionController) this.f3047o.get(getChildAt(i4));
                if (motionController != null) {
                    motionController.setPathMotionArc(iGatPathMotionArc);
                }
            }
        }
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        int[] iArr = new int[this.f3047o.size()];
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            MotionController motionController2 = (MotionController) this.f3047o.get(getChildAt(i6));
            if (motionController2.getAnimateRelativeTo() != -1) {
                sparseBooleanArray.put(motionController2.getAnimateRelativeTo(), true);
                iArr[i5] = motionController2.getAnimateRelativeTo();
                i5++;
            }
        }
        if (this.mDecoratorsHelpers != null) {
            for (int i7 = 0; i7 < i5; i7++) {
                MotionController motionController3 = (MotionController) this.f3047o.get(findViewById(iArr[i7]));
                if (motionController3 != null) {
                    this.f3042j.getKeyFrames(motionController3);
                }
            }
            Iterator<MotionHelper> it = this.mDecoratorsHelpers.iterator();
            while (it.hasNext()) {
                it.next().onPreSetup(this, this.f3047o);
            }
            for (int i8 = 0; i8 < i5; i8++) {
                MotionController motionController4 = (MotionController) this.f3047o.get(findViewById(iArr[i8]));
                if (motionController4 != null) {
                    motionController4.setup(width, height, this.mTransitionDuration, getNanoTime());
                }
            }
        } else {
            for (int i9 = 0; i9 < i5; i9++) {
                MotionController motionController5 = (MotionController) this.f3047o.get(findViewById(iArr[i9]));
                if (motionController5 != null) {
                    this.f3042j.getKeyFrames(motionController5);
                    motionController5.setup(width, height, this.mTransitionDuration, getNanoTime());
                }
            }
        }
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt2 = getChildAt(i10);
            MotionController motionController6 = (MotionController) this.f3047o.get(childAt2);
            if (!sparseBooleanArray.get(childAt2.getId()) && motionController6 != null) {
                this.f3042j.getKeyFrames(motionController6);
                motionController6.setup(width, height, this.mTransitionDuration, getNanoTime());
            }
        }
        float staggered = this.f3042j.getStaggered();
        if (staggered != 0.0f) {
            boolean z2 = ((double) staggered) < 0.0d;
            float fAbs = Math.abs(staggered);
            float fMax = -3.4028235E38f;
            float fMin = Float.MAX_VALUE;
            float fMax2 = -3.4028235E38f;
            float fMin2 = Float.MAX_VALUE;
            for (int i11 = 0; i11 < childCount; i11++) {
                MotionController motionController7 = (MotionController) this.f3047o.get(getChildAt(i11));
                if (!Float.isNaN(motionController7.f3035f)) {
                    for (int i12 = 0; i12 < childCount; i12++) {
                        MotionController motionController8 = (MotionController) this.f3047o.get(getChildAt(i12));
                        if (!Float.isNaN(motionController8.f3035f)) {
                            fMin = Math.min(fMin, motionController8.f3035f);
                            fMax = Math.max(fMax, motionController8.f3035f);
                        }
                    }
                    while (i2 < childCount) {
                        MotionController motionController9 = (MotionController) this.f3047o.get(getChildAt(i2));
                        if (!Float.isNaN(motionController9.f3035f)) {
                            motionController9.f3037h = 1.0f / (1.0f - fAbs);
                            if (z2) {
                                motionController9.f3036g = fAbs - (((fMax - motionController9.f3035f) / (fMax - fMin)) * fAbs);
                            } else {
                                motionController9.f3036g = fAbs - (((motionController9.f3035f - fMin) * fAbs) / (fMax - fMin));
                            }
                        }
                        i2++;
                    }
                    return;
                }
                float finalX = motionController7.getFinalX();
                float finalY = motionController7.getFinalY();
                float f2 = z2 ? finalY - finalX : finalY + finalX;
                fMin2 = Math.min(fMin2, f2);
                fMax2 = Math.max(fMax2, f2);
            }
            while (i2 < childCount) {
                MotionController motionController10 = (MotionController) this.f3047o.get(getChildAt(i2));
                float finalX2 = motionController10.getFinalX();
                float finalY2 = motionController10.getFinalY();
                float f3 = z2 ? finalY2 - finalX2 : finalY2 + finalX2;
                motionController10.f3037h = 1.0f / (1.0f - fAbs);
                motionController10.f3036g = fAbs - (((f3 - fMin2) * fAbs) / (fMax2 - fMin2));
                i2++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect toRect(ConstraintWidget constraintWidget) {
        this.S.top = constraintWidget.getY();
        this.S.left = constraintWidget.getX();
        Rect rect = this.S;
        int width = constraintWidget.getWidth();
        Rect rect2 = this.S;
        rect.right = width + rect2.left;
        int height = constraintWidget.getHeight();
        Rect rect3 = this.S;
        rect2.bottom = height + rect3.top;
        return rect3;
    }

    private static boolean willJump(float f2, float f3, float f4) {
        if (f2 > 0.0f) {
            float f5 = f2 / f4;
            return f3 + ((f2 * f5) - (((f4 * f5) * f5) / 2.0f)) > 1.0f;
        }
        float f6 = (-f2) / f4;
        return f3 + ((f2 * f6) + (((f4 * f6) * f6) / 2.0f)) < 0.0f;
    }

    void H(float f2) {
        if (this.f3042j == null) {
            return;
        }
        float f3 = this.f3049q;
        float f4 = this.f3048p;
        if (f3 != f4 && this.mTransitionInstantly) {
            this.f3049q = f4;
        }
        float f5 = this.f3049q;
        if (f5 == f2) {
            return;
        }
        this.mTemporalInterpolator = false;
        this.f3050r = f2;
        this.mTransitionDuration = r0.getDuration() / 1000.0f;
        setProgress(this.f3050r);
        this.f3043k = null;
        this.f3044l = this.f3042j.getInterpolator();
        this.mTransitionInstantly = false;
        this.mAnimationStartTime = getNanoTime();
        this.f3051s = true;
        this.f3048p = f5;
        this.f3049q = f5;
        invalidate();
    }

    void I(boolean z2) {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            return;
        }
        motionScene.disableAutoTransition(z2);
    }

    void J(boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            MotionController motionController = (MotionController) this.f3047o.get(getChildAt(i2));
            if (motionController != null) {
                motionController.f(z2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00e2 A[PHI: r3
      0x00e2: PHI (r3v50 float) = (r3v49 float), (r3v51 float), (r3v51 float) binds: [B:47:0x00ab, B:58:0x00d6, B:60:0x00da] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0170  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void K(boolean r23) {
        /*
            Method dump skipped, instructions count: 630
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.K(boolean):void");
    }

    protected void L() {
        int iIntValue;
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
        if ((this.mTransitionListener != null || ((copyOnWriteArrayList = this.mTransitionListeners) != null && !copyOnWriteArrayList.isEmpty())) && this.mListenerState == -1) {
            this.mListenerState = this.f3046n;
            if (this.V.isEmpty()) {
                iIntValue = -1;
            } else {
                ArrayList arrayList = this.V;
                iIntValue = ((Integer) arrayList.get(arrayList.size() - 1)).intValue();
            }
            int i2 = this.f3046n;
            if (iIntValue != i2 && i2 != -1) {
                this.V.add(Integer.valueOf(i2));
            }
        }
        processTransitionCompleted();
        Runnable runnable = this.mOnComplete;
        if (runnable != null) {
            runnable.run();
        }
        int[] iArr = this.mScheduledTransitionTo;
        if (iArr == null || this.P <= 0) {
            return;
        }
        transitionToState(iArr[0]);
        int[] iArr2 = this.mScheduledTransitionTo;
        System.arraycopy(iArr2, 1, iArr2, 0, iArr2.length - 1);
        this.P--;
    }

    void M(int i2, float f2, float f3, float f4, float[] fArr) throws Resources.NotFoundException {
        String resourceName;
        HashMap map = this.f3047o;
        View viewById = getViewById(i2);
        MotionController motionController = (MotionController) map.get(viewById);
        if (motionController != null) {
            motionController.h(f2, f3, f4, fArr);
            float y2 = viewById.getY();
            this.lastPos = f2;
            this.lastY = y2;
            return;
        }
        if (viewById == null) {
            resourceName = "" + i2;
        } else {
            resourceName = viewById.getContext().getResources().getResourceName(i2);
        }
        Log.w("MotionLayout", "WARNING could not find view id " + resourceName);
    }

    String N(int i2) {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            return null;
        }
        return motionScene.lookUpConstraintName(i2);
    }

    MotionController O(int i2) {
        return (MotionController) this.f3047o.get(findViewById(i2));
    }

    int P(String str) {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            return 0;
        }
        return motionScene.lookUpConstraintId(str);
    }

    protected MotionTracker Q() {
        return MyTracker.obtain();
    }

    void R() {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            return;
        }
        if (motionScene.f(this, this.f3046n)) {
            requestLayout();
            return;
        }
        int i2 = this.f3046n;
        if (i2 != -1) {
            this.f3042j.addOnClickListeners(this, i2);
        }
        if (this.f3042j.C()) {
            this.f3042j.B();
        }
    }

    public void addTransitionListener(TransitionListener transitionListener) {
        if (this.mTransitionListeners == null) {
            this.mTransitionListeners = new CopyOnWriteArrayList<>();
        }
        this.mTransitionListeners.add(transitionListener);
    }

    public boolean applyViewTransition(int i2, MotionController motionController) {
        MotionScene motionScene = this.f3042j;
        if (motionScene != null) {
            return motionScene.applyViewTransition(i2, motionController);
        }
        return false;
    }

    public ConstraintSet cloneConstraintSet(int i2) {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            return null;
        }
        ConstraintSet constraintSetH = motionScene.h(i2);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintSetH);
        return constraintSet;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        ViewTransitionController viewTransitionController;
        ArrayList<MotionHelper> arrayList = this.mDecoratorsHelpers;
        if (arrayList != null) {
            Iterator<MotionHelper> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onPreDraw(canvas);
            }
        }
        K(false);
        MotionScene motionScene = this.f3042j;
        if (motionScene != null && (viewTransitionController = motionScene.f3128c) != null) {
            viewTransitionController.c();
        }
        super.dispatchDraw(canvas);
        if (this.f3042j == null) {
            return;
        }
        if ((this.f3053u & 1) == 1 && !isInEditMode()) {
            this.mFrames++;
            long nanoTime = getNanoTime();
            long j2 = this.mLastDrawTime;
            if (j2 != -1) {
                if (nanoTime - j2 > 200000000) {
                    this.mLastFps = ((int) ((this.mFrames / (r5 * 1.0E-9f)) * 100.0f)) / 100.0f;
                    this.mFrames = 0;
                    this.mLastDrawTime = nanoTime;
                }
            } else {
                this.mLastDrawTime = nanoTime;
            }
            Paint paint = new Paint();
            paint.setTextSize(42.0f);
            String str = this.mLastFps + " fps " + Debug.getState(this, this.mBeginState) + " -> ";
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(Debug.getState(this, this.mEndState));
            sb.append(" (progress: ");
            sb.append(((int) (getProgress() * 1000.0f)) / 10.0f);
            sb.append(" ) state=");
            int i2 = this.f3046n;
            sb.append(i2 == -1 ? "undefined" : Debug.getState(this, i2));
            String string = sb.toString();
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            canvas.drawText(string, 11.0f, getHeight() - 29, paint);
            paint.setColor(-7864184);
            canvas.drawText(string, 10.0f, getHeight() - 30, paint);
        }
        if (this.f3053u > 1) {
            if (this.f3054v == null) {
                this.f3054v = new DevModeDraw();
            }
            this.f3054v.draw(canvas, this.f3047o, this.f3042j.getDuration(), this.f3053u);
        }
        ArrayList<MotionHelper> arrayList2 = this.mDecoratorsHelpers;
        if (arrayList2 != null) {
            Iterator<MotionHelper> it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                it2.next().onPostDraw(canvas);
            }
        }
    }

    public void enableTransition(int i2, boolean z2) {
        MotionScene.Transition transition = getTransition(i2);
        if (z2) {
            transition.setEnabled(true);
            return;
        }
        MotionScene motionScene = this.f3042j;
        if (transition == motionScene.f3127b) {
            Iterator<MotionScene.Transition> it = motionScene.getTransitionsWithState(this.f3046n).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MotionScene.Transition next = it.next();
                if (next.isEnabled()) {
                    this.f3042j.f3127b = next;
                    break;
                }
            }
        }
        transition.setEnabled(false);
    }

    public void enableViewTransition(int i2, boolean z2) {
        MotionScene motionScene = this.f3042j;
        if (motionScene != null) {
            motionScene.enableViewTransition(i2, z2);
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout
    protected void f(int i2) {
        this.f3229d = null;
    }

    public void fireTrigger(int i2, boolean z2, float f2) {
        TransitionListener transitionListener = this.mTransitionListener;
        if (transitionListener != null) {
            transitionListener.onTransitionTrigger(this, i2, z2, f2);
        }
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList = this.mTransitionListeners;
        if (copyOnWriteArrayList != null) {
            Iterator<TransitionListener> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                it.next().onTransitionTrigger(this, i2, z2, f2);
            }
        }
    }

    public ConstraintSet getConstraintSet(int i2) {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            return null;
        }
        return motionScene.h(i2);
    }

    public int[] getConstraintSetIds() {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            return null;
        }
        return motionScene.getConstraintSetIds();
    }

    public int getCurrentState() {
        return this.f3046n;
    }

    public void getDebugMode(boolean z2) {
        this.f3053u = z2 ? 2 : 1;
        invalidate();
    }

    public ArrayList<MotionScene.Transition> getDefinedTransitions() {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            return null;
        }
        return motionScene.getDefinedTransitions();
    }

    public DesignTool getDesignTool() {
        if (this.mDesignTool == null) {
            this.mDesignTool = new DesignTool(this);
        }
        return this.mDesignTool;
    }

    public int getEndState() {
        return this.mEndState;
    }

    protected long getNanoTime() {
        return System.nanoTime();
    }

    public float getProgress() {
        return this.f3049q;
    }

    public MotionScene getScene() {
        return this.f3042j;
    }

    public int getStartState() {
        return this.mBeginState;
    }

    public float getTargetPosition() {
        return this.f3050r;
    }

    public MotionScene.Transition getTransition(int i2) {
        return this.f3042j.getTransitionById(i2);
    }

    public Bundle getTransitionState() {
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.recordState();
        return this.mStateCache.getTransitionState();
    }

    public long getTransitionTimeMs() {
        if (this.f3042j != null) {
            this.mTransitionDuration = r0.getDuration() / 1000.0f;
        }
        return (long) (this.mTransitionDuration * 1000.0f);
    }

    public float getVelocity() {
        return this.f3045m;
    }

    public void getViewVelocity(View view, float f2, float f3, float[] fArr, int i2) {
        float interpolation;
        float velocity = this.f3045m;
        float f4 = this.f3049q;
        if (this.f3043k != null) {
            float fSignum = Math.signum(this.f3050r - f4);
            float interpolation2 = this.f3043k.getInterpolation(this.f3049q + EPSILON);
            interpolation = this.f3043k.getInterpolation(this.f3049q);
            velocity = (fSignum * ((interpolation2 - interpolation) / EPSILON)) / this.mTransitionDuration;
        } else {
            interpolation = f4;
        }
        Interpolator interpolator = this.f3043k;
        if (interpolator instanceof MotionInterpolator) {
            velocity = ((MotionInterpolator) interpolator).getVelocity();
        }
        MotionController motionController = (MotionController) this.f3047o.get(view);
        if ((i2 & 1) == 0) {
            motionController.m(interpolation, view.getWidth(), view.getHeight(), f2, f3, fArr);
        } else {
            motionController.h(interpolation, f2, f3, fArr);
        }
        if (i2 < 2) {
            fArr[0] = fArr[0] * velocity;
            fArr[1] = fArr[1] * velocity;
        }
    }

    @Override // android.view.View
    public boolean isAttachedToWindow() {
        return super.isAttachedToWindow();
    }

    public boolean isDelayedApplicationOfInitialState() {
        return this.mDelayedApply;
    }

    public boolean isInRotation() {
        return this.mInRotation;
    }

    public boolean isInteractionEnabled() {
        return this.mInteractionEnabled;
    }

    public boolean isViewTransitionEnabled(int i2) {
        MotionScene motionScene = this.f3042j;
        if (motionScene != null) {
            return motionScene.isViewTransitionEnabled(i2);
        }
        return false;
    }

    public void jumpToState(int i2) {
        if (!isAttachedToWindow()) {
            this.f3046n = i2;
        }
        if (this.mBeginState == i2) {
            setProgress(0.0f);
        } else if (this.mEndState == i2) {
            setProgress(1.0f);
        } else {
            setTransition(i2, i2);
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout
    public void loadLayoutDescription(int i2) {
        MotionScene.Transition transition;
        if (i2 == 0) {
            this.f3042j = null;
            return;
        }
        try {
            MotionScene motionScene = new MotionScene(getContext(), this, i2);
            this.f3042j = motionScene;
            if (this.f3046n == -1) {
                this.f3046n = motionScene.u();
                this.mBeginState = this.f3042j.u();
                this.mEndState = this.f3042j.j();
            }
            if (!isAttachedToWindow()) {
                this.f3042j = null;
                return;
            }
            try {
                Display display = getDisplay();
                this.mPreviouseRotation = display == null ? 0 : display.getRotation();
                MotionScene motionScene2 = this.f3042j;
                if (motionScene2 != null) {
                    ConstraintSet constraintSetH = motionScene2.h(this.f3046n);
                    this.f3042j.z(this);
                    ArrayList<MotionHelper> arrayList = this.mDecoratorsHelpers;
                    if (arrayList != null) {
                        Iterator<MotionHelper> it = arrayList.iterator();
                        while (it.hasNext()) {
                            it.next().onFinishedMotionScene(this);
                        }
                    }
                    if (constraintSetH != null) {
                        constraintSetH.applyTo(this);
                    }
                    this.mBeginState = this.f3046n;
                }
                R();
                StateCache stateCache = this.mStateCache;
                if (stateCache != null) {
                    if (this.mDelayedApply) {
                        post(new Runnable() { // from class: androidx.constraintlayout.motion.widget.MotionLayout.1
                            @Override // java.lang.Runnable
                            public void run() {
                                MotionLayout.this.mStateCache.a();
                            }
                        });
                        return;
                    } else {
                        stateCache.a();
                        return;
                    }
                }
                MotionScene motionScene3 = this.f3042j;
                if (motionScene3 == null || (transition = motionScene3.f3127b) == null || transition.getAutoTransition() != 4) {
                    return;
                }
                transitionToEnd();
                setState(TransitionState.SETUP);
                setState(TransitionState.MOVING);
            } catch (Exception e2) {
                throw new IllegalArgumentException("unable to parse MotionScene file", e2);
            }
        } catch (Exception e3) {
            throw new IllegalArgumentException("unable to parse MotionScene file", e3);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        MotionScene.Transition transition;
        int i2;
        super.onAttachedToWindow();
        Display display = getDisplay();
        if (display != null) {
            this.mPreviouseRotation = display.getRotation();
        }
        MotionScene motionScene = this.f3042j;
        if (motionScene != null && (i2 = this.f3046n) != -1) {
            ConstraintSet constraintSetH = motionScene.h(i2);
            this.f3042j.z(this);
            ArrayList<MotionHelper> arrayList = this.mDecoratorsHelpers;
            if (arrayList != null) {
                Iterator<MotionHelper> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().onFinishedMotionScene(this);
                }
            }
            if (constraintSetH != null) {
                constraintSetH.applyTo(this);
            }
            this.mBeginState = this.f3046n;
        }
        R();
        StateCache stateCache = this.mStateCache;
        if (stateCache != null) {
            if (this.mDelayedApply) {
                post(new Runnable() { // from class: androidx.constraintlayout.motion.widget.MotionLayout.4
                    @Override // java.lang.Runnable
                    public void run() {
                        MotionLayout.this.mStateCache.a();
                    }
                });
                return;
            } else {
                stateCache.a();
                return;
            }
        }
        MotionScene motionScene2 = this.f3042j;
        if (motionScene2 == null || (transition = motionScene2.f3127b) == null || transition.getAutoTransition() != 4) {
            return;
        }
        transitionToEnd();
        setState(TransitionState.SETUP);
        setState(TransitionState.MOVING);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        TouchResponse touchResponse;
        int iG;
        RectF rectFF;
        MotionScene motionScene = this.f3042j;
        if (motionScene != null && this.mInteractionEnabled) {
            ViewTransitionController viewTransitionController = motionScene.f3128c;
            if (viewTransitionController != null) {
                viewTransitionController.i(motionEvent);
            }
            MotionScene.Transition transition = this.f3042j.f3127b;
            if (transition != null && transition.isEnabled() && (touchResponse = transition.getTouchResponse()) != null && ((motionEvent.getAction() != 0 || (rectFF = touchResponse.f(this, new RectF())) == null || rectFF.contains(motionEvent.getX(), motionEvent.getY())) && (iG = touchResponse.g()) != -1)) {
                View view = this.mRegionView;
                if (view == null || view.getId() != iG) {
                    this.mRegionView = findViewById(iG);
                }
                if (this.mRegionView != null) {
                    this.mBoundsCheck.set(r0.getLeft(), this.mRegionView.getTop(), this.mRegionView.getRight(), this.mRegionView.getBottom());
                    if (this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY()) && !handlesTouchEvent(this.mRegionView.getLeft(), this.mRegionView.getTop(), this.mRegionView, motionEvent)) {
                        return onTouchEvent(motionEvent);
                    }
                }
            }
        }
        return false;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        this.mInLayout = true;
        try {
            if (this.f3042j == null) {
                super.onLayout(z2, i2, i3, i4, i5);
                return;
            }
            int i6 = i4 - i2;
            int i7 = i5 - i3;
            if (this.f3058z != i6 || this.A != i7) {
                rebuildScene();
                K(true);
            }
            this.f3058z = i6;
            this.A = i7;
            this.f3056x = i6;
            this.f3057y = i7;
        } finally {
            this.mInLayout = false;
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    protected void onMeasure(int i2, int i3) {
        if (this.f3042j == null) {
            super.onMeasure(i2, i3);
            return;
        }
        boolean z2 = false;
        boolean z3 = (this.mLastWidthMeasureSpec == i2 && this.mLastHeightMeasureSpec == i3) ? false : true;
        if (this.mNeedsFireTransitionCompleted) {
            this.mNeedsFireTransitionCompleted = false;
            R();
            processTransitionCompleted();
            z3 = true;
        }
        if (this.f3228c) {
            z3 = true;
        }
        this.mLastWidthMeasureSpec = i2;
        this.mLastHeightMeasureSpec = i3;
        int iU = this.f3042j.u();
        int iJ = this.f3042j.j();
        if ((z3 || this.U.isNotConfiguredWith(iU, iJ)) && this.mBeginState != -1) {
            super.onMeasure(i2, i3);
            this.U.c(this.f3227b, this.f3042j.h(iU), this.f3042j.h(iJ));
            this.U.reEvaluateState();
            this.U.setMeasuredId(iU, iJ);
        } else {
            if (z3) {
                super.onMeasure(i2, i3);
            }
            z2 = true;
        }
        if (this.H || z2) {
            int paddingTop = getPaddingTop() + getPaddingBottom();
            int width = this.f3227b.getWidth() + getPaddingLeft() + getPaddingRight();
            int height = this.f3227b.getHeight() + paddingTop;
            int i4 = this.M;
            if (i4 == Integer.MIN_VALUE || i4 == 0) {
                width = (int) (this.I + (this.O * (this.K - r8)));
                requestLayout();
            }
            int i5 = this.N;
            if (i5 == Integer.MIN_VALUE || i5 == 0) {
                height = (int) (this.J + (this.O * (this.L - r8)));
                requestLayout();
            }
            setMeasuredDimension(width, height);
        }
        evaluateLayout();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(@NonNull View view, float f2, float f3, boolean z2) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(@NonNull View view, float f2, float f3) {
        return false;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(@NonNull final View view, int i2, int i3, @NonNull int[] iArr, int i4) {
        MotionScene.Transition transition;
        TouchResponse touchResponse;
        int iG;
        MotionScene motionScene = this.f3042j;
        if (motionScene == null || (transition = motionScene.f3127b) == null || !transition.isEnabled()) {
            return;
        }
        int i5 = -1;
        if (!transition.isEnabled() || (touchResponse = transition.getTouchResponse()) == null || (iG = touchResponse.g()) == -1 || view.getId() == iG) {
            if (motionScene.n()) {
                TouchResponse touchResponse2 = transition.getTouchResponse();
                if (touchResponse2 != null && (touchResponse2.getFlags() & 4) != 0) {
                    i5 = i3;
                }
                float f2 = this.f3048p;
                if ((f2 == 1.0f || f2 == 0.0f) && view.canScrollVertically(i5)) {
                    return;
                }
            }
            if (transition.getTouchResponse() != null && (transition.getTouchResponse().getFlags() & 1) != 0) {
                float fO = motionScene.o(i2, i3);
                float f3 = this.f3049q;
                if ((f3 <= 0.0f && fO < 0.0f) || (f3 >= 1.0f && fO > 0.0f)) {
                    view.setNestedScrollingEnabled(false);
                    view.post(new Runnable(this) { // from class: androidx.constraintlayout.motion.widget.MotionLayout.3
                        @Override // java.lang.Runnable
                        public void run() {
                            view.setNestedScrollingEnabled(true);
                        }
                    });
                    return;
                }
            }
            float f4 = this.f3048p;
            long nanoTime = getNanoTime();
            float f5 = i2;
            this.C = f5;
            float f6 = i3;
            this.D = f6;
            this.F = (float) ((nanoTime - this.E) * 1.0E-9d);
            this.E = nanoTime;
            motionScene.w(f5, f6);
            if (f4 != this.f3048p) {
                iArr[0] = i2;
                iArr[1] = i3;
            }
            K(false);
            if (iArr[0] == 0 && iArr[1] == 0) {
                return;
            }
            this.B = true;
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScroll(@NonNull View view, int i2, int i3, int i4, int i5, int i6) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i2, int i3) {
        this.E = getNanoTime();
        this.F = 0.0f;
        this.C = 0.0f;
        this.D = 0.0f;
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i2) {
        MotionScene motionScene = this.f3042j;
        if (motionScene != null) {
            motionScene.setRtl(e());
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i2, int i3) {
        MotionScene.Transition transition;
        MotionScene motionScene = this.f3042j;
        return (motionScene == null || (transition = motionScene.f3127b) == null || transition.getTouchResponse() == null || (this.f3042j.f3127b.getTouchResponse().getFlags() & 2) != 0) ? false : true;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onStopNestedScroll(@NonNull View view, int i2) {
        MotionScene motionScene = this.f3042j;
        if (motionScene != null) {
            float f2 = this.F;
            if (f2 == 0.0f) {
                return;
            }
            motionScene.x(this.C / f2, this.D / f2);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null || !this.mInteractionEnabled || !motionScene.C()) {
            return super.onTouchEvent(motionEvent);
        }
        MotionScene.Transition transition = this.f3042j.f3127b;
        if (transition != null && !transition.isEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        this.f3042j.y(motionEvent, getCurrentState(), this);
        if (this.f3042j.f3127b.isTransitionFlag(4)) {
            return this.f3042j.f3127b.getTouchResponse().h();
        }
        return true;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof MotionHelper) {
            MotionHelper motionHelper = (MotionHelper) view;
            if (this.mTransitionListeners == null) {
                this.mTransitionListeners = new CopyOnWriteArrayList<>();
            }
            this.mTransitionListeners.add(motionHelper);
            if (motionHelper.isUsedOnShow()) {
                if (this.mOnShowHelpers == null) {
                    this.mOnShowHelpers = new ArrayList<>();
                }
                this.mOnShowHelpers.add(motionHelper);
            }
            if (motionHelper.isUseOnHide()) {
                if (this.mOnHideHelpers == null) {
                    this.mOnHideHelpers = new ArrayList<>();
                }
                this.mOnHideHelpers.add(motionHelper);
            }
            if (motionHelper.isDecorator()) {
                if (this.mDecoratorsHelpers == null) {
                    this.mDecoratorsHelpers = new ArrayList<>();
                }
                this.mDecoratorsHelpers.add(motionHelper);
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
        if (arrayList != null) {
            arrayList.remove(view);
        }
        ArrayList<MotionHelper> arrayList2 = this.mOnHideHelpers;
        if (arrayList2 != null) {
            arrayList2.remove(view);
        }
    }

    @Deprecated
    public void rebuildMotion() {
        Log.e("MotionLayout", "This method is deprecated. Please call rebuildScene() instead.");
        rebuildScene();
    }

    public void rebuildScene() {
        this.U.reEvaluateState();
        invalidate();
    }

    public boolean removeTransitionListener(TransitionListener transitionListener) {
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList = this.mTransitionListeners;
        if (copyOnWriteArrayList == null) {
            return false;
        }
        return copyOnWriteArrayList.remove(transitionListener);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View, android.view.ViewParent
    public void requestLayout() {
        MotionScene motionScene;
        MotionScene.Transition transition;
        if (!this.H && this.f3046n == -1 && (motionScene = this.f3042j) != null && (transition = motionScene.f3127b) != null) {
            int layoutDuringTransition = transition.getLayoutDuringTransition();
            if (layoutDuringTransition == 0) {
                return;
            }
            if (layoutDuringTransition == 2) {
                int childCount = getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    ((MotionController) this.f3047o.get(getChildAt(i2))).remeasure();
                }
                return;
            }
        }
        super.requestLayout();
    }

    @RequiresApi(api = 17)
    public void rotateTo(int i2, int i3) {
        this.mInRotation = true;
        this.mPreRotateWidth = getWidth();
        this.mPreRotateHeight = getHeight();
        int rotation = getDisplay().getRotation();
        this.Q = (rotation + 1) % 4 <= (this.mPreviouseRotation + 1) % 4 ? 2 : 1;
        this.mPreviouseRotation = rotation;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            ViewState viewState = (ViewState) this.R.get(childAt);
            if (viewState == null) {
                viewState = new ViewState();
                this.R.put(childAt, viewState);
            }
            viewState.getState(childAt);
        }
        this.mBeginState = -1;
        this.mEndState = i2;
        this.f3042j.A(-1, i2);
        this.U.c(this.f3227b, null, this.f3042j.h(this.mEndState));
        this.f3048p = 0.0f;
        this.f3049q = 0.0f;
        invalidate();
        transitionToEnd(new Runnable() { // from class: androidx.constraintlayout.motion.widget.MotionLayout.2
            @Override // java.lang.Runnable
            public void run() {
                MotionLayout.this.mInRotation = false;
            }
        });
        if (i3 > 0) {
            this.mTransitionDuration = i3 / 1000.0f;
        }
    }

    public void scheduleTransitionTo(int i2) {
        if (getCurrentState() == -1) {
            transitionToState(i2);
            return;
        }
        int[] iArr = this.mScheduledTransitionTo;
        if (iArr == null) {
            this.mScheduledTransitionTo = new int[4];
        } else if (iArr.length <= this.P) {
            this.mScheduledTransitionTo = Arrays.copyOf(iArr, iArr.length * 2);
        }
        int[] iArr2 = this.mScheduledTransitionTo;
        int i3 = this.P;
        this.P = i3 + 1;
        iArr2[i3] = i2;
    }

    public void setDebugMode(int i2) {
        this.f3053u = i2;
        invalidate();
    }

    public void setDelayedApplicationOfInitialState(boolean z2) {
        this.mDelayedApply = z2;
    }

    public void setInteractionEnabled(boolean z2) {
        this.mInteractionEnabled = z2;
    }

    public void setInterpolatedProgress(float f2) {
        if (this.f3042j != null) {
            setState(TransitionState.MOVING);
            Interpolator interpolator = this.f3042j.getInterpolator();
            if (interpolator != null) {
                setProgress(interpolator.getInterpolation(f2));
                return;
            }
        }
        setProgress(f2);
    }

    public void setOnHide(float f2) {
        ArrayList<MotionHelper> arrayList = this.mOnHideHelpers;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mOnHideHelpers.get(i2).setProgress(f2);
            }
        }
    }

    public void setOnShow(float f2) {
        ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mOnShowHelpers.get(i2).setProgress(f2);
            }
        }
    }

    public void setProgress(float f2, float f3) {
        if (!isAttachedToWindow()) {
            if (this.mStateCache == null) {
                this.mStateCache = new StateCache();
            }
            this.mStateCache.setProgress(f2);
            this.mStateCache.setVelocity(f3);
            return;
        }
        setProgress(f2);
        setState(TransitionState.MOVING);
        this.f3045m = f3;
        if (f3 != 0.0f) {
            H(f3 > 0.0f ? 1.0f : 0.0f);
        } else {
            if (f2 == 0.0f || f2 == 1.0f) {
                return;
            }
            H(f2 > 0.5f ? 1.0f : 0.0f);
        }
    }

    public void setScene(MotionScene motionScene) {
        this.f3042j = motionScene;
        motionScene.setRtl(e());
        rebuildScene();
    }

    void setStartState(int i2) {
        if (isAttachedToWindow()) {
            this.f3046n = i2;
            return;
        }
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.setStartState(i2);
        this.mStateCache.setEndState(i2);
    }

    void setState(TransitionState transitionState) {
        TransitionState transitionState2 = TransitionState.FINISHED;
        if (transitionState == transitionState2 && this.f3046n == -1) {
            return;
        }
        TransitionState transitionState3 = this.T;
        this.T = transitionState;
        TransitionState transitionState4 = TransitionState.MOVING;
        if (transitionState3 == transitionState4 && transitionState == transitionState4) {
            fireTransitionChange();
        }
        int i2 = AnonymousClass5.f3063a[transitionState3.ordinal()];
        if (i2 != 1 && i2 != 2) {
            if (i2 == 3 && transitionState == transitionState2) {
                L();
                return;
            }
            return;
        }
        if (transitionState == transitionState4) {
            fireTransitionChange();
        }
        if (transitionState == transitionState2) {
            L();
        }
    }

    public void setTransition(int i2, int i3) {
        if (!isAttachedToWindow()) {
            if (this.mStateCache == null) {
                this.mStateCache = new StateCache();
            }
            this.mStateCache.setStartState(i2);
            this.mStateCache.setEndState(i3);
            return;
        }
        MotionScene motionScene = this.f3042j;
        if (motionScene != null) {
            this.mBeginState = i2;
            this.mEndState = i3;
            motionScene.A(i2, i3);
            this.U.c(this.f3227b, this.f3042j.h(i2), this.f3042j.h(i3));
            rebuildScene();
            this.f3049q = 0.0f;
            transitionToStart();
        }
    }

    public void setTransitionDuration(int i2) {
        MotionScene motionScene = this.f3042j;
        if (motionScene == null) {
            Log.e("MotionLayout", "MotionScene not defined");
        } else {
            motionScene.setDuration(i2);
        }
    }

    public void setTransitionListener(TransitionListener transitionListener) {
        this.mTransitionListener = transitionListener;
    }

    public void setTransitionState(Bundle bundle) {
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.setTransitionState(bundle);
        if (isAttachedToWindow()) {
            this.mStateCache.a();
        }
    }

    @Override // android.view.View
    public String toString() {
        Context context = getContext();
        return Debug.getName(context, this.mBeginState) + "->" + Debug.getName(context, this.mEndState) + " (pos:" + this.f3049q + " Dpos/Dt:" + this.f3045m;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void touchAnimateTo(int r10, float r11, float r12) {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.touchAnimateTo(int, float, float):void");
    }

    public void touchSpringTo(float f2, float f3) {
        if (this.f3042j == null || this.f3049q == f2) {
            return;
        }
        this.mTemporalInterpolator = true;
        this.mAnimationStartTime = getNanoTime();
        this.mTransitionDuration = this.f3042j.getDuration() / 1000.0f;
        this.f3050r = f2;
        this.f3051s = true;
        this.mStopLogic.springConfig(this.f3049q, f2, f3, this.f3042j.r(), this.f3042j.s(), this.f3042j.q(), this.f3042j.t(), this.f3042j.p());
        int i2 = this.f3046n;
        this.f3050r = f2;
        this.f3046n = i2;
        this.f3043k = this.mStopLogic;
        this.mTransitionInstantly = false;
        this.mAnimationStartTime = getNanoTime();
        invalidate();
    }

    public void transitionToEnd() {
        H(1.0f);
        this.mOnComplete = null;
    }

    public void transitionToStart() {
        H(0.0f);
    }

    public void transitionToState(int i2) {
        if (isAttachedToWindow()) {
            transitionToState(i2, -1, -1);
            return;
        }
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.setEndState(i2);
    }

    public void updateState(int i2, ConstraintSet constraintSet) {
        MotionScene motionScene = this.f3042j;
        if (motionScene != null) {
            motionScene.setConstraintSet(i2, constraintSet);
        }
        updateState();
        if (this.f3046n == i2) {
            constraintSet.applyTo(this);
        }
    }

    public void updateStateAnimate(int i2, ConstraintSet constraintSet, int i3) {
        if (this.f3042j != null && this.f3046n == i2) {
            updateState(R.id.view_transition, getConstraintSet(i2));
            setState(R.id.view_transition, -1, -1);
            updateState(i2, constraintSet);
            MotionScene.Transition transition = new MotionScene.Transition(-1, this.f3042j, R.id.view_transition, i2);
            transition.setDuration(i3);
            setTransition(transition);
            transitionToEnd();
        }
    }

    public void viewTransition(int i2, View... viewArr) {
        MotionScene motionScene = this.f3042j;
        if (motionScene != null) {
            motionScene.viewTransition(i2, viewArr);
        } else {
            Log.e("MotionLayout", " no motionScene");
        }
    }

    private static class MyTracker implements MotionTracker {

        /* renamed from: me, reason: collision with root package name */
        private static MyTracker f3095me = new MyTracker();

        /* renamed from: a, reason: collision with root package name */
        VelocityTracker f3096a;

        private MyTracker() {
        }

        public static MyTracker obtain() {
            f3095me.f3096a = VelocityTracker.obtain();
            return f3095me;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void addMovement(MotionEvent motionEvent) {
            VelocityTracker velocityTracker = this.f3096a;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void clear() {
            VelocityTracker velocityTracker = this.f3096a;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void computeCurrentVelocity(int i2) {
            VelocityTracker velocityTracker = this.f3096a;
            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(i2);
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public float getXVelocity() {
            VelocityTracker velocityTracker = this.f3096a;
            if (velocityTracker != null) {
                return velocityTracker.getXVelocity();
            }
            return 0.0f;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public float getYVelocity() {
            VelocityTracker velocityTracker = this.f3096a;
            if (velocityTracker != null) {
                return velocityTracker.getYVelocity();
            }
            return 0.0f;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void recycle() {
            VelocityTracker velocityTracker = this.f3096a;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.f3096a = null;
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void computeCurrentVelocity(int i2, float f2) {
            VelocityTracker velocityTracker = this.f3096a;
            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(i2, f2);
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public float getXVelocity(int i2) {
            VelocityTracker velocityTracker = this.f3096a;
            if (velocityTracker != null) {
                return velocityTracker.getXVelocity(i2);
            }
            return 0.0f;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public float getYVelocity(int i2) {
            if (this.f3096a != null) {
                return getYVelocity(i2);
            }
            return 0.0f;
        }
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public void onNestedScroll(@NonNull View view, int i2, int i3, int i4, int i5, int i6, int[] iArr) {
        if (this.B || i2 != 0 || i3 != 0) {
            iArr[0] = iArr[0] + i4;
            iArr[1] = iArr[1] + i5;
        }
        this.B = false;
    }

    public void transitionToEnd(Runnable runnable) {
        H(1.0f);
        this.mOnComplete = runnable;
    }

    public void transitionToState(int i2, int i3) {
        if (!isAttachedToWindow()) {
            if (this.mStateCache == null) {
                this.mStateCache = new StateCache();
            }
            this.mStateCache.setEndState(i2);
            return;
        }
        transitionToState(i2, -1, -1, i3);
    }

    public void updateState() {
        this.U.c(this.f3227b, this.f3042j.h(this.mBeginState), this.f3042j.h(this.mEndState));
        rebuildScene();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout
    public void setState(int i2, int i3, int i4) {
        setState(TransitionState.SETUP);
        this.f3046n = i2;
        this.mBeginState = -1;
        this.mEndState = -1;
        ConstraintLayoutStates constraintLayoutStates = this.f3229d;
        if (constraintLayoutStates != null) {
            constraintLayoutStates.updateConstraints(i2, i3, i4);
            return;
        }
        MotionScene motionScene = this.f3042j;
        if (motionScene != null) {
            motionScene.h(i2).applyTo(this);
        }
    }

    public void setProgress(float f2) {
        if (f2 < 0.0f || f2 > 1.0f) {
            Log.w("MotionLayout", "Warning! Progress is defined for values between 0.0 and 1.0 inclusive");
        }
        if (!isAttachedToWindow()) {
            if (this.mStateCache == null) {
                this.mStateCache = new StateCache();
            }
            this.mStateCache.setProgress(f2);
            return;
        }
        if (f2 <= 0.0f) {
            if (this.f3049q == 1.0f && this.f3046n == this.mEndState) {
                setState(TransitionState.MOVING);
            }
            this.f3046n = this.mBeginState;
            if (this.f3049q == 0.0f) {
                setState(TransitionState.FINISHED);
            }
        } else if (f2 >= 1.0f) {
            if (this.f3049q == 0.0f && this.f3046n == this.mBeginState) {
                setState(TransitionState.MOVING);
            }
            this.f3046n = this.mEndState;
            if (this.f3049q == 1.0f) {
                setState(TransitionState.FINISHED);
            }
        } else {
            this.f3046n = -1;
            setState(TransitionState.MOVING);
        }
        if (this.f3042j == null) {
            return;
        }
        this.mTransitionInstantly = true;
        this.f3050r = f2;
        this.f3048p = f2;
        this.mTransitionLastTime = -1L;
        this.mAnimationStartTime = -1L;
        this.f3043k = null;
        this.f3051s = true;
        invalidate();
    }

    public void transitionToState(int i2, int i3, int i4) {
        transitionToState(i2, i3, i4, -1);
    }

    public void transitionToState(int i2, int i3, int i4, int i5) {
        StateSet stateSet;
        int iConvertToConstraintSet;
        MotionScene motionScene = this.f3042j;
        if (motionScene != null && (stateSet = motionScene.f3126a) != null && (iConvertToConstraintSet = stateSet.convertToConstraintSet(this.f3046n, i2, i3, i4)) != -1) {
            i2 = iConvertToConstraintSet;
        }
        int i6 = this.f3046n;
        if (i6 == i2) {
            return;
        }
        if (this.mBeginState == i2) {
            H(0.0f);
            if (i5 > 0) {
                this.mTransitionDuration = i5 / 1000.0f;
                return;
            }
            return;
        }
        if (this.mEndState == i2) {
            H(1.0f);
            if (i5 > 0) {
                this.mTransitionDuration = i5 / 1000.0f;
                return;
            }
            return;
        }
        this.mEndState = i2;
        if (i6 != -1) {
            setTransition(i6, i2);
            H(1.0f);
            this.f3049q = 0.0f;
            transitionToEnd();
            if (i5 > 0) {
                this.mTransitionDuration = i5 / 1000.0f;
                return;
            }
            return;
        }
        this.mTemporalInterpolator = false;
        this.f3050r = 1.0f;
        this.f3048p = 0.0f;
        this.f3049q = 0.0f;
        this.mTransitionLastTime = getNanoTime();
        this.mAnimationStartTime = getNanoTime();
        this.mTransitionInstantly = false;
        this.f3043k = null;
        if (i5 == -1) {
            this.mTransitionDuration = this.f3042j.getDuration() / 1000.0f;
        }
        this.mBeginState = -1;
        this.f3042j.A(-1, this.mEndState);
        SparseArray sparseArray = new SparseArray();
        if (i5 == 0) {
            this.mTransitionDuration = this.f3042j.getDuration() / 1000.0f;
        } else if (i5 > 0) {
            this.mTransitionDuration = i5 / 1000.0f;
        }
        int childCount = getChildCount();
        this.f3047o.clear();
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            this.f3047o.put(childAt, new MotionController(childAt));
            sparseArray.put(childAt.getId(), (MotionController) this.f3047o.get(childAt));
        }
        this.f3051s = true;
        this.U.c(this.f3227b, null, this.f3042j.h(i2));
        rebuildScene();
        this.U.build();
        computeCurrentPositions();
        int width = getWidth();
        int height = getHeight();
        if (this.mDecoratorsHelpers != null) {
            for (int i8 = 0; i8 < childCount; i8++) {
                MotionController motionController = (MotionController) this.f3047o.get(getChildAt(i8));
                if (motionController != null) {
                    this.f3042j.getKeyFrames(motionController);
                }
            }
            Iterator<MotionHelper> it = this.mDecoratorsHelpers.iterator();
            while (it.hasNext()) {
                it.next().onPreSetup(this, this.f3047o);
            }
            for (int i9 = 0; i9 < childCount; i9++) {
                MotionController motionController2 = (MotionController) this.f3047o.get(getChildAt(i9));
                if (motionController2 != null) {
                    motionController2.setup(width, height, this.mTransitionDuration, getNanoTime());
                }
            }
        } else {
            for (int i10 = 0; i10 < childCount; i10++) {
                MotionController motionController3 = (MotionController) this.f3047o.get(getChildAt(i10));
                if (motionController3 != null) {
                    this.f3042j.getKeyFrames(motionController3);
                    motionController3.setup(width, height, this.mTransitionDuration, getNanoTime());
                }
            }
        }
        float staggered = this.f3042j.getStaggered();
        if (staggered != 0.0f) {
            float fMin = Float.MAX_VALUE;
            float fMax = -3.4028235E38f;
            for (int i11 = 0; i11 < childCount; i11++) {
                MotionController motionController4 = (MotionController) this.f3047o.get(getChildAt(i11));
                float finalY = motionController4.getFinalY() + motionController4.getFinalX();
                fMin = Math.min(fMin, finalY);
                fMax = Math.max(fMax, finalY);
            }
            for (int i12 = 0; i12 < childCount; i12++) {
                MotionController motionController5 = (MotionController) this.f3047o.get(getChildAt(i12));
                float finalX = motionController5.getFinalX();
                float finalY2 = motionController5.getFinalY();
                motionController5.f3037h = 1.0f / (1.0f - staggered);
                motionController5.f3036g = staggered - ((((finalX + finalY2) - fMin) * staggered) / (fMax - fMin));
            }
        }
        this.f3048p = 0.0f;
        this.f3049q = 0.0f;
        this.f3051s = true;
        invalidate();
    }

    public void setTransition(int i2) {
        float f2;
        if (this.f3042j != null) {
            MotionScene.Transition transition = getTransition(i2);
            this.mBeginState = transition.getStartConstraintSetId();
            this.mEndState = transition.getEndConstraintSetId();
            if (!isAttachedToWindow()) {
                if (this.mStateCache == null) {
                    this.mStateCache = new StateCache();
                }
                this.mStateCache.setStartState(this.mBeginState);
                this.mStateCache.setEndState(this.mEndState);
                return;
            }
            int i3 = this.f3046n;
            if (i3 == this.mBeginState) {
                f2 = 0.0f;
            } else {
                f2 = i3 == this.mEndState ? 1.0f : Float.NaN;
            }
            this.f3042j.setTransition(transition);
            this.U.c(this.f3227b, this.f3042j.h(this.mBeginState), this.f3042j.h(this.mEndState));
            rebuildScene();
            if (this.f3049q != f2) {
                if (f2 == 0.0f) {
                    J(true);
                    this.f3042j.h(this.mBeginState).applyTo(this);
                } else if (f2 == 1.0f) {
                    J(false);
                    this.f3042j.h(this.mEndState).applyTo(this);
                }
            }
            this.f3049q = Float.isNaN(f2) ? 0.0f : f2;
            if (Float.isNaN(f2)) {
                Log.v("MotionLayout", Debug.getLocation() + " transitionToStart ");
                transitionToStart();
                return;
            }
            setProgress(f2);
        }
    }

    private void checkStructure(int i2, ConstraintSet constraintSet) {
        String name = Debug.getName(getContext(), i2);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            int id = childAt.getId();
            if (id == -1) {
                Log.w("MotionLayout", "CHECK: " + name + " ALL VIEWS SHOULD HAVE ID's " + childAt.getClass().getName() + " does not!");
            }
            if (constraintSet.getConstraint(id) == null) {
                Log.w("MotionLayout", "CHECK: " + name + " NO CONSTRAINTS for " + Debug.getName(childAt));
            }
        }
        int[] knownIds = constraintSet.getKnownIds();
        for (int i4 = 0; i4 < knownIds.length; i4++) {
            int i5 = knownIds[i4];
            String name2 = Debug.getName(getContext(), i5);
            if (findViewById(knownIds[i4]) == null) {
                Log.w("MotionLayout", "CHECK: " + name + " NO View matches id " + name2);
            }
            if (constraintSet.getHeight(i5) == -1) {
                Log.w("MotionLayout", "CHECK: " + name + "(" + name2 + ") no LAYOUT_HEIGHT");
            }
            if (constraintSet.getWidth(i5) == -1) {
                Log.w("MotionLayout", "CHECK: " + name + "(" + name2 + ") no LAYOUT_HEIGHT");
            }
        }
    }

    protected void setTransition(MotionScene.Transition transition) {
        this.f3042j.setTransition(transition);
        setState(TransitionState.SETUP);
        if (this.f3046n == this.f3042j.j()) {
            this.f3049q = 1.0f;
            this.f3048p = 1.0f;
            this.f3050r = 1.0f;
        } else {
            this.f3049q = 0.0f;
            this.f3048p = 0.0f;
            this.f3050r = 0.0f;
        }
        this.mTransitionLastTime = transition.isTransitionFlag(1) ? -1L : getNanoTime();
        int iU = this.f3042j.u();
        int iJ = this.f3042j.j();
        if (iU == this.mBeginState && iJ == this.mEndState) {
            return;
        }
        this.mBeginState = iU;
        this.mEndState = iJ;
        this.f3042j.A(iU, iJ);
        this.U.c(this.f3227b, this.f3042j.h(this.mBeginState), this.f3042j.h(this.mEndState));
        this.U.setMeasuredId(this.mBeginState, this.mEndState);
        this.U.reEvaluateState();
        rebuildScene();
    }

    private void checkStructure(MotionScene.Transition transition) {
        if (transition.getStartConstraintSetId() == transition.getEndConstraintSetId()) {
            Log.e("MotionLayout", "CHECK: start and end constraint set should not be the same!");
        }
    }

    public MotionLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f3044l = null;
        this.f3045m = 0.0f;
        this.mBeginState = -1;
        this.f3046n = -1;
        this.mEndState = -1;
        this.mLastWidthMeasureSpec = 0;
        this.mLastHeightMeasureSpec = 0;
        this.mInteractionEnabled = true;
        this.f3047o = new HashMap();
        this.mAnimationStartTime = 0L;
        this.mTransitionDuration = 1.0f;
        this.f3048p = 0.0f;
        this.f3049q = 0.0f;
        this.f3050r = 0.0f;
        this.f3051s = false;
        this.f3052t = false;
        this.f3053u = 0;
        this.mTemporalInterpolator = false;
        this.mStopLogic = new StopLogic();
        this.mDecelerateLogic = new DecelerateInterpolator();
        this.f3055w = true;
        this.B = false;
        this.mKeepAnimating = false;
        this.mOnShowHelpers = null;
        this.mOnHideHelpers = null;
        this.mDecoratorsHelpers = null;
        this.mTransitionListeners = null;
        this.mFrames = 0;
        this.mLastDrawTime = -1L;
        this.mLastFps = 0.0f;
        this.mListenerState = 0;
        this.mListenerPosition = 0.0f;
        this.G = false;
        this.H = false;
        this.mKeyCache = new KeyCache();
        this.mInLayout = false;
        this.mOnComplete = null;
        this.mScheduledTransitionTo = null;
        this.P = 0;
        this.mInRotation = false;
        this.Q = 0;
        this.R = new HashMap();
        this.S = new Rect();
        this.mDelayedApply = false;
        this.T = TransitionState.UNDEFINED;
        this.U = new Model();
        this.mNeedsFireTransitionCompleted = false;
        this.mBoundsCheck = new RectF();
        this.mRegionView = null;
        this.mInverseMatrix = null;
        this.V = new ArrayList();
        init(attributeSet);
    }

    public MotionLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f3044l = null;
        this.f3045m = 0.0f;
        this.mBeginState = -1;
        this.f3046n = -1;
        this.mEndState = -1;
        this.mLastWidthMeasureSpec = 0;
        this.mLastHeightMeasureSpec = 0;
        this.mInteractionEnabled = true;
        this.f3047o = new HashMap();
        this.mAnimationStartTime = 0L;
        this.mTransitionDuration = 1.0f;
        this.f3048p = 0.0f;
        this.f3049q = 0.0f;
        this.f3050r = 0.0f;
        this.f3051s = false;
        this.f3052t = false;
        this.f3053u = 0;
        this.mTemporalInterpolator = false;
        this.mStopLogic = new StopLogic();
        this.mDecelerateLogic = new DecelerateInterpolator();
        this.f3055w = true;
        this.B = false;
        this.mKeepAnimating = false;
        this.mOnShowHelpers = null;
        this.mOnHideHelpers = null;
        this.mDecoratorsHelpers = null;
        this.mTransitionListeners = null;
        this.mFrames = 0;
        this.mLastDrawTime = -1L;
        this.mLastFps = 0.0f;
        this.mListenerState = 0;
        this.mListenerPosition = 0.0f;
        this.G = false;
        this.H = false;
        this.mKeyCache = new KeyCache();
        this.mInLayout = false;
        this.mOnComplete = null;
        this.mScheduledTransitionTo = null;
        this.P = 0;
        this.mInRotation = false;
        this.Q = 0;
        this.R = new HashMap();
        this.S = new Rect();
        this.mDelayedApply = false;
        this.T = TransitionState.UNDEFINED;
        this.U = new Model();
        this.mNeedsFireTransitionCompleted = false;
        this.mBoundsCheck = new RectF();
        this.mRegionView = null;
        this.mInverseMatrix = null;
        this.V = new ArrayList();
        init(attributeSet);
    }
}
