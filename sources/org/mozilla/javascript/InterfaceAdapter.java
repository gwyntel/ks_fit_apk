package org.mozilla.javascript;

import java.lang.reflect.Method;

/* loaded from: classes5.dex */
public class InterfaceAdapter {
    private final Object proxyHelper;

    private InterfaceAdapter(ContextFactory contextFactory, Class<?> cls) {
        this.proxyHelper = VMBridge.instance.getInterfaceProxyHelper(contextFactory, new Class[]{cls});
    }

    static Object create(Context context, Class<?> cls, ScriptableObject scriptableObject) throws SecurityException {
        InterfaceAdapter interfaceAdapter;
        if (!cls.isInterface()) {
            throw new IllegalArgumentException();
        }
        Scriptable topCallScope = ScriptRuntime.getTopCallScope(context);
        ClassCache classCache = ClassCache.get(topCallScope);
        InterfaceAdapter interfaceAdapter2 = (InterfaceAdapter) classCache.getInterfaceAdapter(cls);
        ContextFactory factory = context.getFactory();
        if (interfaceAdapter2 == null) {
            Method[] methods = cls.getMethods();
            if (scriptableObject instanceof Callable) {
                int length = methods.length;
                if (length == 0) {
                    throw Context.reportRuntimeError1("msg.no.empty.interface.conversion", cls.getName());
                }
                if (length > 1) {
                    String name = methods[0].getName();
                    for (int i2 = 1; i2 < length; i2++) {
                        if (!name.equals(methods[i2].getName())) {
                            throw Context.reportRuntimeError1("msg.no.function.interface.conversion", cls.getName());
                        }
                    }
                }
            }
            InterfaceAdapter interfaceAdapter3 = new InterfaceAdapter(factory, cls);
            classCache.cacheInterfaceAdapter(cls, interfaceAdapter3);
            interfaceAdapter = interfaceAdapter3;
        } else {
            interfaceAdapter = interfaceAdapter2;
        }
        return VMBridge.instance.newInterfaceProxy(interfaceAdapter.proxyHelper, factory, interfaceAdapter, scriptableObject, topCallScope);
    }

    public Object invoke(ContextFactory contextFactory, final Object obj, final Scriptable scriptable, final Object obj2, final Method method, final Object[] objArr) {
        return contextFactory.call(new ContextAction() { // from class: org.mozilla.javascript.InterfaceAdapter.1
            @Override // org.mozilla.javascript.ContextAction
            public Object run(Context context) {
                return InterfaceAdapter.this.invokeImpl(context, obj, scriptable, obj2, method, objArr);
            }
        });
    }

    Object invokeImpl(Context context, Object obj, Scriptable scriptable, Object obj2, Method method, Object[] objArr) {
        Callable callable;
        if (obj instanceof Callable) {
            callable = (Callable) obj;
        } else {
            String name = method.getName();
            Object property = ScriptableObject.getProperty((Scriptable) obj, name);
            if (property == Scriptable.NOT_FOUND) {
                Context.reportWarning(ScriptRuntime.getMessage1("msg.undefined.function.interface", name));
                Class<?> returnType = method.getReturnType();
                if (returnType == Void.TYPE) {
                    return null;
                }
                return Context.jsToJava(null, returnType);
            }
            if (!(property instanceof Callable)) {
                throw Context.reportRuntimeError1("msg.not.function.interface", name);
            }
            callable = (Callable) property;
        }
        WrapFactory wrapFactory = context.getWrapFactory();
        if (objArr == null) {
            objArr = ScriptRuntime.emptyArgs;
        } else {
            int length = objArr.length;
            for (int i2 = 0; i2 != length; i2++) {
                Object obj3 = objArr[i2];
                if (!(obj3 instanceof String) && !(obj3 instanceof Number) && !(obj3 instanceof Boolean)) {
                    objArr[i2] = wrapFactory.wrap(context, scriptable, obj3, null);
                }
            }
        }
        Object objCall = callable.call(context, scriptable, wrapFactory.wrapAsJavaObject(context, scriptable, obj2, null), objArr);
        Class<?> returnType2 = method.getReturnType();
        if (returnType2 == Void.TYPE) {
            return null;
        }
        return Context.jsToJava(objCall, returnType2);
    }
}
