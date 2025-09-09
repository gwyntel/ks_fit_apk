package com.aliyun.alink.sdk.jsbridge;

import android.text.TextUtils;
import android.util.Log;
import com.aliyun.alink.sdk.jsbridge.methodexport.MethodExported;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    Map<String, C0092a> f11508a = new HashMap(5);

    /* renamed from: b, reason: collision with root package name */
    boolean f11509b = true;

    /* renamed from: com.aliyun.alink.sdk.jsbridge.a$a, reason: collision with other inner class name */
    static class C0092a {

        /* renamed from: a, reason: collision with root package name */
        Method f11510a;

        public C0092a(Method method) {
            this.f11510a = method;
        }

        public void a(Object obj, Object[] objArr, BoneCallback boneCallback) throws IllegalAccessException, InvocationTargetException {
            ArrayList arrayList = new ArrayList(objArr.length + 1);
            for (Object obj2 : objArr) {
                arrayList.add(obj2);
            }
            arrayList.add(boneCallback);
            try {
                this.f11510a.invoke(obj, arrayList.toArray());
            } catch (IllegalAccessException e2) {
                Log.e("APluginRegistry", String.format(Locale.ENGLISH, "can not invoke method:object=%s,method=%s, parameters=%s", obj, this.f11510a, objArr));
                e2.printStackTrace();
                throw e2;
            } catch (InvocationTargetException e3) {
                Log.e("APluginRegistry", String.format(Locale.ENGLISH, "can not invoke method:object=%s,method=%s, parameters=%s", obj, this.f11510a, objArr));
                e3.printStackTrace();
                throw e3;
            } catch (Throwable th) {
                Log.e("APluginRegistry", String.format(Locale.ENGLISH, "can not invoke method:object=%s,method=%s, parameters=%s", obj, this.f11510a, objArr));
                th.printStackTrace();
                throw th;
            }
        }
    }

    public void a(Object obj) throws SecurityException {
        try {
            for (Method method : obj.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(MethodExported.class)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes[parameterTypes.length - 1] == BoneCallback.class) {
                        MethodExported methodExported = (MethodExported) method.getAnnotation(MethodExported.class);
                        if (TextUtils.isEmpty(methodExported.name())) {
                            this.f11508a.put(method.getName(), new C0092a(method));
                        } else {
                            this.f11508a.put(methodExported.name(), new C0092a(method));
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        this.f11509b = true;
    }

    public void a() {
        this.f11508a.clear();
    }

    public boolean a(Object obj, String str, Object[] objArr, BoneCallback boneCallback) throws IllegalAccessException, InvocationTargetException {
        if (!this.f11509b || !this.f11508a.containsKey(str)) {
            return false;
        }
        this.f11508a.get(str).a(obj, objArr, boneCallback);
        return true;
    }
}
