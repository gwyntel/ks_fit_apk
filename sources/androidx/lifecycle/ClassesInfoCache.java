package androidx.lifecycle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
/* loaded from: classes.dex */
final class ClassesInfoCache {
    private static final int CALL_TYPE_NO_ARG = 0;
    private static final int CALL_TYPE_PROVIDER = 1;
    private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;

    /* renamed from: a, reason: collision with root package name */
    static ClassesInfoCache f4557a = new ClassesInfoCache();
    private final Map<Class<?>, CallbackInfo> mCallbackMap = new HashMap();
    private final Map<Class<?>, Boolean> mHasLifecycleMethods = new HashMap();

    @Deprecated
    static class CallbackInfo {

        /* renamed from: a, reason: collision with root package name */
        final Map f4558a = new HashMap();

        /* renamed from: b, reason: collision with root package name */
        final Map f4559b;

        CallbackInfo(Map map) {
            this.f4559b = map;
            for (Map.Entry entry : map.entrySet()) {
                Lifecycle.Event event = (Lifecycle.Event) entry.getValue();
                List arrayList = (List) this.f4558a.get(event);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    this.f4558a.put(event, arrayList);
                }
                arrayList.add((MethodReference) entry.getKey());
            }
        }

        private static void invokeMethodsForEvent(List<MethodReference> list, LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    list.get(size).a(lifecycleOwner, event, obj);
                }
            }
        }

        void a(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            invokeMethodsForEvent((List) this.f4558a.get(event), lifecycleOwner, event, obj);
            invokeMethodsForEvent((List) this.f4558a.get(Lifecycle.Event.ON_ANY), lifecycleOwner, event, obj);
        }
    }

    @Deprecated
    static final class MethodReference {

        /* renamed from: a, reason: collision with root package name */
        final int f4560a;

        /* renamed from: b, reason: collision with root package name */
        final Method f4561b;

        MethodReference(int i2, Method method) throws SecurityException {
            this.f4560a = i2;
            this.f4561b = method;
            method.setAccessible(true);
        }

        void a(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                int i2 = this.f4560a;
                if (i2 == 0) {
                    this.f4561b.invoke(obj, null);
                } else if (i2 == 1) {
                    this.f4561b.invoke(obj, lifecycleOwner);
                } else {
                    if (i2 != 2) {
                        return;
                    }
                    this.f4561b.invoke(obj, lifecycleOwner, event);
                }
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException("Failed to call observer method", e3.getCause());
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MethodReference)) {
                return false;
            }
            MethodReference methodReference = (MethodReference) obj;
            return this.f4560a == methodReference.f4560a && this.f4561b.getName().equals(methodReference.f4561b.getName());
        }

        public int hashCode() {
            return (this.f4560a * 31) + this.f4561b.getName().hashCode();
        }
    }

    ClassesInfoCache() {
    }

    private CallbackInfo createInfo(Class<?> cls, @Nullable Method[] methodArr) {
        int i2;
        CallbackInfo callbackInfoA;
        Class<? super Object> superclass = cls.getSuperclass();
        HashMap map = new HashMap();
        if (superclass != null && (callbackInfoA = a(superclass)) != null) {
            map.putAll(callbackInfoA.f4559b);
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            for (Map.Entry entry : a(cls2).f4559b.entrySet()) {
                verifyAndPutHandler(map, (MethodReference) entry.getKey(), (Lifecycle.Event) entry.getValue(), cls);
            }
        }
        if (methodArr == null) {
            methodArr = getDeclaredMethods(cls);
        }
        boolean z2 = false;
        for (Method method : methodArr) {
            OnLifecycleEvent onLifecycleEvent = (OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class);
            if (onLifecycleEvent != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length <= 0) {
                    i2 = 0;
                } else {
                    if (!LifecycleOwner.class.isAssignableFrom(parameterTypes[0])) {
                        throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                    }
                    i2 = 1;
                }
                Lifecycle.Event eventValue = onLifecycleEvent.value();
                if (parameterTypes.length > 1) {
                    if (!Lifecycle.Event.class.isAssignableFrom(parameterTypes[1])) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    }
                    if (eventValue != Lifecycle.Event.ON_ANY) {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    }
                    i2 = 2;
                }
                if (parameterTypes.length > 2) {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
                verifyAndPutHandler(map, new MethodReference(i2, method), eventValue, cls);
                z2 = true;
            }
        }
        CallbackInfo callbackInfo = new CallbackInfo(map);
        this.mCallbackMap.put(cls, callbackInfo);
        this.mHasLifecycleMethods.put(cls, Boolean.valueOf(z2));
        return callbackInfo;
    }

    private Method[] getDeclaredMethods(Class<?> cls) {
        try {
            return cls.getDeclaredMethods();
        } catch (NoClassDefFoundError e2) {
            throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e2);
        }
    }

    private void verifyAndPutHandler(Map<MethodReference, Lifecycle.Event> map, MethodReference methodReference, Lifecycle.Event event, Class<?> cls) {
        Lifecycle.Event event2 = map.get(methodReference);
        if (event2 == null || event == event2) {
            if (event2 == null) {
                map.put(methodReference, event);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Method " + methodReference.f4561b.getName() + " in " + cls.getName() + " already declared with different @OnLifecycleEvent value: previous value " + event2 + ", new value " + event);
    }

    CallbackInfo a(Class cls) {
        CallbackInfo callbackInfo = this.mCallbackMap.get(cls);
        return callbackInfo != null ? callbackInfo : createInfo(cls, null);
    }

    boolean b(Class cls) {
        Boolean bool = this.mHasLifecycleMethods.get(cls);
        if (bool != null) {
            return bool.booleanValue();
        }
        Method[] declaredMethods = getDeclaredMethods(cls);
        for (Method method : declaredMethods) {
            if (((OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class)) != null) {
                createInfo(cls, declaredMethods);
                return true;
            }
        }
        this.mHasLifecycleMethods.put(cls, Boolean.FALSE);
        return false;
    }
}
