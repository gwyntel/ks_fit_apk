package org.mozilla.javascript.regexp;

import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
class NativeRegExpCtor extends BaseFunction {
    private static final int DOLLAR_ID_BASE = 12;
    private static final int Id_AMPERSAND = 6;
    private static final int Id_BACK_QUOTE = 10;
    private static final int Id_DOLLAR_1 = 13;
    private static final int Id_DOLLAR_2 = 14;
    private static final int Id_DOLLAR_3 = 15;
    private static final int Id_DOLLAR_4 = 16;
    private static final int Id_DOLLAR_5 = 17;
    private static final int Id_DOLLAR_6 = 18;
    private static final int Id_DOLLAR_7 = 19;
    private static final int Id_DOLLAR_8 = 20;
    private static final int Id_DOLLAR_9 = 21;
    private static final int Id_PLUS = 8;
    private static final int Id_QUOTE = 12;
    private static final int Id_STAR = 2;
    private static final int Id_UNDERSCORE = 4;
    private static final int Id_input = 3;
    private static final int Id_lastMatch = 5;
    private static final int Id_lastParen = 7;
    private static final int Id_leftContext = 9;
    private static final int Id_multiline = 1;
    private static final int Id_rightContext = 11;
    private static final int MAX_INSTANCE_ID = 21;
    static final long serialVersionUID = -5733330028285400526L;
    private int multilineAttr = 4;
    private int starAttr = 4;
    private int inputAttr = 4;
    private int underscoreAttr = 4;

    NativeRegExpCtor() {
    }

    private static RegExpImpl getImpl() {
        return (RegExpImpl) ScriptRuntime.getRegExpProxy(Context.getCurrentContext());
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (objArr.length > 0) {
            Object obj = objArr[0];
            if ((obj instanceof NativeRegExp) && (objArr.length == 1 || objArr[1] == Undefined.instance)) {
                return obj;
            }
        }
        return construct(context, scriptable, objArr);
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function
    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        NativeRegExp nativeRegExp = new NativeRegExp();
        nativeRegExp.compile(context, scriptable, objArr);
        ScriptRuntime.setBuiltinProtoAndParent(nativeRegExp, scriptable, TopLevel.Builtins.RegExp);
        return nativeRegExp;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00ea, code lost:
    
        if (r11.charAt(0) == '$') goto L88;
     */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00f9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0109  */
    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findInstanceIdInfo(java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExpCtor.findInstanceIdInfo(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.BaseFunction
    public int getArity() {
        return 2;
    }

    @Override // org.mozilla.javascript.BaseFunction
    public String getFunctionName() {
        return "RegExp";
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        int maxInstanceId = i2 - super.getMaxInstanceId();
        if (1 > maxInstanceId || maxInstanceId > 21) {
            return super.getInstanceIdName(i2);
        }
        switch (maxInstanceId) {
            case 1:
                return "multiline";
            case 2:
                return "$*";
            case 3:
                return "input";
            case 4:
                return "$_";
            case 5:
                return "lastMatch";
            case 6:
                return "$&";
            case 7:
                return "lastParen";
            case 8:
                return "$+";
            case 9:
                return "leftContext";
            case 10:
                return "$`";
            case 11:
                return "rightContext";
            case 12:
                return "$'";
            default:
                return new String(new char[]{'$', (char) (maxInstanceId + 36)});
        }
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        Object parenSubString;
        int maxInstanceId = i2 - super.getMaxInstanceId();
        if (1 > maxInstanceId || maxInstanceId > 21) {
            return super.getInstanceIdValue(i2);
        }
        RegExpImpl impl = getImpl();
        switch (maxInstanceId) {
            case 1:
            case 2:
                return ScriptRuntime.wrapBoolean(impl.multiline);
            case 3:
            case 4:
                parenSubString = impl.input;
                break;
            case 5:
            case 6:
                parenSubString = impl.lastMatch;
                break;
            case 7:
            case 8:
                parenSubString = impl.lastParen;
                break;
            case 9:
            case 10:
                parenSubString = impl.leftContext;
                break;
            case 11:
            case 12:
                parenSubString = impl.rightContext;
                break;
            default:
                parenSubString = impl.getParenSubString(maxInstanceId - 13);
                break;
        }
        return parenSubString == null ? "" : parenSubString.toString();
    }

    @Override // org.mozilla.javascript.BaseFunction
    public int getLength() {
        return 2;
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return super.getMaxInstanceId() + 21;
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdAttributes(int i2, int i3) {
        int maxInstanceId = i2 - super.getMaxInstanceId();
        switch (maxInstanceId) {
            case 1:
                this.multilineAttr = i3;
                break;
            case 2:
                this.starAttr = i3;
                break;
            case 3:
                this.inputAttr = i3;
                break;
            case 4:
                this.underscoreAttr = i3;
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                break;
            default:
                int i4 = maxInstanceId - 13;
                if (i4 < 0 || i4 > 8) {
                    super.setInstanceIdAttributes(i2, i3);
                    break;
                }
                break;
        }
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdValue(int i2, Object obj) throws RuntimeException {
        int maxInstanceId = i2 - super.getMaxInstanceId();
        switch (maxInstanceId) {
            case 1:
            case 2:
                getImpl().multiline = ScriptRuntime.toBoolean(obj);
                break;
            case 3:
            case 4:
                getImpl().input = ScriptRuntime.toString(obj);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                break;
            default:
                int i3 = maxInstanceId - 13;
                if (i3 < 0 || i3 > 8) {
                    super.setInstanceIdValue(i2, obj);
                    break;
                }
                break;
        }
    }
}
