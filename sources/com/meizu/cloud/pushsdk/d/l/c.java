package com.meizu.cloud.pushsdk.d.l;

import com.meizu.cloud.pushinternal.DebugLogger;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final HashMap<String, Method> f19253a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private final com.meizu.cloud.pushsdk.d.l.a f19254b;

    /* renamed from: c, reason: collision with root package name */
    private final String f19255c;

    /* renamed from: d, reason: collision with root package name */
    private Class<?>[] f19256d;

    private class a {
    }

    c(com.meizu.cloud.pushsdk.d.l.a aVar, String str, Class<?>... clsArr) {
        this.f19254b = aVar;
        this.f19255c = str;
        this.f19256d = clsArr;
    }

    private Method b() throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        Class<?> clsA = this.f19254b.a();
        for (Method method : clsA.getMethods()) {
            if (a(method, this.f19255c, this.f19256d)) {
                return method;
            }
        }
        for (Method method2 : clsA.getDeclaredMethods()) {
            if (a(method2, this.f19255c, this.f19256d)) {
                return method2;
            }
        }
        throw new NoSuchMethodException("No similar method " + this.f19255c + " with params " + Arrays.toString(this.f19256d) + " could be found on type " + clsA);
    }

    public <T> d<T> a(Object obj, Object... objArr) throws NoSuchMethodException, SecurityException {
        d<T> dVar = new d<>();
        try {
            String strA = a();
            Method methodB = f19253a.get(strA);
            if (methodB == null) {
                if (this.f19256d.length == objArr.length) {
                    methodB = this.f19254b.a().getMethod(this.f19255c, this.f19256d);
                } else {
                    if (objArr.length > 0) {
                        this.f19256d = new Class[objArr.length];
                        for (int i2 = 0; i2 < objArr.length; i2++) {
                            this.f19256d[i2] = objArr[i2].getClass();
                        }
                    }
                    methodB = b();
                }
                f19253a.put(strA, methodB);
            }
            methodB.setAccessible(true);
            dVar.f19258b = (T) methodB.invoke(obj, objArr);
            dVar.f19257a = true;
        } catch (Exception e2) {
            DebugLogger.d("ReflectMethod", "invoke exception, " + e2.getMessage());
        }
        return dVar;
    }

    public <T> d<T> a(Object... objArr) {
        try {
            return a(this.f19254b.a(), objArr);
        } catch (ClassNotFoundException unused) {
            return new d<>();
        }
    }

    private Class<?> a(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        return cls.isPrimitive() ? Boolean.TYPE == cls ? Boolean.class : Integer.TYPE == cls ? Integer.class : Long.TYPE == cls ? Long.class : Short.TYPE == cls ? Short.class : Byte.TYPE == cls ? Byte.class : Double.TYPE == cls ? Double.class : Float.TYPE == cls ? Float.class : Character.TYPE == cls ? Character.class : Void.TYPE == cls ? Void.class : cls : cls;
    }

    private String a() throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder(this.f19254b.a().getName());
        sb.append(this.f19255c);
        for (Class<?> cls : this.f19256d) {
            sb.append(cls.getName());
        }
        return sb.toString();
    }

    private boolean a(Method method, String str, Class<?>[] clsArr) {
        return method.getName().equals(str) && a(method.getParameterTypes(), clsArr);
    }

    private boolean a(Class<?>[] clsArr, Class<?>[] clsArr2) {
        if (clsArr.length != clsArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < clsArr2.length; i2++) {
            if (clsArr2[i2] != a.class && !a(clsArr[i2]).isAssignableFrom(a(clsArr2[i2]))) {
                return false;
            }
        }
        return true;
    }
}
