package org.mozilla.javascript;

import androidx.media3.exoplayer.rtsp.SessionDescription;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.linksdk.alcs.AlcsConstant;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.text.Typography;

/* loaded from: classes5.dex */
final class NativeString extends IdScriptableObject {
    private static final int ConstructorId_charAt = -5;
    private static final int ConstructorId_charCodeAt = -6;
    private static final int ConstructorId_concat = -14;
    private static final int ConstructorId_equalsIgnoreCase = -30;
    private static final int ConstructorId_fromCharCode = -1;
    private static final int ConstructorId_indexOf = -7;
    private static final int ConstructorId_lastIndexOf = -8;
    private static final int ConstructorId_localeCompare = -34;
    private static final int ConstructorId_match = -31;
    private static final int ConstructorId_replace = -33;
    private static final int ConstructorId_search = -32;
    private static final int ConstructorId_slice = -15;
    private static final int ConstructorId_split = -9;
    private static final int ConstructorId_substr = -13;
    private static final int ConstructorId_substring = -10;
    private static final int ConstructorId_toLocaleLowerCase = -35;
    private static final int ConstructorId_toLowerCase = -11;
    private static final int ConstructorId_toUpperCase = -12;
    private static final int Id_anchor = 28;
    private static final int Id_big = 21;
    private static final int Id_blink = 22;
    private static final int Id_bold = 16;
    private static final int Id_charAt = 5;
    private static final int Id_charCodeAt = 6;
    private static final int Id_codePointAt = 45;
    private static final int Id_concat = 14;
    private static final int Id_constructor = 1;
    private static final int Id_endsWith = 42;
    private static final int Id_equals = 29;
    private static final int Id_equalsIgnoreCase = 30;
    private static final int Id_fixed = 18;
    private static final int Id_fontcolor = 26;
    private static final int Id_fontsize = 25;
    private static final int Id_includes = 40;
    private static final int Id_indexOf = 7;
    private static final int Id_italics = 17;
    private static final int Id_lastIndexOf = 8;
    private static final int Id_length = 1;
    private static final int Id_link = 27;
    private static final int Id_localeCompare = 34;
    private static final int Id_match = 31;
    private static final int Id_normalize = 43;
    private static final int Id_repeat = 44;
    private static final int Id_replace = 33;
    private static final int Id_search = 32;
    private static final int Id_slice = 15;
    private static final int Id_small = 20;
    private static final int Id_split = 9;
    private static final int Id_startsWith = 41;
    private static final int Id_strike = 19;
    private static final int Id_sub = 24;
    private static final int Id_substr = 13;
    private static final int Id_substring = 10;
    private static final int Id_sup = 23;
    private static final int Id_toLocaleLowerCase = 35;
    private static final int Id_toLocaleUpperCase = 36;
    private static final int Id_toLowerCase = 11;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int Id_toUpperCase = 12;
    private static final int Id_trim = 37;
    private static final int Id_trimLeft = 38;
    private static final int Id_trimRight = 39;
    private static final int Id_valueOf = 4;
    private static final int MAX_INSTANCE_ID = 1;
    private static final int MAX_PROTOTYPE_ID = 46;
    private static final Object STRING_TAG = "String";
    private static final int SymbolId_iterator = 46;
    static final long serialVersionUID = 920268368584188687L;
    private CharSequence string;

    NativeString(CharSequence charSequence) {
        this.string = charSequence;
    }

    static void init(Scriptable scriptable, boolean z2) {
        new NativeString("").exportAsJSClass(46, scriptable, z2);
    }

