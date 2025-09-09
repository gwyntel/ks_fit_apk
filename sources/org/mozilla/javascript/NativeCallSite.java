package org.mozilla.javascript;

/* loaded from: classes5.dex */
public class NativeCallSite extends IdScriptableObject {
    private static final String CALLSITE_TAG = "CallSite";
    private static final int Id_constructor = 1;
    private static final int Id_getColumnNumber = 9;
    private static final int Id_getEvalOrigin = 10;
    private static final int Id_getFileName = 7;
    private static final int Id_getFunction = 4;
    private static final int Id_getFunctionName = 5;
    private static final int Id_getLineNumber = 8;
    private static final int Id_getMethodName = 6;
    private static final int Id_getThis = 2;
    private static final int Id_getTypeName = 3;
    private static final int Id_isConstructor = 14;
    private static final int Id_isEval = 12;
    private static final int Id_isNative = 13;
    private static final int Id_isToplevel = 11;
    private static final int Id_toString = 15;
    private static final int MAX_PROTOTYPE_ID = 15;
    private ScriptStackElement element;

    private NativeCallSite() {
    }

    private Object getFalse() {
        return Boolean.FALSE;
    }

    private Object getFileName(Scriptable scriptable) {
        while (scriptable != null && !(scriptable instanceof NativeCallSite)) {
            scriptable = scriptable.getPrototype();
        }
        if (scriptable == null) {
            return Scriptable.NOT_FOUND;
        }
        ScriptStackElement scriptStackElement = ((NativeCallSite) scriptable).element;
        if (scriptStackElement == null) {
            return null;
        }
        return scriptStackElement.fileName;
    }

    private Object getFunctionName(Scriptable scriptable) {
        while (scriptable != null && !(scriptable instanceof NativeCallSite)) {
            scriptable = scriptable.getPrototype();
        }
        if (scriptable == null) {
            return Scriptable.NOT_FOUND;
        }
        ScriptStackElement scriptStackElement = ((NativeCallSite) scriptable).element;
        if (scriptStackElement == null) {
            return null;
        }
        return scriptStackElement.functionName;
    }

    private Object getLineNumber(Scriptable scriptable) {
        int i2;
        while (scriptable != null && !(scriptable instanceof NativeCallSite)) {
            scriptable = scriptable.getPrototype();
        }
        if (scriptable == null) {
            return Scriptable.NOT_FOUND;
        }
        ScriptStackElement scriptStackElement = ((NativeCallSite) scriptable).element;
        return (scriptStackElement == null || (i2 = scriptStackElement.lineNumber) < 0) ? Undefined.instance : Integer.valueOf(i2);
    }

    private Object getNull() {
        return null;
    }

    private Object getUndefined() {
        return Undefined.instance;
    }

    static void init(Scriptable scriptable, boolean z2) {
        new NativeCallSite().exportAsJSClass(15, scriptable, z2);
    }

    private Object js_toString(Scriptable scriptable) {
        while (scriptable != null && !(scriptable instanceof NativeCallSite)) {
            scriptable = scriptable.getPrototype();
        }
        if (scriptable == null) {
            return Scriptable.NOT_FOUND;
        }
        StringBuilder sb = new StringBuilder();
        ((NativeCallSite) scriptable).element.renderJavaStyle(sb);
        return sb.toString();
    }

