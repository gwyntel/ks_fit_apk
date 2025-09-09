package org.apache.commons.lang;

/* loaded from: classes5.dex */
public class NullArgumentException extends IllegalArgumentException {
    private static final long serialVersionUID = 1174360235354917591L;

    public NullArgumentException(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str == null ? "Argument" : str);
        stringBuffer.append(" must not be null.");
        super(stringBuffer.toString());
    }
}
