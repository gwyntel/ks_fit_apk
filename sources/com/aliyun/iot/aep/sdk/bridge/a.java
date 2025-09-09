package com.aliyun.iot.aep.sdk.bridge;

import android.text.TextUtils;
import android.util.Log;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCall;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneMethod;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    Map<String, C0094a> f11667a = new HashMap(5);

    /* renamed from: b, reason: collision with root package name */
    boolean f11668b = true;

    public void a(Object obj) throws SecurityException {
        try {
            for (Method method : obj.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(BoneMethod.class)) {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    if (Arrays.binarySearch(method.getParameterTypes(), BoneCallback.class) < 0) {
                        ALog.e("APluginRegistry", String.format(Locale.ENGLISH, "%s must have  parameter BoneCallback", method));
                    } else {
                        BoneMethod boneMethod = (BoneMethod) method.getAnnotation(BoneMethod.class);
                        if (TextUtils.isEmpty(boneMethod.name())) {
                            this.f11667a.put(method.getName().toLowerCase(), new C0094a(method));
                        } else {
                            this.f11667a.put(boneMethod.name().toLowerCase(), new C0094a(method));
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        this.f11668b = true;
    }

    /* renamed from: com.aliyun.iot.aep.sdk.bridge.a$a, reason: collision with other inner class name */
    static class C0094a {

        /* renamed from: a, reason: collision with root package name */
        Method f11669a;

        public C0094a(Method method) {
            this.f11669a = method;
        }

        public void a(Object obj, JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) throws Exception {
            Class<?>[] parameterTypes = this.f11669a.getParameterTypes();
            Object[] objArr = new Object[parameterTypes.length];
            int i2 = -1;
            int i3 = 0;
            while (i3 < parameterTypes.length) {
                Class<?> cls = parameterTypes[i3];
                if (!a(cls)) {
                    i2++;
                }
                int i4 = i2;
                objArr[i3] = a(cls, i4, jSContext, boneCall, boneCallback);
                i3++;
                i2 = i4;
            }
            try {
                this.f11669a.invoke(obj, objArr);
            } catch (Exception e2) {
                Log.e("APluginRegistry", String.format(Locale.ENGLISH, "can not invoke method:object=%s,method=%s, parameters=%s", obj, this.f11669a, boneCall.args));
                boneCallback.failed("608", "can not invoke method:" + this.f11669a.getName(), "方法执行出错");
                e2.printStackTrace();
                throw e2;
            }
        }

        private boolean a(Class cls) {
            return cls == JSContext.class || cls == BoneCall.class || cls == BoneCallback.class;
        }

        private Object a(Class cls, int i2, JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) {
            if (cls == JSContext.class) {
                return jSContext;
            }
            if (cls == BoneCall.class) {
                return boneCall;
            }
            if (cls == BoneCallback.class) {
                return boneCallback;
            }
            if (cls != Boolean.class && cls != Boolean.TYPE) {
                if (cls != Long.class && cls != Long.TYPE) {
                    if (cls != Double.class && cls != Double.TYPE && cls != Float.TYPE && cls != Float.class) {
                        if (cls != Integer.class && cls != Integer.TYPE && cls != Short.class && cls != Short.TYPE) {
                            if (cls == String.class) {
                                return boneCall.args.optString(i2);
                            }
                            if (cls == JSONObject.class) {
                                return boneCall.args.optJSONObject(i2);
                            }
                            if (cls == JSONArray.class) {
                                return boneCall.args.optJSONArray(i2);
                            }
                            throw new IllegalArgumentException(String.format("method %s not support parameter %s", this.f11669a.getName(), cls.getCanonicalName()));
                        }
                        return Integer.valueOf(boneCall.args.optInt(i2));
                    }
                    return Double.valueOf(boneCall.args.optDouble(i2));
                }
                return Long.valueOf(boneCall.args.optLong(i2));
            }
            return Boolean.valueOf(boneCall.args.optBoolean(i2));
        }
    }

    public void a() {
        this.f11667a.clear();
    }

    public boolean a(Object obj, JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) throws Exception {
        if (!this.f11668b) {
            return false;
        }
        String lowerCase = boneCall.methodName.toLowerCase();
        if (!this.f11667a.containsKey(lowerCase)) {
            boneCallback.failed("608", "method not found", "方法未实现");
            return false;
        }
        this.f11667a.get(lowerCase).a(obj, jSContext, boneCall, boneCallback);
        return true;
    }
}
