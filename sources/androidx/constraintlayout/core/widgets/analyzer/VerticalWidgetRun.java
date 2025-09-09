package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;

/* loaded from: classes.dex */
public class VerticalWidgetRun extends WidgetRun {
    public DependencyNode baseline;

    /* renamed from: g, reason: collision with root package name */
    DimensionDependency f2939g;

    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2940a;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            f2940a = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2940a[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2940a[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public VerticalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        DependencyNode dependencyNode = new DependencyNode(this);
        this.baseline = dependencyNode;
        this.f2939g = null;
        this.start.f2927b = DependencyNode.Type.TOP;
        this.end.f2927b = DependencyNode.Type.BOTTOM;
        dependencyNode.f2927b = DependencyNode.Type.BASELINE;
        this.orientation = 1;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.f2955a.setY(dependencyNode.value);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void c() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        ConstraintWidget constraintWidget = this.f2955a;
        if (constraintWidget.measured) {
            this.f2958d.resolve(constraintWidget.getHeight());
        }
        if (!this.f2958d.resolved) {
            this.f2957c = this.f2955a.getVerticalDimensionBehaviour();
            if (this.f2955a.hasBaseline()) {
                this.f2939g = new BaselineDimensionDependency(this);
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.f2957c;
            if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent2 = this.f2955a.getParent()) != null && parent2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
                    int height = (parent2.getHeight() - this.f2955a.mTop.getMargin()) - this.f2955a.mBottom.getMargin();
                    a(this.start, parent2.verticalRun.start, this.f2955a.mTop.getMargin());
                    a(this.end, parent2.verticalRun.end, -this.f2955a.mBottom.getMargin());
                    this.f2958d.resolve(height);
                    return;
                }
                if (this.f2957c == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.f2958d.resolve(this.f2955a.getHeight());
                }
            }
        } else if (this.f2957c == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent = this.f2955a.getParent()) != null && parent.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
            a(this.start, parent.verticalRun.start, this.f2955a.mTop.getMargin());
            a(this.end, parent.verticalRun.end, -this.f2955a.mBottom.getMargin());
            return;
        }
        DimensionDependency dimensionDependency = this.f2958d;
        boolean z2 = dimensionDependency.resolved;
        if (z2) {
            ConstraintWidget constraintWidget2 = this.f2955a;
            if (constraintWidget2.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget2.mListAnchors;
                ConstraintAnchor constraintAnchor = constraintAnchorArr[2];
                ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
                if (constraintAnchor2 != null && constraintAnchorArr[3].mTarget != null) {
                    if (constraintWidget2.isInVerticalChain()) {
                        this.start.f2928c = this.f2955a.mListAnchors[2].getMargin();
                        this.end.f2928c = -this.f2955a.mListAnchors[3].getMargin();
                    } else {
                        DependencyNode dependencyNodeF = f(this.f2955a.mListAnchors[2]);
                        if (dependencyNodeF != null) {
                            a(this.start, dependencyNodeF, this.f2955a.mListAnchors[2].getMargin());
                        }
                        DependencyNode dependencyNodeF2 = f(this.f2955a.mListAnchors[3]);
                        if (dependencyNodeF2 != null) {
                            a(this.end, dependencyNodeF2, -this.f2955a.mListAnchors[3].getMargin());
                        }
                        this.start.delegateToWidgetRun = true;
                        this.end.delegateToWidgetRun = true;
                    }
                    if (this.f2955a.hasBaseline()) {
                        a(this.baseline, this.start, this.f2955a.getBaselineDistance());
                        return;
                    }
                    return;
                }
                if (constraintAnchor2 != null) {
                    DependencyNode dependencyNodeF3 = f(constraintAnchor);
                    if (dependencyNodeF3 != null) {
                        a(this.start, dependencyNodeF3, this.f2955a.mListAnchors[2].getMargin());
                        a(this.end, this.start, this.f2958d.value);
                        if (this.f2955a.hasBaseline()) {
                            a(this.baseline, this.start, this.f2955a.getBaselineDistance());
                            return;
                        }
                        return;
                    }
                    return;
                }
                ConstraintAnchor constraintAnchor3 = constraintAnchorArr[3];
                if (constraintAnchor3.mTarget != null) {
                    DependencyNode dependencyNodeF4 = f(constraintAnchor3);
                    if (dependencyNodeF4 != null) {
                        a(this.end, dependencyNodeF4, -this.f2955a.mListAnchors[3].getMargin());
                        a(this.start, this.end, -this.f2958d.value);
                    }
                    if (this.f2955a.hasBaseline()) {
                        a(this.baseline, this.start, this.f2955a.getBaselineDistance());
                        return;
                    }
                    return;
                }
                ConstraintAnchor constraintAnchor4 = constraintAnchorArr[4];
                if (constraintAnchor4.mTarget != null) {
                    DependencyNode dependencyNodeF5 = f(constraintAnchor4);
                    if (dependencyNodeF5 != null) {
                        a(this.baseline, dependencyNodeF5, 0);
                        a(this.start, this.baseline, -this.f2955a.getBaselineDistance());
                        a(this.end, this.start, this.f2958d.value);
                        return;
                    }
                    return;
                }
                if ((constraintWidget2 instanceof Helper) || constraintWidget2.getParent() == null || this.f2955a.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                    return;
                }
                a(this.start, this.f2955a.getParent().verticalRun.start, this.f2955a.getY());
                a(this.end, this.start, this.f2958d.value);
                if (this.f2955a.hasBaseline()) {
                    a(this.baseline, this.start, this.f2955a.getBaselineDistance());
                    return;
                }
                return;
            }
        }
        if (z2 || this.f2957c != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            dimensionDependency.addDependency(this);
        } else {
            ConstraintWidget constraintWidget3 = this.f2955a;
            int i2 = constraintWidget3.mMatchConstraintDefaultHeight;
            if (i2 == 2) {
                ConstraintWidget parent3 = constraintWidget3.getParent();
                if (parent3 != null) {
                    DimensionDependency dimensionDependency2 = parent3.verticalRun.f2958d;
                    this.f2958d.f2932g.add(dimensionDependency2);
                    dimensionDependency2.f2931f.add(this.f2958d);
                    DimensionDependency dimensionDependency3 = this.f2958d;
                    dimensionDependency3.delegateToWidgetRun = true;
                    dimensionDependency3.f2931f.add(this.start);
                    this.f2958d.f2931f.add(this.end);
                }
            } else if (i2 == 3 && !constraintWidget3.isInVerticalChain()) {
                ConstraintWidget constraintWidget4 = this.f2955a;
                if (constraintWidget4.mMatchConstraintDefaultWidth != 3) {
                    DimensionDependency dimensionDependency4 = constraintWidget4.horizontalRun.f2958d;
                    this.f2958d.f2932g.add(dimensionDependency4);
                    dimensionDependency4.f2931f.add(this.f2958d);
                    DimensionDependency dimensionDependency5 = this.f2958d;
                    dimensionDependency5.delegateToWidgetRun = true;
                    dimensionDependency5.f2931f.add(this.start);
                    this.f2958d.f2931f.add(this.end);
                }
            }
        }
        ConstraintWidget constraintWidget5 = this.f2955a;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget5.mListAnchors;
        ConstraintAnchor constraintAnchor5 = constraintAnchorArr2[2];
        ConstraintAnchor constraintAnchor6 = constraintAnchor5.mTarget;
        if (constraintAnchor6 != null && constraintAnchorArr2[3].mTarget != null) {
            if (constraintWidget5.isInVerticalChain()) {
                this.start.f2928c = this.f2955a.mListAnchors[2].getMargin();
                this.end.f2928c = -this.f2955a.mListAnchors[3].getMargin();
            } else {
                DependencyNode dependencyNodeF6 = f(this.f2955a.mListAnchors[2]);
                DependencyNode dependencyNodeF7 = f(this.f2955a.mListAnchors[3]);
                if (dependencyNodeF6 != null) {
                    dependencyNodeF6.addDependency(this);
                }
                if (dependencyNodeF7 != null) {
                    dependencyNodeF7.addDependency(this);
                }
                this.f2960f = WidgetRun.RunType.CENTER;
            }
            if (this.f2955a.hasBaseline()) {
                b(this.baseline, this.start, 1, this.f2939g);
            }
        } else if (constraintAnchor6 != null) {
            DependencyNode dependencyNodeF8 = f(constraintAnchor5);
            if (dependencyNodeF8 != null) {
                a(this.start, dependencyNodeF8, this.f2955a.mListAnchors[2].getMargin());
                b(this.end, this.start, 1, this.f2958d);
                if (this.f2955a.hasBaseline()) {
                    b(this.baseline, this.start, 1, this.f2939g);
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.f2957c;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour2 == dimensionBehaviour3 && this.f2955a.getDimensionRatio() > 0.0f) {
                    HorizontalWidgetRun horizontalWidgetRun = this.f2955a.horizontalRun;
                    if (horizontalWidgetRun.f2957c == dimensionBehaviour3) {
                        horizontalWidgetRun.f2958d.f2931f.add(this.f2958d);
                        this.f2958d.f2932g.add(this.f2955a.horizontalRun.f2958d);
                        this.f2958d.updateDelegate = this;
                    }
                }
            }
        } else {
            ConstraintAnchor constraintAnchor7 = constraintAnchorArr2[3];
            if (constraintAnchor7.mTarget != null) {
                DependencyNode dependencyNodeF9 = f(constraintAnchor7);
                if (dependencyNodeF9 != null) {
                    a(this.end, dependencyNodeF9, -this.f2955a.mListAnchors[3].getMargin());
                    b(this.start, this.end, -1, this.f2958d);
                    if (this.f2955a.hasBaseline()) {
                        b(this.baseline, this.start, 1, this.f2939g);
                    }
                }
            } else {
                ConstraintAnchor constraintAnchor8 = constraintAnchorArr2[4];
                if (constraintAnchor8.mTarget != null) {
                    DependencyNode dependencyNodeF10 = f(constraintAnchor8);
                    if (dependencyNodeF10 != null) {
                        a(this.baseline, dependencyNodeF10, 0);
                        b(this.start, this.baseline, -1, this.f2939g);
                        b(this.end, this.start, 1, this.f2958d);
                    }
                } else if (!(constraintWidget5 instanceof Helper) && constraintWidget5.getParent() != null) {
                    a(this.start, this.f2955a.getParent().verticalRun.start, this.f2955a.getY());
                    b(this.end, this.start, 1, this.f2958d);
                    if (this.f2955a.hasBaseline()) {
                        b(this.baseline, this.start, 1, this.f2939g);
                    }
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = this.f2957c;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (dimensionBehaviour4 == dimensionBehaviour5 && this.f2955a.getDimensionRatio() > 0.0f) {
                        HorizontalWidgetRun horizontalWidgetRun2 = this.f2955a.horizontalRun;
                        if (horizontalWidgetRun2.f2957c == dimensionBehaviour5) {
                            horizontalWidgetRun2.f2958d.f2931f.add(this.f2958d);
                            this.f2958d.f2932g.add(this.f2955a.horizontalRun.f2958d);
                            this.f2958d.updateDelegate = this;
                        }
                    }
                }
            }
        }
        if (this.f2958d.f2932g.size() == 0) {
            this.f2958d.readyToSolve = true;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void d() {
        this.f2956b = null;
        this.start.clear();
        this.end.clear();
        this.baseline.clear();
        this.f2958d.clear();
        this.f2959e = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    boolean h() {
        return this.f2957c != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.f2955a.mMatchConstraintDefaultHeight == 0;
    }

    void l() {
        this.f2959e = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.baseline.clear();
        this.baseline.resolved = false;
        this.f2958d.resolved = false;
    }

    public String toString() {
        return "VerticalRun " + this.f2955a.getDebugName();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        float f2;
        float dimensionRatio;
        float dimensionRatio2;
        int i2;
        int i3 = AnonymousClass1.f2940a[this.f2960f.ordinal()];
        if (i3 == 1) {
            k(dependency);
        } else if (i3 == 2) {
            j(dependency);
        } else if (i3 == 3) {
            ConstraintWidget constraintWidget = this.f2955a;
            i(dependency, constraintWidget.mTop, constraintWidget.mBottom, 1);
            return;
        }
        DimensionDependency dimensionDependency = this.f2958d;
        if (dimensionDependency.readyToSolve && !dimensionDependency.resolved && this.f2957c == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget2 = this.f2955a;
            int i4 = constraintWidget2.mMatchConstraintDefaultHeight;
            if (i4 == 2) {
                ConstraintWidget parent = constraintWidget2.getParent();
                if (parent != null) {
                    if (parent.verticalRun.f2958d.resolved) {
                        this.f2958d.resolve((int) ((r7.value * this.f2955a.mMatchConstraintPercentHeight) + 0.5f));
                    }
                }
            } else if (i4 == 3 && constraintWidget2.horizontalRun.f2958d.resolved) {
                int dimensionRatioSide = constraintWidget2.getDimensionRatioSide();
                if (dimensionRatioSide == -1) {
                    ConstraintWidget constraintWidget3 = this.f2955a;
                    f2 = constraintWidget3.horizontalRun.f2958d.value;
                    dimensionRatio = constraintWidget3.getDimensionRatio();
                } else if (dimensionRatioSide == 0) {
                    dimensionRatio2 = r7.horizontalRun.f2958d.value * this.f2955a.getDimensionRatio();
                    i2 = (int) (dimensionRatio2 + 0.5f);
                    this.f2958d.resolve(i2);
                } else if (dimensionRatioSide != 1) {
                    i2 = 0;
                    this.f2958d.resolve(i2);
                } else {
                    ConstraintWidget constraintWidget4 = this.f2955a;
                    f2 = constraintWidget4.horizontalRun.f2958d.value;
                    dimensionRatio = constraintWidget4.getDimensionRatio();
                }
                dimensionRatio2 = f2 / dimensionRatio;
                i2 = (int) (dimensionRatio2 + 0.5f);
                this.f2958d.resolve(i2);
            }
        }
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.readyToSolve) {
            DependencyNode dependencyNode2 = this.end;
            if (dependencyNode2.readyToSolve) {
                if (dependencyNode.resolved && dependencyNode2.resolved && this.f2958d.resolved) {
                    return;
                }
                if (!this.f2958d.resolved && this.f2957c == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    ConstraintWidget constraintWidget5 = this.f2955a;
                    if (constraintWidget5.mMatchConstraintDefaultWidth == 0 && !constraintWidget5.isInVerticalChain()) {
                        DependencyNode dependencyNode3 = (DependencyNode) this.start.f2932g.get(0);
                        DependencyNode dependencyNode4 = (DependencyNode) this.end.f2932g.get(0);
                        int i5 = dependencyNode3.value;
                        DependencyNode dependencyNode5 = this.start;
                        int i6 = i5 + dependencyNode5.f2928c;
                        int i7 = dependencyNode4.value + this.end.f2928c;
                        dependencyNode5.resolve(i6);
                        this.end.resolve(i7);
                        this.f2958d.resolve(i7 - i6);
                        return;
                    }
                }
                if (!this.f2958d.resolved && this.f2957c == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.f2932g.size() > 0 && this.end.f2932g.size() > 0) {
                    DependencyNode dependencyNode6 = (DependencyNode) this.start.f2932g.get(0);
                    int i8 = (((DependencyNode) this.end.f2932g.get(0)).value + this.end.f2928c) - (dependencyNode6.value + this.start.f2928c);
                    DimensionDependency dimensionDependency2 = this.f2958d;
                    int i9 = dimensionDependency2.wrapValue;
                    if (i8 < i9) {
                        dimensionDependency2.resolve(i8);
                    } else {
                        dimensionDependency2.resolve(i9);
                    }
                }
                if (this.f2958d.resolved && this.start.f2932g.size() > 0 && this.end.f2932g.size() > 0) {
                    DependencyNode dependencyNode7 = (DependencyNode) this.start.f2932g.get(0);
                    DependencyNode dependencyNode8 = (DependencyNode) this.end.f2932g.get(0);
                    int i10 = dependencyNode7.value + this.start.f2928c;
                    int i11 = dependencyNode8.value + this.end.f2928c;
                    float verticalBiasPercent = this.f2955a.getVerticalBiasPercent();
                    if (dependencyNode7 == dependencyNode8) {
                        i10 = dependencyNode7.value;
                        i11 = dependencyNode8.value;
                        verticalBiasPercent = 0.5f;
                    }
                    this.start.resolve((int) (i10 + 0.5f + (((i11 - i10) - this.f2958d.value) * verticalBiasPercent)));
                    this.end.resolve(this.start.value + this.f2958d.value);
                }
            }
        }
    }
}
