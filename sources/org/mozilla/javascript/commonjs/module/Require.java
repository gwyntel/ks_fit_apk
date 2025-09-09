package org.mozilla.javascript.commonjs.module;

import com.facebook.share.internal.ShareConstants;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/* loaded from: classes5.dex */
public class Require extends BaseFunction {
    private static final ThreadLocal<Map<String, Scriptable>> loadingModuleInterfaces = new ThreadLocal<>();
    private static final long serialVersionUID = 1;
    private Scriptable mainExports;
    private final ModuleScriptProvider moduleScriptProvider;
    private final Scriptable nativeScope;
    private final Scriptable paths;
    private final Script postExec;
    private final Script preExec;
    private final boolean sandboxed;
    private String mainModuleId = null;
    private final Map<String, Scriptable> exportedModuleInterfaces = new ConcurrentHashMap();
    private final Object loadLock = new Object();

    public Require(Context context, Scriptable scriptable, ModuleScriptProvider moduleScriptProvider, Script script, Script script2, boolean z2) {
        this.moduleScriptProvider = moduleScriptProvider;
        this.nativeScope = scriptable;
        this.sandboxed = z2;
        this.preExec = script;
        this.postExec = script2;
        setPrototype(ScriptableObject.getFunctionPrototype(scriptable));
        if (z2) {
            this.paths = null;
            return;
        }
        Scriptable scriptableNewArray = context.newArray(scriptable, 0);
        this.paths = scriptableNewArray;
        defineReadOnlyProperty(this, "paths", scriptableNewArray);
    }

    private static void defineReadOnlyProperty(ScriptableObject scriptableObject, String str, Object obj) {
        ScriptableObject.putProperty(scriptableObject, str, obj);
        scriptableObject.setAttributes(str, 5);
    }

    private Scriptable executeModuleScript(Context context, String str, Scriptable scriptable, ModuleScript moduleScript, boolean z2) {
        ScriptableObject scriptableObject = (ScriptableObject) context.newObject(this.nativeScope);
        URI uri = moduleScript.getUri();
        URI base = moduleScript.getBase();
        defineReadOnlyProperty(scriptableObject, "id", str);
        if (!this.sandboxed) {
            defineReadOnlyProperty(scriptableObject, ShareConstants.MEDIA_URI, uri.toString());
        }
        Scriptable moduleScope = new ModuleScope(this.nativeScope, uri, base);
        moduleScope.put("exports", moduleScope, scriptable);
        moduleScope.put("module", moduleScope, scriptableObject);
        scriptableObject.put("exports", scriptableObject, scriptable);
        install(moduleScope);
        if (z2) {
            defineReadOnlyProperty(this, "main", scriptableObject);
        }
        executeOptionalScript(this.preExec, context, moduleScope);
        moduleScript.getScript().exec(context, moduleScope);
        executeOptionalScript(this.postExec, context, moduleScope);
        return ScriptRuntime.toObject(context, this.nativeScope, ScriptableObject.getProperty(scriptableObject, "exports"));
    }

    private static void executeOptionalScript(Script script, Context context, Scriptable scriptable) {
        if (script != null) {
            script.exec(context, scriptable);
        }
    }

