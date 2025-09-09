package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;

/* loaded from: classes.dex */
public class HorizontalWidgetRun extends WidgetRun {
    private static int[] tempDimensions = new int[2];

    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2933a;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            f2933a = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2933a[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2933a[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.f2927b = DependencyNode.Type.LEFT;
        this.end.f2927b = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    private void computeInsetRatio(int[] iArr, int i2, int i3, int i4, int i5, float f2, int i6) {
        int i7 = i3 - i2;
        int i8 = i5 - i4;
        if (i6 != -1) {
            if (i6 == 0) {
                iArr[0] = (int) ((i8 * f2) + 0.5f);
                iArr[1] = i8;
                return;
            } else {
                if (i6 != 1) {
                    return;
                }
                iArr[0] = i7;
                iArr[1] = (int) ((i7 * f2) + 0.5f);
                return;
            }
        }
        int i9 = (int) ((i8 * f2) + 0.5f);
        int i10 = (int) ((i7 / f2) + 0.5f);
        if (i9 <= i7) {
            iArr[0] = i9;
            iArr[1] = i8;
        } else if (i10 <= i8) {
            iArr[0] = i7;
            iArr[1] = i10;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.f2955a.setX(dependencyNode.value);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void c() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        ConstraintWidget constraintWidget = this.f2955a;
        if (constraintWidget.measured) {
            this.f2958d.resolve(constraintWidget.getWidth());
        }
        if (this.f2958d.resolved) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.f2957c;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
            if (dimensionBehaviour == dimensionBehaviour2 && (parent = this.f2955a.getParent()) != null && (parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent.getHorizontalDimensionBehaviour() == dimensionBehaviour2)) {
                a(this.start, parent.horizontalRun.start, this.f2955a.mLeft.getMargin());
                a(this.end, parent.horizontalRun.end, -this.f2955a.mRight.getMargin());
                return;
            }
        } else {
            ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = this.f2955a.getHorizontalDimensionBehaviour();
            this.f2957c = horizontalDimensionBehaviour;
            if (horizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                if (horizontalDimensionBehaviour == dimensionBehaviour3 && (parent2 = this.f2955a.getParent()) != null && (parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent2.getHorizontalDimensionBehaviour() == dimensionBehaviour3)) {
                    int width = (parent2.getWidth() - this.f2955a.mLeft.getMargin()) - this.f2955a.mRight.getMargin();
                    a(this.start, parent2.horizontalRun.start, this.f2955a.mLeft.getMargin());
                    a(this.end, parent2.horizontalRun.end, -this.f2955a.mRight.getMargin());
                    this.f2958d.resolve(width);
                    return;
                }
                if (this.f2957c == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.f2958d.resolve(this.f2955a.getWidth());
                }
            }
        }
        DimensionDependency dimensionDependency = this.f2958d;
        if (dimensionDependency.resolved) {
            ConstraintWidget constraintWidget2 = this.f2955a;
            if (constraintWidget2.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget2.mListAnchors;
                ConstraintAnchor constraintAnchor = constraintAnchorArr[0];
                ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
                if (constraintAnchor2 != null && constraintAnchorArr[1].mTarget != null) {
                    if (constraintWidget2.isInHorizontalChain()) {
                        this.start.f2928c = this.f2955a.mListAnchors[0].getMargin();
                        this.end.f2928c = -this.f2955a.mListAnchors[1].getMargin();
                        return;
                    }
                    DependencyNode dependencyNodeF = f(this.f2955a.mListAnchors[0]);
                    if (dependencyNodeF != null) {
                        a(this.start, dependencyNodeF, this.f2955a.mListAnchors[0].getMargin());
                    }
                    DependencyNode dependencyNodeF2 = f(this.f2955a.mListAnchors[1]);
                    if (dependencyNodeF2 != null) {
                        a(this.end, dependencyNodeF2, -this.f2955a.mListAnchors[1].getMargin());
                    }
                    this.start.delegateToWidgetRun = true;
                    this.end.delegateToWidgetRun = true;
                    return;
                }
                if (constraintAnchor2 != null) {
                    DependencyNode dependencyNodeF3 = f(constraintAnchor);
                    if (dependencyNodeF3 != null) {
                        a(this.start, dependencyNodeF3, this.f2955a.mListAnchors[0].getMargin());
                        a(this.end, this.start, this.f2958d.value);
                        return;
                    }
                    return;
                }
                ConstraintAnchor constraintAnchor3 = constraintAnchorArr[1];
                if (constraintAnchor3.mTarget != null) {
                    DependencyNode dependencyNodeF4 = f(constraintAnchor3);
                    if (dependencyNodeF4 != null) {
                        a(this.end, dependencyNodeF4, -this.f2955a.mListAnchors[1].getMargin());
                        a(this.start, this.end, -this.f2958d.value);
                        return;
                    }
                    return;
                }
                if ((constraintWidget2 instanceof Helper) || constraintWidget2.getParent() == null || this.f2955a.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                    return;
                }
                a(this.start, this.f2955a.getParent().horizontalRun.start, this.f2955a.getX());
                a(this.end, this.start, this.f2958d.value);
                return;
            }
        }
        if (this.f2957c == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget3 = this.f2955a;
            int i2 = constraintWidget3.mMatchConstraintDefaultWidth;
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
            } else if (i2 == 3) {
                if (constraintWidget3.mMatchConstraintDefaultHeight == 3) {
                    this.start.updateDelegate = this;
                    this.end.updateDelegate = this;
                    VerticalWidgetRun verticalWidgetRun = constraintWidget3.verticalRun;
                    verticalWidgetRun.start.updateDelegate = this;
                    verticalWidgetRun.end.updateDelegate = this;
                    dimensionDependency.updateDelegate = this;
                    if (constraintWidget3.isInVerticalChain()) {
                        this.f2958d.f2932g.add(this.f2955a.verticalRun.f2958d);
                        this.f2955a.verticalRun.f2958d.f2931f.add(this.f2958d);
                        VerticalWidgetRun verticalWidgetRun2 = this.f2955a.verticalRun;
                        verticalWidgetRun2.f2958d.updateDelegate = this;
                        this.f2958d.f2932g.add(verticalWidgetRun2.start);
                        this.f2958d.f2932g.add(this.f2955a.verticalRun.end);
                        this.f2955a.verticalRun.start.f2931f.add(this.f2958d);
                        this.f2955a.verticalRun.end.f2931f.add(this.f2958d);
                    } else if (this.f2955a.isInHorizontalChain()) {
                        this.f2955a.verticalRun.f2958d.f2932g.add(this.f2958d);
                        this.f2958d.f2931f.add(this.f2955a.verticalRun.f2958d);
                    } else {
                        this.f2955a.verticalRun.f2958d.f2932g.add(this.f2958d);
                    }
                } else {
                    DimensionDependency dimensionDependency4 = constraintWidget3.verticalRun.f2958d;
                    dimensionDependency.f2932g.add(dimensionDependency4);
                    dimensionDependency4.f2931f.add(this.f2958d);
                    this.f2955a.verticalRun.start.f2931f.add(this.f2958d);
                    this.f2955a.verticalRun.end.f2931f.add(this.f2958d);
                    DimensionDependency dimensionDependency5 = this.f2958d;
                    dimensionDependency5.delegateToWidgetRun = true;
                    dimensionDependency5.f2931f.add(this.start);
                    this.f2958d.f2931f.add(this.end);
                    this.start.f2932g.add(this.f2958d);
                    this.end.f2932g.add(this.f2958d);
                }
            }
        }
        ConstraintWidget constraintWidget4 = this.f2955a;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget4.mListAnchors;
        ConstraintAnchor constraintAnchor4 = constraintAnchorArr2[0];
        ConstraintAnchor constraintAnchor5 = constraintAnchor4.mTarget;
        if (constraintAnchor5 != null && constraintAnchorArr2[1].mTarget != null) {
            if (constraintWidget4.isInHorizontalChain()) {
                this.start.f2928c = this.f2955a.mListAnchors[0].getMargin();
                this.end.f2928c = -this.f2955a.mListAnchors[1].getMargin();
                return;
            }
            DependencyNode dependencyNodeF5 = f(this.f2955a.mListAnchors[0]);
            DependencyNode dependencyNodeF6 = f(this.f2955a.mListAnchors[1]);
            if (dependencyNodeF5 != null) {
                dependencyNodeF5.addDependency(this);
            }
            if (dependencyNodeF6 != null) {
                dependencyNodeF6.addDependency(this);
            }
            this.f2960f = WidgetRun.RunType.CENTER;
            return;
        }
        if (constraintAnchor5 != null) {
            DependencyNode dependencyNodeF7 = f(constraintAnchor4);
            if (dependencyNodeF7 != null) {
                a(this.start, dependencyNodeF7, this.f2955a.mListAnchors[0].getMargin());
                b(this.end, this.start, 1, this.f2958d);
                return;
            }
            return;
        }
        ConstraintAnchor constraintAnchor6 = constraintAnchorArr2[1];
        if (constraintAnchor6.mTarget != null) {
            DependencyNode dependencyNodeF8 = f(constraintAnchor6);
            if (dependencyNodeF8 != null) {
                a(this.end, dependencyNodeF8, -this.f2955a.mListAnchors[1].getMargin());
                b(this.start, this.end, -1, this.f2958d);
                return;
            }
            return;
        }
        if ((constraintWidget4 instanceof Helper) || constraintWidget4.getParent() == null) {
            return;
        }
        a(this.start, this.f2955a.getParent().horizontalRun.start, this.f2955a.getX());
        b(this.end, this.start, 1, this.f2958d);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void d() {
        this.f2956b = null;
        this.start.clear();
        this.end.clear();
        this.f2958d.clear();
        this.f2959e = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    boolean h() {
        return this.f2957c != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.f2955a.mMatchConstraintDefaultWidth == 0;
    }

    void l() {
        this.f2959e = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.f2958d.resolved = false;
    }

    public String toString() {
        return "HorizontalRun " + this.f2955a.getDebugName();
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x02df  */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void update(androidx.constraintlayout.core.widgets.analyzer.Dependency r17) {
        /*
            Method dump skipped, instructions count: 1088
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun.update(androidx.constraintlayout.core.widgets.analyzer.Dependency):void");
    }
}
