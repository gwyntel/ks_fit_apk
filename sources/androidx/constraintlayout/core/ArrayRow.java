package androidx.constraintlayout.core;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import com.aliyun.alink.linksdk.alcs.coap.resources.LinkFormat;
import com.facebook.appevents.UserDataStore;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ArrayRow implements LinearSystem.Row {
    private static final boolean DEBUG = false;
    private static final boolean FULL_NEW_CHECK = false;

    /* renamed from: a, reason: collision with root package name */
    SolverVariable f2549a = null;

    /* renamed from: b, reason: collision with root package name */
    float f2550b = 0.0f;

    /* renamed from: c, reason: collision with root package name */
    boolean f2551c = false;

    /* renamed from: d, reason: collision with root package name */
    ArrayList f2552d = new ArrayList();

    /* renamed from: e, reason: collision with root package name */
    boolean f2553e = false;
    public ArrayRowVariables variables;

    public interface ArrayRowVariables {
        void add(SolverVariable solverVariable, float f2, boolean z2);

        void clear();

        boolean contains(SolverVariable solverVariable);

        void display();

        void divideByAmount(float f2);

        float get(SolverVariable solverVariable);

        int getCurrentSize();

        SolverVariable getVariable(int i2);

        float getVariableValue(int i2);

        int indexOf(SolverVariable solverVariable);

        void invert();

        void put(SolverVariable solverVariable, float f2);

        float remove(SolverVariable solverVariable, boolean z2);

        int sizeInBytes();

        float use(ArrayRow arrayRow, boolean z2);
    }

    public ArrayRow() {
    }

    private boolean isNew(SolverVariable solverVariable, LinearSystem linearSystem) {
        return solverVariable.usageInRowCount <= 1;
    }

    private SolverVariable pickPivotInVariables(boolean[] zArr, SolverVariable solverVariable) {
        SolverVariable.Type type;
        int currentSize = this.variables.getCurrentSize();
        SolverVariable solverVariable2 = null;
        float f2 = 0.0f;
        for (int i2 = 0; i2 < currentSize; i2++) {
            float variableValue = this.variables.getVariableValue(i2);
            if (variableValue < 0.0f) {
                SolverVariable variable = this.variables.getVariable(i2);
                if ((zArr == null || !zArr[variable.id]) && variable != solverVariable && (((type = variable.f2573d) == SolverVariable.Type.SLACK || type == SolverVariable.Type.ERROR) && variableValue < f2)) {
                    f2 = variableValue;
                    solverVariable2 = variable;
                }
            }
        }
        return solverVariable2;
    }

    ArrayRow a(SolverVariable solverVariable, int i2) {
        this.variables.put(solverVariable, i2);
        return this;
    }

    public ArrayRow addError(LinearSystem linearSystem, int i2) {
        this.variables.put(linearSystem.createErrorVariable(i2, LinkFormat.END_POINT), 1.0f);
        this.variables.put(linearSystem.createErrorVariable(i2, UserDataStore.EMAIL), -1.0f);
        return this;
    }

    boolean b(LinearSystem linearSystem) {
        boolean z2;
        SolverVariable solverVariableC = c(linearSystem);
        if (solverVariableC == null) {
            z2 = true;
        } else {
            j(solverVariableC);
            z2 = false;
        }
        if (this.variables.getCurrentSize() == 0) {
            this.f2553e = true;
        }
        return z2;
    }

    SolverVariable c(LinearSystem linearSystem) {
        int currentSize = this.variables.getCurrentSize();
        SolverVariable solverVariable = null;
        float f2 = 0.0f;
        float f3 = 0.0f;
        boolean z2 = false;
        boolean z3 = false;
        SolverVariable solverVariable2 = null;
        for (int i2 = 0; i2 < currentSize; i2++) {
            float variableValue = this.variables.getVariableValue(i2);
            SolverVariable variable = this.variables.getVariable(i2);
            if (variable.f2573d == SolverVariable.Type.UNRESTRICTED) {
                if (solverVariable == null || f2 > variableValue) {
                    boolean zIsNew = isNew(variable, linearSystem);
                    z2 = zIsNew;
                    f2 = variableValue;
                    solverVariable = variable;
                } else if (!z2 && isNew(variable, linearSystem)) {
                    f2 = variableValue;
                    solverVariable = variable;
                    z2 = true;
                }
            } else if (solverVariable == null && variableValue < 0.0f) {
                if (solverVariable2 == null || f3 > variableValue) {
                    boolean zIsNew2 = isNew(variable, linearSystem);
                    z3 = zIsNew2;
                    f3 = variableValue;
                    solverVariable2 = variable;
                } else if (!z3 && isNew(variable, linearSystem)) {
                    f3 = variableValue;
                    solverVariable2 = variable;
                    z3 = true;
                }
            }
        }
        return solverVariable != null ? solverVariable : solverVariable2;
    }

    @Override // androidx.constraintlayout.core.LinearSystem.Row
    public void clear() {
        this.variables.clear();
        this.f2549a = null;
        this.f2550b = 0.0f;
    }

    public ArrayRow createRowDimensionRatio(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f2) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, 1.0f);
        this.variables.put(solverVariable3, f2);
        this.variables.put(solverVariable4, -f2);
        return this;
    }

    public ArrayRow createRowEqualDimension(float f2, float f3, float f4, SolverVariable solverVariable, int i2, SolverVariable solverVariable2, int i3, SolverVariable solverVariable3, int i4, SolverVariable solverVariable4, int i5) {
        if (f3 == 0.0f || f2 == f4) {
            this.f2550b = ((-i2) - i3) + i4 + i5;
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else {
            float f5 = (f2 / f3) / (f4 / f3);
            this.f2550b = ((-i2) - i3) + (i4 * f5) + (i5 * f5);
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, f5);
            this.variables.put(solverVariable3, -f5);
        }
        return this;
    }

    public ArrayRow createRowEqualMatchDimensions(float f2, float f3, float f4, SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4) {
        this.f2550b = 0.0f;
        if (f3 == 0.0f || f2 == f4) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else if (f2 == 0.0f) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
        } else if (f4 == 0.0f) {
            this.variables.put(solverVariable3, 1.0f);
            this.variables.put(solverVariable4, -1.0f);
        } else {
            float f5 = (f2 / f3) / (f4 / f3);
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, f5);
            this.variables.put(solverVariable3, -f5);
        }
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, int i2) {
        if (i2 < 0) {
            this.f2550b = i2 * (-1);
            this.variables.put(solverVariable, 1.0f);
        } else {
            this.f2550b = i2;
            this.variables.put(solverVariable, -1.0f);
        }
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i2) {
        boolean z2 = false;
        if (i2 != 0) {
            if (i2 < 0) {
                i2 *= -1;
                z2 = true;
            }
            this.f2550b = i2;
        }
        if (z2) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, 1.0f);
        }
        return this;
    }

    public ArrayRow createRowLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i2) {
        boolean z2 = false;
        if (i2 != 0) {
            if (i2 < 0) {
                i2 *= -1;
                z2 = true;
            }
            this.f2550b = i2;
        }
        if (z2) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, 1.0f);
        } else {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        }
        return this;
    }

    public ArrayRow createRowWithAngle(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f2) {
        this.variables.put(solverVariable3, 0.5f);
        this.variables.put(solverVariable4, 0.5f);
        this.variables.put(solverVariable, -0.5f);
        this.variables.put(solverVariable2, -0.5f);
        this.f2550b = -f2;
        return this;
    }

    ArrayRow d(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, float f2, SolverVariable solverVariable3, SolverVariable solverVariable4, int i3) {
        if (solverVariable2 == solverVariable3) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable2, -2.0f);
            return this;
        }
        if (f2 == 0.5f) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            if (i2 > 0 || i3 > 0) {
                this.f2550b = (-i2) + i3;
            }
        } else if (f2 <= 0.0f) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.f2550b = i2;
        } else if (f2 >= 1.0f) {
            this.variables.put(solverVariable4, -1.0f);
            this.variables.put(solverVariable3, 1.0f);
            this.f2550b = -i3;
        } else {
            float f3 = 1.0f - f2;
            this.variables.put(solverVariable, f3 * 1.0f);
            this.variables.put(solverVariable2, f3 * (-1.0f));
            this.variables.put(solverVariable3, (-1.0f) * f2);
            this.variables.put(solverVariable4, 1.0f * f2);
            if (i2 > 0 || i3 > 0) {
                this.f2550b = ((-i2) * f3) + (i3 * f2);
            }
        }
        return this;
    }

    ArrayRow e(SolverVariable solverVariable, int i2) {
        this.f2549a = solverVariable;
        float f2 = i2;
        solverVariable.computedValue = f2;
        this.f2550b = f2;
        this.f2553e = true;
        return this;
    }

    ArrayRow f(SolverVariable solverVariable, SolverVariable solverVariable2, float f2) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, f2);
        return this;
    }

    void g() {
        float f2 = this.f2550b;
        if (f2 < 0.0f) {
            this.f2550b = f2 * (-1.0f);
            this.variables.invert();
        }
    }

    @Override // androidx.constraintlayout.core.LinearSystem.Row
    public SolverVariable getKey() {
        return this.f2549a;
    }

    @Override // androidx.constraintlayout.core.LinearSystem.Row
    public SolverVariable getPivotCandidate(LinearSystem linearSystem, boolean[] zArr) {
        return pickPivotInVariables(zArr, null);
    }

    boolean h() {
        SolverVariable solverVariable = this.f2549a;
        return solverVariable != null && (solverVariable.f2573d == SolverVariable.Type.UNRESTRICTED || this.f2550b >= 0.0f);
    }

    boolean i(SolverVariable solverVariable) {
        return this.variables.contains(solverVariable);
    }

    @Override // androidx.constraintlayout.core.LinearSystem.Row
    public void initFromRow(LinearSystem.Row row) {
        if (row instanceof ArrayRow) {
            ArrayRow arrayRow = (ArrayRow) row;
            this.f2549a = null;
            this.variables.clear();
            for (int i2 = 0; i2 < arrayRow.variables.getCurrentSize(); i2++) {
                this.variables.add(arrayRow.variables.getVariable(i2), arrayRow.variables.getVariableValue(i2), true);
            }
        }
    }

    @Override // androidx.constraintlayout.core.LinearSystem.Row
    public boolean isEmpty() {
        return this.f2549a == null && this.f2550b == 0.0f && this.variables.getCurrentSize() == 0;
    }

    void j(SolverVariable solverVariable) {
        SolverVariable solverVariable2 = this.f2549a;
        if (solverVariable2 != null) {
            this.variables.put(solverVariable2, -1.0f);
            this.f2549a.f2570a = -1;
            this.f2549a = null;
        }
        float fRemove = this.variables.remove(solverVariable, true) * (-1.0f);
        this.f2549a = solverVariable;
        if (fRemove == 1.0f) {
            return;
        }
        this.f2550b /= fRemove;
        this.variables.divideByAmount(fRemove);
    }

    int k() {
        return (this.f2549a != null ? 4 : 0) + 8 + this.variables.sizeInBytes();
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.lang.String l() {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.ArrayRow.l():java.lang.String");
    }

    public SolverVariable pickPivot(SolverVariable solverVariable) {
        return pickPivotInVariables(null, solverVariable);
    }

    public void reset() {
        this.f2549a = null;
        this.variables.clear();
        this.f2550b = 0.0f;
        this.f2553e = false;
    }

    public String toString() {
        return l();
    }

    @Override // androidx.constraintlayout.core.LinearSystem.Row
    public void updateFromFinalVariable(LinearSystem linearSystem, SolverVariable solverVariable, boolean z2) {
        if (solverVariable == null || !solverVariable.isFinalValue) {
            return;
        }
        this.f2550b += solverVariable.computedValue * this.variables.get(solverVariable);
        this.variables.remove(solverVariable, z2);
        if (z2) {
            solverVariable.removeFromRow(this);
        }
        if (LinearSystem.SIMPLIFY_SYNONYMS && this.variables.getCurrentSize() == 0) {
            this.f2553e = true;
            linearSystem.hasSimpleDefinition = true;
        }
    }

    @Override // androidx.constraintlayout.core.LinearSystem.Row
    public void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z2) {
        this.f2550b += arrayRow.f2550b * this.variables.use(arrayRow, z2);
        if (z2) {
            arrayRow.f2549a.removeFromRow(this);
        }
        if (LinearSystem.SIMPLIFY_SYNONYMS && this.f2549a != null && this.variables.getCurrentSize() == 0) {
            this.f2553e = true;
            linearSystem.hasSimpleDefinition = true;
        }
    }

    public void updateFromSynonymVariable(LinearSystem linearSystem, SolverVariable solverVariable, boolean z2) {
        if (solverVariable == null || !solverVariable.f2576g) {
            return;
        }
        float f2 = this.variables.get(solverVariable);
        this.f2550b += solverVariable.f2578i * f2;
        this.variables.remove(solverVariable, z2);
        if (z2) {
            solverVariable.removeFromRow(this);
        }
        this.variables.add(linearSystem.f2562e.f2557d[solverVariable.f2577h], f2, z2);
        if (LinearSystem.SIMPLIFY_SYNONYMS && this.variables.getCurrentSize() == 0) {
            this.f2553e = true;
            linearSystem.hasSimpleDefinition = true;
        }
    }

    @Override // androidx.constraintlayout.core.LinearSystem.Row
    public void updateFromSystem(LinearSystem linearSystem) {
        if (linearSystem.f2559b.length == 0) {
            return;
        }
        boolean z2 = false;
        while (!z2) {
            int currentSize = this.variables.getCurrentSize();
            for (int i2 = 0; i2 < currentSize; i2++) {
                SolverVariable variable = this.variables.getVariable(i2);
                if (variable.f2570a != -1 || variable.isFinalValue || variable.f2576g) {
                    this.f2552d.add(variable);
                }
            }
            int size = this.f2552d.size();
            if (size > 0) {
                for (int i3 = 0; i3 < size; i3++) {
                    SolverVariable solverVariable = (SolverVariable) this.f2552d.get(i3);
                    if (solverVariable.isFinalValue) {
                        updateFromFinalVariable(linearSystem, solverVariable, true);
                    } else if (solverVariable.f2576g) {
                        updateFromSynonymVariable(linearSystem, solverVariable, true);
                    } else {
                        updateFromRow(linearSystem, linearSystem.f2559b[solverVariable.f2570a], true);
                    }
                }
                this.f2552d.clear();
            } else {
                z2 = true;
            }
        }
        if (LinearSystem.SIMPLIFY_SYNONYMS && this.f2549a != null && this.variables.getCurrentSize() == 0) {
            this.f2553e = true;
            linearSystem.hasSimpleDefinition = true;
        }
    }

    @Override // androidx.constraintlayout.core.LinearSystem.Row
    public void addError(SolverVariable solverVariable) {
        int i2 = solverVariable.strength;
        float f2 = 1.0f;
        if (i2 != 1) {
            if (i2 == 2) {
                f2 = 1000.0f;
            } else if (i2 == 3) {
                f2 = 1000000.0f;
            } else if (i2 == 4) {
                f2 = 1.0E9f;
            } else if (i2 == 5) {
                f2 = 1.0E12f;
            }
        }
        this.variables.put(solverVariable, f2);
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, SolverVariable solverVariable2, int i2) {
        boolean z2 = false;
        if (i2 != 0) {
            if (i2 < 0) {
                i2 *= -1;
                z2 = true;
            }
            this.f2550b = i2;
        }
        if (!z2) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
        } else {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
        }
        return this;
    }

    public ArrayRow(Cache cache) {
        this.variables = new ArrayLinkedVariables(this, cache);
    }

    public ArrayRow createRowGreaterThan(SolverVariable solverVariable, int i2, SolverVariable solverVariable2) {
        this.f2550b = i2;
        this.variables.put(solverVariable, -1.0f);
        return this;
    }
}
