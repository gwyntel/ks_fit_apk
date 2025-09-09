package androidx.constraintlayout.core;

import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class LinearSystem {
    public static long ARRAY_ROW_CREATION = 0;
    public static final boolean DEBUG = false;
    private static final boolean DEBUG_CONSTRAINTS = false;
    public static final boolean FULL_DEBUG = false;
    public static final boolean MEASURE = false;
    public static long OPTIMIZED_ARRAY_ROW_CREATION = 0;
    public static boolean OPTIMIZED_ENGINE = false;
    private static int POOL_SIZE = 1000;
    public static boolean SIMPLIFY_SYNONYMS = true;
    public static boolean SKIP_COLUMNS = true;
    public static boolean USE_BASIC_SYNONYMS = true;
    public static boolean USE_DEPENDENCY_ORDERING = false;
    public static boolean USE_SYNONYMS = true;
    public static Metrics sMetrics;

    /* renamed from: b, reason: collision with root package name */
    ArrayRow[] f2559b;

    /* renamed from: e, reason: collision with root package name */
    final Cache f2562e;
    private Row mGoal;
    private Row mTempGoal;
    public boolean hasSimpleDefinition = false;

    /* renamed from: a, reason: collision with root package name */
    int f2558a = 0;
    private HashMap<String, SolverVariable> mVariables = null;
    private int TABLE_SIZE = 32;
    private int mMaxColumns = 32;
    public boolean graphOptimizer = false;
    public boolean newgraphOptimizer = false;
    private boolean[] mAlreadyTestedCandidates = new boolean[32];

    /* renamed from: c, reason: collision with root package name */
    int f2560c = 1;

    /* renamed from: d, reason: collision with root package name */
    int f2561d = 0;
    private int mMaxRows = 32;
    private SolverVariable[] mPoolVariables = new SolverVariable[POOL_SIZE];
    private int mPoolVariablesCount = 0;

    interface Row {
        void addError(SolverVariable solverVariable);

        void clear();

        SolverVariable getKey();

        SolverVariable getPivotCandidate(LinearSystem linearSystem, boolean[] zArr);

        void initFromRow(Row row);

        boolean isEmpty();

        void updateFromFinalVariable(LinearSystem linearSystem, SolverVariable solverVariable, boolean z2);

        void updateFromRow(LinearSystem linearSystem, ArrayRow arrayRow, boolean z2);

        void updateFromSystem(LinearSystem linearSystem);
    }

    class ValuesRow extends ArrayRow {
        public ValuesRow(Cache cache) {
            this.variables = new SolverVariableValues(this, cache);
        }
    }

    public LinearSystem() {
        this.f2559b = null;
        this.f2559b = new ArrayRow[32];
        releaseRows();
        Cache cache = new Cache();
        this.f2562e = cache;
        this.mGoal = new PriorityGoalRow(cache);
        if (OPTIMIZED_ENGINE) {
            this.mTempGoal = new ValuesRow(cache);
        } else {
            this.mTempGoal = new ArrayRow(cache);
        }
    }

    private SolverVariable acquireSolverVariable(SolverVariable.Type type, String str) {
        SolverVariable solverVariable = (SolverVariable) this.f2562e.f2556c.acquire();
        if (solverVariable == null) {
            solverVariable = new SolverVariable(type, str);
            solverVariable.setType(type, str);
        } else {
            solverVariable.reset();
            solverVariable.setType(type, str);
        }
        int i2 = this.mPoolVariablesCount;
        int i3 = POOL_SIZE;
        if (i2 >= i3) {
            int i4 = i3 * 2;
            POOL_SIZE = i4;
            this.mPoolVariables = (SolverVariable[]) Arrays.copyOf(this.mPoolVariables, i4);
        }
        SolverVariable[] solverVariableArr = this.mPoolVariables;
        int i5 = this.mPoolVariablesCount;
        this.mPoolVariablesCount = i5 + 1;
        solverVariableArr[i5] = solverVariable;
        return solverVariable;
    }

    private void addError(ArrayRow arrayRow) {
        arrayRow.addError(this, 0);
    }

    private final void addRow(ArrayRow arrayRow) {
        int i2;
        if (SIMPLIFY_SYNONYMS && arrayRow.f2553e) {
            arrayRow.f2549a.setFinalValue(this, arrayRow.f2550b);
        } else {
            ArrayRow[] arrayRowArr = this.f2559b;
            int i3 = this.f2561d;
            arrayRowArr[i3] = arrayRow;
            SolverVariable solverVariable = arrayRow.f2549a;
            solverVariable.f2570a = i3;
            this.f2561d = i3 + 1;
            solverVariable.updateReferencesWithNewDefinition(this, arrayRow);
        }
        if (SIMPLIFY_SYNONYMS && this.hasSimpleDefinition) {
            int i4 = 0;
            while (i4 < this.f2561d) {
                if (this.f2559b[i4] == null) {
                    System.out.println("WTF");
                }
                ArrayRow arrayRow2 = this.f2559b[i4];
                if (arrayRow2 != null && arrayRow2.f2553e) {
                    arrayRow2.f2549a.setFinalValue(this, arrayRow2.f2550b);
                    if (OPTIMIZED_ENGINE) {
                        this.f2562e.f2554a.release(arrayRow2);
                    } else {
                        this.f2562e.f2555b.release(arrayRow2);
                    }
                    this.f2559b[i4] = null;
                    int i5 = i4 + 1;
                    int i6 = i5;
                    while (true) {
                        i2 = this.f2561d;
                        if (i5 >= i2) {
                            break;
                        }
                        ArrayRow[] arrayRowArr2 = this.f2559b;
                        int i7 = i5 - 1;
                        ArrayRow arrayRow3 = arrayRowArr2[i5];
                        arrayRowArr2[i7] = arrayRow3;
                        SolverVariable solverVariable2 = arrayRow3.f2549a;
                        if (solverVariable2.f2570a == i5) {
                            solverVariable2.f2570a = i7;
                        }
                        i6 = i5;
                        i5++;
                    }
                    if (i6 < i2) {
                        this.f2559b[i6] = null;
                    }
                    this.f2561d = i2 - 1;
                    i4--;
                }
                i4++;
            }
            this.hasSimpleDefinition = false;
        }
    }

    private void addSingleError(ArrayRow arrayRow, int i2) {
        a(arrayRow, i2, 0);
    }

    private void computeValues() {
        for (int i2 = 0; i2 < this.f2561d; i2++) {
            ArrayRow arrayRow = this.f2559b[i2];
            arrayRow.f2549a.computedValue = arrayRow.f2550b;
        }
    }

    public static ArrayRow createRowDimensionPercent(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, float f2) {
        return linearSystem.createRow().f(solverVariable, solverVariable2, f2);
    }

    private SolverVariable createVariable(String str, SolverVariable.Type type) {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.variables++;
        }
        if (this.f2560c + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable solverVariableAcquireSolverVariable = acquireSolverVariable(type, null);
        solverVariableAcquireSolverVariable.setName(str);
        int i2 = this.f2558a + 1;
        this.f2558a = i2;
        this.f2560c++;
        solverVariableAcquireSolverVariable.id = i2;
        if (this.mVariables == null) {
            this.mVariables = new HashMap<>();
        }
        this.mVariables.put(str, solverVariableAcquireSolverVariable);
        this.f2562e.f2557d[this.f2558a] = solverVariableAcquireSolverVariable;
        return solverVariableAcquireSolverVariable;
    }

    private void displayRows() {
        displaySolverVariables();
        String str = "";
        for (int i2 = 0; i2 < this.f2561d; i2++) {
            str = (str + this.f2559b[i2]) + "\n";
        }
        System.out.println(str + this.mGoal + "\n");
    }

    private void displaySolverVariables() {
        System.out.println("Display Rows (" + this.f2561d + "x" + this.f2560c + ")\n");
    }

    private int enforceBFS(Row row) throws Exception {
        for (int i2 = 0; i2 < this.f2561d; i2++) {
            ArrayRow arrayRow = this.f2559b[i2];
            if (arrayRow.f2549a.f2573d != SolverVariable.Type.UNRESTRICTED && arrayRow.f2550b < 0.0f) {
                boolean z2 = false;
                int i3 = 0;
                while (!z2) {
                    Metrics metrics = sMetrics;
                    if (metrics != null) {
                        metrics.bfs++;
                    }
                    i3++;
                    float f2 = Float.MAX_VALUE;
                    int i4 = 0;
                    int i5 = -1;
                    int i6 = -1;
                    int i7 = 0;
                    while (true) {
                        if (i4 >= this.f2561d) {
                            break;
                        }
                        ArrayRow arrayRow2 = this.f2559b[i4];
                        if (arrayRow2.f2549a.f2573d != SolverVariable.Type.UNRESTRICTED && !arrayRow2.f2553e && arrayRow2.f2550b < 0.0f) {
                            int i8 = 9;
                            if (SKIP_COLUMNS) {
                                int currentSize = arrayRow2.variables.getCurrentSize();
                                int i9 = 0;
                                while (i9 < currentSize) {
                                    SolverVariable variable = arrayRow2.variables.getVariable(i9);
                                    float f3 = arrayRow2.variables.get(variable);
                                    if (f3 > 0.0f) {
                                        int i10 = 0;
                                        while (i10 < i8) {
                                            float f4 = variable.f2571b[i10] / f3;
                                            if ((f4 < f2 && i10 == i7) || i10 > i7) {
                                                i7 = i10;
                                                i6 = variable.id;
                                                i5 = i4;
                                                f2 = f4;
                                            }
                                            i10++;
                                            i8 = 9;
                                        }
                                    }
                                    i9++;
                                    i8 = 9;
                                }
                            } else {
                                for (int i11 = 1; i11 < this.f2560c; i11++) {
                                    SolverVariable solverVariable = this.f2562e.f2557d[i11];
                                    float f5 = arrayRow2.variables.get(solverVariable);
                                    if (f5 > 0.0f) {
                                        for (int i12 = 0; i12 < 9; i12++) {
                                            float f6 = solverVariable.f2571b[i12] / f5;
                                            if ((f6 < f2 && i12 == i7) || i12 > i7) {
                                                i7 = i12;
                                                i5 = i4;
                                                i6 = i11;
                                                f2 = f6;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        i4++;
                    }
                    if (i5 != -1) {
                        ArrayRow arrayRow3 = this.f2559b[i5];
                        arrayRow3.f2549a.f2570a = -1;
                        Metrics metrics2 = sMetrics;
                        if (metrics2 != null) {
                            metrics2.pivots++;
                        }
                        arrayRow3.j(this.f2562e.f2557d[i6]);
                        SolverVariable solverVariable2 = arrayRow3.f2549a;
                        solverVariable2.f2570a = i5;
                        solverVariable2.updateReferencesWithNewDefinition(this, arrayRow3);
                    } else {
                        z2 = true;
                    }
                    if (i3 > this.f2560c / 2) {
                        z2 = true;
                    }
                }
                return i3;
            }
        }
        return 0;
    }

    private String getDisplaySize(int i2) {
        int i3 = i2 * 4;
        int i4 = i3 / 1024;
        int i5 = i4 / 1024;
        if (i5 > 0) {
            return "" + i5 + " Mb";
        }
        if (i4 > 0) {
            return "" + i4 + " Kb";
        }
        return "" + i3 + " bytes";
    }

    private String getDisplayStrength(int i2) {
        return i2 == 1 ? "LOW" : i2 == 2 ? "MEDIUM" : i2 == 3 ? "HIGH" : i2 == 4 ? "HIGHEST" : i2 == 5 ? "EQUALITY" : i2 == 8 ? "FIXED" : i2 == 6 ? "BARRIER" : "NONE";
    }

    public static Metrics getMetrics() {
        return sMetrics;
    }

    private void increaseTableSize() {
        int i2 = this.TABLE_SIZE * 2;
        this.TABLE_SIZE = i2;
        this.f2559b = (ArrayRow[]) Arrays.copyOf(this.f2559b, i2);
        Cache cache = this.f2562e;
        cache.f2557d = (SolverVariable[]) Arrays.copyOf(cache.f2557d, this.TABLE_SIZE);
        int i3 = this.TABLE_SIZE;
        this.mAlreadyTestedCandidates = new boolean[i3];
        this.mMaxColumns = i3;
        this.mMaxRows = i3;
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.tableSizeIncrease++;
            metrics.maxTableSize = Math.max(metrics.maxTableSize, i3);
            Metrics metrics2 = sMetrics;
            metrics2.lastTableSize = metrics2.maxTableSize;
        }
    }

    private final int optimize(Row row, boolean z2) {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.optimize++;
        }
        for (int i2 = 0; i2 < this.f2560c; i2++) {
            this.mAlreadyTestedCandidates[i2] = false;
        }
        boolean z3 = false;
        int i3 = 0;
        while (!z3) {
            Metrics metrics2 = sMetrics;
            if (metrics2 != null) {
                metrics2.iterations++;
            }
            i3++;
            if (i3 >= this.f2560c * 2) {
                return i3;
            }
            if (row.getKey() != null) {
                this.mAlreadyTestedCandidates[row.getKey().id] = true;
            }
            SolverVariable pivotCandidate = row.getPivotCandidate(this, this.mAlreadyTestedCandidates);
            if (pivotCandidate != null) {
                boolean[] zArr = this.mAlreadyTestedCandidates;
                int i4 = pivotCandidate.id;
                if (zArr[i4]) {
                    return i3;
                }
                zArr[i4] = true;
            }
            if (pivotCandidate != null) {
                float f2 = Float.MAX_VALUE;
                int i5 = -1;
                for (int i6 = 0; i6 < this.f2561d; i6++) {
                    ArrayRow arrayRow = this.f2559b[i6];
                    if (arrayRow.f2549a.f2573d != SolverVariable.Type.UNRESTRICTED && !arrayRow.f2553e && arrayRow.i(pivotCandidate)) {
                        float f3 = arrayRow.variables.get(pivotCandidate);
                        if (f3 < 0.0f) {
                            float f4 = (-arrayRow.f2550b) / f3;
                            if (f4 < f2) {
                                i5 = i6;
                                f2 = f4;
                            }
                        }
                    }
                }
                if (i5 > -1) {
                    ArrayRow arrayRow2 = this.f2559b[i5];
                    arrayRow2.f2549a.f2570a = -1;
                    Metrics metrics3 = sMetrics;
                    if (metrics3 != null) {
                        metrics3.pivots++;
                    }
                    arrayRow2.j(pivotCandidate);
                    SolverVariable solverVariable = arrayRow2.f2549a;
                    solverVariable.f2570a = i5;
                    solverVariable.updateReferencesWithNewDefinition(this, arrayRow2);
                }
            } else {
                z3 = true;
            }
        }
        return i3;
    }

    private void releaseRows() {
        int i2 = 0;
        if (OPTIMIZED_ENGINE) {
            while (i2 < this.f2561d) {
                ArrayRow arrayRow = this.f2559b[i2];
                if (arrayRow != null) {
                    this.f2562e.f2554a.release(arrayRow);
                }
                this.f2559b[i2] = null;
                i2++;
            }
            return;
        }
        while (i2 < this.f2561d) {
            ArrayRow arrayRow2 = this.f2559b[i2];
            if (arrayRow2 != null) {
                this.f2562e.f2555b.release(arrayRow2);
            }
            this.f2559b[i2] = null;
            i2++;
        }
    }

    void a(ArrayRow arrayRow, int i2, int i3) {
        arrayRow.a(createErrorVariable(i3, null), i2);
    }

    public void addCenterPoint(ConstraintWidget constraintWidget, ConstraintWidget constraintWidget2, float f2, int i2) {
        ConstraintAnchor.Type type = ConstraintAnchor.Type.LEFT;
        SolverVariable solverVariableCreateObjectVariable = createObjectVariable(constraintWidget.getAnchor(type));
        ConstraintAnchor.Type type2 = ConstraintAnchor.Type.TOP;
        SolverVariable solverVariableCreateObjectVariable2 = createObjectVariable(constraintWidget.getAnchor(type2));
        ConstraintAnchor.Type type3 = ConstraintAnchor.Type.RIGHT;
        SolverVariable solverVariableCreateObjectVariable3 = createObjectVariable(constraintWidget.getAnchor(type3));
        ConstraintAnchor.Type type4 = ConstraintAnchor.Type.BOTTOM;
        SolverVariable solverVariableCreateObjectVariable4 = createObjectVariable(constraintWidget.getAnchor(type4));
        SolverVariable solverVariableCreateObjectVariable5 = createObjectVariable(constraintWidget2.getAnchor(type));
        SolverVariable solverVariableCreateObjectVariable6 = createObjectVariable(constraintWidget2.getAnchor(type2));
        SolverVariable solverVariableCreateObjectVariable7 = createObjectVariable(constraintWidget2.getAnchor(type3));
        SolverVariable solverVariableCreateObjectVariable8 = createObjectVariable(constraintWidget2.getAnchor(type4));
        ArrayRow arrayRowCreateRow = createRow();
        double d2 = f2;
        double d3 = i2;
        arrayRowCreateRow.createRowWithAngle(solverVariableCreateObjectVariable2, solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable6, solverVariableCreateObjectVariable8, (float) (Math.sin(d2) * d3));
        addConstraint(arrayRowCreateRow);
        ArrayRow arrayRowCreateRow2 = createRow();
        arrayRowCreateRow2.createRowWithAngle(solverVariableCreateObjectVariable, solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, solverVariableCreateObjectVariable7, (float) (Math.cos(d2) * d3));
        addConstraint(arrayRowCreateRow2);
    }

    public void addCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, float f2, SolverVariable solverVariable3, SolverVariable solverVariable4, int i3, int i4) {
        ArrayRow arrayRowCreateRow = createRow();
        arrayRowCreateRow.d(solverVariable, solverVariable2, i2, f2, solverVariable3, solverVariable4, i3);
        if (i4 != 8) {
            arrayRowCreateRow.addError(this, i4);
        }
        addConstraint(arrayRowCreateRow);
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addConstraint(androidx.constraintlayout.core.ArrayRow r8) {
        /*
            r7 = this;
            if (r8 != 0) goto L3
            return
        L3:
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            r1 = 1
            if (r0 == 0) goto L17
            long r3 = r0.constraints
            long r3 = r3 + r1
            r0.constraints = r3
            boolean r3 = r8.f2553e
            if (r3 == 0) goto L17
            long r3 = r0.simpleconstraints
            long r3 = r3 + r1
            r0.simpleconstraints = r3
        L17:
            int r0 = r7.f2561d
            r3 = 1
            int r0 = r0 + r3
            int r4 = r7.mMaxRows
            if (r0 >= r4) goto L26
            int r0 = r7.f2560c
            int r0 = r0 + r3
            int r4 = r7.mMaxColumns
            if (r0 < r4) goto L29
        L26:
            r7.increaseTableSize()
        L29:
            boolean r0 = r8.f2553e
            r4 = 0
            if (r0 != 0) goto La1
            r8.updateFromSystem(r7)
            boolean r0 = r8.isEmpty()
            if (r0 == 0) goto L38
            return
        L38:
            r8.g()
            boolean r0 = r8.b(r7)
            if (r0 == 0) goto L98
            androidx.constraintlayout.core.SolverVariable r0 = r7.createExtraVariable()
            r8.f2549a = r0
            int r5 = r7.f2561d
            r7.addRow(r8)
            int r6 = r7.f2561d
            int r5 = r5 + r3
            if (r6 != r5) goto L98
            androidx.constraintlayout.core.LinearSystem$Row r4 = r7.mTempGoal
            r4.initFromRow(r8)
            androidx.constraintlayout.core.LinearSystem$Row r4 = r7.mTempGoal
            r7.optimize(r4, r3)
            int r4 = r0.f2570a
            r5 = -1
            if (r4 != r5) goto L99
            androidx.constraintlayout.core.SolverVariable r4 = r8.f2549a
            if (r4 != r0) goto L76
            androidx.constraintlayout.core.SolverVariable r0 = r8.pickPivot(r0)
            if (r0 == 0) goto L76
            androidx.constraintlayout.core.Metrics r4 = androidx.constraintlayout.core.LinearSystem.sMetrics
            if (r4 == 0) goto L73
            long r5 = r4.pivots
            long r5 = r5 + r1
            r4.pivots = r5
        L73:
            r8.j(r0)
        L76:
            boolean r0 = r8.f2553e
            if (r0 != 0) goto L7f
            androidx.constraintlayout.core.SolverVariable r0 = r8.f2549a
            r0.updateReferencesWithNewDefinition(r7, r8)
        L7f:
            boolean r0 = androidx.constraintlayout.core.LinearSystem.OPTIMIZED_ENGINE
            if (r0 == 0) goto L8b
            androidx.constraintlayout.core.Cache r0 = r7.f2562e
            androidx.constraintlayout.core.Pools$Pool r0 = r0.f2554a
            r0.release(r8)
            goto L92
        L8b:
            androidx.constraintlayout.core.Cache r0 = r7.f2562e
            androidx.constraintlayout.core.Pools$Pool r0 = r0.f2555b
            r0.release(r8)
        L92:
            int r0 = r7.f2561d
            int r0 = r0 - r3
            r7.f2561d = r0
            goto L99
        L98:
            r3 = r4
        L99:
            boolean r0 = r8.h()
            if (r0 != 0) goto La0
            return
        La0:
            r4 = r3
        La1:
            if (r4 != 0) goto La6
            r7.addRow(r8)
        La6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.LinearSystem.addConstraint(androidx.constraintlayout.core.ArrayRow):void");
    }

    public ArrayRow addEquality(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        if (USE_BASIC_SYNONYMS && i3 == 8 && solverVariable2.isFinalValue && solverVariable.f2570a == -1) {
            solverVariable.setFinalValue(this, solverVariable2.computedValue + i2);
            return null;
        }
        ArrayRow arrayRowCreateRow = createRow();
        arrayRowCreateRow.createRowEquals(solverVariable, solverVariable2, i2);
        if (i3 != 8) {
            arrayRowCreateRow.addError(this, i3);
        }
        addConstraint(arrayRowCreateRow);
        return arrayRowCreateRow;
    }

    public void addGreaterBarrier(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, boolean z2) {
        ArrayRow arrayRowCreateRow = createRow();
        SolverVariable solverVariableCreateSlackVariable = createSlackVariable();
        solverVariableCreateSlackVariable.strength = 0;
        arrayRowCreateRow.createRowGreaterThan(solverVariable, solverVariable2, solverVariableCreateSlackVariable, i2);
        addConstraint(arrayRowCreateRow);
    }

    public void addGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        ArrayRow arrayRowCreateRow = createRow();
        SolverVariable solverVariableCreateSlackVariable = createSlackVariable();
        solverVariableCreateSlackVariable.strength = 0;
        arrayRowCreateRow.createRowGreaterThan(solverVariable, solverVariable2, solverVariableCreateSlackVariable, i2);
        if (i3 != 8) {
            a(arrayRowCreateRow, (int) (arrayRowCreateRow.variables.get(solverVariableCreateSlackVariable) * (-1.0f)), i3);
        }
        addConstraint(arrayRowCreateRow);
    }

    public void addLowerBarrier(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, boolean z2) {
        ArrayRow arrayRowCreateRow = createRow();
        SolverVariable solverVariableCreateSlackVariable = createSlackVariable();
        solverVariableCreateSlackVariable.strength = 0;
        arrayRowCreateRow.createRowLowerThan(solverVariable, solverVariable2, solverVariableCreateSlackVariable, i2);
        addConstraint(arrayRowCreateRow);
    }

    public void addLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        ArrayRow arrayRowCreateRow = createRow();
        SolverVariable solverVariableCreateSlackVariable = createSlackVariable();
        solverVariableCreateSlackVariable.strength = 0;
        arrayRowCreateRow.createRowLowerThan(solverVariable, solverVariable2, solverVariableCreateSlackVariable, i2);
        if (i3 != 8) {
            a(arrayRowCreateRow, (int) (arrayRowCreateRow.variables.get(solverVariableCreateSlackVariable) * (-1.0f)), i3);
        }
        addConstraint(arrayRowCreateRow);
    }

    public void addRatio(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f2, int i2) {
        ArrayRow arrayRowCreateRow = createRow();
        arrayRowCreateRow.createRowDimensionRatio(solverVariable, solverVariable2, solverVariable3, solverVariable4, f2);
        if (i2 != 8) {
            arrayRowCreateRow.addError(this, i2);
        }
        addConstraint(arrayRowCreateRow);
    }

    public void addSynonym(SolverVariable solverVariable, SolverVariable solverVariable2, int i2) {
        if (solverVariable.f2570a != -1 || i2 != 0) {
            addEquality(solverVariable, solverVariable2, i2, 8);
            return;
        }
        if (solverVariable2.f2576g) {
            solverVariable2 = this.f2562e.f2557d[solverVariable2.f2577h];
        }
        if (solverVariable.f2576g) {
            SolverVariable solverVariable3 = this.f2562e.f2557d[solverVariable.f2577h];
        } else {
            solverVariable.setSynonym(this, solverVariable2, 0.0f);
        }
    }

    void b(Row row) throws Exception {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.minimizeGoal++;
            metrics.maxVariables = Math.max(metrics.maxVariables, this.f2560c);
            Metrics metrics2 = sMetrics;
            metrics2.maxRows = Math.max(metrics2.maxRows, this.f2561d);
        }
        enforceBFS(row);
        optimize(row, false);
        computeValues();
    }

    public SolverVariable createErrorVariable(int i2, String str) {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.errors++;
        }
        if (this.f2560c + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable solverVariableAcquireSolverVariable = acquireSolverVariable(SolverVariable.Type.ERROR, str);
        int i3 = this.f2558a + 1;
        this.f2558a = i3;
        this.f2560c++;
        solverVariableAcquireSolverVariable.id = i3;
        solverVariableAcquireSolverVariable.strength = i2;
        this.f2562e.f2557d[i3] = solverVariableAcquireSolverVariable;
        this.mGoal.addError(solverVariableAcquireSolverVariable);
        return solverVariableAcquireSolverVariable;
    }

    public SolverVariable createExtraVariable() {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.extravariables++;
        }
        if (this.f2560c + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable solverVariableAcquireSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK, null);
        int i2 = this.f2558a + 1;
        this.f2558a = i2;
        this.f2560c++;
        solverVariableAcquireSolverVariable.id = i2;
        this.f2562e.f2557d[i2] = solverVariableAcquireSolverVariable;
        return solverVariableAcquireSolverVariable;
    }

    public SolverVariable createObjectVariable(Object obj) {
        SolverVariable solverVariable = null;
        if (obj == null) {
            return null;
        }
        if (this.f2560c + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        if (obj instanceof ConstraintAnchor) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) obj;
            solverVariable = constraintAnchor.getSolverVariable();
            if (solverVariable == null) {
                constraintAnchor.resetSolverVariable(this.f2562e);
                solverVariable = constraintAnchor.getSolverVariable();
            }
            int i2 = solverVariable.id;
            if (i2 == -1 || i2 > this.f2558a || this.f2562e.f2557d[i2] == null) {
                if (i2 != -1) {
                    solverVariable.reset();
                }
                int i3 = this.f2558a + 1;
                this.f2558a = i3;
                this.f2560c++;
                solverVariable.id = i3;
                solverVariable.f2573d = SolverVariable.Type.UNRESTRICTED;
                this.f2562e.f2557d[i3] = solverVariable;
            }
        }
        return solverVariable;
    }

    public ArrayRow createRow() {
        ArrayRow arrayRow;
        if (OPTIMIZED_ENGINE) {
            arrayRow = (ArrayRow) this.f2562e.f2554a.acquire();
            if (arrayRow == null) {
                arrayRow = new ValuesRow(this.f2562e);
                OPTIMIZED_ARRAY_ROW_CREATION++;
            } else {
                arrayRow.reset();
            }
        } else {
            arrayRow = (ArrayRow) this.f2562e.f2555b.acquire();
            if (arrayRow == null) {
                arrayRow = new ArrayRow(this.f2562e);
                ARRAY_ROW_CREATION++;
            } else {
                arrayRow.reset();
            }
        }
        SolverVariable.a();
        return arrayRow;
    }

    public SolverVariable createSlackVariable() {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.slackvariables++;
        }
        if (this.f2560c + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable solverVariableAcquireSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK, null);
        int i2 = this.f2558a + 1;
        this.f2558a = i2;
        this.f2560c++;
        solverVariableAcquireSolverVariable.id = i2;
        this.f2562e.f2557d[i2] = solverVariableAcquireSolverVariable;
        return solverVariableAcquireSolverVariable;
    }

    public void displayReadableRows() {
        displaySolverVariables();
        String str = " num vars " + this.f2558a + "\n";
        for (int i2 = 0; i2 < this.f2558a + 1; i2++) {
            SolverVariable solverVariable = this.f2562e.f2557d[i2];
            if (solverVariable != null && solverVariable.isFinalValue) {
                str = str + " $[" + i2 + "] => " + solverVariable + " = " + solverVariable.computedValue + "\n";
            }
        }
        String str2 = str + "\n";
        for (int i3 = 0; i3 < this.f2558a + 1; i3++) {
            SolverVariable[] solverVariableArr = this.f2562e.f2557d;
            SolverVariable solverVariable2 = solverVariableArr[i3];
            if (solverVariable2 != null && solverVariable2.f2576g) {
                str2 = str2 + " ~[" + i3 + "] => " + solverVariable2 + " = " + solverVariableArr[solverVariable2.f2577h] + " + " + solverVariable2.f2578i + "\n";
            }
        }
        String str3 = str2 + "\n\n #  ";
        for (int i4 = 0; i4 < this.f2561d; i4++) {
            str3 = (str3 + this.f2559b[i4].l()) + "\n #  ";
        }
        if (this.mGoal != null) {
            str3 = str3 + "Goal: " + this.mGoal + "\n";
        }
        System.out.println(str3);
    }

    public void displayVariablesReadableRows() {
        displaySolverVariables();
        String str = "";
        for (int i2 = 0; i2 < this.f2561d; i2++) {
            if (this.f2559b[i2].f2549a.f2573d == SolverVariable.Type.UNRESTRICTED) {
                str = (str + this.f2559b[i2].l()) + "\n";
            }
        }
        System.out.println(str + this.mGoal + "\n");
    }

    public void fillMetrics(Metrics metrics) {
        sMetrics = metrics;
    }

    public Cache getCache() {
        return this.f2562e;
    }

    public int getMemoryUsed() {
        int iK = 0;
        for (int i2 = 0; i2 < this.f2561d; i2++) {
            ArrayRow arrayRow = this.f2559b[i2];
            if (arrayRow != null) {
                iK += arrayRow.k();
            }
        }
        return iK;
    }

    public int getNumEquations() {
        return this.f2561d;
    }

    public int getNumVariables() {
        return this.f2558a;
    }

    public int getObjectVariableValue(Object obj) {
        SolverVariable solverVariable = ((ConstraintAnchor) obj).getSolverVariable();
        if (solverVariable != null) {
            return (int) (solverVariable.computedValue + 0.5f);
        }
        return 0;
    }

    public void minimize() throws Exception {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            metrics.minimize++;
        }
        if (this.mGoal.isEmpty()) {
            computeValues();
            return;
        }
        if (!this.graphOptimizer && !this.newgraphOptimizer) {
            b(this.mGoal);
            return;
        }
        Metrics metrics2 = sMetrics;
        if (metrics2 != null) {
            metrics2.graphOptimizer++;
        }
        for (int i2 = 0; i2 < this.f2561d; i2++) {
            if (!this.f2559b[i2].f2553e) {
                b(this.mGoal);
                return;
            }
        }
        Metrics metrics3 = sMetrics;
        if (metrics3 != null) {
            metrics3.fullySolved++;
        }
        computeValues();
    }

    public void removeRow(ArrayRow arrayRow) {
        SolverVariable solverVariable;
        int i2;
        if (!arrayRow.f2553e || (solverVariable = arrayRow.f2549a) == null) {
            return;
        }
        int i3 = solverVariable.f2570a;
        if (i3 != -1) {
            while (true) {
                i2 = this.f2561d;
                if (i3 >= i2 - 1) {
                    break;
                }
                ArrayRow[] arrayRowArr = this.f2559b;
                int i4 = i3 + 1;
                ArrayRow arrayRow2 = arrayRowArr[i4];
                SolverVariable solverVariable2 = arrayRow2.f2549a;
                if (solverVariable2.f2570a == i4) {
                    solverVariable2.f2570a = i3;
                }
                arrayRowArr[i3] = arrayRow2;
                i3 = i4;
            }
            this.f2561d = i2 - 1;
        }
        SolverVariable solverVariable3 = arrayRow.f2549a;
        if (!solverVariable3.isFinalValue) {
            solverVariable3.setFinalValue(this, arrayRow.f2550b);
        }
        if (OPTIMIZED_ENGINE) {
            this.f2562e.f2554a.release(arrayRow);
        } else {
            this.f2562e.f2555b.release(arrayRow);
        }
    }

    public void reset() {
        Cache cache;
        int i2 = 0;
        while (true) {
            cache = this.f2562e;
            SolverVariable[] solverVariableArr = cache.f2557d;
            if (i2 >= solverVariableArr.length) {
                break;
            }
            SolverVariable solverVariable = solverVariableArr[i2];
            if (solverVariable != null) {
                solverVariable.reset();
            }
            i2++;
        }
        cache.f2556c.releaseAll(this.mPoolVariables, this.mPoolVariablesCount);
        this.mPoolVariablesCount = 0;
        Arrays.fill(this.f2562e.f2557d, (Object) null);
        HashMap<String, SolverVariable> map = this.mVariables;
        if (map != null) {
            map.clear();
        }
        this.f2558a = 0;
        this.mGoal.clear();
        this.f2560c = 1;
        for (int i3 = 0; i3 < this.f2561d; i3++) {
            ArrayRow arrayRow = this.f2559b[i3];
            if (arrayRow != null) {
                arrayRow.f2551c = false;
            }
        }
        releaseRows();
        this.f2561d = 0;
        if (OPTIMIZED_ENGINE) {
            this.mTempGoal = new ValuesRow(this.f2562e);
        } else {
            this.mTempGoal = new ArrayRow(this.f2562e);
        }
    }

    public void addEquality(SolverVariable solverVariable, int i2) {
        if (USE_BASIC_SYNONYMS && solverVariable.f2570a == -1) {
            float f2 = i2;
            solverVariable.setFinalValue(this, f2);
            for (int i3 = 0; i3 < this.f2558a + 1; i3++) {
                SolverVariable solverVariable2 = this.f2562e.f2557d[i3];
                if (solverVariable2 != null && solverVariable2.f2576g && solverVariable2.f2577h == solverVariable.id) {
                    solverVariable2.setFinalValue(this, solverVariable2.f2578i + f2);
                }
            }
            return;
        }
        int i4 = solverVariable.f2570a;
        if (i4 != -1) {
            ArrayRow arrayRow = this.f2559b[i4];
            if (arrayRow.f2553e) {
                arrayRow.f2550b = i2;
                return;
            }
            if (arrayRow.variables.getCurrentSize() == 0) {
                arrayRow.f2553e = true;
                arrayRow.f2550b = i2;
                return;
            } else {
                ArrayRow arrayRowCreateRow = createRow();
                arrayRowCreateRow.createRowEquals(solverVariable, i2);
                addConstraint(arrayRowCreateRow);
                return;
            }
        }
        ArrayRow arrayRowCreateRow2 = createRow();
        arrayRowCreateRow2.e(solverVariable, i2);
        addConstraint(arrayRowCreateRow2);
    }
}
