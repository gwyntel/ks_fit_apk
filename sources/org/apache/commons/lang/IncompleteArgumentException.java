package org.apache.commons.lang;

import java.util.Arrays;

/* loaded from: classes5.dex */
public class IncompleteArgumentException extends IllegalArgumentException {
    private static final long serialVersionUID = 4954193403612068178L;

    public IncompleteArgumentException(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(" is incomplete.");
        super(stringBuffer.toString());
    }

    private static final String safeArrayToString(Object[] objArr) {
        if (objArr == null) {
            return null;
        }
        return Arrays.asList(objArr).toString();
    }

    public IncompleteArgumentException(String str, String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(" is missing the following items: ");
        stringBuffer.append(safeArrayToString(strArr));
        super(stringBuffer.toString());
    }
}
