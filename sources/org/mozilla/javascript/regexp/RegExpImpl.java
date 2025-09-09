package org.mozilla.javascript.regexp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.RegExpProxy;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
public class RegExpImpl implements RegExpProxy {
    protected String input;
    protected SubString lastMatch;
    protected SubString lastParen;
    protected SubString leftContext;
    protected boolean multiline;
    protected SubString[] parens;
    protected SubString rightContext;

    private static NativeRegExp createRegExp(Context context, Scriptable scriptable, Object[] objArr, int i2, boolean z2) {
        Object obj;
        String string;
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        if (objArr.length == 0 || (obj = objArr[0]) == Undefined.instance) {
            return new NativeRegExp(topLevelScope, NativeRegExp.compileRE(context, "", "", false));
        }
        if (obj instanceof NativeRegExp) {
            return (NativeRegExp) obj;
        }
        String string2 = ScriptRuntime.toString(obj);
        if (i2 < objArr.length) {
            objArr[0] = string2;
            string = ScriptRuntime.toString(objArr[i2]);
        } else {
            string = null;
        }
        return new NativeRegExp(topLevelScope, NativeRegExp.compileRE(context, string2, string, z2));
    }

    private static void do_replace(GlobData globData, Context context, RegExpImpl regExpImpl) throws RuntimeException {
        int i2;
        StringBuilder sb = globData.charBuf;
        String str = globData.repstr;
        int iIndexOf = globData.dollar;
        int i3 = 0;
        if (iIndexOf != -1) {
            int[] iArr = new int[1];
            int i4 = 0;
            do {
                sb.append(str.substring(i4, iIndexOf));
                SubString subStringInterpretDollar = interpretDollar(context, regExpImpl, str, iIndexOf, iArr);
                if (subStringInterpretDollar != null) {
                    int i5 = subStringInterpretDollar.length;
                    if (i5 > 0) {
                        String str2 = subStringInterpretDollar.str;
                        int i6 = subStringInterpretDollar.index;
                        sb.append((CharSequence) str2, i6, i5 + i6);
                    }
                    int i7 = iArr[0];
                    int i8 = iIndexOf + i7;
                    i2 = iIndexOf + i7;
                    i4 = i8;
                } else {
                    i4 = iIndexOf;
                    i2 = iIndexOf + 1;
                }
                iIndexOf = str.indexOf(36, i2);
            } while (iIndexOf >= 0);
            i3 = i4;
        }
        int length = str.length();
        if (length > i3) {
            sb.append(str.substring(i3, length));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static org.mozilla.javascript.regexp.SubString interpretDollar(org.mozilla.javascript.Context r7, org.mozilla.javascript.regexp.RegExpImpl r8, java.lang.String r9, int r10, int[] r11) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 196
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.RegExpImpl.interpretDollar(org.mozilla.javascript.Context, org.mozilla.javascript.regexp.RegExpImpl, java.lang.String, int, int[]):org.mozilla.javascript.regexp.SubString");
    }

    private static Object matchOrReplace(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr, RegExpImpl regExpImpl, GlobData globData, NativeRegExp nativeRegExp) throws RuntimeException {
        String str = globData.str;
        boolean z2 = (nativeRegExp.getFlags() & 1) != 0;
        globData.global = z2;
        int[] iArr = {0};
        int i2 = globData.mode;
        if (i2 == 3) {
            Object objExecuteRegExp = nativeRegExp.executeRegExp(context, scriptable, regExpImpl, str, iArr, 0);
            if (objExecuteRegExp == null || !objExecuteRegExp.equals(Boolean.TRUE)) {
                return -1;
            }
            return Integer.valueOf(regExpImpl.leftContext.length);
        }
        if (!z2) {
            return nativeRegExp.executeRegExp(context, scriptable, regExpImpl, str, iArr, i2 == 2 ? 0 : 1);
        }
        nativeRegExp.lastIndex = Double.valueOf(0.0d);
        Object obj = null;
        int i3 = 0;
        while (iArr[0] <= str.length()) {
            Object objExecuteRegExp2 = nativeRegExp.executeRegExp(context, scriptable, regExpImpl, str, iArr, 0);
            if (objExecuteRegExp2 != null && objExecuteRegExp2.equals(Boolean.TRUE)) {
                int i4 = globData.mode;
                if (i4 == 1) {
                    match_glob(globData, context, scriptable, i3, regExpImpl);
                } else {
                    if (i4 != 2) {
                        Kit.codeBug();
                    }
                    SubString subString = regExpImpl.lastMatch;
                    int i5 = globData.leftIndex;
                    int i6 = subString.index;
                    globData.leftIndex = i6 + subString.length;
                    replace_glob(globData, context, scriptable, regExpImpl, i5, i6 - i5);
                }
                if (regExpImpl.lastMatch.length == 0) {
                    if (iArr[0] != str.length()) {
                        iArr[0] = iArr[0] + 1;
                    }
                }
                i3++;
                obj = objExecuteRegExp2;
            }
            return objExecuteRegExp2;
        }
        return obj;
    }

    private static void match_glob(GlobData globData, Context context, Scriptable scriptable, int i2, RegExpImpl regExpImpl) {
        if (globData.arrayobj == null) {
            globData.arrayobj = context.newArray(scriptable, 0);
        }
        String string = regExpImpl.lastMatch.toString();
        Scriptable scriptable2 = globData.arrayobj;
        scriptable2.put(i2, scriptable2, string);
    }

    private static void replace_glob(GlobData globData, Context context, Scriptable scriptable, RegExpImpl regExpImpl, int i2, int i3) throws RuntimeException {
        int length;
        String string;
        int i4;
        if (globData.lambda != null) {
            SubString[] subStringArr = regExpImpl.parens;
            int length2 = subStringArr == null ? 0 : subStringArr.length;
            Object[] objArr = new Object[length2 + 3];
            objArr[0] = regExpImpl.lastMatch.toString();
            for (int i5 = 0; i5 < length2; i5++) {
                SubString subString = subStringArr[i5];
                if (subString != null) {
                    objArr[i5 + 1] = subString.toString();
                } else {
                    objArr[i5 + 1] = Undefined.instance;
                }
            }
            objArr[length2 + 1] = Integer.valueOf(regExpImpl.leftContext.length);
            objArr[length2 + 2] = globData.str;
            if (regExpImpl != ScriptRuntime.getRegExpProxy(context)) {
                Kit.codeBug();
            }
            RegExpImpl regExpImpl2 = new RegExpImpl();
            regExpImpl2.multiline = regExpImpl.multiline;
            regExpImpl2.input = regExpImpl.input;
            ScriptRuntime.setRegExpProxy(context, regExpImpl2);
            try {
                Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
                string = ScriptRuntime.toString(globData.lambda.call(context, topLevelScope, topLevelScope, objArr));
                ScriptRuntime.setRegExpProxy(context, regExpImpl);
                length = string.length();
            } catch (Throwable th) {
                ScriptRuntime.setRegExpProxy(context, regExpImpl);
                throw th;
            }
        } else {
            length = globData.repstr.length();
            int iIndexOf = globData.dollar;
            if (iIndexOf >= 0) {
                int[] iArr = new int[1];
                do {
                    SubString subStringInterpretDollar = interpretDollar(context, regExpImpl, globData.repstr, iIndexOf, iArr);
                    if (subStringInterpretDollar != null) {
                        int i6 = subStringInterpretDollar.length;
                        int i7 = iArr[0];
                        length += i6 - i7;
                        i4 = iIndexOf + i7;
                    } else {
                        i4 = iIndexOf + 1;
                    }
                    iIndexOf = globData.repstr.indexOf(36, i4);
                } while (iIndexOf >= 0);
            }
            string = null;
        }
        int i8 = length + i3 + regExpImpl.rightContext.length;
        StringBuilder sb = globData.charBuf;
        if (sb == null) {
            sb = new StringBuilder(i8);
            globData.charBuf = sb;
        } else {
            sb.ensureCapacity(sb.length() + i8);
        }
        sb.append((CharSequence) regExpImpl.leftContext.str, i2, i3 + i2);
        if (globData.lambda != null) {
            sb.append(string);
        } else {
            do_replace(globData, context, regExpImpl);
        }
    }

    @Override // org.mozilla.javascript.RegExpProxy
    public Object action(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr, int i2) throws RuntimeException {
        String string;
        NativeRegExp nativeRegExpCreateRegExp;
        String string2;
        Function function;
        Object objMatchOrReplace;
        GlobData globData = new GlobData();
        globData.mode = i2;
        globData.str = ScriptRuntime.toString(scriptable2);
        if (i2 == 1) {
            Object objMatchOrReplace2 = matchOrReplace(context, scriptable, scriptable2, objArr, this, globData, createRegExp(context, scriptable, objArr, 1, false));
            Scriptable scriptable3 = globData.arrayobj;
            return scriptable3 == null ? objMatchOrReplace2 : scriptable3;
        }
        if (i2 != 2) {
            if (i2 == 3) {
                return matchOrReplace(context, scriptable, scriptable2, objArr, this, globData, createRegExp(context, scriptable, objArr, 1, false));
            }
            throw Kit.codeBug();
        }
        boolean z2 = (objArr.length > 0 && (objArr[0] instanceof NativeRegExp)) || objArr.length > 2;
        if (z2) {
            nativeRegExpCreateRegExp = createRegExp(context, scriptable, objArr, 2, true);
            string = null;
        } else {
            string = ScriptRuntime.toString(objArr.length < 1 ? Undefined.instance : objArr[0]);
            nativeRegExpCreateRegExp = null;
        }
        Object obj = objArr.length < 2 ? Undefined.instance : objArr[1];
        if (obj instanceof Function) {
            function = (Function) obj;
            string2 = null;
        } else {
            string2 = ScriptRuntime.toString(obj);
            function = null;
        }
        globData.lambda = function;
        globData.repstr = string2;
        globData.dollar = string2 == null ? -1 : string2.indexOf(36);
        globData.charBuf = null;
        globData.leftIndex = 0;
        if (z2) {
            objMatchOrReplace = matchOrReplace(context, scriptable, scriptable2, objArr, this, globData, nativeRegExpCreateRegExp);
        } else {
            String str = globData.str;
            int iIndexOf = str.indexOf(string);
            if (iIndexOf >= 0) {
                int length = string.length();
                this.lastParen = null;
                this.leftContext = new SubString(str, 0, iIndexOf);
                this.lastMatch = new SubString(str, iIndexOf, length);
                this.rightContext = new SubString(str, iIndexOf + length, (str.length() - iIndexOf) - length);
                objMatchOrReplace = Boolean.TRUE;
            } else {
                objMatchOrReplace = Boolean.FALSE;
            }
        }
        if (globData.charBuf == null) {
            if (globData.global || objMatchOrReplace == null || !objMatchOrReplace.equals(Boolean.TRUE)) {
                return globData.str;
            }
            SubString subString = this.leftContext;
            replace_glob(globData, context, scriptable, this, subString.index, subString.length);
        }
        SubString subString2 = this.rightContext;
        StringBuilder sb = globData.charBuf;
        String str2 = subString2.str;
        int i3 = subString2.index;
        sb.append((CharSequence) str2, i3, subString2.length + i3);
        return globData.charBuf.toString();
    }

    @Override // org.mozilla.javascript.RegExpProxy
    public Object compileRegExp(Context context, String str, String str2) {
        return NativeRegExp.compileRE(context, str, str2, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x004d, code lost:
    
        r0 = r0 - r1;
     */
    @Override // org.mozilla.javascript.RegExpProxy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int find_split(org.mozilla.javascript.Context r14, org.mozilla.javascript.Scriptable r15, java.lang.String r16, java.lang.String r17, org.mozilla.javascript.Scriptable r18, int[] r19, int[] r20, boolean[] r21, java.lang.String[][] r22) {
        /*
            r13 = this;
            r7 = r13
            r8 = 0
            r0 = r19[r8]
            int r9 = r16.length()
            int r10 = r14.getLanguageVersion()
            r11 = r18
            org.mozilla.javascript.regexp.NativeRegExp r11 = (org.mozilla.javascript.regexp.NativeRegExp) r11
        L10:
            r12 = r19[r8]
            r19[r8] = r0
            r6 = 0
            r0 = r11
            r1 = r14
            r2 = r15
            r3 = r13
            r4 = r16
            r5 = r19
            java.lang.Object r0 = r0.executeRegExp(r1, r2, r3, r4, r5, r6)
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r2 = 1
            if (r0 == r1) goto L2d
            r19[r8] = r12
            r20[r8] = r2
            r21[r8] = r8
            return r9
        L2d:
            r0 = r19[r8]
            r19[r8] = r12
            r21[r8] = r2
            org.mozilla.javascript.regexp.SubString r1 = r7.lastMatch
            int r1 = r1.length
            r20[r8] = r1
            if (r1 != 0) goto L4d
            r3 = r19[r8]
            if (r0 != r3) goto L4d
            if (r0 != r9) goto L4a
            r1 = 120(0x78, float:1.68E-43)
            if (r10 != r1) goto L48
            r20[r8] = r2
            goto L4e
        L48:
            r0 = -1
            goto L4e
        L4a:
            int r0 = r0 + 1
            goto L10
        L4d:
            int r0 = r0 - r1
        L4e:
            org.mozilla.javascript.regexp.SubString[] r1 = r7.parens
            if (r1 != 0) goto L54
            r1 = r8
            goto L55
        L54:
            int r1 = r1.length
        L55:
            java.lang.String[] r2 = new java.lang.String[r1]
            r22[r8] = r2
            r2 = r8
        L5a:
            if (r2 >= r1) goto L6b
            org.mozilla.javascript.regexp.SubString r3 = r13.getParenSubString(r2)
            r4 = r22[r8]
            java.lang.String r3 = r3.toString()
            r4[r2] = r3
            int r2 = r2 + 1
            goto L5a
        L6b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.RegExpImpl.find_split(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.String, java.lang.String, org.mozilla.javascript.Scriptable, int[], int[], boolean[], java.lang.String[][]):int");
    }

    SubString getParenSubString(int i2) {
        SubString subString;
        SubString[] subStringArr = this.parens;
        return (subStringArr == null || i2 >= subStringArr.length || (subString = subStringArr[i2]) == null) ? new SubString() : subString;
    }

    @Override // org.mozilla.javascript.RegExpProxy
    public boolean isRegExp(Scriptable scriptable) {
        return scriptable instanceof NativeRegExp;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0061  */
    @Override // org.mozilla.javascript.RegExpProxy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object js_split(org.mozilla.javascript.Context r29, org.mozilla.javascript.Scriptable r30, java.lang.String r31, java.lang.Object[] r32) {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.RegExpImpl.js_split(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.String, java.lang.Object[]):java.lang.Object");
    }

    @Override // org.mozilla.javascript.RegExpProxy
    public Scriptable wrapRegExp(Context context, Scriptable scriptable, Object obj) {
        return new NativeRegExp(scriptable, (RECompiled) obj);
    }

    private static int find_split(Context context, Scriptable scriptable, String str, String str2, int i2, RegExpProxy regExpProxy, Scriptable scriptable2, int[] iArr, int[] iArr2, boolean[] zArr, String[][] strArr) {
        int iIndexOf;
        int i3 = iArr[0];
        int length = str.length();
        if (i2 == 120 && scriptable2 == null && str2.length() == 1 && str2.charAt(0) == ' ') {
            if (i3 == 0) {
                while (i3 < length && Character.isWhitespace(str.charAt(i3))) {
                    i3++;
                }
                iArr[0] = i3;
            }
            if (i3 == length) {
                return -1;
            }
            while (i3 < length && !Character.isWhitespace(str.charAt(i3))) {
                i3++;
            }
            int i4 = i3;
            while (i4 < length && Character.isWhitespace(str.charAt(i4))) {
                i4++;
            }
            iArr2[0] = i4 - i3;
            return i3;
        }
        if (i3 > length) {
            return -1;
        }
        if (scriptable2 != null) {
            return regExpProxy.find_split(context, scriptable, str, str2, scriptable2, iArr, iArr2, zArr, strArr);
        }
        if (i2 != 0 && i2 < 130 && length == 0) {
            return -1;
        }
        if (str2.length() != 0) {
            int i5 = iArr[0];
            return (i5 < length && (iIndexOf = str.indexOf(str2, i5)) != -1) ? iIndexOf : length;
        }
        if (i2 != 120) {
            if (i3 == length) {
                return -1;
            }
            return i3 + 1;
        }
        if (i3 != length) {
            return i3 + 1;
        }
        iArr2[0] = 1;
        return i3;
    }
}
