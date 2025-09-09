package org.mozilla.javascript;

import androidx.core.app.NotificationCompat;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.tekartik.sqflite.Constant;

/* loaded from: classes5.dex */
public class BaseFunction extends IdScriptableObject implements Function {
    private static final Object FUNCTION_TAG = "Function";
    private static final int Id_apply = 4;
    private static final int Id_arguments = 5;
    private static final int Id_arity = 2;
    private static final int Id_bind = 6;
    private static final int Id_call = 5;
    private static final int Id_constructor = 1;
    private static final int Id_length = 1;
    private static final int Id_name = 3;
    private static final int Id_prototype = 4;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int MAX_INSTANCE_ID = 5;
    private static final int MAX_PROTOTYPE_ID = 6;
    static final long serialVersionUID = 5311394446546053859L;
    private int argumentsAttributes;
    private Object argumentsObj;
    private Object prototypeProperty;
    private int prototypePropertyAttributes;

    public BaseFunction() {
        this.argumentsObj = Scriptable.NOT_FOUND;
        this.prototypePropertyAttributes = 6;
        this.argumentsAttributes = 6;
    }

    private Object getArguments() {
        Object objDefaultGet = defaultHas(Constant.PARAM_SQL_ARGUMENTS) ? defaultGet(Constant.PARAM_SQL_ARGUMENTS) : this.argumentsObj;
        if (objDefaultGet != Scriptable.NOT_FOUND) {
            return objDefaultGet;
        }
        Scriptable scriptableFindFunctionActivation = ScriptRuntime.findFunctionActivation(Context.getContext(), this);
        if (scriptableFindFunctionActivation == null) {
            return null;
        }
        return scriptableFindFunctionActivation.get(Constant.PARAM_SQL_ARGUMENTS, scriptableFindFunctionActivation);
    }

    static void init(Scriptable scriptable, boolean z2) {
        BaseFunction baseFunction = new BaseFunction();
        baseFunction.prototypePropertyAttributes = 7;
        baseFunction.exportAsJSClass(6, scriptable, z2);
    }

    static boolean isApply(IdFunctionObject idFunctionObject) {
        return idFunctionObject.hasTag(FUNCTION_TAG) && idFunctionObject.methodId() == 4;
    }

    static boolean isApplyOrCall(IdFunctionObject idFunctionObject) {
        if (!idFunctionObject.hasTag(FUNCTION_TAG)) {
            return false;
        }
        int iMethodId = idFunctionObject.methodId();
        return iMethodId == 4 || iMethodId == 5;
    }

    private static Object jsConstructor(Context context, Scriptable scriptable, Object[] objArr) {
        int i2;
        int length = objArr.length;
        StringBuilder sb = new StringBuilder();
        sb.append("function ");
        if (context.getLanguageVersion() != 120) {
            sb.append("anonymous");
        }
        sb.append('(');
        int i3 = 0;
        while (true) {
            i2 = length - 1;
            if (i3 >= i2) {
                break;
            }
            if (i3 > 0) {
                sb.append(',');
            }
            sb.append(ScriptRuntime.toString(objArr[i3]));
            i3++;
        }
        sb.append(") {");
        if (length != 0) {
            sb.append(ScriptRuntime.toString(objArr[i2]));
        }
        sb.append("\n}");
        String string = sb.toString();
        int[] iArr = new int[1];
        String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
        if (sourcePositionFromStack == null) {
            iArr[0] = 1;
            sourcePositionFromStack = "<eval'ed string>";
        }
        String strMakeUrlForGeneratedScript = ScriptRuntime.makeUrlForGeneratedScript(false, sourcePositionFromStack, iArr[0]);
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        ErrorReporter errorReporterForEval = DefaultErrorReporter.forEval(context.getErrorReporter());
        Evaluator evaluatorCreateInterpreter = Context.createInterpreter();
        if (evaluatorCreateInterpreter != null) {
            return context.compileFunction(topLevelScope, string, evaluatorCreateInterpreter, errorReporterForEval, strMakeUrlForGeneratedScript, 1, null);
        }
        throw new JavaScriptException("Interpreter not present", sourcePositionFromStack, iArr[0]);
    }

