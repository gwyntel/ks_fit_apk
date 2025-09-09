package org.mozilla.javascript;

/* loaded from: classes5.dex */
public class Delegator implements Function {
    protected Scriptable obj;

    public Delegator() {
        this.obj = null;
    }

    @Override // org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        return ((Function) this.obj).call(context, scriptable, scriptable2, objArr);
    }

    @Override // org.mozilla.javascript.Function
    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        Scriptable scriptable2 = this.obj;
        if (scriptable2 != null) {
            return ((Function) scriptable2).construct(context, scriptable, objArr);
        }
        Delegator delegatorNewInstance = newInstance();
        delegatorNewInstance.setDelegee(objArr.length == 0 ? new NativeObject() : ScriptRuntime.toObject(context, scriptable, objArr[0]));
        return delegatorNewInstance;
    }

    @Override // org.mozilla.javascript.Scriptable
    public void delete(String str) {
        this.obj.delete(str);
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object get(String str, Scriptable scriptable) {
        return this.obj.get(str, scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public String getClassName() {
        return this.obj.getClassName();
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        return (cls == null || cls == ScriptRuntime.ScriptableClass || cls == ScriptRuntime.FunctionClass) ? this : this.obj.getDefaultValue(cls);
    }

    public Scriptable getDelegee() {
        return this.obj;
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object[] getIds() {
        return this.obj.getIds();
    }

    @Override // org.mozilla.javascript.Scriptable
    public Scriptable getParentScope() {
        return this.obj.getParentScope();
    }

    @Override // org.mozilla.javascript.Scriptable
    public Scriptable getPrototype() {
        return this.obj.getPrototype();
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean has(String str, Scriptable scriptable) {
        return this.obj.has(str, scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean hasInstance(Scriptable scriptable) {
        return this.obj.hasInstance(scriptable);
    }

    protected Delegator newInstance() {
        try {
            return (Delegator) getClass().newInstance();
        } catch (Exception e2) {
            throw Context.throwAsScriptRuntimeEx(e2);
        }
    }

    @Override // org.mozilla.javascript.Scriptable
    public void put(String str, Scriptable scriptable, Object obj) {
        this.obj.put(str, scriptable, obj);
    }

    public void setDelegee(Scriptable scriptable) {
        this.obj = scriptable;
    }

    @Override // org.mozilla.javascript.Scriptable
    public void setParentScope(Scriptable scriptable) {
        this.obj.setParentScope(scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void setPrototype(Scriptable scriptable) {
        this.obj.setPrototype(scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void delete(int i2) {
        this.obj.delete(i2);
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object get(int i2, Scriptable scriptable) {
        return this.obj.get(i2, scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean has(int i2, Scriptable scriptable) {
        return this.obj.has(i2, scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void put(int i2, Scriptable scriptable, Object obj) {
        this.obj.put(i2, scriptable, obj);
    }

    public Delegator(Scriptable scriptable) {
        this.obj = scriptable;
    }
}
