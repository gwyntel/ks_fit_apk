package org.mozilla.javascript;

/* loaded from: classes5.dex */
public abstract class ES6Iterator extends IdScriptableObject {
    public static final String DONE_PROPERTY = "done";
    private static final int Id_iterator = 2;
    private static final int Id_next = 1;
    private static final int Id_toStringTag = 3;
    private static final int MAX_PROTOTYPE_ID = 3;
    public static final String NEXT_METHOD = "next";
    public static final String VALUE_PROPERTY = "value";
    protected boolean exhausted = false;

    ES6Iterator() {
    }

    static void init(ScriptableObject scriptableObject, boolean z2, IdScriptableObject idScriptableObject, String str) {
        if (scriptableObject != null) {
            idScriptableObject.setParentScope(scriptableObject);
            idScriptableObject.setPrototype(ScriptableObject.getObjectPrototype(scriptableObject));
        }
        idScriptableObject.activatePrototypeMap(3);
        if (z2) {
            idScriptableObject.sealObject();
        }
        if (scriptableObject != null) {
            scriptableObject.associateValue(str, idScriptableObject);
        }
    }

    private Scriptable makeIteratorResult(Context context, Scriptable scriptable, boolean z2, Object obj) {
        Scriptable scriptableNewObject = context.newObject(scriptable);
        ScriptableObject.putProperty(scriptableNewObject, "value", obj);
        ScriptableObject.putProperty(scriptableNewObject, DONE_PROPERTY, Boolean.valueOf(z2));
        return scriptableNewObject;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(getTag())) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (!(scriptable2 instanceof ES6Iterator)) {
            throw IdScriptableObject.incompatibleCallError(idFunctionObject);
        }
        ES6Iterator eS6Iterator = (ES6Iterator) scriptable2;
        if (iMethodId == 1) {
            return eS6Iterator.next(context, scriptable);
        }
        if (iMethodId == 2) {
            return eS6Iterator;
        }
        throw new IllegalArgumentException(String.valueOf(iMethodId));
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(Symbol symbol) {
        if (SymbolKey.ITERATOR.equals(symbol)) {
            return 2;
        }
        return SymbolKey.TO_STRING_TAG.equals(symbol) ? 3 : 0;
    }

    protected abstract String getTag();

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        if (i2 == 1) {
            initPrototypeMethod(getTag(), i2, NEXT_METHOD, 0);
        } else if (i2 == 2) {
            initPrototypeMethod(getTag(), i2, SymbolKey.ITERATOR, "[Symbol.iterator]", 0);
        } else {
            if (i2 != 3) {
                throw new IllegalArgumentException(String.valueOf(i2));
            }
            initPrototypeValue(3, SymbolKey.TO_STRING_TAG, getClassName(), 3);
        }
    }

    protected abstract boolean isDone(Context context, Scriptable scriptable);

    protected Object next(Context context, Scriptable scriptable) {
        Object objNextValue = Undefined.instance;
        boolean z2 = isDone(context, scriptable) || this.exhausted;
        if (z2) {
            this.exhausted = true;
        } else {
            objNextValue = nextValue(context, scriptable);
        }
        return makeIteratorResult(context, scriptable, z2, objNextValue);
    }

    protected abstract Object nextValue(Context context, Scriptable scriptable);

    ES6Iterator(Scriptable scriptable) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        setParentScope(topLevelScope);
        setPrototype((IdScriptableObject) ScriptableObject.getTopScopeValue(topLevelScope, getTag()));
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        return NEXT_METHOD.equals(str) ? 1 : 0;
    }
}
