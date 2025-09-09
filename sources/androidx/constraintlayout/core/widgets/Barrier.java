package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import com.alipay.sdk.m.u.i;
import java.util.HashMap;

/* loaded from: classes.dex */
public class Barrier extends HelperWidget {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    private static final boolean USE_RELAX_GONE = false;
    private static final boolean USE_RESOLUTION = true;
    private int mBarrierType = 0;
    private boolean mAllowsGoneWidget = true;
    private int mMargin = 0;
    boolean N = false;

    public Barrier() {
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem, boolean z2) {
        ConstraintAnchor[] constraintAnchorArr;
        boolean z3;
        int i2;
        int i3;
        int i4;
        ConstraintAnchor[] constraintAnchorArr2 = this.mListAnchors;
        constraintAnchorArr2[0] = this.mLeft;
        constraintAnchorArr2[2] = this.mTop;
        constraintAnchorArr2[1] = this.mRight;
        constraintAnchorArr2[3] = this.mBottom;
        int i5 = 0;
        while (true) {
            constraintAnchorArr = this.mListAnchors;
            if (i5 >= constraintAnchorArr.length) {
                break;
            }
            ConstraintAnchor constraintAnchor = constraintAnchorArr[i5];
            constraintAnchor.f2888b = linearSystem.createObjectVariable(constraintAnchor);
            i5++;
        }
        int i6 = this.mBarrierType;
        if (i6 < 0 || i6 >= 4) {
            return;
        }
        ConstraintAnchor constraintAnchor2 = constraintAnchorArr[i6];
        if (!this.N) {
            allSolved();
        }
        if (this.N) {
            this.N = false;
            int i7 = this.mBarrierType;
            if (i7 == 0 || i7 == 1) {
                linearSystem.addEquality(this.mLeft.f2888b, this.f2899j);
                linearSystem.addEquality(this.mRight.f2888b, this.f2899j);
                return;
            } else {
                if (i7 == 2 || i7 == 3) {
                    linearSystem.addEquality(this.mTop.f2888b, this.f2900k);
                    linearSystem.addEquality(this.mBottom.f2888b, this.f2900k);
                    return;
                }
                return;
            }
        }
        for (int i8 = 0; i8 < this.mWidgetsCount; i8++) {
            ConstraintWidget constraintWidget = this.mWidgets[i8];
            if ((this.mAllowsGoneWidget || constraintWidget.allowedInBarrier()) && ((((i3 = this.mBarrierType) == 0 || i3 == 1) && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mLeft.mTarget != null && constraintWidget.mRight.mTarget != null) || (((i4 = this.mBarrierType) == 2 || i4 == 3) && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget != null))) {
                z3 = true;
                break;
            }
        }
        z3 = false;
        boolean z4 = this.mLeft.hasCenteredDependents() || this.mRight.hasCenteredDependents();
        boolean z5 = this.mTop.hasCenteredDependents() || this.mBottom.hasCenteredDependents();
        int i9 = !(!z3 && (((i2 = this.mBarrierType) == 0 && z4) || ((i2 == 2 && z5) || ((i2 == 1 && z4) || (i2 == 3 && z5))))) ? 4 : 5;
        for (int i10 = 0; i10 < this.mWidgetsCount; i10++) {
            ConstraintWidget constraintWidget2 = this.mWidgets[i10];
            if (this.mAllowsGoneWidget || constraintWidget2.allowedInBarrier()) {
                SolverVariable solverVariableCreateObjectVariable = linearSystem.createObjectVariable(constraintWidget2.mListAnchors[this.mBarrierType]);
                ConstraintAnchor[] constraintAnchorArr3 = constraintWidget2.mListAnchors;
                int i11 = this.mBarrierType;
                ConstraintAnchor constraintAnchor3 = constraintAnchorArr3[i11];
                constraintAnchor3.f2888b = solverVariableCreateObjectVariable;
                ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
                int i12 = (constraintAnchor4 == null || constraintAnchor4.mOwner != this) ? 0 : constraintAnchor3.mMargin;
                if (i11 == 0 || i11 == 2) {
                    linearSystem.addLowerBarrier(constraintAnchor2.f2888b, solverVariableCreateObjectVariable, this.mMargin - i12, z3);
                } else {
                    linearSystem.addGreaterBarrier(constraintAnchor2.f2888b, solverVariableCreateObjectVariable, this.mMargin + i12, z3);
                }
                linearSystem.addEquality(constraintAnchor2.f2888b, solverVariableCreateObjectVariable, this.mMargin + i12, i9);
            }
        }
        int i13 = this.mBarrierType;
        if (i13 == 0) {
            linearSystem.addEquality(this.mRight.f2888b, this.mLeft.f2888b, 0, 8);
            linearSystem.addEquality(this.mLeft.f2888b, this.mParent.mRight.f2888b, 0, 4);
            linearSystem.addEquality(this.mLeft.f2888b, this.mParent.mLeft.f2888b, 0, 0);
            return;
        }
        if (i13 == 1) {
            linearSystem.addEquality(this.mLeft.f2888b, this.mRight.f2888b, 0, 8);
            linearSystem.addEquality(this.mLeft.f2888b, this.mParent.mLeft.f2888b, 0, 4);
            linearSystem.addEquality(this.mLeft.f2888b, this.mParent.mRight.f2888b, 0, 0);
        } else if (i13 == 2) {
            linearSystem.addEquality(this.mBottom.f2888b, this.mTop.f2888b, 0, 8);
            linearSystem.addEquality(this.mTop.f2888b, this.mParent.mBottom.f2888b, 0, 4);
            linearSystem.addEquality(this.mTop.f2888b, this.mParent.mTop.f2888b, 0, 0);
        } else if (i13 == 3) {
            linearSystem.addEquality(this.mTop.f2888b, this.mBottom.f2888b, 0, 8);
            linearSystem.addEquality(this.mTop.f2888b, this.mParent.mTop.f2888b, 0, 4);
            linearSystem.addEquality(this.mTop.f2888b, this.mParent.mBottom.f2888b, 0, 0);
        }
    }

