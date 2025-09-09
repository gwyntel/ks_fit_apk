package org.mozilla.javascript;

import com.tekartik.sqflite.Constant;

/* loaded from: classes5.dex */
public final class NativeCall extends IdScriptableObject {
    private static final Object CALL_TAG = "Call";
    private static final int Id_constructor = 1;
    private static final int MAX_PROTOTYPE_ID = 1;
    static final long serialVersionUID = -7471457301304454454L;
    private Arguments arguments;
    NativeFunction function;
    boolean isStrict;
    Object[] originalArgs;
    transient NativeCall parentActivationCall;

    NativeCall() {
    }

    static void init(Scriptable scriptable, boolean z2) {
        new NativeCall().exportAsJSClass(1, scriptable, z2);
    }

    public void defineAttributesForArguments() {
        Arguments arguments = this.arguments;
        if (arguments != null) {
            arguments.defineAttributesForStrictMode();
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(CALL_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId != 1) {
            throw new IllegalArgumentException(String.valueOf(iMethodId));
        }
        if (scriptable2 != null) {
            throw Context.reportRuntimeError1("msg.only.from.new", "Call");
        }
        ScriptRuntime.checkDeprecated(context, "Call");
        NativeCall nativeCall = new NativeCall();
        nativeCall.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        return nativeCall;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        return str.equals("constructor") ? 1 : 0;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Call";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        if (i2 != 1) {
            throw new IllegalArgumentException(String.valueOf(i2));
        }
        initPrototypeMethod(CALL_TAG, i2, "constructor", 1);
    }

    NativeCall(NativeFunction nativeFunction, Scriptable scriptable, Object[] objArr, boolean z2, boolean z3) {
        this.function = nativeFunction;
        setParentScope(scriptable);
        this.originalArgs = objArr == null ? ScriptRuntime.emptyArgs : objArr;
        this.isStrict = z3;
        int paramAndVarCount = nativeFunction.getParamAndVarCount();
        int paramCount = nativeFunction.getParamCount();
        if (paramAndVarCount != 0) {
            int i2 = 0;
            while (i2 < paramCount) {
                defineProperty(nativeFunction.getParamOrVarName(i2), i2 < objArr.length ? objArr[i2] : Undefined.instance, 4);
                i2++;
            }
        }
        if (!super.has(Constant.PARAM_SQL_ARGUMENTS, this) && !z2) {
            Arguments arguments = new Arguments(this);
            this.arguments = arguments;
            defineProperty(Constant.PARAM_SQL_ARGUMENTS, arguments, 4);
        }
        if (paramAndVarCount != 0) {
            while (paramCount < paramAndVarCount) {
                String paramOrVarName = nativeFunction.getParamOrVarName(paramCount);
                if (!super.has(paramOrVarName, this)) {
                    if (nativeFunction.getParamOrVarConst(paramCount)) {
                        defineProperty(paramOrVarName, Undefined.instance, 13);
                    } else {
                        defineProperty(paramOrVarName, Undefined.instance, 4);
                    }
                }
                paramCount++;
            }
        }
    }
}
