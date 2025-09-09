package org.apache.commons.lang.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import org.apache.commons.lang.ArrayUtils;

/* loaded from: classes5.dex */
public class EqualsBuilder {
    private boolean isEquals = true;

    private static void reflectionAppend(Object obj, Object obj2, Class cls, EqualsBuilder equalsBuilder, boolean z2, String[] strArr) throws SecurityException {
        Field[] declaredFields = cls.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        for (int i2 = 0; i2 < declaredFields.length && equalsBuilder.isEquals; i2++) {
            Field field = declaredFields[i2];
            if (!ArrayUtils.contains(strArr, field.getName()) && field.getName().indexOf(36) == -1 && ((z2 || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()))) {
                try {
                    equalsBuilder.append(field.get(obj), field.get(obj2));
                } catch (IllegalAccessException unused) {
                    throw new InternalError("Unexpected IllegalAccessException");
                }
            }
        }
    }

    public static boolean reflectionEquals(Object obj, Object obj2) {
        return reflectionEquals(obj, obj2, false, null, null);
    }

    protected void a(boolean z2) {
        this.isEquals = z2;
    }

    public EqualsBuilder append(Object obj, Object obj2) {
        if (!this.isEquals || obj == obj2) {
            return this;
        }
        if (obj == null || obj2 == null) {
            a(false);
            return this;
        }
        if (!obj.getClass().isArray()) {
            this.isEquals = obj.equals(obj2);
        } else if (obj.getClass() != obj2.getClass()) {
            a(false);
        } else if (obj instanceof long[]) {
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
            append((Object[]) obj, (Object[]) obj2);
        }
        return this;
    }

    public EqualsBuilder appendSuper(boolean z2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = z2;
        return this;
    }

    public boolean isEquals() {
        return this.isEquals;
    }

    public void reset() {
        this.isEquals = true;
    }

    public static boolean reflectionEquals(Object obj, Object obj2, Collection collection) {
        return reflectionEquals(obj, obj2, ReflectionToStringBuilder.d(collection));
    }

