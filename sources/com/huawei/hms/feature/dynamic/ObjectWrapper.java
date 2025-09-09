package com.huawei.hms.feature.dynamic;

import android.os.IBinder;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public class ObjectWrapper<T> extends IObjectWrapper.Stub {

    /* renamed from: a, reason: collision with root package name */
    public final T f16109a;

    public ObjectWrapper(T t2) {
        this.f16109a = t2;
    }

    public static <T> T unwrap(IObjectWrapper iObjectWrapper) throws SecurityException {
        if (iObjectWrapper instanceof ObjectWrapper) {
            return ((ObjectWrapper) iObjectWrapper).f16109a;
        }
        IBinder iBinderAsBinder = iObjectWrapper.asBinder();
        Field[] declaredFields = iBinderAsBinder.getClass().getDeclaredFields();
        int i2 = 0;
        for (Field field : declaredFields) {
            if (!field.isSynthetic()) {
                i2++;
            }
        }
        if (i2 == 1) {
            if (declaredFields[0].isAccessible()) {
                throw new IllegalArgumentException("The field is accessible.");
            }
            declaredFields[0].setAccessible(true);
            try {
                return (T) declaredFields[0].get(iBinderAsBinder);
            } catch (Exception unused) {
                throw new IllegalArgumentException("Get binder failed: null or not permitted.");
            }
        }
        throw new IllegalArgumentException("Got " + declaredFields.length + " fields, The number of field number should be 1.");
    }

    public static <T> IObjectWrapper wrap(T t2) {
        return new ObjectWrapper(t2);
    }
}
