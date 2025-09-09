package org.mozilla.javascript;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Iterator;

/* loaded from: classes5.dex */
public abstract class VMBridge {
    static final VMBridge instance = makeInstance();

    private static VMBridge makeInstance() {
        VMBridge vMBridge;
        String[] strArr = {"org.mozilla.javascript.VMBridge_custom", "org.mozilla.javascript.jdk18.VMBridge_jdk18", "org.mozilla.javascript.jdk15.VMBridge_jdk15"};
        for (int i2 = 0; i2 != 3; i2++) {
            Class<?> clsClassOrNull = Kit.classOrNull(strArr[i2]);
            if (clsClassOrNull != null && (vMBridge = (VMBridge) Kit.newInstanceOrNull(clsClassOrNull)) != null) {
                return vMBridge;
            }
        }
        throw new IllegalStateException("Failed to create VMBridge instance");
    }

    protected abstract Context getContext(Object obj);

    protected abstract Object getInterfaceProxyHelper(ContextFactory contextFactory, Class<?>[] clsArr);

    protected abstract Iterator<?> getJavaIterator(Context context, Scriptable scriptable, Object obj);

    protected abstract Object getThreadContextHelper();

    public abstract boolean isDefaultMethod(Method method);

    protected abstract Object newInterfaceProxy(Object obj, ContextFactory contextFactory, InterfaceAdapter interfaceAdapter, Object obj2, Scriptable scriptable);

    protected abstract void setContext(Object obj, Context context);

    protected abstract boolean tryToMakeAccessible(AccessibleObject accessibleObject);
}
