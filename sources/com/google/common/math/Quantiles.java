package com.google.common.math;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
public final class Quantiles {

    public static final class Scale {
        private final int scale;

        public ScaleAndIndex index(int i2) {
            return new ScaleAndIndex(this.scale, i2);
        }

        public ScaleAndIndexes indexes(int... iArr) {
            return new ScaleAndIndexes(this.scale, (int[]) iArr.clone());
        }

        private Scale(int i2) {
            Preconditions.checkArgument(i2 > 0, "Quantile scale must be positive");
            this.scale = i2;
        }

        public ScaleAndIndexes indexes(Collection<Integer> collection) {
            return new ScaleAndIndexes(this.scale, Ints.toArray(collection));
        }
    }

    public static final class ScaleAndIndex {
        private final int index;
        private final int scale;

        public double compute(Collection<? extends Number> collection) {
            return computeInPlace(Doubles.toArray(collection));
        }

        public double computeInPlace(double... dArr) {
            Preconditions.checkArgument(dArr.length > 0, "Cannot calculate quantiles of an empty dataset");
            if (Quantiles.containsNaN(dArr)) {
                return Double.NaN;
            }
            long length = this.index * (dArr.length - 1);
            int iDivide = (int) LongMath.divide(length, this.scale, RoundingMode.DOWN);
            int i2 = (int) (length - (iDivide * this.scale));
            Quantiles.selectInPlace(iDivide, dArr, 0, dArr.length - 1);
            if (i2 == 0) {
                return dArr[iDivide];
            }
            int i3 = iDivide + 1;
            Quantiles.selectInPlace(i3, dArr, i3, dArr.length - 1);
            return Quantiles.interpolate(dArr[iDivide], dArr[i3], i2, this.scale);
        }

        private ScaleAndIndex(int i2, int i3) {
            Quantiles.checkIndex(i3, i2);
            this.scale = i2;
            this.index = i3;
        }

        public double compute(double... dArr) {
            return computeInPlace((double[]) dArr.clone());
        }

        public double compute(long... jArr) {
            return computeInPlace(Quantiles.longsToDoubles(jArr));
        }

        public double compute(int... iArr) {
            return computeInPlace(Quantiles.intsToDoubles(iArr));
        }
    }

    public static final class ScaleAndIndexes {
        private final int[] indexes;
        private final int scale;

        public Map<Integer, Double> compute(Collection<? extends Number> collection) {
            return computeInPlace(Doubles.toArray(collection));
        }

        public Map<Integer, Double> computeInPlace(double... dArr) {
            int i2 = 0;
            Preconditions.checkArgument(dArr.length > 0, "Cannot calculate quantiles of an empty dataset");
            if (Quantiles.containsNaN(dArr)) {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                int[] iArr = this.indexes;
                int length = iArr.length;
                while (i2 < length) {
                    linkedHashMap.put(Integer.valueOf(iArr[i2]), Double.valueOf(Double.NaN));
                    i2++;
                }
                return Collections.unmodifiableMap(linkedHashMap);
            }
            int[] iArr2 = this.indexes;
            int[] iArr3 = new int[iArr2.length];
            int[] iArr4 = new int[iArr2.length];
            int[] iArr5 = new int[iArr2.length * 2];
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (i3 >= this.indexes.length) {
                    break;
                }
                long length2 = r5[i3] * (dArr.length - 1);
                int iDivide = (int) LongMath.divide(length2, this.scale, RoundingMode.DOWN);
                int i5 = (int) (length2 - (iDivide * this.scale));
                iArr3[i3] = iDivide;
                iArr4[i3] = i5;
                iArr5[i4] = iDivide;
                int i6 = i4 + 1;
                if (i5 != 0) {
                    iArr5[i6] = iDivide + 1;
                    i4 += 2;
                } else {
                    i4 = i6;
                }
                i3++;
            }
            Arrays.sort(iArr5, 0, i4);
            Quantiles.selectAllInPlace(iArr5, 0, i4 - 1, dArr, 0, dArr.length - 1);
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            while (true) {
                int[] iArr6 = this.indexes;
                if (i2 >= iArr6.length) {
                    return Collections.unmodifiableMap(linkedHashMap2);
                }
                int i7 = iArr3[i2];
                int i8 = iArr4[i2];
                if (i8 == 0) {
                    linkedHashMap2.put(Integer.valueOf(iArr6[i2]), Double.valueOf(dArr[i7]));
                } else {
                    linkedHashMap2.put(Integer.valueOf(iArr6[i2]), Double.valueOf(Quantiles.interpolate(dArr[i7], dArr[i7 + 1], i8, this.scale)));
                }
                i2++;
            }
        }

        private ScaleAndIndexes(int i2, int[] iArr) {
            for (int i3 : iArr) {
                Quantiles.checkIndex(i3, i2);
            }
            Preconditions.checkArgument(iArr.length > 0, "Indexes must be a non empty array");
            this.scale = i2;
            this.indexes = iArr;
        }

