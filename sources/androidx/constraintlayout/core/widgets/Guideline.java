package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.HashMap;

/* loaded from: classes.dex */
public class Guideline extends ConstraintWidget {
    public static final int HORIZONTAL = 0;
    public static final int RELATIVE_BEGIN = 1;
    public static final int RELATIVE_END = 2;
    public static final int RELATIVE_PERCENT = 0;
    public static final int RELATIVE_UNKNOWN = -1;
    public static final int VERTICAL = 1;
    private boolean resolved;
    protected float N = -1.0f;
    protected int O = -1;
    protected int P = -1;
    protected boolean Q = true;
    private ConstraintAnchor mAnchor = this.mTop;
    private int mOrientation = 0;
    private int mMinimumPosition = 0;

    /* renamed from: androidx.constraintlayout.core.widgets.Guideline$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2920a;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            f2920a = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2920a[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2920a[ConstraintAnchor.Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2920a[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f2920a[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f2920a[ConstraintAnchor.Type.CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f2920a[ConstraintAnchor.Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f2920a[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f2920a[ConstraintAnchor.Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public Guideline() {
        this.f2895f.clear();
        this.f2895f.add(this.mAnchor);
        int length = this.mListAnchors.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.mListAnchors[i2] = this.mAnchor;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem, boolean z2) {
        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) getParent();
        if (constraintWidgetContainer == null) {
            return;
        }
        ConstraintAnchor anchor = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor anchor2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.RIGHT);
        ConstraintWidget constraintWidget = this.mParent;
        boolean z3 = constraintWidget != null && constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (this.mOrientation == 0) {
            anchor = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.TOP);
            anchor2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BOTTOM);
            ConstraintWidget constraintWidget2 = this.mParent;
            z3 = constraintWidget2 != null && constraintWidget2.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        }
        if (this.resolved && this.mAnchor.hasFinalValue()) {
            SolverVariable solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mAnchor);
            linearSystem.addEquality(solverVariableCreateObjectVariable, this.mAnchor.getFinalValue());
            if (this.O != -1) {
                if (z3) {
                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(anchor2), solverVariableCreateObjectVariable, 0, 5);
                }
            } else if (this.P != -1 && z3) {
                SolverVariable solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(anchor2);
                linearSystem.addGreaterThan(solverVariableCreateObjectVariable, linearSystem.createObjectVariable(anchor), 0, 5);
                linearSystem.addGreaterThan(solverVariableCreateObjectVariable2, solverVariableCreateObjectVariable, 0, 5);
            }
            this.resolved = false;
            return;
        }
        if (this.O != -1) {
            SolverVariable solverVariableCreateObjectVariable3 = linearSystem.createObjectVariable(this.mAnchor);
            linearSystem.addEquality(solverVariableCreateObjectVariable3, linearSystem.createObjectVariable(anchor), this.O, 8);
            if (z3) {
                linearSystem.addGreaterThan(linearSystem.createObjectVariable(anchor2), solverVariableCreateObjectVariable3, 0, 5);
                return;
            }
            return;
        }
        if (this.P == -1) {
            if (this.N != -1.0f) {
                linearSystem.addConstraint(LinearSystem.createRowDimensionPercent(linearSystem, linearSystem.createObjectVariable(this.mAnchor), linearSystem.createObjectVariable(anchor2), this.N));
                return;
            }
            return;
        }
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem.createObjectVariable(this.mAnchor);
        SolverVariable solverVariableCreateObjectVariable5 = linearSystem.createObjectVariable(anchor2);
        linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable5, -this.P, 8);
        if (z3) {
            linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, linearSystem.createObjectVariable(anchor), 0, 5);
            linearSystem.addGreaterThan(solverVariableCreateObjectVariable5, solverVariableCreateObjectVariable4, 0, 5);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public boolean allowedInBarrier() {
        return true;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> map) {
        super.copy(constraintWidget, map);
        Guideline guideline = (Guideline) constraintWidget;
        this.N = guideline.N;
        this.O = guideline.O;
        this.P = guideline.P;
        this.Q = guideline.Q;
        setOrientation(guideline.mOrientation);
    }

    public void cyclePosition() {
        if (this.O != -1) {
            g();
        } else if (this.N != -1.0f) {
            f();
        } else if (this.P != -1) {
            e();
        }
    }

    void e() {
        int x2 = getX();
        if (this.mOrientation == 0) {
            x2 = getY();
        }
        setGuideBegin(x2);
    }

    void f() {
        int width = getParent().getWidth() - getX();
        if (this.mOrientation == 0) {
            width = getParent().getHeight() - getY();
        }
        setGuideEnd(width);
    }

    void g() {
        float x2 = getX() / getParent().getWidth();
        if (this.mOrientation == 0) {
            x2 = getY() / getParent().getHeight();
        }
        setGuidePercent(x2);
    }

    public ConstraintAnchor getAnchor() {
        return this.mAnchor;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getRelativeBegin() {
        return this.O;
    }

    public int getRelativeBehaviour() {
        if (this.N != -1.0f) {
            return 0;
        }
        if (this.O != -1) {
            return 1;
        }
        return this.P != -1 ? 2 : -1;
    }

    public int getRelativeEnd() {
        return this.P;
    }

    public float getRelativePercent() {
        return this.N;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public String getType() {
        return "Guideline";
    }

    public boolean isPercent() {
        return this.N != -1.0f && this.O == -1 && this.P == -1;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public boolean isResolvedHorizontally() {
        return this.resolved;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public boolean isResolvedVertically() {
        return this.resolved;
    }

    public void setFinalValue(int i2) {
        this.mAnchor.setFinalValue(i2);
        this.resolved = true;
    }

    public void setGuideBegin(int i2) {
        if (i2 > -1) {
            this.N = -1.0f;
            this.O = i2;
            this.P = -1;
        }
    }

    public void setGuideEnd(int i2) {
        if (i2 > -1) {
            this.N = -1.0f;
            this.O = -1;
            this.P = i2;
        }
    }

    public void setGuidePercent(int i2) {
        setGuidePercent(i2 / 100.0f);
    }

    public void setMinimumPosition(int i2) {
        this.mMinimumPosition = i2;
    }

    public void setOrientation(int i2) {
        if (this.mOrientation == i2) {
            return;
        }
        this.mOrientation = i2;
        this.f2895f.clear();
        if (this.mOrientation == 1) {
            this.mAnchor = this.mLeft;
        } else {
            this.mAnchor = this.mTop;
        }
        this.f2895f.add(this.mAnchor);
        int length = this.mListAnchors.length;
        for (int i3 = 0; i3 < length; i3++) {
            this.mListAnchors[i3] = this.mAnchor;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public void updateFromSolver(LinearSystem linearSystem, boolean z2) {
        if (getParent() == null) {
            return;
        }
        int objectVariableValue = linearSystem.getObjectVariableValue(this.mAnchor);
        if (this.mOrientation == 1) {
            setX(objectVariableValue);
            setY(0);
            setHeight(getParent().getHeight());
            setWidth(0);
            return;
        }
        setX(0);
        setY(objectVariableValue);
        setWidth(getParent().getWidth());
        setHeight(0);
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        int i2 = AnonymousClass1.f2920a[type.ordinal()];
        if (i2 == 1 || i2 == 2) {
            if (this.mOrientation == 1) {
                return this.mAnchor;
            }
            return null;
        }
        if ((i2 == 3 || i2 == 4) && this.mOrientation == 0) {
            return this.mAnchor;
        }
        return null;
    }

    public void setGuidePercent(float f2) {
        if (f2 > -1.0f) {
            this.N = f2;
            this.O = -1;
            this.P = -1;
        }
    }
}