    public boolean allSolved() {
        int i2;
        int i3;
        int i4;
        boolean z2 = true;
        int i5 = 0;
        while (true) {
            i2 = this.mWidgetsCount;
            if (i5 >= i2) {
                break;
            }
            ConstraintWidget constraintWidget = this.mWidgets[i5];
            if ((this.mAllowsGoneWidget || constraintWidget.allowedInBarrier()) && ((((i3 = this.mBarrierType) == 0 || i3 == 1) && !constraintWidget.isResolvedHorizontally()) || (((i4 = this.mBarrierType) == 2 || i4 == 3) && !constraintWidget.isResolvedVertically()))) {
                z2 = false;
            }
            i5++;
        }
        if (!z2 || i2 <= 0) {
            return false;
        }
        int iMax = 0;
        boolean z3 = false;
        for (int i6 = 0; i6 < this.mWidgetsCount; i6++) {
            ConstraintWidget constraintWidget2 = this.mWidgets[i6];
            if (this.mAllowsGoneWidget || constraintWidget2.allowedInBarrier()) {
                if (!z3) {
                    int i7 = this.mBarrierType;
                    if (i7 == 0) {
                        iMax = constraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT).getFinalValue();
                    } else if (i7 == 1) {
                        iMax = constraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT).getFinalValue();
                    } else if (i7 == 2) {
                        iMax = constraintWidget2.getAnchor(ConstraintAnchor.Type.TOP).getFinalValue();
                    } else if (i7 == 3) {
                        iMax = constraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getFinalValue();
                    }
                    z3 = true;
                }
                int i8 = this.mBarrierType;
                if (i8 == 0) {
                    iMax = Math.min(iMax, constraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT).getFinalValue());
                } else if (i8 == 1) {
                    iMax = Math.max(iMax, constraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT).getFinalValue());
                } else if (i8 == 2) {
                    iMax = Math.min(iMax, constraintWidget2.getAnchor(ConstraintAnchor.Type.TOP).getFinalValue());
                } else if (i8 == 3) {
                    iMax = Math.max(iMax, constraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getFinalValue());
                }
            }
        }
        int i9 = iMax + this.mMargin;
        int i10 = this.mBarrierType;
        if (i10 == 0 || i10 == 1) {
            setFinalHorizontal(i9, i9);
        } else {
            setFinalVertical(i9, i9);
        }
        this.N = true;
        return true;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public boolean allowedInBarrier() {
        return true;
    }

    @Deprecated
    public boolean allowsGoneWidget() {
        return this.mAllowsGoneWidget;
    }

    @Override // androidx.constraintlayout.core.widgets.HelperWidget, androidx.constraintlayout.core.widgets.ConstraintWidget
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> map) {
        super.copy(constraintWidget, map);
        Barrier barrier = (Barrier) constraintWidget;
        this.mBarrierType = barrier.mBarrierType;
        this.mAllowsGoneWidget = barrier.mAllowsGoneWidget;
        this.mMargin = barrier.mMargin;
    }

    protected void e() {
        for (int i2 = 0; i2 < this.mWidgetsCount; i2++) {
            ConstraintWidget constraintWidget = this.mWidgets[i2];
            if (this.mAllowsGoneWidget || constraintWidget.allowedInBarrier()) {
                int i3 = this.mBarrierType;
                if (i3 == 0 || i3 == 1) {
                    constraintWidget.d(0, true);
                } else if (i3 == 2 || i3 == 3) {
                    constraintWidget.d(1, true);
                }
            }
        }
    }

    public boolean getAllowsGoneWidget() {
        return this.mAllowsGoneWidget;
    }

    public int getBarrierType() {
        return this.mBarrierType;
    }

    public int getMargin() {
        return this.mMargin;
    }

    public int getOrientation() {
        int i2 = this.mBarrierType;
        if (i2 == 0 || i2 == 1) {
            return 0;
        }
        return (i2 == 2 || i2 == 3) ? 1 : -1;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public boolean isResolvedHorizontally() {
        return this.N;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public boolean isResolvedVertically() {
        return this.N;
    }

    public void setAllowsGoneWidget(boolean z2) {
        this.mAllowsGoneWidget = z2;
    }

    public void setBarrierType(int i2) {
        this.mBarrierType = i2;
    }

    public void setMargin(int i2) {
        this.mMargin = i2;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public String toString() {
        String str = "[Barrier] " + getDebugName() + " {";
        for (int i2 = 0; i2 < this.mWidgetsCount; i2++) {
            ConstraintWidget constraintWidget = this.mWidgets[i2];
            if (i2 > 0) {
                str = str + ", ";
            }
            str = str + constraintWidget.getDebugName();
        }
        return str + i.f9804d;
    }

    public Barrier(String str) {
        setDebugName(str);
    }
}