    private Scriptable getExportedModuleInterface(Context context, String str, URI uri, URI uri2, boolean z2) {
        Scriptable scriptable;
        Scriptable scriptable2 = this.exportedModuleInterfaces.get(str);
        if (scriptable2 != null) {
            if (z2) {
                throw new IllegalStateException("Attempt to set main module after it was loaded");
            }
            return scriptable2;
        }
        ThreadLocal<Map<String, Scriptable>> threadLocal = loadingModuleInterfaces;
        Map<String, Scriptable> map = threadLocal.get();
        if (map != null && (scriptable = map.get(str)) != null) {
            return scriptable;
        }
        synchronized (this.loadLock) {
            try {
                Scriptable scriptable3 = this.exportedModuleInterfaces.get(str);
                if (scriptable3 != null) {
                    return scriptable3;
                }
                ModuleScript module = getModule(context, str, uri, uri2);
                if (this.sandboxed && !module.isSandboxed()) {
                    throw ScriptRuntime.throwError(context, this.nativeScope, "Module \"" + str + "\" is not contained in sandbox.");
                }
                Scriptable scriptableNewObject = context.newObject(this.nativeScope);
                boolean z3 = map == null;
                if (z3) {
                    map = new HashMap<>();
                    threadLocal.set(map);
                }
                map.put(str, scriptableNewObject);
                try {
                    try {
                        Scriptable scriptableExecuteModuleScript = executeModuleScript(context, str, scriptableNewObject, module, z2);
                        if (scriptableNewObject != scriptableExecuteModuleScript) {
                            map.put(str, scriptableExecuteModuleScript);
                            scriptableNewObject = scriptableExecuteModuleScript;
                        }
                        if (z3) {
                            this.exportedModuleInterfaces.putAll(map);
                            threadLocal.set(null);
                        }
                        return scriptableNewObject;
                    } catch (Throwable th) {
                        if (z3) {
                            this.exportedModuleInterfaces.putAll(map);
                            loadingModuleInterfaces.set(null);
                        }
                        throw th;
                    }
                } catch (RuntimeException e2) {
                    map.remove(str);
                    throw e2;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    private ModuleScript getModule(Context context, String str, URI uri, URI uri2) {
        try {
            ModuleScript moduleScript = this.moduleScriptProvider.getModuleScript(context, str, uri, uri2, this.paths);
            if (moduleScript != null) {
                return moduleScript;
            }
            throw ScriptRuntime.throwError(context, this.nativeScope, "Module \"" + str + "\" not found.");
        } catch (RuntimeException e2) {
            throw e2;
        } catch (Exception e3) {
            throw Context.throwAsScriptRuntimeEx(e3);
        }
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        URI uri;
        URI uri2;
        if (objArr == null || objArr.length < 1) {
            throw ScriptRuntime.throwError(context, scriptable, "require() needs one argument");
        }
        String string = (String) Context.jsToJava(objArr[0], String.class);
        if (!string.startsWith("./") && !string.startsWith("../")) {
            uri2 = null;
            uri = null;
        } else {
            if (!(scriptable2 instanceof ModuleScope)) {
                throw ScriptRuntime.throwError(context, scriptable, "Can't resolve relative module ID \"" + string + "\" when require() is used outside of a module");
            }
            ModuleScope moduleScope = (ModuleScope) scriptable2;
            URI base = moduleScope.getBase();
            URI uri3 = moduleScope.getUri();
            URI uriResolve = uri3.resolve(string);
            if (base == null) {
                string = uriResolve.toString();
            } else {
                string = base.relativize(uri3).resolve(string).toString();
                if (string.charAt(0) == '.') {
                    if (this.sandboxed) {
                        throw ScriptRuntime.throwError(context, scriptable, "Module \"" + string + "\" is not contained in sandbox.");
                    }
                    string = uriResolve.toString();
                }
            }
            uri = base;
            uri2 = uriResolve;
        }
        return getExportedModuleInterface(context, string, uri2, uri, false);
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function
    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        throw ScriptRuntime.throwError(context, scriptable, "require() can not be invoked as a constructor");
    }

    @Override // org.mozilla.javascript.BaseFunction
    public int getArity() {
        return 1;
    }

    @Override // org.mozilla.javascript.BaseFunction
    public String getFunctionName() {
        return "require";
    }

    @Override // org.mozilla.javascript.BaseFunction
    public int getLength() {
        return 1;
    }

    public void install(Scriptable scriptable) {
        ScriptableObject.putProperty(scriptable, "require", this);
    }

    public Scriptable requireMain(Context context, String str) {
        URI uri;
        String str2 = this.mainModuleId;
        if (str2 != null) {
            if (str2.equals(str)) {
                return this.mainExports;
            }
            throw new IllegalStateException("Main module already set to " + this.mainModuleId);
        }
        try {
            if (this.moduleScriptProvider.getModuleScript(context, str, null, null, this.paths) != null) {
                this.mainExports = getExportedModuleInterface(context, str, null, null, true);
            } else if (!this.sandboxed) {
                try {
                    uri = new URI(str);
                } catch (URISyntaxException unused) {
                    uri = null;
                }
                if (uri == null || !uri.isAbsolute()) {
                    File file = new File(str);
                    if (!file.isFile()) {
                        throw ScriptRuntime.throwError(context, this.nativeScope, "Module \"" + str + "\" not found.");
                    }
                    uri = file.toURI();
                }
                URI uri2 = uri;
                this.mainExports = getExportedModuleInterface(context, uri2.toString(), uri2, null, true);
            }
            this.mainModuleId = str;
            return this.mainExports;
        } catch (RuntimeException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new RuntimeException(e3);
        }
    }
}
