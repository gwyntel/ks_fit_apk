package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.Xml;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ViewTransition {
    public static final String CONSTRAINT_OVERRIDE = "ConstraintOverride";
    public static final String CUSTOM_ATTRIBUTE = "CustomAttribute";
    public static final String CUSTOM_METHOD = "CustomMethod";
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    public static final String KEY_FRAME_SET_TAG = "KeyFrameSet";
    public static final int ONSTATE_ACTION_DOWN = 1;
    public static final int ONSTATE_ACTION_DOWN_UP = 3;
    public static final int ONSTATE_ACTION_UP = 2;
    public static final int ONSTATE_SHARED_VALUE_SET = 4;
    public static final int ONSTATE_SHARED_VALUE_UNSET = 5;
    private static final int SPLINE_STRING = -1;
    private static String TAG = "ViewTransition";
    private static final int UNSET = -1;
    public static final String VIEW_TRANSITION_TAG = "ViewTransition";

    /* renamed from: a, reason: collision with root package name */
    int f3137a;

    /* renamed from: b, reason: collision with root package name */
    KeyFrames f3138b;

    /* renamed from: c, reason: collision with root package name */
    ConstraintSet.Constraint f3139c;

    /* renamed from: d, reason: collision with root package name */
    Context f3140d;
    private int mId;
    private int mTargetId;
    private String mTargetString;
    private int mOnStateTransition = -1;
    private boolean mDisabled = false;
    private int mPathMotionArc = 0;
    private int mDuration = -1;
    private int mUpDuration = -1;
    private int mDefaultInterpolator = 0;
    private String mDefaultInterpolatorString = null;
    private int mDefaultInterpolatorID = -1;
    private int mSetsTag = -1;
    private int mClearsTag = -1;
    private int mIfTagSet = -1;
    private int mIfTagNotSet = -1;
    private int mSharedValueTarget = -1;
    private int mSharedValueID = -1;
    private int mSharedValueCurrent = -1;

    static class Animate {

        /* renamed from: a, reason: collision with root package name */
        long f3142a;

        /* renamed from: b, reason: collision with root package name */
        MotionController f3143b;

        /* renamed from: c, reason: collision with root package name */
        int f3144c;

        /* renamed from: d, reason: collision with root package name */
        int f3145d;

        /* renamed from: f, reason: collision with root package name */
        ViewTransitionController f3147f;

        /* renamed from: g, reason: collision with root package name */
        Interpolator f3148g;

        /* renamed from: i, reason: collision with root package name */
        float f3150i;

        /* renamed from: j, reason: collision with root package name */
        float f3151j;

        /* renamed from: k, reason: collision with root package name */
        long f3152k;

        /* renamed from: m, reason: collision with root package name */
        boolean f3154m;
        private final int mClearsTag;
        private final int mSetsTag;

        /* renamed from: e, reason: collision with root package name */
        KeyCache f3146e = new KeyCache();

        /* renamed from: h, reason: collision with root package name */
        boolean f3149h = false;

        /* renamed from: l, reason: collision with root package name */
        Rect f3153l = new Rect();

        Animate(ViewTransitionController viewTransitionController, MotionController motionController, int i2, int i3, int i4, Interpolator interpolator, int i5, int i6) {
            this.f3154m = false;
            this.f3147f = viewTransitionController;
            this.f3143b = motionController;
            this.f3144c = i2;
            this.f3145d = i3;
            long jNanoTime = System.nanoTime();
            this.f3142a = jNanoTime;
            this.f3152k = jNanoTime;
            this.f3147f.b(this);
            this.f3148g = interpolator;
            this.mSetsTag = i5;
            this.mClearsTag = i6;
            if (i4 == 3) {
                this.f3154m = true;
            }
            this.f3151j = i2 == 0 ? Float.MAX_VALUE : 1.0f / i2;
            a();
        }

        void a() {
            if (this.f3149h) {
                c();
            } else {
                b();
            }
        }

        void b() {
            long jNanoTime = System.nanoTime();
            long j2 = jNanoTime - this.f3152k;
            this.f3152k = jNanoTime;
            float f2 = this.f3150i + (((float) (j2 * 1.0E-6d)) * this.f3151j);
            this.f3150i = f2;
            if (f2 >= 1.0f) {
                this.f3150i = 1.0f;
            }
            Interpolator interpolator = this.f3148g;
            float interpolation = interpolator == null ? this.f3150i : interpolator.getInterpolation(this.f3150i);
            MotionController motionController = this.f3143b;
            boolean zN = motionController.n(motionController.f3031b, interpolation, jNanoTime, this.f3146e);
            if (this.f3150i >= 1.0f) {
                if (this.mSetsTag != -1) {
                    this.f3143b.getView().setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
                }
                if (this.mClearsTag != -1) {
                    this.f3143b.getView().setTag(this.mClearsTag, null);
                }
                if (!this.f3154m) {
                    this.f3147f.h(this);
                }
            }
            if (this.f3150i < 1.0f || zN) {
                this.f3147f.f();
            }
        }

        void c() {
            long jNanoTime = System.nanoTime();
            long j2 = jNanoTime - this.f3152k;
            this.f3152k = jNanoTime;
            float f2 = this.f3150i - (((float) (j2 * 1.0E-6d)) * this.f3151j);
            this.f3150i = f2;
            if (f2 < 0.0f) {
                this.f3150i = 0.0f;
            }
            Interpolator interpolator = this.f3148g;
            float interpolation = interpolator == null ? this.f3150i : interpolator.getInterpolation(this.f3150i);
            MotionController motionController = this.f3143b;
            boolean zN = motionController.n(motionController.f3031b, interpolation, jNanoTime, this.f3146e);
            if (this.f3150i <= 0.0f) {
                if (this.mSetsTag != -1) {
                    this.f3143b.getView().setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
                }
                if (this.mClearsTag != -1) {
                    this.f3143b.getView().setTag(this.mClearsTag, null);
                }
                this.f3147f.h(this);
            }
            if (this.f3150i > 0.0f || zN) {
                this.f3147f.f();
            }
        }

        void d(boolean z2) {
            int i2;
            this.f3149h = z2;
            if (z2 && (i2 = this.f3145d) != -1) {
                this.f3151j = i2 == 0 ? Float.MAX_VALUE : 1.0f / i2;
            }
            this.f3147f.f();
            this.f3152k = System.nanoTime();
        }

        public void reactTo(int i2, float f2, float f3) {
            if (i2 == 1) {
                if (this.f3149h) {
                    return;
                }
                d(true);
            } else {
                if (i2 != 2) {
                    return;
                }
                this.f3143b.getView().getHitRect(this.f3153l);
                if (this.f3153l.contains((int) f2, (int) f3) || this.f3149h) {
                    return;
                }
                d(true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    ViewTransition(android.content.Context r10, org.xmlpull.v1.XmlPullParser r11) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.ViewTransition.<init>(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyTransition$0(View[] viewArr) {
        if (this.mSetsTag != -1) {
            for (View view : viewArr) {
                view.setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
            }
        }
        if (this.mClearsTag != -1) {
            for (View view2 : viewArr) {
                view2.setTag(this.mClearsTag, null);
            }
        }
    }

    private void parseViewTransitionTags(Context context, XmlPullParser xmlPullParser) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.ViewTransition);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.ViewTransition_android_id) {
                this.mId = typedArrayObtainStyledAttributes.getResourceId(index, this.mId);
            } else if (index == R.styleable.ViewTransition_motionTarget) {
                if (MotionLayout.IS_IN_EDIT_MODE) {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.mTargetId);
                    this.mTargetId = resourceId;
                    if (resourceId == -1) {
                        this.mTargetString = typedArrayObtainStyledAttributes.getString(index);
                    }
                } else if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                    this.mTargetString = typedArrayObtainStyledAttributes.getString(index);
                } else {
                    this.mTargetId = typedArrayObtainStyledAttributes.getResourceId(index, this.mTargetId);
                }
            } else if (index == R.styleable.ViewTransition_onStateTransition) {
                this.mOnStateTransition = typedArrayObtainStyledAttributes.getInt(index, this.mOnStateTransition);
            } else if (index == R.styleable.ViewTransition_transitionDisable) {
                this.mDisabled = typedArrayObtainStyledAttributes.getBoolean(index, this.mDisabled);
            } else if (index == R.styleable.ViewTransition_pathMotionArc) {
                this.mPathMotionArc = typedArrayObtainStyledAttributes.getInt(index, this.mPathMotionArc);
            } else if (index == R.styleable.ViewTransition_duration) {
                this.mDuration = typedArrayObtainStyledAttributes.getInt(index, this.mDuration);
            } else if (index == R.styleable.ViewTransition_upDuration) {
                this.mUpDuration = typedArrayObtainStyledAttributes.getInt(index, this.mUpDuration);
            } else if (index == R.styleable.ViewTransition_viewTransitionMode) {
                this.f3137a = typedArrayObtainStyledAttributes.getInt(index, this.f3137a);
            } else if (index == R.styleable.ViewTransition_motionInterpolator) {
                int i3 = typedArrayObtainStyledAttributes.peekValue(index).type;
                if (i3 == 1) {
                    int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                    this.mDefaultInterpolatorID = resourceId2;
                    if (resourceId2 != -1) {
                        this.mDefaultInterpolator = -2;
                    }
                } else if (i3 == 3) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.mDefaultInterpolatorString = string;
                    if (string == null || string.indexOf("/") <= 0) {
                        this.mDefaultInterpolator = -1;
                    } else {
                        this.mDefaultInterpolatorID = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                        this.mDefaultInterpolator = -2;
                    }
                } else {
                    this.mDefaultInterpolator = typedArrayObtainStyledAttributes.getInteger(index, this.mDefaultInterpolator);
                }
            } else if (index == R.styleable.ViewTransition_setsTag) {
                this.mSetsTag = typedArrayObtainStyledAttributes.getResourceId(index, this.mSetsTag);
            } else if (index == R.styleable.ViewTransition_clearsTag) {
                this.mClearsTag = typedArrayObtainStyledAttributes.getResourceId(index, this.mClearsTag);
            } else if (index == R.styleable.ViewTransition_ifTagSet) {
                this.mIfTagSet = typedArrayObtainStyledAttributes.getResourceId(index, this.mIfTagSet);
            } else if (index == R.styleable.ViewTransition_ifTagNotSet) {
                this.mIfTagNotSet = typedArrayObtainStyledAttributes.getResourceId(index, this.mIfTagNotSet);
            } else if (index == R.styleable.ViewTransition_SharedValueId) {
                this.mSharedValueID = typedArrayObtainStyledAttributes.getResourceId(index, this.mSharedValueID);
            } else if (index == R.styleable.ViewTransition_SharedValue) {
                this.mSharedValueTarget = typedArrayObtainStyledAttributes.getInteger(index, this.mSharedValueTarget);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void updateTransition(MotionScene.Transition transition, View view) {
        int i2 = this.mDuration;
        if (i2 != -1) {
            transition.setDuration(i2);
        }
        transition.setPathMotionArc(this.mPathMotionArc);
        transition.setInterpolatorInfo(this.mDefaultInterpolator, this.mDefaultInterpolatorString, this.mDefaultInterpolatorID);
        int id = view.getId();
        KeyFrames keyFrames = this.f3138b;
        if (keyFrames != null) {
            ArrayList<Key> keyFramesForView = keyFrames.getKeyFramesForView(-1);
            KeyFrames keyFrames2 = new KeyFrames();
            Iterator<Key> it = keyFramesForView.iterator();
            while (it.hasNext()) {
                keyFrames2.addKey(it.next().mo4clone().setViewId(id));
            }
            transition.addKeyFrame(keyFrames2);
        }
    }

    void b(ViewTransitionController viewTransitionController, MotionLayout motionLayout, View view) {
        MotionController motionController = new MotionController(view);
        motionController.q(view);
        this.f3138b.addAllFrames(motionController);
        motionController.setup(motionLayout.getWidth(), motionLayout.getHeight(), this.mDuration, System.nanoTime());
        new Animate(viewTransitionController, motionController, this.mDuration, this.mUpDuration, this.mOnStateTransition, f(motionLayout.getContext()), this.mSetsTag, this.mClearsTag);
    }

    void c(ViewTransitionController viewTransitionController, MotionLayout motionLayout, int i2, ConstraintSet constraintSet, final View... viewArr) {
        if (this.mDisabled) {
            return;
        }
        int i3 = this.f3137a;
        if (i3 == 2) {
            b(viewTransitionController, motionLayout, viewArr[0]);
            return;
        }
        if (i3 == 1) {
            for (int i4 : motionLayout.getConstraintSetIds()) {
                if (i4 != i2) {
                    ConstraintSet constraintSet2 = motionLayout.getConstraintSet(i4);
                    for (View view : viewArr) {
                        ConstraintSet.Constraint constraint = constraintSet2.getConstraint(view.getId());
                        ConstraintSet.Constraint constraint2 = this.f3139c;
                        if (constraint2 != null) {
                            constraint2.applyDelta(constraint);
                            constraint.mCustomConstraints.putAll(this.f3139c.mCustomConstraints);
                        }
                    }
                }
            }
        }
        ConstraintSet constraintSet3 = new ConstraintSet();
        constraintSet3.clone(constraintSet);
        for (View view2 : viewArr) {
            ConstraintSet.Constraint constraint3 = constraintSet3.getConstraint(view2.getId());
            ConstraintSet.Constraint constraint4 = this.f3139c;
            if (constraint4 != null) {
                constraint4.applyDelta(constraint3);
                constraint3.mCustomConstraints.putAll(this.f3139c.mCustomConstraints);
            }
        }
        motionLayout.updateState(i2, constraintSet3);
        motionLayout.updateState(R.id.view_transition, constraintSet);
        motionLayout.setState(R.id.view_transition, -1, -1);
        MotionScene.Transition transition = new MotionScene.Transition(-1, motionLayout.f3042j, R.id.view_transition, i2);
        for (View view3 : viewArr) {
            updateTransition(transition, view3);
        }
        motionLayout.setTransition(transition);
        motionLayout.transitionToEnd(new Runnable() { // from class: androidx.constraintlayout.motion.widget.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f3162a.lambda$applyTransition$0(viewArr);
            }
        });
    }

    boolean d(View view) {
        int i2 = this.mIfTagSet;
        boolean z2 = i2 == -1 || view.getTag(i2) != null;
        int i3 = this.mIfTagNotSet;
        return z2 && (i3 == -1 || view.getTag(i3) == null);
    }

    int e() {
        return this.mId;
    }

    Interpolator f(Context context) {
        int i2 = this.mDefaultInterpolator;
        if (i2 == -2) {
            return AnimationUtils.loadInterpolator(context, this.mDefaultInterpolatorID);
        }
        if (i2 == -1) {
            final Easing interpolator = Easing.getInterpolator(this.mDefaultInterpolatorString);
            return new Interpolator(this) { // from class: androidx.constraintlayout.motion.widget.ViewTransition.1
                @Override // android.animation.TimeInterpolator
                public float getInterpolation(float f2) {
                    return (float) interpolator.get(f2);
                }
            };
        }
        if (i2 == 0) {
            return new AccelerateDecelerateInterpolator();
        }
        if (i2 == 1) {
            return new AccelerateInterpolator();
        }
        if (i2 == 2) {
            return new DecelerateInterpolator();
        }
        if (i2 == 4) {
            return new BounceInterpolator();
        }
        if (i2 == 5) {
            return new OvershootInterpolator();
        }
        if (i2 != 6) {
            return null;
        }
        return new AnticipateInterpolator();
    }

    boolean g() {
        return !this.mDisabled;
    }

    public int getSharedValue() {
        return this.mSharedValueTarget;
    }

    public int getSharedValueCurrent() {
        return this.mSharedValueCurrent;
    }

    public int getSharedValueID() {
        return this.mSharedValueID;
    }

    public int getStateTransition() {
        return this.mOnStateTransition;
    }

    boolean h(View view) {
        String str;
        if (view == null) {
            return false;
        }
        if ((this.mTargetId == -1 && this.mTargetString == null) || !d(view)) {
            return false;
        }
        if (view.getId() == this.mTargetId) {
            return true;
        }
        return this.mTargetString != null && (view.getLayoutParams() instanceof ConstraintLayout.LayoutParams) && (str = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).constraintTag) != null && str.matches(this.mTargetString);
    }

    void i(boolean z2) {
        this.mDisabled = !z2;
    }

    boolean j(int i2) {
        int i3 = this.mOnStateTransition;
        return i3 == 1 ? i2 == 0 : i3 == 2 ? i2 == 1 : i3 == 3 && i2 == 0;
    }

    public void setSharedValue(int i2) {
        this.mSharedValueTarget = i2;
    }

    public void setSharedValueCurrent(int i2) {
        this.mSharedValueCurrent = i2;
    }

    public void setSharedValueID(int i2) {
        this.mSharedValueID = i2;
    }

    public void setStateTransition(int i2) {
        this.mOnStateTransition = i2;
    }

    public String toString() {
        return "ViewTransition(" + Debug.getName(this.f3140d, this.mId) + ")";
    }
}