    static NativeCallSite make(Scriptable scriptable, Scriptable scriptable2) {
        NativeCallSite nativeCallSite = new NativeCallSite();
        Scriptable scriptable3 = (Scriptable) scriptable2.get("prototype", scriptable2);
        nativeCallSite.setParentScope(scriptable);
        nativeCallSite.setPrototype(scriptable3);
        return nativeCallSite;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(CALLSITE_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        switch (iMethodId) {
            case 1:
                return make(scriptable, idFunctionObject);
            case 2:
            case 3:
            case 4:
            case 9:
                return getUndefined();
            case 5:
                return getFunctionName(scriptable2);
            case 6:
                return getNull();
            case 7:
                return getFileName(scriptable2);
            case 8:
                return getLineNumber(scriptable2);
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return getFalse();
            case 15:
                return js_toString(scriptable2);
            default:
                throw new IllegalArgumentException(String.valueOf(iMethodId));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0087  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r7) {
        /*
            r6 = this;
            int r0 = r7.length()
            r1 = 116(0x74, float:1.63E-43)
            r2 = 105(0x69, float:1.47E-43)
            r3 = 3
            r4 = 0
            switch(r0) {
                case 6: goto L8e;
                case 7: goto L8a;
                case 8: goto L75;
                case 9: goto Ld;
                case 10: goto L70;
                case 11: goto L4e;
                case 12: goto Ld;
                case 13: goto L26;
                case 14: goto Ld;
                case 15: goto Lf;
                default: goto Ld;
            }
        Ld:
            goto L87
        Lf:
            char r0 = r7.charAt(r3)
            r1 = 67
            if (r0 != r1) goto L1d
            java.lang.String r0 = "getColumnNumber"
            r3 = 9
            goto L92
        L1d:
            r1 = 70
            if (r0 != r1) goto L87
            java.lang.String r0 = "getFunctionName"
            r3 = 5
            goto L92
        L26:
            char r0 = r7.charAt(r3)
            r1 = 69
            if (r0 == r1) goto L49
            r1 = 111(0x6f, float:1.56E-43)
            if (r0 == r1) goto L44
            r1 = 76
            if (r0 == r1) goto L3f
            r1 = 77
            if (r0 == r1) goto L3b
            goto L87
        L3b:
            java.lang.String r0 = "getMethodName"
            r3 = 6
            goto L92
        L3f:
            java.lang.String r0 = "getLineNumber"
            r3 = 8
            goto L92
        L44:
            java.lang.String r0 = "isConstructor"
            r3 = 14
            goto L92
        L49:
            java.lang.String r0 = "getEvalOrigin"
            r3 = 10
            goto L92
        L4e:
            r0 = 4
            char r5 = r7.charAt(r0)
            if (r5 == r2) goto L6c
            r2 = 121(0x79, float:1.7E-43)
            if (r5 == r2) goto L69
            if (r5 == r1) goto L65
            r1 = 117(0x75, float:1.64E-43)
            if (r5 == r1) goto L60
            goto L87
        L60:
            java.lang.String r1 = "getFunction"
            r3 = r0
            r0 = r1
            goto L92
        L65:
            java.lang.String r0 = "constructor"
            r3 = 1
            goto L92
        L69:
            java.lang.String r0 = "getTypeName"
            goto L92
        L6c:
            java.lang.String r0 = "getFileName"
            r3 = 7
            goto L92
        L70:
            java.lang.String r0 = "isToplevel"
            r3 = 11
            goto L92
        L75:
            char r0 = r7.charAt(r4)
            if (r0 != r2) goto L80
            java.lang.String r0 = "isNative"
            r3 = 13
            goto L92
        L80:
            if (r0 != r1) goto L87
            java.lang.String r0 = "toString"
            r3 = 15
            goto L92
        L87:
            r0 = 0
            r3 = r4
            goto L92
        L8a:
            java.lang.String r0 = "getThis"
            r3 = 2
            goto L92
        L8e:
            java.lang.String r0 = "isEval"
            r3 = 12
        L92:
            if (r0 == 0) goto L9d
            if (r0 == r7) goto L9d
            boolean r7 = r0.equals(r7)
            if (r7 != 0) goto L9d
            goto L9e
        L9d:
            r4 = r3
        L9e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeCallSite.findPrototypeId(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CALLSITE_TAG;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        switch (i2) {
            case 1:
                str = "constructor";
                break;
            case 2:
                str = "getThis";
                break;
            case 3:
                str = "getTypeName";
                break;
            case 4:
                str = "getFunction";
                break;
            case 5:
                str = "getFunctionName";
                break;
            case 6:
                str = "getMethodName";
                break;
            case 7:
                str = "getFileName";
                break;
            case 8:
                str = "getLineNumber";
                break;
            case 9:
                str = "getColumnNumber";
                break;
            case 10:
                str = "getEvalOrigin";
                break;
            case 11:
                str = "isToplevel";
                break;
            case 12:
                str = "isEval";
                break;
            case 13:
                str = "isNative";
                break;
            case 14:
                str = "isConstructor";
                break;
            case 15:
                str = "toString";
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
        initPrototypeMethod(CALLSITE_TAG, i2, str, 0);
    }

    void setElement(ScriptStackElement scriptStackElement) {
        this.element = scriptStackElement;
    }

    public String toString() {
        ScriptStackElement scriptStackElement = this.element;
        return scriptStackElement == null ? "" : scriptStackElement.toString();
    }
}
