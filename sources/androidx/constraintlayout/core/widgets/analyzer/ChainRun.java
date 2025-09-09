package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ChainRun extends WidgetRun {
    private int chainStyle;

    /* renamed from: g, reason: collision with root package name */
    ArrayList f2924g;

    public ChainRun(ConstraintWidget constraintWidget, int i2) {
        super(constraintWidget);
        this.f2924g = new ArrayList();
        this.orientation = i2;
        build();
    }

    private void build() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2 = this.f2955a;
        ConstraintWidget previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
        while (true) {
            ConstraintWidget constraintWidget3 = previousChainMember;
            constraintWidget = constraintWidget2;
            constraintWidget2 = constraintWidget3;
            if (constraintWidget2 == null) {
                break;
            } else {
                previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
            }
        }
        this.f2955a = constraintWidget;
        this.f2924g.add(constraintWidget.getRun(this.orientation));
        ConstraintWidget nextChainMember = constraintWidget.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            this.f2924g.add(nextChainMember.getRun(this.orientation));
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        Iterator it = this.f2924g.iterator();
        while (it.hasNext()) {
            WidgetRun widgetRun = (WidgetRun) it.next();
            int i2 = this.orientation;
            if (i2 == 0) {
                widgetRun.f2955a.horizontalChainRun = this;
            } else if (i2 == 1) {
                widgetRun.f2955a.verticalChainRun = this;
            }
        }
        if (this.orientation == 0 && ((ConstraintWidgetContainer) this.f2955a.getParent()).isRtl() && this.f2924g.size() > 1) {
            ArrayList arrayList = this.f2924g;
            this.f2955a = ((WidgetRun) arrayList.get(arrayList.size() - 1)).f2955a;
        }
        this.chainStyle = this.orientation == 0 ? this.f2955a.getHorizontalChainStyle() : this.f2955a.getVerticalChainStyle();
    }

    private ConstraintWidget getFirstVisibleWidget() {
        for (int i2 = 0; i2 < this.f2924g.size(); i2++) {
            WidgetRun widgetRun = (WidgetRun) this.f2924g.get(i2);
            if (widgetRun.f2955a.getVisibility() != 8) {
                return widgetRun.f2955a;
            }
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        for (int size = this.f2924g.size() - 1; size >= 0; size--) {
            WidgetRun widgetRun = (WidgetRun) this.f2924g.get(size);
            if (widgetRun.f2955a.getVisibility() != 8) {
                return widgetRun.f2955a;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        for (int i2 = 0; i2 < this.f2924g.size(); i2++) {
            ((WidgetRun) this.f2924g.get(i2)).applyToWidget();
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void c() {
        Iterator it = this.f2924g.iterator();
        while (it.hasNext()) {
            ((WidgetRun) it.next()).c();
        }
        int size = this.f2924g.size();
        if (size < 1) {
            return;
        }
        ConstraintWidget constraintWidget = ((WidgetRun) this.f2924g.get(0)).f2955a;
        ConstraintWidget constraintWidget2 = ((WidgetRun) this.f2924g.get(size - 1)).f2955a;
        if (this.orientation == 0) {
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
            DependencyNode dependencyNodeG = g(constraintAnchor, 0);
            int margin = constraintAnchor.getMargin();
            ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
            if (firstVisibleWidget != null) {
                margin = firstVisibleWidget.mLeft.getMargin();
            }
            if (dependencyNodeG != null) {
                a(this.start, dependencyNodeG, margin);
            }
            DependencyNode dependencyNodeG2 = g(constraintAnchor2, 0);
            int margin2 = constraintAnchor2.getMargin();
            ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
            if (lastVisibleWidget != null) {
                margin2 = lastVisibleWidget.mRight.getMargin();
            }
            if (dependencyNodeG2 != null) {
                a(this.end, dependencyNodeG2, -margin2);
            }
        } else {
            ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
            ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
            DependencyNode dependencyNodeG3 = g(constraintAnchor3, 1);
            int margin3 = constraintAnchor3.getMargin();
            ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
            if (firstVisibleWidget2 != null) {
                margin3 = firstVisibleWidget2.mTop.getMargin();
            }
            if (dependencyNodeG3 != null) {
                a(this.start, dependencyNodeG3, margin3);
            }
            DependencyNode dependencyNodeG4 = g(constraintAnchor4, 1);
            int margin4 = constraintAnchor4.getMargin();
            ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
            if (lastVisibleWidget2 != null) {
                margin4 = lastVisibleWidget2.mBottom.getMargin();
            }
            if (dependencyNodeG4 != null) {
                a(this.end, dependencyNodeG4, -margin4);
            }
        }
        this.start.updateDelegate = this;
        this.end.updateDelegate = this;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void d() {
        this.f2956b = null;
        Iterator it = this.f2924g.iterator();
        while (it.hasNext()) {
            ((WidgetRun) it.next()).d();
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public long getWrapDimension() {
        int size = this.f2924g.size();
        long wrapDimension = 0;
        for (int i2 = 0; i2 < size; i2++) {
            wrapDimension = wrapDimension + r4.start.f2928c + ((WidgetRun) this.f2924g.get(i2)).getWrapDimension() + r4.end.f2928c;
        }
        return wrapDimension;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    boolean h() {
        int size = this.f2924g.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (!((WidgetRun) this.f2924g.get(i2)).h()) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ChainRun ");
        sb.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        Iterator it = this.f2924g.iterator();
        while (it.hasNext()) {
            WidgetRun widgetRun = (WidgetRun) it.next();
            sb.append("<");
            sb.append(widgetRun);
            sb.append("> ");
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0153  */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void update(androidx.constraintlayout.core.widgets.analyzer.Dependency r27) {
        /*
            Method dump skipped, instructions count: 1062
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.ChainRun.update(androidx.constraintlayout.core.widgets.analyzer.Dependency):void");
    }
}
