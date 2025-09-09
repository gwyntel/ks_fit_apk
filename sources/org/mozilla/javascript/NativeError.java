package org.mozilla.javascript;

import java.io.Serializable;
import java.lang.reflect.Method;

/* loaded from: classes5.dex */
final class NativeError extends IdScriptableObject {
    private static final int ConstructorId_captureStackTrace = -1;
    public static final int DEFAULT_STACK_LIMIT = -1;
    private static final Method ERROR_DELEGATE_GET_STACK;
    private static final Method ERROR_DELEGATE_SET_STACK;
    private static final Object ERROR_TAG = "Error";
    private static final int Id_constructor = 1;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int MAX_PROTOTYPE_ID = 3;
    private static final String STACK_HIDE_KEY = "_stackHide";
    static final long serialVersionUID = -5338413581437645187L;
    private RhinoException stackProvider;

    static {
        try {
            ERROR_DELEGATE_GET_STACK = NativeError.class.getMethod("getStackDelegated", Scriptable.class);
            ERROR_DELEGATE_SET_STACK = NativeError.class.getMethod("setStackDelegated", Scriptable.class, Object.class);
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        }
    }

    NativeError() {
    }

    private Object callPrepareStack(Function function, ScriptStackElement[] scriptStackElementArr) {
        Context currentContext = Context.getCurrentContext();
        Object[] objArr = new Object[scriptStackElementArr.length];
        for (int i2 = 0; i2 < scriptStackElementArr.length; i2++) {
            NativeCallSite nativeCallSite = (NativeCallSite) currentContext.newObject(this, "CallSite");
            nativeCallSite.setElement(scriptStackElementArr[i2]);
            objArr[i2] = nativeCallSite;
        }
        return function.call(currentContext, function, this, new Object[]{this, currentContext.newArray(this, objArr)});
    }

    static void init(Scriptable scriptable, boolean z2) {
        NativeError nativeError = new NativeError();
        ScriptableObject.putProperty(nativeError, "name", "Error");
        ScriptableObject.putProperty(nativeError, "message", "");
        ScriptableObject.putProperty(nativeError, "fileName", "");
        ScriptableObject.putProperty((Scriptable) nativeError, "lineNumber", (Object) 0);
        nativeError.setAttributes("name", 2);
        nativeError.setAttributes("message", 2);
        nativeError.exportAsJSClass(3, scriptable, z2);
        NativeCallSite.init(nativeError, z2);
    }

    private static void js_captureStackTrace(Context context, Scriptable scriptable, Object[] objArr) {
        Object obj;
        ScriptableObject scriptableObject = (ScriptableObject) ScriptRuntime.toObjectOrNull(context, objArr[0], scriptable);
        Scriptable scriptable2 = objArr.length > 1 ? (Function) ScriptRuntime.toObjectOrNull(context, objArr[1], scriptable) : null;
        NativeError nativeError = (NativeError) context.newObject(scriptable, "Error");
        nativeError.setStackProvider(new EvaluatorException("[object Object]"));
        if (scriptable2 != null && (obj = scriptable2.get("name", scriptable2)) != null && !Undefined.instance.equals(obj)) {
            nativeError.associateValue(STACK_HIDE_KEY, Context.toString(obj));
        }
        scriptableObject.defineProperty("stack", nativeError, ERROR_DELEGATE_GET_STACK, ERROR_DELEGATE_SET_STACK, 0);
    }

    private static String js_toSource(Context context, Scriptable scriptable, Scriptable scriptable2) {
        int int32;
        Object property = ScriptableObject.getProperty(scriptable2, "name");
        Object property2 = ScriptableObject.getProperty(scriptable2, "message");
        Object property3 = ScriptableObject.getProperty(scriptable2, "fileName");
        Object property4 = ScriptableObject.getProperty(scriptable2, "lineNumber");
        StringBuilder sb = new StringBuilder();
        sb.append("(new ");
        Object obj = Scriptable.NOT_FOUND;
        if (property == obj) {
            property = Undefined.instance;
        }
        sb.append(ScriptRuntime.toString(property));
        sb.append("(");
        if (property2 != obj || property3 != obj || property4 != obj) {
            if (property2 == obj) {
                property2 = "";
            }
            sb.append(ScriptRuntime.uneval(context, scriptable, property2));
            if (property3 != obj || property4 != obj) {
                sb.append(", ");
                if (property3 == obj) {
                    property3 = "";
                }
                sb.append(ScriptRuntime.uneval(context, scriptable, property3));
                if (property4 != obj && (int32 = ScriptRuntime.toInt32(property4)) != 0) {
                    sb.append(", ");
                    sb.append(ScriptRuntime.toString(int32));
                }
            }
        }
        sb.append("))");
        return sb.toString();
    }

