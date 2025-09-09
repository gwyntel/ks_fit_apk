package org.mozilla.javascript.commonjs.module.provider;

import java.io.Reader;
import java.io.Serializable;
import java.net.URI;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.commonjs.module.ModuleScript;
import org.mozilla.javascript.commonjs.module.ModuleScriptProvider;

/* loaded from: classes5.dex */
public abstract class CachingModuleScriptProviderBase implements ModuleScriptProvider, Serializable {
    private static final int loadConcurrencyLevel = Runtime.getRuntime().availableProcessors() * 8;
    private static final int loadLockCount;
    private static final int loadLockMask;
    private static final int loadLockShift;
    private static final long serialVersionUID = 1;
    private final Object[] loadLocks = new Object[loadLockCount];
    private final ModuleSourceProvider moduleSourceProvider;

    public static class CachedModuleScript {
        private final ModuleScript moduleScript;
        private final Object validator;

        public CachedModuleScript(ModuleScript moduleScript, Object obj) {
            this.moduleScript = moduleScript;
            this.validator = obj;
        }

        ModuleScript getModule() {
            return this.moduleScript;
        }

        Object getValidator() {
            return this.validator;
        }
    }

    static {
        int i2 = 0;
        int i3 = 1;
        while (i3 < loadConcurrencyLevel) {
            i2++;
            i3 <<= 1;
        }
        loadLockShift = 32 - i2;
        loadLockMask = i3 - 1;
        loadLockCount = i3;
    }

    protected CachingModuleScriptProviderBase(ModuleSourceProvider moduleSourceProvider) {
        int i2 = 0;
        while (true) {
            Object[] objArr = this.loadLocks;
            if (i2 >= objArr.length) {
                this.moduleSourceProvider = moduleSourceProvider;
                return;
            } else {
                objArr[i2] = new Object();
                i2++;
            }
        }
    }

    private static boolean equal(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    protected static int getConcurrencyLevel() {
        return loadLockCount;
    }

    private static Object getValidator(CachedModuleScript cachedModuleScript) {
        if (cachedModuleScript == null) {
            return null;
        }
        return cachedModuleScript.getValidator();
    }

    protected abstract CachedModuleScript getLoadedModule(String str);

    @Override // org.mozilla.javascript.commonjs.module.ModuleScriptProvider
    public ModuleScript getModuleScript(Context context, String str, URI uri, URI uri2, Scriptable scriptable) throws Exception {
        CachedModuleScript loadedModule = getLoadedModule(str);
        Object validator = getValidator(loadedModule);
        ModuleSource moduleSourceLoadSource = uri == null ? this.moduleSourceProvider.loadSource(str, scriptable, validator) : this.moduleSourceProvider.loadSource(uri, uri2, validator);
        if (moduleSourceLoadSource == ModuleSourceProvider.NOT_MODIFIED) {
            return loadedModule.getModule();
        }
        if (moduleSourceLoadSource == null) {
            return null;
        }
        Reader reader = moduleSourceLoadSource.getReader();
        try {
            synchronized (this.loadLocks[(str.hashCode() >>> loadLockShift) & loadLockMask]) {
                CachedModuleScript loadedModule2 = getLoadedModule(str);
                if (loadedModule2 != null && !equal(validator, getValidator(loadedModule2))) {
                    return loadedModule2.getModule();
                }
                URI uri3 = moduleSourceLoadSource.getUri();
                ModuleScript moduleScript = new ModuleScript(context.compileReader(reader, uri3.toString(), 1, moduleSourceLoadSource.getSecurityDomain()), uri3, moduleSourceLoadSource.getBase());
                putLoadedModule(str, moduleScript, moduleSourceLoadSource.getValidator());
                return moduleScript;
            }
        } finally {
            reader.close();
        }
    }

    protected abstract void putLoadedModule(String str, ModuleScript moduleScript, Object obj);
}