        public Map<Integer, Double> compute(double... dArr) {
            return computeInPlace((double[]) dArr.clone());
        }

        public Map<Integer, Double> compute(long... jArr) {
            return computeInPlace(Quantiles.longsToDoubles(jArr));
        }

        public Map<Integer, Double> compute(int... iArr) {
            return computeInPlace(Quantiles.intsToDoubles(iArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkIndex(int i2, int i3) {
        if (i2 < 0 || i2 > i3) {
            throw new IllegalArgumentException("Quantile indexes must be between 0 and the scale, which is " + i3);
        }
    }

    private static int chooseNextSelection(int[] iArr, int i2, int i3, int i4, int i5) {
        if (i2 == i3) {
            return i2;
        }
        int i6 = i4 + i5;
        int i7 = i6 >>> 1;
        while (i3 > i2 + 1) {
            int i8 = (i2 + i3) >>> 1;
            int i9 = iArr[i8];
            if (i9 > i7) {
                i3 = i8;
            } else {
                if (i9 >= i7) {
                    return i8;
                }
                i2 = i8;
            }
        }
        return (i6 - iArr[i2]) - iArr[i3] > 0 ? i3 : i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean containsNaN(double... dArr) {
        for (double d2 : dArr) {
            if (Double.isNaN(d2)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double interpolate(double d2, double d3, double d4, double d5) {
        if (d2 == Double.NEGATIVE_INFINITY) {
            return d3 == Double.POSITIVE_INFINITY ? Double.NaN : Double.NEGATIVE_INFINITY;
        }
        if (d3 == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }
        return d2 + (((d3 - d2) * d4) / d5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double[] intsToDoubles(int[] iArr) {
        int length = iArr.length;
        double[] dArr = new double[length];
        for (int i2 = 0; i2 < length; i2++) {
            dArr[i2] = iArr[i2];
        }
        return dArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double[] longsToDoubles(long[] jArr) {
        int length = jArr.length;
        double[] dArr = new double[length];
        for (int i2 = 0; i2 < length; i2++) {
            dArr[i2] = jArr[i2];
        }
        return dArr;
    }

    public static ScaleAndIndex median() {
        return scale(2).index(1);
    }

    private static void movePivotToStartOfSlice(double[] dArr, int i2, int i3) {
        int i4 = (i2 + i3) >>> 1;
        double d2 = dArr[i3];
        double d3 = dArr[i4];
        boolean z2 = d2 < d3;
        double d4 = dArr[i2];
        boolean z3 = d3 < d4;
        boolean z4 = d2 < d4;
        if (z2 == z3) {
            swap(dArr, i4, i2);
        } else if (z2 != z4) {
            swap(dArr, i2, i3);
        }
    }

    private static int partition(double[] dArr, int i2, int i3) {
        movePivotToStartOfSlice(dArr, i2, i3);
        double d2 = dArr[i2];
        int i4 = i3;
        while (i3 > i2) {
            if (dArr[i3] > d2) {
                swap(dArr, i4, i3);
                i4--;
            }
            i3--;
        }
        swap(dArr, i2, i4);
        return i4;
    }

    public static Scale percentiles() {
        return scale(100);
    }

    public static Scale quartiles() {
        return scale(4);
    }

    public static Scale scale(int i2) {
        return new Scale(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void selectAllInPlace(int[] iArr, int i2, int i3, double[] dArr, int i4, int i5) {
        int iChooseNextSelection = chooseNextSelection(iArr, i2, i3, i4, i5);
        int i6 = iArr[iChooseNextSelection];
        selectInPlace(i6, dArr, i4, i5);
        int i7 = iChooseNextSelection - 1;
        while (i7 >= i2 && iArr[i7] == i6) {
            i7--;
        }
        if (i7 >= i2) {
            selectAllInPlace(iArr, i2, i7, dArr, i4, i6 - 1);
        }
        int i8 = iChooseNextSelection + 1;
        while (i8 <= i3 && iArr[i8] == i6) {
            i8++;
        }
        if (i8 <= i3) {
            selectAllInPlace(iArr, i8, i3, dArr, i6 + 1, i5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void selectInPlace(int i2, double[] dArr, int i3, int i4) {
        if (i2 != i3) {
            while (i4 > i3) {
                int iPartition = partition(dArr, i3, i4);
                if (iPartition >= i2) {
                    i4 = iPartition - 1;
                }
                if (iPartition <= i2) {
                    i3 = iPartition + 1;
                }
            }
            return;
        }
        int i5 = i3;
        for (int i6 = i3 + 1; i6 <= i4; i6++) {
            if (dArr[i5] > dArr[i6]) {
                i5 = i6;
            }
        }
        if (i5 != i3) {
            swap(dArr, i5, i3);
        }
    }

    private static void swap(double[] dArr, int i2, int i3) {
        double d2 = dArr[i2];
        dArr[i2] = dArr[i3];
        dArr[i3] = d2;
    }
}
