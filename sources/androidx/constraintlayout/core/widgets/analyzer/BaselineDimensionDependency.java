package androidx.constraintlayout.core.widgets.analyzer;

/* loaded from: classes.dex */
class BaselineDimensionDependency extends DimensionDependency {
    public BaselineDimensionDependency(WidgetRun widgetRun) {
        super(widgetRun);
    }

    public void update(DependencyNode dependencyNode) {
        WidgetRun widgetRun = this.f2926a;
        ((VerticalWidgetRun) widgetRun).baseline.f2928c = widgetRun.f2955a.getBaselineDistance();
        this.resolved = true;
    }
}
