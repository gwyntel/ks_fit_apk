package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
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
import kotlin.jvm.internal.ShortCompanionObject;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
public final class Shorts extends ShortsMethodsForWeb {
    public static final int BYTES = 2;
    public static final short MAX_POWER_OF_TWO = 16384;

    private enum LexicographicalComparator implements Comparator<short[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Shorts.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(short[] sArr, short[] sArr2) {
            int iMin = Math.min(sArr.length, sArr2.length);
            for (int i2 = 0; i2 < iMin; i2++) {
                int iCompare = Short.compare(sArr[i2], sArr2[i2]);
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            return sArr.length - sArr2.length;
        }
    }

    @GwtCompatible
    private static class ShortArrayAsList extends AbstractList<Short> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 0;
        final short[] array;
        final int end;
        final int start;

        ShortArrayAsList(short[] sArr) {
            this(sArr, 0, sArr.length);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(@CheckForNull Object obj) {
            return (obj instanceof Short) && Shorts.indexOf(this.array, ((Short) obj).shortValue(), this.start, this.end) != -1;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(@CheckForNull Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ShortArrayAsList)) {
                return super.equals(obj);
            }
            ShortArrayAsList shortArrayAsList = (ShortArrayAsList) obj;
            int size = size();
            if (shortArrayAsList.size() != size) {
                return false;
            }
            for (int i2 = 0; i2 < size; i2++) {
                if (this.array[this.start + i2] != shortArrayAsList.array[shortArrayAsList.start + i2]) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            int iHashCode = 1;
            for (int i2 = this.start; i2 < this.end; i2++) {
                iHashCode = (iHashCode * 31) + Shorts.hashCode(this.array[i2]);
            }
            return iHashCode;
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(@CheckForNull Object obj) {
            int iIndexOf;
            if (!(obj instanceof Short) || (iIndexOf = Shorts.indexOf(this.array, ((Short) obj).shortValue(), this.start, this.end)) < 0) {
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
            if (!(obj instanceof Short) || (iLastIndexOf = Shorts.lastIndexOf(this.array, ((Short) obj).shortValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return iLastIndexOf - this.start;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.end - this.start;
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Short> subList(int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3, size());
            if (i2 == i3) {
                return Collections.emptyList();
            }
            short[] sArr = this.array;
            int i4 = this.start;
            return new ShortArrayAsList(sArr, i2 + i4, i4 + i3);
        }

        short[] toShortArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            StringBuilder sb = new StringBuilder(size() * 6);
            sb.append('[');
            sb.append((int) this.array[this.start]);
            int i2 = this.start;
            while (true) {
                i2++;
                if (i2 >= this.end) {
                    sb.append(']');
                    return sb.toString();
                }
                sb.append(", ");
                sb.append((int) this.array[i2]);
            }
        }

        ShortArrayAsList(short[] sArr, int i2, int i3) {
            this.array = sArr;
            this.start = i2;
            this.end = i3;
        }

        @Override // java.util.AbstractList, java.util.List
        public Short get(int i2) {
            Preconditions.checkElementIndex(i2, size());
            return Short.valueOf(this.array[this.start + i2]);
        }

        @Override // java.util.AbstractList, java.util.List
        public Short set(int i2, Short sh) {
            Preconditions.checkElementIndex(i2, size());
            short[] sArr = this.array;
            int i3 = this.start;
            short s2 = sArr[i3 + i2];
            sArr[i3 + i2] = ((Short) Preconditions.checkNotNull(sh)).shortValue();
            return Short.valueOf(s2);
        }
    }

    private static final class ShortConverter extends Converter<String, Short> implements Serializable {
        static final Converter<String, Short> INSTANCE = new ShortConverter();
        private static final long serialVersionUID = 1;

        private ShortConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "Shorts.stringConverter()";
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.base.Converter
        public String doBackward(Short sh) {
            return sh.toString();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.base.Converter
        public Short doForward(String str) {
            return Short.decode(str);
        }
    }

    private Shorts() {
    }

    public static List<Short> asList(short... sArr) {
        return sArr.length == 0 ? Collections.emptyList() : new ShortArrayAsList(sArr);
    }

    private static int checkNoOverflow(long j2) {
        int i2 = (int) j2;
        Preconditions.checkArgument(j2 == ((long) i2), "the total number of elements (%s) in the arrays must fit in an int", j2);
        return i2;
    }

    public static short checkedCast(long j2) {
        short s2 = (short) j2;
        Preconditions.checkArgument(((long) s2) == j2, "Out of range: %s", j2);
        return s2;
    }

    @InlineMe(replacement = "Short.compare(a, b)")
    public static int compare(short s2, short s3) {
        return Short.compare(s2, s3);
    }

    public static short[] concat(short[]... sArr) {
        long length = 0;
        for (short[] sArr2 : sArr) {
            length += sArr2.length;
        }
        short[] sArr3 = new short[checkNoOverflow(length)];
        int length2 = 0;
        for (short[] sArr4 : sArr) {
            System.arraycopy(sArr4, 0, sArr3, length2, sArr4.length);
            length2 += sArr4.length;
        }
        return sArr3;
    }

    public static short constrainToRange(short s2, short s3, short s4) {
        Preconditions.checkArgument(s3 <= s4, "min (%s) must be less than or equal to max (%s)", (int) s3, (int) s4);
        return s2 < s3 ? s3 : s2 < s4 ? s2 : s4;
    }

    public static boolean contains(short[] sArr, short s2) {
        for (short s3 : sArr) {
            if (s3 == s2) {
                return true;
            }
        }
        return false;
    }

    public static short[] ensureCapacity(short[] sArr, int i2, int i3) {
        Preconditions.checkArgument(i2 >= 0, "Invalid minLength: %s", i2);
        Preconditions.checkArgument(i3 >= 0, "Invalid padding: %s", i3);
        return sArr.length < i2 ? Arrays.copyOf(sArr, i2 + i3) : sArr;
    }

    @GwtIncompatible
    public static short fromByteArray(byte[] bArr) {
        Preconditions.checkArgument(bArr.length >= 2, "array too small: %s < %s", bArr.length, 2);
        return fromBytes(bArr[0], bArr[1]);
    }

    @GwtIncompatible
    public static short fromBytes(byte b2, byte b3) {
        return (short) ((b2 << 8) | (b3 & 255));
    }

    public static int hashCode(short s2) {
        return s2;
    }

    public static int indexOf(short[] sArr, short s2) {
        return indexOf(sArr, s2, 0, sArr.length);
    }

    public static String join(String str, short... sArr) {
        Preconditions.checkNotNull(str);
        if (sArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(sArr.length * 6);
        sb.append((int) sArr[0]);
        for (int i2 = 1; i2 < sArr.length; i2++) {
            sb.append(str);
            sb.append((int) sArr[i2]);
        }
        return sb.toString();
    }

    public static int lastIndexOf(short[] sArr, short s2) {
        return lastIndexOf(sArr, s2, 0, sArr.length);
    }

    public static Comparator<short[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    @GwtIncompatible("Available in GWT! Annotation is to avoid conflict with GWT specialization of base class.")
    public static short max(short... sArr) {
        Preconditions.checkArgument(sArr.length > 0);
        short s2 = sArr[0];
        for (int i2 = 1; i2 < sArr.length; i2++) {
            short s3 = sArr[i2];
            if (s3 > s2) {
                s2 = s3;
            }
        }
        return s2;
    }

    @GwtIncompatible("Available in GWT! Annotation is to avoid conflict with GWT specialization of base class.")
    public static short min(short... sArr) {
        Preconditions.checkArgument(sArr.length > 0);
        short s2 = sArr[0];
        for (int i2 = 1; i2 < sArr.length; i2++) {
            short s3 = sArr[i2];
            if (s3 < s2) {
                s2 = s3;
            }
        }
        return s2;
    }

    public static void reverse(short[] sArr) {
        Preconditions.checkNotNull(sArr);
        reverse(sArr, 0, sArr.length);
    }

    public static void rotate(short[] sArr, int i2) {
        rotate(sArr, i2, 0, sArr.length);
    }

    public static short saturatedCast(long j2) {
        return j2 > 32767 ? ShortCompanionObject.MAX_VALUE : j2 < -32768 ? ShortCompanionObject.MIN_VALUE : (short) j2;
    }

    public static void sortDescending(short[] sArr) {
        Preconditions.checkNotNull(sArr);
        sortDescending(sArr, 0, sArr.length);
    }

    public static Converter<String, Short> stringConverter() {
        return ShortConverter.INSTANCE;
    }

    public static short[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof ShortArrayAsList) {
            return ((ShortArrayAsList) collection).toShortArray();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        short[] sArr = new short[length];
        for (int i2 = 0; i2 < length; i2++) {
            sArr[i2] = ((Number) Preconditions.checkNotNull(array[i2])).shortValue();
        }
        return sArr;
    }

    @GwtIncompatible
    public static byte[] toByteArray(short s2) {
        return new byte[]{(byte) (s2 >> 8), (byte) s2};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int indexOf(short[] sArr, short s2, int i2, int i3) {
        while (i2 < i3) {
            if (sArr[i2] == s2) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int lastIndexOf(short[] sArr, short s2, int i2, int i3) {
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            if (sArr[i4] == s2) {
                return i4;
            }
        }
        return -1;
    }

    public static void rotate(short[] sArr, int i2, int i3, int i4) {
        Preconditions.checkNotNull(sArr);
        Preconditions.checkPositionIndexes(i3, i4, sArr.length);
        if (sArr.length <= 1) {
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
        reverse(sArr, i3, i7);
        reverse(sArr, i7, i4);
        reverse(sArr, i3, i4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0023, code lost:
    
        r0 = r0 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int indexOf(short[] r5, short[] r6) {
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
            short r3 = r5[r3]
            short r4 = r6[r2]
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.primitives.Shorts.indexOf(short[], short[]):int");
    }

    public static void reverse(short[] sArr, int i2, int i3) {
        Preconditions.checkNotNull(sArr);
        Preconditions.checkPositionIndexes(i2, i3, sArr.length);
        for (int i4 = i3 - 1; i2 < i4; i4--) {
            short s2 = sArr[i2];
            sArr[i2] = sArr[i4];
            sArr[i4] = s2;
            i2++;
        }
    }

    public static void sortDescending(short[] sArr, int i2, int i3) {
        Preconditions.checkNotNull(sArr);
        Preconditions.checkPositionIndexes(i2, i3, sArr.length);
        Arrays.sort(sArr, i2, i3);
        reverse(sArr, i2, i3);
    }
}
