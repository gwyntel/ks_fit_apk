package com.alibaba.fastjson.serializer;

import org.apache.commons.io.IOUtils;

/* loaded from: classes2.dex */
public class SerialContext {
    public final int features;
    public final Object fieldName;
    public final Object object;
    public final SerialContext parent;

    public SerialContext(SerialContext serialContext, Object obj, Object obj2, int i2, int i3) {
        this.parent = serialContext;
        this.object = obj;
        this.fieldName = obj2;
        this.features = i2;
    }

    public Object getFieldName() {
        return this.fieldName;
    }

    public Object getObject() {
        return this.object;
    }

    public SerialContext getParent() {
        return this.parent;
    }

    public String getPath() {
        return toString();
    }

    public String toString() {
        if (this.parent == null) {
            return "$";
        }
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    protected void toString(StringBuilder sb) {
        SerialContext serialContext = this.parent;
        if (serialContext == null) {
            sb.append('$');
            return;
        }
        serialContext.toString(sb);
        Object obj = this.fieldName;
        if (obj == null) {
            sb.append(".null");
            return;
        }
        if (obj instanceof Integer) {
            sb.append('[');
            sb.append(((Integer) this.fieldName).intValue());
            sb.append(']');
            return;
        }
        sb.append('.');
        String string = this.fieldName.toString();
        for (int i2 = 0; i2 < string.length(); i2++) {
            char cCharAt = string.charAt(i2);
            if ((cCharAt < '0' || cCharAt > '9') && ((cCharAt < 'A' || cCharAt > 'Z') && ((cCharAt < 'a' || cCharAt > 'z') && cCharAt <= 128))) {
                for (int i3 = 0; i3 < string.length(); i3++) {
                    char cCharAt2 = string.charAt(i3);
                    if (cCharAt2 == '\\') {
                        sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                        sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                        sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                    } else if ((cCharAt2 >= '0' && cCharAt2 <= '9') || ((cCharAt2 >= 'A' && cCharAt2 <= 'Z') || ((cCharAt2 >= 'a' && cCharAt2 <= 'z') || cCharAt2 > 128))) {
                        sb.append(cCharAt2);
                    } else if (cCharAt2 == '\"') {
                        sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                        sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                        sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                    } else {
                        sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                        sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                    }
                    sb.append(cCharAt2);
                }
                return;
            }
        }
        sb.append(string);
    }
}
