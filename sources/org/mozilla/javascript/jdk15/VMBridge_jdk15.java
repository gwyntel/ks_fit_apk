package org.mozilla.javascript.jdk15;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.InterfaceAdapter;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.VMBridge;
import org.mozilla.javascript.Wrapper;

/* loaded from: classes5.dex */
public class VMBridge_jdk15 extends VMBridge {
    private ThreadLocal<Object[]> contextLocal = new ThreadLocal<>();

    public VMBridge_jdk15() throws NoSuchMethodException, InstantiationException, SecurityException {
        try {
            Method.class.getMethod("isVarArgs", null);
        } catch (NoSuchMethodException e2) {
            throw new InstantiationException(e2.getMessage());
        }
    }

    @Override // org.mozilla.javascript.VMBridge
    protected Context getContext(Object obj) {
        return (Context) ((Object[]) obj)[0];
    }

    @Override // org.mozilla.javascript.VMBridge
    protected Object getInterfaceProxyHelper(ContextFactory contextFactory, Class<?>[] clsArr) {
        try {
            return Proxy.getProxyClass(clsArr[0].getClassLoader(), clsArr).getConstructor(InvocationHandler.class);
        } catch (NoSuchMethodException e2) {
            throw Kit.initCause(new IllegalStateException(), e2);
        }
    }

    @Override // org.mozilla.javascript.VMBridge
    protected Iterator<?> getJavaIterator(Context context, Scriptable scriptable, Object obj) {
        if (!(obj instanceof Wrapper)) {
            return null;
        }
        Object objUnwrap = ((Wrapper) obj).unwrap();
        return objUnwrap instanceof Iterable ? ((Iterable) objUnwrap).iterator() : objUnwrap instanceof Iterator ? (Iterator) objUnwrap : null;
    }

    @Override // org.mozilla.javascript.VMBridge
    protected Object getThreadContextHelper() {
        Object[] objArr = this.contextLocal.get();
        if (objArr != null) {
            return objArr;
        }
        Object[] objArr2 = new Object[1];
        this.contextLocal.set(objArr2);
        return objArr2;
    }

    @Override // org.mozilla.javascript.VMBridge
    public boolean isDefaultMethod(Method method) {
        return false;
    }

    @Override // org.mozilla.javascript.VMBridge
    protected Object newInterfaceProxy(Object obj, final ContextFactory contextFactory, final InterfaceAdapter interfaceAdapter, final Object obj2, final Scriptable scriptable) {
        try {
            return ((Constructor) obj).newInstance(new InvocationHandler() { // from class: org.mozilla.javascript.jdk15.VMBridge_jdk15.1
                @Override // java.lang.reflect.InvocationHandler
                public Object invoke(Object obj3, Method method, Object[] objArr) {
                    if (method.getDeclaringClass() == Object.class) {
                        String name = method.getName();
                        if (name.equals("equals")) {
                            return Boolean.valueOf(obj3 == objArr[0]);
                        }
                        if (name.equals("hashCode")) {
                            return Integer.valueOf(obj2.hashCode());
                        }
                        if (name.equals("toString")) {
                            return "Proxy[" + obj2.toString() + "]";
                        }
                    }
                    return interfaceAdapter.invoke(contextFactory, obj2, scriptable, obj3, method, objArr);
                }
            });
        } catch (IllegalAccessException e2) {
            throw Kit.initCause(new IllegalStateException(), e2);
        } catch (InstantiationException e3) {
            throw Kit.initCause(new IllegalStateException(), e3);
        } catch (InvocationTargetException e4) {
            throw Context.throwAsScriptRuntimeEx(e4);
        }
    }

    @Override // org.mozilla.javascript.VMBridge
    protected void setContext(Object obj, Context context) {
        ((Object[]) obj)[0] = context;
    }

    @Override // org.mozilla.javascript.VMBridge
    protected boolean tryToMakeAccessible(AccessibleObject accessibleObject) throws SecurityException {
        if (accessibleObject.isAccessible()) {
            return true;
        }
        try {
            accessibleObject.setAccessible(true);
        } catch (Exception unused) {
        }
        return accessibleObject.isAccessible();
    }
}
