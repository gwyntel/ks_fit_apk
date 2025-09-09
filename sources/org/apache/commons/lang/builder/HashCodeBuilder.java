package org.apache.commons.lang.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;

/* loaded from: classes5.dex */
public class HashCodeBuilder {
    private static final ThreadLocal REGISTRY = new ThreadLocal();

    /* renamed from: a, reason: collision with root package name */
    static /* synthetic */ Class f26625a;
    private final int iConstant;
    private int iTotal;

    public HashCodeBuilder() {
        this.iConstant = 37;
        this.iTotal = 17;
    }

    static /* synthetic */ Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    static Set b() {
        return (Set) REGISTRY.get();
    }

    static boolean c(Object obj) {
        Set setB = b();
        return setB != null && setB.contains(new IDKey(obj));
    }

    static void d(Object obj) {
        Class clsA = f26625a;
        if (clsA == null) {
            clsA = a("org.apache.commons.lang.builder.HashCodeBuilder");
            f26625a = clsA;
        }
        synchronized (clsA) {
            try {
                if (b() == null) {
                    REGISTRY.set(new HashSet());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        b().add(new IDKey(obj));
    }

    static void e(Object obj) {
        Set setB = b();
        if (setB != null) {
            setB.remove(new IDKey(obj));
            Class clsA = f26625a;
            if (clsA == null) {
                clsA = a("org.apache.commons.lang.builder.HashCodeBuilder");
                f26625a = clsA;
            }
            synchronized (clsA) {
                try {
                    Set setB2 = b();
                    if (setB2 != null && setB2.isEmpty()) {
                        REGISTRY.set(null);
                    }
                } finally {
                }
            }
        }
    }

    private static void reflectionAppend(Object obj, Class cls, HashCodeBuilder hashCodeBuilder, boolean z2, String[] strArr) {
        if (c(obj)) {
            return;
        }
        try {
            d(obj);
            Field[] declaredFields = cls.getDeclaredFields();
            AccessibleObject.setAccessible(declaredFields, true);
            for (Field field : declaredFields) {
                if (!ArrayUtils.contains(strArr, field.getName()) && field.getName().indexOf(36) == -1 && (z2 || !Modifier.isTransient(field.getModifiers()))) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    } else {
                        try {
                            hashCodeBuilder.append(field.get(obj));
                        } catch (IllegalAccessException unused) {
                            throw new InternalError("Unexpected IllegalAccessException");
                        }
                    }
                }
            }
            e(obj);
        } catch (Throwable th) {
            e(obj);
            throw th;
        }
    }

    public static int reflectionHashCode(int i2, int i3, Object obj) {
        return reflectionHashCode(i2, i3, obj, false, null, null);
    }

    public HashCodeBuilder append(boolean z2) {
        this.iTotal = (this.iTotal * this.iConstant) + (!z2 ? 1 : 0);
        return this;
    }

    public HashCodeBuilder appendSuper(int i2) {
        this.iTotal = (this.iTotal * this.iConstant) + i2;
        return this;
    }

    public int hashCode() {
        return toHashCode();
    }

    public int toHashCode() {
        return this.iTotal;
    }

    public static int reflectionHashCode(int i2, int i3, Object obj, boolean z2) {
        return reflectionHashCode(i2, i3, obj, z2, null, null);
    }

    public HashCodeBuilder append(boolean[] zArr) {
        if (zArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (boolean z2 : zArr) {
                append(z2);
            }
        }
        return this;
    }

    public static int reflectionHashCode(int i2, int i3, Object obj, boolean z2, Class cls) {
        return reflectionHashCode(i2, i3, obj, z2, cls, null);
    }

    public HashCodeBuilder(int i2, int i3) {
        this.iTotal = 0;
        if (i2 != 0) {
            if (i2 % 2 == 0) {
                throw new IllegalArgumentException("HashCodeBuilder requires an odd initial value");
            }
            if (i3 != 0) {
                if (i3 % 2 != 0) {
                    this.iConstant = i3;
                    this.iTotal = i2;
                    return;
                }
                throw new IllegalArgumentException("HashCodeBuilder requires an odd multiplier");
            }
            throw new IllegalArgumentException("HashCodeBuilder requires a non zero multiplier");
        }
        throw new IllegalArgumentException("HashCodeBuilder requires a non zero initial value");
    }

    public static int reflectionHashCode(int i2, int i3, Object obj, boolean z2, Class cls, String[] strArr) {
        if (obj != null) {
            HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(i2, i3);
            Class<?> superclass = obj.getClass();
            reflectionAppend(obj, superclass, hashCodeBuilder, z2, strArr);
            while (superclass.getSuperclass() != null && superclass != cls) {
                superclass = superclass.getSuperclass();
                reflectionAppend(obj, superclass, hashCodeBuilder, z2, strArr);
            }
            return hashCodeBuilder.toHashCode();
        }
        throw new IllegalArgumentException("The object to build a hash code for must not be null");
    }

    public HashCodeBuilder append(byte b2) {
        this.iTotal = (this.iTotal * this.iConstant) + b2;
        return this;
    }

    public HashCodeBuilder append(byte[] bArr) {
        if (bArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (byte b2 : bArr) {
                append(b2);
            }
        }
        return this;
    }

    public HashCodeBuilder append(char c2) {
        this.iTotal = (this.iTotal * this.iConstant) + c2;
        return this;
    }

    public HashCodeBuilder append(char[] cArr) {
        if (cArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (char c2 : cArr) {
                append(c2);
            }
        }
        return this;
    }

    public static int reflectionHashCode(Object obj) {
        return reflectionHashCode(17, 37, obj, false, null, null);
    }

    public static int reflectionHashCode(Object obj, boolean z2) {
        return reflectionHashCode(17, 37, obj, z2, null, null);
    }

    public HashCodeBuilder append(double d2) {
        return append(Double.doubleToLongBits(d2));
    }

    public static int reflectionHashCode(Object obj, Collection collection) {
        return reflectionHashCode(obj, ReflectionToStringBuilder.d(collection));
    }

    public HashCodeBuilder append(double[] dArr) {
        if (dArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (double d2 : dArr) {
                append(d2);
            }
        }
        return this;
    }

    public static int reflectionHashCode(Object obj, String[] strArr) {
        return reflectionHashCode(17, 37, obj, false, null, strArr);
    }

    public HashCodeBuilder append(float f2) {
        this.iTotal = (this.iTotal * this.iConstant) + Float.floatToIntBits(f2);
        return this;
    }

    public HashCodeBuilder append(float[] fArr) {
        if (fArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (float f2 : fArr) {
                append(f2);
            }
        }
        return this;
    }

    public HashCodeBuilder append(int i2) {
        this.iTotal = (this.iTotal * this.iConstant) + i2;
        return this;
    }

    public HashCodeBuilder append(int[] iArr) {
        if (iArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (int i2 : iArr) {
                append(i2);
            }
        }
        return this;
    }

    public HashCodeBuilder append(long j2) {
        this.iTotal = (this.iTotal * this.iConstant) + ((int) (j2 ^ (j2 >> 32)));
        return this;
    }

    public HashCodeBuilder append(long[] jArr) {
        if (jArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (long j2 : jArr) {
                append(j2);
            }
        }
        return this;
    }

    public HashCodeBuilder append(Object obj) {
        if (obj == null) {
            this.iTotal *= this.iConstant;
        } else if (obj.getClass().isArray()) {
            if (obj instanceof long[]) {
                append((long[]) obj);
            } else if (obj instanceof int[]) {
                append((int[]) obj);
            } else if (obj instanceof short[]) {
                append((short[]) obj);
            } else if (obj instanceof char[]) {
                append((char[]) obj);
            } else if (obj instanceof byte[]) {
                append((byte[]) obj);
            } else if (obj instanceof double[]) {
                append((double[]) obj);
            } else if (obj instanceof float[]) {
                append((float[]) obj);
            } else if (obj instanceof boolean[]) {
                append((boolean[]) obj);
            } else {
                append((Object[]) obj);
            }
        } else {
            this.iTotal = (this.iTotal * this.iConstant) + obj.hashCode();
        }
        return this;
    }

    public HashCodeBuilder append(Object[] objArr) {
        if (objArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (Object obj : objArr) {
                append(obj);
            }
        }
        return this;
    }

    public HashCodeBuilder append(short s2) {
        this.iTotal = (this.iTotal * this.iConstant) + s2;
        return this;
    }

    public HashCodeBuilder append(short[] sArr) {
        if (sArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (short s2 : sArr) {
                append(s2);
            }
        }
        return this;
    }
}
