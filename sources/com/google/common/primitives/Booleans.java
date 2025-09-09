package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.InlineMe;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
public final class Booleans {

    @GwtCompatible
    private static class BooleanArrayAsList extends AbstractList<Boolean> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 0;
        final boolean[] array;
        final int end;
        final int start;

        BooleanArrayAsList(boolean[] zArr) {
            this(zArr, 0, zArr.length);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(@CheckForNull Object obj) {
            return (obj instanceof Boolean) && Booleans.indexOf(this.array, ((Boolean) obj).booleanValue(), this.start, this.end) != -1;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(@CheckForNull Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof BooleanArrayAsList)) {
                return super.equals(obj);
            }
            BooleanArrayAsList booleanArrayAsList = (BooleanArrayAsList) obj;
            int size = size();
            if (booleanArrayAsList.size() != size) {
                return false;
            }
            for (int i2 = 0; i2 < size; i2++) {
                if (this.array[this.start + i2] != booleanArrayAsList.array[booleanArrayAsList.start + i2]) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            int iHashCode = 1;
            for (int i2 = this.start; i2 < this.end; i2++) {
                iHashCode = (iHashCode * 31) + Booleans.hashCode(this.array[i2]);
            }
            return iHashCode;
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(@CheckForNull Object obj) {
            int iIndexOf;
            if (!(obj instanceof Boolean) || (iIndexOf = Booleans.indexOf(this.array, ((Boolean) obj).booleanValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return iIndexOf - this.start;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(@CheckForNull Object obj) {
            int iLastIndexOf;
            if (!(obj instanceof Boolean) || (iLastIndexOf = Booleans.lastIndexOf(this.array, ((Boolean) obj).booleanValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return iLastIndexOf - this.start;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.end - this.start;
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Boolean> subList(int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3, size());
            if (i2 == i3) {
                return Collections.emptyList();
            }
            boolean[] zArr = this.array;
            int i4 = this.start;
            return new BooleanArrayAsList(zArr, i2 + i4, i4 + i3);
        }

        boolean[] toBooleanArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            StringBuilder sb = new StringBuilder(size() * 7);
            sb.append(this.array[this.start] ? "[true" : "[false");
            int i2 = this.start;
            while (true) {
                i2++;
                if (i2 >= this.end) {
                    sb.append(']');
                    return sb.toString();
                }
                sb.append(this.array[i2] ? ", true" : ", false");
            }
        }

        BooleanArrayAsList(boolean[] zArr, int i2, int i3) {
            this.array = zArr;
            this.start = i2;
            this.end = i3;
        }

        @Override // java.util.AbstractList, java.util.List
        public Boolean get(int i2) {
            Preconditions.checkElementIndex(i2, size());
            return Boolean.valueOf(this.array[this.start + i2]);
        }

        @Override // java.util.AbstractList, java.util.List
        public Boolean set(int i2, Boolean bool) {
            Preconditions.checkElementIndex(i2, size());
            boolean[] zArr = this.array;
            int i3 = this.start;
            boolean z2 = zArr[i3 + i2];
            zArr[i3 + i2] = ((Boolean) Preconditions.checkNotNull(bool)).booleanValue();
            return Boolean.valueOf(z2);
        }
    }

    private enum BooleanComparator implements Comparator<Boolean> {
        TRUE_FIRST(1, "Booleans.trueFirst()"),
        FALSE_FIRST(-1, "Booleans.falseFirst()");

        private final String toString;
        private final int trueValue;

        BooleanComparator(int i2, String str) {
            this.trueValue = i2;
            this.toString = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.toString;
        }

        @Override // java.util.Comparator
        public int compare(Boolean bool, Boolean bool2) {
            return (bool2.booleanValue() ? this.trueValue : 0) - (bool.booleanValue() ? this.trueValue : 0);
        }
    }

    private enum LexicographicalComparator implements Comparator<boolean[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Booleans.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(boolean[] zArr, boolean[] zArr2) {
            int iMin = Math.min(zArr.length, zArr2.length);
            for (int i2 = 0; i2 < iMin; i2++) {
                int iCompare = Boolean.compare(zArr[i2], zArr2[i2]);
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            return zArr.length - zArr2.length;
        }
    }

    private Booleans() {
    }

    public static List<Boolean> asList(boolean... zArr) {
        return zArr.length == 0 ? Collections.emptyList() : new BooleanArrayAsList(zArr);
    }

    private static int checkNoOverflow(long j2) {
        int i2 = (int) j2;
        Preconditions.checkArgument(j2 == ((long) i2), "the total number of elements (%s) in the arrays must fit in an int", j2);
        return i2;
    }

    @InlineMe(replacement = "Boolean.compare(a, b)")
    public static int compare(boolean z2, boolean z3) {
        return Boolean.compare(z2, z3);
    }

    public static boolean[] concat(boolean[]... zArr) {
        long length = 0;
        for (boolean[] zArr2 : zArr) {
            length += zArr2.length;
        }
        boolean[] zArr3 = new boolean[checkNoOverflow(length)];
        int length2 = 0;
        for (boolean[] zArr4 : zArr) {
            System.arraycopy(zArr4, 0, zArr3, length2, zArr4.length);
            length2 += zArr4.length;
        }
        return zArr3;
    }

    public static boolean contains(boolean[] zArr, boolean z2) {
        for (boolean z3 : zArr) {
            if (z3 == z2) {
                return true;
            }
        }
        return false;
    }

    public static int countTrue(boolean... zArr) {
        int i2 = 0;
        for (boolean z2 : zArr) {
            if (z2) {
                i2++;
            }
        }
        return i2;
    }

    public static boolean[] ensureCapacity(boolean[] zArr, int i2, int i3) {
        Preconditions.checkArgument(i2 >= 0, "Invalid minLength: %s", i2);
        Preconditions.checkArgument(i3 >= 0, "Invalid padding: %s", i3);
        return zArr.length < i2 ? Arrays.copyOf(zArr, i2 + i3) : zArr;
    }

    public static Comparator<Boolean> falseFirst() {
        return BooleanComparator.FALSE_FIRST;
    }

    public static int hashCode(boolean z2) {
        return z2 ? 1231 : 1237;
    }

    public static int indexOf(boolean[] zArr, boolean z2) {
        return indexOf(zArr, z2, 0, zArr.length);
    }

    public static String join(String str, boolean... zArr) {
        Preconditions.checkNotNull(str);
        if (zArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(zArr.length * 7);
        sb.append(zArr[0]);
        for (int i2 = 1; i2 < zArr.length; i2++) {
            sb.append(str);
            sb.append(zArr[i2]);
        }
        return sb.toString();
    }

    public static int lastIndexOf(boolean[] zArr, boolean z2) {
        return lastIndexOf(zArr, z2, 0, zArr.length);
    }

    public static Comparator<boolean[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static void reverse(boolean[] zArr) {
        Preconditions.checkNotNull(zArr);
        reverse(zArr, 0, zArr.length);
    }

    public static void rotate(boolean[] zArr, int i2) {
        rotate(zArr, i2, 0, zArr.length);
    }

    public static boolean[] toArray(Collection<Boolean> collection) {
        if (collection instanceof BooleanArrayAsList) {
            return ((BooleanArrayAsList) collection).toBooleanArray();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        boolean[] zArr = new boolean[length];
        for (int i2 = 0; i2 < length; i2++) {
            zArr[i2] = ((Boolean) Preconditions.checkNotNull(array[i2])).booleanValue();
        }
        return zArr;
    }

    public static Comparator<Boolean> trueFirst() {
        return BooleanComparator.TRUE_FIRST;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int indexOf(boolean[] zArr, boolean z2, int i2, int i3) {
        while (i2 < i3) {
            if (zArr[i2] == z2) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int lastIndexOf(boolean[] zArr, boolean z2, int i2, int i3) {
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            if (zArr[i4] == z2) {
                return i4;
            }
        }
        return -1;
    }

    public static void rotate(boolean[] zArr, int i2, int i3, int i4) {
        Preconditions.checkNotNull(zArr);
        Preconditions.checkPositionIndexes(i3, i4, zArr.length);
        if (zArr.length <= 1) {
            return;
        }
        int i5 = i4 - i3;
        int i6 = (-i2) % i5;
        if (i6 < 0) {
            i6 += i5;
        }
        int i7 = i6 + i3;
        if (i7 == i3) {
            return;
        }
        reverse(zArr, i3, i7);
        reverse(zArr, i7, i4);
        reverse(zArr, i3, i4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0023, code lost:
    
        r0 = r0 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int indexOf(boolean[] r5, boolean[] r6) {
        /*
            java.lang.String r0 = "array"
            com.google.common.base.Preconditions.checkNotNull(r5, r0)
            java.lang.String r0 = "target"
            com.google.common.base.Preconditions.checkNotNull(r6, r0)
            int r0 = r6.length
            r1 = 0
            if (r0 != 0) goto Lf
            return r1
        Lf:
            r0 = r1
        L10:
            int r2 = r5.length
            int r3 = r6.length
            int r2 = r2 - r3
            int r2 = r2 + 1
            if (r0 >= r2) goto L2a
            r2 = r1
        L18:
            int r3 = r6.length
            if (r2 >= r3) goto L29
            int r3 = r0 + r2
            boolean r3 = r5[r3]
            boolean r4 = r6[r2]
            if (r3 == r4) goto L26
            int r0 = r0 + 1
            goto L10
        L26:
            int r2 = r2 + 1
            goto L18
        L29:
            return r0
        L2a:
            r5 = -1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.primitives.Booleans.indexOf(boolean[], boolean[]):int");
    }

    public static void reverse(boolean[] zArr, int i2, int i3) {
        Preconditions.checkNotNull(zArr);
        Preconditions.checkPositionIndexes(i2, i3, zArr.length);
        for (int i4 = i3 - 1; i2 < i4; i4--) {
            boolean z2 = zArr[i2];
            zArr[i2] = zArr[i4];
            zArr[i4] = z2;
            i2++;
        }
    }
}
