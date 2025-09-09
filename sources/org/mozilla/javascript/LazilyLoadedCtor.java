package org.mozilla.javascript;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;

/* loaded from: classes5.dex */
public final class LazilyLoadedCtor implements Serializable {
    private static final int STATE_BEFORE_INIT = 0;
    private static final int STATE_INITIALIZING = 1;
    private static final int STATE_WITH_VALUE = 2;
    private static final long serialVersionUID = 1;
    private final String className;
    private Object initializedValue;
    private final boolean privileged;
    private final String propertyName;
    private final ScriptableObject scope;
    private final boolean sealed;
    private int state;

    public LazilyLoadedCtor(ScriptableObject scriptableObject, String str, String str2, boolean z2) {
        this(scriptableObject, str, str2, z2, false);
    }

    private Object buildValue() {
        return this.privileged ? AccessController.doPrivileged(new PrivilegedAction<Object>() { // from class: org.mozilla.javascript.LazilyLoadedCtor.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                return LazilyLoadedCtor.this.buildValue0();
            }
        }) : buildValue0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object buildValue0() {
        Class<? extends Scriptable> clsCast = cast(Kit.classOrNull(this.className));
        if (clsCast != null) {
            try {
                BaseFunction baseFunctionBuildClassCtor = ScriptableObject.buildClassCtor(this.scope, clsCast, this.sealed, false);
                if (baseFunctionBuildClassCtor != null) {
                    return baseFunctionBuildClassCtor;
                }
                Scriptable scriptable = this.scope;
                Object obj = scriptable.get(this.propertyName, scriptable);
                if (obj != Scriptable.NOT_FOUND) {
                    return obj;
                }
            } catch (IllegalAccessException | InstantiationException | SecurityException | RhinoException unused) {
            } catch (InvocationTargetException e2) {
                Throwable targetException = e2.getTargetException();
                if (targetException instanceof RuntimeException) {
                    throw ((RuntimeException) targetException);
                }
            }
        }
        return Scriptable.NOT_FOUND;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Class<? extends Scriptable> cast(Class<?> cls) {
        return cls;
    }

    Object getValue() {
        if (this.state == 2) {
            return this.initializedValue;
        }
        throw new IllegalStateException(this.propertyName);
    }

    void init() {
        synchronized (this) {
            try {
                int i2 = this.state;
                if (i2 == 1) {
                    throw new IllegalStateException("Recursive initialization for " + this.propertyName);
                }
                if (i2 == 0) {
                    this.state = 1;
                    Object obj = Scriptable.NOT_FOUND;
                    try {
                        this.initializedValue = buildValue();
                        this.state = 2;
                    } catch (Throwable th) {
                        this.initializedValue = obj;
                        this.state = 2;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    LazilyLoadedCtor(ScriptableObject scriptableObject, String str, String str2, boolean z2, boolean z3) {
        this.scope = scriptableObject;
        this.propertyName = str;
        this.className = str2;
        this.sealed = z2;
        this.privileged = z3;
        this.state = 0;
        scriptableObject.addLazilyInitializedValue(str, 0, this, 2);
    }
}
