package androidx.constraintlayout.core;

import androidx.constraintlayout.core.ArrayRow;
import java.util.Arrays;

/* loaded from: classes.dex */
public class SolverVariableValues implements ArrayRow.ArrayRowVariables {
    private static final boolean DEBUG = false;
    private static final boolean HASH = true;
    private static float epsilon = 0.001f;

    /* renamed from: i, reason: collision with root package name */
    protected final Cache f2589i;
    private final ArrayRow mRow;
    private final int NONE = -1;
    private int SIZE = 16;
    private int HASH_SIZE = 16;

    /* renamed from: a, reason: collision with root package name */
    int[] f2581a = new int[16];

    /* renamed from: b, reason: collision with root package name */
    int[] f2582b = new int[16];

    /* renamed from: c, reason: collision with root package name */
    int[] f2583c = new int[16];

    /* renamed from: d, reason: collision with root package name */
    float[] f2584d = new float[16];

    /* renamed from: e, reason: collision with root package name */
    int[] f2585e = new int[16];

    /* renamed from: f, reason: collision with root package name */
    int[] f2586f = new int[16];

    /* renamed from: g, reason: collision with root package name */
    int f2587g = 0;

    /* renamed from: h, reason: collision with root package name */
    int f2588h = -1;

    SolverVariableValues(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.f2589i = cache;
        clear();
    }

    private void addToHashMap(SolverVariable solverVariable, int i2) {
        int[] iArr;
        int i3 = solverVariable.id % this.HASH_SIZE;
        int[] iArr2 = this.f2581a;
        int i4 = iArr2[i3];
        if (i4 == -1) {
            iArr2[i3] = i2;
        } else {
            while (true) {
                iArr = this.f2582b;
                int i5 = iArr[i4];
                if (i5 == -1) {
                    break;
                } else {
                    i4 = i5;
                }
            }
            iArr[i4] = i2;
        }
        this.f2582b[i2] = -1;
    }

    private void addVariable(int i2, SolverVariable solverVariable, float f2) {
        this.f2583c[i2] = solverVariable.id;
        this.f2584d[i2] = f2;
        this.f2585e[i2] = -1;
        this.f2586f[i2] = -1;
        solverVariable.addToRow(this.mRow);
        solverVariable.usageInRowCount++;
        this.f2587g++;
    }

    private void displayHash() {
        for (int i2 = 0; i2 < this.HASH_SIZE; i2++) {
            if (this.f2581a[i2] != -1) {
                String str = hashCode() + " hash [" + i2 + "] => ";
                int i3 = this.f2581a[i2];
                boolean z2 = false;
                while (!z2) {
                    str = str + " " + this.f2583c[i3];
                    int i4 = this.f2582b[i3];
                    if (i4 != -1) {
                        i3 = i4;
                    } else {
                        z2 = true;
                    }
                }
                System.out.println(str);
            }
        }
    }

    private int findEmptySlot() {
        for (int i2 = 0; i2 < this.SIZE; i2++) {
            if (this.f2583c[i2] == -1) {
                return i2;
            }
        }
        return -1;
    }

    private void increaseSize() {
        int i2 = this.SIZE * 2;
        this.f2583c = Arrays.copyOf(this.f2583c, i2);
        this.f2584d = Arrays.copyOf(this.f2584d, i2);
        this.f2585e = Arrays.copyOf(this.f2585e, i2);
        this.f2586f = Arrays.copyOf(this.f2586f, i2);
        this.f2582b = Arrays.copyOf(this.f2582b, i2);
        for (int i3 = this.SIZE; i3 < i2; i3++) {
            this.f2583c[i3] = -1;
            this.f2582b[i3] = -1;
        }
        this.SIZE = i2;
    }

    private void insertVariable(int i2, SolverVariable solverVariable, float f2) {
        int iFindEmptySlot = findEmptySlot();
        addVariable(iFindEmptySlot, solverVariable, f2);
        if (i2 != -1) {
            this.f2585e[iFindEmptySlot] = i2;
            int[] iArr = this.f2586f;
            iArr[iFindEmptySlot] = iArr[i2];
            iArr[i2] = iFindEmptySlot;
        } else {
            this.f2585e[iFindEmptySlot] = -1;
            if (this.f2587g > 0) {
                this.f2586f[iFindEmptySlot] = this.f2588h;
                this.f2588h = iFindEmptySlot;
            } else {
                this.f2586f[iFindEmptySlot] = -1;
            }
        }
        int i3 = this.f2586f[iFindEmptySlot];
        if (i3 != -1) {
            this.f2585e[i3] = iFindEmptySlot;
        }
        addToHashMap(solverVariable, iFindEmptySlot);
    }

