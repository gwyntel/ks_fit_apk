package org.mozilla.javascript;

import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.tekartik.sqflite.Constant;

/* loaded from: classes5.dex */
final class Arguments extends IdScriptableObject {
    private static final String FTAG = "Arguments";
    private static final int Id_callee = 1;
    private static final int Id_caller = 3;
    private static final int Id_length = 2;
    private static final int MAX_INSTANCE_ID = 3;
    private static BaseFunction iteratorMethod = new BaseFunction() { // from class: org.mozilla.javascript.Arguments.1
        @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function, org.mozilla.javascript.Callable
        public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
            return new NativeArrayIterator(scriptable, scriptable2);
        }
    };
    static final long serialVersionUID = 4275508002492040609L;
    private NativeCall activation;
    private Object[] args;
    private Object calleeObj;
    private Object callerObj;
    private Object lengthObj;
    private int callerAttr = 2;
    private int calleeAttr = 2;
    private int lengthAttr = 2;

    private static class ThrowTypeError extends BaseFunction {
        private String propertyName;

        ThrowTypeError(String str) {
            this.propertyName = str;
        }

        @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function, org.mozilla.javascript.Callable
        public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
            throw ScriptRuntime.typeError1("msg.arguments.not.access.strict", this.propertyName);
        }
    }

    public Arguments(NativeCall nativeCall) {
        this.activation = nativeCall;
        Scriptable parentScope = nativeCall.getParentScope();
        setParentScope(parentScope);
        setPrototype(ScriptableObject.getObjectPrototype(parentScope));
        Object[] objArr = nativeCall.originalArgs;
        this.args = objArr;
        this.lengthObj = Integer.valueOf(objArr.length);
        NativeFunction nativeFunction = nativeCall.function;
        this.calleeObj = nativeFunction;
        int languageVersion = nativeFunction.getLanguageVersion();
        if (languageVersion > 130 || languageVersion == 0) {
            this.callerObj = Scriptable.NOT_FOUND;
        } else {
            this.callerObj = null;
        }
        defineProperty(SymbolKey.ITERATOR, iteratorMethod, 2);
    }

    private Object arg(int i2) {
        if (i2 >= 0) {
            Object[] objArr = this.args;
            if (objArr.length > i2) {
                return objArr[i2];
            }
        }
        return Scriptable.NOT_FOUND;
    }

    private Object getFromActivation(int i2) {
        String paramOrVarName = this.activation.function.getParamOrVarName(i2);
        Scriptable scriptable = this.activation;
        return scriptable.get(paramOrVarName, scriptable);
    }

    private void putIntoActivation(int i2, Object obj) {
        String paramOrVarName = this.activation.function.getParamOrVarName(i2);
        Scriptable scriptable = this.activation;
        scriptable.put(paramOrVarName, scriptable, obj);
    }

    private void removeArg(int i2) {
        synchronized (this) {
            try {
                Object[] objArr = this.args;
                Object obj = objArr[i2];
                Object obj2 = Scriptable.NOT_FOUND;
                if (obj != obj2) {
                    if (objArr == this.activation.originalArgs) {
                        this.args = (Object[]) objArr.clone();
                    }
                    this.args[i2] = obj2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void replaceArg(int i2, Object obj) {
        if (sharedWithActivation(i2)) {
            putIntoActivation(i2, obj);
        }
        synchronized (this) {
            try {
                Object[] objArr = this.args;
                if (objArr == this.activation.originalArgs) {
                    this.args = (Object[]) objArr.clone();
                }
                this.args[i2] = obj;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private boolean sharedWithActivation(int i2) {
        NativeFunction nativeFunction;
        int paramCount;
        if (Context.getContext().isStrictMode() || i2 >= (paramCount = (nativeFunction = this.activation.function).getParamCount())) {
            return false;
        }
        if (i2 < paramCount - 1) {
            String paramOrVarName = nativeFunction.getParamOrVarName(i2);
            for (int i3 = i2 + 1; i3 < paramCount; i3++) {
                if (paramOrVarName.equals(nativeFunction.getParamOrVarName(i3))) {
                    return false;
                }
            }
        }
        return true;
    }

    void defineAttributesForStrictMode() {
        if (Context.getContext().isStrictMode()) {
            setGetterOrSetter("caller", 0, new ThrowTypeError("caller"), true);
            setGetterOrSetter("caller", 0, new ThrowTypeError("caller"), false);
            setGetterOrSetter("callee", 0, new ThrowTypeError("callee"), true);
            setGetterOrSetter("callee", 0, new ThrowTypeError("callee"), false);
            setAttributes("caller", 6);
            setAttributes("callee", 6);
            this.callerObj = null;
            this.calleeObj = null;
        }
    }

    @Override // org.mozilla.javascript.ScriptableObject
    protected void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject, boolean z2) {
        super.defineOwnProperty(context, obj, scriptableObject, z2);
        double number = ScriptRuntime.toNumber(obj);
        int i2 = (int) number;
        if (number != i2) {
            return;
        }
        Object objArg = arg(i2);
        Object obj2 = Scriptable.NOT_FOUND;
        if (objArg == obj2) {
            return;
        }
        if (isAccessorDescriptor(scriptableObject)) {
            removeArg(i2);
            return;
        }
        Object property = ScriptableObject.getProperty(scriptableObject, "value");
        if (property == obj2) {
            return;
        }
        replaceArg(i2, property);
        if (ScriptableObject.isFalse(ScriptableObject.getProperty(scriptableObject, "writable"))) {
            removeArg(i2);
        }
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void delete(int i2) {
        if (i2 >= 0 && i2 < this.args.length) {
            removeArg(i2);
        }
        super.delete(i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findInstanceIdInfo(java.lang.String r7) {
        /*
            r6 = this;
            int r0 = r7.length()
            r1 = 6
            r2 = 2
            r3 = 3
            r4 = 1
            r5 = 0
            if (r0 != r1) goto L28
            r0 = 5
            char r0 = r7.charAt(r0)
            r1 = 101(0x65, float:1.42E-43)
            if (r0 != r1) goto L18
            java.lang.String r0 = "callee"
            r1 = r4
            goto L2a
        L18:
            r1 = 104(0x68, float:1.46E-43)
            if (r0 != r1) goto L20
            java.lang.String r0 = "length"
            r1 = r2
            goto L2a
        L20:
            r1 = 114(0x72, float:1.6E-43)
            if (r0 != r1) goto L28
            java.lang.String r0 = "caller"
            r1 = r3
            goto L2a
        L28:
            r0 = 0
            r1 = r5
        L2a:
            if (r0 == 0) goto L35
            if (r0 == r7) goto L35
            boolean r0 = r0.equals(r7)
            if (r0 != 0) goto L35
            goto L36
        L35:
            r5 = r1
        L36:
            org.mozilla.javascript.Context r0 = org.mozilla.javascript.Context.getContext()
            boolean r0 = r0.isStrictMode()
            if (r0 == 0) goto L49
            if (r5 == r4) goto L44
            if (r5 != r3) goto L49
        L44:
            int r7 = super.findInstanceIdInfo(r7)
            return r7
        L49:
            if (r5 != 0) goto L50
            int r7 = super.findInstanceIdInfo(r7)
            return r7
        L50:
            if (r5 == r4) goto L62
            if (r5 == r2) goto L5f
            if (r5 != r3) goto L59
            int r7 = r6.callerAttr
            goto L64
        L59:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            r7.<init>()
            throw r7
        L5f:
            int r7 = r6.lengthAttr
            goto L64
        L62:
            int r7 = r6.calleeAttr
        L64:
            int r7 = org.mozilla.javascript.IdScriptableObject.instanceIdInfo(r7, r5)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Arguments.findInstanceIdInfo(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object get(int i2, Scriptable scriptable) {
        Object objArg = arg(i2);
        return objArg == Scriptable.NOT_FOUND ? super.get(i2, scriptable) : sharedWithActivation(i2) ? getFromActivation(i2) : objArg;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return FTAG;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject
    Object[] getIds(boolean z2, boolean z3) throws RuntimeException {
        int iIntValue;
        Object[] ids = super.getIds(z2, z3);
        Object[] objArr = this.args;
        if (objArr.length == 0) {
            return ids;
        }
        int length = objArr.length;
        boolean[] zArr = new boolean[length];
        int length2 = objArr.length;
        for (int i2 = 0; i2 != ids.length; i2++) {
            Object obj = ids[i2];
            if ((obj instanceof Integer) && (iIntValue = ((Integer) obj).intValue()) >= 0 && iIntValue < this.args.length && !zArr[iIntValue]) {
                zArr[iIntValue] = true;
                length2--;
            }
        }
        if (!z2) {
            for (int i3 = 0; i3 < length; i3++) {
                if (!zArr[i3] && super.has(i3, this)) {
                    zArr[i3] = true;
                    length2--;
                }
            }
        }
        if (length2 == 0) {
            return ids;
        }
        Object[] objArr2 = new Object[ids.length + length2];
        System.arraycopy(ids, 0, objArr2, length2, ids.length);
        int i4 = 0;
        for (int i5 = 0; i5 != this.args.length; i5++) {
            if (!zArr[i5]) {
                objArr2[i4] = Integer.valueOf(i5);
                i4++;
            }
        }
        if (i4 != length2) {
            Kit.codeBug();
        }
        return objArr2;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        if (i2 == 1) {
            return "callee";
        }
        if (i2 == 2) {
            return SessionDescription.ATTR_LENGTH;
        }
        if (i2 != 3) {
            return null;
        }
        return "caller";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        Scriptable scriptable;
        if (i2 == 1) {
            return this.calleeObj;
        }
        if (i2 == 2) {
            return this.lengthObj;
        }
        if (i2 != 3) {
            return super.getInstanceIdValue(i2);
        }
        Object obj = this.callerObj;
        if (obj == UniqueTag.NULL_VALUE) {
            return null;
        }
        return (obj != null || (scriptable = this.activation.parentActivationCall) == null) ? obj : scriptable.get(Constant.PARAM_SQL_ARGUMENTS, scriptable);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 3;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject
    protected ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        if (obj instanceof Scriptable) {
            return super.getOwnPropertyDescriptor(context, obj);
        }
        double number = ScriptRuntime.toNumber(obj);
        int i2 = (int) number;
        if (number != i2) {
            return super.getOwnPropertyDescriptor(context, obj);
        }
        Object objArg = arg(i2);
        if (objArg == Scriptable.NOT_FOUND) {
            return super.getOwnPropertyDescriptor(context, obj);
        }
        if (sharedWithActivation(i2)) {
            objArg = getFromActivation(i2);
        }
        if (super.has(i2, this)) {
            ScriptableObject ownPropertyDescriptor = super.getOwnPropertyDescriptor(context, obj);
            ownPropertyDescriptor.put("value", ownPropertyDescriptor, objArg);
            return ownPropertyDescriptor;
        }
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        return ScriptableObject.buildDataDescriptor(parentScope, objArg, 0);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean has(int i2, Scriptable scriptable) {
        if (arg(i2) != Scriptable.NOT_FOUND) {
            return true;
        }
        return super.has(i2, scriptable);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(int i2, Scriptable scriptable, Object obj) {
        if (arg(i2) == Scriptable.NOT_FOUND) {
            super.put(i2, scriptable, obj);
        } else {
            replaceArg(i2, obj);
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdAttributes(int i2, int i3) {
        if (i2 == 1) {
            this.calleeAttr = i3;
            return;
        }
        if (i2 == 2) {
            this.lengthAttr = i3;
        } else if (i2 != 3) {
            super.setInstanceIdAttributes(i2, i3);
        } else {
            this.callerAttr = i3;
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdValue(int i2, Object obj) {
        if (i2 == 1) {
            this.calleeObj = obj;
            return;
        }
        if (i2 == 2) {
            this.lengthObj = obj;
        } else {
            if (i2 != 3) {
                super.setInstanceIdValue(i2, obj);
                return;
            }
            if (obj == null) {
                obj = UniqueTag.NULL_VALUE;
            }
            this.callerObj = obj;
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(String str, Scriptable scriptable, Object obj) {
        super.put(str, scriptable, obj);
    }
}
