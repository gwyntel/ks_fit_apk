package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import java.util.Iterator;

/* loaded from: classes.dex */
class HelperReferences extends WidgetRun {
    public HelperReferences(ConstraintWidget constraintWidget) {
        super(constraintWidget);
    }

    private void addDependency(DependencyNode dependencyNode) {
        this.start.f2931f.add(dependencyNode);
        dependencyNode.f2932g.add(this.start);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        ConstraintWidget constraintWidget = this.f2955a;
        if (constraintWidget instanceof Barrier) {
            int barrierType = ((Barrier) constraintWidget).getBarrierType();
            if (barrierType == 0 || barrierType == 1) {
                this.f2955a.setX(this.start.value);
            } else {
                this.f2955a.setY(this.start.value);
            }
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void c() {
        ConstraintWidget constraintWidget = this.f2955a;
        if (constraintWidget instanceof Barrier) {
            this.start.delegateToWidgetRun = true;
            Barrier barrier = (Barrier) constraintWidget;
            int barrierType = barrier.getBarrierType();
            boolean allowsGoneWidget = barrier.getAllowsGoneWidget();
            int i2 = 0;
            if (barrierType == 0) {
                this.start.f2927b = DependencyNode.Type.LEFT;
                while (i2 < barrier.mWidgetsCount) {
                    ConstraintWidget constraintWidget2 = barrier.mWidgets[i2];
                    if (allowsGoneWidget || constraintWidget2.getVisibility() != 8) {
                        DependencyNode dependencyNode = constraintWidget2.horizontalRun.start;
                        dependencyNode.f2931f.add(this.start);
                        this.start.f2932g.add(dependencyNode);
                    }
                    i2++;
                }
                addDependency(this.f2955a.horizontalRun.start);
                addDependency(this.f2955a.horizontalRun.end);
                return;
            }
            if (barrierType == 1) {
                this.start.f2927b = DependencyNode.Type.RIGHT;
                while (i2 < barrier.mWidgetsCount) {
                    ConstraintWidget constraintWidget3 = barrier.mWidgets[i2];
                    if (allowsGoneWidget || constraintWidget3.getVisibility() != 8) {
                        DependencyNode dependencyNode2 = constraintWidget3.horizontalRun.end;
                        dependencyNode2.f2931f.add(this.start);
                        this.start.f2932g.add(dependencyNode2);
                    }
                    i2++;
                }
                addDependency(this.f2955a.horizontalRun.start);
                addDependency(this.f2955a.horizontalRun.end);
                return;
            }
            if (barrierType == 2) {
                this.start.f2927b = DependencyNode.Type.TOP;
                while (i2 < barrier.mWidgetsCount) {
                    ConstraintWidget constraintWidget4 = barrier.mWidgets[i2];
                    if (allowsGoneWidget || constraintWidget4.getVisibility() != 8) {
                        DependencyNode dependencyNode3 = constraintWidget4.verticalRun.start;
                        dependencyNode3.f2931f.add(this.start);
                        this.start.f2932g.add(dependencyNode3);
                    }
                    i2++;
                }
                addDependency(this.f2955a.verticalRun.start);
                addDependency(this.f2955a.verticalRun.end);
                return;
            }
            if (barrierType != 3) {
                return;
            }
            this.start.f2927b = DependencyNode.Type.BOTTOM;
            while (i2 < barrier.mWidgetsCount) {
                ConstraintWidget constraintWidget5 = barrier.mWidgets[i2];
                if (allowsGoneWidget || constraintWidget5.getVisibility() != 8) {
                    DependencyNode dependencyNode4 = constraintWidget5.verticalRun.end;
                    dependencyNode4.f2931f.add(this.start);
                    this.start.f2932g.add(dependencyNode4);
                }
                i2++;
            }
            addDependency(this.f2955a.verticalRun.start);
            addDependency(this.f2955a.verticalRun.end);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void d() {
        this.f2956b = null;
        this.start.clear();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    boolean h() {
        return false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        Barrier barrier = (Barrier) this.f2955a;
        int barrierType = barrier.getBarrierType();
        Iterator it = this.start.f2932g.iterator();
        int i2 = 0;
        int i3 = -1;
        while (it.hasNext()) {
            int i4 = ((DependencyNode) it.next()).value;
            if (i3 == -1 || i4 < i3) {
                i3 = i4;
            }
            if (i2 < i4) {
                i2 = i4;
            }
        }
        if (barrierType == 0 || barrierType == 2) {
            this.start.resolve(i3 + barrier.getMargin());
        } else {
            this.start.resolve(i2 + barrier.getMargin());
        }
    }
}