    private static String js_concat(String str, Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return str;
        }
        if (length == 1) {
            return str.concat(ScriptRuntime.toString(objArr[0]));
        }
        int length2 = str.length();
        String[] strArr = new String[length];
        for (int i2 = 0; i2 != length; i2++) {
            String string = ScriptRuntime.toString(objArr[i2]);
            strArr[i2] = string;
            length2 += string.length();
        }
        StringBuilder sb = new StringBuilder(length2);
        sb.append(str);
        for (int i3 = 0; i3 != length; i3++) {
            sb.append(strArr[i3]);
        }
        return sb.toString();
    }

    private static int js_indexOf(int i2, String str, Object[] objArr) {
        int length;
        String string = ScriptRuntime.toString(objArr, 0);
        double integer = ScriptRuntime.toInteger(objArr, 1);
        if (integer > str.length() && i2 != 41 && i2 != 42) {
            return -1;
        }
        if (integer < 0.0d) {
            integer = 0.0d;
        } else {
            if (integer > str.length()) {
                length = str.length();
            } else if (i2 == 42 && (integer != integer || integer > str.length())) {
                length = str.length();
            }
            integer = length;
        }
        if (42 != i2) {
            return i2 == 41 ? str.startsWith(string, (int) integer) ? 0 : -1 : str.indexOf(string, (int) integer);
        }
        if (objArr.length == 0 || objArr.length == 1 || (objArr.length == 2 && objArr[1] == Undefined.instance)) {
            integer = str.length();
        }
        return str.substring(0, (int) integer).endsWith(string) ? 0 : -1;
    }

    private static int js_lastIndexOf(String str, Object[] objArr) {
        String string = ScriptRuntime.toString(objArr, 0);
        double number = ScriptRuntime.toNumber(objArr, 1);
        if (number != number || number > str.length()) {
            number = str.length();
        } else if (number < 0.0d) {
            number = 0.0d;
        }
        return str.lastIndexOf(string, (int) number);
    }

    private static String js_repeat(Context context, Scriptable scriptable, IdFunctionObject idFunctionObject, Object[] objArr) {
        String string = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, scriptable, idFunctionObject));
        double integer = ScriptRuntime.toInteger(objArr, 0);
        if (integer < 0.0d || integer == Double.POSITIVE_INFINITY) {
            throw ScriptRuntime.rangeError("Invalid count value");
        }
        if (integer == 0.0d || string.length() == 0) {
            return "";
        }
        long length = string.length() * ((long) integer);
        if (integer > 2.147483647E9d || length > 2147483647L) {
            throw ScriptRuntime.rangeError("Invalid size or count value");
        }
        StringBuilder sb = new StringBuilder((int) length);
        sb.append(string);
        int i2 = (int) integer;
        int i3 = 1;
        while (i3 <= i2 / 2) {
            sb.append((CharSequence) sb);
            i3 *= 2;
        }
        if (i3 < i2) {
            sb.append(sb.substring(0, string.length() * (i2 - i3)));
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x003f A[PHI: r6
      0x003f: PHI (r6v4 double) = (r6v3 double), (r6v5 double) binds: [B:26:0x0044, B:22:0x003c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.CharSequence js_slice(java.lang.CharSequence r10, java.lang.Object[] r11) {
        /*
            int r0 = r11.length
            r1 = 0
            r3 = 1
            if (r0 >= r3) goto L8
            r4 = r1
            goto Lf
        L8:
            r0 = 0
            r0 = r11[r0]
            double r4 = org.mozilla.javascript.ScriptRuntime.toInteger(r0)
        Lf:
            int r0 = r10.length()
            int r6 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r6 >= 0) goto L1f
            double r6 = (double) r0
            double r4 = r4 + r6
            int r6 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r6 >= 0) goto L25
            r4 = r1
            goto L25
        L1f:
            double r6 = (double) r0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 <= 0) goto L25
            r4 = r6
        L25:
            int r6 = r11.length
            r7 = 2
            if (r6 < r7) goto L4c
            r11 = r11[r3]
            java.lang.Object r3 = org.mozilla.javascript.Undefined.instance
            if (r11 != r3) goto L30
            goto L4c
        L30:
            double r6 = org.mozilla.javascript.ScriptRuntime.toInteger(r11)
            int r11 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r11 >= 0) goto L41
            double r8 = (double) r0
            double r6 = r6 + r8
            int r11 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r11 >= 0) goto L3f
            goto L46
        L3f:
            r1 = r6
            goto L46
        L41:
            double r1 = (double) r0
            int r11 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r11 <= 0) goto L3f
        L46:
            int r11 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r11 >= 0) goto L4d
            r1 = r4
            goto L4d
        L4c:
            double r1 = (double) r0
        L4d:
            int r11 = (int) r4
            int r0 = (int) r1
            java.lang.CharSequence r10 = r10.subSequence(r11, r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeString.js_slice(java.lang.CharSequence, java.lang.Object[]):java.lang.CharSequence");
    }

    private static CharSequence js_substr(CharSequence charSequence, Object[] objArr) {
        double d2;
        if (objArr.length < 1) {
            return charSequence;
        }
        double integer = ScriptRuntime.toInteger(objArr[0]);
        int length = charSequence.length();
        if (integer < 0.0d) {
            integer += length;
            if (integer < 0.0d) {
                integer = 0.0d;
            }
        } else {
            double d3 = length;
            if (integer > d3) {
                integer = d3;
            }
        }
        if (objArr.length == 1) {
            d2 = length;
        } else {
            double integer2 = ScriptRuntime.toInteger(objArr[1]);
            double d4 = (integer2 >= 0.0d ? integer2 : 0.0d) + integer;
            d2 = length;
            if (d4 <= d2) {
                d2 = d4;
            }
        }
        return charSequence.subSequence((int) integer, (int) d2);
    }

    private static CharSequence js_substring(Context context, CharSequence charSequence, Object[] objArr) {
        Object obj;
        int length = charSequence.length();
        double integer = ScriptRuntime.toInteger(objArr, 0);
        double d2 = 0.0d;
        if (integer < 0.0d) {
            integer = 0.0d;
        } else {
            double d3 = length;
            if (integer > d3) {
                integer = d3;
            }
        }
        if (objArr.length <= 1 || (obj = objArr[1]) == Undefined.instance) {
            d2 = length;
        } else {
            double integer2 = ScriptRuntime.toInteger(obj);
            if (integer2 >= 0.0d) {
                d2 = length;
                if (integer2 <= d2) {
                    d2 = integer2;
                }
            }
            if (d2 < integer) {
                if (context.getLanguageVersion() != 120) {
                    double d4 = integer;
                    integer = d2;
                    d2 = d4;
                } else {
                    d2 = integer;
                }
            }
        }
        return charSequence.subSequence((int) integer, (int) d2);
    }

    private static NativeString realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeString) {
            return (NativeString) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    private static String tagify(Object obj, String str, String str2, Object[] objArr) {
        String string = ScriptRuntime.toString(obj);
        StringBuilder sb = new StringBuilder();
        sb.append(Typography.less);
        sb.append(str);
        if (str2 != null) {
            sb.append(' ');
            sb.append(str2);
            sb.append("=\"");
            sb.append(ScriptRuntime.toString(objArr, 0));
            sb.append(Typography.quote);
        }
        sb.append(Typography.greater);
        sb.append(string);
        sb.append("</");
        sb.append(str);
        sb.append(Typography.greater);
        return sb.toString();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:200)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public java.lang.Object execIdCall(org.mozilla.javascript.IdFunctionObject r17, org.mozilla.javascript.Context r18, org.mozilla.javascript.Scriptable r19, org.mozilla.javascript.Scriptable r20, java.lang.Object[] r21) {
        /*
            Method dump skipped, instructions count: 1042
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeString.execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        Object obj = STRING_TAG;
        addIdFunctionProperty(idFunctionObject, obj, -1, "fromCharCode", 1);
        addIdFunctionProperty(idFunctionObject, obj, -5, "charAt", 2);
        addIdFunctionProperty(idFunctionObject, obj, -6, "charCodeAt", 2);
        addIdFunctionProperty(idFunctionObject, obj, -7, "indexOf", 2);
        addIdFunctionProperty(idFunctionObject, obj, -8, "lastIndexOf", 2);
        addIdFunctionProperty(idFunctionObject, obj, -9, "split", 3);
        addIdFunctionProperty(idFunctionObject, obj, -10, "substring", 3);
        addIdFunctionProperty(idFunctionObject, obj, -11, "toLowerCase", 1);
        addIdFunctionProperty(idFunctionObject, obj, -12, "toUpperCase", 1);
        addIdFunctionProperty(idFunctionObject, obj, -13, "substr", 3);
        addIdFunctionProperty(idFunctionObject, obj, -14, "concat", 2);
        addIdFunctionProperty(idFunctionObject, obj, -15, "slice", 3);
        addIdFunctionProperty(idFunctionObject, obj, -30, "equalsIgnoreCase", 2);
        addIdFunctionProperty(idFunctionObject, obj, -31, "match", 2);
        addIdFunctionProperty(idFunctionObject, obj, -32, FirebaseAnalytics.Event.SEARCH, 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_replace, "replace", 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_localeCompare, "localeCompare", 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_toLocaleLowerCase, "toLocaleLowerCase", 1);
        super.fillConstructorProperties(idFunctionObject);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        return str.equals(SessionDescription.ATTR_LENGTH) ? IdScriptableObject.instanceIdInfo(7, 1) : super.findInstanceIdInfo(str);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(Symbol symbol) {
        return SymbolKey.ITERATOR.equals(symbol) ? 46 : 0;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object get(int i2, Scriptable scriptable) {
        return (i2 < 0 || i2 >= this.string.length()) ? super.get(i2, scriptable) : String.valueOf(this.string.charAt(i2));
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "String";
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject
    protected Object[] getIds(boolean z2, boolean z3) {
        Context currentContext = Context.getCurrentContext();
        if (currentContext == null || currentContext.getLanguageVersion() < 200) {
            return super.getIds(z2, z3);
        }
        Object[] ids = super.getIds(z2, z3);
        Object[] objArr = new Object[ids.length + this.string.length()];
        int i2 = 0;
        while (i2 < this.string.length()) {
            objArr[i2] = Integer.valueOf(i2);
            i2++;
        }
        System.arraycopy(ids, 0, objArr, i2, ids.length);
        return objArr;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        return i2 == 1 ? SessionDescription.ATTR_LENGTH : super.getInstanceIdName(i2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        return i2 == 1 ? ScriptRuntime.wrapInt(this.string.length()) : super.getInstanceIdValue(i2);
    }

    int getLength() {
        return this.string.length();
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 1;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean has(int i2, Scriptable scriptable) {
        if (i2 < 0 || i2 >= this.string.length()) {
            return super.has(i2, scriptable);
        }
        return true;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        String str2;
        String str3;
        int i3;
        String str4;
        if (i2 == 46) {
            initPrototypeMethod(STRING_TAG, i2, SymbolKey.ITERATOR, "[Symbol.iterator]", 0);
            return;
        }
        switch (i2) {
            case 1:
                str = "constructor";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 2:
                str2 = "toString";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 3:
                str2 = "toSource";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 4:
                str2 = "valueOf";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 5:
                str = "charAt";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 6:
                str = "charCodeAt";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 7:
                str = "indexOf";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 8:
                str = "lastIndexOf";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 9:
                str3 = "split";
                i3 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 10:
                str3 = "substring";
                i3 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 11:
                str2 = "toLowerCase";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 12:
                str2 = "toUpperCase";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 13:
                str3 = "substr";
                i3 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 14:
                str = "concat";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 15:
                str3 = "slice";
                i3 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 16:
                str2 = TtmlNode.BOLD;
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 17:
                str2 = "italics";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 18:
                str2 = "fixed";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 19:
                str2 = "strike";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 20:
                str2 = "small";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 21:
                str2 = "big";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 22:
                str2 = "blink";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 23:
                str2 = "sup";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 24:
                str2 = AlcsConstant.EVENT_SUB;
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 25:
                str2 = "fontsize";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 26:
                str2 = "fontcolor";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 27:
                str2 = "link";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 28:
                str2 = "anchor";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 29:
                str = "equals";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 30:
                str = "equalsIgnoreCase";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 31:
                str = "match";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 32:
                str = FirebaseAnalytics.Event.SEARCH;
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 33:
                str3 = "replace";
                i3 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 34:
                str = "localeCompare";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 35:
                str2 = "toLocaleLowerCase";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 36:
                str2 = "toLocaleUpperCase";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 37:
                str2 = "trim";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 38:
                str2 = "trimLeft";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 39:
                str2 = "trimRight";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 40:
                str = "includes";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 41:
                str = "startsWith";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 42:
                str = "endsWith";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 43:
                str2 = "normalize";
                str4 = str2;
                i3 = 0;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 44:
                str = "repeat";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            case 45:
                str = "codePointAt";
                str4 = str;
                i3 = 1;
                initPrototypeMethod(STRING_TAG, i2, str4, (String) null, i3);
                return;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(int i2, Scriptable scriptable, Object obj) {
        if (i2 < 0 || i2 >= this.string.length()) {
            super.put(i2, scriptable, obj);
        }
    }

    public CharSequence toCharSequence() {
        return this.string;
    }

    public String toString() {
        CharSequence charSequence = this.string;
        return charSequence instanceof String ? (String) charSequence : charSequence.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01fb A[ADDED_TO_REGION] */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 552
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeString.findPrototypeId(java.lang.String):int");
    }
}
