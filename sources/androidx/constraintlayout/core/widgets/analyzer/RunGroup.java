package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
class RunGroup {
    public static final int BASELINE = 2;
    public static final int END = 1;
    public static final int START = 0;
    public static int index;

    /* renamed from: a, reason: collision with root package name */
    WidgetRun f2934a;

    /* renamed from: b, reason: collision with root package name */
    WidgetRun f2935b;

    /* renamed from: d, reason: collision with root package name */
    int f2937d;

    /* renamed from: e, reason: collision with root package name */
    int f2938e;
    public int position = 0;
    public boolean dual = false;

    /* renamed from: c, reason: collision with root package name */
    ArrayList f2936c = new ArrayList();

    public RunGroup(WidgetRun widgetRun, int i2) {
        this.f2934a = null;
        this.f2935b = null;
        int i3 = index;
        this.f2937d = i3;
        index = i3 + 1;
        this.f2934a = widgetRun;
        this.f2935b = widgetRun;
        this.f2938e = i2;
    }

    private boolean defineTerminalWidget(WidgetRun widgetRun, int i2) {
        DependencyNode dependencyNode;
        WidgetRun widgetRun2;
        DependencyNode dependencyNode2;
        WidgetRun widgetRun3;
        if (!widgetRun.f2955a.isTerminalWidget[i2]) {
            return false;
        }
        for (Dependency dependency : widgetRun.start.f2931f) {
            if ((dependency instanceof DependencyNode) && (widgetRun3 = (dependencyNode2 = (DependencyNode) dependency).f2926a) != widgetRun && dependencyNode2 == widgetRun3.start) {
                if (widgetRun instanceof ChainRun) {
                    Iterator it = ((ChainRun) widgetRun).f2924g.iterator();
                    while (it.hasNext()) {
                        defineTerminalWidget((WidgetRun) it.next(), i2);
                    }
                } else if (!(widgetRun instanceof HelperReferences)) {
                    widgetRun.f2955a.isTerminalWidget[i2] = false;
                }
                defineTerminalWidget(dependencyNode2.f2926a, i2);
            }
        }
        for (Dependency dependency2 : widgetRun.end.f2931f) {
            if ((dependency2 instanceof DependencyNode) && (widgetRun2 = (dependencyNode = (DependencyNode) dependency2).f2926a) != widgetRun && dependencyNode == widgetRun2.start) {
                if (widgetRun instanceof ChainRun) {
                    Iterator it2 = ((ChainRun) widgetRun).f2924g.iterator();
                    while (it2.hasNext()) {
                        defineTerminalWidget((WidgetRun) it2.next(), i2);
                    }
                } else if (!(widgetRun instanceof HelperReferences)) {
                    widgetRun.f2955a.isTerminalWidget[i2] = false;
                }
                defineTerminalWidget(dependencyNode.f2926a, i2);
            }
        }
        return false;
    }

