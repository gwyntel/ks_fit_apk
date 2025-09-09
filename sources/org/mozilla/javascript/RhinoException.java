package org.mozilla.javascript;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.CharArrayWriter;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public abstract class RhinoException extends RuntimeException {
    private static final Pattern JAVA_STACK_PATTERN = Pattern.compile("_c_(.*)_\\d+");
    static final long serialVersionUID = 1883500631321581169L;
    private static StackStyle stackStyle;
    private int columnNumber;
    int[] interpreterLineData;
    Object interpreterStackInfo;
    private int lineNumber;
    private String lineSource;
    private String sourceName;

    /* renamed from: org.mozilla.javascript.RhinoException$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$mozilla$javascript$StackStyle;

        static {
            int[] iArr = new int[StackStyle.values().length];
            $SwitchMap$org$mozilla$javascript$StackStyle = iArr;
            try {
                iArr[StackStyle.MOZILLA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$mozilla$javascript$StackStyle[StackStyle.V8.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$mozilla$javascript$StackStyle[StackStyle.RHINO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static {
        StackStyle stackStyle2 = StackStyle.RHINO;
        stackStyle = stackStyle2;
        String property = System.getProperty("rhino.stack.style");
        if (property != null) {
            if ("Rhino".equalsIgnoreCase(property)) {
                stackStyle = stackStyle2;
            } else if ("Mozilla".equalsIgnoreCase(property)) {
                stackStyle = StackStyle.MOZILLA;
            } else if ("V8".equalsIgnoreCase(property)) {
                stackStyle = StackStyle.V8;
            }
        }
    }

    RhinoException() {
        Evaluator evaluatorCreateInterpreter = Context.createInterpreter();
        if (evaluatorCreateInterpreter != null) {
            evaluatorCreateInterpreter.captureStackInfo(this);
        }
    }

    static String formatStackTrace(ScriptStackElement[] scriptStackElementArr, String str) {
        StringBuilder sb = new StringBuilder();
        String systemProperty = SecurityUtilities.getSystemProperty("line.separator");
        if (stackStyle == StackStyle.V8 && !TmpConstant.GROUP_ROLE_UNKNOWN.equals(str)) {
            sb.append(str);
            sb.append(systemProperty);
        }
        for (ScriptStackElement scriptStackElement : scriptStackElementArr) {
            int i2 = AnonymousClass1.$SwitchMap$org$mozilla$javascript$StackStyle[stackStyle.ordinal()];
            if (i2 == 1) {
                scriptStackElement.renderMozillaStyle(sb);
            } else if (i2 == 2) {
                scriptStackElement.renderV8Style(sb);
            } else if (i2 == 3) {
                scriptStackElement.renderJavaStyle(sb);
            }
            sb.append(systemProperty);
        }
        return sb.toString();
    }

    private String generateStackTrace() {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        super.printStackTrace(new PrintWriter(charArrayWriter));
        String string = charArrayWriter.toString();
        Evaluator evaluatorCreateInterpreter = Context.createInterpreter();
        if (evaluatorCreateInterpreter != null) {
            return evaluatorCreateInterpreter.getPatchedStack(this, string);
        }
        return null;
    }

    public static StackStyle getStackStyle() {
        return stackStyle;
    }

    public static void setStackStyle(StackStyle stackStyle2) {
        stackStyle = stackStyle2;
    }

    public static void useMozillaStackStyle(boolean z2) {
        stackStyle = z2 ? StackStyle.MOZILLA : StackStyle.RHINO;
    }

    public static boolean usesMozillaStackStyle() {
        return stackStyle == StackStyle.MOZILLA;
    }

    public final int columnNumber() {
        return this.columnNumber;
    }

    public String details() {
        return super.getMessage();
    }

    @Override // java.lang.Throwable
    public final String getMessage() {
        String strDetails = details();
        if (this.sourceName == null || this.lineNumber <= 0) {
            return strDetails;
        }
        StringBuilder sb = new StringBuilder(strDetails);
        sb.append(" (");
        String str = this.sourceName;
        if (str != null) {
            sb.append(str);
        }
        if (this.lineNumber > 0) {
            sb.append('#');
            sb.append(this.lineNumber);
        }
        sb.append(')');
        return sb.toString();
    }

    public ScriptStackElement[] getScriptStack() {
        return getScriptStack(-1, null);
    }

    public String getScriptStackTrace() {
        return getScriptStackTrace(-1, null);
    }

    public final void initColumnNumber(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException(String.valueOf(i2));
        }
        if (this.columnNumber > 0) {
            throw new IllegalStateException();
        }
        this.columnNumber = i2;
    }

    public final void initLineNumber(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException(String.valueOf(i2));
        }
        if (this.lineNumber > 0) {
            throw new IllegalStateException();
        }
        this.lineNumber = i2;
    }

    public final void initLineSource(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        if (this.lineSource != null) {
            throw new IllegalStateException();
        }
        this.lineSource = str;
    }

    public final void initSourceName(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        if (this.sourceName != null) {
            throw new IllegalStateException();
        }
        this.sourceName = str;
    }

    public final int lineNumber() {
        return this.lineNumber;
    }

    public final String lineSource() {
        return this.lineSource;
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        if (this.interpreterStackInfo == null) {
            super.printStackTrace(printWriter);
        } else {
            printWriter.print(generateStackTrace());
        }
    }

    final void recordErrorOrigin(String str, int i2, String str2, int i3) {
        if (i2 == -1) {
            i2 = 0;
        }
        if (str != null) {
            initSourceName(str);
        }
        if (i2 != 0) {
            initLineNumber(i2);
        }
        if (str2 != null) {
            initLineSource(str2);
        }
        if (i3 != 0) {
            initColumnNumber(i3);
        }
    }

    public final String sourceName() {
        return this.sourceName;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.mozilla.javascript.ScriptStackElement[] getScriptStack(int r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.RhinoException.getScriptStack(int, java.lang.String):org.mozilla.javascript.ScriptStackElement[]");
    }

    public String getScriptStackTrace(int i2, String str) {
        return formatStackTrace(getScriptStack(i2, str), details());
    }

    RhinoException(String str) {
        super(str);
        Evaluator evaluatorCreateInterpreter = Context.createInterpreter();
        if (evaluatorCreateInterpreter != null) {
            evaluatorCreateInterpreter.captureStackInfo(this);
        }
    }

    @Deprecated
    public String getScriptStackTrace(FilenameFilter filenameFilter) {
        return getScriptStackTrace();
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        if (this.interpreterStackInfo == null) {
            super.printStackTrace(printStream);
        } else {
            printStream.print(generateStackTrace());
        }
    }
}
