package org.mozilla.javascript;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.mozilla.javascript.JavaAdapter;

/* loaded from: classes5.dex */
public class ClassCache implements Serializable {
    private static final Object AKEY = "ClassCache";
    private static final long serialVersionUID = -8866246036237312215L;
    private Scriptable associatedScope;
    private volatile boolean cachingIsEnabled = true;
    private transient Map<JavaAdapter.JavaAdapterSignature, Class<?>> classAdapterCache;
    private transient Map<Class<?>, JavaMembers> classTable;
    private int generatedClassSerial;
    private transient Map<Class<?>, Object> interfaceAdapterCache;

    public static ClassCache get(Scriptable scriptable) {
        ClassCache classCache = (ClassCache) ScriptableObject.getTopScopeValue(scriptable, AKEY);
        if (classCache != null) {
            return classCache;
        }
        throw new RuntimeException("Can't find top level scope for ClassCache.get");
    }

    public boolean associate(ScriptableObject scriptableObject) {
        if (scriptableObject.getParentScope() != null) {
            throw new IllegalArgumentException();
        }
        if (this != scriptableObject.associateValue(AKEY, this)) {
            return false;
        }
        this.associatedScope = scriptableObject;
        return true;
    }

    synchronized void cacheInterfaceAdapter(Class<?> cls, Object obj) {
        try {
            if (this.cachingIsEnabled) {
                if (this.interfaceAdapterCache == null) {
                    this.interfaceAdapterCache = new ConcurrentHashMap(16, 0.75f, 1);
                }
                this.interfaceAdapterCache.put(cls, obj);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void clearCaches() {
        this.classTable = null;
        this.classAdapterCache = null;
        this.interfaceAdapterCache = null;
    }

    Scriptable getAssociatedScope() {
        return this.associatedScope;
    }

    Map<Class<?>, JavaMembers> getClassCacheMap() {
        if (this.classTable == null) {
            this.classTable = new ConcurrentHashMap(16, 0.75f, 1);
        }
        return this.classTable;
    }

    Object getInterfaceAdapter(Class<?> cls) {
        Map<Class<?>, Object> map = this.interfaceAdapterCache;
        if (map == null) {
            return null;
        }
        return map.get(cls);
    }

    Map<JavaAdapter.JavaAdapterSignature, Class<?>> getInterfaceAdapterCacheMap() {
        if (this.classAdapterCache == null) {
            this.classAdapterCache = new ConcurrentHashMap(16, 0.75f, 1);
        }
        return this.classAdapterCache;
    }

    public final boolean isCachingEnabled() {
        return this.cachingIsEnabled;
    }

    @Deprecated
    public boolean isInvokerOptimizationEnabled() {
        return false;
    }

    public final synchronized int newClassSerialNumber() {
        int i2;
        i2 = this.generatedClassSerial + 1;
        this.generatedClassSerial = i2;
        return i2;
    }

    public synchronized void setCachingEnabled(boolean z2) {
        try {
            if (z2 == this.cachingIsEnabled) {
                return;
            }
            if (!z2) {
                clearCaches();
            }
            this.cachingIsEnabled = z2;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Deprecated
    public synchronized void setInvokerOptimizationEnabled(boolean z2) {
    }
}