    private BaseFunction realFunction(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        Object defaultValue = scriptable.getDefaultValue(ScriptRuntime.FunctionClass);
        if (defaultValue instanceof Delegator) {
            defaultValue = ((Delegator) defaultValue).getDelegee();
        }
        if (defaultValue instanceof BaseFunction) {
            return (BaseFunction) defaultValue;
        }
        throw ScriptRuntime.typeError1("msg.incompat.call", idFunctionObject.getFunctionName());
    }

    private synchronized Object setupDefaultPrototype() {
        Object obj = this.prototypeProperty;
        if (obj != null) {
            return obj;
        }
        NativeObject nativeObject = new NativeObject();
        nativeObject.defineProperty("constructor", this, 2);
        this.prototypeProperty = nativeObject;
        Scriptable objectPrototype = ScriptableObject.getObjectPrototype(this);
        if (objectPrototype != nativeObject) {
            nativeObject.setPrototype(objectPrototype);
        }
        return nativeObject;
    }

    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        return Undefined.instance;
    }

    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        Scriptable parentScope;
        Scriptable classPrototype;
        Scriptable scriptableCreateObject = createObject(context, scriptable);
        if (scriptableCreateObject != null) {
            Object objCall = call(context, scriptable, scriptableCreateObject, objArr);
            return objCall instanceof Scriptable ? (Scriptable) objCall : scriptableCreateObject;
        }
        Object objCall2 = call(context, scriptable, null, objArr);
        if (!(objCall2 instanceof Scriptable)) {
            throw new IllegalStateException("Bad implementaion of call as constructor, name=" + getFunctionName() + " in " + getClass().getName());
        }
        Scriptable scriptable2 = (Scriptable) objCall2;
        if (scriptable2.getPrototype() == null && scriptable2 != (classPrototype = getClassPrototype())) {
            scriptable2.setPrototype(classPrototype);
        }
        if (scriptable2.getParentScope() != null || scriptable2 == (parentScope = getParentScope())) {
            return scriptable2;
        }
        scriptable2.setParentScope(parentScope);
        return scriptable2;
    }

    public Scriptable createObject(Context context, Scriptable scriptable) {
        NativeObject nativeObject = new NativeObject();
        nativeObject.setPrototype(getClassPrototype());
        nativeObject.setParentScope(getParentScope());
        return nativeObject;
    }

    String decompile(int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        boolean z2 = (i3 & 1) != 0;
        if (!z2) {
            sb.append("function ");
            sb.append(getFunctionName());
            sb.append("() {\n\t");
        }
        sb.append("[native code, arity=");
        sb.append(getArity());
        sb.append("]\n");
        if (!z2) {
            sb.append("}\n");
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        int int32;
        Object[] objArr2;
        Scriptable scriptable3;
        if (!idFunctionObject.hasTag(FUNCTION_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        int i2 = 0;
        switch (iMethodId) {
            case 1:
                return jsConstructor(context, scriptable, objArr);
            case 2:
                return realFunction(scriptable2, idFunctionObject).decompile(ScriptRuntime.toInt32(objArr, 0), 0);
            case 3:
                BaseFunction baseFunctionRealFunction = realFunction(scriptable2, idFunctionObject);
                int i3 = 2;
                if (objArr.length != 0 && (int32 = ScriptRuntime.toInt32(objArr[0])) >= 0) {
                    i3 = 0;
                    i2 = int32;
                }
                return baseFunctionRealFunction.decompile(i2, i3);
            case 4:
            case 5:
                return ScriptRuntime.applyOrCall(iMethodId == 4, context, scriptable, scriptable2, objArr);
            case 6:
                if (!(scriptable2 instanceof Callable)) {
                    throw ScriptRuntime.notFunctionError(scriptable2);
                }
                Callable callable = (Callable) scriptable2;
                int length = objArr.length;
                if (length > 0) {
                    Scriptable objectOrNull = ScriptRuntime.toObjectOrNull(context, objArr[0], scriptable);
                    int i4 = length - 1;
                    Object[] objArr3 = new Object[i4];
                    System.arraycopy(objArr, 1, objArr3, 0, i4);
                    scriptable3 = objectOrNull;
                    objArr2 = objArr3;
                } else {
                    objArr2 = ScriptRuntime.emptyArgs;
                    scriptable3 = null;
                }
                return new BoundFunction(context, scriptable, callable, scriptable3, objArr2);
            default:
                throw new IllegalArgumentException(String.valueOf(iMethodId));
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        idFunctionObject.setPrototype(this);
        super.fillConstructorProperties(idFunctionObject);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002a  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findInstanceIdInfo(java.lang.String r9) {
        /*
            r8 = this;
            int r0 = r9.length()
            r1 = 3
            r2 = 2
            r3 = 1
            r4 = 5
            r5 = 4
            r6 = 0
            if (r0 == r5) goto L35
            if (r0 == r4) goto L31
            r7 = 6
            if (r0 == r7) goto L2d
            r7 = 9
            if (r0 == r7) goto L16
            goto L2a
        L16:
            char r0 = r9.charAt(r6)
            r7 = 97
            if (r0 != r7) goto L22
            java.lang.String r0 = "arguments"
            r7 = r4
            goto L38
        L22:
            r7 = 112(0x70, float:1.57E-43)
            if (r0 != r7) goto L2a
            java.lang.String r0 = "prototype"
            r7 = r5
            goto L38
        L2a:
            r0 = 0
            r7 = r6
            goto L38
        L2d:
            java.lang.String r0 = "length"
            r7 = r3
            goto L38
        L31:
            java.lang.String r0 = "arity"
            r7 = r2
            goto L38
        L35:
            java.lang.String r0 = "name"
            r7 = r1
        L38:
            if (r0 == 0) goto L43
            if (r0 == r9) goto L43
            boolean r0 = r0.equals(r9)
            if (r0 != 0) goto L43
            r7 = r6
        L43:
            if (r7 != 0) goto L4a
            int r9 = super.findInstanceIdInfo(r9)
            return r9
        L4a:
            if (r7 == r3) goto L67
            if (r7 == r2) goto L67
            if (r7 == r1) goto L67
            if (r7 == r5) goto L5d
            if (r7 != r4) goto L57
            int r9 = r8.argumentsAttributes
            goto L68
        L57:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            r9.<init>()
            throw r9
        L5d:
            boolean r9 = r8.hasPrototypeProperty()
            if (r9 != 0) goto L64
            return r6
        L64:
            int r9 = r8.prototypePropertyAttributes
            goto L68
        L67:
            r9 = 7
        L68:
            int r9 = org.mozilla.javascript.IdScriptableObject.instanceIdInfo(r9, r7)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.BaseFunction.findInstanceIdInfo(java.lang.String):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0043  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r5) {
        /*
            r4 = this;
            int r0 = r5.length()
            r1 = 5
            r2 = 4
            r3 = 0
            if (r0 == r2) goto L30
            if (r0 == r1) goto L2c
            r1 = 8
            if (r0 == r1) goto L18
            r1 = 11
            if (r0 == r1) goto L14
            goto L43
        L14:
            java.lang.String r0 = "constructor"
            r1 = 1
            goto L45
        L18:
            r1 = 3
            char r0 = r5.charAt(r1)
            r2 = 111(0x6f, float:1.56E-43)
            if (r0 != r2) goto L24
            java.lang.String r0 = "toSource"
            goto L45
        L24:
            r1 = 116(0x74, float:1.63E-43)
            if (r0 != r1) goto L43
            java.lang.String r0 = "toString"
            r1 = 2
            goto L45
        L2c:
            java.lang.String r0 = "apply"
            r1 = r2
            goto L45
        L30:
            char r0 = r5.charAt(r3)
            r2 = 98
            if (r0 != r2) goto L3c
            java.lang.String r0 = "bind"
            r1 = 6
            goto L45
        L3c:
            r2 = 99
            if (r0 != r2) goto L43
            java.lang.String r0 = "call"
            goto L45
        L43:
            r0 = 0
            r1 = r3
        L45:
            if (r0 == 0) goto L50
            if (r0 == r5) goto L50
            boolean r5 = r0.equals(r5)
            if (r5 != 0) goto L50
            goto L51
        L50:
            r3 = r1
        L51:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.BaseFunction.findPrototypeId(java.lang.String):int");
    }

    public int getArity() {
        return 0;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Function";
    }

    protected Scriptable getClassPrototype() {
        Object prototypeProperty = getPrototypeProperty();
        return prototypeProperty instanceof Scriptable ? (Scriptable) prototypeProperty : ScriptableObject.getObjectPrototype(this);
    }

    public String getFunctionName() {
        return "";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? super.getInstanceIdName(i2) : Constant.PARAM_SQL_ARGUMENTS : "prototype" : "name" : "arity" : SessionDescription.ATTR_LENGTH;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? super.getInstanceIdValue(i2) : getArguments() : getPrototypeProperty() : getFunctionName() : ScriptRuntime.wrapInt(getArity()) : ScriptRuntime.wrapInt(getLength());
    }

    public int getLength() {
        return 0;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 5;
    }

    protected Object getPrototypeProperty() {
        Object obj = this.prototypeProperty;
        if (obj == null) {
            return this instanceof NativeFunction ? setupDefaultPrototype() : Undefined.instance;
        }
        if (obj == UniqueTag.NULL_VALUE) {
            return null;
        }
        return obj;
    }

    @Override // org.mozilla.javascript.ScriptableObject
    public String getTypeOf() {
        return avoidObjectDetection() ? "undefined" : "function";
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean hasInstance(Scriptable scriptable) {
        Object property = ScriptableObject.getProperty(this, "prototype");
        if (property instanceof Scriptable) {
            return ScriptRuntime.jsDelegatesTo(scriptable, (Scriptable) property);
        }
        throw ScriptRuntime.typeError1("msg.instanceof.bad.prototype", getFunctionName());
    }

    protected boolean hasPrototypeProperty() {
        return this.prototypeProperty != null || (this instanceof NativeFunction);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        int i3 = 1;
        switch (i2) {
            case 1:
                str = "constructor";
                break;
            case 2:
                i3 = 0;
                str = "toString";
                break;
            case 3:
                str = "toSource";
                break;
            case 4:
                i3 = 2;
                str = "apply";
                break;
            case 5:
                str = NotificationCompat.CATEGORY_CALL;
                break;
            case 6:
                str = "bind";
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
        initPrototypeMethod(FUNCTION_TAG, i2, str, i3);
    }

    public void setImmunePrototypeProperty(Object obj) {
        if ((this.prototypePropertyAttributes & 1) != 0) {
            throw new IllegalStateException();
        }
        if (obj == null) {
            obj = UniqueTag.NULL_VALUE;
        }
        this.prototypeProperty = obj;
        this.prototypePropertyAttributes = 7;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdAttributes(int i2, int i3) {
        if (i2 == 4) {
            this.prototypePropertyAttributes = i3;
        } else if (i2 != 5) {
            super.setInstanceIdAttributes(i2, i3);
        } else {
            this.argumentsAttributes = i3;
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdValue(int i2, Object obj) throws RuntimeException {
        if (i2 == 1 || i2 == 2 || i2 == 3) {
            return;
        }
        if (i2 == 4) {
            if ((this.prototypePropertyAttributes & 1) == 0) {
                if (obj == null) {
                    obj = UniqueTag.NULL_VALUE;
                }
                this.prototypeProperty = obj;
                return;
            }
            return;
        }
        if (i2 != 5) {
            super.setInstanceIdValue(i2, obj);
            return;
        }
        if (obj == Scriptable.NOT_FOUND) {
            Kit.codeBug();
        }
        if (defaultHas(Constant.PARAM_SQL_ARGUMENTS)) {
            defaultPut(Constant.PARAM_SQL_ARGUMENTS, obj);
        } else if ((this.argumentsAttributes & 1) == 0) {
            this.argumentsObj = obj;
        }
    }

    public BaseFunction(Scriptable scriptable, Scriptable scriptable2) {
        super(scriptable, scriptable2);
        this.argumentsObj = Scriptable.NOT_FOUND;
        this.prototypePropertyAttributes = 6;
        this.argumentsAttributes = 6;
    }
}
