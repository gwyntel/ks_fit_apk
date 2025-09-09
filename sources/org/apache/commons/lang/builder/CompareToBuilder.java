package org.apache.commons.lang.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Comparator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;

/* loaded from: classes5.dex */
public class CompareToBuilder {
    private int comparison = 0;

    private static void reflectionAppend(Object obj, Object obj2, Class cls, CompareToBuilder compareToBuilder, boolean z2, String[] strArr) throws SecurityException {
        Field[] declaredFields = cls.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        for (int i2 = 0; i2 < declaredFields.length && compareToBuilder.comparison == 0; i2++) {
            Field field = declaredFields[i2];
            if (!ArrayUtils.contains(strArr, field.getName()) && field.getName().indexOf(36) == -1 && ((z2 || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()))) {
                try {
                    compareToBuilder.append(field.get(obj), field.get(obj2));
                } catch (IllegalAccessException unused) {
                    throw new InternalError("Unexpected IllegalAccessException");
                }
            }
        }
    }

    public static int reflectionCompare(Object obj, Object obj2) {
        return reflectionCompare(obj, obj2, false, null, null);
    }

    public CompareToBuilder append(Object obj, Object obj2) {
        return append(obj, obj2, (Comparator) null);
    }

    public CompareToBuilder appendSuper(int i2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = i2;
        return this;
    }

    public int toComparison() {
        return this.comparison;
    }

    public static int reflectionCompare(Object obj, Object obj2, boolean z2) {
        return reflectionCompare(obj, obj2, z2, null, null);
    }

    public CompareToBuilder append(Object obj, Object obj2, Comparator comparator) {
        if (this.comparison != 0 || obj == obj2) {
            return this;
        }
        if (obj == null) {
            this.comparison = -1;
            return this;
        }
        if (obj2 == null) {
            this.comparison = 1;
            return this;
        }
        if (obj.getClass().isArray()) {
            if (obj instanceof long[]) {
                append((long[]) obj, (long[]) obj2);
            } else if (obj instanceof int[]) {
                append((int[]) obj, (int[]) obj2);
            } else if (obj instanceof short[]) {
                append((short[]) obj, (short[]) obj2);
            } else if (obj instanceof char[]) {
                append((char[]) obj, (char[]) obj2);
            } else if (obj instanceof byte[]) {
                append((byte[]) obj, (byte[]) obj2);
            } else if (obj instanceof double[]) {
                append((double[]) obj, (double[]) obj2);
            } else if (obj instanceof float[]) {
                append((float[]) obj, (float[]) obj2);
            } else if (obj instanceof boolean[]) {
                append((boolean[]) obj, (boolean[]) obj2);
            } else {
                append((Object[]) obj, (Object[]) obj2, comparator);
            }
        } else if (comparator == null) {
            this.comparison = ((Comparable) obj).compareTo(obj2);
        } else {
            this.comparison = comparator.compare(obj, obj2);
        }
        return this;
    }

    public static int reflectionCompare(Object obj, Object obj2, Collection collection) {
        return reflectionCompare(obj, obj2, ReflectionToStringBuilder.d(collection));
    }

    public static int reflectionCompare(Object obj, Object obj2, String[] strArr) {
        return reflectionCompare(obj, obj2, false, null, strArr);
    }

    public static int reflectionCompare(Object obj, Object obj2, boolean z2, Class cls) {
        return reflectionCompare(obj, obj2, z2, cls, null);
    }

    public static int reflectionCompare(Object obj, Object obj2, boolean z2, Class cls, String[] strArr) throws SecurityException {
        if (obj == obj2) {
            return 0;
        }
        if (obj != null && obj2 != null) {
            Class<?> superclass = obj.getClass();
            if (superclass.isInstance(obj2)) {
                CompareToBuilder compareToBuilder = new CompareToBuilder();
                reflectionAppend(obj, obj2, superclass, compareToBuilder, z2, strArr);
                while (superclass.getSuperclass() != null && superclass != cls) {
                    superclass = superclass.getSuperclass();
                    reflectionAppend(obj, obj2, superclass, compareToBuilder, z2, strArr);
                }
                return compareToBuilder.toComparison();
            }
            throw new ClassCastException();
        }
        throw null;
    }