    private long traverseEnd(DependencyNode dependencyNode, long j2) {
        WidgetRun widgetRun = dependencyNode.f2926a;
        if (widgetRun instanceof HelperReferences) {
            return j2;
        }
        int size = dependencyNode.f2931f.size();
        long jMin = j2;
        for (int i2 = 0; i2 < size; i2++) {
            Dependency dependency = (Dependency) dependencyNode.f2931f.get(i2);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.f2926a != widgetRun) {
                    jMin = Math.min(jMin, traverseEnd(dependencyNode2, dependencyNode2.f2928c + j2));
                }
            }
        }
        if (dependencyNode != widgetRun.end) {
            return jMin;
        }
        long wrapDimension = j2 - widgetRun.getWrapDimension();
        return Math.min(Math.min(jMin, traverseEnd(widgetRun.start, wrapDimension)), wrapDimension - widgetRun.start.f2928c);
    }

    private long traverseStart(DependencyNode dependencyNode, long j2) {
        WidgetRun widgetRun = dependencyNode.f2926a;
        if (widgetRun instanceof HelperReferences) {
            return j2;
        }
        int size = dependencyNode.f2931f.size();
        long jMax = j2;
        for (int i2 = 0; i2 < size; i2++) {
            Dependency dependency = (Dependency) dependencyNode.f2931f.get(i2);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.f2926a != widgetRun) {
                    jMax = Math.max(jMax, traverseStart(dependencyNode2, dependencyNode2.f2928c + j2));
                }
            }
        }
        if (dependencyNode != widgetRun.start) {
            return jMax;
        }
        long wrapDimension = j2 + widgetRun.getWrapDimension();
        return Math.max(Math.max(jMax, traverseStart(widgetRun.end, wrapDimension)), wrapDimension - widgetRun.end.f2928c);
    }

    public void add(WidgetRun widgetRun) {
        this.f2936c.add(widgetRun);
        this.f2935b = widgetRun;
    }

    public long computeWrapSize(ConstraintWidgetContainer constraintWidgetContainer, int i2) {
        long wrapDimension;
        int i3;
        WidgetRun widgetRun = this.f2934a;
        if (widgetRun instanceof ChainRun) {
            if (((ChainRun) widgetRun).orientation != i2) {
                return 0L;
            }
        } else if (i2 == 0) {
            if (!(widgetRun instanceof HorizontalWidgetRun)) {
                return 0L;
            }
        } else if (!(widgetRun instanceof VerticalWidgetRun)) {
            return 0L;
        }
        DependencyNode dependencyNode = (i2 == 0 ? constraintWidgetContainer.horizontalRun : constraintWidgetContainer.verticalRun).start;
        DependencyNode dependencyNode2 = (i2 == 0 ? constraintWidgetContainer.horizontalRun : constraintWidgetContainer.verticalRun).end;
        boolean zContains = widgetRun.start.f2932g.contains(dependencyNode);
        boolean zContains2 = this.f2934a.end.f2932g.contains(dependencyNode2);
        long wrapDimension2 = this.f2934a.getWrapDimension();
        if (zContains && zContains2) {
            long jTraverseStart = traverseStart(this.f2934a.start, 0L);
            long jTraverseEnd = traverseEnd(this.f2934a.end, 0L);
            long j2 = jTraverseStart - wrapDimension2;
            WidgetRun widgetRun2 = this.f2934a;
            int i4 = widgetRun2.end.f2928c;
            if (j2 >= (-i4)) {
                j2 += i4;
            }
            int i5 = widgetRun2.start.f2928c;
            long j3 = ((-jTraverseEnd) - wrapDimension2) - i5;
            if (j3 >= i5) {
                j3 -= i5;
            }
            float biasPercent = widgetRun2.f2955a.getBiasPercent(i2);
            float f2 = biasPercent > 0.0f ? (long) ((j3 / biasPercent) + (j2 / (1.0f - biasPercent))) : 0L;
            long j4 = ((long) ((f2 * biasPercent) + 0.5f)) + wrapDimension2 + ((long) ((f2 * (1.0f - biasPercent)) + 0.5f));
            wrapDimension = r12.start.f2928c + j4;
            i3 = this.f2934a.end.f2928c;
        } else {
            if (zContains) {
                return Math.max(traverseStart(this.f2934a.start, r12.f2928c), this.f2934a.start.f2928c + wrapDimension2);
            }
            if (zContains2) {
                return Math.max(-traverseEnd(this.f2934a.end, r12.f2928c), (-this.f2934a.end.f2928c) + wrapDimension2);
            }
            wrapDimension = r12.start.f2928c + this.f2934a.getWrapDimension();
            i3 = this.f2934a.end.f2928c;
        }
        return wrapDimension - i3;
    }

    public void defineTerminalWidgets(boolean z2, boolean z3) {
        if (z2) {
            WidgetRun widgetRun = this.f2934a;
            if (widgetRun instanceof HorizontalWidgetRun) {
                defineTerminalWidget(widgetRun, 0);
            }
        }
        if (z3) {
            WidgetRun widgetRun2 = this.f2934a;
            if (widgetRun2 instanceof VerticalWidgetRun) {
                defineTerminalWidget(widgetRun2, 1);
            }
        }
    }
}
