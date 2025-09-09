package org.mozilla.javascript;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.kingsmith.miot.KsProperty;
import com.umeng.analytics.pro.ay;
import org.mozilla.javascript.typedarrays.Conversions;

/* loaded from: classes5.dex */
final class NativeMath extends IdScriptableObject {
    private static final int Id_E = 30;
    private static final int Id_LN10 = 32;
    private static final int Id_LN2 = 33;
    private static final int Id_LOG10E = 35;
    private static final int Id_LOG2E = 34;
    private static final int Id_PI = 31;
    private static final int Id_SQRT1_2 = 36;
    private static final int Id_SQRT2 = 37;
    private static final int Id_abs = 2;
    private static final int Id_acos = 3;
    private static final int Id_asin = 4;
    private static final int Id_atan = 5;
    private static final int Id_atan2 = 6;
    private static final int Id_cbrt = 20;
    private static final int Id_ceil = 7;
    private static final int Id_cos = 8;
    private static final int Id_cosh = 21;
    private static final int Id_exp = 9;
    private static final int Id_expm1 = 22;
    private static final int Id_floor = 10;
    private static final int Id_hypot = 23;
    private static final int Id_imul = 28;
    private static final int Id_log = 11;
    private static final int Id_log10 = 25;
    private static final int Id_log1p = 24;
    private static final int Id_max = 12;
    private static final int Id_min = 13;
    private static final int Id_pow = 14;
    private static final int Id_random = 15;
    private static final int Id_round = 16;
    private static final int Id_sin = 17;
    private static final int Id_sinh = 26;
    private static final int Id_sqrt = 18;
    private static final int Id_tan = 19;
    private static final int Id_tanh = 27;
    private static final int Id_toSource = 1;
    private static final int Id_trunc = 29;
    private static final int LAST_METHOD_ID = 29;
    private static final Object MATH_TAG = "Math";
    private static final int MAX_ID = 37;
    static final long serialVersionUID = -8838847185801131569L;

    private NativeMath() {
    }

    static void init(Scriptable scriptable, boolean z2) {
        NativeMath nativeMath = new NativeMath();
        nativeMath.activatePrototypeMap(37);
        nativeMath.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        nativeMath.setParentScope(scriptable);
        if (z2) {
            nativeMath.sealObject();
        }
        ScriptableObject.defineProperty(scriptable, "Math", nativeMath, 2);
    }

    private double js_hypot(Object[] objArr) {
        double d2 = 0.0d;
        if (objArr == null) {
            return 0.0d;
        }
        for (Object obj : objArr) {
            double number = ScriptRuntime.toNumber(obj);
            if (number == ScriptRuntime.NaN) {
                return number;
            }
            if (number == Double.POSITIVE_INFINITY || number == Double.NEGATIVE_INFINITY) {
                return Double.POSITIVE_INFINITY;
            }
            d2 += number * number;
        }
        return Math.sqrt(d2);
    }

