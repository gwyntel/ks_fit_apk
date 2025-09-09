package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.Motion;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes;
import androidx.constraintlayout.core.motion.key.MotionKeyCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Transition implements TypedValues {
    public static final int END = 1;
    public static final int INTERPOLATED = 2;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final int SPLINE_STRING = -1;
    public static final int START = 0;

    /* renamed from: a, reason: collision with root package name */
    HashMap f2843a = new HashMap();
    private HashMap<String, WidgetState> state = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    TypedBundle f2844b = new TypedBundle();
    private int mDefaultInterpolator = 0;
    private String mDefaultInterpolatorString = null;
    private Easing mEasing = null;
    private int mAutoTransition = 0;
    private int mDuration = 400;
    private float mStagger = 0.0f;

    static class KeyPosition {

        /* renamed from: a, reason: collision with root package name */
        int f2845a;

        /* renamed from: b, reason: collision with root package name */
        String f2846b;

        /* renamed from: c, reason: collision with root package name */
        int f2847c;

        /* renamed from: d, reason: collision with root package name */
        float f2848d;

        /* renamed from: e, reason: collision with root package name */
        float f2849e;

        public KeyPosition(String str, int i2, int i3, float f2, float f3) {
            this.f2846b = str;
            this.f2845a = i2;
            this.f2847c = i3;
            this.f2848d = f2;
            this.f2849e = f3;
        }
    }

    static class WidgetState {

        /* renamed from: d, reason: collision with root package name */
        Motion f2853d;

        /* renamed from: h, reason: collision with root package name */
        KeyCache f2857h = new KeyCache();

        /* renamed from: i, reason: collision with root package name */
        int f2858i = -1;

        /* renamed from: j, reason: collision with root package name */
        int f2859j = -1;

        /* renamed from: a, reason: collision with root package name */
        WidgetFrame f2850a = new WidgetFrame();

        /* renamed from: b, reason: collision with root package name */
        WidgetFrame f2851b = new WidgetFrame();

        /* renamed from: c, reason: collision with root package name */
        WidgetFrame f2852c = new WidgetFrame();

        /* renamed from: e, reason: collision with root package name */
        MotionWidget f2854e = new MotionWidget(this.f2850a);

        /* renamed from: f, reason: collision with root package name */
        MotionWidget f2855f = new MotionWidget(this.f2851b);

        /* renamed from: g, reason: collision with root package name */
        MotionWidget f2856g = new MotionWidget(this.f2852c);

        public WidgetState() {
            Motion motion = new Motion(this.f2854e);
            this.f2853d = motion;
            motion.setStart(this.f2854e);
            this.f2853d.setEnd(this.f2855f);
        }

        public WidgetFrame getFrame(int i2) {
            return i2 == 0 ? this.f2850a : i2 == 1 ? this.f2851b : this.f2852c;
        }

        public void interpolate(int i2, int i3, float f2, Transition transition) {
            this.f2858i = i3;
            this.f2859j = i2;
            this.f2853d.setup(i2, i3, 1.0f, System.nanoTime());
            WidgetFrame.interpolate(i2, i3, this.f2852c, this.f2850a, this.f2851b, transition, f2);
            this.f2852c.interpolatedPos = f2;
            this.f2853d.interpolate(this.f2856g, f2, System.nanoTime(), this.f2857h);
        }

        public void setKeyAttribute(TypedBundle typedBundle) {
            MotionKeyAttributes motionKeyAttributes = new MotionKeyAttributes();
            typedBundle.applyDelta(motionKeyAttributes);
            this.f2853d.addKey(motionKeyAttributes);
        }

        public void setKeyCycle(TypedBundle typedBundle) {
            MotionKeyCycle motionKeyCycle = new MotionKeyCycle();
            typedBundle.applyDelta(motionKeyCycle);
            this.f2853d.addKey(motionKeyCycle);
        }

        public void setKeyPosition(TypedBundle typedBundle) {
            MotionKeyPosition motionKeyPosition = new MotionKeyPosition();
            typedBundle.applyDelta(motionKeyPosition);
            this.f2853d.addKey(motionKeyPosition);
        }

        public void update(ConstraintWidget constraintWidget, int i2) {
            if (i2 == 0) {
                this.f2850a.update(constraintWidget);
                this.f2853d.setStart(this.f2854e);
            } else if (i2 == 1) {
                this.f2851b.update(constraintWidget);
                this.f2853d.setEnd(this.f2855f);
            }
            this.f2859j = -1;
        }
    }

    public static Interpolator getInterpolator(int i2, final String str) {
        switch (i2) {
            case -1:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.a
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$0(str, f2);
                    }
                };
            case 0:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.b
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$1(f2);
                    }
                };
            case 1:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.c
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$2(f2);
                    }
                };
            case 2:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.d
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$3(f2);
                    }
                };
            case 3:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.e
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$4(f2);
                    }
                };
            case 4:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.h
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$7(f2);
                    }
                };
            case 5:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.g
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$6(f2);
                    }
                };
            case 6:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.f
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$5(f2);
                    }
                };
            default:
                return null;
        }
    }

    private WidgetState getWidgetState(String str) {
        return this.state.get(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$0(String str, float f2) {
        return (float) Easing.getInterpolator(str).get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$1(float f2) {
        return (float) Easing.getInterpolator("standard").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$2(float f2) {
        return (float) Easing.getInterpolator("accelerate").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$3(float f2) {
        return (float) Easing.getInterpolator("decelerate").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$4(float f2) {
        return (float) Easing.getInterpolator("linear").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$5(float f2) {
        return (float) Easing.getInterpolator("anticipate").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$6(float f2) {
        return (float) Easing.getInterpolator("overshoot").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$7(float f2) {
        return (float) Easing.getInterpolator("spline(0.0, 0.2, 0.4, 0.6, 0.8 ,1.0, 0.8, 1.0, 0.9, 1.0)").get(f2);
    }

    public void addCustomColor(int i2, String str, String str2, int i3) {
        getWidgetState(str, null, i2).getFrame(i2).addCustomColor(str2, i3);
    }

    public void addCustomFloat(int i2, String str, String str2, float f2) {
        getWidgetState(str, null, i2).getFrame(i2).addCustomFloat(str2, f2);
    }

    public void addKeyAttribute(String str, TypedBundle typedBundle) {
        getWidgetState(str, null, 0).setKeyAttribute(typedBundle);
    }

    public void addKeyCycle(String str, TypedBundle typedBundle) {
        getWidgetState(str, null, 0).setKeyCycle(typedBundle);
    }

    public void addKeyPosition(String str, TypedBundle typedBundle) {
        getWidgetState(str, null, 0).setKeyPosition(typedBundle);
    }

    public void clear() {
        this.state.clear();
    }

    public boolean contains(String str) {
        return this.state.containsKey(str);
    }

    public void fillKeyPositions(WidgetFrame widgetFrame, float[] fArr, float[] fArr2, float[] fArr3) {
        KeyPosition keyPosition;
        int i2 = 0;
        for (int i3 = 0; i3 <= 100; i3++) {
            HashMap map = (HashMap) this.f2843a.get(Integer.valueOf(i3));
            if (map != null && (keyPosition = (KeyPosition) map.get(widgetFrame.widget.stringId)) != null) {
                fArr[i2] = keyPosition.f2848d;
                fArr2[i2] = keyPosition.f2849e;
                fArr3[i2] = keyPosition.f2845a;
                i2++;
            }
        }
    }

    public KeyPosition findNextPosition(String str, int i2) {
        KeyPosition keyPosition;
        while (i2 <= 100) {
            HashMap map = (HashMap) this.f2843a.get(Integer.valueOf(i2));
            if (map != null && (keyPosition = (KeyPosition) map.get(str)) != null) {
                return keyPosition;
            }
            i2++;
        }
        return null;
    }

    public KeyPosition findPreviousPosition(String str, int i2) {
        KeyPosition keyPosition;
        while (i2 >= 0) {
            HashMap map = (HashMap) this.f2843a.get(Integer.valueOf(i2));
            if (map != null && (keyPosition = (KeyPosition) map.get(str)) != null) {
                return keyPosition;
            }
            i2--;
        }
        return null;
    }

    public int getAutoTransition() {
        return this.mAutoTransition;
    }

    public WidgetFrame getEnd(String str) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            return null;
        }
        return widgetState.f2851b;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        return 0;
    }

    public WidgetFrame getInterpolated(String str) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            return null;
        }
        return widgetState.f2852c;
    }

    public int getKeyFrames(String str, float[] fArr, int[] iArr, int[] iArr2) {
        return this.state.get(str).f2853d.buildKeyFrames(fArr, iArr, iArr2);
    }

    public Motion getMotion(String str) {
        return getWidgetState(str, null, 0).f2853d;
    }

    public int getNumberKeyPositions(WidgetFrame widgetFrame) {
        int i2 = 0;
        for (int i3 = 0; i3 <= 100; i3++) {
            HashMap map = (HashMap) this.f2843a.get(Integer.valueOf(i3));
            if (map != null && ((KeyPosition) map.get(widgetFrame.widget.stringId)) != null) {
                i2++;
            }
        }
        return i2;
    }

    public float[] getPath(String str) {
        float[] fArr = new float[124];
        this.state.get(str).f2853d.buildPath(fArr, 62);
        return fArr;
    }

    public WidgetFrame getStart(String str) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            return null;
        }
        return widgetState.f2850a;
    }

    public boolean hasPositionKeyframes() {
        return this.f2843a.size() > 0;
    }

    public void interpolate(int i2, int i3, float f2) {
        Easing easing = this.mEasing;
        if (easing != null) {
            f2 = (float) easing.get(f2);
        }
        Iterator<String> it = this.state.keySet().iterator();
        while (it.hasNext()) {
            this.state.get(it.next()).interpolate(i2, i3, f2, this);
        }
    }

    public boolean isEmpty() {
        return this.state.isEmpty();
    }

    public void setTransitionProperties(TypedBundle typedBundle) {
        typedBundle.applyDelta(this.f2844b);
        typedBundle.applyDelta(this);
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, int i3) {
        return false;
    }

    public void updateFrom(ConstraintWidgetContainer constraintWidgetContainer, int i2) {
        ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
        int size = children.size();
        for (int i3 = 0; i3 < size; i3++) {
            ConstraintWidget constraintWidget = children.get(i3);
            getWidgetState(constraintWidget.stringId, null, i2).update(constraintWidget, i2);
        }
    }

    private WidgetState getWidgetState(String str, ConstraintWidget constraintWidget, int i2) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            widgetState = new WidgetState();
            this.f2844b.applyDelta(widgetState.f2853d);
            this.state.put(str, widgetState);
            if (constraintWidget != null) {
                widgetState.update(constraintWidget, i2);
            }
        }
        return widgetState;
    }

    public void addKeyPosition(String str, int i2, int i3, float f2, float f3) {
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.add(TypedValues.PositionType.TYPE_POSITION_TYPE, 2);
        typedBundle.add(100, i2);
        typedBundle.add(506, f2);
        typedBundle.add(TypedValues.PositionType.TYPE_PERCENT_Y, f3);
        getWidgetState(str, null, 0).setKeyPosition(typedBundle);
        KeyPosition keyPosition = new KeyPosition(str, i2, i3, f2, f3);
        HashMap map = (HashMap) this.f2843a.get(Integer.valueOf(i2));
        if (map == null) {
            map = new HashMap();
            this.f2843a.put(Integer.valueOf(i2), map);
        }
        map.put(str, keyPosition);
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, boolean z2) {
        return false;
    }

    public WidgetFrame getEnd(ConstraintWidget constraintWidget) {
        return getWidgetState(constraintWidget.stringId, null, 1).f2851b;
    }

    public WidgetFrame getInterpolated(ConstraintWidget constraintWidget) {
        return getWidgetState(constraintWidget.stringId, null, 2).f2852c;
    }

    public WidgetFrame getStart(ConstraintWidget constraintWidget) {
        return getWidgetState(constraintWidget.stringId, null, 0).f2850a;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, float f2) {
        if (i2 != 706) {
            return false;
        }
        this.mStagger = f2;
        return false;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, String str) {
        if (i2 != 705) {
            return false;
        }
        this.mDefaultInterpolatorString = str;
        this.mEasing = Easing.getInterpolator(str);
        return false;
    }

    public Interpolator getInterpolator() {
        return getInterpolator(this.mDefaultInterpolator, this.mDefaultInterpolatorString);
    }
}
