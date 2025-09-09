package org.mozilla.javascript;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class NativeWith implements Scriptable, IdFunctionCall, Serializable {
    private static final Object FTAG = "With";
    private static final int Id_constructor = 1;
    private static final long serialVersionUID = 1;
    protected Scriptable parent;
    protected Scriptable prototype;

    private NativeWith() {
    }

    static void init(Scriptable scriptable, boolean z2) {
        NativeWith nativeWith = new NativeWith();
        nativeWith.setParentScope(scriptable);
        nativeWith.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        IdFunctionObject idFunctionObject = new IdFunctionObject(nativeWith, FTAG, 1, "With", 0, scriptable);
        idFunctionObject.markAsConstructor(nativeWith);
        if (z2) {
            idFunctionObject.sealObject();
        }
        idFunctionObject.exportAsScopeProperty();
    }

    static boolean isWithFunction(Object obj) {
        if (!(obj instanceof IdFunctionObject)) {
            return false;
        }
        IdFunctionObject idFunctionObject = (IdFunctionObject) obj;
        return idFunctionObject.hasTag(FTAG) && idFunctionObject.methodId() == 1;
    }

    static Object newWithSpecial(Context context, Scriptable scriptable, Object[] objArr) {
        ScriptRuntime.checkDeprecated(context, "With");
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        NativeWith nativeWith = new NativeWith();
        nativeWith.setPrototype(objArr.length == 0 ? ScriptableObject.getObjectPrototype(topLevelScope) : ScriptRuntime.toObject(context, topLevelScope, objArr[0]));
        nativeWith.setParentScope(topLevelScope);
        return nativeWith;
    }

    @Override // org.mozilla.javascript.Scriptable
    public void delete(String str) {
        this.prototype.delete(str);
    }

    @Override // org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (idFunctionObject.hasTag(FTAG) && idFunctionObject.methodId() == 1) {
            throw Context.reportRuntimeError1("msg.cant.call.indirect", "With");
        }
        throw idFunctionObject.unknown();
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object get(String str, Scriptable scriptable) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        return this.prototype.get(str, scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "With";
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        return this.prototype.getDefaultValue(cls);
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object[] getIds() {
        return this.prototype.getIds();
    }

    @Override // org.mozilla.javascript.Scriptable
    public Scriptable getParentScope() {
        return this.parent;
    }

    @Override // org.mozilla.javascript.Scriptable
    public Scriptable getPrototype() {
        return this.prototype;
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean has(String str, Scriptable scriptable) {
        Scriptable scriptable2 = this.prototype;
        return scriptable2.has(str, scriptable2);
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean hasInstance(Scriptable scriptable) {
        return this.prototype.hasInstance(scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void put(String str, Scriptable scriptable, Object obj) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        this.prototype.put(str, scriptable, obj);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void setParentScope(Scriptable scriptable) {
        this.parent = scriptable;
    }

    @Override // org.mozilla.javascript.Scriptable
    public void setPrototype(Scriptable scriptable) {
        this.prototype = scriptable;
    }

    protected Object updateDotQuery(boolean z2) {
        throw new IllegalStateException();
    }

    protected NativeWith(Scriptable scriptable, Scriptable scriptable2) {
        this.parent = scriptable;
        this.prototype = scriptable2;
    }

    @Override // org.mozilla.javascript.Scriptable
    public void delete(int i2) {
        this.prototype.delete(i2);
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean has(int i2, Scriptable scriptable) {
        Scriptable scriptable2 = this.prototype;
        return scriptable2.has(i2, scriptable2);
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object get(int i2, Scriptable scriptable) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        return this.prototype.get(i2, scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void put(int i2, Scriptable scriptable, Object obj) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        this.prototype.put(i2, scriptable, obj);
    }
}