    public static boolean reflectionEquals(Object obj, Object obj2, String[] strArr) {
        return reflectionEquals(obj, obj2, false, null, strArr);
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z2) {
        return reflectionEquals(obj, obj2, z2, null, null);
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z2, Class cls) {
        return reflectionEquals(obj, obj2, z2, cls, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean reflectionEquals(java.lang.Object r11, java.lang.Object r12, boolean r13, java.lang.Class r14, java.lang.String[] r15) throws java.lang.SecurityException {
        /*
            if (r11 != r12) goto L4
            r11 = 1
            return r11
        L4:
            r0 = 0
            if (r11 == 0) goto L56
            if (r12 != 0) goto La
            goto L56
        La:
            java.lang.Class r1 = r11.getClass()
            java.lang.Class r2 = r12.getClass()
            boolean r3 = r1.isInstance(r12)
            if (r3 == 0) goto L1f
            boolean r3 = r2.isInstance(r11)
            if (r3 != 0) goto L2d
            goto L2c
        L1f:
            boolean r3 = r2.isInstance(r11)
            if (r3 == 0) goto L56
            boolean r3 = r1.isInstance(r12)
            if (r3 != 0) goto L2c
            goto L2d
        L2c:
            r1 = r2
        L2d:
            org.apache.commons.lang.builder.EqualsBuilder r10 = new org.apache.commons.lang.builder.EqualsBuilder
            r10.<init>()
            r4 = r11
            r5 = r12
            r6 = r1
            r7 = r10
            r8 = r13
            r9 = r15
            reflectionAppend(r4, r5, r6, r7, r8, r9)     // Catch: java.lang.IllegalArgumentException -> L56
        L3b:
            java.lang.Class r2 = r1.getSuperclass()     // Catch: java.lang.IllegalArgumentException -> L56
            if (r2 == 0) goto L51
            if (r1 == r14) goto L51
            java.lang.Class r1 = r1.getSuperclass()     // Catch: java.lang.IllegalArgumentException -> L56
            r2 = r11
            r3 = r12
            r4 = r1
            r5 = r10
            r6 = r13
            r7 = r15
            reflectionAppend(r2, r3, r4, r5, r6, r7)     // Catch: java.lang.IllegalArgumentException -> L56
            goto L3b
        L51:
            boolean r11 = r10.isEquals()
            return r11
        L56:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(java.lang.Object, java.lang.Object, boolean, java.lang.Class, java.lang.String[]):boolean");
    }

    public EqualsBuilder append(long j2, long j3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = j2 == j3;
        return this;
    }

    public EqualsBuilder append(int i2, int i3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = i2 == i3;
        return this;
    }

    public EqualsBuilder append(short s2, short s3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = s2 == s3;
        return this;
    }

    public EqualsBuilder append(char c2, char c3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = c2 == c3;
        return this;
    }

    public EqualsBuilder append(byte b2, byte b3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = b2 == b3;
        return this;
    }

    public EqualsBuilder append(double d2, double d3) {
        return !this.isEquals ? this : append(Double.doubleToLongBits(d2), Double.doubleToLongBits(d3));
    }

    public EqualsBuilder append(float f2, float f3) {
        return !this.isEquals ? this : append(Float.floatToIntBits(f2), Float.floatToIntBits(f3));
    }

    public EqualsBuilder append(boolean z2, boolean z3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = z2 == z3;
        return this;
    }

    public EqualsBuilder append(Object[] objArr, Object[] objArr2) {
        if (!this.isEquals || objArr == objArr2) {
            return this;
        }
        if (objArr != null && objArr2 != null) {
            if (objArr.length != objArr2.length) {
                a(false);
                return this;
            }
            for (int i2 = 0; i2 < objArr.length && this.isEquals; i2++) {
                append(objArr[i2], objArr2[i2]);
            }
            return this;
        }
        a(false);
        return this;
    }

    public EqualsBuilder append(long[] jArr, long[] jArr2) {
        if (!this.isEquals || jArr == jArr2) {
            return this;
        }
        if (jArr != null && jArr2 != null) {
            if (jArr.length != jArr2.length) {
                a(false);
                return this;
            }
            for (int i2 = 0; i2 < jArr.length && this.isEquals; i2++) {
                append(jArr[i2], jArr2[i2]);
            }
            return this;
        }
        a(false);
        return this;
    }

    public EqualsBuilder append(int[] iArr, int[] iArr2) {
        if (!this.isEquals || iArr == iArr2) {
            return this;
        }
        if (iArr != null && iArr2 != null) {
            if (iArr.length != iArr2.length) {
                a(false);
                return this;
            }
            for (int i2 = 0; i2 < iArr.length && this.isEquals; i2++) {
                append(iArr[i2], iArr2[i2]);
            }
            return this;
        }
        a(false);
        return this;
    }

    public EqualsBuilder append(short[] sArr, short[] sArr2) {
        if (!this.isEquals || sArr == sArr2) {
            return this;
        }
        if (sArr != null && sArr2 != null) {
            if (sArr.length != sArr2.length) {
                a(false);
                return this;
            }
            for (int i2 = 0; i2 < sArr.length && this.isEquals; i2++) {
                append(sArr[i2], sArr2[i2]);
            }
            return this;
        }
        a(false);
        return this;
    }

    public EqualsBuilder append(char[] cArr, char[] cArr2) {
        if (!this.isEquals || cArr == cArr2) {
            return this;
        }
        if (cArr != null && cArr2 != null) {
            if (cArr.length != cArr2.length) {
                a(false);
                return this;
            }
            for (int i2 = 0; i2 < cArr.length && this.isEquals; i2++) {
                append(cArr[i2], cArr2[i2]);
            }
            return this;
        }
        a(false);
        return this;
    }

    public EqualsBuilder append(byte[] bArr, byte[] bArr2) {
        if (!this.isEquals || bArr == bArr2) {
            return this;
        }
        if (bArr != null && bArr2 != null) {
            if (bArr.length != bArr2.length) {
                a(false);
                return this;
            }
            for (int i2 = 0; i2 < bArr.length && this.isEquals; i2++) {
                append(bArr[i2], bArr2[i2]);
            }
            return this;
        }
        a(false);
        return this;
    }

    public EqualsBuilder append(double[] dArr, double[] dArr2) {
        if (!this.isEquals || dArr == dArr2) {
            return this;
        }
        if (dArr != null && dArr2 != null) {
            if (dArr.length != dArr2.length) {
                a(false);
                return this;
            }
            for (int i2 = 0; i2 < dArr.length && this.isEquals; i2++) {
                append(dArr[i2], dArr2[i2]);
            }
            return this;
        }
        a(false);
        return this;
    }

    public EqualsBuilder append(float[] fArr, float[] fArr2) {
        if (!this.isEquals || fArr == fArr2) {
            return this;
        }
        if (fArr != null && fArr2 != null) {
            if (fArr.length != fArr2.length) {
                a(false);
                return this;
            }
            for (int i2 = 0; i2 < fArr.length && this.isEquals; i2++) {
                append(fArr[i2], fArr2[i2]);
            }
            return this;
        }
        a(false);
        return this;
    }

    public EqualsBuilder append(boolean[] zArr, boolean[] zArr2) {
        if (!this.isEquals || zArr == zArr2) {
            return this;
        }
        if (zArr != null && zArr2 != null) {
            if (zArr.length != zArr2.length) {
                a(false);
                return this;
            }
            for (int i2 = 0; i2 < zArr.length && this.isEquals; i2++) {
                append(zArr[i2], zArr2[i2]);
            }
            return this;
        }
        a(false);
        return this;
    }
}
