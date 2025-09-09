package androidx.constraintlayout.core;

import androidx.constraintlayout.core.ArrayRow;
import java.util.Arrays;
import java.util.Comparator;

/* loaded from: classes.dex */
public class PriorityGoalRow extends ArrayRow {
    private static final boolean DEBUG = false;
    private static final float epsilon = 1.0E-4f;
    private int TABLE_SIZE;
    private SolverVariable[] arrayGoals;

    /* renamed from: f, reason: collision with root package name */
    GoalVariableAccessor f2564f;

    /* renamed from: g, reason: collision with root package name */
    Cache f2565g;
    private int numGoals;
    private SolverVariable[] sortArray;

    class GoalVariableAccessor {

        /* renamed from: a, reason: collision with root package name */
        SolverVariable f2567a;

        /* renamed from: b, reason: collision with root package name */
        PriorityGoalRow f2568b;

        public GoalVariableAccessor(PriorityGoalRow priorityGoalRow) {
            this.f2568b = priorityGoalRow;
        }

        public void add(SolverVariable solverVariable) {
            for (int i2 = 0; i2 < 9; i2++) {
                float[] fArr = this.f2567a.f2572c;
                float f2 = fArr[i2] + solverVariable.f2572c[i2];
                fArr[i2] = f2;
                if (Math.abs(f2) < 1.0E-4f) {
                    this.f2567a.f2572c[i2] = 0.0f;
                }
            }
        }

        public boolean addToGoal(SolverVariable solverVariable, float f2) {
            boolean z2 = true;
            if (!this.f2567a.inGoal) {
                for (int i2 = 0; i2 < 9; i2++) {
                    float f3 = solverVariable.f2572c[i2];
                    if (f3 != 0.0f) {
                        float f4 = f3 * f2;
                        if (Math.abs(f4) < 1.0E-4f) {
                            f4 = 0.0f;
                        }
                        this.f2567a.f2572c[i2] = f4;
                    } else {
                        this.f2567a.f2572c[i2] = 0.0f;
                    }
                }
                return true;
            }
            for (int i3 = 0; i3 < 9; i3++) {
                float[] fArr = this.f2567a.f2572c;
                float f5 = fArr[i3] + (solverVariable.f2572c[i3] * f2);
                fArr[i3] = f5;
                if (Math.abs(f5) < 1.0E-4f) {
                    this.f2567a.f2572c[i3] = 0.0f;
                } else {
                    z2 = false;
                }
            }
            if (z2) {
                PriorityGoalRow.this.removeGoal(this.f2567a);
            }
            return false;
        }

        public void init(SolverVariable solverVariable) {
            this.f2567a = solverVariable;
        }

