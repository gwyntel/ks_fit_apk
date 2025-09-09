package org.mozilla.javascript.regexp;

import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
public class NativeRegExp extends IdScriptableObject implements Function {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ANCHOR_BOL = -2;
    private static final int INDEX_LEN = 2;
    private static final int Id_compile = 1;
    private static final int Id_exec = 4;
    private static final int Id_global = 3;
    private static final int Id_ignoreCase = 4;
    private static final int Id_lastIndex = 1;
    private static final int Id_multiline = 5;
    private static final int Id_prefix = 6;
    private static final int Id_source = 2;
    private static final int Id_test = 5;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    public static final int JSREG_FOLD = 2;
    public static final int JSREG_GLOB = 1;
    public static final int JSREG_MULTILINE = 4;
    public static final int MATCH = 1;
    private static final int MAX_INSTANCE_ID = 5;
    private static final int MAX_PROTOTYPE_ID = 6;
    public static final int PREFIX = 2;
    private static final Object REGEXP_TAG = new Object();
    private static final byte REOP_ALNUM = 9;
    private static final byte REOP_ALT = 31;
    private static final byte REOP_ALTPREREQ = 53;
    private static final byte REOP_ALTPREREQ2 = 55;
    private static final byte REOP_ALTPREREQi = 54;
    private static final byte REOP_ASSERT = 41;
    private static final byte REOP_ASSERTNOTTEST = 44;
    private static final byte REOP_ASSERTTEST = 43;
    private static final byte REOP_ASSERT_NOT = 42;
    private static final byte REOP_BACKREF = 13;
    private static final byte REOP_BOL = 2;
    private static final byte REOP_CLASS = 22;
    private static final byte REOP_DIGIT = 7;
    private static final byte REOP_DOT = 6;
    private static final byte REOP_EMPTY = 1;
    private static final byte REOP_END = 57;
    private static final byte REOP_ENDCHILD = 49;
    private static final byte REOP_EOL = 3;
    private static final byte REOP_FLAT = 14;
    private static final byte REOP_FLAT1 = 15;
    private static final byte REOP_FLAT1i = 17;
    private static final byte REOP_FLATi = 16;
    private static final byte REOP_JUMP = 32;
    private static final byte REOP_LPAREN = 29;
    private static final byte REOP_MINIMALOPT = 47;
    private static final byte REOP_MINIMALPLUS = 46;
    private static final byte REOP_MINIMALQUANT = 48;
    private static final byte REOP_MINIMALREPEAT = 52;
    private static final byte REOP_MINIMALSTAR = 45;
    private static final byte REOP_NCLASS = 23;
    private static final byte REOP_NONALNUM = 10;
    private static final byte REOP_NONDIGIT = 8;
    private static final byte REOP_NONSPACE = 12;
    private static final byte REOP_OPT = 28;
    private static final byte REOP_PLUS = 27;
    private static final byte REOP_QUANT = 25;
    private static final byte REOP_REPEAT = 51;
    private static final byte REOP_RPAREN = 30;
    private static final byte REOP_SIMPLE_END = 23;
    private static final byte REOP_SIMPLE_START = 1;
    private static final byte REOP_SPACE = 11;
    private static final byte REOP_STAR = 26;
    private static final byte REOP_UCFLAT1 = 18;
    private static final byte REOP_UCFLAT1i = 19;
    private static final byte REOP_WBDRY = 4;
    private static final byte REOP_WNONBDRY = 5;
    public static final int TEST = 0;
    private static final boolean debug = false;
    static final long serialVersionUID = 4965263491464903264L;
    Object lastIndex;
    private int lastIndexAttr;
    private RECompiled re;

    NativeRegExp(Scriptable scriptable, RECompiled rECompiled) {
        Double dValueOf = Double.valueOf(0.0d);
        this.lastIndex = dValueOf;
        this.lastIndexAttr = 6;
        this.re = rECompiled;
        this.lastIndex = dValueOf;
        ScriptRuntime.setBuiltinProtoAndParent(this, scriptable, TopLevel.Builtins.RegExp);
    }

    private static void addCharacterRangeToCharSet(RECharSet rECharSet, char c2, char c3) {
        int i2 = c2 / '\b';
        int i3 = c3 / '\b';
        if (c3 >= rECharSet.length || c2 > c3) {
            throw ScriptRuntime.constructError("SyntaxError", "invalid range in character class");
        }
        char c4 = (char) (c2 & 7);
        char c5 = (char) (c3 & 7);
        if (i2 == i3) {
            byte[] bArr = rECharSet.bits;
            bArr[i2] = (byte) (((255 >> (7 - (c5 - c4))) << c4) | bArr[i2]);
            return;
        }
        byte[] bArr2 = rECharSet.bits;
        bArr2[i2] = (byte) ((255 << c4) | bArr2[i2]);
        while (true) {
            i2++;
            if (i2 >= i3) {
                byte[] bArr3 = rECharSet.bits;
                bArr3[i3] = (byte) (bArr3[i3] | (255 >> (7 - c5)));
                return;
            }
            rECharSet.bits[i2] = -1;
        }
    }

