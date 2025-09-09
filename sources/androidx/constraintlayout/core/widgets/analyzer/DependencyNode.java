package androidx.constraintlayout.core.widgets.analyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class DependencyNode implements Dependency {

    /* renamed from: a, reason: collision with root package name */
    WidgetRun f2926a;

    /* renamed from: c, reason: collision with root package name */
    int f2928c;
    public int value;
    public Dependency updateDelegate = null;
    public boolean delegateToWidgetRun = false;
    public boolean readyToSolve = false;

    /* renamed from: b, reason: collision with root package name */
    Type f2927b = Type.UNKNOWN;

    /* renamed from: d, reason: collision with root package name */
    int f2929d = 1;

    /* renamed from: e, reason: collision with root package name */
    DimensionDependency f2930e = null;
    public boolean resolved = false;

    /* renamed from: f, reason: collision with root package name */
    List f2931f = new ArrayList();

    /* renamed from: g, reason: collision with root package name */
    List f2932g = new ArrayList();

    enum Type {
        UNKNOWN,
        HORIZONTAL_DIMENSION,
        VERTICAL_DIMENSION,
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        BASELINE
    }

    public DependencyNode(WidgetRun widgetRun) {
        this.f2926a = widgetRun;
    }

    public void addDependency(Dependency dependency) {
        this.f2931f.add(dependency);
        if (this.resolved) {
            dependency.update(dependency);
        }
    }

    public void clear() {
        this.f2932g.clear();
        this.f2931f.clear();
        this.resolved = false;
        this.value = 0;
        this.readyToSolve = false;
        this.delegateToWidgetRun = false;
    }

    public String name() {
        String str;
        String debugName = this.f2926a.f2955a.getDebugName();
        Type type = this.f2927b;
        if (type == Type.LEFT || type == Type.RIGHT) {
            str = debugName + "_HORIZONTAL";
        } else {
            str = debugName + "_VERTICAL";
        }
        return str + ":" + this.f2927b.name();
    }

    public void resolve(int i2) {
        if (this.resolved) {
            return;
        }
        this.resolved = true;
        this.value = i2;
        for (Dependency dependency : this.f2931f) {
            dependency.update(dependency);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f2926a.f2955a.getDebugName());
        sb.append(":");
        sb.append(this.f2927b);
        sb.append("(");
        sb.append(this.resolved ? Integer.valueOf(this.value) : "unresolved");
        sb.append(") <t=");
        sb.append(this.f2932g.size());
        sb.append(":d=");
        sb.append(this.f2931f.size());
        sb.append(">");
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        Iterator it = this.f2932g.iterator();
        while (it.hasNext()) {
            if (!((DependencyNode) it.next()).resolved) {
                return;
            }
        }
        this.readyToSolve = true;
        Dependency dependency2 = this.updateDelegate;
        if (dependency2 != null) {
            dependency2.update(this);
        }
        if (this.delegateToWidgetRun) {
            this.f2926a.update(this);
            return;
        }
        DependencyNode dependencyNode = null;
        int i2 = 0;
        for (DependencyNode dependencyNode2 : this.f2932g) {
            if (!(dependencyNode2 instanceof DimensionDependency)) {
                i2++;
                dependencyNode = dependencyNode2;
            }
        }
        if (dependencyNode != null && i2 == 1 && dependencyNode.resolved) {
            DimensionDependency dimensionDependency = this.f2930e;
            if (dimensionDependency != null) {
                if (!dimensionDependency.resolved) {
                    return;
                } else {
                    this.f2928c = this.f2929d * dimensionDependency.value;
                }
            }
            resolve(dependencyNode.value + this.f2928c);
        }
        Dependency dependency3 = this.updateDelegate;
        if (dependency3 != null) {
            dependency3.update(this);
        }
    }
}
