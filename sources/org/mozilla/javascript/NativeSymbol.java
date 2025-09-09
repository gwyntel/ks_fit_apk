package org.mozilla.javascript;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class NativeSymbol extends IdScriptableObject implements Symbol {
    public static final String CLASS_NAME = "Symbol";
    private static final int ConstructorId_for = -1;
    private static final int ConstructorId_keyFor = -2;
    private static final int Id_constructor = 1;
    private static final int Id_toString = 2;
    private static final int Id_valueOf = 4;
    private static final int MAX_PROTOTYPE_ID = 5;
    private static final int SymbolId_toPrimitive = 5;
    private static final int SymbolId_toStringTag = 3;
    public static final String TYPE_NAME = "symbol";
    private static final long serialVersionUID = -589539749749830003L;
    private final SymbolKey key;
    private final NativeSymbol symbolData;
    private static final Object GLOBAL_TABLE_KEY = new Object();
    private static final Object CONSTRUCTOR_SLOT = new Object();

    private NativeSymbol(String str) {
        this.key = new SymbolKey(str);
        this.symbolData = this;
    }

    public static NativeSymbol construct(Context context, Scriptable scriptable, Object[] objArr) {
        Object obj = CONSTRUCTOR_SLOT;
        context.putThreadLocal(obj, Boolean.TRUE);
        try {
            NativeSymbol nativeSymbol = (NativeSymbol) context.newObject(scriptable, CLASS_NAME, objArr);
            context.removeThreadLocal(obj);
            return nativeSymbol;
        } catch (Throwable th) {
            context.removeThreadLocal(CONSTRUCTOR_SLOT);
            throw th;
        }
    }

    private static void createStandardSymbol(Context context, Scriptable scriptable, ScriptableObject scriptableObject, String str, SymbolKey symbolKey) {
        scriptableObject.defineProperty(str, context.newObject(scriptable, CLASS_NAME, new Object[]{str, symbolKey}), 7);
    }

    private Map<String, NativeSymbol> getGlobalMap() {
        ScriptableObject scriptableObject = (ScriptableObject) ScriptableObject.getTopLevelScope(this);
        Object obj = GLOBAL_TABLE_KEY;
        Map<String, NativeSymbol> map = (Map) scriptableObject.getAssociatedValue(obj);
        if (map != null) {
            return map;
        }
        HashMap map2 = new HashMap();
        scriptableObject.associateValue(obj, map2);
        return map2;
    }

    private NativeSymbol getSelf(Object obj) {
        try {
            return (NativeSymbol) obj;
        } catch (ClassCastException unused) {
            throw ScriptRuntime.typeError1("msg.invalid.type", obj.getClass().getName());
        }
    }

    public static void init(Context context, Scriptable scriptable, boolean z2) {
        IdFunctionObject idFunctionObjectExportAsJSClass = new NativeSymbol("").exportAsJSClass(5, scriptable, false);
        Object obj = CONSTRUCTOR_SLOT;
        context.putThreadLocal(obj, Boolean.TRUE);
        try {
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "iterator", SymbolKey.ITERATOR);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "species", SymbolKey.SPECIES);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "toStringTag", SymbolKey.TO_STRING_TAG);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "hasInstance", SymbolKey.HAS_INSTANCE);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "isConcatSpreadable", SymbolKey.IS_CONCAT_SPREADABLE);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "isRegExp", SymbolKey.IS_REGEXP);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "toPrimitive", SymbolKey.TO_PRIMITIVE);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "match", SymbolKey.MATCH);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "replace", SymbolKey.REPLACE);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, FirebaseAnalytics.Event.SEARCH, SymbolKey.SEARCH);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "split", SymbolKey.SPLIT);
            createStandardSymbol(context, scriptable, idFunctionObjectExportAsJSClass, "unscopables", SymbolKey.UNSCOPABLES);
            context.removeThreadLocal(obj);
            if (z2) {
                idFunctionObjectExportAsJSClass.sealObject();
            }
        } catch (Throwable th) {
            context.removeThreadLocal(CONSTRUCTOR_SLOT);
            throw th;
        }
    }

    private static NativeSymbol js_constructor(Object[] objArr) {
        String string = "";
        if (objArr.length > 0 && !Undefined.instance.equals(objArr[0])) {
            string = ScriptRuntime.toString(objArr[0]);
        }
        return objArr.length > 1 ? new NativeSymbol((SymbolKey) objArr[1]) : new NativeSymbol(string);
    }

    private Object js_for(Context context, Scriptable scriptable, Object[] objArr) {
        String string = ScriptRuntime.toString(objArr.length > 0 ? objArr[0] : Undefined.instance);
        Map<String, NativeSymbol> globalMap = getGlobalMap();
        NativeSymbol nativeSymbol = globalMap.get(string);
        if (nativeSymbol != null) {
            return nativeSymbol;
        }
        NativeSymbol nativeSymbolConstruct = construct(context, scriptable, new Object[]{string});
        globalMap.put(string, nativeSymbolConstruct);
        return nativeSymbolConstruct;
    }

    private Object js_keyFor(Context context, Scriptable scriptable, Object[] objArr) {
        Object obj = objArr.length > 0 ? objArr[0] : Undefined.instance;
        if (!(obj instanceof NativeSymbol)) {
            throw ScriptRuntime.throwCustomError(context, scriptable, "TypeError", "Not a Symbol");
        }
        NativeSymbol nativeSymbol = (NativeSymbol) obj;
        for (Map.Entry<String, NativeSymbol> entry : getGlobalMap().entrySet()) {
            if (entry.getValue().key == nativeSymbol.key) {
                return entry.getKey();
            }
        }
        return Undefined.instance;
    }

    private Object js_valueOf() {
        return this.symbolData;
    }

    public boolean equals(Object obj) {
        return this.key.equals(obj);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(CLASS_NAME)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == -2) {
            return js_keyFor(context, scriptable, objArr);
        }
        if (iMethodId == -1) {
            return js_for(context, scriptable, objArr);
        }
        if (iMethodId != 1) {
            return iMethodId != 2 ? (iMethodId == 4 || iMethodId == 5) ? getSelf(scriptable2).js_valueOf() : super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr) : getSelf(scriptable2).toString();
        }
        if (scriptable2 != null) {
            return construct(context, scriptable, objArr);
        }
        if (context.getThreadLocal(CONSTRUCTOR_SLOT) != null) {
            return js_constructor(objArr);
        }
        throw ScriptRuntime.typeError0("msg.no.symbol.new");
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        super.fillConstructorProperties(idFunctionObject);
        addIdFunctionProperty(idFunctionObject, CLASS_NAME, -1, "for", 1);
        addIdFunctionProperty(idFunctionObject, CLASS_NAME, -2, "keyFor", 1);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int i2;
        int length = str.length();
        if (length == 7) {
            str2 = "valueOf";
            i2 = 4;
        } else if (length == 8) {
            str2 = "toString";
            i2 = 2;
        } else if (length == 11) {
            str2 = "constructor";
            i2 = 1;
        } else {
            str2 = null;
            i2 = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i2;
        }
        return 0;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CLASS_NAME;
    }

    SymbolKey getKey() {
        return this.key;
    }

    @Override // org.mozilla.javascript.ScriptableObject
    public String getTypeOf() {
        return isSymbol() ? TYPE_NAME : super.getTypeOf();
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        if (i2 == 1) {
            initPrototypeMethod(CLASS_NAME, i2, "constructor", 1);
            return;
        }
        if (i2 == 2) {
            initPrototypeMethod(CLASS_NAME, i2, "toString", 0);
            return;
        }
        if (i2 == 3) {
            initPrototypeValue(i2, SymbolKey.TO_STRING_TAG, CLASS_NAME, 3);
            return;
        }
        if (i2 == 4) {
            initPrototypeMethod(CLASS_NAME, i2, "valueOf", 0);
        } else if (i2 != 5) {
            super.initPrototypeId(i2);
        } else {
            initPrototypeMethod(CLASS_NAME, i2, SymbolKey.TO_PRIMITIVE, "Symbol.toPrimitive", 1);
        }
    }

    public boolean isSymbol() {
        return this.symbolData == this;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(String str, Scriptable scriptable, Object obj) {
        if (!isSymbol()) {
            super.put(str, scriptable, obj);
        } else if (Context.getCurrentContext().isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.no.assign.symbol.strict");
        }
    }

    public String toString() {
        return this.key.toString();
    }

    private NativeSymbol(SymbolKey symbolKey) {
        this.key = symbolKey;
        this.symbolData = this;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(int i2, Scriptable scriptable, Object obj) {
        if (!isSymbol()) {
            super.put(i2, scriptable, obj);
        } else if (Context.getCurrentContext().isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.no.assign.symbol.strict");
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(Symbol symbol) {
        if (SymbolKey.TO_STRING_TAG.equals(symbol)) {
            return 3;
        }
        return SymbolKey.TO_PRIMITIVE.equals(symbol) ? 5 : 0;
    }

    public NativeSymbol(NativeSymbol nativeSymbol) {
        this.key = nativeSymbol.key;
        this.symbolData = nativeSymbol.symbolData;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.SymbolScriptable
    public void put(Symbol symbol, Scriptable scriptable, Object obj) {
        if (!isSymbol()) {
            super.put(symbol, scriptable, obj);
        } else if (Context.getCurrentContext().isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.no.assign.symbol.strict");
        }
    }
}
