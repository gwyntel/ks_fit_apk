package org.mozilla.javascript;

/* loaded from: classes5.dex */
class DefaultErrorReporter implements ErrorReporter {
    static final DefaultErrorReporter instance = new DefaultErrorReporter();
    private ErrorReporter chainedReporter;
    private boolean forEval;

    private DefaultErrorReporter() {
    }

    static ErrorReporter forEval(ErrorReporter errorReporter) {
        DefaultErrorReporter defaultErrorReporter = new DefaultErrorReporter();
        defaultErrorReporter.forEval = true;
        defaultErrorReporter.chainedReporter = errorReporter;
        return defaultErrorReporter;
    }

    @Override // org.mozilla.javascript.ErrorReporter
    public void error(String str, String str2, int i2, String str3, int i3) {
        String str4;
        if (this.forEval) {
            if (str.startsWith("TypeError: ")) {
                str = str.substring(11);
                str4 = "TypeError";
            } else {
                str4 = "SyntaxError";
            }
            throw ScriptRuntime.constructError(str4, str, str2, i2, str3, i3);
        }
        ErrorReporter errorReporter = this.chainedReporter;
        if (errorReporter == null) {
            throw runtimeError(str, str2, i2, str3, i3);
        }
        errorReporter.error(str, str2, i2, str3, i3);
    }

    @Override // org.mozilla.javascript.ErrorReporter
    public EvaluatorException runtimeError(String str, String str2, int i2, String str3, int i3) {
        ErrorReporter errorReporter = this.chainedReporter;
        return errorReporter != null ? errorReporter.runtimeError(str, str2, i2, str3, i3) : new EvaluatorException(str, str2, i2, str3, i3);
    }

    @Override // org.mozilla.javascript.ErrorReporter
    public void warning(String str, String str2, int i2, String str3, int i3) {
        ErrorReporter errorReporter = this.chainedReporter;
        if (errorReporter != null) {
            errorReporter.warning(str, str2, i2, str3, i3);
        }
    }
}
