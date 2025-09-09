package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Guideline;

/* loaded from: classes.dex */
class GuidelineReference extends WidgetRun {
    public GuidelineReference(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        constraintWidget.horizontalRun.d();
        constraintWidget.verticalRun.d();
        this.orientation = ((Guideline) constraintWidget).getOrientation();
    }

    private void addDependency(DependencyNode dependencyNode) {
        this.start.f2931f.add(dependencyNode);
        dependencyNode.f2932g.add(this.start);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        if (((Guideline) this.f2955a).getOrientation() == 1) {
            this.f2955a.setX(this.start.value);
        } else {
            this.f2955a.setY(this.start.value);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void c() {
        Guideline guideline = (Guideline) this.f2955a;
        int relativeBegin = guideline.getRelativeBegin();
        int relativeEnd = guideline.getRelativeEnd();
        guideline.getRelativePercent();
        if (guideline.getOrientation() == 1) {
            if (relativeBegin != -1) {
                this.start.f2932g.add(this.f2955a.mParent.horizontalRun.start);
                this.f2955a.mParent.horizontalRun.start.f2931f.add(this.start);
                this.start.f2928c = relativeBegin;
            } else if (relativeEnd != -1) {
                this.start.f2932g.add(this.f2955a.mParent.horizontalRun.end);
                this.f2955a.mParent.horizontalRun.end.f2931f.add(this.start);
                this.start.f2928c = -relativeEnd;
            } else {
                DependencyNode dependencyNode = this.start;
                dependencyNode.delegateToWidgetRun = true;
                dependencyNode.f2932g.add(this.f2955a.mParent.horizontalRun.end);
                this.f2955a.mParent.horizontalRun.end.f2931f.add(this.start);
            }
            addDependency(this.f2955a.horizontalRun.start);
            addDependency(this.f2955a.horizontalRun.end);
            return;
        }
        if (relativeBegin != -1) {
            this.start.f2932g.add(this.f2955a.mParent.verticalRun.start);
            this.f2955a.mParent.verticalRun.start.f2931f.add(this.start);
            this.start.f2928c = relativeBegin;
        } else if (relativeEnd != -1) {
            this.start.f2932g.add(this.f2955a.mParent.verticalRun.end);
            this.f2955a.mParent.verticalRun.end.f2931f.add(this.start);
            this.start.f2928c = -relativeEnd;
        } else {
            DependencyNode dependencyNode2 = this.start;
            dependencyNode2.delegateToWidgetRun = true;
            dependencyNode2.f2932g.add(this.f2955a.mParent.verticalRun.end);
            this.f2955a.mParent.verticalRun.end.f2931f.add(this.start);
        }
        addDependency(this.f2955a.verticalRun.start);
        addDependency(this.f2955a.verticalRun.end);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void d() {
        this.start.clear();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    boolean h() {
        return false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.readyToSolve && !dependencyNode.resolved) {
            this.start.resolve((int) ((((DependencyNode) dependencyNode.f2932g.get(0)).value * ((Guideline) this.f2955a).getRelativePercent()) + 0.5f));
        }
    }
}
