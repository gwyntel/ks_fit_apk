package org.mozilla.javascript;

/* loaded from: classes5.dex */
final class NativeNumber extends IdScriptableObject {
    private static final int ConstructorId_isFinite = -1;
    private static final int ConstructorId_isInteger = -3;
    private static final int ConstructorId_isNaN = -2;
    private static final int ConstructorId_isSafeInteger = -4;
    private static final int ConstructorId_parseFloat = -5;
    private static final int ConstructorId_parseInt = -6;
    private static final int Id_constructor = 1;
    private static final int Id_toExponential = 7;
    private static final int Id_toFixed = 6;
    private static final int Id_toLocaleString = 3;
    private static final int Id_toPrecision = 8;
    private static final int Id_toSource = 4;
    private static final int Id_toString = 2;
    private static final int Id_valueOf = 5;
    private static final int MAX_PRECISION = 100;
    private static final int MAX_PROTOTYPE_ID = 8;
    private static final double MAX_SAFE_INTEGER;
    private static final double MIN_SAFE_INTEGER;
    private static final Object NUMBER_TAG = "Number";
    static final long serialVersionUID = 3504516769741512101L;
    private double doubleValue;

    static {
        double dPow = Math.pow(2.0d, 53.0d) - 1.0d;
        MAX_SAFE_INTEGER = dPow;
        MIN_SAFE_INTEGER = -dPow;
    }

    NativeNumber(double d2) {
        this.doubleValue = d2;
    }

    private Double doubleVal(Number number) {
        return number instanceof Double ? (Double) number : Double.valueOf(number.doubleValue());
    }

