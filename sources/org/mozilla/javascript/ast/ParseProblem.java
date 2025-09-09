package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public class ParseProblem {
    private int length;
    private String message;
    private int offset;
    private String sourceName;
    private Type type;

    public enum Type {
        Error,
        Warning
    }

    public ParseProblem(Type type, String str, String str2, int i2, int i3) {
        setType(type);
        setMessage(str);
        setSourceName(str2);
        setFileOffset(i2);
        setLength(i3);
    }

    public int getFileOffset() {
        return this.offset;
    }

    public int getLength() {
        return this.length;
    }

    public String getMessage() {
        return this.message;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public Type getType() {
        return this.type;
    }

    public void setFileOffset(int i2) {
        this.offset = i2;
    }

    public void setLength(int i2) {
        this.length = i2;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setSourceName(String str) {
        this.sourceName = str;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(200);
        sb.append(this.sourceName);
        sb.append(":");
        sb.append("offset=");
        sb.append(this.offset);
        sb.append(",");
        sb.append("length=");
        sb.append(this.length);
        sb.append(",");
        sb.append(this.type == Type.Error ? "error: " : "warning: ");
        sb.append(this.message);
        return sb.toString();
    }
}