    private static void addCharacterToCharSet(RECharSet rECharSet, char c2) {
        int i2 = c2 / '\b';
        if (c2 >= rECharSet.length) {
            throw ScriptRuntime.constructError("SyntaxError", "invalid range in character class");
        }
        byte[] bArr = rECharSet.bits;
        bArr[i2] = (byte) ((1 << (c2 & 7)) | bArr[i2]);
    }

    private static int addIndex(byte[] bArr, int i2, int i3) {
        if (i3 < 0) {
            throw Kit.codeBug();
        }
        if (i3 > 65535) {
            throw Context.reportRuntimeError("Too complex regexp");
        }
        bArr[i2] = (byte) (i3 >> 8);
        bArr[i2 + 1] = (byte) i3;
        return i2 + 2;
    }

    private static boolean backrefMatcher(REGlobalData rEGlobalData, int i2, String str, int i3) {
        long[] jArr = rEGlobalData.parens;
        if (jArr == null || i2 >= jArr.length) {
            return false;
        }
        int iParensIndex = rEGlobalData.parensIndex(i2);
        if (iParensIndex == -1) {
            return true;
        }
        int iParensLength = rEGlobalData.parensLength(i2);
        int i4 = rEGlobalData.cp;
        if (i4 + iParensLength > i3) {
            return false;
        }
        if ((rEGlobalData.regexp.flags & 2) != 0) {
            for (int i5 = 0; i5 < iParensLength; i5++) {
                char cCharAt = str.charAt(iParensIndex + i5);
                char cCharAt2 = str.charAt(rEGlobalData.cp + i5);
                if (cCharAt != cCharAt2 && upcase(cCharAt) != upcase(cCharAt2)) {
                    return false;
                }
            }
        } else if (!str.regionMatches(iParensIndex, str, i4, iParensLength)) {
            return false;
        }
        rEGlobalData.cp += iParensLength;
        return true;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:31:0x005b
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:195)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:62)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:124)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v10, types: [int] */
    /* JADX WARN: Type inference failed for: r14v11, types: [int] */
    /* JADX WARN: Type inference failed for: r14v7, types: [int] */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r14v9 */
    /* JADX WARN: Type inference failed for: r8v6, types: [int] */
    /* JADX WARN: Type inference failed for: r8v8, types: [int] */
    private static boolean calculateBitmapSize(org.mozilla.javascript.regexp.CompilerState r16, org.mozilla.javascript.regexp.RENode r17, char[] r18, int r19, int r20) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.calculateBitmapSize(org.mozilla.javascript.regexp.CompilerState, org.mozilla.javascript.regexp.RENode, char[], int, int):boolean");
    }

    private static boolean classMatcher(REGlobalData rEGlobalData, RECharSet rECharSet, char c2) {
        if (!rECharSet.converted) {
            processCharSet(rEGlobalData, rECharSet);
        }
        int i2 = c2 >> 3;
        int i3 = rECharSet.length;
        boolean z2 = true;
        if (i3 != 0 && c2 < i3 && (rECharSet.bits[i2] & (1 << (c2 & 7))) != 0) {
            z2 = false;
        }
        return rECharSet.sense ^ z2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static RECompiled compileRE(Context context, String str, String str2, boolean z2) {
        int i2;
        int i3;
        RECompiled rECompiled = new RECompiled(str);
        int length = str.length();
        if (str2 != null) {
            i2 = 0;
            for (int i4 = 0; i4 < str2.length(); i4++) {
                char cCharAt = str2.charAt(i4);
                if (cCharAt == 'g') {
                    i3 = 1;
                } else if (cCharAt == 'i') {
                    i3 = 2;
                } else if (cCharAt == 'm') {
                    i3 = 4;
                } else {
                    reportError("msg.invalid.re.flag", String.valueOf(cCharAt));
                    i3 = 0;
                }
                if ((i2 & i3) != 0) {
                    reportError("msg.invalid.re.flag", String.valueOf(cCharAt));
                }
                i2 |= i3;
            }
        } else {
            i2 = 0;
        }
        rECompiled.flags = i2;
        CompilerState compilerState = new CompilerState(context, rECompiled.source, length, i2);
        if (z2 && length > 0) {
            RENode rENode = new RENode((byte) 14);
            compilerState.result = rENode;
            rENode.chr = compilerState.cpbegin[0];
            rENode.length = length;
            rENode.flatIndex = 0;
            compilerState.progLength += 5;
        } else {
            if (!parseDisjunction(compilerState)) {
                return null;
            }
            if (compilerState.maxBackReference > compilerState.parenCount) {
                compilerState = new CompilerState(context, rECompiled.source, length, i2);
                compilerState.backReferenceLimit = compilerState.parenCount;
                if (!parseDisjunction(compilerState)) {
                    return null;
                }
            }
        }
        rECompiled.program = new byte[compilerState.progLength + 1];
        int i5 = compilerState.classCount;
        if (i5 != 0) {
            rECompiled.classList = new RECharSet[i5];
            rECompiled.classCount = i5;
        }
        int iEmitREBytecode = emitREBytecode(compilerState, rECompiled, 0, compilerState.result);
        byte[] bArr = rECompiled.program;
        bArr[iEmitREBytecode] = REOP_END;
        rECompiled.parenCount = compilerState.parenCount;
        byte b2 = bArr[0];
        if (b2 == 2) {
            rECompiled.anchorCh = -2;
        } else if (b2 != 31) {
            switch (b2) {
                case 14:
                case 16:
                    rECompiled.anchorCh = rECompiled.source[getIndex(bArr, 1)];
                    break;
                case 15:
                case 17:
                    rECompiled.anchorCh = (char) (bArr[1] & 255);
                    break;
                case 18:
                case 19:
                    rECompiled.anchorCh = (char) getIndex(bArr, 1);
                    break;
            }
        } else {
            RENode rENode2 = compilerState.result;
            if (rENode2.kid.op == 2 && rENode2.kid2.op == 2) {
                rECompiled.anchorCh = -2;
            }
        }
        return rECompiled;
    }

    private static void doFlat(CompilerState compilerState, char c2) {
        RENode rENode = new RENode((byte) 14);
        compilerState.result = rENode;
        rENode.chr = c2;
        rENode.length = 1;
        rENode.flatIndex = -1;
        compilerState.progLength += 3;
    }

    private static char downcase(char c2) {
        if (c2 < 128) {
            return ('A' > c2 || c2 > 'Z') ? c2 : (char) (c2 + ' ');
        }
        char lowerCase = Character.toLowerCase(c2);
        return lowerCase < 128 ? c2 : lowerCase;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:200)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:124)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private static int emitREBytecode(org.mozilla.javascript.regexp.CompilerState r9, org.mozilla.javascript.regexp.RECompiled r10, int r11, org.mozilla.javascript.regexp.RENode r12) {
        /*
            Method dump skipped, instructions count: 468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.emitREBytecode(org.mozilla.javascript.regexp.CompilerState, org.mozilla.javascript.regexp.RECompiled, int, org.mozilla.javascript.regexp.RENode):int");
    }

    private static String escapeRegExp(Object obj) {
        String string = ScriptRuntime.toString(obj);
        StringBuilder sb = null;
        int i2 = 0;
        for (int iIndexOf = string.indexOf(47); iIndexOf > -1; iIndexOf = string.indexOf(47, iIndexOf + 1)) {
            if (iIndexOf == i2 || string.charAt(iIndexOf - 1) != '\\') {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append((CharSequence) string, i2, iIndexOf);
                sb.append("\\/");
                i2 = iIndexOf + 1;
            }
        }
        if (sb == null) {
            return string;
        }
        sb.append((CharSequence) string, i2, string.length());
        return sb.toString();
    }

    private Object execSub(Context context, Scriptable scriptable, Object[] objArr, int i2) {
        String string;
        RegExpImpl impl = getImpl(context);
        if (objArr.length == 0) {
            string = impl.input;
            if (string == null) {
                string = ScriptRuntime.toString(Undefined.instance);
            }
        } else {
            string = ScriptRuntime.toString(objArr[0]);
        }
        String str = string;
        double d2 = 0.0d;
        double integer = (this.re.flags & 1) != 0 ? ScriptRuntime.toInteger(this.lastIndex) : 0.0d;
        if (integer < 0.0d || str.length() < integer) {
            this.lastIndex = Double.valueOf(0.0d);
            return null;
        }
        int[] iArr = {(int) integer};
        Object objExecuteRegExp = executeRegExp(context, scriptable, impl, str, iArr, i2);
        if ((this.re.flags & 1) == 0) {
            return objExecuteRegExp;
        }
        if (objExecuteRegExp != null && objExecuteRegExp != Undefined.instance) {
            d2 = iArr[0];
        }
        this.lastIndex = Double.valueOf(d2);
        return objExecuteRegExp;
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x0297, code lost:
    
        if (r9[r0] == 44) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ac, code lost:
    
        if (classMatcher(r20, r20.regexp.classList[r1], r3) == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b9, code lost:
    
        if (r3 != r1) goto L35;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:155:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0386  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x039c  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x03a1  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x03c7  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x03d7  */
    /* JADX WARN: Type inference failed for: r0v9, types: [boolean] */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v13 */
    /* JADX WARN: Type inference failed for: r15v14 */
    /* JADX WARN: Type inference failed for: r15v15 */
    /* JADX WARN: Type inference failed for: r15v16 */
    /* JADX WARN: Type inference failed for: r15v17 */
    /* JADX WARN: Type inference failed for: r15v19, types: [int] */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v20 */
    /* JADX WARN: Type inference failed for: r15v22 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v4, types: [int] */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r2v4, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean executeREBytecode(org.mozilla.javascript.regexp.REGlobalData r20, java.lang.String r21, int r22) {
        /*
            Method dump skipped, instructions count: 1100
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.executeREBytecode(org.mozilla.javascript.regexp.REGlobalData, java.lang.String, int):boolean");
    }

    private static boolean flatNIMatcher(REGlobalData rEGlobalData, int i2, int i3, String str, int i4) {
        if (rEGlobalData.cp + i3 > i4) {
            return false;
        }
        char[] cArr = rEGlobalData.regexp.source;
        for (int i5 = 0; i5 < i3; i5++) {
            char c2 = cArr[i2 + i5];
            char cCharAt = str.charAt(rEGlobalData.cp + i5);
            if (c2 != cCharAt && upcase(c2) != upcase(cCharAt)) {
                return false;
            }
        }
        rEGlobalData.cp += i3;
        return true;
    }

    private static boolean flatNMatcher(REGlobalData rEGlobalData, int i2, int i3, String str, int i4) {
        if (rEGlobalData.cp + i3 > i4) {
            return false;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            if (rEGlobalData.regexp.source[i2 + i5] != str.charAt(rEGlobalData.cp + i5)) {
                return false;
            }
        }
        rEGlobalData.cp += i3;
        return true;
    }

    private static int getDecimalValue(char c2, CompilerState compilerState, int i2, String str) {
        int i3 = compilerState.cp;
        char[] cArr = compilerState.cpbegin;
        int i4 = c2 - 48;
        boolean z2 = false;
        while (true) {
            int i5 = compilerState.cp;
            if (i5 == compilerState.cpend) {
                break;
            }
            char c3 = cArr[i5];
            if (!isDigit(c3)) {
                break;
            }
            if (!z2 && (i4 = (i4 * 10) + (c3 - '0')) >= i2) {
                i4 = i2;
                z2 = true;
            }
            compilerState.cp++;
        }
        if (z2) {
            reportError(str, String.valueOf(cArr, i3, compilerState.cp - i3));
        }
        return i4;
    }

    private static RegExpImpl getImpl(Context context) {
        return (RegExpImpl) ScriptRuntime.getRegExpProxy(context);
    }

    private static int getIndex(byte[] bArr, int i2) {
        return (bArr[i2 + 1] & 255) | ((bArr[i2] & 255) << 8);
    }

    private static int getOffset(byte[] bArr, int i2) {
        return getIndex(bArr, i2);
    }

    public static void init(Context context, Scriptable scriptable, boolean z2) {
        NativeRegExp nativeRegExp = new NativeRegExp();
        nativeRegExp.re = compileRE(context, "", null, false);
        nativeRegExp.activatePrototypeMap(6);
        nativeRegExp.setParentScope(scriptable);
        nativeRegExp.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        NativeRegExpCtor nativeRegExpCtor = new NativeRegExpCtor();
        nativeRegExp.defineProperty("constructor", nativeRegExpCtor, 2);
        ScriptRuntime.setFunctionProtoAndParent(nativeRegExpCtor, scriptable);
        nativeRegExpCtor.setImmunePrototypeProperty(nativeRegExp);
        if (z2) {
            nativeRegExp.sealObject();
            nativeRegExpCtor.sealObject();
        }
        ScriptableObject.defineProperty(scriptable, "RegExp", nativeRegExpCtor, 2);
    }

    private static boolean isControlLetter(char c2) {
        return ('a' <= c2 && c2 <= 'z') || ('A' <= c2 && c2 <= 'Z');
    }

    static boolean isDigit(char c2) {
        return '0' <= c2 && c2 <= '9';
    }

    private static boolean isLineTerm(char c2) {
        return ScriptRuntime.isJSLineTerminator(c2);
    }

    private static boolean isREWhiteSpace(int i2) {
        return ScriptRuntime.isJSWhitespaceOrLineTerminator(i2);
    }

    private static boolean isWord(char c2) {
        return ('a' <= c2 && c2 <= 'z') || ('A' <= c2 && c2 <= 'Z') || isDigit(c2) || c2 == '_';
    }

    private static boolean matchRegExp(REGlobalData rEGlobalData, RECompiled rECompiled, String str, int i2, int i3, boolean z2) {
        int i4 = rECompiled.parenCount;
        if (i4 != 0) {
            rEGlobalData.parens = new long[i4];
        } else {
            rEGlobalData.parens = null;
        }
        rEGlobalData.backTrackStackTop = null;
        rEGlobalData.stateStackTop = null;
        rEGlobalData.multiline = z2 || (rECompiled.flags & 4) != 0;
        rEGlobalData.regexp = rECompiled;
        int i5 = rECompiled.anchorCh;
        int i6 = i2;
        while (i6 <= i3) {
            if (i5 >= 0) {
                while (i6 != i3) {
                    char cCharAt = str.charAt(i6);
                    if (cCharAt != i5 && ((rEGlobalData.regexp.flags & 2) == 0 || upcase(cCharAt) != upcase((char) i5))) {
                        i6++;
                    }
                }
                return false;
            }
            rEGlobalData.cp = i6;
            rEGlobalData.skipped = i6 - i2;
            for (int i7 = 0; i7 < rECompiled.parenCount; i7++) {
                rEGlobalData.parens[i7] = -1;
            }
            boolean zExecuteREBytecode = executeREBytecode(rEGlobalData, str, i3);
            rEGlobalData.backTrackStackTop = null;
            rEGlobalData.stateStackTop = null;
            if (zExecuteREBytecode) {
                return true;
            }
            if (i5 == -2 && !rEGlobalData.multiline) {
                rEGlobalData.skipped = i3;
                return false;
            }
            i6 = rEGlobalData.skipped + i2 + 1;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0032, code lost:
    
        if (r1 != null) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0034, code lost:
    
        r5.result = new org.mozilla.javascript.regexp.RENode((byte) 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003c, code lost:
    
        r5.result = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x003e, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean parseAlternative(org.mozilla.javascript.regexp.CompilerState r5) {
        /*
            char[] r0 = r5.cpbegin
            r1 = 0
            r2 = r1
        L4:
            int r3 = r5.cp
            int r4 = r5.cpend
            if (r3 == r4) goto L31
            char r3 = r0[r3]
            r4 = 124(0x7c, float:1.74E-43)
            if (r3 == r4) goto L31
            int r4 = r5.parenNesting
            if (r4 == 0) goto L19
            r4 = 41
            if (r3 != r4) goto L19
            goto L31
        L19:
            boolean r3 = parseTerm(r5)
            if (r3 != 0) goto L21
            r5 = 0
            return r5
        L21:
            if (r1 != 0) goto L27
            org.mozilla.javascript.regexp.RENode r1 = r5.result
            r2 = r1
            goto L2b
        L27:
            org.mozilla.javascript.regexp.RENode r3 = r5.result
            r2.next = r3
        L2b:
            org.mozilla.javascript.regexp.RENode r3 = r2.next
            if (r3 == 0) goto L4
            r2 = r3
            goto L2b
        L31:
            r0 = 1
            if (r1 != 0) goto L3c
            org.mozilla.javascript.regexp.RENode r1 = new org.mozilla.javascript.regexp.RENode
            r1.<init>(r0)
            r5.result = r1
            goto L3e
        L3c:
            r5.result = r1
        L3e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.parseAlternative(org.mozilla.javascript.regexp.CompilerState):boolean");
    }

    private static boolean parseDisjunction(CompilerState compilerState) {
        int i2;
        int i3;
        if (!parseAlternative(compilerState)) {
            return false;
        }
        char[] cArr = compilerState.cpbegin;
        int i4 = compilerState.cp;
        if (i4 != cArr.length && cArr[i4] == '|') {
            compilerState.cp = i4 + 1;
            RENode rENode = new RENode((byte) 31);
            rENode.kid = compilerState.result;
            if (!parseDisjunction(compilerState)) {
                return false;
            }
            RENode rENode2 = compilerState.result;
            rENode.kid2 = rENode2;
            compilerState.result = rENode;
            RENode rENode3 = rENode.kid;
            byte b2 = rENode3.op;
            if (b2 == 14 && rENode2.op == 14) {
                rENode.op = (compilerState.flags & 2) == 0 ? (byte) 53 : (byte) 54;
                rENode.chr = rENode3.chr;
                rENode.index = rENode2.chr;
                compilerState.progLength += 13;
            } else if (b2 == 22 && (i3 = rENode3.index) < 256 && rENode2.op == 14 && (compilerState.flags & 2) == 0) {
                rENode.op = (byte) 55;
                rENode.chr = rENode2.chr;
                rENode.index = i3;
                compilerState.progLength += 13;
            } else if (b2 == 14 && rENode2.op == 22 && (i2 = rENode2.index) < 256 && (compilerState.flags & 2) == 0) {
                rENode.op = (byte) 55;
                rENode.chr = rENode3.chr;
                rENode.index = i2;
                compilerState.progLength += 13;
            } else {
                compilerState.progLength += 9;
            }
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x039b  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x03df A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:195:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x015d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean parseTerm(org.mozilla.javascript.regexp.CompilerState r16) {
        /*
            Method dump skipped, instructions count: 1106
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.parseTerm(org.mozilla.javascript.regexp.CompilerState):boolean");
    }

    private static REProgState popProgState(REGlobalData rEGlobalData) {
        REProgState rEProgState = rEGlobalData.stateStackTop;
        rEGlobalData.stateStackTop = rEProgState.previous;
        return rEProgState;
    }

    private static void processCharSet(REGlobalData rEGlobalData, RECharSet rECharSet) {
        synchronized (rECharSet) {
            try {
                if (!rECharSet.converted) {
                    processCharSetImpl(rEGlobalData, rECharSet);
                    rECharSet.converted = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:38:0x0076
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:195)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:62)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:124)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    /* JADX WARN: Failed to find 'out' block for switch in B:24:0x0050. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x0053. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:26:0x0056. Please report as an issue. */
    private static void processCharSetImpl(org.mozilla.javascript.regexp.REGlobalData r14, org.mozilla.javascript.regexp.RECharSet r15) {
        /*
            Method dump skipped, instructions count: 452
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.processCharSetImpl(org.mozilla.javascript.regexp.REGlobalData, org.mozilla.javascript.regexp.RECharSet):void");
    }

    private static void pushBackTrackState(REGlobalData rEGlobalData, byte b2, int i2) {
        REProgState rEProgState = rEGlobalData.stateStackTop;
        rEGlobalData.backTrackStackTop = new REBackTrackData(rEGlobalData, b2, i2, rEGlobalData.cp, rEProgState.continuationOp, rEProgState.continuationPc);
    }

    private static void pushProgState(REGlobalData rEGlobalData, int i2, int i3, int i4, REBackTrackData rEBackTrackData, int i5, int i6) {
        rEGlobalData.stateStackTop = new REProgState(rEGlobalData.stateStackTop, i2, i3, i4, rEBackTrackData, i5, i6);
    }

    private static NativeRegExp realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeRegExp) {
            return (NativeRegExp) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    private static boolean reopIsSimple(int i2) {
        return i2 >= 1 && i2 <= 23;
    }

    private static void reportError(String str, String str2) {
        throw ScriptRuntime.constructError("SyntaxError", ScriptRuntime.getMessage1(str, str2));
    }

    private static void reportWarning(Context context, String str, String str2) {
        if (context.hasFeature(11)) {
            Context.reportWarning(ScriptRuntime.getMessage1(str, str2));
        }
    }

    private static void resolveForwardJump(byte[] bArr, int i2, int i3) {
        if (i2 > i3) {
            throw Kit.codeBug();
        }
        addIndex(bArr, i2, i3 - i2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002d A[PHI: r7
      0x002d: PHI (r7v9 int) = 
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v5 int)
      (r7v7 int)
      (r7v7 int)
      (r7v8 int)
      (r7v8 int)
      (r7v10 int)
      (r7v10 int)
     binds: [B:103:0x01b4, B:105:0x01c0, B:97:0x01a3, B:99:0x01ad, B:67:0x0147, B:69:0x0151, B:63:0x0134, B:65:0x013e, B:59:0x0121, B:61:0x012b, B:55:0x010e, B:57:0x0118, B:51:0x00fb, B:53:0x0105, B:47:0x00e8, B:49:0x00f2, B:43:0x00d5, B:45:0x00df, B:34:0x008e, B:20:0x005b, B:22:0x0061, B:13:0x0039, B:17:0x0049, B:7:0x0014, B:9:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x017b A[PHI: r5
      0x017b: PHI (r5v10 boolean) = (r5v5 boolean), (r5v13 boolean), (r5v13 boolean) binds: [B:93:0x019c, B:78:0x016e, B:80:0x0178] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int simpleMatch(org.mozilla.javascript.regexp.REGlobalData r3, java.lang.String r4, int r5, byte[] r6, int r7, int r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 514
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.simpleMatch(org.mozilla.javascript.regexp.REGlobalData, java.lang.String, int, byte[], int, int, boolean):int");
    }

    private static int toASCIIHexDigit(int i2) {
        if (i2 < 48) {
            return -1;
        }
        if (i2 <= 57) {
            return i2 - 48;
        }
        int i3 = i2 | 32;
        if (97 > i3 || i3 > 102) {
            return -1;
        }
        return i3 - 87;
    }

    private static char upcase(char c2) {
        if (c2 < 128) {
            return ('a' > c2 || c2 > 'z') ? c2 : (char) (c2 - ' ');
        }
        char upperCase = Character.toUpperCase(c2);
        return upperCase < 128 ? c2 : upperCase;
    }

    @Override // org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        return execSub(context, scriptable, objArr, 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    org.mozilla.javascript.Scriptable compile(org.mozilla.javascript.Context r4, org.mozilla.javascript.Scriptable r5, java.lang.Object[] r6) {
        /*
            r3 = this;
            int r5 = r6.length
            r0 = 0
            r1 = 1
            if (r5 <= 0) goto L27
            r5 = r6[r0]
            boolean r2 = r5 instanceof org.mozilla.javascript.regexp.NativeRegExp
            if (r2 == 0) goto L27
            int r4 = r6.length
            if (r4 <= r1) goto L1c
            r4 = r6[r1]
            java.lang.Object r6 = org.mozilla.javascript.Undefined.instance
            if (r4 != r6) goto L15
            goto L1c
        L15:
            java.lang.String r4 = "msg.bad.regexp.compile"
            org.mozilla.javascript.EcmaError r4 = org.mozilla.javascript.ScriptRuntime.typeError0(r4)
            throw r4
        L1c:
            org.mozilla.javascript.regexp.NativeRegExp r5 = (org.mozilla.javascript.regexp.NativeRegExp) r5
            org.mozilla.javascript.regexp.RECompiled r4 = r5.re
            r3.re = r4
            java.lang.Object r4 = r5.lastIndex
            r3.lastIndex = r4
            return r3
        L27:
            int r5 = r6.length
            if (r5 == 0) goto L36
            r5 = r6[r0]
            boolean r2 = r5 instanceof org.mozilla.javascript.Undefined
            if (r2 == 0) goto L31
            goto L36
        L31:
            java.lang.String r5 = escapeRegExp(r5)
            goto L38
        L36:
            java.lang.String r5 = ""
        L38:
            int r2 = r6.length
            if (r2 <= r1) goto L46
            r6 = r6[r1]
            java.lang.Object r1 = org.mozilla.javascript.Undefined.instance
            if (r6 == r1) goto L46
            java.lang.String r6 = org.mozilla.javascript.ScriptRuntime.toString(r6)
            goto L47
        L46:
            r6 = 0
        L47:
            org.mozilla.javascript.regexp.RECompiled r4 = compileRE(r4, r5, r6, r0)
            r3.re = r4
            r4 = 0
            java.lang.Double r4 = java.lang.Double.valueOf(r4)
            r3.lastIndex = r4
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.compile(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[]):org.mozilla.javascript.Scriptable");
    }

    @Override // org.mozilla.javascript.Function
    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        return (Scriptable) execSub(context, scriptable, objArr, 1);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(REGEXP_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        switch (iMethodId) {
            case 1:
                return realThis(scriptable2, idFunctionObject).compile(context, scriptable, objArr);
            case 2:
            case 3:
                return realThis(scriptable2, idFunctionObject).toString();
            case 4:
                return realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 1);
            case 5:
                Object objExecSub = realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 0);
                Boolean bool = Boolean.TRUE;
                return bool.equals(objExecSub) ? bool : Boolean.FALSE;
            case 6:
                return realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 2);
            default:
                throw new IllegalArgumentException(String.valueOf(iMethodId));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.lang.Boolean] */
    Object executeRegExp(Context context, Scriptable scriptable, RegExpImpl regExpImpl, String str, int[] iArr, int i2) {
        Scriptable scriptableNewArray;
        Scriptable scriptable2;
        NativeRegExp nativeRegExp = this;
        REGlobalData rEGlobalData = new REGlobalData();
        int i3 = iArr[0];
        int length = str.length();
        int i4 = i3 > length ? length : i3;
        SubString subString = null;
        if (!matchRegExp(rEGlobalData, nativeRegExp.re, str, i4, length, regExpImpl.multiline)) {
            if (i2 != 2) {
                return null;
            }
            return Undefined.instance;
        }
        int i5 = rEGlobalData.cp;
        iArr[0] = i5;
        int i6 = i5 - (rEGlobalData.skipped + i4);
        int i7 = i5 - i6;
        if (i2 == 0) {
            scriptable2 = null;
            scriptableNewArray = Boolean.TRUE;
        } else {
            scriptableNewArray = context.newArray(scriptable, 0);
            scriptableNewArray.put(0, scriptableNewArray, str.substring(i7, i7 + i6));
            scriptable2 = scriptableNewArray;
        }
        int i8 = nativeRegExp.re.parenCount;
        if (i8 == 0) {
            regExpImpl.parens = null;
            regExpImpl.lastParen = new SubString();
        } else {
            regExpImpl.parens = new SubString[i8];
            int i9 = 0;
            while (i9 < nativeRegExp.re.parenCount) {
                int iParensIndex = rEGlobalData.parensIndex(i9);
                if (iParensIndex != -1) {
                    subString = new SubString(str, iParensIndex, rEGlobalData.parensLength(i9));
                    regExpImpl.parens[i9] = subString;
                    if (i2 != 0) {
                        scriptable2.put(i9 + 1, scriptable2, subString.toString());
                    }
                } else if (i2 != 0) {
                    scriptable2.put(i9 + 1, scriptable2, Undefined.instance);
                }
                i9++;
                nativeRegExp = this;
            }
            regExpImpl.lastParen = subString;
        }
        if (i2 != 0) {
            scriptable2.put(FirebaseAnalytics.Param.INDEX, scriptable2, Integer.valueOf(rEGlobalData.skipped + i4));
            scriptable2.put("input", scriptable2, str);
        }
        if (regExpImpl.lastMatch == null) {
            regExpImpl.lastMatch = new SubString();
            regExpImpl.leftContext = new SubString();
            regExpImpl.rightContext = new SubString();
        }
        SubString subString2 = regExpImpl.lastMatch;
        subString2.str = str;
        subString2.index = i7;
        subString2.length = i6;
        regExpImpl.leftContext.str = str;
        if (context.getLanguageVersion() == 120) {
            SubString subString3 = regExpImpl.leftContext;
            subString3.index = i4;
            subString3.length = rEGlobalData.skipped;
        } else {
            SubString subString4 = regExpImpl.leftContext;
            subString4.index = 0;
            subString4.length = i4 + rEGlobalData.skipped;
        }
        SubString subString5 = regExpImpl.rightContext;
        subString5.str = str;
        subString5.index = i5;
        subString5.length = length - i5;
        return scriptableNewArray;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0041  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findInstanceIdInfo(java.lang.String r9) {
        /*
            r8 = this;
            int r0 = r9.length()
            r1 = 6
            r2 = 4
            r3 = 5
            r4 = 1
            r5 = 2
            r6 = 3
            r7 = 0
            if (r0 != r1) goto L21
            char r0 = r9.charAt(r7)
            r1 = 103(0x67, float:1.44E-43)
            if (r0 != r1) goto L19
            java.lang.String r0 = "global"
            r1 = r6
            goto L43
        L19:
            r1 = 115(0x73, float:1.61E-43)
            if (r0 != r1) goto L41
            java.lang.String r0 = "source"
            r1 = r5
            goto L43
        L21:
            r1 = 9
            if (r0 != r1) goto L39
            char r0 = r9.charAt(r7)
            r1 = 108(0x6c, float:1.51E-43)
            if (r0 != r1) goto L31
            java.lang.String r0 = "lastIndex"
            r1 = r4
            goto L43
        L31:
            r1 = 109(0x6d, float:1.53E-43)
            if (r0 != r1) goto L41
            java.lang.String r0 = "multiline"
            r1 = r3
            goto L43
        L39:
            r1 = 10
            if (r0 != r1) goto L41
            java.lang.String r0 = "ignoreCase"
            r1 = r2
            goto L43
        L41:
            r0 = 0
            r1 = r7
        L43:
            if (r0 == 0) goto L4e
            if (r0 == r9) goto L4e
            boolean r0 = r0.equals(r9)
            if (r0 != 0) goto L4e
            goto L4f
        L4e:
            r7 = r1
        L4f:
            if (r7 != 0) goto L56
            int r9 = super.findInstanceIdInfo(r9)
            return r9
        L56:
            if (r7 == r4) goto L69
            if (r7 == r5) goto L67
            if (r7 == r6) goto L67
            if (r7 == r2) goto L67
            if (r7 != r3) goto L61
            goto L67
        L61:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            r9.<init>()
            throw r9
        L67:
            r9 = 7
            goto L6b
        L69:
            int r9 = r8.lastIndexAttr
        L6b:
            int r9 = org.mozilla.javascript.IdScriptableObject.instanceIdInfo(r9, r7)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.findInstanceIdInfo(java.lang.String):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003f  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r6) {
        /*
            r5 = this;
            int r0 = r6.length()
            r1 = 116(0x74, float:1.63E-43)
            r2 = 4
            r3 = 0
            if (r0 == r2) goto L2e
            r2 = 6
            if (r0 == r2) goto L2b
            r2 = 7
            if (r0 == r2) goto L27
            r2 = 8
            if (r0 == r2) goto L15
            goto L3f
        L15:
            r2 = 3
            char r0 = r6.charAt(r2)
            r4 = 111(0x6f, float:1.56E-43)
            if (r0 != r4) goto L21
            java.lang.String r0 = "toSource"
            goto L41
        L21:
            if (r0 != r1) goto L3f
            java.lang.String r0 = "toString"
            r2 = 2
            goto L41
        L27:
            java.lang.String r0 = "compile"
            r2 = 1
            goto L41
        L2b:
            java.lang.String r0 = "prefix"
            goto L41
        L2e:
            char r0 = r6.charAt(r3)
            r4 = 101(0x65, float:1.42E-43)
            if (r0 != r4) goto L39
            java.lang.String r0 = "exec"
            goto L41
        L39:
            if (r0 != r1) goto L3f
            java.lang.String r0 = "test"
            r2 = 5
            goto L41
        L3f:
            r0 = 0
            r2 = r3
        L41:
            if (r0 == 0) goto L4c
            if (r0 == r6) goto L4c
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L4c
            goto L4d
        L4c:
            r3 = r2
        L4d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.findPrototypeId(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "RegExp";
    }

    int getFlags() {
        return this.re.flags;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? super.getInstanceIdName(i2) : "multiline" : "ignoreCase" : "global" : "source" : "lastIndex";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        if (i2 == 1) {
            return this.lastIndex;
        }
        if (i2 == 2) {
            return new String(this.re.source);
        }
        if (i2 == 3) {
            return ScriptRuntime.wrapBoolean((this.re.flags & 1) != 0);
        }
        if (i2 == 4) {
            return ScriptRuntime.wrapBoolean((this.re.flags & 2) != 0);
        }
        if (i2 != 5) {
            return super.getInstanceIdValue(i2);
        }
        return ScriptRuntime.wrapBoolean((this.re.flags & 4) != 0);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 5;
    }

    @Override // org.mozilla.javascript.ScriptableObject
    public String getTypeOf() {
        return "object";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        String str2;
        int i3 = 0;
        int i4 = 1;
        switch (i2) {
            case 1:
                i3 = 2;
                str = "compile";
                String str3 = str;
                i4 = i3;
                str2 = str3;
                initPrototypeMethod(REGEXP_TAG, i2, str2, i4);
                return;
            case 2:
                str = "toString";
                String str32 = str;
                i4 = i3;
                str2 = str32;
                initPrototypeMethod(REGEXP_TAG, i2, str2, i4);
                return;
            case 3:
                str = "toSource";
                String str322 = str;
                i4 = i3;
                str2 = str322;
                initPrototypeMethod(REGEXP_TAG, i2, str2, i4);
                return;
            case 4:
                str2 = "exec";
                initPrototypeMethod(REGEXP_TAG, i2, str2, i4);
                return;
            case 5:
                str2 = "test";
                initPrototypeMethod(REGEXP_TAG, i2, str2, i4);
                return;
            case 6:
                str2 = RequestParameters.PREFIX;
                initPrototypeMethod(REGEXP_TAG, i2, str2, i4);
                return;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdAttributes(int i2, int i3) {
        if (i2 != 1) {
            super.setInstanceIdAttributes(i2, i3);
        } else {
            this.lastIndexAttr = i3;
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdValue(int i2, Object obj) {
        if (i2 == 1) {
            this.lastIndex = obj;
        } else {
            if (i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5) {
                return;
            }
            super.setInstanceIdValue(i2, obj);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(IOUtils.DIR_SEPARATOR_UNIX);
        char[] cArr = this.re.source;
        if (cArr.length != 0) {
            sb.append(cArr);
        } else {
            sb.append("(?:)");
        }
        sb.append(IOUtils.DIR_SEPARATOR_UNIX);
        if ((this.re.flags & 1) != 0) {
            sb.append('g');
        }
        if ((this.re.flags & 2) != 0) {
            sb.append('i');
        }
        if ((this.re.flags & 4) != 0) {
            sb.append('m');
        }
        return sb.toString();
    }

    private static void pushBackTrackState(REGlobalData rEGlobalData, byte b2, int i2, int i3, int i4, int i5) {
        rEGlobalData.backTrackStackTop = new REBackTrackData(rEGlobalData, b2, i2, i3, i4, i5);
    }

    NativeRegExp() {
        this.lastIndex = Double.valueOf(0.0d);
        this.lastIndexAttr = 6;
    }
}