    private void removeFromHashMap(SolverVariable solverVariable) {
        int[] iArr;
        int i2;
        int i3 = solverVariable.id;
        int i4 = i3 % this.HASH_SIZE;
        int[] iArr2 = this.f2581a;
        int i5 = iArr2[i4];
        if (i5 == -1) {
            return;
        }
        if (this.f2583c[i5] == i3) {
            int[] iArr3 = this.f2582b;
            iArr2[i4] = iArr3[i5];
            iArr3[i5] = -1;
            return;
        }
        while (true) {
            iArr = this.f2582b;
            i2 = iArr[i5];
            if (i2 == -1 || this.f2583c[i2] == i3) {
                break;
            } else {
                i5 = i2;
            }
        }
        if (i2 == -1 || this.f2583c[i2] != i3) {
            return;
        }
        iArr[i5] = iArr[i2];
        iArr[i2] = -1;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public void add(SolverVariable solverVariable, float f2, boolean z2) {
        float f3 = epsilon;
        if (f2 <= (-f3) || f2 >= f3) {
            int iIndexOf = indexOf(solverVariable);
            if (iIndexOf == -1) {
                put(solverVariable, f2);
                return;
            }
            float[] fArr = this.f2584d;
            float f4 = fArr[iIndexOf] + f2;
            fArr[iIndexOf] = f4;
            float f5 = epsilon;
            if (f4 <= (-f5) || f4 >= f5) {
                return;
            }
            fArr[iIndexOf] = 0.0f;
            remove(solverVariable, z2);
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public void clear() {
        int i2 = this.f2587g;
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable variable = getVariable(i3);
            if (variable != null) {
                variable.removeFromRow(this.mRow);
            }
        }
        for (int i4 = 0; i4 < this.SIZE; i4++) {
            this.f2583c[i4] = -1;
            this.f2582b[i4] = -1;
        }
        for (int i5 = 0; i5 < this.HASH_SIZE; i5++) {
            this.f2581a[i5] = -1;
        }
        this.f2587g = 0;
        this.f2588h = -1;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public boolean contains(SolverVariable solverVariable) {
        return indexOf(solverVariable) != -1;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public void display() {
        int i2 = this.f2587g;
        System.out.print("{ ");
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable variable = getVariable(i3);
            if (variable != null) {
                System.out.print(variable + " = " + getVariableValue(i3) + " ");
            }
        }
        System.out.println(" }");
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public void divideByAmount(float f2) {
        int i2 = this.f2587g;
        int i3 = this.f2588h;
        for (int i4 = 0; i4 < i2; i4++) {
            float[] fArr = this.f2584d;
            fArr[i3] = fArr[i3] / f2;
            i3 = this.f2586f[i3];
            if (i3 == -1) {
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public float get(SolverVariable solverVariable) {
        int iIndexOf = indexOf(solverVariable);
        if (iIndexOf != -1) {
            return this.f2584d[iIndexOf];
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public int getCurrentSize() {
        return this.f2587g;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public SolverVariable getVariable(int i2) {
        int i3 = this.f2587g;
        if (i3 == 0) {
            return null;
        }
        int i4 = this.f2588h;
        for (int i5 = 0; i5 < i3; i5++) {
            if (i5 == i2 && i4 != -1) {
                return this.f2589i.f2557d[this.f2583c[i4]];
            }
            i4 = this.f2586f[i4];
            if (i4 == -1) {
                break;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public float getVariableValue(int i2) {
        int i3 = this.f2587g;
        int i4 = this.f2588h;
        for (int i5 = 0; i5 < i3; i5++) {
            if (i5 == i2) {
                return this.f2584d[i4];
            }
            i4 = this.f2586f[i4];
            if (i4 == -1) {
                return 0.0f;
            }
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public int indexOf(SolverVariable solverVariable) {
        if (this.f2587g != 0 && solverVariable != null) {
            int i2 = solverVariable.id;
            int i3 = this.f2581a[i2 % this.HASH_SIZE];
            if (i3 == -1) {
                return -1;
            }
            if (this.f2583c[i3] == i2) {
                return i3;
            }
            do {
                i3 = this.f2582b[i3];
                if (i3 == -1) {
                    break;
                }
            } while (this.f2583c[i3] != i2);
            if (i3 != -1 && this.f2583c[i3] == i2) {
                return i3;
            }
        }
        return -1;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public void invert() {
        int i2 = this.f2587g;
        int i3 = this.f2588h;
        for (int i4 = 0; i4 < i2; i4++) {
            float[] fArr = this.f2584d;
            fArr[i3] = fArr[i3] * (-1.0f);
            i3 = this.f2586f[i3];
            if (i3 == -1) {
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public void put(SolverVariable solverVariable, float f2) {
        float f3 = epsilon;
        if (f2 > (-f3) && f2 < f3) {
            remove(solverVariable, true);
            return;
        }
        if (this.f2587g == 0) {
            addVariable(0, solverVariable, f2);
            addToHashMap(solverVariable, 0);
            this.f2588h = 0;
            return;
        }
        int iIndexOf = indexOf(solverVariable);
        if (iIndexOf != -1) {
            this.f2584d[iIndexOf] = f2;
            return;
        }
        if (this.f2587g + 1 >= this.SIZE) {
            increaseSize();
        }
        int i2 = this.f2587g;
        int i3 = this.f2588h;
        int i4 = -1;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = this.f2583c[i3];
            int i7 = solverVariable.id;
            if (i6 == i7) {
                this.f2584d[i3] = f2;
                return;
            }
            if (i6 < i7) {
                i4 = i3;
            }
            i3 = this.f2586f[i3];
            if (i3 == -1) {
                break;
            }
        }
        insertVariable(i4, solverVariable, f2);
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public float remove(SolverVariable solverVariable, boolean z2) {
        int iIndexOf = indexOf(solverVariable);
        if (iIndexOf == -1) {
            return 0.0f;
        }
        removeFromHashMap(solverVariable);
        float f2 = this.f2584d[iIndexOf];
        if (this.f2588h == iIndexOf) {
            this.f2588h = this.f2586f[iIndexOf];
        }
        this.f2583c[iIndexOf] = -1;
        int[] iArr = this.f2585e;
        int i2 = iArr[iIndexOf];
        if (i2 != -1) {
            int[] iArr2 = this.f2586f;
            iArr2[i2] = iArr2[iIndexOf];
        }
        int i3 = this.f2586f[iIndexOf];
        if (i3 != -1) {
            iArr[i3] = iArr[iIndexOf];
        }
        this.f2587g--;
        solverVariable.usageInRowCount--;
        if (z2) {
            solverVariable.removeFromRow(this.mRow);
        }
        return f2;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public int sizeInBytes() {
        return 0;
    }

    public String toString() {
        String str = hashCode() + " { ";
        int i2 = this.f2587g;
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable variable = getVariable(i3);
            if (variable != null) {
                String str2 = str + variable + " = " + getVariableValue(i3) + " ";
                int iIndexOf = indexOf(variable);
                String str3 = str2 + "[p: ";
                String str4 = (this.f2585e[iIndexOf] != -1 ? str3 + this.f2589i.f2557d[this.f2583c[this.f2585e[iIndexOf]]] : str3 + "none") + ", n: ";
                str = (this.f2586f[iIndexOf] != -1 ? str4 + this.f2589i.f2557d[this.f2583c[this.f2586f[iIndexOf]]] : str4 + "none") + "]";
            }
        }
        return str + " }";
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public float use(ArrayRow arrayRow, boolean z2) {
        float f2 = get(arrayRow.f2549a);
        remove(arrayRow.f2549a, z2);
        SolverVariableValues solverVariableValues = (SolverVariableValues) arrayRow.variables;
        int currentSize = solverVariableValues.getCurrentSize();
        int i2 = 0;
        int i3 = 0;
        while (i2 < currentSize) {
            int i4 = solverVariableValues.f2583c[i3];
            if (i4 != -1) {
                add(this.f2589i.f2557d[i4], solverVariableValues.f2584d[i3] * f2, z2);
                i2++;
            }
            i3++;
        }
        return f2;
    }
}
