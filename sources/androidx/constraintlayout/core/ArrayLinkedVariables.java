package androidx.constraintlayout.core;

import androidx.constraintlayout.core.ArrayRow;
import java.util.Arrays;

/* loaded from: classes.dex */
public class ArrayLinkedVariables implements ArrayRow.ArrayRowVariables {
    private static final boolean DEBUG = false;
    private static final boolean FULL_NEW_CHECK = false;
    private static float epsilon = 0.001f;

    /* renamed from: b, reason: collision with root package name */
    protected final Cache f2548b;
    private final ArrayRow mRow;

    /* renamed from: a, reason: collision with root package name */
    int f2547a = 0;
    private int ROW_SIZE = 8;
    private SolverVariable candidate = null;
    private int[] mArrayIndices = new int[8];
    private int[] mArrayNextIndices = new int[8];
    private float[] mArrayValues = new float[8];
    private int mHead = -1;
    private int mLast = -1;
    private boolean mDidFillOnce = false;

    ArrayLinkedVariables(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.f2548b = cache;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public void add(SolverVariable solverVariable, float f2, boolean z2) {
        float f3 = epsilon;
        if (f2 <= (-f3) || f2 >= f3) {
            int i2 = this.mHead;
            if (i2 == -1) {
                this.mHead = 0;
                this.mArrayValues[0] = f2;
                this.mArrayIndices[0] = solverVariable.id;
                this.mArrayNextIndices[0] = -1;
                solverVariable.usageInRowCount++;
                solverVariable.addToRow(this.mRow);
                this.f2547a++;
                if (this.mDidFillOnce) {
                    return;
                }
                int i3 = this.mLast + 1;
                this.mLast = i3;
                int[] iArr = this.mArrayIndices;
                if (i3 >= iArr.length) {
                    this.mDidFillOnce = true;
                    this.mLast = iArr.length - 1;
                    return;
                }
                return;
            }
            int i4 = -1;
            for (int i5 = 0; i2 != -1 && i5 < this.f2547a; i5++) {
                int i6 = this.mArrayIndices[i2];
                int i7 = solverVariable.id;
                if (i6 == i7) {
                    float[] fArr = this.mArrayValues;
                    float f4 = fArr[i2] + f2;
                    float f5 = epsilon;
                    if (f4 > (-f5) && f4 < f5) {
                        f4 = 0.0f;
                    }
                    fArr[i2] = f4;
                    if (f4 == 0.0f) {
                        if (i2 == this.mHead) {
                            this.mHead = this.mArrayNextIndices[i2];
                        } else {
                            int[] iArr2 = this.mArrayNextIndices;
                            iArr2[i4] = iArr2[i2];
                        }
                        if (z2) {
                            solverVariable.removeFromRow(this.mRow);
                        }
                        if (this.mDidFillOnce) {
                            this.mLast = i2;
                        }
                        solverVariable.usageInRowCount--;
                        this.f2547a--;
                        return;
                    }
                    return;
                }
                if (i6 < i7) {
                    i4 = i2;
                }
                i2 = this.mArrayNextIndices[i2];
            }
            int length = this.mLast;
            int i8 = length + 1;
            if (this.mDidFillOnce) {
                int[] iArr3 = this.mArrayIndices;
                if (iArr3[length] != -1) {
                    length = iArr3.length;
                }
            } else {
                length = i8;
            }
            int[] iArr4 = this.mArrayIndices;
            if (length >= iArr4.length && this.f2547a < iArr4.length) {
                int i9 = 0;
                while (true) {
                    int[] iArr5 = this.mArrayIndices;
                    if (i9 >= iArr5.length) {
                        break;
                    }
                    if (iArr5[i9] == -1) {
                        length = i9;
                        break;
                    }
                    i9++;
                }
            }
            int[] iArr6 = this.mArrayIndices;
            if (length >= iArr6.length) {
                length = iArr6.length;
                int i10 = this.ROW_SIZE * 2;
                this.ROW_SIZE = i10;
                this.mDidFillOnce = false;
                this.mLast = length - 1;
                this.mArrayValues = Arrays.copyOf(this.mArrayValues, i10);
                this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
                this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
            }
            this.mArrayIndices[length] = solverVariable.id;
            this.mArrayValues[length] = f2;
            if (i4 != -1) {
                int[] iArr7 = this.mArrayNextIndices;
                iArr7[length] = iArr7[i4];
                iArr7[i4] = length;
            } else {
                this.mArrayNextIndices[length] = this.mHead;
                this.mHead = length;
            }
            solverVariable.usageInRowCount++;
            solverVariable.addToRow(this.mRow);
            this.f2547a++;
            if (!this.mDidFillOnce) {
                this.mLast++;
            }
            int i11 = this.mLast;
            int[] iArr8 = this.mArrayIndices;
            if (i11 >= iArr8.length) {
                this.mDidFillOnce = true;
                this.mLast = iArr8.length - 1;
            }
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public final void clear() {
        int i2 = this.mHead;
        for (int i3 = 0; i2 != -1 && i3 < this.f2547a; i3++) {
            SolverVariable solverVariable = this.f2548b.f2557d[this.mArrayIndices[i2]];
            if (solverVariable != null) {
                solverVariable.removeFromRow(this.mRow);
            }
            i2 = this.mArrayNextIndices[i2];
        }
        this.mHead = -1;
        this.mLast = -1;
        this.mDidFillOnce = false;
        this.f2547a = 0;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public boolean contains(SolverVariable solverVariable) {
        int i2 = this.mHead;
        if (i2 == -1) {
            return false;
        }
        for (int i3 = 0; i2 != -1 && i3 < this.f2547a; i3++) {
            if (this.mArrayIndices[i2] == solverVariable.id) {
                return true;
            }
            i2 = this.mArrayNextIndices[i2];
        }
        return false;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public void display() {
        int i2 = this.f2547a;
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
        int i2 = this.mHead;
        for (int i3 = 0; i2 != -1 && i3 < this.f2547a; i3++) {
            float[] fArr = this.mArrayValues;
            fArr[i2] = fArr[i2] / f2;
            i2 = this.mArrayNextIndices[i2];
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public final float get(SolverVariable solverVariable) {
        int i2 = this.mHead;
        for (int i3 = 0; i2 != -1 && i3 < this.f2547a; i3++) {
            if (this.mArrayIndices[i2] == solverVariable.id) {
                return this.mArrayValues[i2];
            }
            i2 = this.mArrayNextIndices[i2];
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public int getCurrentSize() {
        return this.f2547a;
    }

    public int getHead() {
        return this.mHead;
    }

    public final int getId(int i2) {
        return this.mArrayIndices[i2];
    }

    public final int getNextIndice(int i2) {
        return this.mArrayNextIndices[i2];
    }

    public final float getValue(int i2) {
        return this.mArrayValues[i2];
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public SolverVariable getVariable(int i2) {
        int i3 = this.mHead;
        for (int i4 = 0; i3 != -1 && i4 < this.f2547a; i4++) {
            if (i4 == i2) {
                return this.f2548b.f2557d[this.mArrayIndices[i3]];
            }
            i3 = this.mArrayNextIndices[i3];
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public float getVariableValue(int i2) {
        int i3 = this.mHead;
        for (int i4 = 0; i3 != -1 && i4 < this.f2547a; i4++) {
            if (i4 == i2) {
                return this.mArrayValues[i3];
            }
            i3 = this.mArrayNextIndices[i3];
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public int indexOf(SolverVariable solverVariable) {
        int i2 = this.mHead;
        if (i2 == -1) {
            return -1;
        }
        for (int i3 = 0; i2 != -1 && i3 < this.f2547a; i3++) {
            if (this.mArrayIndices[i2] == solverVariable.id) {
                return i2;
            }
            i2 = this.mArrayNextIndices[i2];
        }
        return -1;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public void invert() {
        int i2 = this.mHead;
        for (int i3 = 0; i2 != -1 && i3 < this.f2547a; i3++) {
            float[] fArr = this.mArrayValues;
            fArr[i2] = fArr[i2] * (-1.0f);
            i2 = this.mArrayNextIndices[i2];
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public final void put(SolverVariable solverVariable, float f2) {
        if (f2 == 0.0f) {
            remove(solverVariable, true);
            return;
        }
        int i2 = this.mHead;
        if (i2 == -1) {
            this.mHead = 0;
            this.mArrayValues[0] = f2;
            this.mArrayIndices[0] = solverVariable.id;
            this.mArrayNextIndices[0] = -1;
            solverVariable.usageInRowCount++;
            solverVariable.addToRow(this.mRow);
            this.f2547a++;
            if (this.mDidFillOnce) {
                return;
            }
            int i3 = this.mLast + 1;
            this.mLast = i3;
            int[] iArr = this.mArrayIndices;
            if (i3 >= iArr.length) {
                this.mDidFillOnce = true;
                this.mLast = iArr.length - 1;
                return;
            }
            return;
        }
        int i4 = -1;
        for (int i5 = 0; i2 != -1 && i5 < this.f2547a; i5++) {
            int i6 = this.mArrayIndices[i2];
            int i7 = solverVariable.id;
            if (i6 == i7) {
                this.mArrayValues[i2] = f2;
                return;
            }
            if (i6 < i7) {
                i4 = i2;
            }
            i2 = this.mArrayNextIndices[i2];
        }
        int length = this.mLast;
        int i8 = length + 1;
        if (this.mDidFillOnce) {
            int[] iArr2 = this.mArrayIndices;
            if (iArr2[length] != -1) {
                length = iArr2.length;
            }
        } else {
            length = i8;
        }
        int[] iArr3 = this.mArrayIndices;
        if (length >= iArr3.length && this.f2547a < iArr3.length) {
            int i9 = 0;
            while (true) {
                int[] iArr4 = this.mArrayIndices;
                if (i9 >= iArr4.length) {
                    break;
                }
                if (iArr4[i9] == -1) {
                    length = i9;
                    break;
                }
                i9++;
            }
        }
        int[] iArr5 = this.mArrayIndices;
        if (length >= iArr5.length) {
            length = iArr5.length;
            int i10 = this.ROW_SIZE * 2;
            this.ROW_SIZE = i10;
            this.mDidFillOnce = false;
            this.mLast = length - 1;
            this.mArrayValues = Arrays.copyOf(this.mArrayValues, i10);
            this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
            this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
        }
        this.mArrayIndices[length] = solverVariable.id;
        this.mArrayValues[length] = f2;
        if (i4 != -1) {
            int[] iArr6 = this.mArrayNextIndices;
            iArr6[length] = iArr6[i4];
            iArr6[i4] = length;
        } else {
            this.mArrayNextIndices[length] = this.mHead;
            this.mHead = length;
        }
        solverVariable.usageInRowCount++;
        solverVariable.addToRow(this.mRow);
        int i11 = this.f2547a + 1;
        this.f2547a = i11;
        if (!this.mDidFillOnce) {
            this.mLast++;
        }
        int[] iArr7 = this.mArrayIndices;
        if (i11 >= iArr7.length) {
            this.mDidFillOnce = true;
        }
        if (this.mLast >= iArr7.length) {
            this.mDidFillOnce = true;
            this.mLast = iArr7.length - 1;
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public final float remove(SolverVariable solverVariable, boolean z2) {
        if (this.candidate == solverVariable) {
            this.candidate = null;
        }
        int i2 = this.mHead;
        if (i2 == -1) {
            return 0.0f;
        }
        int i3 = 0;
        int i4 = -1;
        while (i2 != -1 && i3 < this.f2547a) {
            if (this.mArrayIndices[i2] == solverVariable.id) {
                if (i2 == this.mHead) {
                    this.mHead = this.mArrayNextIndices[i2];
                } else {
                    int[] iArr = this.mArrayNextIndices;
                    iArr[i4] = iArr[i2];
                }
                if (z2) {
                    solverVariable.removeFromRow(this.mRow);
                }
                solverVariable.usageInRowCount--;
                this.f2547a--;
                this.mArrayIndices[i2] = -1;
                if (this.mDidFillOnce) {
                    this.mLast = i2;
                }
                return this.mArrayValues[i2];
            }
            i3++;
            i4 = i2;
            i2 = this.mArrayNextIndices[i2];
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public int sizeInBytes() {
        return (this.mArrayIndices.length * 12) + 36;
    }

    public String toString() {
        int i2 = this.mHead;
        String str = "";
        for (int i3 = 0; i2 != -1 && i3 < this.f2547a; i3++) {
            str = ((str + " -> ") + this.mArrayValues[i2] + " : ") + this.f2548b.f2557d[this.mArrayIndices[i2]];
            i2 = this.mArrayNextIndices[i2];
        }
        return str;
    }

    @Override // androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
    public float use(ArrayRow arrayRow, boolean z2) {
        float f2 = get(arrayRow.f2549a);
        remove(arrayRow.f2549a, z2);
        ArrayRow.ArrayRowVariables arrayRowVariables = arrayRow.variables;
        int currentSize = arrayRowVariables.getCurrentSize();
        for (int i2 = 0; i2 < currentSize; i2++) {
            SolverVariable variable = arrayRowVariables.getVariable(i2);
            add(variable, arrayRowVariables.get(variable) * f2, z2);
        }
        return f2;
    }
}