        public final boolean isNegative() {
            for (int i2 = 8; i2 >= 0; i2--) {
                float f2 = this.f2567a.f2572c[i2];
                if (f2 > 0.0f) {
                    return false;
                }
                if (f2 < 0.0f) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isNull() {
            for (int i2 = 0; i2 < 9; i2++) {
                if (this.f2567a.f2572c[i2] != 0.0f) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isSmallerThan(SolverVariable solverVariable) {
            int i2 = 8;
            while (true) {
                if (i2 < 0) {
                    break;
                }
                float f2 = solverVariable.f2572c[i2];
                float f3 = this.f2567a.f2572c[i2];
                if (f3 == f2) {
                    i2--;
                } else if (f3 < f2) {
                    return true;
                }
            }
            return false;
        }

        public void reset() {
            Arrays.fill(this.f2567a.f2572c, 0.0f);
        }

        public String toString() {
            String str = "[ ";
            if (this.f2567a != null) {
                for (int i2 = 0; i2 < 9; i2++) {
                    str = str + this.f2567a.f2572c[i2] + " ";
                }
            }
            return str + "] " + this.f2567a;
        }
    }

    public PriorityGoalRow(Cache cache) {
        super(cache);
        this.TABLE_SIZE = 128;
        this.arrayGoals = new SolverVariable[128];
        this.sortArray = new SolverVariable[128];
        this.numGoals = 0;
        this.f2564f = new GoalVariableAccessor(this);
        this.f2565g = cache;
    }

    private final void addToGoal(SolverVariable solverVariable) {
        int i2;
        int i3 = this.numGoals + 1;
        SolverVariable[] solverVariableArr = this.arrayGoals;
        if (i3 > solverVariableArr.length) {
            SolverVariable[] solverVariableArr2 = (SolverVariable[]) Arrays.copyOf(solverVariableArr, solverVariableArr.length * 2);
            this.arrayGoals = solverVariableArr2;
            this.sortArray = (SolverVariable[]) Arrays.copyOf(solverVariableArr2, solverVariableArr2.length * 2);
        }
        SolverVariable[] solverVariableArr3 = this.arrayGoals;
        int i4 = this.numGoals;
        solverVariableArr3[i4] = solverVariable;
        int i5 = i4 + 1;
        this.numGoals = i5;
        if (i5 > 1 && solverVariableArr3[i4].id > solverVariable.id) {
            int i6 = 0;
            while (true) {
                i2 = this.numGoals;
                if (i6 >= i2) {
                    break;
                }
                this.sortArray[i6] = this.arrayGoals[i6];
                i6++;
            }
            Arrays.sort(this.sortArray, 0, i2, new Comparator<SolverVariable>() { // from class: androidx.constraintlayout.core.PriorityGoalRow.1
                @Override // java.util.Comparator
                public int compare(SolverVariable solverVariable2, SolverVariable solverVariable3) {
                    return solverVariable2.id - solverVariable3.id;
                }
            });
            for (int i7 = 0; i7 < this.numGoals; i7++) {
                this.arrayGoals[i7] = this.sortArray[i7];
            }
        }
        solverVariable.inGoal = true;
        solverVariable.addToRow(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeGoal(SolverVariable solverVariable) {
        int i2 = 0;
        while (i2 < this.numGoals) {
            if (this.arrayGoals[i2] == solverVariable) {
                while (true) {
                    int i3 = this.numGoals;
                    if (i2 >= i3 - 1) {
                        this.numGoals = i3 - 1;
                        solverVariable.inGoal = false;
                        return;
                    } else {
                        SolverVariable[] solverVariableArr = this.arrayGoals;
                        int i4 = i2 + 1;
                        solverVariableArr[i2] = solverVariableArr[i4];
                        i2 = i4;
                    }
                }
            } else {
                i2++;
            }
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    public void addError(SolverVariable solverVariable) {
        this.f2564f.init(solverVariable);
        this.f2564f.reset();
        solverVariable.f2572c[solverVariable.strength] = 1.0f;
        addToGoal(solverVariable);
    }

    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    public void clear() {
        this.numGoals = 0;
        this.f2550b = 0.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002e  */
    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.constraintlayout.core.SolverVariable getPivotCandidate(androidx.constraintlayout.core.LinearSystem r5, boolean[] r6) {
        /*
            r4 = this;
            r5 = -1
            r0 = 0
            r1 = r5
        L3:
            int r2 = r4.numGoals
            if (r0 >= r2) goto L32
            androidx.constraintlayout.core.SolverVariable[] r2 = r4.arrayGoals
            r2 = r2[r0]
            int r3 = r2.id
            boolean r3 = r6[r3]
            if (r3 == 0) goto L12
            goto L2f
        L12:
            androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor r3 = r4.f2564f
            r3.init(r2)
            if (r1 != r5) goto L22
            androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor r2 = r4.f2564f
            boolean r2 = r2.isNegative()
            if (r2 == 0) goto L2f
            goto L2e
        L22:
            androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor r2 = r4.f2564f
            androidx.constraintlayout.core.SolverVariable[] r3 = r4.arrayGoals
            r3 = r3[r1]
            boolean r2 = r2.isSmallerThan(r3)
            if (r2 == 0) goto L2f
        L2e:
            r1 = r0
        L2f:
            int r0 = r0 + 1
            goto L3
        L32:
            if (r1 != r5) goto L36
            r5 = 0
            return r5
        L36:
            androidx.constraintlayout.core.SolverVariable[] r5 = r4.arrayGoals
            r5 = r5[r1]
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.PriorityGoalRow.getPivotCandidate(androidx.constraintlayout.core.LinearSystem, boolean[]):androidx.constraintlayout.core.SolverVariable");
    }

    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    public boolean isEmpty() {
        return this.numGoals == 0;
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public String toString() {
        String str = " goal -> (" + this.f2550b + ") : ";
        for (int i2 = 0; i2 < this.numGoals; i2++) {
            this.f2564f.init(this.arrayGoals[i2]);
            str = str + this.f2564f + " ";
        }
        return str;
    }

    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    public void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z2) {
        SolverVariable solverVariable = arrayRow.f2549a;
        if (solverVariable == null) {
            return;
        }
        ArrayRow.ArrayRowVariables arrayRowVariables = arrayRow.variables;
        int currentSize = arrayRowVariables.getCurrentSize();
        for (int i2 = 0; i2 < currentSize; i2++) {
            SolverVariable variable = arrayRowVariables.getVariable(i2);
            float variableValue = arrayRowVariables.getVariableValue(i2);
            this.f2564f.init(variable);
            if (this.f2564f.addToGoal(solverVariable, variableValue)) {
                addToGoal(variable);
            }
            this.f2550b += arrayRow.f2550b * variableValue;
        }
        removeGoal(solverVariable);
    }
}
