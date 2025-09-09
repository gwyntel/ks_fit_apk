package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@Immutable
@GwtCompatible
/* loaded from: classes3.dex */
public final class ImmutableDoubleArray implements Serializable {
    private static final ImmutableDoubleArray EMPTY = new ImmutableDoubleArray(new double[0]);
    private final double[] array;
    private final int end;
    private final transient int start;

    static class AsList extends AbstractList<Double> implements RandomAccess, Serializable {
        private final ImmutableDoubleArray parent;

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(@CheckForNull Object obj) {
            return indexOf(obj) >= 0;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof AsList) {
                return this.parent.equals(((AsList) obj).parent);
            }
            if (!(obj instanceof List)) {
                return false;
            }
            List list = (List) obj;
            if (size() != list.size()) {
                return false;
            }
            int i2 = this.parent.start;
            for (Object obj2 : list) {
                if (obj2 instanceof Double) {
                    int i3 = i2 + 1;
                    if (ImmutableDoubleArray.areEqual(this.parent.array[i2], ((Double) obj2).doubleValue())) {
                        i2 = i3;
                    }
                }
                return false;
            }
            return true;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            return this.parent.hashCode();
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(@CheckForNull Object obj) {
            if (obj instanceof Double) {
                return this.parent.indexOf(((Double) obj).doubleValue());
            }
            return -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(@CheckForNull Object obj) {
            if (obj instanceof Double) {
                return this.parent.lastIndexOf(((Double) obj).doubleValue());
            }
            return -1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.parent.length();
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Double> subList(int i2, int i3) {
            return this.parent.subArray(i2, i3).asList();
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return this.parent.toString();
        }

        private AsList(ImmutableDoubleArray immutableDoubleArray) {
            this.parent = immutableDoubleArray;
        }

        @Override // java.util.AbstractList, java.util.List
        public Double get(int i2) {
            return Double.valueOf(this.parent.get(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean areEqual(double d2, double d3) {
        return Double.doubleToLongBits(d2) == Double.doubleToLongBits(d3);
    }

    public static Builder builder(int i2) {
        Preconditions.checkArgument(i2 >= 0, "Invalid initialCapacity: %s", i2);
        return new Builder(i2);
    }

    public static ImmutableDoubleArray copyOf(double[] dArr) {
        return dArr.length == 0 ? EMPTY : new ImmutableDoubleArray(Arrays.copyOf(dArr, dArr.length));
    }

    private boolean isPartialView() {
        return this.start > 0 || this.end < this.array.length;
    }

    public static ImmutableDoubleArray of() {
        return EMPTY;
    }

    public List<Double> asList() {
        return new AsList();
    }

    public boolean contains(double d2) {
        return indexOf(d2) >= 0;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ImmutableDoubleArray)) {
            return false;
        }
        ImmutableDoubleArray immutableDoubleArray = (ImmutableDoubleArray) obj;
        if (length() != immutableDoubleArray.length()) {
            return false;
        }
        for (int i2 = 0; i2 < length(); i2++) {
            if (!areEqual(get(i2), immutableDoubleArray.get(i2))) {
                return false;
            }
        }
        return true;
    }

    public double get(int i2) {
        Preconditions.checkElementIndex(i2, length());
        return this.array[this.start + i2];
    }

    public int hashCode() {
        int iHashCode = 1;
        for (int i2 = this.start; i2 < this.end; i2++) {
            iHashCode = (iHashCode * 31) + Doubles.hashCode(this.array[i2]);
        }
        return iHashCode;
    }

    public int indexOf(double d2) {
        for (int i2 = this.start; i2 < this.end; i2++) {
            if (areEqual(this.array[i2], d2)) {
                return i2 - this.start;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.end == this.start;
    }

    public int lastIndexOf(double d2) {
        int i2 = this.end;
        do {
            i2--;
            if (i2 < this.start) {
                return -1;
            }
        } while (!areEqual(this.array[i2], d2));
        return i2 - this.start;
    }

    public int length() {
        return this.end - this.start;
    }

    Object readResolve() {
        return isEmpty() ? EMPTY : this;
    }

    public ImmutableDoubleArray subArray(int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i3, length());
        if (i2 == i3) {
            return EMPTY;
        }
        double[] dArr = this.array;
        int i4 = this.start;
        return new ImmutableDoubleArray(dArr, i2 + i4, i4 + i3);
    }

    public double[] toArray() {
        return Arrays.copyOfRange(this.array, this.start, this.end);
    }

    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder(length() * 5);
        sb.append('[');
        sb.append(this.array[this.start]);
        int i2 = this.start;
        while (true) {
            i2++;
            if (i2 >= this.end) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            sb.append(this.array[i2]);
        }
    }

    public ImmutableDoubleArray trimmed() {
        return isPartialView() ? new ImmutableDoubleArray(toArray()) : this;
    }

    Object writeReplace() {
        return trimmed();
    }

    private ImmutableDoubleArray(double[] dArr) {
        this(dArr, 0, dArr.length);
    }

    public static ImmutableDoubleArray of(double d2) {
        return new ImmutableDoubleArray(new double[]{d2});
    }

    public static final class Builder {
        private double[] array;
        private int count = 0;

        Builder(int i2) {
            this.array = new double[i2];
        }

        private void ensureRoomFor(int i2) {
            int i3 = this.count + i2;
            double[] dArr = this.array;
            if (i3 > dArr.length) {
                this.array = Arrays.copyOf(dArr, expandedCapacity(dArr.length, i3));
            }
        }

        private static int expandedCapacity(int i2, int i3) {
            if (i3 < 0) {
                throw new AssertionError("cannot store more than MAX_VALUE elements");
            }
            int iHighestOneBit = i2 + (i2 >> 1) + 1;
            if (iHighestOneBit < i3) {
                iHighestOneBit = Integer.highestOneBit(i3 - 1) << 1;
            }
            if (iHighestOneBit < 0) {
                return Integer.MAX_VALUE;
            }
            return iHighestOneBit;
        }

        @CanIgnoreReturnValue
        public Builder add(double d2) {
            ensureRoomFor(1);
            double[] dArr = this.array;
            int i2 = this.count;
            dArr[i2] = d2;
            this.count = i2 + 1;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addAll(double[] dArr) {
            ensureRoomFor(dArr.length);
            System.arraycopy(dArr, 0, this.array, this.count, dArr.length);
            this.count += dArr.length;
            return this;
        }

        public ImmutableDoubleArray build() {
            if (this.count == 0) {
                return ImmutableDoubleArray.EMPTY;
            }
            return new ImmutableDoubleArray(this.array, 0, this.count);
        }

        @CanIgnoreReturnValue
        public Builder addAll(Iterable<Double> iterable) {
            if (iterable instanceof Collection) {
                return addAll((Collection<Double>) iterable);
            }
            Iterator<Double> it = iterable.iterator();
            while (it.hasNext()) {
                add(it.next().doubleValue());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addAll(Collection<Double> collection) {
            ensureRoomFor(collection.size());
            for (Double d2 : collection) {
                double[] dArr = this.array;
                int i2 = this.count;
                this.count = i2 + 1;
                dArr[i2] = d2.doubleValue();
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addAll(ImmutableDoubleArray immutableDoubleArray) {
            ensureRoomFor(immutableDoubleArray.length());
            System.arraycopy(immutableDoubleArray.array, immutableDoubleArray.start, this.array, this.count, immutableDoubleArray.length());
            this.count += immutableDoubleArray.length();
            return this;
        }
    }

    private ImmutableDoubleArray(double[] dArr, int i2, int i3) {
        this.array = dArr;
        this.start = i2;
        this.end = i3;
    }

    public static Builder builder() {
        return new Builder(10);
    }

    public static ImmutableDoubleArray of(double d2, double d3) {
        return new ImmutableDoubleArray(new double[]{d2, d3});
    }

    public static ImmutableDoubleArray copyOf(Collection<Double> collection) {
        return collection.isEmpty() ? EMPTY : new ImmutableDoubleArray(Doubles.toArray(collection));
    }

    public static ImmutableDoubleArray of(double d2, double d3, double d4) {
        return new ImmutableDoubleArray(new double[]{d2, d3, d4});
    }

    public static ImmutableDoubleArray copyOf(Iterable<Double> iterable) {
        if (iterable instanceof Collection) {
            return copyOf((Collection<Double>) iterable);
        }
        return builder().addAll(iterable).build();
    }

    public static ImmutableDoubleArray of(double d2, double d3, double d4, double d5) {
        return new ImmutableDoubleArray(new double[]{d2, d3, d4, d5});
    }

    public static ImmutableDoubleArray of(double d2, double d3, double d4, double d5, double d6) {
        return new ImmutableDoubleArray(new double[]{d2, d3, d4, d5, d6});
    }

    public static ImmutableDoubleArray of(double d2, double d3, double d4, double d5, double d6, double d7) {
        return new ImmutableDoubleArray(new double[]{d2, d3, d4, d5, d6, d7});
    }

    public static ImmutableDoubleArray of(double d2, double... dArr) {
        Preconditions.checkArgument(dArr.length <= 2147483646, "the total number of elements must fit in an int");
        double[] dArr2 = new double[dArr.length + 1];
        dArr2[0] = d2;
        System.arraycopy(dArr, 0, dArr2, 1, dArr.length);
        return new ImmutableDoubleArray(dArr2);
    }
}
