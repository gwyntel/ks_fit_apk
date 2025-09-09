package org.mozilla.javascript;

/* loaded from: classes5.dex */
public class EvaluatorException extends RhinoException {
    static final long serialVersionUID = -8743165779676009808L;

    public EvaluatorException(String str) {
        super(str);
    }

    @Deprecated
    public int getColumnNumber() {
        return columnNumber();
    }

    @Deprecated
    public int getLineNumber() {
        return lineNumber();
    }

    @Deprecated
    public String getLineSource() {
        return lineSource();
    }

    @Deprecated
    public String getSourceName() {
        return sourceName();
    }

    public EvaluatorException(String str, String str2, int i2) {
        this(str, str2, i2, null, 0);
    }

    public EvaluatorException(String str, String str2, int i2, String str3, int i3) {
        super(str);
        recordErrorOrigin(str2, i2, str3, i3);
    }
}