    public CompareToBuilder append(long j2, long j3) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = j2 < j3 ? -1 : j2 > j3 ? 1 : 0;
        return this;
    }

    public CompareToBuilder append(int i2, int i3) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = i2 < i3 ? -1 : i2 > i3 ? 1 : 0;
        return this;
    }

    public CompareToBuilder append(short s2, short s3) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = s2 < s3 ? -1 : s2 > s3 ? 1 : 0;
        return this;
    }

    public CompareToBuilder append(char c2, char c3) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = c2 < c3 ? -1 : c2 > c3 ? 1 : 0;
        return this;
    }

    public CompareToBuilder append(byte b2, byte b3) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = b2 < b3 ? -1 : b2 > b3 ? 1 : 0;
        return this;
    }

    public CompareToBuilder append(double d2, double d3) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = NumberUtils.compare(d2, d3);
        return this;
    }

    public CompareToBuilder append(float f2, float f3) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = NumberUtils.compare(f2, f3);
        return this;
    }

    public CompareToBuilder append(boolean z2, boolean z3) {
        if (this.comparison != 0 || z2 == z3) {
            return this;
        }
        if (!z2) {
            this.comparison = -1;
        } else {
            this.comparison = 1;
        }
        return this;
    }

    public CompareToBuilder append(Object[] objArr, Object[] objArr2) {
        return append(objArr, objArr2, (Comparator) null);
    }

    public CompareToBuilder append(Object[] objArr, Object[] objArr2, Comparator comparator) {
        if (this.comparison != 0 || objArr == objArr2) {
            return this;
        }
        if (objArr == null) {
            this.comparison = -1;
            return this;
        }
        if (objArr2 == null) {
            this.comparison = 1;
            return this;
        }
        if (objArr.length != objArr2.length) {
            this.comparison = objArr.length >= objArr2.length ? 1 : -1;
            return this;
        }
        for (int i2 = 0; i2 < objArr.length && this.comparison == 0; i2++) {
            append(objArr[i2], objArr2[i2], comparator);
        }
        return this;
    }

    public CompareToBuilder append(long[] jArr, long[] jArr2) {
        if (this.comparison != 0 || jArr == jArr2) {
            return this;
        }
        if (jArr == null) {
            this.comparison = -1;
            return this;
        }
        if (jArr2 == null) {
            this.comparison = 1;
            return this;
        }
        if (jArr.length != jArr2.length) {
            this.comparison = jArr.length >= jArr2.length ? 1 : -1;
            return this;
        }
        for (int i2 = 0; i2 < jArr.length && this.comparison == 0; i2++) {
            append(jArr[i2], jArr2[i2]);
        }
        return this;
    }

    public CompareToBuilder append(int[] iArr, int[] iArr2) {
        if (this.comparison != 0 || iArr == iArr2) {
            return this;
        }
        if (iArr == null) {
            this.comparison = -1;
            return this;
        }
        if (iArr2 == null) {
            this.comparison = 1;
            return this;
        }
        if (iArr.length != iArr2.length) {
            this.comparison = iArr.length >= iArr2.length ? 1 : -1;
            return this;
        }
        for (int i2 = 0; i2 < iArr.length && this.comparison == 0; i2++) {
            append(iArr[i2], iArr2[i2]);
        }
        return this;
    }

    public CompareToBuilder append(short[] sArr, short[] sArr2) {
        if (this.comparison != 0 || sArr == sArr2) {
            return this;
        }
        if (sArr == null) {
            this.comparison = -1;
            return this;
        }
        if (sArr2 == null) {
            this.comparison = 1;
            return this;
        }
        if (sArr.length != sArr2.length) {
            this.comparison = sArr.length >= sArr2.length ? 1 : -1;
            return this;
        }
        for (int i2 = 0; i2 < sArr.length && this.comparison == 0; i2++) {
            append(sArr[i2], sArr2[i2]);
        }
        return this;
    }

    public CompareToBuilder append(char[] cArr, char[] cArr2) {
        if (this.comparison != 0 || cArr == cArr2) {
            return this;
        }
        if (cArr == null) {
            this.comparison = -1;
            return this;
        }
        if (cArr2 == null) {
            this.comparison = 1;
            return this;
        }
        if (cArr.length != cArr2.length) {
            this.comparison = cArr.length >= cArr2.length ? 1 : -1;
            return this;
        }
        for (int i2 = 0; i2 < cArr.length && this.comparison == 0; i2++) {
            append(cArr[i2], cArr2[i2]);
        }
        return this;
    }

    public CompareToBuilder append(byte[] bArr, byte[] bArr2) {
        if (this.comparison != 0 || bArr == bArr2) {
            return this;
        }
        if (bArr == null) {
            this.comparison = -1;
            return this;
        }
        if (bArr2 == null) {
            this.comparison = 1;
            return this;
        }
        if (bArr.length != bArr2.length) {
            this.comparison = bArr.length >= bArr2.length ? 1 : -1;
            return this;
        }
        for (int i2 = 0; i2 < bArr.length && this.comparison == 0; i2++) {
            append(bArr[i2], bArr2[i2]);
        }
        return this;
    }

    public CompareToBuilder append(double[] dArr, double[] dArr2) {
        if (this.comparison != 0 || dArr == dArr2) {
            return this;
        }
        if (dArr == null) {
            this.comparison = -1;
            return this;
        }
        if (dArr2 == null) {
            this.comparison = 1;
            return this;
        }
        if (dArr.length != dArr2.length) {
            this.comparison = dArr.length >= dArr2.length ? 1 : -1;
            return this;
        }
        for (int i2 = 0; i2 < dArr.length && this.comparison == 0; i2++) {
            append(dArr[i2], dArr2[i2]);
        }
        return this;
    }

    public CompareToBuilder append(float[] fArr, float[] fArr2) {
        if (this.comparison != 0 || fArr == fArr2) {
            return this;
        }
        if (fArr == null) {
            this.comparison = -1;
            return this;
        }
        if (fArr2 == null) {
            this.comparison = 1;
            return this;
        }
        if (fArr.length != fArr2.length) {
            this.comparison = fArr.length >= fArr2.length ? 1 : -1;
            return this;
        }
        for (int i2 = 0; i2 < fArr.length && this.comparison == 0; i2++) {
            append(fArr[i2], fArr2[i2]);
        }
        return this;
    }

    public CompareToBuilder append(boolean[] zArr, boolean[] zArr2) {
        if (this.comparison != 0 || zArr == zArr2) {
            return this;
        }
        if (zArr == null) {
            this.comparison = -1;
            return this;
        }
        if (zArr2 == null) {
            this.comparison = 1;
            return this;
        }
        if (zArr.length != zArr2.length) {
            this.comparison = zArr.length >= zArr2.length ? 1 : -1;
            return this;
        }
        for (int i2 = 0; i2 < zArr.length && this.comparison == 0; i2++) {
            append(zArr[i2], zArr2[i2]);
        }
        return this;
    }
}
