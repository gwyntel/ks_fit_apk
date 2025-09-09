package com.alipay.sdk.m.e;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class a implements i, j {
    @Override // com.alipay.sdk.m.e.j
    public final Object a(Object obj) {
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : (Object[]) obj) {
            arrayList.add(f.b(obj2));
        }
        return arrayList;
    }

    @Override // com.alipay.sdk.m.e.i
    public final Object a(Object obj, Type type) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (!obj.getClass().equals(org.json.alipay.a.class)) {
            return null;
        }
        org.json.alipay.a aVar = (org.json.alipay.a) obj;
        if (type instanceof GenericArrayType) {
            throw new IllegalArgumentException("Does not support generic array!");
        }
        Class<?> componentType = ((Class) type).getComponentType();
        int iA = aVar.a();
        Object objNewInstance = Array.newInstance(componentType, iA);
        for (int i2 = 0; i2 < iA; i2++) {
            Array.set(objNewInstance, i2, e.a(aVar.a(i2), componentType));
        }
        return objNewInstance;
    }

    @Override // com.alipay.sdk.m.e.i, com.alipay.sdk.m.e.j
    public final boolean a(Class<?> cls) {
        return cls.isArray();
    }
}
