package org.mozilla.javascript;

/* loaded from: classes5.dex */
public class EcmaError extends RhinoException {
    static final long serialVersionUID = -6261226256957286699L;
    private String errorMessage;
    private String errorName;

    EcmaError(String str, String str2, String str3, int i2, String str4, int i3) {
        recordErrorOrigin(str3, i2, str4, i3);
        this.errorName = str;
        this.errorMessage = str2;
    }

    @Override // org.mozilla.javascript.RhinoException
    public String details() {
        return this.errorName + ": " + this.errorMessage;
    }

    @Deprecated
    public int getColumnNumber() {
        return columnNumber();
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Deprecated
    public Scriptable getErrorObject() {
        return null;
    }

    @Deprecated
    public int getLineNumber() {
        return lineNumber();
    }

    @Deprecated
    public String getLineSource() {
        return lineSource();
    }

    public String getName() {
        return this.errorName;
    }

    @Deprecated
    public String getSourceName() {
        return sourceName();
    }

    @Deprecated
    public EcmaError(Scriptable scriptable, String str, int i2, int i3, String str2) {
        this("InternalError", ScriptRuntime.toString(scriptable), str, i2, str2, i3);
    }
}
