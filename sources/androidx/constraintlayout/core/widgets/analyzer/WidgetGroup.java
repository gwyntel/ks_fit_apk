package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Chain;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import com.facebook.internal.AnalyticsEvents;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class WidgetGroup {
    private static final boolean DEBUG = false;

    /* renamed from: f, reason: collision with root package name */
    static int f2941f;

    /* renamed from: b, reason: collision with root package name */
    int f2943b;

    /* renamed from: d, reason: collision with root package name */
    int f2945d;

    /* renamed from: a, reason: collision with root package name */
    ArrayList f2942a = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    boolean f2944c = false;

    /* renamed from: e, reason: collision with root package name */
    ArrayList f2946e = null;
    private int moveTo = -1;

    class MeasureResult {

        /* renamed from: a, reason: collision with root package name */
        WeakReference f2947a;

        /* renamed from: b, reason: collision with root package name */
        int f2948b;

        /* renamed from: c, reason: collision with root package name */
        int f2949c;

        /* renamed from: d, reason: collision with root package name */
        int f2950d;

        /* renamed from: e, reason: collision with root package name */
        int f2951e;

        /* renamed from: f, reason: collision with root package name */
        int f2952f;

        /* renamed from: g, reason: collision with root package name */
        int f2953g;

        public MeasureResult(ConstraintWidget constraintWidget, LinearSystem linearSystem, int i2) {
            this.f2947a = new WeakReference(constraintWidget);
            this.f2948b = linearSystem.getObjectVariableValue(constraintWidget.mLeft);
            this.f2949c = linearSystem.getObjectVariableValue(constraintWidget.mTop);
            this.f2950d = linearSystem.getObjectVariableValue(constraintWidget.mRight);
            this.f2951e = linearSystem.getObjectVariableValue(constraintWidget.mBottom);
            this.f2952f = linearSystem.getObjectVariableValue(constraintWidget.mBaseline);
            this.f2953g = i2;
        }

        public void apply() {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.f2947a.get();
            if (constraintWidget != null) {
                constraintWidget.setFinalFrame(this.f2948b, this.f2949c, this.f2950d, this.f2951e, this.f2952f, this.f2953g);
            }
        }
    }

    public WidgetGroup(int i2) {
        int i3 = f2941f;
        f2941f = i3 + 1;
        this.f2943b = i3;
        this.f2945d = i2;
    }

    private boolean contains(ConstraintWidget constraintWidget) {
        return this.f2942a.contains(constraintWidget);
    }

    private String getOrientationString() {
        int i2 = this.f2945d;
        return i2 == 0 ? "Horizontal" : i2 == 1 ? "Vertical" : i2 == 2 ? "Both" : AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
    }

    private int measureWrap(int i2, ConstraintWidget constraintWidget) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.getDimensionBehaviour(i2);
        if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || dimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
            return i2 == 0 ? constraintWidget.getWidth() : constraintWidget.getHeight();
        }
        return -1;
    }

    private int solverMeasure(LinearSystem linearSystem, ArrayList<ConstraintWidget> arrayList, int i2) {
        int objectVariableValue;
        int objectVariableValue2;
        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) arrayList.get(0).getParent();
        linearSystem.reset();
        constraintWidgetContainer.addToSolver(linearSystem, false);
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList.get(i3).addToSolver(linearSystem, false);
        }
        if (i2 == 0 && constraintWidgetContainer.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(constraintWidgetContainer, linearSystem, arrayList, 0);
        }
        if (i2 == 1 && constraintWidgetContainer.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(constraintWidgetContainer, linearSystem, arrayList, 1);
        }
        try {
            linearSystem.minimize();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.f2946e = new ArrayList();
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            this.f2946e.add(new MeasureResult(arrayList.get(i4), linearSystem, i2));
        }
        if (i2 == 0) {
            objectVariableValue = linearSystem.getObjectVariableValue(constraintWidgetContainer.mLeft);
            objectVariableValue2 = linearSystem.getObjectVariableValue(constraintWidgetContainer.mRight);
            linearSystem.reset();
        } else {
            objectVariableValue = linearSystem.getObjectVariableValue(constraintWidgetContainer.mTop);
            objectVariableValue2 = linearSystem.getObjectVariableValue(constraintWidgetContainer.mBottom);
            linearSystem.reset();
        }
        return objectVariableValue2 - objectVariableValue;
    }

    public boolean add(ConstraintWidget constraintWidget) {
        if (this.f2942a.contains(constraintWidget)) {
            return false;
        }
        this.f2942a.add(constraintWidget);
        return true;
    }

    public void apply() {
        if (this.f2946e != null && this.f2944c) {
            for (int i2 = 0; i2 < this.f2946e.size(); i2++) {
                ((MeasureResult) this.f2946e.get(i2)).apply();
            }
        }
    }

    public void cleanup(ArrayList<WidgetGroup> arrayList) {
        int size = this.f2942a.size();
        if (this.moveTo != -1 && size > 0) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                WidgetGroup widgetGroup = arrayList.get(i2);
                if (this.moveTo == widgetGroup.f2943b) {
                    moveTo(this.f2945d, widgetGroup);
                }
            }
        }
        if (size == 0) {
            arrayList.remove(this);
        }
    }

    public void clear() {
        this.f2942a.clear();
    }

    public int getId() {
        return this.f2943b;
    }

    public int getOrientation() {
        return this.f2945d;
    }

    public boolean intersectWith(WidgetGroup widgetGroup) {
        for (int i2 = 0; i2 < this.f2942a.size(); i2++) {
            if (widgetGroup.contains((ConstraintWidget) this.f2942a.get(i2))) {
                return true;
            }
        }
        return false;
    }

    public boolean isAuthoritative() {
        return this.f2944c;
    }

    public void moveTo(int i2, WidgetGroup widgetGroup) {
        Iterator it = this.f2942a.iterator();
        while (it.hasNext()) {
            ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
            widgetGroup.add(constraintWidget);
            if (i2 == 0) {
                constraintWidget.horizontalGroup = widgetGroup.getId();
            } else {
                constraintWidget.verticalGroup = widgetGroup.getId();
            }
        }
        this.moveTo = widgetGroup.f2943b;
    }

    public void setAuthoritative(boolean z2) {
        this.f2944c = z2;
    }

    public void setOrientation(int i2) {
        this.f2945d = i2;
    }

    public int size() {
        return this.f2942a.size();
    }

    public String toString() {
        String str = getOrientationString() + " [" + this.f2943b + "] <";
        Iterator it = this.f2942a.iterator();
        while (it.hasNext()) {
            str = str + " " + ((ConstraintWidget) it.next()).getDebugName();
        }
        return str + " >";
    }

    public int measureWrap(LinearSystem linearSystem, int i2) {
        if (this.f2942a.size() == 0) {
            return 0;
        }
        return solverMeasure(linearSystem, this.f2942a, i2);
    }
}