    private Object execConstructorCall(int i2, Object[] objArr) {
        switch (i2) {
            case -6:
                return NativeGlobal.js_parseInt(objArr);
            case -5:
                return NativeGlobal.js_parseFloat(objArr);
            case -4:
                if (objArr.length != 0) {
                    Object obj = Undefined.instance;
                    Object obj2 = objArr[0];
                    if (obj != obj2) {
                        return obj2 instanceof Number ? Boolean.valueOf(isSafeInteger((Number) obj2)) : Boolean.FALSE;
                    }
                }
                return Boolean.FALSE;
            case -3:
                if (objArr.length != 0) {
                    Object obj3 = Undefined.instance;
                    Object obj4 = objArr[0];
                    if (obj3 != obj4) {
                        return obj4 instanceof Number ? Boolean.valueOf(isInteger((Number) obj4)) : Boolean.FALSE;
                    }
                }
                return Boolean.FALSE;
            case -2:
                if (objArr.length != 0) {
                    Object obj5 = Undefined.instance;
                    Object obj6 = objArr[0];
                    if (obj5 != obj6) {
                        return obj6 instanceof Number ? isNaN((Number) obj6) : Boolean.FALSE;
                    }
                }
                return Boolean.FALSE;
            case -1:
                if (objArr.length != 0) {
                    Object obj7 = Undefined.instance;
                    Object obj8 = objArr[0];
                    if (obj7 != obj8) {
                        return obj8 instanceof Number ? isFinite(obj8) : Boolean.FALSE;
                    }
                }
                return Boolean.FALSE;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
    }

    static void init(Scriptable scriptable, boolean z2) {
        new NativeNumber(0.0d).exportAsJSClass(8, scriptable, z2);
    }

    private boolean isDoubleInteger(Double d2) {
        return (d2.isInfinite() || d2.isNaN() || Math.floor(d2.doubleValue()) != d2.doubleValue()) ? false : true;
    }

    private boolean isDoubleNan(Double d2) {
        return d2.isNaN();
    }

    private boolean isDoubleSafeInteger(Double d2) {
        return isDoubleInteger(d2) && d2.doubleValue() <= MAX_SAFE_INTEGER && d2.doubleValue() >= MIN_SAFE_INTEGER;
    }

    static Object isFinite(Object obj) {
        Double dValueOf = Double.valueOf(ScriptRuntime.toNumber(obj));
        return ScriptRuntime.wrapBoolean((dValueOf.isInfinite() || dValueOf.isNaN()) ? false : true);
    }

    private boolean isInteger(Number number) {
        return ScriptRuntime.toBoolean(Boolean.valueOf(isDoubleInteger(doubleVal(number))));
    }

    private Object isNaN(Number number) {
        return Boolean.valueOf(ScriptRuntime.toBoolean(Boolean.valueOf(isDoubleNan(doubleVal(number)))));
    }

    private boolean isSafeInteger(Number number) {
        return ScriptRuntime.toBoolean(Boolean.valueOf(isDoubleSafeInteger(doubleVal(number))));
    }

    private static String num_to(double d2, Object[] objArr, int i2, int i3, int i4, int i5) {
        int int32 = 0;
        if (objArr.length != 0) {
            double integer = ScriptRuntime.toInteger(objArr[0]);
            if (integer < i4 || integer > 100.0d) {
                throw ScriptRuntime.constructError("RangeError", ScriptRuntime.getMessage1("msg.bad.precision", ScriptRuntime.toString(objArr[0])));
            }
            int32 = ScriptRuntime.toInt32(integer);
            i2 = i3;
        }
        StringBuilder sb = new StringBuilder();
        DToA.JS_dtostr(sb, i2, int32 + i5, d2);
        return sb.toString();
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        if (!idFunctionObject.hasTag(NUMBER_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == 1) {
            double number = objArr.length >= 1 ? ScriptRuntime.toNumber(objArr[0]) : 0.0d;
            return scriptable2 == null ? new NativeNumber(number) : ScriptRuntime.wrapNumber(number);
        }
        if (iMethodId < 1) {
            return execConstructorCall(iMethodId, objArr);
        }
        if (!(scriptable2 instanceof NativeNumber)) {
            throw IdScriptableObject.incompatibleCallError(idFunctionObject);
        }
        double d2 = ((NativeNumber) scriptable2).doubleValue;
        int int32 = 10;
        switch (iMethodId) {
            case 2:
            case 3:
                if (objArr.length != 0 && (obj = objArr[0]) != Undefined.instance) {
                    int32 = ScriptRuntime.toInt32(obj);
                }
                return ScriptRuntime.numberToString(d2, int32);
            case 4:
                return "(new Number(" + ScriptRuntime.toString(d2) + "))";
            case 5:
                return ScriptRuntime.wrapNumber(d2);
            case 6:
                return num_to(d2, objArr, 2, 2, -20, 0);
            case 7:
                return Double.isNaN(d2) ? "NaN" : Double.isInfinite(d2) ? d2 >= 0.0d ? "Infinity" : "-Infinity" : num_to(d2, objArr, 1, 3, 0, 1);
            case 8:
                return (objArr.length == 0 || objArr[0] == Undefined.instance) ? ScriptRuntime.numberToString(d2, 10) : Double.isNaN(d2) ? "NaN" : Double.isInfinite(d2) ? d2 >= 0.0d ? "Infinity" : "-Infinity" : num_to(d2, objArr, 0, 4, 1, 0);
            default:
                throw new IllegalArgumentException(String.valueOf(iMethodId));
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        idFunctionObject.defineProperty("NaN", ScriptRuntime.NaNobj, 7);
        idFunctionObject.defineProperty("POSITIVE_INFINITY", ScriptRuntime.wrapNumber(Double.POSITIVE_INFINITY), 7);
        idFunctionObject.defineProperty("NEGATIVE_INFINITY", ScriptRuntime.wrapNumber(Double.NEGATIVE_INFINITY), 7);
        idFunctionObject.defineProperty("MAX_VALUE", ScriptRuntime.wrapNumber(Double.MAX_VALUE), 7);
        idFunctionObject.defineProperty("MIN_VALUE", ScriptRuntime.wrapNumber(Double.MIN_VALUE), 7);
        idFunctionObject.defineProperty("MAX_SAFE_INTEGER", ScriptRuntime.wrapNumber(MAX_SAFE_INTEGER), 7);
        idFunctionObject.defineProperty("MIN_SAFE_INTEGER", ScriptRuntime.wrapNumber(MIN_SAFE_INTEGER), 7);
        Object obj = NUMBER_TAG;
        addIdFunctionProperty(idFunctionObject, obj, -1, "isFinite", 1);
        addIdFunctionProperty(idFunctionObject, obj, -2, "isNaN", 1);
        addIdFunctionProperty(idFunctionObject, obj, -3, "isInteger", 1);
        addIdFunctionProperty(idFunctionObject, obj, -4, "isSafeInteger", 1);
        addIdFunctionProperty(idFunctionObject, obj, -5, "parseFloat", 1);
        addIdFunctionProperty(idFunctionObject, obj, -6, "parseInt", 1);
        super.fillConstructorProperties(idFunctionObject);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0059  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r8) {
        /*
            r7 = this;
            int r0 = r8.length()
            r1 = 7
            r2 = 116(0x74, float:1.63E-43)
            r3 = 0
            if (r0 == r1) goto L47
            r4 = 3
            r5 = 8
            if (r0 == r5) goto L35
            r6 = 11
            if (r0 == r6) goto L23
            r2 = 13
            if (r0 == r2) goto L20
            r1 = 14
            if (r0 == r1) goto L1c
            goto L59
        L1c:
            java.lang.String r0 = "toLocaleString"
            r1 = r4
            goto L5b
        L20:
            java.lang.String r0 = "toExponential"
            goto L5b
        L23:
            char r0 = r8.charAt(r3)
            r1 = 99
            if (r0 != r1) goto L2f
            java.lang.String r0 = "constructor"
            r1 = 1
            goto L5b
        L2f:
            if (r0 != r2) goto L59
            java.lang.String r0 = "toPrecision"
            r1 = r5
            goto L5b
        L35:
            char r0 = r8.charAt(r4)
            r1 = 111(0x6f, float:1.56E-43)
            if (r0 != r1) goto L41
            java.lang.String r0 = "toSource"
            r1 = 4
            goto L5b
        L41:
            if (r0 != r2) goto L59
            java.lang.String r0 = "toString"
            r1 = 2
            goto L5b
        L47:
            char r0 = r8.charAt(r3)
            if (r0 != r2) goto L51
            java.lang.String r0 = "toFixed"
            r1 = 6
            goto L5b
        L51:
            r1 = 118(0x76, float:1.65E-43)
            if (r0 != r1) goto L59
            java.lang.String r0 = "valueOf"
            r1 = 5
            goto L5b
        L59:
            r0 = 0
            r1 = r3
        L5b:
            if (r0 == 0) goto L66
            if (r0 == r8) goto L66
            boolean r8 = r0.equals(r8)
            if (r8 != 0) goto L66
            goto L67
        L66:
            r3 = r1
        L67:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeNumber.findPrototypeId(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Number";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        String str2;
        int i3 = 1;
        switch (i2) {
            case 1:
                str = "constructor";
                initPrototypeMethod(NUMBER_TAG, i2, str, i3);
                return;
            case 2:
                str = "toString";
                initPrototypeMethod(NUMBER_TAG, i2, str, i3);
                return;
            case 3:
                str = "toLocaleString";
                initPrototypeMethod(NUMBER_TAG, i2, str, i3);
                return;
            case 4:
                str2 = "toSource";
                String str3 = str2;
                i3 = 0;
                str = str3;
                initPrototypeMethod(NUMBER_TAG, i2, str, i3);
                return;
            case 5:
                str2 = "valueOf";
                String str32 = str2;
                i3 = 0;
                str = str32;
                initPrototypeMethod(NUMBER_TAG, i2, str, i3);
                return;
            case 6:
                str = "toFixed";
                initPrototypeMethod(NUMBER_TAG, i2, str, i3);
                return;
            case 7:
                str = "toExponential";
                initPrototypeMethod(NUMBER_TAG, i2, str, i3);
                return;
            case 8:
                str = "toPrecision";
                initPrototypeMethod(NUMBER_TAG, i2, str, i3);
                return;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
    }

    public String toString() {
        return ScriptRuntime.numberToString(this.doubleValue, 10);
    }
}