    private Object js_imul(Object[] objArr) {
        if (objArr == null || objArr.length < 2) {
            return ScriptRuntime.wrapNumber(ScriptRuntime.NaN);
        }
        long uint32 = (Conversions.toUint32(objArr[0]) * Conversions.toUint32(objArr[1])) % Conversions.THIRTYTWO_BIT;
        if (uint32 >= 2147483648L) {
            uint32 -= Conversions.THIRTYTWO_BIT;
        }
        return Double.valueOf(ScriptRuntime.toNumber(Long.valueOf(uint32)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0066, code lost:
    
        if (r21 < 1.0d) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private double js_pow(double r21, double r23) {
        /*
            r20 = this;
            r0 = r23
            int r2 = (r0 > r0 ? 1 : (r0 == r0 ? 0 : -1))
            if (r2 == 0) goto La
            r16 = r0
            goto La1
        La:
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r5 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            if (r4 != 0) goto L16
            r16 = r5
            goto La1
        L16:
            int r7 = (r21 > r2 ? 1 : (r21 == r2 ? 0 : -1))
            r8 = -9223372036854775808
            r10 = 0
            r12 = 1
            r14 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            r16 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            if (r7 != 0) goto L49
            double r5 = r5 / r21
            int r5 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r5 <= 0) goto L2d
            if (r4 <= 0) goto La1
            goto L46
        L2d:
            long r5 = (long) r0
            double r2 = (double) r5
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 != 0) goto L3f
            long r0 = r5 & r12
            int r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r0 == 0) goto L3f
            if (r4 <= 0) goto L3c
            goto L3d
        L3c:
            r8 = r14
        L3d:
            r2 = r8
            goto L46
        L3f:
            if (r4 <= 0) goto L44
            r2 = 0
            goto L46
        L44:
            r2 = r16
        L46:
            r16 = r2
            goto La1
        L49:
            double r2 = java.lang.Math.pow(r21, r23)
            int r7 = (r2 > r2 ? 1 : (r2 == r2 ? 0 : -1))
            if (r7 == 0) goto L46
            int r7 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            r18 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            if (r7 != 0) goto L6b
            int r0 = (r21 > r18 ? 1 : (r21 == r18 ? 0 : -1))
            if (r0 < 0) goto La1
            int r0 = (r5 > r21 ? 1 : (r5 == r21 ? 0 : -1))
            if (r0 >= 0) goto L60
            goto La1
        L60:
            int r0 = (r18 > r21 ? 1 : (r18 == r21 ? 0 : -1))
            if (r0 >= 0) goto L46
            int r0 = (r21 > r5 ? 1 : (r21 == r5 ? 0 : -1))
            if (r0 >= 0) goto L46
        L68:
            r16 = 0
            goto La1
        L6b:
            int r7 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1))
            if (r7 != 0) goto L81
            int r0 = (r21 > r18 ? 1 : (r21 == r18 ? 0 : -1))
            if (r0 < 0) goto L68
            int r0 = (r5 > r21 ? 1 : (r5 == r21 ? 0 : -1))
            if (r0 >= 0) goto L78
            goto L68
        L78:
            int r0 = (r18 > r21 ? 1 : (r18 == r21 ? 0 : -1))
            if (r0 >= 0) goto L46
            int r0 = (r21 > r5 ? 1 : (r21 == r5 ? 0 : -1))
            if (r0 >= 0) goto L46
            goto La1
        L81:
            int r5 = (r21 > r16 ? 1 : (r21 == r16 ? 0 : -1))
            if (r5 != 0) goto L88
            if (r4 <= 0) goto L68
            goto La1
        L88:
            int r5 = (r21 > r14 ? 1 : (r21 == r14 ? 0 : -1))
            if (r5 != 0) goto L46
            long r2 = (long) r0
            double r5 = (double) r2
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 != 0) goto L9f
            long r0 = r2 & r12
            int r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r0 == 0) goto L9f
            if (r4 <= 0) goto L9b
            goto L9c
        L9b:
            r14 = r8
        L9c:
            r16 = r14
            goto La1
        L9f:
            if (r4 <= 0) goto L68
        La1:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeMath.js_pow(double, double):double");
    }

    private double js_trunc(double d2) {
        return d2 < 0.0d ? Math.ceil(d2) : Math.floor(d2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        double number;
        if (!idFunctionObject.hasTag(MATH_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        double dAtan = Double.NaN;
        int i2 = 0;
        switch (iMethodId) {
            case 1:
                return "Math";
            case 2:
                number = ScriptRuntime.toNumber(objArr, 0);
                if (number != 0.0d) {
                    if (number < 0.0d) {
                        number = -number;
                    }
                    dAtan = number;
                    return ScriptRuntime.wrapNumber(dAtan);
                }
                dAtan = 0.0d;
                return ScriptRuntime.wrapNumber(dAtan);
            case 3:
            case 4:
                double number2 = ScriptRuntime.toNumber(objArr, 0);
                if (number2 == number2 && -1.0d <= number2 && number2 <= 1.0d) {
                    number = iMethodId == 3 ? Math.acos(number2) : Math.asin(number2);
                    dAtan = number;
                }
                return ScriptRuntime.wrapNumber(dAtan);
            case 5:
                dAtan = Math.atan(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 6:
                dAtan = Math.atan2(ScriptRuntime.toNumber(objArr, 0), ScriptRuntime.toNumber(objArr, 1));
                return ScriptRuntime.wrapNumber(dAtan);
            case 7:
                dAtan = Math.ceil(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 8:
                double number3 = ScriptRuntime.toNumber(objArr, 0);
                if (number3 != Double.POSITIVE_INFINITY && number3 != Double.NEGATIVE_INFINITY) {
                    dAtan = Math.cos(number3);
                }
                return ScriptRuntime.wrapNumber(dAtan);
            case 9:
                number = ScriptRuntime.toNumber(objArr, 0);
                if (number != Double.POSITIVE_INFINITY) {
                    if (number != Double.NEGATIVE_INFINITY) {
                        number = Math.exp(number);
                    }
                    dAtan = 0.0d;
                    return ScriptRuntime.wrapNumber(dAtan);
                }
                dAtan = number;
                return ScriptRuntime.wrapNumber(dAtan);
            case 10:
                dAtan = Math.floor(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 11:
                double number4 = ScriptRuntime.toNumber(objArr, 0);
                if (number4 >= 0.0d) {
                    dAtan = Math.log(number4);
                }
                return ScriptRuntime.wrapNumber(dAtan);
            case 12:
            case 13:
                double dMax = iMethodId != 12 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
                while (true) {
                    if (i2 != objArr.length) {
                        dAtan = ScriptRuntime.toNumber(objArr[i2]);
                        if (dAtan == dAtan) {
                            dMax = iMethodId == 12 ? Math.max(dMax, dAtan) : Math.min(dMax, dAtan);
                            i2++;
                        }
                    } else {
                        dAtan = dMax;
                    }
                }
                return ScriptRuntime.wrapNumber(dAtan);
            case 14:
                dAtan = js_pow(ScriptRuntime.toNumber(objArr, 0), ScriptRuntime.toNumber(objArr, 1));
                return ScriptRuntime.wrapNumber(dAtan);
            case 15:
                dAtan = Math.random();
                return ScriptRuntime.wrapNumber(dAtan);
            case 16:
                dAtan = ScriptRuntime.toNumber(objArr, 0);
                if (dAtan == dAtan && dAtan != Double.POSITIVE_INFINITY && dAtan != Double.NEGATIVE_INFINITY) {
                    long jRound = Math.round(dAtan);
                    if (jRound != 0) {
                        number = jRound;
                    } else if (dAtan < 0.0d) {
                        number = ScriptRuntime.negativeZero;
                    } else if (dAtan != 0.0d) {
                        dAtan = 0.0d;
                    }
                    dAtan = number;
                }
                return ScriptRuntime.wrapNumber(dAtan);
            case 17:
                double number5 = ScriptRuntime.toNumber(objArr, 0);
                if (number5 != Double.POSITIVE_INFINITY && number5 != Double.NEGATIVE_INFINITY) {
                    dAtan = Math.sin(number5);
                }
                return ScriptRuntime.wrapNumber(dAtan);
            case 18:
                dAtan = Math.sqrt(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 19:
                dAtan = Math.tan(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 20:
                dAtan = Math.cbrt(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 21:
                dAtan = Math.cosh(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 22:
                dAtan = Math.expm1(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 23:
                dAtan = js_hypot(objArr);
                return ScriptRuntime.wrapNumber(dAtan);
            case 24:
                dAtan = Math.log1p(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 25:
                dAtan = Math.log10(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 26:
                dAtan = Math.sinh(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 27:
                dAtan = Math.tanh(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            case 28:
                return js_imul(objArr);
            case 29:
                dAtan = js_trunc(ScriptRuntime.toNumber(objArr, 0));
                return ScriptRuntime.wrapNumber(dAtan);
            default:
                throw new IllegalStateException(String.valueOf(iMethodId));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x01af, code lost:
    
        if (r17.charAt(1) == 'b') goto L121;
     */
    /* JADX WARN: Removed duplicated region for block: B:135:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01e6 A[ADDED_TO_REGION] */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeMath.findPrototypeId(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Math";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        double d2;
        String str;
        String str2;
        String str3;
        if (i2 > 29) {
            switch (i2) {
                case 30:
                    d2 = 2.718281828459045d;
                    str = ExifInterface.LONGITUDE_EAST;
                    break;
                case 31:
                    d2 = 3.141592653589793d;
                    str = "PI";
                    break;
                case 32:
                    d2 = 2.302585092994046d;
                    str = "LN10";
                    break;
                case 33:
                    d2 = 0.6931471805599453d;
                    str = "LN2";
                    break;
                case 34:
                    d2 = 1.4426950408889634d;
                    str = "LOG2E";
                    break;
                case 35:
                    d2 = 0.4342944819032518d;
                    str = "LOG10E";
                    break;
                case 36:
                    d2 = 0.7071067811865476d;
                    str = "SQRT1_2";
                    break;
                case 37:
                    d2 = 1.4142135623730951d;
                    str = "SQRT2";
                    break;
                default:
                    throw new IllegalStateException(String.valueOf(i2));
            }
            initPrototypeValue(i2, str, ScriptRuntime.wrapNumber(d2), 7);
            return;
        }
        int i3 = 1;
        switch (i2) {
            case 1:
                str2 = "toSource";
                i3 = 0;
                str3 = str2;
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 2:
                str3 = "abs";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 3:
                str3 = "acos";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 4:
                str3 = "asin";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 5:
                str3 = "atan";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 6:
                str3 = "atan2";
                i3 = 2;
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 7:
                str3 = "ceil";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 8:
                str3 = "cos";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 9:
                str3 = ay.f21367b;
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 10:
                str3 = "floor";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 11:
                str3 = "log";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 12:
                str3 = KsProperty.Max;
                i3 = 2;
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 13:
                str3 = "min";
                i3 = 2;
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 14:
                str3 = "pow";
                i3 = 2;
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 15:
                str2 = AlinkConstants.KEY_RANDOM;
                i3 = 0;
                str3 = str2;
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 16:
                str3 = "round";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 17:
                str3 = "sin";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 18:
                str3 = "sqrt";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 19:
                str3 = "tan";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 20:
                str3 = "cbrt";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 21:
                str3 = "cosh";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 22:
                str3 = "expm1";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 23:
                str3 = "hypot";
                i3 = 2;
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 24:
                str3 = "log1p";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 25:
                str3 = "log10";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 26:
                str3 = "sinh";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 27:
                str3 = "tanh";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 28:
                str3 = "imul";
                i3 = 2;
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            case 29:
                str3 = "trunc";
                initPrototypeMethod(MATH_TAG, i2, str3, i3);
                return;
            default:
                throw new IllegalStateException(String.valueOf(i2));
        }
    }
}
