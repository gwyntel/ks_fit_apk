package org.mozilla.javascript;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes5.dex */
public class Kit {
    private static Method Throwable_initCause;

    private static final class ComplexKey {
        private int hash;
        private Object key1;
        private Object key2;

        ComplexKey(Object obj, Object obj2) {
            this.key1 = obj;
            this.key2 = obj2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ComplexKey)) {
                return false;
            }
            ComplexKey complexKey = (ComplexKey) obj;
            return this.key1.equals(complexKey.key1) && this.key2.equals(complexKey.key2);
        }

        public int hashCode() {
            if (this.hash == 0) {
                this.hash = this.key1.hashCode() ^ this.key2.hashCode();
            }
            return this.hash;
        }
    }

    static {
        try {
            Class<?> clsClassOrNull = classOrNull("java.lang.Throwable");
            Throwable_initCause = clsClassOrNull.getMethod("initCause", clsClassOrNull);
        } catch (Exception unused) {
        }
    }

    public static Object addListener(Object obj, Object obj2) {
        if (obj2 == null) {
            throw new IllegalArgumentException();
        }
        if (obj2 instanceof Object[]) {
            throw new IllegalArgumentException();
        }
        if (obj == null) {
            return obj2;
        }
        if (!(obj instanceof Object[])) {
            return new Object[]{obj, obj2};
        }
        Object[] objArr = (Object[]) obj;
        int length = objArr.length;
        if (length < 2) {
            throw new IllegalArgumentException();
        }
        Object[] objArr2 = new Object[length + 1];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        objArr2[length] = obj2;
        return objArr2;
    }

    public static Class<?> classOrNull(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException | IllegalArgumentException | LinkageError | SecurityException unused) {
            return null;
        }
    }

    public static RuntimeException codeBug() throws RuntimeException {
        IllegalStateException illegalStateException = new IllegalStateException("FAILED ASSERTION");
        illegalStateException.printStackTrace(System.err);
        throw illegalStateException;
    }

    public static Object getListener(Object obj, int i2) {
        if (i2 == 0) {
            if (obj == null) {
                return null;
            }
            if (!(obj instanceof Object[])) {
                return obj;
            }
            Object[] objArr = (Object[]) obj;
            if (objArr.length >= 2) {
                return objArr[0];
            }
            throw new IllegalArgumentException();
        }
        if (i2 == 1) {
            if (obj instanceof Object[]) {
                return ((Object[]) obj)[1];
            }
            if (obj != null) {
                return null;
            }
            throw new IllegalArgumentException();
        }
        Object[] objArr2 = (Object[]) obj;
        int length = objArr2.length;
        if (length < 2) {
            throw new IllegalArgumentException();
        }
        if (i2 == length) {
            return null;
        }
        return objArr2[i2];
    }

    public static RuntimeException initCause(RuntimeException runtimeException, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = Throwable_initCause;
        if (method != null) {
            try {
                method.invoke(runtimeException, th);
            } catch (Exception unused) {
            }
        }
        return runtimeException;
    }

    static Object initHash(Map<Object, Object> map, Object obj, Object obj2) {
        synchronized (map) {
            try {
                Object obj3 = map.get(obj);
                if (obj3 == null) {
                    map.put(obj, obj2);
                } else {
                    obj2 = obj3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return obj2;
    }

    public static Object makeHashKeyFromPair(Object obj, Object obj2) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
        if (obj2 != null) {
            return new ComplexKey(obj, obj2);
        }
        throw new IllegalArgumentException();
    }

    static Object newInstanceOrNull(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException | InstantiationException | LinkageError | SecurityException unused) {
            return null;
        }
    }

    public static String readReader(Reader reader) throws IOException {
        char[] cArr = new char[512];
        int i2 = 0;
        while (true) {
            int i3 = reader.read(cArr, i2, cArr.length - i2);
            if (i3 < 0) {
                return new String(cArr, 0, i2);
            }
            i2 += i3;
            if (i2 == cArr.length) {
                char[] cArr2 = new char[cArr.length * 2];
                System.arraycopy(cArr, 0, cArr2, 0, i2);
                cArr = cArr2;
            }
        }
    }

    public static byte[] readStream(InputStream inputStream, int i2) throws IOException {
        if (i2 <= 0) {
            throw new IllegalArgumentException("Bad initialBufferCapacity: " + i2);
        }
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (true) {
            int i4 = inputStream.read(bArr, i3, bArr.length - i3);
            if (i4 < 0) {
                break;
            }
            i3 += i4;
            if (i3 == bArr.length) {
                byte[] bArr2 = new byte[bArr.length * 2];
                System.arraycopy(bArr, 0, bArr2, 0, i3);
                bArr = bArr2;
            }
        }
        if (i3 == bArr.length) {
            return bArr;
        }
        byte[] bArr3 = new byte[i3];
        System.arraycopy(bArr, 0, bArr3, 0, i3);
        return bArr3;
    }

    public static Object removeListener(Object obj, Object obj2) {
        if (obj2 == null) {
            throw new IllegalArgumentException();
        }
        if (obj2 instanceof Object[]) {
            throw new IllegalArgumentException();
        }
        if (obj == obj2) {
            return null;
        }
        if (!(obj instanceof Object[])) {
            return obj;
        }
        Object[] objArr = (Object[]) obj;
        int length = objArr.length;
        if (length < 2) {
            throw new IllegalArgumentException();
        }
        if (length == 2) {
            Object obj3 = objArr[1];
            return obj3 == obj2 ? objArr[0] : objArr[0] == obj2 ? obj3 : obj;
        }
        int i2 = length;
        while (true) {
            int i3 = i2 - 1;
            if (objArr[i3] == obj2) {
                Object[] objArr2 = new Object[length - 1];
                System.arraycopy(objArr, 0, objArr2, 0, i3);
                System.arraycopy(objArr, i2, objArr2, i3, length - i2);
                return objArr2;
            }
            if (i3 == 0) {
                return obj;
            }
            i2 = i3;
        }
    }

    static boolean testIfCanLoadRhinoClasses(ClassLoader classLoader) {
        Class<?> cls = ScriptRuntime.ContextFactoryClass;
        return classOrNull(classLoader, cls.getName()) == cls;
    }

    public static int xDigitToInt(int i2, int i3) {
        int i4;
        if (i2 <= 57) {
            i4 = i2 - 48;
            if (i4 < 0) {
                return -1;
            }
        } else if (i2 <= 70) {
            if (65 > i2) {
                return -1;
            }
            i4 = i2 - 55;
        } else {
            if (i2 > 102 || 97 > i2) {
                return -1;
            }
            i4 = i2 - 87;
        }
        return i4 | (i3 << 4);
    }

    public static Class<?> classOrNull(ClassLoader classLoader, String str) {
        try {
            return classLoader.loadClass(str);
        } catch (ClassNotFoundException | IllegalArgumentException | LinkageError | SecurityException unused) {
            return null;
        }
    }

    public static RuntimeException codeBug(String str) throws RuntimeException {
        IllegalStateException illegalStateException = new IllegalStateException("FAILED ASSERTION: " + str);
        illegalStateException.printStackTrace(System.err);
        throw illegalStateException;
    }
}