    private static Object js_toString(Scriptable scriptable) {
        Object property = ScriptableObject.getProperty(scriptable, "name");
        Object obj = Scriptable.NOT_FOUND;
        String string = (property == obj || property == Undefined.instance) ? "Error" : ScriptRuntime.toString(property);
        Object property2 = ScriptableObject.getProperty(scriptable, "message");
        String string2 = (property2 == obj || property2 == Undefined.instance) ? "" : ScriptRuntime.toString(property2);
        if (string.toString().length() == 0) {
            return string2;
        }
        if (string2.toString().length() == 0) {
            return string;
        }
        return string + ": " + string2;
    }

    static NativeError make(Context context, Scriptable scriptable, IdFunctionObject idFunctionObject, Object[] objArr) {
        Scriptable scriptable2 = (Scriptable) idFunctionObject.get("prototype", idFunctionObject);
        NativeError nativeError = new NativeError();
        nativeError.setPrototype(scriptable2);
        nativeError.setParentScope(scriptable);
        int length = objArr.length;
        if (length >= 1) {
            Object obj = objArr[0];
            if (obj != Undefined.instance) {
                ScriptableObject.putProperty(nativeError, "message", ScriptRuntime.toString(obj));
            }
            if (length >= 2) {
                ScriptableObject.putProperty(nativeError, "fileName", objArr[1]);
                if (length >= 3) {
                    ScriptableObject.putProperty(nativeError, "lineNumber", Integer.valueOf(ScriptRuntime.toInt32(objArr[2])));
                }
            }
        }
        return nativeError;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(ERROR_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == -1) {
            js_captureStackTrace(context, scriptable2, objArr);
            return Undefined.instance;
        }
        if (iMethodId == 1) {
            return make(context, scriptable, idFunctionObject, objArr);
        }
        if (iMethodId == 2) {
            return js_toString(scriptable2);
        }
        if (iMethodId == 3) {
            return js_toSource(context, scriptable, scriptable2);
        }
        throw new IllegalArgumentException(String.valueOf(iMethodId));
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        addIdFunctionProperty(idFunctionObject, ERROR_TAG, -1, "captureStackTrace", 2);
        ProtoProps protoProps = new ProtoProps();
        associateValue("_ErrorPrototypeProps", protoProps);
        idFunctionObject.defineProperty("stackTraceLimit", protoProps, ProtoProps.GET_STACK_LIMIT, ProtoProps.SET_STACK_LIMIT, 0);
        idFunctionObject.defineProperty("prepareStackTrace", protoProps, ProtoProps.GET_PREPARE_STACK, ProtoProps.SET_PREPARE_STACK, 0);
        super.fillConstructorProperties(idFunctionObject);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0025  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r5) {
        /*
            r4 = this;
            int r0 = r5.length()
            r1 = 8
            r2 = 0
            if (r0 != r1) goto L1d
            r0 = 3
            char r1 = r5.charAt(r0)
            r3 = 111(0x6f, float:1.56E-43)
            if (r1 != r3) goto L15
            java.lang.String r1 = "toSource"
            goto L27
        L15:
            r0 = 116(0x74, float:1.63E-43)
            if (r1 != r0) goto L25
            java.lang.String r1 = "toString"
            r0 = 2
            goto L27
        L1d:
            r1 = 11
            if (r0 != r1) goto L25
            java.lang.String r1 = "constructor"
            r0 = 1
            goto L27
        L25:
            r1 = 0
            r0 = r2
        L27:
            if (r1 == 0) goto L32
            if (r1 == r5) goto L32
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L32
            goto L33
        L32:
            r2 = r0
        L33:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeError.findPrototypeId(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Error";
    }

    public Object getStackDelegated(Scriptable scriptable) {
        int stackTraceLimit;
        Function prepareStackTrace;
        if (this.stackProvider == null) {
            return Scriptable.NOT_FOUND;
        }
        ProtoProps protoProps = (ProtoProps) ((NativeError) getPrototype()).getAssociatedValue("_ErrorPrototypeProps");
        if (protoProps != null) {
            stackTraceLimit = protoProps.getStackTraceLimit();
            prepareStackTrace = protoProps.getPrepareStackTrace();
        } else {
            stackTraceLimit = -1;
            prepareStackTrace = null;
        }
        ScriptStackElement[] scriptStack = this.stackProvider.getScriptStack(stackTraceLimit, (String) getAssociatedValue(STACK_HIDE_KEY));
        Object stackTrace = prepareStackTrace == null ? RhinoException.formatStackTrace(scriptStack, this.stackProvider.details()) : callPrepareStack(prepareStackTrace, scriptStack);
        setStackDelegated(scriptable, stackTrace);
        return stackTrace;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        int i3;
        String str;
        if (i2 != 1) {
            i3 = 0;
            if (i2 == 2) {
                str = "toString";
            } else {
                if (i2 != 3) {
                    throw new IllegalArgumentException(String.valueOf(i2));
                }
                str = "toSource";
            }
        } else {
            i3 = 1;
            str = "constructor";
        }
        initPrototypeMethod(ERROR_TAG, i2, str, i3);
    }

    public void setStackDelegated(Scriptable scriptable, Object obj) {
        scriptable.delete("stack");
        this.stackProvider = null;
        scriptable.put("stack", scriptable, obj);
    }

    public void setStackProvider(RhinoException rhinoException) {
        if (this.stackProvider == null) {
            this.stackProvider = rhinoException;
            defineProperty("stack", this, ERROR_DELEGATE_GET_STACK, ERROR_DELEGATE_SET_STACK, 2);
        }
    }

    public String toString() {
        Object objJs_toString = js_toString(this);
        return objJs_toString instanceof String ? (String) objJs_toString : super.toString();
    }

    private static final class ProtoProps implements Serializable {
        static final Method GET_PREPARE_STACK;
        static final Method GET_STACK_LIMIT;
        static final String KEY = "_ErrorPrototypeProps";
        static final Method SET_PREPARE_STACK;
        static final Method SET_STACK_LIMIT;
        private static final long serialVersionUID = 1907180507775337939L;
        private Function prepareStackTrace;
        private int stackTraceLimit;

        static {
            try {
                GET_STACK_LIMIT = ProtoProps.class.getMethod("getStackTraceLimit", Scriptable.class);
                SET_STACK_LIMIT = ProtoProps.class.getMethod("setStackTraceLimit", Scriptable.class, Object.class);
                GET_PREPARE_STACK = ProtoProps.class.getMethod("getPrepareStackTrace", Scriptable.class);
                SET_PREPARE_STACK = ProtoProps.class.getMethod("setPrepareStackTrace", Scriptable.class, Object.class);
            } catch (NoSuchMethodException e2) {
                throw new RuntimeException(e2);
            }
        }

        private ProtoProps() {
            this.stackTraceLimit = -1;
        }

        public Object getPrepareStackTrace(Scriptable scriptable) {
            Function prepareStackTrace = getPrepareStackTrace();
            return prepareStackTrace == null ? Undefined.instance : prepareStackTrace;
        }

        public Object getStackTraceLimit(Scriptable scriptable) {
            int i2 = this.stackTraceLimit;
            return i2 >= 0 ? Integer.valueOf(i2) : Double.valueOf(Double.POSITIVE_INFINITY);
        }

        public void setPrepareStackTrace(Scriptable scriptable, Object obj) {
            if (obj == null || Undefined.instance.equals(obj)) {
                this.prepareStackTrace = null;
            } else if (obj instanceof Function) {
                this.prepareStackTrace = (Function) obj;
            }
        }

        public void setStackTraceLimit(Scriptable scriptable, Object obj) {
            double number = Context.toNumber(obj);
            if (Double.isNaN(number) || Double.isInfinite(number)) {
                this.stackTraceLimit = -1;
            } else {
                this.stackTraceLimit = (int) number;
            }
        }

        public Function getPrepareStackTrace() {
            return this.prepareStackTrace;
        }

        public int getStackTraceLimit() {
            return this.stackTraceLimit;
        }
    }
}
