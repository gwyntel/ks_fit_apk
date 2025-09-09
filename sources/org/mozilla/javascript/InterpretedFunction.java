package org.mozilla.javascript;

import org.mozilla.javascript.debug.DebuggableScript;

/* loaded from: classes5.dex */
final class InterpretedFunction extends NativeFunction implements Script {
    static final long serialVersionUID = 541475680333911468L;
    InterpreterData idata;
    SecurityController securityController;
    Object securityDomain;

    private InterpretedFunction(InterpreterData interpreterData, Object obj) {
        Object dynamicSecurityDomain;
        this.idata = interpreterData;
        SecurityController securityController = Context.getContext().getSecurityController();
        if (securityController != null) {
            dynamicSecurityDomain = securityController.getDynamicSecurityDomain(obj);
        } else {
            if (obj != null) {
                throw new IllegalArgumentException();
            }
            dynamicSecurityDomain = null;
        }
        this.securityController = securityController;
        this.securityDomain = dynamicSecurityDomain;
    }

    static InterpretedFunction createFunction(Context context, Scriptable scriptable, InterpreterData interpreterData, Object obj) {
        InterpretedFunction interpretedFunction = new InterpretedFunction(interpreterData, obj);
        interpretedFunction.initScriptFunction(context, scriptable);
        return interpretedFunction;
    }

    static InterpretedFunction createScript(InterpreterData interpreterData, Object obj) {
        return new InterpretedFunction(interpreterData, obj);
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        return !ScriptRuntime.hasTopCall(context) ? ScriptRuntime.doTopCall(this, context, scriptable, scriptable2, objArr, this.idata.isStrict) : Interpreter.interpret(this, context, scriptable, scriptable2, objArr);
    }

    @Override // org.mozilla.javascript.Script
    public Object exec(Context context, Scriptable scriptable) {
        if (isScript()) {
            return !ScriptRuntime.hasTopCall(context) ? ScriptRuntime.doTopCall(this, context, scriptable, scriptable, ScriptRuntime.emptyArgs, this.idata.isStrict) : Interpreter.interpret(this, context, scriptable, scriptable, ScriptRuntime.emptyArgs);
        }
        throw new IllegalStateException();
    }

    @Override // org.mozilla.javascript.NativeFunction
    public DebuggableScript getDebuggableView() {
        return this.idata;
    }

    @Override // org.mozilla.javascript.NativeFunction
    public String getEncodedSource() {
        return Interpreter.getEncodedSource(this.idata);
    }

    @Override // org.mozilla.javascript.BaseFunction
    public String getFunctionName() {
        String str = this.idata.itsName;
        return str == null ? "" : str;
    }

    @Override // org.mozilla.javascript.NativeFunction
    protected int getLanguageVersion() {
        return this.idata.languageVersion;
    }

    @Override // org.mozilla.javascript.NativeFunction
    protected int getParamAndVarCount() {
        return this.idata.argNames.length;
    }

    @Override // org.mozilla.javascript.NativeFunction
    protected int getParamCount() {
        return this.idata.argCount;
    }

    @Override // org.mozilla.javascript.NativeFunction
    protected boolean getParamOrVarConst(int i2) {
        return this.idata.argIsConst[i2];
    }

    @Override // org.mozilla.javascript.NativeFunction
    protected String getParamOrVarName(int i2) {
        return this.idata.argNames[i2];
    }

    public boolean isScript() {
        return this.idata.itsFunctionType == 0;
    }

    @Override // org.mozilla.javascript.NativeFunction
    public Object resumeGenerator(Context context, Scriptable scriptable, int i2, Object obj, Object obj2) {
        return Interpreter.resumeGenerator(context, scriptable, i2, obj, obj2);
    }

    static InterpretedFunction createFunction(Context context, Scriptable scriptable, InterpretedFunction interpretedFunction, int i2) {
        InterpretedFunction interpretedFunction2 = new InterpretedFunction(interpretedFunction, i2);
        interpretedFunction2.initScriptFunction(context, scriptable);
        return interpretedFunction2;
    }

    private InterpretedFunction(InterpretedFunction interpretedFunction, int i2) {
        this.idata = interpretedFunction.idata.itsNestedFunctions[i2];
        this.securityController = interpretedFunction.securityController;
        this.securityDomain = interpretedFunction.securityDomain;
    }
}
