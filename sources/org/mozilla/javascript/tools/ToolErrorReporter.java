package org.mozilla.javascript.tools;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.SecurityUtilities;
import org.mozilla.javascript.WrappedException;

/* loaded from: classes5.dex */
public class ToolErrorReporter implements ErrorReporter {
    private static final String messagePrefix = "js: ";
    private PrintStream err;
    private boolean hasReportedErrorFlag;
    private boolean reportWarnings;

    public ToolErrorReporter(boolean z2) {
        this(z2, System.err);
    }

    private String buildIndicator(int i2) {
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < i2 - 1; i3++) {
            sb.append(".");
        }
        sb.append("^");
        return sb.toString();
    }

    private static String getExceptionMessage(RhinoException rhinoException) {
        return rhinoException instanceof JavaScriptException ? getMessage("msg.uncaughtJSException", rhinoException.details()) : rhinoException instanceof EcmaError ? getMessage("msg.uncaughtEcmaError", rhinoException.details()) : rhinoException instanceof EvaluatorException ? rhinoException.details() : rhinoException.toString();
    }

    public static String getMessage(String str) {
        return getMessage(str, (Object[]) null);
    }

    private void reportErrorMessage(String str, String str2, int i2, String str3, int i3, boolean z2) {
        String message;
        if (i2 > 0) {
            String strValueOf = String.valueOf(i2);
            message = str2 != null ? getMessage("msg.format3", new Object[]{str2, strValueOf, str}) : getMessage("msg.format2", new Object[]{strValueOf, str});
        } else {
            message = getMessage("msg.format1", new Object[]{str});
        }
        if (z2) {
            message = getMessage("msg.warning", message);
        }
        this.err.println(messagePrefix + message);
        if (str3 != null) {
            this.err.println(messagePrefix + str3);
            this.err.println(messagePrefix + buildIndicator(i3));
        }
    }

    public static void reportException(ErrorReporter errorReporter, RhinoException rhinoException) {
        if (errorReporter instanceof ToolErrorReporter) {
            ((ToolErrorReporter) errorReporter).reportException(rhinoException);
        } else {
            errorReporter.error(getExceptionMessage(rhinoException), rhinoException.sourceName(), rhinoException.lineNumber(), rhinoException.lineSource(), rhinoException.columnNumber());
        }
    }

    @Override // org.mozilla.javascript.ErrorReporter
    public void error(String str, String str2, int i2, String str3, int i3) {
        this.hasReportedErrorFlag = true;
        reportErrorMessage(str, str2, i2, str3, i3, false);
    }

    public boolean hasReportedError() {
        return this.hasReportedErrorFlag;
    }

    public boolean isReportingWarnings() {
        return this.reportWarnings;
    }

    @Override // org.mozilla.javascript.ErrorReporter
    public EvaluatorException runtimeError(String str, String str2, int i2, String str3, int i3) {
        return new EvaluatorException(str, str2, i2, str3, i3);
    }

    public void setIsReportingWarnings(boolean z2) {
        this.reportWarnings = z2;
    }

    @Override // org.mozilla.javascript.ErrorReporter
    public void warning(String str, String str2, int i2, String str3, int i3) {
        if (this.reportWarnings) {
            reportErrorMessage(str, str2, i2, str3, i3, true);
        }
    }

    public ToolErrorReporter(boolean z2, PrintStream printStream) {
        this.reportWarnings = z2;
        this.err = printStream;
    }

    public static String getMessage(String str, String str2) {
        return getMessage(str, new Object[]{str2});
    }

    public static String getMessage(String str, Object obj, Object obj2) {
        return getMessage(str, new Object[]{obj, obj2});
    }

    public static String getMessage(String str, Object[] objArr) {
        Context currentContext = Context.getCurrentContext();
        try {
            String string = ResourceBundle.getBundle("org.mozilla.javascript.tools.resources.Messages", currentContext == null ? Locale.getDefault() : currentContext.getLocale()).getString(str);
            return objArr == null ? string : new MessageFormat(string).format(objArr);
        } catch (MissingResourceException unused) {
            throw new RuntimeException("no message resource found for message property " + str);
        }
    }

    public void reportException(RhinoException rhinoException) {
        if (rhinoException instanceof WrappedException) {
            ((WrappedException) rhinoException).printStackTrace(this.err);
            return;
        }
        reportErrorMessage(getExceptionMessage(rhinoException) + SecurityUtilities.getSystemProperty("line.separator") + rhinoException.getScriptStackTrace(), rhinoException.sourceName(), rhinoException.lineNumber(), rhinoException.lineSource(), rhinoException.columnNumber(), false);
    }
}
