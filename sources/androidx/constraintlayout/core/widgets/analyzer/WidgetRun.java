package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;

/* loaded from: classes.dex */
public abstract class WidgetRun implements Dependency {

    /* renamed from: a, reason: collision with root package name */
    ConstraintWidget f2955a;

    /* renamed from: b, reason: collision with root package name */
    RunGroup f2956b;

    /* renamed from: c, reason: collision with root package name */
    protected ConstraintWidget.DimensionBehaviour f2957c;
    public int matchConstraintsType;

    /* renamed from: d, reason: collision with root package name */
    DimensionDependency f2958d = new DimensionDependency(this);
    public int orientation = 0;

    /* renamed from: e, reason: collision with root package name */
    boolean f2959e = false;
    public DependencyNode start = new DependencyNode(this);
    public DependencyNode end = new DependencyNode(this);

    /* renamed from: f, reason: collision with root package name */
    protected RunType f2960f = RunType.NONE;

    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.WidgetRun$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2961a;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            f2961a = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2961a[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2961a[ConstraintAnchor.Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2961a[ConstraintAnchor.Type.BASELINE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f2961a[ConstraintAnchor.Type.BOTTOM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    enum RunType {
        NONE,
        START,
        END,
        CENTER
    }

    public WidgetRun(ConstraintWidget constraintWidget) {
        this.f2955a = constraintWidget;
    }

    private void resolveDimension(int i2, int i3) {
        int i4 = this.matchConstraintsType;
        if (i4 == 0) {
            this.f2958d.resolve(e(i3, i2));
            return;
        }
        if (i4 == 1) {
            this.f2958d.resolve(Math.min(e(this.f2958d.wrapValue, i2), i3));
            return;
        }
        if (i4 == 2) {
            ConstraintWidget parent = this.f2955a.getParent();
            if (parent != null) {
                if ((i2 == 0 ? parent.horizontalRun : parent.verticalRun).f2958d.resolved) {
                    ConstraintWidget constraintWidget = this.f2955a;
                    this.f2958d.resolve(e((int) ((r9.value * (i2 == 0 ? constraintWidget.mMatchConstraintPercentWidth : constraintWidget.mMatchConstraintPercentHeight)) + 0.5f), i2));
                    return;
                }
                return;
            }
            return;
        }
        if (i4 != 3) {
            return;
        }
        ConstraintWidget constraintWidget2 = this.f2955a;
        WidgetRun widgetRun = constraintWidget2.horizontalRun;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = widgetRun.f2957c;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (dimensionBehaviour == dimensionBehaviour2 && widgetRun.matchConstraintsType == 3) {
            VerticalWidgetRun verticalWidgetRun = constraintWidget2.verticalRun;
            if (verticalWidgetRun.f2957c == dimensionBehaviour2 && verticalWidgetRun.matchConstraintsType == 3) {
                return;
            }
        }
        if (i2 == 0) {
            widgetRun = constraintWidget2.verticalRun;
        }
        if (widgetRun.f2958d.resolved) {
            float dimensionRatio = constraintWidget2.getDimensionRatio();
            this.f2958d.resolve(i2 == 1 ? (int) ((widgetRun.f2958d.value / dimensionRatio) + 0.5f) : (int) ((dimensionRatio * widgetRun.f2958d.value) + 0.5f));
        }
    }

    protected final void a(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i2) {
        dependencyNode.f2932g.add(dependencyNode2);
        dependencyNode.f2928c = i2;
        dependencyNode2.f2931f.add(dependencyNode);
    }

    abstract void applyToWidget();

    protected final void b(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i2, DimensionDependency dimensionDependency) {
        dependencyNode.f2932g.add(dependencyNode2);
        dependencyNode.f2932g.add(this.f2958d);
        dependencyNode.f2929d = i2;
        dependencyNode.f2930e = dimensionDependency;
        dependencyNode2.f2931f.add(dependencyNode);
        dimensionDependency.f2931f.add(dependencyNode);
    }

    abstract void c();

    abstract void d();

    protected final int e(int i2, int i3) {
        int iMax;
        if (i3 == 0) {
            ConstraintWidget constraintWidget = this.f2955a;
            int i4 = constraintWidget.mMatchConstraintMaxWidth;
            iMax = Math.max(constraintWidget.mMatchConstraintMinWidth, i2);
            if (i4 > 0) {
                iMax = Math.min(i4, i2);
            }
            if (iMax == i2) {
                return i2;
            }
        } else {
            ConstraintWidget constraintWidget2 = this.f2955a;
            int i5 = constraintWidget2.mMatchConstraintMaxHeight;
            iMax = Math.max(constraintWidget2.mMatchConstraintMinHeight, i2);
            if (i5 > 0) {
                iMax = Math.min(i5, i2);
            }
            if (iMax == i2) {
                return i2;
            }
        }
        return iMax;
    }

    protected final DependencyNode f(ConstraintAnchor constraintAnchor) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        int i2 = AnonymousClass1.f2961a[constraintAnchor2.mType.ordinal()];
        if (i2 == 1) {
            return constraintWidget.horizontalRun.start;
        }
        if (i2 == 2) {
            return constraintWidget.horizontalRun.end;
        }
        if (i2 == 3) {
            return constraintWidget.verticalRun.start;
        }
        if (i2 == 4) {
            return constraintWidget.verticalRun.baseline;
        }
        if (i2 != 5) {
            return null;
        }
        return constraintWidget.verticalRun.end;
    }

    protected final DependencyNode g(ConstraintAnchor constraintAnchor, int i2) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        WidgetRun widgetRun = i2 == 0 ? constraintWidget.horizontalRun : constraintWidget.verticalRun;
        int i3 = AnonymousClass1.f2961a[constraintAnchor2.mType.ordinal()];
        if (i3 != 1) {
            if (i3 != 2) {
                if (i3 != 3) {
                    if (i3 != 5) {
                        return null;
                    }
                }
            }
            return widgetRun.end;
        }
        return widgetRun.start;
    }

    public long getWrapDimension() {
        if (this.f2958d.resolved) {
            return r0.value;
        }
        return 0L;
    }

    abstract boolean h();

    protected void i(Dependency dependency, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i2) {
        DependencyNode dependencyNodeF = f(constraintAnchor);
        DependencyNode dependencyNodeF2 = f(constraintAnchor2);
        if (dependencyNodeF.resolved && dependencyNodeF2.resolved) {
            int margin = dependencyNodeF.value + constraintAnchor.getMargin();
            int margin2 = dependencyNodeF2.value - constraintAnchor2.getMargin();
            int i3 = margin2 - margin;
            if (!this.f2958d.resolved && this.f2957c == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                resolveDimension(i2, i3);
            }
            DimensionDependency dimensionDependency = this.f2958d;
            if (dimensionDependency.resolved) {
                if (dimensionDependency.value == i3) {
                    this.start.resolve(margin);
                    this.end.resolve(margin2);
                    return;
                }
                ConstraintWidget constraintWidget = this.f2955a;
                float horizontalBiasPercent = i2 == 0 ? constraintWidget.getHorizontalBiasPercent() : constraintWidget.getVerticalBiasPercent();
                if (dependencyNodeF == dependencyNodeF2) {
                    margin = dependencyNodeF.value;
                    margin2 = dependencyNodeF2.value;
                    horizontalBiasPercent = 0.5f;
                }
                this.start.resolve((int) (margin + 0.5f + (((margin2 - margin) - this.f2958d.value) * horizontalBiasPercent)));
                this.end.resolve(this.start.value + this.f2958d.value);
            }
        }
    }

    public boolean isCenterConnection() {
        int size = this.start.f2932g.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (((DependencyNode) this.start.f2932g.get(i3)).f2926a != this) {
                i2++;
            }
        }
        int size2 = this.end.f2932g.size();
        for (int i4 = 0; i4 < size2; i4++) {
            if (((DependencyNode) this.end.f2932g.get(i4)).f2926a != this) {
                i2++;
            }
        }
        return i2 >= 2;
    }

    public boolean isDimensionResolved() {
        return this.f2958d.resolved;
    }

    public boolean isResolved() {
        return this.f2959e;
    }

    protected void j(Dependency dependency) {
    }

    protected void k(Dependency dependency) {
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
    }

    public long wrapSize(int i2) {
        int i3;
        DimensionDependency dimensionDependency = this.f2958d;
        if (!dimensionDependency.resolved) {
            return 0L;
        }
        long j2 = dimensionDependency.value;
        if (isCenterConnection()) {
            i3 = this.start.f2928c - this.end.f2928c;
        } else {
            if (i2 != 0) {
                return j2 - this.end.f2928c;
            }
            i3 = this.start.f2928c;
        }
        return j2 + i3;
    }
}
