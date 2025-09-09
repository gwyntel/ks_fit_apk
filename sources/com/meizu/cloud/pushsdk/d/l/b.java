package com.meizu.cloud.pushsdk.d.l;

import com.meizu.cloud.pushinternal.DebugLogger;
import java.lang.reflect.Constructor;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private final a f19251a;

    /* renamed from: b, reason: collision with root package name */
    private final Class<?>[] f19252b;

    b(a aVar, Class<?>... clsArr) {
        this.f19251a = aVar;
        this.f19252b = clsArr;
    }

    public <T> d<T> a(Object... objArr) throws NoSuchMethodException, SecurityException {
        d<T> dVar = new d<>();
        try {
            Constructor<?> declaredConstructor = this.f19251a.a().getDeclaredConstructor(this.f19252b);
            declaredConstructor.setAccessible(true);
            dVar.f19258b = (T) declaredConstructor.newInstance(objArr);
            dVar.f19257a = true;
        } catch (Exception e2) {
            DebugLogger.e("ReflectConstructor", "newInstance", e2);
        }
        return dVar;
    }
}
