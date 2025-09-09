package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.state.helpers.Facade;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class ConstraintReference implements Reference {

    /* renamed from: a, reason: collision with root package name */
    final State f2800a;

    /* renamed from: a0, reason: collision with root package name */
    Dimension f2801a0;

    /* renamed from: b0, reason: collision with root package name */
    Dimension f2803b0;
    private Object key;
    private float mCircularAngle;
    private float mCircularDistance;
    private ConstraintWidget mConstraintWidget;
    private HashMap<String, Integer> mCustomColors;
    private HashMap<String, Float> mCustomFloats;
    private Object mView;

    /* renamed from: b, reason: collision with root package name */
    String f2802b = null;

    /* renamed from: c, reason: collision with root package name */
    Facade f2804c = null;

    /* renamed from: d, reason: collision with root package name */
    int f2805d = 0;

    /* renamed from: e, reason: collision with root package name */
    int f2806e = 0;

    /* renamed from: f, reason: collision with root package name */
    float f2807f = -1.0f;

    /* renamed from: g, reason: collision with root package name */
    float f2808g = -1.0f;

    /* renamed from: h, reason: collision with root package name */
    float f2809h = 0.5f;

    /* renamed from: i, reason: collision with root package name */
    float f2810i = 0.5f;

    /* renamed from: j, reason: collision with root package name */
    protected int f2811j = 0;

    /* renamed from: k, reason: collision with root package name */
    protected int f2812k = 0;

    /* renamed from: l, reason: collision with root package name */
    protected int f2813l = 0;

    /* renamed from: m, reason: collision with root package name */
    protected int f2814m = 0;

    /* renamed from: n, reason: collision with root package name */
    protected int f2815n = 0;

    /* renamed from: o, reason: collision with root package name */
    protected int f2816o = 0;

    /* renamed from: p, reason: collision with root package name */
    protected int f2817p = 0;

    /* renamed from: q, reason: collision with root package name */
    protected int f2818q = 0;

    /* renamed from: r, reason: collision with root package name */
    protected int f2819r = 0;

    /* renamed from: s, reason: collision with root package name */
    protected int f2820s = 0;

    /* renamed from: t, reason: collision with root package name */
    protected int f2821t = 0;

    /* renamed from: u, reason: collision with root package name */
    protected int f2822u = 0;

    /* renamed from: v, reason: collision with root package name */
    int f2823v = 0;

    /* renamed from: w, reason: collision with root package name */
    int f2824w = 0;

    /* renamed from: x, reason: collision with root package name */
    float f2825x = Float.NaN;

    /* renamed from: y, reason: collision with root package name */
    float f2826y = Float.NaN;

    /* renamed from: z, reason: collision with root package name */
    float f2827z = Float.NaN;
    float A = Float.NaN;
    float B = Float.NaN;
    float C = Float.NaN;
    float D = Float.NaN;
    float E = Float.NaN;
    float F = Float.NaN;
    float G = Float.NaN;
    float H = Float.NaN;
    int I = 0;
    protected Object J = null;
    protected Object K = null;
    protected Object L = null;
    protected Object M = null;
    protected Object N = null;
    protected Object O = null;
    protected Object P = null;
    protected Object Q = null;
    protected Object R = null;
    protected Object S = null;
    protected Object T = null;
    protected Object U = null;
    Object V = null;
    Object W = null;
    Object X = null;
    Object Y = null;
    State.Constraint Z = null;

    /* renamed from: androidx.constraintlayout.core.state.ConstraintReference$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2828a;

        static {
            int[] iArr = new int[State.Constraint.values().length];
            f2828a = iArr;
            try {
                iArr[State.Constraint.LEFT_TO_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2828a[State.Constraint.LEFT_TO_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2828a[State.Constraint.RIGHT_TO_LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2828a[State.Constraint.RIGHT_TO_RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f2828a[State.Constraint.START_TO_START.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f2828a[State.Constraint.START_TO_END.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f2828a[State.Constraint.END_TO_START.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f2828a[State.Constraint.END_TO_END.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f2828a[State.Constraint.TOP_TO_TOP.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f2828a[State.Constraint.TOP_TO_BOTTOM.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f2828a[State.Constraint.BOTTOM_TO_TOP.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f2828a[State.Constraint.BOTTOM_TO_BOTTOM.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f2828a[State.Constraint.BASELINE_TO_BOTTOM.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f2828a[State.Constraint.BASELINE_TO_TOP.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f2828a[State.Constraint.BASELINE_TO_BASELINE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f2828a[State.Constraint.CIRCULAR_CONSTRAINT.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f2828a[State.Constraint.CENTER_HORIZONTALLY.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f2828a[State.Constraint.CENTER_VERTICALLY.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    public interface ConstraintReferenceFactory {
        ConstraintReference create(State state);
    }

    static class IncorrectConstraintException extends Exception {
        private final ArrayList<String> mErrors;

        public IncorrectConstraintException(ArrayList<String> arrayList) {
            this.mErrors = arrayList;
        }

        public ArrayList<String> getErrors() {
            return this.mErrors;
        }

        @Override // java.lang.Throwable
        public String toString() {
            return "IncorrectConstraintException: " + this.mErrors.toString();
        }
    }

    public ConstraintReference(State state) {
        Object obj = Dimension.WRAP_DIMENSION;
        this.f2801a0 = Dimension.Fixed(obj);
        this.f2803b0 = Dimension.Fixed(obj);
        this.mCustomColors = new HashMap<>();
        this.mCustomFloats = new HashMap<>();
        this.f2800a = state;
    }

    private void applyConnection(ConstraintWidget constraintWidget, Object obj, State.Constraint constraint) {
        ConstraintWidget target = getTarget(obj);
        if (target == null) {
        }
        int[] iArr = AnonymousClass1.f2828a;
        int i2 = iArr[constraint.ordinal()];
        switch (iArr[constraint.ordinal()]) {
            case 1:
                ConstraintAnchor.Type type = ConstraintAnchor.Type.LEFT;
                constraintWidget.getAnchor(type).connect(target.getAnchor(type), this.f2811j, this.f2817p, false);
                break;
            case 2:
                constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(target.getAnchor(ConstraintAnchor.Type.RIGHT), this.f2811j, this.f2817p, false);
                break;
            case 3:
                constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(target.getAnchor(ConstraintAnchor.Type.LEFT), this.f2812k, this.f2818q, false);
                break;
            case 4:
                ConstraintAnchor.Type type2 = ConstraintAnchor.Type.RIGHT;
                constraintWidget.getAnchor(type2).connect(target.getAnchor(type2), this.f2812k, this.f2818q, false);
                break;
            case 5:
                ConstraintAnchor.Type type3 = ConstraintAnchor.Type.LEFT;
                constraintWidget.getAnchor(type3).connect(target.getAnchor(type3), this.f2813l, this.f2819r, false);
                break;
            case 6:
                constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(target.getAnchor(ConstraintAnchor.Type.RIGHT), this.f2813l, this.f2819r, false);
                break;
            case 7:
                constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(target.getAnchor(ConstraintAnchor.Type.LEFT), this.f2814m, this.f2820s, false);
                break;
            case 8:
                ConstraintAnchor.Type type4 = ConstraintAnchor.Type.RIGHT;
                constraintWidget.getAnchor(type4).connect(target.getAnchor(type4), this.f2814m, this.f2820s, false);
                break;
            case 9:
                ConstraintAnchor.Type type5 = ConstraintAnchor.Type.TOP;
                constraintWidget.getAnchor(type5).connect(target.getAnchor(type5), this.f2815n, this.f2821t, false);
                break;
            case 10:
                constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).connect(target.getAnchor(ConstraintAnchor.Type.BOTTOM), this.f2815n, this.f2821t, false);
                break;
            case 11:
                constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(target.getAnchor(ConstraintAnchor.Type.TOP), this.f2816o, this.f2822u, false);
                break;
            case 12:
                ConstraintAnchor.Type type6 = ConstraintAnchor.Type.BOTTOM;
                constraintWidget.getAnchor(type6).connect(target.getAnchor(type6), this.f2816o, this.f2822u, false);
                break;
            case 13:
                constraintWidget.immediateConnect(ConstraintAnchor.Type.BASELINE, target, ConstraintAnchor.Type.BOTTOM, this.f2823v, this.f2824w);
                break;
            case 14:
                constraintWidget.immediateConnect(ConstraintAnchor.Type.BASELINE, target, ConstraintAnchor.Type.TOP, this.f2823v, this.f2824w);
                break;
            case 15:
                ConstraintAnchor.Type type7 = ConstraintAnchor.Type.BASELINE;
                constraintWidget.immediateConnect(type7, target, type7, this.f2823v, this.f2824w);
                break;
            case 16:
                constraintWidget.connectCircularConstraint(target, this.mCircularAngle, (int) this.mCircularDistance);
                break;
        }
    }

    private void dereference() {
        this.J = get(this.J);
        this.K = get(this.K);
        this.L = get(this.L);
        this.M = get(this.M);
        this.N = get(this.N);
        this.O = get(this.O);
        this.P = get(this.P);
        this.Q = get(this.Q);
        this.R = get(this.R);
        this.S = get(this.S);
        this.T = get(this.T);
        this.U = get(this.U);
        this.V = get(this.V);
        this.W = get(this.W);
        this.X = get(this.X);
    }

    private Object get(Object obj) {
        if (obj == null) {
            return null;
        }
        return !(obj instanceof ConstraintReference) ? this.f2800a.a(obj) : obj;
    }

    private ConstraintWidget getTarget(Object obj) {
        if (obj instanceof Reference) {
            return ((Reference) obj).getConstraintWidget();
        }
        return null;
    }

    public void addCustomColor(String str, int i2) {
        this.mCustomColors.put(str, Integer.valueOf(i2));
    }

    public void addCustomFloat(String str, float f2) {
        if (this.mCustomFloats == null) {
            this.mCustomFloats = new HashMap<>();
        }
        this.mCustomFloats.put(str, Float.valueOf(f2));
    }

    public ConstraintReference alpha(float f2) {
        this.F = f2;
        return this;
    }

    @Override // androidx.constraintlayout.core.state.Reference
    public void apply() {
        if (this.mConstraintWidget == null) {
            return;
        }
        Facade facade = this.f2804c;
        if (facade != null) {
            facade.apply();
        }
        this.f2801a0.apply(this.f2800a, this.mConstraintWidget, 0);
        this.f2803b0.apply(this.f2800a, this.mConstraintWidget, 1);
        dereference();
        applyConnection(this.mConstraintWidget, this.J, State.Constraint.LEFT_TO_LEFT);
        applyConnection(this.mConstraintWidget, this.K, State.Constraint.LEFT_TO_RIGHT);
        applyConnection(this.mConstraintWidget, this.L, State.Constraint.RIGHT_TO_LEFT);
        applyConnection(this.mConstraintWidget, this.M, State.Constraint.RIGHT_TO_RIGHT);
        applyConnection(this.mConstraintWidget, this.N, State.Constraint.START_TO_START);
        applyConnection(this.mConstraintWidget, this.O, State.Constraint.START_TO_END);
        applyConnection(this.mConstraintWidget, this.P, State.Constraint.END_TO_START);
        applyConnection(this.mConstraintWidget, this.Q, State.Constraint.END_TO_END);
        applyConnection(this.mConstraintWidget, this.R, State.Constraint.TOP_TO_TOP);
        applyConnection(this.mConstraintWidget, this.S, State.Constraint.TOP_TO_BOTTOM);
        applyConnection(this.mConstraintWidget, this.T, State.Constraint.BOTTOM_TO_TOP);
        applyConnection(this.mConstraintWidget, this.U, State.Constraint.BOTTOM_TO_BOTTOM);
        applyConnection(this.mConstraintWidget, this.V, State.Constraint.BASELINE_TO_BASELINE);
        applyConnection(this.mConstraintWidget, this.W, State.Constraint.BASELINE_TO_TOP);
        applyConnection(this.mConstraintWidget, this.X, State.Constraint.BASELINE_TO_BOTTOM);
        applyConnection(this.mConstraintWidget, this.Y, State.Constraint.CIRCULAR_CONSTRAINT);
        int i2 = this.f2805d;
        if (i2 != 0) {
            this.mConstraintWidget.setHorizontalChainStyle(i2);
        }
        int i3 = this.f2806e;
        if (i3 != 0) {
            this.mConstraintWidget.setVerticalChainStyle(i3);
        }
        float f2 = this.f2807f;
        if (f2 != -1.0f) {
            this.mConstraintWidget.setHorizontalWeight(f2);
        }
        float f3 = this.f2808g;
        if (f3 != -1.0f) {
            this.mConstraintWidget.setVerticalWeight(f3);
        }
        this.mConstraintWidget.setHorizontalBiasPercent(this.f2809h);
        this.mConstraintWidget.setVerticalBiasPercent(this.f2810i);
        ConstraintWidget constraintWidget = this.mConstraintWidget;
        WidgetFrame widgetFrame = constraintWidget.frame;
        widgetFrame.pivotX = this.f2825x;
        widgetFrame.pivotY = this.f2826y;
        widgetFrame.rotationX = this.f2827z;
        widgetFrame.rotationY = this.A;
        widgetFrame.rotationZ = this.B;
        widgetFrame.translationX = this.C;
        widgetFrame.translationY = this.D;
        widgetFrame.translationZ = this.E;
        widgetFrame.scaleX = this.G;
        widgetFrame.scaleY = this.H;
        widgetFrame.alpha = this.F;
        int i4 = this.I;
        widgetFrame.visibility = i4;
        constraintWidget.setVisibility(i4);
        HashMap<String, Integer> map = this.mCustomColors;
        if (map != null) {
            for (String str : map.keySet()) {
                this.mConstraintWidget.frame.setCustomAttribute(str, TypedValues.Custom.TYPE_COLOR, this.mCustomColors.get(str).intValue());
            }
        }
        HashMap<String, Float> map2 = this.mCustomFloats;
        if (map2 != null) {
            for (String str2 : map2.keySet()) {
                this.mConstraintWidget.frame.setCustomAttribute(str2, TypedValues.Custom.TYPE_FLOAT, this.mCustomFloats.get(str2).floatValue());
            }
        }
    }

    public ConstraintReference baseline() {
        this.Z = State.Constraint.BASELINE_TO_BASELINE;
        return this;
    }

    public ConstraintReference baselineToBaseline(Object obj) {
        this.Z = State.Constraint.BASELINE_TO_BASELINE;
        this.V = obj;
        return this;
    }

    public ConstraintReference baselineToBottom(Object obj) {
        this.Z = State.Constraint.BASELINE_TO_BOTTOM;
        this.X = obj;
        return this;
    }

    public ConstraintReference baselineToTop(Object obj) {
        this.Z = State.Constraint.BASELINE_TO_TOP;
        this.W = obj;
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.constraintlayout.core.state.ConstraintReference bias(float r3) {
        /*
            r2 = this;
            androidx.constraintlayout.core.state.State$Constraint r0 = r2.Z
            if (r0 != 0) goto L5
            return r2
        L5:
            int[] r1 = androidx.constraintlayout.core.state.ConstraintReference.AnonymousClass1.f2828a
            int r0 = r0.ordinal()
            r0 = r1[r0]
            r1 = 17
            if (r0 == r1) goto L1c
            r1 = 18
            if (r0 == r1) goto L19
            switch(r0) {
                case 1: goto L1c;
                case 2: goto L1c;
                case 3: goto L1c;
                case 4: goto L1c;
                case 5: goto L1c;
                case 6: goto L1c;
                case 7: goto L1c;
                case 8: goto L1c;
                case 9: goto L19;
                case 10: goto L19;
                case 11: goto L19;
                case 12: goto L19;
                default: goto L18;
            }
        L18:
            goto L1e
        L19:
            r2.f2810i = r3
            goto L1e
        L1c:
            r2.f2809h = r3
        L1e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.ConstraintReference.bias(float):androidx.constraintlayout.core.state.ConstraintReference");
    }

    public ConstraintReference bottom() {
        if (this.T != null) {
            this.Z = State.Constraint.BOTTOM_TO_TOP;
        } else {
            this.Z = State.Constraint.BOTTOM_TO_BOTTOM;
        }
        return this;
    }

    public ConstraintReference bottomToBottom(Object obj) {
        this.Z = State.Constraint.BOTTOM_TO_BOTTOM;
        this.U = obj;
        return this;
    }

    public ConstraintReference bottomToTop(Object obj) {
        this.Z = State.Constraint.BOTTOM_TO_TOP;
        this.T = obj;
        return this;
    }

    public ConstraintReference centerHorizontally(Object obj) {
        Object obj2 = get(obj);
        this.N = obj2;
        this.Q = obj2;
        this.Z = State.Constraint.CENTER_HORIZONTALLY;
        this.f2809h = 0.5f;
        return this;
    }

    public ConstraintReference centerVertically(Object obj) {
        Object obj2 = get(obj);
        this.R = obj2;
        this.U = obj2;
        this.Z = State.Constraint.CENTER_VERTICALLY;
        this.f2810i = 0.5f;
        return this;
    }

    public ConstraintReference circularConstraint(Object obj, float f2, float f3) {
        this.Y = get(obj);
        this.mCircularAngle = f2;
        this.mCircularDistance = f3;
        this.Z = State.Constraint.CIRCULAR_CONSTRAINT;
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ConstraintReference clear() {
        State.Constraint constraint = this.Z;
        if (constraint != null) {
            switch (AnonymousClass1.f2828a[constraint.ordinal()]) {
                case 1:
                case 2:
                    this.J = null;
                    this.K = null;
                    this.f2811j = 0;
                    this.f2817p = 0;
                    break;
                case 3:
                case 4:
                    this.L = null;
                    this.M = null;
                    this.f2812k = 0;
                    this.f2818q = 0;
                    break;
                case 5:
                case 6:
                    this.N = null;
                    this.O = null;
                    this.f2813l = 0;
                    this.f2819r = 0;
                    break;
                case 7:
                case 8:
                    this.P = null;
                    this.Q = null;
                    this.f2814m = 0;
                    this.f2820s = 0;
                    break;
                case 9:
                case 10:
                    this.R = null;
                    this.S = null;
                    this.f2815n = 0;
                    this.f2821t = 0;
                    break;
                case 11:
                case 12:
                    this.T = null;
                    this.U = null;
                    this.f2816o = 0;
                    this.f2822u = 0;
                    break;
                case 15:
                    this.V = null;
                    break;
                case 16:
                    this.Y = null;
                    break;
            }
        } else {
            this.J = null;
            this.K = null;
            this.f2811j = 0;
            this.L = null;
            this.M = null;
            this.f2812k = 0;
            this.N = null;
            this.O = null;
            this.f2813l = 0;
            this.P = null;
            this.Q = null;
            this.f2814m = 0;
            this.R = null;
            this.S = null;
            this.f2815n = 0;
            this.T = null;
            this.U = null;
            this.f2816o = 0;
            this.V = null;
            this.Y = null;
            this.f2809h = 0.5f;
            this.f2810i = 0.5f;
            this.f2817p = 0;
            this.f2818q = 0;
            this.f2819r = 0;
            this.f2820s = 0;
            this.f2821t = 0;
            this.f2822u = 0;
        }
        return this;
    }

    public ConstraintReference clearHorizontal() {
        start().clear();
        end().clear();
        left().clear();
        right().clear();
        return this;
    }

    public ConstraintReference clearVertical() {
        top().clear();
        baseline().clear();
        bottom().clear();
        return this;
    }

    public ConstraintWidget createConstraintWidget() {
        return new ConstraintWidget(getWidth().a(), getHeight().a());
    }

    public ConstraintReference end() {
        if (this.P != null) {
            this.Z = State.Constraint.END_TO_START;
        } else {
            this.Z = State.Constraint.END_TO_END;
        }
        return this;
    }

    public ConstraintReference endToEnd(Object obj) {
        this.Z = State.Constraint.END_TO_END;
        this.Q = obj;
        return this;
    }

    public ConstraintReference endToStart(Object obj) {
        this.Z = State.Constraint.END_TO_START;
        this.P = obj;
        return this;
    }

    public float getAlpha() {
        return this.F;
    }

    @Override // androidx.constraintlayout.core.state.Reference
    public ConstraintWidget getConstraintWidget() {
        if (this.mConstraintWidget == null) {
            ConstraintWidget constraintWidgetCreateConstraintWidget = createConstraintWidget();
            this.mConstraintWidget = constraintWidgetCreateConstraintWidget;
            constraintWidgetCreateConstraintWidget.setCompanionWidget(this.mView);
        }
        return this.mConstraintWidget;
    }

    @Override // androidx.constraintlayout.core.state.Reference
    public Facade getFacade() {
        return this.f2804c;
    }

    public Dimension getHeight() {
        return this.f2803b0;
    }

    public int getHorizontalChainStyle() {
        return this.f2805d;
    }

    public float getHorizontalChainWeight() {
        return this.f2807f;
    }

    @Override // androidx.constraintlayout.core.state.Reference
    public Object getKey() {
        return this.key;
    }

    public float getPivotX() {
        return this.f2825x;
    }

    public float getPivotY() {
        return this.f2826y;
    }

    public float getRotationX() {
        return this.f2827z;
    }

    public float getRotationY() {
        return this.A;
    }

    public float getRotationZ() {
        return this.B;
    }

    public float getScaleX() {
        return this.G;
    }

    public float getScaleY() {
        return this.H;
    }

    public String getTag() {
        return this.f2802b;
    }

    public float getTranslationX() {
        return this.C;
    }

    public float getTranslationY() {
        return this.D;
    }

    public float getTranslationZ() {
        return this.E;
    }

    public int getVerticalChainStyle(int i2) {
        return this.f2806e;
    }

    public float getVerticalChainWeight() {
        return this.f2808g;
    }

    public Object getView() {
        return this.mView;
    }

    public Dimension getWidth() {
        return this.f2801a0;
    }

    public ConstraintReference height(Dimension dimension) {
        return setHeight(dimension);
    }

    public ConstraintReference horizontalBias(float f2) {
        this.f2809h = f2;
        return this;
    }

    public ConstraintReference left() {
        if (this.J != null) {
            this.Z = State.Constraint.LEFT_TO_LEFT;
        } else {
            this.Z = State.Constraint.LEFT_TO_RIGHT;
        }
        return this;
    }

    public ConstraintReference leftToLeft(Object obj) {
        this.Z = State.Constraint.LEFT_TO_LEFT;
        this.J = obj;
        return this;
    }

    public ConstraintReference leftToRight(Object obj) {
        this.Z = State.Constraint.LEFT_TO_RIGHT;
        this.K = obj;
        return this;
    }

    public ConstraintReference margin(Object obj) {
        return margin(this.f2800a.convertDimension(obj));
    }

    public ConstraintReference marginGone(Object obj) {
        return marginGone(this.f2800a.convertDimension(obj));
    }

    public ConstraintReference pivotX(float f2) {
        this.f2825x = f2;
        return this;
    }

    public ConstraintReference pivotY(float f2) {
        this.f2826y = f2;
        return this;
    }

    public ConstraintReference right() {
        if (this.L != null) {
            this.Z = State.Constraint.RIGHT_TO_LEFT;
        } else {
            this.Z = State.Constraint.RIGHT_TO_RIGHT;
        }
        return this;
    }

    public ConstraintReference rightToLeft(Object obj) {
        this.Z = State.Constraint.RIGHT_TO_LEFT;
        this.L = obj;
        return this;
    }

    public ConstraintReference rightToRight(Object obj) {
        this.Z = State.Constraint.RIGHT_TO_RIGHT;
        this.M = obj;
        return this;
    }

    public ConstraintReference rotationX(float f2) {
        this.f2827z = f2;
        return this;
    }

    public ConstraintReference rotationY(float f2) {
        this.A = f2;
        return this;
    }

    public ConstraintReference rotationZ(float f2) {
        this.B = f2;
        return this;
    }

    public ConstraintReference scaleX(float f2) {
        this.G = f2;
        return this;
    }

    public ConstraintReference scaleY(float f2) {
        this.H = f2;
        return this;
    }

    @Override // androidx.constraintlayout.core.state.Reference
    public void setConstraintWidget(ConstraintWidget constraintWidget) {
        if (constraintWidget == null) {
            return;
        }
        this.mConstraintWidget = constraintWidget;
        constraintWidget.setCompanionWidget(this.mView);
    }

    public void setFacade(Facade facade) {
        this.f2804c = facade;
        if (facade != null) {
            setConstraintWidget(facade.getConstraintWidget());
        }
    }

    public ConstraintReference setHeight(Dimension dimension) {
        this.f2803b0 = dimension;
        return this;
    }

    public void setHorizontalChainStyle(int i2) {
        this.f2805d = i2;
    }

    public void setHorizontalChainWeight(float f2) {
        this.f2807f = f2;
    }

    @Override // androidx.constraintlayout.core.state.Reference
    public void setKey(Object obj) {
        this.key = obj;
    }

    public void setTag(String str) {
        this.f2802b = str;
    }

    public void setVerticalChainStyle(int i2) {
        this.f2806e = i2;
    }

    public void setVerticalChainWeight(float f2) {
        this.f2808g = f2;
    }

    public void setView(Object obj) {
        this.mView = obj;
        ConstraintWidget constraintWidget = this.mConstraintWidget;
        if (constraintWidget != null) {
            constraintWidget.setCompanionWidget(obj);
        }
    }

    public ConstraintReference setWidth(Dimension dimension) {
        this.f2801a0 = dimension;
        return this;
    }

    public ConstraintReference start() {
        if (this.N != null) {
            this.Z = State.Constraint.START_TO_START;
        } else {
            this.Z = State.Constraint.START_TO_END;
        }
        return this;
    }

    public ConstraintReference startToEnd(Object obj) {
        this.Z = State.Constraint.START_TO_END;
        this.O = obj;
        return this;
    }

    public ConstraintReference startToStart(Object obj) {
        this.Z = State.Constraint.START_TO_START;
        this.N = obj;
        return this;
    }

    public ConstraintReference top() {
        if (this.R != null) {
            this.Z = State.Constraint.TOP_TO_TOP;
        } else {
            this.Z = State.Constraint.TOP_TO_BOTTOM;
        }
        return this;
    }

    public ConstraintReference topToBottom(Object obj) {
        this.Z = State.Constraint.TOP_TO_BOTTOM;
        this.S = obj;
        return this;
    }

    public ConstraintReference topToTop(Object obj) {
        this.Z = State.Constraint.TOP_TO_TOP;
        this.R = obj;
        return this;
    }

    public ConstraintReference translationX(float f2) {
        this.C = f2;
        return this;
    }

    public ConstraintReference translationY(float f2) {
        this.D = f2;
        return this;
    }

    public ConstraintReference translationZ(float f2) {
        this.E = f2;
        return this;
    }

    public void validate() throws IncorrectConstraintException {
        ArrayList arrayList = new ArrayList();
        if (this.J != null && this.K != null) {
            arrayList.add("LeftToLeft and LeftToRight both defined");
        }
        if (this.L != null && this.M != null) {
            arrayList.add("RightToLeft and RightToRight both defined");
        }
        if (this.N != null && this.O != null) {
            arrayList.add("StartToStart and StartToEnd both defined");
        }
        if (this.P != null && this.Q != null) {
            arrayList.add("EndToStart and EndToEnd both defined");
        }
        if ((this.J != null || this.K != null || this.L != null || this.M != null) && (this.N != null || this.O != null || this.P != null || this.Q != null)) {
            arrayList.add("Both left/right and start/end constraints defined");
        }
        if (arrayList.size() > 0) {
            throw new IncorrectConstraintException(arrayList);
        }
    }

    public ConstraintReference verticalBias(float f2) {
        this.f2810i = f2;
        return this;
    }

    public ConstraintReference visibility(int i2) {
        this.I = i2;
        return this;
    }

    public ConstraintReference width(Dimension dimension) {
        return setWidth(dimension);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ConstraintReference margin(int i2) {
        State.Constraint constraint = this.Z;
        if (constraint != null) {
            switch (AnonymousClass1.f2828a[constraint.ordinal()]) {
                case 1:
                case 2:
                    this.f2811j = i2;
                    break;
                case 3:
                case 4:
                    this.f2812k = i2;
                    break;
                case 5:
                case 6:
                    this.f2813l = i2;
                    break;
                case 7:
                case 8:
                    this.f2814m = i2;
                    break;
                case 9:
                case 10:
                    this.f2815n = i2;
                    break;
                case 11:
                case 12:
                    this.f2816o = i2;
                    break;
                case 13:
                case 14:
                case 15:
                    this.f2823v = i2;
                    break;
                case 16:
                    this.mCircularDistance = i2;
                    break;
            }
        } else {
            this.f2811j = i2;
            this.f2812k = i2;
            this.f2813l = i2;
            this.f2814m = i2;
            this.f2815n = i2;
            this.f2816o = i2;
        }
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ConstraintReference marginGone(int i2) {
        State.Constraint constraint = this.Z;
        if (constraint != null) {
            switch (AnonymousClass1.f2828a[constraint.ordinal()]) {
                case 1:
                case 2:
                    this.f2817p = i2;
                    break;
                case 3:
                case 4:
                    this.f2818q = i2;
                    break;
                case 5:
                case 6:
                    this.f2819r = i2;
                    break;
                case 7:
                case 8:
                    this.f2820s = i2;
                    break;
                case 9:
                case 10:
                    this.f2821t = i2;
                    break;
                case 11:
                case 12:
                    this.f2822u = i2;
                    break;
                case 13:
                case 14:
                case 15:
                    this.f2824w = i2;
                    break;
            }
        } else {
            this.f2817p = i2;
            this.f2818q = i2;
            this.f2819r = i2;
            this.f2820s = i2;
            this.f2821t = i2;
            this.f2822u = i2;
        }
        return this;
    }
}
